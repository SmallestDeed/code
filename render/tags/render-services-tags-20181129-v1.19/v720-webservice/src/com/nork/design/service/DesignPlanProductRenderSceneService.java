package com.nork.design.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanProductRenderScene;
import com.nork.design.model.DesignPlanProductResult;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import com.nork.design.model.UnityPlanProduct;
import com.nork.design.model.search.DesignPlanProductSearch;

/**
 * copy from DesignPlanProductService
 * @author huangsongbo
 *
 */
public interface DesignPlanProductRenderSceneService {
    public List<DesignPlanProductResult> getDesignPlanProductList(DesignPlanProductRenderScene designPlanProductRenderScene);
	/**
	 * List<DesignPlanProduct> -> List<DesignPlanProductEctype>
	 * @author huangsongbo
	 * @param designPlanProductList
	 * @param planId
	 * @return 
	 */
	boolean copyFromDesignPlanProductList(List<DesignPlanProduct> designPlanProductList, long planId);
	
	/**
	 * 添加设计方案产品副本数据
	 * @author huangsongbo
	 * @param designPlanProductRenderScene
	 */
	public void add(DesignPlanProductRenderScene designPlanProductRenderScene);
	
	/**
	 * 批量添加设计方案产品副本数据
	 * @author huangsongbo
	 * @param designPlanProductRenderSceneList
	 */
	public void add(List<DesignPlanProductRenderScene> designPlanProductRenderSceneList);

	/**
	 * 通过planId删除设计方案产品表(副本)数据
	 * @author huangsongbo
	 * @param id
	 */
	void deleteByPlanId(Integer id);

	/**
	 * 通过设计方案id(副本)获取设计方案产品表(副本)
	 * @author huangsongbo
	 * @param planId
	 * @return
	 */
	List<DesignPlanProductRenderScene> getListByPlanId(Integer planId);
	/**
	 *
	 *通过副本id获得产品数量（副本产品表）
	 * @Author yanghz
	 * @create
	 */
	int getProductCount(Integer designPlanId);

	/**
	 * 设计方案产品汇总
	 * @author huangsongbo
	 * @param designPlanProductRenderScene
	 * @return
	 */
	int planProductCount(DesignPlanProductRenderScene designPlanProductRenderScene);

	/**
	 * 获得设计方案产品列表副本
	 * @author huangsongbo
	 * @param designPlanProductRenderScene
	 * @return
	 */
	List<DesignPlanProductResult> planProductList(DesignPlanProductRenderScene designPlanProductRenderScene);

	/**
	 * 获取设计方案副本费用列表
	 * @param loginUser
	 * @param designPlanProductRenderScene
	 */
	List<ProductsCostType> costList(LoginUser loginUser, DesignPlanProductRenderScene designPlanProductRenderScene);

	int costTypeListCount(DesignPlanProductRenderScene designPlanProductRenderScene);

	/**
	 * 结算类型汇总清单
	 * @param designPlanProductRenderScene
	 * @return
	 */
	List<ProductsCostType> costTypeList(DesignPlanProductRenderScene designPlanProductRenderScene);

	/**
	 * 结算汇总清单
	 * @return
	 */
	List<ProductsCost> costList(ProductsCostType productsCostType);

	/**
	 * 结算清单明细
	 * @param cost
	 * @return
	 */
	List<ProductCostDetail> costDetail(ProductsCost cost);

	int costTypeListCount(DesignPlanProduct designPlanProduct);

	List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);
	
	List<DesignPlanProductResult> getScenePlanProductList(DesignPlanProductRenderScene designPlanProductRenderScene);
}
