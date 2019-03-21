package com.sandu.user.model;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:58 2018/6/12 0012
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/6/12 0012PM 8:58
 */
@Data
public class CompanyShopVo  implements Serializable{
    private static final long serialVersionUID = -5178774959932982698L;

    private Integer companyShopId; //店铺ID

    private String companyShopName; //店铺名称


    private String companyShopPicPath; //店铺图片

    private String companyShopDesc; //店铺详情

    private String provinceName;

    private String cityName;

    private String areaName;

    private String streetName;
}
