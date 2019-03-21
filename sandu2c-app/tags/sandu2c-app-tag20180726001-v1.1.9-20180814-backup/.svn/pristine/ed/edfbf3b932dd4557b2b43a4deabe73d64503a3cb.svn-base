package com.sandu.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sandu.Thread.CustomerBaseInfoThread;
import com.sandu.cart.service.MallBaseCartService;
import com.sandu.customer.model.CustomerBaseInfo;
import com.sandu.customer.service.CustomerBaseInfoService;
import com.sandu.customer.service.CustomerOperateLogService;
import com.sandu.customer.service.CustomerScoreDetailService;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserFinanceService;

import lombok.extern.slf4j.Slf4j;

/**
 * 计算客户积分
 * 
 * @author Sandu
 * @datetime 2018/7/31 17:39
 */
@Configuration
@EnableScheduling
@Slf4j
public class SchedulingConfig {

	@Resource
	private SysUserService sysUserService;

	@Autowired
	private CustomerBaseInfoService customerBaseInfoService;

	@Autowired
	private MallBaseOrderService mallBaseOrderService;

	@Autowired
	private CustomerOperateLogService customerOperateLogService;

	@Autowired
	private CustomerScoreDetailService customerScoreDetailService;

	@Autowired
	private MallBaseCartService mallBaseCartService;

	@Autowired
	private UserProductCollectService userProductCollectService;

	@Autowired
	private UserFinanceService userFinanceService;

	/**
	 * corePoolSize： 线程池维护线程的最少数量 maximumPoolSize：线程池维护线程的最大数量 keepAliveTime：
	 * 线程池维护线程所允许的空闲时间 unit： 线程池维护线程所允许的空闲时间的单位 workQueue： 线程池所使用的缓冲队列
	 * threadFactory：创建执行线程的工厂 handler： 线程池对拒绝任务的处理策略
	 */
	ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 300, 300, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

	/**
	 * 计算客户积分规则 绑定手机号+1; 收货地址或所在区域+1; 产品替换渲染+2，后每次+1，最大6; 购物车商品（我的购物车）+8; 商品收藏
	 * （我的收藏）+6 购买（我的订单）+10; 购买包年包月（我的服务渲染） +2； 装进我家 +8; 上传户型（我的户型)+6 查看商品详情 + 0.5
	 * 每次+0.5，上限5
	 */
	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(cron = "0 0/5 * * * ? ")
	public void modifyCustomerScore() {
		final int limit = 2000;
		int pageNo = 0;
		List<CustomerBaseInfo> infoList = new ArrayList<CustomerBaseInfo>();
		log.info("modifyCustomerScore同步客户积分开始,pageNo:{},limit:{}", pageNo, limit);
		do {
			try {
				Map<String, Object> infoMap = new HashMap<String, Object>();
				infoMap.put("limitString", "limit " + (pageNo * limit) + ", " + limit);
				infoList = customerBaseInfoService.queryCustomerInfoByMap(infoMap);
				pageNo++;
				executor.submit(new CustomerBaseInfoThread(sysUserService, customerBaseInfoService,
						mallBaseOrderService, customerOperateLogService, customerScoreDetailService,
						mallBaseCartService, userProductCollectService, userFinanceService, infoList));
			} catch (Exception e) {
				log.error("modifyCustomerScore方法执行失败,失败信息{}", e.getMessage());
			}
		} while (infoList.size() > 0);
		log.info("modifyCustomerScore同步客户积分结束,pageNo:{},limit:{}", pageNo, limit);
	}
}
