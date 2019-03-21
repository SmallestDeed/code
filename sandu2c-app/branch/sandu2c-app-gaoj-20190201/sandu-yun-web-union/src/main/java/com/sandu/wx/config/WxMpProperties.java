package com.sandu.wx.config;

import com.sandu.gson.GsonUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * wechat mp properties
 *
 * @author weis
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;


    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }
}
