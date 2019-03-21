package com.sandu.rendermachine.model.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDicitonaryOptimize implements Serializable{
	private Integer id;
	/** 类型 */
	private String type;
	/** 唯一标示 */
	private String valuekey;
	/** 值 */
	private Integer value;
	/** 名称 */
	private String name;
	/** 排序 */
	private Integer ordering;
	/** 图片id */
	private Integer picId;
	/** 图片路径 */
	private String picPath;
	/** att1 */
	private String att1;
	/** att2 */
	private String att2;
	/** 消耗时间 **/
	private String timeConsuming;

}
