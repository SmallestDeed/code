/**    
 * 文件名：SysuserGroup.java    
 *    
 * 版本信息：    
 * 日期：2017-7-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.system.model;

import java.io.Serializable;
import java.util.Date;

/**    
 *     
 * 项目名称：timeSpace    
 * 类名称：SysuserGroup     系统用户自定义分组
 * 类描述：    
 * 创建人：Timy.Liu   
 * 创建时间：2017-7-6 上午10:30:18    
 * 修改人：Timy.Liu    
 * 修改时间：2017-7-6 上午10:30:18    
 * 修改备注：    
 * @version     
 *     
 */
public class SysUserGroup implements Serializable {
    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = -962523722991483840L;
    /**
     * 数据库id
     */
    private int id;
    /**
     * 业务 business id
     */
    private String bid;
    /**
     * 分组名称
     */
    private String name;
    
    /**
     * 分组创建者id
     */
    private int  userId;
    /**
     * 分组类型，设计方案分组 为0
     */
    private int type;
    /**
     * v
     */
    private Date ctime;
    /**
     * 修改时间
     */
    private Date utime;
    
    /**
     * 描述
     */
    private String description;
    /** 每页显示多少条记录 */
    private int pageSize;

    /** 开始记录数 */
    private int start;
    
    private int sceneId;
    /**
     * 分享次数
     */
    private int shareTimes;
    
    public int getSceneId() {
        return sceneId;
    }
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Date getCtime() {
        return ctime;
    }
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    public Date getUtime() {
        return utime;
    }
    public void setUtime(Date utime) {
        this.utime = utime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getShareTimes() {
        return shareTimes;
    }
    public void setShareTimes(int shareTimes) {
        this.shareTimes = shareTimes;
    }
    
}
