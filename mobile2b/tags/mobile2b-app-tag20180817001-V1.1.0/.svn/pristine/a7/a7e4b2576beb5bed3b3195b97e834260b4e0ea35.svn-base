package com.nork.common.pano.share;

import com.nork.common.util.Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/17.
 */
public class Sign {

    public static Map<String,String> sign(String jsapi_ticket, String url){
        Map<String, String> ret = new HashMap<>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        StringBuilder string1 = new StringBuilder();
        String signature = "";

        string1.append("jsapi_ticket=").append(jsapi_ticket)
                .append("&noncestr=").append(nonce_str)
                .append("&timestamp=").append(timestamp)
                .append("&url=").append(url);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.toString().getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", Utils.getPropertyName("config/share","wx.share.appid",""));
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
