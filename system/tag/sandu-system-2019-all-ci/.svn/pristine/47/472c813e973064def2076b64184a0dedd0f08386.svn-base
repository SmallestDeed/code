package com.sandu.web.pointmall.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.base.matadata.ResultCode;
import com.sandu.api.base.matadata.ResultMessage;
import com.sandu.api.pointmall.model.vo.GiftSingleVo;
import com.sandu.api.pointmall.model.vo.GiftVo;
import com.sandu.api.pointmall.model.vo.OrderGiftDetailVo;
import com.sandu.api.pointmall.service.GiftImageService;
import com.sandu.api.pointmall.service.GiftService;
import com.sandu.api.pointmall.service.OrderService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "礼品", tags = "GiftInfo")
@RestController("giftController2")
@RequestMapping(value = "/v1/point/gift")
public class GiftController {

    @Autowired
    private GiftService giftService;

    @Autowired
    private GiftImageService giftImageService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    @ApiOperation(value = "获取礼品列表信息", response = ResultMessage.class)
    public ResultMessage list()
    {
        ResultMessage message = new ResultMessage();
        List<GiftVo> list_Gift = giftService.getGiftList();
        message.setCode(ResultCode.Success);
        message.setData(list_Gift);
        return message;

    }

    /****
     * 获取礼品详细信息，并加入对应的图片集合，图片集合中封面放在第一位，其他按排序升序
     * 用于确认订单页面和商品详情页面
     * @param id 礼品Id
     * @return
     */
    @RequestMapping(value = {"/get"}, method = RequestMethod.GET)
    @ApiOperation(value = "获取礼品详情信息", response = ResultMessage.class)
    public  ResultMessage get(@RequestParam("id")Integer id)
    {
        ResultMessage message=new ResultMessage();
        //LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        List<String> imges = null;
        GiftSingleVo giftSingleVo = giftService.getGiftAndImgsById(id);

        if (giftSingleVo!=null){
            imges = giftImageService.getGiftImageList(id);
            giftSingleVo.setFilenames(imges);
        }
        message.setCode(ResultCode.Success);
        message.setData(giftSingleVo);
        return  message;
    }

    /***
     * 获取我的礼品数量，返回值放在 ResultMessage 的 message中
     * @param uid 用户ID
     * @return
     */
    @RequestMapping(value = {"/getMyGiftNum"}, method = RequestMethod.GET)
    @ApiOperation(value = "获取我的礼品数量", response = ResultMessage.class)
    public  ResultMessage getMyGiftNum()
    {
        ResultMessage message=new ResultMessage();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        List<OrderGiftDetailVo> list = orderService.getOrderGiftDetailVoList(loginUser.getId());
        message.setCode(ResultCode.Success);
        message.setMessage(String.valueOf(list.size()));

        return  message;
    }
}
