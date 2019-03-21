package com.sandu.web.basewaterjet.controller;

import com.google.common.collect.Lists;
import com.sandu.api.basewaterjet.input.BasewaterjetAdd;
import com.sandu.api.basewaterjet.input.BasewaterjetQuery;
import com.sandu.api.basewaterjet.input.BasewaterjetUpdate;
import com.sandu.api.basewaterjet.input.StatusUpdate;
import com.sandu.api.basewaterjet.model.Basewaterjet;
import com.sandu.api.basewaterjet.output.BasewaterjetVO;
import com.sandu.api.basewaterjet.output.BrandNameVO;
import com.sandu.api.basewaterjet.output.CadUploadVO;
import com.sandu.api.basewaterjet.service.biz.BasewaterjetBizService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.output.ImgUploadVO;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.common.exception.BizException;
import com.sandu.commons.FileUtils;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.Utils;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sandu.common.ReturnData.builder;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-09 10:03
 */
@Api(value = "Basewaterjet", tags = "basewaterjet", description = "水刀模板")
@RestController
@RequestMapping(value = "/v1/basewaterjet")
@Slf4j
public class BasewaterjetController extends BaseController {

    @Value("${file.storage.path}")
    private String rootPath;

    @Value("${product.baseWaterjetTemplate.templatePic.upload.path}")
    private String uploadPicPath;

    @Value("${product.baseWaterjetTemplate.cadSourceFile.upload.path}")
    private String uploadFilePath;

    @Value("${product.baseWaterjetTemplate.templatedescribe.upload.path}")
    private String uploadDescFilePath;

    @Autowired
    private BasewaterjetBizService basewaterjetBizService;

    @Autowired
    private ResPicService resPicService;

    @ApiOperation(value = "新建水刀模板", response = ReturnData.class)
    @PostMapping
    public ReturnData createBasewaterjet(@Valid @RequestBody BasewaterjetAdd input, BindingResult validResult) {
        //参数校验
        if (validResult.hasErrors()) {
            return processValidError(validResult, builder());
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null)
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("请重新登录");

        try {

            //保存水刀介绍
            if(StringUtils.isNotBlank(input.getTemplateDescribe())){
                //生成文件
                String fileId = addTemplateDescribeFile(input.getTemplateDescribe(),loginUser);
                if(StringUtils.isBlank(fileId))
                    return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("保存店铺介绍失败");
                input.setTemplateDescribe(fileId);
            }

            int basewaterjetId =  basewaterjetBizService.create(input,loginUser);
            if (basewaterjetId > 0) {
                return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("新增水刀模板成功");
            }else{
                return ReturnData.builder().success(false).code(ResponseEnum.NOT_CONTENT).message("新增水刀模板失败");
            }

        }catch (BizException e){
            log.error("新增水刀模板失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("新增水刀模板失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }

    }

    @ApiOperation(value = "编辑水刀模板", response = ReturnData.class)
    @PutMapping
    public ReturnData updateBasewaterjet(@Valid @RequestBody BasewaterjetUpdate input, BindingResult validResult) {
        //参数校验
        if (validResult.hasErrors()) {
            return processValidError(validResult, builder());
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null)
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("请重新登录");

        try {

            //保存水刀介绍
            if(StringUtils.isNotBlank(input.getTemplateDescribe())){

                //判断是否之前有水刀介绍
                Basewaterjet basewaterjet = basewaterjetBizService.getById(input.getBasewaterjetId());

                //判断
                if(StringUtils.isNotBlank(basewaterjet.getTemplateDescribe()) && StringUtils.isNotBlank(basewaterjet.getFilePath())){

                    //重新写入
                    FileUtils.writeTxtFile(rootPath+basewaterjet.getFilePath(), input.getTemplateDescribe());
                    input.setTemplateDescribe(null);

                }else{

                    //生成文件
                    String fileId = addTemplateDescribeFile(input.getTemplateDescribe(),loginUser);
                    if(StringUtils.isBlank(fileId))
                        return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("保存店铺介绍失败");
                    input.setTemplateDescribe(fileId);

                }

            }

            basewaterjetBizService.update(input,loginUser);
            return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("编辑水刀模板成功");

        }catch (BizException e){
            log.error("编辑水刀模板失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("编辑水刀模板失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }

    }

    @ApiOperation(value = "删除水刀模板", response = ReturnData.class)
    @DeleteMapping
    public ReturnData deleteBasewaterjet(String basewaterjetIds) {
        //参数校验
        if(StringUtils.isBlank(basewaterjetIds))
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("参数不能为空");

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null)
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("请重新登录");

        try {

            basewaterjetBizService.delete(basewaterjetIds,loginUser);
            return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("删除水刀模板成功");

        }catch (BizException e){
            log.error("删除水刀模板失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("删除水刀模板失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }

    }

    @ApiOperation(value = "获取水刀模板详情", response = BasewaterjetVO.class)
    @GetMapping
    public ReturnData getByBasewaterjetId(String basewaterjetId) {
        //参数校验
        int id = 0;
        if(StringUtils.isBlank(basewaterjetId))
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("参数错误");
        else{
            try {
                id = Integer.parseInt(basewaterjetId);
            }catch (Exception e){
                return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("参数错误");
            }
        }

        try {

            Basewaterjet basewaterjet =  basewaterjetBizService.getById(id);
            if(null != basewaterjet){
                basewaterjet.setBasewaterjetId(basewaterjet.getId());

                //获取水刀介绍
                if(StringUtils.isNotBlank(basewaterjet.getTemplateDescribe()) && StringUtils.isNotBlank(basewaterjet.getFilePath())){
                    String fileContext = FileUtils.getFileContext(rootPath + basewaterjet.getFilePath());
                    basewaterjet.setTemplateDescribe(fileContext);
                }
            }

            return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("获取水刀模板详情成功").data(basewaterjet);

        }catch (BizException e){
            log.error("获取水刀模板详情失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("获取水刀模板详情失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }
    }

    @ApiOperation(value = "查询水刀模板列表", response = BasewaterjetVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer")
    })
    @GetMapping(value = "/list")
    public ReturnData queryBasewaterjetList(@Valid BasewaterjetQuery query, BindingResult validResult) {

        //参数校验
        if (validResult.hasErrors()) {
            return processValidError(validResult, builder());
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null)
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("请重新登录");

        try {

            //品牌参数
            List<Integer> brandIdList = null;
            if(null == query.getBrandId()){
                List<BrandNameVO> brandList  = basewaterjetBizService.getBrandNameList(loginUser.getId(),loginUser);
                if(null != brandList && brandList.size() > 0){
                    brandIdList = brandList.stream().map(brandNameVO -> brandNameVO.getId()).collect(Collectors.toList());
                }
            }else{
                brandIdList = new ArrayList<>();
                brandIdList.add(query.getBrandId());
            }
            query.setBrandIdList(brandIdList);

            if(null == query.getBrandIdList())
                return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("该用户未关联品牌");


            List<BasewaterjetVO> basewaterjets = Lists.newArrayList();
            int count = basewaterjetBizService.queryCount(query);

            if(count > 0){
                List<Basewaterjet> basewaterjetList = basewaterjetBizService.query(query);

                if(basewaterjetList != null && basewaterjetList.size() > 0){

                    basewaterjetList.stream().forEach(basewaterjet -> {

                        BasewaterjetVO output = new BasewaterjetVO();
                        BeanUtils.copyProperties(basewaterjet, output);
                        output.setBasewaterjetId(basewaterjet.getId());

                        basewaterjets.add(output);
                    });

                }
            }

            return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("获取水刀模板列表成功").list(basewaterjets).total(count);

        }catch (BizException e){
            log.error("查询水刀模板列表失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("查询水刀模板列表失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }

    }


    @ApiOperation(value = "获取水刀模板品牌列表", response = BasewaterjetVO.class)
    @GetMapping(value = "/brandlist")
    public ReturnData getBrandList(String userId) {
        //参数校验
        int id = 0;
        if(StringUtils.isBlank(userId))
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("参数错误");
        else{
            try {
                id = Integer.parseInt(userId);
            }catch (Exception e){
                return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("参数错误");
            }
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null)
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("请重新登录");

        try {

            List<BrandNameVO> brandNameList =  basewaterjetBizService.getBrandNameList(id,loginUser);

            return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("获取水刀模板品牌集合成功").list(brandNameList);

        }catch (BizException e){
            log.error("获取水刀模板品牌集合失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("获取水刀模板品牌集合失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }
    }

    @ApiOperation(value = "水刀模板上下架", response = BasewaterjetVO.class)
    @PutMapping(value = "/upperandlowerstatus")
    public ReturnData upperandlowerstatus(@Valid @RequestBody StatusUpdate statusUpdate , BindingResult validResult) {
        //参数校验
        if (validResult.hasErrors()) {
            return processValidError(validResult, builder());
        }
        Integer status = null;
        try {
            status = Integer.parseInt(statusUpdate.getTemplateStatus());
        }catch (Exception e){
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("参数错误");
        }


        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null)
            return ReturnData.builder().success(false).code(ResponseEnum.PARAM_ERROR).message("请重新登录");

        try {

            int count = basewaterjetBizService.upperandlowerstatus(statusUpdate.getBasewaterjetIds(),status,loginUser);

            String message = "";
            if(statusUpdate.getTemplateStatus().equals("1")){
                message = "上架";
            }else{
                message = "下架";
            }

            if(count > 0){
                return ReturnData.builder().success(true).code(ResponseEnum.SUCCESS).message("水刀模板"+message+"成功");
            }else{
                return ReturnData.builder().success(false).code(ResponseEnum.SUCCESS).message("水刀模板"+message+"失败");
            }

        }catch (BizException e){
            log.error("水刀模板上下架失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message(e.getMessage());
        }catch (Exception e){
            log.error("水刀模板上下架失败",e);
            return ReturnData.builder().success(false).code(ResponseEnum.ERROR).message("系统错误");
        }
    }


    @ApiOperation(value = "上传水刀模板图片", response = ImgUploadVO.class)
    @PostMapping("/img/upload")
    public ResponseEnvelope uploadImg(@RequestParam("file") MultipartFile file) {
        ImgUploadVO imgUploadVO = new ImgUploadVO();

        try {
            //流验证
            if (file.isEmpty() || org.apache.commons.lang3.StringUtils.isBlank(file.getOriginalFilename())) {
                return new ResponseEnvelope(false, "流为空", imgUploadVO);
            }

            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(com.sandu.commons.LoginUser.class);
            if(null == loginUser)
                return new ResponseEnvelope(false, "未登录，请重新登录", imgUploadVO);

            //路径补全
            uploadPicPath = StringUtil.replaceDate(uploadPicPath, null);

            //创建流与验证
            File dir = new File(rootPath + uploadPicPath);
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
            if (!("jpg，png，jpeg".contains(suffix)||"JPG，PNG，JPEG".contains(suffix))) {
                return new ResponseEnvelope(false, "仅支持图片格式jpg、png、jpeg");
            }
            if (size > 10 * 1024 * 1024) {
                return new ResponseEnvelope(false, "仅支持上传图片大小10M");
            }

            //路径补全
            String filePath = "";
            StringBuffer sb = new StringBuffer();
            sb.append(uploadPicPath);
            sb.append("/");
            sb.append(this.generateRandomDigitString(6));
            sb.append("_");
            sb.append(System.currentTimeMillis());
            sb.append(".");
            sb.append(suffix);
            filePath = sb.toString();

            //上传图片
            File picFile = new File(rootPath+filePath);
            file.transferTo(picFile);


            log.error("上传文件完成。。。。"+rootPath+filePath);
            log.error("开始新增数据。。。。");

            //获取上传文件信息
            Map<String, String> map = FileUtils.getMap(picFile,"product.baseWaterjetTemplate.templatePic.upload.path",false);

            log.error("获取数据流。。。。");

            //2.新增resPic图片信息表
            Integer id = basewaterjetBizService.saveUploadImgPic(map,null,loginUser,"水刀模板图片");

            if(id > 0) {
                imgUploadVO.setPicPath(filePath);
                imgUploadVO.setId(id);
                return new ResponseEnvelope(true, "新增图片成功", imgUploadVO);
            }else{
                return new ResponseEnvelope(true, "新增图片失败", imgUploadVO);
            }
        } catch (Exception e) {

            log.error("uploadImg 方法 系统异常", e);
            return new ResponseEnvelope(false, "系统异常", imgUploadVO);
        }
    }

    @ApiOperation(value = "上传水刀模板CAD文件", response = ImgUploadVO.class)
    @PostMapping("/file/upload")
    public ResponseEnvelope uploadFile(@RequestParam("file") MultipartFile file) {
        CadUploadVO cadUploadVO = new CadUploadVO();

        try {
            //流验证
            if (file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())) {
                return new ResponseEnvelope(false, "流为空", cadUploadVO);
            }

            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if(null == loginUser)
                return new ResponseEnvelope(false, "未登录，请重新登录", cadUploadVO);

            //路径补全
            uploadFilePath = StringUtil.replaceDate(uploadFilePath, null);

            //创建流与验证
            File dir = new File(rootPath + uploadFilePath);
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
            if (!("dwg，dwt，bak，dxf".contains(suffix)||"DWG，DWT，BAK，DXF".contains(suffix))) {
                return new ResponseEnvelope(false, "仅支持文件格式dwg、dwt、bak、dxf");
            }

            //路径补全
            String filePath = "";
            StringBuffer sb = new StringBuffer();
            sb.append(uploadFilePath);
            sb.append("/");
            sb.append(this.generateRandomDigitString(6));
            sb.append("_");
            sb.append(System.currentTimeMillis());
            sb.append(".");
            sb.append(suffix);
            filePath = sb.toString();

            //上传图片
            File picFile = new File(rootPath+filePath);
            file.transferTo(picFile);

            log.error("上传文件完成。。。。"+rootPath+filePath);
            log.error("开始新增数据。。。。");
            //获取上传文件信息
            Map<String, String> map = FileUtils.getMap(picFile,"product.baseWaterjetTemplate.cadSourceFile.upload.path",false);

            log.error("获取数据流。。。。");

            //2.新增resFile文件表
            Integer id = basewaterjetBizService.saveUploadFile(map,null,loginUser,"水刀模板CAD文件");

            if(id > 0) {
                cadUploadVO.setPath(filePath);
                cadUploadVO.setId(id+"");
                cadUploadVO.setName(filename);
                return new ResponseEnvelope(true, "新增CAD文件成功", cadUploadVO);
            }else{
                return new ResponseEnvelope(false, "新增CAD文件失败", cadUploadVO);
            }
        } catch (Exception e) {
            log.error("uploadFile 方法 系统异常", e);
            return new ResponseEnvelope(false, "系统异常", cadUploadVO);
        }
    }


    private String addTemplateDescribeFile(String templateDescribe,LoginUser loginUser){
        String fileId = "";

        //路径
        uploadDescFilePath = StringUtil.replaceDate(uploadDescFilePath, null);

        //创建流与验证
        File dir = new File(rootPath + uploadDescFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //路径补全
        String suffix = "txt";
        String filePath = "";
        StringBuffer sb = new StringBuffer();
        sb.append(uploadDescFilePath);
        sb.append("/");
        sb.append(this.generateRandomDigitString(6));
        sb.append("_");
        sb.append(System.currentTimeMillis());
        sb.append(".");
        sb.append(suffix);
        filePath = sb.toString();

        boolean falg = FileUtils.writeTxtFile(rootPath+filePath, templateDescribe);

        if (falg) {

            //获取上传文件信息
            File file = new File(rootPath+filePath);
            Map<String, String> map = FileUtils.getMap(file, "product.baseWaterjetTemplate.templatedescribe.upload.path",false);

            //2.新增resFile文件表
            Integer id = basewaterjetBizService.saveUploadFile(map,null,loginUser,"水刀模板介绍文件");
            if(null != id)
                fileId = id.toString();

        } else {
            return null;
        }

        return fileId;
    }



    private static String generateRandomDigitString(int aLength) {
        SecureRandom tRandom = new SecureRandom();
        String aString = "";
        tRandom.nextLong();
        long tLong = Math.abs(tRandom.nextLong());

        for(aString = String.valueOf(tLong).trim(); aString.length() < aLength; aString = aString + String.valueOf(tLong).trim()) {
            tLong = Math.abs(tRandom.nextLong());
        }

        aString = aString.substring(0, aLength);
        return aString;
    }
}
