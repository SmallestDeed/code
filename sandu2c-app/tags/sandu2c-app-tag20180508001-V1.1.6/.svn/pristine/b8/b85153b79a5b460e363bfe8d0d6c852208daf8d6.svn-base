package com.sandu.home.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseHouseApply.java
 * @Package com.sandu.home.model
 * @Description:户型房型-户型申请表
 * @createAuthor pandajun
 * @CreateDate 2016-10-13 11:45:31
 */
@Data
public class BaseHouseApply extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 申请时间
     **/
    private Date applyTime;
    /**
     * 用户id
     **/
    private Integer userId;
    /**
     * 图片id
     **/
    private Integer picId;
    /**
     * 描述
     **/
    private String description;
    /**
     * 处理状态
     **/
    private Integer status;
    /**
     * 城市信息
     **/
    private String cityInfo;
    /**
     * 小区信息
     **/
    private String livingInfo;
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
    /**
     * 整数备用2
     **/
    private Integer numa2;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 小区名称
     **/
    private String houseName;
    /**
     * 小区面积
     **/
    private String houseArea;
    /**
     * 平台编码
     **/
    private String platform;
    /**
     * 业务模块 house
     **/
    private String module;
    /**
     * 图片类型 image
     **/
    private String type;


    /**
     * 获取对象的copy
     **/
    public BaseHouseApply copy() {
        BaseHouseApply obj = new BaseHouseApply();
        obj.setApplyTime(this.applyTime);
        obj.setUserId(this.userId);
        obj.setPicId(this.picId);
        obj.setDescription(this.description);
        obj.setStatus(this.status);
        obj.setCityInfo(this.cityInfo);
        obj.setLivingInfo(this.livingInfo);
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
        map.put("applyTime", this.applyTime);
        map.put("userId", this.userId);
        map.put("picId", this.picId);
        map.put("description", this.description);
        map.put("status", this.status);
        map.put("cityInfo", this.cityInfo);
        map.put("livingInfo", this.livingInfo);
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
