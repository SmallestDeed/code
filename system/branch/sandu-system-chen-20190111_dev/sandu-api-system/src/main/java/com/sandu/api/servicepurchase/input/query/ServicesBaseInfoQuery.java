package com.sandu.api.servicepurchase.input.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * @author sandu-lipeiyuan
 */
@Data
public class ServicesBaseInfoQuery implements Serializable {

    /**
     * 套餐id
     */
    private Long id;

    /**
     *  需要过滤的套餐id
     */
    private Long noId;

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
     * 用户类型Set
     */
    private Set<String> userScopeSet;
    /**
     * 用户描述
     */
    private String serviceDesc;

    /**
     * 销售渠道
     */
    private String saleChannel;

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
     * 套餐id集
     */
    private Set<Long> ids;

    /**
     * 当前第几页
     */
    private Integer start;
    /**
     * 每页显示多少条
     */
    private Integer limit;

}
