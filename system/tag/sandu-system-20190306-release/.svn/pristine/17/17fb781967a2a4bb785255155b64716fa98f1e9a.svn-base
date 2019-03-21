package com.sandu.api.servicepurchase.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesRoleInfoBO implements Serializable {
    /**
     * 套餐类型
     */
    private Integer serviceType;
    /**
     * 套餐临期标识
     */
    private Boolean tipsFlag = false;
    /**
     * 套餐到期标志
     */
    private Boolean maturityFlag = false;
    /**
     * 套餐角色
     */
    private Set<Long> roleIds;
    /**
     * 旧套餐标识
     */
    private Boolean oldServiceFlag = false;

    /**
     * 套餐到期剩余天数
     */
    private Integer RemainingDays;

    /**
     * 套餐名称
     */
    private String serviceName;

    /**
     * 套餐所属公司
     */
    private String companyName = "";
}

