package com.sandu.web.user;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.product.model.BaseCompany;
import com.sandu.user.model.AreaInfoVo;
import com.sandu.user.model.CompanyShopVo;
import com.sandu.user.model.UserRoleContants;
import com.sandu.user.model.UserVo;
import com.sandu.user.model.view.UserInfoVo;
import com.sandu.user.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version V1.0
 * @Title: BaseProductController.java
 * @Package com.sandu.product.controller
 * @Description:产品模块-企业Controller
 * @createAuthor pandajun
 * @CreateDate 2017-11-17 17:01:37
 */
@Controller
@RequestMapping("/v1/union/baseCompany")
public class CompanyController {

    private final static Logger logger = LoggerFactory.getLogger(CompanyController.class);
    private final static String CLASS_LOG_PREFIX = "[公司服务]";

    @Autowired
    private SysUserService sysUserService;


    /**
     * @Title: 获得用户详情
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getUserInfo(@RequestParam(required = true) Integer friendId, @RequestParam(required = true) Integer type, HttpServletRequest request) {
        if (friendId == null || friendId == 0) {
            return new ResponseEnvelope(false, "缺失参数friendId");
        }
        UserVo userInfo = sysUserService.getUserInfo(friendId);
        if (userInfo == null) {
            return new ResponseEnvelope(false, "无法获取到用户");
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserName(userInfo.getUserName());
        userInfoVo.setUserPicPath(userInfo.getPicPath());
        userInfoVo.setUserId(userInfo.getUserId());
        if (StringUtils.isNotBlank(userInfo.getAreaLongCode())) {
            List<String> areaCodes = Utils.getListFromStr(userInfo.getAreaLongCode());
            List<AreaInfoVo> areaInfoList = sysUserService.getAreaInfo(areaCodes);
            if (areaInfoList != null && areaInfoList.size() > 0) {
                for (AreaInfoVo areaInfoVo : areaInfoList) {
                    if (areaInfoVo.getLevelId() == 1) {
                        userInfoVo.setProvinceName(areaInfoVo.getAreaName());
                    } else if (areaInfoVo.getLevelId() == 2) {
                        userInfoVo.setCityName(areaInfoVo.getAreaName());
                    } else if (areaInfoVo.getLevelId() == 3) {
                        userInfoVo.setAreaName(areaInfoVo.getAreaName());
                    }

                }


            }
            Integer userType = userInfo.getUserType();
            //根据不同的入口和不同的角色显示的信息不同
            if (userType != null && userType != 0 && type == 1) {
                switch (userType.intValue()) {
                    //装修公司,门店,设计公司采用公司名称,区域,log,简介
                    case UserRoleContants.DECORATE_COMPANY:
                    case UserRoleContants.DEALERS:
                    case UserRoleContants.DESIGNER_COMPANY:
                        BaseCompany companyInfo = sysUserService.getCompanyInfo(friendId);
                        if (companyInfo != null) {
                            userInfoVo.setUserName(companyInfo.getCompanyName());
                            userInfoVo.setUserPicPath(companyInfo.getCompanyLogoPicPath());
                            userInfoVo.setUserDetails(companyInfo.getCompanyDesc());
                            userInfoVo.setProvinceName(companyInfo.getProvinceName());
                            userInfoVo.setCityName(companyInfo.getCityName());
                            userInfoVo.setAreaName(companyInfo.getAreaName());
                            userInfoVo.setStreetName(companyInfo.getStreetName());
                        }
                        break;
                    default:
                        ;
                }

            } else if (userType != null && userType != 0 && type == 2) {
                //装修公司,设计公司采用店铺名称,区域,log,简介
                switch (userType.intValue()) {
                    case UserRoleContants.DECORATE_COMPANY:
                    case UserRoleContants.DESIGNER_COMPANY:
                        CompanyShopVo companyShop;
                        List<CompanyShopVo> companyShopVoList = sysUserService.getCompanyShop(friendId);
                        if(companyShopVoList != null && companyShopVoList.size() >0) {
                            companyShop = companyShopVoList.get(0);
                            userInfoVo.setUserName(companyShop.getCompanyShopName());
                            userInfoVo.setUserPicPath(companyShop.getCompanyShopPicPath());
                            userInfoVo.setUserDetails(companyShop.getCompanyShopDesc());
                            userInfoVo.setProvinceName(companyShop.getProvinceName());
                            userInfoVo.setCityName(companyShop.getCityName());
                            userInfoVo.setAreaName(companyShop.getAreaName());
                            userInfoVo.setStreetName(companyShop.getStreetName());
                        }

                    //门店采用公司名称,区域,log,简介
                    case UserRoleContants.DEALERS:
                        BaseCompany companyInfo = sysUserService.getCompanyInfo(friendId);
                        if (companyInfo != null) {
                            userInfoVo.setUserName(companyInfo.getCompanyName());
                            userInfoVo.setUserPicPath(companyInfo.getCompanyLogoPicPath());
                            userInfoVo.setUserDetails(companyInfo.getCompanyDesc());
                            userInfoVo.setProvinceName(companyInfo.getProvinceName());
                            userInfoVo.setCityName(companyInfo.getCityName());
                            userInfoVo.setAreaName(companyInfo.getAreaName());
                            userInfoVo.setStreetName(companyInfo.getStreetName());
                        }
                        break;


                }

            }


        }
        return new ResponseEnvelope(true, "", userInfoVo);
    }


}
