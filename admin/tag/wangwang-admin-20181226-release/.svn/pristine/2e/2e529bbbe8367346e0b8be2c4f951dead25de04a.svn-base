package com.sandu.api.resmodel.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.input.ResModelQuery;
import com.sandu.api.resmodel.input.ResModelUpdate;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.bo.ModelListBO;
import com.sandu.api.resmodel.model.bo.ModelTextureInfoBO;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Sandu
 */
public interface ResModelService {

    /**
     * 添加模型
     *
     * @param resModelAdd record
     * @return id
     */
    Long addResModel(ResModelAdd resModelAdd);

    /**
     * 修改模型
     *
     * @param resModelUpdate 修改信息
     * @return id
     */
    Long saveResModel(ResModelUpdate resModelUpdate);

    /**
     * 删除模型
     *
     * @param id id
     * @return 1, 0
     */
    int deleteResModel(Long id);


    int deleteResModelsByProductIds(List<Long> ids);

    /**
     * 获取单个
     *
     * @param id id
     * @return 模型
     */
    ResModel getResModelDetail(Long id);

    /**
     * 列表模型
     *
     * @param resModelQuery 条件
     * @return page
     */
    PageInfo<ModelListBO> listResModel(ResModelQuery resModelQuery);

    /**
     * 获取模型和id code的map
     *
     * @param modelIds ids
     * @return Map(id, code)
     */
    Map<Integer, ResModel> getModelByIds(List<Integer> modelIds);

    /**
     * 获取模型贴图信息
     *
     * @param modelId modelid
     * @return 贴图信息
     */
    List<ModelTextureInfoBO> getModelTextureInfoByModelId(long modelId);

    /**
     * 获取没有转化的模型一条
     *
     * @return 单条模型
     * @param machineIp
     */
    ResModel getQueue(String machineIp);

    /**
     * 重新设置模型产品id
     *
     * @param ids ids
     * @return 1, 0
     */
    int resetProductId(List<Integer> ids);

    /**
     * 获取模型通过ids
     *
     * @param ids ids
     * @return list
     */
    List<ModelListBO> listModelByIds(List<Integer> ids);

    /**
     * 获取所有的公司 和是否交付
     *
     * @param companyId 公司
     * @param modelId   模型
     * @return 公司
     */
    List<CompanyWithDeliveredBO> listCompanyWithDelivered(Long companyId, Long modelId);

    List<ModelTextureInfoBO> getModelTextureInfoByModelIds(Set<Integer> modelId);

    List<ModelListBO> listModelOriginInfoByOriginModelIds(List<Long> collect);

    List<ResModel> listModelForFixPath(int limit);

    void updateByPrimaryKeySelective(ResModel model);

	ResModel queryModelByCode(Integer companyId, String modelCode);
}
