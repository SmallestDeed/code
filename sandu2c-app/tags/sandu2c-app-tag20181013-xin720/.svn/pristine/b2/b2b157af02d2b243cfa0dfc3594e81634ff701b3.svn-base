package com.sandu.web.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:28 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import com.sandu.user.MediationAuthorizeAdd;
import com.sandu.user.MediationAuthorizeVo;
import com.sandu.user.UserMediationAuthorize;
import com.sandu.user.model.MediationCardTypeContants;
import com.sandu.user.model.MediationCardTypeNameContants;
import com.sandu.user.model.MediationReAuthorizeAdd;
import com.sandu.user.model.UserRoleContants;
import com.sandu.user.service.MediationAuthorizeService;
import com.sandu.validate.AnnotationValidator;
import com.sandu.validate.vo.ValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weisheng
 * @Title: 中介用户管理
 * @Package
 * @Description:
 * @date 2018/8/21 0021AM 10:28
 */
@RestController
@Slf4j
@RequestMapping("/v1/union/authorize")
public class MediationAuthorizeController {



    private static Gson gson = new Gson();


    @Autowired
    private MediationAuthorizeService mediationAuthorizeService;
    @Autowired
    private ResPicService resPicService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }




/*

判断该用户是否认证过
 */

    @RequestMapping(value = "/isauthorize",method = RequestMethod.GET)
    @ResponseBody
    public Object isAuthorize( HttpServletRequest request, HttpServletResponse response) {

        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:"+gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if(null == loginUser.getUserType() || loginUser.getUserType().intValue()!= UserRoleContants.MEDIATION){
            log.warn("该用户角色类型:"+loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        //验证该用户是否已提交审核信息
        UserMediationAuthorize userMediationAuthorize;
        String authorizeStatusName;
        try {
            userMediationAuthorize = mediationAuthorizeService.getMediationAuthorizeByUserId(loginUser.getId());
            if(userMediationAuthorize==null){
                authorizeStatusName = MediationCardTypeNameContants.PENDING_APPLY;
                log.warn("该用户未提交审核申请:"+loginUser.getId());
                return new ResponseEnvelope(false,authorizeStatusName);
            }
            //处理认证类型状态名称,'认证状态,待申请:0,待审核:1,已认证:2,认证失败:3',
            switch (userMediationAuthorize.getStatus()){
                case MediationCardTypeContants.PENDING_AUDIT:
                    authorizeStatusName=MediationCardTypeNameContants.PENDING_AUDIT;
                    break;
                case MediationCardTypeContants.ALREADY_AUTHORIZE:
                    authorizeStatusName = MediationCardTypeNameContants.ALREADY_AUTHORIZE;
                    break;
                case MediationCardTypeContants.AUTHORIZE_FAILURE:
                    authorizeStatusName = MediationCardTypeNameContants.AUTHORIZE_FAILURE;
                    break;
                default:
                    authorizeStatusName = MediationCardTypeNameContants.PENDING_APPLY;
                    return new ResponseEnvelope(false,authorizeStatusName);

            }
        }catch (Exception e){
            log.error("验证用户审核信息异常:"+e);
            authorizeStatusName = MediationCardTypeNameContants.PENDING_APPLY;
            return new ResponseEnvelope(false, authorizeStatusName);
        }

        return new ResponseEnvelope(true, authorizeStatusName,userMediationAuthorize);
    }





/*

获取中介用户认证详情
 */

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public Object mediationAuthorizeInfo(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
        //校验参数
        if (id == null || id == 0) {
            return new ResponseEnvelope(false, "缺失必填参数ID!");
        }

        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:"+gson.toJson(loginUser));


        //校验登录用户角色权限(只允许中介访问)
        if(null == loginUser.getUserType() || loginUser.getUserType().intValue()!=UserRoleContants.MEDIATION){
            log.warn("该用户角色类型:"+loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        UserMediationAuthorize userMediationAuthorize;
        MediationAuthorizeVo mediationAuthorizeVo = new MediationAuthorizeVo();
        try {

            userMediationAuthorize = mediationAuthorizeService.getMediationAuthorizeById(id);
            if (null == userMediationAuthorize) {
                return new ResponseEnvelope(false, "该用户认证信息不存在!" + id);
            }
            BeanUtils.copyProperties(userMediationAuthorize, mediationAuthorizeVo);

            //处理照片地址
            String cardPicPath = "";
            try{
                ResPic resPic = resPicService.get(userMediationAuthorize.getCardPicId());
                if(null != resPic){
                    cardPicPath = resPic.getPicPath();
                }
            }catch (Exception e){
                log.error("查询照片信息异常", e);
                return new ResponseEnvelope(false, "查询照片信息异常!" );
            }
            mediationAuthorizeVo.setCardPicPath(cardPicPath);

            //处理认证类型名称,证件类型,身份证:1,户口簿:2,工作证:3,驾驶证:4',
            switch (mediationAuthorizeVo.getCardType()){
                case MediationCardTypeContants.ID_CARD:
                 mediationAuthorizeVo.setCardTypeName(MediationCardTypeNameContants.ID_CARD);
                 break;
                case MediationCardTypeContants.RESIDENCE_CARD:
                    mediationAuthorizeVo.setCardTypeName(MediationCardTypeNameContants.RESIDENCE_CARD);
                    break;
                case MediationCardTypeContants.EMPLOYEE_CARD:
                    mediationAuthorizeVo.setCardTypeName(MediationCardTypeNameContants.EMPLOYEE_CARD);
                    break;
                case MediationCardTypeContants.DRIVER_CARD:
                    mediationAuthorizeVo.setCardTypeName(MediationCardTypeNameContants.DRIVER_CARD);
                    break;
                default:
                    mediationAuthorizeVo.setCardTypeName(MediationCardTypeNameContants.EMPLOYEE_CARD);
                    break;
            }
            //处理认证类型状态名称,'认证状态,待申请:0,待审核:1,已认证:2,认证失败:3',
            switch (mediationAuthorizeVo.getStatus()){
                case MediationCardTypeContants.PENDING_AUDIT:
                    mediationAuthorizeVo.setStatusName(MediationCardTypeNameContants.PENDING_AUDIT);
                    break;
                case MediationCardTypeContants.ALREADY_AUTHORIZE:
                    mediationAuthorizeVo.setStatusName(MediationCardTypeNameContants.ALREADY_AUTHORIZE);
                    break;
                case MediationCardTypeContants.AUTHORIZE_FAILURE:
                    mediationAuthorizeVo.setStatusName(MediationCardTypeNameContants.AUTHORIZE_FAILURE);
                    break;
                default:
                    mediationAuthorizeVo.setStatusName(MediationCardTypeNameContants.PENDING_APPLY);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据异常", e);
            return new ResponseEnvelope(false, "数据异常!" );
        }
        return new ResponseEnvelope(true, "", mediationAuthorizeVo);
    }



    /*
        中介用户提交审核认证
*/
    @RequestMapping(value = "/check")
    @ResponseBody
    public Object checkMediationAuthorize(@RequestBody MediationAuthorizeAdd mediationAuthorizeAdd, HttpServletRequest request, HttpServletResponse response) {

        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:"+gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if(null == loginUser.getUserType() || loginUser.getUserType().intValue()!= UserRoleContants.MEDIATION){
            log.warn("该用户角色类型:"+loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }


        //校验传入信息
        ValidateResult result = AnnotationValidator.validate(mediationAuthorizeAdd);
        if (!result.isValid()) {
            log.warn("参数校验未通过:"+gson.toJson(result));
            return new ResponseEnvelope(false, result.getFieldName() + "------------" + result.getMessage());
        }
        log.info("传参信息校验通过:"+gson.toJson(mediationAuthorizeAdd));

        //验证该用户是否已提交审核信息
        UserMediationAuthorize userMediationAuthorize;
        try {
            userMediationAuthorize = mediationAuthorizeService.getMediationAuthorizeByUserId(loginUser.getId());
            if(userMediationAuthorize!=null){
                log.warn("该用户已经提交审核申请,认证信息ID:"+userMediationAuthorize.getId());
                return new ResponseEnvelope(false,"该用户已经提交审核申请,无法重复提交");
            }
        }catch (Exception e){
            log.error("验证用户审核信息异常:"+e);
            return new ResponseEnvelope(false, "验证用户审核信息异常:");
        }


        //验证证件照片是否存在
        ResPic resPic;
        try {
            resPic = resPicService.get(mediationAuthorizeAdd.getCardPicId());
            if(null==resPic){
                log.warn("证件照片不存在,无法提交--resPicId:"+mediationAuthorizeAdd.getCardPicId());
                return new ResponseEnvelope(false,"证件照片不存在,无法提交--resPicId:"+mediationAuthorizeAdd.getCardPicId());
            }
        }catch (Exception e){
            log.error("验证证件照片信息异常:"+e);
            return new ResponseEnvelope(false, "验证证件照片信息异常:");
        }



        //保存审核数据
        int authorizeId;
        try {
            authorizeId =  mediationAuthorizeService.checkMediationAuthorize(mediationAuthorizeAdd,loginUser.getId(),loginUser.getName());
            if(0 == authorizeId){
                return new ResponseEnvelope(false, "保存审核失败,审核ID"+authorizeId);
            }
        }catch (Exception e){
            log.error("保存审核数据异常:"+e);
            return new ResponseEnvelope(false, "保存审核数据异常:");
        }

        return new ResponseEnvelope(true,"提交审核成功",authorizeId);
    }


    /*
    中介用户重新提交审核认证
*/
    @RequestMapping(value = "/recheck",method = RequestMethod.POST)
    @ResponseBody
    public Object reCheckMediationAuthorize(@RequestBody MediationReAuthorizeAdd mediationReAuthorizeAdd, HttpServletRequest request, HttpServletResponse response) {

        //获取当前登录用户
       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:"+gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if(null == loginUser.getUserType() || loginUser.getUserType().intValue()!=UserRoleContants.MEDIATION){
            log.warn("该用户角色类型:"+loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        //校验传入信息
        ValidateResult result = AnnotationValidator.validate(mediationReAuthorizeAdd);
        if (!result.isValid()) {
            log.warn("参数校验未通过:"+gson.toJson(result));
            return new ResponseEnvelope(false, result.getFieldName() + "------------" + result.getMessage());
        }
        log.info("传参信息校验通过:"+gson.toJson(mediationReAuthorizeAdd));

        //验证该审核信息是否存在
        UserMediationAuthorize userMediationAuthorize ;
        try {
            userMediationAuthorize = mediationAuthorizeService.getMediationAuthorizeById(mediationReAuthorizeAdd.getAuthorizeId());
            if( null == userMediationAuthorize){
                log.warn("该认证信息不存在,认证信息ID:"+mediationReAuthorizeAdd.getAuthorizeId());
                return new ResponseEnvelope(false,"该认证信息不存在,无法重新审核");
            }
        }catch (Exception e){
            log.error("查询认证信息数据异常:"+e);
            return new ResponseEnvelope(false, "查询认证信息数据异常:");
        }


        //验证证件照片是否存在
        ResPic resPic;
        try {
            resPic = resPicService.get(mediationReAuthorizeAdd.getCardPicId());
            if(null==resPic){
                log.warn("证件照片不存在,无法提交--resPicId:"+mediationReAuthorizeAdd.getCardPicId());
                return new ResponseEnvelope(false,"证件照片不存在,无法提交--resPicId:"+mediationReAuthorizeAdd.getCardPicId());
            }
        }catch (Exception e){
            log.error("验证证件照片信息异常:"+e);
            return new ResponseEnvelope(false, "验证证件照片信息异常");
        }


        //重新审核保存审核数据
        int authorizeId;
        try {
            authorizeId =  mediationAuthorizeService.reCheckMediationAuthorize(mediationReAuthorizeAdd,loginUser.getId(),loginUser.getName());
            if(0 == authorizeId){
                return new ResponseEnvelope(false, "保存审核失败,审核ID"+authorizeId);
            }
        }catch (Exception e){
            log.error("重新提交审核保存审核数据异常:"+e);
            return new ResponseEnvelope(false, "重新提交审核保存审核数据异常:");
        }

        return new ResponseEnvelope(true,"重新提交审核成功",authorizeId);
    }


}
