package com.sandu.sys.dao;

import com.sandu.common.persistence.CrudDao;
import com.sandu.sys.model.ProductCategory;
import com.sandu.sys.model.query.ProductCategoryQuery;
import com.sandu.sys.model.vo.ProCategoryVO;
import com.sandu.sys.model.vo.ProductCategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao extends
CrudDao<ProductCategory,ProductCategoryQuery,ProductCategoryVo>{

	List<ProductCategoryVo> findFirstList(ProductCategoryQuery query);
    /**
     * 通过分类ids获取分类名称
     * @param categoryIdList
     * @return
     */
    List<ProductCategoryVo> findListByIds(@Param("categoryIdList") List<Integer> categoryIdList);


    /**
     * 通过分类等级，查询分类信息
     * @param level 分类等级
     * @return list 分类集合
     */
    List<ProCategoryVO> getListByLevel(@Param("level") Integer level,@Param("start") Integer start,@Param("pageSize") Integer pageSize);
    
}
