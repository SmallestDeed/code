package com.sandu.web.home.controller;

import com.google.gson.Gson;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.home.BaseHouseObject;
import com.sandu.common.util.collections.Lists;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.SpaceCommonStatus;
import com.sandu.home.model.search.BaseHouseSearch;
import com.sandu.home.model.vo.ExpandHouseVo;
import com.sandu.home.service.BaseHouseService;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserSessionService;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 户型类型接口
 * @author WangHaiLin
 * @date 2018/5/10  14:45
 */
@Api(tags = "basehouse",description = "户型类型")
@Controller
@RequestMapping("/v1/xzminiprogram/home/basehouse")
public class BaseHouseController {
    private final static String CLASS_LOG_PREFIX = "[户型搜索服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseHouseController.class);
    private static Gson gson = new Gson();
    @Autowired
    private BaseHouseService baseHouseService;
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private ResHousePicService resHousePicService;
    @Autowired
    private HouseSpaceNewService houseSpaceNewService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 户型类型搜索
     * @param livingId
     * @param pageSize
     * @param curPage
     * @return
     */
    @ApiOperation(value = "户型类型搜索",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页数量",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "curPage",value = "当前页",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "livingId",value = "推荐方案Id",paramType = "query",dataType = "Integer",required = true)
    })
    @RequestMapping(value = "/getbasehouselist",method = RequestMethod.GET)
    @ResponseBody
    public Object houseList(@RequestParam(value = "livingId") String livingId,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                            @RequestParam(value = "curPage", required = false) Integer curPage){
        if (StringUtils.isBlank(livingId)) {
            return new ResponseEnvelope(false, "参数livingId不能为空");
        }
        BaseHouseSearch baseHouseSearch = new BaseHouseSearch();
        List<BaseHouse> list = new ArrayList<BaseHouse>();
        int total = 0;
        try {
            baseHouseSearch.setLivingId(Integer.parseInt(livingId));
            if (null != pageSize && 0 != pageSize) {
                baseHouseSearch.setLimit(pageSize);
            } else {
                baseHouseSearch.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
            }
            if(null!=curPage && 0!=curPage){
                baseHouseSearch.setStart((curPage-1)*pageSize);
            }else{
                baseHouseSearch.setStart(0);
            }
            this.internalPermissions(baseHouseSearch);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
            total = baseHouseService.getHaveSpaceCount(baseHouseSearch);
            if (total > 0) {
                list = baseHouseService.getHaveSpaceList(baseHouseSearch);
                for (BaseHouse baseHouse : list) {
                    // 取户型的缩略图和大图
                    if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() != 0) {
                        ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
                        if (null != resHousePic) {
                            baseHouse.setThumbnailPath(resHousePic.getThumbnailPath());
                            baseHouse.setLargeThumbnailPath(resHousePic.getLargeThumbnailPath());
                        }
                    }else if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
                        // 截图图片处理 v3.9.4(draw-v1.0.2)
                        ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
                        baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                        baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                    }

                    // 取户型的空间定位类型
                    List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
                    logger.info(CLASS_LOG_PREFIX + "通过户型ID获取该户型下所有的空间类型：{}", gson.toJson(spaceTypeList));
                    if (Lists.isEmpty(spaceTypeList)) {
                        continue;
                    }
                    Map<String, Integer> elementsCount = new HashMap<String, Integer>();
                    for (String s : spaceTypeList) {
                        Integer i = elementsCount.get(s);
                        if (i == null) {
                            elementsCount.put(s, 1);
                        } else {
                            elementsCount.put(s, i + 1);
                        }
                    }
                    baseHouse.setHouseTypeStr(((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
                            + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室"
                            + (elementsCount.containsKey("6") ? elementsCount.get("6") : 0) + "卫"
                            + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨"
                            + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0) + "书"
                            + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0) + "衣"
                            + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "数据异常!");
        }
        return new ResponseEnvelope(true, "success", BaseHouseObject.convertTobaseLivingVo(list), total);
    }


    /**
     * 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
     *
     * @param baseHouseSearch
     */
    public void internalPermissions(BaseHouseSearch baseHouseSearch) {
        Integer spaceCommonStatusList[] = null;// 存放空间状态的list 用于in 查询
        Integer designTempletPutawayStateList[] = null; // 存放样板房状态的list 用于in 查询
        spaceCommonStatusList = new Integer[]{SpaceCommonStatus.IS_RELEASE};
        designTempletPutawayStateList = new Integer[]{DesignTempletPutawayState.IS_RELEASE};
        baseHouseSearch.setSpaceCommonStatusList(spaceCommonStatusList);
        baseHouseSearch.setDesignTempletPutawayStateList(designTempletPutawayStateList);
    }


    /**
     * 获取购买户型的相关配置
     * @return
     */
    @ApiOperation(value = "获取购买户型的相关配置",response = ResponseEnvelope.class)
    @RequestMapping(value = "/getexpandhousedict",method = RequestMethod.GET)
    @ResponseBody
    public Object getExpandHouseDict(){
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setIsDeleted(0);
        sysDictionary.setOrders(" ordering asc ");
        sysDictionary.setType(SysDictionaryConstant.EXPAND_HOUSE_TYPE);
        List<SysDictionary> list = sysDictionaryService.getList(sysDictionary);
        if (list == null || list.isEmpty()) {
            logger.info("未找到购买户型的相关的字典配置, sysDictionary ==> {}", sysDictionary);
            return new ResponseEnvelope(false, "未找到购买户型的相关的字典配置");
        }

        List<ExpandHouseVo> exList = new ArrayList<ExpandHouseVo>();
        list.forEach(dict -> {
            ExpandHouseVo vo = new ExpandHouseVo();
            vo.setId(dict.getId());
            vo.setType(dict.getType());
            vo.setValuekey(dict.getValuekey());
            vo.setValue(dict.getValue());
            vo.setExpandNumber(Integer.parseInt(dict.getAtt2()));
            vo.setExpandIntegral(Double.parseDouble(dict.getAtt1()));
            exList.add(vo);
        });
        return new ResponseEnvelope(true, "success", exList);
    }


    @ApiOperation(value = "根据渲染方案副本主键集合查询所关联的房型信息",response = ResponseEnvelope.class)
    @ApiImplicitParam(name = "sceneIds",value = "渲染方案副本主键集合",paramType = "query",dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/queryBaseHouse",method = RequestMethod.GET)
    public Object listBaseHouseBySceneIds(@RequestParam(value = "sceneIds") String sceneIds){
        logger.debug("Process in BaseHouseController.listBaseHouseBySceneIds method parameter sceneIds:{}", sceneIds);
        List<BaseHouse> baseHouseList = null;
        if(StringUtils.isEmpty(sceneIds)){
            return new ResponseEnvelope(true, "success", baseHouseList);
        }
        List<String> ids = Arrays.asList(sceneIds.split(","));
        baseHouseList = baseHouseService.listBaseHouseByDesignPLanReanderSceneId(ids);
        return new ResponseEnvelope(true, "success", baseHouseList);
    }



    /***
     * 获取一个户型中所包含的 空间的类型
     * @return
     */
    @ApiOperation(value = "获取一个户型中所包含的 空间的类型",response = ResponseEnvelope.class)
    @ApiImplicitParam(name = "houseId",value = "户型Id",paramType = "query",required = true,dataType = "Integer")
    @RequestMapping(value = "/getSpaceNameInHouse",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getSpaceNameInHouse(@RequestParam("houseId")Integer houseId) {
        List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeByHouseId(houseId);
        return new ResponseEnvelope(true,spaceTypeList);
    }
}
