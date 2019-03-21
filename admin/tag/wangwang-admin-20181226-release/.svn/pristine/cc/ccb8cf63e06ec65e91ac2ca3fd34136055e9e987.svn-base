package com.sandu.web.designplan.controller;

import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.sandu.constant.ResponseEnum.SUCCESS;

/**
 * @author  by bvvy
 */
@RestController
@RequestMapping("/v1/renderpic")
@Api(tags = "RenderPic", description = "渲染图")
public class ResRenderPicController {

    @Resource
    private DesignPlanBizService designPlanBizService;

    @PostMapping("/pic/ban")
    @ApiOperation("设置图片禁用")
    public ReturnData banResRenderPic(@RequestBody Long picId) {
        designPlanBizService.banResRenderPic(picId);
        return ReturnData.builder().code(SUCCESS);
    }

    @DeleteMapping
    @ApiOperation("设置删除")
    public ReturnData deleteResRenderPic(@RequestParam Long picId) {
        designPlanBizService.deleteResRenderPic(picId);
        return ReturnData.builder().code(SUCCESS);
    }

}
