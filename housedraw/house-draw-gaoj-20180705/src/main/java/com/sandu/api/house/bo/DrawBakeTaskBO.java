package com.sandu.api.house.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sandu.api.house.model.DrawBakeTaskDetail;

import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/27
 */
@Data
public class DrawBakeTaskBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 房型id
	 */
	private Long houseId;

	/**
	 * 房型编码
	 */
	private String houseCode;

	/**
	 * 烘焙状态 （0：未烘焙 1：成功 2：失败）
	 */
	private Integer status;

	/**
	 * 烘焙任务结果描述
	 */
	private String message;
	
	/**
	 * base_house的Id
	 */
	private Long relHouseId;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/** 烘焙任务详情 */
	private List<DrawBakeTaskDetail> drawBakeTaskDetail;
}
