package com.sandu.design.model;

import java.io.Serializable;

import com.sandu.design.model.po.DesignRulesPO;

/**   
 * @Title: DesignRules.java 
 * @Package com.nork.designconfig.model
 * @Description:设计配置-设计规则
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 19:56:47
 * @version V1.0   
 */
public class DesignRules extends DesignRulesPO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String rulesTypeName;
	private String rulesSignName;
	private String rulesLevelName;
	private String extName;
	private String rulesModelName;
	
	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;
	/**级别限制的资源数量*/
	private int levelLimitCount=0;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public int getLevelLimitCount() {
		return levelLimitCount;
	}

	public void setLevelLimitCount(int levelLimitCount) {
		this.levelLimitCount = levelLimitCount;
	}

	public String getRulesModelName() {
		return rulesModelName;
	}

	public void setRulesModelName(String rulesModelName) {
		this.rulesModelName = rulesModelName;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public String getRulesLevelName() {
		return rulesLevelName;
	}

	public void setRulesLevelName(String rulesLevelName) {
		this.rulesLevelName = rulesLevelName;
	}

	public String getRulesSignName() {
		return rulesSignName;
	}

	public void setRulesSignName(String rulesSignName) {
		this.rulesSignName = rulesSignName;
	}

	public String getRulesTypeName() {
		return rulesTypeName;
	}

	public void setRulesTypeName(String rulesTypeName) {
		this.rulesTypeName = rulesTypeName;
	}

   @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DesignRules other = (DesignRules) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRulesType() == null ? other.getRulesType() == null : this.getRulesType().equals(other.getRulesType()))
            && (this.getRulesBusiness() == null ? other.getRulesBusiness() == null : this.getRulesBusiness().equals(other.getRulesBusiness()))
            && (this.getRulesSign() == null ? other.getRulesSign() == null : this.getRulesSign().equals(other.getRulesSign()))
            && (this.getRulesLevel() == null ? other.getRulesLevel() == null : this.getRulesLevel().equals(other.getRulesLevel()))
            && (this.getRulesMainObj() == null ? other.getRulesMainObj() == null : this.getRulesMainObj().equals(other.getRulesMainObj()))
            && (this.getRulesMainValue() == null ? other.getRulesMainValue() == null : this.getRulesMainValue().equals(other.getRulesMainValue()))
            && (this.getRulesSecondaryObjs() == null ? other.getRulesSecondaryObjs() == null : this.getRulesSecondaryObjs().equals(other.getRulesSecondaryObjs()))
            && (this.getRulesSecondaryValues() == null ? other.getRulesSecondaryValues() == null : this.getRulesSecondaryValues().equals(other.getRulesSecondaryValues()))
            && (this.getRulesPriority() == null ? other.getRulesPriority() == null : this.getRulesPriority().equals(other.getRulesPriority()))
            && (this.getRulesOrdering() == null ? other.getRulesOrdering() == null : this.getRulesOrdering().equals(other.getRulesOrdering()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getExt4() == null ? other.getExt4() == null : this.getExt4().equals(other.getExt4()))
            && (this.getExt5() == null ? other.getExt5() == null : this.getExt5().equals(other.getExt5()))
            && (this.getExt6() == null ? other.getExt6() == null : this.getExt6().equals(other.getExt6()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
            && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRulesType() == null) ? 0 : getRulesType().hashCode());
        result = prime * result + ((getRulesBusiness() == null) ? 0 : getRulesBusiness().hashCode());
        result = prime * result + ((getRulesSign() == null) ? 0 : getRulesSign().hashCode());
        result = prime * result + ((getRulesLevel() == null) ? 0 : getRulesLevel().hashCode());
        result = prime * result + ((getRulesMainObj() == null) ? 0 : getRulesMainObj().hashCode());
        result = prime * result + ((getRulesMainValue() == null) ? 0 : getRulesMainValue().hashCode());
        result = prime * result + ((getRulesSecondaryObjs() == null) ? 0 : getRulesSecondaryObjs().hashCode());
        result = prime * result + ((getRulesSecondaryValues() == null) ? 0 : getRulesSecondaryValues().hashCode());
        result = prime * result + ((getRulesPriority() == null) ? 0 : getRulesPriority().hashCode());
        result = prime * result + ((getRulesOrdering() == null) ? 0 : getRulesOrdering().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getExt4() == null) ? 0 : getExt4().hashCode());
        result = prime * result + ((getExt5() == null) ? 0 : getExt5().hashCode());
        result = prime * result + ((getExt6() == null) ? 0 : getExt6().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public DesignRules copy(){
       DesignRules obj =  new DesignRules();
       obj.setRulesType(this.getRulesType());
       obj.setRulesBusiness(this.getRulesBusiness());
       obj.setRulesSign(this.getRulesSign());
       obj.setRulesLevel(this.getRulesLevel());
       obj.setRulesMainObj(this.getRulesMainObj());
       obj.setRulesMainValue(this.getRulesMainValue());
       obj.setRulesSecondaryObjs(this.getRulesSecondaryObjs());
       obj.setRulesSecondaryValues(this.getRulesSecondaryValues());
       obj.setRulesPriority(this.getRulesPriority());
       obj.setRulesOrdering(this.getRulesOrdering());
       obj.setExt1(this.getExt1());
       obj.setExt2(this.getExt2());
       obj.setExt3(this.getExt3());
       obj.setExt4(this.getExt4());
       obj.setExt5(this.getExt5());
       obj.setExt6(this.getExt6());
       obj.setSysCode(this.getSysCode());
       obj.setCreator(this.getCreator());
       obj.setGmtCreate(this.getGmtCreate());
       obj.setModifier(this.getModifier());
       obj.setGmtModified(this.getGmtModified());
       obj.setIsDeleted(this.getIsDeleted());
       obj.setAtt1(this.getAtt1());
       obj.setAtt2(this.getAtt2());
       obj.setNuma1(this.getNuma1());
       obj.setNuma2(this.getNuma2());
       obj.setRemark(this.getRemark());
       return obj;
    }
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("rulesType",this.rulesType);
       map.put("rulesBusiness",this.rulesBusiness);
       map.put("rulesSign",this.rulesSign);
       map.put("rulesLevel",this.rulesLevel);
       map.put("rulesMainObj",this.rulesMainObj);
       map.put("rulesMainValue",this.rulesMainValue);
       map.put("rulesSecondaryObjs",this.rulesSecondaryObjs);
       map.put("rulesSecondaryValues",this.rulesSecondaryValues);
       map.put("rulesPriority",this.rulesPriority);
       map.put("rulesOrdering",this.rulesOrdering);
       map.put("ext1",this.ext1);
       map.put("ext2",this.ext2);
       map.put("ext3",this.ext3);
       map.put("ext4",this.ext4);
       map.put("ext5",this.ext5);
       map.put("ext6",this.ext6);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);
       return map;
    }*/
}
