package com.nork.product.service2.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.product.cache.ProCategoryCacher;
import com.nork.product.dao.ProCategoryMapper;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProCategory;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service2.ProCategoryService;

/**
 * @Title: ProCategoryServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品类目ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:46:36
 * @version V1.0
 */
public class ProCategoryServiceImpl implements ProCategoryService {
	private static Logger logger = Logger.getLogger(ProCategoryServiceImpl.class);

	private static final String TYPE_NOT_NULL = "参数type不能为空";
	private static final String MSGID_NOT_NULL = "参数msgId不能为空";
	private static final String TYPE_IS = "参数type为‘";
	private static final String MUST_BE_INTRODUCTION_CID = "’时，必须传入cid参数";
	private static final String ELSE = "其他";

	private ProCategoryMapper proCategoryMapper;
	@Autowired
	private ProCategoryService proCategoryService;

	@Autowired
	public void setProCategoryMapper(ProCategoryMapper proCategoryMapper) {
		this.proCategoryMapper = proCategoryMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param proCategory
	 * @return int
	 */
	@Override
	public int add(ProCategory proCategory) {
		proCategoryMapper.insertSelective(proCategory);
		return proCategory.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param proCategory
	 * @return int
	 */
	@Override
	public int update(ProCategory proCategory) {
		return proCategoryMapper.updateByPrimaryKeySelective(proCategory);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return proCategoryMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return ProCategory
	 */
	@Override
	public ProCategory get(Integer id) {
		return proCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param proCategory
	 * @return List<ProCategory>
	 */
	@Override
	public List<ProCategory> getList(ProCategory proCategory) {
		return proCategoryMapper.selectList(proCategory);
	}

	/**
	 * 所有数据
	 * 
	 * @param proCategory
	 * @return List<ProCategory>
	 */
	@Override
	public List<ProCategory> getListV2(ProCategory proCategory) {
		return proCategoryMapper.selectListV2(proCategory);
	}

	/**
	 * 获取数据数量
	 *
	 * @param proCategory
	 * @return int
	 */
	@Override
	public int getCount(ProCategorySearch proCategorySearch) {
		return proCategoryMapper.selectCount(proCategorySearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param proCategory
	 * @return List<ProCategory>
	 */
	@Override
	public List<ProCategory> getPaginatedList(ProCategorySearch proCategorySearch) {
		return proCategoryMapper.selectPaginatedList(proCategorySearch);
	}

	/**
	 * 根据分类Code查询
	 * 
	 * @return
	 */
	@Override
	public List<CategoryProductResult> getProCategoryListByCode(ProductCategoryRel productCategoryRelSearch) {
		return proCategoryMapper.getProCategoryListByCode(productCategoryRelSearch);
	}

	/**
	 * 根据类目ID查询子类目
	 * 
	 * @param pid
	 * @return
	 */
	@Override
	public List<ProCategory> asyncLoadTree(Integer pid) {
		return proCategoryMapper.asyncLoadTree(pid);
	}

	/**
	 * 根据分类Code查询(前台使用)
	 * 
	 * @return
	 */
	@Override
	public List<CategoryProductResult> getProCategoryListByCodeForWeb(ProductCategoryRel productCategoryRelSearch) {
		return proCategoryMapper.getProCategoryListByCodeForWeb(productCategoryRelSearch);
	}

	/**
	 * 修改分类.如果code变动过，则需要修改分类下所有子节点的code
	 * 
	 * @return
	 */
	@Override
	public int updateRecursion(ProCategory proCategory) {
		ProCategory oldProCategory = proCategoryMapper.selectByPrimaryKey(proCategory.getId());
		if (!oldProCategory.getCode().equals(proCategory.getCode())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("oldCode", "." + oldProCategory.getCode() + ".");
			params.put("newCode", "." + proCategory.getCode() + ".");
			proCategoryMapper.recursionUpdate(params);
		}
		return proCategoryMapper.updateByPrimaryKeySelective(proCategory);
	}

	@Override
	public int getProCategoryListCountByCode(ProductCategoryRel productCategoryRelSearch) {
		return proCategoryMapper.getProCategoryListCountByCode(productCategoryRelSearch);
	}

	@Override
	public ProCategory getAllCategory() {
		List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
		ProCategory category = null;
		ProCategory pc = new ProCategory();
		pc.setState("0");
		pc.setOrder(" ordering ");

		List<ProCategory> categoryAllList = null;

		if (Utils.enableRedisCache()) {
			categoryAllList = ProCategoryCacher.getAllList(pc);
		} else {
			categoryAllList = this.getListV2(pc);
		}

		if (categoryAllList != null && categoryAllList.size() >= 0) {
			for (ProCategory ppc : categoryAllList) {
				if (ppc.getId().intValue() == 1) {
					category = ppc;
					break;
				}
			}
		}
		categoryList = recursionCategory2(category, categoryAllList);
		category.setChildrenNodes(categoryList);
		return category;
	}

	public List<SearchProCategorySmall> recursionCategory2(ProCategory category, List<ProCategory> categoryAllList) {
		List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
		ProCategory procate = new ProCategory();
		procate.setPid(category.getId());
		procate.setLongCode(category.getLongCode());

		List<ProCategory> list = new ArrayList<ProCategory>();
		if (categoryAllList != null && categoryAllList.size() > 0) {
			for (ProCategory pc : categoryAllList) {
				if (pc.getPid().intValue() == procate.getPid().intValue()
						&& pc.getLongCode().indexOf(category.getLongCode()) != -1) {
					list.add(pc);
				}
			}
		} else {

			// list = this.getList(procate);
			if (Utils.enableRedisCache()) {
				list = ProCategoryCacher.getAllList(procate);
			} else {
				list = this.getList(procate);
			}
		}
		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
		}
		SearchProCategorySmall newCategory = null;
		for (ProCategory childrenNode : list) {
			newCategory = new SearchProCategorySmall();
			newCategory.setAa(childrenNode.getId());
			newCategory.setCc(childrenNode.getPid());
			newCategory.setDd(childrenNode.getName());
			newCategory.setBb(childrenNode.getCode());
			newCategory.setEe(childrenNode.getOrdering());
			newCategory.setFf(recursionCategory2(childrenNode, categoryAllList));
			newCategory.setGg(childrenNode.getNameFirstLetter());
			childrenNodes.add(newCategory);
		}
		category.setChildrenNodes(childrenNodes);

		return childrenNodes;
	}

	/**
	 * 通过code查找ProCategory
	 * 
	 * @author huangsongbo
	 * @param categoryCode
	 * @return
	 */
	public ProCategory findOneByCode(String code) {
		return proCategoryMapper.findOneByCode(code);
	}

	/**
	 * 递归查询分类
	 * 
	 * @param category
	 * @return
	 */
	private List<SearchProCategorySmall> recursionCategory(ProCategory category) {
		List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
		List<ProCategory> list = null;

		if (Utils.enableRedisCache()) {
			ProCategory pc = new ProCategory();
			pc.setPid(category.getId());
			pc.setLongCode(category.getLongCode());
			list = ProCategoryCacher.getAllList(pc);
		} else {
			ProCategorySearch search = new ProCategorySearch();
			search.setPid(category.getId());
			search.setLongCode(category.getLongCode());
			list = proCategoryService.getList(search);
		}

		if (list != null && list.size() > 0) {
			if (childrenNodes == null) {
				childrenNodes = new ArrayList<SearchProCategorySmall>();
			}
			SearchProCategorySmall newCategory = null;
			SearchProCategorySmall newCategory_ = null;
			for (ProCategory childrenNode : list) {
				if (ELSE.equals(childrenNode.getName())) {
					newCategory_ = new SearchProCategorySmall();
					newCategory_.setAa(childrenNode.getId());
					newCategory_.setCc(childrenNode.getPid());
					newCategory_.setBb(childrenNode.getCode());
					newCategory_.setDd(childrenNode.getName());
					newCategory_.setFf(recursionCategory(childrenNode));
				} else {
					newCategory = new SearchProCategorySmall();
					newCategory.setAa(childrenNode.getId());
					newCategory.setCc(childrenNode.getPid());
					newCategory.setBb(childrenNode.getCode());
					newCategory.setDd(childrenNode.getName());
					newCategory.setFf(recursionCategory(childrenNode));
					childrenNodes.add(newCategory);
				}
			}
			if (newCategory_ != null) {
				childrenNodes.add(newCategory_);
			}
			category.setChildrenNodes(childrenNodes);
		}
		return childrenNodes;
	}

	/**
	 * 分类查询接口供移动端调用
	 * 
	 * @return
	 */
	public Object searchProCategory(String type, String cid, String msgId) {
		long startTime = System.currentTimeMillis();
		String msg = "";
		if (StringUtils.isBlank(msgId)) {
			msg = MSGID_NOT_NULL;
			return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
		}
		if (StringUtils.isBlank(type)) {
			msg = TYPE_NOT_NULL;
			return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
		}
		List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
		if (StringUtils.isNotBlank(type)) {
			ProCategory category = null;
			int total = 0;
			// 查询所有类目
			if ("A".equals(type)) {
				if (Utils.enableRedisCache()) {
					category = (ProCategory) ProCategoryCacher.getAllList();
				} else {
					category = proCategoryService.getAllCategory();
				}
			} else {
				if (StringUtils.isNotBlank(cid)) {
					// 查询pid等于cid的所有产品类目
					if ("P".equals(type)) {
						if (Utils.enableRedisCache()) {
							category = ProCategoryCacher.get(Integer.valueOf(cid));
						} else {
							category = proCategoryService.get(Integer.valueOf(cid));
						}
						categoryList = recursionCategory(category);
						MyCompartor compartor = new MyCompartor();
						Collections.sort(categoryList, compartor);
						category.setChildrenNodes(categoryList);
						// 查询ID等cid的类目
					} else if ("O".equals(type)) {
						if (Utils.enableRedisCache()) {
							category = ProCategoryCacher.get(Integer.valueOf(cid));
						} else {
							category = proCategoryService.get(Integer.valueOf(cid));
						}
					}
				} else {
					msg = TYPE_IS + type + MUST_BE_INTRODUCTION_CID;
					return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
				}
			}
			if (category != null) {
				return resultList(msgId, startTime, category, total);
			}
		} else {
			msg = TYPE_NOT_NULL;
			return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
		}
		return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
	}

	private Object resultList(String msgId, long startTime, ProCategory category, int total) {
		SearchProCategorySmall categorySmall;
		categorySmall = new SearchProCategorySmall();
		categorySmall.setGg(category.getNameFirstLetter());
		categorySmall.setAa(category.getId());
		categorySmall.setBb(category.getCode());
		categorySmall.setCc(category.getPid());
		categorySmall.setDd(category.getName());
		categorySmall.setFf(category.getChildrenNodes() == null ? new ArrayList<SearchProCategorySmall>() : category.getChildrenNodes());
		List<SearchProCategorySmall> resultList = new ArrayList<SearchProCategorySmall>();
		resultList.add(categorySmall);
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		logger.info("searchProCategory executeTime : " + executeTime + "ms");
		MyCompartor compartor = new MyCompartor();
		Collections.sort(resultList, compartor);
		return new ResponseEnvelope<SearchProCategorySmall>(total, resultList, msgId);
	}

	// 1、排序升序 2、name拼音首字排序
	class MyCompartor implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			ProCategory sdto1 = (ProCategory) o1;
			ProCategory sdto2 = (ProCategory) o2;
			int flag = (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
					.compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
			if (flag != 0) {
				return (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
						.compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
			} else {
				Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
				return com.compare(sdto1.getName(), sdto2.getName());
			}
		}
	}
}
