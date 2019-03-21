package com.nork.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.nork.product.model.PrepProductPropsInfo;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.ProductStatuCode;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.PrepProductPropsInfoService;
import com.nork.product.service.PrepProductSearchInfoService;

public class PrepareProductSearchMainInfoJob implements Callable<Boolean> {
	
	
	private static Logger logger = Logger
			.getLogger(PrepareProductSearchMainInfoJob.class);
	
	private PrepProductSearchInfoService prepProductSearchInfoService;
	
	private List<PrepProductSearchInfo> productList;
	
	private PrepProductPropsInfoService prepProductPropsInfoService;
	
	private BaseProductService baseProductService;
	
	private List<PrepProductPropsInfo> prepProductPropsInfoList;
	
	private String jobName;
	public PrepareProductSearchMainInfoJob(PrepProductSearchInfoService prepService, List<PrepProductSearchInfo> searchList, PrepProductPropsInfoService prepPropsService, List<PrepProductPropsInfo> propsList, String name,BaseProductService productService) {
		this.prepProductSearchInfoService = prepService;
		this.productList = searchList;
		this.prepProductPropsInfoService = prepPropsService;
		this.prepProductPropsInfoList = propsList;
		this.jobName = name;
		this.baseProductService = productService;
	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		long begin = Calendar.getInstance().getTimeInMillis();
		logger.error("Begin call the job this.productList.size=" + this.productList.size() + "this.prepProductPropsInfoList" + this.prepProductPropsInfoList.size());
		Boolean result = true;
		List <Integer>productIdList = new ArrayList<Integer>();
		HashSet <Integer>productset = new HashSet<Integer>();
		if(this.productList.size() > 0) {
			prepProductSearchInfoService.batchSavePrepSearchList(this.productList);
			for(PrepProductSearchInfo info : this.productList) {
				productset.add(info.getProductId());
			}
		}
		for(Integer proId :productset) {
			productIdList.add(proId);
		}
		if(this.prepProductPropsInfoList.size() > 0) {
			prepProductPropsInfoService.batchSave(this.prepProductPropsInfoList);
		}
		baseProductService.updateProductStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_RELEASE,productIdList);
		long period = Calendar.getInstance().getTimeInMillis() - begin;
		logger.error("end call succes of jobName" + this.jobName + "=>period=" + period);
		return result;
	}

}
