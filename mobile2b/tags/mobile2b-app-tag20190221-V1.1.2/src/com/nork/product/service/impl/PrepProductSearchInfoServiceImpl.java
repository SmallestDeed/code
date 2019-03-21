package com.nork.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.design.model.TempletStatuCode;
import com.nork.design.service.DesignTempletService;
import com.nork.home.model.SpaceCommonStatuCode;
import com.nork.home.service.SpaceCommonService;
import com.nork.job.PrepareProductSearchMainInfoJob;
import com.nork.job.SaveRenderPicJob;
import com.nork.product.dao.PrepProductSearchInfoMapper;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.GroupProductStateCode;
import com.nork.product.model.PrepProductPropsInfo;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.ProductStatuCode;
import com.nork.product.model.StructureProductStatusCode;
import com.nork.product.model.search.PrepProductSearchInfoSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.product.service.PrepProductPropsInfoService;
import com.nork.product.service.PrepProductSearchInfoService;
import com.nork.product.service.StructureProductService;
import com.nork.system.service.SysResLevelCfgService;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolConfig;
import com.nork.threadpool.ThreadPoolManager;

/**   
 * @Title: PrepProductSearchInfoServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-预处理表(产品搜索)ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0   
 */
@Service("prepProductSearchInfoService")
public class PrepProductSearchInfoServiceImpl implements PrepProductSearchInfoService {

	private static Logger logger = Logger.getLogger(PrepProductSearchInfoServiceImpl.class);
	@Autowired
	private SysResLevelCfgService sysResLevelCfgService;
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
	
	@Autowired
	private ThreadPoolManager uploadFileThreadPoolManager;

	/**
	 * 新增数据
	 *
	 * @param prepProductSearchInfo
	 * @return  int 
	 */
	@Override
	public int add(PrepProductSearchInfo prepProductSearchInfo) {
		prepProductSearchInfoMapper.insertSelective(prepProductSearchInfo);
		return prepProductSearchInfo.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param prepProductSearchInfo
	 * @return  int 
	 */
	@Override
	public int update(PrepProductSearchInfo prepProductSearchInfo) {
		return prepProductSearchInfoMapper
				.updateByPrimaryKeySelective(prepProductSearchInfo);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return prepProductSearchInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  PrepProductSearchInfo 
	 */
	@Override
	public PrepProductSearchInfo get(Integer id,int userId) {
		return prepProductSearchInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  prepProductSearchInfo
	 * @return   List<PrepProductSearchInfo>
	 */
	@Override
	public List<PrepProductSearchInfo> getList(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, categoryCode);//如果是空间，他返回的是百分比，需要乘总数
        int valNum = Integer.valueOf(val);
        prepProductSearchInfo.setLevelLimitCount(valNum);
	    return prepProductSearchInfoMapper.selectList(prepProductSearchInfo);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  prepProductSearchInfo
	 * @return   int
	 */
	@Override
	public int getCount(PrepProductSearchInfoSearch prepProductSearchInfoSearch,int userId,String categoryCode){
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId,categoryCode);//如果是空间，他返回的是百分比，需要乘总数
        int valNum = Integer.valueOf(val);
        prepProductSearchInfoSearch.setLevelLimitCount(valNum);
		return  prepProductSearchInfoMapper.selectCount(prepProductSearchInfoSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  prepProductSearchInfo
	 * @return   List<PrepProductSearchInfo>
	 */
	@Override
	public List<PrepProductSearchInfo> getPaginatedList(
			PrepProductSearchInfoSearch prepProductSearchInfoSearch,int userId,String categoryCode) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, categoryCode);//如果是空间，他返回的是百分比，需要乘总数
        int valNum = Integer.valueOf(val);
        prepProductSearchInfoSearch.setLevelLimitCount(valNum);
        
		return prepProductSearchInfoMapper.selectPaginatedList(prepProductSearchInfoSearch);
	}

	public List<Integer> getProductIdList(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId,categoryCode);//如果是空间，他返回的是百分比，需要乘总数
	    int valNum = Integer.valueOf(val);
	    prepProductSearchInfo.setLevelLimitCount(valNum);
	    
		return prepProductSearchInfoMapper.getProductIdList(prepProductSearchInfo);
	}

	public Integer getCount(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode) {
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, categoryCode);//如果是空间，他返回的是百分比，需要乘总数
        int valNum = Integer.valueOf(val);
        prepProductSearchInfo.setLevelLimitCount(valNum);
		return prepProductSearchInfoMapper.getCount(prepProductSearchInfo);
	}

	public List<CategoryProductResult> getProductIdListV2(PrepProductSearchInfo prepProductSearchInfo,int userId,String categoryCode) {
	    
	    String val = sysResLevelCfgService.getLimitResNumByUserId(userId, categoryCode);//如果是空间，他返回的是百分比，需要乘总数
        int valNum = Integer.valueOf(val);
        prepProductSearchInfo.setLevelLimitCount(valNum);
		return prepProductSearchInfoMapper.getProductIdListV2(prepProductSearchInfo);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void noTranUpdatePutawayProductAndSetStatus() {
		// 如果发现还有正在更新的数据 ->停止更新 ->start
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.RELEASEING);
		List<Integer> productIdList = baseProductService.findIdListByStatus(productStatusList,0);
		if(productIdList != null && productIdList.size() > 0){
			logger.error("------还有产品正在更新数据");
			return;
		}
		logger.error("------开始更新数据-----");
		// 将空间/样板房/产品/结构/组合数据由已上架->发布中 ->start
		spaceCommonService.updateStatus(SpaceCommonStatuCode.HAS_BEEN_PUTAWAY, SpaceCommonStatuCode.RELEASEING);
		designTempletService.updateStatus(TempletStatuCode.HAS_BEEN_PUTAWAY, TempletStatuCode.RELEASEING);
		baseProductService.updateStatus(ProductStatuCode.HAS_BEEN_PUTAWAY, ProductStatuCode.RELEASEING);
		structureProductService.updateStatus(StructureProductStatusCode.HAS_BEEN_PUTAWAY, StructureProductStatusCode.RELEASEING);
		groupProductService.updateStatus(GroupProductStateCode.HAS_BEEN_PUTAWAY, GroupProductStateCode.RELEASEING);
		// 将空间/样板房/产品/结构/组合数据由已上架->发布中 ->end

//		int limit = 2000;
//		productStatusList = new ArrayList<>();
//		productStatusList.add(ProductStatuCode.HAS_BEEN_PUTAWAY);
		try{
			//批量执行产品预处理数据
//			do{
//				productIdList = new ArrayList<>();
//				productIdList = baseProductService.findIdListByStatus(productStatusList,limit);
//				if (productIdList != null && productIdList.size() > 0) {
//					logger.error("------productIdList:" + productIdList.size());
//					baseProductService.updateProductStatus(ProductStatuCode.HAS_BEEN_PUTAWAY, ProductStatuCode.RELEASEING, productIdList);
//				}
//				this.noTranUpdatePutawayProduct();
//				baseProductService.updateProductStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_PUTAWAY,productIdList);
//			}while(limit == productIdList.size());

			//this.noTranUpdatePutawayProduct2();
			this.noTranUpdatePutawayProduct();

		}catch(Exception e){
			logger.error("------错误信息:" + e);
			spaceCommonService.updateStatus(SpaceCommonStatuCode.RELEASEING, SpaceCommonStatuCode.HAS_BEEN_PUTAWAY);
			designTempletService.updateStatus(TempletStatuCode.RELEASEING, TempletStatuCode.HAS_BEEN_PUTAWAY);
			baseProductService.updateStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_PUTAWAY);
			// 即使增量预处理失败,也要把组合/结构设置成已发布->start
			structureProductService.updateStatus(StructureProductStatusCode.RELEASEING, StructureProductStatusCode.HAS_BEEN_RELEASE);
			groupProductService.updateStatus(GroupProductStateCode.RELEASEING, GroupProductStateCode.HAS_BEEN_RELEASE);
			// 即使增量预处理失败,也要把组合/结构设置成已发布->end
			
			logger.error("------回滚空间/样板房/产品/结构/组合状态(发布中->已上架)完毕");
			e.printStackTrace();
		}
		
		// 将空间/样板房/产品/结构/组合数据由发布中->已发布 ->start
		spaceCommonService.updateStatus(SpaceCommonStatuCode.RELEASEING, SpaceCommonStatuCode.HAS_BEEN_RELEASE);
		designTempletService.updateStatus(TempletStatuCode.RELEASEING, TempletStatuCode.HAS_BEEN_RELEASE);
		baseProductService.updateStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_RELEASE);
		//由于多线程只更新产品，不更新白膜，所以这里只针对白膜状态变更
		//baseProductService.updateBmProductStatus(ProductStatuCode.RELEASEING, ProductStatuCode.HAS_BEEN_RELEASE);
		structureProductService.updateStatus(StructureProductStatusCode.RELEASEING, StructureProductStatusCode.HAS_BEEN_RELEASE);
		groupProductService.updateStatus(GroupProductStateCode.RELEASEING, GroupProductStateCode.HAS_BEEN_RELEASE);
		logger.warn("------增量更新success");
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
		logger.error("------需更新产品数据:" + count);
		if(count == 0){
			logger.warn("------没有需要更新的产品数据(已上架的产品数量为0)");
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
	
	private void noTranUpdatePutawayProduct2() {
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.RELEASEING);
		
		// 如果该状态下的产品数据count=0,则return(无需更新) ->start
		int count = baseProductService.getcountByStatusList(productStatusList);
		if(count == 0){
			logger.warn("------没有需要更新的产品数据(已上架的产品数量为0)");
			return;
		}
		logger.error("产品数据数量：" + count);
		List<PrepProductSearchInfo> allPrepSearchList = this.getAllPrepSearchList(null, productStatusList);
		List<PrepProductPropsInfo> allPrepProductPropsList = prepProductPropsInfoService.getAllPrepProductPropsList(productStatusList);
		logger.error("预处理数据数量：" + allPrepSearchList.size());
		int planJobOfCount = 8;
		int limitOfsearch = allPrepSearchList.size()/planJobOfCount;
		int limitOfProps = allPrepProductPropsList.size()/planJobOfCount;
		int sizeOfPrepSearchList = allPrepSearchList.size();
		int sizeOfPropsList = allPrepProductPropsList.size();
		int jobOfCount = 8;
		if((sizeOfPrepSearchList % planJobOfCount >0)  || (sizeOfPropsList % planJobOfCount >0) ) {
			jobOfCount = 9;
		}
		List <List<PrepProductSearchInfo>>JobListOfsearchList = SystemCommonUtil.splitListOfSearchList(allPrepSearchList, limitOfsearch);
		List <List<PrepProductPropsInfo>>JobListOfPropsList = SystemCommonUtil.splitListOfPropsList(allPrepProductPropsList, limitOfProps);
		
		
		ThreadPool threadPool = uploadFileThreadPoolManager.getThreadPool();// 取得线程池实列
		ExecutorService executorService = threadPool.getThreadPool(ThreadPoolConfig.THREAD_POOL_NAME);
		ThreadPoolExecutor ex = (ThreadPoolExecutor) executorService;
		
		logger.error("SearchList" + sizeOfPrepSearchList + "props size=" + sizeOfPropsList + "taskCount" + ex.getActiveCount());
		
		for(int i = 0; i < jobOfCount; i ++) {
			List <PrepProductSearchInfo>searchList = new ArrayList<PrepProductSearchInfo>();
			List <PrepProductPropsInfo>propsList = new ArrayList<PrepProductPropsInfo>();
			if(i < JobListOfsearchList.size()) {
				searchList = JobListOfsearchList.get(i);
			}
			
			if(i < JobListOfPropsList.size()) {
				propsList = JobListOfPropsList.get(i);
			}
			String jobName = "preparJob_" + i;
			logger.error("Create the new job of =>" + jobName);
			PrepareProductSearchMainInfoJob job = new PrepareProductSearchMainInfoJob(this, searchList , prepProductPropsInfoService, propsList,jobName,baseProductService);
			Future<Boolean> future = threadPool.submit(job);
		}
	
		
//		
//		// 如果该状态下的产品数据count=0,则return(无需更新) ->end
//		
//		// 更新预处理主表 ->start
//		this.noTranUpdateByProductStatusList(productStatusList);
//		// 更新预处理主表 ->end
//		
//		// 更新预处理属性表 ->start
//		prepProductPropsInfoService.noTranUpdateByProductStatusList(productStatusList);
		// 更新预处理属性表 ->end
		
	}
	
	
	
	private void noTranUpdateByProductStatusList(List<Integer> productStatusList) {
		
		logger.warn("------开始预处理主表数据");
		
		// 删除要更新的产品对应的预处理旧数据(需要把新的预处理数据更新上去) ->start
		this.deleteByProductStatusList(productStatusList);
		// 删除要更新的产品对应的预处理旧数据(需要把新的预处理数据更新上去) ->end
		
		// 更新数据(添加) ->start
		this.preprocess(null, productStatusList);
		// 更新数据(添加) ->end
		
		logger.warn("------预处理主表数据结束");
		
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void deleteByProductStatusList(List<Integer> productStatusList) {
		if(productStatusList == null || productStatusList.size() == 0){
			return;
		}
		prepProductSearchInfoMapper.deleteByProductStatusList(productStatusList);
	}

	@Override
	public void updateByProductIdList(Set<Integer> updateMainProductIdList) {
		// 删除以前数据(根据产品idList删除)
		if(updateMainProductIdList == null || updateMainProductIdList.size() == 0){
			return;
		}
		this.deleteByProductIdList(updateMainProductIdList);
		// 更新数据(添加)
		/*String productStatusStr = Utils.getValueByFileKey(AppProperties.APP, AppProperties.PREP_PRODUCTSTATUS_FILEKEY, "1,2");
		List<Integer> productStatusList = Utils.getIntegerListFromStringList(productStatusStr);*/
		List<Integer> productStatusList = new ArrayList<Integer>();
		productStatusList.add(ProductStatuCode.RELEASEING);
		this.preprocess(new ArrayList<Integer>(updateMainProductIdList), productStatusList);
	}
	
	/**
	 * 通过productIdList删除数据
	 * @author huangsongbo
	 * @param updateMainProductIdList
	 */
	@Override
	public void deleteByProductIdList(Set<Integer> updateMainProductIdList) {
		prepProductSearchInfoMapper.deleteByProductIdList(updateMainProductIdList);
	}
	
	/**
	 * 预处理数据,根据产品状态/产品idList决定处理哪些产品
	 * @author huangsongbo
	 * @param productIdList 产品idList
	 * @param productStatusList 产品状态
	 */
	public void preprocess(List<Integer> productIdList, List<Integer> productStatusList) {
		
		// 查询数据->start
		Integer start = 0;
		Integer limit = 1000;
		List<PrepProductSearchInfo> totalList = new ArrayList<>();
		List<PrepProductSearchInfo> list = new ArrayList<>();
		do{
			Long startTime = new Date().getTime();
			list = prepProductSearchInfoMapper.getPPSIByProductIdList(productIdList, productStatusList, start, limit);
			start += limit;
			totalList.addAll(list);
			Long endTime = new Date().getTime();
			logger.warn("------已查询:"+totalList.size()+"条记录,阶段耗时:"+(endTime-startTime));
		}while(list !=null && list.size() >0);
		logger.warn("------预处理主表数据(prep_product_search_info)查询完毕,准备insert");
		// 查询数据->end
		
		// 向预处理表插入数据->start
		this.preprocessSave(totalList);
		// 向预处理表插入数据->end
		
		logger.warn("------预处理主表数据(prep_product_search_info)insert完毕");
		
	}
	
	private List<PrepProductSearchInfo> getAllPrepSearchList(List<Integer> productIdList, List<Integer> productStatusList) {
//		Integer start = 0;
//		Integer limit = 1000;
//		List<PrepProductSearchInfo> totalList = new ArrayList<>();
//		List<PrepProductSearchInfo> list = new ArrayList<>();
//		do{
//			Long startTime = new Date().getTime();
//			list = prepProductSearchInfoMapper.getPPSIByProductIdList(productIdList, productStatusList, start, limit);
//			start += limit;
//			totalList.addAll(list);
//			Long endTime = new Date().getTime();
//			logger.error("------已查询:"+totalList.size()+"条记录,阶段耗时:"+(endTime-startTime));
//		}while(list !=null && list.size() >0);
		List<PrepProductSearchInfo> totalList = prepProductSearchInfoMapper.getPPSIByProductIdList(productIdList, productStatusList, 0, Integer.MAX_VALUE);
		return totalList;
	}
	
	
	private void preprocessSave(List<PrepProductSearchInfo> totalList) {
		Integer size = totalList.size();
		Integer start = 0;
		Integer limit = 1000;
		List<PrepProductSearchInfo> list = new ArrayList<PrepProductSearchInfo>();
		do{
			list = totalList.subList(start, (start += limit) > size ? size : start);
			this.batchSave(list);
			list = new ArrayList<PrepProductSearchInfo>();
			//System.out.println("已插入" + start + "条数据");
			//System.out.println("共" + totalList.size() + "条数据");
		}while(start < size);
	}
	
	private void batchSave(List<PrepProductSearchInfo> list) {
		if(list == null || list.size() == 0){
			return;
		}
		prepProductSearchInfoMapper.batchSave(list);
	}

	@Override
	public int batchSavePrepSearchList(List<PrepProductSearchInfo> list) {
		// TODO Auto-generated method stub
		if(list == null || list.size() == 0){
			return 0;
		}
		logger.error("call batch save==" + list.size());
		prepProductSearchInfoMapper.batchSave(list);
		logger.error("end call batch save==" + list.size());
		return list.size();
	}

}
