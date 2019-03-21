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
import com.sandu.interaction.dao.CompanyCommentDao;
import com.sandu.interaction.dao.DesignerCommentDao;
import com.sandu.interaction.dao.PlanCommentDao;
import com.sandu.interaction.dao.ShopCommentDao;
import com.sandu.interaction.dao.TradeCommentDao;
import com.sandu.interaction.model.Comment;
import com.sandu.interaction.model.query.CollectionQuery;
import com.sandu.interaction.model.query.CommentQuery;
import com.sandu.interaction.model.vo.CommentVo;
import com.sandu.interaction.service.CommentService;
import com.sandu.matadata.CacheKeys;
import com.sandu.matadata.Page;
import com.sandu.matadata.enums.InteractionObjectType;

/***
 * 评论服务
 * 
 * @author Administrator
 *
 */
@Service("commentService")
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private CompanyCommentDao companyCommentDao;
	@Autowired
	private DesignerCommentDao designerCommentDao;
	@Autowired
	private PlanCommentDao planCommentDao;
	@Autowired
	private ShopCommentDao shopCommentDao;
	@Autowired
	private TradeCommentDao tradeCommentDao;
	@Autowired
	private CompanyShopDao companyShopDao;
	
	@Override
	public boolean add(Comment comment) {
		boolean isAdded=false;
		comment.preInsert();
		if(comment.getType()==InteractionObjectType.Company.value()) {
			isAdded=companyCommentDao.insert(comment)>0?true:false;
		}
		if(comment.getType()==InteractionObjectType.Designer.value()) {
			isAdded=designerCommentDao.insert(comment)>0?true:false;
		}
		if(comment.getType()==InteractionObjectType.Plan.value()) {
			isAdded=planCommentDao.insert(comment)>0?true:false;
		}
		if(comment.getType()==InteractionObjectType.Shop.value()) {
			isAdded=shopCommentDao.insert(comment)>0?true:false;
			if(isAdded) {
				CompanyShop shop=new CompanyShop();
				shop.setId(comment.getObjId());
				shop.setCommentCount(1);
				shop.setGmtModified(new Date());
				companyShopDao.updateCommentCount(shop);
			}
		}
		if(comment.getType()==InteractionObjectType.TradeInfor.value()) {
			isAdded=tradeCommentDao.insert(comment)>0?true:false;
		}
		return isAdded;
	}

	@Override
	public boolean delete(Comment comment) {
		boolean isDeleted=false;
		if(comment.getType()==InteractionObjectType.Company.value()) {
			isDeleted=companyCommentDao.delete(comment)>0?true:false;
		}
		if(comment.getType()==InteractionObjectType.Designer.value()) {
			isDeleted=designerCommentDao.delete(comment)>0?true:false;
		}
		if(comment.getType()==InteractionObjectType.Plan.value()) {
			isDeleted=planCommentDao.delete(comment)>0?true:false;
		}
		if(comment.getType()==InteractionObjectType.Shop.value()) {
			isDeleted=shopCommentDao.delete(comment)>0?true:false;
			if(isDeleted) {
				CompanyShop shop=new CompanyShop();
				shop.setId(comment.getObjId());
				shop.setCommentCount(-1);
				shop.setGmtModified(new Date());
				companyShopDao.updateCommentCount(shop);
			}
		}
		if(comment.getType()==InteractionObjectType.TradeInfor.value()) {
			isDeleted=tradeCommentDao.delete(comment)>0?true:false;
		}
		return isDeleted;
	}
    @Override
    public int getCount(int type, long objId) {
    	int count = 0;
		String key = CacheKeys.getCommentCount(type, objId);
		if (redisService.exists(key)) {
			count = Integer.parseInt(redisService.get(key));
		} else {
			CommentQuery query = new CommentQuery();
			query.setObjId(BigInteger.valueOf(objId));
			if (type == InteractionObjectType.Company.value()) {
				count = companyCommentDao.count(query);
			}
			if (type == InteractionObjectType.Designer.value()) {
				count = designerCommentDao.count(query);
			}
			if (type == InteractionObjectType.Plan.value()) {
				count = planCommentDao.count(query);
			}
			if (type == InteractionObjectType.Shop.value()) {
				count = shopCommentDao.count(query);
			}
			if (type == InteractionObjectType.TradeInfor.value()) {
				count = tradeCommentDao.count(query);
			}
			redisService.addString(key, GlobalConstant.CACHE_SECONDS, String.valueOf(count));
		}
		return count;
    }
    
    @Override
    public int getCount(int type,long userId, long objId) {
    	int count = 0;
		String key = CacheKeys.getCommentCount(type, objId);
		if (redisService.exists(key)) {
			count = Integer.parseInt(redisService.get(key));
		} else {
			CommentQuery query = new CommentQuery();
			query.setObjId(BigInteger.valueOf(objId));
			query.setUserId(BigInteger.valueOf(userId));
			if (type == InteractionObjectType.Company.value()) {
				count = companyCommentDao.count(query);
			}
			if (type == InteractionObjectType.Designer.value()) {
				count = designerCommentDao.count(query);
			}
			if (type == InteractionObjectType.Plan.value()) {
				count = planCommentDao.count(query);
			}
			if (type == InteractionObjectType.Shop.value()) {
				count = shopCommentDao.count(query);
			}
			if (type == InteractionObjectType.TradeInfor.value()) {
				count = tradeCommentDao.count(query);
			}
			redisService.addString(key, GlobalConstant.CACHE_SECONDS, String.valueOf(count));
		}
		return count;
    }
	
	@Override
	public Page<CommentVo> getList(CommentQuery query) {
		Page<CommentVo> page=new Page<CommentVo>();
		if(query.getType()==InteractionObjectType.Company.value()) {
			page.setCount(companyCommentDao.findFontCount(query));
			page.setList(companyCommentDao.findFontPageList(query));
		}
		if(query.getType()==InteractionObjectType.Designer.value()) {
			page.setCount(designerCommentDao.findFontCount(query));
			page.setList(designerCommentDao.findFontPageList(query));
		}
		if(query.getType()==InteractionObjectType.Plan.value()) {
			page.setCount(planCommentDao.findFontCount(query));
			page.setList(planCommentDao.findFontPageList(query));
		}
		if(query.getType()==InteractionObjectType.Shop.value()) {
			page.setCount(shopCommentDao.findFontCount(query));
			page.setList(shopCommentDao.findFontPageList(query));
		}
		if(query.getType()==InteractionObjectType.TradeInfor.value()) {
			page.setCount(tradeCommentDao.findFontCount(query));
			page.setList(tradeCommentDao.findFontPageList(query));
		}
		return page;
	}

}
