package com.nork.repair.service;

import com.nork.system.model.ResModel;
import com.nork.system.model.ResTexture;

public interface RepairService {

    /**
     * 修复企业的模型数据（数据从原到目标）
     * @param sourceCompanyId   原公司ID
     * @param sourceBrandId     原品牌ID
     * @param targetCompanyId   目标公司ID
     * @return
     */
    void repairModel(Integer sourceCompanyId, Integer sourceBrandId, Integer targetCompanyId);

    void repairModelDo(ResModel resModel, Integer sourceCompanyId, Integer targetCompanyId, Integer sourceBrandId);

    /**
     * 修复企业的材质数据（数据从原到目标）
     * @param sourceCompanyId   原公司ID
     * @param targetCompanyId   目标公司ID
     * @return
     */
    void repairTexture(Integer sourceCompanyId, Integer targetCompanyId);

    Integer repairTextureDo(ResTexture resTexture, Integer targetCompanyId);
}
