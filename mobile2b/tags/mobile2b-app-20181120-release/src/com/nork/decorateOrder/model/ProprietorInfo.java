package com.nork.decorateOrder.model;

import java.io.Serializable;
import java.util.Date;

import com.nork.decorateOrder.constant.ProprietorInfoConstants.PROPRIETORINFO_DECORATEHOUSETYPE;

import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-10-18 13:56:05.190
 */

@Data
public class ProprietorInfo implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * 自增id<p>
     * proprietor_info.id
     */
    private Integer id;

    /**
     * 类型（0：0元设计，1：装修报价）<p>
     * proprietor_info.type
     */
    private Integer type;

    /**
     * 姓名<p>
     * proprietor_info.user_name
     */
    private String userName;

    /**
     * 手机号码<p>
     * proprietor_info.mobile
     */
    private String mobile;

    /**
     * 业务类型(0-业主信息;1-设计师信息;2-设计装修公司信息)<p>
     * proprietor_info.business_type
     */
    private Integer businessType;

    /**
     * 公司类型(0-设计公司;1-装修公司)<p>
     * proprietor_info.company_type
     */
    private Integer companyType;

    /**
     * 公司名称<p>
     * proprietor_info.company_name
     */
    private String companyName;

    /**
     * 省编码<p>
     * proprietor_info.province_code
     */
    private String provinceCode;

    /**
     * 市编码<p>
     * proprietor_info.city_code
     */
    private String cityCode;

    /**
     * 小区名称<p>
     * proprietor_info.area_name
     */
    private String areaName;

    /**
     * 房屋面积(平米)<p>
     * proprietor_info.house_acreage
     */
    private String houseAcreage;

    /**
     * 来源类型（0：PC，1：小程序）<p>
     * proprietor_info.source_type
     */
    private Integer sourceType;

    /**
     * 户型<p>
     * proprietor_info.house_type
     */
    private String houseType;

    /**
     * 处理结果（0：未处理，1：已处理）<p>
     * proprietor_info.process
     */
    private Integer process;

    /**
     * 创建人<p>
     * proprietor_info.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * proprietor_info.gmt_created
     */
    private Date gmtCreated;

    /**
     * 修改人<p>
     * proprietor_info.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * proprietor_info.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除（0：未删除，1：已删除）<p>
     * proprietor_info.is_deleted
     */
    private Integer isDeleted;

    /**
     * 是否初始化数据（0：原始数据，1：新数据）<p>
     * proprietor_info.is_init
     */
    private Integer isInit;

    /**
     * 预约方案Id<p>
     * proprietor_info.designplan_id
     */
    private Long designplanId;

    /**预约方案类型（0：推荐方案；1：全屋方案）**/
    private Integer designplanType;

    /**
     * 详细地址<p>
     * proprietor_info.address
     */
    private String address;

    /**
     * 几室<p>
     * proprietor_info.bedroom_num
     */
    private Integer bedroomNum;

    /**
     * 几厅<p>
     * proprietor_info.living_room_num
     */
    private Integer livingRoomNum;

    /**
     * 几卫<p>
     * proprietor_info.toilet_num
     */
    private Integer toiletNum;

    /**
     * 装修方式(取字典类型decorateType的value)<p>
     * proprietor_info.decorate_type
     */
    private Integer decorateType;

    /**
     * 装修预算(取字典类型decorateBudget的value)<p>
     * proprietor_info.decorate_budget
     */
    private Integer decorateBudget;

    /**
     * 装修风格(base_product_style全屋方案风格ID)<p>
     * proprietor_info.decorate_style
     */
    private Integer decorateStyle;

    /**
     * 装修类型(0-新房装修;1-旧房改造)<p>
     * proprietor_info.decorate_house_type
     */
    private Integer decorateHouseType;

    /**
     * 客户类型(取字典类型customerType的value)<p>
     * proprietor_info.customer_type
     */
    private Integer customerType;

    /**
     * 回访时间<p>
     * proprietor_info.revisit_time
     */
    private Date revisitTime;

    private String remark;

    /**
     * 签约用户Id
     */
    private Long appointUserId;

    // ------非数据库字段 ->start
    
    /**
     * 客户来源
     */
	@SuppressWarnings("unused")
	private String clientSource;
    
    /**
     * 装修类型info
     * 新房装修/旧房改造
     */
    @SuppressWarnings("unused")
	private String decorateHouseTypeInfo;
    
    /**
     * 省信息
     */
    private String provinceInfo;
    
    /**
     * 市信息
     */
    private String cityInfo;
    
    /**
     * 装修风格info
     */
    private String decorateStyleInfo;
    
    /**
     * 装修方式info
     * 半包/全包...
     */
    private String decorateTypeInfo;
    
    /**
     * 预算info
     */
    private String decorateBudgetInfo;
    
    // ------非数据库字段 ->end
    
    public String getClientSource() {
    	return "平台抢单";
    }
    
    public String getDecorateHouseTypeInfo() {
    	if(this.decorateHouseType != null) {
    		for(PROPRIETORINFO_DECORATEHOUSETYPE decorateHouseTypeEnum : PROPRIETORINFO_DECORATEHOUSETYPE.values()) {
    			if(decorateHouseTypeEnum.getValue().intValue() == this.decorateHouseType.intValue()) {
    				return decorateHouseTypeEnum.getInfo();
    			}
    		}
    	}else {
    		
    	}
    	
    	return "未知装修类型";
    }
    
}