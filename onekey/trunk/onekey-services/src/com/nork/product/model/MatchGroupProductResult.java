package com.nork.product.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class MatchGroupProductResult implements Serializable {
    private static final long serialVersionUID = 1L;

    //白膜主产品编码
    private String bmMianProductCode;
    //白膜组合产品list
    private List<Map<String,String>> bmGroupProductList;
    //模板主产品code
    private String templetMianProductCode;
    //模板组合配置
    private String templetGroupConfig;
    //模板组合配置
    private String templetPosName;
    //模板组合产品list
    private List<Map<String,String>> templetGroupProductList;
    //方案组合ID
    private String planGroupId;
    /**
     * 是否匹配过
     * 0:未匹配过
     * 1:匹配过
     */
    private Integer isReName = 0;

    public Integer getIsReName() {
		return isReName;
	}

	public void setIsReName(Integer isReName) {
		this.isReName = isReName;
	}

	public String getPlanGroupId() {
        return planGroupId;
    }

    public void setPlanGroupId(String planGroupId) {
        this.planGroupId = planGroupId;
    }

    public List<Map<String, String>> getTempletGroupProductList() {
        return templetGroupProductList;
    }

    public void setTempletGroupProductList(List<Map<String, String>> templetGroupProductList) {
        this.templetGroupProductList = templetGroupProductList;
    }

    public String getTempletPosName() {
        return templetPosName;
    }

    public void setTempletPosName(String templetPosName) {
        this.templetPosName = templetPosName;
    }

    public String getBmMianProductCode() {
        return bmMianProductCode;
    }

    public void setBmMianProductCode(String bmMianProductCode) {
        this.bmMianProductCode = bmMianProductCode;
    }

    public List<Map<String, String>> getBmGroupProductList() {
        return bmGroupProductList;
    }

    public void setBmGroupProductList(List<Map<String, String>> bmGroupProductList) {
        this.bmGroupProductList = bmGroupProductList;
    }

    public String getTempletMianProductCode() {
        return templetMianProductCode;
    }

    public void setTempletMianProductCode(String templetMianProductCode) {
        this.templetMianProductCode = templetMianProductCode;
    }
    public String getTempletGroupConfig() {
        return templetGroupConfig;
    }

    public void setTempletGroupConfig(String templetGroupConfig) {
        this.templetGroupConfig = templetGroupConfig;
    }
}

