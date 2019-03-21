package com.nork.repair.job;

import com.nork.common.util.Utils;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.repair.common.CategoryConstant;
import com.nork.repair.model.CategoryProductRel;
import com.nork.repair.model.ProductCategoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class BgWallCategoryMountJob implements Callable {

    Logger logger = LoggerFactory.getLogger(BgWallCategoryMountJob.class);

    private List<ProductCategoryInfo> productCategoryInfoList;
    private ProductCategoryRelService productCategoryRelService;
    private String jobName;

    public BgWallCategoryMountJob(List<ProductCategoryInfo> productCategoryInfoList,
                                  ProductCategoryRelService productCategoryRelService,
                                  String jobName){
        this.productCategoryInfoList = productCategoryInfoList;
        this.productCategoryRelService = productCategoryRelService;
        this.jobName = jobName;
    }

    @Override
    public Object call() throws Exception {
        logger.error("{} 线程{}：开始挂载分类...", Utils.getCurrentDateTime(), jobName);
        long startTime = System.currentTimeMillis();
        List<ProductCategoryRel> categoryRelList = new ArrayList<>();
        for (ProductCategoryInfo productCategory : productCategoryInfoList) {

            Integer bigTypeValue = productCategory.getBigTypeValue();
            Integer smallTypeValue = productCategory.getSmallTypeValue();
            String categoryName = productCategory.getName();
            Integer productId = productCategory.getProductId() == null ? 0 : productCategory.getProductId();

            if (null != bigTypeValue && CategoryConstant.PRODUCT_BIG_TYPE_QIANGM == bigTypeValue && null != smallTypeValue) {
                CategoryProductRel productRel = null;
                switch (smallTypeValue) {
                    case CategoryConstant.PRODUCT_SMALL_TYPE_DIANS:
                        //查询该分类下是否有产品，有不新增，没有则在分类添加该产品
                        productRel = productCategoryRelService.getCategoryProductInfo(CategoryConstant.CATEGORY_DIANS_STYLE_LONG_CODE, categoryName.trim(), productId);
                        break;
                    case CategoryConstant.PRODUCT_SMALL_TYPE_SHAF:
                        productRel = productCategoryRelService.getCategoryProductInfo(CategoryConstant.CATEGORY_SHAF_STYLE_LONG_CODE, categoryName.trim(), productId);
                        break;
                    case CategoryConstant.PRODUCT_SMALL_TYPE_CANT:
                        productRel = productCategoryRelService.getCategoryProductInfo(CategoryConstant.CATEGORY_CANT_STYLE_LONG_CODE, categoryName.trim(), productId);
                        break;
                    case CategoryConstant.PRODUCT_SMALL_TYPE_CHUANGT:
                        productRel = productCategoryRelService.getCategoryProductInfo(CategoryConstant.CATEGORY_CHUANGT_STYLE_LONG_CODE, categoryName.trim(), productId);
                        break;
                    case CategoryConstant.PRODUCT_SMALL_TYPE_BJING:
                        productRel = productCategoryRelService.getCategoryProductInfo(CategoryConstant.CATEGORY_BEIJING_STYLE_LONG_CODE, categoryName.trim(), productId);
                        break;
                    default:
                        break;
                }
                if (null != productRel && productRel.getProductCount() < 1) {
                    if (null != productRel.getCategoryId()) {
                        //初始化对象，后面批量新增
                        ProductCategoryRel productCategoryRel = productCategoryRelService.initCategoryProductRel(productRel.getCategoryId(), productId, productCategory.getStatus());
                        categoryRelList.add(productCategoryRel);
                    } else {
                        logger.error("找不到分类 smallTypeValue:{}, name:{}, productId:{}", smallTypeValue, categoryName, productId);
                        continue;
                    }
                } else {
                    //已存在不处理
                    continue;
                }
            } else {
                logger.error("产品分类：bigTypeValue:{}, smallTypeValue:{}, productId:{}", bigTypeValue, smallTypeValue, productId);
                continue;
            }

            //批量保存
            if (categoryRelList.size()%100 == 0) {
                int saveNumber = productCategoryRelService.batchAdd(categoryRelList);
                if (saveNumber < 1) {
                    logger.error("{} 保存失败！", jobName);
                } else {
                    categoryRelList = new ArrayList<>();
                }
            }
        }

        //最后一批批量保存
        if (categoryRelList.size() > 0) {
            int saveNumber = productCategoryRelService.batchAdd(categoryRelList);
            if (saveNumber < 1) {
                logger.error("{} 保存失败！", jobName);
            }
        }

        logger.error("{} {}挂载产品分类结束 总共耗时：{}ms", jobName, Utils.getCurrentDateTime(), System.currentTimeMillis() - startTime);
        return null;
    }
}
