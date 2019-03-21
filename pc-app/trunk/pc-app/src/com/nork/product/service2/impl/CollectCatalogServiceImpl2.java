package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.Lists;
import com.nork.product.cache.BaseProductCacher;
import com.nork.product.cache.ProductCategoryRelCacher;
import com.nork.product.cache.UserProductCollectCacher;
import com.nork.product.dao2.CollectCatalogMapper;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CollectCatalog;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.result.SearchCollectCatalogResult;
import com.nork.product.model.search.CollectCatalogSearch;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.model.small.UserProductCollectSmall;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.UserProductCollectService;
import com.nork.product.service2.CollectCatalogService2;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

/**
 * @Title: CollectCatalogServiceImpl.java
 * @Package com.nork.product.service2.impl
 * @Description:产品管理-收藏目录表ServiceImpl
 * @CreateAuthor yangzhun
 * @CreateDate 2017-6-14 14:18:07
 */
@Service("collectCatalogService2")
@Transactional()
public class CollectCatalogServiceImpl2 implements CollectCatalogService2 {

	@Autowired
	private CollectCatalogMapper collercCatalogMapper2;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private UserProductCollectService userProductCollectService;
	@Autowired
	private BaseProductService baseProductService;

	/**
	 * 收藏目录表列表 接口
	 */
	@Override
	public ResponseEnvelope queryCollectCatalogList(
			CollectCatalogSearch collectCatalogSearch, String msgId,
			LoginUser loginUser) {

		collectCatalogSearch.setUserId(loginUser.getId());
		List<SearchCollectCatalogResult> ResultList = new ArrayList<SearchCollectCatalogResult>();
		List<CollectCatalog> list = new ArrayList<CollectCatalog>();
		int count = collercCatalogMapper2.selectCount(collectCatalogSearch);

		if (count <= 0) {
			SysUser user = sysUserService.get(loginUser.getId());
			newCollectCatalogController(user);
			count = collercCatalogMapper2.selectCount(collectCatalogSearch);
		}
		if (count > 0) {
			collectCatalogSearch.setOrder("gmt_create");
			collectCatalogSearch.setOrderNum("desc");
			list = collercCatalogMapper2
					.selectPaginatedList(collectCatalogSearch);
		}
		if (list != null && list.size() > 0) {
			for (CollectCatalog collectCatalog : list) {
				SearchCollectCatalogResult searchCollectCatalogResult = new SearchCollectCatalogResult();
				searchCollectCatalogResult.setId(collectCatalog.getId());
				searchCollectCatalogResult.setCatalogName(collectCatalog
						.getCatalogName());
				searchCollectCatalogResult.setSysCode(collectCatalog
						.getSysCode());
				searchCollectCatalogResult.setIsLocked(collectCatalog
						.getIsLocked());
				ResultList.add(searchCollectCatalogResult);
			}
		}
		return new ResponseEnvelope(ResultList, msgId);
	}

	/**
	 * 新建一个默认文件夹
	 * 
	 * @param user
	 */
	private void newCollectCatalogController(SysUser user) {
		CollectCatalog catalog = new CollectCatalog();
		catalog.setCatalogName(SystemCommonConstant.DEFAULT);
		catalog.setIsLocked(1);
		catalog.setUserId(user.getId());
		catalog.setIsDeleted(0);
		catalog.setSysCode(System.currentTimeMillis() + "_" + randomNumber());

		catalog.setModifier(user.getMobile());
		catalog.setCreator(user.getNickName());
		collercCatalogMapper2.insertSelective(catalog);
	}

	/**
	 * 获得一个随机数
	 * 
	 * @return
	 */
	private int randomNumber() {
		Set<Integer> m = new HashSet<Integer>();
		int a;
		do {
			a = (int) (Math.random() * 1000000);
		} while (m.contains(a));
		m.add(a);
		return a;
	}

	/**
	 * 添加收藏目录
	 */
	@Override
	public ResponseEnvelope<CollectCatalog> addCollectCatalog(
			CollectCatalog catalog, String msgId) {

		collercCatalogMapper2.insertSelective(catalog);
		return new ResponseEnvelope<CollectCatalog>(catalog, msgId, true);
	}

	/**
	 * 保存我的产品到 收藏目录
	 * 
	 * @param style
	 * @param userProductCollect
	 * @return
	 */
	public ResponseEnvelope collecProduct(String style,
			UserProductCollect userProductCollect, LoginUser loginUser) {
		Integer userId = 0;
		userId = loginUser.getId();
		userProductCollect.setUserId(userId);

		if (StringUtils.isBlank(userProductCollect.getMsgId())) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,
					SystemCommonConstant.MSGID_NOT_NULL,
					userProductCollect.getMsgId());
		}
		if (userProductCollect.getUserId() == null
				|| userProductCollect.getUserId() == 0) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,
					SystemCommonConstant.USERID_NOT_NULL,
					userProductCollect.getMsgId());
		}
		if (userProductCollect.getCollectCatalogId() == null
				|| userProductCollect.getCollectCatalogId() == 0) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,
					SystemCommonConstant.COLLECTCATALOGID_NOT_NULL,
					userProductCollect.getMsgId());
		}
		if (userProductCollect.getProductId() == null) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,
					SystemCommonConstant.PRODUCTID_NOT_NULL,
					userProductCollect.getMsgId());
		} else if (userProductCollect.getProductId() != null) {
			BaseProduct bp = new BaseProduct();
			bp = baseProductService.get(userProductCollect.getProductId());
			if (bp == null) {
				return new ResponseEnvelope<UserProductCollectSmall>(false,
						SystemCommonConstant.PRODUCT_NOT_EXIST,
						userProductCollect.getMsgId());
			}
		}

		CollectCatalog collectCatalog = collercCatalogMapper2
				.selectByPrimaryKey(userProductCollect.getCollectCatalogId());
		if (collectCatalog == null) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,
					SystemCommonConstant.COLLECTION_DIRECTORY_NOT_EXIST,
					userProductCollect.getMsgId());
		}
		// 检测是否收藏过该产品(不分收藏夹)
		Integer collectCatalogId = userProductCollect.getCollectCatalogId();
		userProductCollect.setCollectCatalogId(null);
		List<UserProductCollect> list = userProductCollectService
				.getList(userProductCollect);
		if (Lists.isNotEmpty(list)) {
			return new ResponseEnvelope<UserProductCollectSmall>(false,
					SystemCommonConstant.PRODUCT_COLLECTED,
					userProductCollect.getMsgId());
		}
		userProductCollect.setCollectCatalogId(collectCatalogId);
		// 检测是否收藏过该产品(不分收藏夹)->end

		if (userProductCollect.getId() == null) {
			int id = userProductCollectService.add(userProductCollect);
			userProductCollect.setId(id);
			BaseProductCacher.remove(id);

		} else {
			int id = userProductCollectService.update(userProductCollect);
			BaseProductCacher.remove(id);
		}
		UserProductCollectCacher.remove(userProductCollect.getId());
		ProductCategoryRelCacher.remove(1);
		if ("small".equals(style)) {
			return new ResponseEnvelope<UserProductCollect>(true,
					SystemCommonConstant.OK, userProductCollect.getMsgId());
		}

		return new ResponseEnvelope<UserProductCollect>(userProductCollect,
				userProductCollect.getMsgId(), true);
	}

	/**
	 * 删除 收藏目录(转移 产品至 默认目录 方法)
	 * 
	 * @param collectCatalog
	 * @param msgId
	 * @return
	 */
	public ResponseEnvelope<CollectCatalog> deleteCollectCatalog(
			CollectCatalog collectCatalog, String msgId) {

		if (msgId == null || "".equals(msgId)) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.LACK_MSGID, msgId);
		}
		if (collectCatalog == null) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.LACK_ID, msgId);
		}
		if (collectCatalog.getId() == null || collectCatalog.getId() <= 0) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.LACK_ID, msgId);
		}
		if (collectCatalog.getUserId() == null
				|| collectCatalog.getUserId() <= 0) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.LACK_USERID, msgId);
		}

		List<CollectCatalog> list = collercCatalogMapper2
				.selectList(collectCatalog);
		if (list == null || list.size() <= 0) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.DIRECTORY_NOT_EXIST, msgId);
		}

		// CollectCatalog
		// collect_Catalog=collectCatalogService.get(collectCatalog.getId());
		if (SystemCommonConstant.DEFAULT.equals(list.get(0).getCatalogName())) {
			return new ResponseEnvelope<CollectCatalog>(false,
					SystemCommonConstant.DIRECTORY_DEFAULT, msgId);
		}

		collercCatalogMapper2.deleteByPrimaryKey(collectCatalog.getId());

		// 查询该用户的 默认收藏夹 Id
		CollectCatalogSearch collectCatalogSearch = new CollectCatalogSearch();
		collectCatalogSearch.setUserId(collectCatalog.getUserId());
		collectCatalogSearch.setCatalogName(SystemCommonConstant.DEFAULT);
		List<CollectCatalog> CollectCatalogList = collercCatalogMapper2
				.selectPaginatedList(collectCatalogSearch);
		int defaultId = CollectCatalogList.get(0).getId();

		// 通过用户id 和 收藏夹Id，将将收藏夹ID 改为 默认Id
		UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
		userProductCollectSearch.setUserId(collectCatalog.getUserId());
		userProductCollectSearch.setCollectCatalogId(collectCatalog.getId());
		userProductCollectSearch.setDefaultId(defaultId);
		// 转移方法 
		userProductCollectService.transferCollection(userProductCollectSearch);

		return new ResponseEnvelope<CollectCatalog>(true, msgId, true);
	}
}
