package com.sandu.design.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class DesignRulesPO implements Serializable {

	private static final long serialVersionUID = -5869860756459653200L;
	
	private Integer id;
	
	/**
	 * 规则类型
	 */
	private String rulesType;
	
	/**
	 * 规则业务值
	 */
	private String rulesBusiness;
	
	/**
	 * 规则标识
	 */
	private String rulesSign;
	
	/**
	 * 规则级别
	 */
	private String rulesLevel;
	
	/**
	 * 规则主体
	 */
	private String rulesMainObj;
	
	/**
	 * 规则主体值
	 */
	private String rulesMainValue;
	
	/**
	 * 规则次体
	 */
	private String rulesSecondaryObjs;
	
	/**
	 * 规则次体值集合
	 */
	private String rulesSecondaryValues;
	
	/**
	 * 规则优先级
	 */
	private String rulesPriority;
	
	/**
	 * 规则排序
	 */
	private String rulesOrdering;
	
	/**
	 * 规则模式1
	 */
	private String ext1;
	
	/**
	 * 扩展2
	 */
	private String ext2;
	
	/**
	 * 扩展3
	 */
	private String ext3;
	
	/**
	 * 扩展4
	 */
	private String ext4;
	
	/**
	 * 扩展5
	 */
	private String ext5;
	
	/**
	 * 扩展6
	 */
	private String ext6;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
	/**
	 * 创建者
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
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 字符备用1
	 */
	private String att1;
	
	/**
	 * 字符备用2
	 */
	private String att2;
	
	/**
	 * 整数备用1
	 */
	private Integer numa1;
	
	/**
	 * 整数备用2
	 */
	private Integer numa2;
	
	/**
	 * 备注
	 */
	private String remark;
	
}
