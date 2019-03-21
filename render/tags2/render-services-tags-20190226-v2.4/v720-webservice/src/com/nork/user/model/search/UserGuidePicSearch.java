package com.nork.user.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.user.model.UserGuidePic;

/**
 * @Title: UserGuidePicSearch.java
 * @Package com.nork.user.model
 * @Description:用户指南-用户指南图片列表查询对象
 * @createAuthor pandajun
 * @CreateDate 2016-12-07 14:42:28
 * @version V1.0
 */
public class UserGuidePicSearch extends UserGuidePic implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 图片地址-模糊查询 **/
	private String sch_PicUrl_;
	/** 图片地址-左模糊查询 **/
	private String sch_PicUrl;
	/** 图片地址-右模糊查询 **/
	private String schPicUrl_;
	/** 图片地址-区间查询-开始字符串 **/
	private String picUrlStart;
	/** 图片地址-区间查询-结束字符串 **/
	private String picUrlEnd;
	/** 创建时间-区间查询-开始时间 **/
	private Date gmtCreateStart;
	/** 创建时间-区间查询-结束时间 **/
	private Date gmtCreateEnd;
	/** 创建人-模糊查询 **/
	private String sch_Creator_;
	/** 创建人-左模糊查询 **/
	private String sch_Creator;
	/** 创建人-右模糊查询 **/
	private String schCreator_;
	/** 创建人-区间查询-开始字符串 **/
	private String creatorStart;
	/** 创建人-区间查询-结束字符串 **/
	private String creatorEnd;

	private Integer ugId;

	public String getSch_PicUrl_() {
		return sch_PicUrl_;
	}

	public void setSch_PicUrl_(String sch_PicUrl_) {
		this.sch_PicUrl_ = sch_PicUrl_;
	}

	public String getSch_PicUrl() {
		return sch_PicUrl;
	}

	public void setSch_PicUrl(String sch_PicUrl) {
		this.sch_PicUrl = sch_PicUrl;
	}

	public String getSchPicUrl_() {
		return schPicUrl_;
	}

	public void setSchPicUrl_(String schPicUrl_) {
		this.schPicUrl_ = schPicUrl_;
	}

	public String getPicUrlStart() {
		return picUrlStart;
	}

	public void setPicUrlStart(String picUrlStart) {
		this.picUrlStart = picUrlStart;
	}

	public String getPicUrlEnd() {
		return picUrlEnd;
	}

	public void setPicUrlEnd(String picUrlEnd) {
		this.picUrlEnd = picUrlEnd;
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

	public Integer getUgId() {
		return ugId;
	}

	public void setUgId(Integer ugId) {
		this.ugId = ugId;
	}

}
