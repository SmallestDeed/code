package com.sandu.web.system.controller;


import com.google.gson.Gson;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;
import com.sandu.system.service.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysDictionaryController.java
 * @Package com.sandu.system.controller
 * @Description:系统管理-数据字典Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
@Controller
@RequestMapping("/v1/tocmobile/system/sysDictionary")
public class SysDictionaryController {
    private static Gson gson = new Gson();
    private static Logger logger = LoggerFactory.getLogger(SysDictionaryController.class);

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    //返回空间类型列表，面积，空间结构
    @RequestMapping(value = "/getHouseTypeList")
    @ResponseBody
    public ResponseEnvelope getHouseTypeList(@ModelAttribute("sysDictionarySearch") SysDictionarySearch sysDictionarySearch,
                                             HttpServletRequest request, HttpServletResponse response) {
        ResponseEnvelope res = null;
        sysDictionarySearch.setIsDeleted(0);
        String type = sysDictionarySearch.getType();
        if (StringUtils.isBlank(type)) {
            res = new ResponseEnvelope(false, "空间类型不能为null!");
        }
        List<SysDictionary> list = new ArrayList<SysDictionary>();
        int total = 0;
        try {
            total = sysDictionaryService.getCount(sysDictionarySearch);
            logger.info("total:" + total);

            if (total > 0) {
                list = sysDictionaryService.getPaginatedList(sysDictionarySearch);
                logger.info("list:" + gson.toJson(list));
            }
            res = new ResponseEnvelope(true, "", list, total);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEnvelope(false, "数据异常!");
        }
        return res;
    }


}
