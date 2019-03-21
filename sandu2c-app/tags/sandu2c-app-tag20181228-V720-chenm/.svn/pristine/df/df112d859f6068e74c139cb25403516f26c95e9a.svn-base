package com.sandu.web.product.controller;


import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.common.util.LoginUserCommonConstant;
import com.sandu.common.util.Utils;
import com.sandu.product.model.CollectCatalog;
import com.sandu.product.model.search.CollectCatalogSearch;
import com.sandu.product.service.CollectCatalogService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/v1/product/collectcatalog")
public class CollectCatalogController {
    @Autowired
    private CollectCatalogService collectCatalogService;

    @Autowired
    private UserSessionService userSessionService;

    /**
     * 收藏目录表列表 接口
     */
    @RequestMapping(value = "/querycollectcataloglist")
    @ResponseBody
    public Object queryCollectCatalogList(
            @ModelAttribute("collectCatalogSearch") CollectCatalogSearch collectCatalogSearch,
            HttpServletRequest request, HttpServletResponse response) {

        LoginUser loginUser = new LoginUser();
        loginUser.setId(175);
        loginUser.setUserType(1);
        if (collectCatalogSearch == null) {
            collectCatalogSearch = new CollectCatalogSearch();
        }
        collectCatalogSearch.setUserId(loginUser.getId());
        collectCatalogSearch.setIsDeleted(0);
        return collectCatalogService.queryCollectCatalogList(collectCatalogSearch, loginUser);
    }

    /**
     * 添加收藏目录
     *
     * @param request
     * @param catalogName
     * @return
     */
 
    @RequestMapping(value = "/addCollectCatalog")
    @ResponseBody
    public Object addCollectCatalog(HttpServletRequest request, String catalogName) {
        CollectCatalog catalog = new CollectCatalog();
        try {
            if (StringUtils.isBlank(catalogName)) {
                return new ResponseEnvelope(false, "目录名称catalogName参数为空！");
            }
            if ("默认".equals(catalogName.trim())) {
                return new ResponseEnvelope(false, "该目录名称无法创建,请重新命名！");
            }
            com.nork.common.model.LoginUser loginUser=LoginContext.getLoginUser(com.nork.common.model.LoginUser.class);
            if(loginUser==null){
                return new ResponseEnvelope(false, "登录失效！");
            }
            sysSave(catalog, loginUser);
            catalog.setCatalogName(catalogName);
            catalog.setUserId(loginUser.getId());
            collectCatalogService.add(catalog);
            return new ResponseEnvelope(true, "", catalog);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "系统异常！");
        }
    }

    /**
     * 删除收藏目录
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/deleteCollectCatalog")
    @ResponseBody
    public Object deleteCollectCatalog(@ModelAttribute("collectCatalog") CollectCatalog collectCatalog,
                                       HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(175);
        loginUser.setUserType(1);
        if (loginUser == null || loginUser.getId().intValue() <= 0) {
            return new ResponseEnvelope(false, "登录失效！");
        }
        if (collectCatalog == null) {
            return new ResponseEnvelope(false, "删除失败,缺少参数id!");
        }
        if (collectCatalog.getId() == null || collectCatalog.getId() <= 0) {
            return new ResponseEnvelope(false, "删除失败,缺少参数id!");
        }
        collectCatalog.setUserId(loginUser.getId());
        return collectCatalogService.deleteCollectCatalog(collectCatalog);
    }

    /**
     * 自动存储系统字段
     */
    private void sysSave(CollectCatalog model, com.nork.common.model.LoginUser loginUser) {
        if (model != null && loginUser != null) {
            if (model.getId() == null) {
                model.setGmtCreate(new Date());
                model.setCreator(loginUser.getLoginName());
                model.setIsDeleted(0);
                if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                    model.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            model.setGmtModified(new Date());
            model.setModifier(loginUser.getLoginName());
        }
    }

}
