package com.sandu.pay.alipay.util.httpClient;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:24 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/5/29 0029PM 3:24
 */
public class MyX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }

}
