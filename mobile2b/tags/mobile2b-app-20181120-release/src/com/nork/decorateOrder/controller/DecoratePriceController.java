package com.nork.decorateOrder.controller;

import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.decorateOrder.common.PageHelper;
import com.nork.decorateOrder.constant.DecoratePriceConstants;
import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.decorateOrder.model.dto.DecoratePriceSubmitPriceParamDTO;
import com.nork.decorateOrder.model.input.DecoratePriceListQuery;
import com.nork.decorateOrder.model.output.DecorateCustomerDetail;
import com.nork.decorateOrder.model.output.DecoratePriceDetail;
import com.nork.decorateOrder.model.output.DecoratePriceVO;
import com.nork.decorateOrder.service.DecoratePriceService;
import com.nork.ramCache.service.RAMCacheService;
import com.nork.system.config.SystemConfig;
import com.nork.system.model.SysUser;
import com.sandu.common.LoginContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
* @description： 平台派单相关接口
* @author : WangHaiLin  
* @date : 2018/10/19 17:57  
*
*/
@Controller
@RequestMapping("/{style}/mobile/decoratePrice")
public class DecoratePriceController {

    private static Logger LOGGER = Logger.getLogger(DecoratePriceController.class);

    private final static String LOGPREFIX = "[平台派单模块]:";

    @Autowired
    private DecoratePriceService decoratePriceService;

    @Autowired
    private RAMCacheService ramCacheService;

    /**
     * 平台派单列表查询
     * @param query
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope<DecoratePriceVO> getCustomListInfo(DecoratePriceListQuery query) {
        //校验参数
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null || loginUser.getId() == null) {
            LOGGER.error(LOGPREFIX + "(loginUser == null || loginUser.getId() == null) = true");
            return new ResponseEnvelope<>(false, "请重新登录");
        }
        //获取登录用户所属企业Id
        SysUser user = ramCacheService.getSysUser(loginUser.getId().longValue());
        if (null==user&&null==user.getpCompanyId()){
            return new ResponseEnvelope<>(false, "当前用户所属企业Id查询失败   userId="+loginUser.getId());
        }
        query.setCompanyId(user.getpCompanyId().intValue());

        //获取当前登录用户Id
        query.setUserId(loginUser.getId());
        //处理报价状态
        if (null==query.getPriceStatus()){
            return new ResponseEnvelope<>(false, "请传递PriceStatus");
        }
        List<Integer> statusList = dealStatus(query.getPriceStatus());

        query.setStatusList(statusList);
        LOGGER.error(LOGPREFIX +"========"+statusList );
        try{
            //查询平台派单数量
            int count = decoratePriceService.getCount(query);
            //分页处理
            PageHelper helper = PageHelper.getPage(count, query.getLimit(), query.getStart());
            query.setStart(helper.getStart());
            query.setLimit(helper.getPageSize());

            List<DecoratePriceVO> list=null;
            //平台派单数量大于零时进行列表查询
            if (count>0){
                list= decoratePriceService.getList(query);
            }
            return new ResponseEnvelope<>(true,"none" ,count,list);

        }catch (Exception e){
            LOGGER.error(LOGPREFIX + "(列表查询--getList方法异常 ：)"+e);
            return new ResponseEnvelope<>(false,"系统异常");
        }
    }

    /**
     * 通过订单Id查询客户订单详细信息
     * @param priceId 派单Id
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope<DecoratePriceDetail> getCustomListInfo(Integer priceId) {
        //校验参数
        if( null==priceId) {
            LOGGER.error(LOGPREFIX + "(orderId == null ) = true");
            return new ResponseEnvelope<>(false, "请传递priceId");
        }
        try{
            //查询详情
            DecoratePriceDetail detail = decoratePriceService.getDetailById(priceId.longValue());
            if (null==detail){
                return new ResponseEnvelope<>("查无此信息");
            }
            return new ResponseEnvelope<>(true ,detail);

        }catch (Exception e){
            LOGGER.error(LOGPREFIX + "(派单详情查--getDetail方法询异常 ：)"+e);
            return new ResponseEnvelope<>(false,"系统异常");
        }
    }

    /**
     * 提交报价接口
     * 
     * @author huangsongbo
     * @return
     */
    @RequestMapping(value = "/submitPrice", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope<DecoratePrice> submitPrice(@RequestBody @Valid DecoratePriceSubmitPriceParamDTO paramDTO) {
    	// ------参数验证 ->start
    	// 部分参数已被 @Valid 验证
    	
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
    	
    	try {
			decoratePriceService.submitPrice(paramDTO, loginUser.getId().longValue());
		} catch (BizException e) {
			return new ResponseEnvelope<>(false, e.getMessage());
		}
    	
    	return new ResponseEnvelope<>(true, "报价成功");
    }

    /**
     * 处理 状态
     * @param priceStatus
     * @return
     */
    private List<Integer> dealStatus(Integer priceStatus){
        List<Integer> statusList=null;
        //前端传2则表示（待报价+已报价）
        if (2==priceStatus){
            statusList=new ArrayList<>();
            statusList.add(DecoratePriceConstants.DECORATEPRICE_STATUS_NO_BID);//待报价
            statusList.add(DecoratePriceConstants.DECORATEPRICE_STATUS_HAVE_BID);//已报价
        }
        //前端传3表示（报价超时+未选中）
        else if (3==priceStatus){
            statusList=new ArrayList<>();
            statusList.add(DecoratePriceConstants.DECORATEPRICE_STATUS_UNCHECKED);//未选中
            statusList.add(DecoratePriceConstants.DECORATEPRICE_STATUS_BID_OVERTIME);//超时取消报价
        }
        return statusList;
    }
    
}
