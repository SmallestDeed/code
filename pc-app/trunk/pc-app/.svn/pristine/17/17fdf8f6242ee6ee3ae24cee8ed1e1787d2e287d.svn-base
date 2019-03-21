package com.nork.repair.job;


import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.Utils;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.repair.service.RepairService;
import com.nork.system.model.ResModel;
import com.nork.system.service.ResModelService;

import java.util.Date;
import java.util.concurrent.Callable;

public class RepairModelJob implements Callable {

    private Integer sourceCompanyId;

    private Integer sourceBrandId;

    private Integer targetCompanyId;

    private Integer targetBrandId;

    private ResModel resModel;

    private RepairService repairService = SpringContextHolder.getBean(RepairService.class);

    public RepairModelJob(ResModel resModel, Integer sourceCompanyId, Integer sourceBrandId, Integer targetCompanyId){
        this.resModel = resModel;
        this.sourceCompanyId = sourceCompanyId;
        this.sourceBrandId = sourceBrandId;
        this.targetCompanyId = targetCompanyId;
    }

    public RepairModelJob(ResModel resModel, Integer sourceCompanyId, Integer sourceBrandId, Integer targetCompanyId, Integer targetBrandId){
        this.resModel = resModel;
        this.sourceCompanyId = sourceCompanyId;
        this.sourceBrandId = sourceBrandId;
        this.targetCompanyId = targetCompanyId;
        this.targetBrandId = targetBrandId;
    }

    @Override
    public Object call() throws Exception {
        repairService.repairModelDo(resModel, sourceCompanyId, targetCompanyId, sourceBrandId);
        return null;
    }

}
