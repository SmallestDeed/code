/**    
 * 文件名：DesignPlanGroup.java    
 *    
 * 版本信息：    
 * 日期：2017-7-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.design.model;

import java.io.Serializable;

/**    
 *     
 * 项目名称：timeSpace    
 * 类名称：DesignPlanGroup    
 * 类描述： 设计方案分组   
 * 创建人：Timy.Liu   
 * 创建时间：2017-7-6 下午4:03:08    
 * 修改人：Timy.Liu    
 * 修改时间：2017-7-6 下午4:03:08    
 * 修改备注：    
 * @version     
 *     
 */
public class DesignPlanGroup implements Serializable{
    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = 8109588154596097065L;
    /**
     * 业务id
     */
    
    private String bid;
    /**
     * 分组业务id
     */
    private String gid;
    /**
     * 设计方案缩略图id
     */
    private int thumbId;
    /**
     * 对应的房屋空间类型
     */
    private String spaceType;
    
    /**
     * 分组名称
     */
    private String  name;
    /**
     * 副本图ids
     */
    private String designPlanRenderSceneIds;
    /**
     * 用户id
     */
    private int userId;
    
    /**
     * 副本id
     */
    private Integer sceneId;
    /**
     * 分组描述
     */
    private String  description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSceneId() {
		return sceneId;
	}
	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getDesignPlanRenderSceneIds() {
		return designPlanRenderSceneIds;
	}
	public void setDesignPlanRenderSceneIds(String designPlanRenderSceneIds) {
		this.designPlanRenderSceneIds = designPlanRenderSceneIds;
	}
	public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }
    public int getThumbId() {
        return thumbId;
    }
    public void setThumbId(int thumbId) {
        this.thumbId = thumbId;
    }
    public String getSpaceType() {
        return spaceType;
    }
    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }
    
}
