package com.sandu.interaction.service.impl;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.company.dao.CompanyShopDao;
import com.sandu.company.model.CompanyShop;
import com.sandu.interaction.dao.CompanyCollectionDao;
import com.sandu.interaction.dao.DesignerCollectionDao;
import com.sandu.interaction.dao.PlanCollectionDao;
import com.sandu.interaction.dao.ShopCollectionDao;
import com.sandu.interaction.dao.TradeCollectionDao;
import com.sandu.interaction.model.Collection;
import com.sandu.interaction.model.query.CollectionQuery;
import com.sandu.interaction.model.vo.CollectionVo;
import com.sandu.interaction.service.CollectionService;
import com.sandu.matadata.CacheKeys;
import com.sandu.matadata.Page;
import com.sandu.matadata.enums.InteractionObjectType;

/***
 * 收藏服务
 * 
 * @author Administrator
 *
 */
@Service("collectionService")
@Transactional(readOnly = true)
public class CollectionServiceImpl implements CollectionService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private CompanyCollectionDao companyCollectionDao;
	@Autowired
	private DesignerCollectionDao designerCollectionDao;
	@Autowired
	private PlanCollectionDao planCollectionDao;
	@Autowired
	private ShopCollectionDao shopCollectionDao;
	@Autowired
	private TradeCollectionDao tradeCollectionDao;
	@Autowired
	private CompanyShopDao companyShopDao;

	@Override
	public boolean add(Collection collection) {
		boolean isAdded = false;
		collection.preInsert();
		if (collection.getType() == InteractionObjectType.Company.value()) {
			isAdded = companyCollectionDao.insert(collection) > 0 ? true : false;
		}
		if (collection.getType() == InteractionObjectType.Designer.value()) {
			
			isAdded = designerCollectionDao.insert(collection) > 0 ? true : false;
		}
		if (collection.getType() == InteractionObjectType.Plan.value()) {
			isAdded = planCollectionDao.insert(collection) > 0 ? true : false;
		}
		if (collection.getType() == InteractionObjectType.Shop.value()) {
			isAdded = shopCollectionDao.insert(collection) > 0 ? true : false;
			if (isAdded) {
				CompanyShop shop = new CompanyShop();
				shop.setId(collection.getObjId());
				shop.setCollectionCount(1);
				shop.setGmtModified(new Date());
				companyShopDao.updateCollectionCount(shop);
			}
		}
		if (collection.getType() == InteractionObjectType.TradeInfor.value()) {
			isAdded = tradeCollectionDao.insert(collection) > 0 ? true : false;
		}
		return isAdded;
	}

	@Override
	public boolean delete(Collection collection) {
		boolean isDeleted = false;
		if (collection.getType() == InteractionObjectType.Company.value()) {
			isDeleted = companyCollectionDao.delete(collection) > 0 ? true : false;
		}
		if (collection.getType() == InteractionObjectType.Designer.value()) {
			isDeleted = designerCollectionDao.delete(collection) > 0 ? true : false;
		}
		if (collection.getType() == InteractionObjectType.Plan.value()) {
			isDeleted = planCollectionDao.delete(collection) > 0 ? true : false;
		}
		if (collection.getType() == InteractionObjectType.Shop.value()) {
			isDeleted = shopCollectionDao.delete(collection) > 0 ? true : false;
			if (isDeleted) {
				CompanyShop shop = new CompanyShop();
				shop.setId(collection.getObjId());
				shop.setCollectionCount(-1);
				shop.setGmtModified(new Date());
				companyShopDao.updateCollectionCount(shop);
			}
		}
		if (collection.getType() == InteractionObjectType.TradeInfor.value()) {
			isDeleted = tradeCollectionDao.delete(collection) > 0 ? true : false;
		}
		return isDeleted;
	}

	@Override
	public Page<CollectionVo> getList(CollectionQuery query) {
		Page<CollectionVo> page = new Page<CollectionVo>();
		if (query.getType() == InteractionObjectType.Company.value()) {
			page.setCount(companyCollectionDao.findFontCount(query));
			page.setList(companyCollectionDao.findFontPageList(query));
		}
		if (query.getType() == InteractionObjectType.Designer.value()) {
			page.setCount(designerCollectionDao.findFontCount(query));
			page.setList(designerCollectionDao.findFontPageList(query));
		}
		if (query.getType() == InteractionObjectType.Plan.value()) {
			page.setCount(planCollectionDao.findFontCount(query));
			page.setList(planCollectionDao.findFontPageList(query));
		}
		if (query.getType() == InteractionObjectType.Shop.value()) {
			page.setCount(shopCollectionDao.findFontCount(query));
			page.setList(shopCollectionDao.findFontPageList(query));
		}
		if (query.getType() == InteractionObjectType.TradeInfor.value()) {
			page.setCount(tradeCollectionDao.findFontCount(query));
			page.setList(tradeCollectionDao.findFontPageList(query));
		}
		return page;
	}

	@Override
	public int getCount(int collectionType, long objId) {
		int count = 0;
		String key = CacheKeys.getCollectionCount(collectionType, objId);
		if (redisService.exists(key)) {
			count = Integer.parseInt(redisService.get(key));
		} else {
			CollectionQuery query = new CollectionQuery();
			query.setObjId(BigInteger.valueOf(objId));
			if (collectionType == InteractionObjectType.Company.value()) {
				count = companyCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.Designer.value()) {
				count = designerCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.Plan.value()) {
				count = planCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.Shop.value()) {
				count = shopCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.TradeInfor.value()) {
				count = tradeCollectionDao.count(query);
			}
			redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS, String.valueOf(count));
		}
		return count;
	}

	@Override
	public int getCount(int collectionType, long userId, long objId) {
		int count = 0;
		String key = CacheKeys.getCollectionCount(collectionType, userId, objId);
		if (redisService.exists(key)) {
			count = Integer.parseInt(redisService.get(key));
		} else {
			CollectionQuery query = new CollectionQuery();
			query.setObjId(BigInteger.valueOf(objId));
			query.setUserId(BigInteger.valueOf(userId));
			if (collectionType == InteractionObjectType.Company.value()) {
				count = companyCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.Designer.value()) {
				count = designerCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.Plan.value()) {
				count = planCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.Shop.value()) {
				count = shopCollectionDao.count(query);
			}
			if (collectionType == InteractionObjectType.TradeInfor.value()) {
				count = tradeCollectionDao.count(query);
			}
			redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS, String.valueOf(count));
		}
		return count;
	}
}
