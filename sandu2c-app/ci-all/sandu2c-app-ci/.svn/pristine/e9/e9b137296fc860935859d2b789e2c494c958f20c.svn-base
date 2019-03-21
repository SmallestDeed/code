package com.sandu.web.home.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.home.model.vo.BaseHouseVo;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.system.service.NodeInfoBizService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.exception.BizException;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.home.BaseHouseObject;
import com.sandu.common.util.collections.Lists;
import com.sandu.designtemplate.model.DesignTempletPutawayState;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.SpaceCommonStatus;
import com.sandu.home.model.dto.BaseHouseGetMatchResultDTO;
import com.sandu.home.model.dto.HouseGuidePicInfoDTO;
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

/**
 * @Title: BaseHouseController.java
 * @Package com.sandu.home.controller
 * @Description:户型Controller
 */
@Controller
@RequestMapping("/v1/miniprogram/home/basehouse")
public class BaseHouseController {
    private final static String CLASS_LOG_PREFIX = "[户型搜索服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseHouseController.class);
    private static Gson gson = new Gson();
    /*** 获取路径开始部分 */

    @Autowired
    private BaseHouseService baseHouseService;
    @Autowired
    private ResHousePicService resHousePicService;
    @Autowired
    private HouseSpaceNewService houseSpaceNewService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private NodeInfoBizService nodeInfoBizService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * 户型类型搜索接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getbasehouselist")
    @ResponseBody
    public Object houseList(@RequestParam(value = "livingId", required = false) String livingId,
                            @ModelAttribute PageModel pageModel, HttpServletRequest request) {
        if (StringUtils.isBlank(livingId)) {
            return new ResponseEnvelope(false, "参数livingId不能为空");
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null){
            return new ResponseEnvelope(false, "请登录！！！");
        }
        BaseHouseSearch baseHouseSearch = new BaseHouseSearch();
        List<BaseHouse> list = new ArrayList<BaseHouse>();
        int total = 0;
        try {
            baseHouseSearch.setLivingId(Integer.parseInt(livingId));
            if (null != pageModel && 0 != pageModel.getPageSize()) {
                baseHouseSearch.setStart(pageModel.getStart());
                baseHouseSearch.setLimit(pageModel.getPageSize());
            } else {
                baseHouseSearch.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
            }
            this.internalPermissions(baseHouseSearch);// 判断该用户该环境 拥有 看到 哪些状态的空间和样板房，并且赋值
            total = baseHouseService.getHaveSpaceCount(baseHouseSearch);
            if (total > 0) {
                list = baseHouseService.getHaveSpaceList(baseHouseSearch);
                for (BaseHouse baseHouse : list) {

                    // 取户型的缩略图和大图
                    if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
                        // 截图图片处理 v3.9.4(draw-v1.0.2)
                        ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
                        baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                        baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                    } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
                        ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
                            baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
                            baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
                    } else {
                        baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
                        baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
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
                            + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "数据异常!");
        }
        list = nodeInfoBizService.getNodeData(list, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_HOUSE, loginUser.getId());
        return new ResponseEnvelope(true, "success", BaseHouseObject.convertTobaseLivingVo(list), total);

    }
    
    
    /**
     * 获取购买户型的相关配置
     * @param request
     * @return
     */

    @RequestMapping(value = "/getexpandhousedict")
    @ResponseBody
    public Object getExpandHouseDict(HttpServletRequest request) {

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

    @ResponseBody
    @RequestMapping("/queryBaseHouse")
    public Object listBaseHouseBySceneIds(String sceneIds, HttpServletRequest request){
        logger.debug("Process in BaseHouseController.listBaseHouseBySceneIds method parameter sceneIds:{}", sceneIds);
        List<BaseHouse> baseHouseList = null;
        if(com.sandu.common.util.StringUtils.isEmpty(sceneIds)){
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
    @SuppressWarnings("rawtypes")
//    @RequestMapping(value = "/getSpaceNameInHouse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getSpaceNameInHouse")
    @ResponseBody

    public ResponseEnvelope getSpaceNameInHouse(@RequestBody BaseHouseSearch baseHouseSearch) {

        Integer houseId = baseHouseSearch.getHouseId();
        List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeByHouseId(houseId);
        return new ResponseEnvelope(true, "success", spaceTypeList);
//        return new ResponseEnvelope(true,spaceTypeList);
    }

    /**
     * 获取户型2d导航图及其关联的信息(坐标信息,坐标对应跳转的样板房信息)
     * 需求:可以点击2d导航图上的坐标(选择样板房),搜索对应的推荐方案
     * 
     * @author huangsongbo
     * @date 2018.09.20
     * @param houseId 户型id
     * @param fullHousePlanId 全屋方案id 用来查询当前渲染状态
     * @return
     */
    @GetMapping("/getHouseGuidePicInfo")
    @ResponseBody
    public ResponseEnvelope<HouseGuidePicInfoDTO> getHouseGuidePicInfo(
    		Integer houseId,
    		Integer fullHousePlanId
    		) {
    	// 参数验证 ->start
    	if(houseId == null) {
    		return new ResponseEnvelope<HouseGuidePicInfoDTO> (false, "参数 houseId 不能为空");
    	}
    	// 参数验证 ->end
    	
    	HouseGuidePicInfoDTO houseGuidePicInfoDTO = null;
    	try {
    		houseGuidePicInfoDTO = baseHouseService.getHouseGuidePicInfoDTO(houseId, fullHousePlanId, null, null);
    	}catch (BizException e) {
    		/*logger.error(e.getMessage());*/
    		return new ResponseEnvelope<HouseGuidePicInfoDTO> (false, e.getMessage());
		}
    	
		return new ResponseEnvelope<HouseGuidePicInfoDTO>(true, "success", houseGuidePicInfoDTO);
    }
    
    
    /**
     * 获取可装修方案结果:
     * 选择推荐方案,再选择户型(houseId)或者我的方案(recommendedPlanId)
     * 
     * a.户型中只有一个适用的样板房
     *     |
     *     |->检测该样板房对应方案状态
     *             |
     *             |->未渲染/渲染失败: 直接装进该样板房
     *             |
     *             |->渲染中: 提示样板房对应的方案正在渲染中,不允许装修
     *             |
     *             |->渲染完成: 提示要更新还是新增
     * 
     * b.户型中有n(n>=2)个适用样板房
     *     |
     *     |->检测样板房状态为渲染中的个数n1
     *             |
     *             |->n1 = n - 1(只有一个可装入样板房):
     *             |        |
     *             |        |->检测剩余的样板房的状态
     *             |                |
     *             |                |->未渲染/渲染失败: 直接装进该样板房
     *             |                |
     *             |                |->渲染完成: 提示要更新还是新增
     *             |
     *             |->n1 = n(没有可装入样板房):提示样板房对应的方案正在渲染中,不允许装修
     *             |
     *             |->n1 < n - 1(有多个可装入样板房(数量>2)):弹出户型图供其选择
     *             
     * c.没有适用样板房:提示不匹配
     * 
     * @author huangsongbo
     * @param houseId
     * @param recommendedPlanId
     * @return
     */
    @GetMapping("/getMatchResult")
    @ResponseBody
    public ResponseEnvelope<BaseHouseGetMatchResultDTO> getMatchResult(
    		Integer houseId,
    		Integer recommendedPlanId,
    		Integer fullHousePlanId
    		){
    	// 参数验证 ->start
    	if(recommendedPlanId == null) {
    		return new ResponseEnvelope<BaseHouseGetMatchResultDTO> (false, "参数 recommendedPlanId 不能为空");
    	}
    	if(houseId == null && fullHousePlanId == null) {
    		return new ResponseEnvelope<BaseHouseGetMatchResultDTO> (false, "参数 houseId 和 fullHousePlanId 不能同时为空");
    	}
    	// 参数验证 ->end
    	
    	BaseHouseGetMatchResultDTO baseHouseGetMatchResultDTO;
		try {
			baseHouseGetMatchResultDTO = baseHouseService.getMatchResult(houseId, recommendedPlanId, fullHousePlanId);
		} catch (BizException e) {
			return new ResponseEnvelope<BaseHouseGetMatchResultDTO> (false, e.getMessage());
		}
    	return new ResponseEnvelope<BaseHouseGetMatchResultDTO>(true, "success", baseHouseGetMatchResultDTO);
    }

    @RequestMapping("/getFavoriteHouse")
    @ResponseBody
    public ResponseEnvelope getFavoriteHouse(Integer start, Integer limit){
        // 登录校验
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null){
            return new ResponseEnvelope(false, "请登录！");
        }
        Integer userId = loginUser.getId();
        List<Integer> houseIds = nodeInfoBizService.getContentIdList(userId,
                NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_HOUSE, NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_FAVORITE);
        if (houseIds == null || houseIds.size() <= 0){
            return new ResponseEnvelope(true,"没有收藏户型");
        }
        List<BaseHouseVo> list = baseHouseService.getListByIds(houseIds, start, limit);
        list = nodeInfoBizService.getNodeData(list, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_HOUSE, userId);
        return new ResponseEnvelope(true, "查询成功！", list, houseIds.size());
    }

    @RequestMapping("/getServerTime")
    @ResponseBody
    public ResponseEnvelope getServerTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new ResponseEnvelope(true, format.format(new Date()));
    }
}
