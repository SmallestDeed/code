package com.sandu.home.model.search;

import com.sandu.home.model.SpaceCommon;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V1.0
 * @Title: SpaceCommonSearch.java
 * @Package com.sandu.home.model
 * @Description:户型房型-通用空间表查询对象
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 15:48:39
 */
public class SpaceCommonSearch extends SpaceCommon implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 系统编码-模糊查询
     **/
    private String sch_SysCode_;
    /**
     * 系统编码-左模糊查询
     **/
    private String sch_SysCode;
    /**
     * 系统编码-右模糊查询
     **/
    private String schSysCode_;
    /**
     * 系统编码-区间查询-开始字符串
     **/
    private String sysCodeStart;
    /**
     * 系统编码-区间查询-结束字符串
     **/
    private String sysCodeEnd;
    /**
     * 创建者-模糊查询
     **/
    private String sch_Creator_;
    /**
     * 创建者-左模糊查询
     **/
    private String sch_Creator;
    /**
     * 创建者-右模糊查询
     **/
    private String schCreator_;
    /**
     * 创建者-区间查询-开始字符串
     **/
    private String creatorStart;
    /**
     * 创建者-区间查询-结束字符串
     **/
    private String creatorEnd;
    /**
     * 创建时间-区间查询-开始时间
     **/
    private Date gmtCreateStart;
    /**
     * 创建时间-区间查询-结束时间
     **/
    private Date gmtCreateEnd;
    /**
     * 修改人-模糊查询
     **/
    private String sch_Modifier_;
    /**
     * 修改人-左模糊查询
     **/
    private String sch_Modifier;
    /**
     * 修改人-右模糊查询
     **/
    private String schModifier_;
    /**
     * 修改人-区间查询-开始字符串
     **/
    private String modifierStart;
    /**
     * 修改人-区间查询-结束字符串
     **/
    private String modifierEnd;
    /**
     * 修改时间-区间查询-开始时间
     **/
    private Date gmtModifiedStart;
    /**
     * 修改时间-区间查询-结束时间
     **/
    private Date gmtModifiedEnd;
    /**
     * 通用房型编码-模糊查询
     **/
    private String sch_SpaceCode_;
    /**
     * 通用房型编码-左模糊查询
     **/
    private String sch_SpaceCode;
    /**
     * 通用房型编码-右模糊查询
     **/
    private String schSpaceCode_;
    /**
     * 通用房型编码-区间查询-开始字符串
     **/
    private String spaceCodeStart;
    /**
     * 通用房型编码-区间查询-结束字符串
     **/
    private String spaceCodeEnd;
    /**
     * 通用房型名称-模糊查询
     **/
    private String sch_SpaceName_;
    /**
     * 通用房型名称-左模糊查询
     **/
    private String sch_SpaceName;
    /**
     * 通用房型名称-右模糊查询
     **/
    private String schSpaceName_;
    /**
     * 通用房型名称-区间查询-开始字符串
     **/
    private String spaceNameStart;
    /**
     * 通用房型名称-区间查询-结束字符串
     **/
    private String spaceNameEnd;
    /**
     * 主体长度-模糊查询
     **/
    private String sch_MainLength_;
    /**
     * 主体长度-左模糊查询
     **/
    private String sch_MainLength;
    /**
     * 主体长度-右模糊查询
     **/
    private String schMainLength_;
    /**
     * 主体长度-区间查询-开始字符串
     **/
    private String mainLengthStart;
    /**
     * 主体长度-区间查询-结束字符串
     **/
    private String mainLengthEnd;
    /**
     * 主体宽度-模糊查询
     **/
    private String sch_MainWidth_;
    /**
     * 主体宽度-左模糊查询
     **/
    private String sch_MainWidth;
    /**
     * 主体宽度-右模糊查询
     **/
    private String schMainWidth_;
    /**
     * 主体宽度-区间查询-开始字符串
     **/
    private String mainWidthStart;
    /**
     * 主体宽度-区间查询-结束字符串
     **/
    private String mainWidthEnd;
    /**
     * 空间面积-模糊查询
     **/
    private String sch_SpaceAreas_;
    /**
     * 空间面积-左模糊查询
     **/
    private String sch_SpaceAreas;
    /**
     * 空间面积-右模糊查询
     **/
    private String schSpaceAreas_;
    /**
     * 空间面积-区间查询-开始字符串
     **/
    private String spaceAreasStart;
    /**
     * 空间面积-区间查询-结束字符串
     **/
    private String spaceAreasEnd;
    /**
     * 空间形状-模糊查询
     **/
    private String sch_SpaceShape_;
    /**
     * 空间形状-左模糊查询
     **/
    private String sch_SpaceShape;
    /**
     * 空间形状-右模糊查询
     **/
    private String schSpaceShape_;
    /**
     * 空间形状-区间查询-开始字符串
     **/
    private String spaceShapeStart;
    /**
     * 空间形状-区间查询-结束字符串
     **/
    private String spaceShapeEnd;
    /**
     * 门的位置类型-模糊查询
     **/
    private String sch_DoorLocationType_;
    /**
     * 门的位置类型-左模糊查询
     **/
    private String sch_DoorLocationType;
    /**
     * 门的位置类型-右模糊查询
     **/
    private String schDoorLocationType_;
    /**
     * 门的位置类型-区间查询-开始字符串
     **/
    private String doorLocationTypeStart;
    /**
     * 门的位置类型-区间查询-结束字符串
     **/
    private String doorLocationTypeEnd;
    /**
     * 空间描述-模糊查询
     **/
    private String sch_SpaceDesc_;
    /**
     * 空间描述-左模糊查询
     **/
    private String sch_SpaceDesc;
    /**
     * 空间描述-右模糊查询
     **/
    private String schSpaceDesc_;
    /**
     * 空间描述-区间查询-开始字符串
     **/
    private String spaceDescStart;
    /**
     * 空间描述-区间查询-结束字符串
     **/
    private String spaceDescEnd;
    /**
     * 位置数组-模糊查询
     **/
    private String sch_LocationArrays_;
    /**
     * 位置数组-左模糊查询
     **/
    private String sch_LocationArrays;
    /**
     * 位置数组-右模糊查询
     **/
    private String schLocationArrays_;
    /**
     * 位置数组-区间查询-开始字符串
     **/
    private String locationArraysStart;
    /**
     * 位置数组-区间查询-结束字符串
     **/
    private String locationArraysEnd;
    /**
     * 过道位置-模糊查询
     **/
    private String sch_WayLocation_;
    /**
     * 过道位置-左模糊查询
     **/
    private String sch_WayLocation;
    /**
     * 过道位置-右模糊查询
     **/
    private String schWayLocation_;
    /**
     * 过道位置-区间查询-开始字符串
     **/
    private String wayLocationStart;
    /**
     * 过道位置-区间查询-结束字符串
     **/
    private String wayLocationEnd;
    /**
     * 占总空间的比例-模糊查询
     **/
    private String sch_SpacePercent_;
    /**
     * 占总空间的比例-左模糊查询
     **/
    private String sch_SpacePercent;
    /**
     * 占总空间的比例-右模糊查询
     **/
    private String schSpacePercent_;
    /**
     * 占总空间的比例-区间查询-开始字符串
     **/
    private String spacePercentStart;
    /**
     * 占总空间的比例-区间查询-结束字符串
     **/
    private String spacePercentEnd;
    /**
     * 字符备用1-模糊查询
     **/
    private String sch_Att1_;
    /**
     * 字符备用1-左模糊查询
     **/
    private String sch_Att1;
    /**
     * 字符备用1-右模糊查询
     **/
    private String schAtt1_;
    /**
     * 字符备用1-区间查询-开始字符串
     **/
    private String att1Start;
    /**
     * 字符备用1-区间查询-结束字符串
     **/
    private String att1End;
    /**
     * 字符备用2-模糊查询
     **/
    private String sch_Att2_;
    /**
     * 字符备用2-左模糊查询
     **/
    private String sch_Att2;
    /**
     * 字符备用2-右模糊查询
     **/
    private String schAtt2_;
    /**
     * 字符备用2-区间查询-开始字符串
     **/
    private String att2Start;
    /**
     * 字符备用2-区间查询-结束字符串
     **/
    private String att2End;
    /**
     * 字符备用3-模糊查询
     **/
    private String sch_View3dPic;
    /**
     * 字符备用3-左模糊查询
     **/
    private String sch_View3dPic_;
    /**
     * 字符备用3-右模糊查询
     **/
    private String schView3dPic_;
    /**
     * 字符备用3-区间查询-开始字符串
     **/
    private String view3dPicStart;
    /**
     * 字符备用3-区间查询-结束字符串
     **/
    private String view3dPicEnd;
    /**
     * 字符备用4-模糊查询
     **/
    private String sch_Att4_;
    /**
     * 字符备用4-左模糊查询
     **/
    private String sch_Att4;
    /**
     * 字符备用4-右模糊查询
     **/
    private String schAtt4_;
    /**
     * 字符备用4-区间查询-开始字符串
     **/
    private String att4Start;
    /**
     * 字符备用4-区间查询-结束字符串
     **/
    private String att4End;
    /**
     * 字符备用5-模糊查询
     **/
    private String sch_Att5_;
    /**
     * 字符备用5-左模糊查询
     **/
    private String sch_Att5;
    /**
     * 字符备用5-右模糊查询
     **/
    private String schAtt5_;
    /**
     * 字符备用5-区间查询-开始字符串
     **/
    private String att5Start;
    /**
     * 字符备用5-区间查询-结束字符串
     **/
    private String att5End;
    /**
     * 字符备用6-模糊查询
     **/
    private String sch_Att6_;
    /**
     * 字符备用6-左模糊查询
     **/
    private String sch_Att6;
    /**
     * 字符备用6-右模糊查询
     **/
    private String schAtt6_;
    /**
     * 字符备用6-区间查询-开始字符串
     **/
    private String att6Start;
    /**
     * 字符备用6-区间查询-结束字符串
     **/
    private String att6End;
    /**
     * 时间备用1-区间查询-开始时间
     **/
    private Date publishModifiedStart;
    /**
     * 时间备用1-区间查询-结束时间
     **/
    private Date publishModifiedEnd;
    /**
     * 时间备用2-区间查询-开始时间
     **/
    private Date dateAtt2Start;
    /**
     * 时间备用2-区间查询-结束时间
     **/
    private Date dateAtt2End;
    /**
     * 备注-模糊查询
     **/
    private String sch_Remark_;
    /**
     * 备注-左模糊查询
     **/
    private String sch_Remark;
    /**
     * 备注-右模糊查询
     **/
    private String schRemark_;
    /**
     * 备注-区间查询-开始字符串
     **/
    private String remarkStart;
    /**
     * 备注-区间查询-结束字符串
     **/
    private String remarkEnd;

    //是否有缩略图
    private int isHasPic;
    //是否有模型
    private int isHasModel;
    //排序规则
    private String orderByNum;
    //输入的长度
    private String lengthValue;
    //输入的宽度
    private String widthValue;
    //计算出来的面积值
    private Double areaCount;
    //总使用排序
    private String totalNum;

    private int isNeedStatistics;

    public int getIsNeedStatistics() {
        return isNeedStatistics;
    }

    public void setIsNeedStatistics(int isNeedStatistics) {
        this.isNeedStatistics = isNeedStatistics;
    }


    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getSch_SysCode_() {
        return sch_SysCode_;
    }

    public void setSch_SysCode_(String sch_SysCode_) {
        this.sch_SysCode_ = sch_SysCode_;
    }

    public String getSch_SysCode() {
        return sch_SysCode;
    }

    public void setSch_SysCode(String sch_SysCode) {
        this.sch_SysCode = sch_SysCode;
    }

    public String getSchSysCode_() {
        return schSysCode_;
    }

    public void setSchSysCode_(String schSysCode_) {
        this.schSysCode_ = schSysCode_;
    }

    public String getSysCodeStart() {
        return sysCodeStart;
    }

    public void setSysCodeStart(String sysCodeStart) {
        this.sysCodeStart = sysCodeStart;
    }

    public String getSysCodeEnd() {
        return sysCodeEnd;
    }

    public void setSysCodeEnd(String sysCodeEnd) {
        this.sysCodeEnd = sysCodeEnd;
    }

    public String getSch_Creator_() {
        return sch_Creator_;
    }

    public void setSch_Creator_(String sch_Creator_) {
        this.sch_Creator_ = sch_Creator_;
    }

    public String getSch_Creator() {
        return sch_Creator;
    }

    public void setSch_Creator(String sch_Creator) {
        this.sch_Creator = sch_Creator;
    }

    public String getSchCreator_() {
        return schCreator_;
    }

    public void setSchCreator_(String schCreator_) {
        this.schCreator_ = schCreator_;
    }

    public String getCreatorStart() {
        return creatorStart;
    }

    public void setCreatorStart(String creatorStart) {
        this.creatorStart = creatorStart;
    }

    public String getCreatorEnd() {
        return creatorEnd;
    }

    public void setCreatorEnd(String creatorEnd) {
        this.creatorEnd = creatorEnd;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public void setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public void setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
    }

    public String getSch_Modifier_() {
        return sch_Modifier_;
    }

    public void setSch_Modifier_(String sch_Modifier_) {
        this.sch_Modifier_ = sch_Modifier_;
    }

    public String getSch_Modifier() {
        return sch_Modifier;
    }

    public void setSch_Modifier(String sch_Modifier) {
        this.sch_Modifier = sch_Modifier;
    }

    public String getSchModifier_() {
        return schModifier_;
    }

    public void setSchModifier_(String schModifier_) {
        this.schModifier_ = schModifier_;
    }

    public String getModifierStart() {
        return modifierStart;
    }

    public void setModifierStart(String modifierStart) {
        this.modifierStart = modifierStart;
    }

    public String getModifierEnd() {
        return modifierEnd;
    }

    public void setModifierEnd(String modifierEnd) {
        this.modifierEnd = modifierEnd;
    }

    public Date getGmtModifiedStart() {
        return gmtModifiedStart;
    }

    public void setGmtModifiedStart(Date gmtModifiedStart) {
        this.gmtModifiedStart = gmtModifiedStart;
    }

    public Date getGmtModifiedEnd() {
        return gmtModifiedEnd;
    }

    public void setGmtModifiedEnd(Date gmtModifiedEnd) {
        this.gmtModifiedEnd = gmtModifiedEnd;
    }

    public String getSch_SpaceCode_() {
        return sch_SpaceCode_;
    }

    public void setSch_SpaceCode_(String sch_SpaceCode_) {
        this.sch_SpaceCode_ = sch_SpaceCode_;
    }

    public String getSch_SpaceCode() {
        return sch_SpaceCode;
    }

    public void setSch_SpaceCode(String sch_SpaceCode) {
        this.sch_SpaceCode = sch_SpaceCode;
    }

    public String getSchSpaceCode_() {
        return schSpaceCode_;
    }

    public void setSchSpaceCode_(String schSpaceCode_) {
        this.schSpaceCode_ = schSpaceCode_;
    }

    public String getSpaceCodeStart() {
        return spaceCodeStart;
    }

    public void setSpaceCodeStart(String spaceCodeStart) {
        this.spaceCodeStart = spaceCodeStart;
    }

    public String getSpaceCodeEnd() {
        return spaceCodeEnd;
    }

    public void setSpaceCodeEnd(String spaceCodeEnd) {
        this.spaceCodeEnd = spaceCodeEnd;
    }

    public String getSch_SpaceName_() {
        return sch_SpaceName_;
    }

    public void setSch_SpaceName_(String sch_SpaceName_) {
        this.sch_SpaceName_ = sch_SpaceName_;
    }

    public String getSch_SpaceName() {
        return sch_SpaceName;
    }

    public void setSch_SpaceName(String sch_SpaceName) {
        this.sch_SpaceName = sch_SpaceName;
    }

    public String getSchSpaceName_() {
        return schSpaceName_;
    }

    public void setSchSpaceName_(String schSpaceName_) {
        this.schSpaceName_ = schSpaceName_;
    }

    public String getSpaceNameStart() {
        return spaceNameStart;
    }

    public void setSpaceNameStart(String spaceNameStart) {
        this.spaceNameStart = spaceNameStart;
    }

    public String getSpaceNameEnd() {
        return spaceNameEnd;
    }

    public void setSpaceNameEnd(String spaceNameEnd) {
        this.spaceNameEnd = spaceNameEnd;
    }

    public String getSch_MainLength_() {
        return sch_MainLength_;
    }

    public void setSch_MainLength_(String sch_MainLength_) {
        this.sch_MainLength_ = sch_MainLength_;
    }

    public String getSch_MainLength() {
        return sch_MainLength;
    }

    public void setSch_MainLength(String sch_MainLength) {
        this.sch_MainLength = sch_MainLength;
    }

    public String getSchMainLength_() {
        return schMainLength_;
    }

    public void setSchMainLength_(String schMainLength_) {
        this.schMainLength_ = schMainLength_;
    }

    public String getMainLengthStart() {
        return mainLengthStart;
    }

    public void setMainLengthStart(String mainLengthStart) {
        this.mainLengthStart = mainLengthStart;
    }

    public String getMainLengthEnd() {
        return mainLengthEnd;
    }

    public void setMainLengthEnd(String mainLengthEnd) {
        this.mainLengthEnd = mainLengthEnd;
    }

    public String getSch_MainWidth_() {
        return sch_MainWidth_;
    }

    public void setSch_MainWidth_(String sch_MainWidth_) {
        this.sch_MainWidth_ = sch_MainWidth_;
    }

    public String getSch_MainWidth() {
        return sch_MainWidth;
    }

    public void setSch_MainWidth(String sch_MainWidth) {
        this.sch_MainWidth = sch_MainWidth;
    }

    public String getSchMainWidth_() {
        return schMainWidth_;
    }

    public void setSchMainWidth_(String schMainWidth_) {
        this.schMainWidth_ = schMainWidth_;
    }

    public String getMainWidthStart() {
        return mainWidthStart;
    }

    public void setMainWidthStart(String mainWidthStart) {
        this.mainWidthStart = mainWidthStart;
    }

    public String getMainWidthEnd() {
        return mainWidthEnd;
    }

    public void setMainWidthEnd(String mainWidthEnd) {
        this.mainWidthEnd = mainWidthEnd;
    }

    public String getSch_SpaceAreas_() {
        return sch_SpaceAreas_;
    }

    public void setSch_SpaceAreas_(String sch_SpaceAreas_) {
        this.sch_SpaceAreas_ = sch_SpaceAreas_;
    }

    public String getSch_SpaceAreas() {
        return sch_SpaceAreas;
    }

    public void setSch_SpaceAreas(String sch_SpaceAreas) {
        this.sch_SpaceAreas = sch_SpaceAreas;
    }

    public String getSchSpaceAreas_() {
        return schSpaceAreas_;
    }

    public void setSchSpaceAreas_(String schSpaceAreas_) {
        this.schSpaceAreas_ = schSpaceAreas_;
    }

    public String getSpaceAreasStart() {
        return spaceAreasStart;
    }

    public void setSpaceAreasStart(String spaceAreasStart) {
        this.spaceAreasStart = spaceAreasStart;
    }

    public String getSpaceAreasEnd() {
        return spaceAreasEnd;
    }

    public void setSpaceAreasEnd(String spaceAreasEnd) {
        this.spaceAreasEnd = spaceAreasEnd;
    }

    public String getSch_SpaceShape_() {
        return sch_SpaceShape_;
    }

    public void setSch_SpaceShape_(String sch_SpaceShape_) {
        this.sch_SpaceShape_ = sch_SpaceShape_;
    }

    public String getSch_SpaceShape() {
        return sch_SpaceShape;
    }

    public void setSch_SpaceShape(String sch_SpaceShape) {
        this.sch_SpaceShape = sch_SpaceShape;
    }

    public String getSchSpaceShape_() {
        return schSpaceShape_;
    }

    public void setSchSpaceShape_(String schSpaceShape_) {
        this.schSpaceShape_ = schSpaceShape_;
    }

    public String getSpaceShapeStart() {
        return spaceShapeStart;
    }

    public void setSpaceShapeStart(String spaceShapeStart) {
        this.spaceShapeStart = spaceShapeStart;
    }

    public String getSpaceShapeEnd() {
        return spaceShapeEnd;
    }

    public void setSpaceShapeEnd(String spaceShapeEnd) {
        this.spaceShapeEnd = spaceShapeEnd;
    }

    public String getSch_DoorLocationType_() {
        return sch_DoorLocationType_;
    }

    public void setSch_DoorLocationType_(String sch_DoorLocationType_) {
        this.sch_DoorLocationType_ = sch_DoorLocationType_;
    }

    public String getSch_DoorLocationType() {
        return sch_DoorLocationType;
    }

    public void setSch_DoorLocationType(String sch_DoorLocationType) {
        this.sch_DoorLocationType = sch_DoorLocationType;
    }

    public String getSchDoorLocationType_() {
        return schDoorLocationType_;
    }

    public void setSchDoorLocationType_(String schDoorLocationType_) {
        this.schDoorLocationType_ = schDoorLocationType_;
    }

    public String getDoorLocationTypeStart() {
        return doorLocationTypeStart;
    }

    public void setDoorLocationTypeStart(String doorLocationTypeStart) {
        this.doorLocationTypeStart = doorLocationTypeStart;
    }

    public String getDoorLocationTypeEnd() {
        return doorLocationTypeEnd;
    }

    public void setDoorLocationTypeEnd(String doorLocationTypeEnd) {
        this.doorLocationTypeEnd = doorLocationTypeEnd;
    }

    public String getSch_SpaceDesc_() {
        return sch_SpaceDesc_;
    }

    public void setSch_SpaceDesc_(String sch_SpaceDesc_) {
        this.sch_SpaceDesc_ = sch_SpaceDesc_;
    }

    public String getSch_SpaceDesc() {
        return sch_SpaceDesc;
    }

    public void setSch_SpaceDesc(String sch_SpaceDesc) {
        this.sch_SpaceDesc = sch_SpaceDesc;
    }

    public String getSchSpaceDesc_() {
        return schSpaceDesc_;
    }

    public void setSchSpaceDesc_(String schSpaceDesc_) {
        this.schSpaceDesc_ = schSpaceDesc_;
    }

    public String getSpaceDescStart() {
        return spaceDescStart;
    }

    public void setSpaceDescStart(String spaceDescStart) {
        this.spaceDescStart = spaceDescStart;
    }

    public String getSpaceDescEnd() {
        return spaceDescEnd;
    }

    public void setSpaceDescEnd(String spaceDescEnd) {
        this.spaceDescEnd = spaceDescEnd;
    }

    public String getSch_LocationArrays_() {
        return sch_LocationArrays_;
    }

    public void setSch_LocationArrays_(String sch_LocationArrays_) {
        this.sch_LocationArrays_ = sch_LocationArrays_;
    }

    public String getSch_LocationArrays() {
        return sch_LocationArrays;
    }

    public void setSch_LocationArrays(String sch_LocationArrays) {
        this.sch_LocationArrays = sch_LocationArrays;
    }

    public String getSchLocationArrays_() {
        return schLocationArrays_;
    }

    public void setSchLocationArrays_(String schLocationArrays_) {
        this.schLocationArrays_ = schLocationArrays_;
    }

    public String getLocationArraysStart() {
        return locationArraysStart;
    }

    public void setLocationArraysStart(String locationArraysStart) {
        this.locationArraysStart = locationArraysStart;
    }

    public String getLocationArraysEnd() {
        return locationArraysEnd;
    }

    public void setLocationArraysEnd(String locationArraysEnd) {
        this.locationArraysEnd = locationArraysEnd;
    }

    public String getSch_WayLocation_() {
        return sch_WayLocation_;
    }

    public void setSch_WayLocation_(String sch_WayLocation_) {
        this.sch_WayLocation_ = sch_WayLocation_;
    }

    public String getSch_WayLocation() {
        return sch_WayLocation;
    }

    public void setSch_WayLocation(String sch_WayLocation) {
        this.sch_WayLocation = sch_WayLocation;
    }

    public String getSchWayLocation_() {
        return schWayLocation_;
    }

    public void setSchWayLocation_(String schWayLocation_) {
        this.schWayLocation_ = schWayLocation_;
    }

    public String getWayLocationStart() {
        return wayLocationStart;
    }

    public void setWayLocationStart(String wayLocationStart) {
        this.wayLocationStart = wayLocationStart;
    }

    public String getWayLocationEnd() {
        return wayLocationEnd;
    }

    public void setWayLocationEnd(String wayLocationEnd) {
        this.wayLocationEnd = wayLocationEnd;
    }

    public String getSch_SpacePercent_() {
        return sch_SpacePercent_;
    }

    public void setSch_SpacePercent_(String sch_SpacePercent_) {
        this.sch_SpacePercent_ = sch_SpacePercent_;
    }

    public String getSch_SpacePercent() {
        return sch_SpacePercent;
    }

    public void setSch_SpacePercent(String sch_SpacePercent) {
        this.sch_SpacePercent = sch_SpacePercent;
    }

    public String getSchSpacePercent_() {
        return schSpacePercent_;
    }

    public void setSchSpacePercent_(String schSpacePercent_) {
        this.schSpacePercent_ = schSpacePercent_;
    }

    public String getSpacePercentStart() {
        return spacePercentStart;
    }

    public void setSpacePercentStart(String spacePercentStart) {
        this.spacePercentStart = spacePercentStart;
    }

    public String getSpacePercentEnd() {
        return spacePercentEnd;
    }

    public void setSpacePercentEnd(String spacePercentEnd) {
        this.spacePercentEnd = spacePercentEnd;
    }

    public String getSch_Att1_() {
        return sch_Att1_;
    }

    public void setSch_Att1_(String sch_Att1_) {
        this.sch_Att1_ = sch_Att1_;
    }

    public String getSch_Att1() {
        return sch_Att1;
    }

    public void setSch_Att1(String sch_Att1) {
        this.sch_Att1 = sch_Att1;
    }

    public String getSchAtt1_() {
        return schAtt1_;
    }

    public void setSchAtt1_(String schAtt1_) {
        this.schAtt1_ = schAtt1_;
    }

    public String getAtt1Start() {
        return att1Start;
    }

    public void setAtt1Start(String att1Start) {
        this.att1Start = att1Start;
    }

    public String getAtt1End() {
        return att1End;
    }

    public void setAtt1End(String att1End) {
        this.att1End = att1End;
    }

    public String getSch_Att2_() {
        return sch_Att2_;
    }

    public void setSch_Att2_(String sch_Att2_) {
        this.sch_Att2_ = sch_Att2_;
    }

    public String getSch_Att2() {
        return sch_Att2;
    }

    public void setSch_Att2(String sch_Att2) {
        this.sch_Att2 = sch_Att2;
    }

    public String getSchAtt2_() {
        return schAtt2_;
    }

    public void setSchAtt2_(String schAtt2_) {
        this.schAtt2_ = schAtt2_;
    }

    public String getAtt2Start() {
        return att2Start;
    }

    public void setAtt2Start(String att2Start) {
        this.att2Start = att2Start;
    }

    public String getAtt2End() {
        return att2End;
    }

    public void setAtt2End(String att2End) {
        this.att2End = att2End;
    }

    public String getSch_View3dPic() {
        return sch_View3dPic;
    }

    public void setSch_View3dPic(String sch_View3dPic) {
        this.sch_View3dPic = sch_View3dPic;
    }

    public String getSch_View3dPic_() {
        return sch_View3dPic_;
    }

    public void setSch_View3dPic_(String sch_View3dPic_) {
        this.sch_View3dPic_ = sch_View3dPic_;
    }

    public String getSchView3dPic_() {
        return schView3dPic_;
    }

    public void setSchView3dPic_(String schView3dPic_) {
        this.schView3dPic_ = schView3dPic_;
    }

    public String getView3dPicStart() {
        return view3dPicStart;
    }

    public void setView3dPicStart(String view3dPicStart) {
        this.view3dPicStart = view3dPicStart;
    }

    public String getView3dPicEnd() {
        return view3dPicEnd;
    }

    public void setView3dPicEnd(String view3dPicEnd) {
        this.view3dPicEnd = view3dPicEnd;
    }

    public String getSch_Att4_() {
        return sch_Att4_;
    }

    public void setSch_Att4_(String sch_Att4_) {
        this.sch_Att4_ = sch_Att4_;
    }

    public String getSch_Att4() {
        return sch_Att4;
    }

    public void setSch_Att4(String sch_Att4) {
        this.sch_Att4 = sch_Att4;
    }

    public String getSchAtt4_() {
        return schAtt4_;
    }

    public void setSchAtt4_(String schAtt4_) {
        this.schAtt4_ = schAtt4_;
    }

    public String getAtt4Start() {
        return att4Start;
    }

    public void setAtt4Start(String att4Start) {
        this.att4Start = att4Start;
    }

    public String getAtt4End() {
        return att4End;
    }

    public void setAtt4End(String att4End) {
        this.att4End = att4End;
    }

    public String getSch_Att5_() {
        return sch_Att5_;
    }

    public void setSch_Att5_(String sch_Att5_) {
        this.sch_Att5_ = sch_Att5_;
    }

    public String getSch_Att5() {
        return sch_Att5;
    }

    public void setSch_Att5(String sch_Att5) {
        this.sch_Att5 = sch_Att5;
    }

    public String getSchAtt5_() {
        return schAtt5_;
    }

    public void setSchAtt5_(String schAtt5_) {
        this.schAtt5_ = schAtt5_;
    }

    public String getAtt5Start() {
        return att5Start;
    }

    public void setAtt5Start(String att5Start) {
        this.att5Start = att5Start;
    }

    public String getAtt5End() {
        return att5End;
    }

    public void setAtt5End(String att5End) {
        this.att5End = att5End;
    }

    public String getSch_Att6_() {
        return sch_Att6_;
    }

    public void setSch_Att6_(String sch_Att6_) {
        this.sch_Att6_ = sch_Att6_;
    }

    public String getSch_Att6() {
        return sch_Att6;
    }

    public void setSch_Att6(String sch_Att6) {
        this.sch_Att6 = sch_Att6;
    }

    public String getSchAtt6_() {
        return schAtt6_;
    }

    public void setSchAtt6_(String schAtt6_) {
        this.schAtt6_ = schAtt6_;
    }

    public String getAtt6Start() {
        return att6Start;
    }

    public void setAtt6Start(String att6Start) {
        this.att6Start = att6Start;
    }

    public String getAtt6End() {
        return att6End;
    }

    public void setAtt6End(String att6End) {
        this.att6End = att6End;
    }

    public Date getPublishModifiedStart() {
        return publishModifiedStart;
    }

    public void setPublishModifiedStart(Date publishModifiedStart) {
        this.publishModifiedStart = publishModifiedStart;
    }

    public Date getPublishModifiedEnd() {
        return publishModifiedEnd;
    }

    public void setPublishModifiedEnd(Date publishModifiedEnd) {
        this.publishModifiedEnd = publishModifiedEnd;
    }

    public Date getDateAtt2Start() {
        return dateAtt2Start;
    }

    public void setDateAtt2Start(Date dateAtt2Start) {
        this.dateAtt2Start = dateAtt2Start;
    }

    public Date getDateAtt2End() {
        return dateAtt2End;
    }

    public void setDateAtt2End(Date dateAtt2End) {
        this.dateAtt2End = dateAtt2End;
    }

    public String getSch_Remark_() {
        return sch_Remark_;
    }

    public void setSch_Remark_(String sch_Remark_) {
        this.sch_Remark_ = sch_Remark_;
    }

    public String getSch_Remark() {
        return sch_Remark;
    }

    public void setSch_Remark(String sch_Remark) {
        this.sch_Remark = sch_Remark;
    }

    public String getSchRemark_() {
        return schRemark_;
    }

    public void setSchRemark_(String schRemark_) {
        this.schRemark_ = schRemark_;
    }

    public String getRemarkStart() {
        return remarkStart;
    }

    public void setRemarkStart(String remarkStart) {
        this.remarkStart = remarkStart;
    }

    public String getRemarkEnd() {
        return remarkEnd;
    }

    public void setRemarkEnd(String remarkEnd) {
        this.remarkEnd = remarkEnd;
    }

    public int getIsHasPic() {
        return isHasPic;
    }

    public void setIsHasPic(int isHasPic) {
        this.isHasPic = isHasPic;
    }

    public int getIsHasModel() {
        return isHasModel;
    }

    public void setIsHasModel(int isHasModel) {
        this.isHasModel = isHasModel;
    }

    public String getOrderByNum() {
        return orderByNum;
    }

    public void setOrderByNum(String orderByNum) {
        this.orderByNum = orderByNum;
    }

    public Double getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(Double areaCount) {
        this.areaCount = areaCount;
    }

    public String getLengthValue() {
        return lengthValue;
    }

    public void setLengthValue(String lengthValue) {
        this.lengthValue = lengthValue;
    }

    public String getWidthValue() {
        return widthValue;
    }

    public void setWidthValue(String widthValue) {
        this.widthValue = widthValue;
    }


}
