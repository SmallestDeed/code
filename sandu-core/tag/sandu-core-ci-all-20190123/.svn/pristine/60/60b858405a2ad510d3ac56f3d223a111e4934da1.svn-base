package com.sandu.web.fullhouse.controller;

import com.google.gson.Gson;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.base.service.RedisService;
import com.sandu.api.fullhouse.common.exception.BizException;
import com.sandu.api.fullhouse.input.*;
import com.sandu.api.fullhouse.output.DesignPlanStyleVO;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanEditPageVO;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanVO;
import com.sandu.api.fullhouse.output.MatchInfoVO;
import com.sandu.api.fullhouse.service.biz.FullHouseDesignPlanBizService;
import com.sandu.api.fullhouse.service.biz.FullHousePCBizService;
import com.sandu.api.fullhouse.service.biz.FullHouseToCBizService;
import com.sandu.common.LoginContext;
import com.sandu.interceptor.NeedNotLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2(topic = "[全屋方案接口服务]")
@RestController
@RequestMapping("/v1/core/fullHouse")
@Api(tags = "全屋方案接口")
public class FullHouseDesignPlanController {
    @Autowired
    private FullHousePCBizService fullHousePCBizService;
    @Autowired
    private FullHouseToCBizService fullHouseToCBizService;
    @Autowired
    private RedisService redisService;
    private static final Gson gson = new Gson();

    @ApiOperation(value = "查询可以添加到全屋方案的单空间方案组", response = ResponseEnvelope.class)
    @PostMapping("/designPlanGroup")
    @NeedNotLogin
    public ResponseEnvelope selectDesignPlanGroup(@RequestBody @Validated DesignPlanQuery query, BindingResult bindingResult) {
        // 校验参数
        if (bindingResult.hasErrors()) {
            String errorMessages = getErrorMessages(bindingResult);
            return new ResponseEnvelope(false, errorMessages);
        }
        // 获取当前登录用户
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user == null) {
            return new ResponseEnvelope(false, "用户未登录");
        }
        log.info("当前登录用户id:{}", user.getId());
        query.setUserId(user.getId());
        // 查询当前用户可用于制作全屋方案的方案总数
        // 可用于全屋方案规则：1.当前用户制作的；2.已审核通过的推荐方案
        Integer count = fullHousePCBizService.selectSingleDesignPlanCount(query);
        log.info("查出的方案总数:{}", count);
        if (count <= 0) {
            return new ResponseEnvelope(true, "未找到符合条件的方案");
        }
        // 查询方案
        List<DesignPlanVO> designPlanVOList = fullHousePCBizService.selectSingleDesignPlan(query);
        log.info("实际查出的方案数量:{}", designPlanVOList.size());
        return new ResponseEnvelope(true, count, designPlanVOList);
    }

    @ApiOperation(value = "查询任意空间类型下所有的方案风格", response = ResponseEnvelope.class)
    @GetMapping("/fullHouseStyle")
    @NeedNotLogin
    public ResponseEnvelope selectDesignPlanStyle(@Validated DesignPlanStyleQuery query, BindingResult bindingResult) {
        // 校验参数
        if (bindingResult.hasErrors()) {
            String errorMessages = getErrorMessages(bindingResult);
            return new ResponseEnvelope(false, errorMessages);
        }
        log.info("查询的空间类型:{}", query.getSpaceType());
        // 查询当前空间类型的所有方案风格
        List<DesignPlanStyleVO> designPlanStyleVOList = fullHousePCBizService.selectDesignPlanStyle(query);
        return new ResponseEnvelope(true, designPlanStyleVOList);
    }

    @ApiOperation(value = "查询全屋方案列表", response = ResponseEnvelope.class)
    @PostMapping("/fullHouseList")
    @NeedNotLogin
    public ResponseEnvelope selectFullHouseDesignPlanList(@Validated FullHouseDesignPlanQuery query,
                                                          String msgId,
                                                          BindingResult bindingResult) {
        // 校验参数
        if (bindingResult.hasErrors()) {
            String errorMessages = getErrorMessages(bindingResult);
            ResponseEnvelope responseEnvelope = new ResponseEnvelope(false, errorMessages);
            responseEnvelope.setMsgId(msgId);
            return responseEnvelope;
        }
        // 获取当前登录用户
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user == null) {
            ResponseEnvelope responseEnvelope = new ResponseEnvelope(false, "用户未登录");
            responseEnvelope.setMsgId(msgId);
            return responseEnvelope;
        }
        log.info("当前登录用户id:{}", user.getId());
        query.setUserId(user.getId());
        // 查找当前用户制作的符合查询条件的全屋方案总数
        int count = fullHousePCBizService.selectFullHouseDesignPlanCount(query);
        log.info("查询出的全屋方案总数,count:{}", count);
        if (count <= 0) {
            ResponseEnvelope responseEnvelope = new ResponseEnvelope(true, "未找到符合条件的全屋方案");
            responseEnvelope.setMsgId(msgId);
            return responseEnvelope;
        }
        // 查找当前用户制作的符合查询条件的全屋方案列表
        List<FullHouseDesignPlanVO> fullHouseDesignPlanVOList = fullHousePCBizService.selectFullHouseDesignPlan(query);
        log.info("实际查出的方案数量:{}", fullHouseDesignPlanVOList.size());
        ResponseEnvelope responseEnvelope = new ResponseEnvelope(true, count, fullHouseDesignPlanVOList);
        responseEnvelope.setMsgId(msgId);
        return responseEnvelope;
    }

    @ApiOperation(value = "制作全屋方案", response = ResponseEnvelope.class)
    @PostMapping("/createFullHouse")
    @NeedNotLogin
    public ResponseEnvelope createFullHouseDesignPlan(@RequestBody @Validated FullHouseDesignPlanAdd fullHouseDesignPlanAdd,
                                                      BindingResult bindingResult) {
        // 校验参数
        if (bindingResult.hasErrors()) {
            String errorMessages = getErrorMessages(bindingResult);
            return new ResponseEnvelope(false, errorMessages);
        }
        // 获取当前登录用户
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user == null) {
            return new ResponseEnvelope(false, "用户未登录");
        }
        log.info("当前登录用户id:{}", user.getId());
        // 制作全屋方案
        log.info("制作全屋方案,fullHouseDesignPlanAdd:{}", gson.toJson(fullHouseDesignPlanAdd));
        try {
            fullHouseDesignPlanAdd.setUserId(user.getId());
            fullHousePCBizService.addFullHouseDesignPlan(fullHouseDesignPlanAdd);
        } catch (Exception e) {
            log.error("", e);
            return new ResponseEnvelope(false, e.getMessage());
        }
        return new ResponseEnvelope(true, "制作全屋方案成功");
    }

    @ApiOperation(value = "删除全屋方案", response = ResponseEnvelope.class)
    @PostMapping("/deleteFullHouse")
    @NeedNotLogin
    public ResponseEnvelope deleteFullHouseDesignPlan(Integer fullHouseId, String msgId) {
        ResponseEnvelope responseEnvelope;
        try {
            log.info("删除全屋方案，fullHouseId:{}", fullHouseId);
            fullHousePCBizService.deleteFullHouseDesignPlan(fullHouseId);
        } catch (Exception e) {
            log.error("", e);
            responseEnvelope = new ResponseEnvelope(false, e.getMessage());
            responseEnvelope.setMsgId(msgId);
            return responseEnvelope;
        }
        responseEnvelope = new ResponseEnvelope(true, "删除全屋方案成功");
        responseEnvelope.setMsgId(msgId);
        return responseEnvelope;
    }

    @ApiOperation(value = "查询全屋方案更换方案时显示的单空间方案列表", response = ResponseEnvelope.class)
    @GetMapping("/fullHouseDetailList")
    @NeedNotLogin
    public ResponseEnvelope selectFullHouseDesignPlanDetailList(@RequestParam("id") Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEnvelope(false, "全屋方案id不合法");
        }
        log.info("跟换全屋方案中的单空间方案，全屋方案ID:{}", id);
        FullHouseDesignPlanEditPageVO vo = fullHousePCBizService.selectFullHouseDesignPlanDetailList(id);
        log.info("跟换全屋方案页面，单空间方案列表查询结果:{}", gson.toJson(vo));
        return new ResponseEnvelope(true, vo);
    }

    @ApiOperation(value = "更新全屋方案信息", response = ResponseEnvelope.class)
    @PostMapping("/updateFullHouseDesignPlan")
    @NeedNotLogin
    public ResponseEnvelope updateFullHouseDesignPlan(@RequestBody @Validated FullHouseDesignPlanUpdate update, BindingResult bindingResult) {
        // 校验参数
        if (bindingResult.hasErrors()) {
            String errorMessages = getErrorMessages(bindingResult);
            return new ResponseEnvelope(false, errorMessages);
        }
        // 获取当前登录用户
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user == null) {
            return new ResponseEnvelope(false, "用户未登录");
        }
        log.info("当前登录用户id:{}", user.getId());
        log.info("更新全屋方案的方案详情,参数update:{}", gson.toJson(update));
        try {
            update.setUserId(user.getId());
            fullHousePCBizService.updateFullHouseDesignPlan(update);
        } catch (Exception e) {
            log.error("", e);
            return new ResponseEnvelope(false, e.getMessage());
        }
        return new ResponseEnvelope(true, "更新成功");
    }

    @ApiOperation(value = "获取户型匹配全屋方案方案")
    @GetMapping("/getMatchInfo")
    @NeedNotLogin
    public Object getMatchInfo(@ApiParam(name = "houseId", value = "户型id") Integer houseId,
                               @ApiParam(name = "fullHousePlanId", value = "全屋方案id") Integer fullHousePlanId) {
        // 参数验证 ->start
        if (houseId == null) {
            return new ResponseEnvelope<>(false, "参数houseId不能为空");
        }
        if (fullHousePlanId == null) {
            return new ResponseEnvelope<>(false, "参数fullHousePlanId不能为空");
        }
        // 参数验证 ->end

        List<MatchInfoVO> result = null;
        try {
            result = fullHousePCBizService.getMatchInfo(houseId, fullHousePlanId);
        } catch (BizExceptionEE e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        }

        return new ResponseEnvelope<>(true, result == null ? 0 : result.size(), result);
    }

    @ApiOperation(value = "装进我家创建全屋方案")
    @PostMapping("/addFullHouseDesignPlan")
    @NeedNotLogin
    public ResponseEnvelope addFullHouseDesignPlanScene(@RequestBody FullHouseDesignPlanAdd fullHouseDesignPlanAdd) {
        // 制作全屋方案
        log.info("装进我家创建全屋方案,fullHouseDesignPlanAdd:{}", gson.toJson(fullHouseDesignPlanAdd));
        String idAndUuid;
        try {
            idAndUuid = fullHouseToCBizService.addFullHouseDesignPlan(fullHouseDesignPlanAdd);
        } catch (Exception e) {
            log.error("", e);
            return new ResponseEnvelope(false, e.getMessage());
        }
        log.info("装进我家---创建---全屋方案成功,全屋方案ID&&UUID:{}", idAndUuid);
        return new ResponseEnvelope(true, "装进我家创建全屋方案成功", idAndUuid);
    }

    @ApiOperation(value = "装进我家修改全屋方案")
    @PostMapping("/updateFullHouseDesignPlanScene")
    @NeedNotLogin
    public ResponseEnvelope updateFullHouseDesignPlanScene(@RequestBody @Validated FullHouseDesignPlanSceneUpdate fullHouseDesignPlanSceneUpdate,
                                                           BindingResult bindingResult){
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 争抢锁的参数
        String key = fullHouseDesignPlanSceneUpdate.getFullHouseId() + "";
        String value = UUID.randomUUID().toString();
        long time = 5L;
        // 获取redis分布式锁
        while (!redisService.set(key, value, "nx", "ex", time)) {
            if (System.currentTimeMillis() - startTime > 1000) {
                return new ResponseEnvelope(false, "操作失败，超时");
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                return new ResponseEnvelope(false, "操作失败");
            }
        }
        // 校验参数
        if (bindingResult.hasErrors()) {
            String errorMessages = getErrorMessages(bindingResult);
            // 释放锁，不是原子性的
            if (value.equals(redisService.get(key))) {
                redisService.del(key);
            }
            return new ResponseEnvelope(false, errorMessages);
        }
        log.info("装进我家修改全屋方案，FullHouseDesignPlanSceneUpdate:{}", gson.toJson(fullHouseDesignPlanSceneUpdate));
        String uuid;
        try {
            uuid = fullHouseToCBizService.updateFullHouseDesignPlan(fullHouseDesignPlanSceneUpdate);
        } catch (Exception e) {
            log.error("", e);
            // 释放锁，不是原子性的
            if (value.equals(redisService.get(key))) {
                redisService.del(key);
            }
            return new ResponseEnvelope(false, e.getMessage());
        }
        log.info("装进我家---修改---全屋方案成功,UUID:{}", uuid);
        // 释放锁，不是原子性的
        if (value.equals(redisService.get(key))) {
            redisService.del(key);
        }
        return new ResponseEnvelope(true, "装进我家修改全屋方案成功", uuid);
    }

    @ApiOperation(value = "单空间装进我家时临时创建的全屋方案，用于在移动端创建全屋方案")
    @GetMapping("/addTempFullHouseDesignPlan")
    @NeedNotLogin
    public ResponseEnvelope addTempFullHouseDesignPlan(Integer userId, Integer oldFullHouseDesignPlanId, Integer houseId){
        // 校验参数
        if (userId == null){
            return new ResponseEnvelope(false, "用户ID为空");
        }
        if (houseId == null){
            return new ResponseEnvelope(false, "户型ID为空");
        }
        log.info("当前用户,userId:{}", userId);
        Integer fullHouseId;
        try {
            fullHouseId = fullHouseToCBizService.addTempFullHouseDesignPlan(userId,oldFullHouseDesignPlanId, houseId);
        } catch (Exception e) {
            log.error("", e);
            return new ResponseEnvelope(false, e.getMessage());
        }
        log.info("创建临时方案成功,fullHouseId:{}", fullHouseId);
        return new ResponseEnvelope(true, "创建临时全屋方案成功", fullHouseId);
    }

    @ApiOperation(value = "在供求信息列表处改造全屋方案时复制全屋方案")
    @PostMapping(value = "/copyFullHouseDesignPlan")
    @NeedNotLogin
    public ResponseEnvelope copyFullHouseDesignPlan(@RequestBody @Validated FullHouseDesignPlanCopy fullHouseDesignPlanCopy,
                                                    BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return new ResponseEnvelope(false,getErrorMessages(bindingResult));
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            return new ResponseEnvelope(false,"请登录");
        }
        Integer userId = loginUser.getId();
        if (null == userId || 0 == userId) {
            return new ResponseEnvelope(false,"用户id为空");
        }

        // idAndMainTaskId
        String idAndMainTaskId;
        try {
            idAndMainTaskId = fullHouseToCBizService.copyFullHouseDesignPlan(fullHouseDesignPlanCopy,userId);
        } catch (BizException e) {
            log.error("copyFullHouseDesignPlan ===> exception:{}",e);
            return new ResponseEnvelope(false,"复制全屋方案失败，"+e);
        }

        if (null == idAndMainTaskId || "".equals(idAndMainTaskId)) {
            return new ResponseEnvelope(false,"复制全屋方案失败 idAndMainTaskId is empty");
        }

        return new ResponseEnvelope(true,"复制全屋方案成功",idAndMainTaskId);
    }

    @GetMapping(value = "/copyMsgFullHouseExist")
    public Object copyMsgFullHouseExist(@RequestParam(value = "historyMsgId") String historyMsgId){

        Assert.hasLength(historyMsgId,"消息id不能为空!");

        //当前登录用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        Assert.notNull(loginUser,"请登录!!!");

        Map<String,Object> dataMap = null;
        try {
            dataMap = fullHouseToCBizService.copyMsgFullHouseExist(historyMsgId,loginUser.getId());
            return new ResponseEnvelope<>(true,dataMap);
        }catch (IllegalArgumentException ill){
            return new ResponseEnvelope<>(false,ill.getMessage());
        }
        catch (Exception e) {
            log.error("系统错误",e);
            return new ResponseEnvelope<>(false,"系统错误");
        }
    }

    /**
     * created by zhangchengda
     * 2018/8/16 12:32
     * 获得所有错误信息拼接的字符串
     *
     * @param bindingResult
     * @return 所有错误信息拼接的字符串
     */
    private String getErrorMessages(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();
        errors.forEach(error -> {
            stringBuilder.append(error.getDefaultMessage())
                    .append(";");
        });
        return stringBuilder.toString();
    }
}
