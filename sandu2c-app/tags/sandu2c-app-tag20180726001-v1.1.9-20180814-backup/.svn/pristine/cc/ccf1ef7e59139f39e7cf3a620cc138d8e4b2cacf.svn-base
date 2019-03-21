package com.sandu.pay.order.metadata;

import java.io.Serializable;
import java.util.List;

/**
 * 渲染免费信息VO
 * Created by yanghz on 2017/4/27 0027.
 */
public class RenderCheckVo implements Serializable{
    /**
     * 是否免费
     * 1：免费  0：收费
     */
    private Integer isFree;
    /**
     * 免费时间开始
     */
    private String freeTimeStart;
    /**
     * 免费时间结束
     */
    private String freeTimeEnd;
    /**
     * eg:晚10:00-早8:00
     */
    private String freeTimeName;
    
    /**
     * 价格列表
     */
    private List<RenderPriceInfoVo> priceList;
    
    public List<RenderPriceInfoVo> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<RenderPriceInfoVo> priceList) {
		this.priceList = priceList;
	}

	public String getFreeTimeName() {
		return freeTimeName;
	}

	public void setFreeTimeName(String freeTimeName) {
		this.freeTimeName = freeTimeName;
	}

	public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

	public String getFreeTimeStart() {
		return freeTimeStart;
	}

	public void setFreeTimeStart(String freeTimeStart) {
		this.freeTimeStart = freeTimeStart;
	}

	public String getFreeTimeEnd() {
		return freeTimeEnd;
	}

	public void setFreeTimeEnd(String freeTimeEnd) {
		this.freeTimeEnd = freeTimeEnd;
	}

	@Override
	public String toString() {
		return "RenderCheckVo [isFree=" + isFree + ", freeTimeStart=" + freeTimeStart + ", freeTimeEnd=" + freeTimeEnd
				+ ", freeTimeName=" + freeTimeName + "]";
	}

    
}
