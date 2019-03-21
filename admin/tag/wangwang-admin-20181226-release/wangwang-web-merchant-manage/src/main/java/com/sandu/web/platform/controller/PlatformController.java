package com.sandu.web.platform.controller;

import static com.sandu.constant.ResponseEnum.SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.platform.output.PlatformTypeVO;
import com.sandu.api.platform.output.PlatformVO;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.common.ReturnData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "平台信息", tags = "platform")
@RestController
@RequestMapping("/v1/platform")
public class PlatformController {
	@Autowired
	private PlatformService platformService;

	@ApiOperation(value = "根据平台类型获取平台", response = PlatformVO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "platformType", value = "2b渠道/2c:线上", paramType = "query", dataType = "string", required = true), })
	@GetMapping("/list")
	public ReturnData list(String platformType) {
		ReturnData data = ReturnData.builder();
		List<String> types = new ArrayList<>();
		types.add(platformType);
		Map<Integer, String> map = platformService.getPlatformIdAndNameByBussinessType(types.get(0));
		// List<Integer> ids = platformService.getPlatformIdsByBussinessTypes(types);
		// Map<Integer, String> map = platformService.getAllPlatformIdAndName();
		List<PlatformVO> platforms = new ArrayList();
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			PlatformVO vo = new PlatformVO();
			vo.setId(entry.getKey());
			vo.setName(entry.getValue().toString());
			platforms.add(vo);
		}
		return data.success(true).code(SUCCESS).data(platforms);

	}

	// 获取产品和方案的上架平台
	@ApiOperation(value = "根据菜单类型获取平台", response = PlatformTypeVO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "menuType", value = "0:产品/1:方案", paramType = "query", dataType = "string", required = true), })
	@GetMapping("/listByType")
	public ReturnData listByType(String menuType) {
		ReturnData data = ReturnData.builder();
		List<String> types = new ArrayList<>();
		types.add("2c");
		Map<Integer, String> map = platformService.getPlatformIdAndNameByBussinessType(types.get(0));
		List<PlatformTypeVO> platforms = new ArrayList();
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			PlatformTypeVO vo = new PlatformTypeVO();
			vo.setId(entry.getKey() + "");
			vo.setName(entry.getValue().toString());
			platforms.add(vo);
		}
		if ("0".equals(menuType)) {
			// 通用版和2B移动端
			PlatformTypeVO vo = new PlatformTypeVO();
			vo.setId("1");
			vo.setName("2B-移动端");
			platforms.add(vo);
			PlatformTypeVO pcvo = new PlatformTypeVO();
			pcvo.setId("2");
			pcvo.setName("2B-PC");
			platforms.add(pcvo);
		} else {
			PlatformTypeVO bvo = new PlatformTypeVO();
			bvo.setId("1");
			bvo.setName("2B-移动端");
			platforms.add(bvo);
			PlatformTypeVO vo = new PlatformTypeVO();
			vo.setId("ONEKEY");
			vo.setName("通用版-一键方案");
			platforms.add(vo);
			PlatformTypeVO pcvo = new PlatformTypeVO();
			pcvo.setId("OPEN");
			pcvo.setName("通用版-公开方案");
			platforms.add(pcvo);
		}
		return data.success(true).code(SUCCESS).data(platforms);
	}
}
