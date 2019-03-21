package com.sandu.pay.alipay.util.httpClient;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 2:55 2018/9/3 0003
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/9/3 0003PM 2:55
 */
@Data
public class TicketSuccess implements Serializable{


/*    {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm
        3sUw==","expire_seconds":60,"url":"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI"}
    参数	说明
        ticket	获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
        expire_seconds	该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
        url	二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片*/

private String ticket;
private String expireSeconds;
private String url;


}
