package com.sandu.api.designplan.model;

import com.sandu.api.base.common.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: DesignPlanSummaryInfo.java
 * @Package com.nork.mobile2c.model
 * @Description:移动端2C-方案点赞收藏数量表
 * @createAuthor pandajun 
 * @CreateDate 2018-01-09 10:16:03
 * @version V1.0   
 */
@Data
public class DesignPlanSummaryInfo extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  方案id  **/
	private Integer designId;
	/**  方案点赞数量  **/
	private Integer likeNum;
	/**  方案收藏数量  **/
	private Integer collectNum;
	/**  方案点赞数量  **/
	private Integer viewNum;
	/**  方案收藏数量  **/
	private Integer renderNum;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  备注  **/
	private String remark;
	/**  点赞状态（0：未点赞，1：已点赞）  **/
	private Integer isLike;
	/**  收藏状态（0：未收藏，1：已收藏）  **/
	private Integer isFavorite;

	@Override
	public String toString() {
		return "DesignPlanSummaryInfo{" +
				"id=" + id +
				", designId=" + designId +
				", likeNum=" + likeNum +
				", collectNum=" + collectNum +
				", viewNum=" + viewNum +
				", renderNum=" + renderNum +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", remark='" + remark + '\'' +
				'}';
	}
}
