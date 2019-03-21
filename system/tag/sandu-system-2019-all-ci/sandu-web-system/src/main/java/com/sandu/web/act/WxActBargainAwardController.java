package com.sandu.web.act;

import javax.annotation.Resource;

import com.sandu.api.act.output.WxActBargainAwardMsgWebVO;
import com.sandu.api.act.service.WxActBargainAwardMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sandu.api.act.input.WxActBargainAwardAdd;
import com.sandu.api.act.service.WxActBargainAwardService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.ResponseBo;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "WxActBargainAwardMsg", description = "领奖消息")
@RestController
@RequestMapping("/v1/act/bargain/award")
public class WxActBargainAwardController {
	
	private Logger logger = LoggerFactory.getLogger(WxActBargainAwardController.class);
	
    @Autowired
    private WxActBargainAwardService wxActBargainAwardService;

    @Resource
    private SysUserService userService;

	/*@Autowired
    private WxActBargainAwardMsgService wxActBargainAwardMsgService;*/

    
    @ApiOperation(value = "砍价成功领奖品", response = ResponseEnvelope.class)
    @PostMapping("/addAwardRecord")
    public ResponseEnvelope addAwardRecord(WxActBargainAwardAdd awardAdd) {
		try {
			ResponseEnvelope rsp = validateParams(awardAdd);
			if(rsp!=null) {
				return rsp;
			}
			if(!this.isMobile(awardAdd.getMobile())) {
				return new ResponseEnvelope<>(false, "手机号格式有误!");
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!");
			}
			ResponseBo flag = userService.checkPhone(awardAdd.getMobile(), awardAdd.getValidationCode());
	        if (flag.getStatus()) {
	        	wxActBargainAwardService.addAwardRecord(awardAdd,user);
			}else {
				return new ResponseEnvelope<>(false, flag.getMsg());
			}
			return new ResponseEnvelope<>(true, "领取成功");
    	}catch(SystemException ex) {
    		logger.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
			logger.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^((17[0-9])|(2[0-9][0-9])|(13[0-9])|(15[012356789])|(18[0-9])|(14[57])|(16[0-9])|(19[0-9]))[0-9]{8}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    private ResponseEnvelope validateParams(WxActBargainAwardAdd awardAdd) {
    	if(awardAdd==null) {
    		return new ResponseEnvelope<>(false, "参数不能为空!");
    	}
    	if(StringUtils.isBlank(awardAdd.getRegId())){
    		return new ResponseEnvelope<>(false, "任务id不能为空!");
    	}
    	if(StringUtils.isBlank(awardAdd.getReceiver())){
    		return new ResponseEnvelope<>(false, "收货人不能为空!");
    	}
    	if(awardAdd.getReceiver().length()>10) {
    		return new ResponseEnvelope<>(false, "收货人长度不超过10个字!");
    	}
    	if(StringUtils.isBlank(awardAdd.getMobile())){
    		return new ResponseEnvelope<>(false, "手机号不能为空!");
    	}
    	if(StringUtils.isBlank(awardAdd.getValidationCode())){
    		return new ResponseEnvelope<>(false, "验证码不能为空!");
    	}
    	if(StringUtils.isBlank(awardAdd.getAddress())){
    		return new ResponseEnvelope<>(false, "收货地址不能为空!");
    	}
    	if(awardAdd.getAddress().length()>100) {
    		return new ResponseEnvelope<>(false, "地址长度不超过100个字!");
    	}
    	return null;
	}

	/*@ApiOperation(value = "领奖消息列表查询(随机)", response = ResponseEnvelope.class)
    @GetMapping("/getWxActBargainAwardMsgRandomList")
    public ResponseEnvelope getWxActBargainAwardMsgRandomList(String actId) {

        if (actId==null){
            return new ResponseEnvelope<>(false, "活动Id不能为空！");
        }
        List<WxActBargainAwardMsgWebVO> list = wxActBargainAwardMsgService.getMsgRandomList(actId);
        return new ResponseEnvelope<>(true,"领奖消息列表查询完成",list);
    }*/
     
   
    

}
