package com.sandu.pay.alipay.util.httpClient;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:40 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title: accessToken返回成功
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/29 0029PM 3:40
 */
@Data
public class AccessTokenSuccess implements Serializable{
    private static final long serialVersionUID = -788329192757159650L;

    private String access_token; //获取到的凭证

    private String expires_in; //凭证有效时间，单位：秒

}
