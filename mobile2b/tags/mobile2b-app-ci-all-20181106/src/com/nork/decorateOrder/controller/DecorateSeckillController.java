package com.nork.decorateOrder.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nork.common.async.ExecutorServiceUtils;
import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.decorateOrder.config.DecorateOrderConfig;
import com.nork.decorateOrder.constant.DecorateSeckillConstants;
import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.ProprietorInfo;
import com.nork.decorateOrder.model.dto.DecorateSeckillDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetDetailsDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetListDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillGetListParamDTO;
import com.nork.decorateOrder.model.dto.DecorateSeckillSeckillResultDTO;
import com.nork.decorateOrder.service.DecorateSeckillService;
import com.nork.system.config.SystemConfig;
import com.sandu.common.LoginContext;

@Controller
@RequestMapping("/{style}/mobile/decorateSeckill")
public class DecorateSeckillController {

	private final static Logger LOGGER = LoggerFactory.getLogger(DecorateSeckillController.class);
	
	private final static String LOGPREFIX = "[限时快抢订单模块]:";
	
	@Autowired
	private DecorateSeckillService decorateSeckillService;

	/**
	 * 获取移动2b首页展示数据
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@RequestMapping(value = "/getHomePageInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEnvelope<DecorateSeckillGetListDTO> getHomePageInfo() {
		
		// ------参数验证 ->start
		LoginUser loginUser = null;
		if(SystemConfig.DEBUGMODEL) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	loginUser = Utils.getDebugUser(request);
		}else {
			loginUser = LoginContext.getLoginUser(LoginUser.class);
		}
		if(loginUser == null || loginUser.getId() == null) {
			LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
			return new ResponseEnvelope<>(false, "请重新登录");
		}
		// ------参数验证 ->end
		
		DecorateSeckillGetListDTO decorateSeckillGetListDTO = null;
		try {
			decorateSeckillGetListDTO = decorateSeckillService.getHomePageInfo(loginUser.getId().longValue());
		} catch (BizException e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		return new ResponseEnvelope<>(true, decorateSeckillGetListDTO);
	}
	
	/**
	 * 移动2b端抢单列表
	 * 
	 * @param cityCode 
	 * @param decorateBudgetValue
	 * @param orderType
	 * orderType = 1: 会把我的抢到的,并且未支付的,并且未支付超时的订单 排到最前面
	 * 默认排序: 未抢订单 > 已抢订单
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEnvelope<DecorateSeckillDTO> getList(
			@RequestBody @Valid DecorateSeckillGetListParamDTO paramDTO
			){
		// ------参数验证 ->start
		if(paramDTO == null) {
			return new ResponseEnvelope<>(false, "传参异常");
		}
		
		LoginUser loginUser = null;
		if(SystemConfig.DEBUGMODEL) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	loginUser = Utils.getDebugUser(request);
		}else {
			loginUser = LoginContext.getLoginUser(LoginUser.class);
		}
		if(loginUser == null || loginUser.getId() == null) {
			LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
			return new ResponseEnvelope<>(false, "请重新登录");
		}
		
		if(paramDTO.getOrderType() == null) {
			paramDTO.setOrderType(DecorateSeckillConstants.DECORATESECKILLSEARCH_ORDERTYPE_NON_PAYMENT_ORDER_PRIORITY);
		}
		
		if(StringUtils.isEmpty(paramDTO.getCityCode())) {
			paramDTO.setCityCode(null);
		}
		if(paramDTO.getDecorateBudgetValue() != null && paramDTO.getDecorateBudgetValue().intValue() == -1) {
			paramDTO.setDecorateBudgetValue(null);
		}
		// ------参数验证 ->end
		
		List<DecorateSeckill> decorateSeckillList = decorateSeckillService.findList(paramDTO, loginUser.getId().longValue());
		List<DecorateSeckillDTO> decorateSeckillDTOList = decorateSeckillService.getDecorateSeckillDTOList(decorateSeckillList);
		
		return new ResponseEnvelope<>(true, decorateSeckillDTOList);
	}
	
	/**
	 * 获取限时快抢订单详情
	 * 
	 * @author huangsongbo
	 * @param id 抢单id
	 * @return
	 */
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEnvelope<DecorateSeckillGetDetailsDTO> getDetails(
			Long id
			) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return new ResponseEnvelope<>(false, "参数: id 不能为空");
		}
		
		LoginUser loginUser = null;
		if(SystemConfig.DEBUGMODEL) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	loginUser = Utils.getDebugUser(request);
		}else {
			loginUser = LoginContext.getLoginUser(LoginUser.class);
		}
		if(loginUser == null || loginUser.getId() == null) {
			LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
			return new ResponseEnvelope<>(false, "请重新登录");
		}
		// ------参数验证 ->end
		
		DecorateSeckill decorateSeckill = null;
		try {
			decorateSeckill = decorateSeckillService.getDetails(id, loginUser.getId().longValue());
		} catch (BizException e) {
			LOGGER.error("{} {}", LOGPREFIX, e.getMessage());
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		DecorateSeckillGetDetailsDTO decorateSeckillGetDetailsDTO = this.getDecorateSeckillGetDetailsDTO(decorateSeckill);
		
		// ------查询用户余额 ->start
		// 由https://zhifu.sanduspace.com/v1/web/system/sysUser/getUserBalance 该接口查余额数据
		// ------查询用户余额 ->end
		
		return new ResponseEnvelope<>(true, decorateSeckillGetDetailsDTO);
	}
	
	/**
	 * 抢单接口
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/seckill", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEnvelope<DecorateSeckillSeckillResultDTO> seckill(
			Long id
			) {
		// ------参数验证 ->start
		if(id == null) {
			LOGGER.error(LOGPREFIX + "id = null");
			return new ResponseEnvelope<>(false, "参数: id 不能为空");
		}
		
		LoginUser loginUser = null;
		if(SystemConfig.DEBUGMODEL) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	loginUser = Utils.getDebugUser(request);
		}else {
			loginUser = LoginContext.getLoginUser(LoginUser.class);
		}
		if(loginUser == null || loginUser.getId() == null) {
			LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
			return new ResponseEnvelope<>(false, "请重新登录");
		}
		
		Long userId = loginUser.getId().longValue();
		// ------参数验证 ->end
		
		// ------查看该单的状态是不是已被抢,if(true) -> return 抢单失败 ->start
		// 不要此逻辑也不会出错
		// ------查看该单的状态是不是已被抢,if(true) -> return 抢单失败 ->end
		
		// ------检测该用户的今日剩余抢单次数, 如果没有次数了,return 抢单失败 ->start
		int count = decorateSeckillService.getSeckillResidueCount(loginUser.getId().longValue());
		if(count == 0) {
			return new ResponseEnvelope<>(true, new DecorateSeckillSeckillResultDTO(
					DecorateSeckillConstants.DECORATESECKILLSECKILLRESULTDTO_STATUS_FAILED_NO_CHANCE, 
					"抢单失败, 今日抢单次数已经用完", null
					));
		}
		// ------检测该用户的今日剩余抢单次数, 如果没有次数了,return 抢单失败 ->end
		
		// ------抢单逻辑 ->start
		int updateCount;
		try {
			updateCount = decorateSeckillService.seckill(id, userId);
		} catch (BizException e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}
		// ------抢单逻辑 ->end
		
		// 抢单成功后续逻辑, decorate_order 和 decorate_seckill_order 插入数据
		if(updateCount > 0) {
			
			// ------insert decorate_order 和 decorate_seckill_order 这两张表 记录抢单的一些信息 ->start
			// 取消异步,因为前段有个很快的动作: 抢到之后立马会查订单状态, 如果没有及时执行异步里的方法, 该类型出现"待支付"的订单显示状态为:"已抢购"
			// 采用异步
			/*ExecutorServiceUtils.getInstance().execute(new Runnable() {
				
				@Override
				public void run() {*/
					decorateSeckillService.syncOrder(id, userId);
				/*}
				
			});*/
			// ------insert decorate_order 和 decorate_seckill_order 这两张表 记录抢单的一些信息 ->start
			
			// 抢到了
			return new ResponseEnvelope<>(true, new DecorateSeckillSeckillResultDTO(
					DecorateSeckillConstants.DECORATESECKILLSECKILLRESULTDTO_STATUS_SUCCESS, 
					"抢单成功",
					DecorateOrderConfig.DECORATESECKILL_PAYMENT_TIMEOUT_MINUTE * 60 * 1000L
					));
		}else {
			// 没有抢到
			return new ResponseEnvelope<>(true, new DecorateSeckillSeckillResultDTO(
					DecorateSeckillConstants.DECORATESECKILLSECKILLRESULTDTO_STATUS_FAILED, 
					"客官, 来迟了一步",
					null
					));
		}
	}
	
	/**
	 * 获取该用户今日的抢单剩余次数
	 * 
	 * @author huangsongbo
	 * @return
	 */
	@RequestMapping(value = "/getSeckillResidueCount", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEnvelope<String> getSeckillResidueCount(){
		// ------参数验证 ->start
		LoginUser loginUser = null;
		if(SystemConfig.DEBUGMODEL) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	loginUser = Utils.getDebugUser(request);
		}else {
			loginUser = LoginContext.getLoginUser(LoginUser.class);
		}
		if(loginUser == null || loginUser.getId() == null) {
			LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
			return new ResponseEnvelope<>(false, "请重新登录");
		}
		// ------参数验证 ->end
		
		int count = decorateSeckillService.getSeckillResidueCount(loginUser.getId().longValue());
		
		ResponseEnvelope<String> result = new ResponseEnvelope<String>();
		result.setSuccess(true);
		result.setTotalCount(count);
		
		return result;
	}
	
	/**
	 * 查询出的decorateSeckill转化为返回前端的dto
	 * 
	 * @author huangsongbo
	 * @param decorateSeckill
	 * @return
	 */
	private DecorateSeckillGetDetailsDTO getDecorateSeckillGetDetailsDTO(DecorateSeckill decorateSeckill) {
		// ------参数验证 ->start
		if(decorateSeckill == null) {
			LOGGER.error(LOGPREFIX + "decorateSeckill = null");
			return null;
		}
		// ------参数验证 ->end
		
		DecorateSeckillGetDetailsDTO decorateSeckillGetDetailsDTO = new DecorateSeckillGetDetailsDTO();
		decorateSeckillGetDetailsDTO.setOrderStatus(decorateSeckill.getResultStatus());
		decorateSeckillGetDetailsDTO.setPriceInfo(((decorateSeckill.getPrice() == null ? new BigDecimal(0) : decorateSeckill.getPrice()).multiply(new BigDecimal(10))).intValue() + "度币");
		decorateSeckillGetDetailsDTO.setId(decorateSeckill.getId());
		decorateSeckillGetDetailsDTO.setRemainingTime(decorateSeckill.getRemainingTime());
		
		ProprietorInfo proprietorInfo = decorateSeckill.getProprietorInfo();
		if(proprietorInfo != null) {
			decorateSeckillGetDetailsDTO.setBudgetInfo(proprietorInfo.getDecorateBudgetInfo());
			decorateSeckillGetDetailsDTO.setClientSource(proprietorInfo.getClientSource());
			// eg: 新房装修; 现代简约; 半包
			decorateSeckillGetDetailsDTO.setDecorateInfo(
					proprietorInfo.getDecorateHouseTypeInfo() + "; " + 
					proprietorInfo.getDecorateStyleInfo() + "; " + 
					proprietorInfo.getDecorateTypeInfo()
					);
			// eg: 120㎡; 3室2厅2卫
			decorateSeckillGetDetailsDTO.setHouseInfo(
					proprietorInfo.getHouseAcreage() + "㎡; " + 
					proprietorInfo.getBedroomNum() + "室" +
					proprietorInfo.getLivingRoomNum() + "厅" +
					proprietorInfo.getToiletNum() + "卫"
					);
			// eg: 广东深圳; 万科金域华府
			decorateSeckillGetDetailsDTO.setPositionInfo(
					proprietorInfo.getProvinceInfo() + 
					proprietorInfo.getCityInfo() + "; " +
					proprietorInfo.getAreaName()
					);
			decorateSeckillGetDetailsDTO.setRemark(proprietorInfo.getRemark() == null ? "" : proprietorInfo.getRemark());
			decorateSeckillGetDetailsDTO.setUserName(proprietorInfo.getUserName());
			decorateSeckillGetDetailsDTO.setMobile(com.nork.decorateOrder.common.Utils.delMobile(proprietorInfo.getMobile()));
		}
		
		return decorateSeckillGetDetailsDTO;
	}
	
}
