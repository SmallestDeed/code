package com.sandu.web.supplydemand;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 9:37 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.file.FileVO;
import com.sandu.common.file.uploader.Uploader;
import com.sandu.common.file.uploader.factory.UploaderFactory;
import com.sandu.common.file.util.file.FilePathUtil;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.supplydemand.service.SupplyDemandPicService;
import com.sandu.system.service.ResPicService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.io.FilenameUtils.getBaseName;

/**
 * @author weisheng
 * @Title: 图片上传
 * @Package 图片上传
 * @Description: 图片上传
 * @date 2018/5/4 0004AM 9:37
 */
@Slf4j
@RestController
@RequestMapping("/v1/union/supplydemandpic")
public class SupplyDemandPicController {
    private final static String CLASS_LOG_PREFIX = "[图片上传服务]";

    /**
     * 默认限制大小 1M
     */
    private static final long DEFAULT_LIMIT = 1024 * 1024 * 1;

    private static final String DEFAULT_RESFORMAT = ".+(.BMP|.JPG|.JPEG|.GIF|.PNG)$";

    private static final String USER_PIC = "user" ;

    private static final String SUPPLYDEMAND_PIC = "supplyDemand";

    @Autowired
    private SupplyDemandPicService supplyDemandPicService;

    @Autowired
    private ResPicService resPicService;

    @Value("${file.storage.path}")
    private String storagePath;

    /**
     * 供求信息图片上传
     *
     * @param file     文件
     * @param platform platform
     * @param module   module
     * @param type     type
     * @return 图片路径
     */
    @PostMapping("/uploadrespic")
    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", value = "平台 ios windowPc web ipad macBookPc android", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "module", value = "业务模块: 产品 product ", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "文件类型: image 图片 file文件 model模型", paramType = "query", dataType = "string", required = true)
    })
    public ResponseEnvelope imgUpload(
            @RequestPart("file") MultipartFile file,
            @RequestParam String platform,
            @RequestParam String module,
            @RequestParam String type) {
       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }
        LocalDateTime now = LocalDateTime.now();
        String absolutePath = FilePathUtil.absolutePath(storagePath, platform, module, type, now, file.getOriginalFilename());
        Uploader uploader = UploaderFactory.createUploader(type, file, absolutePath);

        if (file.getSize() > DEFAULT_LIMIT) {
            return new ResponseEnvelope(false, "请上传小于1M的图片");
        }
        String baseName = getBaseName(absolutePath) + FilePathUtil.getExtension(absolutePath).toUpperCase();
        Pattern compile = Pattern.compile(DEFAULT_RESFORMAT);
        Matcher matcher = compile.matcher(baseName);
        if (!matcher.find()) {
            return new ResponseEnvelope(false, "允许上传的图片格式为BMP\\JPG\\JPEG\\GIF\\PNG(格式不区分大小写)");
        }

        uploader.upload();
        FileVO fileVO = uploader.getFileVO();
        String returnPath = FilePathUtil.formatPath(FilePathUtil.serverPath()) + FilePathUtil.removeStartSlash(FilePathUtil.relativePath(storagePath, absolutePath));
        fileVO.setUrl(returnPath);
        //保存到数据库 并设置返回id
        Long picId = supplyDemandPicService.saveResPic(fileVO, type, absolutePath, loginUser, storagePath);
        fileVO.setResId(picId);
        if (picId == null || picId == 0) {
            return new ResponseEnvelope(false, "Failed to upload picture");
        }
        return new ResponseEnvelope(true, "Success to upload picture", fileVO);
    }


    /**
     * 图片上传
     *
     * @param base64Data    Base64码
     * @param platform platform
     * @param module   module
     * @param type     type
     * @return 图片路径
     */
    @RequestMapping(value = "/uploadBase64", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传Base64图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", value = "平台 ios windowPc web ipad macBookPc android", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "module", value = "业务模块: 产品 product 用户 user", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "文件类型: image 图片 file文件 model模型", paramType = "query", dataType = "string", required = true)
    })
    public ResponseEnvelope base64UpLoad(@RequestParam String base64Data,
                                         @RequestParam String platform,
                                         @RequestParam String module,
                                         @RequestParam String type) {
        /* LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
       if (loginUser == null) {
            return new ResponseEnvelope(false, "请先登录");
        }*/
        LoginUser loginUser = new LoginUser();
       loginUser.setName("测试");
       loginUser.setId(823);
        try {
            log.info("上传文件的数据：" + base64Data);
            String dataPrix = "";
            String data = "";

            log.info("对数据进行判断格式是否正确");
            if (base64Data == null || "".equals(base64Data)) {
                throw new Exception("上传失败，上传图片数据为空");
            } else {
                String[] d = base64Data.split("base64,");
                if (d != null && d.length == 2) {
                    dataPrix = d[0];
                    data = d[1];
                } else {
                    return new ResponseEnvelope(false, "上传失败，数据不合法");
                }
            }

            log.info("对数据进行解析，获取文件名和流数据");
            String suffix = "";
            if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if ("data:image/jpg;".equalsIgnoreCase(dataPrix)) {//data:image/x-icon;base64,base64编码的jpg图片数据
                suffix = ".jpg";
            } else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            } else if ("data:image/bmp;".equalsIgnoreCase(dataPrix)) {//data:image/png;base64,base64编码的bmp图片数据
                suffix = ".bmp";
            } else {
                return new ResponseEnvelope(false,"上传图片格式不合法");
            }
            LocalDateTime now = LocalDateTime.now();
            String tempFileName = FilePathUtil.filename(now) + suffix;
            log.info("生成文件名为：" + tempFileName);

            String absolutePath = FilePathUtil.absolutePath(storagePath, platform, module, type, now, tempFileName);
            log.info("生成文件路径为：" + absolutePath);
            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);
            try {
                InputStream input = new ByteArrayInputStream(bs);
                MultipartFile multipartFile = new MockMultipartFile("file", tempFileName, "text/plain", IOUtils.toByteArray(input));
                Uploader uploader = UploaderFactory.createUploader(type, multipartFile, absolutePath);
                if (multipartFile.getSize() > DEFAULT_LIMIT) {
                    log.info("文件大小为:"+multipartFile.getSize());
                    return new ResponseEnvelope(false, "请上传小于1M的图片");
                }
                uploader.upload();
                log.info("上传文件到服务器完成");
                FileVO fileVO = uploader.getFileVO();
                String returnPath = FilePathUtil.formatPath(FilePathUtil.serverPath()) + FilePathUtil.removeStartSlash(FilePathUtil.relativePath(storagePath, absolutePath));
                fileVO.setUrl(returnPath);
                //保存到数据库 并设置返回id
                Long picId = 0L;
                if(USER_PIC.equals(module)){
                    picId = resPicService.saveUserResPic(fileVO, type, absolutePath,loginUser,storagePath,module);
                }else if (SUPPLYDEMAND_PIC.equals(module)){
                    picId =supplyDemandPicService.saveResPic(fileVO, type, absolutePath, loginUser, storagePath);
                }
                fileVO.setResId(picId);
                if (picId == null || picId == 0) {
                    return new ResponseEnvelope(false, "上传图片失败");
                }
                log.info("上传成功");
                return new ResponseEnvelope(true, "上传图片成功", fileVO);
            } catch (Exception ee) {
                log.info("上传失败，写入文件失败" + ee.getMessage());
                return new ResponseEnvelope(false,"上传失败，写入文件失败");
            }
        } catch (Exception e) {
            log.info("上传失败," + e.getMessage());
            return new ResponseEnvelope(false,"上传图片失败");
        }
    }
}












