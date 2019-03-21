package com.sandu.product.service.impl;

import com.sandu.common.util.FileUploadUtils;
import com.sandu.common.util.Utils;
import com.sandu.product.dao.StructureProductMapper;
import com.sandu.product.model.*;
import com.sandu.product.service.BaseProductService;
import com.sandu.product.service.ProductAttributeService;
import com.sandu.product.service.StructureProductDetailsService;
import com.sandu.product.service.StructureProductService;
import com.sandu.system.model.ResFile;
import com.sandu.system.service.ResFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 产品模块-结构服务
 * @date 20171014
 * @auth pengxuangang
 */
@Service("structureProductService")
public class StructureProductServiceImpl implements StructureProductService {

    @Autowired
    private StructureProductMapper structureProductMapper;

    @Autowired
    private ResFileService resFileService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private StructureProductDetailsService structureProductDetailsService;

    @Override
    public int add(StructureProduct structureProduct) {
        structureProductMapper.insertSelective(structureProduct);
        return structureProduct.getId();
    }

    @Override
    public int update(StructureProduct structureProduct) {
        return structureProductMapper.updateByPrimaryKeySelective(structureProduct);
    }

    @Override
    public int delete(Integer id) {
        return structureProductMapper.deleteByPrimaryKey(id);
    }

    @Override
    public StructureProduct get(Integer id) {
        return structureProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StructureProduct> getList(StructureProduct structureProduct) {
        return structureProductMapper.selectList(structureProduct);
    }

    @Override
    public int getCount(StructureProductSearch structureProductSearch) {
        return structureProductMapper.selectCount(structureProductSearch);
    }

    @Override
    public List<StructureProduct> getPaginatedList(StructureProductSearch structureProductSearch) {
        return structureProductMapper.selectPaginatedList(structureProductSearch);
    }

    @Override
    public SearchStructureProductResult getSearchStructureProductResult(StructureProduct structureProduct, String templetStructureCode) {
        SearchStructureProductResult searchStructureProductResult = new SearchStructureProductResult
                (structureProduct.getId(), structureProduct.getStructureName(), structureProduct.getStructureCode(),
                        "", "", null, "", "");
        /*config(从文件中取)*/
        Integer fileId = structureProduct.getConfigFileId();
        if (fileId != null && fileId > 0) {
            ResFile resFile = resFileService.get(fileId);
            if (resFile != null) {
                String url = Utils.getAbsolutePath(resFile.getFilePath(), Utils.getAbsolutePathType.encrypt);
                String config = FileUploadUtils.getFileContext(url);
                searchStructureProductResult.setStructureConfig(config);
            }
        }
        searchStructureProductResult.setDelStructureCode(templetStructureCode);
        searchStructureProductResult.setPlanStructureRegionMark(structureProduct.getPlanStructureRegionMark());
        /*查询明细*/
        List<SearchStructureProductDetailResult> searchStructureProductDetailResults = new ArrayList<>();
        List<StructureProductDetails> structureProductDetailList = structureProductDetailsService.findAllByStructureId(structureProduct.getId());
        if (structureProductDetailList != null && structureProductDetailList.size() > 0) {
            for (StructureProductDetails structureProductDetail : structureProductDetailList) {
                Integer productId = structureProductDetail.getProductId();
                BaseProduct baseProduct = null;
                if (productId != null && productId > 0) {
                    if (Utils.enableRedisCache()) {
                        baseProduct = BaseProductCacher.get(productId);
                    } else {
                        baseProduct = baseProductService.get(productId);
                    }
                }
                if (baseProduct == null) {
                    continue;
                }
                SearchStructureProductDetailResult searchStructureProductDetailResult = new SearchStructureProductDetailResult();
                searchStructureProductDetailResult.setCameraLook(structureProductDetail.getCameraLook());
                searchStructureProductDetailResult.setCameraView(structureProductDetail.getCameraView());
                searchStructureProductDetailResult.setProductStructureId(structureProduct.getId());
                searchStructureProductDetailResult.setProductId(baseProduct.getId());
                searchStructureProductDetailResult.setProductCode(baseProduct.getProductCode());
                Map<String, String> map = productAttributeService.getPropertyMap(baseProduct.getId());
                searchStructureProductDetailResult.setStructureProductSign(map.get("structureSign"));
                searchStructureProductDetailResults.add(searchStructureProductDetailResult);
            }
        }
        searchStructureProductResult.setStructureProductList(searchStructureProductDetailResults);
        return searchStructureProductResult;
    }

    @Override
    public List<StructureProduct> getStructureObject(StructureProductSearch structureProductSearch) {
        return structureProductMapper.getStructureObject(structureProductSearch);
    }

    @Override
    public List<StructureProduct> getStructuresByRecommendedPlanId(Integer recommendedPlanId) {
        return structureProductMapper.getStructuresByRecommendedPlanId(recommendedPlanId);
    }
}
