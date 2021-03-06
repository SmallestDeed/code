package com.sandu.web.product.controller;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.RequestUtils;
import com.sandu.common.util.Utils;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.system.exception.DomainConfigurationException;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseProductController.java
 * @Package com.sandu.product.controller
 * @Description:产品模块-企业Controller
 * @createAuthor pandajun
 * @CreateDate 2017-11-17 17:01:37
 */
@Controller
@RequestMapping("/v1/product/baseCompany")
public class CompanyController {

    private final static Logger logger = LoggerFactory.getLogger(CompanyController.class);
    private final static String CLASS_LOG_PREFIX = "[产品中心服务]";
    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private ResPicService resPicService;
   

    /**
     * 公司详情接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/companyDetails",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope companyDetails(HttpServletRequest request,
                                           HttpServletResponse response) {
    	
    	//接收验证域名参数
        String domainUrl = null;
        try {
            domainUrl = request.getHeader("Referer");
            if(domainUrl==null){
                domainUrl = request.getHeader("Custom-Referer");
            }
        }catch (Exception e){
            domainUrl = request.getHeader("Custom-Referer");
        }
        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(domainUrl);
        //获取企业logo图片地址
        if(baseCompany!=null) {
        	 Map resultMap = new HashMap();
        	 resultMap.put("picPath", "");
        	 resultMap.put("companyName", baseCompany.getCompanyName());
        	 if (baseCompany.getCompanyLogo() != null) {
                 ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
                 if (resPic != null) {
                     resultMap.put("picPath", resPic.getPicPath());
                 }
             }
        	 return new ResponseEnvelope(true, "", resultMap);
        }else {
        	 return new ResponseEnvelope(false, "企业不存在!");
        }

        
    }
}
