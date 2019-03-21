package com.nork.product.controller;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.small.CompanyDispatchInfo;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import com.sandu.common.LoginContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 企业控制层
 * @author WangHaiLin
 * @date 2018/10/23  9:27
 */
@Controller
@RequestMapping("/{style}/product/baseCompany")
public class BaseCompanyController {
    private static Logger logger = Logger.getLogger(BaseCommonController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BaseCompanyService baseCompanyService;


    /**
     * 修改企业 “是否接收派单”
     * @param isReceivePlatformDispatch 平台派单
     * @param isReceiveInteriorDispatch 内部推荐
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setup/dispatch",method = RequestMethod.POST)
    @ResponseBody
    public Object setUpDispatch(Integer isReceivePlatformDispatch ,Integer isReceiveInteriorDispatch) throws Exception  {
        try {
            //获取当前登录用户id
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if(loginUser == null || loginUser.getId() == null) {
                logger.error("企业派遣单设置修改" + "(loginUser == null || loginUser.getId() == null) = true");
                return new ResponseEnvelope<>(false, "请重新登录");
            }
            //获取当前用户完整信息
            SysUser user = sysUserService.get(loginUser.getId());
            //获取当前用户企业Id
            Integer companyId = user.getCompanyId();
            //构造参数
            BaseCompany company=new BaseCompany();
            company.setId(companyId);
            if (null!=isReceiveInteriorDispatch){
                company.setIsReceiveInteriorDispatch(isReceiveInteriorDispatch);
            }
            if (null!=isReceivePlatformDispatch){
                company.setIsReceivePlatformDispatch(isReceivePlatformDispatch);
            }
            company.setModifier(loginUser.getLoginName());
            company.setGmtModified(new Date());
            //修改设置
            int update = baseCompanyService.update(company);
            if (update>0){
                return new ResponseEnvelope<CompanyDispatchInfo>(true,"修改设置成功");
            }
            return new ResponseEnvelope<CompanyDispatchInfo>(false,"修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope<CompanyDispatchInfo>(false, "数据异常!");
        }
    }


    /**
     * create by WangHaiLin
     * 获取企业是否接收派遣单信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get/dispatch",method = RequestMethod.GET)
    @ResponseBody
    public Object getDispatchInfo() throws Exception  {

        try {
            //获取当前登录用户id
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if(loginUser == null || loginUser.getId() == null) {
                logger.error("企业派遣单设置查询" + "(loginUser == null || loginUser.getId() == null) = true");
                return new ResponseEnvelope<>(false, "请重新登录");
            }
            Integer id = loginUser.getId();
            //获取当前用户完整信息
            SysUser user = sysUserService.get(id);
            //获取当前用户企业Id
            Integer companyId = user.getCompanyId();
            //获取当前设置
            CompanyDispatchInfo dispatchInfo = baseCompanyService.getDispatchInfo(companyId);
            logger.error("企业派遣单设置查询" + dispatchInfo);
            if (null==dispatchInfo.getIsReceiveInteriorDispatch()){
                dispatchInfo.setIsReceiveInteriorDispatch(0);
            }
            if (null==dispatchInfo.getIsReceivePlatformDispatch()){
                dispatchInfo.setIsReceivePlatformDispatch(0);
            }
            return new ResponseEnvelope<CompanyDispatchInfo>(true,dispatchInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope<CompanyDispatchInfo>(false, "数据异常!");
        }
    }
}
