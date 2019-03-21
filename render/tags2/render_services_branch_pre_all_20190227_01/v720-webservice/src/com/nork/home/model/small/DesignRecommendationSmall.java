package com.nork.home.model.small;

import java.io.Serializable;


/**   
 * @Title: DesignRecommendation.java 
 * @Package com.nork.home.model.small
 * @Description:户型房型-效果图推荐
 * @createAuthor pandajun 
 * @CreateDate 2015-07-06 20:00:19
 * @version V1.0   
 */
public class DesignRecommendationSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  来源code  **/
	private String srcCode;
	/**  来源类型  **/
	private String srcType;
	/**  图片ID  **/
	private String picId;
	/**  首页是否显示  **/
	private Integer homeIsDisplayed;
	/**  设计风格  **/
	private String designStyleId;
	/**  用户ID  **/
	private Integer userId;
	/**  面积  **/
	private String spaceArea;
	/**  名称  **/
	private String designName;

	public  String getSrcCode() {
		return srcCode;
	}
	public void setSrcCode(String srcCode){
		this.srcCode = srcCode;
	}
	public  String getSrcType() {
		return srcType;
	}
	public void setSrcType(String srcType){
		this.srcType = srcType;
	}
	public  String getPicId() {
		return picId;
	}
	public void setPicId(String picId){
		this.picId = picId;
	}
	public  Integer getHomeIsDisplayed() {
		return homeIsDisplayed;
	}
	public void setHomeIsDisplayed(Integer homeIsDisplayed){
		this.homeIsDisplayed = homeIsDisplayed;
	}
	public  String getDesignStyleId() {
		return designStyleId;
	}
	public void setDesignStyleId(String designStyleId){
		this.designStyleId = designStyleId;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(String spaceArea){
		this.spaceArea = spaceArea;
	}
	public  String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName){
		this.designName = designName; 
	}
	private String spaceName;
    private String picPath;
    
	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
