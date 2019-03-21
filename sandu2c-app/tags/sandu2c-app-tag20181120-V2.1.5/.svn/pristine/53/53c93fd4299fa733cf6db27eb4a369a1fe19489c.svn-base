package com.sandu.base;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:15 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @author weisheng
 * @Title: 二维码
 * @Package
 * @Description:
 * @date 2018/5/29 0029PM 6:15
 */
@Data
public class QRCode implements Serializable {
    private static final long serialVersionUID = 3888327091399732591L;

    private String QRUrl; //获取微信二维码API

    private String scene;  //场景业务内容

    private String page; //跳转页面,默认首页

    private int width; //二维码的宽度,默认值430

    private Boolean autoColor; //自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调.默认值false

    private String lineColor; //auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示,默认值{"r":"0","g":"0","b":"0"}

    private Boolean isHyaline; //是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码,默认值false
}
