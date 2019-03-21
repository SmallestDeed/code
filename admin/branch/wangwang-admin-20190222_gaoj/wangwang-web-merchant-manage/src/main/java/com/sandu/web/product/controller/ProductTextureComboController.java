package com.sandu.web.product.controller;

import com.sandu.api.brand.service.BrandService;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.input.ProductSKUInfo;
import com.sandu.api.product.input.ProductTextureAdd;
import com.sandu.api.product.input.ProductTextureUpdate;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.ProductProp;
import com.sandu.api.product.model.bo.ProductBO;
import com.sandu.api.product.model.bo.TexturePicInfo;
import com.sandu.api.product.model.po.ProductUpdatePO;
import com.sandu.api.product.output.ProductTextureVO;
import com.sandu.api.product.output.ProductVO;
import com.sandu.api.product.service.ProductControlService;
import com.sandu.api.product.service.ProductPropService;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.TransStatus;
import com.sandu.api.resmodel.output.ResModelVO;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.input.ResTextureAdd;
import com.sandu.api.restexture.input.ResTextureUpdate;
import com.sandu.api.restexture.output.ResTextureVO;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.api.restexture.service.biz.ResTextureBizService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.service.UserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.constant.Constants;
import com.sandu.constant.ResponseEnum;
import com.sandu.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.common.ReturnData.builder;
import static com.sandu.constant.ResponseEnum.*;
import static com.sandu.util.Commoner.isNotEmpty;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/21 14:29
 */
@Api(description = "产品材质", tags = "productTextureCombo")
@RestController
@RequestMapping("/v1/productTextureCombo")
@Slf4j
public class ProductTextureComboController extends BaseController {
    @Resource
    private ProductBizService productBizService;
    @Resource
    private ProductControlService productControlService;
    @Resource
    private QueueService queueService;
    @Resource
    private ProductPropService productPropService;
    @Resource
    private PlatformService platformService;
    @Resource
    private HttpUtils httpUtils;

    @Resource
    private ResTextureService resTextureService;

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private BrandService brandService;

    @Resource
    private ResTextureBizService resTextureBizService;

    @Resource
    private ResModelService resModelService;

    @Resource
    private ResPicService resPicService;

    @Resource
    private UserService userService;

    @Value("${file.storage.path}")
    private String basePath;

    @Value("${product.sync.index.url}")
    private String syncIndexUrl;


    @RequiresPermissions({"product:add"})
    @ApiOperation(value = "新增产品材质信息")
    @PostMapping
    public ReturnData saveProductTexture(@Valid @RequestBody ProductTextureAdd add, BindingResult result) {
        ReturnData data = builder();
        if (result.hasErrors()) {
            return processValidError(result, data);
        }
        /**
         * 针对产品材质新增进行校验
         */
        //针对床架的处理,产品属性必填。
        if (checkBedProps(add.getProductAdd().getProductSmallType(), add.getProductAdd().getProductSKUInfos())) {
            return builder().code(ResponseEnum.PARAM_ERROR).message("此类产品请输入完整的产品属性！");
        }
        log.info("{}", add.getProductAdd());
        List<Integer> ids = null;
        try {
            //一石多面新增材质贴图
            StringBuilder sb = new StringBuilder();
            add.getResTextureAdd().setInitWithModelId(1);
            String fileLength = add.getResTextureAdd()== null ? "":add.getResTextureAdd().getFileWidth();
            String fileWidth = add.getResTextureAdd()==null ? "":add.getResTextureAdd().getFileLength();
            for(TexturePicInfo textureInfo : add.getResTextureAdd().getTexturePicInfos()){
            	ResTextureAdd addInfo = new ResTextureAdd();
            	BeanUtils.copyProperties(add.getResTextureAdd(), addInfo);
            	addInfo.setThumbnailPath(textureInfo.getThumbnailPath());
            	addInfo.setNormalPicId(textureInfo.getNormalPicId());
            	addInfo.setNormalParam(textureInfo.getNormalParam());
            	addInfo.setThumbnailId(textureInfo.getThumbnailId());
                addInfo.setFileLength(fileLength);
                addInfo.setFileWidth(fileWidth);
                Long textureId = resTextureService.addResTexture(addInfo);
                if (textureId < 1) {
                    return builder().code(ERROR).message("材质插入数据失败...");
                }
                sb.append(textureId).append(",");
            }

//            for(ResTextureAdd textureAdd : add.getResTextureAdd()){
//                textureAdd.setInitWithModelId(1);
//                Long textureId = resTextureService.addResTexture(textureAdd);
//                if (textureId < 1) {
//                    return builder().code(ERROR).message("材质插入数据失败...");
//                }
//                sb.append(textureId).append(",");
//            }

            //不是一石多面
//            add.getResTextureAdd().setInitWithModelId(1);
//            Long textureId = resTextureService.addResTexture(add.getResTextureAdd());
//            if (textureId < 1) {
//                return builder().code(ERROR).message("材质插入数据失败...");
//            }

            //关联材质信息
            add.getProductAdd().setMaterialPicIds(sb.toString().substring(0,sb.toString().lastIndexOf(",")));
//            add.getProductAdd().setMaterialPicIds(textureId.toString());
//            add.getProductAdd().setTextureId(textureId.intValue());
            ids = productBizService.saveProductBiz(add.getProductAdd());
            if (ids == null || ids.isEmpty()) {
                sycMessageDoSend(SyncMessage.ACTION_ADD, ids);
                return builder().code(ERROR).message("产品插入数据失败...");
            }

        } catch (Exception e) {
            return builder().code(ERROR).message(e.getMessage());
        }

        return builder().code(SUCCESS).data(data);
    }



    private boolean checkBedProps(String smallType, List<ProductSKUInfo> skus) {
        List<String> checkType = Arrays.asList("dbbd", "sfbd");
        if (checkType.contains(smallType)) {
            if (skus == null || skus.isEmpty()) {
                return true;
            }
            List<Integer> propIds = skus.get(0).getPropIds();
            if (propIds == null) {
                return true;
            }
            List<ProductProp> prop = productPropService.getProductPropByLongCode(smallType);
            if (prop != null) {
                return prop.stream().filter(item -> item.getLevel().equals(4)).count() != propIds.size();
            }
        }
        return false;
    }

    @RequiresPermissions({"product:view"})
    @ApiOperation(value = "根据产品ID查询产品", response = ProductTextureVO.class)
    @GetMapping(value = "/{platformType}/{productId}/info")
    public Object getProductInfoById(@ApiParam(value = "要查看的产品ID", required = true) @PathVariable long productId,
                                     @ApiParam(value = "所查询的渠道:产品库library线上online/渠道channel", required = true)
                                     @PathVariable String platformType) {
        ReturnData data = builder();
        ProductBO bo = productBizService.findProductInfoByProductId(productId, platformType);

        if (bo == null) {
            return data.success(false).code(NOT_CONTENT).message("未查询到相关数据...");
        }
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(bo, vo);
        vo.setCategoryCodes(bo.getCategoryCodeList());
        vo.setSaleUnitId(bo.getSaleUnitValue());
        if (bo.getPrice() != null) {
            vo.setPrice(bo.getPrice());
        }
        if (bo.getAdvicePrice() != null) {
            vo.setAdvicePrice(bo.getAdvicePrice());
        }
        vo.setCode(bo.getCode());
        vo.setName(bo.getName());
        if (bo.getModel() != null) {
            ResModelVO model = ResModelVO.builder().modelCode(bo.getModel().getModelCode())
                    .modelName(bo.getModel().getModelName())
                    .thumbPicPath(bo.getModel().getThumbPicPath())
                    .length(bo.getModel().getLength())
                    .width(bo.getModel().getWidth())
                    .height(bo.getModel().getHeight())
                    .modelId(bo.getModel().getId()).build();
            vo.setModel(model);
        }
        if (bo.getPicId() != null) {
            vo.setDefaultPicId(bo.getPicId().toString());
        }
        vo.setDefaultPicPath(bo.getPicPath());
        //风格
        vo.setBaseStyleId(bo.getProStyleId());
        vo.setBaseStyleName(bo.getProStyleName());
        if (vo.getSeriesId().equals(0)) {
            vo.setSeriesId(null);
        }
        if (vo.getModelingId().equals(0)) {
            vo.setModelingId(null);
        }

        //材质
        ResTextureVO info = resTextureBizService.getResTextureDetail(Long.valueOf(vo.getMaterialPicIds()));
        if (!isNotEmpty(info)) {
            return data.success(false).code(NOT_CONTENT).message("未查询到相关数据...");

        }
        ProductTextureVO resultVO = new ProductTextureVO();
        resultVO.setProductVO(vo);
        resultVO.setResTextureVO(info);
        return ReturnData.builder().code(SUCCESS).data(resultVO);

    }


    @RequiresPermissions({"product:edit"})
    @ApiOperation(value = "产品库更新产品", response = ReturnData.class)
    @PutMapping("/library")
    public ReturnData updateProduct(@Valid @RequestBody ProductTextureUpdate update, BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        StringBuilder sb = new StringBuilder();

        for(TexturePicInfo info : update.getResTextureUpdate().getTexturePicInfos()) {
        	 String fileLength = update.getResTextureUpdate()== null ? "":update.getResTextureUpdate().getFileLength();
             String fileWidth = update.getResTextureUpdate()==null ? "":update.getResTextureUpdate().getFileWidth();
        	if(info.getTextureId() == 0) {
                //新增
                ResTextureAdd textureAdd =
                        ResTextureAdd.builder().initWithModelId(1)
                        .companyId(update.getResTextureUpdate().getCompanyId())
                        .fileLength(fileLength)
                        .fileWidth(fileWidth)
                        .normalParam(info.getNormalParam())
                        .normalPicId(info.getNormalPicId())
                        .thumbnailId(info.getThumbnailId())
                        .thumbnailPath(info.getThumbnailPath())
                        .textureAttrValue(update.getResTextureUpdate().getTextureAttrValue())
                        .textureName(update.getProductUpdate().getName())
                        .texture(update.getResTextureUpdate().getTexture()).build();
                Long textureId = resTextureService.addResTexture(textureAdd);
                if (textureId < 1) {
                    return data.success(false).message("更新失败").code(ERROR);
                }
                sb.append(textureId).append(",");
            }else {
                //修改
                update.getResTextureUpdate().setTextureId(info.getTextureId());
                update.getResTextureUpdate().setNormalParam(info.getNormalParam());
                update.getResTextureUpdate().setNormalPicId(info.getNormalPicId());
                update.getResTextureUpdate().setThumbnailId(info.getThumbnailId());
                update.getResTextureUpdate().setThumbnailPath(info.getThumbnailPath());
                update.getResTextureUpdate().setFileLength(fileLength);
                update.getResTextureUpdate().setFileWidth(fileWidth);
                if (resTextureService.saveResTexture(update.getResTextureUpdate()) < 1) {
                    return data.success(false).message("更新失败").code(ERROR);
                }
                sb.append(info.getTextureId()).append(",");
            }
        }


//        if (resTextureService.saveResTexture(update.getResTextureUpdate()) < 1) {
//            return data.success(false).message("更新失败").code(ERROR);
//        }

        ProductUpdatePO updatePo = new ProductUpdatePO();


        updatePo.setMaterialPicIds(sb.toString().substring(0,sb.toString().lastIndexOf(",")));
//        update.getProductUpdate().setTextureId(update.getResTextureUpdate().getTextureId());
        BeanUtils.copyProperties(update.getProductUpdate(), updatePo);
        updatePo.setEditType("library");
        return doUpdate(updatePo, data);

    }

    private ReturnData doUpdate(@Valid @RequestBody ProductUpdatePO update, ReturnData data) {
        //更新产品
        update.setProduct(new Product());
        boolean b = productBizService.updateProductBiz(update);
        if (b) {
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, Collections.singletonList(update.getId().intValue()));
            return data.success(true).code(SUCCESS);
        }
        return data.success(false).message("更新失败").code(ERROR);
    }

    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("P-" + System.currentTimeMillis());
        message.setModule(SyncMessage.MODULE_PRODUCT);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.send(message);
    }

}
