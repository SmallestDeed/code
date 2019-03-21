package com.sandu.web.house.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.api.house.bo.DrawUploadBO;
import com.sandu.api.house.output.UploadFileVO;
import com.sandu.api.house.service.UploadService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.exception.BusinessException;
import com.sandu.util.UploadUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */

@Slf4j
@RestController
@RequestMapping("/v1/file")
@Api(tags = "上传文件、图片")
public class UploadController extends BaseController {

	@Autowired
	private UploadService uploadService;
	
	/**
	 * 上传文件接口
	 * 
	 * @param files
	 * @param drawUploadBOS
	 * @param request
	 * @param uploadProperty 配置:save:保存文件数据;其他:不保存文件数据
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/upload")
	@ApiOperation(value = "上传文件、图片", response = UploadFileVO.class)
	public ReturnData upload(
			MultipartHttpServletRequest files, 
			String drawUploadBOS, 
			HttpServletRequest request,
			String uploadProperty) {

		ReturnData response = ReturnData.builder();
		response = setMsgId(request);

		// 开始处理
		List<UploadFileVO> responseFileDatas = new ArrayList<>();
		List<DrawUploadBO> drawUploadBOList = getDrawUploadBOS(drawUploadBOS);
		Map<String, DrawUploadBO> uploadMap = drawUploadBOList.stream().collect(Collectors.toMap(DrawUploadBO::getFileName, f -> f, (p, n) -> n));
		if (uploadMap == null || uploadMap.isEmpty()) {
			throw new BusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
		}

		for (Iterator<String> it = files.getFileNames(); it.hasNext(); ) {
			List<MultipartFile> file = files.getFiles(it.next());
			for (MultipartFile multipartFile : file) {
				UploadFileVO fileVO = UploadUtils.upload(multipartFile, uploadMap);
				responseFileDatas.add(fileVO);
			}
		}

		// 资源数据表添加资源数据 add by songjianming@sanduspace.cn 2018/3/14
		if (StringUtils.equals(DrawBaseHouseConstant.UPLOAD_UPLOADPROPERTY_SAVE, uploadProperty)) {
			if (!responseFileDatas.isEmpty()) {
				log.info("处理上传的文件保存到数据库");
				uploadService.saveUploadFile(responseFileDatas);
			}
		}

		return response.list(responseFileDatas);
	}

	private List<DrawUploadBO> getDrawUploadBOS(String drawUploadBOS) {
		try {
			return new ObjectMapper().readValue(drawUploadBOS, new TypeReference<List<DrawUploadBO>>() {
			});
		} catch (Exception e) {
			throw new BusinessException(false, ResponseEnum.PARAM_ERROR_JSONSTR_EMPTY);
		}
	}
}
