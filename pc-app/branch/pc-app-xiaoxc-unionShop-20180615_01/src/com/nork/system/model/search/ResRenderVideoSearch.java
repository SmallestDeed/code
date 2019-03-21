package com.nork.system.model.search;

import java.io.Serializable;
import java.util.Date;
import com.nork.system.model.ResRenderVideo;

/**   
 * @Title: ResRenderVideoSearch.java 
 * @Package com.nork.system.model
 * @Description:渲染视频资源库-渲染视频资源表查询对象
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 22:42:48
 * @version V1.0   
 */
public class ResRenderVideoSearch extends  ResRenderVideo implements Serializable{
private static final long serialVersionUID = 1L;
	/**  视频编码-模糊查询  **/
	private String sch_VideoCode_;
	/**  视频编码-左模糊查询  **/
	private String sch_VideoCode;
	/**  视频编码-右模糊查询  **/
	private String schVideoCode_;
	/**  视频编码-区间查询-开始字符串  **/
	private String videoCodeStart;
	/**  视频编码-区间查询-结束字符串  **/
	private String videoCodeEnd;
	/**  视频名称-模糊查询  **/
	private String sch_VideoName_;
	/**  视频名称-左模糊查询  **/
	private String sch_VideoName;
	/**  视频名称-右模糊查询  **/
	private String schVideoName_;
	/**  视频名称-区间查询-开始字符串  **/
	private String videoNameStart;
	/**  视频名称-区间查询-结束字符串  **/
	private String videoNameEnd;
	/**  视频文件名称-模糊查询  **/
	private String sch_VideoFileName_;
	/**  视频文件名称-左模糊查询  **/
	private String sch_VideoFileName;
	/**  视频文件名称-右模糊查询  **/
	private String schVideoFileName_;
	/**  视频文件名称-区间查询-开始字符串  **/
	private String videoFileNameStart;
	/**  视频文件名称-区间查询-结束字符串  **/
	private String videoFileNameEnd;
	/**  视频类型-模糊查询  **/
	private String sch_VideoType_;
	/**  视频类型-左模糊查询  **/
	private String sch_VideoType;
	/**  视频类型-右模糊查询  **/
	private String schVideoType_;
	/**  视频类型-区间查询-开始字符串  **/
	private String videoTypeStart;
	/**  视频类型-区间查询-结束字符串  **/
	private String videoTypeEnd;
	/**  视频后缀-模糊查询  **/
	private String sch_VideoSuffix_;
	/**  视频后缀-左模糊查询  **/
	private String sch_VideoSuffix;
	/**  视频后缀-右模糊查询  **/
	private String schVideoSuffix_;
	/**  视频后缀-区间查询-开始字符串  **/
	private String videoSuffixStart;
	/**  视频后缀-区间查询-结束字符串  **/
	private String videoSuffixEnd;
	/**  视频格式-模糊查询  **/
	private String sch_VideoFormat_;
	/**  视频格式-左模糊查询  **/
	private String sch_VideoFormat;
	/**  视频格式-右模糊查询  **/
	private String schVideoFormat_;
	/**  视频格式-区间查询-开始字符串  **/
	private String videoFormatStart;
	/**  视频格式-区间查询-结束字符串  **/
	private String videoFormatEnd;
	/**  视频路径-模糊查询  **/
	private String sch_VideoPath_;
	/**  视频路径-左模糊查询  **/
	private String sch_VideoPath;
	/**  视频路径-右模糊查询  **/
	private String schVideoPath_;
	/**  视频路径-区间查询-开始字符串  **/
	private String videoPathStart;
	/**  视频路径-区间查询-结束字符串  **/
	private String videoPathEnd;
	/**  视频描述-模糊查询  **/
	private String sch_VideoDesc_;
	/**  视频描述-左模糊查询  **/
	private String sch_VideoDesc;
	/**  视频描述-右模糊查询  **/
	private String schVideoDesc_;
	/**  视频描述-区间查询-开始字符串  **/
	private String videoDescStart;
	/**  视频描述-区间查询-结束字符串  **/
	private String videoDescEnd;
	/**  key标识-模糊查询  **/
	private String sch_FileKey_;
	/**  key标识-左模糊查询  **/
	private String sch_FileKey;
	/**  key标识-右模糊查询  **/
	private String schFileKey_;
	/**  key标识-区间查询-开始字符串  **/
	private String fileKeyStart;
	/**  key标识-区间查询-结束字符串  **/
	private String fileKeyEnd;
	/**  key标识（多个）-模糊查询  **/
	private String sch_FileKeys_;
	/**  key标识（多个）-左模糊查询  **/
	private String sch_FileKeys;
	/**  key标识（多个）-右模糊查询  **/
	private String schFileKeys_;
	/**  key标识（多个）-区间查询-开始字符串  **/
	private String fileKeysStart;
	/**  key标识（多个）-区间查询-结束字符串  **/
	private String fileKeysEnd;
	/**  视频对应的封面图id信息-模糊查询  **/
	private String sch_SmallVideoInfo_;
	/**  视频对应的封面图id信息-左模糊查询  **/
	private String sch_SmallVideoInfo;
	/**  视频对应的封面图id信息-右模糊查询  **/
	private String schSmallVideoInfo_;
	/**  视频对应的封面图id信息-区间查询-开始字符串  **/
	private String smallVideoInfoStart;
	/**  视频对应的封面图id信息-区间查询-结束字符串  **/
	private String smallVideoInfoEnd;
	/**  渲染任务创建时间-区间查询-开始时间  **/
	private Date taskCreateTimeStart;
	/**  渲染任务创建时间-区间查询-结束时间  **/
	private Date taskCreateTimeEnd;
	/**  系统编码-模糊查询  **/
	private String sch_SysCode_;
	/**  系统编码-左模糊查询  **/
	private String sch_SysCode;
	/**  系统编码-右模糊查询  **/
	private String schSysCode_;
	/**  系统编码-区间查询-开始字符串  **/
	private String sysCodeStart;
	/**  系统编码-区间查询-结束字符串  **/
	private String sysCodeEnd;
	/**  创建者-模糊查询  **/
	private String sch_Creator_;
	/**  创建者-左模糊查询  **/
	private String sch_Creator;
	/**  创建者-右模糊查询  **/
	private String schCreator_;
	/**  创建者-区间查询-开始字符串  **/
	private String creatorStart;
	/**  创建者-区间查询-结束字符串  **/
	private String creatorEnd;
	/**  创建时间-区间查询-开始时间  **/
	private Date gmtCreateStart;
	/**  创建时间-区间查询-结束时间  **/
	private Date gmtCreateEnd;
	/**  修改人-模糊查询  **/
	private String sch_Modifier_;
	/**  修改人-左模糊查询  **/
	private String sch_Modifier;
	/**  修改人-右模糊查询  **/
	private String schModifier_;
	/**  修改人-区间查询-开始字符串  **/
	private String modifierStart;
	/**  修改人-区间查询-结束字符串  **/
	private String modifierEnd;
	/**  修改时间-区间查询-开始时间  **/
	private Date gmtModifiedStart;
	/**  修改时间-区间查询-结束时间  **/
	private Date gmtModifiedEnd;
	/**  字符备用1-模糊查询  **/
	private String sch_Att1_;
	/**  字符备用1-左模糊查询  **/
	private String sch_Att1;
	/**  字符备用1-右模糊查询  **/
	private String schAtt1_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String att1Start;
	/**  字符备用1-区间查询-结束字符串  **/
	private String att1End;
	/**  字符备用2-模糊查询  **/
	private String sch_Att2_;
	/**  字符备用2-左模糊查询  **/
	private String sch_Att2;
	/**  字符备用2-右模糊查询  **/
	private String schAtt2_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String att2Start;
	/**  字符备用2-区间查询-结束字符串  **/
	private String att2End;
	/**  备注-模糊查询  **/
	private String sch_Remark_;
	/**  备注-左模糊查询  **/
	private String sch_Remark;
	/**  备注-右模糊查询  **/
	private String schRemark_;
	/**  备注-区间查询-开始字符串  **/
	private String remarkStart;
	/**  备注-区间查询-结束字符串  **/
	private String remarkEnd;

	public  String getSch_VideoCode_() {
		return sch_VideoCode_;
	}
	public void setSch_VideoCode_(String sch_VideoCode_){
		this.sch_VideoCode_ = sch_VideoCode_;
	}
	public  String getSch_VideoCode() {
		return sch_VideoCode;
	}
	public void setSch_VideoCode(String sch_VideoCode){
		this.sch_VideoCode = sch_VideoCode;
	}
	public  String getSchVideoCode_() {
		return schVideoCode_;
	}
	public void setSchVideoCode_(String schVideoCode_){
		this.schVideoCode_ = schVideoCode_;
	}
	public  String getVideoCodeStart() {
		return videoCodeStart;
	}
	public void setVideoCodeStart(String videoCodeStart){
		this.videoCodeStart = videoCodeStart;
	}
	public  String getVideoCodeEnd() {
		return videoCodeEnd;
	}
	public void setVideoCodeEnd(String videoCodeEnd){
		this.videoCodeEnd = videoCodeEnd;
	}
	public  String getSch_VideoName_() {
		return sch_VideoName_;
	}
	public void setSch_VideoName_(String sch_VideoName_){
		this.sch_VideoName_ = sch_VideoName_;
	}
	public  String getSch_VideoName() {
		return sch_VideoName;
	}
	public void setSch_VideoName(String sch_VideoName){
		this.sch_VideoName = sch_VideoName;
	}
	public  String getSchVideoName_() {
		return schVideoName_;
	}
	public void setSchVideoName_(String schVideoName_){
		this.schVideoName_ = schVideoName_;
	}
	public  String getVideoNameStart() {
		return videoNameStart;
	}
	public void setVideoNameStart(String videoNameStart){
		this.videoNameStart = videoNameStart;
	}
	public  String getVideoNameEnd() {
		return videoNameEnd;
	}
	public void setVideoNameEnd(String videoNameEnd){
		this.videoNameEnd = videoNameEnd;
	}
	public  String getSch_VideoFileName_() {
		return sch_VideoFileName_;
	}
	public void setSch_VideoFileName_(String sch_VideoFileName_){
		this.sch_VideoFileName_ = sch_VideoFileName_;
	}
	public  String getSch_VideoFileName() {
		return sch_VideoFileName;
	}
	public void setSch_VideoFileName(String sch_VideoFileName){
		this.sch_VideoFileName = sch_VideoFileName;
	}
	public  String getSchVideoFileName_() {
		return schVideoFileName_;
	}
	public void setSchVideoFileName_(String schVideoFileName_){
		this.schVideoFileName_ = schVideoFileName_;
	}
	public  String getVideoFileNameStart() {
		return videoFileNameStart;
	}
	public void setVideoFileNameStart(String videoFileNameStart){
		this.videoFileNameStart = videoFileNameStart;
	}
	public  String getVideoFileNameEnd() {
		return videoFileNameEnd;
	}
	public void setVideoFileNameEnd(String videoFileNameEnd){
		this.videoFileNameEnd = videoFileNameEnd;
	}
	public  String getSch_VideoType_() {
		return sch_VideoType_;
	}
	public void setSch_VideoType_(String sch_VideoType_){
		this.sch_VideoType_ = sch_VideoType_;
	}
	public  String getSch_VideoType() {
		return sch_VideoType;
	}
	public void setSch_VideoType(String sch_VideoType){
		this.sch_VideoType = sch_VideoType;
	}
	public  String getSchVideoType_() {
		return schVideoType_;
	}
	public void setSchVideoType_(String schVideoType_){
		this.schVideoType_ = schVideoType_;
	}
	public  String getVideoTypeStart() {
		return videoTypeStart;
	}
	public void setVideoTypeStart(String videoTypeStart){
		this.videoTypeStart = videoTypeStart;
	}
	public  String getVideoTypeEnd() {
		return videoTypeEnd;
	}
	public void setVideoTypeEnd(String videoTypeEnd){
		this.videoTypeEnd = videoTypeEnd;
	}
	public  String getSch_VideoSuffix_() {
		return sch_VideoSuffix_;
	}
	public void setSch_VideoSuffix_(String sch_VideoSuffix_){
		this.sch_VideoSuffix_ = sch_VideoSuffix_;
	}
	public  String getSch_VideoSuffix() {
		return sch_VideoSuffix;
	}
	public void setSch_VideoSuffix(String sch_VideoSuffix){
		this.sch_VideoSuffix = sch_VideoSuffix;
	}
	public  String getSchVideoSuffix_() {
		return schVideoSuffix_;
	}
	public void setSchVideoSuffix_(String schVideoSuffix_){
		this.schVideoSuffix_ = schVideoSuffix_;
	}
	public  String getVideoSuffixStart() {
		return videoSuffixStart;
	}
	public void setVideoSuffixStart(String videoSuffixStart){
		this.videoSuffixStart = videoSuffixStart;
	}
	public  String getVideoSuffixEnd() {
		return videoSuffixEnd;
	}
	public void setVideoSuffixEnd(String videoSuffixEnd){
		this.videoSuffixEnd = videoSuffixEnd;
	}
	public  String getSch_VideoFormat_() {
		return sch_VideoFormat_;
	}
	public void setSch_VideoFormat_(String sch_VideoFormat_){
		this.sch_VideoFormat_ = sch_VideoFormat_;
	}
	public  String getSch_VideoFormat() {
		return sch_VideoFormat;
	}
	public void setSch_VideoFormat(String sch_VideoFormat){
		this.sch_VideoFormat = sch_VideoFormat;
	}
	public  String getSchVideoFormat_() {
		return schVideoFormat_;
	}
	public void setSchVideoFormat_(String schVideoFormat_){
		this.schVideoFormat_ = schVideoFormat_;
	}
	public  String getVideoFormatStart() {
		return videoFormatStart;
	}
	public void setVideoFormatStart(String videoFormatStart){
		this.videoFormatStart = videoFormatStart;
	}
	public  String getVideoFormatEnd() {
		return videoFormatEnd;
	}
	public void setVideoFormatEnd(String videoFormatEnd){
		this.videoFormatEnd = videoFormatEnd;
	}
	public  String getSch_VideoPath_() {
		return sch_VideoPath_;
	}
	public void setSch_VideoPath_(String sch_VideoPath_){
		this.sch_VideoPath_ = sch_VideoPath_;
	}
	public  String getSch_VideoPath() {
		return sch_VideoPath;
	}
	public void setSch_VideoPath(String sch_VideoPath){
		this.sch_VideoPath = sch_VideoPath;
	}
	public  String getSchVideoPath_() {
		return schVideoPath_;
	}
	public void setSchVideoPath_(String schVideoPath_){
		this.schVideoPath_ = schVideoPath_;
	}
	public  String getVideoPathStart() {
		return videoPathStart;
	}
	public void setVideoPathStart(String videoPathStart){
		this.videoPathStart = videoPathStart;
	}
	public  String getVideoPathEnd() {
		return videoPathEnd;
	}
	public void setVideoPathEnd(String videoPathEnd){
		this.videoPathEnd = videoPathEnd;
	}
	public  String getSch_VideoDesc_() {
		return sch_VideoDesc_;
	}
	public void setSch_VideoDesc_(String sch_VideoDesc_){
		this.sch_VideoDesc_ = sch_VideoDesc_;
	}
	public  String getSch_VideoDesc() {
		return sch_VideoDesc;
	}
	public void setSch_VideoDesc(String sch_VideoDesc){
		this.sch_VideoDesc = sch_VideoDesc;
	}
	public  String getSchVideoDesc_() {
		return schVideoDesc_;
	}
	public void setSchVideoDesc_(String schVideoDesc_){
		this.schVideoDesc_ = schVideoDesc_;
	}
	public  String getVideoDescStart() {
		return videoDescStart;
	}
	public void setVideoDescStart(String videoDescStart){
		this.videoDescStart = videoDescStart;
	}
	public  String getVideoDescEnd() {
		return videoDescEnd;
	}
	public void setVideoDescEnd(String videoDescEnd){
		this.videoDescEnd = videoDescEnd;
	}
	public  String getSch_FileKey_() {
		return sch_FileKey_;
	}
	public void setSch_FileKey_(String sch_FileKey_){
		this.sch_FileKey_ = sch_FileKey_;
	}
	public  String getSch_FileKey() {
		return sch_FileKey;
	}
	public void setSch_FileKey(String sch_FileKey){
		this.sch_FileKey = sch_FileKey;
	}
	public  String getSchFileKey_() {
		return schFileKey_;
	}
	public void setSchFileKey_(String schFileKey_){
		this.schFileKey_ = schFileKey_;
	}
	public  String getFileKeyStart() {
		return fileKeyStart;
	}
	public void setFileKeyStart(String fileKeyStart){
		this.fileKeyStart = fileKeyStart;
	}
	public  String getFileKeyEnd() {
		return fileKeyEnd;
	}
	public void setFileKeyEnd(String fileKeyEnd){
		this.fileKeyEnd = fileKeyEnd;
	}
	public  String getSch_FileKeys_() {
		return sch_FileKeys_;
	}
	public void setSch_FileKeys_(String sch_FileKeys_){
		this.sch_FileKeys_ = sch_FileKeys_;
	}
	public  String getSch_FileKeys() {
		return sch_FileKeys;
	}
	public void setSch_FileKeys(String sch_FileKeys){
		this.sch_FileKeys = sch_FileKeys;
	}
	public  String getSchFileKeys_() {
		return schFileKeys_;
	}
	public void setSchFileKeys_(String schFileKeys_){
		this.schFileKeys_ = schFileKeys_;
	}
	public  String getFileKeysStart() {
		return fileKeysStart;
	}
	public void setFileKeysStart(String fileKeysStart){
		this.fileKeysStart = fileKeysStart;
	}
	public  String getFileKeysEnd() {
		return fileKeysEnd;
	}
	public void setFileKeysEnd(String fileKeysEnd){
		this.fileKeysEnd = fileKeysEnd;
	}
	public  String getSch_SmallVideoInfo_() {
		return sch_SmallVideoInfo_;
	}
	public void setSch_SmallVideoInfo_(String sch_SmallVideoInfo_){
		this.sch_SmallVideoInfo_ = sch_SmallVideoInfo_;
	}
	public  String getSch_SmallVideoInfo() {
		return sch_SmallVideoInfo;
	}
	public void setSch_SmallVideoInfo(String sch_SmallVideoInfo){
		this.sch_SmallVideoInfo = sch_SmallVideoInfo;
	}
	public  String getSchSmallVideoInfo_() {
		return schSmallVideoInfo_;
	}
	public void setSchSmallVideoInfo_(String schSmallVideoInfo_){
		this.schSmallVideoInfo_ = schSmallVideoInfo_;
	}
	public  String getSmallVideoInfoStart() {
		return smallVideoInfoStart;
	}
	public void setSmallVideoInfoStart(String smallVideoInfoStart){
		this.smallVideoInfoStart = smallVideoInfoStart;
	}
	public  String getSmallVideoInfoEnd() {
		return smallVideoInfoEnd;
	}
	public void setSmallVideoInfoEnd(String smallVideoInfoEnd){
		this.smallVideoInfoEnd = smallVideoInfoEnd;
	}
	public  Date getTaskCreateTimeStart() {
		return taskCreateTimeStart;
	}
	public void setTaskCreateTimeStart(Date taskCreateTimeStart){
		this.taskCreateTimeStart = taskCreateTimeStart;
	}
	public  Date getTaskCreateTimeEnd() {
		return taskCreateTimeEnd;
	}
	public void setTaskCreateTimeEnd(Date taskCreateTimeEnd){
		this.taskCreateTimeEnd = taskCreateTimeEnd;
	}
	public  String getSch_SysCode_() {
		return sch_SysCode_;
	}
	public void setSch_SysCode_(String sch_SysCode_){
		this.sch_SysCode_ = sch_SysCode_;
	}
	public  String getSch_SysCode() {
		return sch_SysCode;
	}
	public void setSch_SysCode(String sch_SysCode){
		this.sch_SysCode = sch_SysCode;
	}
	public  String getSchSysCode_() {
		return schSysCode_;
	}
	public void setSchSysCode_(String schSysCode_){
		this.schSysCode_ = schSysCode_;
	}
	public  String getSysCodeStart() {
		return sysCodeStart;
	}
	public void setSysCodeStart(String sysCodeStart){
		this.sysCodeStart = sysCodeStart;
	}
	public  String getSysCodeEnd() {
		return sysCodeEnd;
	}
	public void setSysCodeEnd(String sysCodeEnd){
		this.sysCodeEnd = sysCodeEnd;
	}
	public  String getSch_Creator_() {
		return sch_Creator_;
	}
	public void setSch_Creator_(String sch_Creator_){
		this.sch_Creator_ = sch_Creator_;
	}
	public  String getSch_Creator() {
		return sch_Creator;
	}
	public void setSch_Creator(String sch_Creator){
		this.sch_Creator = sch_Creator;
	}
	public  String getSchCreator_() {
		return schCreator_;
	}
	public void setSchCreator_(String schCreator_){
		this.schCreator_ = schCreator_;
	}
	public  String getCreatorStart() {
		return creatorStart;
	}
	public void setCreatorStart(String creatorStart){
		this.creatorStart = creatorStart;
	}
	public  String getCreatorEnd() {
		return creatorEnd;
	}
	public void setCreatorEnd(String creatorEnd){
		this.creatorEnd = creatorEnd;
	}
	public  Date getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(Date gmtCreateStart){
		this.gmtCreateStart = gmtCreateStart;
	}
	public  Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(Date gmtCreateEnd){
		this.gmtCreateEnd = gmtCreateEnd;
	}
	public  String getSch_Modifier_() {
		return sch_Modifier_;
	}
	public void setSch_Modifier_(String sch_Modifier_){
		this.sch_Modifier_ = sch_Modifier_;
	}
	public  String getSch_Modifier() {
		return sch_Modifier;
	}
	public void setSch_Modifier(String sch_Modifier){
		this.sch_Modifier = sch_Modifier;
	}
	public  String getSchModifier_() {
		return schModifier_;
	}
	public void setSchModifier_(String schModifier_){
		this.schModifier_ = schModifier_;
	}
	public  String getModifierStart() {
		return modifierStart;
	}
	public void setModifierStart(String modifierStart){
		this.modifierStart = modifierStart;
	}
	public  String getModifierEnd() {
		return modifierEnd;
	}
	public void setModifierEnd(String modifierEnd){
		this.modifierEnd = modifierEnd;
	}
	public  Date getGmtModifiedStart() {
		return gmtModifiedStart;
	}
	public void setGmtModifiedStart(Date gmtModifiedStart){
		this.gmtModifiedStart = gmtModifiedStart;
	}
	public  Date getGmtModifiedEnd() {
		return gmtModifiedEnd;
	}
	public void setGmtModifiedEnd(Date gmtModifiedEnd){
		this.gmtModifiedEnd = gmtModifiedEnd;
	}
	public  String getSch_Att1_() {
		return sch_Att1_;
	}
	public void setSch_Att1_(String sch_Att1_){
		this.sch_Att1_ = sch_Att1_;
	}
	public  String getSch_Att1() {
		return sch_Att1;
	}
	public void setSch_Att1(String sch_Att1){
		this.sch_Att1 = sch_Att1;
	}
	public  String getSchAtt1_() {
		return schAtt1_;
	}
	public void setSchAtt1_(String schAtt1_){
		this.schAtt1_ = schAtt1_;
	}
	public  String getAtt1Start() {
		return att1Start;
	}
	public void setAtt1Start(String att1Start){
		this.att1Start = att1Start;
	}
	public  String getAtt1End() {
		return att1End;
	}
	public void setAtt1End(String att1End){
		this.att1End = att1End;
	}
	public  String getSch_Att2_() {
		return sch_Att2_;
	}
	public void setSch_Att2_(String sch_Att2_){
		this.sch_Att2_ = sch_Att2_;
	}
	public  String getSch_Att2() {
		return sch_Att2;
	}
	public void setSch_Att2(String sch_Att2){
		this.sch_Att2 = sch_Att2;
	}
	public  String getSchAtt2_() {
		return schAtt2_;
	}
	public void setSchAtt2_(String schAtt2_){
		this.schAtt2_ = schAtt2_;
	}
	public  String getAtt2Start() {
		return att2Start;
	}
	public void setAtt2Start(String att2Start){
		this.att2Start = att2Start;
	}
	public  String getAtt2End() {
		return att2End;
	}
	public void setAtt2End(String att2End){
		this.att2End = att2End;
	}
	public  String getSch_Remark_() {
		return sch_Remark_;
	}
	public void setSch_Remark_(String sch_Remark_){
		this.sch_Remark_ = sch_Remark_;
	}
	public  String getSch_Remark() {
		return sch_Remark;
	}
	public void setSch_Remark(String sch_Remark){
		this.sch_Remark = sch_Remark;
	}
	public  String getSchRemark_() {
		return schRemark_;
	}
	public void setSchRemark_(String schRemark_){
		this.schRemark_ = schRemark_;
	}
	public  String getRemarkStart() {
		return remarkStart;
	}
	public void setRemarkStart(String remarkStart){
		this.remarkStart = remarkStart;
	}
	public  String getRemarkEnd() {
		return remarkEnd;
	}
	public void setRemarkEnd(String remarkEnd){
		this.remarkEnd = remarkEnd;
	}

}
