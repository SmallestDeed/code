package com.sandu.web.designplan.controller;

import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.design.model.input.DesignPlanRenderSceneSearch;
import com.sandu.design.model.output.DesignPlanRenderSceneVo;
import com.sandu.design.service.DesignPlanRenderSceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "效果图方案", tags = "Panorama-designPlanRenderScene", description = "720分享制作-效果图方案")
@RestController
@RequestMapping("/v1/panorama/renderscene")
@Slf4j
public class DesignPlanRenderSceneController {

    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    @ApiOperation(value = "720制作-效果图方案列表", response = ResponseEnvelope.class)
    @PostMapping("/list")
    public Object list(@RequestBody DesignPlanRenderSceneSearch search){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        search.setUserId(loginUser.getId());
        int total = designPlanRenderSceneService.selectCount(search);
        List<DesignPlanRenderSceneVo> designPlanRenderSceneVoList = null;
        if( total > 0 ){
            designPlanRenderSceneVoList = designPlanRenderSceneService.selectList(search);
        }
        return new ResponseEnvelope<>(total, designPlanRenderSceneVoList);
    }

}
