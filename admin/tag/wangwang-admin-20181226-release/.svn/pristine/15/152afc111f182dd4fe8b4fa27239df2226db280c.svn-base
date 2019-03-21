package com.sandu.service.resmodel.impl.biz;

import static com.sandu.util.CodeUtil.formatCode;
import static com.sandu.util.Commoner.isNotEmpty;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.ProductTextureInfo;
import com.sandu.api.product.service.ProductService;
import com.sandu.api.resmodel.input.ModelAreaTextureRelAdd;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.input.ResModelQuery;
import com.sandu.api.resmodel.input.ResModelUpdate;
import com.sandu.api.resmodel.model.ModelAreaRel;
import com.sandu.api.resmodel.model.ModelAreaTextureRel;
import com.sandu.api.resmodel.model.ModelCopyLog;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.bo.AreaTextureBO;
import com.sandu.api.resmodel.model.bo.ModelAreaBO;
import com.sandu.api.resmodel.model.bo.ModelAreaTextureRelBO;
import com.sandu.api.resmodel.model.bo.ModelBO;
import com.sandu.api.resmodel.model.bo.ModelDeliverPO;
import com.sandu.api.resmodel.model.bo.ModelListBO;
import com.sandu.api.resmodel.model.bo.ModelTextureInfoBO;
import com.sandu.api.resmodel.service.ModelAreaRelService;
import com.sandu.api.resmodel.service.ModelCopyLogService;
import com.sandu.api.resmodel.service.ResModelBizService;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.input.ResTextureAdd;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.service.ModelAreaTextureRelService;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.constant.Punctuation;
import com.sandu.service.resmodel.dao.ResModelDao;
import com.sandu.util.JsonParser;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandu
 */
@Slf4j
@Service("resModelBizService")

public class ResModelBizServiceImpl implements ResModelBizService {
    private final ResModelService resModelService;
    private final ModelAreaTextureRelService modelAreaTextureRelService;
    private final ResTextureService resTextureService;
    private final ModelAreaRelService modelAreaRelService;
    private final ProductService productService;
    private final DictionaryService dictionaryService;

    @Resource
    private ModelCopyLogService modelCopyLogService;

    @Resource
    private ResModelDao resModelDao;

    @Autowired
    public ResModelBizServiceImpl(ResModelService resModelService, ModelAreaTextureRelService modelAreaTextureRelService,
                                  ResTextureService resTextureService, ModelAreaRelService modelAreaRelService,
                                  ProductService productService, DictionaryService dictionaryService) {
        this.resModelService = resModelService;
        this.modelAreaTextureRelService = modelAreaTextureRelService;
        this.resTextureService = resTextureService;
        this.modelAreaRelService = modelAreaRelService;
        this.productService = productService;
        this.dictionaryService = dictionaryService;
    }

    @Override
    public List<ModelAreaBO> showModelTextureInfo(long modelId) {
        //获取此模型所有贴图信息
        List<ModelTextureInfoBO> textureInfos = resModelService.getModelTextureInfoByModelId(modelId);
//        textureInfos.stream().collect(Collectors.groupingBy(ModelTextureInfoBO::getAreaId))
        //根据贴图ID获取贴图路径
        List<Integer> textureIds = textureInfos.stream().map(ModelTextureInfoBO::getTextureId).distinct().collect(toList());
        Map<Integer, ResTexture> textures = resTextureService.getIdAndFilePathByIds(textureIds);
        //获取此模型所有区域
        List<ModelAreaRel> areaRelList = modelAreaRelService.listByModelId((int) modelId);
        List<ModelAreaBO> ret = new LinkedList<>();
        areaRelList.forEach(area -> {
            textureInfos.stream().filter(info -> info.getAreaId().equals(area.getId())).forEach(info -> {
                Optional<ModelAreaBO> first = ret.stream().filter(item -> item.getAreaId().equals(info.getAreaId())).findFirst();
                ModelAreaBO bo = null;
                bo = first.orElseGet(ModelAreaBO::new);
                if (bo.getRels() == null) {
                    //设置贴图区域详情
                    bo.setRels(new ArrayList<>());
                    bo.setAreaCode(info.getAreaCode());
                    bo.setAreaId(+info.getAreaId());
                    bo.setModelId(info.getModelId().longValue());
                    bo.setAreaName(info.getAreaName());
                    bo.setHeight(area.getHeight());
                    bo.setWidth(area.getWidth());
                }
                if (info.getIsDefault() != null && info.getIsDefault() == 1) {
                    bo.setDefalutTextureId(info.getTextureId());
                }
                //设置区域材质信息
                log.debug("TextureId= {}", info.getTextureId());
                if (info.getTextureId() != null) {
                    AreaTextureBO textureBO = new AreaTextureBO();
                    textureBO.setAffectPrice(info.getAffectPrice());
                    textureBO.setTextureId(info.getTextureId());
                    textureBO.setDefaultFlag(info.getIsDefault().equals(1));
                    ResTexture temp = textures.get(textureBO.getTextureId());
                   // log.info("temp信息:{}", temp);
                    if(temp!=null) {
                        textureBO.setPicPath(temp.getFilePath());
                        textureBO.setTextureCode(temp.getFileCode());
                        textureBO.setModelNumber(temp.getModelNumber());
                        textureBO.setTextureName(temp.getName());
                    }else {
                    	textureBO.setPicPath("");
                        textureBO.setTextureCode("");
                        textureBO.setModelNumber("");
                        textureBO.setTextureName("");
                    }
                    bo.getRels().add(textureBO);
                }
                if (!ret.contains(bo)) {
                    ret.add(bo);
                }
            });
        });

        ret.sort(Comparator.comparing(ModelAreaBO::getAreaCode));
        return ret;
    }


    @Override
    @Transactional
    public boolean saveModelTextureInfo(List<ModelAreaTextureRel> list) {
        list.forEach(item -> {
            item.setGmtCreate(new Date());
            item.setGmtModified(new Date());
            item.setCreator("sanduManager");
        });
        int count = modelAreaTextureRelService.insertList(list);
        return count > 0;
    }
    
    public void updateModelTextureInfo(List<ModelAreaTextureRel> list) {
        list.forEach(item -> {
            item.setGmtCreate(new Date());
            item.setGmtModified(new Date());
            item.setCreator("sanduManager");
            modelAreaTextureRelService.updateModelTextureInfo(item);
        });
    }

    @Override
    public Map<Integer, List<ModelAreaTextureRelBO>> modelId2AreaTextures(Set<Integer> modelId) {
        if (modelId.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ModelTextureInfoBO> infos = resModelService.getModelTextureInfoByModelIds(modelId);
        return infos.stream().filter(item -> item.getTextureId() != null).collect(groupingBy(ModelTextureInfoBO::getModelId, mapping(item -> {
            ModelAreaTextureRelBO bo = new ModelAreaTextureRelBO();
            bo.setAreaId(item.getAreaId());
            bo.setAffectPrice(item.getAffectPrice());
            bo.setIsDefault(item.getIsDefault());
            bo.setTextureId(item.getTextureId());
            return bo;
        }, toList())));
    }

    @Override
    public int updateModelTextureInfoList(List<ModelAreaTextureRel> list) {
        List<Integer> collect = list.stream().map(ModelAreaTextureRel::getAreaId).collect(toList());
        modelAreaTextureRelService.deleteByAreaIds(collect);
        this.saveModelTextureInfo(list);
        return 0;
    }

    @Override
    public PageInfo<ModelListBO> queryModelList(ResModelQuery query) {
        if (Math.abs(query.getIsUsed()) > 1) {
            return new PageInfo<>();
        }
        final long listResModelStartTime = System.currentTimeMillis();
        log.warn("ListResModel start:{}", listResModelStartTime);

        PageInfo<ModelListBO> list = resModelService.listResModel(query);
        //设置模型来源信息
        setModelOriginInfo(list);

        log.warn("ListResModel end: cost time={}", System.currentTimeMillis() - listResModelStartTime);

        List<Long> modelIds = list.getList().stream().map(ModelListBO::getId).filter(Objects::nonNull).collect(toList());
        final long startTime = System.currentTimeMillis();

        log.warn("mapProductByModelIds start:{}", startTime);
        Map<Integer, Product> map = productService.mapProductByModelIds(modelIds);
        log.warn("mapProductByModelIds end, cost time={}", System.currentTimeMillis() - startTime);

        list.getList().forEach(item -> {
            item.setCategoryNames(dictionaryService.getProductTypeNameInfos(item.getSmallTypeMark(), item.getTypeMark()));
            Product product = map.get(item.getId().intValue());
            if (isNotEmpty(product)) {
                item.setConcerProductId(product.getId().intValue());
                item.setConcerProductName(product.getProductCode());
            }
            if (query.isChooseModelFlag()) {
                //设置区域信息
                List<ModelAreaBO> modelAreaBOS = this.showModelTextureInfo(item.getId());
                Integer count = modelAreaBOS.stream().mapToInt(ibo -> ibo.getRels().size()).sum();
                item.setTextureCount(count);
                item.setModelTextureInfo(modelAreaBOS);
            }
        });
        return list;
    }

    private void setModelOriginInfo(PageInfo<ModelListBO> list) {
        resModelService.listModelOriginInfoByOriginModelIds(
                list.getList().stream().filter(it -> Objects.nonNull(it.getOriginId()))
                        .map(it -> Long.valueOf(it.getOriginId())).distinct()
                        .collect(toList())
        ).forEach(originInfo -> {
            list.getList().stream().filter(it -> it.getOriginId().equals(originInfo.getId().toString()))
                    .findFirst()
                    .ifPresent(it -> it.setModelOrigin(originInfo.getModelOrigin()));

        });
    }
    
    /**
     * 修改指定模型的数据
     * @param list
     * @param balls
     * @return
     */
    public int updateModelAreaRelAndTexture1(List<ModelAreaRel> list, List<ResModelAdd> balls) {
    	Integer modelId = list.get(0).getModelId();
        List<ModelAreaRel> reList =  modelAreaRelService.listByModelId(modelId);
        if(reList != null && reList.size() > 0) {
        	List<Integer> areaList = reList.stream().map(ModelAreaRel::getId).collect(toList());
            if(areaList != null) {
            	//获取材质贴图区域并删除
            	List<Integer> idList = modelAreaTextureRelService.getIdsByAreaIds(areaList);
            	modelAreaTextureRelService.deleteByAreaIds(idList);
            	//获取所有的材质并删除
            	List<Integer> textureIds = modelAreaTextureRelService.getTextureIdsById(idList);
            	if(textureIds!=null && textureIds.size() > 0) {
            		textureIds.forEach(rextureId -> resTextureService.deleteResTexture(Long.valueOf(rextureId)) );
            	}
            }
            //删除模型区域表
            modelAreaRelService.deleteAreaByModelId(modelId);
        }
        //再重新插入
        return insetModelAreaRelAndDefaultTextureWithBalls(list,balls);
    }
    
    
    /**
     * 修改指定模型的数据
     * @param list
     * @param balls
     * @return
     */
    public int updateModelAreaRelAndTexture(List<ModelAreaRel> list, List<ResModelAdd> balls) {
    	Integer modelId = list.get(0).getModelId();
    	List<ModelAreaTextureRel> modelAreaTextureList = new ArrayList<ModelAreaTextureRel>();
    	Map<Integer, Integer> areaTextureMap = new HashMap<Integer,Integer>();
    	//获取模型区域信息
        List<ModelAreaRel> reList =  modelAreaRelService.listByModelId(modelId);
        if(reList != null && reList.size() > 0) {
        	List<Integer> areaList = reList.stream().map(ModelAreaRel::getId).collect(toList());
            if(areaList != null) {
            	//转换区域编码和区域ID
            	Map<String, Integer> textureAreaMap = reList.stream().collect(Collectors.toMap(ModelAreaRel::getCode, ModelAreaRel::getId));
            	
            	//赋值区域ID
            	list = list.stream().map(item -> {
            		ModelAreaRel rel = new ModelAreaRel();
            		rel.setId(textureAreaMap.get(item.getCode()));
            		rel.setCode(item.getCode());
            		rel.setName(item.getName());
                    return rel;
                }).collect(toList());
            	
            	//根据区域ID获取材质贴图区域关联
            	modelAreaTextureList = modelAreaTextureRelService.getModelAreaTextureRelList(areaList).stream().filter(item -> 1 == item.getIsDefault()).collect(Collectors.toList());
            	areaTextureMap =  modelAreaTextureList.stream().collect(Collectors.toMap(ModelAreaTextureRel::getAreaId, ModelAreaTextureRel::getId));
            	
            	//取modelAreaTexture下面所有的材质ID
//            	List<Integer> idList = modelAreaTextureList.stream().map(ModelAreaTextureRel::getTextureId).collect(toList());
//            	
//            	//获取模型编辑器上传的默认材质ID
//            	if(idList != null && idList.size() > 0) {
//            		List<Integer> textureIds = resTextureService.getDefaultTextureIdsById(idList,modelId);
//                	if(textureIds!=null && textureIds.size() > 0) {
//                		textureIds.forEach(rextureId -> resTextureService.deleteResTexture(Long.valueOf(rextureId)) );
//                	}
//                	//删除默认材质
//                	List<Integer> deleteIds =  modelAreaTextureList.stream().filter(it -> textureIds.contains(it.getTextureId())).map(ModelAreaTextureRel::getId).collect(toList());
//                	modelAreaTextureRelService.deleteByIds(deleteIds);
//            	}
            }
            //删除模型区域表
            //modelAreaRelService.deleteAreaByModelId(modelId);
        }
        //再重新插入
        return this.updateModelAreaRelAndDefaultTextureWithBalls(list,balls,areaTextureMap);
    }

    public int updateModelAreaRelAndDefaultTextureWithBalls(List<ModelAreaRel> list, List<ResModelAdd> balls,Map<Integer, Integer> areaTextureMap) {
        if (list.isEmpty()) {
            return -1;
        }
        log.info("areas:{}", list);
        log.info("balls:{}", balls);
        Integer modelId = list.get(0).getModelId();
        Map<String, Long> ballId2textureIdMap = new HashMap<>(balls.size());
        balls.forEach(item -> {
            //保存材质球
            Long ballId = resModelService.addResModel(item);
            //保存材质   材质球-->材质
            Long textureId = resTextureService.addResTexture(
                    ResTextureAdd.builder()
                            .textureballFileId(ballId.intValue())
                            .companyId(item.getCompanyId())
                            .textureName(item.getModelName())
                            .thumbnailId(item.getBall2TexturePicId())
                            .thumbnailPath(item.getThumbPicPath())
                            .fileLength(StringUtils.isBlank(item.getHeight()+"") ? "0" : item.getHeight()+"")    //材质的长,取模型的高
                            .fileWidth(StringUtils.isBlank(item.getWidth()+"") ? "0" : item.getWidth()+"")
                            .initWithModelId(modelId)
                            .build());
            ballId2textureIdMap.put(item.getAreaCode(), textureId);
        });
        //处理模型区域，若区域不存在,则插入区域
        list.forEach(item ->{
        	if(item.getId() == null) {
        		List<ModelAreaRel> insertList = new ArrayList<ModelAreaRel>();
        		insertList.add(item);
        		modelAreaRelService.saveModelAreaRelList(insertList);
        		item.setId(insertList.get(0).getId());
        	}
        });
        //关联区域-默认贴图
        log.debug("ball2Map:{}", ballId2textureIdMap);
        log.debug("rearList:{}", list);
        List<ModelAreaTextureRel> areaTextureRels = list.stream().map(item -> {
            ModelAreaTextureRel rel = new ModelAreaTextureRel();
            rel.setAffectPrice(0);
            rel.setAreaId(item.getId());
            rel.setIsDefault(Byte.parseByte("1"));
            rel.setTextureId(ballId2textureIdMap.get(item.getCode()).intValue());
            rel.setId(areaTextureMap.get(item.getId()));
            return rel;
        }).collect(toList());
        this.updateModelTextureInfo(areaTextureRels);
        //this.saveModelTextureInfo(areaTextureRels);
        return 1;
    }
    
    
    @Override
    public int insetModelAreaRelAndDefaultTextureWithBalls(List<ModelAreaRel> list, List<ResModelAdd> balls) {
        if (list.isEmpty()) {
            return -1;
        }
        log.info("areas:[]", list);
        log.info("balls:[]", balls);
        Integer modelId = list.get(0).getModelId();
        Map<String, Long> ballId2textureIdMap = new HashMap<>(balls.size());
        balls.forEach(item -> {
            //保存材质球
            Long ballId = resModelService.addResModel(item);
            //保存材质   材质球-->材质
            Long textureId = resTextureService.addResTexture(
                    ResTextureAdd.builder()
                            .textureballFileId(ballId.intValue())
                            .companyId(item.getCompanyId())
                            .textureName(item.getModelName())
                            .thumbnailId(item.getBall2TexturePicId())
                            .thumbnailPath(item.getThumbPicPath())
                            .fileLength(StringUtils.isBlank(item.getHeight()+"") ? "0" : item.getHeight()+"")    //材质的长,取模型的高
                            .fileWidth(StringUtils.isBlank(item.getWidth()+"") ? "0" : item.getWidth()+"")
                            .initWithModelId(modelId)
                            .build());
            ballId2textureIdMap.put(item.getAreaCode(), textureId);
        });
        //保存区域
        modelAreaRelService.saveModelAreaRelList(list);
        //关联区域-默认贴图
        log.debug("ball2Map:[]", ballId2textureIdMap);
        log.debug("rearList:[]", list);
        List<ModelAreaTextureRel> areaTextureRels = list.stream().map(item -> {
            ModelAreaTextureRel rel = new ModelAreaTextureRel();
            rel.setAffectPrice(0);
            rel.setAreaId(item.getId());
            rel.setIsDefault(Byte.parseByte("1"));
            rel.setTextureId(ballId2textureIdMap.get(item.getCode()).intValue());
            return rel;
        }).collect(toList());
        this.saveModelTextureInfo(areaTextureRels);
        return 1;
    }

    @Override
    public Product insetModelAreaRelAndDefaultTextureWithTextures(List<ModelAreaRel> list, List<ModelAreaTextureRel> areaTextureRels) {
        if (list.isEmpty()) {
            return null;
        }
        //删除之前的区域贴图信息
        Optional.ofNullable(list.get(0)).map(ModelAreaRel::getModelId).ifPresent(item -> {
                    modelAreaRelService.deleteAreaByModelId(item);
            deleteAreaTextureInfoByModelId(item);
                }
        );
        log.info("areas:[]", list);
        log.info("areaTextureRels:[]", areaTextureRels);
        //保存区域
        list.forEach(item -> {
            item.setId(null);
            item.setGmtCreate(new Date());
            item.setGmtModified(new Date());
            item.setCreator("--");
        });
        //保存新增区域
        modelAreaRelService.saveModelAreaRelList(list.stream().filter(item -> item.getId() == null).collect(toList()));
        //关联区域-默认贴图
        //兼容之前的模型贴图信息
        List<ProductTextureInfo> productTextureInfos = new ArrayList<>();
        Set<Integer> materialPicIds = new HashSet<>();

        list.forEach(area -> {
            ProductTextureInfo pti = new ProductTextureInfo();
            StringBuilder textureIds = new StringBuilder();
            areaTextureRels.stream()
                    .filter(rel -> rel.getAreaCode().equals(area.getCode()))
                    .forEach(rel -> {
                        rel.setAreaId(area.getId());
                        pti.setName(area.getName());
                        pti.setKey(Integer.valueOf(area.getCode() + 1).toString());
                        pti.setWidth(area.getWidth());
                        pti.setHeight(area.getHeight());
                        pti.setWidth(area.getWidth());
                        pti.setHeight(area.getHeight());
                        if (Byte.valueOf("1").equals(rel.getIsDefault())) {
                            pti.setDefaultId(rel.getTextureId());
                        }
                        textureIds.append(rel.getTextureId()).append(",");
                        materialPicIds.add(rel.getTextureId());
                    });
            if (textureIds.length() > 1) {
                textureIds.substring(0, textureIds.length() - 1);
            }
            pti.setTextureIds(textureIds.toString());
            if(pti.getDefaultId() != null){
                productTextureInfos.add(pti);
            }
        });
        this.saveModelTextureInfo(areaTextureRels);
        Product product = new Product();
        product.setMaterialPicIds(Joiner.on(Punctuation.COMMA).skipNulls().join(materialPicIds));
        product.setSplitTexturesInfo(JsonParser.toJson(productTextureInfos));
        return product;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteAreaTextureInfoByModelId(Integer modelId) {
        List<ModelAreaRel> list = modelAreaRelService.listByModelId(modelId);
        List<Integer> ids = list.stream().map(ModelAreaRel::getId).collect(toList());
//        return modelAreaTextureRelService.deleteByAreaIds(ids);
        List<Integer> matrIds = modelAreaTextureRelService.getIdsByAreaIds(ids);
        return modelAreaTextureRelService.deleteByIds(matrIds);
    }

    @Override
    public void deliverOrNot2Company(ModelDeliverPO modelDeliverPO) {
        Long modelId = modelDeliverPO.getModelId().get(0);
        //原来的交付信息
        List<Long> deliveredCompanyIds = resModelDao.listDeliveredCompanyIds(modelId);
        //先交付
        deliverModel2Company(modelDeliverPO);
        List<Integer> toIds = modelDeliverPO.getToCompanyId();
        // 取消交付
        deliveredCompanyIds.forEach(deliveredId -> {
            if (!toIds.contains(deliveredId.intValue())) {
                cancelDeliverModel2Company(deliveredId, modelId);
            }
        });
    }


    /**
     * 取消交付
     *
     * @param companyId 已经交付的公司
     * @param modelId   原本的模型
     */
    private void cancelDeliverModel2Company(Long companyId, Long modelId) {
        // 删除交付后的模型 --> 1 先获取交付后的模型  2 逐个击破
        List<Long> targetModelIds = modelCopyLogService.listDeliveredModelIds(modelId, companyId);

        targetModelIds.forEach(resModelService::deleteResModel);
        //  将交付记录删除
        modelCopyLogService.deleteByCompanyIdAndSourceId(companyId, modelId);

    }

    @Override
    public void deliverModel2Company(ModelDeliverPO modelDeliverPO) {
        List<ResModel> models = new ArrayList<>();

        for (Long modelId : modelDeliverPO.getModelId()) {
            List<Long> ids = resModelDao.listDeliveredCompanyIds(modelId);
            modelDeliverPO.getToCompanyId().stream().filter(it -> !ids.contains(it.longValue())).forEach(
                    companyId -> {
                        ResModel model = resModelService.getResModelDetail(modelId);
                        model.setId(null);
                        model.setCompanyId(companyId);
                        model.setGmtCreate(new Date());
                        model.setGmtModified(new Date());
                        model.setOriginId(modelId);
                        model.setModelCode(formatCode("M", resModelDao.getMaxId()));
                        resModelDao.addResModel(model);
                        models.add(model);
                    }
            );
        }
        //写入模型区域
        List<Long> originModelIds = models.stream().map(ResModel::getOriginId).collect(toList());
        List<ModelAreaRel> modelAreaRels = modelAreaRelService.listByModelIds(originModelIds);
        List<ModelAreaRel> targetModelAreaRels = new ArrayList<>();
        for (ResModel model : models) {
            for (ModelAreaRel modelAreaRel : modelAreaRels) {
                if (Objects.equals(model.getOriginId(), modelAreaRel.getModelId().longValue())) {
                    ModelAreaRel tempModelArea = new ModelAreaRel();
                    BeanUtils.copyProperties(modelAreaRel, tempModelArea);
                    tempModelArea.setModelId(model.getId().intValue());
                    tempModelArea.setId(null);
                    targetModelAreaRels.add(tempModelArea);
                }
            }
            //写入交付记录
            ModelCopyLog modelCopyLog = new ModelCopyLog();
            modelCopyLog.setSourceId(model.getOriginId());
            modelCopyLog.setTargetId(model.getId());
            modelCopyLog.setSourceCompanyId(modelDeliverPO.getFromCompanyId());
            modelCopyLog.setTargetCompanyId(model.getCompanyId().longValue());
            modelCopyLog.setKind(1);
            modelCopyLog.setGmtCreate(new Date());
            modelCopyLogService.addModelCopyLog(modelCopyLog);
        }
        modelAreaRelService.saveModelAreaRelList(targetModelAreaRels);
    }

    @Override
    public List<CompanyWithDeliveredBO> listCompanyWithDelivered(Long companyId, Long modelId) {
        return resModelService.listCompanyWithDelivered(companyId, modelId);
    }

    @Override
    public ModelBO modelDetails(Long id) {
        ResModel detail = resModelService.getResModelDetail(id);
        Optional.ofNullable(detail).orElseThrow(() -> new RuntimeException("获取模型失败,模型ID:" + id));
        Map<String, Dictionary> propInfo = dictionaryService.getProductTypeValueAndSmallBySmallValueKey(detail.getSmallTypeMark());
        List<String> names = propInfo.entrySet().stream()
                .sorted((o1, o2) -> (int) (o1.getValue().getId() - o2.getValue().getId()))
                .map(item -> item.getValue().getName())
                .collect(toList());
        ModelBO modelBO = new ModelBO();
        BeanUtils.copyProperties(detail, modelBO);
        modelBO.setTypeNames(names);
        //模型分类  从大小类中取
        if (StringUtils.isNoneBlank(detail.getSmallTypeMark())) {
            String firstCode = "";
            if (detail.getSmallTypeMark().trim().startsWith("basic_")) {
                firstCode = "model_type";
            } else {
                firstCode = "product_type";
            }
            modelBO.setCategoryIds(firstCode + "," + detail.getTypeMark() + "," + detail.getSmallTypeMark());
        }
        //设置模型各区域详情
        List<ModelAreaBO> modelAreaBOS = showModelTextureInfo(id);
        modelBO.setModelTextureInfo(modelAreaBOS);
        return modelBO;
    }

    @Override
    public void addModel(ResModelAdd resModelAdd) {
        Long id = resModelService.addResModel(resModelAdd);
        if (resModelAdd.getModelAreaTextureRelAdd() != null && !resModelAdd.getModelAreaTextureRelAdd().isEmpty()) {
            saveModelAreaTextureInfos(resModelAdd.getModelAreaTextureRelAdd(), id);
        }
    }

    @Override
    public void updateModel(ResModelUpdate resModelUpdate) {
        resModelService.saveResModel(resModelUpdate);
        if (resModelUpdate.getModelAreaTextureRelAdd() != null && !resModelUpdate.getModelAreaTextureRelAdd().isEmpty()) {
            saveModelAreaTextureInfos(resModelUpdate.getModelAreaTextureRelAdd(), resModelUpdate.getModelId());
        }
    }


    private void saveModelAreaTextureInfos(List<ModelAreaTextureRelAdd> list, Long modelId) {
        log.info("add:{}", list);
        List<ModelAreaTextureRel> rels = new LinkedList<>();
        List<ModelAreaRel> areas = list.stream().map(area -> {
            ModelAreaRel tmp = new ModelAreaRel();
            tmp.setId(area.getAreaId());
            tmp.setCode(area.getAreaCode());
            tmp.setName(area.getAreaName());
            tmp.setModelId(modelId.intValue());
            tmp.setIsDeleted((byte) 0);
            tmp.setWidth(area.getWidth());
            tmp.setHeight(area.getHeight());
            if (area.getRels() != null) {
                area.getRels().forEach(info -> {
                    ModelAreaTextureRel modelAreaTextureRel = new ModelAreaTextureRel();
                    modelAreaTextureRel.setAffectPrice(info.getPrice());
                    modelAreaTextureRel.setTextureId(info.getTextureId());
                    modelAreaTextureRel.setAreaCode(tmp.getCode());
                    modelAreaTextureRel.setIsDefault((byte) (info.isDefaultFlag() ? 1 : 0));
                    rels.add(modelAreaTextureRel);
                });
            }
            return tmp;
        }).distinct().collect(toList());
//        设置模型区域贴图相关数据，更新相应产品json
        Product product = this.insetModelAreaRelAndDefaultTextureWithTextures(areas, rels);
        if (Objects.isNull(product)) {
            return;
        }
        ResModel model = resModelService.getResModelDetail(modelId);
        if (model.getProductId() != null && model.getProductId() > 0) {
            product.setId(model.getProductId().longValue());
            productService.updateProduct(product);
        }
    }

	@Override
	public ResModel queryModelByCode(Integer companyId, String modelCode) {
		return resModelService.queryModelByCode(companyId,modelCode);
	}

}
