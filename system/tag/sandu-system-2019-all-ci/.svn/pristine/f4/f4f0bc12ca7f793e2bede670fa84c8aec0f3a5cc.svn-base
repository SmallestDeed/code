package com.sandu.web.company;

import static com.sandu.api.constance.BusinessTypeConstant.BUSINESS_TYPE_COMPANY;
import static com.sandu.api.constance.BusinessTypeConstant.BUSINESS_TYPE_FRANCHISER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.sandu.api.company.CompanyBizException;
import com.sandu.api.company.input.InnerCompanyInput;
import com.sandu.api.company.input.InnerCompanyQuery;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.bo.InnerCompanyBO;
import com.sandu.api.company.output.InnerCompanyDetails;
import com.sandu.api.company.service.biz.InnerCompanyBizService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.constant.Punctuation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandu
 * @ClassName BaseCompanyController
 * @date 2018/11/6
 */

@Slf4j
@RestController
@RequestMapping("/v1/comapny/inner")
public class InnerCompanyController extends BaseController {


	@Autowired
	private InnerCompanyBizService innerCompanyBizService;


	@RequiresPermissions({"manufacturer.manage.add", "company.manage.add"})
	@PostMapping()
	public ReturnData add(InnerCompanyInput baseCompany) {
		ReturnData returnData = this.validCompanyParam(baseCompany);
		if (returnData != null) {
			return returnData;
		}

		try {
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			baseCompany.setLoginUser(loginUser);
			innerCompanyBizService.addCompany(baseCompany);
		} catch (CompanyBizException biz) {
			return ReturnData.builder().message(biz.getMessage()).success(false);
		} catch (Exception e) {
			log.error("message:{}", e.getCause(), e);
			return ReturnData.builder().message("系统错误...").success(false);
		}

		return ReturnData.builder().message("操作成功 ").success(true);
	}


	@RequiresPermissions({"manufacturer.user.del", "company.manage.del"})
	@DeleteMapping("/{ids}")
	public ReturnData<BaseCompany> delete(@PathVariable("ids") String companyIds) {
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		List<Integer> collect = Stream.of(Strings.nullToEmpty(companyIds).split(Punctuation.COMMA))
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		try {
			innerCompanyBizService.deleteByIds(collect, loginUser.getId().intValue());
		} catch (Exception e) {
			log.error("message:{}", e.getCause());
			return ReturnData.builder().message("系统错误...").success(false);
		}
		return ReturnData.builder().message("操作成功 ").success(true);
	}


	@RequiresPermissions({"company.manage.update", "manufacturer.user.update"})
	@PostMapping("update")
	public ReturnData update(InnerCompanyInput baseCompany) {
		ReturnData returnData = this.validCompanyParam(baseCompany);
		if (returnData != null) {
			return returnData;
		}

		try {
			innerCompanyBizService.updateCompany(baseCompany);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnData.builder().message("系统错误...").success(false);
		}
		return ReturnData.builder().message("操作成功 ").success(true);
	}

	private ReturnData validCompanyParam(InnerCompanyInput baseCompany) {
		if (baseCompany.getWithDistributor()) {
			//新增厂商
			if (baseCompany.getIsManage() == null) {
				return ReturnData.builder().message("请选择是否开通小程序客户管理").success(false);
			}
		} else {
			if (baseCompany.getBusinessType() == null) {
				return ReturnData.builder().message("请选择企业类型").success(false);
			}
		}
		return null;
	}


	@RequiresPermissions({"company.manage.view", "manufacturer.user.view"})
	@GetMapping
	public ReturnData<InnerCompanyBO> list(InnerCompanyQuery query) {
		PageInfo<InnerCompanyBO> list;
		if (query.getListType() == 1) {
			query.setCompanyType(BUSINESS_TYPE_COMPANY);
		} else {
			query.setExcludeCompanyType(Arrays.asList(BUSINESS_TYPE_COMPANY, BUSINESS_TYPE_FRANCHISER));
		}
		try {
			list = innerCompanyBizService.listCompany(query);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnData.builder().message("系统错误...").success(false);
		}
		return ReturnData.builder().list(list.getList()).total(list.getTotal()).success(true);
	}


	@RequiresPermissions({"company.manage.view", "manufacturer.user.view"})
	@GetMapping("/{id}")
	public ReturnData<InnerCompanyDetails> get(@PathVariable("id") Long companyId) {
		InnerCompanyDetails details = null;
		try {
			details = innerCompanyBizService.details(companyId);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnData.builder().message("系统错误...").success(false);
		}
		return ReturnData.builder().data(details).success(true);
	}

}
