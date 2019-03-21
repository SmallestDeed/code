package com.sandu.api.servicepurchase.serivce;

import com.sandu.api.servicepurchase.input.ServicesPurchaseRecordAdd;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.user.model.LoginUser;

import java.util.Map;

public interface ServicesAccountBuyService {

    boolean addServicesAccount(ServicesPurchaseRecordAdd servicesPurchaseRecordAdd, ServicesBaseInfo servicesBaseInfo,
                               ServicesPrice servicesPrice, Map<Long, Long> roleAndPlatfromMap, LoginUser loginUser);
}