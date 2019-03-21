package com.sandu.system.model.search;

import com.sandu.designplan.model.ResRenderPic;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V1.0
 * @Title: ResRenderPicSearch.java
 * @Package com.sandu.system.model
 * @Description:渲染图片资源库-渲染图片资源库查询对象
 * @createAuthor pandajun
 * @CreateDate 2017-03-22 14:39:08
 */
public class ResRenderPicSearch extends ResRenderPic implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 图片编码-模糊查询
     **/
    private String sch_PicCode_;
    /**
     * 图片编码-左模糊查询
     **/
    private String sch_PicCode;
    /**
     * 图片编码-右模糊查询
     **/
    private String schPicCode_;
    /**
     * 图片编码-区间查询-开始字符串
     **/
    private String picCodeStart;
    /**
     * 图片编码-区间查询-结束字符串
     **/
    private String picCodeEnd;
    /**
     * 图片名称-模糊查询
     **/
    private String sch_PicName_;
    /**
     * 图片名称-左模糊查询
     **/
    private String sch_PicName;
    /**
     * 图片名称-右模糊查询
     **/
    private String schPicName_;
    /**
     * 图片名称-区间查询-开始字符串
     **/
    private String picNameStart;
    /**
     * 图片名称-区间查询-结束字符串
     **/
    private String picNameEnd;
    /**
     * 图片文件名称-模糊查询
     **/
    private String sch_PicFileName_;
    /**
     * 图片文件名称-左模糊查询
     **/
    private String sch_PicFileName;
    /**
     * 图片文件名称-右模糊查询
     **/
    private String schPicFileName_;
    /**
     * 图片文件名称-区间查询-开始字符串
     **/
    private String picFileNameStart;
    /**
     * 图片文件名称-区间查询-结束字符串
     **/
    private String picFileNameEnd;
    /**
     * 图片类型-模糊查询
     **/
    private String sch_PicType_;
    /**
     * 图片类型-左模糊查询
     **/
    private String sch_PicType;
    /**
     * 图片类型-右模糊查询
     **/
    private String schPicType_;
    /**
     * 图片类型-区间查询-开始字符串
     **/
    private String picTypeStart;
    /**
     * 图片类型-区间查询-结束字符串
     **/
    private String picTypeEnd;
    /**
     * 图片后缀-模糊查询
     **/
    private String sch_PicSuffix_;
    /**
     * 图片后缀-左模糊查询
     **/
    private String sch_PicSuffix;
    /**
     * 图片后缀-右模糊查询
     **/
    private String schPicSuffix_;
    /**
     * 图片后缀-区间查询-开始字符串
     **/
    private String picSuffixStart;
    /**
     * 图片后缀-区间查询-结束字符串
     **/
    private String picSuffixEnd;
    /**
     * 图片格式-模糊查询
     **/
    private String sch_PicFormat_;
    /**
     * 图片格式-左模糊查询
     **/
    private String sch_PicFormat;
    /**
     * 图片格式-右模糊查询
     **/
    private String schPicFormat_;
    /**
     * 图片格式-区间查询-开始字符串
     **/
    private String picFormatStart;
    /**
     * 图片格式-区间查询-结束字符串
     **/
    private String picFormatEnd;
    /**
     * 图片路径-模糊查询
     **/
    private String sch_PicPath_;
    /**
     * 图片路径-左模糊查询
     **/
    private String sch_PicPath;
    /**
     * 图片路径-右模糊查询
     **/
    private String schPicPath_;
    /**
     * 图片路径-区间查询-开始字符串
     **/
    private String picPathStart;
    /**
     * 图片路径-区间查询-结束字符串
     **/
    private String picPathEnd;
    /**
     * 图片描述-模糊查询
     **/
    private String sch_PicDesc_;
    /**
     * 图片描述-左模糊查询
     **/
    private String sch_PicDesc;
    /**
     * 图片描述-右模糊查询
     **/
    private String schPicDesc_;
    /**
     * 图片描述-区间查询-开始字符串
     **/
    private String picDescStart;
    /**
     * 图片描述-区间查询-结束字符串
     **/
    private String picDescEnd;
    /**
     * key标识-模糊查询
     **/
    private String sch_FileKey_;
    /**
     * key标识-左模糊查询
     **/
    private String sch_FileKey;
    /**
     * key标识-右模糊查询
     **/
    private String schFileKey_;
    /**
     * key标识-区间查询-开始字符串
     **/
    private String fileKeyStart;
    /**
     * key标识-区间查询-结束字符串
     **/
    private String fileKeyEnd;
    /**
     * key标识（多个）-模糊查询
     **/
    private String sch_FileKeys_;
    /**
     * key标识（多个）-左模糊查询
     **/
    private String sch_FileKeys;
    /**
     * key标识（多个）-右模糊查询
     **/
    private String schFileKeys_;
    /**
     * key标识（多个）-区间查询-开始字符串
     **/
    private String fileKeysStart;
    /**
     * key标识（多个）-区间查询-结束字符串
     **/
    private String fileKeysEnd;
    /**
     * 图片对应的缩略图id信息-模糊查询
     **/
    private String sch_SmallPicInfo_;
    /**
     * 图片对应的缩略图id信息-左模糊查询
     **/
    private String sch_SmallPicInfo;
    /**
     * 图片对应的缩略图id信息-右模糊查询
     **/
    private String schSmallPicInfo_;
    /**
     * 图片对应的缩略图id信息-区间查询-开始字符串
     **/
    private String smallPicInfoStart;
    /**
     * 图片对应的缩略图id信息-区间查询-结束字符串
     **/
    private String smallPicInfoEnd;
    /**
     * 全景图地址-模糊查询
     **/
    private String sch_PanoPath_;
    /**
     * 全景图地址-左模糊查询
     **/
    private String sch_PanoPath;
    /**
     * 全景图地址-右模糊查询
     **/
    private String schPanoPath_;
    /**
     * 全景图地址-区间查询-开始字符串
     **/
    private String panoPathStart;
    /**
     * 全景图地址-区间查询-结束字符串
     **/
    private String panoPathEnd;
    /**
     * 渲染任务创建时间-区间查询-开始时间
     **/
    private Date taskCreateTimeStart;
    /**
     * 渲染任务创建时间-区间查询-结束时间
     **/
    private Date taskCreateTimeEnd;
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

    public String getSch_PicCode_() {
        return sch_PicCode_;
    }

    public void setSch_PicCode_(String sch_PicCode_) {
        this.sch_PicCode_ = sch_PicCode_;
    }

    public String getSch_PicCode() {
        return sch_PicCode;
    }

    public void setSch_PicCode(String sch_PicCode) {
        this.sch_PicCode = sch_PicCode;
    }

    public String getSchPicCode_() {
        return schPicCode_;
    }

    public void setSchPicCode_(String schPicCode_) {
        this.schPicCode_ = schPicCode_;
    }

    public String getPicCodeStart() {
        return picCodeStart;
    }

    public void setPicCodeStart(String picCodeStart) {
        this.picCodeStart = picCodeStart;
    }

    public String getPicCodeEnd() {
        return picCodeEnd;
    }

    public void setPicCodeEnd(String picCodeEnd) {
        this.picCodeEnd = picCodeEnd;
    }

    public String getSch_PicName_() {
        return sch_PicName_;
    }

    public void setSch_PicName_(String sch_PicName_) {
        this.sch_PicName_ = sch_PicName_;
    }

    public String getSch_PicName() {
        return sch_PicName;
    }

    public void setSch_PicName(String sch_PicName) {
        this.sch_PicName = sch_PicName;
    }

    public String getSchPicName_() {
        return schPicName_;
    }

    public void setSchPicName_(String schPicName_) {
        this.schPicName_ = schPicName_;
    }

    public String getPicNameStart() {
        return picNameStart;
    }

    public void setPicNameStart(String picNameStart) {
        this.picNameStart = picNameStart;
    }

    public String getPicNameEnd() {
        return picNameEnd;
    }

    public void setPicNameEnd(String picNameEnd) {
        this.picNameEnd = picNameEnd;
    }

    public String getSch_PicFileName_() {
        return sch_PicFileName_;
    }

    public void setSch_PicFileName_(String sch_PicFileName_) {
        this.sch_PicFileName_ = sch_PicFileName_;
    }

    public String getSch_PicFileName() {
        return sch_PicFileName;
    }

    public void setSch_PicFileName(String sch_PicFileName) {
        this.sch_PicFileName = sch_PicFileName;
    }

    public String getSchPicFileName_() {
        return schPicFileName_;
    }

    public void setSchPicFileName_(String schPicFileName_) {
        this.schPicFileName_ = schPicFileName_;
    }

    public String getPicFileNameStart() {
        return picFileNameStart;
    }

    public void setPicFileNameStart(String picFileNameStart) {
        this.picFileNameStart = picFileNameStart;
    }

    public String getPicFileNameEnd() {
        return picFileNameEnd;
    }

    public void setPicFileNameEnd(String picFileNameEnd) {
        this.picFileNameEnd = picFileNameEnd;
    }

    public String getSch_PicType_() {
        return sch_PicType_;
    }

    public void setSch_PicType_(String sch_PicType_) {
        this.sch_PicType_ = sch_PicType_;
    }

    public String getSch_PicType() {
        return sch_PicType;
    }

    public void setSch_PicType(String sch_PicType) {
        this.sch_PicType = sch_PicType;
    }

    public String getSchPicType_() {
        return schPicType_;
    }

    public void setSchPicType_(String schPicType_) {
        this.schPicType_ = schPicType_;
    }

    public String getPicTypeStart() {
        return picTypeStart;
    }

    public void setPicTypeStart(String picTypeStart) {
        this.picTypeStart = picTypeStart;
    }

    public String getPicTypeEnd() {
        return picTypeEnd;
    }

    public void setPicTypeEnd(String picTypeEnd) {
        this.picTypeEnd = picTypeEnd;
    }

    public String getSch_PicSuffix_() {
        return sch_PicSuffix_;
    }

    public void setSch_PicSuffix_(String sch_PicSuffix_) {
        this.sch_PicSuffix_ = sch_PicSuffix_;
    }

    public String getSch_PicSuffix() {
        return sch_PicSuffix;
    }

    public void setSch_PicSuffix(String sch_PicSuffix) {
        this.sch_PicSuffix = sch_PicSuffix;
    }

    public String getSchPicSuffix_() {
        return schPicSuffix_;
    }

    public void setSchPicSuffix_(String schPicSuffix_) {
        this.schPicSuffix_ = schPicSuffix_;
    }

    public String getPicSuffixStart() {
        return picSuffixStart;
    }

    public void setPicSuffixStart(String picSuffixStart) {
        this.picSuffixStart = picSuffixStart;
    }

    public String getPicSuffixEnd() {
        return picSuffixEnd;
    }

    public void setPicSuffixEnd(String picSuffixEnd) {
        this.picSuffixEnd = picSuffixEnd;
    }

    public String getSch_PicFormat_() {
        return sch_PicFormat_;
    }

    public void setSch_PicFormat_(String sch_PicFormat_) {
        this.sch_PicFormat_ = sch_PicFormat_;
    }

    public String getSch_PicFormat() {
        return sch_PicFormat;
    }

    public void setSch_PicFormat(String sch_PicFormat) {
        this.sch_PicFormat = sch_PicFormat;
    }

    public String getSchPicFormat_() {
        return schPicFormat_;
    }

    public void setSchPicFormat_(String schPicFormat_) {
        this.schPicFormat_ = schPicFormat_;
    }

    public String getPicFormatStart() {
        return picFormatStart;
    }

    public void setPicFormatStart(String picFormatStart) {
        this.picFormatStart = picFormatStart;
    }

    public String getPicFormatEnd() {
        return picFormatEnd;
    }

    public void setPicFormatEnd(String picFormatEnd) {
        this.picFormatEnd = picFormatEnd;
    }

    public String getSch_PicPath_() {
        return sch_PicPath_;
    }

    public void setSch_PicPath_(String sch_PicPath_) {
        this.sch_PicPath_ = sch_PicPath_;
    }

    public String getSch_PicPath() {
        return sch_PicPath;
    }

    public void setSch_PicPath(String sch_PicPath) {
        this.sch_PicPath = sch_PicPath;
    }

    public String getSchPicPath_() {
        return schPicPath_;
    }

    public void setSchPicPath_(String schPicPath_) {
        this.schPicPath_ = schPicPath_;
    }

    public String getPicPathStart() {
        return picPathStart;
    }

    public void setPicPathStart(String picPathStart) {
        this.picPathStart = picPathStart;
    }

    public String getPicPathEnd() {
        return picPathEnd;
    }

    public void setPicPathEnd(String picPathEnd) {
        this.picPathEnd = picPathEnd;
    }

    public String getSch_PicDesc_() {
        return sch_PicDesc_;
    }

    public void setSch_PicDesc_(String sch_PicDesc_) {
        this.sch_PicDesc_ = sch_PicDesc_;
    }

    public String getSch_PicDesc() {
        return sch_PicDesc;
    }

    public void setSch_PicDesc(String sch_PicDesc) {
        this.sch_PicDesc = sch_PicDesc;
    }

    public String getSchPicDesc_() {
        return schPicDesc_;
    }

    public void setSchPicDesc_(String schPicDesc_) {
        this.schPicDesc_ = schPicDesc_;
    }

    public String getPicDescStart() {
        return picDescStart;
    }

    public void setPicDescStart(String picDescStart) {
        this.picDescStart = picDescStart;
    }

    public String getPicDescEnd() {
        return picDescEnd;
    }

    public void setPicDescEnd(String picDescEnd) {
        this.picDescEnd = picDescEnd;
    }

    public String getSch_FileKey_() {
        return sch_FileKey_;
    }

    public void setSch_FileKey_(String sch_FileKey_) {
        this.sch_FileKey_ = sch_FileKey_;
    }

    public String getSch_FileKey() {
        return sch_FileKey;
    }

    public void setSch_FileKey(String sch_FileKey) {
        this.sch_FileKey = sch_FileKey;
    }

    public String getSchFileKey_() {
        return schFileKey_;
    }

    public void setSchFileKey_(String schFileKey_) {
        this.schFileKey_ = schFileKey_;
    }

    public String getFileKeyStart() {
        return fileKeyStart;
    }

    public void setFileKeyStart(String fileKeyStart) {
        this.fileKeyStart = fileKeyStart;
    }

    public String getFileKeyEnd() {
        return fileKeyEnd;
    }

    public void setFileKeyEnd(String fileKeyEnd) {
        this.fileKeyEnd = fileKeyEnd;
    }

    public String getSch_FileKeys_() {
        return sch_FileKeys_;
    }

    public void setSch_FileKeys_(String sch_FileKeys_) {
        this.sch_FileKeys_ = sch_FileKeys_;
    }

    public String getSch_FileKeys() {
        return sch_FileKeys;
    }

    public void setSch_FileKeys(String sch_FileKeys) {
        this.sch_FileKeys = sch_FileKeys;
    }

    public String getSchFileKeys_() {
        return schFileKeys_;
    }

    public void setSchFileKeys_(String schFileKeys_) {
        this.schFileKeys_ = schFileKeys_;
    }

    public String getFileKeysStart() {
        return fileKeysStart;
    }

    public void setFileKeysStart(String fileKeysStart) {
        this.fileKeysStart = fileKeysStart;
    }

    public String getFileKeysEnd() {
        return fileKeysEnd;
    }

    public void setFileKeysEnd(String fileKeysEnd) {
        this.fileKeysEnd = fileKeysEnd;
    }

    public String getSch_SmallPicInfo_() {
        return sch_SmallPicInfo_;
    }

    public void setSch_SmallPicInfo_(String sch_SmallPicInfo_) {
        this.sch_SmallPicInfo_ = sch_SmallPicInfo_;
    }

    public String getSch_SmallPicInfo() {
        return sch_SmallPicInfo;
    }

    public void setSch_SmallPicInfo(String sch_SmallPicInfo) {
        this.sch_SmallPicInfo = sch_SmallPicInfo;
    }

    public String getSchSmallPicInfo_() {
        return schSmallPicInfo_;
    }

    public void setSchSmallPicInfo_(String schSmallPicInfo_) {
        this.schSmallPicInfo_ = schSmallPicInfo_;
    }

    public String getSmallPicInfoStart() {
        return smallPicInfoStart;
    }

    public void setSmallPicInfoStart(String smallPicInfoStart) {
        this.smallPicInfoStart = smallPicInfoStart;
    }

    public String getSmallPicInfoEnd() {
        return smallPicInfoEnd;
    }

    public void setSmallPicInfoEnd(String smallPicInfoEnd) {
        this.smallPicInfoEnd = smallPicInfoEnd;
    }

    public String getSch_PanoPath_() {
        return sch_PanoPath_;
    }

    public void setSch_PanoPath_(String sch_PanoPath_) {
        this.sch_PanoPath_ = sch_PanoPath_;
    }

    public String getSch_PanoPath() {
        return sch_PanoPath;
    }

    public void setSch_PanoPath(String sch_PanoPath) {
        this.sch_PanoPath = sch_PanoPath;
    }

    public String getSchPanoPath_() {
        return schPanoPath_;
    }

    public void setSchPanoPath_(String schPanoPath_) {
        this.schPanoPath_ = schPanoPath_;
    }

    public String getPanoPathStart() {
        return panoPathStart;
    }

    public void setPanoPathStart(String panoPathStart) {
        this.panoPathStart = panoPathStart;
    }

    public String getPanoPathEnd() {
        return panoPathEnd;
    }

    public void setPanoPathEnd(String panoPathEnd) {
        this.panoPathEnd = panoPathEnd;
    }

    public Date getTaskCreateTimeStart() {
        return taskCreateTimeStart;
    }

    public void setTaskCreateTimeStart(Date taskCreateTimeStart) {
        this.taskCreateTimeStart = taskCreateTimeStart;
    }

    public Date getTaskCreateTimeEnd() {
        return taskCreateTimeEnd;
    }

    public void setTaskCreateTimeEnd(Date taskCreateTimeEnd) {
        this.taskCreateTimeEnd = taskCreateTimeEnd;
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

}
