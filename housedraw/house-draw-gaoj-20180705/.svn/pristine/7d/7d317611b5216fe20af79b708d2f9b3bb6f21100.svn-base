package com.sandu.test.pic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.api.house.model.DrawResHousePic;
import com.sandu.api.house.service.DrawResHousePicService;
import com.sandu.common.constant.SystemConfigEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年3月22日
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResPicTests {
	
	@Autowired
	DrawResHousePicService drawResHousePicService;
	
//	@Test
	public void test() {
		try {
			DrawResHousePic drawResHousePic = drawResHousePicService.getDrawResHousePic(272L);
			drawResHousePicService.copyFile(drawResHousePic, SystemConfigEnum.DESIGN_TEMPLET_PIC_UPLOAD.getFileKey(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
