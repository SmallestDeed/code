package com.nork.product.service2.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.design.service.DesignPlanService;
import com.nork.product.dao.ProductRecommendationMapper;
import com.nork.product.model.DesignTempletProducts;
import com.nork.product.model.ProductRecommendation;
import com.nork.product.model.RecommendProductResult;
import com.nork.product.model.search.ProductRecommendationSearch;
import com.nork.product.service2.ProductRecommendationService;

/**
 * @Title: ProductRecommendationServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品-产品推荐ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 14:02:16
 * @version V1.0
 */
public class ProductRecommendationServiceImpl implements ProductRecommendationService {
	
	private static final String PARAMETER_NULL = "参数为空";
	private static final String TEMPLATEID_NULL = "样板房id为空";
	private static final String DATA_EXCEPTION = "数据异常!";
	
	private ProductRecommendationMapper productRecommendationMapper;
	@Autowired
	private ProductRecommendationService productRecommendationService;
	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	public void setProductRecommendationMapper(ProductRecommendationMapper productRecommendationMapper) {
		this.productRecommendationMapper = productRecommendationMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param productRecommendation
	 * @return int
	 */
	@Override
	public int add(ProductRecommendation productRecommendation) {
		productRecommendationMapper.insertSelective(productRecommendation);
		return productRecommendation.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param productRecommendation
	 * @return int
	 */
	@Override
	public int update(ProductRecommendation productRecommendation) {
		return productRecommendationMapper.updateByPrimaryKeySelective(productRecommendation);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return productRecommendationMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return ProductRecommendation
	 */
	@Override
	public ProductRecommendation get(Integer id) {
		return productRecommendationMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param productRecommendation
	 * @return List<ProductRecommendation>
	 */
	@Override
	public List<ProductRecommendation> getList(ProductRecommendation productRecommendation) {
		return productRecommendationMapper.selectList(productRecommendation);
	}

	/**
	 * 获取数据数量
	 *
	 * @param productRecommendation
	 * @return int
	 */
	@Override
	public int getCount(ProductRecommendationSearch productRecommendationSearch) {
		return productRecommendationMapper.selectCount(productRecommendationSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param productRecommendation
	 * @return List<ProductRecommendation>
	 */
	@Override
	public List<ProductRecommendation> getPaginatedList(ProductRecommendationSearch productRecommendationSearch) {
		return productRecommendationMapper.selectPaginatedList(productRecommendationSearch);
	}

	@Override
	public List<DesignTempletProducts> getDesignProductList(DesignTempletProducts DesignTempletProducts) {
		return productRecommendationMapper.selectDesignProductList(DesignTempletProducts);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 根据大类小类和样板房ID查询
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List<RecommendProductResult> getRecommendProduct(ProductRecommendationSearch productRecommendation) {
		return productRecommendationMapper.getRecommendProduct(productRecommendation);
	}

	/**
	 * 产品推荐详情
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List<RecommendProductResult> recommendProductList(ProductRecommendationSearch productRecommendation) {
		return productRecommendationMapper.recommendProductList(productRecommendation);
	}

	@Override
	public int recommendProductCount(ProductRecommendationSearch productRecommendation) {
		return productRecommendationMapper.recommendProductCount(productRecommendation);
	}

	/**
	 * 产品推荐详情/历史推荐产品列表
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List<RecommendProductResult> getRecommendProList(DesignTempletProductSearch designTempletProductSearch) {
		return productRecommendationMapper.recommendProList(designTempletProductSearch);
	}

	@Override
	public int getRecommendProCount(DesignTempletProductSearch designTempletProductSearch) {
		return productRecommendationMapper.recommendProCount(designTempletProductSearch);
	}

	/**
	 * 产品管理 - 产品推荐列表
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List<RecommendProductResult> getProductRecommendList(RecommendProductResult recommendProductResult) {
		return productRecommendationMapper.proRecommendList(recommendProductResult);
	}

	@Override
	public int getProductRecommendCount(RecommendProductResult recommendProductResult) {
		return productRecommendationMapper.proRecommendCount(recommendProductResult);
	}

	/**
	 * 通过样板房编码删除推荐
	 * 
	 * @param designCode
	 * @return
	 */
	@Override
	public int deleteByDesignCode(String designCode) {
		// 删除资源文件

		// 删除数据
		return productRecommendationMapper.deleteByDesignCode(designCode);
	}

	@Override
	public ProductRecommendation sysSave(ProductRecommendation productRecommendation, LoginUser loginUser) {
		productRecommendation.setCreator(loginUser.getLoginName());
		productRecommendation.setModifier(loginUser.getLoginName());
		productRecommendation.setGmtCreate(new Date());
		productRecommendation.setGmtModified(new Date());
		return productRecommendation;
	}

	/**
	 * 查询样板间推荐产品
	 * 
	 * @return
	 */
	public Object designTemplateRecommend(ProductRecommendationSearch productRecommendationSearch) {
		if (productRecommendationSearch == null) {
			return new ResponseEnvelope<RecommendProductResult>(false, PARAMETER_NULL, productRecommendationSearch.getMsgId());
		}
		if (productRecommendationSearch.getDesignTempletId() == null) {
			return new ResponseEnvelope<RecommendProductResult>(false, TEMPLATEID_NULL,
					productRecommendationSearch.getMsgId());
		}
		List<RecommendProductResult> list = null;
		int total = 0;
		try {
			DesignPlan designPlan = designPlanService.get(productRecommendationSearch.getDesignTempletId());
			productRecommendationSearch.setDesignTempletId(designPlan.getDesignTemplateId());
			total = productRecommendationService.getCount(productRecommendationSearch);
			if (total > 0) {
				list = productRecommendationService.getRecommendProduct(productRecommendationSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<RecommendProductResult>(false, DATA_EXCEPTION);
		}
		return new ResponseEnvelope<RecommendProductResult>(total, list, productRecommendationSearch.getMsgId());
	}
}
