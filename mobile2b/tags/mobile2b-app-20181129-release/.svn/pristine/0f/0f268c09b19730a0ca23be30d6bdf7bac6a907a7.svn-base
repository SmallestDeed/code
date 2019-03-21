package com.nork.decorateOrder.service;

import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.common.exception.BizException;
import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.decorateOrder.model.dto.DecoratePriceSubmitPriceParamDTO;
import com.nork.decorateOrder.model.input.DecorateCustomerListQuery;
import com.nork.decorateOrder.model.input.DecoratePriceListQuery;
import com.nork.decorateOrder.model.output.DecoratePriceDetail;
import com.nork.decorateOrder.model.output.DecoratePriceVO;
import com.nork.decorateOrder.model.search.DecoratePriceSearch;

import java.util.List;

/**
* @description： 装修报价
* @author : WangHaiLin  
* @date : 2018/10/20 14:08  
* @param:
* @return:  
*
*/
public interface DecoratePriceService {

	/**
	 * 移动2b端首页显示平台派单数量
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	int getMyOrderCount(Long userId);


	/**
	* 查询我的报价列表
	* @author : WangHaiLin
	* @param query
	* @return java.util.List<com.nork.decorateOrder.model.output.DecoratePriceVO>
	*
	*/
	List<DecoratePriceVO> getList(DecoratePriceListQuery query);

	/**
	 * 查询我的报价数量
	 * @param query 查询入参
	 * @return int 满足条件的数量
	 */
	int getCount(DecoratePriceListQuery query);

	/**
	*  查询我的报价详情
	* @author : WangHaiLin
	* @param priceId 报价单Id
	* @return com.nork.decorateOrder.model.output.DecoratePriceDetail
	*
	*/
	DecoratePriceDetail getDetailById(Long priceId);

	/**
	 * 提交报价,并且检测该订单参与报价的公司是否全部都报价完成, 如果完成, 就开始10家选三家的逻辑
	 *
	 * @author huangsongbo
	 * @param paramDTO
	 * @param longValue
	 * @throws BizException
	 */
	void submitPrice(DecoratePriceSubmitPriceParamDTO paramDTO, Long longValue) throws BizException;

	/**
	 *
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	DecoratePrice get(Long id);

	/**
	 * 更新DecoratePrice的部分信息, 具体信息见DecoratePriceSubmitPriceParamDTO
	 *
	 * @author huangsongbo
	 * @param paramDTO 保价信息
	 * @param userId 用户id
	 * @return
	 */
	int update(DecoratePriceSubmitPriceParamDTO paramDTO, Long userId);

	/**
	 *
	 * @author huangsongbo
	 * @param decoratePrice
	 * @return
	 */
	int update(DecoratePrice decoratePrice);

	/**
	 * 10家选三家逻辑
	 *
	 * @author huangsongbo
	 * @param customerId
	 * @param flag 是否强制执行10家选3家逻辑
	 * if(false) -> 会检测是否还没没报价的公司
	 */
	void checkPriceAndElect(Long customerId, boolean flag);

	/**
	 * 查看此订单还未报价的公司有多少家
	 *
	 * @author huangsongbo
	 * @param customerId
	 * @return
	 */
	int getCountNoBid(Long customerId);

	/**
	 *
	 * @author huangsongbo
	 * @param decoratePriceSearch
	 * @return
	 */
	int getCount(DecoratePriceSearch decoratePriceSearch);

	/**
	 * select * from decorate_price where customer_id = #{customerId}
	 * 
	 * @author huangsongbo
	 * @param customerId
	 * @return
	 */
	List<DecoratePrice> getListByCustomerId(Long customerId);

	/**
	 * 
	 * @author huangsongbo
	 * @param decoratePriceSearch
	 * @return
	 */
	List<DecoratePrice> getList(DecoratePriceSearch decoratePriceSearch);

	/**
	 * 处理报价, 检测已经报价完成的订单, 对这部分订单执行10选3家的逻辑
	 * 
	 * @author huangsongbo
	 */
	void dealWithDecoratePrice();

}
