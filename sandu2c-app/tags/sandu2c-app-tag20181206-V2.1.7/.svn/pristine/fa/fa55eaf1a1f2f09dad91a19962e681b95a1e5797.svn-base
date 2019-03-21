package com.sandu.Thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.sandu.cart.MallBaseCart;
import com.sandu.cart.service.MallBaseCartService;
import com.sandu.customer.model.CustomerBaseInfo;
import com.sandu.customer.model.CustomerScoreDetail;
import com.sandu.customer.service.CustomerBaseInfoService;
import com.sandu.customer.service.CustomerOperateLogService;
import com.sandu.customer.service.CustomerScoreDetailService;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.user.model.UserVo;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserFinanceService;
import com.sandu.useraddress.MallUserAddress;

import lombok.extern.slf4j.Slf4j;

/**
 * 同步用户积分线程类
 * @author Sandu
 */
@Slf4j
public class CustomerBaseInfoThread implements Runnable {

	private SysUserService sysUserService;

	private CustomerBaseInfoService customerBaseInfoService;

	private MallBaseOrderService mallBaseOrderService;

	private CustomerOperateLogService customerOperateLogService;

	private CustomerScoreDetailService customerScoreDetailService;

	private MallBaseCartService mallBaseCartService;

	private UserProductCollectService userProductCollectService;

	private UserFinanceService userFinanceService;

	private List<CustomerBaseInfo> infoList;

	public CustomerBaseInfoThread(SysUserService sysUserService, CustomerBaseInfoService customerBaseInfoService,
			MallBaseOrderService mallBaseOrderService, CustomerOperateLogService customerOperateLogService,
			CustomerScoreDetailService customerScoreDetailService, MallBaseCartService mallBaseCartService,
			UserProductCollectService userProductCollectService, UserFinanceService userFinanceService,
			List<CustomerBaseInfo> infoList) {
		super();
		this.sysUserService = sysUserService;
		this.customerBaseInfoService = customerBaseInfoService;
		this.mallBaseOrderService = mallBaseOrderService;
		this.customerOperateLogService = customerOperateLogService;
		this.customerScoreDetailService = customerScoreDetailService;
		this.mallBaseCartService = mallBaseCartService;
		this.userProductCollectService = userProductCollectService;
		this.userFinanceService = userFinanceService;
		this.infoList = infoList;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		log.info("CustomerBaseInfoThread线程开始执行，开始时间{}", new Date());
		List<CustomerBaseInfo> updateList = new ArrayList<CustomerBaseInfo>();
		if (infoList != null && infoList.size() > 0) {
			for (CustomerBaseInfo customerBaseInfo : infoList) {
				long start = System.currentTimeMillis();
//				log.info("CustomerBaseInfoThread开始处理用户:{}", customerBaseInfo.getUserId());
				Map<String, Object> scoreDetailMap = new HashMap<String, Object>();
				List<CustomerScoreDetail> detailList = new ArrayList<CustomerScoreDetail>();
				try {
					double score = 0.00;
					StringBuffer sb = new StringBuffer();
					sb.append("[userId]:" + customerBaseInfo.getUserId());
					// 绑定手机号+1;
					UserVo user = sysUserService.getUserInfo(customerBaseInfo.getUserId());
					if (user != null && StringUtils.isNoneBlank(user.getMobile())) {
						score += 1;
						sb.append(",");
						sb.append("[1-手机号积分]:" + 1);
						scoreDetailMap.put("1", "1");
					}

					// 收货地址或所在区域+1;
					List<MallUserAddress> addrList = mallBaseOrderService
							.getAddressByUserId(customerBaseInfo.getUserId());
					if (addrList != null && addrList.size() > 0) {
						score += 1;
						sb.append(",");
						sb.append("[2-收货地址或所在区域积分]:" + 1);
						scoreDetailMap.put("2", "1");
					}

					// 产品替换渲染+2，后每次+1，最大6;
					int replaceCount = customerOperateLogService
							.queryAutoRenderTaskStateCount(customerBaseInfo.getUserId(), "1");
					if (replaceCount > 0) {
						int replaceScore = 0;
						if (replaceCount > 5) {
							score += 6;
							replaceScore = 6;
						} else {
							score += (2 + (replaceCount - 1) * 1);
							replaceScore = 2 + (replaceCount - 1) * 1;

						}
						sb.append(",");
						sb.append("[3-产品替换渲染]:" + replaceScore);
						scoreDetailMap.put("3", replaceScore);
					}

					// 购物车商品（我的购物车）+8;
					MallBaseCart baseCart = mallBaseCartService.getByUserId(customerBaseInfo.getUserId());
					if (baseCart != null) {
						score += 8;
						sb.append(",");
						sb.append("[4-购物车商品（我的购物车）]:" + 8);
						scoreDetailMap.put("4", 8);
					}

					// 商品收藏 （我的收藏）+6
					int count = userProductCollectService.getFarivorateCollectCount(customerBaseInfo.getUserId());
					if (count > 0) {
						score += 6;
						sb.append(",");
						sb.append("[5-商品收藏 （我的收藏）]:" + 6);
						scoreDetailMap.put("5", 6);
					}

					// 购买（我的订单）+10;
					int countOrder = mallBaseOrderService.countUserOrder(customerBaseInfo.getUserId(), 2);
					if (countOrder > 0) {
						score += 10;
						sb.append(",");
						sb.append("[6-购买（我的订单）]:" + 10);
						scoreDetailMap.put("6", 10);
					}

					// 购买包年包月（我的服务渲染） +2；
					int countPayModel = customerOperateLogService.queryPayModelGroupRef(customerBaseInfo.getUserId());
					if (countPayModel > 0) {
						score += 2;
						sb.append(",");
						sb.append("[7-购买包年包月（我的服务渲染）]:" + 2);
						scoreDetailMap.put("7", 2);
					}

					// 装进我家 +8;
					int decroateCount = customerOperateLogService
							.queryAutoRenderTaskStateCount(customerBaseInfo.getUserId(), "0");
					if (decroateCount > 0) {
						score += 8;
						sb.append(",");
						sb.append("[8-装进我家]:" + 8);
						scoreDetailMap.put("8", 8);
					}

					// 上传户型（我的户型)+6
					Integer userAlreadyBoughtHouseCount = userFinanceService
							.queryUserAlreadyBoughtHouseCount(customerBaseInfo.getUserId());
					if (userAlreadyBoughtHouseCount > 0) {
						score += 6;
						sb.append(",");
						sb.append("[9-上传户型（我的户型)]:" + 6);
						scoreDetailMap.put("9", 6);
					}

					// 查看商品详情 + 0.5 每次+0.5，上限5
					int goodsCount = customerOperateLogService.queryCustomerOperateLog(customerBaseInfo.getUserId(),
							"2");
					if (goodsCount > 0) {
						double goodsScore = 0;
						if (goodsCount > 10) {
							score += 5;
							goodsScore = 5;
						} else {
							score += goodsCount * 0.5;
							goodsScore = goodsCount * 0.5;
						}
						sb.append(",");
						sb.append("[10-查看商品详情]:" + goodsScore);
						scoreDetailMap.put("10", goodsScore);
					}
//					log.info("用户ID:{},积分详情:{}", customerBaseInfo.getUserId(), sb.toString());
//					log.info("用户ID:{},总积分:{}", customerBaseInfo.getUserId(), score);
					// 同步客户积分
					if (score > 0) {
						CustomerBaseInfo insertInfoVo = new CustomerBaseInfo();
						insertInfoVo.setUserId(customerBaseInfo.getUserId());
						insertInfoVo.setScore(score);
						if(score > 0 && score < 8) {
							insertInfoVo.setLevel((byte) 0);
						}else if(score >= 8 && score < 15) {
							insertInfoVo.setLevel((byte) 1);
						}else if(score >= 15 && score < 25) {
							insertInfoVo.setLevel((byte) 2);
						}else if(score >= 25) {
							insertInfoVo.setLevel((byte) 3);
						}
						updateList.add(insertInfoVo);
					}
					if (!scoreDetailMap.isEmpty()) {
						detailList = assembleData(scoreDetailMap, customerBaseInfo.getUserId());
					}
					if (detailList != null && detailList.size() > 0) {
						// 先删除，再批量新增
						customerScoreDetailService.deleteDetailByUserId(customerBaseInfo.getUserId());
						customerScoreDetailService.batchInsert(detailList);
					}
				} catch (Exception e) {
					log.error("同步客户：{}积分失败,失败信息:{}", customerBaseInfo.getUserId(), e);
				}
				long end = System.currentTimeMillis();
//				log.info("CustomerBaseInfoThread处理用户:{}结束, 耗时:{}ms", customerBaseInfo.getUserId(),(end-start));
			}
		}
		// 执行批量更新
		if (updateList != null && updateList.size() > 0) {
			customerBaseInfoService.batchUpdate(updateList);
		}
		long endTime = System.currentTimeMillis();
//		log.info("CustomerBaseInfoThread线程执行结束，结束时间{},本次耗时:{}ms", new Date(),(endTime-startTime));
	}

	/**
	 * 组装CustomerScoreDetail数据
	 * 
	 * @param scoreDetailMap
	 * @param userId
	 * @return
	 */
	private List<CustomerScoreDetail> assembleData(Map<String, Object> scoreDetailMap, Integer userId) {
		Iterator<Map.Entry<String, Object>> entries = scoreDetailMap.entrySet().iterator();
		List<CustomerScoreDetail> detailList = new ArrayList<CustomerScoreDetail>();
		while (entries.hasNext()) {
			CustomerScoreDetail detail = new CustomerScoreDetail();
			Map.Entry<String, Object> entry = entries.next();
			String operateType = entry.getKey();
			Double levelScore = Double.valueOf(entry.getValue().toString());
			detail.setUserId(Long.valueOf(userId));
			detail.setCreator(String.valueOf(userId));
			detail.setGmtCreate(new Date());
			detail.setModifier(String.valueOf(userId));
			detail.setGmtModified(new Date());
			detail.setIsDeleted(0);
			detail.setScore(levelScore);
			String businessType = "";
			switch (operateType) {
			case "1":
				businessType = "绑定手机号";
				break;
			case "2":
				businessType = "收货地址";
				break;
			case "3":
				businessType = "产品替换渲染";
				break;
			case "4":
				businessType = "购物车商品";
				break;
			case "5":
				businessType = "商品收藏 (我的收藏)";
				break;
			case "6":
				businessType = "购买(我的订单)";
				break;
			case "7":
				businessType = "购买包年包月(我的服务渲染)";
				break;
			case "8":
				businessType = "装进我家";
				break;
			case "9":
				businessType = "上传户型(我的户型)";
				break;
			case "10":
				businessType = "查看商品详情";
				break;
			default:
				break;
			}
			detail.setBusinessType(businessType);
			detail.setOperateType(new Byte(operateType));
			detailList.add(detail);
		}
		return detailList;
	}
}
