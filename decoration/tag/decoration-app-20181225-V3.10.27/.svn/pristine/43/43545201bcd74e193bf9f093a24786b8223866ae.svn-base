package com.nork.design.controller.web;

import java.util.Date;

import com.nork.common.util.JsonUtil;
import com.nork.common.util.StringUtils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.input.DesignPlanCustomizedProductOrderModel;
import com.nork.design.service.DesignPlanService;
import com.sandu.common.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;

import com.nork.design.model.DesignPlanCustomizedProductOrder;
import com.nork.design.service.DesignPlanCustomizedProductOrderService;
import com.nork.common.model.LoginUser;

/**   
 * @Title: DesignPlanCustomizedProductOrderController.java 
 * @Package com.nork.design.controller
 * @Description:设计方案-设计方案定制产品订单表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-11-26 17:46:44
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/design/customized/order")
public class DesignPlanCustomizedProductOrderController {

	private Logger logger = LoggerFactory.getLogger(DesignPlanCustomizedProductOrderController.class);
	private static final String CLASS_LOG_PREFIX = "方案定制产品订单：";

	@Autowired
	private DesignPlanCustomizedProductOrderService designPlanCustomizedProductOrderService;
	@Autowired
	private DesignPlanService designPlanService;

	/**
	 * 保存 设计方案定制产品订单表
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@ModelAttribute("productModel") DesignPlanCustomizedProductOrderModel productOrderModel, String msgId) {
			
		if (null == productOrderModel) {
			logger.error(CLASS_LOG_PREFIX + "Param is empty!");
			return new ResponseEnvelope<DesignPlanCustomizedProductOrderModel>(false, "Param is empty!", msgId);
		}
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (null == loginUser) {
			logger.error(CLASS_LOG_PREFIX + "获取用户为空!");
			return new ResponseEnvelope<DesignPlanCustomizedProductOrderModel>(false, "请登录!", msgId);
		}
		DesignPlanCustomizedProductOrder planOrder = productOrderModel.getDesignPlanCustomizedProduct();
		try {
		    sysSave(planOrder, loginUser);
			if (planOrder.getId() == null || planOrder.getId() == 0) {
				int id = designPlanCustomizedProductOrderService.add(planOrder);
				if (id < 1) {
					return new ResponseEnvelope(false, "更新失败", msgId);
				} else {
					backfillPlanOrderId(id, planOrder.getPlanId());
				}
			} else {
				int id = designPlanCustomizedProductOrderService.update(planOrder);
				if (id < 1) {
					return new ResponseEnvelope(false, "更新失败", msgId);
				} else {
					backfillPlanOrderId(planOrder.getId(), planOrder.getPlanId());
				}
			}
		} catch (Exception e) {
			logger.error(CLASS_LOG_PREFIX + "数据异常planOrder：{}, exception:{}", JsonUtil.toJson(planOrder), e);e.printStackTrace();
			return new ResponseEnvelope<DesignPlanCustomizedProductOrder>(false, "数据异常!", msgId);
		}
		return new ResponseEnvelope<DesignPlanCustomizedProductOrder>(true, planOrder, msgId);
	}


	/**
	 * 更新相同合同号和客户排序时间
	 * @param clientName
	 * @param pactNo
	 * @param createTime
	 * @return
	 */
	private boolean updatePlanOrderData(String clientName, String pactNo, Date createTime) {

		//更新相同客户名称为最新时间，排序用
		if (!StringUtils.isEmpty(clientName)) {
			DesignPlanCustomizedProductOrder clientNameObj = new DesignPlanCustomizedProductOrder();
			clientNameObj.setClientName(clientName);
			clientNameObj.setClientNameSortTime(createTime);
			designPlanCustomizedProductOrderService.updateClientNameSortTime(clientNameObj);
		}
		//更新相同合同号为最新时间，排序用
		if (!StringUtils.isEmpty(pactNo)) {
			DesignPlanCustomizedProductOrder pactNoObj = new DesignPlanCustomizedProductOrder();
			pactNoObj.setPactNo(pactNo);
			pactNoObj.setPactNoSortTime(createTime);
			designPlanCustomizedProductOrderService.updatePactNoSortTime(pactNoObj);
		}

		return true;
	}

	/**
	 * 回填方案订单Id
	 * @param orderId
	 * @param planId
	 * @return
	 */
	private boolean backfillPlanOrderId(Integer orderId, Integer planId) {

		if (null == orderId || null == planId) {
			return  false;
		}
		DesignPlan designPlan = new DesignPlan();
		designPlan.setId(planId);
		designPlan.setOrderId(orderId);
		designPlanService.update(designPlan);
		return true;
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanCustomizedProductOrder model, LoginUser loginUser){
		if (model != null) {
			if(model.getId() == null){
				model.setCreator(loginUser.getLoginName());
				model.setGmtCreate(new Date());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
				   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
			   }
			}
			model.setGmtModified(new Date());
			model.setPactNoSortTime(new Date());
			model.setClientNameSortTime(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
}
