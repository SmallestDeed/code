package com.sandu.api.base.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 *定制企业登录配置信息
 * @date 2018/6/22  14:08
 */
@Data
public class BaseCompanyCustomLoginConfig implements Serializable {

   
    private Long id;
    
     /**
      * 企业id
      */
    private Long companyId; 
    
    /**
     * 企业名称
     */
    private String companyName;
    
    /**
     * 加密密钥
     */
    private String encryptKey;
    /**
     * 初始角色组权限,以逗号分隔
     */
    private String initRoleGroupIds;
    /**
     * 初始角色权限,以逗号分隔
     */
    private String initRoleIds;
    /**
     * 初始平台权限,以逗号分隔
     */
    private String initPlatformIds;

    /**
     * 创建人
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
     *是否删除：0未删除、1已删除
     */
    private Integer isDeleted;

}
