package com.sandu.api.servicepurchase.model;

import com.sandu.api.servicepurchase.input.ServicesPriceAdd;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class ServicesBaseInfo implements Serializable {
    private static final long serialVersionUID = 6358222514859124813L;
    /**
     * 套餐id
     */
    private Long id;

    /**
     * 套餐编码
     */
    private String servicesCode;

    /**
     * 套餐名称
     */
    private String servicesName;

    /**
     * 用户类型
     */
    private String userScope;

    /**
     * 用户类型名称
     */
    private String scopeName;

    /**
     * 套餐描述
     */
    private String serviceDesc;

    /**
     * 销售渠道
     */
    private String saleChannel;

    /**
     * 销售渠道名称
     */
    private String saleNames;

    /**
     * 套餐类型
     */
    private Integer servicesType;

    /**
     * 套餐备注
     */
    private String remark;

    /**
     * 套餐状态
     */
    private String servicesStatus;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改者
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
     * 赠送天数集合
     */
    private List<Integer> giveDuration;

    /**
     * 价格年限集合
     */
    private List<Integer> duration;

    /**
     * 价格集合
     */
    private List<Double> price;

    /**
     * 价格时间单元集合
     */
    private List<Integer> priceUnit;

    /**
     * 免费渲染时间集合
     */
    private List<Integer> freeRenderDuration;

    /**
     * 赠送度币集合
     */
    private List<Integer> sanduCurrency;

    /**
     * 套餐价格id集合
     */
    private List<Integer> priceId;

    /**
     * 企业id
     */
    private Integer companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 功能菜单id
     */
    private Integer funcId;

    private List<ServicesPriceAdd> prices;


}