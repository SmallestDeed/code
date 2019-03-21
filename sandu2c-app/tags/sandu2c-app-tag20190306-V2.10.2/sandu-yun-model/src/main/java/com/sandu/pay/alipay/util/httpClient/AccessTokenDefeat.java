package com.sandu.pay.alipay.util.httpClient;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:43 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title: AccessToken失败
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/29 0029PM 3:43
 */
@Data
public class AccessTokenDefeat implements Serializable{

    private String errcode; //返回编码

    private String errmsg; //返回错误信息
}
