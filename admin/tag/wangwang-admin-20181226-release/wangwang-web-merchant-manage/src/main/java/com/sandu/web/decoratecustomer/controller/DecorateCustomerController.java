package com.sandu.web.decoratecustomer.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sandu.api.decoratecustomer.input.DecorateCustomerAdd;
import com.sandu.api.decoratecustomer.input.DecorateCustomerQuery;
import com.sandu.api.decoratecustomer.input.DecorateCustomerUpdate;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;
import com.sandu.api.decoratecustomer.output.DecorateCustomerListVO;
import com.sandu.api.decoratecustomer.output.DecorateCustomerVO;
import com.sandu.api.decoratecustomer.service.biz.DecorateCustomerBizService;
import com.sandu.api.decorateorder.input.DecorateOrderAdd;
import com.sandu.api.decorateorder.service.biz.DecorateOrderBizService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.commons.LoginUser;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * <p>demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 14:27
 */
@Api(value = "DecorateCustomer", tags = "decorateCustomer", description = "抢单派单客户管理")
@RestController
@RequestMapping(value = "/v1/decorateCustomer")
@Slf4j
public class DecorateCustomerController extends BaseController {

	@Autowired
	private DecorateCustomerBizService decorateCustomerBizService;

	@Autowired
	private DecorateOrderBizService decorateOrderBizService;

	@ApiOperation(value = "新建decorateCustomer", response = ReturnData.class)
	//    @PostMapping
	public ReturnData createDecorateCustomer(
			@Valid @RequestBody DecorateCustomerAdd input, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}

		int decorateCustomerId = decorateCustomerBizService.create(input);
		if (decorateCustomerId > 0) {
			return data.code(ResponseEnum.CREATED).data(decorateCustomerId);
		}

		return data.code(ResponseEnum.ERROR).message("输入数据有误");
	}

	@RequiresPermissions({"decorate:decorateCustomer:edit"})
	@ApiOperation(value = "编辑decorateCustomer", response = ReturnData.class)
	@PutMapping
	public ReturnData updateDecorateCustomer(
			@Valid @RequestBody DecorateCustomerUpdate input, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}
		input.setLoginUser(LoginContext.getLoginUser(LoginUser.class));
		if (input.getDecorateType() == 4
				&& (input.getContractCompany() == null || input.getContractPrice() == null)) {
			log.info("input param :{}", input);
			return data.code(ResponseEnum.ERROR).message("输入数据有误");
		}
		int result = decorateCustomerBizService.update(input);
		if (result > 0) {
			return data.code(ResponseEnum.SUCCESS).success(true).message("操作成功");
		}

		return data.code(ResponseEnum.ERROR).message("输入数据有误");
	}

	@ApiOperation(value = "删除decorateCustomer", response = ReturnData.class)
	//    @DeleteMapping
	public ReturnData deleteDecorateCustomer(String decorateCustomerId) {
		ReturnData data = ReturnData.builder();

		int result = decorateCustomerBizService.delete(decorateCustomerId);
		if (result > 0) {
			return data.code(ResponseEnum.SUCCESS).data(result);
		}

		return data.code(ResponseEnum.ERROR).message("不存在");
	}

	@RequiresPermissions({"decorate:decorateCustomer:view"})
	@ApiOperation(value = "获取decorateCustomer详情", response = DecorateCustomerVO.class)
	@GetMapping(value = "/{decorateCustomerId}")
	public ReturnData getByDecorateCustomerId(@PathVariable int decorateCustomerId) {
		ReturnData data = ReturnData.builder();
		if (decorateCustomerId <= 0) {
			return data.code(ResponseEnum.ERROR).message("ID无效");
		}

		DecorateCustomerBO decorateCustomer = decorateCustomerBizService.getById(decorateCustomerId);
		if (decorateCustomer == null) {
			return data.code(ResponseEnum.ERROR).message("decorateCustomer不存在");
		}

		DecorateCustomerVO output = new DecorateCustomerVO();
		BeanUtils.copyProperties(decorateCustomer, output);
		// 原字段ID转模块ID
		//        output.setDecorateCustomerId(decorateCustomer.getId());

		return data.code(ResponseEnum.SUCCESS).data(output);
	}

	@RequiresPermissions({"decorate:decorateCustomer:view"})
	@ApiOperation(value = "查询decorateCustomer列表", response = DecorateCustomerListVO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
			@ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
	})
	@GetMapping(value = "/list")
	public ReturnData queryDecorateCustomerList(
			@Valid DecorateCustomerQuery query, BindingResult validResult) {
		ReturnData data = ReturnData.builder();
		if (validResult.hasErrors()) {
			return processValidError(validResult, data);
		}

		final PageInfo<DecorateCustomerListBO> results = decorateCustomerBizService.query(query);
		final List<DecorateCustomerListVO> decorateCustomers = Lists.newArrayList();
		if (results != null && results.getTotal() > 0) {
			results
					.getList()
					.forEach(
							decorateCustomer -> {
                DecorateCustomerListVO output = new DecorateCustomerListVO();
                BeanUtils.copyProperties(decorateCustomer, output);
								if (output.getRevisitTime() != null) {
									output.setRevisitTime(DateUtils.addDays(output.getRevisitTime(), 1));
								}
                decorateCustomers.add(output);
							});
			return data.code(ResponseEnum.SUCCESS).list(decorateCustomers).total(results.getTotal());
		}

		return data.code(ResponseEnum.SUCCESS).message("暂无数据");
	}

	@RequiresPermissions({"decorate:decorateCustomer:manage"})
	@ApiOperation("提交抢单")
	@GetMapping("submit/sedkill/{customerId}")
	public ReturnData submitSedKill(@PathVariable("customerId") Integer customerId) {
		Boolean ret = decorateCustomerBizService.submitToSedKill(customerId);
		String message = ret ? "提交成功" : "提交失败";
		return ReturnData.builder().success(ret).code(ResponseEnum.SUCCESS).message(message);
	}

	@RequiresPermissions({"decorate:decorateCustomer:manage"})
	@ApiOperation("内部推荐")
	@GetMapping("inner/recommend/{customerId}/{companyId}")
	public ReturnData innerRecommend(
			@PathVariable("customerId") Integer customerId,
			@PathVariable("companyId") Integer companyId) {

		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		//		LoginUser loginUser = null;
		//		loginUser = new LoginUser();
		//		loginUser.setId(123);
		DecorateOrderAdd add = new DecorateOrderAdd();
		add.setCompanyId(companyId);
		add.setCustomerId(customerId);
		add.setUser(loginUser);
		try {
			decorateOrderBizService.create(add);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnData.builder().code(ResponseEnum.ERROR).success(false).message("指派失败");
		}
		return ReturnData.builder().code(ResponseEnum.SUCCESS).success(true).message("指派成功");
	}

	@GetMapping("test")
	public void testDispatch() {
		decorateCustomerBizService.dispatchDecoratePrice();
	}
}
