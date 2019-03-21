package com.sandu.web.category.controller;

import com.sandu.api.category.model.Category;
import com.sandu.api.category.model.bo.CategoryTreeNode;
import com.sandu.api.category.output.CategoryVO;
import com.sandu.api.category.service.CategoryService;
import com.sandu.api.category.service.biz.CategoryBizService;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;

@Api(description = "分类", tags = "category")
@RestController
@RequestMapping("/v1/category")
public class CategoryContrlloer {

    @Autowired
    private CategoryBizService categoryBizService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取分类搜索下拉框", response = CategoryVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "model/texture,不传type则为查询所有", paramType = "query", dataType = "string"),
    })
    @GetMapping("/tree/search")
    public ReturnData initSearchCategoryTree(String type) {
        ReturnData data = ReturnData.builder();
        Integer param = null;
        if (StringUtils.isNoneBlank(type)) {
            param = "model".equals(type) ? 1 : 2;
        }
        return getCategoryTree(data, param, Category.LEVEL_THIRD);
    }

    private ReturnData getCategoryTree(ReturnData data, Integer param, Integer lastLevel) {
        List<CategoryTreeNode> nodes = categoryBizService.initCategoryTree(param, lastLevel);
        List<CategoryVO> ret = new ArrayList<>();
        for (CategoryTreeNode tmp : nodes) {
            CategoryVO vo = new CategoryVO();
            vo.setCode(tmp.getCode());
            vo.setName(tmp.getName());
            vo.setChildren(tmp.getChildren());
            ret.add(vo);
        }
        if (ret.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        return data.success(true).code(SUCCESS).data(ret);
    }

    @ApiOperation(value = "获取运营树", response = CategoryVO.class)
    @GetMapping("tree")
    public ReturnData initCategoryTree() {
        return getCategoryTree(ReturnData.builder(), null, Category.LEVEL_FIFTH);
    }

    @ApiOperation(value = "校验分类类型(模型/贴图)")
    @GetMapping("type")
    public ReturnData checkCategoryType(String code) {
        Category category = categoryService.getCategoryByCode(code);
        if (category == null || category.getNumAtt2() == null) {
            return ReturnData.builder().message("未知类型").data(-1).code(ERROR);
        }
        if (category.getNumAtt2() == 2) {
            return ReturnData.builder().message("贴图产品").data(2).code(SUCCESS);
        } else if (category.getNumAtt2() == 1) {
            return ReturnData.builder().message("模型产品").data(1).code(SUCCESS);
        }
        return ReturnData.builder().message("未知类型").data(-1).code(ERROR);
    }


    @ApiOperation(value = "获取分类属性节点", response = CategoryVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "3级节点编码", paramType = "query", dataType = "string"),
    })
    @GetMapping("children")
    public ReturnData getChildrenNode(String code) {
        if (StringUtils.isBlank(code)) {
            return ReturnData.builder().message("参数错误,请输入正确的分类编码").code(ERROR);
        }
        List<CategoryTreeNode> nodes = categoryBizService.listCategoryInfo(code);
        List<CategoryVO> ret = nodes.stream().map(item -> {
            CategoryVO vo = new CategoryVO();
            vo.setCode(item.getCode());
            vo.setName(item.getName());
            vo.setChildren(item.getChildren());
            return vo;
        }).collect(Collectors.toList());
        if (ret.isEmpty()) {
            return ReturnData.builder().success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        return ReturnData.builder().success(true).code(SUCCESS).data(ret);
    }

    @ApiOperation("同步运营分类缓存")
    @GetMapping("reset")
    public ReturnData syncRedisCategory(){
        categoryBizService.syncCache();
        return ReturnData.builder().success(true).code(SUCCESS);
    }


}
