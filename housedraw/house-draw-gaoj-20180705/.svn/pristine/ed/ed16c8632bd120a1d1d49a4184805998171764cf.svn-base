package com.sandu.api.house.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Description: 户型绘制烘焙任务详情表
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/27
 */
@Data
public class DrawBakeTaskDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 任务id
	 */
	private Long taskId;

	/**
	 * 空间id
	 */
	private Long spaceId;

	/**
	 * 空间编码
	 */
	private String spaceCode;

	/**
	 * 产品信息
	 */
	private String productData;

	/**
	 * 空间文件id串
	 */
	private String spaceFileIds;

	/**
	 * 空间文件地址多文件逗号隔开
	 */
	private String spaceFilePath;
	
    /**
     * 空间烘焙状态（0：未烘焙、1：烘焙成功、2：烘焙失败；3、烘焙中；4回退）<p>
     * draw_bake_task_detail.status
     */
    private Integer status;

    private String message;

    private Date gmtModified;

    /**
     * 烘焙失败的次数
     */
    private Integer bakeFailCount;

    private Integer isDeleted;
}
