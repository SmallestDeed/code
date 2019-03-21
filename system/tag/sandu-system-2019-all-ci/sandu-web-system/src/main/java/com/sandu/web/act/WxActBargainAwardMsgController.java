package com.sandu.web.act;

import com.sandu.api.act.input.WxActBargainAwardMsgAddForm;
import com.sandu.api.act.input.WxActBargainAwardMsgQuery;
import com.sandu.api.act.model.WxActBargainAwardMsg;
import com.sandu.api.act.output.WxActBargainAwardMsgVO;
import com.sandu.api.act.output.WxActBargainAwardMsgWebVO;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.PageHelper;
import com.sandu.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sandu.api.act.input.WxActBargainAwardMsgUpdate;
import com.sandu.api.act.service.WxActBargainAwardMsgService;
import com.sandu.commons.ResponseEnvelope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(tags = "WxActBargainAwardMsg", description = "领奖消息")
@RestController
@RequestMapping("/v1/act/bargain/awardmsg")
public class WxActBargainAwardMsgController  extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(WxActBargainAwardMsgController.class);
	
    @Autowired
    private WxActBargainAwardMsgService wxActBargainAwardMsgService;
    
    @ApiOperation(value = "领奖消息列表查询", response = ResponseEnvelope.class)
    @PostMapping("/getWxActBargainAwardMsgList")
    public ResponseEnvelope getWxActBargainAwardMsgList(@Valid @RequestBody WxActBargainAwardMsgQuery query,BindingResult validResult) {
		//根据actId 查询wx_act_bargain_award_msg领奖消息表
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        try{
            int count = wxActBargainAwardMsgService.getCountByActId(query);
            List<WxActBargainAwardMsgVO> msgVOList=null;
            logger.info("领奖消息列表查询---消息数量："+count);
            //不做分页处理
           /* PageHelper page = PageHelper.getPage(count, query.getLimit(), query.getPage());
            query.setPage(page.getStart());*/

            if(count>0){
                msgVOList = wxActBargainAwardMsgService.getListByActId(query);
                return new ResponseEnvelope<>(true,count,msgVOList,"领奖消息列表查询完成");
            }
            return new ResponseEnvelope<>(true,count,msgVOList,"列表查询结果为空");
        }catch (Exception e){
            logger.error("领奖消息列表查询方法---系统异常"+e);
            return new ResponseEnvelope(false,"领奖消息列表查询方法---系统异常");
        }
    }

    
    @ApiOperation(value = "领奖消息列表查询(随机)", response = ResponseEnvelope.class)
    @GetMapping("/getWxActBargainAwardMsgRandomList")
    public ResponseEnvelope getWxActBargainAwardMsgRandomList(String actId) {
		//SELECT * FROM wx_act_bargain_award_msg ORDER BY  RAND() LIMIT ?
        if (StringUtils.isBlank(actId)){
            return new ResponseEnvelope<>(false, "活动Id不能为空！");
        }
        List<WxActBargainAwardMsgWebVO> list = wxActBargainAwardMsgService.getMsgRandomList(actId);
        return new ResponseEnvelope<>(true,"领奖消息列表查询完成",list);
    }


    @ApiOperation(value = "领奖消息修改", response = ResponseEnvelope.class)
    @PostMapping("/modifyWxActBargainAwardMsg")
    public ResponseEnvelope modifyWxActBargainAwardMsg(@Valid @RequestBody WxActBargainAwardMsgUpdate wxActBargainAwardMsgUpdate
            ,BindingResult validResult) {
        logger.info("领奖消息修改---入参：{}",wxActBargainAwardMsgUpdate);
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try{
            //数据转换
            wxActBargainAwardMsgUpdate.setGmtModified(new Date());
            wxActBargainAwardMsgUpdate.setModifier(loginUser.getUserName());
            WxActBargainAwardMsg msg = wxActBargainAwardMsgUpdate.dataTransfer(wxActBargainAwardMsgUpdate);
            //update wx_act_bargain_award_msg
            int result = wxActBargainAwardMsgService.modifyWxActBargainAwardMsg(msg);
            if (result>0){
                return new ResponseEnvelope(true,"修改成功");
            }
            return new ResponseEnvelope(true,"修改失败");
        }catch (Exception e){
            logger.error("领奖消息修改方法---系统异常"+e);
            return new ResponseEnvelope(false,"领奖消息修改方法---系统异常");
        }
    }
    
    @ApiOperation(value = "领奖消息删除", response = ResponseEnvelope.class)
    @PostMapping("/removeWxActBargainAwardMsg")
    public ResponseEnvelope removeWxActBargainAwardMsg(@RequestParam("awardMsgId") String awardMsgId) {
        logger.info("领奖消息删除---入参：awardMsgId{}",awardMsgId);
    	//update wx_act_bargain_award_msg set is_deleted=1 where id=msgId
        if (StringUtils.isBlank(awardMsgId)){
            return new ResponseEnvelope(false,"请传入领取Id");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        int result = wxActBargainAwardMsgService.removeWxActBargainAwardMsg(awardMsgId,loginUser.getUserName());
        if (result>0){
            return new ResponseEnvelope(true,"删除成功");
        }
        return new ResponseEnvelope(false,"删除失败");
    }


    @ApiOperation(value = "领奖消息新增", response = ResponseEnvelope.class)
    @PostMapping("/createWxActBargainAwardMsg")
    public ResponseEnvelope createWxActBargainAwardMsg(@Valid @RequestBody WxActBargainAwardMsgAddForm awardMsgAdd
            ,BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        logger.info("领奖消息新增---入参：awardMsgAdd{}",awardMsgAdd);
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        //参数转化
        WxActBargainAwardMsg msg=new WxActBargainAwardMsg();
        String Id = UUID.randomUUID().toString().replace("-", "");
        msg.setId(Id);
        msg.setActId(awardMsgAdd.getActId());
        msg.setMessage(awardMsgAdd.getMessage());
        msg.setAppId(awardMsgAdd.getAppId());
        msg.setCreator(loginUser.getUserName());
        msg.setModifier(loginUser.getUserName());
        msg.setGmtCreate(new Date());
        msg.setGmtModified(new Date());
        msg.setIsDeleted(0);
        msg.setRegistrationId(loginUser.getId().toString());
        try {
            wxActBargainAwardMsgService.createWxActBargainAwardMsg(msg);
            return new ResponseEnvelope(true,"新增领奖消息成功!");
        }catch (Exception e){
            logger.error("领奖消息新增方法---系统异常"+e);
            return new ResponseEnvelope(false,"领奖消息新增--系统异常");
        }
    }


}
