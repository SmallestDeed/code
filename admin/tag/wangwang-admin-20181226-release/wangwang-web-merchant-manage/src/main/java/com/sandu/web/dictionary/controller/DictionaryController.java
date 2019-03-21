package com.sandu.web.dictionary.controller;

import com.google.common.base.Strings;
import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.model.BaseAreaQuery;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.category.model.bo.CategoryTreeNode;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.output.DictionaryVO;
import com.sandu.api.dictionary.output.ProductTypeVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.common.ReturnData;
import com.sandu.constant.Punctuation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;


@Api(description = "数据字典", tags = "dictionary")
@RestController
@RequestMapping("/v1/dictionary")
@Slf4j
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;
    
    @Value("${product.texTure}")
    private String productTexTure;

    @Autowired
    private BaseAreaService areaService;

    @ApiOperation(value = "获取产品售价单位", response = DictionaryVO.class)
    @GetMapping("/sale/unit/list")
    public ReturnData listSaleUnit() {
        ReturnData data = ReturnData.builder();
        List<Dictionary> productUnitPrices = dictionaryService.listByType("productUnitPrice");
        if (productUnitPrices.isEmpty()) {
            return data.success(false).code(NOT_CONTENT).message("为查询到相关数据...");
        }
        List ret = new ArrayList();
        for (Dictionary tmp : productUnitPrices) {
            DictionaryVO vo = new DictionaryVO();
            vo.setId(tmp.getValue());
            vo.setName(tmp.getName());
            ret.add(vo);
        }
        return data.success(true).code(SUCCESS).data(ret);

    }

    @GetMapping("/style/list")
    @ApiOperation(value = "这个应该是设计方案风格", response = DictionaryVO.class)
    public ReturnData designStyle() {
        List<Dictionary> styles = dictionaryService.listByType("productStyle");
        return mergeDicData(styles);
    }

    @GetMapping("/prop/type/check")
    @ApiOperation(value = "校验模型/贴图", response = DictionaryVO.class)
    public ReturnData checkType(String code) {
        Dictionary ret = dictionaryService.getByValueKey(code);
    	checkModelOrTexture(ret);
        return ReturnData.builder().success(true).code(SUCCESS).data(ret != null && "1".equals(ret.getAtt1Info().trim()));
    }
    /**
     * 校验是模型还是贴图产品
     * @param smallType
     * @return
     */
    private Dictionary checkModelOrTexture(Dictionary smallType) {
    	String att1Info = "";
    	if (Objects.isNull(smallType)) {
    		 log.error("小类编码在数据字典不存");
    	}
    	if (Objects.isNull(productTexTure)) {
    		 log.error("参数[product.texTure]未配置");
      	}
    	List<String> productTextList = Arrays.asList(productTexTure.split(Punctuation.COMMA));
    	att1Info =  productTextList.contains(smallType.getValuekey()) ? "0":"1";
    	smallType.setAtt1Info(att1Info);
    	return smallType;
    }

    @GetMapping("/texture/attr")
    public ReturnData textureAttr() {
        List<Dictionary> styles = dictionaryService.listByType("textureAttr");
        return mergeDicData(styles);
    }

    @GetMapping("/texture/textures")
    public ReturnData textureTextures() {
        List<Dictionary> styles = dictionaryService.listByType("texture");
        return mergeDicData(styles);
    }

    @ApiOperation(value = "房屋类型", response = DictionaryVO.class)
    @GetMapping("/group/type/house")
    public ReturnData houseType() {
        List<Dictionary> styles = dictionaryService.listByType("houseType");
        return mergeDicData(styles);
    }

    @ApiOperation(value = "用户类型", response = DictionaryVO.class)
    @GetMapping("/user/type")
    public ReturnData userType() {
        List<Dictionary> userType = dictionaryService.listByType("userType");
        return mergeDicData(userType);
    }

    @ApiOperation(value = "方案报价类型", response = DictionaryVO.class)
    @GetMapping("/plan/decorate/type")
    public ReturnData planDecorateType() {
        List<Dictionary> decorateType = dictionaryService.listByType("decorateType");
        return mergeDicData(decorateType);
    }

    @ApiOperation(value = "房屋空间", response = DictionaryVO.class)
    @GetMapping("/group/area")
    public ReturnData areaSize(Integer value) {
        Dictionary houseType = dictionaryService.getByTypeAndValue("houseType", value);
        List<Dictionary> styles = dictionaryService.listByType(houseType.getValuekey());
        return mergeDicData(styles);
    }

    @ApiOperation(value = "组合类型", response = DictionaryVO.class)
    @GetMapping("/group/type/group")
    public ReturnData groupType() {
        List<Dictionary> styles = dictionaryService.listByType("restaurantAlbuginea");
        return mergeDicData(styles);
    }

    @ApiOperation(value = "产品基本属性", response = DictionaryVO.class)
    @GetMapping("/product/type")
    public ReturnData productType() {
        List<Dictionary> types = dictionaryService.getProductType();
        ProductTypeVO productType = ProductTypeVO.builder().name("产品").keyCode("product_type").typeCode("---").children(new LinkedList<>()).build();
        ProductTypeVO modelType = ProductTypeVO.builder().name("白模").keyCode("model_type").typeCode("---").children(new LinkedList<>()).build();
        types.stream().filter(item -> "productType".equalsIgnoreCase(item.getType())).forEach(type -> {
            List<ProductTypeVO> children = new LinkedList<>();
            List<ProductTypeVO> basicChildren = new LinkedList<>();
            types.stream().filter(smallType -> type.getValuekey().equalsIgnoreCase(smallType.getType()))
                    .forEach(smallType -> {
                        if (smallType.getValuekey().trim().startsWith("basic_")) {
                            basicChildren.add(this.setProductType(smallType));
                        } else {
                            children.add(this.setProductType(smallType));
                        }
                    });
            if (!children.isEmpty()) {
                setProductTypeWithType(productType, type, children);
            }
            if (!basicChildren.isEmpty()) {
                setProductTypeWithType(modelType, type, basicChildren);
            }
        });
//        return ReturnData.builder().code(SUCCESS).success(true).data(Arrays.asList(productType, modelType));
        return ReturnData.builder().code(SUCCESS).success(true).data(Collections.singletonList(productType));
    }

    @ApiOperation(value = "获取字典数据", response = DictionaryVO.class)
    @GetMapping("/with/{type}")
    public ReturnData findWithType(@PathVariable("type") String type) {
        List<Dictionary> dictionaries = dictionaryService.listByType(type);
        return mergeDicData(dictionaries);
    }


    @GetMapping("area/tree")
    public ReturnData listAreaTree() {
        BaseAreaQuery query = new BaseAreaQuery();
        query.setIsDeleted(0);
        List<BaseArea> areas = areaService.queryByOption(query);


        Map<Integer, List<CategoryTreeNode>> collect = areas.stream()
                .filter(it -> it.getLevelId() < 3)
                .map(it -> {
                    CategoryTreeNode node = new CategoryTreeNode();
                    node.setName(it.getAreaName());
                    node.setId(Long.valueOf(it.getAreaCode()));
                    node.setPid(Integer.valueOf(it.getPid()));
                    return node;
                }).collect(Collectors.groupingBy(CategoryTreeNode::getPid));

    collect.forEach((key, value) -> value
                    .forEach(
                            c -> {
                                c.setChildren(collect.get(c.getId().intValue()));
                            }));

        List<CategoryTreeNode> result = Optional.ofNullable(collect.get(0)).orElseGet(Collections::emptyList);
        return ReturnData.builder().code(SUCCESS).data(result);
    }



    private void setProductTypeWithType(ProductTypeVO productType, Dictionary type, List<ProductTypeVO> children) {
        ProductTypeVO productTypeVO = setProductType(type);
        productTypeVO.setChildren(children);
        productTypeVO.setTypeCode(productType.getKeyCode());
        productType.getChildren().add(productTypeVO);
    }

    private ProductTypeVO setProductType(Dictionary type) {
        if(Strings.isNullOrEmpty(type.getAtt1Info())){
            type.setAtt1Info("1");
        }
        return ProductTypeVO.builder()
                .name(type.getName())
                .typeCode(type.getType())
                .keyCode(type.getValuekey())
                .checkModel(Integer.valueOf(1).equals(Integer.valueOf(type.getAtt1Info())))
                .build();
    }

    private ReturnData mergeDicData(List<Dictionary> styles) {
        List<DictionaryVO> vo =
                styles.stream().map(dic ->
                        {
                            DictionaryVO tvo = new DictionaryVO();
                            tvo.setId(dic.getValue());
                            tvo.setName(dic.getName());
                            return tvo;
                        }
                ).collect(Collectors.toList());
        return ReturnData.builder().code(SUCCESS).data(vo);
    }


}
