package com.sandu.web.base.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.base.BaseLivingObject;
import com.sandu.common.tool.EscapeUnescape;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.home.model.BaseHouseResult;
import com.sandu.home.model.SpaceCommonStatus;
import com.sandu.home.service.BaseHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统-小区Controller
 * @author WangHaiLin
 * @date 2018/5/9  14:44
 */
@Api(tags = "baseLiving",description = "小区搜索")
@Controller
@RequestMapping("/v1/xzminiprogram/base/baseliving")
public class BaseLivingController {

    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[小区搜索服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseLivingController.class);

    @Autowired
    private BaseHouseService baseHouseService;
    @Autowired
    private BaseAreaService baseAreaService;
    @InitBinder

    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * 小区列表搜索接口
     * @param livingName 小区名称
     * @param cityCode 区域市编码
     * @param pageSize 每页数量
     * @param curPage 当前页
     * @return ResponseEnvelope
     */
    @ApiOperation(value = "小区列表查询",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "livingName",value = "小区名称",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "cityCode",value = "区域市编码",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页数量",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "curPage",value = "当前页",paramType = "query",dataType = "Integer"),
    })
    @RequestMapping(value = "/getlvinglist",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope houseSearch(@RequestParam(value = "livingName") String livingName,
                                        @RequestParam(value = "cityCode") String cityCode,
                                        @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                        @RequestParam(value = "curPage",required = false) Integer curPage) {
        //1.校验数据
        BaseHouseResult baseHouseResult=new BaseHouseResult();
        baseHouseResult.setCityCode(Strings.nullToEmpty(cityCode));
        baseHouseResult.setLivingName(EscapeUnescape.unescape(livingName));
        //确定页面大小
        if (null != pageSize && 0 != pageSize) {
            baseHouseResult.setLimit(pageSize);
        } else {
            baseHouseResult.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        //确定起始位置
        if(null!=curPage && 0!=curPage){
            baseHouseResult.setStart((curPage-1)*pageSize);
        }else{
            baseHouseResult.setStart(0);
        }
        List<BaseHouseResult> list = new ArrayList<BaseHouseResult>();
        int total = 0;

        String areaCode_p = baseHouseResult.getProvinceCode();
        String areaCode_c = baseHouseResult.getCityCode();
        StringBuffer areaLongCode = new StringBuffer();
        if (StringUtils.isNotBlank(areaCode_p)) {
            areaLongCode.append("." + areaCode_p);
        }
        if (StringUtils.isNotBlank(areaCode_c)) {
            areaLongCode.append("." + areaCode_c);
        }
        if (StringUtils.isNotBlank(areaLongCode.toString())) {
            areaLongCode.append(".");
        }
        baseHouseResult.setAreaLongCode(areaLongCode.toString());
        try {
            this.internalPermissions(baseHouseResult);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
            total = baseHouseService.getHouseCount(baseHouseResult);
            if(total>0) {
                list = baseHouseService.getHouseList(baseHouseResult);
            }

            if (CustomerListUtils.isNotEmpty(list)) {
                for (BaseHouseResult houseResult : list) {
                    StringBuffer areaName = new StringBuffer();
                    String areaCode = houseResult.getAreaLongCode();
                    if (!StringUtils.isEmpty(areaCode)) {
                        if (areaCode.contains(".")) {
                            String area[] = areaCode.split("\\.");
                            BaseArea baseArea = new BaseArea();
                            for (int i = 0; i < area.length; i++) {
                                baseArea.setAreaCode(area[i]);
                                List<BaseArea> areaList = null;
                                areaList = baseAreaService.getList(baseArea);
                                if (CustomerListUtils.isNotEmpty(areaList)) {
                                    areaName.append(areaList.get(0).getAreaName());
                                }
                                // 取区名
                                while (i == area.length - 1) {
                                    if (CustomerListUtils.isNotEmpty(areaList)) {
                                        houseResult.setDistrictName(areaList.get(0).getAreaName());
                                    }
                                    break;
                                }
                            }
                        } else {
                            BaseArea baseArea = new BaseArea();
                            baseArea.setAreaCode(areaCode);
                            List<BaseArea> areaList = null;
                            areaList = baseAreaService.getList(baseArea);
                            if (CustomerListUtils.isNotEmpty(areaList)) {
                                areaName.append(areaList.get(0).getAreaName());
                            }
                        }
                        houseResult.setAreaName(areaName.toString());
                    } else {
                        houseResult.setAreaName("未知");
                    }
                }
            }

            // 户型搜索结果应按照开头字母顺序排序
            if (null != list && list.size() > 0) {
                List<String> livingNames = new ArrayList<>();
                for (BaseHouseResult baseHouseResult2 : list) {
                    livingNames.add(null == baseHouseResult2.getLivingName() ? "该小区没有名字" : baseHouseResult2.getLivingName());
                }
                Comparator<Object> com = Collator.getInstance(Locale.CHINA);
                Collections.sort(livingNames, com);
                List<BaseHouseResult> list2 = new ArrayList<BaseHouseResult>();
                for (String str : livingNames) {
                    for (BaseHouseResult baseHouseResult2 : list) {
                        if (baseHouseResult2.getLivingName().equals(str)) {
                            list2.add(baseHouseResult2);
                        }
                    }
                }
                list = list2;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "数据异常!");
        }
        return new ResponseEnvelope(true, "success", BaseLivingObject.convertTobaseLivingVo(list), total);
    }

    /**
     * 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
     * @param baseHouseResult
     */
    public void internalPermissions(BaseHouseResult baseHouseResult) {
        Integer spaceCommonStatusList[] = null;// 存放空间状态的list 用于in 查询
        Integer designTempletPutawayStateList[] = null; // 存放样板房状态的list 用于in 查询
        spaceCommonStatusList = new Integer[] { SpaceCommonStatus.IS_RELEASE };// 只查询状态为发布中的空间
        designTempletPutawayStateList = new Integer[] { DesignTempletPutawayState.IS_RELEASE };// 只查询状态为发布中的样板房
        baseHouseResult.setSpaceCommonStatusList(spaceCommonStatusList);
        baseHouseResult.setDesignTempletPutawayStateList(designTempletPutawayStateList);
    }
}
