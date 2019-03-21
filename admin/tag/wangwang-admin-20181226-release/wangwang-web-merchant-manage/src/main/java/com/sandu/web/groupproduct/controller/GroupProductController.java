package com.sandu.web.groupproduct.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.sandu.api.groupproducct.input.*;
import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.groupproducct.model.ResFile;
import com.sandu.api.groupproducct.model.bo.GroupProductInfoBO;
import com.sandu.api.groupproducct.model.bo.GroupProductListBO;
import com.sandu.api.groupproducct.model.po.GroupProductUpdatePO;
import com.sandu.api.groupproducct.output.GroupProductListVO;
import com.sandu.api.groupproducct.service.ResFileService;
import com.sandu.api.groupproducct.service.biz.GroupProductBizService;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.input.EditorProductQuery;
import com.sandu.api.product.model.bo.EditorProductListBO;
import com.sandu.api.product.output.EditorProductListVO;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.resmodel.model.bo.ModelListBO;
import com.sandu.api.resmodel.output.ResModelVO;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.user.service.UserService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.constant.Punctuation;
import com.sandu.constant.ResponseEnum;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.platform.model.Platform.*;
import static com.sandu.common.ReturnData.builder;
import static com.sandu.constant.Punctuation.*;
import static com.sandu.constant.ResponseEnum.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-web-merchant-manage
 *
 * @author Sandu
 * @datetime 2018/3/21 11:53
 */

@Api(description = "产品组合", tags = "GroupProduct")
@RestController
@RequestMapping("/v1/group")
@Slf4j
public class GroupProductController extends BaseController {
    @Value("${file.storage.path}")
    private String basePath;
    @Value("${upload.path.format}")
    private String baseFormat;
    @Value("${server.url}")
    private String baseUrl;
    @Resource
    private GroupProductBizService groupProductBizService;
    @Resource
    private PlatformService platformService;
    @Resource
    private ProductBizService productBizService;
    @Resource
    private ResModelService resModelService;
    @Resource
    private ResFileService resFileService;
    @Resource
    private UserService userService;

    @RequiresPermissions({"product:group:view"})
    @ApiOperation(value = "组合详情", response = GroupProductInfoBO.class)
    @GetMapping(value = "/{platformType}/{groupId}/info")
    public Object getProductInfoById(@ApiParam(value = "要查看的产品ID", required = true) @PathVariable Integer groupId,
                                     @ApiParam(value = "所查询的渠道:产品库library线上online/渠道channel", required = true)
                                     @PathVariable String platformType) {
        GroupProductInfoBO groupInfo = groupProductBizService.getGroupInfo(groupId, platformType);
        return builder().code(SUCCESS).data(groupInfo);
    }


    @RequiresPermissions({"product:group:view"})
    @ApiOperation(value = "内容库组合列表", response = GroupProductListVO.class)
    @GetMapping("/library")
    public ReturnData queryGroup(@Valid GroupQuery query, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        PageInfo<GroupProductListBO> groupProductListBOS = groupProductBizService.queryGroup(query, PLATFORM_LIBRARY);
        return setGroupListVo(groupProductListBOS);
    }

    private ReturnData setGroupListVo(PageInfo<GroupProductListBO> groupProductListBOS) {
        log.info("bo list--:{}",groupProductListBOS.getList());
        groupProductListBOS.getList().forEach(
                item->log.info("bo----------:{}",item)
        );
        List<GroupProductListVO> data = groupProductListBOS.getList().stream().map(item -> {
            GroupProductListVO vo = GroupProductListVO.builder().build();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        log.info("vo list:{}",data);
        if (data.isEmpty()) {
            return builder().code(ResponseEnum.NOT_CONTENT).list(data).total(0);
        }
        return builder().code(ResponseEnum.SUCCESS).list(data).total(groupProductListBOS.getTotal());
    }

    @ApiOperation(value = "渠道组合列表", response = GroupProductListVO.class)
    @GetMapping("/channel")
    @RequiresPermissions({"biz:product:group:view"})
    public ReturnData queryGroupChannel(@Valid GroupQuery query, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        List<Integer> list = new ArrayList<>();
        PageInfo<GroupProductListBO> groupProductListBOS = groupProductBizService.queryGroup(query, PLATFORM_CHANNEL);
        return setGroupListVo(groupProductListBOS);
    }

    @ApiOperation(value = "线上组合列表", response = GroupProductListVO.class)
    @GetMapping("/online")
    @RequiresPermissions({"client:product:group:view"})
    public ReturnData queryGroupOnline(@Valid GroupQuery query, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        PageInfo<GroupProductListBO> groupProductListBOS = groupProductBizService.queryGroup(query, PLATFORM_ONLINE);
        return setGroupListVo(groupProductListBOS);
    }

    @ApiOperation("组合分配")
    @PutMapping("allot")
    @RequiresPermissions({"product:group:manage"})
    public ReturnData allotGroup(@Valid @RequestBody GroupAllotUpdate allot, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        if (groupProductBizService.allot(allot.getGroupIds(), allot.getPlatformType())) {
            return builder().code(ResponseEnum.SUCCESS);
        }
        return builder().code(ResponseEnum.ERROR);
    }

    @ApiOperation("渠道组合上架")
    @PutMapping("putAway/channel")
    @RequiresPermissions({"biz:product:group:exhibition"})
    public ReturnData putChannelGroup(@RequestBody @Valid GroupPutUpdate put, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        put.setPlatformType("2b");
        return managePutGroup(put);
    }

    @ApiOperation("线上组合上架")
    @PutMapping("putAway/online")
    @RequiresPermissions({"client:product:group:exhibition"})
    public ReturnData putOnlineGroup(@RequestBody @Valid GroupPutUpdate put, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        put.setPlatformType("2c");
        return managePutGroup(put);
    }

    @ApiOperation("内容库组合上架")
    @PutMapping("putAway")
    @RequiresPermissions({"product:group:exhibition"})
    public ReturnData putLibraryGroup(@RequestBody @Valid GroupPutUpdate put, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }

        if (groupProductBizService.putGroupInLibrary(put)) {
            return builder().code(ResponseEnum.SUCCESS).message("上架成功");
        }
        return builder().code(ResponseEnum.SUCCESS).message("上架失败");
    }

    private ReturnData managePutGroup(@RequestBody @Valid GroupPutUpdate put) {
        boolean flag = true;
        if ("2b".equals(put.getPlatformType())) {
            //渠道上下架
            flag = put.getPutStatus() == 1 ? groupProductBizService.putUp(put.getGroupIds(), put.getPlatformIds(), PLATFORM_CHANNEL)
                    : groupProductBizService.putDown(put.getGroupIds(), put.getPlatformIds(), PLATFORM_CHANNEL);
        }
        if ("2c".equals(put.getPlatformType())) {
            //线上上下架
            List<Integer> platformIds = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList(Platform.BUSINESS_TYPE_2C));
            //上架
            flag = groupProductBizService.putUp(put.getGroupIds(), put.getPlatformIds(), PLATFORM_ONLINE);
            List<String> downIds = platformIds.stream().map(item -> item + "").collect(Collectors.toList());
            List<String> upIds = Arrays.asList(put.getPlatformIds().split(Punctuation.COMMA));
            downIds = downIds.stream().filter(item -> !upIds.contains(item)).collect(Collectors.toList());
            //下架
            flag &= groupProductBizService.putDown(put.getGroupIds(), String.join(Punctuation.COMMA, downIds), PLATFORM_ONLINE);
        }
        return builder().code(ResponseEnum.SUCCESS);
    }


    @ApiOperation("组合编辑")
    @PutMapping("edit/library")
    @RequiresPermissions({"product:group:edit"})
    public ReturnData editProduct(@RequestBody @Valid GroupProductLibraryUpdate update, BindingResult result) {
        if (result.hasErrors()) {
            return builder().code(ERROR);
        }
        GroupProductUpdatePO po = new GroupProductUpdatePO();
        po.setPlatformType(PLATFORM_LIBRARY);
        BeanUtils.copyProperties(update, po);
        return doUpdate(po);
    }


    @ApiOperation("组合线上编辑")
    @PutMapping("edit/manager/online")
    @RequiresPermissions({"client:product:group:edit"})
    public ReturnData editProductOnline(@RequestBody @Valid GroupProductManagerUpdate update, BindingResult result) {
        if (result.hasErrors()) {
            return builder().code(ERROR);
        }
        GroupProductUpdatePO po = new GroupProductUpdatePO();
        po.setPlatformType(PLATFORM_ONLINE);
        BeanUtils.copyProperties(update, po);
        return doUpdate(po);
    }

    @ApiOperation("组合渠道编辑")
    @PutMapping("edit/manager/channel")
    @RequiresPermissions({"biz:product:group:edit"})
    public ReturnData editProductChannel(@RequestBody @Valid GroupProductManagerUpdate update, BindingResult result) {
        if (result.hasErrors()) {
            return builder().code(ERROR);
        }
        GroupProductUpdatePO po = new GroupProductUpdatePO();
        po.setPlatformType(PLATFORM_CHANNEL);
        BeanUtils.copyProperties(update, po);
        return doUpdate(po);
    }

    private ReturnData doUpdate(@RequestBody @Valid GroupProductUpdatePO update) {

        boolean ret = groupProductBizService.updateGroup(update);
        if (ret) {
            return builder().code(ResponseEnum.SUCCESS);
        }
        return builder().code(ResponseEnum.ERROR);
    }

    @ApiOperation(value = "编辑器获取模型资源,多个ID以逗号分隔", response = ResModelVO.class)
    @GetMapping("/editor/getFile")
    public ReturnData getModelInfo(String modelIds) {
        if (StringUtils.isBlank(modelIds)) {
            return builder().code(ResponseEnum.NOT_CONTENT);
        }
        List<Integer> ids = Arrays.stream(modelIds.split(Punctuation.COMMA)).filter(StringUtils::isNoneBlank).map(Integer::parseInt).collect(Collectors.toList());
        List<ModelListBO> list = resModelService.listModelByIds(ids);
        List<ResModelVO> data = list.stream().map(item -> ResModelVO.builder()
                .modelId(item.getId())
                .modelName(item.getModelName())
                .modelPath(baseUrl + item.getModelPath())
                .modelCode(item.getModelCode())
                .concerProductName(item.getConcerProductName())
                .concerProductId(item.getConcerProductId())
                .concerProductCode(item.getConcerProductCode())
                .build()).collect(Collectors.toList());
        if (data.isEmpty()) {
            return builder().code(ResponseEnum.NOT_CONTENT).list(data).total(0);
        }
        return builder().code(ResponseEnum.SUCCESS).list(data);
    }

    @ApiOperation(value = "编辑器产品列表")
    @GetMapping("/editor/product/list")
    public ReturnData getProductListInGroup(@Valid EditorProductQuery editorProductQuery, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder());
        }
        PageInfo<EditorProductListBO> list = productBizService.queryInEditorProductList(editorProductQuery);
        List<EditorProductListVO> data = list.getList().stream().map(item -> {
            EditorProductListVO vo = new EditorProductListVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        if (list.getList().isEmpty()) {
            return builder().code(NOT_CONTENT).message("未查询到相关数据...").list(data).total(0);
        }
        return builder().code(SUCCESS).list(data).total(list.getTotal());
    }

    @ApiOperation(value = "模型编辑器上传组合信息", response = ReturnData.class)
    @PostMapping("/editor/upload")
    public ReturnData addGroupProduct(@RequestPart("file") MultipartFile file, @Valid GroupAdd groupAdd, BindingResult result) {
        if (result.hasErrors()) {
            return processValidError(result, builder().code(ERROR));
        }
        if (!file.getOriginalFilename().endsWith("txt")) {
            return builder().code(ERROR).message("请检查文件格式");
        }
        log.info(file.getSize() + "--------------------------");
        long fileSize = file.getSize();
        log.info("组合信息:" + groupAdd);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH/");
        DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String nowPath = now.format(df);
        String path = "/AA/c_basedesign" + nowPath + "product/groupProduct/file/location/";
        String filePath = basePath + path;
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = "groupLocation" + UNDERLINE + System.currentTimeMillis() + DOT + FilenameUtils.getExtension(file.getOriginalFilename());
        filePath += filename;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //插入组合文件
        String nickName = userService.getUserById(groupAdd.getUserId()).getNickName();
        ResFile res = new ResFile();
        res.setFileName(FilenameUtils.getBaseName(filename));
        res.setFileOriginalName(FilenameUtils.getBaseName(filename));
        res.setFileSize(fileSize + "");
        res.setFileSuffix(FilenameUtils.getExtension(file.getOriginalFilename()));
        res.setGmtCreate(new Date());
        res.setGmtModified(new Date());
        res.setCreator(nickName);
        res.setModifier(nickName);
        res.setFilePath(path + filename);
        res.setFileKey("product.groupProduct.file.location");
        //插入组合记录
        Integer fileId = resFileService.addFile(res);
        GroupProduct group = new GroupProduct();
        group.setCompositeType(groupAdd.getGroupType());
        group.setGroupName(groupAdd.getGroupName());
        Long code = System.currentTimeMillis();
        group.setGroupCode("PG" + code.toString().substring(3));
        group.setGmtCreate(new Date());
        group.setGmtModified(new Date());
        group.setIsDeleted(0);
        group.setCompanyId(groupAdd.getCompanyId());
        group.setState(3);
        group.setBrandId(groupAdd.getBrandId());
        String userNickName = userService.getUserById(groupAdd.getUserId()).getNickName();
        group.setCreator(userNickName);
        group.setModifier(userNickName);
        group.setLocation(fileId.toString());
        Integer groupId = groupProductBizService.addGroup(group, groupAdd.getProductIds(), groupAdd.getMainProductId());
        //更新res_file中的businessId
        res.setBusinessId(groupId);
        resFileService.updateFileById(res);
        return builder().code(SUCCESS).message("新增成功");
    }

    @ApiOperation(value = "删除组合,多个ID以逗号分隔", response = ReturnData.class)
    @ApiImplicitParams(
            @ApiImplicitParam(name = "groupIds", value = "组合ID集,如:123,345", paramType = "query", dataType = "string", required = true)
    )
    @DeleteMapping("/library")
    @RequiresPermissions({"product:group:del"})
    public ReturnData deleteProductById(String groupIds) {
        if (StringUtils.isBlank(groupIds)) {
            return builder().code(ResponseEnum.ERROR).message("参数错误");
        }
        int count = groupProductBizService.deleteGroupByIds(Arrays.stream(groupIds.split(COMMA)).map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList()));
        if (count > 0) {
            return builder().code(ResponseEnum.SUCCESS);
        }
        return builder().code(ResponseEnum.ERROR).message("删除失败...");
    }

    @ApiOperation(value = "公开组合,多个ID以逗号分隔", response = ReturnData.class)
    @RequiresPermissions({"product:group:manage"})
    @PutMapping("/secrecy")
    public ReturnData secrecyProductByIds(@RequestBody @Valid GroupSecrecyUpdate update, BindingResult result) {
        if (result.hasErrors()) {
            return builder().code(ResponseEnum.ERROR).message("参数错误");
        }
        boolean flag = groupProductBizService.updateGroupSecrecyByIds(update.getGroupIds(), update.getSecrecy());
        if (flag) {
            return builder().code(ResponseEnum.SUCCESS);
        }
        return builder().code(ResponseEnum.ERROR).message("删除失败...");
    }
}
