package com.sandu.task.refresh.controller;

import com.sandu.api.task.refresh.service.RefreshDoorPositionService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 3:01 2018/7/5 0005
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/v1/refresh/doorposition")
@Api(tags = "修复数据", description = "RefreshDoorPositionTask")
public class RefreshDoorPositionTask extends BaseController {

    @Autowired
    private RefreshDoorPositionService refreshDoorPositionService;

    private final String KEY = "fajpokjoKDJKF6654654hdhjifdiIHJHOIH444";

    @GetMapping("/refreshTask")
    public ReturnData refreshDoorPosition(String key) {

        ReturnData response = ReturnData.builder();
        boolean status = false;
        response.setStatus(status);

        if (StringUtils.isBlank(key)) {
            response.message("秘钥为空");
            return response;
        }

        if (!KEY.equals(key)) {
            response.message("秘钥不匹配");
            return response;
        }

        try {
            status = refreshDoorPositionService.refreshDoorPosition();
        } catch (Exception e) {
            log.error("RefreshDoorPositionTask =====> exception:{}", e);
        }

        response.status(status);
        response.message("finish ! result:"+status);
        return response;
    }

}
