package com.nork.product.controller.web;

import java.io.File;
import java.util.*;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.*;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.product.model.vo.BrandNameVO;
import com.nork.product.model.vo.UploadVO;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.vo.SysDictionaryVo;
import com.nork.system.service.ResPicService;
import com.sandu.common.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.collections.Lists;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.dto.BaseWaterjetTemplateDTO;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;
import com.nork.product.service.BaseWaterjetTemplateService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/{style}/web/product/baseWaterjetTemplate")
public class WebBaseWaterjetTemplateController {

	private static final Logger logger = LoggerFactory.getLogger(WebBaseWaterjetTemplateController.class);
	
	private String logPrefix = "custom:";
	
	@Autowired
	private BaseWaterjetTemplateService baseWaterjetTemplateService;

	@Autowired
	private ResPicService resPicService;

	/**
	 * 获取水刀模板列表
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public Object findAll(BaseWaterjetTemplateSearch search,HttpServletRequest request,
						  HttpServletResponse response) {
		// 参数验证 ->start
		if(search == null) {
			logger.error(logPrefix + "search = null");
			return new ResponseEnvelope<>(false, "参数错误", null);
		}
//		if(null == search.getSearchType())
//			return new ResponseEnvelope<>(false, "查询类型不能为空", null);
		// 参数验证 ->end

		LoginUser loginUser = null;
		if (SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<DesignPlan>(false, "请先登录！", "none");
		} else {
			loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "未登录，请重新登录", null);

		// 查询条件设置 ->start
		search.setIsDeleted(0);
		//处理  templateShapeValue，选择全部则传-1
		if(search.getTemplateShapeValue() != null && search.getTemplateShapeValue().equals(-1)) {
		    search.setTemplateShapeValue(null);
		}
		search.setUserType(loginUser.getUserType());
		search.setUserId(loginUser.getId());
		// 查询条件设置 ->end
		
		logger.info("查询条件 sch_TemplateName_: {},templateShapeValue:{}",search.getSch_TemplateName_(),search.getTemplateShapeValue());
		int total = baseWaterjetTemplateService.getCountBySearch(search);
		List<BaseWaterjetTemplateDTO> returnList = null;
		List<BaseWaterjetTemplate> list = null;
		if(total > 0) {
			list = baseWaterjetTemplateService.findAllBySearch(search);
		}
		
		list = baseWaterjetTemplateService.setMoreInfoForfindAll(list);
		
		returnList = this.convertToDTO(list);
		
		return new ResponseEnvelope<>(total, returnList, search.getMsgId());
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public Object addSave(BaseWaterjetTemplate baseWaterjetTemplate,String msgId,HttpServletRequest request,
						  HttpServletResponse response) {
		// 参数验证 ->start
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "search = null");
			return new ResponseEnvelope<>(false, "参数错误", msgId);
		}else{
			if(StringUtils.isBlank(baseWaterjetTemplate.getTemplateName()))
				return new ResponseEnvelope<>(false, "水刀名称不能为空", msgId);
			if(null == baseWaterjetTemplate.getTemplatePicId())
				return new ResponseEnvelope<>(false, "水刀图片不能为空", msgId);
			if(null == baseWaterjetTemplate.getCadSourceFileId())
				return new ResponseEnvelope<>(false, "水刀CAD文件不能为空", msgId);
			if(baseWaterjetTemplate.getTemplateName().length() > 50)
				return new ResponseEnvelope<>(false, "水刀名称长度限制50", msgId);
		}

		LoginUser loginUser = null;
		if (SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<>(false, "请先登录！", "none");
		} else {
			loginUser = SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "未登录，请重新登录", msgId);


		systemInfo(baseWaterjetTemplate,loginUser);
		baseWaterjetTemplate.setUserId(loginUser.getId());
		int id = baseWaterjetTemplateService.add(baseWaterjetTemplate);

		if(id > 0){
			return new ResponseEnvelope<>(true, "新增水刀模板成功！", msgId);
		}else{
			return new ResponseEnvelope<>(false, "请先登录！", msgId);
		}

	}

	@RequestMapping(value = "/brandList")
	@ResponseBody
	public Object getBrandList(String msgId,HttpServletRequest request,
						  HttpServletResponse response) {

		LoginUser loginUser = null;
		if (SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
			return new ResponseEnvelope<>(false, "请先登录！", "none");
		} else {
			loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		}
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "未登录，请重新登录", msgId);

		List<BrandNameVO> brandNameVOList = null;

		try {
			brandNameVOList = baseWaterjetTemplateService.selectBrandNameListByUser(loginUser.getId(),loginUser.getUserType());
		}catch (Exception e){
			logger.error("出现系统错误",e);
			return new ResponseEnvelope<>(false, "系统错误", msgId);
		}

		return new ResponseEnvelope<>(msgId,true, "获取用户品牌成功！",0,brandNameVOList );

	}

	@RequestMapping(value = "/templateShapeList")
	@ResponseBody
	public Object getSysDictionaryVo(String msgId,HttpServletRequest request,
						  HttpServletResponse response) {

		List<SysDictionaryVo> sysDictionaryVoList = null;

		try {
			sysDictionaryVoList = baseWaterjetTemplateService.getSysDictionaryList();
		}catch (Exception e){
			logger.error("出现系统错误",e);
			return new ResponseEnvelope<>(false, "系统错误", msgId);
		}

		return new ResponseEnvelope<>(msgId,true, "获取水刀形状成功！",0,sysDictionaryVoList );

	}

	@RequestMapping( value="/file/upload")
	@ResponseBody
	public ResponseEnvelope uploadFile(@RequestParam("msgId") String msgId,String authorization,
									  HttpServletRequest request,HttpServletResponse response) {
		UploadVO uploadVO = new UploadVO();

		//获取登录用户
		Map<String, Object> authorizationMap = Jwt.validToken(authorization);
		request.setAttribute("AuthorizationData", authorizationMap.get("data"));
		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		//参数验证
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "请重新登录", msgId);

		try {

			if (request instanceof MultipartHttpServletRequest) {

				//将HttpServletRequest对象转换为MultipartHttpServletRequest对象
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
				Collection<MultipartFile> values = fileMap.values();
				Iterator<MultipartFile> fileList = values.iterator();

				while (fileList.hasNext()){
					MultipartFile file = fileList.next();

					//文件流验证
					if (file.isEmpty() || org.apache.commons.lang3.StringUtils.isBlank(file.getOriginalFilename()))
						return new ResponseEnvelope(false, "上传文件为空",msgId);


					/** 获取上传文件信息 */
					//上传文件名
					String oldName = file.getOriginalFilename();
					//后缀
					String suffix = StringUtils.substringAfterLast(oldName, ".");
					//大小
					Long size = file.getSize();

					/** 校验 */
					if (!("dxf".contains(suffix)||"DXF".contains(suffix))) {
//					if (!("dwg，dwt，bak，dxf".contains(suffix)||"DWG，DWT，BAK，DXF".contains(suffix))) {
						return new ResponseEnvelope(false, "仅支持文件格式dxf");
//						return new ResponseEnvelope(false, "仅支持文件格式dwg、dwt、bak、dxf");
					}

					//原图路径变量
					String filePathPrefix = "";		//路径前缀
					String filePath = "";			//完整上传路径

					//构建上传路径前缀
					String fileKey = "product.baseWaterjetTemplate.cadSourceFile.upload.path";
					String defaultPath = "/AA/c_basedesign/[yyyy]/[MM]/[dd]/[HH]/product/baseWaterjetTemplate/cadSourceFile/";
					filePathPrefix = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
					filePathPrefix = Utils.replaceDate(filePathPrefix);
					filePathPrefix = Utils.getAbsolutePath(filePathPrefix, null);//这句话获取全部资源路径：在内部获取存储前缀（filePathPrefix）
					if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) )
						filePathPrefix = filePathPrefix.replace("\\", "/");
					File fileCheck = new File(filePathPrefix);//构建文件流文件夹
					if(!fileCheck.exists())
						fileCheck.mkdirs();

					//生成新的文件名称
					String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6) + "." + suffix;

					//完整路径
					filePath = filePathPrefix + fileName;

					//上传图片
					File picFile = new File(filePath);
					file.transferTo(picFile);

					//获取上传文件信息
					Map<String, String> saveFileMap = FileUploadUtils.getMap(picFile,"product.baseWaterjetTemplate.cadSourceFile.upload.path",false);

					//新增照CAD数据信息
					int resId = baseWaterjetTemplateService.saveUploadFile(saveFileMap,0,loginUser,"水刀模板CAD文件");
					if(resId < 1)
						return new ResponseEnvelope(false, "新增CAD信息错误",msgId);

					uploadVO.setId(resId+"");
					uploadVO.setName(fileName);
					uploadVO.setPath(filePath);
				}

				return new ResponseEnvelope(true, uploadVO,"上传成功",msgId);
			}

			return new ResponseEnvelope(false, "上传参数错误",msgId);

		}catch(Exception e){
			logger.error("CAD上传失败",e);
			return new ResponseEnvelope(false, "系统错误",msgId);
		}

	}


	/**
	 * 水刀上传图片
	 * @author chenqiang
	 * @param msgId
	 * @return
	 * @date 2018/8/14 0014 10:20
	 */
	@RequestMapping( value="/img/upload")
	@ResponseBody
	public ResponseEnvelope uploadImg(@RequestParam("msgId") String msgId,String authorization,
									  HttpServletRequest request,HttpServletResponse response) {
		UploadVO uploadVO = new UploadVO();

		//获取登录用户
		Map<String, Object> authorizationMap = Jwt.validToken(authorization);
		request.setAttribute("AuthorizationData", authorizationMap.get("data"));
		LoginUser loginUser = SystemCommonUtil.getLoginUserInfoByAuthData(request);
		//参数验证
		if(null == loginUser)
			return new ResponseEnvelope<>(false, "请重新登录", msgId);

		try {

			if (request instanceof MultipartHttpServletRequest) {

				//将HttpServletRequest对象转换为MultipartHttpServletRequest对象
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
				Collection<MultipartFile> values = fileMap.values();
				Iterator<MultipartFile> fileList = values.iterator();

				while (fileList.hasNext()){
					MultipartFile file = fileList.next();

					//文件流验证
					if (file.isEmpty() || org.apache.commons.lang3.StringUtils.isBlank(file.getOriginalFilename()))
						return new ResponseEnvelope(false, "上传文件为空",msgId);


					/** 获取上传图片信息 */
					//上传文件名
					String oldName = file.getOriginalFilename();
					//后缀
					String suffix = StringUtils.substringAfterLast(oldName, ".");
					//大小
					Long size = file.getSize();

					/** 校验 */
					if (!("jpg，png，jpeg".contains(suffix)||"JPG，PNG，JPEG".contains(suffix))) {
						return new ResponseEnvelope(false, "仅支持图片格式jpg、png、jpeg");
					}
					logger.error("上传图片大小="+size);
					if (size > 10*1024*1024) {
						return new ResponseEnvelope(false, "仅支持上传图片大小10M",msgId);
					}

					//原图路径变量
					String filePathPrefix = "";		//路径前缀
					String filePath = "";			//完整上传路径

					//构建上传路径前缀
					String fileKey = "product.baseWaterjetTemplate.templatePic.upload.path";
					String defaultPath = "/AA/c_basedesign/[yyyy]/[MM]/[dd]/[HH]/product/baseWaterjetTemplate/templatePic/";
					filePathPrefix = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
					filePathPrefix = Utils.replaceDate(filePathPrefix);
					filePathPrefix = Utils.getAbsolutePath(filePathPrefix, null);//这句话获取全部资源路径：在内部获取存储前缀（filePathPrefix）
					if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) )
						filePathPrefix = filePathPrefix.replace("\\", "/");
					File fileCheck = new File(filePathPrefix);//构建文件流文件夹
					if(!fileCheck.exists())
						fileCheck.mkdirs();

					//生成新的文件名称
					String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6) + "." + suffix;

					//完整路径
					filePath = filePathPrefix + fileName;

					//上传图片
					File picFile = new File(filePath);
					file.transferTo(picFile);

					//获取上传文件信息
					Map<String, String> renderPicMap = FileUploadUtils.getMap(picFile,"product.baseWaterjetTemplate.templatePic.upload.path",false);

					//新增照原图数据信息
					int resId = baseWaterjetTemplateService.saveUploadImgPic(renderPicMap,0,loginUser,"水刀模板图片");
					if(resId < 1)
						return new ResponseEnvelope(false, "新增图片信息错误",msgId);

					//生成缩略图
					ResPic resPic = resPicService.get(resId);
					resPicService.createThumbnailNew(resPic,request);

					uploadVO.setId(resId+"");
					uploadVO.setName(fileName);
					uploadVO.setPath(filePath);
				}

				return new ResponseEnvelope(true,uploadVO, "上传成功",msgId);
			}

			return new ResponseEnvelope(false, "上传参数错误",msgId);

		}catch(Exception e){
			logger.error("图片上传失败",e);
			return new ResponseEnvelope(false, "系统错误",msgId);
		}

	}


	private List<BaseWaterjetTemplateDTO> convertToDTO(List<BaseWaterjetTemplate> list) {
		if(Lists.isEmpty(list)) {
			return null;
		}
		List<BaseWaterjetTemplateDTO> baseWaterjetTemplateDTOList = new ArrayList<BaseWaterjetTemplateDTO>();
		list.forEach(item -> {
			BaseWaterjetTemplateDTO baseWaterjetTemplateDTO = this.convertToDTO(item);
			baseWaterjetTemplateDTOList.add(baseWaterjetTemplateDTO);
		});
		return baseWaterjetTemplateDTOList;
	}

	private BaseWaterjetTemplateDTO convertToDTO(BaseWaterjetTemplate item) {
		// 参数验证 ->start
		if(item == null) {
			return null;
		}
		// 参数验证 ->end
		
		BaseWaterjetTemplateDTO baseWaterjetTemplateDTO = new BaseWaterjetTemplateDTO();
		baseWaterjetTemplateDTO.setId(item.getId().intValue());
		baseWaterjetTemplateDTO.setTemplateFileUrl(item.getTemplateFileUrl());
		baseWaterjetTemplateDTO.setTemplateLength(item.getTemplateLength());
		baseWaterjetTemplateDTO.setTemplateName(item.getTemplateName());
		baseWaterjetTemplateDTO.setTemplatePicUrl(item.getTemplatePicUrl());
		baseWaterjetTemplateDTO.setTemplateWidth(item.getTemplateWidth());
		baseWaterjetTemplateDTO.setCadSourceFilePath(item.getCadSourceFilePath());
		return baseWaterjetTemplateDTO;
	}

	private void systemInfo(BaseWaterjetTemplate basewaterjet,LoginUser loginUser){

		if(basewaterjet.getId() == null){
			basewaterjet.setTemplateCode("W");
			basewaterjet.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
			basewaterjet.setCreator(loginUser.getLoginName());
			basewaterjet.setGmtCreate(new Date());
		}

		basewaterjet.setModifier(loginUser.getLoginName());
		basewaterjet.setGmtModified(new Date());
	}
	
}
