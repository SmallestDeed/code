package com.sandu.web.product.controller;

import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.panorama.model.output.UnionContactVo;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.model.input.CompanyShopSearch;
import com.sandu.product.model.output.CompanyShopVo;
import com.sandu.product.service.CompanyShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenm on 2018/11/22.
 */
@Api(value="店铺信息",tags = "companyShop",description = "店铺信息")
@RestController
@RequestMapping("/v1/product/companyShop")
public class CompanyShopController {

    @Autowired
    private CompanyShopService companyShopService;

    @ApiOperation(value = "获取店铺信息",response = ResponseEnvelope.class)
    @GetMapping("/list")
    public Object getCompanyShopList(CompanyShopSearch search){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return new ResponseEnvelope<>(false,"当前登录失效,请重新登录!");
        }
        search.setIsDeleted(0);
        int count = companyShopService.getCountBySearch(search);
        List<CompanyShopVo> list = new ArrayList<CompanyShopVo>();
        if(count > 0){
            list = companyShopService.getCompanyShopListBySearch(search);
        }

        return new ResponseEnvelope<CompanyShopVo>(count,list);
    }


}
