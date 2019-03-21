package com.sandu.api.resmodel.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.product.model.Product;
import com.sandu.api.resmodel.input.ResModelAdd;
import com.sandu.api.resmodel.input.ResModelQuery;
import com.sandu.api.resmodel.input.ResModelUpdate;
import com.sandu.api.resmodel.model.ModelAreaRel;
import com.sandu.api.resmodel.model.ModelAreaTextureRel;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.bo.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author bvvy
 * @date 2018/4/9
 */
public interface ResModelBizService {
    /**
     * 根据模型ID获取模型详情(区域/贴图)
     *
     * @param modelId id
     * @return model
     */
    List<ModelAreaBO> showModelTextureInfo(long modelId);

    /**
     * 保存模型详情
     *
     * @param list 列表
     * @return t f
     */
    boolean saveModelTextureInfo(List<ModelAreaTextureRel> list);


    Map<Integer,List<ModelAreaTextureRelBO>> modelId2AreaTextures(Set<Integer> modelId);

    /**
     * 更新模型材质列表信息
     *
     * @param list list
     * @return 1,  0
     */
    int updateModelTextureInfoList(List<ModelAreaTextureRel> list);

    /**
     * 搜索分页
     *
     * @param query 条件
     * @return 结果
     */
    PageInfo<ModelListBO> queryModelList(ResModelQuery query);

    /**
     * 插入模型区域和区域默认贴图
     *
     * @param list 区域
     * @param balls 区域内默认材质信息
     * @return 1, 0
     */
    int insetModelAreaRelAndDefaultTextureWithBalls(List<ModelAreaRel> list, List<ResModelAdd> balls);

    
    public int updateModelAreaRelAndTexture(List<ModelAreaRel> list, List<ResModelAdd> balls);
    
    
    Product insetModelAreaRelAndDefaultTextureWithTextures(List<ModelAreaRel> list, List<ModelAreaTextureRel> areaTextureRels);

    /**
     * 删除模型材质区域信息
     *
     * @param modelId 模型id
     * @return 1, 0
     */
    int deleteAreaTextureInfoByModelId(Integer modelId);

    /**
     * 只用于单个模型的交付取消
     *
     * @param modelDeliverPO 交付信息
     */
    void deliverOrNot2Company(ModelDeliverPO modelDeliverPO);

    /**
     * 交付模型给公司
     * 这个用于批量的交付,不进行取消的动作
     *
     * @param modelDeliverPO 交付参数
     */
    void deliverModel2Company(ModelDeliverPO modelDeliverPO);

    /**
     * 获取公司全部信息并且是否交付过
     *
     * @param companyId companyid
     * @param modelId   modelId
     * @return list
     */
    List<CompanyWithDeliveredBO> listCompanyWithDelivered(Long companyId, Long modelId);

    ModelBO modelDetails(Long id);

    /**
     * 页面新增模型
     * @param resModelAdd
     */
    void addModel(ResModelAdd resModelAdd);

    /**
     * 页面更新模型
     * @param resModelUpdate
     */
    void updateModel(ResModelUpdate resModelUpdate);

    /**
     * 根据公司和模型编码获取模型数据
     * @param companyId
     * @param modelCode
     * @return
     */
	ResModel queryModelByCode(Integer companyId, String modelCode);
}
