package com.sandu.web.shop;

import com.sandu.api.company.common.FileUtils;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.output.ImgUploadVO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.common.constant.ShopResTypeConstant;
import com.sandu.api.shop.input.*;
import com.sandu.api.shop.output.CompanyShopArticleDetailVO;
import com.sandu.api.shop.output.CompanyShopArticleListVO;
import com.sandu.api.shop.service.CompanyShopArticleService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.PageHelper;
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
 * 店铺博文--后台管理--控制层
 * @author WangHaiLin
 * @date 2018/8/9  11:04
 */

@Api(tags = "shopArticle", description = "店铺博文")
@RestController
@RequestMapping("/v1/company/shop/article")
public class CompanyShopArticleController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CompanyShopArticleController.class);

    @Value("${upload.base.path}")
    private String rootPath;
    @Value("${company.shop.articlePic.upload.path}")
    private String articlePicUploadPath;

    @Autowired
    private CompanyShopArticleService shopArticleService;

    @Autowired
    private ResPicService resPicService;



    @ApiOperation(value = "新增店铺博文", response = ResponseEnvelope.class)
    @PostMapping("/save")
    public ResponseEnvelope saveArticle(@Valid @RequestBody CompanyShopArticleAdd articleAdd, BindingResult validResult) {

        try {
            // 校验参数
            if (validResult.hasErrors()) {
                logger.error("新增店铺博文：入参CompanyShopArticleAdd：{}",articleAdd);
                return processValidError(validResult, new ResponseEnvelope());
            }
            // 获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser == null || loginUser.getId() == null) {
                return new ResponseEnvelope<>(false, "请登录系统！");
            }
            // 新增数据
            Long result = shopArticleService.add(articleAdd, loginUser);
            logger.info("新增店铺博文结果：新增博文id：{}",result);
            if (result > 0) {
                return new ResponseEnvelope<>(true, "创建成功!");
            } else {
                return new ResponseEnvelope<>(false, "创建失败!");
            }
        }catch (Exception e){
            logger.error("新增店铺博文 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "编辑店铺博文", response = ResponseEnvelope.class)
    @PutMapping("/update")
    public ResponseEnvelope editArticle(@Valid @RequestBody CompanyShopArticleUpdate update , BindingResult validResult) {

        try{
            // 校验参数
            if (validResult.hasErrors()) {
                logger.error("修改店铺博文：CompanyShopArticleUpdate：{}",update);
                return processValidError(validResult, new ResponseEnvelope());
            }
            // 获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser == null || loginUser.getId() == null) {
                return new ResponseEnvelope<>(false, "请登录系统！");
            }
            // 修改数据
            int result = shopArticleService.update(update, loginUser);
            if (result > 0) {
                return new ResponseEnvelope<>(true, "修改成功!");
            } else {
                return new ResponseEnvelope<>(false, "修改失败!");
            }
        }catch (Exception e){
            logger.error("修改店铺博文 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }

    @ApiOperation(value = "删除店铺博文", response = ResponseEnvelope.class)
    @DeleteMapping("/remove")
    public ResponseEnvelope removeArticle(@RequestParam("articleId") Long articleId, @RequestParam("shopType") Integer shopType) {
        try {
            // 参数验证
            if (articleId == null || articleId == 0) {
                return new ResponseEnvelope<>(false, "Param is empty！");
            }
            // 获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser == null || loginUser.getId() == null) {
                return new ResponseEnvelope<>(false, "请登录系统！");
            }
            // 删除数据
            int result = shopArticleService.delete(articleId, loginUser, shopType);
            logger.info("删除店铺博文 删除结果result:{}", result);
            if (result > 0) {
                return new ResponseEnvelope<>(true, "删除成功!");
            } else {
                return new ResponseEnvelope<>(false, "删除失败!");
            }
        }catch (Exception e){
            logger.error("删除店铺博文 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }

    }


    @ApiOperation(value = "修改博文发布状态", response = ResponseEnvelope.class)
    @PutMapping("/release")
    public ResponseEnvelope releaseArticle(@Valid @RequestBody CompanyShopArticleReleaseUpdate update, BindingResult validResult) {
        try {
            // 参数验证
            if (validResult.hasErrors()) {
                logger.error("店铺博文发布状态修改：入参 CompanyShopArticleReleaseUpdate：{}",update);
                return processValidError(validResult, new ResponseEnvelope());
            }
            // 获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser == null || loginUser.getId() == null) {
                return new ResponseEnvelope<>(false, "请登录系统！");
            }
            // 修改发布状态
            boolean result = shopArticleService.release(update, loginUser);
            logger.info("店铺博文状态修改 结果result:{}", result);
            if (result) {
                return new ResponseEnvelope<>(true, "博文发布状态修改成功!");
            } else {
                return new ResponseEnvelope<>(false, "博文发布状态修改失败!");
            }
        }catch (Exception e){
            logger.error("店铺博文发布状态修改 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }

    }


    @ApiOperation(value = "店铺博文详情", response = ResponseEnvelope.class)
    @GetMapping("/get")
    public ResponseEnvelope getArticle(@RequestParam("articleId") Long articleId) {
        try{
            // 参数验证
            if (articleId == null || articleId == 0) {
                return new ResponseEnvelope<>(false, "Param is empty！");
            }
            // 获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser == null || loginUser.getId() == null) {
                return new ResponseEnvelope<>(false, "请登录系统！");
            }
            // 查询店铺详情
            CompanyShopArticleDetailVO articleDetailVO = shopArticleService.select(articleId);
            logger.info("获取店铺博文详情 CompanyShopArticleDetailVO:{}",articleDetailVO);
            if (articleDetailVO != null) {
                return new ResponseEnvelope<>(true, articleDetailVO );
            } else {
                return new ResponseEnvelope<>(false, "查询异常!");
            }
        }catch (Exception e){
            logger.error("获取店铺博文详情 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "店铺博文列表", response = ResponseEnvelope.class)
    @PostMapping("/list")
    public ResponseEnvelope articleList(@Valid @RequestBody CompanyShopArticleQuery query, BindingResult validResult) {
        try{
            // 校验参数
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            // 获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser == null || loginUser.getId() == null) {
                return new ResponseEnvelope<>(false, "请登录系统！");
            }
            List<CompanyShopArticleListVO> articleVOList = new ArrayList<>();
            // 查询数量
            int total = shopArticleService.getCount(query);
            logger.info("获取店铺博文列表 博文数量:total:{}",total);
            //处理分页
            PageHelper page = PageHelper.getPage(total, query.getLimit(), query.getPage());
            query.setStart(page.getStart());
            if (total > 0) {
                // 查询列表
                articleVOList = shopArticleService.findList(query);
                logger.info("获取店铺博文列表 查询结果 caseVOList:{}",total);
            }
            return new ResponseEnvelope(true, total, articleVOList);
        }catch (Exception e){
            logger.error("获取店铺博文列表 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }

    @SuppressWarnings("all")
    @ApiOperation(value = "上传店铺博文图片", response = ImgUploadVO.class)
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
        if (StringUtils.isEmpty(articlePicUploadPath)) {
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
            articlePicUploadPath = StringUtil.replaceDate(articlePicUploadPath, null);
            File dir = new File(rootPath + articlePicUploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //路径补全
            String filePath = "";
            StringBuilder builder = new StringBuilder();
            builder.append(articlePicUploadPath);
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
            Map<String, String> map = FileUtils.getMap(picFile, ShopResTypeConstant.SHOP_RES_ARTICLE_PIC_KEY,true);

            //2.新增resPic图片信息表
            Integer resId = resPicService.saveUploadImgPic(map, businessId, loginUser, ShopResTypeConstant.SHOP_RES_ARTICLE_PIC_TYPE);

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




}
