package com.sandu.web.base.controller;

import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseAreaObject;
import com.sandu.common.tool.PinYin4jUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 系统-行政区域Controller
 * @author WangHaiLin
 * @date 2018/5/9  14:32
 */
@Api(tags = "baseArea",description = "行政区域")
@Controller
@RequestMapping("/v1/xzminiprogram/base/basearea")
public class BaseAreaController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[省市区域服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseAreaController.class);

    @Autowired
    private BaseAreaService baseAreaService;

    /**
     * 行政区列表
     * @param pid 父级编码
     * @param levelId 级别
     * @return ResponseEnvelope
     * @throws Exception
     */
    @ApiOperation(value = "行政区列表查询",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "父级编码",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "levelId",value = "级别",required = true,paramType = "query",dataType = "Integer"),
    })
    @ResponseBody
    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public ResponseEnvelope listAll(@RequestParam(value = "pid")String pid,
                                    @RequestParam(value = "levelId")Integer levelId) throws Exception
    {
        //1.校验参数
        if (pid == null||levelId==null) {
            logger.warn(CLASS_LOG_PREFIX + "传参异常，pid OR levelId is null");
            return new ResponseEnvelope(false, "传参异常!");
        }
        //2.参数转换
        BaseArea baseArea = new BaseArea();
        baseArea.setPid(pid);
        baseArea.setLevelId(levelId);

        List<BaseArea> list = new ArrayList<BaseArea>();
        try
        {
            baseArea.setIsDeleted(0);
            logger.info(CLASS_LOG_PREFIX + "查询所有省市区域:{}", gson.toJson(list));
            list = baseAreaService.getList(baseArea);
            logger.info(CLASS_LOG_PREFIX + "查询所有省市区域完成:{}", gson.toJson(list));

            // 省份城市按照字母A~Z排序start
            // 省份城市按照字母A~Z排序start
            logger.info(CLASS_LOG_PREFIX + "对省市进行排序开始");
            List<BaseArea> newBaseArea = deepCopy(list);

            for (int i = 0; i < list.size(); i++)
            {
                if (!com.sandu.common.util.StringUtils.isEmpty(list.get(i).getAreaName()))
                {
                    String pinyin = PinYin4jUtils.hanziToPinyin(list.get(i).getAreaName(), "");

                    // 多音字单独处理
                    if (list.get(i).getAreaName().equals("重庆市"))
                    {
                        list.get(i).setAreaName("chongqing");
                    } else
                    {
                        list.get(i).setAreaName(pinyin);
                    }
                }
            }

            Collections.sort(list, new Comparator<BaseArea>(){
                @Override
                public int compare(BaseArea o1, BaseArea o2)
                {
                    return o1.getAreaName().compareTo(o2.getAreaName());
                }
            });

            for (BaseArea b : list){
                for (BaseArea n : newBaseArea)
                {
                    if (b.getId().intValue() == n.getId().intValue())
                    {
                        b.setAreaName(n.getAreaName());
                    }
                }

            }
            // 省份城市按照字母A~Z排序end

        } catch (Exception e){
            e.printStackTrace();
            logger.error(CLASS_LOG_PREFIX + "查询所有省市区域:{}, Exception:{}.", gson.toJson(list), e.getMessage());
            return new ResponseEnvelope(false, "数据异常!");
        }
        return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVo(list),
                (list == null ? 0 : list.size()));
    }


    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }


    /**
     * 动态获取行政区域列表
     *
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
    @ApiOperation(value = "动态获取行政区列表",response = ResponseEnvelope.class)
    @ResponseBody
    @RequestMapping(value = "/getAllArea", method = RequestMethod.GET)
    public ResponseEnvelope getAllArea() throws Exception
    {
        BaseArea baseArea = new BaseArea();

        List<BaseArea> list = new ArrayList<BaseArea>();

        // 返回列表
        List<BaseArea> nList = new ArrayList<>();
        try
        {
            baseArea.setIsDeleted(0);
            logger.info(CLASS_LOG_PREFIX + "查询所有省市区域:{}", gson.toJson(list));
            list = baseAreaService.getList(baseArea);
            logger.info(CLASS_LOG_PREFIX + "查询所有省市区域完成:{}", gson.toJson(list));

            // 省份城市按照字母A~Z排序start
            // 省份城市按照字母A~Z排序start
            logger.info(CLASS_LOG_PREFIX + "对省市进行排序开始");
            List<BaseArea> newBaseArea = deepCopy(list);

            for (int i = 0; i < list.size(); i++)
            {
                if (!com.sandu.common.util.StringUtils.isEmpty(list.get(i).getAreaName()))
                {
                    String pinyin = PinYin4jUtils.hanziToPinyin(list.get(i).getAreaName(), "");

                    // 多音字单独处理
                    if (list.get(i).getAreaName().equals("重庆市"))
                    {
                        list.get(i).setAreaName("chongqing");
                    } else
                    {
                        list.get(i).setAreaName(pinyin);
                    }
                }
            }

            Collections.sort(list, new Comparator<BaseArea>()
            {
                @Override
                public int compare(BaseArea o1, BaseArea o2)
                {
                    return o1.getAreaName().compareTo(o2.getAreaName());
                }
            });

            for (BaseArea b : list)
            {
                for (BaseArea n : newBaseArea)
                {
                    if (b.getId().intValue() == n.getId().intValue())
                    {
                        b.setAreaName(n.getAreaName());
                    }
                }

            }
            // 省份城市按照字母A~Z排序end

            // 省市区嵌套
            for(BaseArea province : list)
            {
                if(province.getLevelId() == 1)
                {
                    List<BaseArea> cityList = new ArrayList<>();
                    for(BaseArea city : list)
                    {
                        if(city.getLevelId() == 2 && city.getPid().equals(province.getAreaCode()))
                        {
                            List<BaseArea> strictList = new ArrayList<>();
                            for(BaseArea strict : list)
                            {
                                if(strict.getLevelId() == 3 && strict.getPid().equals(city.getAreaCode()))
                                {
                                    // 将区放入市内
                                    strictList.add(strict);
                                }

                            }
                            city.setLowerArea(strictList);
                            // 将市放入省内
                            cityList.add(city);
                        }
                    }
                    province.setLowerArea(cityList);
                    // 将省放入返回列表
                    nList.add(province);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            logger.error(CLASS_LOG_PREFIX + "查询所有省市区域:{}, Exception:{}.", gson.toJson(list), e.getMessage());
            return new ResponseEnvelope(false, "数据异常!");
        }
        return new ResponseEnvelope(true, "ok", BaseAreaObject.convertToBaseAreaVoList(nList));
    }

}
