package com.sandu.product.model;

import com.sandu.pay.order.metadata.ProductType;

/**
 * 产品类型处理
 * @Author yzw
 * @Date 2018/1/24 10:46
 */
public class ProductUtil {

    /**
     * 获取产品名称
     *
     * @param productType 产品类型
     * @return
     */
    public static String getProductNameByProductType(String productType) {
        if (productType.equals(ProductType.COMMON_RENDER)) {// 普通照片级
            return ProductType.COMMON_RENDER_NAME;
        } else if (productType.equals(ProductType.HD_RENDER)) {// 高清照片级
            return ProductType.HD_RENDER_NAME;
        } else if (productType.equals(ProductType.UHD_RENDER)) {// 超高清照片级
            return ProductType.UHD_RENDER_NAME;
        } else if (productType.equals(ProductType.AUTHCODE_PURCHASE)) {// 购买序列号
            return ProductType.AUTHCODE_PURCHASE_NAME;
        } else if (productType.equals(ProductType.AUTHCODE_RENEW)) {// 序列号续费
            return ProductType.AUTHCODE_RENEW_NAME;
        } else if (productType.equals(ProductType.AUTHCODE_DREDGE)) {// 开通序列号
            return ProductType.AUTHCODE_DREDGE_NAME;
        } else if (productType.equals(ProductType.PANORAMA_RENDER)) {// 720全景
            return ProductType.PANORAMA_RENDER_NAME;
        } else if (productType.equals(ProductType.ROAM_VIDEO_RENDER)) {// 漫游视频
            return ProductType.ROAM_VIDEO_RENDER_NAME;
        } else if (productType.equals(ProductType.ROAM_PANORAMA_RENDER)) {// 720多点
            return ProductType.ROAM_PANORAMA_RENDER_NAME;
        }
        return "Sandu";
    }
}
