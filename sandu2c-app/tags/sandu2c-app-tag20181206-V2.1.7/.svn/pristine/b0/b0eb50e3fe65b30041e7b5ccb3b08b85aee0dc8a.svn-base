package com.sandu.web.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.model.CollectionQueryModel;
import com.sandu.product.model.UserProductCollect;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.user.service.UserSessionService;

/**
 * 产品收藏
 */
@Controller
@RequestMapping("/v1/tocmobile/product/productfavorite")
public class ProductFavoriteController {
	private final static Gson gson = new Gson();
	private final static String CLASS_LOG_PREFIX = "[产品收藏服务]:";
	private final static Logger logger = LoggerFactory.getLogger(ProductFavoriteController.class);

	@Autowired
	private UserProductCollectService userProductCollectService;



	/**
	 * 产品收藏列表-----接口
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping("/collectionlist")
	@ResponseBody
	public ResponseEnvelope collectionList(@ModelAttribute PageModel pageModel, Integer isSort,
			String productCategoryCode, HttpServletRequest request) {

		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.",
				null == loginUser ? null : loginUser.toString());
		if (loginUser == null) {
			return new ResponseEnvelope(false, "请登录!");
		}

		CollectionQueryModel model = new CollectionQueryModel();
		model.setIsSort(null == isSort ? 0 : isSort);
		model.setStart(pageModel.getStart());
		model.setPageSize(pageModel.getLimit());
		model.setLoginUser(loginUser);
		model.setProductCategoryCode(null == productCategoryCode ? null : productCategoryCode);
		ResponseEnvelope res = userProductCollectService.collectionList(model);
		logger.info(CLASS_LOG_PREFIX + "响应给客户端的数据:{}", gson.toJson(res));
		return res;
	}

	/**
	 * 产品收藏添加-----接口
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/collecproduct")
	@ResponseBody
	public ResponseEnvelope collecProduct(@ModelAttribute("userProductCollect") UserProductCollect userProductCollect,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.",
				null == loginUser ? null : loginUser.toString());
		if (loginUser == null) {
			return new ResponseEnvelope(false, "请登录!");
		}

		// LoginUser loginUser = UserObject.parseUserSoToLoginUser(userSo);
		userProductCollect.setUserId(loginUser.getId());
		if (userProductCollect.getProductId() == null) {
			return new ResponseEnvelope(false, "参数productId不能为空");
		}
		ResponseEnvelope res = userProductCollectService.collecProduct(userProductCollect, loginUser);
		logger.info(CLASS_LOG_PREFIX + "响应给客户端的数据:{}", gson.toJson(res));
		return res;
	}

	/**
	 * 产品收藏删除-----接口
	 *
	 * @param style
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deluserproduct")
	@ResponseBody
	public Object delUserProduct(@ModelAttribute("userProductCollect") UserProductCollect userProductCollect,
			HttpServletRequest request) {
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.",
				null == loginUser ? null : loginUser.toString());
		if (loginUser == null) {
			return new ResponseEnvelope(false, "请登录!");
		}

		userProductCollect.setUserId(loginUser.getId());
		if (userProductCollect.getProductId() == null) {
			return new ResponseEnvelope(false, "参数productId不能为空！");
		}
		List<UserProductCollect> list = userProductCollectService.getList(userProductCollect);
		logger.info(CLASS_LOG_PREFIX + "用户收藏该产品的记录:{}", gson.toJson(list));
		if (list != null && !list.isEmpty()) {
			int flag = userProductCollectService.delete(list.get(0).getId());
			if (flag == 1) {
				return new ResponseEnvelope(true, "删除成功");
			} else {
				return new ResponseEnvelope(false, "数据删除失败!");
			}
		} else {
			return new ResponseEnvelope(false, "该记录不存在或已被删除!");
		}
	}

}
