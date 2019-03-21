package com.sandu.api.servicepurchase.input.query;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServicesAccountRelQuery implements Serializable {

    /** */
    private Long id;

    /**
     * 公司Id
     */
    private String companyId;

    /**
     * 套餐id(关联services_base_info主键id)
     */
    private Long servicesId;

    /**
     * 购买ID(关联services_purchase_record主键id)
     */
    private Long purchaseRecordId;

    /**
     * 账号
     */
    private String account;

    /**
     * 账号初始密码
     */
    private String password;

    /**
     * 使用状态(0-未使用;1-使用)
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
     * 激活开始时间
     */
    private Date effectiveBegin;

    /**
     * 激活截止时间
     */
    private Date effectiveEnd;

    /**
     * 业务类型(0-购买;1-续费;2-试用)
     */
    private String businessType;

    private Integer userId;

    /**
     * 当前第几页
     */
    private Integer start;
    /**
     * 每页显示多少条
     */
    private Integer limit;

}