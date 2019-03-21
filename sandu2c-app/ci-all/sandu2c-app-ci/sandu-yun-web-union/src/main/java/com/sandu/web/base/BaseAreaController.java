package com.sandu.web.base;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.service.BaseAreaService;
import com.sandu.cache.service.RedisService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseAreaObject;
import com.sandu.common.tool.PinYin4jUtils;
import com.sandu.common.util.StringUtils;
import com.sandu.platform.BasePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Type;
import java.text.Collator;
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
@RequestMapping("/v1/union/base/basearea")
public class BaseAreaController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[省市区域服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseAreaController.class);

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
    public ResponseEnvelope getAllArea(HttpServletRequest request) throws Exception {


        List<BaseAreaVo> nlist = new ArrayList<>();
        String allAreaListInfo = redisService.getMap("BaseArea", "UnionAreaList");
        if (StringUtils.isEmpty(allAreaListInfo)) {
            try {
                List<BaseAreaVo> list = new ArrayList<>();
                list = baseAreaService.getAreaList(new BaseArea());
                // 省份城市按照字母A~Z排序start
                // 省份城市按照字母A~Z排序start
                logger.info(CLASS_LOG_PREFIX + "对省市进行排序开始");
                List<BaseAreaVo> newBaseArea = deepCopy(list);

                for (int i = 0; i < list.size(); i++) {
                    if (!com.sandu.common.util.StringUtils.isEmpty(list.get(i).getAreaName())) {
                        String pinyin = PinYin4jUtils.hanziToPinyin(list.get(i).getAreaName(), "");

                        // 多音字单独处理
                        if (list.get(i).getAreaName().equals("重庆市")) {
                            list.get(i).setAreaName("chongqing");
                        } else {
                            list.get(i).setAreaName(pinyin);
                        }
                    }
                }
                Collections.sort(list, new Comparator<BaseAreaVo>() {
                    @Override
                    public int compare(BaseAreaVo o1, BaseAreaVo o2) {
                        return o1.getAreaName().compareTo(o2.getAreaName());
                    }
                });

                for (BaseAreaVo b : list) {
                    for (BaseAreaVo n : newBaseArea) {
                        if (b.getId().intValue() == n.getId().intValue()) {
                            b.setAreaName(n.getAreaName());
                        }
                    }

                }
                // 省份城市按照字母A~Z排序end

                // 省市区嵌套
                for (BaseAreaVo province : list) {
                    if (province.getLevelId() == 1) {
                        List<BaseAreaVo> cityList = new ArrayList<>();
                        for (BaseAreaVo city : list) {
                            if (city.getLevelId() == 2 && city.getPid().equals(province.getAreaCode())) {
                                List<BaseAreaVo> strictList = new ArrayList<>();
                                for (BaseAreaVo strict : list) {
                                    if (strict.getLevelId() == 3 && strict.getPid().equals(city.getAreaCode())) {
                                        List<BaseAreaVo> townList = new ArrayList<>();
                                        for (BaseAreaVo town : list) {
                                            if (town.getLevelId() == 4 && town.getPid().equals(strict.getAreaCode())) {
                                                townList.add(town);
                                            }
                                        }
                                        strict.setBaseAreaVos(townList);
                                        // 将区放入市内
                                        strictList.add(strict);
                                    }

                                }
                                city.setBaseAreaVos(strictList);
                                // 将市放入省内
                                cityList.add(city);
                            }
                        }
                        province.setBaseAreaVos(cityList);
                        // 将省放入返回列表
                        nlist.add(province);
                    }
                }
                if (nlist != null && nlist.size() > 0) {
                    redisService.addMap("BaseArea", "UnionAreaList", gson.toJson(nlist));
                    return new ResponseEnvelope(true, "ok", nlist);
                } else {
                    return new ResponseEnvelope(false, "数据异常!");
                }


            } catch (Exception e) {
                e.printStackTrace();
                logger.error(CLASS_LOG_PREFIX + "查询所有省市区域:{}, Exception:{}.", gson.toJson(nlist), e.getMessage());
                return new ResponseEnvelope(false, "数据异常!");
            }
        } else {
            Type type = new TypeToken<List<BaseAreaVo>>() {
            }.getType();
            nlist = gson.fromJson(allAreaListInfo, type);
            return new ResponseEnvelope(true, "ok", nlist);
        }

    }


    /**
     * 同城联盟PC端获取省市区列表
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
    @RequestMapping(value = "/getallareacopy", method = RequestMethod.GET)
    public ResponseEnvelope getAllAreaCopy(HttpServletRequest request) throws Exception {


        List<BaseAreaVo> nlist = new ArrayList<>();
        String allAreaListInfo = redisService.getMap("BaseArea", "UnionPcAreaList");
        if (StringUtils.isEmpty(allAreaListInfo)) {
            try {
                List<BaseAreaVo> list = new ArrayList<>();
                list = baseAreaService.getAreaList(new BaseArea());
                // 省份城市按照字母A~Z排序start
                // 省份城市按照字母A~Z排序start
                logger.info(CLASS_LOG_PREFIX + "对省市进行排序开始");
                List<BaseAreaVo> newBaseArea = deepCopy(list);

                for (int i = 0; i < list.size(); i++) {
                    if (!com.sandu.common.util.StringUtils.isEmpty(list.get(i).getAreaName())) {
                        String pinyin = PinYin4jUtils.hanziToPinyin(list.get(i).getAreaName(), "");

                        // 多音字单独处理
                        if (list.get(i).getAreaName().equals("重庆市")) {
                            list.get(i).setAreaNamePinyin("chongqing");
                        } else {
                            list.get(i).setAreaNamePinyin(pinyin);
                        }
                    }
                }
                Collections.sort(list, new Comparator<BaseAreaVo>() {
                    @Override
                    public int compare(BaseAreaVo o1, BaseAreaVo o2) {
                        if (o1.getAreaName().length() != o2.getAreaName().length()) {
                            return o1.getAreaName().length() - o2.getAreaName().length();
                        } else {
                            return o1.getAreaNamePinyin().compareTo(o2.getAreaNamePinyin());
                        }
                    }
                });

                for (BaseAreaVo b : list) {
                    for (BaseAreaVo n : newBaseArea) {
                        if (b.getId().intValue() == n.getId().intValue()) {
                            b.setAreaName(n.getAreaName());
                        }
                    }

                }
                // 省份城市按照字母A~Z排序end

                // 省市区嵌套
                for (BaseAreaVo province : list) {
                    if (province.getLevelId() == 1) {
                        List<BaseAreaVo> cityList = new ArrayList<>();
                        for (BaseAreaVo city : list) {
                            if (city.getLevelId() == 2 && city.getPid().equals(province.getAreaCode())) {
                                List<BaseAreaVo> strictList = new ArrayList<>();
                                for (BaseAreaVo strict : list) {
                                    if (strict.getLevelId() == 3 && strict.getPid().equals(city.getAreaCode())) {
                                        List<BaseAreaVo> townList = new ArrayList<>();
                                        for (BaseAreaVo town : list) {
                                            if (town.getLevelId() == 4 && town.getPid().equals(strict.getAreaCode())) {
                                                townList.add(town);
                                            }
                                        }
                                        strict.setBaseAreaVos(townList);
                                        // 将区放入市内
                                        strictList.add(strict);
                                    }

                                }
                                city.setBaseAreaVos(strictList);
                                // 将市放入省内
                                cityList.add(city);
                            }
                        }
                        province.setBaseAreaVos(cityList);
                        // 将省放入返回列表
                        nlist.add(province);
                    }
                }
                if (nlist != null && nlist.size() > 0) {
                    redisService.addMap("BaseArea", "UnionPcAreaList", gson.toJson(nlist));
                    return new ResponseEnvelope(true, "ok", nlist);
                } else {
                    return new ResponseEnvelope(false, "数据异常!");
                }


            } catch (Exception e) {
                e.printStackTrace();
                logger.error(CLASS_LOG_PREFIX + "查询所有省市区域:{}, Exception:{}.", gson.toJson(nlist), e.getMessage());
                return new ResponseEnvelope(false, "数据异常!");
            }
        } else {
            Type type = new TypeToken<List<BaseAreaVo>>() {
            }.getType();
            nlist = gson.fromJson(allAreaListInfo, type);
            return new ResponseEnvelope(true, "ok", nlist);
        }

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
            list = baseAreaService.getList2(baseArea);
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

            for (BaseArea b : list) {
                for (BaseArea n : newBaseArea) {
                    if (b.getId().intValue() == n.getId().intValue()) {
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

    @ResponseBody
    @RequestMapping(value = "/getAreaByPid", method = RequestMethod.GET)
    public ResponseEnvelope getAreaByPid(String pid, Integer levelId) {
        if (pid == null || levelId == null) {
            return new ResponseEnvelope(false, "缺失参数");
        }
        if ("0".equals(pid) && levelId == 1) {
            List<BaseArea> provinces = baseAreaService.getAreaByPid(pid, levelId);
            Collections.sort(provinces, (o1, o2) -> {
                if("440000".equals(o1.getAreaCode())) {
                    return -1;
                }
                if("440000".equals(o2.getAreaCode())) {
                    return 1;
                }
                return sortArea(o1, o2);
            });
            List<BaseArea> cities = baseAreaService.getAreaByPid("440000", 2);
            Collections.sort(cities, (o1, o2) -> {
                if("440300".equals(o1.getAreaCode())) {
                    return -1;
                }
                if("440300".equals(o2.getAreaCode())) {
                    return 1;
                }
                return sortArea(o1, o2);
            });
            List<BaseArea> areas = baseAreaService.getAreaByPid("440300", 3);
            Collections.sort(areas, (o1, o2) -> sortArea(o1, o2));
            Map<String, List<BaseArea>> map = new HashMap<>();
            map.put("provinces", provinces);
            map.put("cities", cities);
            map.put("areas", areas);
            return new ResponseEnvelope(true, "查询成功", map);
        } else {
            List<BaseArea> areas = baseAreaService.getAreaByPid(pid, levelId);
            Collections.sort(areas, (o1, o2) -> {
                if("440300".equals(o1.getAreaCode()) || "440000".equals(o1.getAreaCode())) {
                    return -1;
                }
                if("440300".equals(o2.getAreaCode()) || "440000".equals(o2.getAreaCode())) {
                    return 1;
                }
                return sortArea(o1, o2);
            });
            if (levelId == 2) {
                BaseArea city = areas.get(0);
                List<BaseArea> areaList = baseAreaService.getAreaByPid(city.getAreaCode(), 3);
                city.setLowerArea(areaList);
            }
            return new ResponseEnvelope(true, "查询成功", areas);
        }
    }

    private int sortArea(BaseArea o1, BaseArea o2){
        String o1Name = PinYin4jUtils.hanziToPinyin(o1.getAreaName(), "");
        String o2Name = PinYin4jUtils.hanziToPinyin(o2.getAreaName(), "");
        if ("重庆市".equals(o1.getAreaName())){
            o1Name = "chongqingshi";
        }
        if ("重庆市".equals(o2.getAreaName())){
            o2Name = "chongqingshi";
        }
        return o1Name.compareToIgnoreCase(o2Name);
    }
}
