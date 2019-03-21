package com.sandu.api.system.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysDictionary implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long id;
    
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
	/**  类型  **/
	private String type;
	/**  唯一标示  **/
	private String valuekey;
	/**  值  **/
	private Integer value;
	
	private String appType;
	/** 字符串类型值**/
	private String  contactValue;
	
	private String timeConsuming;

	private String bigValuekey;

	private Integer bigValue;

	
	/**  名称  **/
	private String name;
	/**  排序  **/
	private Integer ordering;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  字符备用3  **/
	private String att3;
	/**  字符备用4  **/
	private String att4;
	/**  字符备用5  **/
	private String att5;
	/**  字符备用6  **/
	private String att6;
	/**  字符备用6  **/
	private String att7;
	/**  时间备用1  **/
	private Date dateAtt1;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  整数备用1  **/
	private Integer numAtt1;
	/**  整数备用2  **/
	private Long picId;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	/*att1~att6说明信息*/
	private String att1Info;
	private String att2Info;
	private String att3Info;
	private String att4Info;
	private String att5Info;
	private String att6Info;
	private String att7Info;
	
	/* 是否显示U3D模型 0不显示,1显示 */
	private Integer showU3dModel;

	//关联图片的path
	private String picPath;
	


}
