package com.sandu.web.imall;

import com.sandu.api.imallSlide.model.ImallSlide;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.api.imallSlide.service.ImallSlideBizService;
import com.sandu.common.util.Utils;
import com.sandu.commons.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.sandu.constant.ResponseEnum.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 15:35
 */
@Api(value = "积分商城设置", tags = "积分商城设置", description = "积分商城设置")
@RestController
@RequestMapping(value = "/v1/imallSlide")
public class ImallSlideController extends BaseController {

    @Value("${upload.base.path}")
    private String rootPath;

    @Value("${imall.slide.upload.path}")
    private String slidePath;

    private Logger logger = LoggerFactory.getLogger(ImallSlideController.class);

    @Autowired
    private ImallSlideBizService imallSlideBizService;

    @ApiOperation(value = "保存图片", response = ReturnData.class)
    @RequestMapping(value = {"/imageUpload"},consumes = "multipart/form-data",method=RequestMethod.POST)
    @RequiresPermissions({"points:mall:options:edit"})
    public ReturnData imageUpload(int order,HttpServletRequest request) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser= LoginContext.getLoginUser(LoginUser.class);
        String creator=loginUser.getNickName();
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            if (files.size()>0){
                MultipartFile mfile=files.get(0);
                try {
                    // 文件保存路径
                    slidePath = StringUtil.replaceDate(slidePath, null);
                    String filePath = rootPath + slidePath;
                    File file=new File(filePath);
                    if(!file.exists()) file.mkdirs();
                    String suffix=mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf("."));
                    String fileNameNew=UUID.randomUUID().toString();//String.valueOf(new Date().getTime())+String.valueOf(i);//新文件名
                    mfile.transferTo(new File(filePath+fileNameNew+suffix));// 转存文件
                    ImallSlide imallSlide=new ImallSlide();
                    imallSlide.setFileName(slidePath+fileNameNew+suffix);
                    imallSlide.setOrder(order);
                    imallSlide.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                    imallSlide.setType(0);//首页
                    imallSlide.setCreator(creator);
                    int insert = imallSlideBizService.insert(imallSlide);
                    if (insert>0){
                        imallSlide.setId(insert);
                        return data.code(SUCCESS).data(imallSlide);
                    }else{
                        return data.code(NOT_CONTENT).message("上传成功，向数据库添加记录失败");
                    }
                } catch (Exception e) {
                    logger.error("未知错误:",e);
                }

            }
        return data.code(PARAM_ERROR).message("输入数据有误");
    }


    @ApiOperation(value = "删除图片", response = ReturnData.class)
    @RequestMapping(value = {"/delete"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:options:edit"})
    public ReturnData deleteImageById(int id,String fileName) {
        ReturnData data = ReturnData.builder();

        int result = imallSlideBizService.delete(id);
        if (result > 0) {
            try {
                String filePath = ResourceUtils.getURL("classpath:").getPath() + "upload/slide/";
                new File(filePath + fileName).delete();
            } catch (Exception e) {

            }

            return data.code(SUCCESS).data(result);
        }

        return data.code(PARAM_ERROR).message("异常");
    }

    @ApiOperation(value = "查询图片", response = ReturnData.class)
    @RequestMapping(value = {"/list"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:options:view"})
    public ReturnData list(int type) {
        ReturnData data = ReturnData.builder();

        List<ImallSlide> results = imallSlideBizService.getList(type);
        return data.code(SUCCESS).list(results);
    }
}
