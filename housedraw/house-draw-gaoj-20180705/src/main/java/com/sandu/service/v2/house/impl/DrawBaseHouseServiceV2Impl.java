package com.sandu.service.v2.house.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.fix.model.FixCupboardSubmitBO;
import com.sandu.api.fix.service.DrawSpaceSubmitFilter;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawSpaceCommon;
import com.sandu.api.v2.house.bo.DrawHouseSubmitBO;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.service.DrawBaseHouseServiceV2;
import com.sandu.api.v2.house.service.DrawHouseSubmitService;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.util.Regex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DrawBaseHouseServiceV2Impl implements DrawBaseHouseServiceV2 {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DrawHouseSubmitService drawSpaceCommonServiceV2;

    @Autowired
    DrawHouseSubmitService drawDesignTempletServiceV2;

    @Autowired
    DrawHouseSubmitService drawBaseProductServiceV2;

    @Autowired
    DrawHouseSubmitService drawDesignTempletProductServiceV2;

    @Autowired
    DrawBaseHouseMapper drawBaseHouseMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void submit(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginBO) {
        // handler draw space
        List<DrawSpaceBO> drawSpaceBOS = this.getDrawSpaceBOS(dtoNew, loginBO);
        this.handlerSave(drawSpaceBOS, dtoNew.getHouseId().longValue());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<DrawSpaceBO> submit(UserLoginBO loginBO,
                                    FixCupboardSubmitBO fixCupboard,
                                    DrawSpaceSubmitFilter filter) {
        DrawBaseHouseSubmitDTO newSubmit = new DrawBaseHouseSubmitDTO();
        newSubmit.setHouseId(fixCupboard.getHouseId().intValue());
        newSubmit.setSpaceCommonDTOList(fixCupboard.getSpaceCommonDTOList());

        // handler draw space
        List<DrawSpaceBO> drawSpaceBOS = this.getDrawSpaceBOS(newSubmit, loginBO, fixCupboard.getDrawHouse(), filter);

        // save
        this.handlerSave(drawSpaceBOS);

        return drawSpaceBOS;
    }

    /**
     * 处理提交的空间、样板房、产品
     *
     * @param dtoNew
     * @param loginBO
     * @return
     */
    private List<DrawSpaceBO> getDrawSpaceBOS(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginBO) {
        return this.getDrawSpaceBOS(dtoNew, loginBO, dtoNew.getDrawHouse(), null);
    }

    /**
     * 处理提交的空间、样板房、产品
     *
     * @param dtoNew
     * @param loginBO
     * @param drawHouse
     * @param drawSpaceFilter
     * @return
     */
    private List<DrawSpaceBO> getDrawSpaceBOS(DrawBaseHouseSubmitDTO dtoNew,
                                              UserLoginBO loginBO, DrawBaseHouse drawHouse,
                                              DrawSpaceSubmitFilter drawSpaceFilter) {
        DrawHouseSubmitBO houseSubmitBO = new DrawHouseSubmitBO();
        houseSubmitBO.setLoginBO(loginBO);
        houseSubmitBO.setDrawHouse(drawHouse);

        List<DrawSpaceBO> drawSpaceBOS = new ArrayList<>();
        for (DrawSpaceCommonDTO drawSpace : dtoNew.getSpaceCommonDTOList()) {
            DrawSpaceBO drawSpaceBO = new DrawSpaceBO();
            drawSpaceBO.setDrawSpaceCommonDTO(drawSpace);
            houseSubmitBO.setDrawSpaceBO(drawSpaceBO);

            // 处理空间
            DrawSpaceCommon drawSpaceCommon = drawSpaceCommonServiceV2.handlerHouseSubmit(houseSubmitBO);
            drawSpaceBO.setDrawSpaceCommon(drawSpaceCommon);

            // filter space
            if (drawSpaceFilter != null && drawSpaceFilter.test(drawSpaceCommon)) {
                continue;
            }

            // 处理样板房
            DrawDesignTemplet drawDesignTemplet = drawDesignTempletServiceV2.handlerHouseSubmit(houseSubmitBO);
            drawSpaceBO.setDrawDesignTemplet(drawDesignTemplet);
            // 处理 base product
            // 处理 design templet product
            drawBaseProductServiceV2.handlerHouseSubmit(houseSubmitBO);

            // add draw space bo
            drawSpaceBOS.add(drawSpaceBO);
        }

        return drawSpaceBOS;
    }

    /**
     * 保存处理
     *
     * @param drawSpaceBOS
     * @param houseId
     * @return
     */
    private Integer handlerSave(List<DrawSpaceBO> drawSpaceBOS, Long houseId) {
        int updateCount = this.handlerSave(drawSpaceBOS);
        // 户型的总面积处理
        updateCount += handlerHouseArea(drawSpaceBOS, houseId);

        return updateCount;
    }

    /**
     * 修复橱柜save by fix cupboard submit
     *
     * @param drawSpaceBOS
     * @return
     */
    private Integer handlerSave(List<DrawSpaceBO> drawSpaceBOS) {
        // 空间
        int updateCount = drawSpaceCommonServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);
        // 样板房
        updateCount += drawDesignTempletServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);
        // 产品
        updateCount += drawBaseProductServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);
        // 样板房产品
        updateCount += drawDesignTempletProductServiceV2.batchSaveByHouseSubmit(drawSpaceBOS);

        return updateCount;
    }

    /**
     * 户型的总面积处理
     *
     * @param drawSpaceBOS
     * @param houseId
     * @return
     */
    private int handlerHouseArea(List<DrawSpaceBO> drawSpaceBOS, Long houseId) {
        // 户型的总面积处理
        BigDecimal totalArea = new BigDecimal(0);
        for (DrawSpaceBO drawSpaceBO : drawSpaceBOS) {
            // 累计空间面积
            totalArea = this.getSpaceTotalArea(totalArea, drawSpaceBO.getDrawSpaceCommonDTO().getSpaceCommonArea());
        }

        // 更新户型的总面积
        DrawBaseHouse drawHouse = new DrawBaseHouse();
        drawHouse.setTotalArea(Math.round(totalArea.doubleValue()) + "");
        drawHouse.setId(houseId);
        return drawBaseHouseMapper.updateDrawBaseHouse(drawHouse);
    }

    /**
     * 累计空间面积
     *
     * @param totalArea
     * @param spaceTotalArea
     * @return
     */
    private BigDecimal getSpaceTotalArea(BigDecimal totalArea, String spaceTotalArea) {
        return (org.apache.commons.lang3.StringUtils.isNoneBlank(spaceTotalArea) && spaceTotalArea.matches(Regex.DOUBLE_NUMBER.getValue()))
                ? totalArea.add(new BigDecimal(spaceTotalArea)) : totalArea;
    }
}
