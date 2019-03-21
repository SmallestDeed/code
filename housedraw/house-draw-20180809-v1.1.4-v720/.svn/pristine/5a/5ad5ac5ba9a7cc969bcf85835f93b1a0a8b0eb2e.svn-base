package com.sandu.test;

import com.sandu.api.house.service.*;
import com.sandu.api.product.service.DrawBaseProductService;
import com.sandu.api.res.service.ResPicService;
import com.sandu.service.living.dao.BaseLivingMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import com.sandu.service.pic.dao.DrawResHousePicMapper;
import com.sandu.service.pic.dao.ResPicMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018年2月6日
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DrawBaseHouseTests {

    public static final Logger LOG = LoggerFactory.getLogger(DrawBaseHouseTests.class);

    @Autowired
    DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    BaseHouseMapper baseHouseMapper;

    @Autowired
    DrawResHousePicMapper drawResHousePicMapper;

    @Autowired
    DrawBakeTaskService drawBakeTaskService;

    @Autowired
    DrawBakeTaskDetailService drawBakeTaskDetailService;

    @Autowired
    DrawBaseHouseService drawBaseHouseService;

    @Autowired
    DrawBaseProductService drawBaseProductService;

    @Autowired
    ResPicService resPicService;

    @Autowired
    ResPicMapper resPicMapper;

    @Autowired
    BaseLivingService baseLivingService;

    @Autowired
    BaseLivingMapper baseLivingMapper;

    @Test
    public void testAddress() {
        // baseLivingService.getDetailAddress("110000", "110100", "110101");
        // baseLivingService.getDetailAddress("110000", "110100", "110101");

        // baseLivingMapper.getDetailAddress("110000", "110100", "110101");
        // baseLivingMapper.getDetailAddress("110000", "110100", "110101");
    }

    @Test
    public void testBakeHandler() {
        // drawBaseHouseService.handlerSubmitHouse(340L);
    }

    @Test
    public void testRestTask() {
        // drawBakeTaskService.restTask();
    }
}
