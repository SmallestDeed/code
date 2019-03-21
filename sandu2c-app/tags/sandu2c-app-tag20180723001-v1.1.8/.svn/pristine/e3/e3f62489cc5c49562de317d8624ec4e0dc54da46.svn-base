package com.sandu.web.home.controller;

import com.sandu.common.LoginContext;
import com.sandu.common.file.FileVO;
import com.sandu.common.file.uploader.Uploader;
import com.sandu.common.file.uploader.factory.UploaderFactory;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.FileUploadUtils;
import com.sandu.common.util.FtpUploadUtils;
import com.sandu.common.util.ThumbnailConvert;
import com.sandu.common.util.Utils;
import com.sandu.home.model.BaseHouseApply;
import com.sandu.home.service.BaseHouseApplyService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/v1/tocmobile/home/basehouseapply")
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
    private SysUserService sysUserService;
    @Autowired
    private UserSessionService userSessionService;
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
            @RequestPart("file") MultipartFile file, BaseHouseApply baseHouseApply) {
        if (baseHouseApply == null) {
            return new ResponseEnvelope(false, "参数为空");
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
//            return new ResponseEnvelope(false, "请先登录");
            loginUser = new LoginUser();
            loginUser.setId(3808);
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
        LocalDateTime now = LocalDateTime.now();
        String absolutePath = FilePathUtil.absolutePath(storagePath, platform, module, type, now, file.getOriginalFilename());
        Uploader uploader = UploaderFactory.createUploader(type, file, absolutePath);
        uploader.upload();
        FileVO fileVO = uploader.getFileVO();
        String returnPath = FilePathUtil.formatPath(FilePathUtil.serverPath()) + FilePathUtil.removeStartSlash(FilePathUtil.relativePath(storagePath, absolutePath));
        fileVO.setUrl(returnPath);
        //保存到数据库 并设置返回id
        Integer picId = resHousePicService.addNew(fileVO, type, absolutePath, loginUser, storagePath);

        baseHouseApply.setPicId(picId);
        SysUser sysUser = sysUserService.get(loginUser.getId());
        logger.info(CLASS_LOG_PREFIX + "保存户型的系统字段：{}", baseHouseApply.toString(),
                null == sysUser ? null : sysUser.toString());
        baseHouseApply = baseHouseApplyService.sysSave(baseHouseApply, sysUser);

        logger.info(CLASS_LOG_PREFIX + "在户型申请表保存户型数据：{}", baseHouseApply.toString());
        Integer applyId = baseHouseApplyService.add(baseHouseApply);
        logger.info(CLASS_LOG_PREFIX + "在户型申请表保存户型数据完成：户型ID：{}", applyId);
        ResHousePic resHousePic = resHousePicService.get(picId);
        if (resHousePic != null) {
            ResHousePic newResHousePic = new ResHousePic();
            newResHousePic.setId(resHousePic.getId());
            newResHousePic.setBusinessId(applyId);
            logger.info(CLASS_LOG_PREFIX + "更新户型图片数据");
            resHousePicService.update(newResHousePic);
        }
        return new ResponseEnvelope(true, "上传户型成功");

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
//                                   BaseHouseApply baseHouseApply, HttpServletRequest request) throws IOException {
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
//            logger.info(CLASS_LOG_PREFIX + "根据用户ID查询用户信息:{}", null == sysUser ? null : sysUser.toString());
//            sysUser = sysUserService.get(loginUser.getId());
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
