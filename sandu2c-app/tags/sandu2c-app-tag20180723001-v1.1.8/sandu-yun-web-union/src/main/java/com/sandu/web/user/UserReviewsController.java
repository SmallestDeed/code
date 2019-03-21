package com.sandu.web.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:32 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.user.model.UserReviews;
import com.sandu.user.model.input.UserReviewsAdd;
import com.sandu.user.model.view.UserReviewsVo;
import com.sandu.user.service.UserReplyService;
import com.sandu.user.service.UserReviewsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: 用户评论
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/4 0004PM 3:32
 */
@Slf4j
@RestController
@RequestMapping("/v1/union/userreviews")
public class UserReviewsController {

    private final static String CLASS_LOG_PREFIX = "[用户评论基础服务]";

    @Autowired
    private UserReviewsService userReviewsService;

    @Autowired
    private UserReplyService userReplyService;

    /**
     * @Title: 供求信息用户评论列表
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/getuserreviews",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取供求信息用户评论",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessId", value = "业务ID", required = true, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope getSupplyDemandUserReviews(@RequestBody BaseSupplyDemandAdd baseSupplyDemandAdd, HttpServletRequest request) {
        if(baseSupplyDemandAdd.getBusinessId()==null||baseSupplyDemandAdd.getBusinessId()==0){
            return new ResponseEnvelope(false,"缺失参数businessId");
        }
        baseSupplyDemandAdd.setStart(baseSupplyDemandAdd.getCurPage());
        baseSupplyDemandAdd.setLimit(baseSupplyDemandAdd.getPageSize()==0?PageModel.DEFAULT_PAGE_PAGESIZE:baseSupplyDemandAdd.getPageSize());
        int count = userReviewsService.getAllUserReviewsCount(baseSupplyDemandAdd);
        if(count==0){
            return new ResponseEnvelope(false,"该商家暂时没收到评价哦");
        }
        List<UserReviewsVo> userReviewsVOList = userReviewsService.getAllUserReviews(baseSupplyDemandAdd);

        return new ResponseEnvelope(true,"",userReviewsVOList);
    }


    /**
     * @Title: 新增供求信息用户评论
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/adduserreviews",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增供求信息用户评论",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessId", value = "业务ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "reviewsMsg", value = "评论消息", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "reviewsId", value = "评论ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "replyMsg", value = "回复消息", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "replyReviewsId", value = "回复评论ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "replyType", value = "回复类型comment/reply", required = true, paramType = "query", dataType = "String")

    })
    public ResponseEnvelope addSupplyDemandUserReviews(@RequestBody UserReviewsAdd userReviewsAdd, HttpServletRequest request) {
      /*  LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false,"请先登录");
        }*/
        userReviewsAdd.setUserId(/*loginUser.getId()*/823);
        //userReviewsAdd.setCreator(loginUser.getName());

        //对供求信息进行评论
        if(null==userReviewsAdd.getReplyType()){
            if(userReviewsAdd.getBusinessId()==null||userReviewsAdd.getBusinessId()==0){
                return new ResponseEnvelope(false,"缺失参数businessId");
            }
            if(StringUtils.isEmpty(userReviewsAdd.getReviewsMsg())){
                return new ResponseEnvelope(false,"评论不能为空");
            }

            Integer userReviewsId = userReviewsService.addSupplyDemandUserReviews(userReviewsAdd);
            return new ResponseEnvelope(true,"评论成功",userReviewsId);
         //对评论进行回复
        }else if("comment".equals(userReviewsAdd.getReplyType())){
            if(userReviewsAdd.getReviewsId()==null||userReviewsAdd.getReviewsId()==0){
                return new ResponseEnvelope(false,"缺失参数reviewsId");
            }

            if(userReviewsAdd.getBusinessId()==null||userReviewsAdd.getBusinessId()==0){
                return new ResponseEnvelope(false,"缺失参数businessId");
            }
            if(StringUtils.isEmpty(userReviewsAdd.getReplyMsg())){
                return new ResponseEnvelope(false,"回复消息不能为空");
            }
            Integer replyId = userReplyService.addUserReplyToReviews(userReviewsAdd);
            return new ResponseEnvelope(true,"评论成功",replyId);


         //对回复进行回复
        }else if("reply".equals(userReviewsAdd.getReplyType())){
            if(userReviewsAdd.getReviewsId()==null||userReviewsAdd.getReviewsId()==0){
                return new ResponseEnvelope(false,"缺失参数reviewsId");
            }

            if(userReviewsAdd.getBusinessId()==null||userReviewsAdd.getBusinessId()==0){
                return new ResponseEnvelope(false,"缺失参数businessId");
            }

            if(userReviewsAdd.getReplyReviewsId()==null||userReviewsAdd.getReplyReviewsId()==null){
                return new ResponseEnvelope(false,"缺失参数replyReviewsId");
            }

            if(StringUtils.isEmpty(userReviewsAdd.getReplyMsg())){
                return new ResponseEnvelope(false,"回复消息不能为空");
            }
            Integer replyId = userReplyService.addUserReplyToReply(userReviewsAdd);
            return new ResponseEnvelope(true,"评论成功",replyId);
        }


        return new ResponseEnvelope(false,"replyType参数有误");



    }

}
