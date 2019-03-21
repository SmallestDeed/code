package com.sandu.service.v2.house.impl;

import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.service.*;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.v2.house.service.CleanDrawHouseHandler;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/16
 */

@Slf4j
@Service("generalBakeCallbackCleanHandler")
public class GeneralBakeCallbackCleanHandler implements CleanDrawHouseHandler {

    @Autowired
    DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    DrawBakeTaskService drawBakeTaskService;

    @Autowired
    DrawResFileService drawResFileService;

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    DrawSpaceCommonService drawSpaceCommonService;

    @Autowired
    DrawDesignTempletService drawDesignTempletService;

    @Autowired
    DrawDesignTempletProductService drawDesignTempletProductService;

    @Override
    public Integer cleanOldDrawHouse(DrawBaseHouse drawHouse, DrawBakeTaskDetail subTask) {
        Long houseId = drawHouse.getId();
        Long taskId = subTask.getTaskId();
        if (houseId == null || taskId == null) {
            log.warn("参数 houseId 或 taskId 为空");
            return null;
        }

        // 是否第一个空间烘焙
        int taskCount = drawBakeTaskService.getBakeSuccessTask(taskId);
        if (taskCount != DrawBaseHouseConstant.EMPTY_DATA_INIT_FLAG) {
            log.debug("已清空过正式表数据，不需要清空数据");
            return null;
        }

        if (drawHouse.getBaseHouseId() == null || drawHouse.getBaseHouseId() <= 0) {
            log.info("没有需要清除的旧绘制的户型数据，{}(houseId)", houseId);
            return null;
        }

        List<Long> emptySpaces = drawSpaceCommonService.getEmptyDealSpaceCommon(drawHouse.getBaseHouseId());
        if (emptySpaces == null || emptySpaces.isEmpty()) {
            log.info("没有需要清除的旧绘制的户型数据，{}(houseId)", houseId);
            return null;
        }

        log.info("开始删除正式老数据户型数据，{}(emptySpaces)", emptySpaces);

        // 空间
        int updateCount = drawSpaceCommonService.emptySpaceCommon(DrawBaseHouseConstant.IS_DELETED, emptySpaces);

        // 户型、空间关系
        updateCount += drawSpaceCommonService.emptyHouseSpaceRelation(DrawBaseHouseConstant.IS_DELETED, emptySpaces);

        // 这个查询要在删除design_templet之前执行
        List<Long> designTemplets = drawDesignTempletService.getEmptyDesignTemplet(emptySpaces);
        // 样板房硬装产品和软装产品
        if (designTemplets != null && !designTemplets.isEmpty()) {
            updateCount += drawDesignTempletProductService.emptyDesignTempletProduct(DrawBaseHouseConstant.IS_DELETED, designTemplets);
        }

        // 样板房
        updateCount += drawDesignTempletService.emptyDesignTemplet(DrawBaseHouseConstant.IS_DELETED, emptySpaces);

        // 硬装产品
        List<Long> baseProducts = drawBaseProductService.getEmptyBaseProduct(emptySpaces);
        if (baseProducts != null && !baseProducts.isEmpty()) {
            updateCount += drawBaseProductService.emptyBaseProduct(DrawBaseHouseConstant.IS_DELETED, baseProducts);
        }

        // 清除assetbundle资源文件、draw_res_file表相关的assetbundle的文件记录
        List<Long> emptyDrawSpaces = drawSpaceCommonService.getDealDrawSpaceCommon(DrawBaseHouseConstant.IS_DELETED, houseId);
        if (emptyDrawSpaces != null && !emptyDrawSpaces.isEmpty()) {
            updateCount += drawResFileService.clearDrawResFileResource(emptyDrawSpaces, 1);
        }

        log.info("删除正式表的老数据户型数据完成，{}(houseId)", houseId);

        return updateCount;
    }
}
