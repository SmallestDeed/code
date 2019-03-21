package com.sandu.web.supply;


import com.sandu.api.commom.JsonDataModel;
import com.sandu.api.commom.JsonListModel;
import com.sandu.api.supply.input.SupplyDemandAdd;
import com.sandu.api.supply.model.user.LoginUser;
import com.sandu.api.supply.service.SupplyDemandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/4/27 0027PM 5:37
 */

@RestController
@RequestMapping("/v1/supplydemand")
public class SupplyDemandController {
    private final static String CLASS_LOG_PREFIX = "[供求基础服务]";

    @Autowired
    private SupplyDemandService supplyDemandService;

    @RequestMapping(value = "/getallsupplydemandinfo")
    @ResponseBody
    public JsonListModel getAllSupplyDemandInfo(HttpServletRequest request) {


        return null;

    }

    @RequestMapping(value = "/addsupplydemand")
    public JsonDataModel addSupplyDemandInfo(@RequestParam(value = "file", required = false) MultipartFile[] files,
                                             SupplyDemandAdd SupplyDemandAdd) {
        if (SupplyDemandAdd == null || SupplyDemandAdd.getType() == null || SupplyDemandAdd.getCategory() == null ||
                StringUtils.isEmpty(SupplyDemandAdd.getProvince()) || StringUtils.isEmpty(SupplyDemandAdd.getCity()) ||
                StringUtils.isEmpty(SupplyDemandAdd.getAddress()) || StringUtils.isEmpty(SupplyDemandAdd.getDistrict()) ||
                StringUtils.isEmpty(SupplyDemandAdd.getTitle()) || StringUtils.isEmpty(SupplyDemandAdd.getDescription())) {
            return new JsonDataModel("参数缺失");
        }
        //获取当前登录用户，暂时sso没调好
        LoginUser loginUser = new LoginUser();

        //service处理所有的业务逻辑
        int id = supplyDemandService.add(SupplyDemandAdd,loginUser,files);


        return null;
    }
}
