package com.sandu.web.feedback;

import com.sandu.api.feedback.input.SysFeedbackAdd;
import com.sandu.api.feedback.input.SysFeedbackEstimate;
import com.sandu.api.feedback.model.SysUserFeedback;
import com.sandu.api.feedback.output.SysFeedbackAdminListVO;
import com.sandu.api.feedback.output.SysFeedbackWebDetailVO;
import com.sandu.api.feedback.output.SysFeedbackWebListVO;
import com.sandu.api.feedback.service.SysFeedbackService;
import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.platform.model.BasePlatform;
import com.sandu.api.platform.service.BasePlatformService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.Utils;
import com.sandu.constant.FeedbackConstant;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户问题反馈前端接口
 * @author WangHaiLin
 * @date 2018/12/13  18:12
 */
@Api(tags = "FeedbackWeb", description = "问题反馈前端接口")
@RestController
@RequestMapping(value = "/v1/feedback/web")
public class SysUserFeedbackWebController  extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SysUserFeedbackWebController.class);

    @Autowired
    private SysFeedbackService sysFeedbackService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BasePlatformService basePlatformService;

    @ApiOperation(value = "新增反馈", response = ResponseEnvelope.class)
    @PostMapping("/feedbackAdd")
    public ResponseEnvelope addFeedback(@Valid @RequestBody SysFeedbackAdd feedbackAdd, BindingResult validResult,
                                        HttpServletRequest request) {
        logger.info("新增反馈入参feedbackAdd{}",feedbackAdd);
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        try{
            Integer platformId=0;
            if (null==feedbackAdd.getPlatformId()){
                platformId = this.getPlatFormId(request);
            }else{
                platformId=feedbackAdd.getPlatformId();
            }

             if (platformId==0){
                 return new ResponseEnvelope(false,"从头部获取平台编码失败");
             }
             //数据转换
            SysUserFeedback feedback = this.feedbackAddTransform(feedbackAdd);
            //平台Id赋值
            feedback.setPlatformId(platformId);
            SysUser user = sysUserService.getUserById(feedback.getUserId().longValue());
            logger.info("当前用户：{}"+user);
            if (null!=user){
                if (null!=user.getPicId()){
                    feedback.setHeadPicId(user.getPicId().intValue());
                }
                //新增来自随选网和企业小程序时,微信昵称和openId必填
                if (feedback.getPlatformId()==14||feedback.getPlatformId()==16){
                    feedback.setWxNickName(user.getNickName());
                    feedback.setOpenId(user.getOpenId());
                    feedback.setAppId(user.getAppId());
                    feedback.setCompanyId(user.getMiniProgramCompanyId().intValue());
                }else{
                    feedback.setUserName(user.getNickName());
                    if (null!=user.getBusinessAdministrationId()){
                        feedback.setCompanyId(user.getBusinessAdministrationId().intValue());
                    }else if(null!=user.getCompanyId()){
                        feedback.setCompanyId(user.getCompanyId().intValue());
                    }
                }
            }
            int result = sysFeedbackService.add(feedback);
            if (result>0){
                return new ResponseEnvelope<>(true,"新增反馈成功");
            }
            return new ResponseEnvelope<>(false,"新增反馈失败");
        }catch (Exception e){
            logger.error("反馈问题新增系统异常",e);
            return new ResponseEnvelope<>(false,"反馈问题新增系统异常");
        }
    }


    private Integer getPlatFormId(HttpServletRequest request){
        String platformCode = request.getHeader("Platform-Code");
        logger.info("处理平台ID----头部获取平台编码：{}",platformCode);
        Integer platformId=null;
        if (null!=platformCode) {
            if (platformCode.equals(FeedbackConstant.PLATFORM_CODE_MINIPROGRAM)) {
                String referer = request.getHeader("Referer");
                logger.info("处理平台ID----头部获取referer：{}",referer);
                if (referer != null) {
                    String result = referer.substring(referer.indexOf("com/") + 4, referer.length());
                    String[] parts = result.split("/");
                    if (parts[0].equals(FeedbackConstant.XUANZHUANG_REFERER)) {
                        platformId = 16;//随选网
                    } else {
                        platformId = 14;//企业小程序
                    }
                }else{
                    platformId = 0;
                }
            } else if (platformCode.equals(FeedbackConstant.PLATFORM_CODE_MOBILE2B)) {
                String referer = request.getHeader("Referer");
                logger.info("处理平台ID----头部获取referer：{}",referer);
                if (referer != null) {
                    if(referer.contains("https://mm.sanduspace.com/")){//移动端
                        platformId = 1;//移动端
                    }else{
                        String result = referer.substring(referer.indexOf("com/") + 4, referer.length());
                        String[] parts = result.split("/");
                        if (parts[0].equals(FeedbackConstant.XUANZHUANG_REFERER)) {
                            platformId = 16;//随选网
                        } else {
                            platformId = 14;//企业小程序
                        }
                    }
                } else {
                    platformId = 0;
                }
            } else {
                BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
                logger.info("新增反馈----根据平台编码查询平台信息 basePlatform{}", basePlatform);
                if (null != basePlatform) {
                    platformId = basePlatform.getId();
                }else{
                    platformId = 0;
                }
            }
            return platformId;
        }
        return 0;
    }



    @ApiOperation(value = "问题反馈列表", response = SysFeedbackWebListVO.class)
    @GetMapping("/feedbackList")
    public ResponseEnvelope feedbackList(Long userId,Integer platformId,HttpServletRequest request) {
        logger.info("问题列表查询入参userId{}",userId);
        // 校验参数
        if (null==userId) {
            return new ResponseEnvelope<>(false,"userId不能为空");
        }
        try{
            //从头部获取平台编码
            Integer platformIds=0;
            if (null==platformId){
                platformIds = this.getPlatFormId(request);
            }else{
                platformIds=platformId;
            }
            if (platformIds==0){
                return new ResponseEnvelope(false,"从头部获取平台编码失败");
            }
            List<SysFeedbackWebListVO> feedbackList = sysFeedbackService.getListByUserId(userId,platformIds);
            if (feedbackList.size()>0){
                return new ResponseEnvelope<>(true,feedbackList.size(),feedbackList);
            }
            return new ResponseEnvelope<>(false,"查询列表为空");
            /*}
            return new ResponseEnvelope<>(false,"从头部获取平台编码失败");*/

        }catch (Exception e){
            logger.error("问题反馈列表系统异常",e);
            return new ResponseEnvelope<>(false,"问题反馈列表系统异常");
        }
    }



    @ApiOperation(value = "问题反馈详情", response = SysFeedbackWebDetailVO.class)
    @RequestMapping("/feedbackDetail")
    public ResponseEnvelope feedbackDetail(Long feedbackId) {
        logger.info("问题反馈详情入参feedbackId{}",feedbackId);
        // 校验参数
        if (null==feedbackId) {
            return new ResponseEnvelope<>(false,"feedbackId不能为空");
        }
        try{
            //从数据库查询
            SysUserFeedback feedback = sysFeedbackService.getById(feedbackId);
            if (null==feedback){
                return new ResponseEnvelope<>(true,"无此反馈信息",feedback);
            }
            //已回复，并且未读-----修改是否已读标识
            if (feedback.getDealStatus()==1&&feedback.getIsRead()==0){
                feedback.setIsRead(1);
                sysFeedbackService.modifier(feedback);
            }
            //数据转换
            SysFeedbackWebDetailVO detailVO = this.feedbackTransform(feedback);
            //处理问题反馈图片
            if (null!=feedback.getFeedbackPics()){
                List<Integer> idList = Utils.getIntegerListFromStringList(feedback.getFeedbackPics());
                List<ResPicPO> resPicList = resPicService.findByIds(idList);
                detailVO.setFeedbackPicPath(resPicList);
            }
            return new ResponseEnvelope<>(true,"获取反馈详情成功",detailVO);
        }catch (Exception e){
            logger.error("问题反馈详情系统异常",e);
            return new ResponseEnvelope<>(false,"问题反馈详情系统异常");
        }
    }


    @ApiOperation(value = "反馈答复评价", response = ResponseEnvelope.class)
    @PostMapping("/feedbackEstimate")
    public ResponseEnvelope feedbackEstimate(@Valid @RequestBody SysFeedbackEstimate estimate,BindingResult validResult) {
        logger.info("反馈答复评价入参estimate{}",estimate);
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        try{
            SysUserFeedback feedback = sysFeedbackService.getById(estimate.getId());
            //构造答复service层入参
            //SysUserFeedback feedback=new SysUserFeedback();
            feedback.setId(estimate.getId().intValue());
            feedback.setEstimate(estimate.getEstimate());
            //更新=数据
            int result = sysFeedbackService.modifier(feedback);
            if (result>0){
                if (feedback.getPlatformId()==14||feedback.getPlatformId()==16){



                }
                return new ResponseEnvelope<>(true,"评价成功");
            }
            return new ResponseEnvelope<>(false,"评价失败！");
        }catch (Exception e){
            logger.error("反馈答复评价系统异常",e);
            return new ResponseEnvelope<>(false,"反馈答复评价系统异常");
        }
    }




    @ApiOperation(value = "查询用户手机号", response = ResponseEnvelope.class)
    @RequestMapping("/getUserMobile")
    public ResponseEnvelope getUserMobile(Long userId,String msgId) {
        logger.info("反馈答复评价入参estimate{}",userId);
        // 校验参数
        if (null==userId) {
            return new ResponseEnvelope<>(false,"用户Id不能为空",msgId);
        }
        try{
            SysUser sysUser = sysUserService.get(userId.intValue());
            if (null!=sysUser){
                if (StringUtils.isNotBlank(sysUser.getMobile())){
                    return new ResponseEnvelope<>(true,sysUser.getMobile(),msgId,"获取电话号码成功");
                }
                return new ResponseEnvelope<>(false,"用户信息中无电话号码",msgId);
            }
            return new ResponseEnvelope<>(false,"通过Id查询用户失败",msgId);
        }catch (Exception e){
            logger.error("查询用户手机号系统异常",e);
            return new ResponseEnvelope<>(false,"查询用户手机号系统异常");
        }
    }





    //******************PC端接口***********************//

    @ApiOperation(value = "PC端新增反馈", response = ResponseEnvelope.class)
    @PostMapping("/pc/feedbackAdd")
    public ResponseEnvelope pcAddFeedback(SysFeedbackAdd feedbackAdd) {
        logger.info("新增反馈入参feedbackAdd{}",feedbackAdd);
        // 校验参数
        if (null==feedbackAdd.getUserId()||null==feedbackAdd.getFeedbackContent()) {
            return new ResponseEnvelope<>(feedbackAdd.getMsgId(),false,"必传参数为空");
        }
        try{
            //数据转换
            SysUserFeedback feedback = this.feedbackAddTransform(feedbackAdd);
            //平台Id赋值
            feedback.setPlatformId(2);
            SysUser user = sysUserService.getUserById(feedback.getUserId().longValue());
            if (null!=user){
                if (null!=user.getPicId()){
                    feedback.setHeadPicId(user.getPicId().intValue());
                }
                feedback.setUserName(user.getNickName());
                if (null!=user.getBusinessAdministrationId()){
                    feedback.setCompanyId(user.getBusinessAdministrationId().intValue());
                }else if(null!=user.getCompanyId()){
                    feedback.setCompanyId(user.getCompanyId().intValue());
                }
            }
            int result = sysFeedbackService.add(feedback);
            if (result>0){
                return new ResponseEnvelope<>(feedbackAdd.getMsgId(),true,"新增反馈成功");
            }
            return new ResponseEnvelope<>(feedbackAdd.getMsgId(),false,"新增反馈失败");
        }catch (Exception e){
            logger.error("反馈问题新增系统异常",e);
            return new ResponseEnvelope<>(feedbackAdd.getMsgId(),false,"反馈问题新增系统异常");
        }
    }


    @ApiOperation(value = "商家后台、PC端我的反馈列表", response = SysFeedbackWebListVO.class)
    @RequestMapping("/admin/api/feedbackList")
    public ResponseEnvelope adminFeedbackList(Long userId,HttpServletRequest request,String msgId) {
        logger.info("问题列表查询入参userId{}",userId);
        // 校验参数
        if (null==userId) {
            return new ResponseEnvelope<>(msgId,false,"userId不能为空");
        }
        try{
            //从头部获取平台编码
            String platformCode = request.getHeader("Platform-Code");
            if (null==platformCode){
                return new ResponseEnvelope<>(msgId,false,"从头部获取平台编码失败");
            }
            //获取平台信息
            BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
            if (null==basePlatform){
                return new ResponseEnvelope<>(msgId,false,"根据平台编码查询平台信息为空");
            }
            List<SysUserFeedback> feedbackList = sysFeedbackService.getByUserId(userId,basePlatform.getId());
            if (feedbackList.size()>0){
                List<SysFeedbackAdminListVO> list=new ArrayList<>();
                for (SysUserFeedback feedback:feedbackList){
                    SysFeedbackAdminListVO vo=new SysFeedbackAdminListVO();
                    //反馈图片
                    if (null!=feedback.getFeedbackPics()){
                        List<Integer> idList = Utils.getIntegerListFromStringList(feedback.getFeedbackPics());
                        List<ResPicPO> resPicList = resPicService.findByIds(idList);
                        vo.setFeedbackPicPath(resPicList);
                    }
                    vo.setFeedbackId(feedback.getId());
                    vo.setDealStatus(feedback.getDealStatus());
                    vo.setEstimate(feedback.getEstimate());
                    vo.setFeedbackContent(feedback.getFeedbackContent());
                    if (null!=feedback.getGmtCreate()){
                        String date = Utils.formatDate(feedback.getGmtCreate(), "yyyy-MM-dd HH:mm:ss");
                        vo.setGmtCreate(date);
                    }
                    if (null!=feedback.getGmtReply()){
                        String date = Utils.formatDate(feedback.getGmtReply(), "yyyy-MM-dd HH:mm:ss");
                        vo.setGmtReply(date);
                    }
                    vo.setReplyContent(feedback.getReplyContent());
                    //将已回复未阅读的改成已读
                    if (feedback.getDealStatus()==1&&feedback.getIsRead()==0){
                        feedback.setIsRead(1);
                        sysFeedbackService.modifier(feedback);
                    }
                    list.add(vo);
                }
                return new ResponseEnvelope<>(msgId,true,list.size(),list,"列表查询成功");
            }
            return new ResponseEnvelope<>(msgId,false,"查询列表为空");
        }catch (Exception e){
            logger.error("问题反馈列表系统异常",e);
            return new ResponseEnvelope<>(msgId,false,"问题反馈列表系统异常");
        }
    }


    @ApiOperation(value = "PC端反馈答复评价", response = ResponseEnvelope.class)
    @PostMapping("/pc/feedbackEstimate")
    public ResponseEnvelope pcFeedbackEstimate(SysFeedbackEstimate estimate) {
        logger.info("反馈答复评价入参estimate{}",estimate);
        // 校验参数
        if (estimate.getEstimate()==null||null==estimate.getId()) {
            return new ResponseEnvelope<>(estimate.getMsgId(),false,"参数有误！");
        }
        try{
            //构造答复service层入参
            SysUserFeedback feedback=new SysUserFeedback();
            feedback.setId(estimate.getId().intValue());
            feedback.setEstimate(estimate.getEstimate());
            //更新=数据
            int result = sysFeedbackService.modifier(feedback);
            if (result>0){
                return new ResponseEnvelope<>(estimate.getMsgId(),true,"评价成功");
            }
            return new ResponseEnvelope<>(estimate.getMsgId(),false,"评价失败！");
        }catch (Exception e){
            logger.error("反馈答复评价系统异常",e);
            return new ResponseEnvelope<>(estimate.getMsgId(),false,"反馈答复评价系统异常");
        }
    }



    /**
     * 新增数据转化(构造service层入参)
     * @param add 前端传值
     * @return SysUserFeedback
     */
    private SysUserFeedback feedbackAddTransform(SysFeedbackAdd add){
        SysUserFeedback feedback=new SysUserFeedback();
        feedback.setUserId(add.getUserId());
        feedback.setFeedbackContent(add.getFeedbackContent());
        feedback.setFeedbackPics(add.getFeedbackPics());
        feedback.setPhone(add.getPhone());
        feedback.setGmtCreate(new Date());
        feedback.setGmtModified(new Date());
        feedback.setDealStatus(0);//未处理
        feedback.setIsDeleted(0);
        feedback.setIsRead(0);//未读
        return feedback;
    }


    /**
     * 详情输出数据转换
     * @param feedback 数据库查询结果
     * @return SysFeedbackWebDetailVO 输出实体
     */
    private SysFeedbackWebDetailVO feedbackTransform(SysUserFeedback feedback){
        SysFeedbackWebDetailVO detailVO=new SysFeedbackWebDetailVO();
        detailVO.setDealStatus(feedback.getDealStatus());
        detailVO.setEstimate(feedback.getEstimate());
        detailVO.setFeedbackContent(feedback.getFeedbackContent());
        detailVO.setFeedbackId(feedback.getId());
        detailVO.setGmtCreate(Utils.formatDate(feedback.getGmtCreate(),"yyyy-MM-dd HH:mm:ss"));
        detailVO.setGmtReply(Utils.formatDate(feedback.getGmtReply(),"yyyy-MM-dd HH:mm:ss"));
        detailVO.setReplyContent(feedback.getReplyContent());
        return detailVO;
    }



    @ApiOperation(value = "是否有未读反馈", response = SysFeedbackWebListVO.class)
    @RequestMapping("/is/read/feedback")
    public ResponseEnvelope isHaveNotReadFeedbac(Long userId,HttpServletRequest request,String msgId) {
        logger.info("问题列表查询入参userId{}",userId);
        // 校验参数
        if (null==userId) {
            return new ResponseEnvelope<>(msgId,false,"userId不能为空");
        }
        //从头部获取平台编码
        String platformCode = request.getHeader("Platform-Code");
        if (null==platformCode){
            return new ResponseEnvelope<>(msgId,false,"从头部获取平台编码失败");
        }
        //获取平台信息
        BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
        if (null==basePlatform){
            return new ResponseEnvelope<>(msgId,false,"根据平台编码查询平台信息为空");
        }
        try{
            List<SysUserFeedback> feedbackList = sysFeedbackService.getByUserId(userId,basePlatform.getId());
            int hasNotRead=0;
            if (feedbackList.size()>0){
                List<SysUserFeedback> list=new ArrayList<>();
                for (SysUserFeedback feedback:feedbackList){
                    //判断是否已回复尚未查看
                    if (feedback.getDealStatus()==1&&feedback.getIsRead()==0)
                        hasNotRead++;
                    list.add(feedback);
                }
                if (hasNotRead>0){
                    return new ResponseEnvelope<>(msgId,true,"有未读反馈答复");
                }
                return new ResponseEnvelope<>(msgId,false,"没有未读反馈答复");
            }
            return new ResponseEnvelope<>(msgId,false,"没有未读反馈答复");
        }catch (Exception e){
            logger.error("是否有未读反馈系统异常",e);
            return new ResponseEnvelope<>(msgId,false,"是否有未读反馈系统异常");
        }
    }



   /* @ApiOperation(value = "商家后台我的反馈列表", response = SysFeedbackWebListVO.class)
    @GetMapping("/admin/api/feedbackList")
    public ResponseEnvelope adminApiFeedbackList(Long userId,HttpServletRequest request) {
        logger.info("问题列表查询入参userId{}",userId);
        // 校验参数
        if (null==userId) {
            return new ResponseEnvelope<>(false,"userId不能为空");
        }
        try{
            //从头部获取平台编码
            String platformCode = request.getHeader("Platform-Code");
            if (null==platformCode){
                return new ResponseEnvelope<>(false,"从头部获取平台编码失败");
            }
            //获取平台信息
            BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
            if (null==basePlatform){
                return new ResponseEnvelope<>(false,"根据平台编码查询平台信息为空");
            }
            List<SysUserFeedback> feedbackList = sysFeedbackService.getByUserId(userId,basePlatform.getId());
            if (feedbackList.size()>0){
                List<SysFeedbackAdminListVO> list=new ArrayList<>();
                for (SysUserFeedback feedback:feedbackList){
                    SysFeedbackAdminListVO vo=new SysFeedbackAdminListVO();
                    //反馈图片
                    if (null!=feedback.getFeedbackPics()){
                        List<Integer> idList = Utils.getIntegerListFromStringList(feedback.getFeedbackPics());
                        List<ResPicPO> resPicList = resPicService.findByIds(idList);
                        vo.setFeedbackPicPath(resPicList);
                    }
                    vo.setFeedbackId(feedback.getId());
                    vo.setDealStatus(feedback.getDealStatus());
                    vo.setEstimate(feedback.getEstimate());
                    vo.setFeedbackContent(feedback.getFeedbackContent());
                    if (null!=feedback.getGmtCreate()){
                        String date = Utils.formatDate(feedback.getGmtCreate(), "yyyy-MM-dd HH:mm:ss");
                        vo.setGmtCreate(date);
                    }
                    if (null!=feedback.getGmtReply()){
                        String date = Utils.formatDate(feedback.getGmtReply(), "yyyy-MM-dd HH:mm:ss");
                        vo.setGmtReply(date);
                    }
                    vo.setReplyContent(feedback.getReplyContent());
                    //将已回复未阅读的改成已读
                    if (feedback.getDealStatus()==1&&feedback.getIsRead()==0){
                        feedback.setIsRead(1);
                        sysFeedbackService.modifier(feedback);
                    }
                    list.add(vo);
                }
                return new ResponseEnvelope<>("",true,list.size(),list,"列表查询成功");
            }
            return new ResponseEnvelope<>(false,"查询列表为空");
        }catch (Exception e){
            logger.error("问题反馈列表系统异常",e);
            return new ResponseEnvelope<>(false,"问题反馈列表系统异常");
        }
    }*/



}
