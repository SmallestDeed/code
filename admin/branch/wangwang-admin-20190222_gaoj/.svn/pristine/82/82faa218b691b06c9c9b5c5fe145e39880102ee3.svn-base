package com.sandu.service.resmodel.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.category.service.CategoryService;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.input.ResModelQuery;
import com.sandu.api.resmodel.input.ResModelUpdate;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.TransStatus;
import com.sandu.api.resmodel.model.bo.ModelListBO;
import com.sandu.api.resmodel.model.bo.ModelTextureInfoBO;
import com.sandu.api.resmodel.service.ModelAreaRelService;
import com.sandu.api.resmodel.service.ModelCopyLogService;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.service.ModelAreaTextureRelService;
import com.sandu.api.user.service.UserService;
import com.sandu.constant.Punctuation;
import com.sandu.service.resmodel.dao.ResModelDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.util.CodeUtil.formatCode;
import static com.sandu.util.Commoner.isNotEmpty;

/**
 * @author Sandu
 */
@Service("resModelService")
@Slf4j
public class ResModelServiceImpl implements ResModelService {

    @Resource
    private ResModelDao resModelDao;

    @Resource
    private CategoryService categoryService;

    @Resource
    private UserService userService;

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private ModelAreaRelService modelAreaRelService;

    @Resource
    private ModelCopyLogService modelCopyLogService;

    @Resource
    private ModelAreaTextureRelService modelAreaTextureRelService;

    @Override
    public Long addResModel(ResModelAdd resModelAdd) {
        String cateNames = null;
//        if (StringUtils.isNoneBlank(resModelAdd.getSmallType())) {
//            cateNames = Arrays.stream(resModelAdd.getSmallType().split(COMMA))
//                    .map(catCode -> categoryService.getCategoryByCode(catCode).getName()).collect(joining(COMMA));
//        }
        log.info("resmodel : {}", resModelAdd);
        ResModel resModel = ResModel
                .builder()
                .modelDesc(resModelAdd.getModelDesc())
                .modelName(resModelAdd.getModelName())
                .modelPath(resModelAdd.getModelPath())
                .thumbPicPath(resModelAdd.getThumbPicPath())
                // .modelPath(resModelAdd.getModelPath())
                .categoryIds(resModelAdd.getCategoryIds())
                .categoryNames(cateNames)
                .modelModelNum(resModelAdd.getModelModelNum())
                .transStatus(resModelAdd.getTransStatus())
                .gmtModified(new Date())
                .gmtCreate(new Date())
                .isDeleted(0)
                .width(resModelAdd.getWidth())
                .height(resModelAdd.getHeight())
                .length(resModelAdd.getLength())
                .file3duPath(resModelAdd.getModelPath())
                .creator(userService.getUserById(resModelAdd.getUserId()).getUserName())
                .companyId(resModelAdd.getCompanyId())
                .fileKey(resModelAdd.getFileKey())
                .mainModelFlag(resModelAdd.getMainModelFlag())
                .transMachineIp(resModelAdd.getTransMachineIp())
                .build();
        //页面新增模型
        fixWithWebAddModelInfo(resModel, resModelAdd.getCategoryIds());
        //模型编辑器新增
        if (StringUtils.isNoneBlank(resModelAdd.getSmallType())) {
            setModelTypeInfo(resModelAdd.getSmallType(), resModel);
        }
        resModel.setModelCode(formatCode("M", resModelDao.getMaxId()));
        resModelDao.addResModel(resModel);
        return resModel.getId();
    }

    /**
     * 处理页面新增模型参数
     */
    private void fixWithWebAddModelInfo(ResModel resModel, String categoryIds) {
        if (StringUtils.isNoneBlank(categoryIds)) {
            String[] split = categoryIds.split(Punctuation.COMMA);
            if (split.length == 3) {
                resModel.setTypeMark(split[1]);
                resModel.setSmallTypeMark(split[2]);
            }
        }
    }

    private void setModelTypeInfo(String smallType, ResModel resModel) {
        Map<String, Dictionary> typeAndSmallType =
                dictionaryService.getProductTypeValueAndSmallBySmallValueKey(smallType);
        if (typeAndSmallType.isEmpty() || typeAndSmallType.get(Dictionary.DIC_TYPE_TYPE) == null) {
            typeAndSmallType = new HashMap<>(2);
            Dictionary defaultDictionary = new Dictionary();
            defaultDictionary.setValue(-1);
            typeAndSmallType.put(Dictionary.DIC_TYPE_TYPE, defaultDictionary);
            typeAndSmallType.put(Dictionary.DIC_TYPE_SMALL_TYPE, defaultDictionary);
        }
        resModel.setTypeMark(typeAndSmallType.get(Dictionary.DIC_TYPE_TYPE).getValuekey());
        resModel.setSmallTypeMark(typeAndSmallType.get(Dictionary.DIC_TYPE_SMALL_TYPE).getValuekey());
        String firstCode = "";
        if (resModel.getTypeMark().trim().startsWith("basic")) {
            firstCode = "model_type";
        } else {
            firstCode = "product_type";
        }
        resModel.setCategoryIds(firstCode + "," + resModel.getTypeMark() + "," + resModel.getSmallTypeMark());
    }

    @Override
    public Long saveResModel(ResModelUpdate resModelUpdate) {
        ResModel resModel = ResModel
                .builder()
                .modelDesc(Optional.ofNullable(resModelUpdate.getModelDesc()).orElse(""))
                .modelName(resModelUpdate.getModelName())
                .modelName(resModelUpdate.getModelName())
                .modelModelNum(resModelUpdate.getModelModelNum())
                .thumbPicPath(resModelUpdate.getThumbPicPath())
                .categoryIds(resModelUpdate.getCategoryIds())
                .transStatus(resModelUpdate.getTransStatus())
//                .categoryNames(cateNames)
                .id(resModelUpdate.getModelId())
                .mainModelFlag(resModelUpdate.getMainModelFlag())
                .gmtModified(new Date())
                .productId(resModelUpdate.getProductId())
                .transMachineIp(resModelUpdate.getTransMachineIp())
                .build();
        if (StringUtils.isNoneBlank(resModelUpdate.getModelPath()) &&
                resModelUpdate.getModelPath().trim().toLowerCase().endsWith(".3du")) {
            resModel.setFile3duPath(resModelUpdate.getModelPath());
        }
        if (StringUtils.isNoneBlank(resModelUpdate.getModelPath()) &&
                resModelUpdate.getModelPath().trim().toLowerCase().endsWith(".assetbundle")) {
            resModel.setModelPath(resModelUpdate.getModelPath());
        }
        if (resModelUpdate.getProductId() != null && resModelUpdate.getProductId() == 0) {
            resModel.setGmtModified(null);
        }
        if (!TransStatus.SUCCESS.getCode().equals(resModelUpdate.getTransStatus())) {
            resModel.setFile3duPath(resModelUpdate.getModelPath());
        }
        //更新大小类
        fixWithWebAddModelInfo(resModel, resModelUpdate.getCategoryIds());
        resModelDao.saveResModel(resModel);
        return resModel.getId();
    }

    @Override
    public int deleteResModel(Long id) {
        if (id == null) {
            return 0;
        }
        return resModelDao.deleteResModel(id);
    }

    @Override
    public int deleteResModelsByProductIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return resModelDao.deleteResModelsByProductIds(ids);
    }

    @Override
    public ResModel getResModelDetail(Long id) {
        if (id == null) {
            return null;
        }

        return resModelDao.getResModelDetail(id);
    }

    @Override
    public PageInfo<ModelListBO> listResModel(ResModelQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(resModelDao.listResModel(query));
    }

    @Override
    public Map<Integer, ResModel> getModelByIds(List<Integer> modelIds) {
        modelIds = modelIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (modelIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ResModel> models = resModelDao.getModelByIds(modelIds);
        Map<Integer, ResModel> map = new HashMap<>(models.size());
        for (ResModel model : models) {
            map.put(model.getId().intValue(), model);
        }
        return map;
    }

    @Override
    public List<ModelTextureInfoBO> getModelTextureInfoByModelId(long modelId) {
        return resModelDao.getModelTextureInfoByModelId(modelId);
    }

    @Override
    public ResModel getQueue(String machineIp) {
        ResModel resModel = resModelDao.getFirstNoneTransModel(machineIp);
        if (isNotEmpty(resModel)) {
            ResModel tempModel = resModelDao.getResModelDetail(resModel.getId());
            tempModel.setTransStatus(TransStatus.ING.getCode());
            resModelDao.saveResModel(tempModel);
        }
        return resModel;
    }

    @Override
    public int resetProductId(List<Integer> ids) {
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return 0;
        }
        return resModelDao.resetProductId(ids);
    }

    @Override
    public List<ModelListBO> listModelByIds(List<Integer> ids) {
        ids = ids.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return resModelDao.listModelByIds(ids);
    }

    @Override
    public List<CompanyWithDeliveredBO> listCompanyWithDelivered(Long companyId, Long modelId) {
        return resModelDao.listCompanyWithDelivered(companyId, modelId);
    }

    @Override
    public List<ModelTextureInfoBO> getModelTextureInfoByModelIds(Set<Integer> modelId) {
        if (modelId.isEmpty()) {
            return Collections.emptyList();
        }
        return resModelDao.getModelTextureInfoByModelIds(modelId);
    }

    @Override
    public List<ModelListBO> listModelOriginInfoByOriginModelIds(List<Long> collect) {
        if (collect.isEmpty()) {
            return Collections.emptyList();
        }
        return resModelDao.listModelOriginInfoByModelIds(collect);
    }

    @Override
    public List<ResModel> listModelForFixPath(int limit) {
        return resModelDao.listModelForFixPath(limit);
    }

    @Override
    public void updateByPrimaryKeySelective(ResModel model) {
        resModelDao.updateByPrimaryKeySelective(model);
    }

	@Override
	public ResModel queryModelByCode(Integer companyId, String modelCode) {
		return resModelDao.queryModelByCode(companyId,modelCode);
	}


}
