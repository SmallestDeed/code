package com.sandu.web.house.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.sandu.api.v2.house.dto.DrawBaseHouseCallbackDTO;
import com.sandu.api.v2.house.service.DrawBaseHouseServiceV2;
import com.sandu.exception.BizException;
import com.sandu.exception.DrawBusinessException;
import com.sandu.util.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.bo.DrawBaseProductBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseCheck;
import com.sandu.api.house.input.DrawBaseHouseSearch;
import com.sandu.api.house.input.PublicProductInfoDTO;
import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawResFile;
import com.sandu.api.house.output.DrawBakeTaskDetailVO;
import com.sandu.api.house.output.DrawBakeTaskVO;
import com.sandu.api.house.output.DrawBaseHouseSubmitVO;
import com.sandu.api.house.output.DrawBaseHouseVO;
import com.sandu.api.house.output.DrawHouseAndAreaVO;
import com.sandu.api.house.output.DrawProductDataVO;
import com.sandu.api.house.service.DrawBakeTaskService;
import com.sandu.api.house.service.DrawBaseHouseService;
import com.sandu.api.house.service.DrawDesignTempletService;
import com.sandu.api.house.service.DrawResFileService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.house.DrawBaseProductConstant;
import com.sandu.exception.AppException;
import com.sandu.exception.BusinessException;
import com.sandu.util.ExceptionUtils;
import com.sandu.util.Regex;
import com.sandu.util.auth.HouseAuthUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * </p>
 * Company:Sandu Copyright:Copyright(c)2017 Description:
 *
 * @author 何文
 * @version 1.0
 * 
 * @date 2017/12/28
 */

@Slf4j
@RestController
@RequestMapping("/v1/draw/house")
@Api(tags = "绘制户型", description = "DrawBaseHouseController")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DrawBaseHouseController extends BaseController {

	@Autowired
	private DrawBaseHouseService drawBaseHouseService;

	@Autowired
	private DrawBaseHouseServiceV2 drawBaseHouseServiceV2;

	@Autowired
	private DrawBakeTaskService drawBakeTaskService;

	@Autowired
	private DrawDesignTempletService drawDesignTempletService;

	@Autowired
	private DrawResFileService drawResFileService;

	@Autowired
	ObjectMapper objectMapper;

	@Value("${resource.domain}")
	private String resourceDomain;

	/**
	 * 请求示例：http://127.0.0.1:9898/v1/draw/house/list
	 * </p>
	 * 户型列表及户型审核列表
	 *
	 * @param query
	 * @param request
	 * @return
	 */
	@PostMapping("/list")
	@ApiOperation(value = "查询户型列表及户型审核列表", response = DrawBaseHouseVO.class)
 	public ReturnData listDrawHouse(DrawBaseHouseSearch query, HttpServletRequest request) {
		ReturnData response = setMsgId(request);
		Objects.requireNonNull(query, "参数不能为空");
		query.setPageSize(super.getLimit(query.getPageSize()));
		query.setPageNum(super.getPage(query.getPageNum(), query.getPageSize()));
		Map<String, Object> map = drawBaseHouseService.listDrawHouse(query);
		// 转换vo返回
		List<DrawBaseHouseVO> drawBaseHouseVOS = adapterDrawBaseHouseVOS(((List<DrawBaseHouseBO>) map.get("list")));
		return response.list(drawBaseHouseVOS).total((long) map.get("total"));
	}

	private List<DrawBaseHouseVO> adapterDrawBaseHouseVOS(List<DrawBaseHouseBO> list) {
		List<DrawBaseHouseVO> drawBaseHouseVOS = new ArrayList<>();
		for (DrawBaseHouseBO bo : list) {
			DrawBaseHouseVO vo = new DrawBaseHouseVO();
			vo.setHouseCode(bo.getHouseCode());
			vo.setHouseId(bo.getId());
			vo.setHouseName(bo.getHouseName());
			vo.setHousePic(bo.getPicPath());
			vo.setLastUpdateTime(bo.getGmtModified());
			vo.setLivingName(bo.getLivingName());
			vo.setRestoreFile(bo.getFilePath());
			vo.setDetailAddress(bo.getDetailAddress());
			vo.setHouseShortName(bo.getHouseShortName());
			vo.setUserId(bo.getUserId());
			vo.setTotalArea(bo.getTotalArea());
			vo.setIsPublic(bo.getIsPublic());
			vo.setCheckOwnHouse(bo.getCheckOwnHouse());
			vo.setDealStatus(bo.getDealStatus());
			vo.setIsStandard(bo.getIsStandard());
			vo.setLivingId(bo.getLivingId());
			vo.setSnapshotPic(bo.getSnapshotPic());
			vo.setUserName(bo.getUserName());
			vo.setUserMobile(bo.getUserMobile());
			vo.setHouseStatus(bo.getHouseStatus());
			vo.setRejectReason(bo.getRejectReason());
			vo.setPlatformType(bo.getPlatformType());
			drawBaseHouseVOS.add(vo);
		}

		return drawBaseHouseVOS;
	}

	/**
	 * 请求示例：http://127.0.0.1:9898/v1/draw/house/save
	 * </p>
	 * 户型保存接口
	 *
	 * @param request
	 * @param data
	 * @return
	 */
	@PostMapping("/save")
	@ApiOperation(value = "户型保存")
	public ReturnData saveDrawBaseHouse(HttpServletRequest request, String userId, String data) {
		ReturnData response = setMsgId(request);
//		Map<String, Object> map = drawBaseHouseService.saveDrawBaseHouse(data, userId);
		// v2版本的保存户型，添加了异常时，删除资源文件处理功能
		Map<String, Object> map = drawBaseHouseService.saveDrawHouseV2(data, userId);
		return response.data(map);
	}

	/**
	 * 请求示例：http://127.0.0.1:9898/v1/draw/house/delete
	 * </p>
	 * 户型删除接口
	 *
	 * @param request
	 * @param houseId
	 * @return
	 */
	@PostMapping("/delete")
	@ApiOperation(value = "户型删除")
	public ReturnData deleteDrawBaseHouse(HttpServletRequest request, Long houseId, Long userId) {
		// TODO 获取用户
		ReturnData response = setMsgId(request);
		drawBaseHouseService.deleteDrawBaseHouse(houseId, userId);
		return response;
	}

	/**
	 * 请求示例：http://127.0.0.1:9898/v1/draw/house/check
	 * <p>
	 * 户型审核接口
	 *
	 * @param check
	 * @param request
	 * @return
	 */
	@PostMapping("/check")
	@ApiOperation(value = "户型审核接口")
	public ReturnData checkBaseHouse(DrawBaseHouseCheck check, HttpServletRequest request) {
		ReturnData response = setMsgId(request);
		drawBaseHouseService.checkDrawBaseHouse(check);
		return response;

	}

    /**
     * 请求示例：http://127.0.0.1:9898/v1/draw/house/executeBakeTask
     * <p>
     * 烘焙任务列表查询
     *
     * @param queueName
     * @param taskId
     * @param request
     * @return
     */
    @PostMapping("/executeBakeTask")
    @ApiOperation(value = "执行烘焙任务", response = DrawBakeTaskVO.class)
    public ReturnData executeBakeTask(String queueName, Long taskId, HttpServletRequest request) {
        ReturnData returnData = setMsgId(request);
        List<DrawBakeTaskBO> list = drawBakeTaskService.listBakeTask(queueName, taskId);
        returnData.setResourceUrl(resourceDomain);

        return returnData.data(getDrawBakeTaskVO(list));
    }

	@PostMapping("/getBakeTaskId")
	@ApiOperation("获取烘焙任务id")
	public ReturnData getBakeTaskId(String queueName, String msgId) {
		ReturnData returnData = ReturnData.builder();
		returnData.msgId(msgId);

		if (StringUtils.isEmpty(queueName)) {
			returnData.setStatus(false);
			returnData.message("队列名称不能为空!!!");
			return returnData;
		}

		Long bakeTaskId = drawBakeTaskService.getBakeTaskId(queueName);

		if (bakeTaskId != null) {
			returnData.setStatus(true);
			DrawBakeTaskVO b = new DrawBakeTaskVO();
			b.setTaskId(bakeTaskId);
			returnData.setData(b);
			return returnData;
		} else {
			returnData.status(true);
			returnData.setData(null);
			returnData.setMessage("暂时没有烘焙任务");
			return returnData;
		}
	}

	private DrawBakeTaskVO getDrawBakeTaskVO(List<DrawBakeTaskBO> list) throws BusinessException {
		// 返回页面数据对象
		DrawBakeTaskVO taskVO = new DrawBakeTaskVO();

		// json
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, PublicProductInfoDTO.class);

		list.parallelStream().forEach(task -> {
			// 任务数据
			taskVO.setTaskId(task.getId());
			taskVO.setHouseId(task.getHouseId());

			// 任务详情数据
			List<DrawBakeTaskDetail> taskDetails = task.getDrawBakeTaskDetail();

			if (taskDetails != null && !taskDetails.isEmpty()) {
				DrawBakeTaskDetail detail = taskDetails.get(0);
				DrawBakeTaskDetailVO detailVO = new DrawBakeTaskDetailVO();
				detailVO.setTaskDetailId(detail.getId());
				// 空间
				detailVO.setSpaceId(detail.getSpaceId());
				detailVO.setSpaceCode(detail.getSpaceCode());
				detailVO.setSpaceFileId(Long.parseLong(detail.getSpaceFileIds()));
				detailVO.setSpaceFilePath(detail.getSpaceFilePath());

				// 产品view对象
				List<DrawProductDataVO> drawProductDataVOS = getDrawProductDataVOS(detail);

				// public产品对象(栏杆/窗户等) ->start
				List<DrawDesignTemplet> drawDesignTempletList = drawDesignTempletService.findAllBySpaceCommonId2(detail.getSpaceId());
				if (drawDesignTempletList == null || drawDesignTempletList.size() == 0) {
					log.error("draw_样板房信息没有找到,drawSpaceCommonId = {}", detail.getSpaceId());
				} else {
					DrawDesignTemplet drawDesignTemplet = drawDesignTempletList.get(0);
					if (StringUtils.isNotEmpty(drawDesignTemplet.getPublicProductInfo())) {
						List<PublicProductInfoDTO> publicProductInfoDTOList = JSON.parse(drawDesignTemplet.getPublicProductInfo(), javaType);
						if (publicProductInfoDTOList != null && publicProductInfoDTOList.size() > 0) {
							for (PublicProductInfoDTO publicProductInfoDTO : publicProductInfoDTOList) {
								DrawResFile drawResFile = drawResFileService.get(publicProductInfoDTO.getFileId());
								if (drawResFile == null) {
									log.error("draw_res_file数据没有找到,id = {}", publicProductInfoDTO.getFileId());
									continue;
								}
								DrawProductDataVO drawProductDataVO = new DrawProductDataVO();
								drawProductDataVO.setFileId(publicProductInfoDTO.getFileId());
								drawProductDataVO.setFileName(drawResFile.getFileName());
								drawProductDataVO.setFilePath(drawResFile.getFilePath());
								drawProductDataVO.setUniqueId(publicProductInfoDTO.getUniqueId());
								drawProductDataVO.setModelType(DrawBaseProductConstant.MODEL_TYPE_PUBLIC);
								drawProductDataVOS.add(drawProductDataVO);
							}
						}
					}
				}
				// public产品对象(栏杆/窗户等) ->end

				detailVO.setProductData(drawProductDataVOS);

				taskVO.setTaskDetail(detailVO);
			}
		});

		return taskVO;
	}

	private List<DrawProductDataVO> getDrawProductDataVOS(DrawBakeTaskDetail detail) {
		ObjectMapper objectMapper = new ObjectMapper();
		String data = detail.getProductData();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, DrawBaseProductBO.class);
		List<DrawBaseProductBO> drawBaseProductBOS;

		try {
			drawBaseProductBOS = objectMapper.readValue(data, javaType);
		} catch (IOException e) {
			throw new BizException(e);
		}

		List<DrawProductDataVO> drawProductDataVOS = new ArrayList<>();
		if (drawBaseProductBOS != null && !drawBaseProductBOS.isEmpty()) {
			for (DrawBaseProductBO drawBaseProductBO : drawBaseProductBOS) {
				DrawProductDataVO drawProductDataVO = new DrawProductDataVO();
				drawProductDataVO.setProductId(drawBaseProductBO.getId());
				drawProductDataVO.setProductCode(drawBaseProductBO.getProductCode());
				drawProductDataVO.setFileId(drawBaseProductBO.getFileId());
				drawProductDataVO.setFileName(drawBaseProductBO.getFileName());
				drawProductDataVO.setFilePath(drawBaseProductBO.getFilePath());
				drawProductDataVO.setUniqueId(drawBaseProductBO.getUniqueId());
				drawProductDataVO.setModelType(DrawBaseProductConstant.MODEL_TYPE_HARD);
				drawProductDataVOS.add(drawProductDataVO);
			}
		}

		return drawProductDataVOS;
	}

	/**
	 * 烘培任务回调
	 * 
	 * @author huangsongbo
	 * @return
	 */
//	@PostMapping("/callback")
//	public ReturnData callback(String jsonStr, HttpServletRequest request) {
//		ReturnData<DrawBaseHouseSubmitVO> returnData = this.setMsgId(request);
//
//		// 参数验证 ->start
//		if (StringUtils.isEmpty(jsonStr)) {
//			return returnData.status(false).code(ResponseEnum.PARAM_ERROR).message("参数jsonStr不能为空");
//		}
//		// 参数验证 ->end
//		try {
//			Map<String, Object> msgMap = drawBaseHouseService.callback(jsonStr);
//			returnData.message(Objects.toString(msgMap.get("msg")));
//		} catch (Exception e) {
//			log.error("处理烘焙任务异常, JsonStr => {}", jsonStr, e);
//			return handlerBakeFail(returnData, jsonStr, e);
//		}
//
//		return returnData.status(true);
//	}

	@PostMapping("/callback")
	@ApiOperation(value = "v2-烘焙回调")
	public ReturnData callback(String msgId, @RequestParam("jsonStr") String data) {
		long startup = System.currentTimeMillis();
		ReturnData response = setMsgId(msgId);
	//	log.error("处理烘焙参数:"+jsonStr);
		if (StringUtils.isEmpty(data)) {
			return response.status(false).code(ResponseEnum.PARAM_ERROR);
		}

		DrawBaseHouseCallbackDTO dtoNew = null;
		try {
			dtoNew = objectMapper.readValue(data, DrawBaseHouseCallbackDTO.class);
			if (dtoNew == null) {
				throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR);
			}

			// handler bake task
			String message = drawBaseHouseService.callbackV2(dtoNew);
			return response.message(Objects.toString(message, "未知的错误!"));
		} catch (Exception e) {
			log.error("处理烘焙任务异常, data => {}", data, e);
			// handler error task
			return handlerCallbackError(response, dtoNew, data, e);
		} finally {
			log.info("#################### 处理烘焙任务耗时：{}(ms) ####################", System.currentTimeMillis() - startup);
		}
	}

	/**
	 * 处理烘焙失败的任务
	 *
	 * @param response
	 * @param dtoNew
	 * @param data
	 * @param e
	 * @return
	 */
	private ReturnData handlerCallbackError(ReturnData response, DrawBaseHouseCallbackDTO dtoNew,
											String data, Exception e) {
		try {
			if (dtoNew == null) {
				dtoNew = objectMapper.readValue(data, DrawBaseHouseCallbackDTO.class);
			}

			dtoNew.setMessage(ExceptionUtils.getStackTrace(e));
			// handler callback error
			drawBaseHouseServiceV2.handlerCallbackError(dtoNew, data);
		} catch (Exception ex) {
			log.error("ERROR--ERROR--ERROR--回滚烘焙任务异常--ERROR--ERROR--ERROR", ex);
		}

		// handler app exception
		if (e instanceof AppException) {
			AppException ex = (AppException) e;
			return response.status(false).code(ex.getResponseEnum());
		}

		// handler exception
		return response.status(false).message(Objects.toString(e.getMessage(), "未知的错误!"));
	}
	
	/**
	 * 处理烘焙失败
	 * 
	 * @param returnData
	 * @param data
	 * @param e
	 * @return
	 */
	private ReturnData handlerBakeFail(ReturnData returnData, String data, Exception e) {
		String errorMessage = getErrorMessage(e);
		try {
			// 回滚处理烘焙任务失败
			drawBaseHouseService.handlerBakeTaskFailed(data, errorMessage);
			if (e instanceof AppException) {
				AppException ex = (AppException) e;
				return returnData.status(false).code(ex.getResponseEnum());
			}
		} catch (IOException ex) {
			log.error("回滚处理失败的烘焙任务异常-ERROR-ERROR", e);
		}

		return returnData.status(false).message(Objects.toString(e.getMessage(), ResponseEnum.ERROR.getMessage()));
	}
	
	/**
	 * 获取异常信息
	 * 
	 * @param ex
	 * @return
	 */
	private String getErrorMessage(Exception ex) {
		String stackTrace = ExceptionUtils.getStackTrace(ex);
		if (ex instanceof AppException) {
			AppException exception = (AppException) ex;
			String message = exception.getResponseEnum().getMessage();
			return StringUtils.isEmpty(message) ? stackTrace : message + "\n" + stackTrace;
		}

		return stackTrace;
	}

//    /**
//     * 保存户型绘制所有数据 涉及到的流程:
//     * <pre>
//     *     1.新建空间
//     *     2.新建产品数据
//     *     3.新建样板房
//     *     4.新建样板房产品表
//     * </pre>
//     *
//     * @param jsonStr
//     * @param request
//     * @return
//     * @author huangsongbo
//     */
//	@PostMapping("/submit")
//	public ReturnData<DrawBaseHouseSubmitVO> submit(String jsonStr, HttpServletRequest request) {
//		ReturnData<DrawBaseHouseSubmitVO> returnData = ReturnData.builder();
//
//		// 参数验证 ->start
//		if (StringUtils.isEmpty(jsonStr)) {
//			return returnData.status(false).code(ResponseEnum.PARAM_ERROR).message("参数jsonStr不能为空");
//		}
//		// 参数验证 ->end
//
//		returnData = setMsgId(request);
//		UserLoginBO userLoginBO = HouseAuthUtils.getUserLoginBO();
//		DrawBaseHouseSubmitDTO data = drawBaseHouseService.submit(jsonStr, userLoginBO);
//
//		return returnData.status(true).data(data);
//	}

	/**
	 * 保存户型绘制所有数据 涉及到的流程:
	 * <pre>
	 *     1.新建空间
	 *     2.新建产品数据
	 *     3.新建样板房
	 *     4.新建样板房产品表
	 * </pre>
	 *
	 * @param jsonStr
	 * @param request
	 * @return
	 * @author huangsongbo
	 */
	@PostMapping("/submit")
	public ReturnData<DrawBaseHouseSubmitVO> submit(String jsonStr, HttpServletRequest request) {
		long startup = System.currentTimeMillis();

		ReturnData returnData = ReturnData.builder();
		if (StringUtils.isEmpty(jsonStr)) {
			return returnData.status(false).code(ResponseEnum.PARAM_ERROR).message("参数jsonStr不能为空");
		}

		returnData = setMsgId(request);
		drawBaseHouseService.submitV2(jsonStr);

		log.info("#################### 处理提交户型耗时：{}(ms) ####################", System.currentTimeMillis() - startup);

		return returnData;
	}
	
	/**
	 * 普通绘制者 上传户型
	 * @author songjianming@sanduspace.com
	 * @param houseId
	 * @param request
	 * @return
	 */
	@PostMapping("/uploadHouse")
	public ReturnData uploadHouse(Long houseId, HttpServletRequest request) {
		ReturnData response = setMsgId(request);
		if (null == houseId) {
			return response.status(false).code(ResponseEnum.PARAM_ERROR).message("houseId参数为空");
		}
		drawBaseHouseService.uploadHouse(houseId);
		return response;
	}
	
	/**
	 * 完善户型
	 * @author songjianming@sanduspace.com
	 * @param houseId
	 * @param request
	 * @return
	 */
	@PostMapping("/perfectHouse")
	public ReturnData perfectHouse(Long houseId, HttpServletRequest request) {
		ReturnData response = ReturnData.builder();
		if (null == houseId) {
			return response.status(false).code(ResponseEnum.PARAM_ERROR).message("houseId参数为空");
		}
		response = setMsgId(request);
		DrawHouseAndAreaVO drawHouse = drawBaseHouseService.perfectHouse(houseId);
		return response.data(drawHouse);
	}

	@PostMapping("/searchList")
	@ApiOperation(value = "用户绘制时查询户型可供临摹", response = DrawBaseHouseVO.class)
	public ReturnData searchHouse(DrawBaseHouseSearch query, HttpServletRequest request) {
		ReturnData response = ReturnData.builder();
		query = setUserId(query);
		response = setMsgId(request);
		Map<String, Object> map = drawBaseHouseService.getHouses(query);

		if (map.size() > 0) {
			// 转换vo返回
			List<DrawBaseHouseVO> drawBaseHouseVOS = adapterDrawBaseHouseVOS(((List<DrawBaseHouseBO>) map.get("list")));
			// 没有数据提示
			if (drawBaseHouseVOS == null || drawBaseHouseVOS.isEmpty()) {
				return response.status(false).code(ResponseEnum.NOT_HOUSE_CONTENT);
			}
			response.setResourceUrl(resourceDomain);
			response.status(true).code(ResponseEnum.SUCCESS).list(drawBaseHouseVOS)
				.total((Integer) map.get("total")).message("请求成功").msgId(request.getParameter("msgId"));
		}

		return response;
	}

	@PostMapping("/facsimileHouse")
	@ApiOperation(value = "用户搜索户型临摹或者修改", response = DrawBaseHouseVO.class)
	public ReturnData copyOrEditHouse(Long houseId, String type, HttpServletRequest request) {
		if (StringUtils.isEmpty(type) || houseId == null) {
			throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
		}
		ReturnData response = ReturnData.builder();
		response = setMsgId(request);
		DrawHouseAndAreaVO drawHouseAndAreaVO = drawBaseHouseService.facsimileHouse(houseId, type);
		response.setResourceUrl(resourceDomain);
		return response.status(true).code(ResponseEnum.SUCCESS).data(drawHouseAndAreaVO).message("请求成功").msgId(request.getParameter("msgId"));
	}

//	public ReturnData checkOutParameters(DrawBaseHouseSearch query, ReturnData response) {
//		if (query.getLivingName() == null || query.getAreaCode() == null) {
//			if (query.getLivingId() == null) {
//				return response.status(false).message("livingId为空").code(ResponseEnum.PARAM_ERROR);
//			}
//		}
//		if (query.getLivingId() == null) {
//			if (query.getLivingName() == null || query.getAreaCode() == null) {
//				return response.status(false).message("参数为空").code(ResponseEnum.PARAM_ERROR);
//			}
//		}
//		response.status(true);
//		return response;
//	}

	public DrawBaseHouseSearch setUserId(DrawBaseHouseSearch query) {
		UserLoginBO userLoginBO = HouseAuthUtils.getUserLoginBO();
		Long userId = userLoginBO.getId();
		log.debug("户型绘制:获取用户Id ==>:" + userId);
		if (userId == null) {
			throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
		}
		query.setUserId(userId);
		return query;
	}
	
	@PostMapping("/transformHouse")
	public ReturnData transformHouse(String phone, Long houseId, HttpServletRequest request) {
		ReturnData response = setMsgId(request);
		if (!StringUtils.isNoneBlank(phone)) {
			return response.status(false).message("手机号不能为空！");
		}
		
		if (!phone.matches(Regex.MOBILE_PHONE.getValue())) {
			return response.status(false).message(phone.concat("不是手机号码！"));
		}

		if (houseId == null || houseId <= 0) {
			return response.status(false).message("参数houseId不能为空！");
		}

		drawBaseHouseService.transformHouse(phone, houseId);

		return response;
	}

	@PostMapping("/reset")
	public ReturnData resetDrawHouse(String data) {
		if (StringUtils.isEmpty(data)) {
			return ReturnData.builder().status(false).code(ResponseEnum.PARAM_ERROR);
		}

		List<String> strings = Lists.newArrayList(data.split(","));
		List<Integer> houseIds = strings.stream().filter(StringUtils::isNoneBlank).distinct().map(Integer::parseInt).collect(Collectors.toList());

		drawBaseHouseService.resetDrawHouse(houseIds);

		return ReturnData.builder().code(ResponseEnum.SUCCESS).status(true);
	}
}
