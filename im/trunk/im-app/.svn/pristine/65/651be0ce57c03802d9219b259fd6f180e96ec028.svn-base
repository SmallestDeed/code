package com.sandu.im.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseHouse.java
 * @Package com.sandu.business.model
 * @Description:业务-户型
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
@Data
public class BaseHouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userId;
    private Integer isSort;

    //户型截图ID
    private Integer snapPicId;

	Integer spaceCommonStatusList[] = null;//存放空间状态的list  用于in 查询
	Integer designTempletPutawayStateList[] = null; //存放样板房状态的list  用于in 查询

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
     * 户型编码
     **/
    private String houseCode;
    /**
     * 户型简称
     **/
    private String houseShortName;
    /**
     * 户型全称
     **/
    private String houseName;
    /**
     * 户型图
     **/
    private Integer picRes1Id;
    /**
     * CAD图
     **/
    private Integer picRes2Id;
    /**
     * 被处理可以分析的户型图
     **/
    private Integer picRes3Id;
    /**
     * 被查询去掉分析数据的户型图
     **/
    private Integer picRes4Id;
    /**
     * 户型描述
     **/
    private String houseDesc;
    /**
     * 所属小区
     **/
    private Integer livingId;
    /**
     * 总面积
     **/
    private String totalArea;
    /**
     * 总楼层
     **/
    private String totalFloors;
    /**
     * 销售期数
     **/
    private String currentPeriod;
    /**
     * 户型高度
     **/
    private String houseHigh;
    /**
     * 适用楼层
     **/
    private String applyFloors;
    /**
     * 户型结构
     **/
    private String houseLayout;
    /**
     * 室结构类型
     **/
    private String roomLayout;
    /**
     * 厅结构类型
     **/
    private String officeLayout;
    /**
     * 过道结构类型
     **/
    private String wayLayout;
    /**
     * 是否存在对称户型
     **/
    private String isExistSymmetry;
    /**
     * 对称户型组ids
     **/
    private String symmetryIds;
    /**
     * 是否合并
     **/
    private String isMerge;
    /**
     * 合并户型组ids
     **/
    private String mergeIds;
    /**
     * 合并后新的户型id
     **/
    private Integer mergeNewId;
    /**
     * 生成模拟图
     **/
    private Integer picResId;
    /**
     * 图形是否规则
     **/
    private Integer isAll;
    /**
     * 户型类型
     **/
    private String houseCommonCode;
    /**
     * 户型标示
     **/
    private String houseTypeCode;
    /**
     * 户型标签
     **/
    private String houseTag;
    /**
     * 户型状态
     **/
    private String houseStatus;
    /**
     * 处理状态
     **/
    private String dealStatus;
    /**
     * 是否审核
     **/
    private String isReview;
    /**
     * 区域字段(废弃)
     **/
    private String areaCode;
    /**
     * 开盘时间
     **/
    private String att1;
    /**
     * 区域longCode
     **/
    private String areaLongCode;
    /**
     * 户型种类
     **/
    private String houseKind;
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
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 重置状态
     **/
    private Integer resetState;
    /**
     * 整数备用2
     **/
    private Integer numAtt2;
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
    //小区名称
    private String livingName;
    //房型
    private String houseTypeStr;

    private String houseType;
    private String houseAddress;

	private Integer isPublic;
    //户型编码
    private String houseDoorCode;
    //空间编码
    private String spaceCode;
    //空间功能类型
    private String spaceFunctionId;
    //空间形状
    private String spaceShape;
    private String thumbnailPath;
    private String largeThumbnailPath;

    //当前页面(default=0)
    private int currentPage = 0;

    //页面大小(default=10)
    private int pageSize = 10;
    private String province;
    private String city;
    private String area;
    private String living;
    private String livingCode;
    private String ting;
    private String shi;
    private String chufang;
    private String weishengjian;
    private String yangtai;
    private String guodao;
    private String xinghao;

    private String areaCode_p;
    private String areaCode_c;
    private String areaCode_a;
    /**
     * 关联查询出来的定位空间信息,后期再处理
     */
    private String spaceTypeStrs;

    private Integer isFavorite;

    private Integer isLike;

    private Integer favoriteNum;

    private Integer likeNum;


    /**
     * 获取对象的copy
     **/
    public BaseHouse copy() {
        BaseHouse obj = new BaseHouse();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setIsPublic(this.isPublic);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setHouseCode(this.houseCode);
        obj.setHouseShortName(this.houseShortName);
        obj.setHouseName(this.houseName);
        obj.setPicRes1Id(this.picRes1Id);
        obj.setPicRes2Id(this.picRes2Id);
        obj.setPicRes3Id(this.picRes3Id);
        obj.setPicRes4Id(this.picRes4Id);
        obj.setHouseDesc(this.houseDesc);
        obj.setLivingId(this.livingId);
        obj.setTotalArea(this.totalArea);
        obj.setTotalFloors(this.totalFloors);
        obj.setCurrentPeriod(this.currentPeriod);
        obj.setHouseHigh(this.houseHigh);
        obj.setApplyFloors(this.applyFloors);
        obj.setHouseLayout(this.houseLayout);
        obj.setRoomLayout(this.roomLayout);
        obj.setOfficeLayout(this.officeLayout);
        obj.setWayLayout(this.wayLayout);
        obj.setIsExistSymmetry(this.isExistSymmetry);
        obj.setSymmetryIds(this.symmetryIds);
        obj.setIsMerge(this.isMerge);
        obj.setMergeIds(this.mergeIds);
        obj.setMergeNewId(this.mergeNewId);
        obj.setPicResId(this.picResId);
        obj.setIsAll(this.isAll);
        obj.setHouseCommonCode(this.houseCommonCode);
        obj.setHouseTypeCode(this.houseTypeCode);
        obj.setHouseTag(this.houseTag);
        obj.setHouseStatus(this.houseStatus);
        obj.setDealStatus(this.dealStatus);
        obj.setIsReview(this.isReview);
        obj.setAreaCode(this.areaCode);
        obj.setAtt1(this.att1);
        obj.setAreaLongCode(this.areaLongCode);
        obj.setHouseKind(this.houseKind);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setResetState(this.resetState);
        obj.setNumAtt2(this.numAtt2);
        obj.setNumAtt3(this.numAtt3);
        obj.setNumAtt4(this.numAtt4);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("isPublic", this.isPublic);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("houseCode", this.houseCode);
        map.put("houseShortName", this.houseShortName);
        map.put("houseName", this.houseName);
        map.put("picRes1Id", this.picRes1Id);
        map.put("picRes2Id", this.picRes2Id);
        map.put("picRes3Id", this.picRes3Id);
        map.put("picRes4Id", this.picRes4Id);
        map.put("houseDesc", this.houseDesc);
        map.put("livingId", this.livingId);
        map.put("totalArea", this.totalArea);
        map.put("totalFloors", this.totalFloors);
        map.put("currentPeriod", this.currentPeriod);
        map.put("houseHigh", this.houseHigh);
        map.put("applyFloors", this.applyFloors);
        map.put("houseLayout ", this.houseLayout);
        map.put("roomLayout", this.roomLayout);
        map.put("officeLayout", this.officeLayout);
        map.put("wayLayout", this.wayLayout);
        map.put("isExistSymmetry", this.isExistSymmetry);
        map.put("symmetryIds", this.symmetryIds);
        map.put("isMerge", this.isMerge);
        map.put("mergeIds", this.mergeIds);
        map.put("mergeNewId", this.mergeNewId);
        map.put("picResId", this.picResId);
        map.put("isAll", this.isAll);
        map.put("houseCommonCode", this.houseCommonCode);
        map.put("houseTypeCode", this.houseTypeCode);
        map.put("houseTag", this.houseTag);
        map.put("houseStatus", this.houseStatus);
        map.put("dealStatus", this.dealStatus);
        map.put("isReview", this.isReview);
        map.put("areaCode", this.areaCode);
        map.put("att1", this.att1);
        map.put("areaLongCode", this.areaLongCode);
        map.put("houseKind", this.houseKind);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("resetState", this.resetState);
        map.put("numAtt2", this.numAtt2);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

    /**
     * 获取对象的map
     **/
    public BaseHouse toObj(Map<String, Object> map) {
        BaseHouse obj = new BaseHouse();
        if (map != null && map.size() > 0) {
            obj.setSysCode((String) map.get("sysCode"));
            obj.setCreator((String) map.get("creator"));
            obj.setIsPublic((Integer) map.get("isPublic"));
            obj.setGmtCreate((Date) map.get("gmtCreate"));
            obj.setModifier((String) map.get("modifier"));
            obj.setGmtModified((Date) map.get("gmtModified"));
            obj.setIsDeleted((Integer) map.get("isDeleted"));
            obj.setHouseCode((String) map.get("houseCode"));
            obj.setHouseShortName((String) map.get("houseShortName"));
            obj.setHouseName((String) map.get("houseName"));
            obj.setPicRes1Id((Integer) map.get("picRes1Id"));
            obj.setPicRes2Id((Integer) map.get("picRes2Id"));
            obj.setPicRes3Id((Integer) map.get("picRes3Id"));
            obj.setPicRes4Id((Integer) map.get("picRes4Id"));
            obj.setHouseDesc((String) map.get("houseDesc"));
            obj.setLivingId((Integer) map.get("livingId"));
            obj.setTotalArea((String) map.get("totalArea"));
            obj.setTotalFloors((String) map.get("totalFloors"));
            obj.setCurrentPeriod((String) map.get("currentPeriod"));
            obj.setHouseHigh((String) map.get("houseHigh"));
            obj.setApplyFloors((String) map.get("applyFloors"));
            obj.setHouseLayout((String) map.get("houseLayout "));
            obj.setRoomLayout((String) map.get("roomLayout"));
            obj.setOfficeLayout((String) map.get("officeLayout"));
            obj.setWayLayout((String) map.get("wayLayout"));
            obj.setIsExistSymmetry((String) map.get("isExistSymmetry"));
            obj.setSymmetryIds((String) map.get("symmetryIds"));
            obj.setIsMerge((String) map.get("isMerge"));
            obj.setMergeIds((String) map.get("mergeIds"));
            obj.setMergeNewId((Integer) map.get("mergeNewId"));
            obj.setPicResId((Integer) map.get("picResId"));
            obj.setIsAll((Integer) map.get("isAll"));
            obj.setHouseCommonCode((String) map.get("houseCommonCode"));
            obj.setHouseTypeCode((String) map.get("houseTypeCode"));
            obj.setHouseTag((String) map.get("houseTag"));
            obj.setHouseStatus((String) map.get("houseStatus"));
            obj.setDealStatus((String) map.get("dealStatus"));
            obj.setIsReview((String) map.get("isReview"));
            obj.setAreaCode((String) map.get("areaCode"));
            obj.setAtt1((String) map.get("att1"));
            obj.setAreaLongCode((String) map.get("areaLongCode"));
            obj.setHouseKind((String) map.get("houseKind"));
            obj.setAtt4((String) map.get("att4"));
            obj.setAtt5((String) map.get("att5"));
            obj.setAtt6((String) map.get("att6"));
            obj.setDateAtt1((Date) map.get("dateAtt1"));
            obj.setDateAtt2((Date) map.get("dateAtt2"));
            obj.setResetState((Integer) map.get("resetState"));
            obj.setNumAtt2((Integer) map.get("numAtt2"));
            obj.setNumAtt3((Double) map.get("numAtt3"));
            obj.setNumAtt4((Double) map.get("numAtt4"));
            obj.setRemark((String) map.get("remark"));
        }
        return obj;
    }
}
