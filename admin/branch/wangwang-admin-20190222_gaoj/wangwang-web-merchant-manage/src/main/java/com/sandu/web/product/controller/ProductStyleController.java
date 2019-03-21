package com.sandu.web.product.controller;

import com.sandu.api.product.model.ProductStyle;
import com.sandu.api.product.output.ProductStyleVO;
import com.sandu.api.product.service.ProductStyleService;
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

@Api(description = "产品款式", tags = "productStyle")
@RestController
@RequestMapping("/v1/product/style")
public class ProductStyleController {
    @Autowired
    private ProductStyleService productStyleService;

    @ApiOperation(value = "获取产品款式", response = ProductStyleVO.class)
    @GetMapping("list")
    public ReturnData initCategoryTree() {
        ReturnData data = ReturnData.builder();
        List<ProductStyle> ret = productStyleService.listProductStyleIdAndName();
        if (ret.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        List vos = new ArrayList();
        for (ProductStyle tmp : ret) {
            ProductStyleVO vo = new ProductStyleVO();
            vo.setId(tmp.getId().intValue());
            vo.setName(tmp.getStyleName());
            vos.add(vo);
        }
        return data.success(true).code(SUCCESS).data(ret);
    }
}
