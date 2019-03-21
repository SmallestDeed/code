package com.sandu.api.base.common.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: WXAccessTokenResponse
 * @Auther: gaoj
 * @Date: 2019/1/8 17:59
 * @Description:
 * @Version 1.0
 */
@Data
public class WXAccessTokenResponse implements Serializable {

    private String access_token;

    private String expires_in;

    private String errcode;

    private String errmsg;

}
