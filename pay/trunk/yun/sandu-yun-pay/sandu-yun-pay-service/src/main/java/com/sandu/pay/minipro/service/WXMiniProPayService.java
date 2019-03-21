package com.sandu.pay.minipro.service;

import com.sandu.pay.minipro.model.WXMiniPayInfoModel;
import com.sandu.pay.order.model.ResultMessage;

import javax.xml.transform.Result;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/2
 * @since : sandu_yun_1.0
 */
public interface WXMiniProPayService {
    public ResultMessage callWXUnifiedorderService(WXMiniPayInfoModel payInfo); //Send the call WXAPI to create order;

    public Object callBackAfterWXMiniPay();//call back method

    public Object reSignParam();

    public Object updatePayStatus();

    public Object getPayStatus();

}
