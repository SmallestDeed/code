package com.nork.mobile.controller;

import com.google.gson.Gson;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.dao.CompanyFranchiserGroupMapper;
import com.nork.product.model.CompanyFranchiserGroup;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.SysUserMessageService;
import com.nork.system.service.SysUserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description 系统-我的消息表Controller
 * @Date:Created Administrator in 下午 3:41 2017/12/21 0021
 * @Modified By:
 */
@Controller
@RequestMapping("/{style}/mobile/sysUserMessage")
public class MobileSysUserMessageController {
    private static final Gson GSON = new Gson();
    private static Logger logger = Logger.getLogger(MobileSysUserMessageController.class);

    @Autowired
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CompanyFranchiserGroupMapper companyFranchiserGroupMapper;

    /**
     * 查询 我的消息列表
     *
     * @param sysUserMessage
     * @return
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@RequestBody SysUserMessage sysUserMessage) {
    	List<SysUserMessage> allMessages = null;
    	//主账号ID
    	Integer franchiserId = null;
    	//1为经销商 子账号 ,0为其他账号
    	Integer type = 0;
    	//经销商账号类型
    	Integer franchiserAccountType  = null;
    	Integer userId = sysUserMessage.getUserId();
        if (userId == null) {
            return new ResponseEnvelope<SysUserMessage>(false, "用户id不能为空！！");
        }
    	sysUserMessage.setUserId(userId);
        SysUser sysUser = sysUserService.get(userId);
        Integer userType = sysUser.getUserType();
        Integer franchiseGroupId = sysUser.getFranchiserGroupId();
        logger.error("我的消息列表  userId ="+userId+"  franchiseGroupId=" + franchiseGroupId);
        if (userType.intValue() == 3 && franchiseGroupId.intValue() > 0) {
        	CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupMapper.selectByPrimaryKey(franchiseGroupId);
        	franchiserId = companyFranchiserGroup.getFranchiserId();
        	franchiserAccountType = sysUser.getFranchiserAccountType();
        	if (userType.intValue() == 3 && franchiserAccountType.intValue() == 2) {
        		type = 1;
        	}
		}
        sysUserMessage.setFranchiserId(franchiserId);
        Integer count = null;
        count = sysUserMessageService.getCount(sysUserMessage,type);
        if (count > 0) {
            allMessages = sysUserMessageService.getList(sysUserMessage,type);
            try {
                sysUserMessageService.updateIsRead(sysUserMessage);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("------消息列表更改消息已读状态出错："+e);
            }
        }

        return new ResponseEnvelope<>(true, sysUserMessage.getMsgId(), count, allMessages);
    }

    /**
     * 查询 有没有未读的消息
     *
     * @param sysUserMessage
     * @return
     */
    @RequestMapping("/findUnread")
    @ResponseBody
    public Object findUnread(@RequestBody SysUserMessage sysUserMessage) {
        if (sysUserMessage.getUserId() == null) {
            return new ResponseEnvelope(false, "用户id不能为空！！");
        }
        sysUserMessage.setIsRead(0);
        int count = sysUserMessageService.getCount(sysUserMessage,0);

        return new ResponseEnvelope<>(true, count);
    }

    /**
     * 清空所有消息
     *
     * @param sysUserMessage
     * @return
     */
    @RequestMapping(value = "/removeAll")
    @ResponseBody
    public Object removeAll(@RequestBody SysUserMessage sysUserMessage) {
        if (sysUserMessage.getUserId() == null) {
            return new ResponseEnvelope(false, "用户id不能为空！！");
        }

        int count = sysUserMessageService.getCount(sysUserMessage,0);
        if (count <= 0) {
            return new ResponseEnvelope(false, "请确认是否有消息！");
        }

        sysUserMessageService.removeAll(sysUserMessage);

        return new ResponseEnvelope(true, "清空数据成功！");
    }

    /**
     * 删除消息
     *
     * @param sysUserMessage
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestBody SysUserMessage sysUserMessage) {
        if (sysUserMessage.getId() == null) {
            return new ResponseEnvelope(false, "消息id不能为空！！");
        }
        int status = sysUserMessageService.delete(sysUserMessage.getId());
        if (status <= 0) {
            return new ResponseEnvelope(false, "删除消息失败！");
        }
        return new ResponseEnvelope(true, "删除消息成功！");
    }

}
