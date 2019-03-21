/**
 * 
 */
package com.nork.client.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.client.model.UploadFileResponse;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesigPlanWithResult;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.product.model.GroupProductResult;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysUser;
import com.nork.system.model.small.SysUserSmall;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysUserService;

/**
 * @author yangqin
 * @Description 接口列表
 * </p>
 */
@Controller
@RequestMapping(value = "/{style}/my/client/common")
public class CommonInterfaceController {

	private final JsonDataServiceImpl<SysUser> JsonUtil = new JsonDataServiceImpl<SysUser>();

	private static final Logger LOG = LoggerFactory
			.getLogger(CommonInterfaceController.class);

	@Resource
	private SysUserService sysUserService;
	@Resource
	private GroupProductService groupProductService;
	@Resource
	private ResTextureService resTextureService;
	@Resource
	private ResFileService resFileService;
	@Resource
	private ResPicService resPicService;
	@Resource
	private BaseProductService baseProductService;
	@Resource
	private DesignTempletService designTempletService;
	@Resource
	private DesignPlanService designPlanService;

	/**
	 * @Description 获取组合列表接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCombinedList", method = RequestMethod.POST)
	@ResponseBody
	public Object getCombinedList(HttpServletRequest request,
			HttpServletResponse response) {

		LOG.info("进入获取组合列表接口......");

		String msgId = request.getParameter("msgId") == null ? ""
				: request.getParameter("msgId"); // 客户端业务ID。服务端原样返回
		String type = request.getParameter("type") == null ? ""
				: request.getParameter("type"); // 组合类型。可通过此参数过滤。
		String groupCode = request.getParameter("groupCode") == null ? ""
				: request.getParameter("groupCode"); // 组合编码
		String start = request.getParameter("start") == null ? ""
				: request.getParameter("start"); // 分页起始下标。默认值为0
		String limit = request.getParameter("limit") == null ? ""
				: request.getParameter("limit"); // 分页使用，表示每页数据量。默认值为20

		String order = request.getParameter("order") == null ? ""
				: request.getParameter("order"); // 排序字段
		String orderNum = request.getParameter("orderNum") == null ? ""
				: request.getParameter("orderNum"); // 排序规则（desc或asc）

		long totalCount = 0l;

		List<GroupProductResult> dataList = new ArrayList<GroupProductResult>();

		boolean result = true;

		String message = "调用成功";

		GroupProduct groupProduct = new GroupProduct();
		if (StringUtils.isNotEmpty(type)) {
			groupProduct.setType(Integer.valueOf(type));
		}
		if (StringUtils.isNotEmpty(limit)) {
			groupProduct.setLimit(Integer.valueOf(limit));
		}
		if (StringUtils.isNotBlank(start)) {
			groupProduct.setStart(Integer.valueOf(start));
		}
		groupProduct.setGroupCode(groupCode);
		groupProduct.setOrder(order);
		groupProduct.setOrderNum(orderNum);

		try {
			dataList = groupProductService.selectCommonList(groupProduct);
			totalCount = dataList.size();
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			result = false;
			message = "调用失败";
			e.printStackTrace();
		}

		return new ResponseEnvelope<GroupProductResult>(msgId, result, message,
				totalCount, dataList);

	}

	/**
	 * @Description 获取场景列表
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getScenariosList", method = RequestMethod.POST)
	@ResponseBody
	public Object getScenariosList(HttpServletRequest request,
			HttpServletResponse response) {
		String msgId = request.getParameter("msgId") == null ? ""
				: request.getParameter("msgId"); // 客户端业务ID。服务端原样返回
		String start = request.getParameter("start") == null ? ""
				: request.getParameter("start"); // 分页起始下标。默认值为0
		String limit = request.getParameter("limit") == null ? ""
				: request.getParameter("limit"); // 分页使用，表示每页数据量。默认值为20
		String order = request.getParameter("order") == null ? ""
				: request.getParameter("order"); // 排序字段
		String orderNum = request.getParameter("orderNum") == null ? ""
				: request.getParameter("orderNum"); // 排序规则（desc或asc）
		long totalCount = 0l;

		List<DesigPlanWithResult> dataList = new ArrayList<DesigPlanWithResult>();

		boolean result = true;

		String message = "调用成功";

		DesignPlan designPlan = new DesignPlan();

		if (StringUtils.isNotEmpty(limit)) {
			designPlan.setLimit(Integer.valueOf(limit));
		}
		if (StringUtils.isNotBlank(start)) {
			designPlan.setStart(Integer.valueOf(start));
		}
		designPlan.setOrder(order);
		designPlan.setOrderNum(orderNum);
		try {
			dataList = designPlanService.selectDesignList(designPlan);
			totalCount = dataList.size();
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
			result = false;
			message = "调用失败";
			e.printStackTrace();
		}

		return new ResponseEnvelope<DesigPlanWithResult>(msgId, result, message,
				totalCount, dataList);
	}

	/**
	 * @Description 获取产品列表
	 * </p>
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getProductList", method = RequestMethod.POST)
	@ResponseBody
	public Object getProductList(HttpServletRequest request,
			HttpServletResponse response) {

		String msgId = request.getParameter("msgId") == null ? ""
				: request.getParameter("msgId"); // 客户端业务ID。服务端原样返回
		String productType = request.getParameter("productType") == null ? ""
				: request.getParameter("productType"); // 产品大类Code
		/*String productSmallType = request.getParameter("productSmallType") == null ? ""
				: request.getParameter("productSmallType"); // 产品小类Code
*/		String brandId = request.getParameter("brandId") == null ? ""
				: request.getParameter("brandId"); // 产品品牌ID
		String productInfo = request.getParameter("productInfo") == null ? ""
				: request.getParameter("productInfo"); // 产品Code或产品名称
		String start = request.getParameter("start") == null ? ""
				: request.getParameter("start"); // 分页起始下标。默认值为0
		String limit = request.getParameter("limit") == null ? ""
				: request.getParameter("limit"); // 分页使用，表示每页数据量。默认值为20

		BaseProduct baseProduct = new BaseProduct();
		if (StringUtils.isNotEmpty(brandId)) {
			baseProduct.setBrandId(Integer.valueOf(brandId));
		}
		baseProduct.setProductType(productType);
		baseProduct.setProductName(productInfo);
		long totalCount = 0l;

		boolean result = true;

		String message = "调用成功";

		if (StringUtils.isNotEmpty(limit)) {
			baseProduct.setLimit(Integer.valueOf(limit));
		}
		if (StringUtils.isNotBlank(start)) {
			baseProduct.setStart(Integer.valueOf(start));
		}
		List<BaseProduct> dataList = baseProductService.getList(baseProduct);
		totalCount = dataList.size();
		return new ResponseEnvelope<BaseProduct>(msgId, result, message, totalCount,
				dataList);

	}

	/**
	 * 登录接口
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String userName = request.getParameter("userName") == null ? ""
				: request.getParameter("userName");
		String password = request.getParameter("password") == null ? ""
				: request.getParameter("password");
		SysUser sysUser = new SysUser();
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return new ResponseEnvelope<SysUser>(false, "未传递正确的参数!", "login");
		}
		else {
			sysUser.setAccount(userName);
			sysUser.setPassword(password);
		}

		SysUser user = sysUserService.checkUserAccount(sysUser.getAccount(),
				sysUser.getPassword());

		if (user != null) {
			String userJsonBean = JsonUtil.getBeanToJsonData(user);
			SysUserSmall sysUserSmall = new JsonDataServiceImpl<SysUserSmall>()
					.getJsonToBean(userJsonBean, SysUserSmall.class);
			return new ResponseEnvelope<SysUserSmall>(sysUserSmall, sysUser.getMsgId(),
					true);
		}
		else {
			return new ResponseEnvelope<SysUser>(false, "用户名或密码错误", sysUser.getMsgId());

		}

	}

	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param fileName
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("name") String name,
			@RequestParam("file") MultipartFile partFile) {

		String msgId = request.getParameter("msgId") == null ? ""
				: request.getParameter("msgId");

		if (partFile.isEmpty()) {
			return new ResponseEnvelope<UploadFileResponse>(false, "参数fileName不能为空！",
					msgId);
		}
		// 文件后缀，用于判断文件类型
		String fileSuffix = partFile.getOriginalFilename()
				.substring(partFile.getOriginalFilename().lastIndexOf(".") + 1);

		String fileName = UUID.randomUUID().toString();

		// 文件保存目录
		String savePath = "";
		if ("MaterialCfg".equals(fileSuffix)) {// 材质
			savePath = Utils.getPropertyName("client", "product.material.upload.path",
					"/material/");
		}
		else if ("jpg".equalsIgnoreCase(fileSuffix)
				|| "png".equalsIgnoreCase(fileSuffix)) {// 纹理
			savePath = Utils.getPropertyName("client", "texture.upload.path",
					"/texture/");
		}
		else if ("ObjectCfg".equals(fileSuffix)) {// 产品
			savePath = Utils.getPropertyName("client", "product.baseProduct.upload.path",
					"/product/baseProduct/");
		}
		else if ("ComboCfg".equals(fileSuffix)) {// 组合
			savePath = Utils.getPropertyName("client", "product.productGroup.upload.path",
					"/product/groupProduct/");
		}
		else if ("SceneCfg".equals(fileSuffix)) {// 场景
			savePath = Utils.getPropertyName("client", "scene.upload.path", "/scene/");
		}
		try {
			savePath = Utils.replaceDate(savePath);
			/*String filePath = Constants.UPLOAD_ROOT + savePath + fileName;*/
			String filePath = Utils.getAbsolutePath(savePath + fileName, Utils.getAbsolutePathType.encrypt);
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			// 保存文件
			byte[] by = partFile.getBytes();

			BufferedOutputStream output = new BufferedOutputStream(
					new FileOutputStream(file));

			output.write(by);
			output.flush();
			output.close();

			// 保存数据
			@SuppressWarnings("unchecked")
			Map<String, String> map = FileUploadUtils.getMap(file,
					"product.material.upload.path", false);
			if ("MaterialCfg".equals(fileSuffix)) {// 材质
				ResFile resFile = new ResFile();
				resFile.setFileCode(fileName.substring(0, fileName.indexOf(".")));
				resFile.setFileDesc("材质配置文件");
				resFile.setFileName(map.get(FileModel.FILE_NAME));
				resFile.setFileKey(map.get(FileModel.FILE_KEY));
				resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
				resFile.setFileSize(map.get(FileModel.FILE_SIZE));
				resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
				resFile.setFilePath(map.get(FileModel.FILE_PATH));
				sysSave(resFile, request);
				resFileService.add(resFile);
				ResTexture resTexture = new ResTexture();
				resTexture.setType("material");
				resTexture.setAtt1("material");
				resTexture.setPicId(resFile.getId());
				resTextureService.saveParamsByResFile(resTexture, resFile);
			}
			else if ("jpg".equalsIgnoreCase(fileSuffix)
					|| "png".equalsIgnoreCase(fileSuffix)) {// 纹理
				ResPic resPic = new ResPic();
				resPic.setPicCode(fileName.substring(0, fileName.indexOf(".")));
				resPic.setPicDesc("纹理图片");
				resPic.setPicName(map.get(FileModel.FILE_NAME));
				resPic.setFileKey(map.get(FileModel.FILE_KEY));
				resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME));
				resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE)));
				resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX));
				resPic.setPicPath(map.get(FileModel.FILE_PATH));
				sysSave(resPic, request);
				resPicService.add(resPic);
				ResTexture resTexture = new ResTexture();
				resTexture.setAtt1("texture");
				resTexture.setPicId(resPic.getId());
				resTextureService.saveParamsByResPic(resTexture, resPic);
			}
			else if ("ObjectCfg".equals(fileSuffix)) {// 产品
				ResFile resFile = new ResFile();
				resFile.setFileCode(fileName.substring(0, fileName.indexOf(".")));
				resFile.setFileDesc("产品配置文件");
				resFile.setFileName(map.get(FileModel.FILE_NAME));
				resFile.setFileKey(map.get(FileModel.FILE_KEY));
				resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
				resFile.setFileSize(map.get(FileModel.FILE_SIZE));
				resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
				resFile.setFilePath(map.get(FileModel.FILE_PATH));
				sysSave(resFile, request);
				resFileService.add(resFile);

				// 通过产品编码查找产品
				BaseProduct baseProduct = new BaseProduct();
				baseProduct.setProductCode(partFile.getOriginalFilename().substring(0,
						partFile.getOriginalFilename().indexOf(".")));
				List<BaseProduct> list = baseProductService.getList(baseProduct);
				if (list != null && list.size() > 0) {
					baseProduct = list.get(0);
					baseProduct.setConfigId(resFile.getId());
				}
				else {
					return new ResponseEnvelope<UploadFileResponse>(false,
							"参数fileName不能为空！", msgId);
				}
				baseProductService.update(baseProduct);
			}
			else if ("ComboCfg".equals(fileSuffix)) {// 组合

			}
			else if ("SceneCfg".equals(fileSuffix)) {// 场景

			}
		}
		catch (IOException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			UploadFileResponse uploadFileResponse = new UploadFileResponse();
			return new ResponseEnvelope<UploadFileResponse>(uploadFileResponse, msgId,
					false);
		}
		UploadFileResponse uploadFileResponse = new UploadFileResponse();
		return new ResponseEnvelope<UploadFileResponse>(uploadFileResponse, msgId, true);
	}

	/**
	 * 上传缩略图
	 * @param request
	 * @param type
	 * @param fileName
	 * @param msgId
	 * @return
	 */
	@RequestMapping(value = "/uploadThumbnail")
	@ResponseBody
	public Object uploadThumbnail(HttpServletRequest request,
			@RequestParam("file") MultipartFile filePart, String type, String msgId) {
		if (filePart.getOriginalFilename().isEmpty()) {
			return new ResponseEnvelope<UploadFileResponse>(false, "参数fileName不能为空！",
					msgId);
		}
		if (StringUtils.isBlank(type)) {
			return new ResponseEnvelope<UploadFileResponse>(false, "参数type不能为空！", msgId);
		}
		// 文件保存目录
		String savePath = "";
		if ("material".equals(type)) {// 材质
			savePath = Utils.getPropertyName("client",
					"product.material.pic.small.upload.path", "/material/pic/small/");
		}
		else if ("texture".equalsIgnoreCase(type)) {// 纹理
			savePath = Utils.getPropertyName("client", "texture.pic.small.upload.path",
					"/texture/pic/small/");
		}
		else if ("product".equals(type)) {// 产品
			savePath = Utils.getPropertyName("client",
					"product.baseProduct.pic.small.upload.path",
					"/product/baseProduct/pic/small/");
		}
		else if ("group".equals(type)) {// 组合
			savePath = Utils.getPropertyName("client",
					"product.productGroup.pic.small.upload.path",
					"/product/groupProduct/pic/small/");
		}
		else if ("scene".equals(type)) {// 场景
			savePath = Utils.getPropertyName("client", "scene.pic.small.upload.path",
					"/scene/pic/small/");
		}

		String fileName = UUID.randomUUID().toString();

		try {
			savePath = Utils.replaceDate(savePath);
			/*String filePath = Constants.UPLOAD_ROOT + savePath + fileName;*/
			String filePath = Utils.getAbsolutePath(savePath + fileName, Utils.getAbsolutePathType.encrypt);
			// 保存文件
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] by = filePart.getBytes();

			BufferedOutputStream output = new BufferedOutputStream(
					new FileOutputStream(file));
			output.write(by);
			output.flush();
			output.close();

			// 保存数据
			Map<String, String> map = FileUploadUtils.getMap(file,
					"product.material.upload.path", false);
			ResPic resPic = new ResPic();
			resPic.setPicCode(fileName.substring(0, fileName.indexOf(".")));
			resPic.setPicDesc("纹理图片");
			resPic.setPicName(map.get(FileModel.FILE_NAME));
			resPic.setFileKey(map.get(FileModel.FILE_KEY));
			resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME));
			resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE)));
			resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX));
			resPic.setPicPath(map.get(FileModel.FILE_PATH));
			sysSave(resPic, request);
			resPicService.add(resPic);

			// 回填到业务表中
			String businessCode = filePart.getOriginalFilename().substring(0,
					filePart.getOriginalFilename().lastIndexOf("."));
			if ("material".equals(type)) {// 材质
				ResTexture resTexture = new ResTexture();
				resTexture.setName(businessCode);
				List<ResTexture> list = resTextureService.getList(resTexture);
				if (list != null && list.size() > 0) {
					resTexture = list.get(0);
					resTexture.setThumbnailId(resPic.getId());
					resTextureService.update(resTexture);
				}
			}
			else if ("texture".equalsIgnoreCase(type)) {// 纹理

			}
			else if ("product".equals(type)) {// 产品
				BaseProduct baseProduct = new BaseProduct();
				baseProduct.setProductCode(businessCode);
				List<BaseProduct> list = baseProductService.getList(baseProduct);
				if (list != null && list.size() > 0) {
					baseProduct = list.get(0);
					baseProduct.setPicId(resPic.getId());
					baseProductService.update(baseProduct);
				}
			}
			else if ("group".equals(type)) {// 组合
				GroupProduct groupProduct = new GroupProduct();
				groupProduct.setGroupCode(businessCode);
				List<GroupProduct> list = groupProductService.getList(groupProduct);
				if (list != null && list.size() > 0) {
					groupProduct = list.get(0);
					groupProduct.setPicId(resPic.getId());
					groupProductService.update(groupProduct);
				}
			}
			else if ("scene".equals(type)) {// 场景
				DesignTemplet designTemplet = new DesignTemplet();
				designTemplet.setDesignCode(businessCode);
				List<DesignTemplet> list = designTempletService.getList(designTemplet);
				if (list != null && list.size() > 0) {
					designTemplet = list.get(0);
					designTemplet.setThumbnailId(resPic.getId());
					designTempletService.update(designTemplet);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return new ResponseEnvelope<UploadFileResponse>(false, "上传缩略图异常！", msgId);
		}
		return new ResponseEnvelope<UploadFileResponse>(new UploadFileResponse(), msgId,
				true);
	}
//	com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
	public ResPic sysSave(ResPic resPic, HttpServletRequest request) {
		if (resPic != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			}
			else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (resPic.getId() == null) {
				resPic.setGmtCreate(new Date());
				resPic.setCreator(loginUser.getLoginName());
				resPic.setIsDeleted(0);
				if (resPic.getSysCode() == null || "".equals(resPic.getSysCode())) {
					resPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			resPic.setGmtModified(new Date());
			resPic.setModifier(loginUser.getLoginName());
		}
		return resPic;
	}

	public ResFile sysSave(ResFile resFile, HttpServletRequest request) {
		if (resFile != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			}
			else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (resFile.getId() == null) {
				resFile.setGmtCreate(new Date());
				resFile.setCreator(loginUser.getLoginName());
				resFile.setIsDeleted(0);
				if (resFile.getSysCode() == null || "".equals(resFile.getSysCode())) {
					resFile.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_"
							+ Utils.generateRandomDigitString(6));
				}
			}

			resFile.setGmtModified(new Date());
			resFile.setModifier(loginUser.getLoginName());
		}
		return resFile;
	}

	public ResTexture sysSave(ResTexture resTexture, HttpServletRequest request) {
		if (resTexture != null) {
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			}
			else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (resTexture.getId() == null) {
				resTexture.setGmtCreate(new Date());
				resTexture.setCreator(loginUser.getLoginName());
				resTexture.setIsDeleted(0);
				if (resTexture.getSysCode() == null
						|| "".equals(resTexture.getSysCode())) {
					resTexture.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)
							+ "_" + Utils.generateRandomDigitString(6));
				}
			}

			resTexture.setGmtModified(new Date());
			resTexture.setModifier(loginUser.getLoginName());
		}
		return resTexture;
	}

}
