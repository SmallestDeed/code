package com.sandu.web.product.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.input.*;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.ProductProp;
import com.sandu.api.product.model.bo.ProductBO;
import com.sandu.api.product.model.bo.ProductListBO;
import com.sandu.api.product.model.po.HardProductUpdatePO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.model.po.ProductUpdatePO;
import com.sandu.api.product.output.ProductListVO;
import com.sandu.api.product.output.ProductVO;
import com.sandu.api.product.service.ProductControlService;
import com.sandu.api.product.service.ProductPropService;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.resmodel.output.ResModelVO;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.constant.Constants;
import com.sandu.constant.Punctuation;
import com.sandu.constant.ResponseEnum;
import com.sandu.util.HttpUtils;
import com.sandu.util.RabbitUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sandu.common.ReturnData.builder;
import static com.sandu.constant.ResponseEnum.*;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/21 14:29
 */
@Api(description = "产品", tags = "product")
@RestController
@RequestMapping("/v1/product")
@Slf4j
public class ProductController extends BaseController {
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
    private RabbitUtils rabbitUtils;

    @Value("${product.sync.index.url}")
    private String syncIndexUrl;


    @RequiresPermissions({"product:view"})
    @ApiOperation(value = "根据产品ID查询产品", response = ProductVO.class)
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
        return data.success(true).data(vo);

    }

    @RequiresPermissions({"product:view", "platform:factory", "platform:design"})
    @ApiOperation(value = "产品库产品列表", response = ProductListVO.class)
    @GetMapping("/list/library")
    public ReturnData queryLibraryProductList(@Valid ProductQuery productQuery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return processValidError(bindingResult, builder());
        }
        productQuery.setQueryType("library");
        return listProduct(productQuery);
    }

    @RequiresPermissions({"biz:product:view", "platform:2b"})
    @ApiOperation(value = "渠道产品列表", response = ProductListVO.class)
    @GetMapping("/list/channel")
    public ReturnData queryChannelProductList(@Valid ProductQuery productQuery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return processValidError(bindingResult, builder());
        }
        productQuery.setQueryType("channel");
        return listProduct(productQuery);
    }

    @RequiresPermissions({"client:product:view", "platform:2c"})
    @ApiOperation(value = "线上产品列表", response = ProductListVO.class)
    @GetMapping("/list/online")
    public ReturnData queryOnlineProductList(@Valid ProductQuery productQuery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return processValidError(bindingResult, builder());
        }
        productQuery.setQueryType("online");
        return listProduct(productQuery);
    }

    private ReturnData listProduct(ProductQuery productQuery) {
        log.debug("Query: {}", productQuery);
        if (StringUtils.isBlank(productQuery.getOrderField())) {
            productQuery.setOrderField("id");
        }
        if (StringUtils.isBlank(productQuery.getOrderMethod())) {
            productQuery.setOrderMethod("desc");
        }
        ReturnData data = builder();
        ProductQueryPO productQueryPO = new ProductQueryPO();
        if ("product_type".equals(productQueryPO.getProductType()) || "model_type".equals(productQueryPO.getProductType())) {
            productQueryPO.setProductType(null);
        }
        BeanUtils.copyProperties(productQuery, productQueryPO);
        productQueryPO.setPutawayState(productQuery.getPutawayState());
        productQueryPO.setAllotState(productQuery.getAllotState());
        productQueryPO.setModelNumber(productQuery.getProductModelNumber());
        PageInfo<ProductListBO> productListBos = productBizService.queryProductByParam(productQueryPO);
        List<ProductListVO> rets = new ArrayList<>();
        log.info("list:[]", rets);
        if (productListBos != null) {
            for (ProductListBO bo : productListBos.getList()) {
                ProductListVO vo = new ProductListVO();
                BeanUtils.copyProperties(bo, vo);
                vo.setBrandName(bo.getBrandName());
                vo.setCode(bo.getProductCode());
                vo.setCategoryNames(bo.getCategoryNames());
                vo.setGtmCreat(bo.getGmtCreate());
                vo.setModelCode(bo.getModelCode());
                vo.setPicPath(bo.getPicPath());
                vo.setStatus2b(bo.getStatus2b());
                vo.setStatus2c(bo.getStatus2c());
                vo.setIsCreatedTexture(bo.getIsCreatedTexture());
                rets.add(vo);
            }
        }
        if (!rets.isEmpty()) {
            return data.total(productListBos.getTotal()).list(rets).code(SUCCESS).success(true);
        }
        return data.code(NOT_CONTENT).message("未查询到相关数据...").success(false).list(Collections.emptyList()).total(0);
    }


    @RequiresPermissions({"product:add"})
    @ApiOperation(value = "新增产品")
    @PostMapping("")
    public ReturnData saveProduct(@Valid @RequestBody ProductAdd add, BindingResult result) {
        ReturnData data = builder();
        if (result.hasErrors()) {
            return processValidError(result, data);
        }
        //针对床架的处理,产品属性必填。
        if (checkBedProps(add.getProductSmallType(), add.getProductSKUInfos())) {
            return builder().code(ResponseEnum.PARAM_ERROR).message("此类产品请输入完整的产品属性！");
        }
        log.info("{}", add);
        List<Integer> ids = null;
        try {
            if (add.getProductSKUInfos() != null) {
                List<ProductSKUInfo> distinctSKUInfo = add.getProductSKUInfos().stream().distinct().collect(Collectors.toList());
                add.setProductSKUInfos(distinctSKUInfo);
            }
            ids = productBizService.saveProductBiz(add);
            if (ids != null && !ids.isEmpty()) {
                sycMessageDoSend(SyncMessage.ACTION_ADD, ids);
                return builder().success(true).code(SUCCESS).data(data);
            }
        } catch (Exception e) {
            log.error("新增产品失败,exception:{}", e.getMessage());
            e.printStackTrace();
            return builder().code(ERROR).success(false).message(e.getMessage());
        }

        return builder().code(ERROR).success(false).message("插入数据失败...");
    }

    private boolean checkBedProps(String smallType, List<ProductSKUInfo> skus) {
        List<String> checkType = Arrays.asList("dbbd", "sfbd");
        if (checkType.contains(smallType)) {
            if (skus == null || skus.isEmpty()) {
                return true;
            }
            Integer size = skus.stream().map(it -> it.getPropIds().size()).min(Integer::compareTo).orElse(0);
            List<ProductProp> prop = productPropService.getProductPropByLongCode(smallType);
            if (prop != null) {
                return prop.stream().filter(item -> item.getLevel().equals(4)).count() != size;
            }
        }
        return false;
    }

    @RequiresPermissions({"product:edit"})
    @ApiOperation(value = "产品库更新产品", response = ReturnData.class)
    @PutMapping("/library")
    public ReturnData updateProduct(@Valid @RequestBody ProductLibraryUpdate update, BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        ProductUpdatePO updatePo = new ProductUpdatePO();
        BeanUtils.copyProperties(update, updatePo);
        updatePo.setEditType("library");
        return doUpdate(updatePo, data);

    }

    @RequiresPermissions({"product:edit"})
    @ApiOperation(value = "产品库更新定制产品", response = ReturnData.class)
    @PutMapping("/library/hard")
    public ReturnData updateHardProduct(@Valid @RequestBody HardProductLibraryUpdate update, BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        HardProductUpdatePO updatePo = new HardProductUpdatePO();
        BeanUtils.copyProperties(update, updatePo);
        updatePo.setEditType("library");
        log.debug("ProductSKUInfos before distinct:{}", updatePo.getProductSKUInfos().size());
        List<ProductSKUInfo> distinctSKUInfo = updatePo.getProductSKUInfos().stream().distinct().collect(Collectors.toList());
        updatePo.setProductSKUInfos(distinctSKUInfo);
        log.debug("ProductSKUInfos after distinct:{}", updatePo.getProductSKUInfos().size());
        List<Integer> ids = productBizService.updateHardProductBiz(updatePo);
        if (!ids.isEmpty()) {
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, Collections.singletonList(update.getId().intValue()));
        }
        return data.success(true).message("更新成功").code(SUCCESS);

    }

    @RequiresPermissions({"biz:product:edit"})
    @ApiOperation(value = "渠道更新产品")
    @PutMapping("/channel")
    public ReturnData update2bProduct(@Valid @RequestBody ProductManagerUpdate update, BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        ProductUpdatePO updatePo = new ProductUpdatePO();
        BeanUtils.copyProperties(update, updatePo);
        updatePo.setEditType("channel");
        return doUpdate(updatePo, data);

    }

    @RequiresPermissions({"client:product:edit"})
    @ApiOperation(value = "线上更新产品")
    @PutMapping("/online")
    public ReturnData update2cProduct(@Valid @RequestBody ProductManagerUpdate update, BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        ProductUpdatePO updatePo = new ProductUpdatePO();
        BeanUtils.copyProperties(update, updatePo);
        updatePo.setEditType("online");
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


    @RequiresPermissions({"biz:product:del"})
    @ApiOperation(value = "删除产品(ID)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productIds", value = "产品ID集,如:123,345", paramType = "query", dataType = "string", required = true),
    })
    @DeleteMapping("/channel")
    public ReturnData deleteChannelProductById(String productIds) {
        return doDeleteProduct(productIds, "2b");
    }

    @RequiresPermissions({"client:product:del"})
    @ApiOperation(value = "删除产品(ID)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productIds", value = "产品ID集,如:123,345", paramType = "query", dataType = "string", required = true),
    })
    @DeleteMapping("/online")
    public ReturnData deleteOnlineProductById(String productIds) {
        return doDeleteProduct(productIds, "2c");
    }

    @ApiOperation(value = "删除产品(ID)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productIds", value = "产品ID集,如:123,345", paramType = "query", dataType = "string", required = true),
    })

    @RequiresPermissions({"product:del"})
    @DeleteMapping("/library")
    public ReturnData deleteProductById(String productIds) {
        return doDeleteProduct(productIds, "library");
    }

    private ReturnData doDeleteProduct(String productIds, String paltformType) {

        ReturnData data = builder();
        if (productIds != null) {
            String[] split = productIds.split(Punctuation.COMMA);
            List<Integer> ids = new ArrayList<>();
            for (String s : split) {
                Integer id = new Integer(s);
                ids.add(id);
            }
            //校验商品是否已加入拼团活动
            Integer result = productBizService.validGroupActivity(ids);
            if(result > 0) {
                return data.code(ERROR).message("进行中的拼团活动商品不能删除").success(false);
            }
            List<Integer> ret = productBizService.deleteProductsByIds(ids, paltformType);
            if (!ret.isEmpty()) {
                sycMessageDoSend(SyncMessage.ACTION_DELETE, ret);
                List<Integer> goodIds = productBizService.getGoodsIdsByProductIds(ret);
                if (!goodIds.isEmpty()){
                    sycMessageDoSendForGoods(SyncMessage.ACTION_UPDATE, goodIds);
                }
                productBizService.removeGroupActivity(ids);
                return builder().code(SUCCESS).data(data);
            }
        }
        return data.success(false).code(ERROR).message("删除数据失败....");
    }

    @RequiresPermissions({"product:manage"})
    @ApiOperation(value = "分配产品到渠道")
    @PutMapping("/allot")
    public ReturnData allotProducts(@Valid @RequestBody ProductAllotUpdate update,
                                    BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        List<Integer> ids = update.getProductIds();
        List<String> allotPlatformType = Arrays.asList(update.getPlatformType().split(Punctuation.COMMA));
        if (allotPlatformType.contains("") && allotPlatformType.size() == 1) {
            allotPlatformType = Collections.emptyList();
        }
        //分配
        if (!allotPlatformType.isEmpty()) {
            productBizService.allotProduct(ids, allotPlatformType, "doAllot");
        }
        //取消分配
        List<String> cancelPlatformType = new ArrayList<>();
        cancelPlatformType.add("2b");
        cancelPlatformType.add("2c");
        cancelPlatformType.removeAll(allotPlatformType);
        if (!cancelPlatformType.isEmpty()) {
            productBizService.allotProduct(ids, cancelPlatformType, "cancelAllot");
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, ids);
        }
        return data.success(true).code(SUCCESS);
    }

    @RequiresPermissions({"biz:product:exhibition"})
    @ApiOperation(value = " 渠道产品上下架")
    @PutMapping("/put/channel")
    public ReturnData putChannelProducts(@Valid @RequestBody ProductPutUpdate update, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, builder());
        }
        update.setPlatformType("2b");
        return doPutProduct(update);
    }

    @RequiresPermissions({"client:product:exhibition"})
    @ApiOperation(value = " 线上产品上下架")
    @PutMapping("/put/online")
    public ReturnData putOnlineProducts(@Valid @RequestBody ProductPutUpdate update, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return processValidError(validResult, builder());
        }
        update.setPlatformType("2c");
        return doPutProduct(update);
    }


    @RequiresPermissions({"product:exhibition"})
    @ApiOperation(value = "内容库产品上下架")
    @PutMapping("/put/library")
    public ReturnData putlibraryProducts(@Valid @RequestBody ProductUpDown update, BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        List<Integer> ids = update.getProductIds();

        //校验商品是否已加入拼团活动
        boolean flag = true;
        boolean activityFlag = false;
        List<String> platformIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(update.getPlatformIds()));
        List<Integer> putAways = productBizService.getProductPutInfo(ids);

        for(Integer putAway : putAways) {
            if(putAway == null) {
                putAway = 0;
            }
            if(!platformIds.contains("14") && putAway == 1) {
                flag = false;
                activityFlag = true;
            }else if(platformIds.contains("14") && putAway == 0) {
                flag = false;
            }
            if(!flag) {
                Integer result = productBizService.validGroupActivity(ids);
                if(result > 0) {
                    return data.code(ERROR).message("进行中的拼团活动商品不能上下架").success(false);
                }else{
                    flag = true;
                }
            }
        }

        List<String> upPlatformId = Arrays.asList(update.getPlatformIds().split(Punctuation.COMMA));
        if (upPlatformId.contains("") && upPlatformId.size() == 1) {
            upPlatformId = Collections.emptyList();
        }
        //获取所有C端的数据
        List<String> types = new ArrayList<>();
        types.add("2c");
        Map<Integer, String> tocPlatFormMap = platformService.getPlatformIdAndNameByBussinessType(types.get(0));
        //获取所有B端的数据
        types.clear();
        types.add("2b");
        Map<Integer, String> tobPlatFormMap = platformService.getPlatformIdAndNameByBussinessType(types.get(0));
        List<String> tobPlatformIds = new ArrayList<String>();
        tobPlatformIds.addAll(upPlatformId);
        List<String> tocPlatformIds = new ArrayList<String>();
        tocPlatformIds.addAll(upPlatformId);
        //取需要上架到C端的平台,取交集
        tocPlatformIds.retainAll(tocPlatFormMap.keySet().stream().map(key -> String.valueOf(key)).collect(Collectors.toList()));
        //取需要上架到B端的平台,取交集
        tobPlatformIds.retainAll(tobPlatFormMap.keySet().stream().map(key -> String.valueOf(key)).collect(Collectors.toList()));
        // 分配并上架
        if (!tocPlatformIds.isEmpty() || !tobPlatformIds.isEmpty()) {
            productBizService.upAndDownProduct(ids, tocPlatformIds, tobPlatformIds, Platform.PUT_STATUS_UP);
        }
        //合并所有平台
        tocPlatFormMap.putAll(tobPlatFormMap);
        List<String> allPutPlatForms = tocPlatFormMap.keySet().stream().map(key -> String.valueOf(key)).collect(Collectors.toList());
        //取当前需要下架的平台ID
        allPutPlatForms.removeAll(upPlatformId);
        //从C端移除B端平台
        for (Iterator<Map.Entry<Integer, String>> it = tocPlatFormMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, String> platformMap = it.next();
            if (tobPlatFormMap.containsKey(platformMap.getKey())) {
                it.remove();
            }
        }
        // 取消分配并下架
        if (!allPutPlatForms.isEmpty()) {
            tocPlatformIds.clear();
            tocPlatFormMap.forEach((k, v) -> {
                if (allPutPlatForms.contains(String.valueOf(k))) {
                    tocPlatformIds.add(String.valueOf(k));
                }
            });
            tobPlatformIds.clear();
            tobPlatFormMap.forEach((k, v) -> {
                if (allPutPlatForms.contains(String.valueOf(k))) {
                    tobPlatformIds.add(String.valueOf(k));
                }
            });
            if (!tocPlatformIds.isEmpty() || !tobPlatformIds.isEmpty()) {
                productBizService.upAndDownProduct(ids, tocPlatformIds, tobPlatformIds, Platform.PUT_STATUS_DOWN);
            }
        }
        update.getProductIds().addAll(productBizService.getAllProductIds(update.getProductIds()));

        //更新拼团活动状态
        if(flag && activityFlag){
            productBizService.updateGroupActivity(ids);
        }

        sycMessageDoSend(SyncMessage.ACTION_UPDATE, update.getProductIds());
        return data.success(true).code(SUCCESS);
    }

    private ReturnData doPutProduct(@Valid @RequestBody ProductPutUpdate update) {
        List<String> platformType = new ArrayList<>();
        platformType.add(update.getPlatformType());
        boolean flag;
        flag = productBizService.putawayProduct(update.getProductIds(), platformType, update.getPlatformIds(), update.getPutStatus());
        if (flag) {
            sycMessageDoSend(SyncMessage.ACTION_UPDATE, update.getProductIds());
            return builder().success(true).message("操作成功");
        }
        return builder().success(false).message("参数有误");
    }


    @RequiresPermissions({"product:manage"})
    @ApiOperation(value = "公开/取消公开产品")
    @PutMapping("/secrecy")
    public ReturnData changeProductSecrecy(@Valid @RequestBody ProductControlUpdate productControlUpdate,
                                           BindingResult validResult) {
        ReturnData data = builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        Integer secrecy = productControlUpdate.getProductSecrecyStatus();

        boolean flag = productControlService.batchChangeProductSecrecy(productControlUpdate.getProductIds(),
                secrecy);
        if (!flag) {
            return builder().code(ERROR).success(false).message("操作失败...");
        }
        sycMessageDoSend(SyncMessage.ACTION_UPDATE, productControlUpdate.getProductIds());
        return data.success(true).code(SUCCESS);
    }

    @ApiOperation(value = "产品型号推荐", response = ReturnData.class)
    @GetMapping("/modelNumber/recommend")
    public ReturnData recommendsProductWord(Integer brandId, String modelNumber, Integer companyId) {
        if (StringUtils.isBlank(modelNumber) && brandId == null && companyId == null) {
            return ReturnData.builder().data(Collections.emptyList()).code(SUCCESS).success(true);
        }

        List<String> rets = productBizService.getProductNumberWordRecommends(modelNumber, brandId, companyId);
        return ReturnData.builder().data(rets.stream().map(item -> {
            Map<String, String> tmp = new HashMap<>();
            tmp.put("value", item);
            return tmp;
        }).toArray()).code(SUCCESS).success(true);
    }

    @RequiresPermissions({"product:sync"})
    @ApiOperation(value = "同步产品", response = ReturnData.class)
    @GetMapping("/index/sync")
    public ReturnData syncIndex(String productIdsStr) throws IOException {
        if (Strings.isNullOrEmpty(productIdsStr)) {
            return ReturnData.builder().code(SUCCESS).success(false);
        }
        List<Integer> productIds = Stream.of(productIdsStr.split(Punctuation.COMMA)).map(Integer::new).collect(Collectors.toList());
        if (productIds == null || productIds.isEmpty()) {
            return ReturnData.builder().code(SUCCESS).success(false);
        }
        productIds.addAll(productBizService.getAllProductIds(productIds));
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String url = "https://fullsearchsync.ci.sanduspace.com/fullsearch-app/sync/product/syncProductInfoListImmediately";
        HttpPost httpPost = new HttpPost(syncIndexUrl);

        // 设置请求的header
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        // 设置请求的参数
        StringEntity entity = new StringEntity(productIds.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpResponse execute = httpClient.execute(httpPost);
        HttpEntity entity1 = execute.getEntity();
        String massage = EntityUtils.toString(entity1);

        return ReturnData.builder().code(SUCCESS).success(true).message(massage);
    }

    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        rabbitUtils.sycMessageDoSend(ids, messageAction, "P-" + System.currentTimeMillis(),
                SyncMessage.MODULE_PRODUCT, Constants.PLATFORM_CODE_MERCHANT_MANAGE);
    }

    private void sycMessageDoSendForGoods(Integer messageAction, List<Integer> ids) {
        rabbitUtils.sycMessageDoSend(ids, messageAction, "G-" + System.currentTimeMillis(),
                SyncMessage.GOODS_MODULE, Constants.PLATFORM_CODE_MINI_APP);
    }
}
