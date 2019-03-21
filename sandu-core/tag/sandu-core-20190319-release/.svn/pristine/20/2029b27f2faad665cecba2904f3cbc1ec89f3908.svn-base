package com.sandu.service;

import com.sandu.api.base.input.GroupPurchaseActivitySearch;
import com.sandu.api.base.model.*;
import com.sandu.api.base.service.BaseCompanyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/20
 * @since : sandu_yun_1.0
 */
public class BaseCompanyServiceUnitTest extends Tester{

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Test
    public void testBasicScenario() {
        // call method on binarySearch

//        CompanyBrandDesc desc = baseCompanyService.getCompanyBrandDesc("wxe24ed743feb9c17f","Y");


//        List<MiniProgramActBargain> data = baseCompanyService.getMiniProgramActBargainListByAppId("wxe24ed743feb9c17f");
//        System.out.println(data.size());

        GroupPurchaseActivitySearch search = new GroupPurchaseActivitySearch();
        search.setAppId("wxe24ed743feb9c17f");
        List<Integer> statusList = new ArrayList <>();
        statusList.add(0);
        statusList.add(1);
//        search.setStatusList(statusList);
        search.setStatus(2);
//        List<MiniProgramActBargain> data = baseCompanyService.getMiniProgramActBargainListByAppId("wxe24ed743feb9c17f");
        List<GroupPurchaseActivity> data = baseCompanyService.getMiniProgramGroupPurchaseActivityListByAppId(search);
        System.out.println(data.size());
//        assertEquals()
//       MiniProgramDashboard dashboard =  baseCompanyService.getMiniProgramDashboardByAppId("wxe24ed743feb9c17f");
        // check if the value is correct
//        assertEquals(Long.valueOf(1003), desc.getId());
        assertTrue(data.size()>0);

    }
}
