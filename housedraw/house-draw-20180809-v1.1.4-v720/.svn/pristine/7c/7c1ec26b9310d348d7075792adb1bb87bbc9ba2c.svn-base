package com.sandu.common;

import static com.sandu.common.constant.ResponseEnum.PARAM_ERROR;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import com.sandu.common.constant.PageEnum;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BusinessException;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * 基础Controller
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/12/13 17:18
 */
public class BaseController {

    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获取校验的错误消息
     * @param validResult
     * @return 错误消息
     */
    public ReturnData processValidError(BindingResult validResult, ReturnData data){
        StringBuilder error = new StringBuilder();
        validResult.getAllErrors().stream().forEach(e -> {
            error.append(e.getDefaultMessage());
            error.append("; ");
        });
        return data.status(false)
                .code(PARAM_ERROR)
                .message(error.toString());
    }

    /**
     * 获取每页记录数
     * @param limit
     * @param defaultNum
     * @return 每页记录数
     */
	public int getLimit(Integer limit, int defaultNum) {
		return (limit == null || limit < Integer.valueOf(0)) ? (defaultNum < 0 ? PageEnum.DEFAULT_PAGE_SIZE.getValue() : defaultNum) : limit;
	}

	public int getLimit(Integer limit) {
		return getLimit(limit, PageEnum.DEFAULT_PAGE_SIZE.getValue());
	}

	/**
	 * 获取每页起始位置
	 * 
	 * @param page
	 *            页码
	 * @return int 页码
	 */
	public int getPage(Integer page, Integer limit) {
		int start = ((page == null || page < 0) ? PageEnum.DEFAULT_PAGE_NUM.getValue() : page) * getLimit(limit);
		logger.debug("page => {}, limit => {}, page * limit => {}", page, limit, start);
		return start;
	}

    public ReturnData setMsgId(HttpServletRequest request){
        String msgId = request.getParameter("msgId");
        if(null == msgId || ("").equals(msgId)){
            throw new BusinessException(false, ResponseEnum.MSGID_IS_NULL);
        }
        ReturnData data = ReturnData.builder().msgId(msgId).status(true).code(ResponseEnum.SUCCESS).
                message(ResponseEnum.SUCCESS.getMessage());

        return data;
    }

    public ReturnData setMsgId(String msgId) {
        if (null == msgId || ("").equals(msgId)) {
            throw new BusinessException(false, ResponseEnum.MSGID_IS_NULL);
        }

        return ReturnData.builder().msgId(msgId).status(true).code(ResponseEnum.SUCCESS).
                message(ResponseEnum.SUCCESS.getMessage());
    }
}
