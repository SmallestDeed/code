package com.sandu.api.category.service.biz;

import com.sandu.api.category.model.output.CategoryAndIndustryVO;
import com.sandu.api.category.output.ProCategoryListVO;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 产品分类 逻辑 业务层
 * @Date 2018/6/1 0001 10:26
 * @Modified By
 */
@Component
public interface ProCategoryBizService {

    /**
     * 获取企业可见产品范围
     * @author chenqiang
     * @param productVisibilityRange 企业分类ids
     * @return 分类集合
     * @date 2018/5/31 0031 18:21
     */
    List<ProCategoryListVO> getCompanyCategoryList(String productVisibilityRange);

    /**
     * 获取经销商可见产品范围
     * @author chenqiang
     * @param productVisibilityRange 经销商分类ids
     * @return 分类集合
     * @date 2018/5/31 0031 18:21
     */
    List<ProCategoryListVO> getFranchiserCategoryList(Integer companyId, String productVisibilityRange);

    /**
     * 通过企业所属行业，关联企业可见产品范围
     * @param newIndustryList 新所属行业Value
     * @param oldIndustryList 旧所属行业Value
     * @param oldCategoryList 旧可见产品范围
     * @return
     */
    CategoryAndIndustryVO getCategoryByCompanyIndustry(List<Integer> newIndustryList, List<Integer> oldIndustryList, List<Integer> oldCategoryList);
}
