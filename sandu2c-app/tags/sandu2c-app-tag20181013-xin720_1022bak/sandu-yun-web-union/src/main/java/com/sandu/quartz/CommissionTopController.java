package com.sandu.quartz;


import com.nork.common.model.LoginUser;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.BadWordUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.supplydemand.convert.BaseSupplyDemandConvert;
import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.supplydemand.model.BaseSupplyDemand;
import com.sandu.supplydemand.model.SupplyDemandPic;
import com.sandu.supplydemand.output.SupplyDemandCategoryVo;
import com.sandu.supplydemand.service.SupplyDemandCategoryService;
import com.sandu.supplydemand.service.SupplyDemandPicService;
import com.sandu.supplydemand.service.SupplyDemandService;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserRoleContants;
import com.sandu.user.model.UserVo;
import com.sandu.user.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.condition.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/4/27 0027PM 5:37
 */
@Slf4j
@RestController
@RequestMapping("/v1/union/commission")
public class CommissionTopController {

    private static final String MEDIATION_TEST_SIGN= "cityUserTest";

    private static final String KEY= "gYwk78W0suuYnUZkc9W8jBNxGowiZvZw6PfBhS8mEQ0K90W1zt0Daj7izpXcKmB7ioJYpOL0TLyrKKXr4o0vJd6WpcvwYUheEn3";

    @Autowired
    private SysUserService sysUserService;
    /**
     * @Title: 新增佣金和邀请
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/addcommission", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope addCommission(String key,HttpServletRequest request) {
        if(StringUtils.isBlank(key)||!KEY.equals(key)){
            return new ResponseEnvelope(false,"无权限操作");
        }
        //获取100个中介假账号ID
        List<Integer> idList =  sysUserService.getMediationTestId(MEDIATION_TEST_SIGN);
        if(null==idList || idList.size() == 0){
            log.info("未查询到中介假账号");
            return new ResponseEnvelope(false,"未查询到中介假账号");
        }

        //随机抽取2个账号新增4000-8000的佣金数
        Integer firstId = idList.get(new Random().nextInt(idList.size()-1)) ;
        Integer secondId = idList.get(new Random().nextInt(idList.size()-1));
        Integer commission = new Random().nextInt(4001)+4000;
        Boolean flag = sysUserService.addUserCommssion(firstId,secondId,commission);
        if(flag){
            log.info("账号:"+firstId+"----"+secondId+"新增4000-8000的佣金数完成");
            return new ResponseEnvelope(true,"","账号:"+firstId+"----"+secondId+"新增4000-8000的佣金数完成");
        }else {
            log.info("账号:"+firstId+"----"+secondId+"新增4000-8000的佣金数失败");
            return new ResponseEnvelope(false,"账号:"+firstId+"----"+secondId+"新增4000-8000的佣金数失败");
        }

    }

    //新增邀请
    @RequestMapping(value = "/addinvite", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope addInvite(String key,HttpServletRequest request) {
        if(StringUtils.isBlank(key)||!KEY.equals(key)){
            return new ResponseEnvelope(false,"无权限操作");
        }
        //获取100个中介假账号ID
        List<Integer> idList =  sysUserService.getMediationTestId(MEDIATION_TEST_SIGN);
        if(null==idList || idList.size() == 0){
            log.info("未查询到中介假账号");
            return new ResponseEnvelope(false,"未查询到中介假账号");
        }

        //随机抽取2个账号自增一个邀请数
        Integer firstId = idList.get(new Random().nextInt(idList.size()-1)) ;
        Integer secondId = idList.get(new Random().nextInt(idList.size()-1));
        Boolean flag = sysUserService.addInviteCount(firstId,secondId);

        if(flag){
            log.info("账号:"+firstId+"----"+secondId+"自增邀请数完成");
            return new ResponseEnvelope(true,"","账号:"+firstId+"----"+secondId+"自增邀请数完成");
        }else {
            log.info("账号:"+firstId+"----"+secondId+"自增邀请数失败");
            return new ResponseEnvelope(false,"账号:"+firstId+"----"+secondId+"自增邀请数失败");
        }

    }


}
