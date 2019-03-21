package com.sandu.web.living.controller;

import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.living.model.input.BaseLivingSearch;
import com.sandu.living.model.output.BaseLivingVo;
import com.sandu.living.service.BaseLivingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/living")
@Api(value = "小区", tags = "living", description = "小区")
public class BaseLivingController {

    @Autowired
    private BaseLivingService baseLivingService;

    /**
     * 获取小区列表
     * @param baseLivingSearch
     * @return
     */
    @ApiOperation(value = "小区列表" , response = ResponseEnvelope.class)
    @PostMapping("/list")
    public Object list(@RequestBody BaseLivingSearch baseLivingSearch){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        baseLivingSearch.setUserId(loginUser.getId());
        int total = baseLivingService.count(baseLivingSearch);
        List<BaseLivingVo> livingList = new ArrayList<>();
        if( total > 0 ){
            livingList = baseLivingService.list(baseLivingSearch);
        }
        return new ResponseEnvelope<>(total, livingList);
    }

}
