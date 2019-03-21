package com.sandu.im.web.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.sandu.im.common.bo.ShopBo;
import com.sandu.im.common.bo.UserBo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.im.common.ResponseEnvelope;
import com.sandu.im.common.bo.UserContactBo;
import com.sandu.im.common.exception.ErrorCode;
import com.sandu.im.common.exception.MessageException;
import com.sandu.im.service.HistoryMessageService;
import com.sandu.im.service.UserContactService;

@RestController
@RequestMapping(value = "/v1/msgCenter/userContact")
public class UserContactController {
	private static final Logger logger = LoggerFactory.getLogger(UserContactController.class);

	@Autowired
	UserContactService userContactService;

	@Value("${hot.designer.recommend.ids}")
	private String hotDesignerIds;

	@PostMapping(value = {"/resetUnreadMsg"})
	public ResponseEnvelope resetUnreadMsg(@RequestParam("userSessionId") String userSessionId,@RequestParam("contactSessionId") String contactSessionId,
			@RequestParam("relatedObjType") Integer relatedObjType,@RequestParam("relatedObjId") Long relatedObjId) {

		if(StringUtils.isBlank(userSessionId) || StringUtils.isBlank(contactSessionId) || relatedObjType==null || relatedObjId==null) {
			logger.info("[userSessionId:"+userSessionId+",contactSessionId:"+contactSessionId+",relatedObjType:"+relatedObjType+",relatedObjId:"+relatedObjId);
			return ResponseEnvelope.bizError(ErrorCode.PARAM_ERROR);
		}

		try {
			userContactService.resetUnreadMsg(userSessionId, contactSessionId,relatedObjType,relatedObjId);
		}catch(MessageException ex) {
			logger.info("业务异常:"+ex.getMessage());
			return ResponseEnvelope.bizError(ex.getErrorCode(),ex.getMessage());
		}catch(Exception ex) {
			logger.error("未知错误:",ex);
			return ResponseEnvelope.sysError();
		}
		return ResponseEnvelope.success();
	}


	@GetMapping(value = {"/list"})
	public ResponseEnvelope list(@RequestParam("userSessionId") String userSessionId) {

		if(StringUtils.isBlank(userSessionId)) {
			return ResponseEnvelope.bizError(ErrorCode.PARAM_ERROR);
		}

		try {
			List<UserContactBo> list = userContactService.listUserContactsAndLastMsg(userSessionId);
			return ResponseEnvelope.bizSuccess(list);
		}catch(Exception ex) {
			logger.error("未知错误:",ex);
			return ResponseEnvelope.sysError();
		}

	}


	@PostMapping(value = {"/remove"})
	public ResponseEnvelope removeContact(@RequestParam("userSessionId") String userSessionId,@RequestParam("contactSessionId") String contactSessionId,
			@RequestParam("relatedObjType") Integer relatedObjType,@RequestParam("relatedObjId") Long relatedObjId) {

		if(StringUtils.isBlank(userSessionId) || StringUtils.isBlank(contactSessionId) || relatedObjType==null || relatedObjId==null) {
			logger.info("[userSessionId:"+userSessionId+",contactSessionId:"+contactSessionId+",relatedObjType:"+relatedObjType+",relatedObjId:"+relatedObjId);
			return ResponseEnvelope.bizError(ErrorCode.PARAM_ERROR);
		}

		try {
			userContactService.removeContactAndHistoryMsg(userSessionId, contactSessionId,relatedObjType,relatedObjId);
		}catch(MessageException ex) {
			logger.info("业务异常:"+ex.getMessage());
			return ResponseEnvelope.bizError(ex.getErrorCode(),ex.getMessage());
		}catch(Exception ex) {
			logger.error("未知错误:",ex);
			return ResponseEnvelope.sysError();
		}
		return ResponseEnvelope.success();
	}

	@GetMapping("/checkMsgReceiverShopInfo")
	public Object checkMsgReceiverShopInfo(@RequestParam(value = "contactSessionId")String contactSessionId){

		//参数校验
		Assert.hasLength(contactSessionId,"联系人uuid不能为空");

		Map<String,Object> resultMap;
		try {
			resultMap = userContactService.getContactUserShopInfo(contactSessionId);
		}catch (IllegalArgumentException ill){
			return ResponseEnvelope.bizError("400",ill.getMessage());
		}
		catch (Exception e) {
			logger.error("系统错误",e);
			return ResponseEnvelope.bizError("500","系统错误");
		}
		return ResponseEnvelope.bizSuccess(resultMap);
	}

    @PostMapping("/obtainUserList")
    public Object obtainDesignAndDecorationUserList(@RequestParam(value = "userType") Integer userType,
                                                    @RequestParam(value = "userSessionId") String userSessionId,
                                                    @RequestParam(value = "userName", required = false) String userName,
                                                    @RequestParam(value = "shopName", required = false) String shopName,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(value = "lastListSize", defaultValue = "0") Integer lastListSize
    ) {

        Assert.notNull(userType, "获取用户类型不能为空");

        Assert.notNull(userSessionId, "当前聊天用户uuid不能为空");

        try {
            //step1.统计用户最近聊天信息
            int recent = userContactService.countChatRecordByTypeAndUserSessionId(userSessionId, userType, userName, shopName);
            //step2.获取用户最近聊天的记录 => 最多5条
            List<UserBo> recentUserList = new ArrayList<>();
            if (recent > 0) {
                int i = (page - 1) * 5;
                recentUserList = userContactService.getChatRecordByTypeAndUserSessionId(userSessionId, userType, userName, shopName, i, limit);
            }
            //step3.统计最热推荐设计师/装修公司信息
            List<Integer> hotDesignrIds = Arrays.asList(hotDesignerIds.split(",")).stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
            //step4.获取最热推荐设计师信息 => 最多5条
            int designer = (page - 1) * 5;
            List<UserBo> hotDesigners = userContactService.getHotDesignerUserInfo(hotDesignrIds, userName, shopName, designer);
            //step5.统计在三度下有店铺的设计师,并且店铺有发布在随选网的,计算要差多少条,并且,从第几条开始
            int sanduDesigners = userContactService.countSanduShopDesigners(userName, shopName, userType);
            int size = 10 - recentUserList.size() - hotDesigners.size();//查询条数
            List<UserBo> ortherShopDesigners = new ArrayList<>();
            if (sanduDesigners > 0) {
                //step6. 查询三度下所有的店铺设计师
                ortherShopDesigners = userContactService.getSanduShopDesigners(userName, shopName, userType, lastListSize, size);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("total", recent + hotDesignrIds.size() + sanduDesigners);
            result.put("recentUser", recentUserList);
            result.put("hotDesigners", hotDesigners);
            result.put("ortherShopDesigners", ortherShopDesigners);
            return ResponseEnvelope.bizSuccess(result);
        }catch (IllegalArgumentException ill){
            return ResponseEnvelope.bizError("400","参数错误");
        }catch (Exception e) {
            logger.error("系统错误",e);
            return ResponseEnvelope.bizError("500","未知错误");
        }
    }

}
