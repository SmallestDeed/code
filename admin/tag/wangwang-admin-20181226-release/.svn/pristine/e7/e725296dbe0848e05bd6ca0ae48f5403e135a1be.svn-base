package com.sandu.service.resmodel.dao;


import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.resmodel.input.ResModelQuery;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.bo.ModelListBO;
import com.sandu.api.resmodel.model.bo.ModelTextureInfoBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author bvvy
 */
@Repository
public interface ResModelDao {

    /**
     * 删除模型
     *
     * @param id id
     * @return 1,  0
     */
    int deleteResModel(@Param("id") long id);


    int deleteResModelsByProductIds(@Param("ids") List<Long> ids);

    /**
     * 添加模型
     *
     * @param resModel record
     * @return 1, 0
     */
    int addResModel(ResModel resModel);

    /**
     * 修改模型
     *
     * @param resModel record
     */
    void saveResModel(ResModel resModel);

    void updateByPrimaryKeySelective(ResModel resModel);

    /**
     * 获取模型详情
     *
     * @param id id
     * @return record
     */
    ResModel getResModelDetail(@Param("id") long id);

    /**
     * 搜索列表模型
     *
     * @param query 条件
     * @return 列表
     */
    List<ModelListBO> listResModel(ResModelQuery query);

    /**
     * 获取最大的id
     *
     * @return 最大id
     */
    String getMaxId();

    /**
     * 根据模型id集后去模型编码
     *
     * @param modelIds id集
     * @return 模型
     */
    List<ResModel> getModelByIds(@Param("modelIds") List<Integer> modelIds);

    /**
     * 通过模型id获取模型材质信息
     *
     * @param modelId id
     * @return 模型材质信息
     */
    List<ModelTextureInfoBO> getModelTextureInfoByModelId(@Param("modelId") long modelId);

    /**
     * 获取第一个没有转化的模型
     *
     * @return 模型
     * @param machineIp
     */
    ResModel getFirstNoneTransModel(@Param("machineIp") String machineIp);

    /**
     * 通过id集重新设置模型的productId
     *
     * @param ids id集
     * @return 1, 0
     */
    int resetProductId(List<Integer> ids);

    /**
     * 通过id集获取模型
     *
     * @param ids id集
     * @return 模型
     */
    List<ModelListBO> listModelByIds(List<Integer> ids);

    /**
     * 获取公司信息和已经交付的信息
     *
     * @param companyId 公司
     * @param modelId   模型
     * @return 公司
     */
    @Select("select company_name,companyId,if(t2.id is null, 0, 1 ) as delivered from (SELECT DISTINCT bc.company_name,id as companyId ,is_virtual " +
            " FROM base_company bc where is_deleted = 0 and business_type !=2 and business_type!=3 ) t1 " +
            " LEFT JOIN (select  *  from res_model_copy_log rmcl" +
            " WHERE rmcl.source_id = #{modelId} and rmcl.source_company_id = #{companyId} and rmcl.kind = 1) t2 on t1.companyId = t2.target_company_id where  t1.companyId != #{companyId} " +
            "and t1.is_virtual = 0 order by delivered desc")
    List<CompanyWithDeliveredBO> listCompanyWithDelivered(@Param("companyId") Long companyId, @Param("modelId") Long modelId);

    /**
     * 模型批量添加
     *
     * @param models models
     */
    void addResModelList(@Param("models") List<ResModel> models);

    /**
     * 获取已经交付的公司
     *
     * @param modelId Modelid
     * @return list long
     */
    List<Long> listDeliveredCompanyIds(Long modelId);

    List<ModelTextureInfoBO> getModelTextureInfoByModelIds(@Param("modelId") Set<Integer> modelId);

    List<ModelListBO> listModelOriginInfoByModelIds(@Param("modelIds") List<Long> modelIds);

    List<ResModel> listModelForFixPath(@Param("limit") int limit);

	ResModel queryModelByCode(@Param("companyId")Integer companyId, @Param("modelCode")String modelCode);
}
