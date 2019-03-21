package com.sandu.api.user.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 套餐账号关联表 对应实体
 * @author WangHaiLin
 * @date 2018/6/2  16:10
 */
@Data
public class ServicesAccountRef implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 公司Id
     */
    private String companyId;
    /**
     * 套餐Id
     */
    private Long servicesId;
    /**
     * 账号
     */
    private String account;
    /**
     * 账号初始密码
     */
    private String passWord;
    /**
     * 使用状态(0-未使用;1-已使用)
     */
    private String status;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 账号开始时间
     */
    private Date effectiveBegin;

    /**
     * 账号结束时间
     */
    private Date effectiveEnd;

    /**
     * 业务类型(0-购买 1-续费)
     */
    private String businessType;
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
}
