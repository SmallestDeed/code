package com.sandu.api.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserLoginAggregated implements Serializable {

   private long id;

    /**
     * 用户id
     */
   private Long userId;

    /**
     * 平台id
     */
   private Long platformId;

    /**
     * 用户登录总次数
     */
   private Integer loginCount;

    /**
     * 系统编码
     */
   private String sysCode;

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
     * 是否删除
     */
   private Integer isDeleted;
}
