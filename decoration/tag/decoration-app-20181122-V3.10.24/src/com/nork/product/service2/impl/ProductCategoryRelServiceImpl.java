package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.nork.product.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.cache.CommonCacher;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ProductCategoryVO;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.model.SearchProductVO;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.cache.DesignTempletCacher;
import com.nork.design.model.AutoCarryBaimoProducts;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.dao.ProductCategoryRelMapper;
import com.nork.product.model.SplitTextureDTO.ResTextureDTO;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.search.ProductCategoryRelSearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.PrepProductSearchInfoService;
import com.nork.product.service.ProCategoryService;
import com.nork.product.service.ProductAttributeService;
import com.nork.product.service2.ProductCategoryRelService;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.ProductPropsSimple;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.search.SysDictionarySearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: ProductCategoryRelServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品与产品类目关联ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 * @version V1.0
 */
public class ProductCategoryRelServiceImpl implements ProductCategoryRelService {

	private static Logger logger = Logger.getLogger(ProductCategoryRelServiceImpl.class);

	private static final String GAIN_BLACKLIST = "------获取黑名单配置:";
	private static final String MSG_SELECT_CONDITION = "------大小类信息查询条件:";
	private static final String PRODUCT_SPACE_SEARCH_CONDITION = "------产品空间类型搜索条件:";
	private static final String PLANPRODUCTID_NOT_NULL = "------planProductId不为空的逻辑:";
	private static final String SET_NUM_SERARCH_CONDITION = "------设置序列号查询条件:";
	private static final String COUNT_QUERY = "------count查询:";
	private static final String PRODUCT_LIST_QUERY = "------定制产品list查询:";
	private static final String RECOMMEND_PRODUCT_LIST = "------推荐产品list:";
	private static final String SORT_TIME = "------排序总时间:";
	private static final String GAIN_BAIMO_PRODUCT_EXCEPTION = "获取自动携带白模产品配置异常！";
	private static final String GAIN_BAIMO_PRODUCT = "------获取需要自动携带白模产品的配置:";
	private static final String ADDITIVE_ATTR = "------附加属性:";
	private static final String DATA_EXCEPTION = "数据异常!";
	private static final String QUERY_TIME_CONSUMING = "------实时查询总耗时:";
	private static final String COLOUR_SORT = "------色系排序:";
	private static final String LIST_RECOMMEND_SUBCLASS_SORT = "------列表排序,推荐,小类,同属性匹配度,使用量,色系排序:";
	private static final String PRODUCT_SEARCH_SET_QUERY_TIME_CONSUMING = "------(产品搜索接口)设置查询条件耗时:";
	private static final String PRODUCT_SEARCH_PULL_QUERY_TIME_CONSUMING = "------(产品搜索接口)拉取详细信息耗时:";
	private static final String PRODUCT_SEARCH_FALSE_QUERY_TIME_CONSUMING = "------(产品搜索接口,缓存:false)总耗时:";
	private static final String PRODUCT_SEARCH_TURE_QUERY_TIME_CONSUMING = "------(产品搜索接口,缓存:true)总耗时:";
	private static final String HOUSE_TYPE = "房间类型为houseType=";
	private static final String SPACE_KEY_SPACECOMMONID = "通用空间主键spaceCommonId=";
	private static final String DESIGN_KEY_DESIGNPLANID = "设计方案主键designPlanId=";
	private static final String SELECTED_DESIGNPLAN_KEY_DESIGNPLANPRODUCT_PRODUCTID = "当前选中的产品在设计方案产品列表中的数据主键designPlanProduct_ProductId=";
	private static final String SELECTED_TEMPLATE_DATAKEY_TEMPLATEPRODUCTID = "当前选中的产品在样本房列表中的数据主键templateProductId=";
	private static final String SELECTED_TEMPLATE_PRODUCTKEY_TEMPLATEPRODUCT_PRODUCTID = "当前选中的产品在样本房列表中的产品主键templateProduct_ProductId=";
	private static final String SELECTED_PRODUCT_SMALL_TYPE_VALUE = "当前选中产品的小类smallTypeValue=";
	private static final String SELECTED_PRODUCT_PRODUCT_TYPE_VALUE = "当前选中产品的大类productTypeValue=";
	private static final String QUERY_TYPE = "查询类型queryType=";

	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	private ProductCategoryRelMapper productCategoryRelMapper;

	@Autowired
	private AuthorizedConfigService authorizedConfigService;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@Autowired
	private DesignPlanProductService designPlanProductService;

	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	private ProductAttributeService productAttributeService;

	@Autowired
	private DesignTempletService designTempletService;

	@Autowired
	private ProCategoryService proCategoryService;

	@Autowired
	private PrepProductSearchInfoService prepProductSearchInfoService;

	@Autowired
	private BaseBrandService baseBrandService;

	@Autowired
	private ResTextureService resTextureService;

	@Autowired
	private ProductCategoryRelService productCategoryRelService;

	@Autowired
	private ProCategoryService categoryService;

	@Autowired
	private ResPicService resPicService;

	@Autowired
	private ResModelService resModelService;

	@Autowired
	private ResFileService resFileService;

	@Autowired
	public void setProductCategoryRelMapper(ProductCategoryRelMapper productCategoryRelMapper) {
		this.productCategoryRelMapper = productCategoryRelMapper;
	}

	public static Map<String, Object> showMoreSmallTypeMap = getshowMoreSmallTypeMap();

	/**
	 * 新增数据
	 *
	 * @param productCategoryRel
	 * @return int
	 */
	@Override
	public int add(ProductCategoryRel productCategoryRel) {
		productCategoryRelMapper.insertSelective(productCategoryRel);
		return productCategoryRel.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param productCategoryRel
	 * @return int
	 */
	@Override
	public int update(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.updateByPrimaryKeySelective(productCategoryRel);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return productCategoryRelMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return ProductCategoryRel
	 */
	@Override
	public ProductCategoryRel get(Integer id) {
		return productCategoryRelMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param productCategoryRel
	 * @return List<ProductCategoryRel>
	 */
	@Override
	public List<ProductCategoryRel> getList(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.selectList(productCategoryRel);
	}

	/**
	 * 获取数据数量
	 *
	 * @return int
	 */
	public int getCount(ProductCategoryRelSearch productCategoryRelSearch) {
		return productCategoryRelMapper.selectCount(productCategoryRelSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @return List<ProductCategoryRel>
	 */
	@Override
	public List<ProductCategoryRel> getPaginatedList(ProductCategoryRelSearch productCategoryRelSearch) {
		return productCategoryRelMapper.selectPaginatedList(productCategoryRelSearch);
	}

	/**
	 * 根据产品编号和类目编号查询
	 * 
	 * @return
	 */
	@Override
	public ProductCategoryRel findByPidAndCid(Map<String, Object> paramsMap) {
		return productCategoryRelMapper.findByPidAndCid(paramsMap);
	}

	/**
	 * 根据产品ID删除关联信息
	 * 
	 * @return
	 */
	@Override
	public int deletedByProductId(Integer productId) {
		return productCategoryRelMapper.deletedByProductId(productId);
	}

	/**
	 * 根据分类编号查询产品
	 * 
	 * @param longCode
	 * @return
	 */
	@Override
	public List<ProductCategoryRel> findCategoryProductIds(String longCode) {
		return productCategoryRelMapper.findCategoryProductIds(longCode);
	}

	/**
	 * 根据分类code和商品名称查询
	 * 
	 * @param productCategoryRel
	 * @return
	 */
	@Override
	public List<CategoryProductResult> findProductByCategoryCode(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findProductByCategoryCode(productCategoryRel);
	}

	/**
	 * 根据分类code和商品名称查询汇总
	 * 
	 * @param productCategoryRel
	 * @return
	 */
	@Override
	public int findProductByCategoryCodeCount(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findProductByCategoryCodeCount(productCategoryRel);
	}

	@Override
	public int deleteAllByProductId(Integer id) {
		return productCategoryRelMapper.deleteAllByProductId(id);
	}

	@Override
	public List<CategoryProductResult> findCategoryProductResultByLongCode(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findCategoryProductResultByLongCode(productCategoryRel);
	}

	@Override
	public int findCategoryProductResultByLongCodeCount(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findCategoryProductResultByLongCodeCount(productCategoryRel);
	}

	@Override
	public int getProCategoryListCountByCode(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.getProCategoryListCountByCode(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.getProCategoryListByCode(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> findCategoryProductResult(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findCategoryProductResult(productCategoryRel);
	}

	/**
	 * 按照序列号过滤list
	 * 
	 * @author huangsongbo
	 * @param list
	 * @param userId
	 * @return
	 */
	public List<CategoryProductResult> filterByAuthorizedConfig(List<CategoryProductResult> list, Integer userId,
			String terminalImei) {
		// 需求改为序列号设备共用
		Map<Integer, List<AuthorizedConfig>> authorizedConfigsMap = authorizedConfigService
				.getEffectualauthorizedConfigs(userId, null);
		// 检测list是否存在该品牌的产品
		boolean judge = isAliveBrandId(list, authorizedConfigsMap);
		// 检测list是否存在该品牌的产品end
		if (judge) {
			// 过滤
			for (int i = 0; i < list.size(); i++) {
				boolean isRemove = isRemove(list.get(i), authorizedConfigsMap);
				if (isRemove) {
					list.remove(i);
					i--;
				}
			}
		}
		return list;
	}

	/**
	 * 按照序列号过滤list方法2
	 */
	public List<CategoryProductResult> filterByAuthorizedConfig2(List<CategoryProductResult> list, Integer userId,
			String terminalImei) {
		Map<String, List<AuthorizedConfig>> authorizedConfigsMap = authorizedConfigService
				.getEffectualauthorizedConfigs2(userId, terminalImei);
		if (authorizedConfigsMap.isEmpty()) {
			return list;
		}
		// 检测list中数据的brandId有没有存在于mapKey=all的序列号的brandIds中->有->只要该brand的产品
		Map<Integer, List<Integer>> brandIdAndProductIdsMap = new HashMap<Integer, List<Integer>>();
		if (authorizedConfigsMap.containsKey("all")) {
			for (AuthorizedConfig authorizedConfig : authorizedConfigsMap.get("all")) {
				for (String str : authorizedConfig.getBrandIds().split(",")) {
					if (StringUtils.isNotEmpty(str)) {
						if (brandIdAndProductIdsMap.containsKey(Integer.parseInt(str))) {
							// list叠加productId
							List<Integer> productIdList = brandIdAndProductIdsMap.get(Integer.parseInt(str));
							for (String productIdStr : getListByStr(authorizedConfig.getProductIds())) {
								if (productIdList.indexOf(Integer.parseInt(productIdStr)) == -1) {
									productIdList.add(Integer.parseInt(productIdStr));
								}
							}
						} else {
							// map.put
							List<Integer> productIdList = new ArrayList<Integer>();
							for (String productIdStr : getListByStr(authorizedConfig.getProductIds())) {
								if (productIdList.indexOf(Integer.parseInt(productIdStr)) == -1) {
									productIdList.add(Integer.parseInt(productIdStr));
								}
							}
							brandIdAndProductIdsMap.put(Integer.parseInt(str), productIdList);
						}
					} else {
						logger.info("str is null ++++getBrandIds=" + authorizedConfig.getBrandIds());
					}
				}
			}
		}
		// 取mapKey=all的序列号的brandIds->end
		if (!brandIdAndProductIdsMap.isEmpty()) {
			// 检测list产品的brandId是否存在于brandIdAndProductIdsMap中
			boolean flag = false;
			List<CategoryProductResult> returnList = new ArrayList<CategoryProductResult>();
			for (CategoryProductResult categoryProductResult : list) {
				if (brandIdAndProductIdsMap.containsKey(categoryProductResult.getBrandId())) {
					flag = true;
					// 得到productIds
					List<Integer> productIdList = brandIdAndProductIdsMap.get(categoryProductResult.getBrandId());
					if (productIdList.size() == 0) {
						returnList.add(categoryProductResult);
					} else {
						// 检测productIdList
						if (productIdList.indexOf(categoryProductResult.getProductId()) > -1)
							returnList.add(categoryProductResult);
					}
				}
			}
			if (flag) {
				return returnList;
			}
		}
		// 过滤
		for (int i = 0; i < list.size(); i++) {
			boolean isRemove = isRemove2(list.get(i), authorizedConfigsMap);
			if (isRemove) {
				list.remove(i);
				i--;
			}
		}
		// 过滤end
		return list;
	}

	/**
	 * 判断该categoryProductResult是否会被authorizedConfigs(序列号集合)过滤掉
	 * 
	 * @author huangsongbo
	 * @param categoryProductResult
	 * @return
	 */
	private boolean isRemove(CategoryProductResult categoryProductResult,
			Map<Integer, List<AuthorizedConfig>> authorizedConfigsMap) {
		List<Integer> brandIdList = new ArrayList<Integer>();
		for (Integer key : authorizedConfigsMap.keySet()) {
			brandIdList.add(key);
		}
		if (brandIdList.indexOf(categoryProductResult.getBrandId()) == -1) {
			// 该产品的brandId不存在于brandIdList中->移除该产品(返回true)
			return false;
		} else {
			// 判断是否要remove掉
			List<AuthorizedConfig> authorizedConfigs = authorizedConfigsMap.get(categoryProductResult.getBrandId());
			for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
				if (// 满足过滤条件
				matchFilter(categoryProductResult, authorizedConfig)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断该categoryProductResult是否会被authorizedConfigs(序列号集合)过滤掉
	 * 
	 * @author huangsongbo
	 * @param categoryProductResult
	 * @return false:不过滤(保存);true:过滤掉(去掉)
	 */
	private boolean isRemove2(CategoryProductResult categoryProductResult,
			Map<String, List<AuthorizedConfig>> authorizedConfigsMap) {
		boolean isRemoved = true;
		List<String> bigTypeList = new ArrayList<String>();
		for (String key : authorizedConfigsMap.keySet()) {
			bigTypeList.add(key);
		}
		// 根据categoryProductResult的productId关联查询出bigType,smallTypeId
		BaseProduct baseProduct = null;
		if (Utils.enableRedisCache()) {
			baseProduct = BaseProductCacher.get(categoryProductResult.getProductId());
		} else {
			baseProduct = baseProductService.get(categoryProductResult.getProductId());
		}

		if (baseProduct == null) {
			return isRemoved;
		}
		SysDictionary bigType = sysDictionaryService.findOneByTypeAndValue("productType",
				Integer.valueOf(baseProduct.getProductTypeValue()));
		if (bigType == null) {
			return isRemoved;
		}
		SysDictionary smallType = sysDictionaryService.findOneByTypeAndValue(bigType.getValuekey(),
				baseProduct.getProductSmallTypeValue());
		if (smallType != null) {
			if (bigTypeList.indexOf(bigType.getValuekey()) == -1) {
				// 去除
				isRemoved = false;
			} else {
				List<AuthorizedConfig> authorizedConfigs = authorizedConfigsMap.get(bigType.getValuekey());
				for (AuthorizedConfig authorizedConfig : authorizedConfigs) {
					// 符合一个authorizedConfig,返回false,break
					List<String> brandIds = getListByStr(authorizedConfig.getBrandIds());
					List<String> smallTypeIds = getListByStr(authorizedConfig.getSmallTypeIds());
					List<String> productIds = getListByStr(authorizedConfig.getProductIds());
					if (brandIds.indexOf("" + categoryProductResult.getBrandId()) == -1) {
						continue;
					}
					if (smallTypeIds.size() > 0 && smallTypeIds.indexOf("" + smallType.getId()) == -1) {
						continue;
					}
					if (productIds.size() > 0 && productIds.indexOf("" + categoryProductResult.getProductId()) == -1) {
						continue;
					}
					isRemoved = false;
					break;
				}
			}
		}
		return isRemoved;
	}

	/**
	 * 判断指定的categoryProductResult是否满足过滤条件authorizedConfig
	 * 
	 * @param categoryProductResult
	 * @param authorizedConfig
	 * @return
	 */
	private boolean matchFilter(CategoryProductResult categoryProductResult, AuthorizedConfig authorizedConfig) {
		// 根据categoryProductResult的productId关联查询出bigType,smallTypeId
		BaseProduct baseProduct = baseProductService.get(categoryProductResult.getProductId());
		SysDictionary bigType = sysDictionaryService.findOneByTypeAndValue("productType",
				Integer.valueOf(baseProduct.getProductTypeValue()));
		SysDictionary smallType = sysDictionaryService.findOneByTypeAndValue(bigType.getValuekey(),
				baseProduct.getProductSmallTypeValue());
		// 根据categoryProductResult的productId关联查询出bigType,smallTypeId->end
		List<String> bigTypes = getListByStr(authorizedConfig.getBigType());
		List<String> smallTypeIds = getListByStr(authorizedConfig.getSmallTypeIds());
		List<String> productIds = getListByStr(authorizedConfig.getProductIds());
		if (bigTypes.size() == 0) {
			if (productIds.size() == 0) {
				// 保留
				return false;
			} else {
				if (productIds.indexOf("" + categoryProductResult.getProductId()) == -1) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			if (bigTypes.indexOf(bigType.getValuekey()) == -1) {
				// 移除
				return true;
			} else {
				// 大类符合->匹配小类
				if (smallTypeIds.size() == 0)
					// 保留
					return false;
				if (smallTypeIds.indexOf("" + smallType.getId()) == -1) {
					// 移除
					return true;
				} else {
					// 小类符合->匹配产品id
					if (productIds.size() == 0)
						// 保留
						return false;
					if (productIds.indexOf("" + categoryProductResult.getProductId()) != -1) {
						// 保留
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 字符串转化为list
	 * 
	 * @author huangsongbo
	 * @param str
	 * @return
	 */
	private List<String> getListByStr(String str) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isBlank(str))
			return list;
		for (String str2 : str.split(",")) {
			list.add(str2);
		}
		return list;
	}

	/**
	 * 判断list中是否有存在指定品牌的产品
	 * 
	 * @author huangsongbo
	 * @param list
	 *            需过滤的list
	 * @param map
	 * @return
	 */
	public boolean isAliveBrandId(List<CategoryProductResult> list, Map<Integer, List<AuthorizedConfig>> map) {
		if (map.size() == 0)
			return false;
		// 获得brandList
		List<Integer> brandIdList = new ArrayList<Integer>();
		for (Integer key : map.keySet()) {
			brandIdList.add(key);
		}
		// 获得brandList->end
		for (CategoryProductResult categoryProductResult : list) {
			if (brandIdList.indexOf(categoryProductResult.getBrandId()) != -1) {
				// 存在指定品牌的产品
				return true;
			}
		}
		return false;
	}

	@Override
	public List<CategoryProductResult> findBuildingHomeProductResult(ProductCategoryRel productCategoryRel) {

		return productCategoryRelMapper.findBuildingHomeProductResult(productCategoryRel);
	}

	@Override
	public int findBuildingHomeProductCount(ProductCategoryRel productCategoryRel) {

		return productCategoryRelMapper.findBuildingHomeProductCount(productCategoryRel);
	}

	@Override
	public int findCustomizedCategoryProductResultCount(ProductCategoryRel productCategoryRel) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.findCustomizedCategoryProductResultCount(productCategoryRel);
	}

	@Override
	public int findRecommendCategoryProductResultCount(ProductCategoryRel productCategoryRel) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.findRecommendCategoryProductResultCount(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> findCustomizedCategoryProductResult(ProductCategoryRel productCategoryRel) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.findCustomizedCategoryProductResult(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> findRecommendCategoryProductResult(ProductCategoryRel productCategoryRel) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.findRecommendCategoryProductResult(productCategoryRel);
	}

	@Override
	public CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.getCategoryProductResultByProductId(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> getCategoryProductResult(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.getCategoryProductResult(productCategoryRel);
	}

	@Override
	public int getCategoryProductCount(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.getCategoryProductCount(productCategoryRel);
	}

	@Override
	public int getRecommendResultCount(ProductCategoryRel productCategoryRel) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.findRecommendResultCount(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> getRecommendResult(ProductCategoryRel productCategoryRel) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.findRecommendResult(productCategoryRel);
	}

	/**
	 * 分类搜索产品背景墙内容和特殊分类信息条件
	 */
	@Override
	public ProductCategoryRel bgWallAndSpecialTypeInfo(ProductCategoryRel productCategoryRel, boolean isShowbgWall,
			SysDictionary bigSd, SysDictionary smallSd, BaseProduct baimoProduct, BaseProduct productSelected) {
		String specialProductTypes = Utils.getValue("special.productType", "");
		String filterLength = Utils.getValue("app.filter.stretch.length", "0");
		String curtainLength = Utils.getValue("app.filter.3mAbove.stretch.length", "0");

		if (isShowbgWall) {
			// 只显示背景墙exceptRecommend设为true
			// exceptRecommend = "true";
			productCategoryRel.setShowBgWall(true);
			if (baimoProduct != null) {
				String fullPaveLength = baimoProduct.getFullPaveLength();
				if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
					fullPaveLength = baimoProduct.getProductLength();
				}
				String productHeight = baimoProduct.getProductHeight();
				if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
						&& StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0) {
					// 窗帘3米以上过滤40cm
					clFilter(productCategoryRel, bigSd, filterLength, curtainLength, fullPaveLength, productHeight);
				} else {
					productCategoryRel.setValue(true);
				}
			}
		} else {
			productCategoryRel.setShowBgWall(false);
		}
		// 判断是否是特殊分类
		if (StringUtils.isNotBlank(specialProductTypes)) {
			JSONArray productTypeArray = JSONArray.fromObject(specialProductTypes);
			if (productTypeArray != null && productTypeArray.size() > 0) {
				isSpercialType(productCategoryRel, bigSd, smallSd, productTypeArray);
			}
		}
		// 过滤产品长、 高信息
		String productSmallTypes_LH = Utils.getValue("filter.productLH.productSmallType", "");
		if (Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), productSmallTypes_LH)) {
			filterProductInfo(productCategoryRel, baimoProduct, productSelected);
		}
		// 过滤产品长、宽信息
		String productSmallTypes_LW = Utils.getValue("filter.productLW.productSmallType", "");
		if (Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), productSmallTypes_LW)) {
			if (baimoProduct != null) {
				productCategoryRel.setProductLength(baimoProduct.getProductLength());
				productCategoryRel.setProductWidth(baimoProduct.getProductWidth());
			} else {
				productCategoryRel.setProductLength(productSelected.getProductLength());
				productCategoryRel.setProductWidth(productSelected.getProductWidth());
			}
		}
		return productCategoryRel;
	}

	// 窗帘3米以上过滤40cm
	private void clFilter(ProductCategoryRel productCategoryRel, SysDictionary bigSd, String filterLength,
			String curtainLength, String fullPaveLength, String productHeight) {
		if (bigSd.getValue().intValue() == 16 && Utils.getIntValue(fullPaveLength) > 300) {
			productCategoryRel
					.setStartLength((Utils.getIntValue(fullPaveLength) - Utils.getIntValue(curtainLength)) + 1);
			productCategoryRel.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(curtainLength));
		} else {
			productCategoryRel
					.setStartLength((Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength)) + 1);
			productCategoryRel.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength));
		}
		productCategoryRel.setBgWallHeight(productHeight);
	}

	// 判断是否是特殊分类
	private void isSpercialType(ProductCategoryRel productCategoryRel, SysDictionary bigSd, SysDictionary smallSd,
			JSONArray productTypeArray) {
		for (int i = 0; i < productTypeArray.size(); i++) {
			JSONObject jsonObj = (JSONObject) productTypeArray.get(i);
			String bigTypeKey = jsonObj.getString("productTypeKey");
			String smallTypeKeys = "," + jsonObj.getString("productSmallTypeKeys") + ",";
			if (bigTypeKey.equals(bigSd == null ? "" : bigSd.getValuekey())) {
				productCategoryRel.setSpecialProductType(smallTypeKeys);
				String smallKey = "";
				if (smallSd != null) {
					if ("baimo".equals(smallSd.getAtt3())) {
						smallKey = Utils.getTypeValueKey(smallSd.getValuekey());
					} else {
						smallKey = smallSd.getValuekey();
					}
					// 背景墙特殊处理，（app必须配置special.productType背景墙类型）
					if (smallTypeKeys.indexOf("," + smallKey + ",") != -1) {
						productCategoryRel.setProductSmallTypeKey(smallKey);
						break;
					}
				}
			}
		}
	}

	// 过滤产品长、 高信息
	private void filterProductInfo(ProductCategoryRel productCategoryRel, BaseProduct baimoProduct,
			BaseProduct productSelected) {
		if (baimoProduct != null) {
			if (baimoProduct.getProductLength() != null && baimoProduct.getProductHeight() != null) {
				productCategoryRel.setProductLength(baimoProduct.getProductLength());
				productCategoryRel.setProductHeight(baimoProduct.getProductHeight());
			} else {
				productCategoryRel.setProductLength("-1");
				productCategoryRel.setProductHeight("-1");
			}
		} else {
			if (productSelected.getProductLength() != null && productSelected.getProductHeight() != null) {
				productCategoryRel.setProductLength(productSelected.getProductLength());
				productCategoryRel.setProductHeight(productSelected.getProductHeight());
			} else {
				productCategoryRel.setProductLength("-1");
				productCategoryRel.setProductHeight("-1");
			}
		}
	}

	@Override
	public ProductCategoryRel getWallTypeLogic(ProductCategoryRel productCategoryRel,
			DesignPlanProduct designPlanProduct, SysDictionary bigSd ) {
		String filterLength = Utils.getValue("app.filter.stretch.length", "0");
		// 绑定点有多个
		String parentProductId = designPlanProduct.getBindParentProductId();
		List<String> parentProIds = new ArrayList<String>();
		if (StringUtils.isNotBlank(parentProductId)) {
			String arrayParentProId[] = parentProductId.split(",");
			DesignPlanProduct dpp = null;
			// 如果该绑定产品存在，就不需查询绑定产品的分类
			for (String arrId : arrayParentProId) {
				dpp = new DesignPlanProduct();
				dpp.setIsDeleted(0);
				dpp.setPlanId(designPlanProduct.getPlanId());
				dpp.setInitProductId(Utils.getIntValue(arrId));

				List<DesignPlanProduct> ls = designPlanProductService.getList(dpp);
				// 如果背景墙存在空间则不显示背景墙产品
				if (Lists.isEmpty(ls) && ls.size() == 0) {
					parentProIds.add(arrId);
				}
			}
			// 判断是否是新增背景墙,是则过滤背景墙长高
			if (Lists.isNotEmpty(parentProIds) && parentProIds.size() > 0) {
				filterIsNewBgWall(productCategoryRel, bigSd, filterLength, parentProIds, arrayParentProId);
			}
		}
		return productCategoryRel;
	}

	// 判断是否是新增背景墙,是则过滤背景墙长高
	private void filterIsNewBgWall(ProductCategoryRel productCategoryRel, SysDictionary bigSd,
			String filterLength, List<String> parentProIds, String[] arrayParentProId) {
		productCategoryRel.setTemplateProductId(parentProIds);
		productCategoryRel.setShowBgWall(true);
		if ("3".equals(productCategoryRel.getProductTypeValue())) {
			productCategoryRel.setBeijing(true);
		} else {
			productCategoryRel.setStretch(true);
		}
		// 白模背景墙产品信息
		BaseProduct product = new BaseProduct();
		if (Utils.enableRedisCache()) {
			product = BaseProductCacher.get(Utils.getIntValue(arrayParentProId[0]));
		} else {
			product = baseProductService.get(Utils.getIntValue(arrayParentProId[0]));
		}
		// 过滤背景墙长高
		String fullPaveLength = product.getFullPaveLength();
		if (StringUtils.isBlank(fullPaveLength) || Utils.getIntValue(fullPaveLength) == 0) {
			fullPaveLength = product.getProductLength();
		}
		String productHeight = product.getProductHeight();
		if (StringUtils.isNotBlank(fullPaveLength) && Utils.getIntValue(fullPaveLength) > 0
				&& StringUtils.isNotBlank(productHeight) && Utils.getIntValue(productHeight) > 0) {

			productCategoryRel
					.setStartLength((Utils.getIntValue(fullPaveLength) - Utils.getIntValue(filterLength)) + 1);
			productCategoryRel.setEndLength(Utils.getIntValue(fullPaveLength) + Utils.getIntValue(filterLength));
			productCategoryRel.setBgWallHeight(productHeight);

			SysDictionary bmSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					product.getProductSmallTypeValue());
			SysDictionary sdic = null;
			if (bmSd != null && "baimo".equals(bmSd.getAtt3())) {
				sdic = sysDictionaryService.findOneByTypeAndValueKey(bigSd.getValuekey(),
						Utils.getTypeValueKey(bmSd.getValuekey()));
			}
			productCategoryRel.setProductSmallTypeValue(sdic == null ? 0 : sdic.getValue());
		} else {
			productCategoryRel.setValue(true);
		}
	}

	/**
	 * findRecommendCategoryProductResult优化版
	 * 
	 * @author huangsongbo
	 */
	public List<CategoryProductResult> findRecommendCategoryProductResultV2(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findRecommendCategoryProductResultV2(productCategoryRel);
	}

	/**
	 * findCustomizedCategoryProductResult优化版
	 * 
	 * @author huangsongbo
	 */
	public List<CategoryProductResult> findCustomizedCategoryProductResultV2(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findCustomizedCategoryProductResultV2(productCategoryRel);
	}

	public List<CategoryProductResult> findCustomizedCategoryProductResultV3(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findCustomizedCategoryProductResultV3(productCategoryRel);
	}

	public List<CategoryProductResult> findRecommendCategoryProductResultV3(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.findRecommendCategoryProductResultV3(productCategoryRel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEnvelope<CategoryProductResult> searchProduct(ProductCategoryRel productCategoryRel,
			HttpServletRequest request, SearchProductVO serachProductVO, LoginUser loginUser, DesignPlan designPlan) {
		Long startDate = System.currentTimeMillis();
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		DesignPlanProduct designPlanProduct = sethouseTypeSearchCondition(productCategoryRel,
				serachProductVO.getHouseType(), serachProductVO.getPlanProductId());
		int limit = productCategoryRel.getLimit();
		int start = productCategoryRel.getStart();
		long total = 0;
		if ("0".equals(serachProductVO.getTemplateProductId())) {
			serachProductVO.setTemplateProductId("");
		}
		// 获取黑名单配置信息(huangsongbo 2017.1.11)->start
		getBlackListInfo(productCategoryRel, loginUser);
		// 大小类,产品分类查询条件->start
		Long startDate3 = System.currentTimeMillis();
		// 加入产品编码或型号搜索条件
		addProductSearchCondition(productCategoryRel, serachProductVO);
		// 获取当前产品的大类和小类,可以删除
		// 获取产品大小类信息(配置写法去掉.修复数据字典中小类和白模_小类的编码一致.eg:basic_beij->beij)。排序时使用
		SysDictionary bigSd = null;
		SysDictionary bmSmallSd = null;
		SysDictionary smallSd = null;
		if (StringUtils.isNotBlank(serachProductVO.getProductTypeValue())) {
			// 根据类型和value获取类型名称数据
			bigSd = sysDictionaryService.getSysDictionaryByValue("productType",
					Integer.valueOf(serachProductVO.getProductTypeValue()));
		}
		if (bigSd != null && StringUtils.isNotBlank(serachProductVO.getSmallTypeValue())) {
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(serachProductVO.getSmallTypeValue()));
			bmSmallSd = sysDictionaryService.getSysDictionaryByValue(bigSd.getValuekey(),
					Integer.valueOf(serachProductVO.getSmallTypeValue()));
			// 根据类型和value获取类型名称数据
			bmSmallSd = sysDictionaryService.findOneByTypeAndValue(bigSd.getValuekey(),
					Integer.valueOf(serachProductVO.getSmallTypeValue()));
			// 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
			smallSd = sysDictionaryService.dealWithInconformity(bigSd, bmSmallSd);
			if (smallSd != null) {
				productCategoryRel.setProductSmallTypeValue(smallSd.getValue());
			} else {
				smallSd = bmSmallSd;
			}
		}
		// 当前产品是否是特殊产品，过滤产品用
		// 大小类,产品分类查询条件->end
		String versionType = Utils.getPropertyName("app", "sys.version.type", "1").trim();// 1为外网
																							// 2
																							// 为内网
																							// 默认为外网
		if (loginUser.getUserType() == 1 && "2".equals(versionType)) {
			productCategoryRel.setIsInternalUser("yes");
		}
		// 用户Id
		productCategoryRel.setUserId(loginUser.getId());
		BaseProduct productSelected = null;

		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->start
		// 设置产品空间类型搜索条件(客厅的灯只能搜出客厅的灯)->end
		Long startDate7 = System.currentTimeMillis();
		if (serachProductVO.getPlanProductId() != null && serachProductVO.getPlanProductId() != 0) {
			if (designPlanProduct.getProductId() != null && designPlanProduct.getProductId() > 0) {
				if (Utils.enableRedisCache()) {
					productSelected = BaseProductCacher.get(designPlanProduct.getProductId());
				} else {
					productSelected = baseProductService.get(designPlanProduct.getProductId());
				}
				// 获取查询属性产品的条件
				// 修改原因:查过滤属性应该查该产品对应的白膜的属性
				productCategoryRel = productAttributeService.getAttributeCondition(productCategoryRel,
						designPlanProduct.getInitProductId());
			}
			DesignTemplet designTemplet = getDesignTemplet(productCategoryRel, serachProductVO, designPlan, 
					designPlanProduct, productSelected);
			if (designTemplet == null) {
				logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
						+ designPlan.getId());
			} else {
				if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
					logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
							+ ",designId=" + designPlan.getId());
				}
			}
		}
		// 加入大缓存
		// 生成key
		Map<Object, Object> paramsMap = new HashMap<Object, Object>();
		paramsMap.put("productTypeValue", serachProductVO.getProductTypeValue());
		paramsMap.put("smallTypeValue", smallSd.getValue());
		// 白膜不同搜索产品列表不同
		paramsMap.put("templateProductId", serachProductVO.getTemplateProductId());
		paramsMap.put("categoryCode", productCategoryRel.getCategoryCode());
		// 属性过滤缓存
		ResponseEnvelope<CategoryProductResult> result = attrFilterCache(productCategoryRel, serachProductVO,
				 paramsMap);
		boolean stretch = false;
		try {
			if (result != null) {
				list = result.getDatalist();
				total = result.getTotalCount();
			} else {
				// 产品对应白膜信息->start
				BaseProduct baimoProduct = new BaseProduct();
				if (designPlanProduct != null) {
					baimoProduct = productBaimoInfo(serachProductVO,  designPlanProduct);
				}
				// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；
				// 空房模式，强制使用显示推荐+同类型数据模式，排除推荐数据（推荐中无数据）;硬装强制使用（推荐+全部）模式，排除根据实际配置执行)
				String onlyShowCustomization = null;
				if (StringUtils.isNotEmpty(serachProductVO.getProductTypeValue())
						&& StringUtils.isNotEmpty(serachProductVO.getSmallTypeValue())
						&& new Integer(serachProductVO.getProductTypeValue()).intValue() > 0
						&& new Integer(serachProductVO.getSmallTypeValue()).intValue() > 0) {
					// 是否为硬装产品
					SysDictionary sysDictionary = new SysDictionary();
					sysDictionary.setValue(Integer.valueOf(serachProductVO.getProductTypeValue()));
					sysDictionary.setSmallValue(Integer.valueOf(serachProductVO.getSmallTypeValue()));
					sysDictionary = sysDictionaryService.checkType(sysDictionary);// 需调整
					// att4等于1表示为硬装产品
					if ("1".equals(sysDictionary.getAtt4())) {
						onlyShowCustomization = "false";
					} else if (StringUtils.equals("1", sysDictionary.getAtt6())) {// 样板房定制产品
						onlyShowCustomization = "false";
					}

					String bjType = Utils.getValue("app.smallProductType.stretch", "");
					stretch = Utils.isMateProductType(smallSd == null ? "" : smallSd.getValuekey(), bjType);
					if (stretch) {
						onlyShowCustomization = "true";
						if ("3".equals(serachProductVO.getProductTypeValue())) {
							productCategoryRel.setBeijing(true);
						} else {
							productCategoryRel.setStretch(true);
						}
					}
					productCategoryRel = this.bgWallAndSpecialTypeInfo(productCategoryRel, stretch, bigSd, smallSd,
							baimoProduct, productSelected);
				}
				// 显示所有分类相关数据
				productCategoryRel.setOnlyShowRecommend(false);
				productCategoryRel.setExceptRecommend(false);

				// 设置序列号查询条件(huangsongbo 2017.1.11)
				Long startDate8_1 = System.currentTimeMillis();
				List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
				productCategoryRel.setBaseProduct(baseProductList);

				// 检测用户绑定的授权码是否有设计公司的授权码,如果有,则去掉授权码查询条件 ->start
				int count = authorizedConfigService.findCountWhereCompanyTypeIsDesignByUserId(loginUser.getId());
				if (count > 0) {
					// 有设计公司的授权码,则不需要授权码过滤条件
					productCategoryRel.setBaseProduct(null);
				}
				// 检测用户绑定的授权码是否有设计公司的授权码,如果有,则去掉授权码查询条件 ->end

				// 设置序列号查询条件(huangsongbo 2017.1.11)->end

				// 设置是否要查询多个小分类 ->start created by huangsongbo 20170601
				// sortSmallTypeValueList:需要排前面的小类valueList
				List<Integer> sortSmallTypeValueList = new ArrayList<Integer>();
				Integer initProductId = designPlanProduct == null ? -1 : designPlanProduct.getInitProductId();
				BaseProduct baimoBaseProductInit = null;
				if (initProductId != null && initProductId > 0) {
					baimoBaseProductInit = baseProductService.get(initProductId);
				}
				Map<String, List<String>> differenceListMap = (Map<String, List<String>>) showMoreSmallTypeMap.get("differenceListMap");
				List<String> smallTypeValueKeyList = null;
				List<Integer> smallTypeValueList = null;
				if (bmSmallSd != null && bmSmallSd.getId() != null && bmSmallSd.getId() > 0
						&& showMoreSmallTypeMap != null) {
					String smallTypeValueKey = bmSmallSd.getValuekey().replace("basic_", "");
					Map<String, List<String>> showMoreSmallTypeInfoMap = (Map<String, List<String>>) showMoreSmallTypeMap
							.get("showMoreSmallTypeInfoMap");
					// showMoreSmallTypeInfoMap:{tijx=[tijx, diz], chufm=[chufm,
					// yangtm]}
					// smallTypeValueKey:yangtm
					if (showMoreSmallTypeInfoMap.containsKey(smallTypeValueKey)) {
						// 符合显示多个小类的配置
						smallTypeValueKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey);
						smallTypeValueList = sysDictionaryService.getValueByTypeAndValueKeylist(bmSmallSd.getType(),
								smallTypeValueKeyList);
						// 设置排序(点击的该产品的小类(如果是白膜,则要转化成白膜对应的产品小类))
						if (smallSd != null && smallSd.getValue() != null) {
							sortSmallTypeValueList.remove(smallSd.getValue());
							sortSmallTypeValueList.add(smallSd.getValue());
						}
					} else {
						// 可能这个地砖是从踢脚线白膜换过来的(该地砖对应的白膜是踢脚线白膜)
						if (differenceListMap.containsKey(smallTypeValueKey)) {
							// 检测对应的白膜是什么小类(init_product_id)
							if (baimoBaseProductInit != null) {
								SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue(
										"productType", Integer.valueOf(baimoBaseProductInit.getProductTypeValue()),
										baimoBaseProductInit.getProductSmallTypeValue());
								if (sysDictionary != null) {
									String smallTypeValueKey2 = sysDictionary.getValuekey().trim().replace("basic_",
											"");
									if (differenceListMap.get(smallTypeValueKey).indexOf(smallTypeValueKey2) != -1) {
										// 按白膜查询
										smallTypeValueKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey2);
										smallTypeValueList = sysDictionaryService.getValueByTypeAndValueKeylist(
												bmSmallSd.getType(), smallTypeValueKeyList);
										// 设置排序
										// 找到basic_yangtm对应的yangtm的数据字典
										SysDictionary sysDictionary2 = sysDictionaryService
												.findOneByTypeAndValueKey(sysDictionary.getType(), smallTypeValueKey2);
										if (sysDictionary2 != null) {
											sortSmallTypeValueList.remove(sysDictionary2.getValue());
											sortSmallTypeValueList.add(sysDictionary2.getValue());
										}
									}
								}
							}
						}
					}
				}
				productCategoryRel.setSmallTypeList(smallTypeValueKeyList);
				productCategoryRel.setSmallTypeValueList(smallTypeValueList);
				productCategoryRel.setSortSmallTypeValueList(sortSmallTypeValueList);
				// 设置是否要查询多个小分类 ->end created by huangsongbo 20170601
				List<CategoryProductResult> customizeList = new ArrayList<>();// 定制
				List<CategoryProductResult> recommendList = new ArrayList<>();// 推荐和分类
				int customizeCount = 0;
				int recommendCount = 0;
				// 查询定制产品
				if (serachProductVO.getSpaceCommonId() != null) {
					if (ProductTypeConstant.PRODUCT_BIG_TYPE_QIANGM.equals(bigSd == null ? "" : bigSd.getValuekey()) && designPlanProduct != null) {
						productCategoryRel = this.getWallTypeLogic(productCategoryRel, designPlanProduct, bigSd);
					} else if (ProductTypeConstant.PRODUCT_BIG_TYPE_TIANH.equals(bigSd == null ? "" : bigSd.getValuekey())) {
						// 如果查天花只显示定制产品
						onlyShowCustomization = "true";
					}
					// count查询->start
					Long startDate9 = System.currentTimeMillis();
					// 非定制只查询推荐产品
					if (!"true".equals(onlyShowCustomization)) {
						if (Utils.enableRedisCache()) {
							recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
						} else {
							// 新过滤方案(done)
							recommendCount = this.findRecommendCategoryProductResultCount(productCategoryRel);
						}
					}
					// 定制类产品的数据获取
					if (Utils.enableRedisCache()) {
						customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
					} else {
						// 新过滤方案(done)
						customizeCount = this.findCustomizedCategoryProductResultCount(productCategoryRel);
					}
					// 如果是厂商，则只能查询这个厂商品牌下的产品,没有则查询除其外所有品牌的产品
					if (3 == loginUser.getUserType() && customizeCount + recommendCount == 0) {
						// add品牌,大类,小类,产品
						// 针对搜索条件(大类同,小类异的情况->返回空列表)
						boolean falg2 = false;
						String productTypeValue2 = "0";
						if (StringUtils.equals(serachProductVO.getProductTypeValue().trim(), "0")) {
							SysDictionary sysDictionaryBigType = sysDictionaryService
									.findOneByValueKeyInCache(productCategoryRel.getCategoryCode().replace(".", ""));
							if (!StringUtils.equals("productType", sysDictionaryBigType.getType())) {
								sysDictionaryBigType = sysDictionaryService.findOneByTypeAndValueKey("productType",
										sysDictionaryBigType.getType());
							}
							if (sysDictionaryBigType != null)
								productTypeValue2 = sysDictionaryBigType.getValue() + "";
						} else {
							productTypeValue2 = serachProductVO.getProductTypeValue().trim();
						}
						// 通过序列号过滤数据（当序列号存在该大类、小类产品或品牌时，只显示该品牌或该大类或小类的产品，如果不存在则查询所有数据）
						for (BaseProduct baseProduct : baseProductList) {
							// 识别序列号有没有和productTypeValue相同的大类
							if (StringUtils.equals(productTypeValue2, baseProduct.getProductTypeValue())) {
								falg2 = true;
								break;
							}
						}
						if (falg2) {
							productCategoryRel.setBaseProduct(null);
						}
						// add品牌,大类,小类,产品->end
						if (!"true".equals(onlyShowCustomization)) {
							if (Utils.enableRedisCache()) {
								recommendCount = ProductCategoryRelCacher.getRecommendCategoryCount(productCategoryRel);
							} else {
								// 新过滤方案(done)
								recommendCount = this.findRecommendCategoryProductResultCount(productCategoryRel);
							}
						}
						if (Utils.enableRedisCache()) {
							customizeCount = ProductCategoryRelCacher.getCustomizedCategoryCount(productCategoryRel);
						} else {
							// 新过滤方案(done)
							customizeCount = this.findCustomizedCategoryProductResultCount(productCategoryRel);
						}
						// 如果搜索不到,则去掉授权码查询条件再搜索
						total = recommendCount + customizeCount;
						if (total == 0 && productCategoryRel.getBaseProduct() != null
								&& productCategoryRel.getBaseProduct().size() != 0) {
							productCategoryRel.setBaseProduct(null);
							if (!"true".equals(onlyShowCustomization)) {
								if (Utils.enableRedisCache()) {
									recommendCount = ProductCategoryRelCacher
											.getRecommendCategoryCount(productCategoryRel);
								} else {
									// 新过滤方案(done)
									recommendCount = this.findRecommendCategoryProductResultCount(productCategoryRel);
								}
							}
							if (Utils.enableRedisCache()) {
								customizeCount = ProductCategoryRelCacher
										.getCustomizedCategoryCount(productCategoryRel);
							} else {
								// 新过滤方案(done)
								customizeCount = this.findCustomizedCategoryProductResultCount(productCategoryRel);
							}
						}
					}
					// count查询->end

					// 定制产品list查询->start
					Long startDate11 = System.currentTimeMillis();
					if (customizeCount > 0) {
						if (Utils.enableRedisCache()) {
							customizeList = ProductCategoryRelCacher.getListCustomizedCategoryCode(productCategoryRel);
						} else {
							// 新过滤方案(done)
							customizeList = this.findCustomizedCategoryProductResult(productCategoryRel);
						}
					}
					// 定制产品list查询->end
				}

				if ("true".equals(onlyShowCustomization)) {
					list.addAll(customizeList);
				} else {
					// 推荐产品list->
					list = recommendProductList(productCategoryRel,  list, customizeList);
				}
				// list查询->end

				// 排序 推荐 小类 匹配度 使用量 色系->start
				list = getProductList(list, productCategoryRel);
				// 排序 推荐 小类 匹配度 使用量 色系->end

				total = recommendCount + customizeCount;

				// 存入缓存
				if (Utils.enableRedisCache()) {
					CommonCacher.addAll(ModuleType.BaseProduct, "searchProduct", paramsMap,
							new ResponseEnvelope<>(total, list, productCategoryRel.getMsgId()));
				}
			}

			// 分页->start
			list = Utils.paging(list, start, limit);
			// 分页->end

			// 获取需要自动携带白模产品的配置->start
			Long startDate20_3 = System.currentTimeMillis();
			Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
			String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app",
					"design.designPlan.autoCarryBaimoProducts", "");
			if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
				getAutoCarryBaimoProduct(autoCarryMap, autoCarryBaimoProducrsConfig);
			}
			// 获取需要自动携带白模产品的配置->end

			// 附加属性->start
			Long startDate17 = System.currentTimeMillis();
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				CategoryProductResult productResult = list.get(i);
				// 缓存key
				Map<Object, Object> keyMap = new HashMap<Object, Object>();
				keyMap.put("productId", productResult.getProductId());
				keyMap.put("designPlanId", designPlan.getId());
				if (designPlanProduct != null) {
					keyMap.put("designPlanProductId", designPlanProduct.getId());
				}
				// 缓存取数据
				ResponseEnvelope<CategoryProductResult> responseEnvelopeProductResult = null;
				if (Utils.enableRedisCache()) {
					responseEnvelopeProductResult = CommonCacher.getAll(ModuleType.BaseProduct,
							"decorationProductInfoV3", keyMap);
				}
				if (responseEnvelopeProductResult == null) {
					BaseProduct baseProduct = null;
					// join 查询大小类信息,是否是硬装,是否是组合
					Map<String, Integer> InfoByIdMap = new HashMap<>();
					InfoByIdMap.put("id", productResult.getProductId());
					InfoByIdMap.put("designTempletId", designPlan.getDesignTemplateId());
					baseProduct = baseProductService.getInfoById(InfoByIdMap);
					if (baseProduct == null) {
						logger.info("baseProduct is null;productId=" + productResult.getProductId() + ";");
						continue;
					}
					productResult = baseProductService.decorationProductInfoV3(productResult, baseProduct, designPlan,
							designPlanProduct, autoCarryMap, request);
					if (stretch) {
						productResult.setTemplateProductId(serachProductVO.getTemplateProductId());
					}
					if (Utils.enableRedisCache()) {
						CommonCacher.addAll(ModuleType.BaseProduct, "decorationProductInfoV3", keyMap,
								new ResponseEnvelope<CategoryProductResult>(productResult));
					}
				} else {
					CategoryProductResult productResultFromCache = (CategoryProductResult) responseEnvelopeProductResult
							.getObj();
					list.remove(i);
					list.add(i, productResultFromCache);
				}
			}
			// 附加属性->end

			// 列表排序,同小类的排在前面，同大类其次，剩余的排最后
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, DATA_EXCEPTION, productCategoryRel.getMsgId());
		}
		return new ResponseEnvelope<CategoryProductResult>(total, list, productCategoryRel.getMsgId());
	}

	// 属性过滤缓存
	private ResponseEnvelope<CategoryProductResult> attrFilterCache(ProductCategoryRel productCategoryRel,
			SearchProductVO serachProductVO,Map<Object, Object> paramsMap) {
		if (productCategoryRel.getAttributeConditionList() != null
				&& productCategoryRel.getAttributeConditionList().size() > 0) {
			String str = "";
			for (String condition : productCategoryRel.getAttributeConditionList()) {
				str += condition + ",";
			}
			paramsMap.put("attributeConditionList", str);
		}
		paramsMap.put("houseType", serachProductVO.getHouseType());
		paramsMap.put("productModelNumber", productCategoryRel.getProductModelNumber());
		ResponseEnvelope<CategoryProductResult> result = null;
		if (Utils.enableRedisCache()) {
			result = CommonCacher.getAll(ModuleType.BaseProduct, "searchProduct", paramsMap);
		}
		return result;
	}

	// 获取需要自动携带白模产品的配置
	private void getAutoCarryBaimoProduct(Map<String, AutoCarryBaimoProducts> autoCarryMap,
			String autoCarryBaimoProducrsConfig) {
		JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
		try {
			List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
					.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
			if (autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0) {
				for (AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs) {
					autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(), autoCarryBaimoProductsConfig);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(GAIN_BAIMO_PRODUCT_EXCEPTION);
		}
	}

	private DesignTemplet getDesignTemplet(ProductCategoryRel productCategoryRel, SearchProductVO serachProductVO,
			DesignPlan designPlan,   DesignPlanProduct designPlanProduct,
			BaseProduct productSelected) {
		Integer templatePlanProductId;
		productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
		productCategoryRel.setTempletId(designPlan.getDesignTemplateId());
		templatePlanProductId = designPlanProduct.getPlanProductId();
		productCategoryRel.setDesignProductId(templatePlanProductId);
		if (StringUtils.isBlank(serachProductVO.getTemplateProductId())) {
			serachProductVO.setTemplateProductId(productSelected.getBmIds());
		}
		if (StringUtils.isNotBlank(serachProductVO.getTemplateProductId())) {
			String[] arraytemplateProductId = serachProductVO.getTemplateProductId().split(",");
			productCategoryRel.setTemplateProductId(Arrays.asList(arraytemplateProductId));
		}

		DesignTemplet designTemplet = null;
		if (Utils.enableRedisCache()) {
			designTemplet = DesignTempletCacher.get(designPlan.getDesignTemplateId());
		} else {
			designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
		}
		return designTemplet;
	}

	// 推荐产品list->
	private List<CategoryProductResult> recommendProductList(ProductCategoryRel productCategoryRel, 
			List<CategoryProductResult> list, List<CategoryProductResult> customizeList) {
		List<CategoryProductResult> recommendList;
		Long startDate15 = System.currentTimeMillis();
		// 查询推荐和分类
		if (Lists.isNotEmpty(customizeList) && customizeList.size() > 0) {
			list.addAll(customizeList);
			if (Utils.enableRedisCache()) {
				recommendList = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
			} else {
				// 新过滤方案(done)
				recommendList = this.findRecommendCategoryProductResultV2(productCategoryRel);
			}
			list.addAll(recommendList);
		} else {
			if (Utils.enableRedisCache()) {
				list = ProductCategoryRelCacher.getListRecommendCategoryCode(productCategoryRel);
			} else {
				// old function

				// new function
				list = this.findRecommendCategoryProductResultV2(productCategoryRel);
			}
		}
		return list;
	}

	// 产品对应白膜信息-
	private BaseProduct productBaimoInfo(SearchProductVO serachProductVO,  
			DesignPlanProduct designPlanProduct) {
		BaseProduct baimoProduct;
		if (designPlanProduct.getInitProductId() != null && designPlanProduct.getInitProductId().intValue() > 0) {
			if (Utils.enableRedisCache()) {
				baimoProduct = BaseProductCacher.get(designPlanProduct.getInitProductId());
			} else {
				baimoProduct = baseProductService.get(designPlanProduct.getInitProductId());
			}
		} else {
			if (Utils.enableRedisCache()) {
				baimoProduct = BaseProductCacher.get(Utils.getIntValue(serachProductVO.getTemplateProductId()));
			} else {
				baimoProduct = baseProductService.get(Utils.getIntValue(serachProductVO.getTemplateProductId()));
			}
		}
		return baimoProduct;
	}

	// 加入产品编码或型号搜索条件
	private void addProductSearchCondition(ProductCategoryRel productCategoryRel, SearchProductVO serachProductVO) {
		if (productCategoryRel.getCategoryCode().contains(",")) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		} else {
			productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
		}
		if (StringUtils.isNotBlank(serachProductVO.getProductModelNumber())) {
			productCategoryRel.setProductModelNumber(serachProductVO.getProductModelNumber().trim());
		}
		productCategoryRel.setSpaceCommonId(serachProductVO.getSpaceCommonId());
		productCategoryRel.setProductTypeValue(serachProductVO.getProductTypeValue());
	}

	// 获取黑名单配置信息
	private void getBlackListInfo(ProductCategoryRel productCategoryRel, LoginUser loginUser) {
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		productCategoryRel.setBlacklistSet(blacklist);
	}

	// 按搜索产品属性匹配度排序
	public List<CategoryProductResult> getProductList(List<CategoryProductResult> list,
			ProductCategoryRel productCategoryRel) {

		// 如果该产品关联了色系则关联色系排序->start
		Long startDate2_1 = new Date().getTime();
		baseProductService.productColorsSortV2(list );
		Long startDate2_2 = new Date().getTime();
		logger.info(COLOUR_SORT + (startDate2_2 - startDate2_1));
		// 如果该产品关联了色系则关联色系排序->end

		// 列表排序,推荐,小类,同属性匹配度,使用量,色系排序->start
		Long startDate3_1 = new Date().getTime();
		ComparatorO cpmparator = new ComparatorO();
		cpmparator.setList(productCategoryRel.getSortSmallTypeValueList());
		Collections.sort(list, cpmparator);
		Long startDate3_2 = new Date().getTime();
		logger.info(LIST_RECOMMEND_SUBCLASS_SORT + (startDate3_2 - startDate3_1));
		// 列表排序,推荐,小类,同属性匹配度,使用量,色系排序-->end

		return list;
	}

	private DesignPlanProduct sethouseTypeSearchCondition(ProductCategoryRel productCategoryRel, String houseType,
			Integer planProductId) {
		DesignPlanProduct designPlanProduct = null;
		if (StringUtils.isNotBlank(houseType)) {
			// 对应出productHouseType的数据字典的value
			SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
			sysDictionarySearch.setAtt1(houseType);
			sysDictionarySearch.setType("productHouseType");
			List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
			List<String> productHouseTypeList = new ArrayList<String>();// 过滤条件
			List<String> productHouseTypeList3 = new ArrayList<String>();
			if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
				for (SysDictionary sysDictionary : sysDictionaryList) {
					productHouseTypeList.add("" + sysDictionary.getValue());
					productHouseTypeList3.add("" + sysDictionary.getValue());
				}
			}
			// 得到产品空间类型valueList
			// String productHouseTypesStr=productSelected.getHouseType();
			// 取白膜的productHouseType
			if (planProductId != null && planProductId != 0) {
				designPlanProduct = designPlanProductService.get(planProductId);
				if (designPlanProduct == null) {
					return designPlanProduct;
				}
				Integer baimoProductId = designPlanProduct.getInitProductId();
				BaseProduct baimoProduct = baseProductService.get(baimoProductId);
				if (baimoProduct != null) {
					String productHouseTypesStr = baimoProduct.getHouseTypeValues();
					if (StringUtils.isNotBlank(productHouseTypesStr)) {
						List<String> productHouseTypeList2 = Utils.getListFromStr(productHouseTypesStr);
						productHouseTypeList.retainAll(productHouseTypeList2);
					}
				}
			}
			if (productHouseTypeList.size() > 0) {
				productCategoryRel.setHouseTypeList(productHouseTypeList);
			} else {
				productCategoryRel.setHouseTypeList(productHouseTypeList3);
			}
		}
		return designPlanProduct;
	}

	public class ComparatorP implements Comparator<Object> {
		public int compare(Object obj1, Object obj2) {
			ProductProps unity1 = (ProductProps) obj1;
			ProductProps unity2 = (ProductProps) obj2;
			int flag = (unity1.getSequenceNumber() == null ? new Integer(0) : new Integer(unity1.getSequenceNumber()))
					.compareTo(unity2.getSequenceNumber() == null ? new Integer(0)
							: new Integer(unity2.getSequenceNumber()));
			if (flag == 0) {
				return (unity1.getSequenceNumber() == null ? new Integer(0) : new Integer(unity1.getSequenceNumber()))
						.compareTo(unity2.getSequenceNumber() == null ? new Integer(0)
								: new Integer(unity2.getSequenceNumber()));
			} else {
				return flag;
			}
		}
	}

	public class ComparatorO implements Comparator<Object> {
		// 优先排序小类
		private List<Integer> list;

		public List<Integer> getList() {
			return list;
		}

		public void setList(List<Integer> list) {
			this.list = list;
		}

		public int compare(Object obj1, Object obj2) {
			CategoryProductResult unity1 = (CategoryProductResult) obj1;
			CategoryProductResult unity2 = (CategoryProductResult) obj2;
			// 小类排序.优先小类排前面
			Integer productOrderSmallTypeValue1 = this.getProductOrderSmallTypeValue(unity1);
			Integer productOrderSmallTypeValue2 = this.getProductOrderSmallTypeValue(unity2);
			Integer productOrder1 = unity1.getProductOrdering() == null ? new Integer(0)
					: new Integer(unity1.getProductOrdering());
			Integer productOrder2 = unity2.getProductOrdering() == null ? new Integer(0)
					: new Integer(unity2.getProductOrdering());
			Integer colorsOrder1 = unity1.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE)
					: new Integer(unity1.getColorsOrdering());
			Integer colorsOrder2 = unity2.getColorsOrdering() == null ? new Integer(Integer.MAX_VALUE)
					: new Integer(unity2.getColorsOrdering());

			if (this.list != null && productOrderSmallTypeValue1.compareTo(productOrderSmallTypeValue2) != 0) { // 小类优先排序
				return productOrderSmallTypeValue2.compareTo(productOrderSmallTypeValue1);
			} else if (unity1.getOrderType().compareTo(unity2.getOrderType()) != 0) { // 定制、推荐、小类
				return unity1.getOrderType().compareTo(unity2.getOrderType());
			} else if (productOrder1.compareTo(productOrder2) != 0) {// 属性匹配度
				return productOrder1.compareTo(productOrder2);
			} else if (colorsOrder1.compareTo(colorsOrder2) != 0) {// 色系排序
				return colorsOrder1.compareTo(colorsOrder2);
			} else { // 使用量
				return (unity2.getProductCount() == null ? new Integer(0) : unity2.getProductCount())
						.compareTo(unity1.getProductCount() == null ? new Integer(0) : unity1.getProductCount());
			}
		}

		private Integer getProductOrderSmallTypeValue(CategoryProductResult categoryProductResult) {
			if (this.list == null || this.list.size() == 0) {
				return 0;
			}
			if (categoryProductResult.getProductSmallTypeValue() != null) {
				if (this.list.indexOf(categoryProductResult.getProductSmallTypeValue()) != -1) {
					return 1;
				}
			}
			return 0;
		}
	}


	// 序列号查询条件 -
	private Integer numQueryCondition(LoginUser loginUser, PrepProductSearchInfo prepProductSearchInfo) {
		Integer userType = loginUser.getUserType();
		if (userType == 3) {
			List<BaseProduct> baseProductList = baseProductService.getSelectConditionByAuthorizedConfig(loginUser);
			prepProductSearchInfo.setBaseProductList(baseProductList);
		}
		return userType;
	}

	// 获取黑名单配置信息
	private void getBlackInfo(LoginUser loginUser, PrepProductSearchInfo prepProductSearchInfo) {
		Set<String> blacklist = baseProductService.getBlacklist(loginUser.getId());
		if (blacklist != null && blacklist.size() > 0) {
			List<PrepProductSearchInfo> prepProductSearchInfoListForBlackList = sysDictionaryService
					.getbigTypeValueAndSmallTypeValueBySmallTypeValuekeyList(blacklist);
			prepProductSearchInfo.setPrepProductSearchInfoListForBlackList(prepProductSearchInfoListForBlackList);
		}
	}

	// 解析配置filter.productLW.productSmallType并设置对应的查询条件 -
	private void resolveProductSmallType(PrepProductSearchInfo prepProductSearchInfo, BaseProduct baimoBaseProductInit,
			SysDictionary sysDictionarySmallType, String filterProductLW) {
		List<String> filterProductLWSmallTypeList = Utils.getListFromStr(filterProductLW);
		if (sysDictionarySmallType != null) {
			String smallTypeCode = sysDictionarySmallType.getValuekey();
			if (filterProductLWSmallTypeList.indexOf(smallTypeCode) != -1) {
				// 设置长,宽过滤条件(小于)
				if (baimoBaseProductInit != null) {
					Integer productLength = Integer
							.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductLength())
									? baimoBaseProductInit.getProductLength() : "0");
					prepProductSearchInfo.setLessProductLength(productLength);
					Integer productWidth = Integer
							.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductWidth())
									? baimoBaseProductInit.getProductWidth() : "0");
					prepProductSearchInfo.setLessProductWidth(productWidth);
				}
			}
		}
	}

	// 得到小类数据字典
	private void getSmallDictionary(PrepProductSearchInfo prepProductSearchInfo, BaseProduct baimoBaseProductInit,
			String filterProductLH, SysDictionary sysDictionarySmallType) {
		List<String> filterProductLHSmallTypeList = Utils.getListFromStr(filterProductLH);
		if (sysDictionarySmallType != null) {
			String smallTypeCode = sysDictionarySmallType.getValuekey();
			if (filterProductLHSmallTypeList.indexOf(smallTypeCode) != -1) {
				// 设置长,高过滤条件
				if (baimoBaseProductInit != null) {
					Integer productLength = Integer
							.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductLength())
									? baimoBaseProductInit.getProductLength() : "0");
					prepProductSearchInfo.setProductLength(productLength);
					Integer productHeight = Integer
							.valueOf(StringUtils.isNotBlank(baimoBaseProductInit.getProductHeight())
									? baimoBaseProductInit.getProductHeight() : "0");
					prepProductSearchInfo.setProductHeight(productHeight);
				}
			}
		}
	}

	/**
	 * 普通背景墙长高过滤
	 * 
	 * @param productTypeValue
	 * @param prepProductSearchInfo
	 * @param baseProductProp
	 */
	private void bjWallFilter(Integer productTypeValue, PrepProductSearchInfo prepProductSearchInfo,
			BaseProduct baseProductProp) {
		if (baseProductProp != null) {
			// 是背景墙(限制普通背景墙的长高)
			if (3 == productTypeValue) {
				prepProductSearchInfo.setBeijing(true);
			} else {
				prepProductSearchInfo.setStretch(true);
			}
			// 设置长高查询条件
			baseProductProp.getProductLength();
			String beijingHeightStr = baseProductProp.getProductHeight();
			String beijingLengthStr = baseProductProp.getProductLength();
			prepProductSearchInfo.setBeijingHeight(
					StringUtils.isNotBlank(beijingHeightStr) ? Integer.valueOf(beijingHeightStr) : null);
			Integer beijingLengthStart = 0;
			Integer beijingLengthEnd = 0;
			if (StringUtils.isNotBlank(baseProductProp.getFullPaveLength())) {
				Integer fullPaveLength = Integer.valueOf(baseProductProp.getFullPaveLength());
				Integer beijingRange = Integer.valueOf(Utils.getValue("app.filter.stretch.length", "10"));
				Integer curtainRange = Integer.valueOf(Utils.getValue("app.filter.3mAbove.stretch.length", "20"));
				if (16 == productTypeValue && fullPaveLength > 300) {
					beijingLengthStart = fullPaveLength - curtainRange + 1;
					beijingLengthEnd = fullPaveLength + curtainRange;
				} else {
					beijingLengthStart = fullPaveLength - beijingRange + 1;
					beijingLengthEnd = fullPaveLength + beijingRange;
				}
			} else {
				beijingLengthStart = 0;
				beijingLengthEnd = StringUtils.isNotBlank(beijingLengthStr) ? Integer.valueOf(beijingLengthStr) : null;
			}
			prepProductSearchInfo.setBeijingLengthStart(beijingLengthStart);
			prepProductSearchInfo.setBeijingLengthEnd(beijingLengthEnd);
		}
	}

	/**
	 * 处理搜出多个小类的情况
	 * 
	 * @param prepProductSearchInfo
	 * @param baimoBaseProductInit
	 * @param sysDictionarySmallType
	 * @param smallTypeValueTemp
	 * @param differenceListMap
	 */
	private void handleSmallType(PrepProductSearchInfo prepProductSearchInfo, BaseProduct baimoBaseProductInit,
			SysDictionary sysDictionarySmallType, Integer smallTypeValueTemp,
			Map<String, List<String>> differenceListMap) {
		List<Integer> smallTypeValueList = new ArrayList<Integer>();
		smallTypeValueList.add(smallTypeValueTemp);

		// 处理搜出多个小类的情况 ->start
		if (sysDictionarySmallType != null && showMoreSmallTypeMap != null) {
			String smallTypeValueKey = sysDictionarySmallType.getValuekey().replace("basic_", "");
			Map<String, List<String>> showMoreSmallTypeInfoMap = (Map<String, List<String>>) showMoreSmallTypeMap
					.get("showMoreSmallTypeInfoMap");
			// showMoreSmallTypeInfoMap:{tijx=[tijx, diz], chufm=[chufm,
			// yangtm]}
			// smallTypeValueKey:yangtm
			if (showMoreSmallTypeInfoMap.containsKey(smallTypeValueKey)) {
				// 符合显示多个小类的配置
				showMoreSmallType(prepProductSearchInfo, sysDictionarySmallType, smallTypeValueTemp, smallTypeValueList,
						smallTypeValueKey, showMoreSmallTypeInfoMap);
			} else {
				// 可能这个地砖是从踢脚线白膜换过来的(该地砖对应的白膜是踢脚线白膜)
				if (differenceListMap.containsKey(smallTypeValueKey)) {
					// 检测对应的白膜是什么小类(init_product_id)
					if (baimoBaseProductInit != null) {
						checkBaimoType(prepProductSearchInfo, baimoBaseProductInit, sysDictionarySmallType,
								differenceListMap, smallTypeValueList, smallTypeValueKey, showMoreSmallTypeInfoMap);
					}
				}
			}
		}
		// 处理搜出多个小类的情况 ->end

		prepProductSearchInfo.setSmallTypeValueList(smallTypeValueList);
	}

	/**
	 * 过滤属性是依据该产品对应的初始化白膜的过滤属性
	 * 
	 * @param prepProductSearchInfo
	 * @param designPlanProduct
	 * @param baseProductProp
	 */
	private void productFilter(PrepProductSearchInfo prepProductSearchInfo, DesignPlanProduct designPlanProduct,
			BaseProduct baseProductProp) {
		List<ProductPropsSimple> productPropsSimpleListBaimo = baseProductService
				.getProductPropsSimpleByProductId(baseProductProp.getId());
		if (productPropsSimpleListBaimo != null && productPropsSimpleListBaimo.size() > 0) {
			// 取出排序属性+过滤属性
			List<ProductPropsSimple> productOrderPropList = new ArrayList<ProductPropsSimple>();
			List<ProductPropsSimple> productFilterPropList = new ArrayList<ProductPropsSimple>();
			for (ProductPropsSimple productPropsSimple : productPropsSimpleListBaimo) {
				if (productPropsSimple.getIsSort() == 1) {
					// 过滤属性
					productFilterPropList.add(productPropsSimple);
				}
			}
			// 处理过滤属性 ->start
			if (productFilterPropList.size() > 0) {
				prepProductSearchInfo.setHasFilterProps(true);
				prepProductSearchInfo.setBaimoProductId(baseProductProp.getId());
			}
			// 处理过滤属性 ->end
			// 处理排序属性 ->start
			// 排序属性是按照点击的产品的排序属性决定匹配度
			List<ProductPropsSimple> productPropsSimpleList = baseProductService
					.getProductPropsSimpleByProductId(designPlanProduct.getProductId());
			if (productPropsSimpleList != null && productPropsSimpleList.size() > 0) {
				// 取出排序属性+过滤属性
				for (ProductPropsSimple productPropsSimple : productPropsSimpleList) {
					if (productPropsSimple.getIsSort() == 0) {
						// 排序属性
						productOrderPropList.add(productPropsSimple);
					}
				}
			}
			if (productOrderPropList.size() > 0) {
				prepProductSearchInfo.setHasOrderProps(true);
				prepProductSearchInfo.setProductId(designPlanProduct.getProductId());
			}
			// 处理排序属性 ->end
		}
	}

	// 解析配置special.productType并设置对应的查询条件 -
	private SysDictionary setQueryCondition(Integer productTypeValue, Integer smallTypeValue,
			PrepProductSearchInfo prepProductSearchInfo, BaseProduct baimoBaseProductInit,
			DesignPlanProduct designPlanProduct) {
		String filterProductLH = Utils.getValue("filter.productLH.productSmallType", "");
		// 得到小类数据字典
		SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByTypeAndValueAndValue("productType",
				productTypeValue, smallTypeValue);
		if (StringUtils.isNotBlank(filterProductLH)) {
			getSmallDictionary(prepProductSearchInfo, baimoBaseProductInit, filterProductLH, sysDictionarySmallType);
		}
		// 解析配置filter.productLW.productSmallType并设置对应的查询条件 ->end

		// 解析配置special.productType并设置对应的查询条件 ->start
		// 如果点击的产品是白膜,处理小类:basic_mengk变成mengk
		Integer smallTypeValueTemp = smallTypeValue;
		if (designPlanProduct != null
				&& designPlanProduct.getProductId().equals(designPlanProduct.getInitProductId())) {
			// 点击的是白膜
			// 特殊处理背景墙(basic_beij->beijing)
			if (3 == productTypeValue && 16 == smallTypeValue) {
				smallTypeValueTemp = 17;
			} else {
				smallTypeValueTemp = sysDictionaryService.getProductValueByBaimoValue(productTypeValue,
						smallTypeValue) == null ? smallTypeValueTemp
								: sysDictionaryService.getProductValueByBaimoValue(productTypeValue, smallTypeValue);
			}
		}
		// in/notIn
		Map<String, Object> specialProductTypeMap = null;
		SysDictionary sysDictionarySmallTypeTemp = sysDictionaryService.findOneByTypeAndValueAndValue("productType",
				productTypeValue, smallTypeValueTemp);
		Map<String, List<String>> differenceListMap = (Map<String, List<String>>) showMoreSmallTypeMap
				.get("differenceListMap");
		if (sysDictionarySmallTypeTemp != null) {
			if (differenceListMap.containsKey(sysDictionarySmallTypeTemp.getValuekey())) {
				// 检测白膜是不是tijx
				if (baimoBaseProductInit != null) {
					specialProductTypeMap = checkBaimoIsTijx(productTypeValue, baimoBaseProductInit,
							specialProductTypeMap, sysDictionarySmallTypeTemp, differenceListMap);
				}
			}
		}

		if (specialProductTypeMap == null) {
			specialProductTypeMap = this.getSpecialProductTypeMap(productTypeValue, smallTypeValueTemp);
		}
		if (specialProductTypeMap.containsKey("notIn")) {
			prepProductSearchInfo.setExcludeSmallTypeValue((List<Integer>) specialProductTypeMap.get("notIn"));
		}
		if (specialProductTypeMap.containsKey("in")) {
			handleSmallType(prepProductSearchInfo, baimoBaseProductInit, sysDictionarySmallType, smallTypeValueTemp,
					differenceListMap);

		}
		return sysDictionarySmallType;
	}

	// 查找"无品牌"品牌数据id -
	private void searchNoBrandData(PrepProductSearchInfo prepProductSearchInfo) {
		BaseBrand baseBrand = baseBrandService.findOneByBrandReferred("wu");
		Integer brandIdWuPinPai = 0;
		if (baseBrand != null) {
			brandIdWuPinPai = baseBrand.getId();
		}
		prepProductSearchInfo.setBrandIdWuPinPai(brandIdWuPinPai);
	}

	// 设置授权码查询条件 -
	private void setAuthorizedQueryCondition(LoginUser loginUser, PrepProductSearchInfo prepProductSearchInfo,
			ProCategory proCategory, Integer userType) {
		if (userType == 3) {
			// 识别需不需要设置授权码查询条件,比如东鹏,点击的是dim,则需要设置授权码过滤条件,其他分类则不需要授权码过滤条件
			if (proCategory != null) {
				// 识别点击的分类的大类
				List<String> codeList = Utils.getListFromStr(proCategory.getLongCode(), ".");
				if (codeList != null && codeList.size() >= 3) {
					// dim
					String checkCategory = codeList.get(2);
					// 识别授权码对应公司的大类
					List<BaseProduct> baseProductList = baseProductService
							.getSelectConditionByAuthorizedConfigV3(loginUser, checkCategory);
					prepProductSearchInfo.setBaseProductList(baseProductList);
				}
			}
			// 检测用户绑定的授权码是否有设计公司的授权码,如果有,则去掉授权码查询条件 ->start
			int count = authorizedConfigService.findCountWhereCompanyTypeIsDesignByUserId(loginUser.getId());
			if (count > 0) {
				// 有设计公司的授权码,则不需要授权码过滤条件
				prepProductSearchInfo.setBaseProductList(null);
			}
			// 检测用户绑定的授权码是否有设计公司的授权码,如果有,则去掉授权码查询条件 ->end

		}
	}

	// 检测白膜是不是tijx
	private Map<String, Object> checkBaimoIsTijx(Integer productTypeValue, BaseProduct baimoBaseProductInit,
			Map<String, Object> specialProductTypeMap, SysDictionary sysDictionarySmallTypeTemp,
			Map<String, List<String>> differenceListMap) {
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue("productType",
				Integer.valueOf(baimoBaseProductInit.getProductTypeValue()),
				baimoBaseProductInit.getProductSmallTypeValue());
		if (sysDictionary != null) {
			String smallTypeValueKey2 = sysDictionary.getValuekey().trim().replace("basic_", "");
			if (differenceListMap.get(sysDictionarySmallTypeTemp.getValuekey()).indexOf(smallTypeValueKey2) != -1) {
				SysDictionary sysDictionary2 = sysDictionaryService.findOneByTypeAndValueKey(sysDictionary.getType(),
						smallTypeValueKey2);
				specialProductTypeMap = this.getSpecialProductTypeMap(productTypeValue, sysDictionary2.getValue());
			}
		}
		return specialProductTypeMap;
	}

	// 分类code,大类,小类
	private ProCategory categoryInfo(PrepProductSearchInfo prepProductSearchInfo, String categoryCode) {
		ProCategory proCategory;
		proCategory = proCategoryService.findOneByCode(categoryCode);
		if (proCategory != null) {
			Integer level = proCategory.getLevel();
			if (level != null && 0 == level) {
				prepProductSearchInfo.setFirstStageCode(categoryCode);
			} else if (level != null && 1 == level) {
				prepProductSearchInfo.setSecondStageCode(categoryCode);
			} else if (level != null && 2 == level) {
				prepProductSearchInfo.setThirdStageCode(categoryCode);
			} else if (level != null && 3 == level) {
				prepProductSearchInfo.setFourthStageCode(categoryCode);
			} else if (level != null && 4 == level) {
				prepProductSearchInfo.setFifthStageCode(categoryCode);
			} else {
				prepProductSearchInfo.setCategoryLongCodeLike("." + categoryCode + ".");
			}
		}
		return proCategory;
	}

	/****
	 * 检测白膜是什么小类
	 * 
	 * @param prepProductSearchInfo
	 * @param baimoBaseProductInit
	 * @param sysDictionarySmallType
	 * @param differenceListMap
	 * @param smallTypeValueList
	 * @param smallTypeValueKey
	 * @param showMoreSmallTypeInfoMap
	 */
	private void checkBaimoType(PrepProductSearchInfo prepProductSearchInfo, BaseProduct baimoBaseProductInit,
			SysDictionary sysDictionarySmallType, Map<String, List<String>> differenceListMap,
			List<Integer> smallTypeValueList, String smallTypeValueKey,
			Map<String, List<String>> showMoreSmallTypeInfoMap) {
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue("productType",
				Integer.valueOf(baimoBaseProductInit.getProductTypeValue()),
				baimoBaseProductInit.getProductSmallTypeValue());
		if (sysDictionary != null) {
			String smallTypeValueKey2 = sysDictionary.getValuekey().trim().replace("basic_", "");
			if (differenceListMap.get(smallTypeValueKey).indexOf(smallTypeValueKey2) != -1) {
				// 按白膜查询
				List<String> smallTypeValueKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey2);
				List<Integer> smallTypeValueList2 = sysDictionaryService
						.getValueByTypeAndValueKeylist(sysDictionarySmallType.getType(), smallTypeValueKeyList);
				smallTypeValueList.removeAll(smallTypeValueList2);
				smallTypeValueList.addAll(smallTypeValueList2);
				// 设置排序
				List<Integer> sortSmallTypeValueList = prepProductSearchInfo.getSortSmallTypeValueList();
				// 找到basic_yangtm对应的yangtm的数据字典
				SysDictionary sysDictionary2 = sysDictionaryService.findOneByTypeAndValueKey(sysDictionary.getType(),
						smallTypeValueKey2);
				if (sysDictionary2 != null) {
					if (sortSmallTypeValueList != null) {
						sortSmallTypeValueList.remove(sysDictionary2.getValue());
						sortSmallTypeValueList.add(sysDictionary2.getValue());
					} else {
						sortSmallTypeValueList = new ArrayList<Integer>();
						sortSmallTypeValueList.add(sysDictionary2.getValue());
					}
				}
				prepProductSearchInfo.setSortSmallTypeValueList(sortSmallTypeValueList);
			}
		}
	}

	/**
	 * 符合显示多个小类的配置
	 * 
	 * @param prepProductSearchInfo
	 * @param sysDictionarySmallType
	 * @param smallTypeValueTemp
	 * @param smallTypeValueList
	 * @param smallTypeValueKey
	 * @param showMoreSmallTypeInfoMap
	 */
	private void showMoreSmallType(PrepProductSearchInfo prepProductSearchInfo, SysDictionary sysDictionarySmallType,
			Integer smallTypeValueTemp, List<Integer> smallTypeValueList, String smallTypeValueKey,
			Map<String, List<String>> showMoreSmallTypeInfoMap) {
		// 符合显示多个小类的配置
		List<String> smallTypeValueKeyList = showMoreSmallTypeInfoMap.get(smallTypeValueKey);
		List<Integer> smallTypeValueList2 = sysDictionaryService
				.getValueByTypeAndValueKeylist(sysDictionarySmallType.getType(), smallTypeValueKeyList);
		smallTypeValueList.removeAll(smallTypeValueList2);
		smallTypeValueList.addAll(smallTypeValueList2);
		// 设置排序
		List<Integer> sortSmallTypeValueList = prepProductSearchInfo.getSortSmallTypeValueList();
		if (sortSmallTypeValueList != null) {
			sortSmallTypeValueList.remove(smallTypeValueTemp);
			sortSmallTypeValueList.add(smallTypeValueTemp);
		} else {
			sortSmallTypeValueList = new ArrayList<Integer>();
			sortSmallTypeValueList.add(smallTypeValueTemp);
		}
		prepProductSearchInfo.setSortSmallTypeValueList(sortSmallTypeValueList);
	}

	private Map<Object, Object> getKeyMap(PrepProductSearchInfo prepProductSearchInfo, String categoryCode) {
		Map<Object, Object> map = new HashMap<>();
		if (prepProductSearchInfo.getProductStatus() != null)
			map.put("ProductStatus", prepProductSearchInfo.getProductStatus());

		if (prepProductSearchInfo.getProductModelOrBrandName() != null)
			map.put("ProductModelOrBrandName", prepProductSearchInfo.getProductModelOrBrandName());

		if (prepProductSearchInfo.getHouseTypeList() != null)
			map.put("HouseTypeList", prepProductSearchInfo.getHouseTypeList());

		if (prepProductSearchInfo.getBigTypeValue() != null)
			map.put("BigTypeValue", prepProductSearchInfo.getBigTypeValue());

		if (categoryCode != null)
			map.put("CategoryCode", categoryCode);

		if (prepProductSearchInfo.getSpaceCommonId() != null)
			map.put("SpaceCommonId", prepProductSearchInfo.getSpaceCommonId());

		if (prepProductSearchInfo.getDesignTempletId() != null)
			map.put("DesignTempletId", prepProductSearchInfo.getDesignTempletId());

		if (prepProductSearchInfo.getBaimoId() != null)
			map.put("BaimoId", prepProductSearchInfo.getBaimoId());

		if (prepProductSearchInfo.getBaimoProductId() != null)
			map.put("BaimoProductId", prepProductSearchInfo.getBaimoProductId());

		map.put("HasOrderProps", prepProductSearchInfo.isHasOrderProps());

		if (prepProductSearchInfo.getProductId() != null)
			map.put("ProductId", prepProductSearchInfo.getProductId());

		if (prepProductSearchInfo.getExcludeSmallTypeValue() != null)
			map.put("ExcludeSmallTypeValue", prepProductSearchInfo.getExcludeSmallTypeValue());

		if (prepProductSearchInfo.getSmallTypeValue() != null)
			map.put("SmallTypeValue", prepProductSearchInfo.getSmallTypeValue());

		if (prepProductSearchInfo.getProductLength() != null)
			map.put("ProductLength", prepProductSearchInfo.getProductLength());

		if (prepProductSearchInfo.getProductHeight() != null)
			map.put("ProductHeight", prepProductSearchInfo.getProductHeight());

		if (prepProductSearchInfo.getLessProductLength() != null)
			map.put("LessProductLength", prepProductSearchInfo.getLessProductLength());

		if (prepProductSearchInfo.getLessProductWidth() != null)
			map.put("LessProductWidth", prepProductSearchInfo.getLessProductWidth());

		if (prepProductSearchInfo.getBaseProductList() != null)
			map.put("BaseProductList", prepProductSearchInfo.getBaseProductList());

		if (prepProductSearchInfo.getExcludeSmallTypeValue() != null)
			map.put("ExcludeSmallTypeValue", prepProductSearchInfo.getExcludeSmallTypeValue());

		if (prepProductSearchInfo.getBaimoIdList() != null)
			map.put("BaimoIdList", prepProductSearchInfo.getBaimoIdList());

		map.put("Beijing", prepProductSearchInfo.isBeijing());

		if (prepProductSearchInfo.getBeijingHeight() != null)
			map.put("BeijingHeight", prepProductSearchInfo.getBeijingHeight());

		if (prepProductSearchInfo.getBeijingLengthStart() != null)
			map.put("beijingLengthStart", prepProductSearchInfo.getBeijingLengthStart());

		if (prepProductSearchInfo.getBeijingLengthEnd() != null)
			map.put("beijingLengthStart", prepProductSearchInfo.getBeijingLengthEnd());

		map.put("Tianh", prepProductSearchInfo.isTianh());

		if (prepProductSearchInfo.getStart() != null)
			map.put("Start", prepProductSearchInfo.getStart());

		if (prepProductSearchInfo.getLimit() != null)
			map.put("Limit", prepProductSearchInfo.getLimit());
		return map;
	}

	/**
	 * 得到空间类型搜索条件
	 * 
	 * @author huangsongbo
	 * @param houseType
	 * @param baimoBaseProductInit
	 * @return
	 */
	private List<String> getHouseTypeSearchCondition(String houseType, BaseProduct baimoBaseProductInit) {
		if (StringUtils.isNotBlank(houseType)) {
			// 找到houseType对应的productHouseType
			SysDictionarySearch sysDictionarySearch = new SysDictionarySearch();
			sysDictionarySearch.setAtt1(houseType);
			sysDictionarySearch.setType("productHouseType");
			List<SysDictionary> sysDictionaryList = sysDictionaryService.getPaginatedList(sysDictionarySearch);
			List<String> productHouseTypeList = new ArrayList<String>();
			List<String> productHouseTypeList3 = new ArrayList<String>();
			if (sysDictionaryList != null && sysDictionaryList.size() > 0) {
				for (SysDictionary sysDictionary : sysDictionaryList) {
					productHouseTypeList.add("" + sysDictionary.getValue());
					productHouseTypeList3.add("" + sysDictionary.getValue());
				}
			}
			if (baimoBaseProductInit != null) {
				// 和对应初始化白膜的houseTypeValues取交集
				// eg:进入客餐厅,如果点击的灯的初始化白膜是客厅,则只能搜出客厅的等
				String productHouseTypesStr = baimoBaseProductInit.getHouseTypeValues();
				if (StringUtils.isNotBlank(productHouseTypesStr)) {
					List<String> productHouseTypeList2 = Utils.getListFromStr(productHouseTypesStr);
					productHouseTypeList.retainAll(productHouseTypeList2);
				}
			}
			if (productHouseTypeList.size() > 0) {
				return productHouseTypeList;
			} else {
				return productHouseTypeList3;
			}
		}
		return null;
	}

	private Map<String, Object> getSpecialProductTypeMap(Integer bigTypeValue, Integer smallTypeValue) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String specialProductTypeStr = Utils.getValue("special.productType", "");
		if (StringUtils.isNoneBlank(specialProductTypeStr)) {
			JSONArray jsonArray = JSONArray.fromObject(specialProductTypeStr);
			@SuppressWarnings({ "deprecation", "unchecked" })
			List<SpecialProductTypeInfo> specialProductTypeInfoList = JSONArray.toList(jsonArray,
					SpecialProductTypeInfo.class);
			// 取出所有大类查询其对应的value
			List<String> bigTypeList = new ArrayList<String>();
			for (SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList) {
				bigTypeList.add(specialProductTypeInfo.getProductTypeKey());
			}
			// 转换成bigType:value的map
			Map<Integer, String> bigTypeMap = sysDictionaryService.getValueValueKeyMapByTypeAndValueKey("productType",
					bigTypeList);
			if (bigTypeMap.containsKey(bigTypeValue)) {
				// 找到对应大类配置
				String bigType = bigTypeMap.get(bigTypeValue);
				// TODO 循环 多个list
				for (SpecialProductTypeInfo specialProductTypeInfo : specialProductTypeInfoList) {
					if (StringUtils.equals(bigType, specialProductTypeInfo.getProductTypeKey())) {
						String smallTypeStr = specialProductTypeInfo.getProductSmallTypeKeys();
						List<String> smallTypeList = Utils.getListFromStr(smallTypeStr);
						List<Integer> smallTypeValueList = sysDictionaryService.getValueByTypeAndValueKeylist(bigType,
								smallTypeList);
						if (smallTypeValueList.indexOf(smallTypeValue) == -1) {
							// 确定为不包含这些小类
							returnMap.put("notIn", smallTypeValueList);
						} else {
							// 确定为只要改小类
							returnMap.put("in", smallTypeValue);
						}
						break;
					}
				}
			} else {
				// 在配置范围外,不做处理
			}
		}
		return returnMap;
	}

	// 识别是否要显示背景墙
	private void setBgWallCondition(PrepProductSearchInfo prepProductSearchInfo, DesignPlanProduct designPlanProduct) {
		if (designPlanProduct == null)
			return;
		String BindParentProductIdsStr = designPlanProduct.getBindParentProductId();
		List<Integer> bindParentProductIdList = Utils.getIntegerListFromStringList(BindParentProductIdsStr);
		if (bindParentProductIdList == null || bindParentProductIdList.size() == 0)
			return;
		// 检测绑定点上的产品是否还在
		List<Integer> idList = new ArrayList<Integer>();

		DesignPlanProduct designPlan = new DesignPlanProduct();
		designPlan.setPlanId(designPlanProduct.getPlanId());
		designPlan.setResIdList(bindParentProductIdList);
		List<DesignPlanProduct> resDesList = designPlanProductService.getList(designPlanProduct);
		int t = bindParentProductIdList.size() - resDesList.size();
		if (t > 0) {
			for (int i = 0; i < bindParentProductIdList.size(); i++) {
				boolean b = false;
				for (int j = 0; j < resDesList.size(); j++) {
					if (bindParentProductIdList.get(i) == resDesList.get(j).getId()) {
						b = true;
						break;
					}
				}
				if (!b) {
					idList.add(bindParentProductIdList.get(i));

				}
			}
		}

		/*
		 * //TODO 循环 for (Integer id : bindParentProductIdList) {
		 * DesignPlanProduct designPlanProductBind = designPlanProductService.findIdByInitProductIdAndPlanId(id,
		 * designPlanProduct.getPlanId()); 
		 * if (designPlanProductBind == null)
		 * idList.add(id); 
		 * } 
		 * if (idList.size() == 0) 
		 * return;
		 */
		List<Integer> smallTypeValueList = new ArrayList<Integer>();

		BaseProduct baseProductBaimo1 = new BaseProduct();
		baseProductBaimo1.setResIdList(idList);
		List<BaseProduct> reslutList = baseProductService.getDicList(baseProductBaimo1);
		for (int i = 0; i < reslutList.size(); i++) {
			smallTypeValueList.add(reslutList.get(i).getDicSmallTypeValue());
		}
		/*
		 * //TODO 循环
		 *  for (Integer productId : idList) { 
		 *  BaseProduct baseProductBaimo = baseProductService.get(productId); 
		 *  if (baseProductBaimo == null) continue; 
		 *  Integer smallTypeValue = sysDictionaryService.getProductValueByBaimoValue( Integer.valueOf(baseProductBaimo.getProductTypeValue()),
		 * baseProductBaimo.getProductSmallTypeValue());
		 * smallTypeValueList.add(smallTypeValue); }
		 */
		List<Integer> excludeSmallTypeValueList = prepProductSearchInfo.getExcludeSmallTypeValue();
		if (excludeSmallTypeValueList != null && excludeSmallTypeValueList.size() > 0) {
			excludeSmallTypeValueList.removeAll(smallTypeValueList);
			// 设置排序(比如smallTypeValueList = 1 则小类 =1 的排前面)
			prepProductSearchInfo.setSortSmallTypeValueList(smallTypeValueList);
			prepProductSearchInfo.setExcludeSmallTypeValue(excludeSmallTypeValueList);
		}
		List<Integer> baimoIdList = new ArrayList<>();
		if (prepProductSearchInfo.getBaimoId() != null)
			baimoIdList.add(prepProductSearchInfo.getBaimoId());
		baimoIdList.addAll(idList);
		prepProductSearchInfo.setBaimoIdList(baimoIdList);
	}

	private Map<String, AutoCarryBaimoProducts> getAutoCarryMap() {
		Map<String, AutoCarryBaimoProducts> autoCarryMap = new HashMap<>();
		String autoCarryBaimoProducrsConfig = Utils.getPropertyName("app", "design.designPlan.autoCarryBaimoProducts", "");
		if (StringUtils.isNotBlank(autoCarryBaimoProducrsConfig)) {
			JSONArray autoCarryBaimoProducrsObject = JSONArray.fromObject(autoCarryBaimoProducrsConfig);
			try {
				@SuppressWarnings("unchecked")
				List<AutoCarryBaimoProducts> autoCarryBaimoProductsConfigs = (List<AutoCarryBaimoProducts>) JSONArray
						.toCollection(autoCarryBaimoProducrsObject, AutoCarryBaimoProducts.class);
				if (autoCarryBaimoProductsConfigs != null && autoCarryBaimoProductsConfigs.size() > 0) {
					for (AutoCarryBaimoProducts autoCarryBaimoProductsConfig : autoCarryBaimoProductsConfigs) {
						autoCarryMap.put(autoCarryBaimoProductsConfig.getSmallTypeCode(), autoCarryBaimoProductsConfig);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(GAIN_BAIMO_PRODUCT_EXCEPTION);
			}
		}
		return autoCarryMap;
	}

	/**
	 * 处理材质信息,填装材质信息(默认材质)
	 * 
	 * @param categoryProductResult
	 */
	@SuppressWarnings("unchecked")
	private void dealWithTextureInfo(CategoryProductResult categoryProductResult) {
		List<SplitTextureDTO> splitTextureDTOList = new ArrayList<>();
		Integer isSplit = 0;
		if (StringUtils.isNotBlank(categoryProductResult.getSplitTexturesInfoStr())) {
			// 多材质产品
			Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(categoryProductResult.getProductId(),
					categoryProductResult.getSplitTexturesInfoStr(), "choose");
			isSplit = (Integer) map.get("isSplit");
			splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
		} else {
			if (StringUtils.isNotBlank(categoryProductResult.getMaterialPicId())) {
				// TODO: 材质球文件路径
				ResFile textureBallFile = null;
				String materialPath = "";
				ResPic normalPic = null;
				String normalParam = "";
				String normalPath = "";
				ResTexture resTexture = resTextureService.get(Integer.valueOf(categoryProductResult.getMaterialPicId()));
				if (resTexture != null && resTexture.getTextureBallFileId() != null) {
					textureBallFile = resFileService.get(resTexture.getTextureBallFileId());
					if (textureBallFile != null) {
						materialPath = textureBallFile.getFilePath();
						/*materialPath = Utils.dealWithPath(materialPath, "linux");*/
					}
				}
				if (resTexture != null && resTexture.getNormalPicId() != null) {
					resTexture.getNormalParam();
					normalPic = resPicService.get(resTexture.getNormalPicId());
					if (normalPic != null) {
						normalPath = /*Utils.getValue("app.resources.url", "") + */normalPic.getPicPath();
						/*normalPath = Utils.dealWithPath(normalPath, "linux");*/
					}
				}
				// 单材质产品
				SplitTextureDTO splitTextureDTO = new SplitTextureDTO("1", "", null);
				SplitTextureDTO.ResTextureDTO resTextureDTO = splitTextureDTO.new ResTextureDTO(
						Integer.valueOf(categoryProductResult.getMaterialPicId()),
						categoryProductResult.getResTexturePath(), categoryProductResult.getTextureAttrValue(),
						StringUtils.isNotBlank(categoryProductResult.getTextureHeight())
								? (int) Double.parseDouble(categoryProductResult.getTextureHeight()) : null,
						StringUtils.isNotBlank(categoryProductResult.getTextureWidth())
								? (int) Double.parseDouble(categoryProductResult.getTextureWidth()) : null,
						categoryProductResult.getLaymodes(), materialPath, normalParam, normalPath);
				resTextureDTO.setKey("1");
				resTextureDTO.setProductId(categoryProductResult.getProductId());
				List<ResTextureDTO> resTextureDTOList = new ArrayList<ResTextureDTO>();
				resTextureDTOList.add(resTextureDTO);
				splitTextureDTO.setList(resTextureDTOList);
				splitTextureDTOList.add(splitTextureDTO);
			}
		}
		categoryProductResult.setIsSplit(isSplit);
		categoryProductResult.setSplitTexturesChoose(splitTextureDTOList);
	}

	/**
	 * 得到单品搜索相关的配置(同时显示多个小类)
	 * 
	 * @author huangsongbo
	 * @return
	 */
	public static Map<String, Object> getshowMoreSmallTypeMap() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String showMoreSmallType = Utils.getValue("product.searchProduct.showMoreSmallType",
				"[{\"checkType\":\"yaox\",\"showType\":\"yaox,qiangz\"},{\"checkType\":\"bodx\",\"showType\":\"bodx,diz\"},{\"checkType\":\"tijx\",\"showType\":\"tijx,diz\"},{\"checkType\":\"dians,shaf,cant\",\"showType\":\"dians,shaf,cant\"}]");
		if (StringUtils.isBlank(showMoreSmallType)) {
			return null;
		}
		JSONArray showMoreSmallTypeJson = JSONArray.fromObject(showMoreSmallType);
		// product.searchProduct.showMoreSmallType=[{"checkType":"chufm,yangtm","showType":"chufm,yangtm,weisjm"}]
		Map<String, List<String>> showMoreSmallTypeInfoMap = new HashMap<String, List<String>>();
		// chufm,yangtm
		List<String> keyList = new ArrayList<String>();
		// chufm,yangtm
		List<String> valueList = new ArrayList<String>();
		if (showMoreSmallTypeJson != null && showMoreSmallTypeJson.size() > 0) {
			for (int i = 0; i < showMoreSmallTypeJson.size(); i++) {
				JSONObject jsonObject = showMoreSmallTypeJson.getJSONObject(i);
				String checkType = jsonObject.getString("checkType");
				if (StringUtils.isBlank(checkType)) {
					continue;
				}
				String showType = jsonObject.getString("showType");
				List<String> checkTypeList = Utils.getListFromStr(checkType);
				List<String> showTypeList = Utils.getListFromStr(showType);
				keyList.removeAll(checkTypeList);
				keyList.addAll(checkTypeList);
				valueList.removeAll(showTypeList);
				valueList.addAll(showTypeList);
				for (String checkTypeItem : checkTypeList) {
					if (showMoreSmallTypeInfoMap.containsKey(checkTypeItem)) {
						// 更新
						List<String> showTypeListUpdate = new ArrayList<String>();
						showTypeListUpdate.addAll(showMoreSmallTypeInfoMap.get(checkTypeItem));
						showTypeListUpdate.removeAll(showTypeList);
						showTypeListUpdate.addAll(showTypeList);
						showMoreSmallTypeInfoMap.put(checkTypeItem, showTypeListUpdate);
					} else {
						// 添加新元素
						showMoreSmallTypeInfoMap.put(checkTypeItem, showTypeList);
					}
				}
			}
		}

		// 记录diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线) ->start
		// diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线)
		// valueList和keyList差集(决定搜索是否看白膜,比如腰线能搜出地砖,地砖不能搜出腰线,则,地砖产品搜索看白膜)
		List<String> differenceList = new ArrayList<String>();
		Map<String, List<String>> differenceListMap = new HashMap<String, List<String>>();
		for (String key : showMoreSmallTypeInfoMap.keySet()) {
			for (String str : showMoreSmallTypeInfoMap.get(key)) {
				if (showMoreSmallTypeInfoMap.containsKey(str)) {
					// diz有配置,看看diz能不能搜出tijx
					if (showMoreSmallTypeInfoMap.get(str).indexOf(key) != -1) {
						// diz能搜出tijx
					} else {
						// diz不能搜出tijx
						if (differenceListMap.containsKey(str)) {
							differenceListMap.get(str).remove(key);
							differenceListMap.get(str).add(key);
						} else {
							List<String> list = new ArrayList<String>();
							list.add(key);
							differenceListMap.put(str, list);
						}
					}
				} else {
					// 没有diz的配置
					if (differenceListMap.containsKey(str)) {
						differenceListMap.get(str).remove(key);
						differenceListMap.get(str).add(key);
					} else {
						List<String> list = new ArrayList<String>();
						list.add(key);
						differenceListMap.put(str, list);
					}
				}
			}
		}
		// 记录diz:tjx(地砖能被踢脚线搜出,用于后面查看地砖能不能搜出踢脚线) ->start
		returnMap.put("differenceListMap", differenceListMap);
		returnMap.put("differenceList", differenceList);
		returnMap.put("showMoreSmallTypeInfoMap", showMoreSmallTypeInfoMap);
		returnMap.put("keyList", keyList);
		returnMap.put("valueList", valueList);
		return returnMap;
	}

	public List<CategoryProductResult> searchProduct(ProductCategoryRel productCategoryRel,
			ProductCategoryVO productCategoryVO, LoginUser loginUser, String mediaType) {
		logger.info("分类查找产品列表。。。");
		if (StringUtils.isNotBlank(productCategoryVO.getHouseType())) {
			productCategoryRel.setHouseTypeValues(productCategoryVO.getHouseType());
		}
		int total = 0;
		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		// 查询方式
		if (StringUtils.isNotBlank(productCategoryRel.getCategoryCode())
				&& "group".equals(productCategoryVO.getQueryType())) {
			String[] arr = productCategoryRel.getCategoryCode().split(",");
			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
		}
		productCategoryRel.setUserId(loginUser.getId());
		productCategoryRel.setSpaceCommonId(productCategoryVO.getSpaceCommonId());
		if (StringUtils.isNotBlank(productCategoryVO.getTemplateProductId())) {
			String arrayProId[] = productCategoryVO.getTemplateProductId().split(",");
			productCategoryRel.setTemplateProductId(Arrays.asList(arrayProId));
		}
		// 是否为空房模式
		boolean emptyModel = isEmptyModel(productCategoryRel, productCategoryVO);
		// 如果是厂商，则只能查询这个厂商品牌下的产品
		if (3 == loginUser.getUserType()) {
			if (StringUtils.isNotBlank(loginUser.getBrandIds())) {
				String[] brandIds = loginUser.getBrandIds().split(",");
				productCategoryRel.setBrandIds(Arrays.asList(brandIds));
			}
		}
		// 是否只显示推荐产品(显示推荐+同类型数据，只显示推荐数据；不排除推荐数据，排除推荐数据；空房模式，无推荐，显示推荐+同类型数据+排除推荐数据)
		String onlyShowRecommend = Utils.getValue("onlyShowRecommend", "false");
		String exceptRecommend = Utils.getValue("exceptRecommend", "false");
		// 硬装产品判断，过滤
		getProductCategoryRel(productCategoryRel, productCategoryVO, emptyModel, onlyShowRecommend, exceptRecommend);
		// 序列号配置过滤
		Map<String, List<String>> map = authorizedConfigService.getConfigParams(loginUser.getId());
		productCategoryRel.setConfigBrandIdList(map.get("brandIdList"));
		productCategoryRel.setConfigProductIdList(map.get("productIdList"));
		productCategoryRel.setConfigSmallTypeIdList(map.get("smallTypeIdList"));
		productCategoryRel.setConfigTypeValueList(map.get("typeValueList"));
		// 序列号配置过滤END
		if ("group".equals(productCategoryVO.getQueryType())) {
			total = productCategoryRelService.findProductByCategoryCodeCount(productCategoryRel);// findProductByCategoryCode
			if (total > 0) {
				list = productCategoryRelService.findProductByCategoryCode(productCategoryRel);
			}
		} else {
			total = productCategoryRelService.findCategoryProductResultByLongCodeCount(productCategoryRel);
			if (total > 0) {
				list = productCategoryRelService.findCategoryProductResultByLongCode(productCategoryRel);
			}
		}

		// TODO 循环
		for (CategoryProductResult productResult : list) {
			BaseProduct baseProduct = baseProductService.get(productResult.getProductId());
			String modelId = baseProductService.getU3dModelId(productCategoryVO.getMediaType(), baseProduct);
			if (StringUtils.isNotEmpty(modelId)) {
				ResModel resModel = resModelService.get(Integer.valueOf(modelId));
				if (resModel != null) {
					productResult.setProductModelPath(resModel.getModelPath());
				}
			}
			// 产品类型
			productType(productResult, baseProduct);
			// 空间ID
			productResult.setSpaceCommonId(baseProduct.getSpaceComonId());
			// 产品材质路径
			productMaterialPath(productResult, baseProduct);
			// 材质配置文件路径
			Integer materialConfigId = baseProduct.getMaterialFileId();
			if (materialConfigId != null) {
				ResFile resFile = resFileService.get(materialConfigId);
				if (resFile != null) {
					productResult.setMaterialConfigPath(resFile.getFilePath());
				}
			}
			productResult.setParentProductId(baseProduct.getParentId());
		}
		return list;

	}

	/**
	 * 硬装产品判断，过滤
	 * 
	 * @param productCategoryRel
	 * @param productCategoryVO
	 * @param emptyModel
	 * @param onlyShowRecommend
	 * @param exceptRecommend
	 */
	private void getProductCategoryRel(ProductCategoryRel productCategoryRel, ProductCategoryVO productCategoryVO,
			boolean emptyModel, String onlyShowRecommend, String exceptRecommend) {
		if (StringUtils.isNotEmpty(productCategoryVO.getProductTypeValue())
				&& StringUtils.isNotEmpty(productCategoryVO.getSmallTypeValue())
				&& new Integer(productCategoryVO.getProductTypeValue()).intValue() > 0
				&& new Integer(productCategoryVO.getSmallTypeValue()).intValue() > 0) {
			// 是否为硬装产品
			SysDictionary sysDictionary = new SysDictionary();
			sysDictionary.setValue(Integer.valueOf(productCategoryVO.getProductTypeValue()));
			sysDictionary.setSmallValue(Integer.valueOf(productCategoryVO.getSmallTypeValue()));
			sysDictionary = sysDictionaryService.checkType(sysDictionary);
			// att4等于1表示为硬装产品
			if ("1".equals(sysDictionary.getAtt4()) || emptyModel) {
				onlyShowRecommend = "false";
			}
			// 只显示推荐产品时，不需要大小类过滤
			if ("true".equals(onlyShowRecommend)) {
				productCategoryRel.setOnlyShowRecommend(true);
			} else {
				productCategoryRel.setOnlyShowRecommend(false);
				productCategoryRel.setProductTypeValue(productCategoryVO.getProductTypeValue());
				productCategoryRel.setProductSmallTypeValue(StringUtils.isEmpty(productCategoryVO.getSmallTypeValue())
						? -1 : new Integer(productCategoryVO.getSmallTypeValue()).intValue());
			}
			// 排除推荐数据时
			if ("true".equals(exceptRecommend)) {
				productCategoryRel.setExceptRecommend(true);
			} else {
				productCategoryRel.setExceptRecommend(false);
			}
		} else {
			productCategoryRel.setOnlyShowRecommend(false);
			productCategoryRel.setExceptRecommend(true);
			productCategoryRel.setProductTypeValue(null);
			productCategoryRel.setProductSmallTypeValue(null);
		}
	}

	/**
	 * 产品类型
	 * 
	 * @param productResult
	 * @param baseProduct
	 */
	private void productType(CategoryProductResult productResult, BaseProduct baseProduct) {
		String productTypeValue2 = baseProduct.getProductTypeValue();
		if (StringUtils.isNotBlank(productTypeValue2)) {
			SysDictionary productTypeSysDic = sysDictionaryService.getSysDictionaryByValue("productType",
					Integer.valueOf(productTypeValue2));
			productResult.setProductTypeValue(productTypeSysDic.getValue());
			productResult.setProductTypeCode(productTypeSysDic.getValuekey());
			productResult.setProductTypeName(productTypeSysDic.getName());
			Integer productSmallTypeValue2 = baseProduct.getProductSmallTypeValue();
			if (productTypeSysDic.getValue() != null && productSmallTypeValue2 != null) {
				SysDictionary productSmallTypeSysDic = sysDictionaryService
						.getSysDictionaryByValue(productTypeSysDic.getValuekey(), productSmallTypeValue2);
				productResult.setProductSmallTypeCode(productSmallTypeSysDic.getValuekey());
				productResult.setProductSmallTypeName(productSmallTypeSysDic.getName());
				productResult.setProductSmallTypeValue(productSmallTypeSysDic.getValue());

				String rootType = StringUtils.isEmpty(productSmallTypeSysDic.getAtt1()) ? "2"
						: productSmallTypeSysDic.getAtt1().trim();
				productResult.setRootType(rootType);
			}
		}
	}

	/**
	 * 是否为空房模式
	 * 
	 * @param productCategoryRel
	 * @param productCategoryVO
	 * @return
	 */
	private boolean isEmptyModel(ProductCategoryRel productCategoryRel, ProductCategoryVO productCategoryVO) {
		DesignPlan designPlan;
		boolean emptyModel = false;
		if (productCategoryVO.getDesignPlanId() != null && productCategoryVO.getDesignPlanId() != 0
				&& productCategoryVO.getDesignPlanId() != null && productCategoryVO.getDesignPlanId() != 0) {
			designPlan = designPlanService.get(productCategoryVO.getDesignPlanId());
			DesignPlanProduct designPlanProduct = designPlanProductService.get(productCategoryVO.getPlanProductId());
			if (designPlan != null && designPlanProduct != null) {
				productCategoryRel.setDesignTempletId(designPlan.getDesignTemplateId());
				productCategoryRel.setDesignProductId(designPlanProduct.getPlanProductId());
				productCategoryRel.setProductId(designPlanProduct.getProductId());

				DesignTemplet designTemplet = designTempletService.get(designPlan.getDesignTemplateId());
				if (designTemplet == null) {
					logger.info("designTemplet is null ... templeId=" + designPlan.getDesignTemplateId() + ",designId="
							+ designPlan.getId());
				} else {
					if (StringUtils.isEmpty(designTemplet.getDesignCode())) {
						logger.info("designTemplet.designCode is null ... templeId=" + designPlan.getDesignTemplateId()
								+ ",designId=" + designPlan.getId());
					}
				}
				if (!StringUtils.isEmpty(designTemplet.getDesignCode())
						&& designTemplet.getDesignCode().endsWith("_000")) {
					emptyModel = true;
				} else {
					emptyModel = false;
				}
				logger.info(HOUSE_TYPE + productCategoryVO.getHouseType() + ";" + "\n" + SPACE_KEY_SPACECOMMONID
						+ productCategoryVO.getSpaceCommonId() + ";" + "\n" + DESIGN_KEY_DESIGNPLANID
						+ productCategoryVO.getDesignPlanId() + ";" + "\n"
						+ SELECTED_DESIGNPLAN_KEY_DESIGNPLANPRODUCT_PRODUCTID + productCategoryVO.getPlanProductId()
						+ ";" + "\n" + SELECTED_TEMPLATE_DATAKEY_TEMPLATEPRODUCTID
						+ designPlanProduct.getPlanProductId() + ";" + "\n"
						+ SELECTED_TEMPLATE_PRODUCTKEY_TEMPLATEPRODUCT_PRODUCTID
						+ productCategoryVO.getTemplateProductId() + ";" + "\n" + SELECTED_PRODUCT_PRODUCT_TYPE_VALUE
						+ productCategoryVO.getProductTypeValue() + ";" + "\n" + SELECTED_PRODUCT_SMALL_TYPE_VALUE
						+ productCategoryVO.getSmallTypeValue() + ";" + "\n" + QUERY_TYPE
						+ productCategoryVO.getQueryType() + ";");
			}
		}
		return emptyModel;
	}

	/***
	 * 产品材质路径
	 * 
	 * @param productResult
	 * @param baseProduct
	 */
	private void productMaterialPath(CategoryProductResult productResult, BaseProduct baseProduct) {
		String materialIds = productResult.getMaterialPicId();
		if (StringUtils.isNotBlank(materialIds)) {
			String[] mIds = materialIds.split(",");
			if (mIds != null) {
				ResPic resPic = new ResPic();
				resPic.setFileKey("product.baseProduct.material");
				resPic.setBusinessId(baseProduct.getId());
				resPic.setOrders(" sequence asc ");
				List<ResPic> materialPics = resPicService.getList(resPic);
				if (materialPics != null && materialPics.size() > 0) {
					String[] materialPaths = new String[materialPics.size()];
					for (int i = 0; i < materialPics.size(); i++) {
						resPic = materialPics.get(i);
						if (resPic != null) {
							materialPaths[i] = resPic.getPicPath();
						}
					}
					productResult.setMaterialPicPaths(materialPaths);
				}
			}
		}
	}

	/**
	 * 获得分类查询
	 * 
	 * @param response
	 * @param msgId
	 * @return
	 */
	public Object getCategory(String msgId) {
		logger.info("获得分类查询。。。");
		ProCategory category = null;// categoryService.get(1);
		ProCategory pc = new ProCategory();
		pc.setOrder(" long_code ");
		List<ProCategory> categoryAllList = categoryService.getList(pc);
		if (categoryAllList != null && categoryAllList.size() >= 0) {
			for (ProCategory ppc : categoryAllList) {
				if (ppc.getId().intValue() == 1) {
					category = ppc;
					break;
				}
			}
		}
		List<SearchProCategorySmall> categoryList = recursionCategory2(category, categoryAllList);
		category.setChildrenNodes(categoryList);
		SearchProCategorySmall categorySmall = new SearchProCategorySmall();
		categorySmall.setAa(category.getId());
		categorySmall.setBb(category.getCode());
		categorySmall.setCc(category.getPid());
		categorySmall.setDd(category.getName());
		categorySmall.setFf(category.getChildrenNodes());
		String obj = null;
		obj = new JsonDataServiceImpl<SearchProCategorySmall>().getBeanToJsonData(categorySmall);
		return new ResponseEnvelope<SearchProCategorySmall>(categorySmall, msgId, true);
	}

	/**
	 * 递归查询分类
	 * 
	 * @param category
	 * @return
	 */
	public List<SearchProCategorySmall> recursionCategory2(ProCategory category, List<ProCategory> categoryAllList) {
		List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
		ProCategorySearch search = new ProCategorySearch();
		search.setPid(category.getId());
		search.setLongCode(category.getLongCode());
		List<ProCategory> list = new ArrayList<ProCategory>();
		if (categoryAllList != null && categoryAllList.size() > 0) {
			for (ProCategory pc : categoryAllList) {
				if (pc.getPid().intValue() == search.getPid().intValue()
						&& pc.getLongCode().indexOf(category.getLongCode()) != -1) {
					list.add(pc);
				}
			}
		} else {
			list = categoryService.getList(search);
		}
		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
		}
		SearchProCategorySmall newCategory = null;
		for (ProCategory childrenNode : list) {
			newCategory = new SearchProCategorySmall();
			newCategory.setAa(childrenNode.getId());
			newCategory.setCc(childrenNode.getPid());
			newCategory.setDd(childrenNode.getName());
			newCategory.setBb(childrenNode.getCode());
			newCategory.setFf(recursionCategory2(childrenNode, categoryAllList));
			childrenNodes.add(newCategory);
		}
		category.setChildrenNodes(childrenNodes);

		return childrenNodes;
	}

	/**
	 * 递归查询分类
	 * 
	 * @param category
	 * @return
	 */
	public List<SearchProCategorySmall> recursionCategory(ProCategory category) {
		List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
		ProCategorySearch search = new ProCategorySearch();
		search.setPid(category.getId());
		search.setLongCode(category.getLongCode());
		List<ProCategory> list = categoryService.getList(search);
		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
			SearchProCategorySmall newCategory = null;
			for (ProCategory childrenNode : list) {
				newCategory = new SearchProCategorySmall();
				newCategory.setAa(childrenNode.getId());
				newCategory.setCc(childrenNode.getPid());
				newCategory.setDd(childrenNode.getName());
				newCategory.setBb(childrenNode.getCode());
				newCategory.setFf(recursionCategory(childrenNode));
				childrenNodes.add(newCategory);
			}
			category.setChildrenNodes(childrenNodes);
		}
		return childrenNodes;
	}

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public SearchProCategorySmall getAllCategorySmall() {
		ProCategory category = categoryService.get(1);
		List<SearchProCategorySmall> categoryList = recursionCategory(category);
		category.setChildrenNodes(categoryList);
		SearchProCategorySmall allCategorySmall = new SearchProCategorySmall();
		allCategorySmall.setAa(category.getId());
		allCategorySmall.setBb(category.getCode());
		allCategorySmall.setCc(category.getPid());
		allCategorySmall.setDd(category.getName());
		allCategorySmall.setFf(category.getChildrenNodes());
		return allCategorySmall;
	}

	/**
	 * 查询分类1
	 * 
	 * @return
	 */
	public SearchProCategorySmall getCategorySmall1() {
		ProCategory category = categoryService.get(1);
		List<SearchProCategorySmall> categoryList = recursionCategory(category);
		category.setChildrenNodes(categoryList);
		SearchProCategorySmall categorySmall1 = new SearchProCategorySmall();
		categorySmall1.setAa(category.getId());
		categorySmall1.setBb(category.getCode());
		categorySmall1.setCc(category.getPid());
		categorySmall1.setDd(category.getName());
		categorySmall1.setFf(category.getChildrenNodes());
		return categorySmall1;
	}

	/**
	 * 查询分类
	 * 
	 * @return
	 */
	public SearchProCategorySmall getcategorySmall() {
		ProCategory category = null; // categoryService.get(1);
		ProCategory pc = new ProCategory();
		pc.setOrder(" long_code");
		List<ProCategory> categoryAllList = categoryService.getList(pc);
		if (categoryAllList != null && categoryAllList.size() >= 0) {
			for (ProCategory ppc : categoryAllList) {
				if (ppc.getId().intValue() == 1) {
					category = ppc;
					break;
				}
			}
		}
		List<SearchProCategorySmall> categoryList = recursionCategory2(category, categoryAllList);
		category.setChildrenNodes(categoryList);
		SearchProCategorySmall categorySmall = new SearchProCategorySmall();
		categorySmall.setAa(category.getId());
		categorySmall.setBb(category.getCode());
		categorySmall.setCc(category.getPid());
		categorySmall.setDd(category.getName());
		categorySmall.setFf(category.getChildrenNodes());
		return categorySmall;
	}

}
