package com.nork.system.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.system.model.ResFile;

/**   
 * @Title: ResFileSearch.java 
 * @Package com.nork.system.model
 * @Description:系统模块-文件资源库查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-07-02 17:36:00
 * @version V1.0   
 */
public class ResFileSearch extends  ResFile implements Serializable{
private static final long serialVersionUID = 1L;
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
	/**  文件编码-模糊查询  **/
	private String sch_FileCode_;
	/**  文件编码-左模糊查询  **/
	private String sch_FileCode;
	/**  文件编码-右模糊查询  **/
	private String schFileCode_;
	/**  文件编码-区间查询-开始字符串  **/
	private String fileCodeStart;
	/**  文件编码-区间查询-结束字符串  **/
	private String fileCodeEnd;
	/**  文件名称-模糊查询  **/
	private String sch_FileName_;
	/**  文件名称-左模糊查询  **/
	private String sch_FileName;
	/**  文件名称-右模糊查询  **/
	private String schFileName_;
	/**  文件名称-区间查询-开始字符串  **/
	private String fileNameStart;
	/**  文件名称-区间查询-结束字符串  **/
	private String fileNameEnd;
	/**  文件原名称-模糊查询  **/
	private String sch_FileOriginalName_;
	/**  文件原名称-左模糊查询  **/
	private String sch_FileOriginalName;
	/**  文件原名称-右模糊查询  **/
	private String schFileOriginalName_;
	/**  文件原名称-区间查询-开始字符串  **/
	private String fileOriginalNameStart;
	/**  文件原名称-区间查询-结束字符串  **/
	private String fileOriginalNameEnd;
	/**  文件类型-模糊查询  **/
	private String sch_FileType_;
	/**  文件类型-左模糊查询  **/
	private String sch_FileType;
	/**  文件类型-右模糊查询  **/
	private String schFileType_;
	/**  文件类型-区间查询-开始字符串  **/
	private String fileTypeStart;
	/**  文件类型-区间查询-结束字符串  **/
	private String fileTypeEnd;
	/**  文件大小-模糊查询  **/
	private String sch_FileSize_;
	/**  文件大小-左模糊查询  **/
	private String sch_FileSize;
	/**  文件大小-右模糊查询  **/
	private String schFileSize_;
	/**  文件大小-区间查询-开始字符串  **/
	private String fileSizeStart;
	/**  文件大小-区间查询-结束字符串  **/
	private String fileSizeEnd;
	/**  文件后缀-模糊查询  **/
	private String sch_FileSuffix_;
	/**  文件后缀-左模糊查询  **/
	private String sch_FileSuffix;
	/**  文件后缀-右模糊查询  **/
	private String schFileSuffix_;
	/**  文件后缀-区间查询-开始字符串  **/
	private String fileSuffixStart;
	/**  文件后缀-区间查询-结束字符串  **/
	private String fileSuffixEnd;
	/**  文件级别-模糊查询  **/
	private String sch_FileLevel_;
	/**  文件级别-左模糊查询  **/
	private String sch_FileLevel;
	/**  文件级别-右模糊查询  **/
	private String schFileLevel_;
	/**  文件级别-区间查询-开始字符串  **/
	private String fileLevelStart;
	/**  文件级别-区间查询-结束字符串  **/
	private String fileLevelEnd;
	/**  文件路径-模糊查询  **/
	private String sch_FilePath_;
	/**  文件路径-左模糊查询  **/
	private String sch_FilePath;
	/**  文件路径-右模糊查询  **/
	private String schFilePath_;
	/**  文件路径-区间查询-开始字符串  **/
	private String filePathStart;
	/**  文件路径-区间查询-结束字符串  **/
	private String filePathEnd;
	/**  文件描述-模糊查询  **/
	private String sch_FileDesc_;
	/**  文件描述-左模糊查询  **/
	private String sch_FileDesc;
	/**  文件描述-右模糊查询  **/
	private String schFileDesc_;
	/**  文件描述-区间查询-开始字符串  **/
	private String fileDescStart;
	/**  文件描述-区间查询-结束字符串  **/
	private String fileDescEnd;
	/**  字符备用1-模糊查询  **/
	private String sch_FileKey_;
	/**  字符备用1-左模糊查询  **/
	private String sch_FileKey;
	/**  字符备用1-右模糊查询  **/
	private String schFileKey_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String fileKeyStart;
	/**  字符备用1-区间查询-结束字符串  **/
	private String fileKeyEnd;
	/**  字符备用2-模糊查询  **/
	private String sch_FileKeys_;
	/**  字符备用2-左模糊查询  **/
	private String sch_FileKeys;
	/**  字符备用2-右模糊查询  **/
	private String schFileKeys_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String fileKeysStart;
	/**  字符备用2-区间查询-结束字符串  **/
	private String fileKeysEnd;
	/**  字符备用3-模糊查询  **/
	private String sch_BusinessIds_;
	/**  字符备用3-左模糊查询  **/
	private String sch_BusinessIds;
	/**  字符备用3-右模糊查询  **/
	private String schBusinessIds_;
	/**  字符备用3-区间查询-开始字符串  **/
	private String businessIdsStart;
	/**  字符备用3-区间查询-结束字符串  **/
	private String businessIdsEnd;
	/**  字符备用4-模糊查询  **/
	private String sch_Att4_;
	/**  字符备用4-左模糊查询  **/
	private String sch_Att4;
	/**  字符备用4-右模糊查询  **/
	private String schAtt4_;
	/**  字符备用4-区间查询-开始字符串  **/
	private String att4Start;
	/**  字符备用4-区间查询-结束字符串  **/
	private String att4End;
	/**  字符备用5-模糊查询  **/
	private String sch_Att5_;
	/**  字符备用5-左模糊查询  **/
	private String sch_Att5;
	/**  字符备用5-右模糊查询  **/
	private String schAtt5_;
	/**  字符备用5-区间查询-开始字符串  **/
	private String att5Start;
	/**  字符备用5-区间查询-结束字符串  **/
	private String att5End;
	/**  字符备用6-模糊查询  **/
	private String sch_Att6_;
	/**  字符备用6-左模糊查询  **/
	private String sch_Att6;
	/**  字符备用6-右模糊查询  **/
	private String schAtt6_;
	/**  字符备用6-区间查询-开始字符串  **/
	private String att6Start;
	/**  字符备用6-区间查询-结束字符串  **/
	private String att6End;
	/**  时间备用1-区间查询-开始时间  **/
	private Date dateAtt1Start;
	/**  时间备用1-区间查询-结束时间  **/
	private Date dateAtt1End;
	/**  时间备用2-区间查询-开始时间  **/
	private Date dateAtt2Start;
	/**  时间备用2-区间查询-结束时间  **/
	private Date dateAtt2End;
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

	/*添加三个字段:三个字段能获得file_type信息*/
	private String firstMenu;
	
	private String secondMenu;
	
	private String thirdMenuF;
	/*添加正则表达式查询fileKey字段*/
	private String regexpFileKey;
	
	public String getFirstMenu() {
		return firstMenu;
	}
	public void setFirstMenu(String firstMenu) {
		this.firstMenu = firstMenu;
	}
	public String getSecondMenu() {
		return secondMenu;
	}
	public void setSecondMenu(String secondMenu) {
		this.secondMenu = secondMenu;
	}
	public String getThirdMenuF() {
		return thirdMenuF;
	}
	public void setThirdMenuF(String thirdMenuF) {
		this.thirdMenuF = thirdMenuF;
	}
	public String getRegexpFileKey() {
		return regexpFileKey;
	}
	public void setRegexpFileKey(String regexpFileKey) {
		this.regexpFileKey = regexpFileKey;
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
	public  String getSch_FileCode_() {
		return sch_FileCode_;
	}
	public void setSch_FileCode_(String sch_FileCode_){
		this.sch_FileCode_ = sch_FileCode_;
	}
	public  String getSch_FileCode() {
		return sch_FileCode;
	}
	public void setSch_FileCode(String sch_FileCode){
		this.sch_FileCode = sch_FileCode;
	}
	public  String getSchFileCode_() {
		return schFileCode_;
	}
	public void setSchFileCode_(String schFileCode_){
		this.schFileCode_ = schFileCode_;
	}
	public  String getFileCodeStart() {
		return fileCodeStart;
	}
	public void setFileCodeStart(String fileCodeStart){
		this.fileCodeStart = fileCodeStart;
	}
	public  String getFileCodeEnd() {
		return fileCodeEnd;
	}
	public void setFileCodeEnd(String fileCodeEnd){
		this.fileCodeEnd = fileCodeEnd;
	}
	public  String getSch_FileName_() {
		return sch_FileName_;
	}
	public void setSch_FileName_(String sch_FileName_){
		this.sch_FileName_ = sch_FileName_;
	}
	public  String getSch_FileName() {
		return sch_FileName;
	}
	public void setSch_FileName(String sch_FileName){
		this.sch_FileName = sch_FileName;
	}
	public  String getSchFileName_() {
		return schFileName_;
	}
	public void setSchFileName_(String schFileName_){
		this.schFileName_ = schFileName_;
	}
	public  String getFileNameStart() {
		return fileNameStart;
	}
	public void setFileNameStart(String fileNameStart){
		this.fileNameStart = fileNameStart;
	}
	public  String getFileNameEnd() {
		return fileNameEnd;
	}
	public void setFileNameEnd(String fileNameEnd){
		this.fileNameEnd = fileNameEnd;
	}
	public  String getSch_FileOriginalName_() {
		return sch_FileOriginalName_;
	}
	public void setSch_FileOriginalName_(String sch_FileOriginalName_){
		this.sch_FileOriginalName_ = sch_FileOriginalName_;
	}
	public  String getSch_FileOriginalName() {
		return sch_FileOriginalName;
	}
	public void setSch_FileOriginalName(String sch_FileOriginalName){
		this.sch_FileOriginalName = sch_FileOriginalName;
	}
	public  String getSchFileOriginalName_() {
		return schFileOriginalName_;
	}
	public void setSchFileOriginalName_(String schFileOriginalName_){
		this.schFileOriginalName_ = schFileOriginalName_;
	}
	public  String getFileOriginalNameStart() {
		return fileOriginalNameStart;
	}
	public void setFileOriginalNameStart(String fileOriginalNameStart){
		this.fileOriginalNameStart = fileOriginalNameStart;
	}
	public  String getFileOriginalNameEnd() {
		return fileOriginalNameEnd;
	}
	public void setFileOriginalNameEnd(String fileOriginalNameEnd){
		this.fileOriginalNameEnd = fileOriginalNameEnd;
	}
	public  String getSch_FileType_() {
		return sch_FileType_;
	}
	public void setSch_FileType_(String sch_FileType_){
		this.sch_FileType_ = sch_FileType_;
	}
	public  String getSch_FileType() {
		return sch_FileType;
	}
	public void setSch_FileType(String sch_FileType){
		this.sch_FileType = sch_FileType;
	}
	public  String getSchFileType_() {
		return schFileType_;
	}
	public void setSchFileType_(String schFileType_){
		this.schFileType_ = schFileType_;
	}
	public  String getFileTypeStart() {
		return fileTypeStart;
	}
	public void setFileTypeStart(String fileTypeStart){
		this.fileTypeStart = fileTypeStart;
	}
	public  String getFileTypeEnd() {
		return fileTypeEnd;
	}
	public void setFileTypeEnd(String fileTypeEnd){
		this.fileTypeEnd = fileTypeEnd;
	}
	public  String getSch_FileSize_() {
		return sch_FileSize_;
	}
	public void setSch_FileSize_(String sch_FileSize_){
		this.sch_FileSize_ = sch_FileSize_;
	}
	public  String getSch_FileSize() {
		return sch_FileSize;
	}
	public void setSch_FileSize(String sch_FileSize){
		this.sch_FileSize = sch_FileSize;
	}
	public  String getSchFileSize_() {
		return schFileSize_;
	}
	public void setSchFileSize_(String schFileSize_){
		this.schFileSize_ = schFileSize_;
	}
	public  String getFileSizeStart() {
		return fileSizeStart;
	}
	public void setFileSizeStart(String fileSizeStart){
		this.fileSizeStart = fileSizeStart;
	}
	public  String getFileSizeEnd() {
		return fileSizeEnd;
	}
	public void setFileSizeEnd(String fileSizeEnd){
		this.fileSizeEnd = fileSizeEnd;
	}
	public  String getSch_FileSuffix_() {
		return sch_FileSuffix_;
	}
	public void setSch_FileSuffix_(String sch_FileSuffix_){
		this.sch_FileSuffix_ = sch_FileSuffix_;
	}
	public  String getSch_FileSuffix() {
		return sch_FileSuffix;
	}
	public void setSch_FileSuffix(String sch_FileSuffix){
		this.sch_FileSuffix = sch_FileSuffix;
	}
	public  String getSchFileSuffix_() {
		return schFileSuffix_;
	}
	public void setSchFileSuffix_(String schFileSuffix_){
		this.schFileSuffix_ = schFileSuffix_;
	}
	public  String getFileSuffixStart() {
		return fileSuffixStart;
	}
	public void setFileSuffixStart(String fileSuffixStart){
		this.fileSuffixStart = fileSuffixStart;
	}
	public  String getFileSuffixEnd() {
		return fileSuffixEnd;
	}
	public void setFileSuffixEnd(String fileSuffixEnd){
		this.fileSuffixEnd = fileSuffixEnd;
	}
	public  String getSch_FileLevel_() {
		return sch_FileLevel_;
	}
	public void setSch_FileLevel_(String sch_FileLevel_){
		this.sch_FileLevel_ = sch_FileLevel_;
	}
	public  String getSch_FileLevel() {
		return sch_FileLevel;
	}
	public void setSch_FileLevel(String sch_FileLevel){
		this.sch_FileLevel = sch_FileLevel;
	}
	public  String getSchFileLevel_() {
		return schFileLevel_;
	}
	public void setSchFileLevel_(String schFileLevel_){
		this.schFileLevel_ = schFileLevel_;
	}
	public  String getFileLevelStart() {
		return fileLevelStart;
	}
	public void setFileLevelStart(String fileLevelStart){
		this.fileLevelStart = fileLevelStart;
	}
	public  String getFileLevelEnd() {
		return fileLevelEnd;
	}
	public void setFileLevelEnd(String fileLevelEnd){
		this.fileLevelEnd = fileLevelEnd;
	}
	public  String getSch_FilePath_() {
		return sch_FilePath_;
	}
	public void setSch_FilePath_(String sch_FilePath_){
		this.sch_FilePath_ = sch_FilePath_;
	}
	public  String getSch_FilePath() {
		return sch_FilePath;
	}
	public void setSch_FilePath(String sch_FilePath){
		this.sch_FilePath = sch_FilePath;
	}
	public  String getSchFilePath_() {
		return schFilePath_;
	}
	public void setSchFilePath_(String schFilePath_){
		this.schFilePath_ = schFilePath_;
	}
	public  String getFilePathStart() {
		return filePathStart;
	}
	public void setFilePathStart(String filePathStart){
		this.filePathStart = filePathStart;
	}
	public  String getFilePathEnd() {
		return filePathEnd;
	}
	public void setFilePathEnd(String filePathEnd){
		this.filePathEnd = filePathEnd;
	}
	public  String getSch_FileDesc_() {
		return sch_FileDesc_;
	}
	public void setSch_FileDesc_(String sch_FileDesc_){
		this.sch_FileDesc_ = sch_FileDesc_;
	}
	public  String getSch_FileDesc() {
		return sch_FileDesc;
	}
	public void setSch_FileDesc(String sch_FileDesc){
		this.sch_FileDesc = sch_FileDesc;
	}
	public  String getSchFileDesc_() {
		return schFileDesc_;
	}
	public void setSchFileDesc_(String schFileDesc_){
		this.schFileDesc_ = schFileDesc_;
	}
	public  String getFileDescStart() {
		return fileDescStart;
	}
	public void setFileDescStart(String fileDescStart){
		this.fileDescStart = fileDescStart;
	}
	public  String getFileDescEnd() {
		return fileDescEnd;
	}
	public void setFileDescEnd(String fileDescEnd){
		this.fileDescEnd = fileDescEnd;
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
	public String getSch_BusinessIds_() {
		return sch_BusinessIds_;
	}
	public void setSch_BusinessIds_(String sch_BusinessIds_) {
		this.sch_BusinessIds_ = sch_BusinessIds_;
	}
	public String getSch_BusinessIds() {
		return sch_BusinessIds;
	}
	public void setSch_BusinessIds(String sch_BusinessIds) {
		this.sch_BusinessIds = sch_BusinessIds;
	}
	public String getSchBusinessIds_() {
		return schBusinessIds_;
	}
	public void setSchBusinessIds_(String schBusinessIds_) {
		this.schBusinessIds_ = schBusinessIds_;
	}
	public String getBusinessIdsStart() {
		return businessIdsStart;
	}
	public void setBusinessIdsStart(String businessIdsStart) {
		this.businessIdsStart = businessIdsStart;
	}
	public String getBusinessIdsEnd() {
		return businessIdsEnd;
	}
	public void setBusinessIdsEnd(String businessIdsEnd) {
		this.businessIdsEnd = businessIdsEnd;
	}
	public  String getSch_Att4_() {
		return sch_Att4_;
	}
	public void setSch_Att4_(String sch_Att4_){
		this.sch_Att4_ = sch_Att4_;
	}
	public  String getSch_Att4() {
		return sch_Att4;
	}
	public void setSch_Att4(String sch_Att4){
		this.sch_Att4 = sch_Att4;
	}
	public  String getSchAtt4_() {
		return schAtt4_;
	}
	public void setSchAtt4_(String schAtt4_){
		this.schAtt4_ = schAtt4_;
	}
	public  String getAtt4Start() {
		return att4Start;
	}
	public void setAtt4Start(String att4Start){
		this.att4Start = att4Start;
	}
	public  String getAtt4End() {
		return att4End;
	}
	public void setAtt4End(String att4End){
		this.att4End = att4End;
	}
	public  String getSch_Att5_() {
		return sch_Att5_;
	}
	public void setSch_Att5_(String sch_Att5_){
		this.sch_Att5_ = sch_Att5_;
	}
	public  String getSch_Att5() {
		return sch_Att5;
	}
	public void setSch_Att5(String sch_Att5){
		this.sch_Att5 = sch_Att5;
	}
	public  String getSchAtt5_() {
		return schAtt5_;
	}
	public void setSchAtt5_(String schAtt5_){
		this.schAtt5_ = schAtt5_;
	}
	public  String getAtt5Start() {
		return att5Start;
	}
	public void setAtt5Start(String att5Start){
		this.att5Start = att5Start;
	}
	public  String getAtt5End() {
		return att5End;
	}
	public void setAtt5End(String att5End){
		this.att5End = att5End;
	}
	public  String getSch_Att6_() {
		return sch_Att6_;
	}
	public void setSch_Att6_(String sch_Att6_){
		this.sch_Att6_ = sch_Att6_;
	}
	public  String getSch_Att6() {
		return sch_Att6;
	}
	public void setSch_Att6(String sch_Att6){
		this.sch_Att6 = sch_Att6;
	}
	public  String getSchAtt6_() {
		return schAtt6_;
	}
	public void setSchAtt6_(String schAtt6_){
		this.schAtt6_ = schAtt6_;
	}
	public  String getAtt6Start() {
		return att6Start;
	}
	public void setAtt6Start(String att6Start){
		this.att6Start = att6Start;
	}
	public  String getAtt6End() {
		return att6End;
	}
	public void setAtt6End(String att6End){
		this.att6End = att6End;
	}
	public  Date getDateAtt1Start() {
		return dateAtt1Start;
	}
	public void setDateAtt1Start(Date dateAtt1Start){
		this.dateAtt1Start = dateAtt1Start;
	}
	public  Date getDateAtt1End() {
		return dateAtt1End;
	}
	public void setDateAtt1End(Date dateAtt1End){
		this.dateAtt1End = dateAtt1End;
	}
	public  Date getDateAtt2Start() {
		return dateAtt2Start;
	}
	public void setDateAtt2Start(Date dateAtt2Start){
		this.dateAtt2Start = dateAtt2Start;
	}
	public  Date getDateAtt2End() {
		return dateAtt2End;
	}
	public void setDateAtt2End(Date dateAtt2End){
		this.dateAtt2End = dateAtt2End;
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
