package com.sandu.product.service.impl;

import com.sandu.product.dao.ProductCategoryRelMapper;
import com.sandu.product.model.CategoryProductResult;
import com.sandu.product.model.ProCategoryPo;
import com.sandu.product.model.ProductCategoryRel;
import com.sandu.product.model.search.UserProductCollectSearch;
import com.sandu.product.service.BaseProductStyleService;
import com.sandu.product.service.ProductCategoryRelService;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.user.model.UserSO;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title: ProductCategoryRelServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品模块-产品与产品类目关联ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Service("productCategoryRelService")
public class ProductCategoryRelServiceImpl implements ProductCategoryRelService {

	private static Logger logger = LoggerFactory.getLogger(ProductCategoryRelServiceImpl.class);

	@Autowired
	private ProductCategoryRelMapper productCategoryRelMapper;

	@Autowired
	private BaseProductStyleService baseProductStyleService;

	@Autowired
	private UserProductCollectService userProductCollectService;

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

	@Override
	public CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel) {
		return productCategoryRelMapper.getCategoryProductResultByProductId(productCategoryRel);
	}

	@Override
	public int findAllProductCount(ProductCategoryRel productCategoryRel) {

		return productCategoryRelMapper.findAllProductCount(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> findAllProductResult(ProductCategoryRel productCategoryRel) {

		return productCategoryRelMapper.findAllProductResult(productCategoryRel);
	}

	@Override
	public int findAllProductCountByCode(ProductCategoryRel productCategoryRel) {

		return productCategoryRelMapper.selectAllProductCountByCode(productCategoryRel);
	}

	@Override
	public List<CategoryProductResult> findAllProductResultByCode(ProductCategoryRel productCategoryRel,
			UserSO userSo) {
		List<CategoryProductResult> resultByCode = productCategoryRelMapper
				.selectAllProductResultByCode(productCategoryRel);
		for (CategoryProductResult categoryProductResult : resultByCode) {
			// 查询该产品的风格
			if (StringUtils.isNotBlank(categoryProductResult.getProductStyleIdInfo())) {
//				JSONObject styleJson = JSONObject.fromObject(categoryProductResult.getProductStyleIdInfo());
//				List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
//				StringBuffer stringBuffer = new StringBuffer("");
//				for (String str : styleInfoList) {
//					stringBuffer.append(str + "  ");
//				}
				String[] split = categoryProductResult.getProductStyleIdInfo().split(",");
				List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(split);
				categoryProductResult.setProductStyleInfoList(styleInfoList);
			}
			//查询产品价格
			if(null==categoryProductResult.getSalePrice()) {
				categoryProductResult.setSalePrice("0.0");
			}
			// 查询该产品是否被收藏
			UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
			userProductCollectSearch.setProductId(categoryProductResult.getProductId());
			if (userSo != null) {
				userProductCollectSearch.setUserId(userSo.getUserId());
			} else {
				userProductCollectSearch.setUserId(0);
			}
			int count = userProductCollectService.getCount(userProductCollectSearch);
			if (count > 0) {
				categoryProductResult.setIsFavorite(1);
			} else {
				categoryProductResult.setIsFavorite(0);
			}

		}
		return resultByCode;
	}

	@Override
	public List<ProCategoryPo> findProductCategoryByProductId(List<Integer> productIds) {
		List<ProCategoryPo> categoryList = productCategoryRelMapper.selectProductCategoryByProductId(productIds);
		List<ProCategoryPo> twoCategoryList = new ArrayList<ProCategoryPo>();
		List<ProCategoryPo> threeCategoryList = new ArrayList<ProCategoryPo>();
		List<ProCategoryPo> fourCategoryList = new ArrayList<ProCategoryPo>();
		List<ProCategoryPo> fiveCategoryList = new ArrayList<ProCategoryPo>();
		for (ProCategoryPo proCategoryPo : categoryList) {
			if(proCategoryPo.getLevel()==2) {
				twoCategoryList.add(proCategoryPo);
			}
			if (proCategoryPo.getLevel() == 3) {
				threeCategoryList.add(proCategoryPo);
			}
			if (proCategoryPo.getLevel() == 4) {
				fourCategoryList.add(proCategoryPo);
			}
			if (proCategoryPo.getLevel() == 5) {
				fiveCategoryList.add(proCategoryPo);
			}
		}
		if (fourCategoryList != null && fourCategoryList.size() > 0 && fiveCategoryList != null
				&& fiveCategoryList.size() > 0) {
			for (ProCategoryPo proCategoryPo4 : fourCategoryList) {
				List<ProCategoryPo> newCategoryList = new ArrayList<>();
				for (ProCategoryPo proCategoryPo5 : fiveCategoryList) {
					if (proCategoryPo5.getLevel() == 5
							&& proCategoryPo5.getPid().intValue() == proCategoryPo4.getId().intValue()) {
						newCategoryList.add(proCategoryPo5);
					}
				}
				proCategoryPo4.setCategories(newCategoryList);
			}
		}
		if (fourCategoryList != null && fourCategoryList.size() > 0 && threeCategoryList != null
				&& threeCategoryList.size() > 0) {
			for (ProCategoryPo proCategoryPo3 : threeCategoryList) {
				List<ProCategoryPo> newCategoryList = new ArrayList<>();
				for (ProCategoryPo proCategoryPo4 : fourCategoryList) {
					if (proCategoryPo4.getLevel() == 4
							&& proCategoryPo4.getPid().intValue() == proCategoryPo3.getId().intValue()) {
						newCategoryList.add(proCategoryPo4);
					}
				}
				proCategoryPo3.setCategories(newCategoryList);
			}
		}
		if(threeCategoryList != null
				&&!threeCategoryList.isEmpty()&&twoCategoryList!=null&&!twoCategoryList.isEmpty()) {
			for (ProCategoryPo proCategoryPo2 : twoCategoryList) {
				List<ProCategoryPo> newCategoryList = new ArrayList<>();
				for (ProCategoryPo proCategoryPo3 : threeCategoryList) {
					if(proCategoryPo3.getLevel()==3&&proCategoryPo3.getPid().intValue()==proCategoryPo2.getId().intValue()) {
						newCategoryList.add(proCategoryPo3);
					}
				}
				proCategoryPo2.setCategories(newCategoryList);
			}			
		}
		
		return twoCategoryList;
	}

	/**
	 * 根据可见范围查询到所有可见的分类编码
	 */
	@Override
	public List<String> getCodeListByIdList(List<Integer> visibilityRangeIdList) {
		// TODO Auto-generated method stub
		return productCategoryRelMapper.getCodeListByIdList(visibilityRangeIdList);
	}

	@Override
	public List <String> getParentCodeListByIdList(List <Integer> visibilityRangeIdList) {
		return productCategoryRelMapper.getParentCodeListByIdList(visibilityRangeIdList);
	}

}
