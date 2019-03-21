package com.sandu.web.product.controller;

import com.sandu.cityunion.model.UnionSpecialOffer;
import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.constant.BusinessTypeConstant;
import com.sandu.product.model.input.BaseCompanySearch;
import com.sandu.product.model.output.BaseCompanyVo;
import com.sandu.product.service.BaseCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenm on 2018/8/2.
 */
@Api(value="企业信息",tags="company",description = "企业信息")
@RestController
@RequestMapping("/v1/product/baseCompany")
public class BaseCompanyController {

    @Autowired
    private BaseCompanyService baseCompanyService;

    private Logger logger = LoggerFactory.getLogger(BaseCompany.class);

    @ApiOperation(value = "获取企业列表",response = BaseCompanyVo.class)
    @GetMapping("/list")
    public Object getBaseCompanyList(BaseCompanySearch companySearch){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return  new ResponseEnvelope<UnionSpecialOffer>(false,"当前登录失效，请重新登录");
        }
        companySearch.setIsDeleted(0);
        //不查找经销商公司
        List<Integer> hiddenCompanyTypeList = new ArrayList<Integer>();
        hiddenCompanyTypeList.add(BusinessTypeConstant.BUSINESS_TYPE_FRANCHISER);
        companySearch.setHiddenBusinessTypeList(hiddenCompanyTypeList);

        List<BaseCompanyVo> baseCompanyVoList = baseCompanyService.selectBaseCompanyVoList(companySearch);
        if(CustomerListUtils.isEmpty(baseCompanyVoList)){
            return new ResponseEnvelope<BaseCompanyVo>(0,null);
        }
        return new ResponseEnvelope<BaseCompanyVo>(baseCompanyVoList.size(),baseCompanyVoList);
    }
}
