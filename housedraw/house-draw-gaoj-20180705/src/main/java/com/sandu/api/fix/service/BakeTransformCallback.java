package com.sandu.api.fix.service;

import com.sandu.api.house.model.*;

import java.util.List;

/**
 * 烘焙Transform回调
 */
public interface BakeTransformCallback {
    void apply(DrawBaseHouse drawHouse,
               DrawSpaceCommon drawSpace,
               DrawDesignTemplet drawDesignTemplets,
               List<DrawBaseProduct> drawProducts,
               List<DrawDesignTempletProduct> drawDesignTempletProducts);
}
