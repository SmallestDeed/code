package com.sandu.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sandu.common.utils.Utils;
import com.sandu.sys.model.vo.ProCategoryVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.utils.JsonUtils;
import com.sandu.matadata.CacheKeys;
import com.sandu.sys.dao.ProductCategoryDao;
import com.sandu.sys.model.query.ProductCategoryQuery;
import com.sandu.sys.model.vo.ProductCategoryNodeVo;
import com.sandu.sys.model.vo.ProductCategoryVo;
import com.sandu.sys.service.ProductCategoryService;

@Service("productCategoryService")
@Transactional(readOnly = true)
public class ProductCategoryServiceImpl implements ProductCategoryService{
	private static Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private RedisService redisService;
	
	private List<ProductCategoryVo> getChildList(List<ProductCategoryVo> lstCategory,long pid){
		List<ProductCategoryVo> lstChild=new ArrayList<ProductCategoryVo>();
		if(lstCategory!=null && lstCategory.size()>0) {
			for(ProductCategoryVo vo:lstCategory) {
				if(vo.getPid()==pid) {
					lstChild.add(vo); 
				}
			}
		}
		return lstChild;
	}
	
	public List<ProductCategoryVo> getFirstList(){
		List<ProductCategoryVo> lstCategory=null;
		String key=CacheKeys.getFirstProductCategory();
		String jsonDicList=redisService.get(key);
		if(StringUtils.isBlank(jsonDicList)) {
			ProductCategoryQuery query=new ProductCategoryQuery();
			lstCategory= productCategoryDao.findFirstList(query);
			jsonDicList=JsonUtils.toJson(lstCategory);
			redisService.set(key, jsonDicList, GlobalConstant.LONG_CACHE_SECONDS);
		}
		else {
			lstCategory=JsonUtils.fromJson(jsonDicList, new TypeToken<List<ProductCategoryVo>>() {}.getType());
		}
		return lstCategory;
	}
	
	@Override
	public List<ProductCategoryNodeVo> getNodeList() {
		List<ProductCategoryNodeVo> lstNode=new ArrayList<ProductCategoryNodeVo>();
		List<ProductCategoryVo> lstCategory=null;
		String key=CacheKeys.getProductCategory();
		String jsonDicList=redisService.get(key);
		logger.debug("getNodeList{} jsonDicList: "+jsonDicList);
		if(StringUtils.isBlank(jsonDicList)) {
			ProductCategoryQuery query=new ProductCategoryQuery();
			lstCategory= productCategoryDao.findFontAllList(query);
			jsonDicList=JsonUtils.toJson(lstCategory);
			redisService.del(key);
			redisService.addString(key,GlobalConstant.LONG_CACHE_SECONDS,jsonDicList);
		}
		else {
			lstCategory=JsonUtils.fromJson(jsonDicList, new TypeToken<List<ProductCategoryVo>>() {}.getType());
		}
		if(lstCategory!=null && lstCategory.size()>0) {
			for(ProductCategoryVo vo:lstCategory) {
				if(vo.getPid()==1) {
					ProductCategoryNodeVo node=new ProductCategoryNodeVo();
					node.setId(vo.getId());
					node.setLevel(vo.getLevel());
					node.setName(vo.getName());
					node.setPid(vo.getPid());
					node.setChilds(getChildList(lstCategory,vo.getId()));
					lstNode.add(node);
				}
			}
		}
		return lstNode;
	}

	@Override
	public List<ProductCategoryVo> getCategoryListByIds(Integer companyId, String categoryIds) {
		if (StringUtils.isEmpty(categoryIds)) {
			return null;
		}
		List<ProductCategoryVo> lstNode = null;
		String key = CacheKeys.productCategory(companyId);
		String jsonDicList = redisService.get(key);
		if (StringUtils.isBlank(jsonDicList)) {
			List<Integer> categoryIdList = Utils.getIntegerListFromString(categoryIds);
			lstNode = productCategoryDao.findListByIds(categoryIdList);
			if(lstNode==null) {
				lstNode=new ArrayList<ProductCategoryVo>();
			}
			jsonDicList = JsonUtils.toJson(lstNode);
			redisService.set(key, jsonDicList, GlobalConstant.LONG_CACHE_SECONDS);
		} else {
			lstNode = JsonUtils.fromJson(jsonDicList, new TypeToken<List<ProductCategoryVo>>() {}.getType());
		}
		return lstNode;
	}

	@Override
	public List<ProCategoryVO> getListByLevel(Integer level,Integer start,Integer pageSize) {
		return productCategoryDao.getListByLevel(level,start,pageSize);
	}

}
