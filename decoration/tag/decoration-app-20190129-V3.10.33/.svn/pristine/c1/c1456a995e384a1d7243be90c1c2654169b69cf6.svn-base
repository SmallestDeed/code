package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.home.model.SpaceCommon;
import com.nork.product.cache.BaseBrandCacher;
import com.nork.product.dao2.BaseBrandMapper;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.model.small.BaseBrandSmall;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service2.BaseBrandService;
import com.nork.system.cache.ResourceCacher;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

/**
 * @Title: BaseBrandServiceImpl.java
 * @Package com.nork.product.service2.impl
 * @Description:产品-品牌表ServiceImpl
 * @author yangzhun
 * @CreateDate 2017-6-13 17:02:26
 */
@Service("baseBrandService2")
@Transactional()
public class BaseBrandServiceImpl implements BaseBrandService {
	private final JsonDataServiceImpl<BaseBrand> JsonUtil = new JsonDataServiceImpl<BaseBrand>();

	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Autowired
	private BaseBrandMapper baseBrandMapper2;
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResPicService resPicService;

	@Override
	public ResponseEnvelope listAllByPara(String style, String brandName,
			String brandStyleId, String msgId, String limit, String start) {

		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<SpaceCommon>(false,
					SystemCommonConstant.MSGID_NOT_NULL, msgId);
		}

		BaseBrandSearch baseBrandSearch = new BaseBrandSearch();
		List<BaseBrand> list = new ArrayList<BaseBrand>();
		List<BaseBrand> newList = new ArrayList<BaseBrand>();

		// 设置查询条件
		if (StringUtils.isNotBlank(brandName)) {
			baseBrandSearch.setSch_BrandName_(brandName);
		}
		if (StringUtils.isNotBlank(brandStyleId)) {
			baseBrandSearch.setBrandStyleId(Integer.parseInt(brandStyleId));
		}
		if (StringUtils.isNotBlank(start)) {
			baseBrandSearch.setStart(Integer.parseInt(start));
		}
		if (StringUtils.isNotBlank(limit)) {
			baseBrandSearch.setLimit(Integer.parseInt(limit));
		}
		int total = 0;

		String cacheEnable = Utils.getValue(
				SystemCommonConstant.REDIS_CACHE_ENABLE,
				SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE);
		if (StringUtils.equals(SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE,
				cacheEnable)) {
			total = BaseBrandCacher.getBaseBrandTotal(baseBrandSearch);
		} else {
			total = baseBrandMapper2.selectCount(baseBrandSearch);
		}
		if (total > 0) {
			if (StringUtils.equals(
					SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE, cacheEnable)) {
				list = BaseBrandCacher.getPaginatedList(baseBrandSearch);
			} else {
				list = baseBrandMapper2.selectPaginatedList(baseBrandSearch);
			}

		}
		
//		//TODO:将for循环里的DB操作抽出来
//		//得到所有id
//		List<String> idList = null; 
//		for(int i = 0;i<list.size();i++){
//			String picId = list.get(i).getBrandLogo();
//			idList.add(picId);
//		}
//		//将String集合转为Integer集合
//		List<Integer> resPicIdList = Utils.StringToIntegerLst(idList);
//		//根据id集合查询所有
//		List<ResPic> resPicList = resPicService.getBatchGet(resPicIdList); 
//		for(int i=0;i<resPicList.size();i++){
//			ResPic resPic = null;
//			if(StringUtils.equals(
//					SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE,
//					cacheEnable)){
//				resPic = ResourceCacher.getPic(resPicList.get(i).getId());
//			}else{
//				resPic = resPicList.get(i);
//			}
//			if(Integer.parseInt(idList.get(i)) == resPicList.get(i).getId()){
//				resPic = resPicList.get(i);
//			}
//			if (resPic != null) {
//				resPicList.get(i).setBrandLogo(resPic.getPicPath());
//			}
//			
//		}
		
		
		
		if (list != null && list.size() > 0) {
			for (BaseBrand baseBrand : list) {
				String picId = baseBrand.getBrandLogo();
				if (StringUtils.isNumeric(picId)) {
					ResPic resPic = null;
					if (StringUtils.equals(
							SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE,
							cacheEnable)) {
						resPic = ResourceCacher.getPic(Integer.parseInt(picId));
					} else {
						resPic = resPicService.get(Integer.parseInt(picId));
					}
					if (resPic != null) {
						baseBrand.setBrandLogo(resPic.getPicPath());
					}

				}
				Integer count = 0;
				BaseProductSearch baseProductSearch = new BaseProductSearch();
				baseProductSearch.setIsDeleted(0);
				baseProductSearch.setBrandId(baseBrand.getId());
				count = baseProductService.getCount(baseProductSearch);
				if (count.intValue() > 0) {
					newList.add(baseBrand);
				}
			}
		}
		return new ResponseEnvelope<BaseBrand>(total, newList, msgId);
	}

	/**
	 * 查询所有品牌的方法
	 * 
	 * @param style
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	public ResponseEnvelope listAll(String style, String brandName,
			String msgId, String limit, String start) {
		if (StringUtils.isBlank(msgId)) {
			return new ResponseEnvelope<SpaceCommon>(false,
					SystemCommonConstant.MSGID_NOT_NULL, msgId);
		}
		BaseBrandSearch baseBrandSearch = new BaseBrandSearch();
		List<BaseBrand> list = new ArrayList<BaseBrand>();
		int total = 0;
		//设置查询条件
		if (StringUtils.isNotBlank(start)) {
			baseBrandSearch.setStart(Integer.parseInt(start));
		}
		if (StringUtils.isNotBlank(limit)) {
			baseBrandSearch.setLimit(Integer.parseInt(limit));
		}

		String cacheEnable = Utils.getValue(
				SystemCommonConstant.REDIS_CACHE_ENABLE,
				SystemCommonConstant.OPEN_REDIS_CACHE_ENABLE);
		if (StringUtils.equals(SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE,
				cacheEnable)) {
			total = BaseBrandCacher.getBaseBrandTotal(baseBrandSearch);
		} else {
			total = baseBrandMapper2.selectCount(baseBrandSearch);
		}
		if (total > 0) {
			if (StringUtils.equals(
					SystemCommonConstant.CLOSE_REDIS_CACHE_ENABLE, cacheEnable)) {
				list = BaseBrandCacher.getPaginatedList(baseBrandSearch);
			} else {
				list = baseBrandMapper2.selectPaginatedList(baseBrandSearch);
			}
		}

		if ("small".equals(style) && list != null && list.size() > 0) {
			String baseBrandJsonList = JsonUtil.getListToJsonData(list);
			List<BaseBrandSmall> smallList = new JsonDataServiceImpl<BaseBrandSmall>()
					.getJsonToBeanList(baseBrandJsonList, BaseBrandSmall.class);
			return new ResponseEnvelope<BaseBrandSmall>(total, smallList,
					baseBrandSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseBrand>(total, list, msgId);
	}

	/**
	 * 品牌表列表---jsp
	 * 
	 * @param baseBrandSearch
	 * @return
	 */
	public ResponseEnvelope<BaseBrand> jsplist(BaseBrandSearch baseBrandSearch) {
		if (baseBrandSearch.getLimit() == null
				|| baseBrandSearch.getLimit() == 20) {
			baseBrandSearch.setLimit(18);
		}
		List<BaseBrand> list = new ArrayList<BaseBrand>();
		int total = 0;
		total = baseBrandMapper2.selectCount(baseBrandSearch);
		if (total > 0) {
			list = baseBrandMapper2.selectPaginatedList(baseBrandSearch);
			if (CustomerListUtils.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					BaseBrand baseBrand = list.get(i);
					BaseCompany baseCompany = baseCompanyService.get(baseBrand
							.getCompanyId());
					baseBrand.setCompanyName(baseCompany == null ? ""
							: baseCompany.getCompanyName());
				}
			}
		}
		return new ResponseEnvelope<BaseBrand>(total, list);
		// return Utils.getPageUrl(request, JSPMAIN + "/brandBrand");
	}

	/**
	 * 根据品牌ID 查询产品列表
	 * 
	 * @param baseProduct
	 * @param loginUser
	 * @return
	 */
	public List<CategoryProductResult> searchProducts(BaseProduct baseProduct,
			LoginUser loginUser) {

		baseProduct.setOrders(" bp.gmt_modified desc ");
		// 如果是厂商，则只能查询这个厂商品牌下的产品
		if (3 == loginUser.getUserType()) {
			if (baseProduct.getBrandId() != null) {
				if (loginUser.getBrandIds().indexOf(
						baseProduct.getBrandId().toString()) < 0) {
					baseProduct.setBrandId(-9999);
				}
			}
			if (StringUtils.isNotBlank(loginUser.getBrandIds())) {
				String[] brandIds = loginUser.getBrandIds().split(",");
				baseProduct.setBrandIds(Arrays.asList(brandIds));
			}
		}
		baseProduct.setUserId(loginUser.getId());
		//序列号配置过滤 
		Map<String, List<String>> map = authorizedConfigService
				.getConfigParams(loginUser.getId());
		baseProduct.setConfigBrandIdList(map.get("brandIdList"));
		baseProduct.setConfigProductIdList(map.get("productIdList"));
		baseProduct.setConfigSmallTypeIdList(map.get("smallTypeIdList"));
		baseProduct.setConfigTypeValueList(map.get("typeValueList"));
		//序列号配置过滤END
		List<CategoryProductResult> baseProducts = baseProductService
				.selectProductByNameAndBrandId(baseProduct);
		return baseProducts;
	}

	@Override
	public ResponseEnvelope brandList(BaseBrand baseBrand) {
		List<BaseBrand> list = baseBrandMapper2.selectList(baseBrand);
		return new ResponseEnvelope<BaseBrand>(list, null);
	}

}
