package com.sandu.test.utils;

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
		log.debug("{}", "sdf324".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "sdf12345678".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "1233348912".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "12345678912".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "23145678912".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "123334568912".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "1233345678912".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "01234567891".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "213sdf21656".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "2313sdf324".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "@`2313sdf324".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "123456898^&".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "2313sd;'4".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "02313sdf324".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "02313sdf324".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("=========================================================");
		log.debug("{}", "13756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "14756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "15756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "16756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "17756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "18756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "19756489634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("=========================================================");
		log.debug("{}", "f197sjy9634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "1s7Ffff49634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "19756481149634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "197569634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "-13447569634".matches(Regex.MOBILE_PHONE.getValue()));
		log.debug("{}", "+19711569634".matches(Regex.MOBILE_PHONE.getValue()));
	}
}
