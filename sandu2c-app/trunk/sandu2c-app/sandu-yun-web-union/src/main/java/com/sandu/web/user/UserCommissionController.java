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
import com.sandu.user.model.SysUser;
import com.sandu.user.model.input.UserCommisionTopAdd;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.search.CommissionInfoSearch;
import com.sandu.user.model.search.MyCommissionSearch;
import com.sandu.user.model.view.*;
import com.sandu.user.service.MediationBankInfoService;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserCommisionService;
import com.sandu.user.service.UserInviteService;
import com.sandu.validate.AnnotationValidator;
import com.sandu.validate.vo.ValidateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Autowired
    private MediationBankInfoService mediationBankInfoService;

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
        if (null == userCommissionInfo) {
            userCommissionInfo = new UserCommissionInfoDTO();
            userCommissionInfo.setUserId(userId);
            userCommissionInfo.setCommisionCount(0);
        }
        //获取用户邀请次数
        UserInviteAdd userInviteAdd = new UserInviteAdd();
        userInviteAdd.setInviteId(userId);

        int count;
        try {
            count = userInviteService.getUserInviteInfoCountByInviteId(userInviteAdd);
        }catch (Exception e){
            log.error("查询我的邀请次数数据异常"+e);
            return new ResponseEnvelope(false,"查询我的邀请次数数据异常");
        }

        userCommissionInfo.setInviteIdCount(count);

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
        for (NewestcommissioninfoDTO newestcommissioninfoDTO : newestcommissioninfoList) {
            String nickName = newestcommissioninfoDTO.getNickName();
            if (StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)) {
                newestcommissioninfoDTO.setNickName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
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
        if (null == commissionTopDTOS || commissionTopDTOS.size() == 0) {
            res = new ResponseEnvelope(false, "The top information has no data");
            return res;
        }
        log.info("获取最新收入的用户信息:" + gson.toJson(commissionTopDTOS));


        for (CommissionTopDTO commissionTopDTO : commissionTopDTOS) {
            //处理用户昵称为手机号的,隐藏中间部分.
            String nickName = commissionTopDTO.getNickName();
            if (StringUtils.isNotBlank(nickName) && Utils.isMobile(nickName)) {
                commissionTopDTO.setNickName(nickName.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
            //处理用户头像为空去默认头像
            String userHeadPicPath = commissionTopDTO.getUserHeadPicPath();
            Integer sex = commissionTopDTO.getSex();
            if (StringUtils.isBlank(userHeadPicPath)) {
                if (null == sex || sex == 0 || sex == 1) {
                    String userDefaultPic = sysUserService.getUserDefaultPic(1);
                    commissionTopDTO.setUserHeadPicPath(userDefaultPic);
                } else if (sex == 2) {
                    String userDefaultPic = sysUserService.getUserDefaultPic(sex);
                    commissionTopDTO.setUserHeadPicPath(userDefaultPic);
                }
            }

        }


        res = new ResponseEnvelope(true, "", commissionTopDTOS);
        return res;
    }


    /**
     * @Title: 获取我的佣金信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/7/23 0027PM 6:02
     */
    @RequestMapping(value = "/mycommissioninfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getMyCommissionInfo(@ModelAttribute MyCommissionSearch myCommissionSearch, HttpServletRequest request) {
        //获取当前登录用户
       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));



        //校验传入信息
        ValidateResult result = AnnotationValidator.validate(myCommissionSearch);
        if (!result.isValid()) {
            log.warn("参数校验未通过:" + gson.toJson(result));
            return new ResponseEnvelope(false, result.getFieldName() + "-------" + result.getMessage());
        }
        log.info("传参信息校验通过:" + gson.toJson(myCommissionSearch));


        MyCommissionVo myCommissionVo = new MyCommissionVo();
        //查询我的佣金总额
        int commisionTotal;
        try {
            commisionTotal = userCommisionService.getMyCommisionTotalByUserId(loginUser.getId());
            if ( commisionTotal == 0) {
                log.warn("获取我的佣金总额为空:" + loginUser.getId());
                return new ResponseEnvelope(true, "获取我的佣金总额为空");
            }
            myCommissionVo.setCommisionTotal(commisionTotal);
        } catch (Exception e) {
            log.error("查询我的佣金总额异常:" + e);
            return new ResponseEnvelope(false, "查询我的佣金总额异常");
        }
        //查询当月佣金总额
        int commisionMonthTotal;
        try {
            commisionMonthTotal = userCommisionService.getMyCommisionMonthTotalByUserId(loginUser.getId(), myCommissionSearch.getYear(), myCommissionSearch.getMonth());
            if (commisionMonthTotal == 0) {
                log.warn("获取我的当月佣金总额为空:" + loginUser.getId());
                return new ResponseEnvelope(true, "获取我的当月佣金总额为空");
            }
            myCommissionVo.setCommisionMonthTotal(commisionMonthTotal);
        } catch (Exception e) {
            log.error("查询我的当月佣金总额异常:" + e);
            return new ResponseEnvelope(false, "查询我的当月佣金总额异常");
        }

        //查询当月佣金金额明细列表条数
        int monthCommissionCount;
        try {
            monthCommissionCount = userCommisionService.getMonthCommissionCountByUserId(loginUser.getId(),myCommissionSearch);
            if(monthCommissionCount == 0){
                log.warn("查询当月佣金金额明细条数数据为空:"+loginUser.getId()+"----"+gson.toJson(myCommissionSearch));
                return new ResponseEnvelope(true,"查询当月佣金金额明细条数数据为空");
            }
            myCommissionVo.setMonthCommissionCount(new Integer(monthCommissionCount));
        } catch (Exception e) {
            log.error("查询当月佣金金额明细条数数据异常:"+e);
            return new ResponseEnvelope(false,"查询当月佣金金额明细条数数据异常");
        }
        //查询当月佣金金额明细列表详情
        List<MonthCommissionVo> monthCommissionVoList;
        try {
            monthCommissionVoList = userCommisionService.getMonthCommissionVoListByUserId(loginUser.getId(),myCommissionSearch);
            if(monthCommissionVoList == null || monthCommissionVoList.size() == 0 ){
                log.warn("查询当月佣金金额明细列表数据为空:"+loginUser.getId()+"----"+gson.toJson(myCommissionSearch));
                return new ResponseEnvelope(true,"查询当月佣金金额明细列表数据为空");
            }
            myCommissionVo.setMonthCommissionVoList(monthCommissionVoList);
        } catch (Exception e) {
            log.error("查询当月佣金金额明细列表数据异常:"+e);
            return new ResponseEnvelope(false,"查询当月佣金金额明细列表数据异常");
        }



        return new ResponseEnvelope(true, "success", myCommissionVo);
    }


    /**
     * @Title: 获取我的资产
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/7/23 0027PM 6:02
     */
    @RequestMapping(value = "/mycommission", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getMyCommission( HttpServletRequest request) {
      //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));

        MyCommissionInfoVo myCommissionInfoVo = new MyCommissionInfoVo();
        //查询我的佣金总额
        int commisionTotal;
        try {
            commisionTotal= userCommisionService.getMyCommisionTotalByUserId(loginUser.getId());
            if ( commisionTotal == 0) {
                log.warn("获取我的佣金总额为空:" + loginUser.getId());
            }
            myCommissionInfoVo.setCommisionTotal(commisionTotal);
        } catch (Exception e) {
            log.error("查询我的佣金总额异常:" + e);
            return new ResponseEnvelope(false, "查询我的佣金总额异常");
        }

        //查询出已经返现的
        int expensesCommissionTotal;
        try {
            expensesCommissionTotal = userCommisionService.getExpensesCommissionTotalByUserId(loginUser.getId(),0,0);
            if ( expensesCommissionTotal == 0) {
                log.warn("获取我的返现总额为空:" + loginUser.getId());
            }

        } catch (Exception e) {
            log.error("查询我的返现总额异常:" + e);
            return new ResponseEnvelope(false, "查询我的返现总额异常");
        }

        //获取我的资产
        int myCommision = commisionTotal - expensesCommissionTotal;
        if(myCommision<0){
            log.error("获取我的资产数据错误"+commisionTotal+"------------"+expensesCommissionTotal);
            return new ResponseEnvelope(false,"获取我的资产数据错误");
        }
        myCommissionInfoVo.setMyCommisionTotal(myCommision);


        //获取我绑定的银行卡数量
        List<MediationBankInfoListVo> mediationBankInfoListVoList;
        try {
            mediationBankInfoListVoList = mediationBankInfoService.getMediationBankInfoListByUserId(loginUser.getId());
            if (null == mediationBankInfoListVoList || mediationBankInfoListVoList.size() == 0) {
                log.warn("改用户未绑定银行卡信息:" + loginUser.getId());
            }
            myCommissionInfoVo.setMyBankCardCount(mediationBankInfoListVoList.size());
        } catch (Exception e) {
            log.error("查询数据异常:" + e);
            return new ResponseEnvelope(false, "查询数据异常");
        }

        return new ResponseEnvelope(true, "success", myCommissionInfoVo);
    }

    /**
     * @Title: 获取我的收支明细
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/7/23 0027PM 6:02
     */
    @RequestMapping(value = "/myincomeexpenses", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getMyincomeExpenses(@ModelAttribute CommissionInfoSearch commissionInfoSearch,HttpServletRequest request) {
        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));

        CommissionInfoListVo commissionInfoListVo = new CommissionInfoListVo();

        //查询当月佣金总额
        try {
            Integer commisionMonthTotal = userCommisionService.getMyCommisionMonthTotalByUserId(loginUser.getId(), commissionInfoSearch.getYear(), commissionInfoSearch.getMonth());
            if (null == commisionMonthTotal || commisionMonthTotal == 0) {
                log.warn("获取我的当月佣金收入总额为空:" + loginUser.getId());
                return new ResponseEnvelope(true, "获取我的当月佣金收入总额为空");
            }
            commissionInfoListVo.setCommissionTotal(commisionMonthTotal);
        } catch (Exception e) {
            log.error("查询我的当月佣金收入总额异常:" + e);
            return new ResponseEnvelope(false, "查询我的当月佣金收入总额异常");
        }

        //查询当月返现总额
        try {
            Integer expensesCommissionTotal = userCommisionService.getExpensesCommissionTotalByUserId(loginUser.getId(), commissionInfoSearch.getYear(), commissionInfoSearch.getMonth());
            if (null == expensesCommissionTotal || expensesCommissionTotal == 0) {
                log.warn("获取我的当月返现总额为空:" + loginUser.getId());
                return new ResponseEnvelope(true, "获取我的当月返现总额为空");
            }
            commissionInfoListVo.setExpensesCommissionTotal(expensesCommissionTotal);
        } catch (Exception e) {
            log.error("查询我的当月返现总额异常:" + e);
            return new ResponseEnvelope(false, "查询我的当月返现总额异常");
        }



        //获取佣金未返现和已返现的条数
        int commisionInfoCount;
        try {
            commisionInfoCount = userCommisionService.getCommisionInfoCount(loginUser.getId(),commissionInfoSearch);
            if(commisionInfoCount == 0){
                log.warn("获取佣金未返现和已返现的条数为空"+loginUser.getId()+"-----"+gson.toJson(commissionInfoSearch));
                return new ResponseEnvelope(false,"获取佣金未返现和已返现的条数为空");
            }
            commissionInfoListVo.setCommissionInfoCount(commisionInfoCount);
        }catch (Exception e){
            log.error("获取佣金未返现和已返现的条数数据异常:"+e);
            return new ResponseEnvelope(false,"获取佣金未返现和已返现的条数数据异常");
        }

        //获取佣金未返现和已返现的列表详情
        List<CommisionInfoVo> commisionInfoList;
        try {
            commisionInfoList = userCommisionService.getCommissionInfoListVoList(loginUser.getId(),commissionInfoSearch);
            if(commisionInfoList == null || commisionInfoList.size() == 0 ){
                log.warn("获取佣金未返现和已返现的列表详情为空"+loginUser.getId()+"-----"+gson.toJson(commissionInfoSearch));
                return new ResponseEnvelope(false,"获取佣金未返现和已返现的列表详情为空");
            }
            commissionInfoListVo.setCommisionInfoList(commisionInfoList);
        }catch (Exception e){
            log.error("获取佣金未返现和已返现的列表详情异常:"+e);
            return new ResponseEnvelope(false,"获取佣金未返现和已返现的列表详情异常");
        }



        return new ResponseEnvelope(true,"success",commissionInfoListVo);
    }

    @GetMapping("/commissionDetailsPage")
    public Object commissionDetailsPage(Integer userId){

        if (Objects.isNull(userId)){
            return new ResponseEnvelope<>(false,"get userInfo error");
        }

        CommisionDetailsBO detailsBO = null;
        try {
            detailsBO = userCommisionService.obtainUserCommissionDetails(userId);
        } catch (Exception e) {
           log.error("系统错误",e);
           return new ResponseEnvelope<>(false,e.getMessage());
        }
        return new ResponseEnvelope<>(true,detailsBO);
    }

}
