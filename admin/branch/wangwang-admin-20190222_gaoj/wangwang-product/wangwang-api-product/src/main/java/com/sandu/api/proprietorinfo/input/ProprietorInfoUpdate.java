package com.sandu.api.proprietorinfo.input;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProprietorInfoUpdate implements Serializable {
    /**
     * 业主信息ID
     */
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不正确")
    private Integer id;
    /**
     * 客户姓名
     */
    @NotBlank(message = "客户姓名不能为空")
    private String userName;
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String mobile;
    /**
     * 省编码
     */
    @NotBlank(message = "省份不能为空")
    private String provinceCode;
    /**
     * 市编码
     */
    @NotBlank(message = "城市不能为空")
    private String cityCode;
    /**
     * 小区
     */
    private String houseEstate;
    /**
     * 详细地址
     */
    private String address;
    /**
     * N室
     */
    private Byte bedroomNum;
    /**
     * N厅
     */
    private Byte livingRoomNum;
    /**
     * N卫
     */
    private Byte toiletNum;
    /**
     * 户型面积
     */
    private String houseAcreage;
    /**
     * 装修方式(取字典类型decorateType的value)
     */
    private Integer decorateTypeValue;
    /**
     * 装修预算(取字典类型decorateBudget的value)
     */
    private Integer decorateBudgetValue;
    /**
     * 风格
     */
    private Integer goodStyleValue;
    /**
     * 装修类型(0-新房装修;1-旧房改造)
     */
    private Integer decorateHouseTypeKey;
    /**
     * 客户类型(取字典类型customerType的value)
     */
    private Integer customerTypeValue;
    /**
     * 备注
     */
    private String remark;
    /**
     * 下次回访时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextTime;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gmtModified;
}
