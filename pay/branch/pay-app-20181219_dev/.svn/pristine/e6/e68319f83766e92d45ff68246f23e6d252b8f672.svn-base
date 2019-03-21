package com.sandu.pay.wexin.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class BaseService {
	//API的地址
    private String apiUrl;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;
    
    private boolean needCertificate=false;

    public BaseService(String apiUrl,boolean needCertificate) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.apiUrl = apiUrl;
        this.needCertificate=needCertificate;
        //Class c = Class.forName(Configure.HttpsRequestClassName);
        //serviceRequest = (IServiceRequest) c.newInstance();
        init();
    }
    
    private void init(){
    	if(needCertificate){
        	serviceRequest=new HttpsCustomRequest();
        }
        else{
        	serviceRequest=new HttpsRequest();
        }
    }

    protected String sendPost(Object xmlObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiUrl,xmlObj);
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IServiceRequest request){
        serviceRequest = request;
    }
}
