package com.sandu.web.user;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sandu.api.area.model.BaseMobileArea;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.area.service.BaseMobileAreaService;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.dictionary.service.biz.DictionaryBizService;
import com.sandu.api.pic.model.ResPic;
import com.sandu.api.shop.service.biz.ShopBizService;
import com.sandu.api.user.constant.UserTypeConstant;
import com.sandu.commons.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.output.ImgUploadVO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.user.input.*;
import com.sandu.api.user.input.InternalUserUpdate;
import com.sandu.api.user.input.SysEiuUserUpdate;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.output.InternalUserDetailVO;
import com.sandu.api.user.output.InternalUserListVO;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.SysUserManageService;
import com.sandu.common.LoginContext;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.util.StringUtils;
import com.sandu.web.BaseController;
import com.sandu.web.category.ProCategoryController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author chenqiang
 * @Description 用户 RPC 控制层
 * @Date 2018/6/1 0001 10:16
 * @Modified By
 */
@Api(tags = "SysEiuUser", description = "内部用户")
@RestController
@RequestMapping("/v1/sys/eiu/user")
public class SysEiuUserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ProCategoryController.class);

    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${system.sysUser.logo.upload.path}")
    private String uploadPath;

    @Resource
    private SysUserManageService sysUserManageService;

    @Autowired
    private ResPicService resPicService;

    @Autowired
    private DictionaryBizService dictionaryBizService;

    @Autowired
    private BaseMobileAreaService mobileAreaService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private ShopBizService shopBizService;

    @ApiOperation(value = "用户列表", response = InternalUserListVO.class)
    @PostMapping("/list")
    public ResponseEnvelope getIUSysUserList(@Valid @RequestBody SysEiuUserListQuery query, BindingResult validResult) {
        try {

            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.数据转换
            InternalUserQuery internalUserQuery =  this.getInternalUserQuery(query);

            //3.获取list数据
            int count = sysUserManageService.getInternalUserCount(internalUserQuery);
            List<InternalUserListVO> iuUserList = null;
            if(count > 0){
                iuUserList = sysUserManageService.getInternalUserList(internalUserQuery);
            }

            return new ResponseEnvelope<>(true, count , iuUserList);

        } catch (Exception e) {

            logger.error("getSysUserList 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "删除用户", response = ResponseEnvelope.class)
    @DeleteMapping
    public ResponseEnvelope deleteSave(@RequestParam(value = "id") Long userId) {
        try {

            //1.数据校验
            if (null == userId) {
                return new ResponseEnvelope<>(false, "用户id不能为空!");
            }
            //获取当前登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

            //2.删除用户店铺,博文，方案，案例
            int result = shopBizService.delUsersShop(userId,loginUser.getLoginName());

            if (result!=0){
                //3.删除用户数据
                int count = sysUserManageService.deleteInternalUser(userId);

                if(count > 0)
                    return new ResponseEnvelope<>(true, "删除成功!");
                else
                    return new ResponseEnvelope<>(false, "删除失败!");
            }else{
                return new ResponseEnvelope<>(false, "删除用户店铺、博文等失败!");
            }

        } catch (Exception e) {

            logger.error("deleteSave 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "修改用户", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope updateSave(@Valid @RequestBody SysEiuUserUpdate updateEiu, BindingResult validResult) {
        try {

            //1.参数校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            /** 2.数据校验 */
            //2.1密码与确认密码校验
            if(StringUtils.isNotEmpty(updateEiu.getPassword())&&StringUtils.isNotEmpty(updateEiu.getPasswordNew())){
                if(!updateEiu.getPassword().equals(updateEiu.getPasswordNew())){
                    return new ResponseEnvelope<>(false, "密码与确认密码不一样!");
                }
            }
            //2.2手机号校验
//            if(StringUtils.isNotEmpty(updateEiu.getMobile())){
//                InternalUserDetailVO internalUserDetailVO =  sysUserManageService.getInternalUserDetail(updateEiu.getId());
//                if(!updateEiu.getMobile().equals(internalUserDetailVO.getMobile())){
//                    boolean bool = sysUserManageService.checkPhone(updateEiu.getMobile());
//                    if(!bool)
//                        return new ResponseEnvelope<>(false, "存在重复的手机号");
//                }
//            }
            //2.3用户类型校验
            if(null != updateEiu.getId() && null != updateEiu.getUserType()){
                boolean bool = dictionaryBizService.checkUserType(updateEiu.getId().toString(),updateEiu.getUserType());
                if(!bool)
                    return new ResponseEnvelope<>(false, "用户类型有误");
            }
            //手机号格式校验
            if(StringUtils.isNotEmpty(updateEiu.getMobile())){
                boolean bool = StringUtil.checkPhoneRegex(updateEiu.getMobile(),"2");
                if(!bool)
                    return new ResponseEnvelope<>(false, "手机号格式错误");
            }


            //3.数据转换
            InternalUserUpdate internalUserUpdate = this.getInternalUserUpdate(updateEiu);

            //4.获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            /*LoginUser loginUser=new LoginUser();
            loginUser.setLoginName("dd");
            loginUser.setUserType(2);*/

            //5.修改用户
            int count = sysUserManageService.updateInternalUser(internalUserUpdate,loginUser);
            if(count <= 0)
                return new ResponseEnvelope<>(false, "修改失败!");

            //6.修改用户logo
            if(null == updateEiu.getHeadPicId()){
                DealUserHeadPic dealUserHeadPic = new DealUserHeadPic();
                dealUserHeadPic.setUserId(loginUser.getId());
                dealUserHeadPic.setModifier(loginUser.getLoginName());
                dealUserHeadPic.setPicId(updateEiu.getHeadPicId());
                count = sysUserManageService.dealUserHeadPic(dealUserHeadPic);
                if(count <= 0)
                    return new ResponseEnvelope<>(false, "删除用户log失败!");
            }else{

                //回填businessId
                ResPic resPic = new ResPic();
                resPic.setId(new Long(updateEiu.getHeadPicId()));
                resPic.setBusinessId(updateEiu.getId().intValue());
                resPicService.updateByPrimaryKeySelective(resPic);
            }

            return new ResponseEnvelope<>(true, "修改成功!");

        } catch (Exception e) {

            logger.error("updateSave 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "获取用户详情", response = InternalUserDetailVO.class)
    @GetMapping
    public ResponseEnvelope getUserDeteils(@RequestParam(value = "id") Long userId) {
        try {

            //1.数据校验
            if (null == userId) {
                return new ResponseEnvelope<>(false, "用户账号不能为空!");
            }

            //2.获取用户数据
            InternalUserDetailVO internalUserDetailVO =  sysUserManageService.getInternalUserDetail(userId);

            /**modifier wanghl start**/
            logger.info("内部用户查询结果internalUserDetailVO:{}",internalUserDetailVO);
            //处理用户类型的显示名称
            if (null!=internalUserDetailVO.getUserType()){
                List<SysDictionary> typeList = dictionaryService.listByType("userType");
                for (SysDictionary dictionary:typeList) {
                    if (internalUserDetailVO.getUserType().equals(dictionary.getValue())){
                        internalUserDetailVO.setUserTypeStr(dictionary.getName());
                    }
                }
            }
            //处理地址
            this.dealArea(internalUserDetailVO);
            /**modifier wanghl end**/

            return new ResponseEnvelope<>(true, "详情获取成功!",internalUserDetailVO);

        } catch (Exception e) {

            logger.error("getUserDeteils 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }



    @ApiOperation(value = "上传用户头像", response = ImgUploadVO.class)
    @PostMapping("/img/upload")
    public ResponseEnvelope uploadImg(@RequestParam("file") MultipartFile file) {
        ImgUploadVO imgUploadVO = new ImgUploadVO();

        try {
            //流验证
            if (file.isEmpty() || org.apache.commons.lang3.StringUtils.isBlank(file.getOriginalFilename())) {
                return new ResponseEnvelope(false, "流为空", imgUploadVO);
            }

            //获取登录用户
            com.sandu.commons.LoginUser loginUser = LoginContext.getLoginUser(com.sandu.commons.LoginUser.class);

            //路径补全
            uploadPath = StringUtil.replaceDate(uploadPath, null);

            //创建流与验证
            File dir = new File(rootPath+uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            /** 获取上传图片信息 */
            //上传文件名
            String oldName = file.getOriginalFilename();
            //上传文件名
            String filename = StringUtils.substringBeforeLast(oldName, ".");
            //后缀
            String suffix = StringUtils.substringAfterLast(oldName, ".");
            //大小
            Long size = file.getSize();

            /** 校验 */
            if (!("gif，jpg，jpeg，png".contains(suffix)||"GIF，JPG，JPEG，PNG".contains(suffix))) {
                return new ResponseEnvelope(false, "仅支持图片格式gif、jpg、jpeg、png");
            }
            if (size > 500 * 1024) {
                return new ResponseEnvelope(false, "仅支持上传图片大小500k");
            }

            //路径补全
            String filePath = "";
            StringBuffer sb = new StringBuffer();
            sb.append(uploadPath);
            sb.append("/");
            sb.append(Utils.generateRandomDigitString(6));
            sb.append("_");
            sb.append(System.currentTimeMillis());
            sb.append(".");
            sb.append(suffix);
            filePath = sb.toString();

            //上传图片
            File picFile = new File(rootPath+filePath);
            file.transferTo(picFile);

            //获取上传文件信息
            Map<String, String> map = FileUtils.getMap(picFile,"system.sysUser.logo.upload.path",true);

            //2.新增resPic图片信息表
            Integer id = resPicService.saveUploadImgPic(map,null,loginUser,"用户logo图片");

            if(id > 0) {
                imgUploadVO.setPicPath(filePath);
                imgUploadVO.setId(id);
                return new ResponseEnvelope(true, "新增图片成功", imgUploadVO);
            }else{
                return new ResponseEnvelope(true, "新增图片失败", imgUploadVO);
            }
        } catch (Exception e) {

            logger.error("uploadImg 方法 系统异常", e);
            return new ResponseEnvelope(false, "系统异常", imgUploadVO);
        }
    }


    @ApiOperation(value = "删除用户头像", response = ResponseEnvelope.class)
    @DeleteMapping("/logo")
    public ResponseEnvelope deleteUserLogo(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "picIds") String picIds) {
        try {
            //1.数据校验
            if(org.apache.commons.lang3.StringUtils.isEmpty(picIds)){
                return new ResponseEnvelope<>(false, "图片ids不能为空");
            }

            //2.获取登录用户
            com.sandu.commons.LoginUser loginUser = LoginContext.getLoginUser(com.sandu.commons.LoginUser.class);

            //3.删除图片
            boolean bool = resPicService.deletePic(picIds,loginUser);

            //4.判断返回
            if(bool)
                return new ResponseEnvelope<>(true, "删除图片成功");
            else
                return new ResponseEnvelope<>(false, "删除图片失败");

        } catch (Exception e) {

            logger.error("deleteUserLogo 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "校验手机号是否唯一", response = ResponseEnvelope.class)
    @PostMapping("/checkphone")
    public ResponseEnvelope checkPhone(HttpServletRequest request, HttpServletResponse response,
                                       @Valid @ModelAttribute("checkPhone") SysEiuUserCheckPhone checkPhone, BindingResult validResult) {

        try {

            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.判断是否为编辑
            if("edit".equals(checkPhone.getOType())){
                if(null == checkPhone.getId())
                    return new ResponseEnvelope<>(false, "用户id不能为空");
                InternalUserDetailVO internalUserDetailVO =  sysUserManageService.getInternalUserDetail(checkPhone.getId());
                if(checkPhone.getMobile().equals(internalUserDetailVO.getMobile()))
                    return new ResponseEnvelope<>(true, "手机号未被修改");
            }

            //4.校验B端用户是否唯一
            boolean bool = true;
            if(StringUtils.isNotBlank(checkPhone.getUserType()) && Integer.parseInt(checkPhone.getUserType()) == UserTypeConstant.USER_TYPE_DEALERS_D){
                bool = sysUserManageService.checkPhoneDuLI(checkPhone.getMobile());
            }else{
                bool = sysUserManageService.checkPhone(checkPhone.getMobile());
            }


            //5.判断返回
            if(bool)
                return new ResponseEnvelope<>(true, "手机号唯一");
            else
                return new ResponseEnvelope<>(false, "存在重复的手机号");

        } catch (Exception e) {

            logger.error("checkPhone 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    private InternalUserQuery getInternalUserQuery(SysEiuUserListQuery query){
        InternalUserQuery internalUserQuery = new InternalUserQuery();
        internalUserQuery.setCompanyId(new Long(query.getCompanyId()));
        internalUserQuery.setAccount(query.getUserName());
        internalUserQuery.setMobile(query.getPhone());
        internalUserQuery.setUserName(query.getNickName());
        internalUserQuery.setPage(query.getStart());
        internalUserQuery.setLimit(query.getLimit());
        return internalUserQuery;
    }

    private InternalUserUpdate getInternalUserUpdate(SysEiuUserUpdate userUpdate){
        InternalUserUpdate internalUserUpdate = new InternalUserUpdate();
        internalUserUpdate.setUserId(userUpdate.getId());
        internalUserUpdate.setNickName(userUpdate.getNickName());
        internalUserUpdate.setMobile(userUpdate.getMobile());
        internalUserUpdate.setEMail(userUpdate.getEMail());
        internalUserUpdate.setPassword(userUpdate.getPassword());
        internalUserUpdate.setHeadPicId(userUpdate.getHeadPicId());
        internalUserUpdate.setUserType(userUpdate.getUserType());
        /**add by whl**/
        internalUserUpdate.setFirstLoginTime(userUpdate.getFirstLoginTime());
        internalUserUpdate.setProvinceCode(userUpdate.getProvinceCode());
        internalUserUpdate.setCityCode(userUpdate.getCityCode());
        internalUserUpdate.setAreaCode(userUpdate.getAreaCode());
        internalUserUpdate.setStreetCode(userUpdate.getStreetCode());
        internalUserUpdate.setAddress(userUpdate.getAddress());
        return internalUserUpdate;
    }

    /**
     * 查看用户时，当用户信息中保存有地址信息，则展示用户的地址信息，如果没保存地址信息，但是保存有电话号码
     * 则取用户电话号码所在地址进行展示
     * @param userDetailVO 内部用户详情
     */
    private void dealArea(InternalUserDetailVO userDetailVO){
        //省份编码为空，电话号码不为空
        if (org.apache.commons.lang3.StringUtils.isNotBlank(userDetailVO.getProvinceCode())){//保存有地址编码，则取baseArea表查询
            //3.获取当前企业 行政区域 名称
            List<String> codeList = new ArrayList<>();
            codeList.add(userDetailVO.getProvinceCode());
            codeList.add(userDetailVO.getCityCode());
            codeList.add(userDetailVO.getAreaCode());
            codeList.add(userDetailVO.getStreetCode());
            String areaNames = baseAreaService.getAreaNames(codeList);
            logger.info("通过地区编码查询地区信息:{}",areaNames);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(areaNames)){
                String[] codeNames = areaNames.split(",");
                for(int i = 0 ; i < codeNames.length; i++){
                    switch (i){
                        case 0:
                            userDetailVO.setProvinceCodeName(codeNames[i]);
                            break;
                        case 1:
                            userDetailVO.setCityCodeName(codeNames[i]);
                            break;
                        case 2:
                            userDetailVO.setAreaCodeName(codeNames[i]);
                            break;
                        case 3:
                            userDetailVO.setStreetCodeName(codeNames[i]);
                            break;
                    }
                }
            }
        }else{//如果用户没有保存地址编码，则通过电话号码获取用户所在地
            if (org.apache.commons.lang3.StringUtils.isNotBlank(userDetailVO.getMobile())){
                String mobilePrefix = userDetailVO.getMobile().substring(0, 7);
                logger.info("查询手机号归属地：手机号前七位：{}"+mobilePrefix);
                BaseMobileArea mobileArea = mobileAreaService.getAreaByMobile(mobilePrefix);
                logger.info("查询手机号归属地：结果{}"+mobileArea);
                if (null!=mobileArea){
                    userDetailVO.setProvinceCodeName(mobileArea.getProvinceName());
                    userDetailVO.setProvinceCode(mobileArea.getProvinceCode());
                    userDetailVO.setCityCodeName(mobileArea.getCityName());
                    userDetailVO.setCityCode(mobileArea.getCityCode());
                }
            }
        }
    }


}
