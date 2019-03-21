package com.sandu.web.base.controller;

import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseAreaObject;
import com.sandu.common.tool.PinYin4jUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version V1.0
 * @Title: BaseAreaController.java
 * @Package com.sandu.system.controller
 * @Description:系统-行政区域Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 15:31:09
 */
@Controller
@RequestMapping("/v1/base/basearea")
public class BaseAreaController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[省市区域服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseAreaController.class);

    @Autowired
    private BaseAreaService baseAreaService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 行政区域全部列表
     */
    @ResponseBody
    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public ResponseEnvelope listAll(@ModelAttribute BaseAreaVo baseAreaVo
            , HttpServletRequest request) throws Exception {
        if (baseAreaVo == null) {
            logger.warn(CLASS_LOG_PREFIX + "传参异常，baseAreaVo is null");
            return new ResponseEnvelope(false, "传参异常!");
        }
        BaseArea baseArea = BaseAreaObject.parseToBaseArea(baseAreaVo);

        List<BaseArea> list = new ArrayList<BaseArea>();
        try {
            baseArea.setIsDeleted(0);
            logger.info(CLASS_LOG_PREFIX + "查询所有省市区域:{}", gson.toJson(list));
            list = baseAreaService.getList(baseArea);
            logger.info(CLASS_LOG_PREFIX + "查询所有省市区域完成:{}", gson.toJson(list));
            
            //省份城市按照字母A~Z排序start
            //省份城市按照字母A~Z排序start
            logger.info(CLASS_LOG_PREFIX + "对省市进行排序开始");
            List<BaseArea> newBaseArea = deepCopy(list);

            for (int i = 0; i < list.size(); i++) {
                if (!com.sandu.common.util.StringUtils.isEmpty(list.get(i).getAreaName())) {
                    String pinyin = PinYin4jUtils.hanziToPinyin(list.get(i).getAreaName(), "");

                    //多音字单独处理
                    if (list.get(i).getAreaName().equals("重庆市")) {
                        list.get(i).setAreaName("chongqing");
                    } else {
                        list.get(i).setAreaName(pinyin);
                    }
                }
            }

            Collections.sort(list, new Comparator<BaseArea>() {
                @Override
                public int compare(BaseArea o1, BaseArea o2) {
                    return o1.getAreaName().compareTo(o2.getAreaName());
                }
            });

            for(BaseArea b : list){
                for(BaseArea n : newBaseArea){
                    if(b.getId().intValue()==n.getId().intValue()){
                        b.setAreaName(n.getAreaName());
                    }
                }

            }
          //省份城市按照字母A~Z排序end
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(CLASS_LOG_PREFIX + "查询所有省市区域:{}, Exception:{}.", gson.toJson(list), e.getMessage());
            return new ResponseEnvelope(false, "数据异常!");
        }
        return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVo(list), (list == null ? 0 : list.size()));
    }


    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}
