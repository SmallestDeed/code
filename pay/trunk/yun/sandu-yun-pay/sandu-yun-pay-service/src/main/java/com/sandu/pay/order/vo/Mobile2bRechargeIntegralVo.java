package com.sandu.pay.order.vo;

import java.io.Serializable;

/**
 * 移动2b的度币充值的数据字典
 *
 * @Author yzw
 * @Date 2018/3/20 20:38
 */
public class Mobile2bRechargeIntegralVo implements Serializable {
    /**
     * 主键id
     **/
    private Integer id;
    /**
     * 类型
     **/
    private String type;
    /**
     * 唯一标示
     **/
    private String valuekey;
    /**
     * 值
     **/
    private Integer value;
    /**
     * 名称
     **/
    private String name;
    /**
     * 排序
     **/
    private Integer ordering;

    /**
     * 整数备用2
     **/
    private Integer picId;

    //关联图片的path
    private String picPath;

    /**
     * 字符备用1
     **/
    private String att1;
    /**
     * 字符备用2
     **/
    private String att2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValuekey() {
        return valuekey;
    }

    public void setValuekey(String valuekey) {
        this.valuekey = valuekey;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
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

    @Override
    public String toString() {
        return "Mobile2bRechargeIntegralVo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", valuekey='" + valuekey + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                ", ordering=" + ordering +
                ", picId=" + picId +
                ", picPath='" + picPath + '\'' +
                ", att1='" + att1 + '\'' +
                ", att2='" + att2 + '\'' +
                '}';
    }
}
