package com.sandu.im.web.controller;

import com.sandu.im.common.ResponseEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/msgCenter/historyMsg")
@Slf4j
public class UploadPhotoController {

    @Value("${upload.base.path}")
    private String rootPath;

    @Value("${upload.default.path}")
    private String defaultPath;

    @PostMapping(value = "/upload")
    public Object upload(@RequestParam(value = "file",required=true) MultipartFile file){
        //流验证
        if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
            return ResponseEnvelope.bizError("400","请选择你要上传的图片");
        }
        try {

            int width = 0;
            int height = 0;
            BufferedImage image = ImageIO.read(file.getInputStream());
            width = image.getWidth();
            height = image.getHeight();

            Object picPath = this.executeSaveMiniProHeadPic(file);
            Map<String,Object> returnDate = new HashMap<>();
            returnDate.put("picPath",picPath);
            returnDate.put("width",width);
            returnDate.put("height",height);
            return ResponseEnvelope.bizSuccess(returnDate);
        } catch (Exception e) {
            log.error("系统错误",e);
            return ResponseEnvelope.sysError();
        }
    }

    private String executeSaveMiniProHeadPic(MultipartFile file) throws IOException {

        StringBuffer sb = new StringBuffer();
        sb.append(rootPath);
        sb.append(defaultPath);
//         sb.append("C:\\Users\\Sandu\\Desktop\\新建文件夹");
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
        log.info("文件上传的路劲"+filePath);
        //路径
        StringBuffer sBuffer = new StringBuffer(defaultPath);
        sBuffer.append("/");
        sBuffer.append(millis);
        sBuffer.append(".");
        sBuffer.append(suffix);
        String savePath = sBuffer.toString();
        log.error("数据库文件存储路径"+savePath);

        return savePath;
    }
}
