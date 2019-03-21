package com.nork.product.service2;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.CollectCatalog;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.search.CollectCatalogSearch;

/**
 * @Title: CollectCatalogService.java
 * @Package com.nork.product.service2
 * @Description:产品管理-收藏目录表Service
 * @createAuthor yangzhun
 * @CreateDate 2017-6-14 14:09:32
 */

public interface CollectCatalogService2 {

	/**
	 * 收藏目录表列表 接口
	 * 
	 * @param collectCatalogSearch
	 * @param msgId
	 * @param loginUser
	 * @return
	 */

	public ResponseEnvelope queryCollectCatalogList(
			CollectCatalogSearch collectCatalogSearch, String msgId,
			LoginUser loginUser);

	/**
	 * 添加收藏目录
	 * 
	 * @param catalog
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope addCollectCatalog(CollectCatalog catalog,
			String msgId);

	/**
	 * 保存我的产品到 收藏目录
	 * 
	 * @param style
	 * @param userProductCollect
	 * @param loginUser
	 * @return
	 */
	public ResponseEnvelope collecProduct(String style,
			UserProductCollect userProductCollect, LoginUser loginUser);

	/**
	 * 删除 收藏目录(转移 产品至 默认目录 方法)
	 * 
	 * @param collectCatalog
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope<CollectCatalog> deleteCollectCatalog(
			CollectCatalog collectCatalog, String msgId);

}
