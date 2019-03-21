package com.nork.system.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * 修复历史数据查询条件
 *
 * @auth xiaoxc
 * @data 2018/7/18
 */
public class ResRenderPicQuery implements Serializable {

    private static final long serialVersionUID = 1962647127848941265L;
    public static final int QUERY_RESRENDERPIC_LIMIT = 1000;
    public static final int QUERY_RESRENDERPIC_MAX_LIMIT = 100000;

    //推荐方案ID
    private List<String> recommendedIdList;
    //渲染类型
    private List<Integer> renderingTypeList;
    //渲染图片类型
    private List<String> picTypeList;
    //数据查询初始位
    private int start;
    //一次查询多少条
    private int limit;

    public List<String> getRecommendedIdList() {
        return recommendedIdList;
    }

    public void setRecommendedIdList(List<String> recommendedIdList) {
        this.recommendedIdList = recommendedIdList;
    }

    public List<Integer> getRenderingTypeList() {
        return renderingTypeList;
    }

    public void setRenderingTypeList(List<Integer> renderingTypeList) {
        this.renderingTypeList = renderingTypeList;
    }

    public List<String> getPicTypeList() {
        return picTypeList;
    }

    public void setPicTypeList(List<String> picTypeList) {
        this.picTypeList = picTypeList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
