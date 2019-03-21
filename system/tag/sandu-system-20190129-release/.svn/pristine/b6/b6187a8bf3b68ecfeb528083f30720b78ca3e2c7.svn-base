package com.sandu.web.shop;

import com.sandu.annotation.DuplicateSubmitToken;
import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.output.ImgUploadVO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.common.constant.ShopResTypeConstant;
import com.sandu.api.shop.input.*;
import com.sandu.api.shop.model.ProjectCase;
import com.sandu.api.shop.output.ProjectCaseDetailsVO;
import com.sandu.api.shop.output.ProjectCaseVO;
import com.sandu.api.shop.service.ProjectCaseService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.commons.Utils;
import com.sandu.commons.util.StringUtils;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工程案例-后台管理-控制层
 *
 * @auth xiaoxc
 * @data 2018-06-20
 */
@Api(tags = "projectCase", description = "工程案例")
@RestController
@RequestMapping("/v1/shop/case")
public class ProjectCaseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ProjectCaseController.class);
    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${company.shop.casePic.upload.path}")
    private String caseUploadPath;
    @Autowired
    private ProjectCaseService projectCaseService;
    @Autowired
    private ResPicService resPicService;


    @ApiOperation(value = "新增工程案例", response = ResponseEnvelope.class)
    @DuplicateSubmitToken
    @PostMapping("/save")
    public ResponseEnvelope save(@Valid @RequestBody ProjectCaseAdd caseAdd, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 新增数据
        int result = projectCaseService.add(caseAdd, loginUser);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "创建成功!");
        } else {
            return new ResponseEnvelope<>(false, "创建失败!");
        }
    }

    @ApiOperation(value = "编辑工程案例", response = ResponseEnvelope.class)
    @PutMapping("update")
    public ResponseEnvelope edit(@Valid @RequestBody ProjectCaseUpdate caseUpdate, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 修改数据
        int result = projectCaseService.update(caseUpdate, loginUser);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "修改成功!");
        } else {
            return new ResponseEnvelope<>(false, "修改失败!");
        }
    }


    @ApiOperation(value = "删除工程案例", response = ResponseEnvelope.class)
    @DeleteMapping("remove")
    public ResponseEnvelope remove(@RequestParam("caseId") Integer caseId, Integer shopType) {
        // 参数验证
        if (caseId == null || caseId == 0) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 删除数据
        int result = projectCaseService.delete(caseId, loginUser, shopType);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "删除成功!");
        } else {
            return new ResponseEnvelope<>(false, "删除失败!");
        }
    }

    @ApiOperation(value = "工程案例详情", response = ResponseEnvelope.class)
    @GetMapping("get")
    public ResponseEnvelope get(@RequestParam("caseId") Integer caseId) {
        // 参数验证
        if (caseId == null || caseId == 0) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 查询店铺详情
        ProjectCaseDetailsVO detailsVO = projectCaseService.getDetails(caseId);
        if (detailsVO != null) {
            return new ResponseEnvelope<>(true, detailsVO );
        } else {
            return new ResponseEnvelope<>(false, "查询异常!");
        }
    }

    @ApiOperation(value = "工程案例列表", response = ResponseEnvelope.class)
    @PostMapping("list")
    public ResponseEnvelope list(@RequestBody ProjectCaseQuery query, BindingResult validResult) {
        // 校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        List<ProjectCaseVO> caseVOList = new ArrayList<>();
        // 查询数量
        int total = projectCaseService.getCount(query);
        if (total > 0) {
            // 查询列表
            caseVOList = projectCaseService.findList(query);
        }
        return new ResponseEnvelope(true, total, caseVOList);
    }

    @SuppressWarnings("all")
    @ApiOperation(value = "上传工程案例图片", response = ImgUploadVO.class)
    @PostMapping("/img/upload")
    public ResponseEnvelope uploadImg(@RequestParam("file") MultipartFile file, Integer businessId) {
        // 参数验证
        if (file == null) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }
        ImgUploadVO imgUploadVO = new ImgUploadVO();
        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        if (StringUtils.isEmpty(caseUploadPath)) {
            return new ResponseEnvelope<>(false, "caseUploadPath is empty！");
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
        if (!("gif,jpg,jpeg,png".contains(suffix) || "GIF,JPG,JPEG,PNG".contains(suffix))) {
            return new ResponseEnvelope(false, "仅支持图片格式gif、jpg、jpeg、png");
        }
        if (size > 4 * 1024 * 1024) {
            return new ResponseEnvelope(false, "仅支持上传图片大小小于4M的图片");
        }
        try {
            caseUploadPath = StringUtil.replaceDate(caseUploadPath, null);
            File dir = new File(rootPath + caseUploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //路径补全
            String filePath = "";
            StringBuilder builder = new StringBuilder();
            builder.append(caseUploadPath);
            //builder.append("/");
            builder.append(Utils.generateRandomDigitString(6));
            builder.append("_");
            builder.append(System.currentTimeMillis());
            builder.append("_1");
            builder.append(".");
            builder.append(suffix);
            filePath = builder.toString();

            //上传图片
            File picFile = new File(rootPath + filePath);
            file.transferTo(picFile);

            //获取上传文件信息
            Map<String, String> map = FileUtils.getMap(picFile,ShopResTypeConstant.SHOP_RES_CASE_PIC_KEY,true);

            //2.新增resPic图片信息表
            Integer resId = resPicService.saveUploadImgPic(map, businessId, loginUser, ShopResTypeConstant.SHOP_RES_CASE_PIC_TYPE);

            if (resId > 0) {
                imgUploadVO.setPicPath(filePath);
                imgUploadVO.setId(resId);
                return new ResponseEnvelope(true, "上传成功", imgUploadVO);
            }else{
                return new ResponseEnvelope(true, "上传失败", imgUploadVO);
            }
        } catch (Exception e) {
            logger.error("uploadImg 方法 系统异常", e);
            return new ResponseEnvelope(false, "系统异常", imgUploadVO);
        }
    }


    @ApiOperation(value = "更新工程案例发布状态", response = ResponseEnvelope.class)
    @PostMapping("updateReleaseStauts")
    public ResponseEnvelope updateRelease(@RequestBody ProjectCaseUpdate caseUpdate) {
        // 校验参数
        if (caseUpdate == null) {
            return new ResponseEnvelope<>(false, "caseUpdate is empty！");
        }
        Integer caseId = caseUpdate.getCaseId();
        Integer releaseStatus = caseUpdate.getReleaseStatus();
        if (caseId == null || releaseStatus == null) {
            return new ResponseEnvelope<>(false, "Param is empty！");
        }

        // 获取登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope<>(false, "请登录系统！");
        }
        // 设置参数
        ProjectCase projectCase = new ProjectCase();
        projectCase.setId(caseId);
        projectCase.setReleaseStatus(releaseStatus);
        // 更新数据
        int result = projectCaseService.updateCase(projectCase, loginUser, caseUpdate.getShopType());
        if (result > 0) {
            if (releaseStatus == 1) {
                return new ResponseEnvelope<>(true, "发布成功!");
            } else {
                return new ResponseEnvelope<>(true, "取消发布成功!");
            }
        } else {
            if (releaseStatus == 1) {
                return new ResponseEnvelope<>(false, "发布失败!");
            } else {
                return new ResponseEnvelope<>(false, "取消发布失败!");
            }
        }
    }
}
