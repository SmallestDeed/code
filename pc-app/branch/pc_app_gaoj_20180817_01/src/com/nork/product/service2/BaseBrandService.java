package com.nork.product.service2;

import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.search.BaseBrandSearch;

/**
 * @Title: BaseBrandService2.java
 * @Package com.nork.product.service2
 * @Description:产品-品牌表Service
 * @createAuthor yangzhun
 * @CreateDate 2017-6-13 17:02:26
 */

public interface BaseBrandService {

	/**
	 * 根据产品种类 查询所有品牌的方法
	 * 
	 * @param style
	 * @param brandName
	 * @param brandStyleId
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 * @CreateDate 2017-6-13 17:00:25
	 */
	public ResponseEnvelope listAllByPara(String style, String brandName,
			String brandStyleId, String msgId, String limit, String start);

	/**
	 * 查询所有品牌的方法
	 * 
	 * @param style
	 * @param brandName
	 * @param msgId
	 * @param limit
	 * @param start
	 * @return
	 */
	public ResponseEnvelope listAll(String style, String brandName,
			String msgId, String limit, String start);

	/**
	 * 品牌表列表---jsp
	 * 
	 * @param baseBrandSearch
	 * @return
	 */
	public ResponseEnvelope<BaseBrand> jsplist(BaseBrandSearch baseBrandSearch);

	/**
	 * 根据品牌ID 查询产品列表
	 * 
	 * @param baseProduct
	 * @param loginUser
	 * @return
	 */
	public List<CategoryProductResult> searchProducts(BaseProduct baseProduct,
			LoginUser loginUser);
	
	
	/**
	 * @param baseBrand
	 * @return
	 */
	public ResponseEnvelope brandList(BaseBrand baseBrand);

}
