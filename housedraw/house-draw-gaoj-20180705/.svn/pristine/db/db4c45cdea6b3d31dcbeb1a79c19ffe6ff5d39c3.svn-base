package com.sandu.api.house.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * 烘焙任务实体
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/27
 */
@Data
public class DrawBakeTask implements Serializable{

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

    /**
     * 队列名称:不同渲染机对应不同队列.</p>
     * internal : 内部   external : 外部
     */
    private String queueName;

    /**
     * 优先级:请参考java线程优先级,1为最小 5为一般 10为最大
     */
    private Integer priority;
}
