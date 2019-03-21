package com.sandu.api.system.model;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserLoginLog implements Serializable{

    private Long id;

    /**用户Id*/
    private Long userId;

    /**登录类型*/
    private Date loginTime;

    /**登录登出时间*/
    private Integer loginType;

    /**客户端IP*/
    private String clientIp;

    /**服务器IP*/
    private String serverIp;

    /**登录设备*/
    private String loginDevice;

    /**操作系统型号*/
    private String systemModel;

    /**系统编码*/
    private String sysCode;

    /**创建者*/
    private String creator;

    /**创建时间*/
    private Date gmtCreate;

    /**修改人*/
    private String modifier;

    /**修改时间*/
    private Date gmtModified;

    /**是否删除*/
    private Integer isDeleted;

    /**字符备用1*/
    private String att1;

    /**字符备用2*/
    private String att2;

    /**整数备用1*/
    private Integer numa1;

    /**整数备用2*/
    private Integer numa2;

    /**备注*/
    private String remark;

    /**平台id*/
    private Long platformId;

}
