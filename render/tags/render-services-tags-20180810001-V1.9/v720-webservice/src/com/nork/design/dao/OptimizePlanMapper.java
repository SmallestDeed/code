package com.nork.design.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanModel;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductRenderScene;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductDTO;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.ResRenderVideo;

/**
 * @Title: DesignPlanMapper.java
 * @Package com.nork.design.dao
 * @Description:设计方案-设计方案表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-26 11:01:53
 * @version V1.0
 */
@Repository
@Transactional
public interface OptimizePlanMapper {
	int insertDesignPlanOnekey(DesignPlan designPlan);

	DesignPlan selectByPrimaryKeyPlan(Integer id);

	int insertDesignPlanProductOnekey(DesignPlanProduct record);

	int insertResDesignOnekey(ResDesign resDesign);

	ResDesign selectByPrimaryKeyRes(Integer id);

	int countOnekeyPlan(DesignPlan designPlan);

	int insertResRenderPicOnekey(ResRenderPic record);

	int insertResRenderVideoOnekey(ResRenderVideo record);

	int updateByPrimaryKeySelective(ResRenderVideo record);

	int updateByPrimaryKeyPlan(DesignPlan record);

	public DesignPlan getDesignPlanByRecomendPlanIdAndTPId(@Param("recommendedPlanId") Integer recommendedPlanId,
			@Param("designTemplateId") Integer templateId);

	/**
	 * 批量新增数据
	 * 
	 * @author huangsongbo
	 * @param designPlanProductRenderSceneList
	 */
	void insertList(@Param("designPlanProductRenderList") List<DesignPlanProduct> designPlanProductRenderList);

	List<DesignPlanProduct> selectList(DesignPlanProduct designPlan);

	/**
	 * 批量新增设计方案产品
	 * 
	 * @param planProductList
	 */
	void batchAdd(List<DesignPlanProduct> planProductList);

	DesignPlanModel selectDesignPlanInfo(Integer id);

	DesignPlanProduct selectByPrimaryKey(Integer id);

	int updateByPrimaryKeyPlanProduct(DesignPlanProduct record);

	int updateByPrimaryKeyRes(ResDesign resDesign);

	List<DesignPlanProduct> getListByPlanIdAndIsDeleted(@Param("planId") Integer planId,
			@Param("isDeleted") Integer isDeleted);

	int deleteByPlanKey(Integer id);

	int updateByPrimaryKeyPic(ResRenderPic record);

	// -------------分割线-------------
	int costTypeListCount(DesignPlanProduct designPlanProduct);

	/**
	 * 结算类型汇总清单
	 * 
	 * @param designPlanProduct
	 * @return
	 */
	List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);

	/**
	 * 结算汇总清单
	 * 
	 * @param designPlanProduct
	 * @return
	 */
	List<ProductsCost> costList(ProductsCostType productsCostType);

	/**
	 * 结算清单明细
	 * 
	 * @param cost
	 * @return
	 */
	List<ProductCostDetail> costDetail(ProductsCost cost);

	int countOnekeyDesignPlan(ResRenderPic resRenderPic);

	List<ResRenderPic> selectByPlanIdAndTemplateId(@Param("planRecommendedId") Integer planRecommendedId,
			@Param("templateId") Integer templateId, @Param("renderingType") Integer renderingType);

	/**
	 * 查询自动渲染的图
	 * 
	 * @param businessId
	 * @return add by yangzhun
	 */
	List<ResRenderPic> selectAutoPicByBusinessId(Integer businessId);

	/**
	 * 查询自动渲染的视频
	 * 
	 * @param businessId
	 * @return add by yangzhun
	 */
	List<ResRenderVideo> selectAutoVideoByBusinessId(Integer businessId);

	/**
	 * 根据封面图id（渲染任务id）查到自动渲染的视频资源
	 */
	ResRenderVideo selectBySysTaskPicId(Integer sysTaskPicId);

	List<ProductDTO> getProductDTOList(Integer designPlanId);
}
