package com.nork.repair.controller;

import com.nork.common.model.ResponseEnvelope;
import com.nork.repair.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{style}/web/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    /**
     * 修复企业的模型数据（数据从原到目标）
     * @param sourceCompanyId   原公司ID
     * @param sourceBrandId     原品牌ID
     * @param targetCompanyId   目标公司ID
     * @return
     */
    @RequestMapping("/repairModel")
    @ResponseBody
    public Object repairModel(Integer sourceCompanyId,Integer sourceBrandId, Integer targetCompanyId){
        repairService.repairModel(sourceCompanyId, sourceBrandId, targetCompanyId);
        return new ResponseEnvelope(true, "OK！");
    }

    /**
     * 修复企业的材质数据（数据从原到目标）
     * @param sourceCompanyId   原公司ID
     * @param targetCompanyId   目标公司ID
     * @return
     */
    @RequestMapping("/repairTexture")
    @ResponseBody
    public Object repairTexture(Integer sourceCompanyId, Integer targetCompanyId){
        repairService.repairTexture(sourceCompanyId, targetCompanyId);
        return new ResponseEnvelope(true, "OK！");
    }

}
