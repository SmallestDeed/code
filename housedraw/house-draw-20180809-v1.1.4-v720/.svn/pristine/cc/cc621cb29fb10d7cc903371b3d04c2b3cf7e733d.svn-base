package com.sandu.test.draw;

import com.sandu.api.area.model.BaseArea;
import com.sandu.common.constant.bake.BakeTaskQueue;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.service.area.dao.BaseAreaMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.api.house.service.DrawBaseHouseService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class HouseTests {

	@Autowired
	DrawBaseHouseService drawBaseHouseService;

	@Autowired
	DrawBaseHouseMapper drawBaseHouseMapper;

	@Autowired
	BaseAreaMapper baseAreaMapper;

//	@Test
	public void testCallbackEmptyHouse() {
		drawBaseHouseService.handlerCallbackHouse(73L, 1L);
	}

//	@Test
	public void testSubmitEmptyHouse() {
		drawBaseHouseService.handlerSubmitHouse(73L);
	}

//	@Test
	public void testDataType() {
		log.info("################################ {}", getQueueName(21294L));
	}

	String getQueueName(Long houseId) {
		// 数据类型
		Map<String, Integer> map = drawBaseHouseMapper.getDataType(houseId);
		log.debug("添加烘焙任务处理，map => {}", map);

		Integer origin = map.get("origin");
		Integer dataType = map.get("dataType");

		// 内部户型队列
		if (Objects.isNull(dataType) || dataType.equals(DrawBaseHouseConstant.DATA_PLATFORM)) {
			return BakeTaskQueue.INTERNAL.toString();
		}

		// 毛坯房
		if (DrawBaseHouseConstant.ORIGIN_ROUGH_HOUSE.equals(origin)) {
			return BakeTaskQueue.ROUGH_HOUSE.toString();
		}

		// 普通外部队列
		return BakeTaskQueue.EXTERNAL.toString();
	}

	@Test
	public void testLivingByName() {
		String name = "澳门";
		List<BaseArea> baseAreas = baseAreaMapper.listAreaByName(name, name, name);
		log.info("{}", baseAreas);
	}
}
