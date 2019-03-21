package com.sandu.web.act2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.sandu.api.act.model.WxActBargain;
import com.sandu.api.act2.input.ActRedPacketAdd;
import com.sandu.api.act2.input.ActRedPacketQuery;
import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act2.output.PageListRedPacketVO;
import com.sandu.api.act2.output.RedPacketActAndRegDetailsVO;
import com.sandu.api.act2.service.RedPacketService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.util.UUIDUtil;
import com.sandu.web.BaseController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/act2/redpacket")
@Slf4j
public class RedPacketController extends BaseController {
		
    @Autowired
    private RedPacketService redPacketService;

    @Resource
    private SysUserService userService;
    
    @Value("${wx.act.redPacket.url}")
    private String actRedPacketUrl;
    
    
    
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @ApiOperation(value = "获取红包活动及用户任务详情", response = ResponseEnvelope.class)
    @GetMapping("/getUserRedPacketActAndRegDetails")
    public ResponseEnvelope getUserRedPacketActAndRegDetails(String actId) {
    	try {
			if(StringUtils.isBlank(actId)) {
				return new ResponseEnvelope<>(false, "活动id不能为空!"); 
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			RedPacketActAndRegDetailsVO retVo = redPacketService.getUserRedPacketActAndRegDetails(actId, user);
			return new ResponseEnvelope<>(true, retVo);
			
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		if("ACT_NOT_EXIST".equals(ex.getErrorCode())) {
    			ResponseEnvelope rsp = new ResponseEnvelope<>(false, ex.getMessage());
    			rsp.setExceptionCode(404);
    			return rsp;
    		}
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    
    
    @ApiOperation(value = "查询红包活动列表", response = ResponseEnvelope.class)
    @GetMapping("/list")
    public ResponseEnvelope list(ActRedPacketQuery query) {
    	try {
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			PageInfo<RedPacket> pageList = redPacketService.list(query);
			PageInfo<PageListRedPacketVO> retPageList = buildPageResult(pageList);
			return new ResponseEnvelope<>(true, retPageList.getTotal(),retPageList.getList());
		}catch(SystemException ex) {
    		log.warn("业务异常:"+ex.getMessage());
    		return new ResponseEnvelope<>(false, ex.getMessage());
    	}catch(Exception ex) {
    		log.error("系统异常:",ex);
			return new ResponseEnvelope<>(false, "系统异常!");
		}
    }
    
    private PageInfo<PageListRedPacketVO> buildPageResult(PageInfo<RedPacket> pageList){
    	PageInfo<PageListRedPacketVO> retList = new PageInfo<PageListRedPacketVO>();
    	List<PageListRedPacketVO> list = new ArrayList<PageListRedPacketVO>();
    	if(pageList!=null && pageList.getList()!=null&& pageList.getList().size()>0) {
    		for(RedPacket record : pageList.getList()) {
    			list.add(
    					new PageListRedPacketVO(
    							record.getId(),
    							record.getActName(),
    							sdf.format(record.getBeginTime()),
    							sdf.format(record.getEndTime()),
    							redPacketService.getStatus(record),
    							record.getTotalAmount(),
    							actRedPacketUrl)
    					       // actRedPacketUrl + "?actId=" + record.getId())
    					);
    		}
    	}
    	retList.setTotal(pageList.getTotal());
    	retList.setList(list);
    	return retList;    	
    }
    
    @ApiOperation(value = "创建红包活动", response = ResponseEnvelope.class)
    @PostMapping("/create")
    public ResponseEnvelope create(@Valid ActRedPacketAdd actRedPacketAdd, BindingResult validResult) {
        try {
            //验证参数
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult,new ResponseEnvelope());
            }
            
            if(actRedPacketAdd.getActName().length()>=50) {
            	 return new ResponseEnvelope(false,"活动名称不能超过50个字");
            }
            Date beginTime = null;
            Date endTime = null;
            
            try {
            	beginTime = sdf.parse(actRedPacketAdd.getBeginTime());
            }catch(Exception ex) {
            	 return new ResponseEnvelope(false,"开始时间不合法");
            }
            
            try {
            	 endTime = sdf.parse(actRedPacketAdd.getEndTime());
            }catch(Exception ex) {
            	 return new ResponseEnvelope(false,"结束时间不合法");
            }
           
            
           /* if(beginTime.compareTo(new Date())<0) {
            	 return new ResponseEnvelope(false,"活动开始时间必须大于当前时间");
            }*/
            if(actRedPacketAdd.getActAmount()<=0) {
           	 	return new ResponseEnvelope(false,"活动预算必须大于0");
            }
            if(actRedPacketAdd.getActAmount()>99999.99) {
            	 return new ResponseEnvelope(false,"活动预算不能超过99999.99");
            }
            
            if (endTime.compareTo(beginTime) < 0){
                return new ResponseEnvelope(false,"活动结束时间不能晚于活动开始时间");
            }
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            SysUser user = userService.get(loginUser.getId());
			if(user==null) {
				return new ResponseEnvelope<>(false, "用户不存在!"); 
			}
			
            RedPacket redPacket = this.buildRedPacketEntity(actRedPacketAdd,beginTime,endTime,user);
            redPacketService.create(redPacket);
            return new ResponseEnvelope<>(true, "保存成功!");
        } catch (Exception ex) {
            log.error("系统错误",ex);
            return new ResponseEnvelope<>(false, "保存失败!");
        }
    }


	private RedPacket buildRedPacketEntity(ActRedPacketAdd actRedPacketAdd,Date beginTime,Date endTime, SysUser user) {
		Date now = new Date();
		RedPacket redPacket = new RedPacket();
		redPacket.setId(UUIDUtil.getUUID());
		redPacket.setActName(actRedPacketAdd.getActName());
		redPacket.setActRule("");
		redPacket.setBeginTime(beginTime);
		redPacket.setEndTime(endTime);
		redPacket.setTotalAmount(actRedPacketAdd.getActAmount());
		redPacket.setRegCount(0);
		redPacket.setRedPacketConfigCount(4);
		redPacket.setIsEnable(1);
		redPacket.setUserId(user.getId());
		redPacket.setAppId("wx42e6b214e6cdaed3");
		redPacket.setAppName("随选网");
		redPacket.setCompanyId(2501L);
		redPacket.setCompanyName("三度空间");
		redPacket.setCreator(user.getId().toString());
		redPacket.setGmtCreate(now);
		redPacket.setModifier(user.getId().toString());
		redPacket.setGmtModified(now);
		redPacket.setIsDeleted(0);
		return redPacket;
	}
	
	
	@ApiOperation(value = "结束活动", response = ResponseEnvelope.class)
    @PostMapping("/finish")
    public ResponseEnvelope finish(String actId) {

        if (StringUtils.isEmpty(actId)){
            return new ResponseEnvelope(false,"活动id不能为空!");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
        	RedPacket entity = new RedPacket();
        	entity.setId(actId);
        	entity.setGmtModified(new Date());
        	entity.setModifier(loginUser.getId().toString());
        	entity.setIsEnable(0);
            redPacketService.modifyById(entity);
            return new ResponseEnvelope(true,"结束活动成功");
        } catch (Exception e) {
            log.error("系统错误",e);
            return new ResponseEnvelope(false,"系统错误");
        }
    }
    

}
