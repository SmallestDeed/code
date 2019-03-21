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
import com.sandu.interaction.dao.CompanyLikeDao;
import com.sandu.interaction.dao.DesignerLikeDao;
import com.sandu.interaction.dao.PlanLikeDao;
import com.sandu.interaction.dao.ShopLikeDao;
import com.sandu.interaction.dao.TradeLikeDao;
import com.sandu.interaction.model.Like;
import com.sandu.interaction.model.query.LikeQuery;
import com.sandu.interaction.service.LikeService;
import com.sandu.matadata.CacheKeys;
import com.sandu.matadata.enums.InteractionObjectType;

/***
 * 点赞服务接口实现类
 * 
 * @author Administrator
 *
 */
@Service("likeService")
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private CompanyLikeDao companyLikeDao;
	@Autowired
	private DesignerLikeDao designerLikeDao;
	@Autowired
	private ShopLikeDao shopLikeDao;
	@Autowired
	private PlanLikeDao planLikeDao;
	@Autowired
	private TradeLikeDao tradeLikeDao;
	@Autowired
	private CompanyShopDao companyShopDao;
	@Override
	public boolean add(Like like) {
		boolean isAdded = false;
		like.preInsert();
		if (like.getLikeType() == InteractionObjectType.Company.value()) {
			isAdded = companyLikeDao.insert(like) > 0 ? true : false;
		}
		if (like.getLikeType() == InteractionObjectType.Designer.value()) {
			isAdded = designerLikeDao.insert(like) > 0 ? true : false;
		}
		if (like.getLikeType() == InteractionObjectType.Plan.value()) {
			isAdded = planLikeDao.insert(like) > 0 ? true : false;
		}
		if (like.getLikeType() == InteractionObjectType.Shop.value()) {
			isAdded = shopLikeDao.insert(like) > 0 ? true : false;
			if(isAdded) {
				CompanyShop shop=new CompanyShop();
				shop.setId(like.getObjId());
				shop.setLikeCount(1);
				shop.setGmtModified(new Date());
				companyShopDao.updateLikeCount(shop);
			}
		}
		if (like.getLikeType() == InteractionObjectType.TradeInfor.value()) {
			isAdded = tradeLikeDao.insert(like) > 0 ? true : false;
		}
		return isAdded;
	}

	@Override
	public boolean cancel(Like like) {
		boolean isCanceled = false;
		if (like.getLikeType() == InteractionObjectType.Company.value()) {
			isCanceled = companyLikeDao.cancel(like) > 0 ? true : false;
		}
		if (like.getLikeType() == InteractionObjectType.Designer.value()) {
			isCanceled = designerLikeDao.cancel(like) > 0 ? true : false;
		}
		if (like.getLikeType() == InteractionObjectType.Plan.value()) {
			isCanceled = planLikeDao.cancel(like) > 0 ? true : false;
		}
		if (like.getLikeType() == InteractionObjectType.Shop.value()) {
			isCanceled = shopLikeDao.cancel(like) > 0 ? true : false;
			if(isCanceled) {
				CompanyShop shop=new CompanyShop();
				shop.setId(like.getObjId());
				shop.setLikeCount(-1);
				shop.setGmtModified(new Date());
				companyShopDao.updateLikeCount(shop);
			}
		}
		if (like.getLikeType() == InteractionObjectType.TradeInfor.value()) {
			isCanceled = tradeLikeDao.cancel(like) > 0 ? true : false;
		}
		return isCanceled;
	}

	@Override
	public int getCount(int likeType, long objId) {
		int count = 0;
		String key = CacheKeys.getLikeCount(likeType, objId);
		if (redisService.exists(key)) {
			count = Integer.parseInt(redisService.get(key));
		} else {
			LikeQuery query = new LikeQuery();
			query.setLikeType(likeType);
			query.setObjId(BigInteger.valueOf(objId));
			if (query.getLikeType() == InteractionObjectType.Company.value()) {
				count = companyLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.Designer.value()) {
				count = designerLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.Plan.value()) {
				count = planLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.Shop.value()) {
				count = shopLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.TradeInfor.value()) {
				count = tradeLikeDao.count(query);
			}
			redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS,String.valueOf(count));
		}
		return count;
	}
	
	public int getCount(int likeType,long userId,long objId) {
		int count = 0;
		String key = CacheKeys.getLikeCount(likeType, userId,objId);
		if (redisService.exists(key)) {
			count = Integer.parseInt(redisService.get(key));
		} else {
			LikeQuery query = new LikeQuery();
			query.setLikeType(likeType);
			query.setObjId(BigInteger.valueOf(objId));
			query.setUserId(BigInteger.valueOf(userId));
			if (query.getLikeType() == InteractionObjectType.Company.value()) {
				count = companyLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.Designer.value()) {
				count = designerLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.Plan.value()) {
				count = planLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.Shop.value()) {
				count = shopLikeDao.count(query);
			}
			if (query.getLikeType() == InteractionObjectType.TradeInfor.value()) {
				count = tradeLikeDao.count(query);
			}
			redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS,String.valueOf(count));
		}
		return count;
	}
	
}
