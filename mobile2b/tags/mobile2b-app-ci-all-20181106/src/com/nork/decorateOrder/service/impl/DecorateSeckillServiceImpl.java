package com.nork.decorateOrder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.cache.CacheManager;
import com.nork.common.exception.BizException;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.decorateOrder.config.DecorateOrderConfig;
import com.nork.decorateOrder.constant.DecorateOrderConstants;
import com.nork.decorateOrder.constant.DecorateSeckillConstants;
import com.nork.decorateOrder.dao.DecorateSeckillMapper;
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.DecorateSeckillOrder;
import com.nork.decorateOrder.model.ProprietorInfo;
import com.nork.decorateOrder.model.dto.DecorateSeckillDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetListDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetListParamDTO;
import com.nork.decorateOrder.model.dto.SeckillRecordFromRedisDTO;
import com.nork.decorateOrder.model.search.DecorateOrderSearch;
import com.nork.decorateOrder.model.search.DecorateSeckillSearch;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePriceService;
import com.nork.decorateOrder.service.DecorateSeckillOrderService;
import com.nork.decorateOrder.service.DecorateSeckillService;
import com.nork.decorateOrder.service.ProprietorInfoService;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.SysUser;

@Service("decorateSeckillService")
public class DecorateSeckillServiceImpl implements DecorateSeckillService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DecorateSeckillServiceImpl.class);
	
	private final static String LOGPREFIX = "[限时快抢模块]:";

	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private DecoratePriceService decoratePriceService;
	
	@Autowired
	private DecorateSeckillMapper decorateSeckillMapper;
	
	@Autowired
	private ProprietorInfoService proprietorInfoService;
	
	@Autowired
	private DecorateSeckillOrderService decorateSeckillOrderService;
	
	@Autowired
	private RAMCacheService ramCacheService;
	
	@Override
	public int getMySeckillCount() {
		// ------设置查询条件 ->start
		DecorateSeckillSearch decorateSeckillSearch = new DecorateSeckillSearch();
		decorateSeckillSearch.setIsDeleted(0);
		decorateSeckillSearch.setStatus(DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP);
		// ------设置查询条件 ->end
		
		return this.getCount(decorateSeckillSearch);
	}

	@Override
	public int getCount(DecorateSeckillSearch decorateSeckillSearch) {
		// ------参数验证 ->start
		if(decorateSeckillSearch == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckillSearch = null");
			return 0;
		}
		// ------参数验证 ->end
		
		return decorateSeckillMapper.selectCountBySearch(decorateSeckillSearch);
	}

	/**
	 * get 限时快抢(抢单) list
	 * 
	 * @author huangsongbo
	 * @param orderType
	 * orderType = 1: 会把我的抢到的,并且未支付的,并且未支付超时的订单 排到最前面
	 * 默认排序: 未抢订单 > 已抢订单
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<DecorateSeckill> findList(DecorateSeckillGetListParamDTO paramDTO, Long userId) {
		// 返回结果
		List<DecorateSeckill> returnList = new ArrayList<DecorateSeckill>();
		
		// ------参数整理 ->start
		String cityCode = paramDTO.getCityCode();
		Integer decorateBudgetValue = paramDTO.getDecorateBudgetValue();
		Integer decorateTypeValue = paramDTO.getDecorateTypeValue();
		Long baseProductStyleId = paramDTO.getBaseProductStyleId();
		Integer orderType = paramDTO.getOrderType();
		Integer start = paramDTO.getStart();
		Integer limit = paramDTO.getLimit();
		// ------参数整理 ->end
		
		// ------作此逻辑: orderType = 1: 会把我的抢到的单,并且未支付的,排到最前面 ->start
		// 记录我抢到的并且未支付的并且支付未超时的订单
		List<DecorateSeckill> decorateSeckillListNoPay = null;
		// 记录我抢到的并且未支付的并且支付未超时的订单的idList
		List<Long> decorateSeckillIdListNoPay = new ArrayList<Long>();
		
		if(orderType != null && orderType.intValue() == DecorateSeckillConstants.DECORATESECKILLSEARCH_ORDERTYPE_NON_PAYMENT_ORDER_PRIORITY.intValue()) {
			// 获取我抢到的并且未支付的并且支付未超时的订单
			decorateSeckillListNoPay = this.getDecorateSeckillListNoPay(userId);
			if(Lists.isNotEmpty(decorateSeckillListNoPay)) {
				decorateSeckillListNoPay.forEach(item -> {
					// 设置为待支付状态
					item.setResultStatus(DecorateSeckillConstants.DECORATESECKILLSEARCH_RESULTSTATUS_UNPAID);
					
					decorateSeckillIdListNoPay.add(item.getId());
				});
			}
		}
		// ------作此逻辑: orderType = 1: 会把我的抢到的单,并且未支付的,排到最前面 ->end
		
		// ------组装查剩下订单的搜索条件 ->start
		DecorateSeckillSearch decorateSeckillSearch = new DecorateSeckillSearch();
		decorateSeckillSearch.setOrderType(DecorateSeckillConstants.DECORATESECKILLSEARCH_ORDERTYPE_NON_PAYMENT_ORDER_PRIORITY_2);
		decorateSeckillSearch.setCityCode(cityCode);
		decorateSeckillSearch.setDecorateBudgetValue(decorateBudgetValue);
		// 在上面逻辑已查出的订单, 不重复查询
		decorateSeckillSearch.setIdListNotIn(decorateSeckillIdListNoPay);
		decorateSeckillSearch.setStart(start);
		decorateSeckillSearch.setLimit(limit);
		decorateSeckillSearch.setDecorateTypeValue(decorateTypeValue);
		decorateSeckillSearch.setBaseProductStyleId(baseProductStyleId);
		// add by huangsongbo 2018.11.21 已抢的订单只查15天之内的
		decorateSeckillSearch.setBeingSnappedUpSeckillGmtCreateAfter(Utils.getDateBefore(new Date(), 15));
		// ------组装查剩下订单的搜索条件 ->end
		
		// ------查剩下的需要显示的订单 ->start
		List<DecorateSeckill> decorateSeckillList = new ArrayList<>();
		if(start != null && limit != null && Lists.isNotEmpty(decorateSeckillIdListNoPay)) {
			// 有分页的情况
			int end = start + limit;
			if(end > decorateSeckillIdListNoPay.size()) {
				limit = end - decorateSeckillIdListNoPay.size();
				decorateSeckillSearch.setLimit(limit);
				
				decorateSeckillList = this.findMoreInfoList(decorateSeckillSearch);
				
				// ------结合start处理decorateSeckillListNoPay ->start
				if(start > 0) {
					decorateSeckillListNoPay = decorateSeckillListNoPay.subList(start, decorateSeckillListNoPay.size());
				}
				// ------结合start处理decorateSeckillListNoPay ->end
			}else if(end == decorateSeckillIdListNoPay.size()) {
				// ------结合start处理decorateSeckillListNoPay ->start
				if(start > 0) {
					decorateSeckillListNoPay = decorateSeckillListNoPay.subList(start, decorateSeckillListNoPay.size());
				}
				// ------结合start处理decorateSeckillListNoPay ->end
			}else {
				// ------结合start处理decorateSeckillListNoPay ->start
				if(start > 0) {
					decorateSeckillListNoPay = decorateSeckillListNoPay.subList(start, end);
				}
				// ------结合start处理decorateSeckillListNoPay ->end
			}
		}else {
			// 无分页的情况
			decorateSeckillList = this.findMoreInfoList(decorateSeckillSearch);
		}
		
		// ------decorateSeckillList为设置抢单状态(resultStatus) ->start
		if(Lists.isNotEmpty(decorateSeckillList)) {
			decorateSeckillList.forEach(item -> item.setResultStatus(item.getStatus()));
		}
		// ------decorateSeckillList为设置抢单状态(resultStatus) ->end
		
		// ------查剩下的需要显示的订单 ->end
		
		if(Lists.isNotEmpty(decorateSeckillIdListNoPay)) {
			returnList.addAll(decorateSeckillListNoPay);
		}
		if(Lists.isNotEmpty(decorateSeckillList)) {
			returnList.addAll(decorateSeckillList);
		}
		
		return returnList;
	}

	@Override
	public List<DecorateSeckill> getDecorateSeckillListNoPay(Long userId) {
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			return null;
		}
		// ------参数验证 ->end
		
		// 组装查询条件:查自己的,未支付的,且未支付超时的订单 ->start
		DecorateOrderSearch decorateOrderSearch = new DecorateOrderSearch();
		decorateOrderSearch.setUserId(userId);
		decorateOrderSearch.setOrderTimeoutAfter(new Date());
		decorateOrderSearch.setOrderStatus(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_UNPAID);
		decorateOrderSearch.setOrderType(DecorateOrderConstants.DECORATEORDER_ORDERTYPE_SECKILL);
		// 组装查询条件:查自己的,未支付的,且未支付超时的订单 ->end
		
		/*List<Long> decorateSeckillIdList = decorateOrderService.getDecorateSeckillIdList(decorateOrderSearch);*/
		List<DecorateOrder> decorateOrderList = decorateOrderService.getDecorateSeckillIdAndOrderOverTimeList(decorateOrderSearch);
		
		List<Long> decorateSeckillIdList = new ArrayList<Long>();
		// key = 显示快抢id(decorate_seckill.id), value = 支付超时时间
		Map<Long, Date> overTimeMap = new HashMap<Long, Date>();
		if(Lists.isNotEmpty(decorateOrderList)) {
			decorateOrderList.forEach(item -> {
				decorateSeckillIdList.add(item.getDecorateSeckillId());
				overTimeMap.put(item.getDecorateSeckillId(), item.getOrderTimeout());
			});
		}
		
		if(Lists.isEmpty(decorateSeckillIdList)) {
			return null;
		}
		
		DecorateSeckillSearch decorateSeckillSearch = new DecorateSeckillSearch();
		decorateSeckillSearch.setIdList(decorateSeckillIdList);
		List<DecorateSeckill> returnList = this.findMoreInfoList(decorateSeckillSearch);
		if(Lists.isNotEmpty(returnList)) {
			returnList.forEach(item -> {
				// 获取支付超时时间信息
				if(overTimeMap.containsKey(item.getId())) {
					Date date = overTimeMap.get(item.getId());
					if(date != null) {
						item.setOrderTimeout(date);
					}
				}
			});
		}
		return returnList;
	}

	@Override
	public List<DecorateSeckill> findMoreInfoList(DecorateSeckillSearch decorateSeckillSearch) {
		// ------参数验证 ->start
		if(decorateSeckillSearch == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckillSearch = null");
			return null;
		}
		// ------参数验证 ->end
		
		List<DecorateSeckill> decorateSeckillList = this.findList(decorateSeckillSearch);
		if(Lists.isNotEmpty(decorateSeckillList)) {
			decorateSeckillList.forEach(item -> {
				if(item != null) {
					item.setProprietorInfo(proprietorInfoService.setMoreInfo(item.getProprietorInfo()));
				}
			});
		}
		return decorateSeckillList;
	}
	
	@Override
	public List<DecorateSeckill> findList(DecorateSeckillSearch decorateSeckillSearch) {
		// ------参数验证 ->start
		if(decorateSeckillSearch == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckillSearch = null");
			return null;
		}
		// ------参数验证 ->end
		
		return decorateSeckillMapper.selectListBySearch(decorateSeckillSearch);
	}

	@Override
	public DecorateSeckill getDetails(Long id, Long userId) throws BizException {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return null;
		}
		// ------参数验证 ->end
		
		/*DecorateSeckill decorateSeckill = decorateSeckillMapper.getDetails(id);*/
		DecorateSeckill decorateSeckill = this.getDetails(id);
		
		if(decorateSeckill == null) {
			LOGGER.error("{} decorateSeckill = null, decorateSeckill.id = {}");
			throw new BizException("限时快抢订单不存在");
		}
		
		// ------设置getOrderStatus方法isMyOrder参数 ->start
		boolean isMyOrder = false;
		if(userId != null && decorateSeckill.getUserId() != null && userId.longValue() == decorateSeckill.getUserId().longValue()) {
			isMyOrder = true;
		}
		// ------设置getOrderStatus方法isMyOrder参数 ->end
		
		// 设置跟多信息: 风格/地区名称...
		this.setMoreInfo(decorateSeckill);
		
		// ------设置订单状态 ->start
		Integer resultStatus = this.getOrderStatus(decorateSeckill.getStatus(), decorateSeckill.getOrderStatus(), decorateSeckill.getOrderTimeout(), isMyOrder);
		decorateSeckill.setResultStatus(resultStatus);
		decorateSeckill.setRemainingTime(Utils.getTime(new Date(), decorateSeckill.getOrderTimeout()));
		// ------设置订单状态 ->end
		
		return decorateSeckill;
	}

	@Override
	public Integer getOrderStatus(Integer status, Integer orderStatus, Date orderTimeout, boolean isMyOrder) {
		// ------参数验证 ->start
		if(status == null) {
			LOGGER.error(LOGPREFIX + "status = null");
			status = DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP;
		}
		if(orderStatus == null) {
			LOGGER.error(LOGPREFIX + "orderStatus = null");
			return status;
		}
		if(orderTimeout == null) {
			LOGGER.error(LOGPREFIX + "orderTimeout = null");
			return status;
		}
		// ------参数验证 ->end
		
		if(status.intValue() == DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP) {
			return DecorateSeckillConstants.DECORATESECKILLSEARCH_RESULTSTATUS_NOT_BEING_SNAPPED_UP;
		}else {
			if(
					(orderStatus.intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_UNPAID.intValue() || //未支付
					orderStatus.intValue() == DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME.intValue()) && //支付超时
					isMyOrder // 并且是我抢到的单, 才可能显示未支付的状态
					) {
				// 前面已经判断了该订单是未支付或者是支付超时的状态并且是我的订单, 再判断是否支付超时 -> true -> 该状态为待支付状态
				if(new Date().before(orderTimeout)) {
					return DecorateSeckillConstants.DECORATESECKILLSEARCH_RESULTSTATUS_UNPAID;
				}
			}else {
				
			}
		}
		// 剩下的状态都是已抢购
		return DecorateSeckillConstants.DECORATESECKILLSEARCH_RESULTSTATUS_BEING_SNAPPED_UP;
	}

	@Override
	public DecorateSeckill getDetails(Long id) {
		// 参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return null;
		}
		// 参数验证 ->end
		
		DecorateSeckill decorateSeckill = decorateSeckillMapper.selectDetailsById(id);
		
		return decorateSeckill;
	}

	@Override
	public DecorateSeckillGetListDTO getHomePageInfo(Long userId) throws BizException {
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			throw new BizException("查询抢单列表失败: 没找到用户信息");
		}
		// ------参数验证 ->end
		
		// 返回结果
		DecorateSeckillGetListDTO decorateSeckillGetListDTO = new DecorateSeckillGetListDTO();
		
		// ------需要查询我的客户count, 平台派单count, 限时快抢count的情况 ->start
		int myClientCount = decorateOrderService.getMyClientCount(userId);
		int myOrderCount = decoratePriceService.getMyOrderCount(userId);
		int mySeckillCount = this.getMySeckillCount();
		decorateSeckillGetListDTO.setMyClientCount(myClientCount);
		decorateSeckillGetListDTO.setMyOrderCount(myOrderCount);
		decorateSeckillGetListDTO.setMySeckillCount(mySeckillCount);
		// ------需要查询我的客户count, 平台派单count, 限时快抢count的情况 ->end
		
		// ------查询抢单list: 5条未抢, 3条已抢信息 ->start
		List<DecorateSeckill> decorateSeckillList = this.getHomePageDecorateSeckillList(5, 3);
		List<DecorateSeckillDTO> decorateSeckillDTOList = this.getDecorateSeckillDTOList(decorateSeckillList);
		decorateSeckillGetListDTO.setDecorateSeckillList(decorateSeckillDTOList);
		// ------查询抢单list: 5条未抢, 3条已抢信息 ->end
		
		return decorateSeckillGetListDTO;
	}

	@Override
	public List<DecorateSeckill> getHomePageDecorateSeckillList(Integer notBeingSnappedCount, Integer beingSnappedCount) {
		// ------参数验证 ->start
		if(notBeingSnappedCount == null || notBeingSnappedCount < 0) {
			LOGGER.error(LOGPREFIX + "(notBeingSnappedCount == null || notBeingSnappedCount < 0) = true");
			notBeingSnappedCount = 5;
		}
		if(beingSnappedCount == null || beingSnappedCount < 0) {
			LOGGER.error(LOGPREFIX + "(beingSnappedCount == null || beingSnappedCount < 0) = true");
			beingSnappedCount = 3;
		}
		// ------参数验证 ->end
		
		// 返回数据
		List<DecorateSeckill> returnList = new ArrayList<DecorateSeckill>();
		
		DecorateSeckillSearch decorateSeckillSearch = new DecorateSeckillSearch();
		decorateSeckillSearch.setStart(0);
		
		// ------查询未抢购订单list ->start
		decorateSeckillSearch.setLimit(notBeingSnappedCount);
		decorateSeckillSearch.setStatus(DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP);
		List<DecorateSeckill> notBeingSnappedList = this.findMoreInfoList(decorateSeckillSearch);
		returnList.addAll(notBeingSnappedList);
		// ------查询未抢购订单list ->end
		
		// ------查询已抢购订单list ->start
		decorateSeckillSearch.setLimit(beingSnappedCount);
		decorateSeckillSearch.setStatus(DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_BEING_SNAPPED_UP);
		// add by huangsongbo 2018.11.21 已抢的订单只查15天之内的
		decorateSeckillSearch.setBeingSnappedUpSeckillGmtCreateAfter(Utils.getDateBefore(new Date(), 15));
		List<DecorateSeckill> beingSnappedList = this.findMoreInfoList(decorateSeckillSearch);
		returnList.addAll(beingSnappedList);
		// ------查询已抢购订单list ->end
		
		// ------为returnList设置抢单状态(resultStatus) ->start
		if(Lists.isNotEmpty(returnList)) {
			returnList.forEach(item -> item.setResultStatus(item.getStatus()));
		}
		// ------returnList为设置抢单状态(resultStatus) ->end
		
		return returnList;
	}

	@Override
	public List<DecorateSeckillDTO> getDecorateSeckillDTOList(List<DecorateSeckill> decorateSeckillList) {
		// ------参数验证 ->start
		if(Lists.isEmpty(decorateSeckillList)) {
			LOGGER.error(LOGPREFIX + "Lists.isEmpty(decorateSeckillList) = true");
			return null;
		}
		// ------参数验证 ->end
		
		List<DecorateSeckillDTO> decorateSeckillDTOList = new ArrayList<DecorateSeckillDTO>();
		decorateSeckillList.forEach(item -> decorateSeckillDTOList.add(this.getDecorateSeckillDTO(item)));
		return decorateSeckillDTOList;
	}

	@Override
	public int seckill(Long id, Long userId) throws BizException {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			throw new BizException("抢单失败, 参数 id 不能为空");
		}
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			throw new BizException("抢单失败, 参数 userId 不能为空");
		}
		// ------参数验证 ->end
		
		DecorateSeckill decorateSeckill = new DecorateSeckill();
		decorateSeckill.setUserId(userId);
		decorateSeckill.setStatus(DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_BEING_SNAPPED_UP);
		
		int count = this.update(decorateSeckill, id, DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP);
		
		// ------记录该用户的抢单记录, 用于获得今天该用户的剩余抢单次数 ->start
		if(count > 0) {
			SeckillRecordFromRedisDTO seckillRecordFromRedisDTO = this.getSeckillRecordFromRedisDTO(DecorateSeckillConstants.SECKILLRECORDFROMREDISDTO_STATUS_NOPAY);
			this.setSeckillRecord(userId, seckillRecordFromRedisDTO);
		}
		// ------记录该用户的抢单记录, 用于获得今天该用户的剩余抢单次数 ->end
		
		return count;
	}

	@Override
	public void setSeckillRecord(Long userId, SeckillRecordFromRedisDTO seckillRecordFromRedisDTO) {
		// ------参数验证 ->start
		if(seckillRecordFromRedisDTO == null) {
			LOGGER.error(LOGPREFIX + "seckillRecordFromRedisDTO == null");
			return;
		}
		// ------参数验证 ->end
		
		String key = this.getSeckillRecordRedisKey(userId);
		CacheManager.getInstance().getCacher().setObject(key, seckillRecordFromRedisDTO, seckillRecordFromRedisDTO.getSurvivalTime() == null ? 0 : seckillRecordFromRedisDTO.getSurvivalTime().intValue());
	}

	@Override
	public String getSeckillRecordRedisKey(Long userId) {
		return "decorateSeckillRecord:decorateSeckill_seckillRecord_userId_" + userId;
	}

	@Override
	public SeckillRecordFromRedisDTO getSeckillRecordFromRedisDTO(Integer status) {
		// 参数验证 ->start
		if(status == null) {
			status = DecorateSeckillConstants.SECKILLRECORDFROMREDISDTO_STATUS_NOPAY;
		}
		// 参数验证 ->end
		
		Date now = new Date();
		
		SeckillRecordFromRedisDTO seckillRecordFromRedisDTO = new SeckillRecordFromRedisDTO();
		seckillRecordFromRedisDTO.setCreateTime(now.getTime());
		Long survivalTime = 0L;
		if(status.intValue() == DecorateSeckillConstants.SECKILLRECORDFROMREDISDTO_STATUS_NOPAY.intValue()) {
			// 当为抢单时, 超时时间设置为15分钟, 15分钟后, 这条redis记录消失
			survivalTime = DecorateOrderConfig.DECORATESECKILL_PAYMENT_TIMEOUT_MINUTE.longValue() * 60;
		}else {
			// 当识别为抢单后支付时, 超时时间设置为今晚12点超时, 意义: 过了今晚12点, 这条redis记录消失
			survivalTime = (this.getWeeHoursTime() - now.getTime()) / 1000;
		}
		seckillRecordFromRedisDTO.setSurvivalTime(survivalTime);
		seckillRecordFromRedisDTO.setEndTime(now.getTime() + survivalTime);
		seckillRecordFromRedisDTO.setStatus(status);
		return seckillRecordFromRedisDTO;
	}

	/**
	 * 获得今晚12点的time
	 * copy from internet
	 * 
	 * @author huangsongbo
	 * @return
	 */
	private long getWeeHoursTime() {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成这样就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);      
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	@Override
	public int update(
			DecorateSeckill decorateSeckill, 
			Long id,
			Integer status) throws BizException {
		// ------参数验证 ->start
		if(decorateSeckill == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckill = null");
			throw new BizException("更新订单失败: 参数 decorateSeckill 不能为空");
		}
		if(id == null && status == null) {
			LOGGER.error(LOGPREFIX + "(id == null && status == null) = true");
			throw new BizException("更新订单失败: 参数 id 和 参数 status 不能同时为空");
		}
		// ------参数验证 ->end
		
		return decorateSeckillMapper.updateByIdAndStatus(decorateSeckill, id, status);
	}
	
	/**
	 * DecorateSeckill -> DecorateSeckillDTO
	 * 
	 * @author huangsongbo
	 * @param item
	 * @return
	 */
	private DecorateSeckillDTO getDecorateSeckillDTO(DecorateSeckill decorateSeckill) {
		// ------参数验证 ->start
		if(decorateSeckill == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckill = null");
			return null;
		}
		// ------参数验证 ->end
		
		DecorateSeckillDTO decorateSeckillDTO = new DecorateSeckillDTO();
		
		// ------设置DecorateSeckillDTO属性 ->start
		ProprietorInfo proprietorInfo = decorateSeckill.getProprietorInfo();
		if(proprietorInfo != null) {
			decorateSeckillDTO.setBudgetInfo("预算" + proprietorInfo.getDecorateBudgetInfo());
			decorateSeckillDTO.setDecorateHouseTypeInfo(proprietorInfo.getDecorateHouseTypeInfo());
			decorateSeckillDTO.setDecorateStyleInfo(proprietorInfo.getDecorateStyleInfo());
			decorateSeckillDTO.setDecorateTypeInfo(proprietorInfo.getDecorateTypeInfo());
			decorateSeckillDTO.setPositionInfo(proprietorInfo.getProvinceInfo() + proprietorInfo.getCityInfo());
			decorateSeckillDTO.setUserName(proprietorInfo.getUserName());
			decorateSeckillDTO.setHouseAcreageInfo(proprietorInfo.getHouseAcreage() + "㎡");
		}
		decorateSeckillDTO.setOrderStatus(decorateSeckill.getResultStatus());
		decorateSeckillDTO.setRemainingTime(Utils.getTime(new Date(), decorateSeckill.getOrderTimeout()));
		if(decorateSeckill.getPrice() == null) {
			decorateSeckill.setPrice(new BigDecimal(0));
		}
		decorateSeckillDTO.setPriceInfo(decorateSeckill.getPrice().multiply(new BigDecimal(10)).longValue());
		decorateSeckillDTO.setId(decorateSeckill.getId());
		// ------设置DecorateSeckillDTO属性 ->end
		
		return decorateSeckillDTO;
	}
	
	/**
	 * 获取更多的属性,具体设置那些属性看代码
	 * 
	 * @author huangsongbo
	 * @param decorateSeckill
	 */
	private void setMoreInfo(DecorateSeckill decorateSeckill) {
		// ------参数判断 ->start
		if(decorateSeckill == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckill = null");
			return;
		}
		// ------参数判断 ->end
		
		if(decorateSeckill.getProprietorInfo() != null) {
			ProprietorInfo proprietorInfo = proprietorInfoService.setMoreInfo(decorateSeckill.getProprietorInfo());
			decorateSeckill.setProprietorInfo(proprietorInfo);
		}
	}

	@Override
	public void syncOrder(Long id, Long userId) {
		
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return;
		}
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			return;
		}
		
		DecorateSeckill decorateSeckill = this.get(id);
		if(decorateSeckill == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return;
		}
		// ------参数验证 ->end
		
		// ------获取用户信息(userName, 母公司id) ->start
		SysUser sysUser = ramCacheService.getSysUser(userId);
		// ------获取用户信息(userName, 母公司id) ->end
		
		// ------insert decorate_seckill_order ->start
		DecorateSeckillOrder decorateSeckillOrder = decorateSeckillOrderService.add(decorateSeckill, sysUser);
		// ------insert decorate_seckill_order ->end
		
		// ------insert decorate_order ->start
		decorateOrderService.add(decorateSeckillOrder, sysUser);
		// ------insert decorate_order ->end
		
		// ------update decorate_customer.first_seckill_company ->start
		// 此逻辑放在支付成功之后
		// ------update decorate_customer.first_seckill_company ->end
	}

	@Override
	public DecorateSeckill get(Long id) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return null;
		}
		// ------参数验证 ->end
		
		return decorateSeckillMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getSeckillResidueCount(Long userId) {
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			return 0;
		}
		// ------参数验证 ->end
		
		String key = this.getSeckillRecordRedisKey(userId);
		boolean flag = CacheManager.getInstance().getCacher().exists(key);
		if(flag) {
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public void copy(List<Long> seckillOrderIdList) {
		// ------参数验证 ->start
		if(Lists.isEmpty(seckillOrderIdList)) {
			LOGGER.error("{} Lists.isEmpty(seckillOrderIdList) = true", LOGPREFIX);
			return;
		}
		// ------参数验证 ->end
		
		seckillOrderIdList.forEach(item -> {
			DecorateSeckillOrder decorateSeckillOrder = decorateSeckillOrderService.get(item);
			if(decorateSeckillOrder != null) {
				DecorateSeckill decorateSeckill = this.get(decorateSeckillOrder.getDecorateSeckillId());
				
				if(decorateSeckill != null) {
					
					// ------构建decorateSeckill数据准备insert ->start
					Date now = new Date();
					decorateSeckill.setId(null);
					decorateSeckill.setUserId(0L);
					decorateSeckill.setStatus(DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP);
					decorateSeckill.setGmtCreate(now);
					decorateSeckill.setGmtModified(now);
					decorateSeckill.setIsDeleted(0);
					// ------构建decorateSeckill数据准备insert ->end
					
					this.add(decorateSeckill);
				}
			}
		});
	}

	@Override
	public void add(DecorateSeckill decorateSeckill) {
		// ------参数验证 ->start
		if(decorateSeckill == null) {
			LOGGER.error("{} decorateSeckill = null", LOGPREFIX);
			return;
		}
		// ------参数验证 ->end
		
		decorateSeckillMapper.insertSelective(decorateSeckill);
	}

	@Override
	public int update(Long userId, Integer status, List<Long> idList) {
		// ------参数验证 ->start
		if(Lists.isEmpty(idList)) {
			LOGGER.error(LOGPREFIX + "Lists.isEmpty(idList) = true");
			return 0;
		}
		// ------参数验证 ->end
		
		return decorateSeckillMapper.updateUserIdAndStatusByIdList(userId, status, idList);
	}
	
}
