package com.sandu.product.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseProductStyle.java
 * @Package com.sandu.product.model
 * @Description:产品管理-产品风格
 * @createAuthor pandajun
 * @CreateDate 2017-04-17 11:42:49
 */
public class BaseProductStyle extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 父类id
     **/
    private Integer pid;
    /**
     * 名称
     **/
    private String name;
    /**
     * 唯一标识编码
     **/
    private String code;
    /**
     * 新唯一标识编码
     **/
    private String code2;
    /**
     * 方便于搜索的字段
     **/
    private String longCode;
    /**
     * 比如_两条现代简约的数据_value值定为一样
     **/
    private Integer value;
    /**
     * 是否是目录/最下级节点_0:是最底层节点_1:是目录
     **/
    private Integer isLeaf;
    /**
     * 等级目录
     **/
    private Integer level;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 字符备用1
     **/
    private String att1;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 整数备用1
     **/
    private Integer numa1;
    /**  整数备用2  **/
    /**
     * 关联数据字典中空间类型value值
     **/
    private Integer numa2;
    /**
     * 备注
     **/
    private String remark;
    /**
     * longCode右模糊查询
     */
    private String longCode_;
    // (1被选择，0 和 null 或者其他没被选择)
    private Integer designPlanRecommendedBeSelected;


    public Integer getDesignPlanRecommendedBeSelected() {
        return designPlanRecommendedBeSelected;
    }

    public void setDesignPlanRecommendedBeSelected(Integer designPlanRecommendedBeSelected) {
        this.designPlanRecommendedBeSelected = designPlanRecommendedBeSelected;
    }

    public String getLongCode_() {
        return longCode_;
    }

    public void setLongCode_(String longCode_) {
        this.longCode_ = longCode_;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public Integer getNuma1() {
        return numa1;
    }

    public void setNuma1(Integer numa1) {
        this.numa1 = numa1;
    }

    public Integer getNuma2() {
        return numa2;
    }

    public void setNuma2(Integer numa2) {
        this.numa2 = numa2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        BaseProductStyle other = (BaseProductStyle) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
                && (this.getLongCode() == null ? other.getLongCode() == null : this.getLongCode().equals(other.getLongCode()))
                && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                && (this.getIsLeaf() == null ? other.getIsLeaf() == null : this.getIsLeaf().equals(other.getIsLeaf()))
                && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
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
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getLongCode() == null) ? 0 : getLongCode().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getIsLeaf() == null) ? 0 : getIsLeaf().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
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
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public BaseProductStyle copy() {
        BaseProductStyle obj = new BaseProductStyle();
        obj.setPid(this.pid);
        obj.setName(this.name);
        obj.setCode(this.code);
        obj.setLongCode(this.longCode);
        obj.setValue(this.value);
        obj.setIsLeaf(this.isLeaf);
        obj.setLevel(this.level);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setAtt1(this.att1);
        obj.setAtt2(this.att2);
        obj.setNuma1(this.numa1);
        obj.setNuma2(this.numa2);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("pid", this.pid);
        map.put("name", this.name);
        map.put("code", this.code);
        map.put("longCode", this.longCode);
        map.put("value", this.value);
        map.put("isLeaf", this.isLeaf);
        map.put("level", this.level);
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("att1", this.att1);
        map.put("att2", this.att2);
        map.put("numa1", this.numa1);
        map.put("numa2", this.numa2);
        map.put("remark", this.remark);

        return map;
    }
}
