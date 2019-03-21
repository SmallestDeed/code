package com.sandu.web.restexture.controller;


import com.github.pagehelper.PageInfo;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.TransStatus;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.input.ResTextureAdd;
import com.sandu.api.restexture.input.ResTextureQuery;
import com.sandu.api.restexture.input.ResTextureUpdate;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.model.bo.TreeBO;
import com.sandu.api.restexture.output.ResTextureVO;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.api.restexture.service.biz.ResTextureBizService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.service.UserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.LoginContext;
import com.sandu.common.LoginUser;
import com.sandu.common.ReturnData;
import com.sandu.constant.Punctuation;
import com.sandu.util.ModelTransBalanceHelper;
import com.sandu.util.excel.ExcelUtils;
import com.sandu.util.excel.TextureImportBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.constant.ResponseEnum.*;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;

/**
 * @author Sandu
 */
@SuppressWarnings("unchecked")
@RestController
@Slf4j
@Api(tags = "Texture", description = "材质")
@RequestMapping("/v1/texture")
public class ResTextureController extends BaseController {

    @Resource
    private ResTextureService resTextureService;

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private BrandService brandService;

    @Resource
    private ResTextureBizService resTextureBizService;

    @Resource
    private ResModelService resModelService;

    @Resource
    private ResPicService resPicService;

    @Resource
    private UserService userService;

    @Value("${file.storage.path}")
    private String basePath;

    @Autowired
    private ModelTransBalanceHelper modelTransBalanceHelper;

    @GetMapping("/textures")
    @RequiresPermissions({"texture:view"})
    @ApiOperation(value = "列表材质的select", response = TreeBO.class)
    public ReturnData textures() {
        List<TreeBO> trees = resTextureService.listTextures();
        return ReturnData.builder().code(SUCCESS).data(trees);
    }

    @ApiOperation(value = "添加")
    @PostMapping
    @RequiresPermissions({"texture:add"})
    public ReturnData addResTexture(@Valid @RequestBody ResTextureAdd resTextureNew, BindingResult br) {
        ReturnData data = ReturnData.builder();
        if (br.hasErrors()) {
            return processValidError(br, data);
        }
        //新增材质球贴图
        if (StringUtils.isNoneBlank(resTextureNew.getTextureBallPath())) {
            resTextureNew.setTextureballFileId(insertTextureBall(resTextureNew.getTextureBallPath(),
                    resTextureNew.getCompanyId(), resTextureNew.getUserId()).intValue());
        }
        resTextureService.addResTexture(resTextureNew);
        return ReturnData.builder().code(CREATED);
    }

    private Long insertTextureBall(String textureBallPath, Integer companyId, Integer userId) {
        String transStatus = textureBallPath.endsWith("assetbundle".trim()) ? TransStatus.SUCCESS.getCode() : TransStatus.NONE.getCode();
        return resModelService.addResModel(ResModelAdd.builder()
                .modelPath(textureBallPath)
                .companyId(companyId)
                .userId(userId)
                .modelName("MCT" + System.currentTimeMillis())
                .fileKey(ResModel.FILE_KEY_TEXTURE_BALL)
                .transStatus(transStatus)
                .transMachineIp(modelTransBalanceHelper.getMachineIp(ModelTransBalanceHelper.WRITE_TRANS_ACTION))
                .build());
    }

    @ApiOperation(value = "修改", response = ReturnData.class)
    @RequiresPermissions({"texture:edit"})
    @PutMapping
    public ReturnData saveResTexture(@Valid @RequestBody ResTextureUpdate resTextureUpdate, BindingResult br) {
        ReturnData data = ReturnData.builder();
        if (br.hasErrors()) {
            return processValidError(br, data);
        }
        if (resTextureUpdate.getTextureballFileId() == null &&
                StringUtils.isNoneBlank(resTextureUpdate.getTextureBallPath())) {
            resTextureUpdate.setTextureballFileId(insertTextureBall(resTextureUpdate.getTextureBallPath(),
                    resTextureUpdate.getCompanyId(), resTextureUpdate.getUserId()).intValue());
        }
        resTextureService.saveResTexture(resTextureUpdate);
        return ReturnData.builder().code(SUCCESS);
    }

    @ApiOperation(value = "删除")
    @RequiresPermissions({"texture:del"})
    @DeleteMapping
    public ReturnData deleteResTexture(@RequestParam("ids") String ids) {
        //单个删除增加判断逻辑
        String[] strIds = ids.split(COMMA);
        if (strIds.length == 1) {
            if (StringUtils.isNotBlank(strIds[0])) {
                ResTexture resTexture = resTextureService.getResTextureDetail(Long.parseLong(strIds[0]));
                if (resTexture == null) {
                    return ReturnData.builder().code(NOT_FOUND).success(false).message("删除的材质不存在,不能删除!");
                }

                if (resTexture.getIsDeleted() != null && resTexture.getIsDeleted() == 1) {
                    return ReturnData.builder().code(NOT_CONTENT).success(false).message("材质已删除,不能重复删除!");
                }
            }

        }
        Arrays.stream(ids.split(COMMA)).filter(StringUtils::isNoneBlank).forEach(id -> {
                    ResTexture resTexture = resTextureService.getResTextureDetail(Long.parseLong(id));
                    if (resTexture != null && resTexture.getIsDeleted() != null && resTexture.getIsDeleted() == 0) {
                        resTextureService.deleteResTexture(Long.parseLong(id));
                    }
                }
        );
        return ReturnData.builder().code(SUCCESS);
    }

    @ApiOperation(value = "根据id获取", response = ResTextureVO.class)
    @RequiresPermissions({"texture:view"})
    @GetMapping("/{textureId}")
    public ReturnData getResTextureDetail(@PathVariable long textureId) {
        ResTextureVO info = resTextureBizService.getResTextureDetail(textureId);
        if (isNotEmpty(info)) {
            return ReturnData.builder().code(SUCCESS).data(info);
        }
        return ReturnData.builder().code(NOT_FOUND).success(false);
    }

    @ApiOperation(value = "分页", response = ResTextureVO.class)
    @RequiresPermissions({"texture:view"})
    @GetMapping("/list")
    public ReturnData listResTexture(ResTextureQuery query) {

        PageInfo<ResTextureVO> data = resTextureBizService.queryResTextureList(query);
        if (isEmpty(data.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有消息").list(Collections.emptyList()).total(0);
        }
        return ReturnData.builder().list(data.getList()).total(data.getTotal()).code(SUCCESS);
    }

    @ApiOperation("excel批量导入材质")
    @PostMapping("/import")
//    @RequiresPermissions({"texture:add"})
    public ReturnData batchImportTextures(@RequestPart MultipartFile file) {
        if (file == null ||
                !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(Punctuation.DOT) + 1).equalsIgnoreCase("XLSX")
        ) {
            return ReturnData.builder().success(false).code(PARAM_ERROR).message("请上传xlsx格式文件");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        log.info("user info,{}", user);
//        LoginUser user = new LoginUser();
//        user.setId(1123);
//        user.setBusinessAdministrationId(1231L);
        try {
            ExcelUtils<TextureImportBean> utils = new ExcelUtils(TextureImportBean.class);
            List<TextureImportBean> beans = utils.readExcelToModel(file.getInputStream(), 0, basePath, "/AA/c_basedesign/texture_pic");
            List<Dictionary> textures = dictionaryService.listByType("texture");
            List<Dictionary> textureAttrs = dictionaryService.listByType("textureAttr");

            List<Brand> brands = brandService.getBrandByCompanyId(user.getBusinessAdministrationId().intValue());
            log.info("导入材质数量:{}", beans.size());
            List<ResTextureAdd> adds = beans.stream().map(
                    it -> {
                        Long picId = 0L;
                        Long picIdParam = 0L;
                        ResTextureAdd add = initTextureAdd(textures, textureAttrs, brands, it, picId, picIdParam, user.getId());
                        add.setCompanyId(user.getBusinessAdministrationId().intValue());
                        return add;
                    }
            ).filter(it -> it.getCompanyId() != null).collect(Collectors.toList());
            log.info("start insert textures, time:{}", LocalDateTime.now());
            resTextureService.importTexturesFromExcel(adds);
            log.info("end insert textures, time:{}", LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.builder().success(false).code(PARAM_ERROR).message(e.getMessage());
        }
        return ReturnData.builder().success(true).code(SUCCESS);

    }

    private ResTextureAdd initTextureAdd(
            List<Dictionary> textures,
            List<Dictionary> textureAttrs,
            List<Brand> brands,
            TextureImportBean it,
            Long picId,
            Long picIdParam, Integer userId) {
        ResTextureAdd add = ResTextureAdd.builder()
                .textureName(it.getName())
//                .brandId()
//                .textureAttrValue()
//                .texture()
//                .companyId()
                .fileLength(it.getLength() + "")
                .fileWidth(it.getWidth() + "")
                .modelNumber(it.getTextureNumber())
                .normalPicPath(it.getTextureParamPicPath())
                .normalPicId(picIdParam.intValue())
                .normalParam(it.getTextureParamNum().toString())
                .thumbnailPath(it.getTexturePicPath())
                .thumbnailId(picId.intValue())
                .remark(it.getRemark())
                .userId(userId)
                .build();
        brands.stream().filter(b -> b.getBrandName().trim().equals(it.getBrandName().trim())).findFirst()
                .ifPresent(b -> {
                    add.setBrandId(b.getId().intValue());
                    add.setCompanyId(b.getCompanyId());
                });
        textures.stream().filter(t -> t.getName().equals(it.getTexture())).findFirst()
                .ifPresent(t -> add.setTexture(t.getValue().toString()));
        textureAttrs.stream().filter(t -> t.getName().equals(it.getTextureAttrValue())).findFirst()
                .ifPresent(t -> add.setTextureAttrValue(t.getValue()));
        return add;
    }


}

