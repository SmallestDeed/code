package com.sandu.web.user.controller;

import com.sandu.api.user.model.bo.UserBo;
import com.sandu.api.user.service.UserService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.util.Commoner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandu
 */
@Slf4j
@SuppressWarnings("unchecked")
@RestController
@Api(tags = "user", description = "用户")
@RequestMapping("/v1/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @GetMapping("/companyInfo")
    @ApiOperation(value = "根据用户获取企业ID/品牌ID")
    public ReturnData getCompanyInfo(Integer id) {
        if (Commoner.isEmpty(id)) {
            return ReturnData.builder().success(false).message("企业ID有误");
        }
        UserBo user = userService.getUserById(id);
        if (user != null) {
            Map map = new HashMap();
            map.put("companyId", user.getCompanyId());
            return ReturnData.builder().success(true).data(map);
        }
        return ReturnData.builder().success(false).message("未获取到相关公司信息");
    }
}
