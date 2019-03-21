package com.sandu.service;

import com.sandu.api.base.model.CompanyBrandDesc;
import com.sandu.api.base.service.BaseBrandService;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.service.base.dao.BaseCompanyDao;
import com.sandu.service.base.impl.BaseCompanyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/20
 * @since : sandu_yun_1.0
 */
public class BaseCompanyServiceMockUnitTester extends MockTester{

    @InjectMocks
    private BaseCompanyServiceImpl baseCompanyService;

    @Mock
    private BaseCompanyDao baseCompanyDao;
    @Mock
    private BaseBrandService baseBrandService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBasicScenario() {
        Mockito.when(baseCompanyDao.getCompanyBrandDesc(Long.valueOf(1003l))).thenReturn(new CompanyBrandDesc());
        CompanyBrandDesc desc = baseCompanyService.getCompanyBrandDesc("wxe24ed743feb9c17f","Y");
        assertEquals(Long.valueOf(1003), desc.getId());
    }
}
