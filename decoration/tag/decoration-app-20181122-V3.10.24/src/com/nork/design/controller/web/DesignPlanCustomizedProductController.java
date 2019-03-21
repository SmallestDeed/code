package com.nork.design.controller.web;

import java.util.*;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.MessageUtil;
import com.nork.common.util.StringUtils;
import com.nork.design.model.input.DesignPlanCustomizedProductModel;
import com.nork.design.model.output.DesignPlanCustomizedProductVo;
import com.nork.design.service.DesignPlanService;
import com.sandu.common.LoginContext;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;

import com.nork.design.model.DesignPlanCustomizedProduct;
import com.nork.design.model.search.DesignPlanCustomizedProductSearch;
import com.nork.design.service.DesignPlanCustomizedProductService;
import com.nork.common.model.LoginUser;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**   
 * @Title: DesignPlanCustomizedProductController.java 
 * @Package com.nork.design.controller
 * @Description:设计方案-设计方案定制产品表Controller
 * @createAuthor pandajun 
 * @CreateDate 2018-08-28 11:04:09
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/design/customized")
public class DesignPlanCustomizedProductController {

	private static Logger logger = Logger.getLogger(DesignPlanCustomizedProductController.class);

	@Autowired
	private DesignPlanCustomizedProductService designPlanCustomizedProductService;
	@Autowired
	private DesignPlanService designPlanService;


	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@ModelAttribute("productModel") DesignPlanCustomizedProductModel productModel,
					   String msgId, String authorization, HttpServletRequest request) throws Exception{

		//参数校验
		MessageUtil messageUtil = paramVerify(productModel);
		if (!messageUtil.isStauts()) {
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, messageUtil.getMessage(), msgId);
		}

		LoginUser loginUser = null;
		if (!StringUtils.isEmpty(authorization)) {
			// 验证登录参数 ->start
			loginUser = SystemCommonUtil.checkLoginUserIsValidByToken(authorization);
			if (loginUser == null) {
				return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "登录超时,请重新登录!", msgId);
			}
		} else {
			logger.error("authorization is null");
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "authorization is null!", msgId);
		}

		DesignPlanCustomizedProduct customizedProduct = productModel.getDesignPlanCustomizedProduct();
		DesignPlanCustomizedProductVo customizedProductVo = null;

		//上传定制产品模型文件
		if (request instanceof MultipartHttpServletRequest) {
			// 获取文件列表并将物理文件保存到服务器中
			// 将HttpServletRequest对象转换为MultipartHttpServletRequest对象
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			if (null == fileMap || 1 > fileMap.size()) {
				return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "模型文件不能为空！", msgId);
			}
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				Map map = new HashMap(3);
				map.put(FileUploadUtils.UPLOADPATHTKEY, "alpha.customized.product.model.upload.path");
				map.put(FileUploadUtils.FILE, mf);
				boolean flag = FileUploadUtils.saveModelFile(map);
				if (flag) {
					customizedProduct.setModelUrl(map.get(FileUploadUtils.DB_FILE_PATH) + "");
					break;
				} else {
					return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "保存模型文件失败！", msgId);
				}
			}
		}
		//填充系统字段
		sysSave(customizedProduct, loginUser);
		String type = "保存";
		try {
			int id = 0 ;
			if (null == customizedProduct.getId()) {
				id = designPlanCustomizedProductService.add(customizedProduct);
			} else {
				id = designPlanCustomizedProductService.update(customizedProduct);
				type = "更新";
			}
			if (id == 0) {
				return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, type + "定制产品失败！", msgId);
			} else {
				customizedProductVo = new DesignPlanCustomizedProductVo(customizedProduct);
			}
		} catch (Exception e) {
			logger.error(type + "定制产品异常！e = " + e);
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, type + "定制产品异常!", msgId);
		}
		return new ResponseEnvelope<DesignPlanCustomizedProductVo>(customizedProductVo, msgId, true);
	}


	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(Integer planCustomizedProductId, String msgId) {

		if (null == planCustomizedProductId || 1 > planCustomizedProductId) {
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "Param is empty!", msgId);
		}

		try {
			DesignPlanCustomizedProduct customizedProduct = designPlanCustomizedProductService.get(planCustomizedProductId);
			if (null != customizedProduct) {
				String modelUrl = customizedProduct.getModelUrl();
				//物理删除资源
				if (!StringUtils.isEmpty(modelUrl)) {
					FileUploadUtils.deleteFile(modelUrl);
				}
				int i = designPlanCustomizedProductService.delete(planCustomizedProductId);
				if (i == 0) {
					return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "记录不存在!", msgId);
				}
			}
		} catch (Exception e) {
			logger.error("删除方案地址产品异常！e = " + e);
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "删除失败!", msgId);
		}
		return new ResponseEnvelope<DesignPlanCustomizedProduct>(true, msgId, true);
	}


	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(Integer planId, String msgId) {

		if (null == planId || 1 > planId) {
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "Param is empty!", msgId);
		}
		/*********** 设置查询条件 ************/
		DesignPlanCustomizedProductSearch search = new DesignPlanCustomizedProductSearch();
		search.setPlanId(planId);
		search.setIsDeleted(0);
		search.setLimit(-1);
		search.setStart(-1);

		List<com.nork.design.model.output.DesignPlanCustomizedProductVo> voList = new ArrayList<> ();
		int total = 0;
		try {
			logger.info("查询条件 planId : " + planId);
			total = designPlanCustomizedProductService.getCount(search);
            logger.info("total:" + total);
			List<DesignPlanCustomizedProduct> list = new ArrayList<> ();
			if (total > 0) {
				list = designPlanCustomizedProductService.getPaginatedList(search);
			}
			for(DesignPlanCustomizedProduct customizedProduct : list) {
				DesignPlanCustomizedProductVo customizedProductVo = new DesignPlanCustomizedProductVo(customizedProduct);
				voList.add(customizedProductVo);
			}
		} catch (Exception e) {
			logger.error("查询方案定制产品异常, e = " + e);
			return new ResponseEnvelope<DesignPlanCustomizedProduct>(false, "查询方案定制产品数据异常!", msgId);
		}
		return new ResponseEnvelope<>(total, voList, msgId);
	}

	/**
	 * 更新设计方案定制产品订单编码
	 *
	 * @param planId    方案ID
	 * @param orderCode	订单编码
	 * @return
	 */
	@RequestMapping(value = "/updatePlanCustomizedProductOrderCode")
	@ResponseBody
	public Object updatePlanCustomizedProductOrderCode(Integer planId, String orderCode, String msgId, HttpServletRequest request) {
		if (planId == null || StringUtils.isEmpty(orderCode)) {
			return new ResponseEnvelope<>(false, "param is empty！", msgId);
		}
		//获取登录信息
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		//保存逻辑 客户端先保存定制产品订单号才能发送请求第三方平台产品库
		MessageUtil messageUtil = designPlanService.updatePlanCustomizedProductOrderCode(planId, orderCode, loginUser);
		if (!messageUtil.isStauts()) {
			return new ResponseEnvelope<>(false, messageUtil.getMessage(), msgId);
		}
		return new ResponseEnvelope<>(true, "更新成功", msgId);
	}

	/**
	 * 更新设计方案定制产品配置文件
	 *
	 * @param planId    				方案ID
	 * @param customizedProductContext	配置文件内容
	 * @return
	 */
	@RequestMapping(value = "/updatePlanCustomizedProductConfig")
	@ResponseBody
	public Object updatePlanCustomizedProductConfig(Integer planId, String customizedProductContext, String msgId, HttpServletRequest request) {
		if (planId == null || planId == 0) {
			return new ResponseEnvelope<>(false, "planId is empty！", msgId);
		}
		//获取登录信息
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

		//保存逻辑
		MessageUtil messageUtil = designPlanService.updatePlanCustomizedProductConfig(planId,customizedProductContext,loginUser);
		if (!messageUtil.isStauts()) {
			return new ResponseEnvelope<>(false, messageUtil.getMessage(), msgId);
		}
		return new ResponseEnvelope<>(true, "更新成功", msgId);
	}


	/**
	 * 验证新增参数
	 * @param productAdd
	 * @return
	 */
	private MessageUtil paramVerify(DesignPlanCustomizedProductModel productAdd) {
		if (null == productAdd) {
			return new MessageUtil(false, "Param obj is empty!");
		}
		if (null == productAdd.getPlanId() || 1 > productAdd.getPlanId()) {
			return new MessageUtil(false, "Param planId is empty!");
		}
		if (null == productAdd.getExteriorProductId() || 1 > productAdd.getExteriorProductId()) {
			return new MessageUtil(false, "Param exteriorProductId is empty!");
		}
		return new MessageUtil(true);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanCustomizedProduct model, LoginUser loginUser){
		if (model != null) {
			if (null == loginUser) {
				loginUser = new LoginUser();
				loginUser.setLoginName("nologin");
			}
			if (null == model.getId()) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode()==null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
}
