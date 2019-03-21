package com.nork.design.model.small;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**   
 * @Title: DesignPlan.java 
 * @Package com.nork.design.model.small
 * @Description:设计模块-设计方案
 * @createAuthor pandajun 
 * @CreateDate 2015-07-03 17:09:51
 * @version V1.0   
 */
public class DesignPlanSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  方案编码  **/
	private String planCode;
	/**  方案名称  **/
	private String planName;
	/**  设计者id  **/
	private Integer userId;
	/**  方案来源类型  **/
	private String designSourceType;
	/**  来源id  **/
	private Integer designId;
	/**  设计风格  **/
	private Integer designStyleId;
	/**  缩略图id  **/
	private String picId;
	/**  u3d模型id  **/
	private String modelId;
	/**  配置文件id  **/
	private String configFileId;
	/**  空间id  **/
	private String spaceCommonId;
	/**  方案描述  **/
	private String planDesc; 
	/**  创建时间  **/
	private Date gmtCreate; 
	/**  修改时间  **/
	private Date gmtModified; 

	public  String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode){
		this.planCode = planCode;
	}
	public  String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName){
		this.planName = planName;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getDesignSourceType() {
		return designSourceType;
	}
	public void setDesignSourceType(String designSourceType){
		this.designSourceType = designSourceType;
	}
	public  Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId){
		this.designId = designId;
	}
	public  Integer getDesignStyleId() {
		return designStyleId;
	}
	public void setDesignStyleId(Integer designStyleId){
		this.designStyleId = designStyleId;
	}
	 
	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getConfigFileId() {
		return configFileId;
	}

	public void setConfigFileId(String configFileId) {
		this.configFileId = configFileId;
	}

	public String getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(String spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public  String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc){
		this.planDesc = planDesc;
	}
	 
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	 
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	 
	
	private String planUserName;
    private String planStyle;
    private String spaceCode;
    private String spaceName;
    private String spaceAreas;
    private String picPath;
    private String filePath;
    List picList = new ArrayList();
    private Integer commentCount;//评论数量
    private String mostType;  //最多评论 、最多分享
    private Integer collectState;//收藏状态
    private Integer planProductCount;//方案产品数量
    
    
   	public Integer getPlanProductCount() {
   		return planProductCount;
   	}

   	public void setPlanProductCount(Integer planProductCount) {
   		this.planProductCount = planProductCount;
   	}
    
    
    
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List getPicList() {
		return picList;
	}

	public void setPicList(List picList) {
		this.picList = picList;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getMostType() {
		return mostType;
	}

	public void setMostType(String mostType) {
		this.mostType = mostType;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public String getPlanUserName() {
		return planUserName;
	}

	public void setPlanUserName(String planUserName) {
		this.planUserName = planUserName;
	}

	public String getPlanStyle() {
		return planStyle;
	}

	public void setPlanStyle(String planStyle) {
		this.planStyle = planStyle;
	}

	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
    
    

}
