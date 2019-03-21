package com.sandu.web.user.controller;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.base.service.BaseAreaService;
import com.sandu.cache.service.RedisService;
import com.sandu.common.LoginContext;
import com.sandu.common.ReturnData;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.properties.ResProperties;
import com.sandu.common.tool.EscapeUnescape;
import com.sandu.common.util.collections.Lists;
import com.sandu.design.model.ResRenderPicQO;
import com.sandu.design.model.ThumbData;
import com.sandu.designplan.model.DesignPlanRenderScene;
import com.sandu.designplan.model.FavoriteRecommendedModel;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designplan.service.DesignPlanRecommendFavoriteService;
import com.sandu.designplan.service.DesignPlanRenderSceneService;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.vo.UserHouseAccountsVo;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.platform.BasePlatform;
import com.sandu.product.model.CollectionQueryModel;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.render.model.RenderTypeCode;
import com.sandu.render.model.RenderingModel;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.ResRenderPicService;
import com.sandu.user.model.SysUserMessage;
import com.sandu.user.service.SysUserMessageService;
import com.sandu.user.service.UserFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统-用户资源模块
 * @author WangHaiLin
 * @date 2018/5/9  18:08
 */
@Api(tags = "userResources",description = "用户资源")
@RestController
@RequestMapping("/v1/xzminiprogram/userresources")
public class UserResourcesConroller {
    @Autowired
    private UserFinanceService userFinanceService;


    @Autowired
    private ResHousePicService resHousePicService;

    @Autowired
    private HouseSpaceNewService houseSpaceNewService;
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired 
    private RedisService redisService;
    @Autowired
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;
    @Autowired
    private UserProductCollectService userProductCollectService;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;
    @Autowired
    private ResRenderPicService resRenderPicService;
    
    private final static String CLASS_LOG_PREFIX = "[我的消息服务]";
    private final static Gson gson = new Gson();
    private final static String BRAND_WEBSITE_PLATFORM_CODE = "brand2c";
    private final static String BASE_PLATFORM_INFO = "basePlatformInfo";
    private final static Logger logger = LoggerFactory.getLogger(UserResourcesConroller.class);
    /**
     *
     * 我的的户型
     * @return
     */
    @ApiOperation(value = "我的户型",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSort",value = "是否排序",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "每页数量",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "curPage",value = "当前页",paramType = "query",dataType = "Integer")
    })
    @RequestMapping(value = "/myHouseType",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope findUserAccounts(@RequestParam (value = "pageSize",required = false) Integer pageSize,
                                             @RequestParam(value = "curPage",required = false) Integer curPage,
                                             @RequestParam(value = "isSort",required = false) Integer isSort) {
    	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false, "请先登录");
        }
        //查询用户的总共户型套数
        Integer userAlreadyBoughtHouseCount = userFinanceService.queryUserAlreadyBoughtHouseCount(loginUser.getId());
        //查询用户已使用户型套数
        Integer userUsedHouseCount = userFinanceService.queryUserUsedHouseCount(loginUser.getId());
        //查询用户可使用户型套数
        Integer userValidHouseCount = userAlreadyBoughtHouseCount-userUsedHouseCount;

        BaseHouse queryBaseHouse = new BaseHouse();
        queryBaseHouse.setUserId(loginUser.getId());
        //处理页面大小
        if (null != pageSize && 0 != pageSize) {
            queryBaseHouse.setLimit(pageSize);
        } else {
            queryBaseHouse.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        //处理起始位置
        if (null!=curPage && 0!=curPage){
            queryBaseHouse.setStart((curPage-1)*queryBaseHouse.getLimit());
        }else{
            queryBaseHouse.setStart(0);
        }

        if(null!=isSort) {
            queryBaseHouse.setIsSort(isSort);
        }
        //查询用户已使用户型的详细信息
        List<BaseHouse> houselist = userFinanceService.queryUserUsedHouseDetailList(queryBaseHouse);
        if(houselist!=null) {
            for (BaseHouse baseHouse : houselist) {
                // 取户型的缩略图和大图
                if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() != 0) {
                    ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
                    if (null != resHousePic) {
                        baseHouse.setThumbnailPath(resHousePic.getThumbnailPath());
                        baseHouse.setLargeThumbnailPath(resHousePic.getLargeThumbnailPath());
                    }
                }
                String longCodeName ="";
                //查询小区的地址
                if(StringUtils.isNotBlank(baseHouse.getAreaLongCode())) {
                    String longCode = baseHouse.getAreaLongCode();
                    String str = longCode.substring(1, longCode.length()-1);
                    String[] split = str.split("\\.");
                    List<String> list = new ArrayList<String>();
                    for (String code : split) {
                        if(!list.contains(code)) {
                            list.add(code);
                            String codeName = baseAreaService.getCodeName(code);
                            longCodeName += codeName;
                        }
                    }
                }
                baseHouse.setHouseAddress(longCodeName);




                // 取户型的空间定位类型
                List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
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

        UserHouseAccountsVo userHouseAccountsVo = new UserHouseAccountsVo();
        userHouseAccountsVo.setUserAlreadyBoughtHouseCount(userAlreadyBoughtHouseCount);
        userHouseAccountsVo.setUserUsedHouseCount(userUsedHouseCount);
        userHouseAccountsVo.setUserValidHouseCount(userValidHouseCount);
        userHouseAccountsVo.setHouselist(houselist);

        return new ResponseEnvelope(true, "success", userHouseAccountsVo,null==userUsedHouseCount?0:userUsedHouseCount);
    }
    
    /**
     * 我的消息列表
     *
     * 我的消息列表没有分页的这个操作(理论上该接口是有分页的动作)2018-5-17
     *
     * @param sysUserMessage
     * @return
     */
    @ApiOperation(value = "我的消息",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order",value = "排序",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "orderNum",value = "排序方式",paramType = "query",dataType = "String")
           /* @ApiImplicitParam(name = "pageSize",value = "每页数量",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "curPage",value = "当前页",paramType = "query",dataType = "Integer")*/
    })
    @RequestMapping(value = "/myMessage",method = RequestMethod.GET)
    @ResponseBody
    public Object myMessage(@RequestParam (value = "order",required = false) String order,
    						@RequestParam (value = "orderNum",required = false) String orderNum,HttpServletRequest request) {
    	
    	SysUserMessage sysUserMessage = new SysUserMessage();
    	sysUserMessage.setOrder(order);
    	sysUserMessage.setOrderNum(orderNum);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
    	logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
		sysUserMessage.setUserId(loginUser.getId());
		
		//从缓存中获取当前平台
		String basePlatformInfo = redisService.getMap(BASE_PLATFORM_INFO, BRAND_WEBSITE_PLATFORM_CODE);
		if(StringUtils.isNotBlank(basePlatformInfo)) {
			BasePlatform basePlatform =  gson.fromJson(basePlatformInfo ,
					BasePlatform.class);
			sysUserMessage.setPlatformId(basePlatform.getId());
		}
		
        int count = sysUserMessageService.getCount(sysUserMessage);
        if(count==0) {
        	return new ResponseEnvelope<>(true, "您还没有做过渲染");
        }
        List<SysUserMessage> allMessages = sysUserMessageService.getList(sysUserMessage);
        try {
            sysUserMessageService.updateIsRead(sysUserMessage.getUserId());
        }catch (Exception e) {
        	logger.error(CLASS_LOG_PREFIX, e);
            e.printStackTrace();
        }

        return new ResponseEnvelope<>(true, "success", allMessages, count);
    }
    /**
     * 删除消息
     *
     * @param sysUserMessage
     * @return
     */
    @ApiOperation(value = "删除消息",response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "我的消息id",paramType = "query",dataType = "int")
    })
    @RequestMapping(value = "/deleteMessage",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@RequestParam (value = "id",required = true) Integer id,HttpServletRequest request) {
    	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        SysUserMessage sysUserMessage = new SysUserMessage();
        sysUserMessage.setId(id);
        int status = sysUserMessageService.delete(sysUserMessage);
        if (status <= 0) {
            return new ResponseEnvelope(false, "删除消息失败！");
        }
        return new ResponseEnvelope(true, "删除消息成功！");
    }

    /**
     * 我的收藏(方案)
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "我的收藏(方案)", response = ReturnData.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "isSort", value = "排序,0是降序,1是升序",required=true, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "livingName", value = "小区名称(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "pageSize", value = "每页显示多少条记录(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "curPage", value = "当前页码(参数无用)",paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "fBid", value = "方案推荐收藏夹id(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "designPlanStyleId", value = "设计风格ID(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "spaceArea", value = "面积(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "spaceType", value = "空间类型(参数无用)", paramType = "query", dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/myCollectionPlan",method = RequestMethod.GET)
    public ResponseEnvelope mycollectionplan(HttpServletRequest request, HttpServletResponse response) {
        String livingName = request.getParameter("livingName");
        String limit = request.getParameter("pageSize");
        String start = request.getParameter("curPage");
        String favoriteBid = request.getParameter("fBid");
        String isSort = request.getParameter("isSort");
        String designRecommendedStyleId = request.getParameter("designPlanStyleId");//设计风格ID
        String areaValue = request.getParameter("spaceArea");//面积
        String houseType = request.getParameter("spaceType"); //方案的空间类型
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        FavoriteRecommendedModel model = new FavoriteRecommendedModel();
        model.setHouseType(houseType);
        model.setLivingName(livingName);
        model.setAreaValue(areaValue);
        model.setDesignRecommendedStyleId(designRecommendedStyleId);
        model.setUserId(loginUser.getId());
        model.setUserType(loginUser.getUserType());
        model.setLimit(limit);
        model.setStart(start);
        model.setIsSort(isSort);//收藏时间排序
        if (!"-1".equals(favoriteBid)) {
            model.setFavoriteBid(favoriteBid);
        }
        return designPlanRecommendFavoriteService.favoritePlanRecommendedList(model);
    }
    /** 
     * 我的收藏(产品)
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "我的收藏(产品)", response = ReturnData.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "isSort", value = "排序,0是降序,1是升序",required=true, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "pageSize", value = "每页显示多少条记录(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "curPage", value = "当前页码(参数无用)",paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "fBid", value = "方案推荐收藏夹id(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "designPlanStyleId", value = "设计风格ID(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "spaceArea", value = "面积(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "spaceType", value = "空间类型(参数无用)", paramType = "query", dataType = "String")
    })
    @RequestMapping("/myCollectionProducts")
    @ResponseBody
    public ResponseEnvelope mycollectionproducts(@ModelAttribute PageModel pageModel, Integer isSort,String productCategoryCode
            , HttpServletRequest request) {

    	LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info("[选装网产品收藏服务]" + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        CollectionQueryModel model = new CollectionQueryModel();
        model.setIsSort(null == isSort ? 0 : isSort);
        model.setStart(pageModel.getStart());
        model.setPageSize(0 == pageModel.getPageSize() ? 20 : pageModel.getPageSize());
        model.setLoginUser(loginUser);
        model.setProductCategoryCode(null==productCategoryCode?null:productCategoryCode);
        ResponseEnvelope res = userProductCollectService.collectionList(model);
        logger.info(CLASS_LOG_PREFIX + "响应给客户端的数据:{}", gson.toJson(res));
        return res;
    }
    /**
     * 我的装修
     * 
     *RenderingModel 接收参数的对象 无用(忽略掉)
     *
     */
    @ApiOperation(value = "我的装修", response = ReturnData.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "isSort", value = "排序,0是降序,1是升序", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "pageSize", value = "每页显示多少条记录(参数无用)", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "curPage", value = "当前页码(参数无用)",paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/myDesignPlan", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getThumbList(@ModelAttribute RenderingModel thumbData, @ModelAttribute PageModel pageModel, HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if(loginUser==null){
            return new ResponseEnvelope(false, "请登录!");
        }
        DesignPlanRenderScene designPlanRenderScene = new DesignPlanRenderScene();
        designPlanRenderScene.setUserId(loginUser.getId());
        if (StringUtils.isNotBlank(thumbData.getName())) {
            designPlanRenderScene.setPlanName(EscapeUnescape.unescape(thumbData.getName()));
        }
        if (null != thumbData.getSpaceFunctionId() && thumbData.getSpaceFunctionId() > 0) {
            designPlanRenderScene.setSpaceFunctionId(thumbData.getSpaceFunctionId());
        }
        if(null != thumbData.getHouseId()&&thumbData.getHouseId()>0) {
        	designPlanRenderScene.setHouseId(thumbData.getHouseId());
        }
        if (null != pageModel && 0 != pageModel.getPageSize()) {
            designPlanRenderScene.setStart(pageModel.getStart());
            designPlanRenderScene.setLimit(pageModel.getPageSize());
        } else {
            designPlanRenderScene.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        if(null!=thumbData.getIsSort()) {
        	designPlanRenderScene.setIsSort(thumbData.getIsSort());
        }
    	if (StringUtils.isNotBlank(thumbData.getAreaValue())) {
    		designPlanRenderScene.setSpaceAreas(thumbData.getAreaValue());// 空间面积
		}
		if (StringUtils.isNotBlank(thumbData.getSpaceStyleId())) {
			designPlanRenderScene.setSpaceStyleId(thumbData.getSpaceStyleId());// 空间风格
		}
		
		//从缓存中获取当前平台
		String basePlatformInfo = redisService.getMap(BASE_PLATFORM_INFO, BRAND_WEBSITE_PLATFORM_CODE);
		if(StringUtils.isNotBlank(basePlatformInfo)) {
			BasePlatform basePlatform =  gson.fromJson(basePlatformInfo ,
					BasePlatform.class);
			designPlanRenderScene.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
		}
        List<ThumbData> resList = new ArrayList<ThumbData>();
        List<DesignPlanRenderScene> list = null;
        int count = 0;


        count = designPlanRenderSceneService.selectVendorCountV2(designPlanRenderScene);

        if (count > 0) {
            list = designPlanRenderSceneService.selectVendorListV2(designPlanRenderScene);
        }

        if (list == null || list.size() <= 0) {
        	return new ResponseEnvelope(true,"您还没有渲染过方案",null);
        }

        for (DesignPlanRenderScene scene : list) {
            ThumbData thumbData2 = new ThumbData();
            thumbData2.setCpId(scene.getId());
            thumbData2.setFailCause(scene.getFailCause());
            thumbData2.setCheckUserName(scene.getCheckUserName());
            thumbData2.setName(scene.getPlanName());
            this.coverPicHandling(scene, thumbData2);
            resList.add(thumbData2);
        }
        return new ResponseEnvelope(true, "success", resList, count);
    }
    /**
     * 图片封面处理
     *
     * @param scene
     * @param thumbData
     */
    public void coverPicHandling(DesignPlanRenderScene scene, ThumbData thumbData) {
        if (scene == null || thumbData == null) {
            return;
        }
        if (scene.getCoverPicId() != null && scene.getCoverPicId().intValue() > 0) {
            ResRenderPic coverPic = resRenderPicService.get(scene.getCoverPicId());
            if (coverPic != null) {
                this.dataFilling(coverPic, thumbData);
                return;
            }
        }
        List<ResRenderPic> picList = new ArrayList<>(); //查询该设计方案的全部渲染缩略图列表
        ResRenderPicQO resRenderPicQO = new ResRenderPicQO();
        resRenderPicQO.setCreateUserId(scene.getUserId());
        resRenderPicQO.setDesignSceneId(scene.getId());
        resRenderPicQO.setIsDeleted(0);
        List<String> fileKeyLists = new ArrayList<String>();
        fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
        fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
        resRenderPicQO.setFileKeys(fileKeyLists);
        picList = resRenderPicService.selectListByFileKeys(resRenderPicQO);
        if (picList != null && picList.size() > 0) {
            int id = 0;
            for (ResRenderPic resRenderPic : picList) {
                if (id > resRenderPic.getId().intValue()) {
                    continue;
                }
                thumbData.setName(scene.getPlanName());
                this.dataFilling(resRenderPic, thumbData);
                id = resRenderPic.getId();
            }
        }
    }

    /**
     * 对thumbData 进行数据填充
     *
     * @param resRenderPic
     * @param thumbData
     */
    public void dataFilling(ResRenderPic resRenderPic, ThumbData thumbData) {
        if (resRenderPic == null || thumbData == null) {
            return;
        }
        thumbData.setThumbId(resRenderPic.getId());
        //thumbData.setName(resRenderPic.getDesignPlanName());
        thumbData.setPic(resRenderPic.getPicPath());
        thumbData.setType(resRenderPic.getSpaceType());
        thumbData.setArea(resRenderPic.getArea());
        thumbData.setPlanId(resRenderPic.getBusinessId());
        if (resRenderPic.getGmtCreate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            thumbData.setCtime(simpleDateFormat.format(resRenderPic.getGmtCreate()));
        }
        if (RenderTypeCode.COMMON_PICTURE_LEVEL == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRenderPic(true);
        }
        if (RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRender720(true);
        }
        if (RenderTypeCode.ROAM_720_LEVEL == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRenderRoam(true);
        }
        if (RenderTypeCode.COMMON_VIDEO == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRenderVideo(true);
        }
    }

    
}
