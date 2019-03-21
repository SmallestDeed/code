package com.sandu.test.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.service.DrawDesignTempletProductService;

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
public class BaseProductTests {
	
	@Autowired
	DrawDesignTempletProductService drawDesignTempletProductService;
	
//	@Test
	public void testSoftProduct() throws RuntimeException {
		DrawBaseProductDTO baseProduct = new DrawBaseProductDTO(); 
		baseProduct.setIsChanged(2);
		baseProduct.setProductId(172317L);
		baseProduct.setSmallTypeValueKey("basic_taki");
		
		try {
//			drawDesignTempletProductService.handlerChangedSoftProduct(baseProduct, new DrawDesignTempletProduct());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		log.debug(SystemConstant.UPLOAD_ROOT);
	}
}
