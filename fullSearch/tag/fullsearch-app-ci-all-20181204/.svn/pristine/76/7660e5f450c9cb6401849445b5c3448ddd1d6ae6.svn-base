package com.sandu.search.common.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;


//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class Utils {
    private static Logger logger = Logger.getLogger(Utils.class);

    /**
     * 调http接口 get
     *
     * @param url
     * @return
     */
    public static String doGetMethod(String url) {
        String result = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            logger.error("utlis ------ doGetMethod  ------ response : " + response);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            logger.error("doGetMethod exception : {}", e);
        }
        return result;
    }


}