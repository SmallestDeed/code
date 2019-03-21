package com.sandu.web.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 2:41 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.user.model.UserInvite;
import com.sandu.user.model.input.UserCommisionTopAdd;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.view.CommissionTopDTO;
import com.sandu.user.model.view.NewestcommissioninfoDTO;
import com.sandu.user.model.view.UserCommissionInfoDTO;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserCommisionService;
import com.sandu.user.service.UserInviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author weisheng
 * @Title: 用户佣金
 * @Package
 * @Description:
 * @date 2018/7/23 0023PM 2:41
 */
@Api(tags = "UserCommissionController", description = "用户佣金")
@Slf4j
@RestController
@RequestMapping("/v1/union/commission")
public class UserCommissionController {

    private static Gson gson = new Gson();

    @Autowired
    private UserCommisionService userCommisionService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserInviteService userInviteService;
    /**
     * @Title: 获取当前登录用户佣金信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/7/23 0027PM 6:02
     */
    @RequestMapping(value = "/usercommissioninfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取当前登录用户佣金信息", response = ResponseEnvelope.class)
    @ApiImplicitParams({
    })
    public ResponseEnvelope getUserCommissionInfo(HttpServletRequest request) {
        ResponseEnvelope res = new ResponseEnvelope();
        //获取当前登录用户
      LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            res = new ResponseEnvelope(false, "Please login");
            return res;
        }
        log.info("当前登录用户信息:" + gson.toJson(loginUser));
        Integer userId = loginUser.getId();
        //获取用户佣金信息
        UserCommissionInfoDTO userCommissionInfo = userCommisionService.getUserCommissionInfoByUserId(userId);
        if(null == userCommissionInfo){
            userCommissionInfo = new UserCommissionInfoDTO();
            userCommissionInfo.setUserId(userId);
            userCommissionInfo.setCommisionCount(0);
        }
        //获取用户邀请次数
        UserInviteAdd userInviteAdd = new UserInviteAdd();
        userInviteAdd.setInviteId(userId);
        int count = userInviteService.getUserInviteInfoCountByInviteId(userInviteAdd);
        if(count>0){
            userCommissionInfo.setInviteIdCount(count);
        }
        log.info("当前登录用户佣金详情:" + gson.toJson(userCommissionInfo));
        res = new ResponseEnvelope(true, "", userCommissionInfo);

        return res;

    }


    /**
     * @Title: 获取最新收入的6条用户信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/7/23 0027PM 6:02
     */
    @RequestMapping(value = "/newestcommissioninfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取最新收入的用户信息", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "当前页码", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = false, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope getNewestcommissioninfo(@ModelAttribute PageModel pageModel,
                                                    HttpServletRequest request) {
        ResponseEnvelope res = new ResponseEnvelope();
        //设置分页参数
        pageModel.setLimit((0 == pageModel.getPageSize()) ? PageModel.DEFAULT_PAGE_PAGESIZE : pageModel.getPageSize());
        pageModel.setStart(pageModel.getStart());

        List<NewestcommissioninfoDTO> newestcommissioninfoList = userCommisionService.getNewestcommissioninfo(pageModel);
        if (null == newestcommissioninfoList) {
            res = new ResponseEnvelope(false, "User commission is zero");
            return res;
        }
        log.info("获取最新收入的用户信息:" + gson.toJson(newestcommissioninfoList));

        //处理用户昵称为手机号的,隐藏中间部分.
        for(NewestcommissioninfoDTO newestcommissioninfoDTO :newestcommissioninfoList){
            String nickName = newestcommissioninfoDTO.getNickName();
            if(StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)){
                newestcommissioninfoDTO.setNickName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            }
        }
        res = new ResponseEnvelope(true, "success", newestcommissioninfoList);
        return res;
    }



    /**
     * @Title: 获取排行前十的收入排行榜
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/7/23 0027PM 6:02
     */
    @RequestMapping(value = "/commissiontop", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取最新收入的用户信息", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "当前页码", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = false, paramType = "query", dataType = "int")
    })
    public ResponseEnvelope getCommissionTop(@ModelAttribute UserCommisionTopAdd userCommisionTopAdd,
                                                    HttpServletRequest request) {
        ResponseEnvelope res = new ResponseEnvelope();
        //设置分页参数
        userCommisionTopAdd.setLimit((0 == userCommisionTopAdd.getPageSize()) ? PageModel.DEFAULT_PAGE_PAGESIZE : userCommisionTopAdd.getPageSize());
        userCommisionTopAdd.setStart(userCommisionTopAdd.getStart());

        List<CommissionTopDTO> commissionTopDTOS = userCommisionService.getCommissionTop(userCommisionTopAdd);
        if (null == commissionTopDTOS || commissionTopDTOS.size() ==0) {
            res = new ResponseEnvelope(false, "The top information has no data");
            return res;
        }
        log.info("获取最新收入的用户信息:" + gson.toJson(commissionTopDTOS));


        for(CommissionTopDTO commissionTopDTO :commissionTopDTOS){
            //处理用户昵称为手机号的,隐藏中间部分.
            String nickName = commissionTopDTO.getNickName();
            if(StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)){
                commissionTopDTO.setNickName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            }
            //处理用户头像为空去默认头像
            String userHeadPicPath = commissionTopDTO.getUserHeadPicPath();
            Integer sex = commissionTopDTO.getSex();
            if(StringUtils.isBlank(userHeadPicPath)){
                if(null == sex || sex ==0 || sex ==1 ){
                    String userDefaultPic = sysUserService.getUserDefaultPic(1);
                    commissionTopDTO.setUserHeadPicPath(userDefaultPic);
                }else if( sex == 2){
                    String userDefaultPic = sysUserService.getUserDefaultPic(sex);
                    commissionTopDTO.setUserHeadPicPath(userDefaultPic);
                }
            }

        }




        res = new ResponseEnvelope(true, "", commissionTopDTOS);
        return res;
    }



}
