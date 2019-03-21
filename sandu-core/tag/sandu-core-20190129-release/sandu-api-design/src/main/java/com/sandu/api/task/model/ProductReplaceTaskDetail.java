package com.sandu.api.task.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 移动端产品替换详情
 * @author xiaozp
 *
 */
@Data
public class ProductReplaceTaskDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer taskId;

	private String sourceProductCode;

	private Integer destProductId;

	private String  destProductCode;

	/** 产品挂节点信息 */
	private String posIndexPath;
	//源产品ID
	private Integer sourceProductId;
	//源方案产品ID
	private Integer sourcePlanProductId;
	//源材设计方案产品材质信息
	private String sourceSplitTexturesChooseInfo;
	//替换设计方案产品材质信息[{"defaultId":19679,"name":"jianp_F03_0013_002_dyba_0004_01","key":"1","textureIds":"19680,19679,19678,19677,19676"},{"defaultId":19684,"name":"jianp_F03_0013_002_dyba_0004_02","key":"2","textureIds":"19686,19685,19688,19683,19681,19684"},{"defaultId":19692,"name":"jianp_F03_0013_002_dyba_0004_03","key":"3","textureIds":"19692,19691,19690,19689,19688,19687"}]
	private String replaceSplitTexturesChooseInfo;
	//替换材质参数[1:2]
	private String replaceSplitTexturesParam;
	//源材质信息,该参数替换产品时同时替换新产品的材质信息。
	private String sourceTexturesChooseInfo;
	//目标材质信息,该参数替换产品时同时替换新产品的材质信息。
	private String destTexturesChooseInfo;
	//替换材质参数[1:2],该参数替换产品时同时替换新产品的材质信息。
	private String replaceTexturesParam;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;

	private Integer isDeleted;
	//硬装适配参数(2:多模型硬装)
	private Integer productBatchType;

	

}
