package com.sandu.web.home.controller;

import com.google.gson.Gson;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.home.BaseSpaceCommonObject;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.home.model.HouseSpaceResult;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.model.SpaceCommonStatus;
import com.sandu.home.service.SpaceCommonService;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.ResPicService;
import com.sandu.system.service.SysDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 户型房型-通用空间表Controller
 * @author WangHaiLin
 * @date 2018/5/9  14:22
 */
@Api(tags = "spaceCommon",description = "户型房型-通用空间")
@Controller
@RequestMapping("/v1/xzminiprogram/home/spacecommon")
public class SpaceCommonController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[空间布局展示服务]";
    private static Logger logger = LoggerFactory.getLogger(SpaceCommonController.class);

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private ResPicService resPicService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private DesignTempletService designTempletService;


    /**
     * 通过户型搜索空间布局图
     * @param houseId 户型ID
     * @return
     */
    @ApiOperation(value = "通过户型搜索空间布局图",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseId",value = "户型Id",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "spaceFunctionId",value = " 空间类型ID",paramType = "query",dataType = "String")
    })
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/housespacelist",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope houseSpaceList(@RequestParam(value = "houseId") Integer houseId,
                                           @RequestParam(value = "spaceFunctionId",required = false)String spaceFunctionId) {
        String msg = "";
        if (houseId == null || houseId == 0) {
            msg = "参数houseId不能为空!";
            logger.warn(CLASS_LOG_PREFIX + "houseId不能为空,houseId is null");
            return new ResponseEnvelope(false, msg);
        }
        int total = 0;
        List<HouseSpaceResult> list = null;
        SpaceCommon spaceCommonTemporary = new SpaceCommon();
        try {
            //2c移动端这边加一个参数过滤 -->star
            if(!"".equals(spaceFunctionId) && spaceFunctionId != null) {
                Integer spaceTypeValue = Integer.valueOf(spaceFunctionId);
                spaceCommonTemporary.setSpaceFunctionId(spaceTypeValue);
            }
            // 2c移动端这边加一个参数过滤  --> end
            spaceCommonTemporary.setHouseId(houseId);
            spaceCommonTemporary.setIsDeleted(0);
            logger.info(CLASS_LOG_PREFIX + "通过户型查找的空间总数:{}", total);
            total = spaceCommonService.getHouseSpaceListCount(spaceCommonTemporary);
            logger.info(CLASS_LOG_PREFIX + "通过户型查找的空间总数完成:{}", total);
			/* 设置分页查询条件 */
			/* 设置分页查询条件->end */
            if (total > 0) {
                logger.info(CLASS_LOG_PREFIX + "通过户型查找的空间数据:{}", gson.toJson(list));
                list = spaceCommonService.getPaginatedHouseSpaceList(spaceCommonTemporary);
                logger.info(CLASS_LOG_PREFIX + "通过户型查找的空间数据完成:{}", gson.toJson(list));
            }
            if (CustomerListUtils.isNotEmpty(list)) {
                for (HouseSpaceResult houseSpace : list) {
                    SpaceCommon spaceCommon = spaceCommonService.get(houseSpace.getSpaceId());
                    logger.info(CLASS_LOG_PREFIX + "通过空间查找的布局数据:{}", gson.toJson(spaceCommon));
                    List<DesignTemplet> designTemplets = this.getDesignTempletsFromSpaceCommon(spaceCommon.getId());
                    logger.info(CLASS_LOG_PREFIX + "通过空间查找的布局数据完成:{}", gson.toJson(designTemplets));
                    houseSpace.setDesignTemplets(designTemplets);
                    SysDictionary sysDictionary = new SysDictionary();
                    sysDictionary.setType("houseType");
                    sysDictionary.setValue(houseSpace.getSpaceFunctionId());
                    List<SysDictionary> sdList = sysDictionaryService.getList(sysDictionary);
                    logger.info(CLASS_LOG_PREFIX + "从数据字典查询空间的名称完成:{}", gson.toJson(sdList));
                    if (sdList!=null&&sdList.size() > 0) {
                        houseSpace.setFunctionAreas(sdList.get(0).getName() + houseSpace.getSpaceAreas() + "平");
                    }
					/* 首页图 */
                    SpaceCommon spaceCommon2 = spaceCommonService.setPicParams(spaceCommon);
                    if(null!=spaceCommon2) {
                        houseSpace.setViewPlanPath(spaceCommon2.getViewPlanPath());
                        houseSpace.setViewPlanSmallPath(spaceCommon2.getViewPlanSmallPath());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX, e);
            return new ResponseEnvelope(false, "数据异常!",e);
        }

        return new ResponseEnvelope(true, "success", list, total);
    }


    public List<DesignTemplet> getDesignTempletsFromSpaceCommon(Integer spaceCommonIdText) {
        int total = 0;
        List<DesignTemplet> list = new ArrayList<DesignTemplet>();
        DesignTempletSearch designTempletSearch = new DesignTempletSearch();
        try {
            // 发布条件
            designTempletSearch.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());
            designTempletSearch.setSpaceCommonId(spaceCommonIdText);
			/* 根据登录用户类型(内部用户,普通用户)决定查询条件putawayState->end */
            total = designTempletService.getCount(designTempletSearch);
            if (logger.isInfoEnabled()) {
                logger.info(CLASS_LOG_PREFIX + "根据空间查询出样板房列表总记录:{}", total);
            }

            if (total > 0) {
                list = designTempletService.getPaginatedList(designTempletSearch);
                if (logger.isInfoEnabled()) {
                    logger.info(CLASS_LOG_PREFIX + "根据空间查询出样板房列表:{}", gson.toJson(list));
                }
            }
			/* 关联查询spaceName和spaceAreas */
            if (CustomerListUtils.isNotEmpty(list)) {
                for (DesignTemplet templet : list) {
                    setPicUrl(templet);
                }
            }
        } catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX, e);
            return null;
        }
        return list;
    }


    private void setPicUrl(DesignTemplet templet) {
		/* 关联查询平面效果图url及对应缩略图url */
        String effectPlanIds = templet.getEffectPlanIds();
        if (StringUtils.isNotBlank(effectPlanIds)) {
            Integer effectPlanId = 0;
            if (effectPlanIds.split(",").length > 1) {
                effectPlanId = Integer.valueOf(effectPlanIds.split(",")[0]);
            } else {
                effectPlanId = Integer.valueOf(effectPlanIds);
            }
            ResPic resPic = resPicService.get(effectPlanId);
            if (resPic != null && StringUtils.isNotBlank(resPic.getPicPath())) {
                templet.setEffectPlanUrl(resPic.getPicPath());
                ResPic smallResPic = resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
                templet.setEffectPlanSmallUrl(smallResPic == null ? ""
                        : (Utils.isBlank(smallResPic.getPicPath()) ? "" : smallResPic.getPicPath()));

            }
        }
        Integer spaceCommonId = templet.getSpaceCommonId();
        SpaceCommon spacecommon = null;
        spacecommon = spaceCommonService.get(spaceCommonId);

        templet.setSpaceCommonName(spacecommon == null ? "" : spacecommon.getSpaceName());
        templet.setSpaceAreas(spacecommon == null ? "" : spacecommon.getSpaceAreas());
        templet.setSpaceFunctionId(spacecommon == null ? null : spacecommon.getSpaceFunctionId());
        templet.setSpaceShape(spacecommon == null ? "" : spacecommon.getSpaceShape());
    }


    /**
     * 通过面积空间形状搜索空间
     * @param spaceFunctionId 空间类型Id
     * @param areaValue  空间面积
     * @param spaceShape 空间形状
     * @param  户型Id
     * @param pageSize 页面大小
     * @param curPage 当前页
     * @return ResponseEnvelope
     */
    @ApiOperation(value = "通过面积、形状搜索空间",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "每页数量",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "curPage",value = "当前页",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "spaceFunctionId",value = "空间类型ID",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "areaValue",value = "空间面积",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "spaceShape",value = "空间形状",paramType = "query",dataType = "String")
            /*@ApiImplicitParam(name = "houseId",value = "户型ID",paramType = "query",dataType = "Integer")*/
    })
    @RequestMapping(value = "/spacesearch",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope spaceSearch(@RequestParam(value = "spaceFunctionId") String spaceFunctionId,
                                        @RequestParam(value = "areaValue",required = false)String areaValue,
                                        @RequestParam(value = "spaceShape",required = false)String spaceShape,
                                        /*@RequestParam(value = "houseId",required = false)Integer houseId,*/
                                        @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                        @RequestParam(value = "curPage",required = false)Integer curPage){
        String msg = "";
        if (spaceFunctionId == null) {
            logger.warn(CLASS_LOG_PREFIX + "spaceFunctionId is null");
            msg = "传参异常";
            return new ResponseEnvelope(false, msg);
        }
        SpaceCommon spaceCommon = new SpaceCommon();
        spaceCommon.setIsDeleted(0);
        int total = 0;
        List<SpaceCommon> list = new ArrayList<SpaceCommon>();
        try {
            if (StringUtils.isNotBlank(spaceFunctionId)) {
                spaceCommon.setSpaceFunctionId(Integer.valueOf(spaceFunctionId));// 空间功能类型
            }
            if (StringUtils.isNotBlank(areaValue)){
                spaceCommon.setSpaceAreas(areaValue);// 空间面积
            }
            if (StringUtils.isNotBlank(spaceShape)) {
                spaceCommon.setSpaceShape(spaceShape);// 空间形状
            }
            // 只查询标准空间(下面两个条件为标准空间)
            spaceCommon.setPid(0);// 缩略图
            spaceCommon.setIsStandardSpace(1);// 是否是标准空间
            //处理页面大小
            if (null != pageSize && 0 != pageSize) {
                spaceCommon.setLimit(pageSize);
            } else {
                spaceCommon.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
            }
            //处理开始位置
            if(null!=curPage && 0!=curPage){
                spaceCommon.setStart((curPage-1)*spaceCommon.getLimit());
            }else{
                spaceCommon.setStart(0);
            }
            spaceCommon.setStatus(SpaceCommonStatus.IS_RELEASE);
            logger.info(CLASS_LOG_PREFIX + "查询空间总记录数：{}", total);
            total = spaceCommonService.getSpaceSearchCount(spaceCommon);
            logger.info(CLASS_LOG_PREFIX + "查询空间总记录数完成：{}", total);
            if (total > 0) {
                logger.info(CLASS_LOG_PREFIX + "查询空间总数据：{}", gson.toJson(list));
                list = spaceCommonService.getSpaceSearchList(spaceCommon);
                logger.info(CLASS_LOG_PREFIX + "查询空间总数据完成：{}", gson.toJson(list));
            }
            if (CustomerListUtils.isNotEmpty(list)) {
                for (SpaceCommon spaceCommonSingle : list) {
                    //关联空间的平面图
                    SpaceCommon spaceCommon2 = spaceCommonService.setPicParams(spaceCommonSingle);
                    if(null!=spaceCommon2) {
                        spaceCommonSingle.setViewPlanPath(spaceCommon2.getViewPlanPath());
                        spaceCommonSingle.setViewPlanSmallPath(spaceCommon2.getViewPlanSmallPath());
                    }

                }
            }
        } catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX, e);
            return new ResponseEnvelope(false, "数据异常");
        }
        return new ResponseEnvelope(true, "success", BaseSpaceCommonObject.parseToSpaceCommonVoList(list), total);
    }

}
