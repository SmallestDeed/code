package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.common.async.ProductSaveParameter;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.*;
import com.nork.onekeydesign.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.onekeydesign.model.search.DesignPlanProductSearch;
import com.nork.onekeydesign.model.vo.UnityDesignPlanRecommended;
import com.nork.onekeydesign.service.impl.DesignPlanProductServiceImpl.costListEnum;

import com.nork.product.model.result.DesignProductResult;
import net.sf.json.JSONObject;

/**   
 * @Title: DesignPlanProductService.java 
 * @Package com.nork.onekeydesign.service
 * @Description:设计方案-设计方案产品库Service
 * @createAuthor pandajun 
 * @CreateDate 2015-06-26 11:26:11
 * @version V1.0   
 */
public interface DesignPlanProductService {
	/**
	 * 新增数据
	 *
	 * @param designPlanProduct
	 * @return  int 
	 */
	public int add(DesignPlanProduct designPlanProduct);

	/**
	 *    更新数据
	 *
	 * @param designPlanProduct
	 * @return  int 
	 */
	public int update(DesignPlanProduct designPlanProduct);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanProduct 
	 */
	public DesignPlanProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designPlanProduct
	 * @return   List<DesignPlanProduct>
	 */
	public List<DesignPlanProduct> getList(DesignPlanProduct designPlanProduct);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlanProduct
	 * @return   int
	 */
	public int getCount(DesignPlanProductSearch designPlanProductSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanProduct
	 * @return   List<DesignPlanProduct>
	 */
	public List<DesignPlanProduct> getPaginatedList(
				DesignPlanProductSearch designPlanProducttSearch);

	/**
	 * 其他
	 * 
	 */
	public List<UnityPlanProduct> unityProductList(Integer templetId);
	/**
	 *    设计方案产品查询
	 *
	 * @param  designPlanProduct
	 * @return   List<DesignPlanProduct>
	 */
	public List<DesignPlanProductResult> planProductList(DesignPlanProduct designPlanProductt);
	/**
	 *    设计方案产品汇总
	 *
	 * @param  designPlanProduct
	 * @return   List<DesignPlanProduct>
	 */
	public int planProductCount(DesignPlanProduct designPlanProductt);

	int costListCount(DesignPlanProduct designPlanProduct);

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
	/**
	 * 通过挂接点，更新产品列表
	 */
	public int updateDesignPlanProduct(Integer designPlanId,String posIndexPath,Integer productId,String posName,String context, Integer bindProductId,LoginUser loginUser);

	/**
	 * 解除设计方案产品中的组关系
	 * @author huangsongbo
	 * @param designPlanId
	 * @param planGroupId
	 * @return
	 */
	public int relieveGroupByPlanIdAndplanGroupId(Integer designPlanId, String planGroupId);
	
	int costTypeListCount(DesignPlanProduct designPlanProduct);

	/**
	 * 结算汇总清单
	 * @param designPlanProduct
	 * @return
	 */
	List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);

	/**
	 * 批量新增设计方案产品
	 * @param planProductList
	 */
	public void batchAdd(List<DesignPlanProduct> planProductList);

	public List<DesignPlanProductResult> planProductListV2(
			DesignPlanProduct designPlanProduct);
	List<DesignPlanProductResult>  getDesignPlanProductList( DesignPlanProduct designPlanProduct);
	public DesignPlanProduct findIdByInitProductIdAndPlanId(Integer id, Integer planId);

	/**
	 * 获取设计方案费用列表
	 * @param loginUser
	 * @param designPlanProduct
	 */
	List<ProductsCostType> costList(LoginUser loginUser, DesignPlanProduct designPlanProduct, costListEnum type);

	public JSONObject analysisJson(Integer designTempletId,Integer templetId,DesignPlan designPlan, String filePath, LoginUser loginUser) throws Exception;

	List<DesignPlanProductResult> getByPlanIdGroupMainProduct(DesignPlanProduct designPlanProduct);

	/**
	 * 结构背景墙替换保存
	 * @author xiaoxc
	 * @param productJson
	 * @param planProductIds
	 * @return ResponseEnvelope
	 */
	public ResponseEnvelope updateStructureBjWallProducts(String productJson, String planProductIds, String msgId, LoginUser loginUser);

	/**
	 * 根据设计方案id查找设计方案产品列表
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<DesignPlanProduct> getListByPlanId(Integer planId);

	/**
	 * 保存设计方案产品列表(DesignPlanProductList)
	 * @author huangsongbo
	 * @param designPlanProductList
	 * @param id
	 */
	public void saveDesignPlanProductList(List<DesignPlanProduct> designPlanProductList, long id);

	/**
	 * 批量保存设计方案产品列表
	 * @author huangsongbo
	 * @param designPlanProductList
	 */
	void save(List<DesignPlanProduct> designPlanProductList);

	/**
	 * List<DesignPlanProductRenderScene> 转化为List<DesignPlanProduct>
	 * @author huangsongbo
	 * @param designPlanProductRenderSceneList
	 * @return
	 */
	public List<DesignPlanProduct> getDesignPlanProductListByDesignPlanProductRenderSceneList(
			List<DesignPlanProductRenderScene> designPlanProductRenderSceneList);
	

	/**
	 * 通过设计方案id 获取设计方案产品列表详情
	 * @param id
	 * @return
	 */
	public List<DesignPlanProduct> getBaseProductListByPlanId(Integer id);

	/**
	 * describe: 批量删除设计方案产品
	 * creat_user: yanghz
	 * creat_date: 2017-07-25
	 * creat_time: 下午 06:28
	 **/
	void batchDelTempDesignProduct(List<Integer> delProductList);

	/**
	 * 通过planGroupId查找list(找出设计方案产品表中同组的产品)
	 * 
	 * @author huangsongbo
	 * @param planGroupId
	 * @return
	 */
	public List<DesignPlanProduct> getGroupProductList(String planGroupId, Integer planId);

	/**
	 * 新建DesignPlanProduct通过配置文件
	 * 
	 * @author huangsongbo
	 * @param configEncrypt
	 * @param posNameInfoList 
	 * @param opType 
	 * @return
	 */
	boolean updateByConfig(String configEncrypt, List<PosNameInfo> posNameInfoList, Integer opType);

	/**
	 * 根据PlanProductInfo创建DesignPlanProduct数据,返回id
	 * @param planProductInfoMatched
	 * @param username 创建人
	 * @param planId 设计方案id
	 * @param opType 是否是自动渲染
	 * @return
	 */
	public Integer createByPlanProductInfo(PlanProductInfo planProductInfo, Integer planId, String username, Integer opType,UnityDesignPlanRecommended unityDesignPlanRecommended);
	
	/**
	 * 还原已删除的背景墙、门框、窗帘
	 * @param designPlanId
	 * @return
	 */
	public Object reductionProduct(String msgId,Integer designPlanId,String mediaType,LoginUser loginUser);

	public ResponseEnvelope<DesignPlanProduct> delProductAndSynConfigFile(ProductSaveParameter productParam,LoginUser loginUser);

	int costTypeListCount(DesignPlanProduct designPlanProduct, costListEnum type);

	/**
	 * 一件装修匹配材质
	 * 
	 * @author huangsongbo
	 * @param splitTexturesChooseInfo 匹配到的产品的材质信息(初始)(从base_product表中获得的多材质信息)(根据推进方案产品的属性自行搜索出来的产品数据)
	 * @param productId 推荐方案产品id
	 * @param splitTexturesInfoRecommended 推荐方案产品的当前选择材质
	 * @return
	 */
	public String matchSplitTexturesInfo(String splitTexturesChooseInfo, Integer productId,
			String splitTexturesInfoRecommended);


	/**
	 * 根据系列方案产品ID查询方案产品列表
	 * @param planProductIdList (系列方案产品ID)
	 * @return
	 */
	List<DesignProductResult> getPlanSeriesProductByPlanProuctIds(List<String> planProductIdList);

	/**
	 * 更新方案多个产品
	 * @param planProductJson [{方案产品ID ：产品ID}]
	 * @param planId 方案ID
	 * @param loginUser
	 * @return
	 */
	boolean updatePlanProducts(String planProductJson, Integer planId, LoginUser loginUser);

	/**
	 * DesignPlanTemplateProduct -> DesignPlanProduct
	 * 
	 * @author huangsongbo
	 * @param id
	 * @param designPlanId 
	 * @param opType 渲染机:0/通用版:1
	 * @throws IntelligenceDecorationException 
	 */
	public void copyFromDesignPlanTemplateProduct(Integer designPlanTemplateId, Integer designPlanId, Integer opType) throws IntelligenceDecorationException;

	Integer add(DesignPlanProduct designPlanProduct, Integer opType) throws IntelligenceDecorationException;

}
