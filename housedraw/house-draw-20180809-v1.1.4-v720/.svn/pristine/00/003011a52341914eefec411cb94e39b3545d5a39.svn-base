package com.sandu.service.house.impl;

import com.sandu.api.fix.service.BakeTransformCallback;
import com.sandu.api.house.model.*;
import com.sandu.api.house.service.DesignTempletJumpPositionRelService;
import com.sandu.api.house.service.DrawBaseHouseService;
import com.sandu.api.v2.house.bo.CallbackTransformBO;
import com.sandu.common.constant.house.DrawDesignTempletProductConstant;
import com.sandu.mq.queue.SyncMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/19
 */

/**
 * 烘焙处理完时的回调，主要处理更烘焙任务不相关的其它业务逻辑
 */

@Service
public class GeneralBakeTransformCallbackImpl implements BakeTransformCallback {

    @Autowired
    DrawBaseHouseService drawBaseHouseService;

    @Autowired
    DesignTempletJumpPositionRelService designTempletJumpPositionRelService;

    @Override
    public void apply(CallbackTransformBO transformBO) {
        this.apply(transformBO.getDrawHouse(), transformBO.getDrawSpace(),
                transformBO.getDrawDesignTemplet(), transformBO.getHardProducts(),
                transformBO.getDrawDesignTempletProducts());
    }

    @Override
    public void apply(DrawBaseHouse drawHouse, DrawSpaceCommon drawSpace,
                      DrawDesignTemplet drawDesignTemplet, List<DrawBaseProduct> drawProducts,
                      List<DrawDesignTempletProduct> drawDesignTempletProducts) {
        // v1.0.5 rabbitmq搜索推送
        List<Long> pushProducts = drawProducts.stream().filter(p -> !DrawDesignTempletProductConstant.SOFT_PRODUCT.equals(p.getCreateProductStatus()))
                .map(DrawBaseProduct::getBaseProductId).collect(Collectors.toList());
        drawBaseHouseService.doSyncToIndex(SyncMessage.ACTION_ADD, pushProducts);

        // v1.0.7 处理相邻样板房的跳跃点信息(埋点)
        designTempletJumpPositionRelService.transformJumpPositionRel(drawHouse);
    }
}
