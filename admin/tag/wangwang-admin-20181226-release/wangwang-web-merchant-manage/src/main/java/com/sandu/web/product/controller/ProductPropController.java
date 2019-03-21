package com.sandu.web.product.controller;


import com.sandu.api.product.model.ProductProp;
import com.sandu.api.product.model.ProductStyle;
import com.sandu.api.product.output.ProductPropVO;
import com.sandu.api.product.output.ProductPropVO;
import com.sandu.api.product.output.ProductStyleVO;
import com.sandu.api.product.service.ProductPropService;
import com.sandu.common.ReturnData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;

@Api(description = "产品属性", tags = "productProps")
@RestController
@RequestMapping("/v1/product/props")
public class ProductPropController {

    @Autowired
    private ProductPropService productPropService;


    @ApiOperation(value = "获取产品属性", response = ProductStyleVO.class)
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "产品分类编码", paramType = "query", dataType = "string", required = true),
    })
    public ReturnData getPropsByCode(String code) {
        ReturnData data = ReturnData.builder();
        List<ProductProp> props = productPropService.getProductPropByLongCode(code);
        List<ProductPropVO> vo4 = new ArrayList<>();
        setProps(props, vo4);
        return data.success(true).code(SUCCESS).data(vo4);
    }

    public static void setProps(List<ProductProp> props, List<ProductPropVO> vo4) {
        for(ProductProp tmp : props){
            if(tmp.getIsLeaf() != 0 && tmp.getLevel() == 4){
                //四级节点
                ProductPropVO vo = new ProductPropVO();
                vo.setCode(tmp.getCode());
                vo.setId(tmp.getId().intValue());
                vo.setName(tmp.getName());
                vo.setChildren(new ArrayList<>());
                vo4.add(vo);
            }
        }
        props.removeAll(vo4);
        for(ProductProp tmp : props){
            for(ProductPropVO vo : vo4){
                //关联五级节点到四级下
                if(tmp.getPid().equals(vo.getId())){
                    ProductPropVO voTmp = new ProductPropVO();
                    voTmp.setCode(tmp.getCode());
                    voTmp.setId(tmp.getId().intValue());
                    voTmp.setName(tmp.getName());
                    vo.getChildren().add(voTmp);
                }
            }
        }
    }
}
