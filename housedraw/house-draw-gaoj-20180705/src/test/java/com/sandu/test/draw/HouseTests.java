package com.sandu.test.draw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.api.house.service.DrawBaseHouseService;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年3月24日
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class HouseTests {

	@Autowired
	DrawBaseHouseService drawBaseHouseService;

//	@Test
	public void testCallbackEmptyHouse() {
		drawBaseHouseService.handlerCallbackHouse(73L, 1L);
	}

//	@Test
	public void testSubmitEmptyHouse() {
		drawBaseHouseService.handlerSubmitHouse(73L);
	}
}
