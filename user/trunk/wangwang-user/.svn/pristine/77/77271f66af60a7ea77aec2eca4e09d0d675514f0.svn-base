package com.sandu.api.user.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 购买套餐时批量生成用户输入
 * @author WangHaiLin
 * @date 2018/6/1  15:27
 */
@Data
public class UserBatchAdd implements Serializable {
    /**
     * 套餐Id
     */
    private Long serviceId;

    /**
     * 公司Id
     */
    private Long companyId;
    /**
     * 角色集合
     */
    private List<Integer> roleLists;
    /**
     * 平台权限集合
     */
    private List<Integer> platformList;
    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 套餐数量
     */
    private Integer account;

    /**
     * 购买记录Id
     */
    private Long purchaseRecordId;
    
    private String mobile;
    /**
     * 使用类型：0试用，1正式
     */
    private Integer useType;

    /**
     * 用户来源
     */
    private Integer userSource;
}
