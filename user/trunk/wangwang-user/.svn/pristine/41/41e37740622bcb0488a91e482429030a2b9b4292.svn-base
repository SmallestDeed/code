package com.sandu.api.system.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 银行卡信息表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-12-07 11:38:17.992
 */

@Data
public class SysUserBankcardInfo implements Serializable {
    
    /**
     * sys_user_bankcard_info.id
     */
    private Long id;

    /**
     * 姓名<p>
     * sys_user_bankcard_info.name
     */
    private String name;

    /**
     * 银行卡号<p>
     * sys_user_bankcard_info.card_number
     */
    private String cardNumber;

    /**
     * 该银行卡属于哪个银行(建行/农行...), 对应数据字典(sys_dictionary)type = issuingBank的数据的value值<p>
     * sys_user_bankcard_info.issuing_bank_value
     */
    private Integer issuingBankValue;

    /**
     * 所属支行信息<p>
     * sys_user_bankcard_info.sub_branch_info
     */
    private String subBranchInfo;

    /**
     * 用户id(是谁创建了这条银行卡数据)(关联sys_user.id)<p>
     * sys_user_bankcard_info.user_id
     */
    private Long userId;

    /**
     * 创建人<p>
     * sys_user_bankcard_info.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * sys_user_bankcard_info.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * sys_user_bankcard_info.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * sys_user_bankcard_info.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除: 0 = 未删除,1 = 已删除<p>
     * sys_user_bankcard_info.is_deleted
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}