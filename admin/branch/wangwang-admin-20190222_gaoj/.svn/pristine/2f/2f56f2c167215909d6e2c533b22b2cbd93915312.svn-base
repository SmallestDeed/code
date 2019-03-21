package com.sandu.service.brand.dao;

import com.github.pagehelper.PageHelper;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.model.bo.BrandQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BrandDaoTest {

    @Autowired
    private BrandDao brandDao;

    @Test
    public void pageQuery(){

        PageHelper.startPage(2,10);
        List<Brand> brandPagingList = brandDao.queryByOption(new BrandQuery());

        System.out.println(brandPagingList.size());
    }
}