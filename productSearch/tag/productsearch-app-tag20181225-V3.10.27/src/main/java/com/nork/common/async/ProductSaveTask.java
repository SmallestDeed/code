package com.nork.common.async;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nork.common.model.LoginUser;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;

/***
 * 设计方案产品保存任务
 * @author qiu.jun
 * @date 2016.06.16
 *
 */
public class ProductSaveTask implements Callable<Result>{
	private static Logger logger = Logger
			.getLogger(ProductSaveTask.class);
	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private DesignPlanProductService designPlanProductService=SpringContextHolder.getBean(DesignPlanProductService.class);
	
	private ProductSaveParameter parameter;
	
	public ProductSaveTask(ProductSaveParameter parameter){
		this.parameter=parameter;
	}
	@Override
	public Result call() throws Exception {
		DesignPlanProduct designPlanProduct=parameter.getDesignPlanProduct();
		String ids = designPlanProduct.getIds();
		Integer isDelete = designPlanProduct.getIsDeleted();
		Integer planId = designPlanProduct.getPlanId();
		try {
			if (StringUtils.isNotBlank(ids) && isDelete != null && planId != null) {
				List<Integer> ints = new ArrayList<>();
				if (StringUtils.isNotBlank(ids)) {
					if (ids.indexOf(",") != -1) {
						String[] strs = ids.split(",");
						for (String str : strs) {
							if (StringUtils.isNotBlank(str))
								ints.add(Integer.parseInt(str));
						}
					} else {
						if (StringUtils.isNotBlank(ids))
							ints.add(Integer.parseInt(ids));
					}
				}
				/* 循环修改isDelete */
				if (isDelete != null) {
					for (Integer id : ints) {
						DesignPlanProduct plan = designPlanProductService.get(id);
						if (plan != null) {
							/*删除的产品为主产品时->解组*/
							if(new Integer(1).equals(plan.getIsMainProduct())&&StringUtils.isNotBlank(plan.getPlanGroupId())
									&&!StringUtils.equals("0", plan.getPlanGroupId())){
								/*是主产品->解组*/
								designPlanProductService.relieveGroupByPlanIdAndplanGroupId(plan.getPlanId(),plan.getPlanGroupId());
								logger.warn("删除主产品操作导致解组:时间:"+Utils.getTimeStr()+";设计方案id:"+plan.getDesignPlanId()+";planProductId:"+plan.getId());
							}
							/*删除的产品为主产品时->解组->end*/
							DesignPlanProduct newPlanProduct = new DesignPlanProduct();
							newPlanProduct.setId(plan.getId());
							newPlanProduct.setIsDeleted(designPlanProduct.getIsDeleted());
							newPlanProduct.setGmtModified(new Date());
							designPlanProductService.update(newPlanProduct);

						}
					}
				}
				/** 更新设计方案最后修改时间 **/
				DesignPlan designPlan = designPlanService.get(planId);
				if( designPlan != null ){
					DesignPlan newDesignPlan = new DesignPlan();
					newDesignPlan.setId(designPlan.getId());
					newDesignPlan.setIsChange(1);
					newDesignPlan.setGmtModified(new Date());
					designPlanService.update(newDesignPlan);
				}
			}
			if(isDelete == null){
				sysSave(designPlanProduct, parameter.getRequest());
				if (designPlanProduct.getId() == null) {
					int id = designPlanProductService.add(designPlanProduct);
					designPlanProduct.setId(id);
				} else {
					int id = designPlanProductService.update(designPlanProduct);
				}
			}
			if(Utils.enableRedisCache()){
//				DesignPlanProductCacher.remove(1);
//				UsedProductsCacher.remove(1);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanProduct model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils
							.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

}
