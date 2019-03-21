package com.nork.product.service2.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.TempletStatuCode;
import com.nork.design.service.DesignTempletService;
import com.nork.home.model.SpaceCommonStatuCode;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.dao.PrepProductSearchInfoMapper;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.GroupProductStateCode;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.ProductStatuCode;
import com.nork.product.model.StructureProductStatusCode;
import com.nork.product.model.search.PrepProductSearchInfoSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.PrepProductPropsInfoService;
import com.nork.product.service.StructureProductService;
import com.nork.product.service2.PrepProductSearchInfoService;

/**
 * @Title: PrepProductSearchInfoServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品模块-预处理表(产品搜索)ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0
 */
public class PrepProductSearchInfoServiceImpl implements PrepProductSearchInfoService {

	private static Logger logger = Logger.getLogger(PrepProductSearchInfoServiceImpl.class);
	
	private static final String PRODUCT_IS_UPDATE =  "------还有产品正在更新数据";
	private static final String ERROR_MSG =  "------错误信息:";
	private static final String ALL_DATA_ERROR_ROLLBACK =  "------预处理全部数据出错,准备回滚空间/样板房/产品/结构/组合状态(发布中->已上架)";
	private static final String ROLLBACK_FINISH =  "------回滚空间/样板房/产品/结构/组合状态(发布中->已上架)完毕";
	private static final String INCREMENT_UPDATE_SUCCESS =  "------增量更新success";
	private static final String NO_UPDATE_PRODUCT_DATA =  "------没有需要更新的产品数据(已上架的产品数量为0)";
	private static final String BEGIN_HANDLE_TABLE_DATA =  "------开始预处理主表数据";
	private static final String HANDLE_TABLE_DATA_OVER =  "------预处理主表数据结束";
	private static final String SELECT_OVER_BEGIN_INSERT =  "------预处理主表数据(prep_product_search_info)查询完毕,准备insert";
	private static final String INSERT_FINISH =  "------预处理主表数据(prep_product_search_info)insert完毕";
	private static final String DATA =  "条数据";
	private static final String INSERT =  "已插入";
	private static final String TOTALLY =  "共";
	private static final String QUERY =  "------已查询:";
	private static final String RECORD_TIME_CONSUMING =  "条记录,阶段耗时:";
	@Autowired
	private PrepProductSearchInfoMapper prepProductSearchInfoMapper;

	@Autowired
	private SpaceCommonService spaceCommonService;

	@Autowired
	private DesignTempletService designTempletService;

	@Autowired
	private PrepProductPropsInfoService prepProductPropsInfoService;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private StructureProductService structureProductService;

	@Autowired
	private GroupProductService groupProductService;

	/**
	 * 新增数据
	 *
	 * @param prepProductSearchInfo
	 * @return int
	 */
	@Override
	public int add(PrepProductSearchInfo prepProductSearchInfo) {
		prepProductSearchInfoMapper.insertSelective(prepProductSearchInfo);
		return prepProductSearchInfo.getId();
	}

	/**
	 * 更新数据
	 *
	 * @param prepProductSearchInfo
	 * @return int
	 */
	@Override
	public int update(PrepProductSearchInfo prepProductSearchInfo) {
		return prepProductSearchInfoMapper.updateByPrimaryKeySelective(prepProductSearchInfo);
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return prepProductSearchInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 *
	 * @param id
	 * @return PrepProductSearchInfo
	 */
	@Override
	public PrepProductSearchInfo get(Integer id,int userId) {
		return prepProductSearchInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param prepProductSearchInfo
	 * @return List<PrepProductSearchInfo>
	 */
	@Override
	public List<PrepProductSearchInfo> getList(PrepProductSearchInfo prepProductSearchInfo,int userId) {
		return prepProductSearchInfoMapper.selectList(prepProductSearchInfo);
	}

	/**
	 * 获取数据数量
	 *
	 * @param prepProductSearchInfo
	 * @return int
	 */
	@Override
	public int getCount(PrepProductSearchInfoSearch prepProductSearchInfoSearch,int userId) {
		return prepProductSearchInfoMapper.selectCount(prepProductSearchInfoSearch);
	}

	/**
	 * 分页获取数据
	 *
	 * @param prepProductSearchInfo
	 * @return List<PrepProductSearchInfo>
	 */
	@Override
	public List<PrepProductSearchInfo> getPaginatedList(PrepProductSearchInfoSearch prepProductSearchInfoSearch,int userId) {
		return prepProductSearchInfoMapper.selectPaginatedList(prepProductSearchInfoSearch);
	}

	public List<Integer> getProductIdList(PrepProductSearchInfo prepProductSearchInfo,int userId) {
		return prepProductSearchInfoMapper.getProductIdList(prepProductSearchInfo);
	}

	public Integer getCount(PrepProductSearchInfo prepProductSearchInfo,int userId) {
		return prepProductSearchInfoMapper.getCount(prepProductSearchInfo);
	}

	public List<CategoryProductResult> getProductIdListV2(PrepProductSearchInfo prepProductSearchInfo,int userId) {
		return prepProductSearchInfoMapper.getProductIdListV2(prepProductSearchInfo);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void noTranUpdatePutawayProductAndSetStatus() {
		// 如果发现还有正在更新的数据 ->停止更新 ->start
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.RELEASEING);
		List<Integer> productIdList = baseProductService.findIdListByStatus(productStatusList,0);
		if (productIdList != null && productIdList.size() > 0) {
			return;
		}
		// 将空间/样板房/产品/结构/组合数据由已上架->发布中 ->start
		spaceCommonService.updateStatus(SpaceCommonStatuCode.HAS_BEEN_PUTAWAY, SpaceCommonStatuCode.RELEASEING);
		designTempletService.updateStatus(TempletStatuCode.HAS_BEEN_PUTAWAY, TempletStatuCode.RELEASEING);
		baseProductService.updateStatus(ProductStatuCode.HAS_BEEN_PUTAWAY, ProductStatuCode.RELEASEING);
		structureProductService.updateStatus(StructureProductStatusCode.HAS_BEEN_PUTAWAY,
				StructureProductStatusCode.RELEASEING);
		groupProductService.updateStatus(GroupProductStateCode.HAS_BEEN_PUTAWAY, GroupProductStateCode.RELEASEING);

		try {
			// 增量处理发布中(上架)的产品 ->start
			this.noTranUpdatePutawayProduct();
			// 增量处理发布中(上架)的产品 ->end
		} catch (Exception e) {
			spaceCommonService.updateStatus(SpaceCommonStatuCode.RELEASEING, SpaceCommonStatuCode.HAS_BEEN_PUTAWAY);
			designTempletService.updateStatus(TempletStatuCode.RELEASEING, TempletStatuCode.HAS_BEEN_PUTAWAY);
			baseProductService.updateStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_PUTAWAY);

			// 即使增量预处理失败,也要把组合/结构设置成已发布->start
			structureProductService.updateStatus(StructureProductStatusCode.RELEASEING,
					StructureProductStatusCode.HAS_BEEN_RELEASE);
			groupProductService.updateStatus(GroupProductStateCode.RELEASEING, GroupProductStateCode.HAS_BEEN_RELEASE);
			// 即使增量预处理失败,也要把组合/结构设置成已发布->end

			e.printStackTrace();
		}

		// 将空间/样板房/产品/结构/组合数据由发布中->已发布 ->start
		spaceCommonService.updateStatus(SpaceCommonStatuCode.RELEASEING, SpaceCommonStatuCode.HAS_BEEN_RELEASE);
		designTempletService.updateStatus(TempletStatuCode.RELEASEING, TempletStatuCode.HAS_BEEN_RELEASE);
		baseProductService.updateStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_RELEASE);
		structureProductService.updateStatus(StructureProductStatusCode.RELEASEING,
				StructureProductStatusCode.HAS_BEEN_RELEASE);
		groupProductService.updateStatus(GroupProductStateCode.RELEASEING, GroupProductStateCode.HAS_BEEN_RELEASE);
		logger.warn(INCREMENT_UPDATE_SUCCESS);
		// 将空间/样板房/产品/结构/组合数据由发布中->已发布 ->end
	}

	/**
	 * 增量预处理产品
	 */
	private void noTranUpdatePutawayProduct() {
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.RELEASEING);

		// 如果该状态下的产品数据count=0,则return(无需更新) ->start
		int count = baseProductService.getcountByStatusList(productStatusList);
		if (count == 0) {
			logger.warn(NO_UPDATE_PRODUCT_DATA);
			return;
		}
		// 如果该状态下的产品数据count=0,则return(无需更新) ->end

		// 更新预处理主表 ->start
		this.noTranUpdateByProductStatusList(productStatusList);
		// 更新预处理主表 ->end

		// 更新预处理属性表 ->start
		prepProductPropsInfoService.noTranUpdateByProductStatusList(productStatusList);
		// 更新预处理属性表 ->end

	}

	private void noTranUpdateByProductStatusList(List<Integer> productStatusList) {

		logger.warn(BEGIN_HANDLE_TABLE_DATA);

		// 删除要更新的产品对应的预处理旧数据(需要把新的预处理数据更新上去) ->start
		this.deleteByProductStatusList(productStatusList);
		// 删除要更新的产品对应的预处理旧数据(需要把新的预处理数据更新上去) ->end

		// 更新数据(添加) ->start
		this.preprocess(null, productStatusList);
		// 更新数据(添加) ->end

		logger.warn(HANDLE_TABLE_DATA_OVER);

	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void deleteByProductStatusList(List<Integer> productStatusList) {
		if (productStatusList == null || productStatusList.size() == 0) {
			return;
		}
		prepProductSearchInfoMapper.deleteByProductStatusList(productStatusList);
	}

	@Override
	public void updateByProductIdList(Set<Integer> updateMainProductIdList) {
		// 删除以前数据(根据产品idList删除)
		if (updateMainProductIdList == null || updateMainProductIdList.size() == 0) {
			return;
		}
		this.deleteByProductIdList(updateMainProductIdList);
		// 更新数据(添加)
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.RELEASEING);
		this.preprocess(new ArrayList<Integer>(updateMainProductIdList), productStatusList);
	}

	/**
	 * 通过productIdList删除数据
	 * 
	 * @author huangsongbo
	 * @param updateMainProductIdList
	 */
	@Override
	public void deleteByProductIdList(Set<Integer> updateMainProductIdList) {
		prepProductSearchInfoMapper.deleteByProductIdList(updateMainProductIdList);
	}

	/**
	 * 预处理数据,根据产品状态/产品idList决定处理哪些产品
	 * 
	 * @author huangsongbo
	 * @param productIdList
	 *            产品idList
	 * @param productStatusList
	 *            产品状态
	 */
	public void preprocess(List<Integer> productIdList, List<Integer> productStatusList) {

		// 查询数据->start
		Integer start = 0;
		Integer limit = 1000;
		List<PrepProductSearchInfo> totalList = new ArrayList<>();
		List<PrepProductSearchInfo> list = new ArrayList<>();
		do {
			Long startTime = new Date().getTime();
			list = prepProductSearchInfoMapper.getPPSIByProductIdList(productIdList, productStatusList, start, limit);
			start += limit;
			totalList.addAll(list);
			Long endTime = new Date().getTime();
			logger.warn(QUERY + totalList.size() + RECORD_TIME_CONSUMING + (endTime - startTime));
		} while (list != null && list.size() > 0);
		logger.warn(SELECT_OVER_BEGIN_INSERT);
		// 查询数据->end

		// 向预处理表插入数据->start
		this.preprocessSave(totalList);
		// 向预处理表插入数据->end

		logger.warn(INSERT_FINISH);

	}

	private void preprocessSave(List<PrepProductSearchInfo> totalList) {
		Integer size = totalList.size();
		Integer start = 0;
		Integer limit = 1000;
		List<PrepProductSearchInfo> list = new ArrayList<PrepProductSearchInfo>();
		do {
			list = totalList.subList(start, (start += limit) > size ? size : start);
			this.batchSave(list);
			list = new ArrayList<PrepProductSearchInfo>();
			//System.out.println(INSERT + start + DATA);
			//System.out.println(TOTALLY + totalList.size() + DATA);
		} while (start < size);
	}

	private void batchSave(List<PrepProductSearchInfo> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		prepProductSearchInfoMapper.batchSave(list);
	}

}
