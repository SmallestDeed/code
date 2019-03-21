package com.sandu.decorate.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-08-08 15:37
 */
@Data
@Builder
public class PlanDecoratePrice implements Serializable {

    
    /** 主键 */
    private Integer id;
    /** 推荐方案id */
    private Integer planRecommendId;
    /** 效果图方案id */
    private Integer renderSceneId;
    /** 全屋方案id **/
    private Integer fullHouseId;
    /** 报价类型(对应数据字典) */
    private Integer decoratePriceType;
    /** 报价区间(对应数据字典) */
    private Integer decoratePriceRange;
    /** 具体报价 */
    private Integer decoratePrice;
    /** 方案类型（1：推荐方案及效果图方案，2：全屋方案） **/
    private Integer planType;
    /** 创建者 */
    private String creator;
    /** 创建时间 */
    private Date gmtCreate;
    /** 修改人 */
    private String modifier;
    /** 修改时间 */
    private Date gmtModified;
    /** 是否删除（0:否，1:是） */
    private Integer isDeleted;
    /** 备注 */
    private String remark;

    public PlanDecoratePrice() {

    }

    public PlanDecoratePrice(Integer id, Integer planRecommendId, Integer renderSceneId, Integer decoratePriceType, Integer decoratePriceRange, Integer decoratePrice, String creator, Date gmtCreate, String modifier, Date gmtModified, Integer isDeleted, String remark) {
        this.id = id;
        this.planRecommendId = planRecommendId;
        this.renderSceneId = renderSceneId;
        this.decoratePriceType = decoratePriceType;
        this.decoratePriceRange = decoratePriceRange;
        this.decoratePrice = decoratePrice;
        this.creator = creator;
        this.gmtCreate = gmtCreate;
        this.modifier = modifier;
        this.gmtModified = gmtModified;
        this.isDeleted = isDeleted;
        this.remark = remark;
    }

    public PlanDecoratePrice(Integer id, Integer planRecommendId, Integer renderSceneId, Integer fullHouseId, Integer decoratePriceType, Integer decoratePriceRange, Integer decoratePrice, Integer planType, String creator, Date gmtCreate, String modifier, Date gmtModified, Integer isDeleted, String remark) {
        this.id = id;
        this.planRecommendId = planRecommendId;
        this.renderSceneId = renderSceneId;
        this.fullHouseId = fullHouseId;
        this.decoratePriceType = decoratePriceType;
        this.decoratePriceRange = decoratePriceRange;
        this.decoratePrice = decoratePrice;
        this.planType = planType;
        this.creator = creator;
        this.gmtCreate = gmtCreate;
        this.modifier = modifier;
        this.gmtModified = gmtModified;
        this.isDeleted = isDeleted;
        this.remark = remark;
    }
}
