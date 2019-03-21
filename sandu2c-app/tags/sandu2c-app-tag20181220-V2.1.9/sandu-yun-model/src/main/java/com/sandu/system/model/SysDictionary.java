package com.sandu.system.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @version V1.0
 * @Title: SysDictionary.java
 * @Package com.sandu.system.model
 * @Description:系统管理-数据字典
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
@Data
public class SysDictionary extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    private String appType;
    /**
     * 字符串类型值
     **/
    private String contactValue;

    private String timeConsuming;

    private String bigValuekey;

    private Integer bigValue;

    private Integer smallValue;

    /**
     * 名称
     **/
    private String name;
    /**
     * 排序
     **/
    private Integer ordering;
    /**
     * 字符备用1
     **/
    private String att1;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 字符备用3
     **/
    private String att3;
    /**
     * 字符备用4
     **/
    private String att4;
    /**
     * 字符备用5
     **/
    private String att5;
    /**
     * 字符备用6
     **/
    private String att6;
    /**
     * 字符备用6
     **/
    private String att7;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 整数备用1
     **/
    private Integer numAtt1;
    /**
     * 整数备用2
     **/
    private Integer picId;
    /**
     * 数字备用1
     **/
    private Double numAtt3;
    /**
     * 数字备用2
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    private String remark;
    /*att1~att6说明信息*/
    private String att1Info;
    private String att2Info;
    private String att3Info;
    private String att4Info;
    private String att5Info;
    private String att6Info;
    private String att7Info;

    /* 是否显示U3D模型 0不显示,1显示 */
    private Integer showU3dModel;

    //关联图片的path
    private String picPath;

    /**
     *  子父关系的子类集合
     */
    private List<SysDictionary> sonList = new ArrayList<>();

    /**
     * 获取对象的copy
     **/
    public SysDictionary copy() {
        SysDictionary obj = new SysDictionary();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setType(this.type);
        obj.setValuekey(this.valuekey);
        obj.setValue(this.value);
        obj.setName(this.name);
        obj.setOrdering(this.ordering);
        obj.setAtt1(this.att1);
        obj.setAtt2(this.att2);
        obj.setAtt3(this.att3);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setNumAtt1(this.numAtt1);
        obj.setPicId(this.picId);
        obj.setNumAtt3(this.numAtt3);
        obj.setNumAtt4(this.numAtt4);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("type", this.type);
        map.put("valuekey", this.valuekey);
        map.put("value", this.value);
        map.put("name", this.name);
        map.put("ordering", this.ordering);
        map.put("att1", this.att1);
        map.put("att2", this.att2);
        map.put("att3", this.att3);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("numAtt1", this.numAtt1);
        map.put("picId", this.picId);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

}
