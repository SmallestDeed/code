package com.sandu.web.house.controller;

import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.house.model.input.BaseHouseSearch;
import com.sandu.house.model.output.BaseHouseVo;
import com.sandu.house.service.BaseHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "户型", tags = "house", description = "户型")
@RestController
@RequestMapping("/v1/house/")
@Slf4j
public class BaseHouseController {

    @Autowired
    private BaseHouseService baseHouseService;

    @ApiOperation(value = "得到我渲染过的户型", response = ResponseEnvelope.class)
    @PostMapping("/getMyUsed")
    public Object getMyUsed(@RequestBody BaseHouseSearch baseHouseSearch){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        baseHouseSearch.setUserId(loginUser.getId());

        List<BaseHouseVo> houseList = new ArrayList<>();
        int totalCount = baseHouseService.getMyUsedCount(baseHouseSearch);
        if( totalCount > 0 ) {
            houseList = baseHouseService.getMyUsed(baseHouseSearch);
        }
        return new ResponseEnvelope<>(totalCount, houseList);
    }

}
