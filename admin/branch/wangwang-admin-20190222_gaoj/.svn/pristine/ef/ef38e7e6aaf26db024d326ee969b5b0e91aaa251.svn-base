package com.sandu.web.storage.controller;

import com.google.common.io.Files;
import com.sandu.api.groupproducct.model.ResFile;
import com.sandu.api.groupproducct.service.ResFileService;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.output.FileVO;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
import com.sandu.common.ReturnData;
import com.sandu.constant.ResponseEnum;
import com.sandu.exception.AppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import static com.sandu.constant.Constants.FILE_KEY_MAPS;
import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.constant.Punctuation.SLASH;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 *
 * <p>sandu-wangwang
 *
 * @author bvvy (yocome@gmail.com) 2017/12/16 18:52
 */
@SuppressWarnings("unchecked")
@RestController
@Api(tags = "Storage", description = "存储")
@RequestMapping("/v1/storage")
@Slf4j
public class StorageController extends BaseController {

    @Value("${file.storage.path}")
    private String basePath;

    @Value("${upload.path.format}")
    private String baseFormat;

    @Value("${server.url}")
    private String baseUrl;

    @Resource
    private ResPicService resPicService;

    @Resource
    private ResFileService resFileService;

    /**
     * 图片上传
     *
     * @param file     文件
     * @param platform platform
     * @param module   module
     * @param type     type
     * @return 图片路径
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "platform",
                    value = "平台 ios windowPc web ipad macBookPc android",
                    paramType = "query",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "module",
                    value = "业务模块: 产品 product, 模型 model, 店铺 shop, 公司 company",
                    paramType = "query",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(name = "fileKey", paramType = "query", dataType = "string"),
            @ApiImplicitParam(
                    name = "type",
                    value = "文件类型: image 图片 file文件 model模型",
                    paramType = "query",
                    dataType = "string",
                    required = true)
    })
    public ReturnData<FileVO> imgUpload(
            @RequestPart("file") MultipartFile file,
            @RequestParam String module,
            String fileKey,String msgId) {

        // 获取文件key
        String fileKeyTmp = null;
        String secondeDir = "c_basedesign";
        if (StringUtils.isNotBlank(fileKey)) {
            fileKeyTmp = fileKey.trim();
            secondeDir = "c_" + fileKeyTmp.substring(0, fileKeyTmp.lastIndexOf(DOT)).replace(DOT, "");

        } else {
            fileKeyTmp = FILE_KEY_MAPS.get(module);
        }
        if (StringUtils.isBlank(fileKeyTmp)) {
            throw new AppException(ResponseEnum.PARAM_ERROR, "模块参数有误");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        final StringBuilder relativePath =
                new StringBuilder("AA")
                        .append(SLASH)
                        .append(secondeDir)
                        .append(SLASH)
                        .append(currentDateTime.getYear())
                        .append(SLASH)
                        .append(currentDateTime.getMonthValue())
                        .append(SLASH)
                        .append(currentDateTime.getDayOfMonth())
                        .append(SLASH)
                        .append(currentDateTime.getHour())
                        .append(SLASH)
                        .append(fileKeyTmp.replace(DOT, SLASH));
        log.debug("Relative Path: {}", relativePath);

        // 生成文件名
        final DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        final String fileName = currentDateTime.format(ddf);
        final String suffix = Files.getFileExtension(file.getOriginalFilename());
        final String fullRelativePath =
                String.format("/%s/%s.%s", relativePath.toString(), fileName, suffix);
        log.debug("Full relative Path: {}", fullRelativePath);

        final File dir = new File(this.basePath + SLASH + relativePath.toString());
        log.debug("Dir: {}", dir.getPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileVO vo = new FileVO();
        vo.setUrl(this.baseUrl + fullRelativePath);

        // 获取当前用户信息
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user == null) {
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
        ResPic resPic = ResPic
                .builder()
                .picSize(Long.valueOf(file.getSize()).intValue())
                .picPath(fullRelativePath)
                .picWeight(image==null?null:String.valueOf(image.getWidth()))
                .picHigh(image==null?null:String.valueOf(image.getHeight()))
                .fileKey(fileKeyTmp)
                .gmtCreate(currentTime)
                .gmtModified(currentTime)
                .creator(user.getLoginId())
                .modifier(user.getLoginName())
                .picName(fileName)
                .picFileName(fileName)
                .picSuffix(DOT + suffix)
                .picFormat(suffix)
                .picCode(fileName)
                .remark("from-merchant-manage")
                .isDeleted(0)
                .build();
        if(Objects.equals(module, "material")){
            resPic.setFileKey("system.resTexture.pic");
        }
        if (Objects.equals(module, "goods")) {
            resPic.setFileKey("system.goods.pic");
        }

        log.debug("ResPic: {}", resPic);
        try {
            file.transferTo(new File(this.basePath + fullRelativePath));
        } catch (IOException e) {
            log.error("move upload file error", e);
        }
        Long id = resPicService.addResPic(resPic);
        ResPic detail = resPicService.getResPicDetail(id);
        if (detail != null) {
            vo.setUrl(this.baseUrl +detail.getPicPath());
        }
        vo.setResId(id);
        return ReturnData.builder().msgId(msgId).code(ResponseEnum.SUCCESS).data(vo).message("上传成功").success(true);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param filekey
     * @return
     */
    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件")
    public ReturnData<FileVO> uploadFile(
            @RequestPart("file") MultipartFile file, @RequestParam String filekey) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        final StringBuilder relativePath =
                new StringBuilder("AA")
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
        log.debug("Relative Path: {}", relativePath);
        // 生成文件名
        final DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        final String fileName = currentDateTime.format(ddf);
        final String suffix = Files.getFileExtension(file.getOriginalFilename());
        final String fullRelativePath =
                String.format("/%s/%s.%s", relativePath.toString(), fileName, suffix);
        log.debug("Full relative Path: {}", fullRelativePath);
        final File dir = new File(this.basePath + SLASH + relativePath.toString());
        log.debug("Dir: {}", dir.getPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileVO vo = new FileVO();
        vo.setUrl(this.baseUrl + fullRelativePath);
        ResFile resFile = new ResFile();
        try {
            resFile.setFileSize(String.valueOf(file.getBytes().length));
        } catch (IOException e) {
            log.error("Get upload image size error", e);
        }
        // 获取当前用户信息
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user == null) {
            user = new LoginUser();
            user.setLoginId("1");
            user.setLoginName("sanduadmin");
        }
        /* 保存到数据库 并设置返回id*/
        resFile.setFilePath(fullRelativePath);
        resFile.setFileCode(fileName);
        resFile.setFileName(String.format("%s.%s", fileName, suffix)); // 文件名
        resFile.setFileOriginalName(file.getOriginalFilename()); // 原文件名
        resFile.setFileSuffix(suffix);
        resFile.setFileKey(filekey);
        resFile.setFileLevel("0");
        resFile.setFileType(suffix);
        resFile.setGmtCreate(new Date());
        resFile.setGmtModified(new Date());
        resFile.setCreator(user.getId() + "");
        resFile.setModifier(user.getId() + "");
        resFile.setIsDeleted(0);
        log.debug("resFile: {}", resFile);
        log.info("basePath: {}", basePath);
        try {
            file.transferTo(new File(this.basePath + fullRelativePath));
        } catch (IOException e) {
            log.error("move upload file error", e);
        }
        int id = resFileService.addFile(resFile);
        vo.setResId(Long.valueOf(id));
        return ReturnData.builder().code(ResponseEnum.SUCCESS).data(vo);
    }
}
