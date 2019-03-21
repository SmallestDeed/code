package com.sandu.service.templet.impl;

import com.sandu.api.house.bo.JumpPositionDesignTempletProductBO;
import com.sandu.api.house.model.DesignTempletJumpPositionRel;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.service.DesignTempletJumpPositionRelService;
import com.sandu.api.house.service.DrawDesignTempletProductService;
import com.sandu.api.house.service.DrawDesignTempletService;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.house.DoorBigType;
import com.sandu.exception.DrawBusinessException;
import com.sandu.service.templet.dao.DesignTempletJumpPositionRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018/5/24
 */

@Slf4j
@Service
public class DesignTempletJumpPositionRelServiceImpl implements DesignTempletJumpPositionRelService {

    final String EXEC = "bake-task";

    @Autowired
    DesignTempletJumpPositionRelMapper designTempletJumpPositionRelMapper;

    @Autowired
    DrawDesignTempletProductService drawDesignTempletProductService;

    @Autowired
    DrawDesignTempletService drawDesignTempletService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transformJumpPositionRel(DrawBaseHouse drawHouse) {
        if (drawHouse == null) {
            throw new DrawBusinessException(false, ResponseEnum.HOUSE_IS_NOT_FOUND);
        }

        log.info("开始v720房间穿越埋点处理, {}(houseId)", drawHouse.getId());

        List<JumpPositionDesignTempletProductBO> jumpPositions = drawDesignTempletProductService.getJumpPositionDesignTempletProduct(DoorBigType.MENG.toString(), drawHouse.getId());
        if (jumpPositions == null || jumpPositions.isEmpty()) {
            log.warn("{}(houseId)没有需要处理的跳跃点", drawHouse.getId());
            return;
        }

        List<DesignTempletJumpPositionRel> designPositions = new ArrayList<>();
        for (JumpPositionDesignTempletProductBO target : jumpPositions) {
            // 查询当前门可穿越的样板房
            List<JumpPositionDesignTempletProductBO> origins = jumpPositions.stream().filter(t -> !Objects.equals(t.getDrawDtpId(), target.getDrawDtpId())
                    && Objects.equals(t.getUniqueId(), target.getUniqueId())).collect(Collectors.toList());
            if (origins == null || origins.isEmpty()) {
                log.debug("{}(dtpId), {}(uniqueId)没有可跳跃的相邻样板房", target.getDrawDtpId(), target.getUniqueId());
                continue;
            }

            List<DesignTempletJumpPositionRel> positions = this.getJumpPositions(target.getDesignTempletId(), origins, target.getDrawDtpId());
            log.debug("{}(originId)样板房的跳跃点信息 => {}", target.getDesignTempletId(), positions);
            designPositions.addAll(positions);
        }

        if (designPositions.isEmpty()) {
            log.info("{}(houseId)没有需要添加的跳跃点信息", drawHouse.getId());
            return;
        }

        // 处理保存
        this.batchInsertSelective(designPositions);

        log.info("v720房间穿越埋点处理完成, {}(houseId)", drawHouse.getId());
    }

    /**
     * @param targetId
     * @param origins
     * @param drawDtpId
     * @return
     */
    private List<DesignTempletJumpPositionRel> getJumpPositions(Long targetId, List<JumpPositionDesignTempletProductBO> origins, Long drawDtpId) {
        List<DesignTempletJumpPositionRel> jumpPositions = new ArrayList<>();
        for (JumpPositionDesignTempletProductBO origin : origins) {
            // jump position is not null
            if (StringUtils.isEmpty(origin.getProPosition())) {
                log.warn("{}(targetId) jumpPosition信息空", targetId);
               continue;
            }

            DesignTempletJumpPositionRel pos = new DesignTempletJumpPositionRel();
            pos.setOriginId(origin.getDesignTempletId());
            pos.setTargetId(targetId);
            pos.setDrawDtpId(drawDtpId);
            pos.setJumpPosition(origin.getProPosition());
            pos.setCreator(EXEC);
            pos.setModifier(EXEC);
            jumpPositions.add(pos);
        }
        return jumpPositions;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchInsertSelective(List<DesignTempletJumpPositionRel> records) {
        if (records == null || records.isEmpty()) {
            log.warn("参数 records 是空");
            return -1;
        }

        return designTempletJumpPositionRelMapper.batchInsertSelective(records);
    }
}
