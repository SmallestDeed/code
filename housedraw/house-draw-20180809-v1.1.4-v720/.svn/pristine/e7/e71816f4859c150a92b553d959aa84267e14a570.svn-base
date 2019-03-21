package com.sandu.api.fix.service;

import com.sandu.api.house.model.*;
import com.sandu.api.v2.house.bo.CallbackTransformBO;

import java.util.List;

/**
 * 烘焙Transform回调
 */
public interface BakeTransformCallback {

    void apply(CallbackTransformBO transformBO);
    void apply(DrawBaseHouse drawHouse,
               DrawSpaceCommon drawSpace,
               DrawDesignTemplet drawDesignTemplets,
               List<DrawBaseProduct> drawProducts,
               List<DrawDesignTempletProduct> drawDesignTempletProducts);
}
