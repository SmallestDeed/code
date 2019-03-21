package com.nork.design.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.design.model.*;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.ProductStatuCode;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;
import com.nork.user.model.UserTypeCode;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.ClassReflectionUtils;
import com.nork.common.util.Utils;
import com.nork.design.dao.DesignPlanProductRenderSceneMapper;
//import com.nork.design.dao.DesignPlanProductRenderSceneMapper;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.service.DesignPlanProductService;

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
	@Autowired
	private BaseBrandService baseBrandService;
	@Autowired
	private DesignPlanProductService designPlanProductService;
	
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
	 * 获取设计方案副本费用列表(720)
	 * @param loginUser
	 * @param designPlanProductRenderScene
	 */
	@Override
	public List<ProductsCostType> costList(LoginUser loginUser, DesignPlanProduct designPlanProduct){
		/*查询用户关联的序列号list*/
/*		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(loginUser.getId());
		authorizedConfig.setState(new Integer(1));*/
		BaseBrand baseBrand = null;
        if(2==loginUser.getUserType()){//企业用户
        	baseBrand = baseBrandService.findBrandIdByUserIdBase(loginUser);
        }else if (3==loginUser.getUserType()){
        	baseBrand = baseBrandService.findBrandIdByUserIdAndUserType(loginUser);
        }
        
		boolean flag = false;
		 List list= null;
		if(null != baseBrand){
			if(StringUtils.isNotBlank(baseBrand.getBrandStr())){
			
				String beandIds = baseBrand.getBrandStr();
				String[] strs = beandIds.split(",");
				//将字符串数组转换成集合list
			   list=Arrays.asList(strs);
			   BaseBrand baseBrandInfo = baseBrandService.findBrandInfoByUserId(baseBrand.getBrandStr());
			   if(null != baseBrandInfo){
				   if(null != baseBrandInfo.getBrandReferred()){
					   String[] ids = baseBrandInfo.getBrandReferred().split(",");
					   for(int i=0;i<ids.length;i++){
						   if(Utils.isSanduUser(ids[i])){//判断是否是三度品牌
						        flag = true;
						    }
					   }
				   }
			   }
				
			}
		}
		//只过滤2 和3 的用户
		if(1 == loginUser.getUserType()){//判断是否为内部用户
			flag = true;
		}
		if(4 <= loginUser.getUserType()){//其他用户也不做过滤
			flag = true;
		}
		
		List<Integer> putawayState = new ArrayList<>(3);
		putawayState.add(ProductStatuCode.HAS_BEEN_RELEASE);
		if (UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType())) {
			putawayState.add(ProductStatuCode.HAS_BEEN_PUTAWAY);
			putawayState.add(ProductStatuCode.TESTING);
		}
		designPlanProduct.setProductPutawayStateList(putawayState);
		List<String> li=null;
		if(!flag){
			designPlanProduct.setStatus("Y");
			if(null != baseBrand){
				designPlanProduct.setBrandsStr(baseBrand.getBrandStr());//按照品牌过滤
			}
			 li = designPlanProductService.scopeProductByuserId(loginUser.getId());
		}
		
		//除了 厂商和经销商外的用户
		if(flag){
			designPlanProduct.setUserId(loginUser.getId());
			/*add品牌,大类,小类,产品查询条件->end*/
			int total = designPlanProductService.costTypeListCountShare(designPlanProduct);
			List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
			List<ProductsCost> costList = new ArrayList<ProductsCost>();
			if( total > 0 ){
				SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
				
				/*查询软硬装下面包含的产品小类等信息(带入序列号查询条件)*/
				costTypeList = designPlanProductService.costTypeListShare(designPlanProduct);
				for(ProductsCostType costType : costTypeList){
					costType.setPlanId(designPlanProduct.getPlanId());
					costType.setUserId(loginUser.getId());
					costType.setProductPutawayState(putawayState);
					costList = designPlanProductService.costListShare(costType); 
					List<ProductCostDetail> costDetails = null;
					int productCount = 0 ;
					
					List<String> matchingList = new ArrayList<String>();
					
					for (ProductsCost cost : costList) {
						productCount += cost.getProductCount();
						/** 得到结算详情清单 */
						cost.setPlanId(designPlanProduct.getPlanId());
						cost.setUserId(loginUser.getId());
						cost.setAuthorizedConfigList(list);
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
						cost.setProductPutawayState(putawayState);
						cost.setUserId(loginUser.getId());//用户id
						costDetails = designPlanProductService.costDetailShare(cost);
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
						}else{
							productCount -= 1;
							matchingList.add(cost.getCostTypeCode());
						}
						cost.setDetailList(costDetails);
					}
					 LinkedList<ProductsCost> linkedList = new LinkedList<ProductsCost>(costList);
					 for(ProductsCost lis : linkedList){
						 for(String ls :matchingList){
								if(lis.getCostTypeCode().equals(ls)){
									costList.remove(lis);
								}
							}
					 }
					
					costType.setDetailList(costList);
					
					costType.setProductCount(productCount);
					if( dictionary != null ){
						costType.setSalePriceValueName(dictionary.getName());
					}
					costType.setSalePriceValueName(dictionary==null?"":dictionary.getName());
				}
			}
			//return new ResponseEnvelope<ProductsCostType>(total, costTypeList,designPlanProduct.getMsgId());
			return costTypeList;
		}else if(null != li && li.size()>0){
			 List<String> matchingStairList = new ArrayList<String>();//如果二级菜单为空那么就删除一级级菜单
			 List<String> matchingStairTwoList = new ArrayList<String>();//如果三级菜单为空那么就删除二级菜单
			designPlanProduct.setAtt2List(li);
			designPlanProduct.setUserId(loginUser.getId());
			/*add品牌,大类,小类,产品查询条件->end*/
			int total = designPlanProductService.costTypeListCountShare(designPlanProduct);
			List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
			List<ProductsCost> costList = new ArrayList<ProductsCost>();
			if( total > 0 ){
				SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
				
				/*查询软硬装下面包含的产品小类等信息(带入序列号查询条件)*/
				costTypeList = designPlanProductService.costTypeListShare(designPlanProduct);
				for(ProductsCostType costType : costTypeList){
					costType.setPlanId(designPlanProduct.getPlanId());
					costType.setUserId(loginUser.getId());
					//costType.setAuthorizedConfigList(list);
				//	costType.setBaseProduct(baseProductList);//2017-3-10 wutehua
					costType.setProductPutawayState(putawayState);
					/*得到结算汇总清单
					 * 设置查询条件(序列号)*/
					if(!flag){
						if(null != baseBrand){
							costType.setBrandsStr(baseBrand.getBrandStr());//按照品牌过滤
						}
					}
					costList = designPlanProductService.costListShare(costType); 
					
					List<ProductCostDetail> costDetails = null;
					int productCount = 0 ;
					
					List<String> matchingList = new ArrayList<String>();//
					
					
					//如果二级级菜单为空，那么就删除一级菜单
					if(costList.size()<1){
						matchingStairList.add(costType.getCostCodes());
					}
					
					for (ProductsCost cost : costList) {
						productCount += cost.getProductCount();
						/** 得到结算详情清单 */
						cost.setPlanId(designPlanProduct.getPlanId());
						cost.setUserId(loginUser.getId());
						 //如果绑定三度或者是内部则产品清单不做过滤
						if(!flag){
							cost.setStatus("Y");
							if(null != baseBrand){
								cost.setBrandsStr(baseBrand.getBrandStr());//按照品牌过滤
							}
						}
						cost.setAuthorizedConfigList(list);
						//cost.setBaseProduct(baseProductList);//2017-3-10 wutehua
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
						cost.setProductPutawayState(putawayState);
						cost.setUserId(loginUser.getId());//用户id
						costDetails = designPlanProductService.costDetailShare(cost);
						if(costDetails.size()<1){
						}
						if(costDetails!=null&&costDetails.size()>0){
							for (ProductCostDetail productCostDetail : costDetails) {
								BaseProduct baseProduct = baseProductService.get(productCostDetail.getProductId());
								//讲 地面  墙面 天花的 价格 从总价中  去除
								 if("1".equals(baseProduct.getProductTypeValue())||"2".equals(baseProduct.getProductTypeValue())||"3".equals(baseProduct.getProductTypeValue())){
									 if(baseProduct!=null){
	//									 if(baseProduct.getSalePrice()!=null&&costType.getTotalPrice()!=null){
	//										BigDecimal salePrice = new BigDecimal(baseProduct.getSalePrice());
	//										BigDecimal totalPrice  = costType.getTotalPrice();
	//										BigDecimal new_totalPrice  = totalPrice.subtract(salePrice);
	//										costType.setTotalPrice(new_totalPrice);
	//									} 	
										productCostDetail.setTotalPrice(null);
										productCostDetail.setUnitPrice(null);
									} 	
									 
								} 
							}
						}else{
							//如果三级菜单为空，那么就删除二级菜单
							productCount -= 1;
							matchingList.add(cost.getCostTypeCode());
						}
						// costDetails=DesignPlanProductCacher.costDetail(cost);
						if(costDetails.size()>0){
							cost.setDetailList(costDetails);
						}
					}
					//如果三级菜单为空，那么就删除二级菜单
					 LinkedList<ProductsCost> linkedList = new LinkedList<ProductsCost>(costList);
					 for(ProductsCost lis : linkedList){
						 for(String ls :matchingList){
								if(lis.getCostTypeCode().equals(ls)){
									costList.remove(lis);
								}
							}
					 }
					
					costType.setDetailList(costList);
					
					costType.setProductCount(productCount);
					if( dictionary != null ){
						costType.setSalePriceValueName(dictionary.getName());
					}
					costType.setSalePriceValueName(dictionary==null?"":dictionary.getName());
					
				}
			}
			//如果二级菜单为空，那么就删除一级菜单
			if(costList.size()<1){
			 LinkedList<ProductsCostType> linkedLists = new LinkedList<ProductsCostType>(costTypeList);
			 for(ProductsCostType lis : linkedLists){
				 for(String ls :matchingStairList){
					 if(lis.getCostCodes().equals(ls)){
						 costTypeList.remove(lis);
						}
					}
			 }
			}
			//return new ResponseEnvelope<ProductsCostType>(total, costTypeList,designPlanProduct.getMsgId());
			return costTypeList;
		}else{
			//return new ResponseEnvelope<ProductsCostType>(0, null,designPlanProduct.getMsgId());
			return null;
		}
		//authorizedConfig.setTerminalImei(terminalImei);
		/*List<AuthorizedConfig> list = authorizedConfigMapper.selectList(authorizedConfig);
		查询用户关联的序列号list->end
		add品牌,大类,小类,产品查询条件
		List<BaseProduct> baseProductList=new ArrayList<BaseProduct>();
		if( 3 == loginUser.getUserType() ){
			if(list!=null&&list.size()>0){
				for(AuthorizedConfig authorizedConfigItem:list){
					查询条件注入到BaseProduct类中
					BaseProduct baseProduct=baseProductService.getBaseProductFromAuthorizedConfig(authorizedConfigItem);
					baseProductList.add(baseProduct);
				}
				设置查询条件(序列号)
				designPlanProductRenderScene.setBaseProduct(baseProductList);
			}
		}
		add品牌,大类,小类,产品查询条件->end
		int total = this.costTypeListCount(designPlanProductRenderScene);
		List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
		List<ProductsCost> costList = new ArrayList<ProductsCost>();
		if( total > 0 ){
			SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKey("productUnitPrice", "price_yuan");
			查询软硬装下面包含的产品小类等信息(带入序列号查询条件)
			costTypeList = this.costTypeList(designPlanProductRenderScene);
			for(ProductsCostType costType : costTypeList){
				costType.setPlanId(designPlanProductRenderScene.getPlanId());
				costType.setUserId(loginUser.getId());
				//costType.setAuthorizedConfigList(list);
				costType.setBaseProduct(baseProductList);
				得到结算汇总清单
				 * 设置查询条件(序列号)
				costList = this.costList(costType);
				List<ProductCostDetail> costDetails = null;
				int productCount = 0 ;
				for (ProductsCost cost : costList) {
					productCount += cost.getProductCount();
					*//** 得到结算详情清单 *//*
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
		return costTypeList;*/
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

	@Override
	public List<DesignPlanProductRenderScene> getListByPlanIdAndIsDeleted(Integer id, Integer isDeleted) {
		return designPlanProductRenderSceneMapper.getListByPlanIdAndIsDeleted(id, isDeleted);
	}
	
}
