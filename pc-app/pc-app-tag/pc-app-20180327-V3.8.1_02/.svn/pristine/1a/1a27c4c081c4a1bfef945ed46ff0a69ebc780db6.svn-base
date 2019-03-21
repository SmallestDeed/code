package com.nork.product.controller2.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.product.model.CollectCatalog;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.search.CollectCatalogSearch;
import com.nork.product.service2.CollectCatalogService2;

/**
 * @Title: CollectCatalogController2.java
 * @Package com.nork.product.controller2.web
 * @Description:产品管理-收藏目录表Controller
 * @createAuthor yangzhun
 * @CreateDate 2017-6-14 14:38:44
 */
@Controller
@RequestMapping("/{style}/web/product/collectCatalog2")
public class WebCollectCatalogController2 {

	private final JsonDataServiceImpl<UserProductCollect> Json_Util = new JsonDataServiceImpl<UserProductCollect>();

	@Autowired
	private CollectCatalogService2 collectCatalogService2;

	/**
	 * 收藏目录表列表 接口
	 */
	@RequestMapping(value = "/queryCollectCatalogList")
	@ResponseBody
	public ResponseEnvelope<CollectCatalog> queryCollectCatalogList(
			@ModelAttribute("collectCatalogSearch") CollectCatalogSearch collectCatalogSearch,
			String msgId, HttpServletRequest request,
			HttpServletResponse response) {

		LoginUser loginUser = (LoginUser) SystemCommonUtil
				.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.LOGIN_OVERTIME, msgId);
		}

		return collectCatalogService2.queryCollectCatalogList(
				collectCatalogSearch, msgId, loginUser);
	}

	/**
	 * 添加收藏目录
	 * 
	 * @param request
	 * @param catalogName
	 * @return
	 */
	@RequestMapping(value = "/addCollectCatalog")
	@ResponseBody
	public ResponseEnvelope<CollectCatalog> addCollectCatalog(
			HttpServletRequest request, String catalogName, String msgId) {
		if (StringUtils.isBlank(catalogName)) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.CATALOGNAMR_NOT_NULL, msgId);
		}
		if ("默认".equals(catalogName)) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.CATALOGNAMR_CANNOT_CREATE, msgId);
		}

		LoginUser loginUser = (LoginUser) SystemCommonUtil
				.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.LOGIN_FAILURE, msgId);
		}
		CollectCatalog catalog = new CollectCatalog();
		sysSave(catalog, request);
		catalog.setCatalogName(catalogName);
		catalog.setUserId(loginUser.getId());
		return collectCatalogService2.addCollectCatalog(catalog, msgId);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(CollectCatalog model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = SystemCommonUtil
					.getLoginUserFromSession(request);

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

	/**
	 * 保存我的产品到 收藏目录
	 * 
	 * @param style
	 * @param userProductCollect
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/collecProduct")
	@ResponseBody
	public Object collecProduct(
			@PathVariable String style,
			@ModelAttribute("userProductCollect") UserProductCollect userProductCollect,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String jsonStr = Utils.getJsonStr(request);

		if (jsonStr != null && jsonStr.trim().length() > 0) {
			userProductCollect = (UserProductCollect) Json_Util.getJsonToBean(
					jsonStr, UserProductCollect.class);
			if (userProductCollect == null) {
				return new ResponseEnvelope<UserProductCollect>(false,
						SystemCommonConstant.PARAM_EXCEPTION,
						SystemCommonConstant.NONE);
			}
		}

		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		if (loginUser==null) {
			return new ResponseEnvelope<UserProductCollect>(false,
					SystemCommonConstant.LOGIN_OVERTIME,
					userProductCollect.getMsgId());
		}

		sysSave(userProductCollect, request);

		return collectCatalogService2.collecProduct(style, userProductCollect,
				loginUser);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(UserProductCollect model, HttpServletRequest request) {
		if (model != null) {
			LoginUser loginUser = SystemCommonUtil
					.getLoginUserFromSession(request);

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

	/**
	 * 删除 收藏目录(转移 产品至 默认目录 方法)
	 * 
	 * @param collectCatalog
	 * @param msgId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteCollectCatalog")
	@ResponseBody
	public ResponseEnvelope<CollectCatalog> deleteCollectCatalog(
			@ModelAttribute("collectCatalog") CollectCatalog collectCatalog,
			String msgId, HttpServletRequest request,
			HttpServletResponse response) {

		return collectCatalogService2.deleteCollectCatalog(collectCatalog,
				msgId);
	}
}
