package com.sandu.web.upload;

import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.constant.Punctuation.SLASH;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.file.FileVO;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import com.sandu.user.model.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "Storage", description = "存储")
@RequestMapping("/v1/miniprogram")
@Slf4j
public class StorageController extends BaseController {

    @Value("${file.storage.path}")
    private String basePath;

    @Value("${server.url}")
    private String baseUrl;
    
    @Resource
    private ResPicService resPicService;
    
    /**
     * 图片上传
     * @param file     文件
     * @param platform platform
     * @param module   module
     * @param type     type
     * @return 图片路径
     */
    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public ResponseEnvelope imgUpload( @RequestPart("file") MultipartFile file,@RequestParam String filekey) {
    	LocalDateTime currentDateTime = LocalDateTime.now();
        final StringBuilder relativePath = new StringBuilder("AA")
                .append(SLASH)
                .append("c_basedesign")
                .append(SLASH)
                .append(currentDateTime.getYear())
                .append(SLASH)
                .append(currentDateTime.getMonthValue())
                .append(SLASH)
                .append(currentDateTime.getDayOfMonth())
                .append(SLASH)
                .append(currentDateTime.getHour())
                .append(SLASH)
                .append(filekey.replace(DOT, SLASH));
        log.info("Relative Path: {}", relativePath);

        //生成文件名
        final DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        final String fileName = currentDateTime.format(ddf);
        final String suffix = Files.getFileExtension(file.getOriginalFilename());
        final String fullRelativePath = String.format("/%s/%s.%s", relativePath.toString(), fileName, suffix);
        log.info("Full relative Path: {}", fullRelativePath);

        final File dir = new File(this.basePath + SLASH + relativePath.toString());
        log.info("Dir: {}", dir.getPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileVO vo = new FileVO();
        vo.setUrl(this.baseUrl + fullRelativePath);
        //获取当前用户信息
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if(user == null) {
        	user = new LoginUser();
        	user.setLoginId("1");
        	user.setLoginName("sanduadmin");
        }
        final Date currentTime = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            log.error("Get upload image size error", e);
        }
        /* 保存到数据库 并设置返回id*/
        ResPic resPic = new ResPic();
        resPic.setPicSize(Long.valueOf(file.getSize()).intValue());
        resPic.setPicPath(fullRelativePath);
        resPic.setPicWeight(String.valueOf(image.getWidth()));
        resPic.setPicHigh(String.valueOf(image.getHeight()));
        resPic.setFileKey(filekey);
        resPic.setCreator(user.getLoginId());
        resPic.setGmtCreate(currentTime);
        resPic.setModifier(user.getLoginName());
        resPic.setGmtModified(currentTime);
        resPic.setIsDeleted(0);
        resPic.setPicName(fileName);
        resPic.setPicFileName(fileName);
        resPic.setPicSuffix(DOT + suffix);
        resPic.setPicFormat(suffix);
        resPic.setPicCode(fileName);
        resPic.setRemark("from-merchant-manage");
        log.info("ResPic: {}", resPic);
        try {
            file.transferTo(new File(this.basePath + fullRelativePath));
        } catch (IOException e) {
            log.error("move upload file error", e);
        }
        Integer id = resPicService.add(resPic);
        vo.setResId(Long.parseLong(id+""));
        return new ResponseEnvelope(true, vo);
    }
}
