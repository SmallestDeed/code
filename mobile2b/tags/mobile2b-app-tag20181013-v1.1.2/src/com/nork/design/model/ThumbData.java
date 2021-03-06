/**    
 * 文件名：ThumbData.java    
 *    
 * 版本信息：    
 * 日期：2017-7-18    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 项目名称：timeSpace 类名称：ThumbData 缩略图显示列表返回数据分装 类描述： 创建人：Timy.Liu 创建时间：2017-7-18
 * 下午2:12:58 修改人：Timy.Liu 修改时间：2017-7-18 下午2:12:58 修改备注：
 * 
 * @version
 * 
 */
public class ThumbData implements Serializable {

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = -4358560132436951228L;
    
    /**
     * 审核失败原因
     */
    private String  failCause;
    /**
     * 缩略图id
     */
    private int thumbId;

    /**
     * 缩略图封面
     */
    private String pic;

    /**
     * 缩略图对应方案的名称
     */
    private String name;

    /**
     * 创建时间
     */
    private String ctime;

    /**
     * 空间类型
     */
    private String type;

    /**
     * 面积
     */
    private String area;
    /**
     * 一键发布状态
     */
    private int oneKeySt;
    /**
     * 公开状态
     */
    private int pubSt;
    /**
     * 对应的设计方案id
     */
    private Long planId;

    /** 每页显示多少条记录 */
    private int pageSize;

    /** 开始记录数 */
    private int start;
    /**
     * 创建人id
     */
    private long userId;
    /**
     * 设计方案副本id
     */
    private long cpId;

    /**
     * 组的分享次数
     */
    private int shareTimes;

    /**
     * 副本id
     */
    private int sceneId;
    /**
     * 副本ids  用于in 查询
     */
    private List<Integer>sceneIds = new ArrayList<Integer>();

    /**
     * 业务 business id（分组bid）
     */
    private String bid;

    private int picNum;
    
    private String description;
    
    private String checkUserName;
    //移动端需要根据这四个字段判断是否有渲染过这四种,true:有，false：无;默认没有渲染过
    private Boolean renderPic = false;
    private Boolean render720 = false;
    private Boolean renderRoam = false;
    private Boolean renderVideo = false;
    /**全屋方案720 UUID**/
    private String fullHousePlanUUID;
    /**方案空间类型 1：单空间方案，2：全屋方案**/
    private Integer planHouseType;
    private Integer renderState;

    public Integer getRenderState() {
        return renderState;
    }

    public void setRenderState(Integer renderState) {
        this.renderState = renderState;
    }

    public Integer getPlanHouseType() {
        return planHouseType;
    }

    public void setPlanHouseType(Integer planHouseType) {
        this.planHouseType = planHouseType;
    }
    public String getFullHousePlanUUID() {
        return fullHousePlanUUID;
    }

    public void setFullHousePlanUUID(String fullHousePlanUUID) {
        this.fullHousePlanUUID = fullHousePlanUUID;
    }

    public Boolean getRenderPic() {
		return renderPic;
	}

	public void setRenderPic(Boolean renderPic) {
		this.renderPic = renderPic;
	}

	public Boolean getRender720() {
		return render720;
	}

	public void setRender720(Boolean render720) {
		this.render720 = render720;
	}

	public Boolean getRenderRoam() {
		return renderRoam;
	}

	public void setRenderRoam(Boolean renderRoam) {
		this.renderRoam = renderRoam;
	}

	public Boolean getRenderVideo() {
		return renderVideo;
	}

	public void setRenderVideo(Boolean renderVideo) {
		this.renderVideo = renderVideo;
	}


    private String spacecode;
    
    public String getSpacecode() {
		return spacecode;
	}

	public void setSpacecode(String spacecode) {
		this.spacecode = spacecode;
	}

	public String getCheckUserName() {

		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPicNum() {
		return picNum;
	}

	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}

	public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public List<Integer> getSceneIds() {
		return sceneIds;
	}

	public void setSceneIds(List<Integer> sceneIds) {
		this.sceneIds = sceneIds;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

    public int getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(int shareTimes) {
        this.shareTimes = shareTimes;
    }

    public int getThumbId() {
        return thumbId;
    }

    public void setThumbId(int thumbId) {
        this.thumbId = thumbId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public long getCpId() {
        return cpId;
    }

    public void setCpId(long cpId) {
        this.cpId = cpId;
    }

    public int getOneKeySt() {
        return oneKeySt;
    }

    public void setOneKeySt(int oneKeySt) {
        this.oneKeySt = oneKeySt;
    }

    public int getPubSt() {
        return pubSt;
    }

    public void setPubSt(int pubSt) {
        this.pubSt = pubSt;
    }

    /**
     * 风格
     */
    private Integer designStyleId;
	public Integer getDesignStyleId() {
		return designStyleId;
	}

	public void setDesignStyleId(Integer designStyleId) {
		this.designStyleId = designStyleId;
	}

}
