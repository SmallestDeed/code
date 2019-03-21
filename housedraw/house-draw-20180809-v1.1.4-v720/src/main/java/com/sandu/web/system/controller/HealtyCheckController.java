package com.sandu.web.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.common.BaseController;

@RestController
@RequestMapping("/running")
public class HealtyCheckController extends BaseController {
	@RequestMapping("/check")
	public int check(HttpServletRequest request) {
		 
		return 200;
	} 
}
