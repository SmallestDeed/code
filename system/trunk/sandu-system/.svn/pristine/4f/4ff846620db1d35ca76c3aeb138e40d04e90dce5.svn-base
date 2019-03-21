package com.sandu.web.pointmall.controller;

import com.sandu.api.pointmall.model.vo.PointVo;
import com.sandu.api.pointmall.service.UserPointService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.api.base.matadata.ResultCode;
import com.sandu.api.base.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "礼品订单", tags = "GiftUserPoint")
@RestController
@RequestMapping(value = "/v1/point/userPoint")
public class UserPointController {

    @Autowired
    private UserPointService userPointService;

    /***
     * 获取我的可用积分
     * @param uid
     * @return
     */
    @RequestMapping(value = {"/getUserPoint"}, method = RequestMethod.GET)
    @ApiOperation(value = "获取我的可用积分", response = ResultMessage.class)
    public ResultMessage getUserPoint()
    {
        ResultMessage message = new ResultMessage();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        PointVo pointVo = userPointService.getPointVo(loginUser.getId());
        message.setData(pointVo);
        message.setCode(ResultCode.Success);
        return message;
    }


}
