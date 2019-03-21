package com.nork.payAccount.service;

import com.nork.payAccount.model.PayAccount;
import com.nork.render.model.PayDesignPlanFree;

import java.util.Map;

public interface PayAccountService {
    Map<String,Object> handlerUserPayDubi(PayDesignPlanFree payDesignPlanFree);

    PayAccount getUserPayAccountByUserId(Long userId, String platformBussinessType);
}
