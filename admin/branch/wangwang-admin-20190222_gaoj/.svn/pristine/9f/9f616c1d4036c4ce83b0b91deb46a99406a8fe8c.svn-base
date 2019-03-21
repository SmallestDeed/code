package com.sandu.web.product.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.model.bo.DesignPlanStyleBO;
import com.sandu.api.product.output.BaseProductStyleVO;
import com.sandu.api.product.output.DesignPlanStyleVO;
import com.sandu.api.product.service.BaseProductStyleService;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;

@Api(description = "产品风格", tags = "baseProductStyle")
@RestController
@RequestMapping("/v1/product/base/style")
public class BaseProductStyleController {
    @Autowired
    private BaseProductStyleService baseProductStyleService;

    @ApiOperation(value = "获取产品风格", response = BaseProductStyleVO.class)
    @GetMapping("list")
    public ReturnData initCategoryTree() {
        ReturnData data = ReturnData.builder();
        List<BaseProductStyle> ret = baseProductStyleService.listBaseProductStyleIdAndName();
        if (ret.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        List vos = new ArrayList();
        for (BaseProductStyle tmp : ret) {
            BaseProductStyleVO vo = new BaseProductStyleVO();
            vo.setId(tmp.getId().intValue());
            vo.setName(tmp.getName());
            vos.add(vo);
        }
        return data.success(true).code(SUCCESS).data(vos);
    }

    @ApiOperation(value = "获取方案风格", response = BaseProductStyleVO.class)
    @GetMapping("planList/{functionId}")
    public ReturnData initCategoryTree1(@PathVariable Integer functionId) {
        ReturnData data = ReturnData.builder();
        List<BaseProductStyle> ret = baseProductStyleService.listBasePlanStyleIdAndName(functionId);
        if (ret.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        List vos = new ArrayList();
        for (BaseProductStyle tmp : ret) {
            BaseProductStyleVO vo = new BaseProductStyleVO();
            vo.setId(tmp.getId().intValue());
            vo.setName(tmp.getName());
            vos.add(vo);
        }
        return data.success(true).code(SUCCESS).data(vos);
    }

    @ApiOperation(value = "获取方案风格", response = DesignPlanStyleVO.class)
    @GetMapping("/plan/list")
    public ReturnData<DesignPlanStyleBO> listDesignPlanStyle() {
        List<DesignPlanStyleBO> listStyle = baseProductStyleService.listDesignPlanStyle();
        if (listStyle == null || listStyle.isEmpty()) {
            return ReturnData.builder().success(true).code(NOT_CONTENT).data(new ArrayList<>(0));
        }

//        Multimap<String, DesignPlanStyleVO> multiMap = ArrayListMultimap.create();
//        for (DesignPlanStyleBO bo : listStyle) {
//            multiMap.put(bo.getSpaceFunctionName(), DesignPlanStyleVO.builder().styleId(bo.getStyleId()).styleName(bo.getStyleName()).build());
//        }
//
//        List<DesignPlanStyleVO> styles = new ArrayList<>();
//        for (String key : multiMap.keySet()) {
//            styles.add(DesignPlanStyleVO.builder().styleName(key).styles(Lists.newArrayList(multiMap.get(key))).build());
//        }

        return ReturnData.builder().success(true).code(SUCCESS).data(listStyle);
    }
}
