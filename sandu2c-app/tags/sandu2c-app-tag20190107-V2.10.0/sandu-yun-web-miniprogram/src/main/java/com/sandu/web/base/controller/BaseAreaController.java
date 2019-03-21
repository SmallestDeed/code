package com.sandu.web.base.controller;

import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.cache.service.RedisService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseAreaObject;
import com.sandu.common.util.StringUtils;
import com.sandu.listener.BaseAreaInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: BaseAreaController.java
 * @Package com.sandu.system.controller
 * @Description:系统-行政区域Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 15:31:09
 */
@Controller
@RequestMapping("/v1/miniprogram/base/basearea")
public class BaseAreaController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[省市区域服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseAreaController.class);


    @Autowired
    private BaseAreaInit baseAreaInit;
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private RedisService redisService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * 动态获取行政区域列表
     * <p>
     * +--A省
     * |  |
     * |  +--A市
     * |  |  |
     * |  |  +--A区
     * |  |  |
     * |  |  +--B区
     * |  |
     * |  +--B市
     * |
     * +--B省
     */
    @ResponseBody
    @RequestMapping(value = "/getAllArea", method = RequestMethod.GET)
    public ResponseEnvelope getAllArea(String type, HttpServletRequest request) throws Exception {
        long nowTime = System.currentTimeMillis();

        List<BaseArea> nList = baseAreaInit.getListData();
        String areaList = redisService.getMap("BaseArea", "MiniProgramAreaList");
        if (nList.size() > 0) {
            areaList = "not empty";
        }
        //从缓存取数据,并且type为空
        if (StringUtils.isNotBlank(areaList) && type == null) {
            logger.info("查询省市区所需时间(单位为毫秒)"+(System.currentTimeMillis()-nowTime));
            return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVoList(nList));
            //从缓存取数据,并且type为allCity
        } else if (StringUtils.isNotBlank(areaList) && type.equals("allCity")) {
            for (BaseArea provice : nList) {
                for (BaseArea city : provice.getLowerArea()) {
                    BaseArea allCity = new BaseArea();
                    allCity.setAreaName("全市");
                    city.getLowerArea().add(0, allCity);
                }
            }
            logger.info("查询省市区所需时间(单位为毫秒)"+(System.currentTimeMillis()-nowTime));
            return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVoList(nList));
            //从数据库取数据,并且type为null
        } else if (StringUtils.isEmpty(areaList) && type == null) {
            nList = baseAreaService.getNewAreaList();
            if (nList != null && nList.size() > 0) {
                redisService.addMap("BaseArea", "MiniProgramAreaList", gson.toJson(nList));
            } else {
                return new ResponseEnvelope(false, "无法获取到省市区信息!");
            }
            logger.info("查询省市区所需时间(单位为毫秒)"+(System.currentTimeMillis()-nowTime));
            return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVoList(nList));
            //从数据库取数据,并且type为allCity
        } else if (StringUtils.isEmpty(areaList) && type.equals("allCity")) {
            nList = baseAreaService.getNewAreaList();
            for (BaseArea provice : nList) {
                for (BaseArea city : provice.getLowerArea()) {
                    BaseArea allCity = new BaseArea();
                    allCity.setAreaName("全市");
                    city.getLowerArea().add(0, allCity);
                }
            }
            if (nList != null && nList.size() > 0) {
                redisService.addMap("BaseArea", "MiniProgramAreaList", gson.toJson(nList));
            } else {
                return new ResponseEnvelope(false, "无法获取到省市区信息!");
            }
            logger.info("查询省市区所需时间(单位为毫秒)"+(System.currentTimeMillis()-nowTime));
            return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVoList(nList));

        }

        return new ResponseEnvelope(false, "数据异常,传参错误");
    }


}