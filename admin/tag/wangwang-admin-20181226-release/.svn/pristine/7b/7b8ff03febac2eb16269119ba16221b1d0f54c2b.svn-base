package com.sandu.web.product.controller;

import com.sandu.api.product.model.BaseSeries;
import com.sandu.api.product.output.BaseSeriesVO;
import com.sandu.api.product.service.BaseSeriesService;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;

@Api(description = "产品系列", tags = "series")
@RestController
@RequestMapping("/v1/product/series")
public class SeriesController {
    @Autowired
    private BaseSeriesService baseSeriesService;

    @ApiOperation(value = "获取产品系列", response = BaseSeriesVO.class)
    @GetMapping("list")
    public ReturnData listSeries() {
        ReturnData data = ReturnData.builder();
        List<BaseSeries> ret = baseSeriesService.listAll();
        if (ret.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        List vos = new ArrayList();
        for (BaseSeries tmp : ret) {
            BaseSeriesVO vo = new BaseSeriesVO();
            vo.setId(tmp.getId().intValue());
            vo.setName(tmp.getName());
            vos.add(vo);
        }
        return data.success(true).code(SUCCESS).data(ret);
    }
}
