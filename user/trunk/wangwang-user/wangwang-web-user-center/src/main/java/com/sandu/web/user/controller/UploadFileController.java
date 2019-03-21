package com.sandu.web.user.controller;

import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.service.ResPicService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.ResponseEnvelope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/v1/user/center")
@Api("用户图片上传")
public class UploadFileController {

    private final static Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Value("${upload.base.path}")
    private String rootPath;

    @Value("${upload.default.path}")
    private String defaultPath;

    @Autowired
    private ResPicService resPicService;

    @ApiOperation(value = "用户头像上传",response = ResponseEnvelope.class)
    @PostMapping("/upload")
    public ResponseEnvelope uploadUserPic(@RequestParam(value = "file",required=false) MultipartFile file) {
        //流验证
        if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
            return new ResponseEnvelope(false, "请选择您要上传的文件!");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        ResPic resPic = null;

        //创建文件存储路径
        try {
            resPic = this.executeUploadFile(file);
        } catch (IOException e) {
            logger.error("上传用户图片异常", e);
        }
        int result = 0;
        try {
            if (resPic != null) {
                this.setResPicParameters(loginUser, resPic);
                logger.info("图片上传成功,回填数据库数据");
                result = resPicService.save(resPic);
                resPic.setId(result);
            }
            if (result > 0) {
                return new ResponseEnvelope(true, resPic, "上传用户头像成功!", null);
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            return new ResponseEnvelope(false, "系统异常");
        }
        return new ResponseEnvelope(false, "上传图片失败!");
    }

    @ApiOperation(value = "保存微信用户头像",response = ResponseEnvelope.class)
    @PostMapping(value = "/uplodMiniProHeadPic")
    public ResponseEnvelope uplodMiniProHeadPic(@RequestParam(value = "file",required=false) MultipartFile file){

        //流验证
        if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
            return new ResponseEnvelope(false, "请选择您要上传的文件!");
        }

        try {
            Object picPath = this.executeSaveMiniProHeadPic(file);
            return new ResponseEnvelope(true,picPath);
        } catch (Exception e) {
           logger.error("系统错误",e);
           return new ResponseEnvelope(false,"系统错误");
        }
    }

    private String executeSaveMiniProHeadPic(MultipartFile file) throws IOException{

        StringBuffer sb = new StringBuffer();
        sb.append(rootPath);
        sb.append(defaultPath);
        // sb.append("C:\\Users\\Sandu\\Desktop\\新建文件夹");
        File dir = new File(sb.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = file.getOriginalFilename();
        //上传文件名
        String filename = StringUtils.substringBeforeLast(name, ".");
        //后缀
        String suffix = StringUtils.substringAfterLast(name, ".");
        //大小
        Long size = file.getSize();
        long millis = System.currentTimeMillis();
        sb.append("/");
        sb.append(millis);
        sb.append(".");
        sb.append(suffix);

        //上传文件
        String filePath = sb.toString();
        File targetFile = new File(filePath);
        file.transferTo(targetFile);
        logger.info("文件上传的路劲"+filePath);
        //路径
        StringBuffer sBuffer = new StringBuffer(defaultPath);
        sBuffer.append("/");
        sBuffer.append(millis);
        sBuffer.append(".");
        sBuffer.append(suffix);
        String savePath = sBuffer.toString();
        logger.error("数据库文件存储路径"+savePath);

        return savePath;
    }

    private void setResPicParameters(LoginUser loginUser, ResPic resPic) {
        Date now = new Date();
        resPic.setCreator(loginUser.getLoginName());
        resPic.setIsDeleted(0);
        resPic.setGmtCreate(now);
        resPic.setGmtModified(now);
        resPic.setModifier(loginUser.getLoginName());
    }

    private ResPic executeUploadFile(MultipartFile file) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(rootPath);
        sb.append(defaultPath);
        File dir = new File(sb.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = file.getOriginalFilename();
        //上传文件名
        String filename = StringUtils.substringBeforeLast(name, ".");
        //后缀
        String suffix = StringUtils.substringAfterLast(name, ".");
        //大小
        Long size = file.getSize();
        long millis = System.currentTimeMillis();
        sb.append("/");
        sb.append(millis);
        sb.append(".");
        sb.append(suffix);

        //上传文件
        String filePath = sb.toString();
        File targetFile = new File(filePath);
        file.transferTo(targetFile);
        logger.info("文件上传的路劲"+filePath);
        //路径
        StringBuffer sBuffer = new StringBuffer(defaultPath);
        sBuffer.append("/");
        sBuffer.append(millis);
        sBuffer.append(".");
        sBuffer.append(suffix);
        String savePath = sBuffer.toString();
        logger.error("数据库文件存储路径"+savePath);
        ResPic pic = new ResPic();
        pic.setPicFileName(filename);
        pic.setPicSize(size.intValue());
        pic.setPicSuffix(suffix);
        pic.setPicPath(savePath);
        return pic;
    }
}
