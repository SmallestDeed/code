package com.sandu.web.decorateorder.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.decorateorder.input.*;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.output.DecorateOrderVO;
import com.sandu.api.decorateorder.service.biz.DecorateOrderBizService;
import com.sandu.api.groupproducct.service.ResFileService;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.proprietorinfo.service.biz.ProprietorInfoBizService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
import com.sandu.common.ReturnData;
import com.sandu.constant.ResponseEnum;
import com.sandu.exception.ServiceException;
import com.sandu.util.excel.ExcelData;
import com.sandu.util.excel.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CopyRight (c) 2018 Sandu Technology Inc. DecorateOrder
 * 
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Api(value = "DecorateOrder", tags = "DecorateOrder", description = "抢单派单订单")
@RestController
@RequestMapping(value = "/v1/decorateOrder")
@Slf4j
public class DecorateOrderController extends BaseController {

	@Autowired
	private DecorateOrderBizService decorateOrderBizService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BaseAreaService baseAreaService;

	@Autowired
	private ProprietorInfoBizService proprietorInfoBizService;
	
	@Autowired
	private ResFileService resFileService;
	
	
	@ApiOperation(value = "新建DecorateOrder", response = ReturnData.class)
	@PostMapping
	public ReturnData createDecorateOrder(@Valid @RequestBody DecorateOrderAdd input, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		int DecorateOrderId = decorateOrderBizService.create(input);
		if (DecorateOrderId > 0) {
			return data.code(ResponseEnum.SUCCESS).success(true).message("创建成功");
		}
		return data.code(ResponseEnum.ERROR).success(false).message("创建失败");
	}

	@RequiresPermissions({"decorate:signedUp:contract"})
	@ApiOperation(value = "上传合同确定", response = ReturnData.class)
	@PutMapping("/uploadContract")
	public ReturnData updateDecorateOrder(@Valid @RequestBody DecorateOrderUpdate input, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		// 获取用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null) {
			log.error("未登录");
			return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录");
		}
		DecorateOrder order = decorateOrderBizService.getById(input.getId());
		if (order == null) {
			return data.code(ResponseEnum.ERROR).success(false).message("数据非法");
		}
		if (order.getContractStatus()!= 0) {
			return data.code(ResponseEnum.ERROR).success(false).message("待审核的状态才能上传!");
		}
		int result = decorateOrderBizService.uploadContract(input, loginUser.getId(),order.getUserId());
		if (result > 0) {
			return data.code(ResponseEnum.SUCCESS).success(true).message("合同上传成功");

		}
		return data.code(ResponseEnum.ERROR).success(false).message("合同上传失败");
	}

	@RequiresPermissions({"decorate:signedUp:manage"})
	@ApiOperation(value = "更改收款状态", response = ReturnData.class)
	@PutMapping("/updateReceipt")
	public ReturnData updateReceipt(@Valid @RequestBody DecorateOrderReceiptUpdate input, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		// 获取用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null) {
			log.error("未登录");
			return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录");
		}
		DecorateOrder order = decorateOrderBizService.getById(input.getId());
		if (order == null) {
			return data.code(ResponseEnum.ERROR).success(false).message("数据非法");
		}
		if (order.getFinanceReceiptsStatus() == 1) {
			return data.code(ResponseEnum.ERROR).success(false).message("已收款不能重复操作");
		}
		int result = decorateOrderBizService.updateReceipt(input, loginUser.getId());
		if (result > 0) {
			return data.code(ResponseEnum.SUCCESS).success(true).message("更改收款状态成功");
		}
		return data.code(ResponseEnum.ERROR).success(false).message("更改收款状态失败");
	}

	@RequiresPermissions({"decorate:refundment:manage"})
	@ApiOperation(value = "退款审核、驳回", response = ReturnData.class)
	@PutMapping("/updateRefund")
	public ReturnData updateRefund(@Valid @RequestBody DecorateOrderRefundUpdate input, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		// 获取用户信息
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		if (loginUser == null) {
			log.error("未登录");
			return data.code(ResponseEnum.UNAUTHORIZED).success(false).message("未登录");
		}
		DecorateOrder order = decorateOrderBizService.getById(input.getId());
		if (order == null) {
			return data.code(ResponseEnum.ERROR).success(false).message("数据非法");
		}
		if (order.getRefundStatus() != 0) {
			return data.code(ResponseEnum.ERROR).success(false).message("状态不是待审核不能操作!");
		}
		int result = decorateOrderBizService.updateRefund(input, loginUser.getId(),order);
		if (result > 0) {
			return data.code(ResponseEnum.SUCCESS).success(true).message("操作成功");
		}
		return data.code(ResponseEnum.ERROR).success(false).message("操作失败");
	}

	@ApiOperation(value = "删除DecorateOrder", response = ReturnData.class)
	@DeleteMapping
	public ReturnData deleteDecorateOrder(String DecorateOrderId) {
		ReturnData data = ReturnData.builder();
		int result = decorateOrderBizService.delete(DecorateOrderId);
		if (result > 0) {
			return data.code(ResponseEnum.SUCCESS).success(true).message("删除成功");
		}
		return data.code(ResponseEnum.ERROR).success(false).message("删除失败");
	}

	@ApiOperation(value = "获取DecorateOrder详情", response = DecorateOrderVO.class)
	@GetMapping(value = "/{decorateOrderId}")
	public ReturnData getByDecorateOrderId(@PathVariable int decorateOrderId) {
		ReturnData data = ReturnData.builder();
		if (decorateOrderId <= 0) {
			return data.code(ResponseEnum.PARAM_ERROR).success(false).message("ID无效");
		}
		DecorateOrder DecorateOrder = decorateOrderBizService.getById(decorateOrderId);
		if (DecorateOrder == null) {
			return data.code(ResponseEnum.ERROR).success(false).message("DecorateOrder不存在");
		}

		DecorateOrderVO output = new DecorateOrderVO();
		BeanUtils.copyProperties(DecorateOrder, output);
		// 原字段ID转模块ID
		// output.set(DecorateOrder.getId());
		return data.code(ResponseEnum.SUCCESS).success(true).data(output);
	}

	@RequiresPermissions({"decorate:signedUp:view"})
	@ApiOperation(value = "查询已签约客户列表", response = DecorateOrderVO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
			@ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer") })
	@GetMapping(value = "/listContract")
	public ReturnData queryContractOrderList(@Valid DecorateOrderQuery query, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		query.setBusinessType(0); // 查询已签约订单
		PageInfo<DecorateOrder> results = decorateOrderBizService.query(query);
		log.debug("Result: {}", results);
		List<DecorateOrderVO> decorateOrders = Lists.newArrayList();
		List<Integer> companyIds = new ArrayList<Integer>();
		List<Integer> proprietorInfoIds = new ArrayList<Integer>();
		Map<Integer, ProprietorInfo> proprietorInfoMaps = new HashMap<>();
		Map<Long, String> companyMap = new HashMap<Long, String>();
		List<Integer> contractIds = new ArrayList<Integer>();
		Map<Long, String> contarctMap = new HashMap<>();
		if (results != null && results.getTotal() > 0) {
			List<DecorateOrder> orders = results.getList();
			if (orders == null || orders.size() == 0) {
				return data.code(ResponseEnum.NOT_CONTENT).success(true).message("无数据");
			}
			companyIds = orders.stream().distinct().map(order -> order.getCompanyId()).collect(Collectors.toList());
			proprietorInfoIds = orders.stream().distinct().map(order -> order.getProprietorInfoId()).collect(Collectors.toList());
			contractIds = orders.stream().distinct().filter(order-> order.getContractId()!=null).map(order -> order.getContractId()).collect(Collectors.toList());
			if (companyIds != null && companyIds.size() > 0) {
				companyMap = companyService.idAndNameMap(companyIds);
			}
			if (proprietorInfoIds != null) {
				proprietorInfoMaps = proprietorInfoBizService.getByIds(proprietorInfoIds);
			}
			if(contractIds!=null) {
				contarctMap = resFileService.idAndUrlMap(contractIds);
			}
			// 设置公司名字
			for (DecorateOrder order : orders) {
				DecorateOrderVO output = new DecorateOrderVO();
				BeanUtils.copyProperties(order, output);
				Integer contractStatus = order.getContractStatus();
				if(contractStatus != null) {
					switch (contractStatus) {
					case 0:
						output.setContractStatusStr("待上传");
						break;
					case 1:
						output.setContractStatusStr("待审核");
						break;
					case 2:
						output.setContractStatusStr("审核通过");
						break;
					default:
						output.setContractStatusStr("未知");
						break;
					}
				}
				Integer financeReceiptsStatus = order.getFinanceReceiptsStatus();
				if(financeReceiptsStatus != null) {
					switch (financeReceiptsStatus) {
					case 0:
						output.setFinanceReceiptsStatusStr("待收款");
						break;
					case 1:
						output.setFinanceReceiptsStatusStr("已收款");
						break;
					default:
						output.setFinanceReceiptsStatusStr("未知");
						break;
					}
				} 
				// 处理公司信息
				if (companyMap != null && companyMap.size() > 0) {
					if (companyMap.get(Long.valueOf(order.getCompanyId())) != null) {
						output.setCompanyName(companyMap.get(Long.valueOf(order.getCompanyId())));
					}
				}
				if (order.getContractId()!=null && contarctMap != null && contarctMap.size() > 0) {
					if (contarctMap.get(Long.valueOf(order.getContractId())) != null) {
						output.setContractUrl(contarctMap.get(Long.valueOf(order.getContractId())));
					};
				}
				// 处理用户名和手机号码
				if (proprietorInfoMaps != null && proprietorInfoMaps.size() > 0
						&& order.getProprietorInfoId() != null) {
					ProprietorInfo info = proprietorInfoMaps.get(order.getProprietorInfoId());
					if (info != null) {
						output.setUserName(info.getUserName());
						output.setMobile(info.getMobile());
						if (!StringUtils.isEmpty(info.getCityCode())) {
							String cityName = baseAreaService.getProviceCityAreaByCode(info.getCityCode());
							output.setCityName(cityName);
						}
					}
				}
				decorateOrders.add(output);
			}
			return data.code(ResponseEnum.SUCCESS).success(true).list(decorateOrders).total(results.getTotal());
		}
		return data.code(ResponseEnum.NOT_CONTENT).success(true).message("无数据");
	}

	@RequiresPermissions({"decorate:refundment:view"})
	@ApiOperation(value = "查询退款订单客户列表", response = DecorateOrderVO.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
			@ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer") })
	@GetMapping(value = "/listRefund")
	public ReturnData queryRefundOrderList(@Valid DecorateOrderQuery query, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		query.setBusinessType(1); // 查询已签约订单
		PageInfo<DecorateOrder> results = decorateOrderBizService.query(query);
		log.debug("Result: {}", results);
		List<DecorateOrderVO> decorateOrders = Lists.newArrayList();
		List<Integer> companyIds = new ArrayList<Integer>();
		List<Integer> proprietorInfoIds = new ArrayList<Integer>();
		Map<Integer, ProprietorInfo> proprietorInfoMaps = new HashMap<>();
		Map<Long, String> companyMap = new HashMap<Long, String>();
		if (results != null && results.getTotal() > 0) {
			List<DecorateOrder> orders = results.getList();
			if (orders == null || orders.size() == 0) {
				return data.code(ResponseEnum.NOT_CONTENT).success(true).message("无数据");
			}
			companyIds = orders.stream().distinct().map(order -> order.getCompanyId()).collect(Collectors.toList());
			proprietorInfoIds = orders.stream().distinct().map(order -> order.getProprietorInfoId()).collect(Collectors.toList());
			if (companyIds != null && companyIds.size() > 0) {
				companyMap = companyService.idAndNameMap(companyIds);
			}
			if (proprietorInfoIds != null) {
				proprietorInfoMaps = proprietorInfoBizService.getByIds(proprietorInfoIds);
			}
			// 设置公司名字
			for (DecorateOrder order : orders) {
				DecorateOrderVO output = new DecorateOrderVO();
				BeanUtils.copyProperties(order, output);
				// 处理公司信息
				if (companyMap != null && companyMap.size() > 0) {
					if (companyMap.get(Long.valueOf(order.getCompanyId())) != null) {
						output.setCompanyName(companyMap.get(Long.valueOf(order.getCompanyId())));
					};
				}
				Integer refundStatus = order.getRefundStatus();
				if(refundStatus != null) {
					switch (refundStatus) {
					case 0:
						output.setRefundStatusStr("待审核");
						break;
					case 1:
						output.setRefundStatusStr("审核通过");
						break;
					case 2:
						output.setRefundStatusStr("已驳回");
						break;
					default:
						output.setRefundStatusStr("未知");
						break;
					}
				}
				// 处理用户名和手机号码
				if (proprietorInfoMaps != null && proprietorInfoMaps.size() > 0
						&& order.getProprietorInfoId() != null) {
					ProprietorInfo info = proprietorInfoMaps.get(order.getProprietorInfoId());
					if (info != null) {
						output.setUserName(info.getUserName());
						output.setMobile(info.getMobile());
						if (!StringUtils.isEmpty(info.getCityCode())) {
							String cityName = baseAreaService.getProviceCityAreaByCode(info.getCityCode());
							output.setCityName(cityName);
						}
					}
				}
				decorateOrders.add(output);
			}
			return data.code(ResponseEnum.SUCCESS).success(true).list(decorateOrders).total(results.getTotal());
		}
		return data.code(ResponseEnum.NOT_CONTENT).success(true).message("无数据");
	}

	@ApiOperation(value = "已签约客户导出excel")
	@GetMapping(value = "/exportContract")
	public void exportContract(DecorateOrderQuery query,HttpServletResponse response) throws Exception {
		ExcelData data = new ExcelData();
		data.setName("已签约客户导出");
		List<String> titles = new ArrayList<String>();
		titles.addAll(Arrays.asList("姓名","手机号","城市","签约装企","签约价格(元)","服务费(签约价*5%)","是否已收款"));
		data.setTitles(titles);
		List<List<Object>> rows = new ArrayList<List<Object>>();
		List<DecorateOrder> datas = new ArrayList<DecorateOrder>();
		datas = decorateOrderBizService.queryContractOrderList(query);
		if(datas == null ||datas.isEmpty()) {
			throw new ServiceException(ResponseEnum.NOT_CONTENT, "没有数据需要导出");
		}
//		rows.add(Arrays.asList( datas.toArray()));
		rows.add(ExcelUtil.toObject(datas));
		data.setRows(rows);
		// 生成本地
		/*
		 * File f = new File("c:/test.xlsx"); FileOutputStream out = new
		 * FileOutputStream(f); ExportExcelUtils.exportExcel(data, out); out.close();
		 */
		SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String fileName = fdate.format(new Date()) + ".xls";
		ExcelUtil.exportExcel(response, fileName, data);
	}
	
	@ApiOperation(value = "退款管理导出excel")
	@GetMapping(value = "/exportRefund")
	public void exportRefund(DecorateOrderQuery query,HttpServletResponse response) throws Exception {
		ExcelData data = new ExcelData();
		data.setName("退款管理导出excel");
		List<String> titles = new ArrayList<String>();
		titles.addAll(Arrays.asList("姓名","手机号","城市","退款装修公司","退款状态","退款原因","退款驳回原因"));
		data.setTitles(titles);
		List<List<Object>> rows = new ArrayList<List<Object>>();
		List<DecorateOrder> datas = new ArrayList<DecorateOrder>();
		datas = decorateOrderBizService.queryRefundOrderList(query);
		if(datas == null ||datas.isEmpty()) {
			throw new ServiceException(ResponseEnum.NOT_CONTENT, "没有数据需要导出");
		}
		rows.add(ExcelUtil.toObject(datas));
		data.setRows(rows);
		SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String fileName = fdate.format(new Date()) + ".xls";
		ExcelUtil.exportExcel(response, fileName, data);
	}
}
