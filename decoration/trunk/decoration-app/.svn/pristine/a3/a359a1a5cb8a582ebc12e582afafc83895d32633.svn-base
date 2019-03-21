package com.nork.design.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.design.model.*;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.ClassReflectionUtils;
import com.nork.design.dao.DesignPlanProductRenderSceneMapper;
//import com.nork.design.dao.DesignPlanProductRenderSceneMapper;
import com.nork.design.service.DesignPlanProductRenderSceneService;

/**
 * copy from DesignPlanProductServiceImpl
 * @author huangsongbo
 *
 */
@Service("designPlanProductRenderSceneService")
public class DesignPlanProductRenderSceneServiceImpl implements DesignPlanProductRenderSceneService {

	Logger logger = LoggerFactory.getLogger(DesignPlanProductRenderSceneServiceImpl.class);
	
	@Autowired
	private DesignPlanProductRenderSceneMapper designPlanProductRenderSceneMapper;
	@Autowired
	private AuthorizedConfigMapper authorizedConfigMapper;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Override
		
	public boolean copyFromDesignPlanProductList(List<DesignPlanProduct> designPlanProductList, long planId) {
		// 参数验证 ->start
		if(designPlanProductList == null || designPlanProductList.size() == 0){
			logger.error("------function:copyFromDesignPlanProductList->designPlanProductList = null or designPlanProductList.size = 0");
			return false;
		}
		
		if(planId < 1){
			logger.error("------function:copyFromDesignPlanProductList->param:planId < 0");
			return false;
		}
		// 参数验证 ->end
		
		// 保存设计方案产品副本 ->start
		List<DesignPlanProductRenderScene> designPlanProductRenderSceneList = new ArrayList<>();

		for(DesignPlanProduct designPlanProduct : designPlanProductList){
			DesignPlanProductRenderScene designPlanProductRenderScene = new DesignPlanProductRenderScene();
			try {
				ClassReflectionUtils.reflectionAttr(designPlanProduct, designPlanProductRenderScene);
				designPlanProductRenderScene.setId(null);
				designPlanProductRenderScene.setPlanId((int)planId);
				designPlanProductRenderScene.setGmtModified(new Date());
				/*this.add(designPlanProductRenderScene);*/
				designPlanProductRenderSceneList.add(designPlanProductRenderScene);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				// 没有写回滚食物(或者用手动事务?)
				return false;
			}
			
		}
		
		this.add(designPlanProductRenderSceneList);
		// 保存设计方案产品副本 ->end
		
		return true;
	}

	@Override
	public void add(DesignPlanProductRenderScene designPlanProductRenderScene) {
		designPlanProductRenderSceneMapper.insert(designPlanProductRenderScene);
	}

	@Override
	public void add(List<DesignPlanProductRenderScene> designPlanProductRenderSceneList) {
		designPlanProductRenderSceneMapper.insertList(designPlanProductRenderSceneList);
	}

	@Override
	public List<DesignPlanProductRenderScene> getListByPlanId(Integer planId) {
		if(planId == null){
			return null;
		}
		return designPlanProductRenderSceneMapper.getListByPlanId(planId);
	}

    @Override
    public int getProductCount(Integer planId) {
        return  designPlanProductRenderSceneMapper.getProductCount(planId);
    }

    @Override
	public void deleteByPlanId(Integer planId) {
		// TODO Auto-generated method stub
		designPlanProductRenderSceneMapper.deleteByPlanId(planId);
	}

	@Override
	public int planProductCount(DesignPlanProductRenderScene designPlanProductRenderScene) {
		return designPlanProductRenderSceneMapper.planProductCount(designPlanProductRenderScene);
	}

	@Override
	public List<DesignPlanProductResult> planProductList(DesignPlanProductRenderScene designPlanProductRenderScene) {
		return designPlanProductRenderSceneMapper.planProductList(designPlanProductRenderScene);
	}

	/**
	 * 获取设计方案副本费用列表
	 * @param loginUser
	 * @param designPlanProductRenderScene
	 */
	@Override
	public List<ProductsCostType> costList(LoginUser loginUser, DesignPlanProductRenderScene designPlanProductRenderScene){
		/*查询用户关联的序列号list*/
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(new Integer(1));
		//authorizedConfig.setTerminalImei(terminalImei);
		List<AuthorizedConfig> list = authorizedConfigMapper.selectList(authorizedConfig);
		/*查询用户关联的序列号list->end*/
		/*add品牌,大类,小类,产品查询条件*/
		List<BaseProduct> baseProductList=new ArrayList<BaseProduct>();
		if( 3 == loginUser.getUserType() ){
			if(list!=null&&list.size()>0){
				for(AuthorizedConfig authorizedConfigItem:list){
					/*查询条件注入到BaseProduct类中*/
					BaseProduct baseProduct=baseProductService.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
					baseProductList.add(baseProduct);
				}
				/*设置查询条件(序列号)*/
				designPlanProductRenderScene.setBaseProduct(baseProductList);
			}
		}
		/*add品牌,大类,小类,产品查询条件->end*/
		int total = this.costTypeListCount(designPlanProductRenderScene);
		List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
		List<ProductsCost> costList = new ArrayList<ProductsCost>();
		if( total > 0 ){
			SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
			/*查询软硬装下面包含的产品小类等信息(带入序列号查询条件)*/
			costTypeList = this.costTypeList(designPlanProductRenderScene);
			for(ProductsCostType costType : costTypeList){
				costType.setPlanId(designPlanProductRenderScene.getPlanId());
				costType.setUserId(loginUser.getId());
				//costType.setAuthorizedConfigList(list);
				costType.setBaseProduct(baseProductList);
				/*得到结算汇总清单
				 * 设置查询条件(序列号)*/
				costList = this.costList(costType);
				List<ProductCostDetail> costDetails = null;
				int productCount = 0 ;
				for (ProductsCost cost : costList) {
					productCount += cost.getProductCount();
					/** 得到结算详情清单 */
					cost.setPlanId(designPlanProductRenderScene.getPlanId());
					cost.setUserId(loginUser.getId());
					cost.setAuthorizedConfigList(list);
					cost.setBaseProduct(baseProductList);
					if( dictionary != null ){
						cost.setSalePriceValueName(dictionary.getName());
					}
					if("1".equals(cost.getCostTypeValue())||"2".equals(cost.getCostTypeValue())||"3".equals(cost.getCostTypeValue())){
						BigDecimal salePrice = cost.getTotalPrice();
						BigDecimal totalPrice  = costType.getTotalPrice();
						BigDecimal new_totalPrice  = totalPrice.subtract(salePrice);
						costType.setTotalPrice(new_totalPrice);
						cost.setTotalPrice(null);
					}
					costDetails = this.costDetail(cost);
					if(costDetails!=null&&costDetails.size()>0){
						for (ProductCostDetail productCostDetail : costDetails) {
							BaseProduct baseProduct = baseProductService.get(productCostDetail.getProductId());
							//讲 地面  墙面 天花的 价格 从总价中  去除
							if("1".equals(baseProduct.getProductTypeValue())||"2".equals(baseProduct.getProductTypeValue())||"3".equals(baseProduct.getProductTypeValue())){
								if(baseProduct!=null){
									productCostDetail.setTotalPrice(null);
									productCostDetail.setUnitPrice(null);
								}

							}
						}
					}
					cost.setDetailList(costDetails);
				}
				costType.setDetailList(costList);
				costType.setProductCount(productCount);
				if( dictionary != null ){
					costType.setSalePriceValueName(dictionary.getName());
				}
				costType.setSalePriceValueName(dictionary==null?"":dictionary.getName());
			}
		}
		return costTypeList;
	}

	@Override
	public int costTypeListCount(DesignPlanProductRenderScene designPlanProductRenderScene){
		return designPlanProductRenderSceneMapper.costTypeListCount(designPlanProductRenderScene);
	}

	/**
	 * 结算类型汇总清单
	 * @param designPlanProductRenderScene
	 * @return
	 */
	@Override
	public List<ProductsCostType> costTypeList(DesignPlanProductRenderScene designPlanProductRenderScene){
		return designPlanProductRenderSceneMapper.costTypeList(designPlanProductRenderScene);
	}

	/**
	 * 结算汇总清单
	 * @return
	 */
	@Override
	public List<ProductsCost> costList(ProductsCostType productsCostType){
		return designPlanProductRenderSceneMapper.costList(productsCostType);
	}

	/**
	 * 结算清单明细
	 * @param cost
	 * @return
	 */
	@Override
	public List<ProductCostDetail> costDetail(ProductsCost cost){
		return designPlanProductRenderSceneMapper.costDetail(cost);
	}

    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanProductRenderSceneService#getDesignPlanProductList(com.nork.design.model.DesignPlanProductRenderScene)    
     */
    @Override
    public List<DesignPlanProductResult> getDesignPlanProductList(
            DesignPlanProductRenderScene designPlanProductRenderScene) {
        return designPlanProductRenderSceneMapper.getDesignPlanProductList(designPlanProductRenderScene);
    }

	@Override
	public int costTypeListCount(DesignPlanProduct designPlanProduct) {
		return designPlanProductRenderSceneMapper.costTypeListCountV2(designPlanProduct);
	}

	@Override
	public List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct) {
		return designPlanProductRenderSceneMapper.costTypeListV2(designPlanProduct);
	}

	@Override
	public DesignPlanProductRenderScene get(Integer id) {
		// TODO Auto-generated method stub
		return designPlanProductRenderSceneMapper.selectById(id);
	}

	/**
	 * 获取方案副本产品分类key
	 * @param designPlanRenderSceneId
	 * @return
	 */
	@Override
	public List<String> findListBySceneId(Integer designPlanRenderSceneId){
		return designPlanProductRenderSceneMapper.findListBySceneId(designPlanRenderSceneId);
	}
}
