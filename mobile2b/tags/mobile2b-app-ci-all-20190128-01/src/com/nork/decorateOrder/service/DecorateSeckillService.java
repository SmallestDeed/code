package com.nork.decorateOrder.service;

import java.util.Date;
import java.util.List;

import com.nork.common.exception.BizException;
import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.dto.DecorateSeckillDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetListDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetListParamDTO;
import com.nork.decorateOrder.model.dto.SeckillRecordFromRedisDTO;
import com.nork.decorateOrder.model.search.DecorateSeckillSearch;

public interface DecorateSeckillService {

	/**
	 * 获取抢单详情信息
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 * @throws BizException 
	 */
	DecorateSeckill getDetails(Long id,  Long userId) throws BizException;

	/**
	 * 获取抢单详情信息
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	DecorateSeckill getDetails(Long id);
	
	/**
	 * 根据搜索条件查询DecorateSeckillList
	 * 相比于findList方法, 会获取更多的信息
	 * 
	 * @author huangsongbo
	 * @param decorateSeckillSearch
	 * @return
	 */
	List<DecorateSeckill> findMoreInfoList(DecorateSeckillSearch decorateSeckillSearch);

	/**
	 * 获得抢单订单状态
	 * 
	 * @param status
	 * @param orderStatus
	 * @param orderTimeout
	 * @param isMyOrder 是否是我本人的订单
	 * @return
	 * orderStatus = 0: 待抢购
	 * orderStatus = 1: 已抢购
	 * orderStatus = 2: 待支付
	 */
	Integer getOrderStatus(Integer status, Integer orderStatus, Date orderTimeout, boolean isMyOrder);

	/**
	 * 根据搜索条件查询DecorateSeckillList
	 * 
	 * @author huangsongbo
	 * @param decorateSeckillSearch
	 * @return
	 */
	List<DecorateSeckill> findList(DecorateSeckillSearch decorateSeckillSearch);

	/**
	 * 获取我抢到的并且未支付的并且支付未超时的订单
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	List<DecorateSeckill> getDecorateSeckillListNoPay(Long userId);

	/**
	 * 移动2b端首页限时快抢数量
	 * 
	 * @author huangsongbo
	 * @return
	 */
	int getMySeckillCount();

	/**
	 * getCount公共方法
	 * 
	 * @author huangsongbo
	 * @param decorateSeckillSearch
	 * @return
	 */
	int getCount(DecorateSeckillSearch decorateSeckillSearch);

	/**
	 * 获取移动2b端首页展示数据
	 * 
	 * @author huangsongbo
	 * @param userId 用户id
	 * @return
	 * @throws BizException 
	 */
	DecorateSeckillGetListDTO getHomePageInfo(Long userId) throws BizException;

	/**
	 * 制定查notBeingSnappedCount条未抢购数据 + beingSnappedCount条已抢购数据
	 * 
	 * @author huangsongbo
	 * @param notBeingSnappedCount
	 * @param beingSnappedCount
	 * @return
	 */
	List<DecorateSeckill> getHomePageDecorateSeckillList(Integer notBeingSnappedCount, Integer beingSnappedCount);

	/**
	 * 限时快抢列表接口
	 * 
	 * @param paramDTO 搜索条件bean
	 * @param userId
	 * @return
	 */
	List<DecorateSeckill> findList(DecorateSeckillGetListParamDTO paramDTO, Long userId);

	/**
	 * List<DecorateSeckill> -> List<DecorateSeckillDTO>
	 * 
	 * @author huangsongbo
	 * @param decorateSeckillList
	 * @return
	 */
	List<DecorateSeckillDTO> getDecorateSeckillDTOList(List<DecorateSeckill> decorateSeckillList);

	/**
	 * 抢单
	 * 识别update条数, 如果count > 0 代表抢单成功
	 * 原理: 利用mysql update 时的行级锁
	 * 
	 * @author huangsongbo
	 * @param id
	 * @param longValue
	 * @return
	 * @throws BizException 
	 */
	int seckill(Long id, Long longValue) throws BizException;

	/**
	 * update #{decorateSeckill} where id = #{id} and stauts = #{status}
	 * 
	 * @author huangsongbo
	 * @param decorateSeckill
	 * @param id
	 * @param decorateseckillsearchStatusNotBeingSnappedUp
	 * @return
	 * @throws BizException 
	 */
	int update(DecorateSeckill decorateSeckill, Long id, Integer status) throws BizException;

	/**
	 * 同步订单信息
	 * insert data to table decorate_seckill_order and table decorate_order
	 * update decorate_customer.first_seckill_company
	 * 
	 * @author huangsongbo
	 * @param id 订单id(decorateSeckill.id)
	 * @param userId 用户id
	 */
	void syncOrder(Long id, Long userId);

	/**
	 * select * from decorate_seckill where id = #{id}
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	DecorateSeckill get(Long id);

	/**
	 * 获取一条SeckillRecordFromRedisDTO, 会存在redis中用来记录用户当日抢单记录, 用来计算该用户当日剩余抢单次数
	 * 
	 * @author huangsongbo
	 * @param seckillrecordfromredisdtoStatusNopay
	 * @return
	 */
	SeckillRecordFromRedisDTO getSeckillRecordFromRedisDTO(Integer status);

	/**
	 * insert 一条抢单记录到redis里, 用来统计该用户今日剩余抢单次数
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @param seckillRecordFromRedisDTO
	 */
	void setSeckillRecord(Long userId, SeckillRecordFromRedisDTO seckillRecordFromRedisDTO);

	/**
	 * insert 一条抢单记录到redis里, 用来统计该用户今日剩余抢单次数
	 * 此方法为返回该条redis记录的key
	 * 
	 * @param userId
	 * @return
	 */
	String getSeckillRecordRedisKey(Long userId);

	/**
	 * 获取用户今日抢单剩余次数
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	int getSeckillResidueCount(Long userId);

	/**
	 * copy一条抢单数据
	 * 问:copy哪条?
	 * 答:id = #{id} 的那条抢单数据
	 * 
	 * @author huangsongbo
	 * @param seckillOrderIdList
	 */
	void copy(List<Long> seckillOrderIdList);
	
	/**
	 * 
	 * @author huangsongbo
	 * @param decorateSeckill
	 */
	void add(DecorateSeckill decorateSeckill);

	/**
	 * update decorate_seckill set userId = #{userId}, status = #{status} where id in #{idList}
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @param status
	 * @param idList
	 * @return 
	 */
	int update(Long userId, Integer status, List<Long> idList);

	/**
	 * select idList by seckillOrderIdList
	 * then
	 * update decorate_seckill set userId = #{userId}, status = #{status} where id in #{idList}
	 * 
	 * @param userId
	 * @param status
	 * @param seckillOrderIdList
	 */
	int updateBySeckillOrderIdList(Long userId, Integer status, List<Long> seckillOrderIdList);

	/**
	 * 修改抢单状态
	 * 
	 * @author huangsongbo
	 * @param id 抢单id
	 * @param status
	 * status = 8: 变更为已取消
	 * 
	 * @param userId 登录用户id
	 * @throws BizException 
	 */
	void updateStatus(Long id, Integer status, Long userId) throws BizException;

	/**
	 * 
	 * @author huangsongbo
	 * @param id
	 * @param status
	 */
	void updateStatusById(Long id, Integer status);

	/**
	 * 
	 * @author huangsongbo
	 * @param decorateSeckillForUpdate
	 */
	void update(DecorateSeckill decorateSeckillForUpdate);

	/**
	 * 重置用户的今日抢单次数(用户userId = #{userId})
	 * 
	 * @author huangsongbo
	 * @param userId
	 */
	void resetSeckillTimes(Long userId);

	/**
	 * 重置用户的今日抢单次数(用户userId in #{userIdList})
	 * 
	 * @author huangsongbo
	 * @param userIdList
	 */
	void resetSeckillTimes(List<Long> userIdList);

}
