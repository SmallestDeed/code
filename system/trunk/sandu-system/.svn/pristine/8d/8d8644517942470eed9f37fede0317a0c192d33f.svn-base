package com.sandu.web.act3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sandu.api.act3.model.LuckyWheelAwardMsg;
import com.sandu.api.act3.service.LuckyWheelAwardMsgService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.service.SysUserService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/act3/luckywheel/awardmsg")
@Slf4j
public class LuckyWheelAwardMsgController extends BaseController {
		
    @Autowired
    private LuckyWheelAwardMsgService luckyWheelAwardMsgService;

    @Resource
    private SysUserService userService;
    
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @ApiOperation(value = "中奖消息列表", response = ResponseEnvelope.class)
    @GetMapping("/list") 
    public ResponseEnvelope list(String actId) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			List<LuckyWheelAwardMsg> list = luckyWheelAwardMsgService.listNewest15(actId);
			List<String> listMsg = new ArrayList<String>();
			for(LuckyWheelAwardMsg msg:list) {
				listMsg.add(msg.getMessage());
			}
			return new ResponseEnvelope<>(true, listMsg.size(),listMsg);
			
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    

}
