package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.user.model.SysUser;

import java.io.Serializable;

@Data
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgId = null;

    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    private Integer mediaType;

    private String terminalImei;

    private String usbTerminalImei;
    
    private String serverType;

    /**
     * 户型绘制工具 =>版本号
     */
    private String version;

    /**
     * 客户端IP
     **/
    private String clientIp;

    /**
     * 登录设备
     **/
    private String loginDevice;

    /**
     * 操作系统型号
     **/
    private String systemModel;

    /**
     * 登录平台
     **/
    @ApiModelProperty(value = "平台编码", required = true)
    private String platformCode;
    
    /**
     * 选择登录企业id
     */
    private Long loginCompanyId;
    
    /**
     * 小程序用户登录id(每个微信用户对于小程序而言都有唯一不变的openId)
     */
    private String openid;
    
    /**
     * 企业微信小程序appId
     */
    private String appid;
    
    /**
     * 针对定制企业登录的企业编码
     */
    private String companyCode;
    
    /**
     * 企业登录登录时间戳
     */
    private Long timestamp;
    
    /**
     * 企业登录md5签名
     */
    private String sign;
    
    public Integer getPlatformType() {
    	if(BasePlatform.PLATFORM_CODE_MOBILE_2B.equals(platformCode) 
    			|| BasePlatform.PLATFORM_CODE_PC_2B.equals(platformCode) 
    			|| BasePlatform.PLATFORM_CODE_MERCHANT_MANAGE.equals(platformCode)
    			|| BasePlatform.PLATFORM_CODE_PC_HOUSE_DRAW.equals(platformCode)
    			|| BasePlatform.PLATFORM_CODE_U3D_PLUGIN_WIN.equals(platformCode)
    			|| BasePlatform.PLATFORM_CODE_CITY_UNIOM.equals(platformCode)
    			|| BasePlatform.PLATFORM_CODE_PC_2B_CUSTOM.equals(platformCode)
    			|| BasePlatform.PLATFORM_CODE_SANDU_MANAGER_NEW.equals(platformCode)
    			|| BasePlatform.PLATFORM_CODE_OPERATION_PLATFORM.equals(platformCode)
        ) {
    		return SysUser.PLATFORM_TYPE_B; //B端用户
    	}else {
    		return SysUser.PLATFORM_TYPE_C; //c端用户
    	}
    }
    private Long platformId;
}
