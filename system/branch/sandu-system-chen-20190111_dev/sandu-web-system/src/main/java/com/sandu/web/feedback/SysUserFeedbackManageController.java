package com.sandu.web.feedback;

import com.nork.common.model.LoginUser;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.feedback.input.SysFeedbackQuery;
import com.sandu.api.feedback.input.SysFeedbackReply;
import com.sandu.api.feedback.model.SysUserFeedback;
import com.sandu.api.feedback.output.FeedbackCompany;
import com.sandu.api.feedback.output.SysFeedbackManageDetailVO;
import com.sandu.api.feedback.output.SysFeedbackManageListVO;
import com.sandu.api.feedback.service.SysFeedbackService;
import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.api.sysusermessage.service.SysUserMessageService;
import com.sandu.common.LoginContext;
import com.sandu.commons.PageHelper;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.Utils;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 问题反馈运营后台接口
 * @author WangHaiLin
 * @date 2018/12/13  18:15
 */
@Api(tags = "FeedbackManage", description = "问题反馈运营后台接口")
@RestController
@RequestMapping(value = "/v1/feedback/manage")
public class SysUserFeedbackManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SysUserFeedbackManageController.class);

    @Autowired
    private SysFeedbackService sysFeedbackService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private SysUserMessageService sysUserMessageService;

    @ApiOperation(value = "反馈列表", response = ResponseEnvelope.class)
    @GetMapping("/feedbackList")
    public ResponseEnvelope getFeedbackList(@Valid  SysFeedbackQuery query, BindingResult validResult) {
        logger.error("反馈列表查询入参query{}",query);
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        //去电话号码前后空格
        if (StringUtils.isNotBlank(query.getPhone())){
            query.setPhone(query.getPhone().trim());
        }
        if (StringUtils.isNotBlank(query.getWxNickName())){
            query.setWxNickName(query.getWxNickName().trim());
        }
        //去企业名称前后空格
        if (StringUtils.isNotBlank(query.getCompanyName())){
            query.setCompanyName(query.getCompanyName().trim());
        }
        try{
            Integer count = sysFeedbackService.getCount(query);
            List<SysFeedbackManageListVO> list=null;
            if (count>0){
                //分页处理
                if (null==query.getLimit())
                    query.setLimit(10);
                if (null==query.getPage())
                    query.setPage(1);
                PageHelper page = PageHelper.getPage(count, query.getLimit(), query.getPage());
                query.setStart(page.getStart());
                list = sysFeedbackService.getList(query);
            }
            return new ResponseEnvelope<>(true,count,list);
        }catch (Exception e){
            logger.error("反馈列表查询系统异常",e);
            return new ResponseEnvelope<>(false,"反馈列表查询系统异常");
        }
    }



    @ApiOperation(value = "获取反馈详情", response = ResponseEnvelope.class)
    @GetMapping("/feedbackDetail")
    public ResponseEnvelope getFeedbackDetail(Long feedbackId) {
        logger.error("获取反馈详情参feedbackId{}",feedbackId);
        //参数校验
        if (null==feedbackId){
            return new ResponseEnvelope<>(false,"反馈Id不能为空");
        }
        try{
            //从数据库取值
            SysUserFeedback feedback = sysFeedbackService.getById(feedbackId);
            if (null==feedback){
                return new ResponseEnvelope<>(true,"无此反馈信息",feedback);
            }
            //数据转换
            SysFeedbackManageDetailVO detailVO = this.feedbackTransform(feedback);
            // 图片路径处理
            if (null!=detailVO.getHeadPicId()){
                ResPic resPic = resPicService.selectByPrimaryKey(detailVO.getHeadPicId().longValue());
                if (null!=resPic.getPicPath()){
                    detailVO.setHeadPicPath(resPic.getPicPath());
                }
            }
            if (null!=detailVO.getFeedbackPics()){
                List<Integer> idList = Utils.getIntegerListFromStringList(detailVO.getFeedbackPics());
                List<ResPicPO> resPicList = resPicService.findByIds(idList);
                detailVO.setFeedbackPicPath(resPicList);
            }
            //企业名称处理
            if (null!=detailVO.getCompanyId()){
                BaseCompany company = baseCompanyService.getCompanyById(detailVO.getCompanyId());
                if (null!=company.getCompanyName()){
                    detailVO.setCompanyName(company.getCompanyName());
                }
            }
            return new ResponseEnvelope<>(true,"获取详情成功",detailVO);
        }catch (Exception e){
            logger.error("获取反馈详情系统异常",e);
            return new ResponseEnvelope<>(false,"获取反馈详情系统异常");
        }
    }



    @ApiOperation(value = "反馈问题答复", response = ResponseEnvelope.class)
    @PostMapping("/feedbackReply")
    public ResponseEnvelope feedbackReply(@Valid @RequestBody SysFeedbackReply reply,BindingResult validResult) {
        logger.error("反馈问题答复入参reply{}",reply);
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try{
            //构造service层入参
            SysUserFeedback feedback=sysFeedbackService.getById(reply.getId());
            if (null==feedback){
                return new ResponseEnvelope<>(false,"参数中Id不对");
            }
            feedback.setId(reply.getId().intValue());
            feedback.setReplyUserId(loginUser.getId());
            feedback.setDealStatus(1);
            feedback.setReplyContent(reply.getReplyContent());
            feedback.setGmtReply(new Date());
            feedback.setGmtModified(new Date());
            int result = sysFeedbackService.modifier(feedback);
            if (result>0){
                //添加系统消息
                SysUserMessage messages = this.createMessages(reply.getUserId(),loginUser.getLoginName()
                        , reply.getId(), reply.getReplyContent(), feedback.getPlatformId());
                int messageResult = sysUserMessageService.insertSysUserMessage(messages);
                if (messageResult<=0){
                    return new ResponseEnvelope<>(false,"答复成功,但添加系统消息失败");
                }
                return new ResponseEnvelope<>(true,"答复成功");
            }
            return new ResponseEnvelope<>(false,"答复失败");

        }catch (Exception e){
            logger.error("反馈问题答复系统异常",e);
            return new ResponseEnvelope<>(false,"反馈问题答复系统异常");
        }
    }




    @ApiOperation(value = "删除反馈问题", response = ResponseEnvelope.class)
    @DeleteMapping("/feedbackDelete")
    public ResponseEnvelope feedbackDelete(Long feedbackId) {
        logger.error("删除反馈问题入参feedbackId{}",feedbackId);
        //参数校验
        if (null==feedbackId){
            return new ResponseEnvelope<>(false,"反馈Id不能为空！");
        }
        try{
            //构造删除入参
            SysUserFeedback feedback=new SysUserFeedback();
            feedback.setId(feedbackId.intValue());
            feedback.setIsDeleted(1);
            //修改删除字段
            int result = sysFeedbackService.modifier(feedback);
             if (result>0){
                 sysUserMessageService.deleteByTaskId(feedbackId.intValue());
                 return new ResponseEnvelope<>(true,"删除成功！");
             }
            return new ResponseEnvelope<>(false,"删除失败！");
        }catch (Exception e){
            logger.error("反馈问题答复系统异常",e);
            return new ResponseEnvelope<>(false,"反馈问题答复系统异常！");
        }
    }




    @ApiOperation(value = "查询反馈企业", response = ResponseEnvelope.class)
    @DeleteMapping("/feedbackCompany")
    public ResponseEnvelope feedbackCompany() {
        try{
            List<FeedbackCompany> feedbackCompany = sysFeedbackService.getFeedbackCompany();
            if (feedbackCompany.size()>0){
                return new ResponseEnvelope<>(true,feedbackCompany.size(),feedbackCompany);
            }
            return new ResponseEnvelope<>(false,"查询反馈企业失败！");
        }catch (Exception e){
            logger.error("查询反馈企业复系统异常",e);
            return new ResponseEnvelope<>(false,"查询反馈企业复系统异常！");
        }
    }


    /**
     * 详情获取数据转换
     * @param feedback 数据库查询结果
     * @return SysFeedbackManageDetailVO 详情输出实体
     */
    private SysFeedbackManageDetailVO feedbackTransform(SysUserFeedback feedback){
        SysFeedbackManageDetailVO detailVO=new SysFeedbackManageDetailVO();
        detailVO.setDealStatus(feedback.getDealStatus());
        detailVO.setEstimate(feedback.getEstimate());
        detailVO.setFeedbackContent(feedback.getFeedbackContent());
        detailVO.setFeedbackId(feedback.getId());
        detailVO.setPlatformId(feedback.getPlatformId());
        detailVO.setGmtCreate(Utils.formatDate(feedback.getGmtCreate(),"yyyy-MM-dd HH:mm:ss"));
        detailVO.setGmtReply(Utils.formatDate(feedback.getGmtReply(),"yyyy-MM-dd HH:mm:ss"));
        detailVO.setHeadPicId(feedback.getHeadPicId());
        detailVO.setPhone(feedback.getPhone());
        detailVO.setReplyContent(feedback.getReplyContent());
        detailVO.setUserId(feedback.getUserId());
        if (null!=feedback.getFeedbackPics()){
            detailVO.setFeedbackPics(feedback.getFeedbackPics());
        }
        if (null!=feedback.getOpenId()){
            detailVO.setOpenId(feedback.getOpenId());
        }
        if (null!=feedback.getUserName()){
            detailVO.setUserName(feedback.getUserName());
        }
        if (null!=feedback.getWxNickName()){
            detailVO.setWxNickName(feedback.getWxNickName());
        }
        if (null!=feedback.getCompanyId()){
            detailVO.setCompanyId(feedback.getCompanyId());
        }

        return detailVO;
    }


    /**
     * 构造用户消息
     * @param userId 消息结束者Id
     * @param creator 创建者
     * @param feedbackId 问题反馈Id
     * @param content 内容
     * @return
     */
    private SysUserMessage createMessages(Long userId,String creator,Long feedbackId,String content,Integer platformId){
        SysUserMessage message=new SysUserMessage();
        message.setUserId(userId.intValue());
        message.setContent(content);
        message.setMessageType((byte)2);//系统消息
        message.setIsRead((byte)0);
        message.setStatus((byte)1);//成功
        message.setCreator(creator);
        message.setRemark("运营平台答复客户反馈后发生系统消息");
        message.setTitle("问题反馈答复");
        message.setTaskId(feedbackId.intValue());//taskId=feedbackId
        message.setPlatformId(platformId.longValue());
        return message;
    }




}
