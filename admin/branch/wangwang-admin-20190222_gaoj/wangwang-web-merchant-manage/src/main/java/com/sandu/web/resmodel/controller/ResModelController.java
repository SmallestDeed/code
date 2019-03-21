package com.sandu.web.resmodel.controller;

import static com.sandu.api.resmodel.input.ModelAreaRelAdd.MODEL_TYPE_BALL;
import static com.sandu.api.resmodel.input.ModelAreaRelAdd.MODEL_TYPE_MODEL;
import static com.sandu.config.ResPropertiesConstance.PRODUCT_BASE_PRODUCT_U3DMODEL_WINDOWS_PC;
import static com.sandu.config.ResPropertiesConstance.SYSTEM_RES_TEXTURE_TEXTURE_BALL_FILE;
import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.constant.Punctuation.EMPTY;
import static com.sandu.constant.Punctuation.UNDERLINE;
import static com.sandu.constant.ResponseEnum.CREATED;
import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.NOT_FOUND;
import static com.sandu.constant.ResponseEnum.SUCCESS;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;
import static com.sandu.util.ModelTransBalanceHelper.READ_TRANS_ACTION;
import static com.sandu.util.ModelTransBalanceHelper.WRITE_TRANS_ACTION;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.resmodel.input.ModelAreaRelAdd;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.input.ResModelQuery;
import com.sandu.api.resmodel.input.ResModelUpdate;
import com.sandu.api.resmodel.model.ModelAreaRel;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.TransStatus;
import com.sandu.api.resmodel.model.bo.ModelAreaBO;
import com.sandu.api.resmodel.model.bo.ModelBO;
import com.sandu.api.resmodel.model.bo.ModelDeliverPO;
import com.sandu.api.resmodel.model.bo.ModelListBO;
import com.sandu.api.resmodel.output.FileVO;
import com.sandu.api.resmodel.output.ResModelInfoVO;
import com.sandu.api.resmodel.output.ResModelVO;
import com.sandu.api.resmodel.service.ModelAreaRelService;
import com.sandu.api.resmodel.service.ResModelBizService;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.input.ResTextureAdd;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.commons.FileEncrypt;
import com.sandu.constant.Punctuation;
import com.sandu.constant.ResponseEnum;
import com.sandu.interceptor.CancelCheckToken;
import com.sandu.util.HttpUtils;
import com.sandu.util.JsonParser;
import com.sandu.util.ModelTransBalanceHelper;
import com.sandu.util.NetworkUtil;
import com.sandu.util.Randomer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandu
 */
@Slf4j
@SuppressWarnings("unchecked")
@RestController
@Api(tags = "Model", description = "模型")
@RequestMapping("/v1/model")
public class ResModelController extends BaseController {

    @Value("${file.storage.path}")
    private String basePath;
    @Value("${file.storage.origin.path}")
    private String sourceBasePath;
    @Value("${upload.path.format}")
    private String baseFormat;
    @Value("${server.url}")
    private String baseUrl;
    @Value("${service.modelTool.url}")
    private String modelToolUrl;
    @Value("${service.modelTool.dictionary.valueKey}")
    private String modelToolValueKey;
    @Value("${spring.http.assetbundle.maxFileSize}")
    private String assetbundleSize;
    @Value("${spring.http.3du.maxFileSize}")
    private String threeDuSize;
    


    @Resource(name = "resPropertiesMap")
    private Map<String, String> fileKey2Path;
    @Autowired
    private ModelTransBalanceHelper modelTransBalanceHelper;
    @Resource
    private ResModelService resModelService;
    @Resource
    private ResModelBizService resModelBizService;
    @Resource
    private ResTextureService resTextureService;
    @Resource
    private ModelAreaRelService modelAreaRelService;
    @Resource
    private ResPicService resPicService;
    @Resource
    private DictionaryService dictionaryService;
    @Autowired
    private HttpUtils httpUtils;


    @ApiOperation(value = "页面模型文件上传", response = ReturnData.class)
    @ApiImplicitParam(name = "modelType", value = "材质球:ball / 模型:model", paramType = "query", dataType = "string", required = true)
    @PostMapping("/biz/upload/{modelType}")
	public ReturnData bizUpload(@RequestPart MultipartFile file, @PathVariable String modelType) {
        //材质球上传/模型上传
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String path = "";
        if (MODEL_TYPE_MODEL.equals(modelType)) {
            path = this.getPathByFileKey(PRODUCT_BASE_PRODUCT_U3DMODEL_WINDOWS_PC, now);
        } else if (MODEL_TYPE_BALL.equals(modelType)) {
            path = this.getPathByFileKey(SYSTEM_RES_TEXTURE_TEXTURE_BALL_FILE, now);
        } else {
            return ReturnData.builder().code(ERROR).message("模型类型错误!");
        }

        String diskPath = basePath + path;


        String diskSourcePath = diskPath.replace(basePath, sourceBasePath);

        mkDir(diskPath);

        String filename = Randomer.randomNum() + UNDERLINE + now.format(ddf) + DOT + FilenameUtils.getExtension(file.getOriginalFilename());
        diskPath += filename;
        //加密assetbundle文件
        if (filename.trim().endsWith(".assetbundle")) {
            try {
                //判断文件大小
                if (file.getSize() > Integer.parseInt(assetbundleSize)) {
                    return ReturnData.builder().code(ERROR).message("assetbundle文件大小不能超过25M!");
                }
                //加密文件
                log.info("保存加密文件,location:{}", diskPath);
                FileEncrypt.addRedundance(diskPath, file.getInputStream());
                //保存源文件
                log.info("保存源文件,location:{}", diskSourcePath + filename);
                mkDir(diskSourcePath);
                file.transferTo(new File(diskSourcePath + filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //3du文件
            if (file.getSize() > Integer.parseInt(threeDuSize)) {
                return ReturnData.builder().code(ERROR).message("3DU文件大小不能超过50M!");
            }
            try {
                file.transferTo(new File(diskPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileVO vo = new FileVO();
        vo.setUrl(baseUrl + path + filename);
        return ReturnData.builder().code(ResponseEnum.SUCCESS).data(vo);
    }

    private void mkDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @ApiOperation(value = "模型编辑器上传模型信息", response = ReturnData.class)
    @PostMapping("")
    public ReturnData addResModel(@RequestPart("file") MultipartFile[] file, @RequestParam("modelInfo") String modelInfo,
                                  @RequestParam("companyId") Integer companyId, @RequestParam("userId") Integer userId,
                                  @RequestParam("modelType") String modelType) {
        log.info("模型信息:" + modelInfo);
        ModelAreaRelAdd info = JsonParser.fromJson(modelInfo, ModelAreaRelAdd.class);
        StringBuilder modelFileName = new StringBuilder();
        //保存模型及默认材质文件,设置区域与之对应的默认材质信息
        Arrays.stream(file).forEach(item -> modelFileName.append(saveFile(item, info)));
        //保存单个模型或者材质球记录
        ResModelAdd resModel = this.saveModelOrBall(companyId, userId, modelType, info, modelFileName);
        resModel.setTransMachineIp(modelTransBalanceHelper.getMachineIp(WRITE_TRANS_ACTION));
        Long id = resModelService.addResModel(resModel);
        if (ModelAreaRelAdd.MODEL_TYPE_BALL.equals(modelType)) {
            //关联相关贴图信息
            log.debug("resModelInfo : {}", resModel);
            //材质球---
            resTextureService.addResTexture(ResTextureAdd.builder()
                    .textureballFileId(id.intValue())
                    .companyId(companyId)
                    .textureName(info.getModelName())
                    .thumbnailId(info.getPicId())
                    .thumbnailPath(info.getThumbPicPath())
                    .fileLength(StringUtils.isBlank(info.getLength() + "") ? "0" : info.getLength() + "")
                    .fileWidth(StringUtils.isBlank(info.getWidth() + "") ? "0" : info.getWidth() + "")
                    .build());
        }
        if (MODEL_TYPE_MODEL.equals(modelType)) {
            //产品模型
            List<ModelAreaRel> list = new ArrayList<>();
            //设置区域信息
            info.getAreas().forEach(item -> {
                ModelAreaRel tmp = new ModelAreaRel();
                tmp.setName(item.getName());
                tmp.setModelId(id.intValue());
                tmp.setCode(item.getCode());
                tmp.setGmtCreate(new Date());
                tmp.setGmtModified(new Date());
                tmp.setIsDeleted((byte) 0);
                tmp.setCreator(userId.toString());
                tmp.setHeight(item.getHeight());
                tmp.setWidth(item.getWidth());
                list.add(tmp);
            });
            //设置区域默认材质信息
            List<ResModelAdd> balls = info.getAreas().stream().map(item -> {
                ResModelAdd ballAdd = new ResModelAdd();
                if (StringUtils.isNoneBlank(item.getTexturePicId())) {
                    ballAdd.setBall2TexturePicId(Integer.parseInt(item.getTexturePicId()));
                }
                ballAdd.setModelName(info.getModelName() + "-" + item.getName());
                ballAdd.setModelPath(item.getTexturePath());
                ballAdd.setTransStatus(TransStatus.NONE.getCode());
                ballAdd.setCompanyId(companyId);
                ballAdd.setUserId(userId);
                ballAdd.setThumbPicPath(item.getTexturePicPath());
                ballAdd.setWidth(item.getWidth());
                ballAdd.setHeight(item.getHeight());
                ballAdd.setAreaCode(item.getCode());
                ballAdd.setFileKey(ResModel.FILE_KEY_TEXTURE_BALL);
                ballAdd.setTransMachineIp(modelTransBalanceHelper.getMachineIp(WRITE_TRANS_ACTION));
                return ballAdd;
            }).collect(toList());
            //保存材质区域及区域默认材质
            resModelBizService.insetModelAreaRelAndDefaultTextureWithBalls(list, balls);
        }
        return ReturnData.builder().code(CREATED).data(id);
    }
    
   private Map<String,Object> checkResModel(Integer companyId,String modelCode) {
	    Map<String,Object> resultMap = new HashMap<String,Object>();
	    if(companyId == null) {
	    	resultMap.put("fail","companyId参数不能为空!");
	    	return resultMap;
	   	}
	   	if(StringUtils.isEmpty(modelCode)) {
	   		resultMap.put("fail","modelCode模型编码不能为空!");
	    	return resultMap;
	   	}
	   	ResModel model = resModelBizService.queryModelByCode(companyId,modelCode);
	   	if(model == null) {
	   		resultMap.put("fail","模型编码【"+modelCode+"】未查询到模型信息,请核实模型编码是否正确!");
	    	return resultMap;
	   	}
	   	if(StringUtils.isEmpty(model.getFile3duPath())) {
	   		resultMap.put("fail","模型编码【"+modelCode+"】模型文件路径异常!");
	    	return resultMap;
	    }
	   	resultMap.put("success", model);
	   	return resultMap;
   }
   
    
    @ApiOperation(value = "模型编辑器修改上传模型", response = ReturnData.class)
    @PostMapping("/updateResModel")
    public ReturnData updateResModel(
    		 					  @RequestPart("file") MultipartFile[] file, 
    							  @RequestParam("modelInfo") String modelInfo,
                                  @RequestParam("companyId") Integer companyId, 
                                  @RequestParam("userId") Integer userId,
                                  @RequestParam("modelType") String modelType,
                                  @RequestParam("modelCode") String modelCode) {
        log.info("模型信息:" + modelInfo);
        Map<String,Object> resultMap = checkResModel(companyId,modelCode);
    	ResModel model = null;
    	if(resultMap != null) {
    		if(resultMap.containsKey("fail")) {
    	        return ReturnData.builder().code(ERROR).data(resultMap.get("fail"));
    		}
			model = (ResModel) resultMap.get("success");
			if(model == null) {
				return ReturnData.builder().code(ERROR).data("获取模型文件失败!");
			}
    	}else {
    		return ReturnData.builder().code(ERROR).data("模型校验失败");
    	}
        ModelAreaRelAdd info = JsonParser.fromJson(modelInfo, ModelAreaRelAdd.class);
        StringBuilder modelFileName = new StringBuilder();
        //保存模型及默认材质文件,设置区域与之对应的默认材质信息
        Arrays.stream(file).forEach(item -> modelFileName.append(saveFile(item, info)));
        //修改单个模型或者材质球记录
        ResModel resModel = this.updateModelOrBall(model.getId(),userId, info, modelFileName);
        resModel.setTransMachineIp(modelTransBalanceHelper.getMachineIp(WRITE_TRANS_ACTION));
        resModelService.updateByPrimaryKeySelective(resModel);
        if (MODEL_TYPE_MODEL.equals(modelType)) {
            //产品模型
            List<ModelAreaRel> list = new ArrayList<>();
            //设置区域信息
            info.getAreas().forEach(item -> {
                ModelAreaRel tmp = new ModelAreaRel();
                tmp.setName(item.getName());
                tmp.setModelId(resModel.getId().intValue());
                tmp.setCode(item.getCode());
                tmp.setGmtCreate(new Date());
                tmp.setGmtModified(new Date());
                tmp.setIsDeleted((byte) 0);
                tmp.setCreator(userId.toString());
                tmp.setHeight(item.getHeight());
                tmp.setWidth(item.getWidth());
                list.add(tmp);
            });
            //设置区域默认材质信息
            List<ResModelAdd> balls = info.getAreas().stream().map(item -> {
                ResModelAdd ballAdd = new ResModelAdd();
                if (StringUtils.isNoneBlank(item.getTexturePicId())) {
                    ballAdd.setBall2TexturePicId(Integer.parseInt(item.getTexturePicId()));
                }
                ballAdd.setModelName(info.getModelName() + "-" + item.getName());
                ballAdd.setModelPath(item.getTexturePath());
                ballAdd.setTransStatus(TransStatus.NONE.getCode());
                ballAdd.setCompanyId(companyId);
                ballAdd.setUserId(userId);
                ballAdd.setThumbPicPath(item.getTexturePicPath());
                ballAdd.setWidth(item.getWidth());
                ballAdd.setHeight(item.getHeight());
                ballAdd.setAreaCode(item.getCode());
                ballAdd.setFileKey(ResModel.FILE_KEY_TEXTURE_BALL);
                ballAdd.setTransMachineIp(modelTransBalanceHelper.getMachineIp(WRITE_TRANS_ACTION));
                return ballAdd;
            }).collect(toList());
            //保存材质区域及区域默认材质
            resModelBizService.updateModelAreaRelAndTexture(list, balls);
        }
        return ReturnData.builder().code(SUCCESS).data(model.getId());
    }
    
    
    @ApiOperation(value = "通过模型编码获取模型下载路径", response = ReturnData.class)
    @GetMapping("/getModelInfoByCode")
    public ReturnData getModelInfoByCode(
    							  @RequestParam("companyId") Integer companyId, @RequestParam("userId") Integer userId,
                                  @RequestParam("modelCode") String modelCode) {
    	StringBuffer filePath = new StringBuffer();
    	log.info("companyId{},modelCode{}",companyId,modelCode);
    	Map<String,Object> resultMap = checkResModel(companyId,modelCode);
    	if(resultMap != null) {
    		if(resultMap.containsKey("fail")) {
    	        return ReturnData.builder().code(ERROR).data(resultMap.get("fail"));
    		}
			ResModel model = (ResModel) resultMap.get("success");
			if(model != null) {
				//获取模型区域信息
				ResModelInfoVO info = new ResModelInfoVO();
		        List<ModelAreaRel> reList =  modelAreaRelService.listByModelId(model.getId().intValue());
		        if(reList!=null && reList.size() > 0) {
		        	String codes =  reList.stream()
		        									.sorted((a, b)-> Integer.parseInt(a.getCode())-Integer.parseInt(b.getCode()))
		        									.map(ModelAreaRel::getCode)
		        									.collect(Collectors.joining(","));
		        	info.setCodes(codes);
		        }
				filePath.append(baseUrl).append(model.getFile3duPath());
				info.setModelPath(filePath.toString());
				info.setReList(reList);
				return ReturnData.builder().code(SUCCESS).data(info);
			}else {
				return ReturnData.builder().code(ERROR).data("获取模型文件失败!");
			}
    	}else {
    		return ReturnData.builder().code(ERROR).data("模型校验失败");
    	}
    }
    

    private ResModelAdd saveModelOrBall(@RequestParam("companyId") Integer companyId, @RequestParam("userId") Integer userId,
                                        @RequestParam("modelType") String modelType,
                                        ModelAreaRelAdd info,
                                        StringBuilder modelFileName) {
        ResModelAdd resModel = new ResModelAdd();
        resModel.setModelName(info.getModelName());
        resModel.setModelPath(modelFileName.toString());
        resModel.setModelModelNum(info.getModelNum());
        resModel.setTransStatus(TransStatus.NONE.getCode());
        resModel.setCompanyId(companyId);
        resModel.setUserId(userId);
        resModel.setThumbPicPath(info.getThumbPicPath());
        resModel.setCategoryIds(info.getCategoryIds());
        resModel.setLength(info.getLength());
        resModel.setWidth(info.getWidth());
        resModel.setHeight(info.getHeight());
        resModel.setSmallType(Strings.nullToEmpty(info.getSmallType()));
        resModel.setFileKey(ResModel.FILE_KEY_MODEL);
        if (ModelAreaRelAdd.MODEL_TYPE_BALL.equals(modelType)) {
            resModel.setFileKey(ResModel.FILE_KEY_TEXTURE_BALL);
        }
        resModel.setMainModelFlag(info.getMainModelFlag());
        return resModel;
    }
    
    private ResModel updateModelOrBall(Long modelId, Integer userId,
    		ModelAreaRelAdd info,
            StringBuilder modelFileName) {
	    	ResModel resModel = new ResModel();
			resModel.setModelModelNum(info.getModelNum());
			resModel.setFile3duPath(modelFileName.toString());
	        resModel.setThumbPicPath(info.getThumbPicPath());
			resModel.setMainModelFlag(info.getMainModelFlag());
		    resModel.setLength(info.getLength());
	        resModel.setWidth(info.getWidth());
	        resModel.setHeight(info.getHeight());
			resModel.setId(modelId);
	        resModel.setTransStatus(TransStatus.NONE.getCode());
			resModel.setModifier(userId+"");
			resModel.setGmtModified(new Date());
			return resModel;
		}
    

    @ApiOperation(value = "添加模型", response = ReturnData.class)
    @PostMapping("/biz")
    //@RequiresPermissions({"model:add"})
    public ReturnData addResModel(@Valid @RequestBody ResModelAdd resModelAdd, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        resModelAdd.setFileKey(ResModel.FILE_KEY_MODEL);
        if (Strings.nullToEmpty(resModelAdd.getModelPath()).toLowerCase().endsWith("assetbundle")) {
            resModelAdd.setTransStatus(TransStatus.SUCCESS.getCode());
        } else {
            resModelAdd.setTransStatus(TransStatus.NONE.getCode());
            resModelAdd.setTransMachineIp(modelTransBalanceHelper.getMachineIp(WRITE_TRANS_ACTION));
        }
        resModelBizService.addModel(resModelAdd);
        return ReturnData.builder().code(CREATED);
    }


    @ApiOperation(value = "修改模型", response = ReturnData.class)
    @PutMapping
    //@RequiresPermissions({"model:edit"})
    public ReturnData saveResModel(@Valid @RequestBody ResModelUpdate resModelUpdate, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        ResModel detail = resModelService.getResModelDetail(resModelUpdate.getModelId());
        if (!Strings.nullToEmpty(resModelUpdate.getModelPath()).equals(detail.getFile3duPath())) {
            //更新模型文件
            if (Strings.nullToEmpty(resModelUpdate.getModelPath()).toLowerCase().endsWith("assetbundle")) {
                resModelUpdate.setTransStatus(TransStatus.SUCCESS.getCode());
            } else {
                resModelUpdate.setTransStatus(TransStatus.NONE.getCode());
                resModelUpdate.setTransMachineIp(modelTransBalanceHelper.getMachineIp(WRITE_TRANS_ACTION));

            }
        }
        resModelBizService.updateModel(resModelUpdate);
        return ReturnData.builder().code(SUCCESS);
    }

    @ApiOperation(value = "删除模型")
    @DeleteMapping()
    //@RequiresPermissions({"model:del"})
    public ReturnData deleteResModel(@RequestParam String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ReturnData.builder().code(ERROR).message("请选择需要删除的模型数据");
        }
        String[] modelIds = ids.split(COMMA);
        if (modelIds.length == 1) {
            ResModel model = resModelService.getResModelDetail(Long.parseLong(modelIds[0]));
            if (model == null) {
                return ReturnData.builder().code(NOT_FOUND).message("删除的模型信息不存在!");
            }
            if (model.getIsDeleted() == 1) {
                return ReturnData.builder().code(ERROR).message("模型已删除,不能重复操作!");
            }
            resModelService.deleteResModel(Long.parseLong(modelIds[0]));
        } else {
            Arrays.stream(ids.split(COMMA)).forEach(id -> {
                ResModel model = resModelService.getResModelDetail(Long.parseLong(id));
                if (model != null && model.getIsDeleted() == 0) {
                    resModelService.deleteResModel(Long.parseLong(id));
                }
            });
        }
        return ReturnData.builder().code(SUCCESS);
    }

    @ApiOperation(value = "根据id获取", response = ResModelVO.class)
    @GetMapping("/{modelId}")
    //@RequiresPermissions({"model:view"})
    public ReturnData getResModel(@PathVariable long modelId) {
        ModelBO resModel = resModelBizService.modelDetails(modelId);

        if (isNotEmpty(resModel)) {
            return ReturnData.builder().success(true)
                    .data(
                            ResModelVO
                                    .builder()
                                    .modelId(resModel.getId())
                                    .author(resModel.getCreator())
                                    .createDate(resModel.getGmtCreate())
                                    .height(resModel.getHeight())
                                    .width(resModel.getWidth())
                                    .length(resModel.getLength())
                                    .modelCode(resModel.getModelCode())
                                    .modelDesc(resModel.getModelDesc())
                                    .modelFileName(resModel.getModelFileName())
                                    .modelName(resModel.getModelName())
                                    .modelPath(resModel.getModelPath())
                                    .thumbPicPath(resModel.getThumbPicPath())
                                    .transStatus(resModel.getTransStatus())
                                    .updateDate(resModel.getGmtModified())
                                    .modelModelNum(resModel.getModelModelNum())
                                    .categoryIds(resModel.getCategoryIds())
                                    .categoryNames(resModel.getCategoryNames())
                                    .file3duPath(resModel.getFile3duPath())
                                    .smallType(resModel.getSmallTypeMark())
                                    .type(resModel.getModelType())
                                    .typeNames(resModel.getTypeNames())
                                    .modelTextureInfo(resModel.getModelTextureInfo())
                                    .build());
        }
        return ReturnData.builder().code(NOT_FOUND).success(false);
    }

    @ApiOperation(value = "分页", response = ResModelVO.class)
    @GetMapping("/list")
    //@RequiresPermissions({"model:view"})
    public ReturnData listResModel(@Valid ResModelQuery query, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        if (Math.abs(query.getIsUsed()) > 1) {
            return ReturnData.builder().code(ERROR).message("参数错误: isUsed异常");
        }
        if (query.getUsedModelIds() != null) {
            if (query.getExistModelIds() == null) {
                query.setExistModelIds(Collections.emptyList());
            }
            List<Integer> usedIds = query.getUsedModelIds().stream().filter(usedId -> !query.getExistModelIds().contains(usedId)).collect(toList());
            query.setUsedModelIds(usedIds);
        }
        PageInfo<ModelListBO> datas = resModelBizService.queryModelList(query);
        if (isEmpty(datas.getList())) {
            return ReturnData.builder().code(NOT_CONTENT).message("没有数据了").list(Collections.emptyList()).total(0);
        }
        List<ResModelVO> resModelVOS = datas.getList().stream().map(
                resModel -> ResModelVO
                        .builder()
                        .modelId(resModel.getId())
                        .modelCode(resModel.getModelCode())
                        .modelDesc(resModel.getModelDesc())
                        .modelName(resModel.getModelName())
                        .thumbPicPath(resModel.getThumbPicPath())
                        .transStatus(resModel.getTransStatus())
                        .updateDate(resModel.getGmtModified())
                        .categoryIds(resModel.getCategoryIds())
                        .categoryNames(resModel.getCategoryNames())
                        .concerProductId(resModel.getConcerProductId())
                        .concerProductName(resModel.getConcerProductName())
                        .author(resModel.getAuthor())
                        .modelOrigin(resModel.getModelOrigin())
                        .modelModelNum(resModel.getModelModelNum())
                        .width(resModel.getWidth())
                        .length(resModel.getLength())
                        .height(resModel.getHeight())
                        .modelTextureInfo(resModel.getModelTextureInfo())
                        .textureCount(resModel.getTextureCount())
                        .mainModelFlag(resModel.getMainModelFlag())
                        .build()).collect(toList());

        return ReturnData.builder().list(resModelVOS).total(datas.getTotal()).code(SUCCESS).message("成功");
    }


    @ApiOperation(value = "模型-贴图详情", response = ReturnData.class)
    @GetMapping("/area/info")
    public ReturnData showModelAreaInfo(long modelId) {
        List<ModelAreaBO> modelAreaBOS = resModelBizService.showModelTextureInfo(modelId);
        return ReturnData.builder().code(SUCCESS).data(modelAreaBOS);
    }

    @GetMapping("/queue")
    @CancelCheckToken
    @ApiOperation(value = "获取没有转化处理的模型")
    public ReturnData<FileVO> queue() {
        ResModel resModel = resModelService.getQueue(modelTransBalanceHelper.getMachineIp(READ_TRANS_ACTION));
        if (isEmpty(resModel)) {
            return ReturnData.builder().code(ResponseEnum.NOT_CONTENT);
        }
        return ReturnData.builder()
                .code(ResponseEnum.SUCCESS)
                .data(
                        FileVO.builder()
                                .url(baseUrl + getBaseMapping() + resModel.getFile3duPath())
                                .modelId(resModel.getId())
                                .build()
                );
    }

    @PostMapping("/trans/fail")
    @ApiOperation(value = "转化失败后调用接口")
    @CancelCheckToken
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "同过 queue获取到的res_id ", paramType = "query", dataType = "string", required = true),
    })
    public ReturnData transFail(@RequestParam Long modelId) {
        updateModel(modelId, null, TransStatus.FAIL.getCode());
        return ReturnData.builder().code(ResponseEnum.SUCCESS);
    }

    @ApiOperation(value = "转化后模型上传", response = FileVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "同过 queue获取到的模型id ", paramType = "query", dataType = "string", required = true),
    })
    @PostMapping("/upload")
    public ReturnData<FileVO> u3dUpload(
            @RequestPart("file") MultipartFile file,
            @RequestParam Long modelId) {
        if (file == null) {
            return ReturnData.builder().code(ResponseEnum.PARAM_ERROR).data("参数错误,文件为必填!");
        } else {
            log.info("====================filename文件名称:" + file.getOriginalFilename());
            log.info("====================modelId:" + modelId);
            if (!Strings.nullToEmpty(file.getOriginalFilename()).endsWith("assetbundle")) {
                return ReturnData.builder().code(ResponseEnum.FORBIDDEN).data("文件类型错误,只能是assetbundle!");
            }

            ResModel detail = resModelService.getResModelDetail(modelId);
            if (detail == null) {
                return ReturnData.builder().code(ResponseEnum.FORBIDDEN).data("未找到相关模型");
            }

            String filename = this.saveFileWithFileKey(file, detail.getFileKey());

            /* 修改数据库*/
            updateModel(modelId, filename, TransStatus.SUCCESS.getCode());
            FileVO vo = new FileVO();
            vo.setModelId(modelId);
            vo.setUrl(baseUrl + getBaseMapping() + filename);
            return ReturnData.builder().code(ResponseEnum.SUCCESS).data(vo);
        }
    }

    private void updateModel(Long modelId, String path, String transStatus) {
        ResModelUpdate resModel = ResModelUpdate.builder()
                .modelId(modelId)
                .transStatus(transStatus)
                .build();
        if (StringUtils.isNoneBlank(path)) {
            resModel.setModelPath(path);
        }
        resModelService.saveResModel(resModel);
    }

    /**
     * 下载
     *
     * @return 文件
     */
    @GetMapping("/**/{filename:.+\\.(?:jpg|jpeg|png|bmp|gif|JPG|JPEG|PNG|BMP|GIF|xls|3du|fbx|assetbundle|zip|XLS|3DU|FBX|ASSETBUNDLE|ZIP)}")
    public StreamingResponseBody readFile(HttpServletRequest request) throws IOException {
        String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        restOfTheUrl = restOfTheUrl.replace(getBaseMapping(), EMPTY);
        File file = new File(basePath + restOfTheUrl);
        byte[] filebyte = FileUtils.readFileToByteArray(file);
        return out -> out.write(filebyte);
    }

    private String getBaseMapping() {
        return "";
    }

    @PutMapping("/deliver/batch")
    //@RequiresPermissions({"model:delivery"})
    public ReturnData batchDeliverModel2Company(@Valid @RequestBody ModelDeliverPO modelDeliverPO, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        resModelBizService.deliverModel2Company(modelDeliverPO);
        return ReturnData.builder().message("交付成功");

    }

    @PutMapping("/deliver/single")
    //@RequiresPermissions({"model:delivery"})
    public ReturnData singleDeliverModel2Company(@Valid @RequestBody ModelDeliverPO modelDeliverPO, BindingResult br) {
        if (br.hasErrors()) {
            return processValidError(br, ReturnData.builder());
        }
        resModelBizService.deliverOrNot2Company(modelDeliverPO);

        return ReturnData.builder().message("交付成功");

    }

    @GetMapping("/{companyId}/{modelId}/with/delivered")
    //@RequiresPermissions({"model:delivery"})
    public ReturnData listCompanysWithDelivered(@PathVariable Long companyId, @PathVariable Long modelId) {
        List<CompanyWithDeliveredBO> companyWithDeliveredBOS = resModelBizService.listCompanyWithDelivered(companyId, modelId);
        return ReturnData.builder().data(companyWithDeliveredBOS).code(SUCCESS);

    }

    private String saveFile(MultipartFile file, ModelAreaRelAdd info) {

        log.debug("文件名:" + file.getOriginalFilename());
        String targetFileName = file.getOriginalFilename();
        Optional<ModelAreaRelAdd.AreaInfo> isModel = info.getAreas().stream()
                .filter(item -> item.getCode().equals(targetFileName.substring(0, targetFileName.lastIndexOf(Punctuation.DOT))))
                .findFirst();

        String fileName = "";
        if (isModel.isPresent()) {
            //材质
            ModelAreaRelAdd.AreaInfo item = isModel.get();
			String tmpPath = this.saveFileWithFileKey(file, SYSTEM_RES_TEXTURE_TEXTURE_BALL_FILE);
			item.setTexturePath(tmpPath);
            log.debug("ball path--------------->" + item.getTexturePath());
        } else {
            //为模型
            fileName = this.saveFileWithFileKey(file, PRODUCT_BASE_PRODUCT_U3DMODEL_WINDOWS_PC);
            log.debug("model path--------------->" + fileName);
        }

        return fileName;
    }

    private String saveFileWithFileKey(@NotNull MultipartFile file, @NotNull String fileKey) {
        LocalDateTime now = LocalDateTime.now();

        String path = this.getPathByFileKey(fileKey, now);

        DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        String fileName = Randomer.randomNum() + UNDERLINE + now.format(ddf) + DOT + FilenameUtils.getExtension(file.getOriginalFilename());

        // mkdir
        File dir = new File(basePath + path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // save file
        try {
			if (fileName.endsWith(".assetbundle")) {
				FileEncrypt.addRedundance(basePath + path + fileName, file.getInputStream());
			} else {
				file.transferTo(new File(basePath + path + fileName));
			}
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("save file path:{}", basePath + path + fileName);

        return path + fileName;
    }

    private String getPathByFileKey(String fileKey, LocalDateTime time) {
        String pathTemplate = fileKey2Path.get(fileKey);
        if (StringUtils.isBlank(pathTemplate)) {
            throw new RuntimeException("获取文件路径失败,fileKey is " + fileKey);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH");
        String formatPart = time.format(df);
        return pathTemplate.replace("/yyyy/MM/dd/HH", formatPart);
    }

    @ApiOperation("模型编辑器url地址")
    @PostMapping("/editor/file")
    public ReturnData initModelEditor() {
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("msgId", "versionCheck2");
        requestParam.put("systemType", "");
        Integer value = dictionaryService.getByValueKey(modelToolValueKey).getValue();
        dictionaryService.listByType("systemType").stream()
                .filter(item -> (value + "").equals(item.getAtt1()))
                .findFirst().ifPresent(item -> requestParam.put("systemType", item.getValue() + ""));
        String result = "";
        try {
            String response = httpUtils.doGet(modelToolUrl, requestParam, Collections.emptyList());
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            Map<String, Map> info = gson.fromJson(response, HashMap.class);
            if (info.containsKey("success")) {
                Map<String, String> obj = info.get("obj");
                result = obj.get("appPath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.builder().success(!Strings.isNullOrEmpty(result)).data(result);
    }


    @GetMapping("test")
    public String testRequest(HttpServletRequest request) throws IOException {
        return NetworkUtil.getIpAddress(request);
    }
}
