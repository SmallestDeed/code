package com.nork.cityunion.model.vo;

import java.io.Serializable;

/**
 * Created by xiaoxc on 2018/1/25 0025.
 */
public class EditUnionDesignPlanStoreReleaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //发布Id
    private Integer releaseId;
    //发布名称
    private String releaseName;
    //门店信息
    private UnionStorefrontVO unionStorefrontVO;
    //联盟组信息
    private UnionGroupVO unionGroupVO;
    //优惠活动信息
    private UnionSpecialOfferVO unionSpecialOfferVO;

    public Integer getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Integer releaseId) {
        this.releaseId = releaseId;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public UnionStorefrontVO getUnionStorefrontVO() {
        return unionStorefrontVO;
    }

    public void setUnionStorefrontVO(UnionStorefrontVO unionStorefrontVO) {
        this.unionStorefrontVO = unionStorefrontVO;
    }

    public UnionGroupVO getUnionGroupVO() {
        return unionGroupVO;
    }

    public void setUnionGroupVO(UnionGroupVO unionGroupVO) {
        this.unionGroupVO = unionGroupVO;
    }

    public UnionSpecialOfferVO getUnionSpecialOfferVO() {
        return unionSpecialOfferVO;
    }

    public void setUnionSpecialOfferVO(UnionSpecialOfferVO unionSpecialOfferVO) {
        this.unionSpecialOfferVO = unionSpecialOfferVO;
    }
}
