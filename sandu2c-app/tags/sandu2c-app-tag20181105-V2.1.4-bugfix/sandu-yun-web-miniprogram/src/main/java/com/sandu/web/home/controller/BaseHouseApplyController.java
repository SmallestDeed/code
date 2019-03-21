package com.sandu.web.home.controller;

import com.sandu.common.LoginContext;
import com.sandu.common.file.FileVO;
import com.sandu.common.file.uploader.Uploader;
import com.sandu.common.file.uploader.factory.UploaderFactory;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.*;
import com.sandu.home.model.BaseHouseApply;
import com.sandu.home.service.BaseHouseApplyService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.SysUserMessage;
import com.sandu.user.service.SysUserMessageService;
import com.sandu.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v1/miniprogram/home/basehouseapply")
public class BaseHouseApplyController {
    private final static String CLASS_LOG_PREFIX = "[上传户型服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseHouseApplyController.class);
    @Autowired
    private ResHousePicService resHousePicService;
    @Autowired
    private BaseHouseApplyService baseHouseApplyService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private SysUserService sysUserService;
    @Value("${file.storage.path}")
    private String storagePath;

    /**
     * 户型上传接口
     *
     * @param file
     * @param baseHouseApply
     * @return
     * @author huangsongbo
     */
    @RequestMapping("/uploadhouse")
    @ResponseBody
    public ResponseEnvelope imgUpload(
            @RequestPart(value = "file", required = false) MultipartFile file, @Valid BaseHouseApply baseHouseApply, BindingResult bindingResult) {

        /*返回校验失败信息*/
        if (bindingResult.hasErrors()) {
            return new ResponseEnvelope(false, bindingResult.getFieldError().getDefaultMessage());
        }


        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");

        }
        String platform = baseHouseApply.getPlatform();
        if (platform == null) {
            platform = "mobile2c";
        }
        String module = baseHouseApply.getModule();
        if (module == null) {
            module = "house";
        }
        String type = baseHouseApply.getType();
        if (type == null) {
            type = "image";
        }
        Integer picId = 0;
        LocalDateTime now = LocalDateTime.now();
        if (file != null) {
            String absolutePath = FilePathUtil.absolutePath(storagePath, platform, module, type, now, file.getOriginalFilename());
            Uploader uploader = UploaderFactory.createUploader(type, file, absolutePath);
            uploader.upload();
            FileVO fileVO = uploader.getFileVO();
            String relativePath = FilePathUtil.relativePath(storagePath, absolutePath);
            String removeStartSlash = FilePathUtil.removeStartSlash(relativePath);
            String returnPath = FilePathUtil.formatPath(FilePathUtil.serverPath()) + removeStartSlash;
            fileVO.setUrl(returnPath);
            //保存到数据库 并设置返回id
            picId = resHousePicService.addNew(fileVO, type, absolutePath, loginUser, storagePath);

            baseHouseApply.setPicId(picId);
        }

        SysUser sysUser = sysUserService.get(loginUser.getId());
        logger.info(CLASS_LOG_PREFIX + "保存户型的系统字段：{}", baseHouseApply.toString(),
                null == sysUser ? null : sysUser.toString());
        baseHouseApply = baseHouseApplyService.sysSave(baseHouseApply, sysUser);

        logger.info(CLASS_LOG_PREFIX + "在户型申请表保存户型数据：{}", baseHouseApply.toString());
        Integer applyId = baseHouseApplyService.add(baseHouseApply);
        logger.info(CLASS_LOG_PREFIX + "在户型申请表保存户型数据完成：户型ID：{}", applyId);
        if (picId != 0) {
            ResHousePic resHousePic = resHousePicService.get(picId);
            if (resHousePic != null) {
                ResHousePic newResHousePic = new ResHousePic();
                newResHousePic.setId(resHousePic.getId());
                newResHousePic.setBusinessId(applyId);
                logger.info(CLASS_LOG_PREFIX + "更新户型图片数据");
                resHousePicService.update(newResHousePic);
            }
        }

        // created by songjianming@sanduspace.cn on 2018/11/14 添加系统消息推送
        // http://wiki.sdkj.cn:8090/pages/viewpage.action?pageId=22544449
        this.pushSysMessage(sysUser);

        return new ResponseEnvelope(true, "上传户型成功");

    }

    /**
     * 添加系统消息推送
     *
     * @param sysUser
     */
    private void pushSysMessage(SysUser sysUser) {
        // created by songjianming@sanduspace.cn on 2018/11/14 添加系统消息推送
        // http://wiki.sdkj.cn:8090/pages/viewpage.action?pageId=22544449
        SysUserMessage userMessage = new SysUserMessage();
        userMessage.setTitle("恭喜你，户型提交成功！");
        userMessage.setContent("你的户型已经被录入，明日即可查看");
        userMessage.setMessageType(2);
        userMessage.setUserId(sysUser.getId());
        userMessage.setStatus(0);
        userMessage.setCreator(sysUser.getNickName());
        userMessage.setModifier(sysUser.getNickName());
        userMessage.setPlatformId(1);
        log.info("上传户型：系统消息推送：{}", userMessage);
        sysUserMessageService.add(userMessage);
    }

//    /**
//     * 户型上传接口
//     *
//     * @param file
//     * @param baseHouseApply
//     * @return
//     * @author huangsongbo
//     */
//    @RequestMapping("/uploadhouse")
//    @ResponseBody
//    public ResponseEnvelope create(@RequestParam(value = "file", required = false) MultipartFile file,
//                                   BaseHouseApply baseHouseApply, HttpServletRequest request) {
//        /*限制文件类型*/
//        List<SysDictionary> list = sysDictionaryService.findAllByTypeAndAtt1("baseHousePicType", "1");
//        if (list == null || list.size() == 0) {
//            logger.warn(CLASS_LOG_PREFIX + "管理原还未设置可上传的图片格式,请联系管理员");
//            return new ResponseEnvelope(false, "管理原还未设置可上传的图片格式,请联系管理员");
//        }
//        String originalFilename = file.getOriginalFilename();
//        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        boolean flag = false;
//        StringBuffer typeStr = new StringBuffer("");
//        for (SysDictionary sysDictionary : list) {
//            typeStr.append(sysDictionary.getName() + ",");
//            if (suffix.toLowerCase().endsWith(sysDictionary.getName().toLowerCase())) {
//                flag = true;
//            }
//        }
//        if (!flag) {
//            logger.warn(CLASS_LOG_PREFIX + "上传文件类型不支持");
//            return new ResponseEnvelope(flag, "文件类型不支持,支持类型:" + (typeStr.length() == 0 ? "" : typeStr.subSequence(0, typeStr.length() - 1)));
//        }
//        /*限制文件类型->end*/
//		/*保存图片文件*/
//        SysUser sysUser = new SysUser();
//    	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
//        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
//        if(loginUser == null){
//            sysUser.setId(0);
//            sysUser.setNickName("未知用户");
//        }else{
//            sysUser = sysUserService.get(loginUser.getId());
//            logger.info(CLASS_LOG_PREFIX + "根据用户ID查询用户信息:{}", null == sysUser ? null : sysUser.toString());
//        }
//
//        Integer picId = this.saveMultipartFile(file, "home.baseHouseApply.pic.upload.path", sysUser);
//		/* 保存图片文件->end */
//        baseHouseApply.setPicId(picId);
//        logger.info(CLASS_LOG_PREFIX + "保存户型的系统字段：{}", baseHouseApply.toString(),
//                null == sysUser ? null : sysUser.toString());
//        baseHouseApply = baseHouseApplyService.sysSave(baseHouseApply, sysUser);
//        logger.info(CLASS_LOG_PREFIX + "在户型申请表保存户型数据：{}", baseHouseApply.toString());
//        Integer applyId = baseHouseApplyService.add(baseHouseApply);
//        logger.info(CLASS_LOG_PREFIX + "在户型申请表保存户型数据完成：户型ID：{}", applyId);
//        ResHousePic resHousePic = resHousePicService.get(picId);
//        if (resHousePic != null) {
//            ResHousePic newResHousePic = new ResHousePic();
//            newResHousePic.setId(resHousePic.getId());
//            newResHousePic.setBusinessId(applyId);
//            logger.info(CLASS_LOG_PREFIX + "更新户型图片数据");
//            resHousePicService.update(newResHousePic);
//        }
//        return new ResponseEnvelope(true, "上传户型成功");
//    }


    public Integer saveMultipartFile(MultipartFile file, String fileKey, SysUser sysUser) throws IOException {
        String UPLOAD_PATH = fileKey;
        ResHousePic resHousePic = new ResHousePic();
        if (file != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(FileUploadUtils.UPLOADPATHTKEY, UPLOAD_PATH);
            map.put(FileUploadUtils.FILE, file);
            Long fileSize = file.getSize();
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            boolean flag = FileUploadUtils.saveFile2(map);
            if (flag) {
                String realPath = (String) map.get(FileUploadUtils.SERVER_FILE_PATH);
                String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
                String ftpFilePath = (String) map.get("filePath");
                String finalFlieName = (String) map.get("finalFlieName");
                File sFile = new File(realPath);
                try {
                    /* 应对CMYK模式图片上传报错的情况 */
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(sFile);
                    } catch (Exception e) {
                        logger.error(CLASS_LOG_PREFIX + "saveMultipartFile  exception --" + e);
                        try {
                            ThumbnailConvert tc = new ThumbnailConvert();
                            tc.setCMYK_COMMAND(sFile.getPath());
                            bufferedImage = null;
                            Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
                            MediaTracker mediaTracker = new MediaTracker(new Container());
                            mediaTracker.addImage(bufferedImage, 0);
                            mediaTracker.waitForID(0);
                            bufferedImage = ThumbnailConvert.toBufferedImage(image);
                        } catch (Exception e1) {
                            logger.error(CLASS_LOG_PREFIX + "saveMultipartFile  exception --" + e);
                            e1.printStackTrace();
                        }
                    }
                    /* 应对CMYK模式图片上传报错的情况END */
                    if (bufferedImage == null) {
                        return 0;
                    }
                    int width = bufferedImage.getWidth(); // 图片宽度
                    int height = bufferedImage.getHeight();// 图片高度
                    String format = suffix.replace(".", "");
                    resHousePic.setPicWeight(new Integer(width).toString());
                    resHousePic.setPicHigh(new Integer(height).toString());
                    resHousePic.setPicFormat(format);// 图片格式
                } catch (Exception e) {
                    logger.error(CLASS_LOG_PREFIX + "saveMultipartFile  exception --" + e);
                    e.printStackTrace();
                }
                /*ftp处理*/
                if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
                    FtpUploadUtils.uploadFile(finalFlieName, realPath, ftpFilePath);
                }
                if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
                    FtpUploadUtils.uploadFile(finalFlieName, realPath, ftpFilePath);
                }
                /*ftp处理->end*/
                resHousePic.setPicPath(dbFilePath);
                resHousePic.setFileKey(resHousePic.getFileKey());
                resHousePic.setBusinessId(resHousePic.getBusinessId());
                if (StringUtils.isEmpty(resHousePic.getPicName())) {
                    if (filename.lastIndexOf(".") != -1) {
                        resHousePic.setPicName(filename.substring(0, filename.lastIndexOf(".")));// 业务名称
                    } else {
                        resHousePic.setPicName(filename);
                    }
                }
                resHousePic.setPicFileName(filename);
                resHousePic.setPicSuffix(suffix);
                resHousePic.setPicSize(new Long(fileSize).intValue());
                sysSave(resHousePic, sysUser);
                resHousePic.setPicCode(resHousePic.getSysCode());
                resHousePic.setPicType("户型申请图");
                resHousePic.setPicLevel("0");
                resHousePic.setFileKey(fileKey.replace(".upload.path", ""));
                logger.info(CLASS_LOG_PREFIX + "新增户型数据：{}", null == resHousePic ? null : resHousePic.toString());
                int i = resHousePicService.add(resHousePic);
                logger.info(CLASS_LOG_PREFIX + "新增户型数据完成：户型图片ID：{}", i);
                return i;
            }
        }
        return 0;
    }

    /**
     * 自动存储系统字段
     */
    private void sysSave(ResHousePic model, SysUser loginUser) {
        if (model != null) {
            if (model.getId() == null) {
                model.setGmtCreate(new Date());
                model.setCreator(loginUser.getNickName());
                model.setIsDeleted(0);
                if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                    model.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }

            model.setGmtModified(new Date());
            model.setModifier(loginUser.getNickName());
        }
    }

}
