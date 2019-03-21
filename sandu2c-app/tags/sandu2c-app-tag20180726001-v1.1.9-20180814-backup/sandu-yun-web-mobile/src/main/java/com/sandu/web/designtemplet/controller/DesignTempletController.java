package com.sandu.web.designtemplet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.home.DesignTempletObject;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.CustomerListUtils;
import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.service.SpaceCommonService;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;

/**
 * @version V1.0
 * @Title: DesignTempletController.java
 * @Package com.sandu.design.controller
 * @Description:设计模块-设计方案样板房表Controller
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 */
@Controller
@RequestMapping("/v1/tocmobile/design/designtemplet")
public class DesignTempletController {
	private final static Gson gson = new Gson();
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DesignTempletController.class.getName());
	private final static String CLASS_LOG_PREFIX = "[设计方案样板房服务]";
	@Autowired
	private DesignTempletService designTempletService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private SpaceCommonService spaceCommonService;

	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 *
	 * @param spaceCommonIdText
	 * @param limit
	 * @param start
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = {"/spacedesign"},method=RequestMethod.GET)
    @ResponseBody
    public Object spaceDesign(@RequestParam(value = "spaceCommonId", required = false) String spaceCommonIdText,PageModel pageModel,
 HttpServletRequest request) {
        String msg = "";
        if (StringUtils.isBlank(spaceCommonIdText)) {
            msg = "参数spaceCommonId不能为空";
            return new ResponseEnvelope(false, msg);
        }
        int total = 0;
        List<DesignTemplet> list = new ArrayList<DesignTemplet>();
        DesignTempletSearch designTempletSearch = new DesignTempletSearch();
        try {
            // 发布条件
           designTempletSearch.setPutawayState(DesignTempletPutawayState.IS_RELEASE.intValue());

            designTempletSearch.setSpaceCommonId(Integer.parseInt(spaceCommonIdText));
            
        	designTempletSearch.setStart(pageModel.getStart());
        	designTempletSearch.setLimit(pageModel.getLimit());
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
					/* 空间布局缩略图 */
                    ResPic resPic2 = null;
                    if (templet.getPicId() != null) {
                        resPic2 = resPicService.get(templet.getPicId());
                    }
                    if (resPic2 != null) {
                        templet.setPicPath(resPic2.getPicPath());
                        ResPic smallResPic = resPicService.get(Utils.getSmallPicId(resPic2, "ipad"));
                        templet.setSmallpicPath(smallResPic == null ? "" : (Utils.isBlank(smallResPic.getPicPath()) ? "" : smallResPic.getPicPath()));
                    }
                    // 效果图列表
                    String effectPicId = templet.getEffectPic();
                    if (!StringUtils.isEmpty(effectPicId)) {
                        List<String> effectlist = new ArrayList<>();
                        if (effectPicId.contains(",")) {
                            String[] strs = effectPicId.split(",");
                            for (String s : strs) {
                                if (!StringUtils.isEmpty(s)) {
                                    ResPic resPic = null;
                                    resPic = resPicService.get(Integer.parseInt(s));
                                    if (resPic != null && StringUtils.isNotBlank(resPic.getPicPath())) {
                                        effectlist.add(resPic.getPicPath());
                                    }
                                }
                            }
                            templet.setEffectPicListPath(effectlist.toArray(new String[effectlist.size()]));
                            ResPic resPic = null;
                            resPic = resPicService.get(Integer.parseInt(strs[0]));
                            ResPic smallResPic = resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
                            templet.setEffectSmallPicPath(smallResPic == null ? "" : (Utils.isBlank(smallResPic.getPicPath()) ? "" : smallResPic.getPicPath()));
                            //客户端取值错误，临时修改为对应的值todo
                            templet.setPicPath(templet.getEffectPicListPath()[0]);
                            templet.setSmallpicPath(templet.getEffectSmallPicPath());
                        } else {
                            ResPic resPic = null;
                            resPic = resPicService.get(Integer.parseInt(effectPicId));
                            String[] s = new String[1];
                            s[0] = resPic == null ? ""
                                    : (Utils.isEmpty(resPic.getPicPath()) ? "" : resPic.getPicPath());
                            templet.setEffectPicListPath(s);
                            ResPic smallResPic = resPicService.get(Utils.getSmallPicId(resPic, "ipad"));
                            templet.setEffectSmallPicPath(smallResPic == null ? "" : (Utils.isBlank(smallResPic.getPicPath()) ? "" : smallResPic.getPicPath()));
                            //客户端取值错误，临时修改为对应的值todo
                            templet.setPicPath(templet.getEffectPicListPath()[0]);
                            templet.setSmallpicPath(templet.getEffectSmallPicPath());
                        }
                    }
	                /*关联查询平面效果图url及对应缩略图url*/
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
                            templet.setEffectPlanSmallUrl(smallResPic == null ? "" : (Utils.isBlank(smallResPic.getPicPath()) ? "" : smallResPic.getPicPath()));
                        }
                    }
                    Integer spaceCommonId = templet.getSpaceCommonId();
                    SpaceCommon spacecommon = spaceCommonService.get(spaceCommonId);
                    templet.setSpaceCommonName(spacecommon == null ? "" : spacecommon.getSpaceName());
                    templet.setSpaceAreas(spacecommon == null ? "" : spacecommon.getSpaceAreas());
                    templet.setSpaceFunctionId(spacecommon.getSpaceFunctionId());
                    templet.setSpaceShape(spacecommon.getSpaceShape());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "数据异常");
        }
        return new ResponseEnvelope(true, "", DesignTempletObject.parseToSpaceCommonVoList(list), total);
    }


}
