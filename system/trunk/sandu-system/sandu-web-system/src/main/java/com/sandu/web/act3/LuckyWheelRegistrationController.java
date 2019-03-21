package com.sandu.web.act3;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sandu.api.act3.output.LuckyWheelLotteryVO;
import com.sandu.api.act3.service.LuckyWheelRegistrationService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/act3/luckywheel/reg")
@Slf4j
public class LuckyWheelRegistrationController {
	
    @Autowired
    private LuckyWheelRegistrationService luckyWheelRegistrationService;
    
    @Resource
    private SysUserService userService;
    
    
    @ApiOperation(value = "抽奖", response = ResponseEnvelope.class)
    @PostMapping("/lottery")
    public ResponseEnvelope lottery(String actId) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			//SysUser user = userService.get(59031);
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			LuckyWheelLotteryVO retVo = luckyWheelRegistrationService.lottery(actId,user);
			return new ResponseEnvelope<>(true,retVo);
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    @ApiOperation(value = "增加抽奖次数", response = ResponseEnvelope.class)
    @PostMapping("/increaseLotteryCount")
    public ResponseEnvelope increaseLotteryCount(String actId) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			//返回砍价金额
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			luckyWheelRegistrationService.increaseLotteryCount(actId,user);
			
			return new ResponseEnvelope<>(true, "操作成功");
    	}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
   

}
