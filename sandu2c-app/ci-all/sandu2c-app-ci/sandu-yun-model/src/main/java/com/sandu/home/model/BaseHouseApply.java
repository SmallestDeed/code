package com.sandu.home.model;

import com.sandu.common.model.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

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
    @ApiModelProperty(required = true)
    @NotBlank(message = "所在地区必填")
    private String cityInfo;
    /**
     * 小区信息
     **/
    @ApiModelProperty(required = true)
    @NotBlank(message = "小区信息必填")
    private String livingInfo;
    /**
     * 户型名称
     **/
    @ApiModelProperty(required = true)
    private String houseName;
    /**
     * 小区面积
     **/
    @ApiModelProperty(required = true)
    private String houseArea;
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
     * 联系人电话
     **/
    private String contactNumber;

    /**
     * 是否发送模板消息通知 =>{} 0.不需要,1.需要
     */
    private Integer enteringAdviceMsgFlag;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    /**户型类型（几室几厅）**/
    private String houseType;

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
