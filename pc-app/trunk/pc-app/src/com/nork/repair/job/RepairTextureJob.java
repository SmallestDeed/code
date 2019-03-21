package com.nork.repair.job;


import com.nork.common.util.SpringContextHolder;
import com.nork.repair.service.RepairService;
import com.nork.system.model.ResTexture;

import java.util.concurrent.Callable;

public class RepairTextureJob implements Callable {

    private Integer targetCompanyId;

    private ResTexture resTexture;

    private RepairService repairService = SpringContextHolder.getBean(RepairService.class);

    public RepairTextureJob(ResTexture resTexture, Integer targetCompanyId){
        this.resTexture = resTexture;
        this.targetCompanyId = targetCompanyId;
    }

    @Override
    public Object call() throws Exception {
        repairService.repairTextureDo(resTexture, targetCompanyId);
        return null;
    }

}
