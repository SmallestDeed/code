package com.sandu.api.shop.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺-方案表--实体类
 * @author WangHaiLin
 * @date 2018/8/30  16:47
 */
@Data
public class CompanyShopDesignPlan implements Serializable {
    /** 主键ID **/
    private Long id;
    /** 店铺Id **/
    private Long shopId;
    /** 方案ID **/
    private Long planId;
    /** 创建者 **/
    private String creator;
    /** 创建时间 **/
    private Date gmtCreate;
    /** 修改者**/
    private String modifier;
    /** 修改时间 **/
    private Date gmtModified;
    /** 发布OR 取消发布**/
    private Integer isDeleted;
    /** 方案类型  1普通  2智能**/
    private Integer planRecommendedType;
}
