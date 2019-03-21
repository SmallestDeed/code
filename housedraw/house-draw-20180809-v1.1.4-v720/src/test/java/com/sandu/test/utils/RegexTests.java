package com.sandu.test.utils;

import com.sandu.util.FileType;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sandu.util.Regex;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年4月10日
 */

@Slf4j
@SpringBootTest
public class RegexTests {
	
	@Test
	public void testMobilePhone() {
		log.error(Regex.SUFFIX.getValue(FileType.OBJ.getType()));
		log.error("{}", "sdf324.obj".matches(".+\\.(?i)obj$"));
		log.error("{}", "sdf324".matches(Regex.SUFFIX.getValue(FileType.OBJ.getType())));
		log.error("{}", "sdf324.obj".matches(Regex.SUFFIX.getValue(FileType.OBJ.getType())));
		log.error("{}", "sdf324.assetbunlde".matches(Regex.SUFFIX.getValue(FileType.OBJ.getType())));
	}
}
