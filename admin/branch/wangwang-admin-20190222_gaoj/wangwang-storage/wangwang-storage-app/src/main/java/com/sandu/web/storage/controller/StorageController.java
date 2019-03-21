package com.sandu.web.storage.controller;

import com.sandu.api.storage.model.ResModel;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.output.FileVO;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.TransStatus;
import com.sandu.common.exception.StorageException;
import com.sandu.common.uploader.Uploader;
import com.sandu.common.uploader.factory.UploaderFactory;
import com.sandu.common.util.file.FileUtil;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sandu.common.util.file.FilePathUtil.*;
import static com.sandu.constant.Punctuation.*;
import static org.apache.commons.io.FilenameUtils.getBaseName;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author bvvy (yocome@gmail.com) 2017/12/16 18:52
 */
@SuppressWarnings("unchecked")
@RestController
@Api(tags = "Storage", description = "存储")
@RequestMapping("/v1/storage")
@Slf4j
public class StorageController extends BaseController {

    public static final String BASE_MAPPING = "/v1/storage";

    @Resource
    private ResPicService resPicService;

    @Value("${server.url}")
    private String serverPath;

/*    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;*/


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
            @ApiImplicitParam(name = "platform", value = "平台 ios windowPc web ipad macBookPc android", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "module", value = "业务模块: 产品 product,材质 texture ", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "文件类型: image 图片 file文件 model模型", paramType = "query", dataType = "string", required = true)
    })
    public ReturnData<FileVO> imgUpload(
            @RequestPart("file") MultipartFile file,
            @RequestParam String platform,
            @RequestParam String module,
            @RequestParam String type) {
        LocalDateTime now = LocalDateTime.now();
        //获取文件保存绝对路径
        String absolutePath = absolutePath(platform, module, type, now, file.getOriginalFilename());
        Uploader uploader = UploaderFactory.createUploader(type, file, absolutePath);
        uploader.upload();
        FileVO fileVO = uploader.getFileVO();
        String returnPath = formatPath(serverPath) + removeStartSlash(relativePath(absolutePath));
        fileVO.setUrl(returnPath);
        /* 保存到数据库 并设置返回id*/
        saveInfo(fileVO, type, absolutePath, module);
        return ReturnData.builder().data(fileVO);
    }


    /**
     * 保存到数据库
     *
     * @param fileVO   文件信息
     * @param fileType 文件类型
     * @param path     路径
     * @param fileKey  文件filekey
     */
    private void saveInfo(FileVO fileVO, String fileType, String path, String fileKey) {
        switch (fileType) {
            case "image": {
                ResPic resPic = ResPic
                        .builder()
                        .fileKey(fileKey)
                        .picSize(fileVO.getSize().intValue())
                        .picPath(SLASH + relativePath(path))
                        .picWeight(String.valueOf(fileVO.getWidth()))
                        .picHigh(String.valueOf(fileVO.getHeight()))
                        .gmtCreate(new Date())
                        .picFileName(getBaseName(path))
                        .picSuffix(getSuffix(path))
                        .picFormat(getExtension(path))
                        .isDeleted(0)
                        .build();
                if(fileKey.equals("texture")){
                    resPic.setFileKey("system.resTexture.pic");
                }
                resPicService.addResPic(resPic);
                fileVO.setResId(resPic.getId());
                break;
            }
            case "file": {

                break;
            }
            case "model": {
                ResModel resModel = ResModel
                        .builder()
                        .modelSize(String.valueOf(fileVO.getSize()))
                        .modelFileName(getBaseName(path))
                        .modelName(getBaseName(path))
                        .modelSuffix(getSuffix(path))
                        .modelPath(relativePath(path))
                        .fileKey(fileKey)
                        .isDeleted(0)
                        .gmtCreate(new Date())
                        .build();
                fileVO.setResId(resModel.getId());
                break;
            }
            default:
                throw new StorageException(String.format("不存在的文件类型 %s", fileType));
        }
    }

    @DeleteMapping
    @ApiOperation(value = "删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resIds", value = "资源id 使用 , 隔开  例如  1,2,3", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "文件类型: image 图片 file文件 model模型", paramType = "query", dataType = "string", required = true)
    })
    public ReturnData delete(@RequestParam("resIds") String resIdStrs, @RequestParam String type) {

        List<String> resIds = Arrays.asList(resIdStrs.split(COMMA));
//        deleteDbInfo(resIds,type);
        return ReturnData.builder().success(true);
    }

    @DeleteMapping("/path")
    public ReturnData removeFile(@RequestParam() String path) {
        String realPath = storagePath() + SLASH + removeStr(path, formatPath(serverPath, BASE_MAPPING));
        FileUtil.removeFile(realPath);
        return ReturnData.builder().code(ResponseEnum.SUCCESS).message("删除成功");
    }



    /**
     * 下载
     *
     * @return 文件
     */
    @GetMapping("/**/{filename:.+\\.(?:jpg|jpeg|png|bmp|gif|JPG|JPEG|PNG|BMP|GIF|xls|3du|fbx|assetbundle|zip|XLS|3DU|FBX|ASSETBUNDLE|ZIP)}")
    @ApiIgnore
    public StreamingResponseBody readFile(HttpServletRequest request) {
        String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        restOfTheUrl = restOfTheUrl.replace(BASE_MAPPING, EMPTY);
        log.info("file path is {}", restOfTheUrl);
        File file = new File(storagePath() + restOfTheUrl);
        byte[] filebyte = FileUtil.readFileToByteArray(file);
        return out -> out.write(filebyte);
    }

    @GetMapping("/pic/{picId}")
    public ReturnData getPathById(@PathVariable Long picId) {
        String s = resPicService.getResPicDetail(picId).getPicPath();
        return ReturnData.builder().data(s).message("成功");
    }

    /**
     * 异常
     * controller级别异常
     *
     * @param ex 异常
     * @return 信息
     */
    @ExceptionHandler({StorageException.class})
    public ResponseEntity<String> handlerStorageException(StorageException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }







}
