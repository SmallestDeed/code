package com.nork.decorateOrder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.nork.common.async.ExecutorServiceUtils;
import com.nork.common.exception.BizException;
import com.nork.common.util.collections.Lists;
import com.nork.decorateOrder.common.Utils;
import com.nork.decorateOrder.constant.DecorateCustomerConstants;
import com.nork.decorateOrder.constant.DecorateOrderConstants;
import com.nork.decorateOrder.constant.DecoratePriceConstants;
import com.nork.decorateOrder.constant.ProprietorInfoConstants;
import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.decorateOrder.model.dto.DecoratePriceSubmitPriceParamDTO;
import com.nork.decorateOrder.model.input.DecoratePriceListQuery;
import com.nork.decorateOrder.model.output.DecoratePriceDetail;
import com.nork.decorateOrder.model.output.DecoratePriceVO;
import com.nork.decorateOrder.service.ProprietorInfoService;
import com.nork.design.model.DecorateDesignPlanImgInfo;
import com.nork.design.service.DecorateDesignPlanImgService;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.model.SysUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nork.decorateOrder.dao.DecoratePriceMapper;
import com.nork.decorateOrder.model.search.DecoratePriceSearch;
import com.nork.decorateOrder.service.DecorateCustomerService;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecoratePayService;
import com.nork.decorateOrder.service.DecoratePriceService;

@Service("decoratePriceService")
public class DecoratePriceServiceImpl implements DecoratePriceService {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecoratePriceServiceImpl.class); 
	
	private final static String LOGPREFIX = "[装修报价模块]:";
	
	@Autowired
	private DecoratePriceMapper decoratePriceMapper;

	@Autowired
	private ProprietorInfoService proprietorInfoService;

	@Autowired
	private DecorateDesignPlanImgService decorateDesignPlanImgService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
	@Autowired
	private DecorateCustomerService decorateCustomerService;
	
	@Autowired
	private RAMCacheService ramCacheService;

	@Autowired
	@Qualifier("decoratePayDecoratePriceService")
	private DecoratePayService decoratePayDecoratePriceService;
	
	@Override
	public int getMyOrderCount(Long userId) {
		// ------参数验证 ->start
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			return 0;
		}
		// ------参数验证 ->end
		
		SysUser sysUser = ramCacheService.getSysUser(userId);
		if(sysUser == null || sysUser.getpCompanyId() == null) {
			LOGGER.error(LOGPREFIX + "(sysUser == null || sysUser.getpCompanyId() == null) = true");
			return 0;
		}
		
		// ------构造查询条件 ->start
		DecoratePriceSearch decoratePriceSearch = new DecoratePriceSearch();
		/*decoratePriceSearch.setBidUserId(userId);*/
		decoratePriceSearch.setCompanyId(sysUser.getpCompanyId());
		decoratePriceSearch.setNow(new Date());
		// ------构造查询条件 ->end
		
		return decoratePriceMapper.getMyOrderCount(decoratePriceSearch);
	}

	/**
	*  查询我的报价列表
	 *  1. 从decorate_price表、proprietor_info表查出列表
	 *  2. 循环列表，处理 地址的显示 和 存在数据字典中的字段的显示
	 *  3.处理电话号码（不显示完整的电话）
	 *  4.处理方案图片显示
	 *
	* @author : WangHaiLin
	* @param query 列表查询入参
	* @return java.util.List<com.nork.decorateOrder.model.output.DecoratePriceVO>
	*
	*/
	@Override
	public List<DecoratePriceVO> getList(DecoratePriceListQuery query) {
		//1.查询列表
		List<DecoratePriceVO> list = decoratePriceMapper.selectList(query);
		if (null!=list&&list.size()>0){
			for (DecoratePriceVO d:list) {
				//2.处理地理位置和数据字典中字段的显示
				LOGGER.info(LOGPREFIX+"  处理 显示数据前 数据DecorateCustomerVO："+d);
				proprietorInfoService.setMoreInfo(d);
				LOGGER.info(LOGPREFIX+"  处理 显示数据前 数据DecorateCustomerVO："+d);
				//3.处理电话号码的显示
				if (null!=d.getMobile()){
					String newMobile = Utils.delMobile(d.getMobile());
					d.setMobile(newMobile);
				}
				//4.处理方案封面图(未报价的显示方案图)
				if (d.getPriceStatus().equals(DecoratePriceConstants.DECORATEPRICE_STATUS_NO_BID)){
					LOGGER.info(LOGPREFIX+"  方案图片处理 方案类型："+d.getDesignplanType()+"  方案Id："+d.getDesignplanId());
					if (null!=d.getDesignplanType()&&null!=d.getDesignplanId()){
						DecorateDesignPlanImgInfo planInfo = getDesignPlanPicPath(d.getDesignplanType(), d.getDesignplanId());
						if (null!=planInfo){
							d.setPlanPicPath(planInfo.getPicPath());
							d.setPlanName(planInfo.getPlanName());
							d.setPlanRecommendedId(planInfo.getPlanId());
							d.setFullHousePlanUUID(planInfo.getFullHousePlanUUID());
						}
					}
				}
				//5.处理倒计时(报价状态为0：未报价)
				if (null!=d.getEndTime()&&d.getPriceStatus()==0){
					Long countDown = Utils.delLostTime(d.getEndTime());
					//countDown小于0时说明报价已经超时，不再返回
					if (null!=countDown&&countDown>0){
						d.setOfferRemainingTime(countDown);
					}
				}
				//处理超时取消报价问题
				dealOvertime(d);
			}
			return list;
		}
		return null;
	}


	/**
	 * 判断报价截止时间，当截止时间小于当前时间并且状态是“未报价”，则修改状态为“报价超时”
	 * @param price
	 */
	private void dealOvertime(DecoratePriceVO price){
		if (null!=price.getEndTime()&&price.getPriceStatus()==0){
			long nowTime = new Date().getTime();
			long endTime = price.getEndTime().getTime();
			if (nowTime>endTime){
				//将展示状态改为 3 报价超时取消
				price.setPriceStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_BID_OVERTIME);
				//将数据库中状态改为 3 报价超时取消
				DecoratePrice decoratePrice=new DecoratePrice();
				decoratePrice.setId(price.getPriceId());
				decoratePrice.setStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_BID_OVERTIME);
				decoratePrice.setGmtModified(new Date());
				decoratePriceMapper.updateByPrimaryKeySelective(decoratePrice);
			}
		}
	}

	/**
	*  查询我的报价数量
	* @author : WangHaiLin
	* @param query 入参
	* @return int
	*
	*/
	@Override
	public int getCount(DecoratePriceListQuery query) {
		return decoratePriceMapper.getCount(query);
	}

	/**
	* 查询我的报价详情
	* @author : WangHaiLin
	* @param priceId 报价Id
	* @return com.nork.decorateOrder.model.output.DecoratePriceDetail
	*
	*/
	@Override
	public DecoratePriceDetail getDetailById(Long priceId) {
		//1.通过报价单Id查询详情
		DecoratePriceDetail detail = decoratePriceMapper.getDetail(priceId);
		//2.处理 位置等的显示
		proprietorInfoService.setMoreInfo(detail);
		LOGGER.info(LOGPREFIX+"  详情 处理 位置等的显示完成"+detail.toString());
		//3.处理电话号码的显示（支付未完成之前不显示完整电话号码）
		if (null!=detail.getOrderStatus()
				&&detail.getOrderStatus()>(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_OVERTIME)){
			String newMobile =Utils.delMobile(detail.getMobile());
			detail.setMobile(newMobile);
			LOGGER.info(LOGPREFIX+"  详情 处理 电话号码完成"+newMobile);
		}
		//4.处理方案封面图(详情都显示方案图)
		if (null!=detail.getDesignplanType()&&null!=detail.getDesignplanId()){
			//String picPath = new Utils().getDesignPlanPicPath(detail.getDesignplanType(), detail.getDesignplanId());
			DecorateDesignPlanImgInfo planInfo = getDesignPlanPicPath(detail.getDesignplanType(), detail.getDesignplanId());
			if (null!=planInfo){
				detail.setPlanPicPath(planInfo.getPicPath());
				detail.setPlanName(planInfo.getPlanName());
				detail.setPlanRecommendedId(planInfo.getPlanId());
				detail.setFullHousePlanUUID(planInfo.getFullHousePlanUUID());
			}
			LOGGER.info(LOGPREFIX+"  详情 处理 方案图完后 "+detail.getPlanPicPath());
		}
		//5.如果报价完成，需要计算“报价总数”
		if (!detail.getPriceStatus().equals(DecoratePriceConstants.DECORATEPRICE_STATUS_NO_BID)){
			LOGGER.info(LOGPREFIX+"  详情 报价状态："+detail.getPriceStatus());
			if (null!=detail.getMaterialFee()&&null!=detail.getCheckFee()&&null!=detail.getDesignFee()&&null!=detail.getLabourFee()){
				BigDecimal total = detail.getMaterialFee().add(detail.getCheckFee()).add(detail.getDesignFee()).add(detail.getLabourFee());
				detail.setTotalFee(total);
				LOGGER.info(LOGPREFIX+"  详情 处理总价完成："+total);
			}
		}
		//6.处理倒计时(报价状态为0：未报价)
		if (null!=detail.getEndTime()&&detail.getPriceStatus()==0){
			Long countDown = Utils.delLostTime(detail.getEndTime());
			//countDown小于0时说明报价已经超时，不再返回
			if (null!=countDown&&countDown>0){
				detail.setOfferRemainingTime(countDown);
				LOGGER.info(LOGPREFIX+"  详情 处理倒计时完成："+countDown);
			}
		}
		//处理度币（1元=10度币）
		if (null!=detail.getPrice()){
			BigDecimal num=new BigDecimal(10);
			detail.setPrice(detail.getPrice().multiply(num));
		}
		return detail;
	}

	@Override
	public void submitPrice(DecoratePriceSubmitPriceParamDTO paramDTO, Long userId) throws BizException {
		// ------参数验证 ->start
		if(paramDTO == null) {
			LOGGER.error(LOGPREFIX + "paramDTO = null");
			throw new BizException("报价失败(paramDTO = null)");
		}
		if(paramDTO.getId() == null) {
			LOGGER.error(LOGPREFIX + "paramDTO.getId() = null");
			throw new BizException("报价失败(paramDTO.getId() = null)");
		}
		DecoratePrice decoratePrice = this.get(paramDTO.getId());
		if(decoratePrice == null) {
			LOGGER.error(LOGPREFIX + "decoratePrice = null; decoratePrice.id = {}", paramDTO.getId());
			throw new BizException("报价失败(decoratePrice = null; decoratePrice.id = " + paramDTO.getId() + ")");
		}
		if(userId == null) {
			LOGGER.error(LOGPREFIX + "userId = null");
			throw new BizException("报价失败(userId = null)");
		}
		// ------参数验证 ->end

		// ------识别该订单是不是自己公司的, 如果不是自己公司的不能报价 ->start
		SysUser sysUser = ramCacheService.getSysUser(userId);
		if(sysUser == null) {
			LOGGER.error(LOGPREFIX + "sysUser == null, sysUser.id = {}", userId);
			throw new BizException("报价失败(未获取到用户信息)");
		}
		if(sysUser.getpCompanyId() == null) {
			LOGGER.error(LOGPREFIX + "userCompanyId = null");
			throw new BizException("报价失败(未获取到用户的公司信息)");
		}
		if(decoratePrice.getCompanyId() == null) {
			LOGGER.error(LOGPREFIX + "decoratePrice.getCompanyId() = null");
			throw new BizException("报价失败(decoratePrice.getCompanyId() = null)");
		}
		if(decoratePrice.getCompanyId().longValue() == sysUser.getpCompanyId().longValue()) {
			
		}else {
			LOGGER.error(LOGPREFIX + "decoratePrice.getCompanyId().longValue() != userCompanyId.longValue()");
			throw new BizException("报价失败(没有报价权限)");
		}
		// ------识别该订单是不是自己公司的, 如果不是自己公司的不能报价 ->start
		
		// ------识别状态必须是待报价, 否则报价失败 ->start
		if(
				decoratePrice.getStatus() == null
				|| decoratePrice.getStatus().intValue() != DecoratePriceConstants.DECORATEPRICE_STATUS_NO_BID.intValue()
				) {
			LOGGER.error(LOGPREFIX + "decoratePrice.status 不匹配(status = null or status = 已报价)");
			throw new BizException("报价失败(已被报价/报价状态匹配失败)");
		}
		// ------识别状态必须是待报价, 否则报价失败 ->end

		// ------识别报价截止时间, 超过截止时间报价失败 ->start
		if(
				decoratePrice.getEndTime() == null
				|| new Date().after(decoratePrice.getEndTime())
				) {
			LOGGER.error(LOGPREFIX + "decorate_price.end_time 不匹配, 报价超时或者 decorate_price.end_time = null");
			throw new BizException("报价失败(报价超时/未记录超时时间)");
		}
		// ------识别报价截止时间, 超过截止时间报价失败 ->end

		int num = this.update(paramDTO, userId);
		
		// ------异步检测该订单参与报价的公司是否全部都报价完成, 如果完成, 就开始10家选三家的逻辑 ->start
		if(num > 0) {
			ExecutorServiceUtils.getInstance().execute(new Runnable() {

				@Override
				public void run() {
					checkPriceAndElect(decoratePrice.getCustomerId(), false);
				}

			});
		}else {
			LOGGER.error(LOGPREFIX + "num = 0, update failed");
			throw new BizException("报价失败(update decoratePrice failed)");
		}
		// ------异步检测该订单参与报价的公司是否全部都报价完成, 如果完成, 就开始10家选三家的逻辑 ->end
	}

	@Override
	public void checkPriceAndElect(Long customerId, boolean flag) {
		// ------参数验证 ->start
		if(customerId == null) {
			LOGGER.error(LOGPREFIX + "customerId = null");
			return;
		}
		// ------参数验证 ->end

		// ------判断该订单参与报价的公司是否全部报价完成 ->start
		if(!flag) {
			int countNoBid = this.getCountNoBid(customerId);
			if(countNoBid > 0) {
				// 识别为还有未报价的公司
				return;
			}
		}
		// ------判断该订单参与报价的公司是否全部报价完成 ->end

		// ------执行10家选3家逻辑 ->start
		this.elect(customerId);
		// ------执行10家选3家逻辑 ->end
	}

	/**
	 * 根据报价10家选3家逻辑
	 *
	 * @param customerId
	 */
	private void elect(Long customerId) {
		// ------参数验证 ->start
		if(customerId == null) {
			LOGGER.error(LOGPREFIX + "customerId = null");
			return;
		}
		// ------参数验证 ->end

		// ------查询出该订单对应的所有报价信息 ->start
		List<DecoratePrice> decoratePriceList = this.getListByCustomerId(customerId);
		// ------查询出该订单对应的所有报价信息 ->end

		// 记录报价超时的DecoratePrice, 后面要更新状态(更新为报价超时状态)
		List<DecoratePrice> decoratePriceListOverTime = new ArrayList<DecoratePrice>();
		// 记录选上的DecoratePrice, 后面要更新状态(更新为报价已选中)
		List<DecoratePrice> decoratePriceListPickOn = new ArrayList<DecoratePrice>();
		// 记录未选上的DecoratePrice, 后面要更新状态(更新为报价未选中)
		List<DecoratePrice> decoratePriceListNoPickOn = new ArrayList<DecoratePrice>();

		// flag = true: 去头; flag = false: 去尾
		/*boolean flag = true;*/

		// ------选举公司, 10家选3家逻辑, 并且更新一些订单的状态 ->start
		if(Lists.isNotEmpty(decoratePriceList)) {
			for(DecoratePrice decoratePrice : decoratePriceList) {
				if(decoratePrice.getStatus() == null) {
					LOGGER.error(LOGPREFIX + "decoratePrice.getStatus() = null; decoratePrice.id = {}", decoratePrice.getId());
					continue;
				}
				if(decoratePrice.getEndTime() == null) {
					LOGGER.error(LOGPREFIX + "decoratePrice.getEndTime() = null; decoratePrice.id = {}", decoratePrice.getId());
					continue;
				}

				// ------设置总价 ->start
				decoratePrice.setTotalFee(
						(decoratePrice.getCheckFee() == null ? new BigDecimal(0) : decoratePrice.getCheckFee())
						.add((decoratePrice.getDesignFee() == null ? new BigDecimal(0) : decoratePrice.getDesignFee()))
						.add((decoratePrice.getLabourFee() == null ? new BigDecimal(0) : decoratePrice.getLabourFee()))
						.add((decoratePrice.getMaterialFee() == null ? new BigDecimal(0) : decoratePrice.getMaterialFee()))
						);
				// ------设置总价 ->end
				
				// ------获取报价超时的订单, 装进decoratePriceListOverTime ->start
				if(
						decoratePrice.getStatus().intValue() == DecoratePriceConstants.DECORATEPRICE_STATUS_NO_BID
						|| decoratePrice.getStatus().intValue() == DecoratePriceConstants.DECORATEPRICE_STATUS_BID_OVERTIME
						/*&& new Date().after(decoratePrice.getEndTime())*/
						) {
					decoratePriceListOverTime.add(decoratePrice);
					continue;
				}
				// ------获取报价超时的订单, 装进decoratePriceListOverTime ->end

				decoratePriceListPickOn.add(decoratePrice);

			}
			
			// ------开始对候选人进行选举(去头去尾, 并且取三家于平均报价最接近的报价) ->start
			if(decoratePriceListPickOn.size() >= 5) {
				// 去头去尾
				decoratePriceListNoPickOn.add(decoratePriceListPickOn.remove(0));
				decoratePriceListNoPickOn.add(decoratePriceListPickOn.remove(decoratePriceListPickOn.size() - 1));
			}else if (decoratePriceListPickOn.size() == 4) {
				// 去头
				decoratePriceListNoPickOn.add(decoratePriceListPickOn.remove(0));
			}else {
				// 只剩3家一下,直接选剩下的保价
			}
			
			if(decoratePriceListPickOn.size() <= 3) {
				// 全要了
			}else {
				// ------排序 ->start
				Collections.sort(decoratePriceListPickOn, new Comparator<DecoratePrice>() {

					@Override
					public int compare(DecoratePrice o1, DecoratePrice o2) {
						return o1.getTotalFee().subtract(o2.getTotalFee()).intValue();
					}

				});
				// ------排序 ->end
				
				// ------计算总价平均数 ->start
				BigDecimal totalFeeSum = new BigDecimal(0);
				for(DecoratePrice decoratePrice : decoratePriceListPickOn) {
					totalFeeSum = totalFeeSum.add(decoratePrice.getTotalFee());
				}
				
				// 平均数
				BigDecimal average = totalFeeSum.divide(new BigDecimal(decoratePriceListPickOn.size()));
				// ------计算总价平均数 ->end
				
				// ------获得平均数在排序之后的保价list中的index ->start
				int averageIndex = 0;
				for (int index = 0; index < decoratePriceListPickOn.size(); index ++) {
					DecoratePrice decoratePrice = decoratePriceListPickOn.get(index);
					if(decoratePrice.getTotalFee().compareTo(average) == 1) {
						averageIndex = index;
						break;
					}
				}
				// ------获得平均数在排序之后的保价list中的index ->end
				
				// ------取最接近平均保价的三家保价 ->start
				List<Integer> indexList = new ArrayList<Integer>();
				indexList.add(averageIndex);
				indexList.add(averageIndex - 1);
				// 我还需要一个
				if(averageIndex == 1) {
					indexList.add(2);
				}else if (averageIndex == (decoratePriceListPickOn.size() - 1)) {
					indexList.add(averageIndex - 2);
				}else {
					// 0 1 2 3 4 
					// averageIndex = 2 -> 选了1 和 2 -> 拿出0 和 3对比哪个更接近平均值, 就取哪个
					DecoratePrice decoratePrice1 = decoratePriceListPickOn.get(averageIndex - 2);
					DecoratePrice decoratePrice2 = decoratePriceListPickOn.get(averageIndex + 1);
					BigDecimal spread1 = decoratePrice1.getTotalFee().subtract(average).abs();
					BigDecimal spread2 = decoratePrice2.getTotalFee().subtract(average).abs();
					if(spread1.compareTo(spread2) == 1) {
						indexList.add(averageIndex + 1);
					}else {
						indexList.add(averageIndex - 2);
					}
				}
				// ------取最接近平均保价的三家保价 ->end
				
				List<DecoratePrice> decoratePriceListTemp = new ArrayList<DecoratePrice>();
				indexList.forEach(item -> {
					decoratePriceListTemp.add(decoratePriceListPickOn.get(item));
				});
				
				/*Iterator<DecoratePrice> it = decoratePriceListPickOn.iterator();*/
				for (int index = 0; index < decoratePriceListPickOn.size(); index++) {
					DecoratePrice item = decoratePriceListPickOn.get(index);
					if(decoratePriceListTemp.indexOf(item) == -1) {
						decoratePriceListPickOn.remove(item);
						decoratePriceListNoPickOn.add(item);
						index --;
					}
				}

			}
			// ------开始对候选人进行选举(去头去尾, 并且取三家于平均报价最接近的报价) ->end
			
		}
		// ------选举公司, 10家选3家逻辑, 并且更新一些订单的状态 ->end

		// ------处理选上的报价/未选上的报价/超时报价的报价的状态 ->start
		Integer decorateCustomerBidStatus = DecorateCustomerConstants.DECORATECUSTOMER_BIDSTATUS_FINISH;
		
		if(decoratePriceListPickOn.size() > 0) {
			
			// step 1. update decorate_price.status = 4: 已选中
			// step 2. insert decorate_order
			// step 3. 扣款, 并记录扣款状态
			decoratePriceListPickOn.forEach(item -> {
				SysUser sysUser = ramCacheService.getSysUser(item.getBidUserId());
				
				// 执行 step 1
				DecoratePrice decoratePrice = new DecoratePrice();
				decoratePrice.setStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_CHECKED);
				decoratePrice.setId(item.getId());
				this.update(decoratePrice);
				// 执行 step 2
				decorateOrderService.add(item, sysUser);
				// 执行 step 3
				try {
					decoratePayDecoratePriceService.pay(item.getId(), item.getBidUserId());
				} catch (BizException e) {
					LOGGER.error("正在执行10家选3家逻辑, 且已经选定装企并且正在扣款(度比), 扣款失败: {}", e.getMessage());
				}
				decoratePrice.setPayStatus(DecorateOrderConstants.DECORATEORDER_ORDERSTATUS_PAY_WAIT_TO_COMMUNICATE);
			});
		}else {
			// update decorate_customer.bid_status = 2:  完成了报价但是订单未指派出去
			decorateCustomerBidStatus = DecorateCustomerConstants.DECORATECUSTOMER_BIDSTATUS_FINISH_BUT_NULLITY;
		}
		
		if(decoratePriceListOverTime.size() > 0) {
			// update decorate_price.status = 3: 超时取消报价
			decoratePriceListOverTime.forEach(item -> {
				DecoratePrice decoratePrice = new DecoratePrice();
				decoratePrice.setStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_BID_OVERTIME);
				decoratePrice.setId(item.getId());
				this.update(decoratePrice);
			});
		}
		
		if(decoratePriceListNoPickOn.size() > 0) {
			// update decorate_price.status = 2: 未选中
			decoratePriceListNoPickOn.forEach(item -> {
				DecoratePrice decoratePrice = new DecoratePrice();
				decoratePrice.setStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_UNCHECKED);
				decoratePrice.setId(item.getId());
				this.update(decoratePrice);
			});
		}
		
		// 更新decorate_customer.bid_status
		decorateCustomerService.updateBidStatus(decorateCustomerBidStatus, customerId);
		
		// ------处理选上的报价/未选上的报价/超时报价的报价的状态 ->end
	}

	@Override
	public List<DecoratePrice> getListByCustomerId(Long customerId) {
		// ------参数验证 ->start
		if(customerId == null) {
			LOGGER.error(LOGPREFIX + "customerId = null");
		}
		// ------参数验证 ->end
		
		// ------构造搜索条件 ->start
		DecoratePriceSearch decoratePriceSearch = new DecoratePriceSearch();
		decoratePriceSearch.setCustomerId(customerId);
		decoratePriceSearch.setIsDeleted(DecoratePriceConstants.DECORATEPRICE_ISDELETED_DEFAULT);
		// ------构造搜索条件 ->end
		
		return this.getList(decoratePriceSearch);
	}

	@Override
	public List<DecoratePrice> getList(DecoratePriceSearch decoratePriceSearch) {
		// ------参数验证 ->start
		if(decoratePriceSearch == null) {
			LOGGER.error(LOGPREFIX + "decoratePriceSearch = null");
			return null;
		}
		// ------参数验证 ->end
		
		return decoratePriceMapper.selectListBySearch(decoratePriceSearch);
	}

	@Override
	public int getCountNoBid(Long customerId) {
		// ------参数验证 ->start
		if(customerId == null) {
			LOGGER.error(LOGPREFIX + "customerId = null");
			return 0;
		}
		// ------参数验证 ->end

		// ------构建搜索条件 ->start
		DecoratePriceSearch decoratePriceSearch = new DecoratePriceSearch();
		decoratePriceSearch.setCustomerId(customerId);
		decoratePriceSearch.setStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_NO_BID);
		// ------构建搜索条件 ->end

		return this.getCount(decoratePriceSearch);
	}

	@Override
	public int getCount(DecoratePriceSearch decoratePriceSearch) {
		// ------参数验证 ->start
		if(decoratePriceSearch == null) {
			LOGGER.error(LOGPREFIX + "decoratePriceSearch = null");
			return 0;
		}
		// ------参数验证 ->end

		return decoratePriceMapper.selectCountBySearch(decoratePriceSearch);
	}

	@Override
	public int update(DecoratePriceSubmitPriceParamDTO paramDTO, Long userId) {
		// ------参数验证 ->start
		if(paramDTO == null) {
			LOGGER.error(LOGPREFIX + "paramDTO = null");
			return 0;
		}
		if(paramDTO.getId() == null) {
			LOGGER.error(LOGPREFIX + "paramDTO.getId() = null");
			return 0;
		}
		if(
				paramDTO.getCheckFee() == null && paramDTO.getDesignFee() == null
				&& paramDTO.getLabourFee() == null && paramDTO.getMaterialFee() == null
				) {
			LOGGER.error(LOGPREFIX + "没有可更新的信息");
			return 0;
		}
		// ------参数验证 ->end

		// ------设置更新参数 ->start
		DecoratePrice decoratePrice = new DecoratePrice();
		decoratePrice.setId(paramDTO.getId());
		decoratePrice.setStatus(DecoratePriceConstants.DECORATEPRICE_STATUS_HAVE_BID);
		decoratePrice.setMaterialFee(paramDTO.getMaterialFee() == null ? null : new BigDecimal(paramDTO.getMaterialFee()));
		decoratePrice.setCheckFee(paramDTO.getCheckFee() == null ? null : new BigDecimal(paramDTO.getCheckFee()));
		decoratePrice.setLabourFee(paramDTO.getLabourFee() == null ? null : new BigDecimal(paramDTO.getLabourFee()));
		decoratePrice.setDesignFee(paramDTO.getDesignFee() == null ? null : new BigDecimal(paramDTO.getDesignFee()));
		decoratePrice.setSubmitTime(new Date());
		decoratePrice.setBidUserId(userId);
		// ------设置更新参数 ->end

		return this.update(decoratePrice);
	}

	@Override
	public int update(DecoratePrice decoratePrice) {
		// ------参数验证 ->start
		if(decoratePrice == null) {
			LOGGER.error(LOGPREFIX + "decoratePrice = null");
			return 0;
		}
		// ------参数验证 ->end

		return decoratePriceMapper.updateByPrimaryKeySelective(decoratePrice);
	}

	@Override
	public DecoratePrice get(Long id) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return null;
		}
		// ------参数验证 ->end

		return decoratePriceMapper.selectByPrimaryKey(id);
	}



	/**
	 * 查询方案封面图
	 * @param type 类型
	 * @param planId 方案Id
	 * @return string picPath
	 */
	private  DecorateDesignPlanImgInfo getDesignPlanPicPath(Integer type,Long planId){
		DecorateDesignPlanImgInfo planInfo=null;
		if (ProprietorInfoConstants.DESIGN_PLAN_WHOLE_HOUSE.equals(type)){
			planInfo = decorateDesignPlanImgService.getWholeHouseById(planId);
			if (null!=planInfo){
				return planInfo;
			}
		}
		else if (ProprietorInfoConstants.DESIGN_PLAN_RENDER_SCENE.equals(type)){
			planInfo = decorateDesignPlanImgService.getRecommendedById(planId);
			if (null!=planInfo){
				return planInfo;
			}
		}
		return planInfo;
	}

	@Override
	public void dealWithDecoratePrice() {
		LOGGER.info("{} 开始处理已完成的保价, 执行选举逻辑", LOGPREFIX);
		long startTime = System.currentTimeMillis();
		
		// ------查找报价完成的订单(decorate_price.id) ->start
		List<Long> customerIdList = decorateCustomerService.getOverTimeBidCustomerIdList();
		// ------查找报价完成的订单(decorate_price.id) ->end
		
		// ------执行10家选3家的逻辑 ->start
		if(Lists.isNotEmpty(customerIdList)) {
			customerIdList.forEach(item -> {
				this.checkPriceAndElect(item, true);
			});
		}
		// ------执行10家选3家的逻辑 ->end
		
		LOGGER.info("{} 执行完毕, 共处理了{}个平台派单订单, 耗时: {}s", LOGPREFIX, customerIdList == null ? 0 : customerIdList.size(), (System.currentTimeMillis() - startTime) / 1000);
	}

}
