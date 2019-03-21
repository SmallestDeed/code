package com.sandu.im.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.sandu.im.common.ResponseEnvelope;
import com.sandu.im.common.bo.HistoryMessageBo;
import com.sandu.im.common.exception.ErrorCode;
import com.sandu.im.common.vo.PageResultVo;
import com.sandu.im.service.HistoryMessageService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/msgCenter/historyMsg")
public class HistoryMessageController {

	private static final Logger logger = LoggerFactory.getLogger(HistoryMessageController.class);
	@Autowired
	HistoryMessageService historyMessageService;


	/**
	 *
	 * @param userSessionId
	 * @param contactSessionId
	 * @param relatedObjType
	 * @param relatedObjId
	 * @param pageNum
	 * @param pageSize
	 * @param platformCode //mobile 移动端  sxw 随选网小程序  pcUnion pc端同城联盟
	 * @return
	 */
	@GetMapping(value = {"/list"})
	public ResponseEnvelope list(
			@RequestParam("userSessionId") String userSessionId,
			@RequestParam("contactSessionId") String contactSessionId,
			@RequestParam("relatedObjType") Integer relatedObjType,
			@RequestParam("relatedObjId") Long relatedObjId,
			@RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(value = "platformCode")String platformCode
		) {

		logger.info("pageNum:"+pageNum+",pageSize"+pageSize);
		if(StringUtils.isBlank(userSessionId) || StringUtils.isBlank(contactSessionId) || relatedObjType==null || relatedObjId==null || StringUtils.isBlank(platformCode)) {
			logger.info("[userSessionId:"+userSessionId+",contactSessionId:"+contactSessionId+",relatedObjType:"+relatedObjType+",relatedObjId:"+relatedObjId+"platformCode:"+platformCode);
			return ResponseEnvelope.bizError(ErrorCode.PARAM_ERROR);
		}

		try {
			PageInfo<HistoryMessageBo> pageList = historyMessageService.pageListHistoryMessage(userSessionId,contactSessionId,relatedObjType,relatedObjId,pageNum, pageSize,platformCode);
			PageResultVo resultVo = new PageResultVo(pageList.getList(),pageList.getTotal());
			return ResponseEnvelope.bizSuccess(resultVo);
		}catch(Exception ex) {
			logger.error("未知错误:",ex);
			return ResponseEnvelope.sysError();
		}
		
	}
	
}
