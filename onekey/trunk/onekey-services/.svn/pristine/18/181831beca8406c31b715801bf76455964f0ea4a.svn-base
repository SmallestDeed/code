package com.nork.onekeydesign.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.DesignPlanProduct;
import com.nork.onekeydesign.model.DesignPlanProductResult;
import com.nork.onekeydesign.model.ProductCostDetail;
import com.nork.onekeydesign.model.ProductsCost;
import com.nork.onekeydesign.model.ProductsCostType;
import com.nork.onekeydesign.model.UnityPlanProduct;
import com.nork.onekeydesign.model.search.DesignPlanProductSearch;

/**   
 * @Title: DesignPlanProductMapper.java 
 * @Package com.nork.onekeydesign.dao
 * @Description:设计方案-设计方案产品库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-26 11:26:11
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanProductMapper {
    int insertSelective(DesignPlanProduct record);

    int updateByPrimaryKeySelective(DesignPlanProduct record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignPlanProduct selectByPrimaryKey(Integer id);
    
    int selectCount(DesignPlanProductSearch designPlanProductSearch);
    
	List<DesignPlanProduct> selectPaginatedList(
			DesignPlanProductSearch designPlanProductSearch);
			
    List<DesignPlanProduct> selectList(DesignPlanProduct designPlanProduct);
    
	/**
	 * 其他
	 * 
	 */
    List<UnityPlanProduct> unityProuctList(Integer templetId);
    
    List<DesignPlanProductResult> planProductList(DesignPlanProduct designPlanProduct);
    
    int planProductCount(DesignPlanProduct designPlanProduct);

    int costListCount(DesignPlanProduct designPlanProduct);

    /**
     * 结算汇总清单
     * @param designPlanProduct
     * @return
     */
    List<ProductsCost> costList(ProductsCostType productsCostType);

    /**
     * 结算清单明细
     * @param cost
     * @return
     */
    List<ProductCostDetail> costDetail(ProductsCost cost);

	int relieveGroupByPlanIdAndplanGroupId(@Param("designPlanId")Integer designPlanId, @Param("planGroupId")String planGroupId);
	
	int costTypeListCount(DesignPlanProduct designPlanProduct);

    /**
     * 结算类型汇总清单
     * @param designPlanProduct
     * @return
     */
    List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);

	/**
	 * 批量新增设计方案产品
	 * @param planProductList
	 */
	void batchAdd(List<DesignPlanProduct> planProductList);

	List<DesignPlanProductResult> planProductListV2(
			DesignPlanProduct designPlanProduct);
	List<DesignPlanProductResult>  getDesignPlanProductList( DesignPlanProduct designPlanProduct);
	DesignPlanProduct findIdByInitProductIdAndPlanId(@Param("initProductId") Integer initProductId, @Param("planId") Integer planId);

    List<DesignPlanProductResult> byPlanIdGroupMainProduct(DesignPlanProduct designPlanProduct);

    List<DesignPlanProduct> selectPlanProductInfo(DesignPlanProductSearch designPlanProductSearch);
    
    /**
	 * 通过设计方案id 获取设计方案产品列表详情
	 * @param id
	 * @return
	 */
	List<DesignPlanProduct> getBaseProductListByPlanId(@Param("planId")Integer planId);

	/**
	 * 批量新增
	 * @author huangsongbo
	 * @param designPlanProductList
	 */
	void insertList(@Param("designPlanProductList") List<DesignPlanProduct> designPlanProductList);

	List<DesignPlanProduct> getListByPlanIdAndIsDeleted(@Param("planId") Integer planId, @Param("isDeleted") Integer isDeleted);

    void batchDelTempDesignProduct(@Param("delProductList") List<Integer> delProductList);
    
    /**
     * 修改可以还原的产品的is_deleted值
     * @param ids
     * @param isDeleted
     */
    void updateIsDeleted(@Param("ids") List<Integer> ids,@Param("isDeleted")Integer isDeleted);


}
