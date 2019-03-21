package com.sandu.web.decorate.controller;

import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.constant.ResponseEnum;
import com.sandu.decorate.output.SysDictionaryVO;
import com.sandu.decorate.service.biz.PlanDecoratePriceBizService;
import com.sandu.system.model.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "DecoratePrice", tags = "DecoratePrice", description = "DecoratePrice")
@RestController
@RequestMapping("/v1/miniprogram/decoratePrice")
@Slf4j
public class DecoratePriceController extends BaseController {

    @Autowired
    private PlanDecoratePriceBizService planDecoratePriceBizService;

    @ApiOperation(value = "获取所有装修报价筛选分类", response = ReturnData.class)
    @GetMapping("/getDecoratePriceType")
    public ReturnData getDecoratePriceType(HttpServletRequest request) {
        ReturnData data = ReturnData.builder();

        List<SysDictionary> allDecoratePriceType = planDecoratePriceBizService.getAllDecoratePriceType();
        if (allDecoratePriceType == null) {
            return data.code(ResponseEnum.NOT_CONTENT).message("数据为空");
        }
        List<SysDictionaryVO> sysDictionaryVOList = SysDictionaryVO.copyToSysDictionaryVO(allDecoratePriceType);

        return data.code(ResponseEnum.SUCCESS).list(sysDictionaryVOList).message("成功");
    }

}
