package com.nork.pano.service.impl;


import com.nork.common.exception.BizException;
import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.vo.UnionStoreVo;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.cityunion.service.UnionDesignPlanStoreService;
import com.nork.cityunion.service.UnionStorefrontService;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.SysDictionaryConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.pano.PanoramaUtil;
import com.nork.common.util.*;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.common.util.collections.Lists;
import com.nork.design.common.RecommendedDecorateState;
import com.nork.design.dao.DesignRenderRoamMapper;
import com.nork.design.model.*;
import com.nork.design.service.*;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.model.ProductsCostType;
import com.nork.design.service.impl.DesignPlanProductServiceImpl.costListEnum;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.pano.model.constant.SceneTypeConstant;
import com.nork.pano.model.constant.ViewControlTypeConstant;
import com.nork.pano.model.dto.SceneControlInfoDto;
import com.nork.pano.model.dto.SceneUserInfoDto;
import com.nork.pano.model.input.SceneDataSearch;
import com.nork.pano.model.output.*;
import com.nork.pano.model.roam.Roam;
import com.nork.pano.model.scene.*;
import com.nork.pano.model.scene.hotspot.Hotspot;
import com.nork.pano.service.DesignPlanStoreReleaseService;
import com.nork.pano.service.PanoramaService;
import com.nork.product.model.*;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.CompanyShopService;
import com.nork.render.model.RenderPanoLevel;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.*;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserGroupService;
import com.nork.system.service.SysUserService;

import com.nork.user.model.UserTypeCode;
import com.nork.user.model.constant.UserTypeConstant;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service("panoramaService")
@Transactional(rollbackFor = Exception.class)
public class PanoramaServiceImpl implements PanoramaService{
	private static Logger logger = LoggerFactory.getLogger(PanoramaServiceImpl.class);
	@Autowired
    private DesignPlanService designPlanService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private ResRenderPicService resRenderPicService;
    @Autowired
    private DesignPlanProductService designPlanProductService;
    @Autowired
    private DesignRenderRoamMapper designRenderRoamMapper;
    @Autowired
    private ResDesignService resDesignService;
    @Autowired
    private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;
    @Autowired
    private OptimizePlanService optimizeDesingPlanService;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private UnionDesignPlanStoreReleaseService unionDesignPlanStoreReleaseService;
    @Autowired
    private UnionStorefrontService unionStorefrontService;
    @Autowired
    private BaseBrandService baseBrandService;
    @Autowired
    private UnionDesignPlanStoreService unionDesignPlanStoreService;
    @Autowired
    private DesignPlanStoreReleaseService designPlanStoreReleaseService;
    @Autowired
    private DesignRenderRoamService designRenderRoamService;
    @Autowired
    private CompanyShopService companyShopService;
    @Autowired
    private FullHousePlanService fullHousePlanService;
    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;

    private static final String WX_SMALL_INFORMATION_D = Utils.getPropertyName("config/share","wx.small.share.copywriting.informationD","我设计了一套装修方案，分享给你 [企业名称]");
    private static final String WX_SMALL_INFORMATION_R = Utils.getPropertyName("config/share","wx.small.share.copywriting.informationR","我发现了一套不错的装修方案，分享给你 [企业名称]");

    /**
     * 单场景加载（主函数）
     * @param sysCode/
     * @return
     */
    @Override
    public SingleSceneVo vr720Single(String sysCode){
        SingleSceneVo singleSceneVo = null;
        if(StringUtils.isNotBlank(sysCode) ) {
            singleSceneVo = new SingleSceneVo();
            // 获取单个场景对象
            Scene scene = this.getSceneBySysCode(sysCode, "");
            if (scene != null) {
                singleSceneVo.setScene(JAXBUtil.beanToXml(scene, Scene.class));
                // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
                PanoramaVo panoramaVo = this.assemblyPanoramaVoForNewScene(scene);
               /* PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);*/
                panoramaVo.setThumbUrl(scene.getThumbUrl());
                panoramaVo.setWindowsPercent(scene.getWindowsPercent());
                singleSceneVo.setPanoramaVo(panoramaVo);

                // 获取设计方案产品费用清单
                //如果有就走以前,没有就走onekey
                List<ProductsCostType> list = this.getProductsCost(panoramaVo);
                if (list != null && list.size() > 0) {
                    BigDecimal totalPrice = new BigDecimal(0.0);
                    for (ProductsCostType cost : list) {
                        totalPrice = totalPrice.add(cost.getTotalPrice());
                    }
                    singleSceneVo.setTotalPrice(totalPrice.toString());
                    singleSceneVo.setProductsCostTypeList(list);
                }

                // 资源访问url
                singleSceneVo.setResourceUrl(FileUploadUtils.RESOURCES_URL);
            }
        }
        return singleSceneVo;
    }


    /**
     * 720漫游场景加载（主函数）
     * @param sysCode design_render_roam表的uuid
     * @return
     */
    @Override
    public RoamSceneVo vr720Roam(String sysCode){
        RoamSceneVo roamSceneVo = null;
        if( StringUtils.isNotBlank(sysCode) ){
            roamSceneVo = new RoamSceneVo();
        }
        // 获取漫游的全景图模型集合
        List<Roam> roamList = getRoamRenderRelation(sysCode);

        // 组装pano场景标签
        if( roamList != null && roamList.size() > 0 ){
            Integer screenPicId = null;// 截图ID
            StringBuffer sceneXml = new StringBuffer();
            Scene scene = null;
            Image image;
            ImageCube imageCube = null;
            ImageSphere imageSphere;
            List<Hotspot> hotspotList;
            ResRenderPic resRenderPic;
            for( Roam roam : roamList ){
                Integer renderPicId = roam.getFieldName();
                logger.error("renderPicId----------------"+renderPicId);
                resRenderPic = resRenderPicService.get(renderPicId);
                if( resRenderPic != null ){
                    screenPicId = resRenderPic.getSysTaskPicId();
                    logger.error("screenPicId*****************" + screenPicId);
                    scene = new Scene();
                    scene.setPlanId(resRenderPic.getBusinessId());
                    //add by chenm on 2018/11/19
                    scene.setDesignPlanRecommendedId(resRenderPic.getPlanRecommendedId());
                    scene.setDesignPlanRenderSceneId(resRenderPic.getDesignSceneId());

                    scene.setName("scene_"+renderPicId);
                    scene.setTitle("");
                    scene.setThumbUrl("");
                    scene.setView(View.getDefaultView());// 使用默认view
                    image = new Image();
                    File file = new File(Utils.getAbsolutePath(resRenderPic.getPicPath(), null));
                    if (file.exists()) {
                        // 如果是切片目录存储，则使用cube方式加载场景
                        if( file.isDirectory() ){
                            imageCube = new ImageCube();
                            imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/pano_%s.jpg"));
                            image.setCube(imageCube);
                            scene.setImage(image);

                            // 预览图
                            Preview preview = new Preview();
                            preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/preview.jpg"));
                            scene.setPreview(preview);
                        }else{
                            imageSphere = new ImageSphere();
                            imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                            image.setSphere(imageSphere);
                            scene.setImage(image);
                        }
                    }
                    //FIXME:本地读取
                    /*if(resRenderPic.getPicPath().lastIndexOf(".") > 0){
                        imageSphere = new ImageSphere();
                        imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                        image.setSphere(imageSphere);
                        scene.setImage(image);
                    }else{
                        imageCube = new ImageCube();
                        imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/pano_%s.jpg"));
                        image.setCube(imageCube);
                        scene.setImage(image);

                        // 预览图
                        Preview preview = new Preview();
                        preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/preview.jpg"));
                        scene.setPreview(preview);
                    }*/



                    // 添加热点
                    Hotspot hotspot = null;
                    hotspotList = new ArrayList<Hotspot>();
                    for( Roam roam1 : roamList ){
                        if( roam1.getFieldName() != roam.getFieldName() ){
                            hotspot = new Hotspot();
                            hotspot.setName("hotspot_"+roam1.getFieldName());
                            hotspot.setUrl("images/hotspot/locatingPoint.png");
                            hotspot.setOnClick("loadscene('scene_"+roam1.getFieldName()+"')");// 点击事件，加载其他场景
                            hotspot.setText(String.valueOf(roam1.getIndex()));
                            hotspot.setLinkedScene("scene_"+roam1.getFieldName());
//                            hotspot.setOnLoaded("add_all_the_time_tooltip();");// 文字提示小标签.优先使用text中的文本，如果text为空则使用linkscene对应的场景的title属性
                            hotspot.setAth(PanoramaUtil.getAth(roam,roam1));
                            hotspot.setAtv(PanoramaUtil.getAtv(roam,roam1));
                            hotspot.setDistance(PanoramaUtil.distance(roam.getPosition(),roam1.getPosition()));
                            hotspotList.add(hotspot);
                        }
                    }
                    PanoramaUtil.ComparatorT comparatorT = new PanoramaUtil.ComparatorT();
                    Collections.sort(hotspotList,comparatorT);
                    scene.setHotspotList(hotspotList);
                    String sceneStr = JAXBUtil.beanToXml(scene,Scene.class);
                    sceneXml.append(sceneStr).append(",");
                }
            }

            // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
            /*PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);*/
            PanoramaVo panoramaVo = this.assemblyPanoramaVoForNewScene(scene);
            // 获取缩略图
            if( screenPicId != null ) {
            	logger.error("screenPicId ===================" + screenPicId);
                ResRenderPic thumbPic = resRenderPicService.selectOneByPid(screenPicId);
                if( thumbPic != null ){
                    /*panoramaVo.setThumbUrl(FileUploadUtils.RESOURCES_URL + thumbPic.getPicPath());*/
                	panoramaVo.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
                }
            }
            roamSceneVo.setPanoramaVo(panoramaVo);

            // 获取设计方案产品费用清单
            /*List<ProductsCostType> list = this.getProductsCost(panoramaVo.getPlanId(), panoramaVo.getUserId(), panoramaVo.getUserType());*/
            List<ProductsCostType> list = this.getProductsCost(panoramaVo);
            if( list != null && list.size() > 0 ){
                BigDecimal totalPrice = new BigDecimal(0.0);
                for( ProductsCostType cost : list ){
                    totalPrice = totalPrice.add(cost.getTotalPrice());
                }
                roamSceneVo.setTotalPrice(totalPrice.toString());
                roamSceneVo.setProductsCostTypeList(list);
            }

            // 资源访问url
            roamSceneVo.setResourceUrl(FileUploadUtils.RESOURCES_URL);

            try {
                roamSceneVo.setScenes(URLEncoder.encode(sceneXml.substring(0,sceneXml.length() - 1),"UTF-8").replaceAll("\\+","%20"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return roamSceneVo;
    }

    /**
     * 组装720页面基本信息
     * @param scene
     * @return
     */
    @Override
    public PanoramaVo assemblyPanoramaVo(Scene scene) {
    	Integer planId = scene.getPlanId();
    	Integer designPlanRenderSceneId = scene.getDesignPlanRenderSceneId();
    	Integer designPlanRecommendedId = scene.getDesignPlanRecommendedId();
    	
        PanoramaVo panoramaVo = new PanoramaVo();
        if(designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
        	panoramaVo = this.assemblyPanoramaVoRecommendedId(designPlanRecommendedId);
        }else if(designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
        	panoramaVo = this.assemblyPanoramaVoSceneId(designPlanRenderSceneId);
        }else if(planId != null && planId > 0) {
        	panoramaVo = this.assemblyPanoramaVoPlanId(planId);
        }

        return panoramaVo;
    }

	private PanoramaVo assemblyPanoramaVoSceneId(Integer designPlanRenderSceneId) {
		PanoramaVo panoramaVo = new PanoramaVo();
		panoramaVo.setDesignPlanRenderSceneId(designPlanRenderSceneId);
		// 参数验证 ->start
		if(designPlanRenderSceneId == null || designPlanRenderSceneId < 1) {
			return panoramaVo;
		}
		// 参数验证 ->end
		
		DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanRenderSceneId);
		if(designPlanRenderScene != null && designPlanRenderScene.getUserId() != null && designPlanRenderScene.getUserId() > 0) {
		    /*panoramaVo.setPlanId(planId);*/
		    // 设计方案风格，如果没有，则默认为混搭
		    Integer styleId = designPlanRenderScene.getDesignStyleId();
		    panoramaVo.setTitle("3D全景漫游");
//		    panoramaVo.setTitle("3D全景漫游|混搭");
//		    if(styleId != null) {
//		        BaseProductStyle style = baseProductStyleService.get(styleId);
//		        if(style != null) {
//		            panoramaVo.setTitle("3D全景漫游|" + style.getName());
//		        }
//		    }
		    // 获取用户昵称
		    Integer userId = designPlanRenderScene.getUserId();
		    SysUser sysUser = sysUserService.get(userId);
		    if( sysUser != null ){
		    	panoramaVo.setUserId(sysUser.getId());
		    	panoramaVo.setUserType(sysUser.getUserType());
		    	panoramaVo.setUserName(sysUser.getUserName());
		    	//如果是经销商账号 就取公司的Pid
		    	if (sysUser.getUserType().intValue() == 3 ) {
		    		// 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		        	BaseCompany company = baseCompanyService.get(companyId);
		        	if (company.getPid().intValue() > 0) {
		        		BaseCompany baseCompany = baseCompanyService.get(company.getPid());
		        		if(baseCompany != null){
			                panoramaVo.setCompanyName(baseCompany.getCompanyName());
		
			                // 获取用户公司LOGO
			                if( baseCompany.getCompanyLogo() != null ){
			                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
			                    if( resPic != null ){
			                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
			                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
			                    }
			                }
			            }
					}
		        	
				}else {
			        // 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		            BaseCompany baseCompany = baseCompanyService.get(companyId);
		            if(baseCompany != null){
		                panoramaVo.setCompanyName(baseCompany.getCompanyName());
	
		                // 获取用户公司LOGO
		                if( baseCompany.getCompanyLogo() != null ){
		                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
		                    if( resPic != null ){
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
		                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
		                    }
		                }
		            }
			    }
			}

		    // 获取房型类型
		    if(designPlanRenderScene.getSpaceCommonId() != null){
		        SpaceCommon spaceCommon = spaceCommonService.get(designPlanRenderScene.getSpaceCommonId());
		        if(spaceCommon != null && spaceCommon.getSpaceFunctionId() != null){
		            SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
		            panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
		        }
		    }

		    panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
		}
		return panoramaVo;
	}

	private PanoramaVo assemblyPanoramaVoRecommendedId(Integer designPlanRecommendedId) {
		PanoramaVo panoramaVo = new PanoramaVo();
		panoramaVo.setDesignPlanRecommendedId(designPlanRecommendedId);
		// 参数验证 ->start
		if(designPlanRecommendedId == null || designPlanRecommendedId < 1) {
			return panoramaVo;
		}
		// 参数验证 ->end
		
		DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanRecommendedId);
		if(designPlanRecommended != null && designPlanRecommended.getUserId() != null && designPlanRecommended.getUserId() > 0) {
		    /*panoramaVo.setPlanId(planId);*/
		    // 设计方案风格，如果没有，则默认为混搭
//		    Integer styleId = designPlanRecommended.getDesignStyleId();
		    panoramaVo.setTitle("3D全景漫游");
//		    panoramaVo.setTitle("3D全景漫游|混搭");
//		    if(styleId != null) {
//		        BaseProductStyle style = baseProductStyleService.get(styleId);
//		        if(style != null) {
//		            panoramaVo.setTitle("3D全景漫游" + style.getName());
//		        }
//		    }
		    // 获取用户昵称
		    Integer userId = designPlanRecommended.getUserId();
		    SysUser sysUser = sysUserService.get(userId);
		    if( sysUser != null ){
		    	panoramaVo.setUserId(sysUser.getId());
		    	panoramaVo.setUserType(sysUser.getUserType());
		    	panoramaVo.setUserName(sysUser.getUserName());
		    	//如果是经销商账号 就取公司的Pid
		    	if (sysUser.getUserType().intValue() == 3 ) {
		    		// 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		        	BaseCompany company = baseCompanyService.get(companyId);
		        	if (company.getPid().intValue() > 0) {
		        		BaseCompany baseCompany = baseCompanyService.get(company.getPid());
		        		if(baseCompany != null){
			                panoramaVo.setCompanyName(baseCompany.getCompanyName());
		
			                // 获取用户公司LOGO
			                if( baseCompany.getCompanyLogo() != null ){
			                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
			                    if( resPic != null ){
			                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
			                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
			                    }
			                }
			            }
					}
		        	
				}else {
			        // 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		            BaseCompany baseCompany = baseCompanyService.get(companyId);
		            if(baseCompany != null){
		                panoramaVo.setCompanyName(baseCompany.getCompanyName());
	
		                // 获取用户公司LOGO
		                if( baseCompany.getCompanyLogo() != null ){
		                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
		                    if( resPic != null ){
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
		                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
		                    }
		                }
		            }
			    }
			}

		    // 获取房型类型
		    if(designPlanRecommended.getSpaceCommonId() != null){
		        SpaceCommon spaceCommon = spaceCommonService.get(designPlanRecommended.getSpaceCommonId());
		        if(spaceCommon != null && spaceCommon.getSpaceFunctionId() != null){
		            SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
		            panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
		        }
		    }

		    panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
		}
		return panoramaVo;
	}

	private PanoramaVo assemblyPanoramaVoPlanId(Integer planId) {
		PanoramaVo panoramaVo = new PanoramaVo();
		panoramaVo.setPlanId(planId);
		// 参数验证 ->start
		if(planId == null || planId < 1) {
			return panoramaVo;
		}
		// 参数验证 ->end
		
		DesignPlan designPlan = designPlanService.get(planId);
		if( designPlan != null && designPlan.getUserId() != null && designPlan.getUserId() > 0 ){
		    panoramaVo.setPlanId(planId);
		    // 设计方案风格，如果没有，则默认为混搭
		    Integer designStyleId = designPlan.getDesignStyleId();
		    panoramaVo.setTitle("3D全景漫游");
//		    panoramaVo.setTitle("3D全景漫游|混搭");
//		    if( designStyleId != null ) {
//		        BaseProductStyle style = baseProductStyleService.get(designStyleId);
//		        if( style != null ) {
//		            panoramaVo.setTitle("3D全景漫游|" + style.getName());
//		        }
//		    }
		    // 获取用户昵称
		    Integer userId = designPlan.getUserId();
		    SysUser sysUser = sysUserService.get(userId);
		    if( sysUser != null ){
		    	panoramaVo.setUserId(sysUser.getId());
		    	panoramaVo.setUserType(sysUser.getUserType());
		    	panoramaVo.setUserName(sysUser.getUserName());
		    	//如果是经销商账号 就取公司的Pid
		    	if (sysUser.getUserType().intValue() == 3 ) {
		    		// 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		        	BaseCompany company = baseCompanyService.get(companyId);
		        	if (company.getPid().intValue() > 0) {
		        		BaseCompany baseCompany = baseCompanyService.get(company.getPid());
		        		if(baseCompany != null){
			                panoramaVo.setCompanyName(baseCompany.getCompanyName());
		
			                // 获取用户公司LOGO
			                if( baseCompany.getCompanyLogo() != null ){
			                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
			                    if( resPic != null ){
			                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
			                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
			                    }
			                }
			            }
					}
		        	
				}else {
			        // 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		            BaseCompany baseCompany = baseCompanyService.get(companyId);
		            if(baseCompany != null){
		                panoramaVo.setCompanyName(baseCompany.getCompanyName());
	
		                // 获取用户公司LOGO
		                if( baseCompany.getCompanyLogo() != null ){
		                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
		                    if( resPic != null ){
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
		                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
		                    }
		                }
		            }
			    }
			}

		    // 获取房型类型
		    if( designPlan.getSpaceCommonId() != null ){
		        SpaceCommon spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
		        if( spaceCommon != null && spaceCommon.getSpaceFunctionId() != null ){
		            SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
		            panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
		        }
		    }

		    panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
		}
		return panoramaVo;
	}
    
    /**
     * 通过sysCode得到一个场景
     * @param sysCode
     * @return
     */
    @Override
    public Scene getSceneBySysCode(String sysCode, String type){
        Scene scene = null;
        Image image = null;
        ImageSphere imageSphere = null;
        ImageCube imageCube = null;
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setSysCode(sysCode);
        resRenderPic.setRenderingType(4);
        resRenderPic.setStart(0);
        resRenderPic.setLimit(1);
        List<ResRenderPic> list = resRenderPicService.getList(resRenderPic);
        ResRenderPic thumbPic = null;
        if( list != null && list.size() > 0 ){
            resRenderPic = list.get(0);
            scene = new Scene();
            scene.setName("scene_"+resRenderPic.getId());
            scene.setTitle("");
            // 获取缩略图
            thumbPic = resRenderPicService.selectOneByPid(resRenderPic.getId());
            if( thumbPic != null ) {
            	scene.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
            }
            scene.setPlanId(resRenderPic.getBusinessId());
            scene.setDesignPlanRecommendedId(resRenderPic.getPlanRecommendedId());
            scene.setDesignPlanRenderSceneId(resRenderPic.getDesignSceneId());
            scene.setView(View.getDefaultView());// 使用默认view
            image = new Image();
            File file = new File(Utils.getAbsolutePath(resRenderPic.getPicPath(), null));
            if( file.exists() ){
                // 如果是切片目录存储，则使用cube方式加载场景
                if(file.isDirectory()){
                    imageCube = new ImageCube();
                    if ("oldUnionStore".equals(type)) {
                        imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                    } else {
                        imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/pano_%s.jpg"));
                     }
                    image.setCube(imageCube);
                    scene.setImage(image);
                    // 预览图
                    Preview preview = new Preview();
                    preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/preview.jpg"));
                    scene.setPreview(preview);
                }else{
                    imageSphere = new ImageSphere();
                    imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                    image.setSphere(imageSphere);
                    scene.setImage(image);
                }
            }

            String windowsPercent = "width:100%;height:100%;";
            if( resRenderPic.getPanoLevel() != null ){
                if( RenderPanoLevel.LEVEL_ONE.equals(resRenderPic.getPanoLevel()) ){
                    windowsPercent = "width:35%;height:35%;";
                }else if( RenderPanoLevel.LEVEL_TWO.equals(resRenderPic.getPanoLevel()) ){
                    windowsPercent = "width:70%;height:70%;";
                }else if( RenderPanoLevel.LEVEL_THREE.equals(resRenderPic.getPanoLevel()) ){
                    windowsPercent = "width:90%;height:90%;";
                }
            }
            scene.setWindowsPercent(windowsPercent);
        }
        return scene;
    }

    @Override
    public List<ProductsCostType> getProductsCost(PanoramaVo panoramaVo){
    	
    	Integer planId = panoramaVo.getPlanId();
    	Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
    	Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();
    	Integer oneKeyDesignPlanId = panoramaVo.getOneKeyDesignPlanId();
    	Integer userId = panoramaVo.getUserId();
    	Integer userType = panoramaVo.getUserType();
    	List<BaseBrand> unionGroupBrandLs = panoramaVo.getUnionGroupBrands();
    	
    	
        List<ProductsCostType> list = null;
        if( (planId == null &&  designPlanRecommendedId == null && designPlanRenderSceneId == null && oneKeyDesignPlanId == null) 
        		|| userId == null || userType == null ){
            return null;
        }
        // 获取产品列表
        LoginUser loginUser = new LoginUser();
        loginUser.setId(userId);
        loginUser.setUserType(userType);
        DesignPlanProduct designPlanProduct = new DesignPlanProduct();
        designPlanProduct.setUserId(userId);

        List<Integer> putawayState = new ArrayList<>(3);
        putawayState.add(ProductStatuCode.HAS_BEEN_RELEASE);
        if (UserTypeCode.USER_TYPE_INNER.equals(loginUser.getUserType())) {
            putawayState.add(ProductStatuCode.HAS_BEEN_PUTAWAY);
            putawayState.add(ProductStatuCode.TESTING);
        }
        designPlanProduct.setProductPutawayStateList(putawayState);

        logger.error("designPlanRecommendedId = " + designPlanRecommendedId 
        		+ ";designPlanRenderSceneId = "+ designPlanRenderSceneId 
        		+ ";oneKeyDesignPlanId = " + oneKeyDesignPlanId 
        		+ ";planId=" + planId);
        if(designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
        	designPlanProduct.setPlanId(designPlanRecommendedId);
        	list = designPlanProductService.costList(loginUser,designPlanProduct, costListEnum.designPlanRecommended,unionGroupBrandLs);
        }else if(designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
        	designPlanProduct.setPlanId(designPlanRenderSceneId);
        	list = designPlanProductService.costList(loginUser,designPlanProduct, costListEnum.designPlanRenderScene,unionGroupBrandLs);
        }else if(oneKeyDesignPlanId != null && oneKeyDesignPlanId.intValue() > 0) {
        	designPlanProduct.setPlanId(oneKeyDesignPlanId);
        	list = designPlanProductService.costList(loginUser,designPlanProduct,costListEnum.oneKeyDesignPlan,unionGroupBrandLs);
        }else if(planId != null && planId > 0) {
        	designPlanProduct.setPlanId(planId);
        	list = designPlanProductService.costList(loginUser,designPlanProduct, costListEnum.designPlan,unionGroupBrandLs);
        }
        
        return list;
    }
    
    /**
     * 获取全景图组关系
     * @return
     */
    public List<Roam> getRoamRenderRelation(String uuid){
        List<Roam> roamList = null;
        if( StringUtils.isBlank(uuid) ){
            return null;
        }
        // 获取漫游
        logger.error("uuid   =  =  "+uuid);
        DesignRenderRoam designRenderRoam = designRenderRoamMapper.selectByUUID(uuid);
        if( designRenderRoam != null ){
            // 获取多720图片之间的关系信息
            String roamConfigContext = "";
            Integer roamConfigId = designRenderRoam.getRoamConfig();
            if( roamConfigId != null && roamConfigId.intValue() > 0 ){
                ResDesign roamConfig = resDesignService.get(roamConfigId);
                if( roamConfig != null ){
                	roamConfigContext = FileUploadUtils.getFileContext(Utils.getAbsolutePath(roamConfig.getFilePath(), null));
                	//FIXME:本地读取
                   /* roamConfigContext = FileUploadUtils.getJsonByInternet(Utils.getAbsolutePath(roamConfig.getFilePath(), null));*/

                }
            }

            if( StringUtils.isNotBlank(roamConfigContext) ){
                JSONArray jsonArray = JSONArray.fromObject(roamConfigContext);
                roamList = JSONArray.toList(jsonArray,Roam.class);
            }
        }
        return roamList;
    }

    /**
     * 720组合场景加载（主函数）
     * @param code 组合UUID
     * @return
     */
    @Override
    public GroupSceneVo vr720Group(String code){
        GroupSceneVo groupSceneVo = new GroupSceneVo();
        // 获取组合信息
        Group group = sysUserGroupService.doShare(code);
        if( group == null ){
            return null;
        }
        List<GroupDetail> groupDetailList = group.getList();
        if( Lists.isNotEmpty(groupDetailList) ) {
            StringBuilder sceneXml = new StringBuilder();
            List<Thumb> thumbList = new ArrayList<>();
            Scene scene = null;
            Scene sceneMain = null;
            Image image = null;
            ImageCube imageCube = null;
            Thumb thumb = null;
            ImageSphere imageSphere = null;
            GroupDetail groupDetail = null;
//            List<Hotspot> hotspotList = null;
            for( int i=0;i<groupDetailList.size();i++ ){
                groupDetail = groupDetailList.get(i);
                scene = new Scene();
                scene.setPlanId(groupDetail.getPlanId());
                /** 1.组装场景 **/
                scene.setName("scene_"+groupDetail.getPicId());
                scene.setTitle("");
                scene.setThumbUrl(FileUploadUtils.RESOURCES_URL + groupDetail.getThumbPath());
                image = new Image();
                File file = new File(Utils.getAbsolutePath(groupDetail.getPicPath(), null));
                if (file.exists()) {
                    // 如果是切片目录存储，则使用cube方式加载场景
                    if( file.isDirectory() ){
                        imageCube = new ImageCube();
                        imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(groupDetail.getPicPath() + "/pano_%s.jpg"));
                        image.setCube(imageCube);
                        scene.setImage(image);

                        // 预览图
                        Preview preview = new Preview();
                        preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(groupDetail.getPicPath() + "/preview.jpg"));
                        scene.setPreview(preview);
                    }else{
                        imageSphere = new ImageSphere();
                        imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(groupDetail.getPicPath()));
                        image.setSphere(imageSphere);
                        scene.setImage(image);
                    }
                }
                //FIXME:适应本地
               /* if(groupDetail.getPicPath().lastIndexOf(".") > 0){
                    imageSphere = new ImageSphere();
                    imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(groupDetail.getPicPath()));
                    image.setSphere(imageSphere);
                    scene.setImage(image);
                }else{
                    imageCube = new ImageCube();
                    imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(groupDetail.getPicPath() + "/pano_%s.jpg"));
                    image.setCube(imageCube);
                    scene.setImage(image);

                    // 预览图
                    Preview preview = new Preview();
                    preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(groupDetail.getPicPath() + "/preview.jpg"));
                    scene.setPreview(preview);
                }*/
                scene.setView(View.getDefaultView());// 使用默认的view参数
                // 判断类型
                if(RenderTypeCode.ROAM_720_LEVEL == groupDetail.getType() ){// 720漫游
                    // 添加热点
                }
                if(RenderTypeCode.COMMON_PICTURE_LEVEL == groupDetail.getType() ){// 普通高清渲染图
                    //
                }
                if(RenderTypeCode.COMMON_VIDEO == groupDetail.getType() ){// 视频
                    //
                }

                /** 2.组装缩略图列表 **/
                thumb = new Thumb();
                thumb.setType(groupDetail.getType());
                thumb.setThumbUrl(scene.getThumbUrl());
                thumb.setDesc(groupDetail.getThumbDesc());

                sceneXml.append(JAXBUtil.beanToXml(scene,Scene.class)).append(",");
                thumbList.add(thumb);

                if( i == 0 ){
                    sceneMain = scene;
                }
            }
            try {
                groupSceneVo.setScenes(URLEncoder.encode(sceneXml.substring(0, sceneXml.length() - 1), "UTF-8").replaceAll("\\+", "%20"));
                if( sceneMain != null ) {
                    // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
                    PanoramaVo panoramaVo = this.assemblyGroupPanoramaVoForNewScene(sceneMain.getPlanId());
                  /*  PanoramaVo panoramaVo = this.assemblyGroupPanoramaVo(sceneMain.getPlanId());*/
                    // 获取缩略图
                    panoramaVo.setThumbUrl(FileUploadUtils.RESOURCES_URL + group.getThumbPath());
                    groupSceneVo.setPanoramaVo(panoramaVo);
                }
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
            groupSceneVo.setThumbList(thumbList);
        }
        return groupSceneVo;
    }

	/***
	 * 获取设计方案副本的产品费用清单
	 * 
	 * @return
	 */
	@Override
	public List<ProductsCostType> getDesignRenderGroupCost(Integer planId, Integer userId, Integer userType) {
		List<ProductsCostType> list = null;
		if (planId == null || userId == null || userType == null) {
			return null;
		}
		// 获取产品列表
		LoginUser loginUser = new LoginUser();
		loginUser.setId(userId);
		loginUser.setUserType(userType);
		DesignPlanProductRenderScene designPlanProductRenderScene = new DesignPlanProductRenderScene();
		designPlanProductRenderScene.setPlanId(planId);
		designPlanProductRenderScene.setUserId(userId);
		list = designPlanProductRenderSceneService.costList(loginUser, designPlanProductRenderScene);
		return list;
	}

    /**
     * 组装打组720页面基本信息
     * @param planId
     * @return
     */
    @Override
    public PanoramaVo assemblyGroupPanoramaVo(Integer planId) {
        PanoramaVo panoramaVo = new PanoramaVo();
        DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(planId);
        if( designPlanRenderScene != null && designPlanRenderScene.getUserId() != null && designPlanRenderScene.getUserId() > 0 ){
            panoramaVo.setPlanId(planId);
            // 设计方案风格，如果没有，则默认为混搭
            Integer designStyleId = designPlanRenderScene.getDesignStyleId();
            panoramaVo.setTitle("3D全景漫游");
//            panoramaVo.setTitle("3D全景漫游|混搭");
//            if( designStyleId != null ) {
//                BaseProductStyle style = baseProductStyleService.get(designStyleId);
//                if( style != null ) {
//                    panoramaVo.setTitle("3D全景漫游|" + style.getName());
//                }
//            }
            // 获取用户昵称
            Integer userId = designPlanRenderScene.getUserId();
            SysUser sysUser = sysUserService.get(userId);
            if( sysUser != null ){
		    	panoramaVo.setUserId(sysUser.getId());
		    	panoramaVo.setUserType(sysUser.getUserType());
		    	panoramaVo.setUserName(sysUser.getUserName());
		    	//如果是经销商账号 就取公司的Pid
		    	if (sysUser.getUserType().intValue() == 3 ) {
		    		// 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		        	BaseCompany company = baseCompanyService.get(companyId);
		        	if (company.getPid().intValue() > 0) {
		        		BaseCompany baseCompany = baseCompanyService.get(company.getPid());
		        		if(baseCompany != null){
			                panoramaVo.setCompanyName(baseCompany.getCompanyName());
		
			                // 获取用户公司LOGO
			                if( baseCompany.getCompanyLogo() != null ){
			                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
			                    if( resPic != null ){
			                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
			                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
			                    }
			                }
			            }
					}
		        	
				}else {
			        // 获取用户公司
		        	Integer companyId = sysUser.getBusinessAdministrationId();
		            BaseCompany baseCompany = baseCompanyService.get(companyId);
		            if(baseCompany != null){
		                panoramaVo.setCompanyName(baseCompany.getCompanyName());
	
		                // 获取用户公司LOGO
		                if( baseCompany.getCompanyLogo() != null ){
		                    ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
		                    if( resPic != null ){
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
		                    	panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
		                    }
		                }
		            }
			    }
			}

            // 获取房型类型
            if( designPlanRenderScene.getSpaceCommonId() != null ){
                SpaceCommon spaceCommon = spaceCommonService.get(designPlanRenderScene.getSpaceCommonId());
                if( spaceCommon != null && spaceCommon.getSpaceFunctionId() != null ){
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }

        return panoramaVo;
    }
    
    
    /**
     * 从自动渲染资源变量里获取场景信息
     */
	@Override
	public Scene getMobileSceneBySysCodeForAuto(String sysCode) {
		Scene scene = null;
        Image image = null;
        ImageSphere imageSphere = null;
        ImageCube imageCube = null;
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setSysCode(sysCode);
        resRenderPic.setRenderingType(4);
        resRenderPic.setStart(0);
        resRenderPic.setLimit(1);
        List<ResRenderPic> list = resRenderPicService.selectListOfMobile(resRenderPic);
        ResRenderPic thumbPic = null;
        if( list != null && list.size() > 0 ){
            resRenderPic = list.get(0);
            scene = new Scene();
            scene.setName("scene_"+resRenderPic.getId());
            scene.setTitle("");
            // 获取缩略图
            thumbPic = resRenderPicService.selectOneByPidOfMobile(resRenderPic.getId());
            if( thumbPic != null ) {
            	scene.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
            }
            scene.setPlanId(resRenderPic.getBusinessId());
            scene.setView(View.getDefaultView());// 使用默认view
            image = new Image();
            File file = new File(Utils.getAbsolutePath(resRenderPic.getPicPath(), null));
            if (file.exists()) {
                // 如果是切片目录存储，则使用cube方式加载场景
                if( file.isDirectory() ){
                    imageCube = new ImageCube();
                    imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/pano_%s.jpg"));
                    image.setCube(imageCube);
                    scene.setImage(image);

                    // 预览图
                    Preview preview = new Preview();
                    preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/preview.jpg"));
                    scene.setPreview(preview);
                }else{
                    imageSphere = new ImageSphere();
                    imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                    image.setSphere(imageSphere);
                    scene.setImage(image);
                }
            }
            String windowsPercent = "width:100%;height:100%;";
            scene.setWindowsPercent(windowsPercent);
        }
        return scene;
	}
    
	
	 /**
     * 从自动渲染资源里获取场景信息
     */
	@Override
	public SingleSceneVo vr720SingleMobileForAuto(String sysCode) {
		 SingleSceneVo singleSceneVo = null;
	        if(StringUtils.isNotBlank(sysCode) ){
	            singleSceneVo = new SingleSceneVo();
	            // 获取单个场景对象
	            Scene scene = this.getMobileSceneBySysCodeForAuto(sysCode);
	            if( scene != null ) {
	                singleSceneVo.setScene(JAXBUtil.beanToXml(scene,Scene.class));

	                // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
	                PanoramaVo panoramaVo = this.assemblyPanoramaVoForAuto(scene.getPlanId());
	                panoramaVo.setThumbUrl(scene.getThumbUrl());
	                panoramaVo.setWindowsPercent(scene.getWindowsPercent());
	                singleSceneVo.setPanoramaVo(panoramaVo);
	                
	                // 获取设计方案产品费用清单
	                //如果有就走以前,没有就走onekey
	                ResRenderPic resRenderPic = new ResRenderPic();
	                resRenderPic.setSysCode(sysCode);
	                resRenderPic.setBusinessId(panoramaVo.getPlanId());
	                
	                List<ProductsCostType> list = this.getProductsCost(panoramaVo);
	                if( list != null && list.size() > 0 ){
	                    BigDecimal totalPrice = new BigDecimal(0.0);
	                    for( ProductsCostType cost : list ){
	                        totalPrice = totalPrice.add(cost.getTotalPrice());
	                    }
	                    singleSceneVo.setTotalPrice(totalPrice.toString());
	                    singleSceneVo.setProductsCostTypeList(list);
	                }

	                // 资源访问url
	                singleSceneVo.setResourceUrl(FileUploadUtils.RESOURCES_URL);
	            }
	        }
	        return singleSceneVo;
	}

	@Override
	public PanoramaVo assemblyPanoramaVoForAuto(Integer planId) {
		 PanoramaVo panoramaVo = new PanoramaVo();
		 panoramaVo.setPlanId(planId);
		 panoramaVo.setOneKeyDesignPlanId(planId);
		 panoramaVo.setTitle("3D全景漫游");
//		 panoramaVo.setTitle("3D全景漫游|混搭");
		 DesignPlan designPlan = optimizeDesingPlanService.getPlan(planId);
        // 获取房型类型
        if( designPlan.getSpaceCommonId() != null ){
            SpaceCommon spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
            if( spaceCommon != null && spaceCommon.getSpaceFunctionId() != null ){
                SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
            }
        }
        panoramaVo.setUserId(designPlan.getUserId());
        //FIXME: 这里只是把usertype致为    不为3且非空  ， 判断使用，以后逻辑有修改的话，这里要改   add by  yangzhun
        panoramaVo.setUserType(4);
        panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        //TODO : Check it
        panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + "/AA/c_basedesign/2017/09/23/19/product/baseCompany/logo/339407_20170923194347405_1.png");
	    return panoramaVo;
	}
	
	/**
	 * FIXME:添加一个方法测试移动端的720场景去掉限制大小
     * 单场景加载（主函数）
     * @param sysCode
     * @return
     */
    @Override
    public SingleSceneVo vr720MobileSingle(String sysCode){
        SingleSceneVo singleSceneVo = null;
        if(StringUtils.isNotBlank(sysCode) ){
            singleSceneVo = new SingleSceneVo();
            // 获取单个场景对象
            Scene scene = this.getMobileSceneBySysCode(sysCode);
            if( scene != null ) {
                singleSceneVo.setScene(JAXBUtil.beanToXml(scene,Scene.class));

                // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
                PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);
                panoramaVo.setThumbUrl(scene.getThumbUrl());
                panoramaVo.setWindowsPercent(scene.getWindowsPercent());
                singleSceneVo.setPanoramaVo(panoramaVo);
                
                // 获取设计方案产品费用清单
                //如果有就走以前,没有就走onekey
                List<ProductsCostType> list = this.getProductsCost(panoramaVo);
                if( list != null && list.size() > 0 ){
                    BigDecimal totalPrice = new BigDecimal(0.0);
                    for( ProductsCostType cost : list ){
                        totalPrice = totalPrice.add(cost.getTotalPrice());
                    }
                    singleSceneVo.setTotalPrice(totalPrice.toString());
                    singleSceneVo.setProductsCostTypeList(list);
                }

                // 资源访问url
                singleSceneVo.setResourceUrl(FileUploadUtils.RESOURCES_URL);
            }
        }
        return singleSceneVo;
    }

	private Scene getMobileSceneBySysCode(String sysCode) {
        Scene scene = null;
        Image image = null;
        ImageSphere imageSphere = null;
        ImageCube imageCube = null;
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setSysCode(sysCode);
        resRenderPic.setRenderingType(4);
        resRenderPic.setStart(0);
        resRenderPic.setLimit(1);
        List<ResRenderPic> list = resRenderPicService.getList(resRenderPic);
        ResRenderPic thumbPic = null;
        if( list != null && list.size() > 0 ){
            resRenderPic = list.get(0);
            scene = new Scene();
            scene.setName("scene_"+resRenderPic.getId());
            scene.setTitle("");
            // 获取缩略图
            thumbPic = resRenderPicService.selectOneByPid(resRenderPic.getId());
            if( thumbPic != null ) {
            	scene.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
            }
            scene.setPlanId(resRenderPic.getBusinessId());
            scene.setDesignPlanRecommendedId(resRenderPic.getPlanRecommendedId());
            scene.setDesignPlanRenderSceneId(resRenderPic.getDesignSceneId());
            scene.setView(View.getDefaultView());// 使用默认view
            image = new Image();
            File file = new File(Utils.getAbsolutePath(resRenderPic.getPicPath(), null));
            if (file.exists()) {
                // 如果是切片目录存储，则使用cube方式加载场景
                if( file.isDirectory() ){
                    imageCube = new ImageCube();
                    imageCube.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/pano_%s.jpg"));
                    image.setCube(imageCube);
                    scene.setImage(image);

                    // 预览图
                    Preview preview = new Preview();
                    preview.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath() + "/preview.jpg"));
                    scene.setPreview(preview);
                }else{
                    imageSphere = new ImageSphere();
                    imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                    image.setSphere(imageSphere);
                    scene.setImage(image);
                }
            }
            String windowsPercent = "width:100%;height:100%;";
            scene.setWindowsPercent(windowsPercent);
        }
        return scene;
    }

	@Override
	public List<SingleSceneVo> vr720UnionStoreSingle(Integer releaseId) {
	  if(releaseId == null) {
	    return null;
	  }
	  UnionDesignPlanStoreRelease planStoreRelease = unionDesignPlanStoreReleaseService.get(releaseId);
	  if(planStoreRelease == null || planStoreRelease.getId() == null) {
          return null;
      }

      SingleSceneVo singleSceneVo = null;
      List<ResRenderPic> renderPicLs = new ArrayList<ResRenderPic>();
      List<SingleSceneVo> singleSceneVoLs = new ArrayList<SingleSceneVo>();
      renderPicLs = new ArrayList<ResRenderPic>();
      renderPicLs = resRenderPicService.getListByUnionDesignPlanStoreReleaseId(releaseId);
      if(renderPicLs == null || renderPicLs.size() <= 0) {
        return null;
      }
      //同店联盟方案相关信息
      UnionStoreVo unionStoreVo = this.getUnionStoreInfoByReleaseId(releaseId);
      if(unionStoreVo == null) {
        return null;
      }
      //其他信息
      for (ResRenderPic renderPic : renderPicLs) {
        if(renderPic == null) {
          break;
        }

        String picSyscode = renderPic.getSysCode();
        if(StringUtils.isNotBlank(picSyscode) ){

          singleSceneVo = new SingleSceneVo();
          // 获取单个场景对象
          Scene scene = this.getSceneBySysCode(picSyscode, "oldUnionStore");
          if( scene != null ) {
              singleSceneVo.setScene(JAXBUtil.beanToXml(scene,Scene.class));

              // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
              /* PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);*/
              //update by chenm on 2018/11/19
              PanoramaVo panoramaVo = this.assemblyPanoramaVoForNewScene(scene);
              panoramaVo.setThumbUrl(scene.getThumbUrl());
              panoramaVo.setWindowsPercent(scene.getWindowsPercent());
              singleSceneVo.setPanoramaVo(panoramaVo);
              panoramaVo.setRenderPicId(renderPic.getId());
              if(scene.getImage() != null) {
                  //将渲染图地址取出显示,提供给前端
                  String picUrl ;
                  if(scene.getImage().getCube() != null) {
                      picUrl = scene.getImage().getCube().getUrl();
                      singleSceneVo.setIsShear(DesignPlanStoreReleaseDetailsVo.IsShear.IS_SHEAR_6);
                  } else {
                      picUrl = scene.getImage().getSphere().getUrl();
                  }
                  if(StringUtils.isNotBlank(picUrl)) {
                    singleSceneVo.setPicPath(picUrl);
                  }
              }

              // 资源访问url
              singleSceneVo.setResourceUrl(FileUploadUtils.RESOURCES_URL);
              singleSceneVo.setUnionStoreVo(unionStoreVo);
          }
        }

        singleSceneVoLs.add(singleSceneVo);
      }

        return singleSceneVoLs;
    }

	/**
	 * 获取联盟方案中店面相关信息
	 * @return
	 */
	public UnionStoreVo getUnionStoreInfoByReleaseId(Integer releaseId) {
        UnionStoreVo unionStoreVo = new UnionStoreVo();
        UnionDesignPlanStoreRelease planStoreRelease = new UnionDesignPlanStoreRelease();
        planStoreRelease = unionDesignPlanStoreReleaseService.get(releaseId);
        if(planStoreRelease == null || planStoreRelease.getStorefrontId() == null) {
            return null;
        }

        /**
         * 增加人气
         * add by zhangwj   2018-03-12  COMMON-822
         * 当已有人气数超过20后，每次点击访问页面时，人气数增加一个随机数（随机数在1-50）。
         */
        if( planStoreRelease.getPopularityValue() == null ){
            planStoreRelease.setPopularityValue(1);
        /* 2018-07-13   COMMON-1064 去掉随机人气。每次只+1
        }else if( planStoreRelease.getPopularityValue().intValue() > 20 ){
            Random random = new Random();
            Integer randomNum = random.nextInt(51);
            planStoreRelease.setPopularityValue(planStoreRelease.getPopularityValue() + randomNum);*/
        }else{
            planStoreRelease.setPopularityValue(planStoreRelease.getPopularityValue() + 1);
        }

        unionDesignPlanStoreReleaseService.update(planStoreRelease);
        unionStoreVo.setReleaseName(planStoreRelease.getReleaseName() == null ? "三度云享家" : planStoreRelease.getReleaseName());
        //查询门店信息
        UnionStorefront unionStorefront = unionStorefrontService.get(planStoreRelease.getStorefrontId());
        unionStoreVo.setPopularityValue(planStoreRelease.getPopularityValue());
        /*if(unionStorefront == null) {
            return unionStoreVo;
        }*/

        Integer userId = planStoreRelease.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        BaseCompany company = null;
        if (sysUser.getCompanyId() != null) {
            company = baseCompanyService.get(sysUser.getCompanyId());
        }

        /**
         * 如果选择了门店信息则显示门店信息
         */
        if(unionStorefront != null){
            unionStoreVo.setName(unionStorefront.getName());
            unionStoreVo.setContact(unionStorefront.getContact());
            unionStoreVo.setAddress(unionStorefront.getAddress());
            unionStoreVo.setIsDisplayed(unionStorefront.getIsDisplayed());
            unionStoreVo.setPhone(unionStorefront.getPhone());
            /**
             *  logo地址
             *  原需求是要求显示该方案发布者绑定的第一个序列号对应品牌
             *  但是现在已去除序列号的逻辑，用户关联企业可能有多个品牌，也没有产品负责这块
             *  那就改成如果选择显示品牌信息，则显示用户关联企业logo吧.....
             *  update by chenm on 2018/11/19
             */
            if(unionStorefront.getIsDisplayed() == 1) {
                if(company != null && company.getCompanyLogo() != null){
                    ResPic logoPic = resPicService.get(company.getCompanyLogo());
                    unionStoreVo.setBrandLogoPath(logoPic.getPicPath());//前端实际使用的是这个字段吧
                    unionStoreVo.setCompanyLogoPath(logoPic.getPicPath());
                }
            }

        }else {
            /**
             * 未选择门店信息的话
             * 厂商账号优先取品牌馆信息，其次取厂商企业信息，取不到则不显示；
             * 非厂商账号，取用户创建的发布到随选网的店铺，如果没有则不显示。
             * update by chenm on 2018/11/19
             */
            CompanyShop companyShop = companyShopService.getUserCompanyShopForNewScene(userId);
            if (companyShop != null) {
                unionStoreVo.setName(companyShop.getShopName());
                unionStoreVo.setPhone(companyShop.getContactPhone());
                if (companyShop.getLogoPicId() != null) {
                    ResPic logoPic = resPicService.get(companyShop.getLogoPicId());
                    if (logoPic != null) {
                        unionStoreVo.setBrandLogoPath(logoPic.getPicPath());
                    }
                }

            } else {
                if (Objects.equals(UserTypeConstant.USER_TYPE_COMPANY, sysUser.getUserType())) {
                    if (company != null) {
                        unionStoreVo.setName(company.getCompanyName());
                        unionStoreVo.setPhone(company.getPhone());
                        if (company.getCompanyLogo() != null) {
                            ResPic logoPic = resPicService.get(company.getCompanyLogo());
                            if (logoPic != null) {
                                unionStoreVo.setBrandLogoPath(logoPic.getPicPath());
                                unionStoreVo.setCompanyLogoPath(logoPic.getPicPath());
                            }
                        }
                    }
                } else {
                    //什么都不显示
                }
            }


        }
        return unionStoreVo;
	}

    /**
     * 获取联盟方案中店面相关信息
     * 跳转到随选网的场景方法.....
     * add by chenm on 2018/11/19
     *
     * @return
     */
    public UnionStoreVo getUnionStoreDataByReleaseId(Integer releaseId) {
        UnionStoreVo unionStoreVo = new UnionStoreVo();
        UnionDesignPlanStoreRelease planStoreRelease = unionDesignPlanStoreReleaseService.get(releaseId);
        if(planStoreRelease == null) {
            return null;
        }

        if( planStoreRelease.getPopularityValue() == null ){
            planStoreRelease.setPopularityValue(1);
        }else{
            planStoreRelease.setPopularityValue(planStoreRelease.getPopularityValue() + 1);
        }
        Integer userId = planStoreRelease.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        BaseCompany company = baseCompanyService.get(sysUser.getBusinessAdministrationId());

        unionDesignPlanStoreReleaseService.update(planStoreRelease);
        unionStoreVo.setReleaseName(planStoreRelease.getReleaseName() == null ? "三度云享家" : planStoreRelease.getReleaseName());
        //查询门店信息
        UnionStorefront unionStorefront = new UnionStorefront();
        if(planStoreRelease.getStorefrontId() != null){
            unionStorefront = unionStorefrontService.get(planStoreRelease.getStorefrontId());
        }
        unionStoreVo.setPopularityValue(planStoreRelease.getPopularityValue());
        if(unionStorefront != null){
            unionStoreVo.setStorefrontId(unionStorefront.getId());
            unionStoreVo.setName(unionStorefront.getName());
            unionStoreVo.setContact(unionStorefront.getContact());
            unionStoreVo.setAddress(unionStorefront.getAddress());
            unionStoreVo.setIsDisplayed(unionStorefront.getIsDisplayed());
            unionStoreVo.setPhone(unionStorefront.getPhone());
            unionStoreVo.setUserId(unionStorefront.getUserId());
            //同城联盟添加的联系人是没有头像的。。。所以不显示
        }else{
            //没有门店则显示用户信息
            unionStoreVo.setContact(sysUser.getUserName());
            if(sysUser.getPicId() != null){
                ResPic pic = resPicService.get(sysUser.getPicId());
                unionStoreVo.setLogo(pic.getPicPath());
            }

        }
        return unionStoreVo;
    }

	/**
	 * 获取同城联盟720单点分享商品清单
	 */
    @Override
    public UnionStoreProductsCostVo getUnionStoreProductsCostList(Integer releaseId, Integer resPicId) {
      
      UnionStoreProductsCostVo productsCostVo = new UnionStoreProductsCostVo();
      List<ProductsCostType> costTypeList = new ArrayList<ProductsCostType>();
      //验证参数
      if(releaseId == null || resPicId == null) {
          return null;
      }
      UnionDesignPlanStoreRelease planStoreRelease = unionDesignPlanStoreReleaseService.get(releaseId);
      if(planStoreRelease == null || planStoreRelease.getId() == null) {
          return null;
      }
      String key = "USReleaseCost_" + releaseId.toString() + "_picId_" + resPicId.toString(); //拼接缓存中的key
      try {
        //是否开启缓存
        if(Utils.enableRedisCache()) {
          //从缓存中读取商品清单
          Object object = JedisUtils.getObject(key);
          if(object != null) {
            UnionStoreProductsCostVo storeProductsCostVo = (UnionStoreProductsCostVo)object;
            return storeProductsCostVo;
          }
          
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      //场景图
      ResRenderPic pic = new ResRenderPic();
      pic = resRenderPicService.get(resPicId);
      if(pic == null || pic.getSysCode() == null) {
          return null;
      }
      //获取信息
      PanoramaVo panoramaVo = this.getPanoramaVoByPicSysCode(pic);
      //获取同城联盟中同组员选择的品牌列表(需求要求可以看到同联盟组员选择的品牌)
      List<BaseBrand> baseBrandList = baseBrandService.getBrandLsByReleaseId(releaseId);
      panoramaVo.setUnionGroupBrands(baseBrandList);
      
      // 获取设计方案产品费用清单
      //如果有就走以前,没有就走onekey
      costTypeList = this.getProductsCost(panoramaVo);
      if( costTypeList != null && costTypeList.size() > 0 ){
          BigDecimal totalPrice = new BigDecimal(0.0);
          for( ProductsCostType cost : costTypeList ){
              totalPrice = totalPrice.add(cost.getTotalPrice());
          }
          //商品清单总价
          productsCostVo.setTotalPrice(totalPrice.toString());
          //商品清单详情列表
          productsCostVo.setProductsCostTypeList(costTypeList);
      }
      try {
        //是否开启缓存
        if(Utils.enableRedisCache()) {
          JedisUtils.setObject(key, productsCostVo, 10*60);//失效时间设置为10分钟
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return productsCostVo;
    }
    
    /**
     * 组装查询商品清单列表需要的信息
     * @param
     * @return
     */
    private PanoramaVo getPanoramaVoByPicSysCode(ResRenderPic renderPic) {
      PanoramaVo panoramaVo = new PanoramaVo();
      if(renderPic == null || StringUtils.isBlank(renderPic.getSysCode())) {
          return null;
      }
      
      panoramaVo.setPlanId(renderPic.getBusinessId());
      panoramaVo.setDesignPlanRecommendedId(renderPic.getPlanRecommendedId());
      panoramaVo.setDesignPlanRenderSceneId(renderPic.getDesignSceneId());
      
      SysUser sysUser = new SysUser();
      if(renderPic.getPlanRecommendedId() != null && renderPic.getPlanRecommendedId().intValue() > 0) {
           DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(renderPic.getPlanRecommendedId());
          if(planRecommended != null && planRecommended.getUserId() != null && planRecommended.getUserId() > 0) {
              Integer userId = planRecommended.getUserId();
              sysUser = sysUserService.get(userId);
          }
      }else if (renderPic.getDesignSceneId() != null & renderPic.getDesignSceneId().intValue() > 0) {
          DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(renderPic.getDesignSceneId());
          if(designPlanRenderScene != null && designPlanRenderScene.getId() != null && designPlanRenderScene.getUserId() != null) {
            Integer userId = designPlanRenderScene.getUserId();
            sysUser = sysUserService.get(userId);
          }
        
      }else if(panoramaVo.getPlanId() != null && panoramaVo.getPlanId() > 0) {
          DesignPlan designPlan = designPlanService.get(panoramaVo.getPlanId());
          if( designPlan != null && designPlan.getUserId() != null && designPlan.getUserId() > 0 ){
            Integer userId = designPlan.getUserId();
            sysUser = sysUserService.get(userId);
          }
      }
     //得到用户信息 
      if(sysUser != null && sysUser.getId() != null && sysUser.getUserType() != null) {
          panoramaVo.setUserId(sysUser.getId());
          panoramaVo.setUserType(sysUser.getUserType());
      }
      return panoramaVo;
    }


    /**
     * 同城联盟720单点分享页方案数据
     */
    @Override
    public List<UnionStoreSingleSenceInfoVo> getUnionStoreSingleSenceInfoList(Integer releaseId) {
      if(releaseId == null) {
          return null;
      }
      UnionDesignPlanStoreRelease planStoreRelease = unionDesignPlanStoreReleaseService.get(releaseId);
      if(planStoreRelease == null || planStoreRelease.getId() == null) {
          return null;
      }
      List<UnionStoreSingleSenceInfoVo> singleSenceInfoVo = new ArrayList<UnionStoreSingleSenceInfoVo>();
     
      //获得该发布方案的所有场景图信息
      List<ResRenderPic> renderPicLs = new ArrayList<ResRenderPic>();
      renderPicLs = new ArrayList<ResRenderPic>();
      renderPicLs = resRenderPicService.getListByUnionDesignPlanStoreReleaseId(releaseId);
      if(renderPicLs == null || renderPicLs.size() < 1) {
          return null;
      }
      //拼接信息
      for (ResRenderPic resRenderPic : renderPicLs) {
          UnionStoreSingleSenceInfoVo senceInfoVo = new UnionStoreSingleSenceInfoVo();
          senceInfoVo.setPicId(resRenderPic.getId());//场景图id
          ResRenderPic thumbPic = resRenderPicService.selectOneByPid(resRenderPic.getId());
          if(thumbPic != null) {
              senceInfoVo.setPlanThumbnailUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath())); //缩略图地址
          }
          //发布素材
          UnionDesignPlanStore designPlanStore = new UnionDesignPlanStore();
          UnionDesignPlanStore planStore = new UnionDesignPlanStore();
          planStore.setRenderPicId(resRenderPic.getId());
          planStore.setRenderPicSmallId(thumbPic.getId());
          List<UnionDesignPlanStore> planStoreLs = unionDesignPlanStoreService.getList(planStore);
          if(planStoreLs != null && planStoreLs.size() > 0) {
              designPlanStore = planStoreLs.get(0);
              senceInfoVo.setDesignPlanName(designPlanStore.getDesignPlanName());//原方案名称
          }
  
          singleSenceInfoVo.add(senceInfoVo);
      }
      return singleSenceInfoVo;
    }


    /**
     * 加载720场景中用户相关业务信息
     * SDKJ-31 “通用版分享头像信息优先取随选网店铺信息”
     * 如果是推荐方案：
     * 按企业和企业类型去查店铺(厂商查品牌馆，其他查创建最早的店铺/以后查主店铺),
     * 如果有则显示店铺信息,如果没有则显示企业信息。
     * 非推荐方案：
     * 如果是厂商账号则取品牌馆信息，其次取厂商企业的信息，都没有则取用户自身信息;
     * 非厂商账号，取用户最早创建的发布到随选网的店铺,如果没有则取自身信息
     * @param resRenderPic 渲染图
     * @return
     */
    @Override
    public SceneUserInfoDto getSceneUserInfo(ResRenderPic resRenderPic,Integer userId){
        SceneUserInfoDto infoDto = new SceneUserInfoDto();
        SysUser sysUser = new SysUser();

        //推荐方案
        DesignPlanRecommended planRecommended = null;
        //获取用户信息
        if(resRenderPic != null && userId == null){
            //设计方案
            Integer planId = resRenderPic.getBusinessId();
            DesignPlan designPlan = null;
            //效果图方案
            Integer designDesignSceneId = resRenderPic.getDesignSceneId();
            DesignPlanRenderScene planRenderScene = null;
            //推荐方案
            Integer planRecommendedId = resRenderPic.getPlanRecommendedId();
            if(planRecommendedId != null && planRecommendedId > 0) {
                planRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
            }
            if(resRenderPic.getCreateUserId() > 0L){
                userId = Integer.parseInt(resRenderPic.getCreateUserId() + "");
            }else{
                //如果渲染图中没有记录用户信息，则通过方案得到用户信息
                if(planRecommendedId != null && planRecommendedId > 0){
                    if(planRecommended != null){
                        userId = planRecommended.getUserId();
                    }
                }else if(designDesignSceneId != null && designDesignSceneId > 0){
                    planRenderScene = designPlanRenderSceneService.get(designDesignSceneId);
                    if(planRenderScene != null && planRenderScene.getUserId() != null){
                        userId = planRenderScene.getUserId();
                    }
                }else if(planId != null && planId > 0){
                    designPlan = designPlanService.get(planId);
                    if(designPlan != null && designPlan.getUserId() != null){
                        userId = designPlan.getUserId();
                    }
                }else{
                    logger.error("该效果图没有效果图方案，推荐方案，样板房方案id,参数resRenderPic：{}" + resRenderPic.toString());
                    return infoDto;
                }
            }
        }

        sysUser = sysUserService.get(userId);
        if(sysUser == null){
            logger.error("没有找到该用户信息,userId:{}" , userId);
            return infoDto;
        }

        //查找用户企业信息
        BaseCompany company = null;
        //是否显示用户自身信息
        boolean isShowUserInfo = false;
        ResPic logoPic = null;
        String contactName = null;
        String contactPhone = null;
        CompanyShop companyShop = new CompanyShop();
        if(planRecommended != null && planRecommended.getId() > 0){
            company = baseCompanyService.get(planRecommended.getCompanyId());
            //如果是推荐方案，应该按照企业去找店铺，因为交付/分享的推荐方案userId和companyId不对应
            CompanyShop shopSearch = new CompanyShop();
            SysDictionary companyTypeDictionary = sysDictionaryService.findOneByTypeAndValueKeyInCache(
                    SysDictionaryConstant.SYS_DICTIONARY_COMPANY_BUSINESS_TYPE,SysDictionaryConstant.COMPANY_BUSINESS_TYPE_VENDOR);
            if(companyTypeDictionary != null && Objects.equals(company.getBusinessType(),companyTypeDictionary.getValue())){
                SysDictionary shopTypeDictionary = sysDictionaryService.findOneByTypeAndValueKeyInCache(SysDictionaryConstant.SYS_DICTINARY_SHOP_TYPE , SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION);
                if(shopTypeDictionary == null){
                    logger.error("数据字典中缺失type为'{}',valuekey为'{}'的数据!",SysDictionaryConstant.SYS_DICTINARY_SHOP_TYPE ,SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION);
                }else{
                    shopSearch.setBusinessType(shopTypeDictionary.getValue());
                }
                shopSearch.setOrder("id");
                shopSearch.setOrderNum("desc");
            }else{
                shopSearch.setOrder("gmt_create");
                shopSearch.setOrderNum("asc");//取最早创建的数据
            }
            shopSearch.setCompanyId(company.getId());
            shopSearch.setIsDeleted(0);
            //必须是发布到随选网的店铺
            shopSearch.setSch_releasePlatformValues("2");
            List<CompanyShop> shopList = companyShopService.getList(shopSearch);
            if(CustomerListUtils.isNotEmpty(shopList)){
                companyShop = shopList.get(0);
            }
            if(companyShop != null && companyShop.getId() != null && companyShop.getId() > 0) {
                contactName = companyShop.getShopName();
                contactPhone = companyShop.getContactPhone();
                if(companyShop.getLogoPicId() != null){
                    logoPic = resPicService.get(companyShop.getLogoPicId());
                }
            }else{
                if (company != null) {
                    if (company.getCompanyLogo() != null) {
                        logoPic = resPicService.get(company.getCompanyLogo());
                    }
                    contactName = company.getCompanyName();
                    contactPhone = company.getPhone();
                } else {
                    isShowUserInfo = true;
                }
            }
        }else{
            //草图方案或效果图方案
            if (Objects.equals(UserTypeConstant.USER_TYPE_COMPANY, sysUser.getUserType())) {
                if (sysUser.getCompanyId() != null) {
                    company = baseCompanyService.get(sysUser.getCompanyId());
                }
            }else{
                if(sysUser.getBusinessAdministrationId() != null){
                    company = baseCompanyService.get(sysUser.getBusinessAdministrationId());
                }
            }
            /**
             * 查询场景显示的头像和名称,联系电话
             * 厂商账号有优先取品牌馆信息，其次取厂商企业信息，取不到则显示账号信息；
             * 非厂商账号，取用户创建的发布到随选网的店铺，如果没有则显示账号信息。
             */
            companyShop = companyShopService.getUserCompanyShopForNewScene(userId);
            infoDto.setCompanyShopId(companyShop != null ? companyShop.getId():null);
            if(companyShop != null && companyShop.getId() != null && companyShop.getId() > 0){
                //显示店铺信息
                contactName = companyShop.getShopName();
                contactPhone = companyShop.getContactPhone();
                if(companyShop.getLogoPicId() != null){
                    logoPic = resPicService.get(companyShop.getLogoPicId());
                }
            }else {
                if (Objects.equals(UserTypeConstant.USER_TYPE_COMPANY, sysUser.getUserType())) {
                    //厂商账号查找显示信息
                    if (company != null) {
                        //没有店铺信息，则取厂商企业信息
                        contactName = company.getCompanyName();
                        contactPhone = company.getPhone();
                        if (company.getCompanyLogo() != null) {
                            logoPic = resPicService.get(company.getCompanyLogo());
                        }
                    } else {
                        //没有企业信息则显示账号信息
                        isShowUserInfo = true;
                    }
                } else {
                    //非厂商账号
                    isShowUserInfo = true;
                }
            }
        }
        if(isShowUserInfo){
            //显示用户信息
            contactName = StringUtils.isNotBlank(sysUser.getUserName())? sysUser.getUserName() : sysUser.getNickName();
            contactPhone = sysUser.getMobile();
            if(sysUser.getPicId() != null){
                logoPic  = resPicService.get(sysUser.getPicId());
            }
        }
        //赋值
        infoDto.setUserId(userId);
        infoDto.setContactName(contactName);
        infoDto.setContactPhone(contactPhone);
        infoDto.setLogo(logoPic == null ? null : logoPic.getPicPath());
        infoDto.setCompanyTypeValue(company != null ? company.getBusinessType() : null);
        infoDto.setCompanyShopId(companyShop == null ? null : companyShop.getId());
        return infoDto;
    }


    public SceneUserInfoDto getSceneUserInfo2(ResRenderPic resRenderPic){
        SceneUserInfoDto infoDto = new SceneUserInfoDto();
        SysUser sysUser = new SysUser();

        if(resRenderPic == null){
            logger.error("参数resRenderPic为空");
            return infoDto;
        }
        Integer planId = resRenderPic.getBusinessId();//设计方案Id
        Integer designDesignSceneId = resRenderPic.getDesignSceneId();//效果图方案Id
        Integer planRecommendedId = resRenderPic.getPlanRecommendedId();//推荐方案Id

        //获取用户信息
        Integer userId = null;
        Integer planRecommendedCompanyId = null;
        if(resRenderPic.getCreateUserId() > 0){
            userId = Integer.parseInt(resRenderPic.getCreateUserId() + "");
        }else{
            if(planRecommendedId != null && planRecommendedId > 0){
                DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
                if(planRecommended != null){
                    userId = planRecommended.getUserId();
                    planRecommendedCompanyId = planRecommended.getCompanyId();
                }

            }else if(designDesignSceneId != null && designDesignSceneId > 0){
                DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(designDesignSceneId);
                if(planRenderScene != null && planRenderScene.getUserId() != null){
                    userId = planRenderScene.getUserId();
                }
            }else if(planId != null && planId > 0){
                DesignPlan designPlan = designPlanService.get(planId);
                if(designPlan != null && designPlan.getUserId() != null){
                    userId = designPlan.getUserId();
                }
            }else{
                logger.error("参数resRenderPic没有效果图方案，推荐方案，样板房方案id,{}" + resRenderPic.toString());
                return infoDto;
            }
        }

        infoDto.setUserId(userId);
        if(userId != null){
            sysUser = sysUserService.get(userId);
        }
        /**
         * 查询公司信息，产品要求如果是推荐方案的话，则直接使用方案关联公司的信息(logo,企业名，联系电话)
         * 其他则读取用户的
         * 因为分享或交付的方案，用户和企业可能不对应
         *
         */
        BaseCompany company = new BaseCompany();
        if(planRecommendedId != null && planRecommendedId > 0){
            //根据推荐方案查找企业Id
            if(planRecommendedCompanyId != null && planRecommendedCompanyId > 0){
                company = baseCompanyService.get(planRecommendedCompanyId);
            }else{
                DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
                if(planRecommended != null && planRecommended.getCompanyId() != null){
                    company = baseCompanyService.get(planRecommended.getCompanyId());
                }
            }
            //查找企业信息
            if(company != null){
                if(company.getCompanyLogo() != null){
                    ResPic logo = resPicService.get(company.getCompanyLogo());
                    if(logo != null){
                        infoDto.setLogo(logo.getPicPath());//适配前端....
                    }
                }
                infoDto.setContactName(company.getCompanyName());
                infoDto.setContactPhone(company.getPhone());
            }
        }else{
            if(sysUser != null){
                infoDto.setContactName(sysUser.getUserName());
                infoDto.setContactPhone(sysUser.getMobile());
                //草图方案和效果图方案显示用户的信息
                if(sysUser.getPicId() != null){
                    ResPic logo = resPicService.get(sysUser.getPicId());
                    if(logo != null){
                        infoDto.setLogo(logo.getPicPath());//用户logo
                    }
                }
            }else{
                logger.error("没有找到该用户信息,id为{}" , userId);
            }
        }

        /**
         * 得到企业/用户店铺信息
         * 如果用户类型是设计师，则查找个人店铺
         * 不是则查找用户所属企业店铺
         * 有多个店铺，则取创建时间最早的一个
         */
        CompanyShop shop = companyShopService.getCompanyShopByUserInfo(sysUser);
        if(shop != null){
            infoDto.setCompanyShopId(shop.getId());
        }

        return infoDto;
    }

    /**
     * SDKJ-31
     * @param resRenderPic
     * @return
     */
    private RoamSceneDataVo getUserCorrelationInfo(ResRenderPic resRenderPic){
        RoamSceneDataVo dataVo = new RoamSceneDataVo();
        SceneUserInfoDto infoDto = this.getSceneUserInfo(resRenderPic,null);
        if(infoDto == null){
            return null;
        }
        dataVo.setLogo(infoDto.getLogo());
        dataVo.setContactName(infoDto.getContactName());
        dataVo.setContactPhone(infoDto.getContactPhone());
        dataVo.setCompanyShopId(infoDto.getCompanyShopId());
        dataVo.setUserId(infoDto.getUserId());
        return dataVo;
    }

    /**
     * 获取场景信息
     * @param renderPic
     * @return
     */
    public PanoramaDataVo getRoamSenceInfoByRenderPic(ResRenderPic renderPic){
        if(renderPic == null){
            logger.error("参数renderPic为空");
            return null;
        }
        //renderPic:单场景单点,效果图方案组合,同城联盟分享 传的是原图id,多点是传的截图Id

        PanoramaDataVo panoramaDataVo = new PanoramaDataVo();
        //赋值
        panoramaDataVo.setPlanId(renderPic.getBusinessId());
        panoramaDataVo.setDesignDesignSceneId(renderPic.getDesignSceneId());
        panoramaDataVo.setPlanRecommendedId(renderPic.getPlanRecommendedId());
        panoramaDataVo.setResourceId(renderPic.getId());
        panoramaDataVo.setResourcePath(renderPic.getPicPath());
       /* panoramaDataVo.setSpaceType(renderPic.getSpaceType());//空间类型
        panoramaDataVo.setSpaceFunctionId(renderPic.getSpaceFunctionId());*/
        //判断渲染图的类型，返回前端(单点为1，多点为2)
        if(Objects.equals(RenderTypeCode.ROAM_720_LEVEL ,renderPic.getRenderingType())){
            panoramaDataVo.setRenderingType(2);
        }else if(Objects.equals(RenderTypeCode.COMMON_720_LEVEL ,renderPic.getRenderingType())){
            panoramaDataVo.setRenderingType(1);
        }else{
           //目前只支持 720单点/多点场景
        }
        //查找渲染图对应空间类型
        Integer planId = renderPic.getBusinessId();//设计方案Id
        Integer designDesignSceneId = renderPic.getDesignSceneId();//效果图方案Id
        Integer planRecommendedId = renderPic.getPlanRecommendedId();//推荐方案Id
        //空间ID
        Integer spaceCommonId = null;
        if(planRecommendedId != null && planRecommendedId > 0){
            DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
            if(planRecommended != null){
                spaceCommonId = planRecommended.getSpaceCommonId();
            }
        }else if(designDesignSceneId != null && designDesignSceneId > 0){
            DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(designDesignSceneId);
            if(planRenderScene != null && planRenderScene.getUserId() != null){
                spaceCommonId = planRenderScene.getSpaceCommonId();
            }
        }else if(planId != null && planId > 0){
            DesignPlan designPlan = designPlanService.get(planId);
            if(designPlan != null && designPlan.getUserId() != null){
                spaceCommonId = designPlan.getSpaceCommonId();
            }
        }
        if(spaceCommonId != null){
            SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
            panoramaDataVo.setSpaceFunctionId(spaceCommon != null ? spaceCommon.getSpaceFunctionId() : null);
        }

        // 获取缩略图路径
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setPid(renderPic.getId());
       /* resRenderPic.setIsDeleted(0);*/
        List<ResRenderPic> list = resRenderPicService.getList(resRenderPic);
        if( list != null && list.size() > 0 ){
            panoramaDataVo.setThumbPicPath(list.get(0).getPicPath());
        }

        if(Objects.equals(RenderTypeCode.ROAM_720_LEVEL ,renderPic.getRenderingType())){
            //漫游场景信息
            List<Roam> roamList = designPlanStoreReleaseService.getWalkCoordinate(renderPic.getId());
            panoramaDataVo.setRoamList(roamList);
        }else if(Objects.equals(RenderTypeCode.COMMON_720_LEVEL ,renderPic.getRenderingType())){
            Integer isShear  = PanoramaServiceImpl.getIsShearByFilePath(renderPic.getPicPath());
            panoramaDataVo.setIsShear(isShear);
        }

        return panoramaDataVo;
    }

    private boolean checkSearchParamValidity(SceneDataSearch search){
        if(search == null || search.getRenderId() == null || search.getRenderId() <= 0){
            logger.error("请求参数不正确,{}",search);
            return  false;
        }
        return true;
    }

    /**
     * 查看单场景单点、多点场景时，更改区分方案场景显示不同功能与按钮的标识(ViewControlType)
     * 渲染图对应的方案类型(SourcePlanType),方案id(SourcePlanId),返回给前端，区分查询费用清单的参数
     * @param dataVo
     * @param renderPic
     * @param search
     * @return
     */
    private RoamSceneDataVo changeSceneDataIdentifying(RoamSceneDataVo dataVo,ResRenderPic renderPic,SceneDataSearch search) throws BizException{

        List<PanoramaDataVo> list = dataVo.getDetails();
        PanoramaDataVo panoramaDataVo = new PanoramaDataVo();
        if(CustomerListUtils.isNotEmpty(list)){
            panoramaDataVo = list.get(0);
        }
        boolean otherTypeFlag = false;
        Integer coverPicId = null;
        String planName = null;
        Integer spaceCommonId = null;
        //按产品要求区分方案场景显示不同功能与按钮的标识(ViewControlType)
        //判断渲染图对应的方案类型(SourcePlanType),方案id(SourcePlanId),返回给前端，区分查询费用清单的参数
        if (Objects.equals(search.getPlanSourceType(), PanoramaDataVo.PlanSourceType.DESIGN_PLAN)) {
            //是草图方案
            panoramaDataVo.setSourcePlanType(PanoramaDataVo.PlanSourceType.DESIGN_PLAN);
            panoramaDataVo.setSourcePlanId(panoramaDataVo.getPlanId());
            DesignPlan designPlan = designPlanService.get(panoramaDataVo.getPlanId());
            if(designPlan != null){
                if(designPlan.getCoverPicId() != null && designPlan.getCoverPicId() > 0 ){
                    coverPicId = designPlan.getCoverPicId();
                }else{
                    coverPicId = resRenderPicService.getSmallRenderPicByPlan(panoramaDataVo.getPlanId(), DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGN_PLAN);
                }
                planName = designPlan.getPlanName();
            }
        } else if (Objects.equals(search.getPlanSourceType(), PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN)) {
            //是效果图方案
            panoramaDataVo.setSourcePlanType(PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN);
            panoramaDataVo.setSourcePlanId(panoramaDataVo.getDesignDesignSceneId());
            DesignPlanRenderScene planRenderScene =  designPlanRenderSceneService.get(panoramaDataVo.getDesignDesignSceneId());
            if(planRenderScene != null){
                planName = planRenderScene.getPlanName();
                if(planRenderScene.getCoverPicId() != null && planRenderScene.getCoverPicId() > 0 ){
                    coverPicId = planRenderScene.getCoverPicId();
                }else{
                    coverPicId = resRenderPicService.getSmallRenderPicByPlan(panoramaDataVo.getDesignDesignSceneId(), DesignPlanStoreReleaseDetailsVo.PlanSourceType.DESIGNSCENE_PLAN);
                }
            }
        } else if (search.getPlanSourceType() == null || Objects.equals(search.getPlanSourceType(), PanoramaDataVo.PlanSourceType.RECOMMENDED_PLAN)){
            //目前与前端约定的只有1 和 2,3为推荐方案
            otherTypeFlag = true;
            DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(panoramaDataVo.getPlanRecommendedId());
            if(planRecommended != null){
                planName = planRecommended.getPlanName();
                if(planRecommended.getCoverPicId() != null && planRecommended.getCoverPicId() > 0 ){
                    coverPicId = planRecommended.getCoverPicId();
                }else{
                    coverPicId = resRenderPicService.getSmallRenderPicByPlan(panoramaDataVo.getPlanRecommendedId(), DesignPlanStoreReleaseDetailsVo.PlanSourceType.RECOMMENDED_PLAN);
                }
                dataVo.setGroupPrimaryId(planRecommended.getGroupPrimaryId());
                if(Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, planRecommended.getRecommendedType()) && Objects.equals(1, planRecommended.getChargeType())){
                    dataVo.setPlanPrice(planRecommended.getPlanPrice());
                }
                spaceCommonId = planRecommended.getSpaceCommonId();
            }
        }else{
            throw new BizException("planSourceType参数无效");
        }
        //封面图
        if(coverPicId != null){
            ResRenderPic pic = resRenderPicService.get(coverPicId);
            if(pic != null){
                dataVo.setCoverPicPath(pic.getPicPath());
            }
        }
        //空间信息
        if(spaceCommonId != null && spaceCommonId > 0){
            Integer functionId = spaceCommonService.getSpaceFunctionIdBySpaceCommonId(spaceCommonId);
            dataVo.setSpaceType(functionId);
            //空间类型
            SysDictionary houseTypeDictionary = sysDictionaryService.findOneByTypeAndValue("housetype",functionId);
            dataVo.setHouseTypeName(houseTypeDictionary != null ? houseTypeDictionary.getName() : null);
        }
        //方案名称
        dataVo.setPlanName(planName);
        if(otherTypeFlag){
            //如果传的是其他类型，都按推荐方案算
            panoramaDataVo.setSourcePlanType(PanoramaDataVo.PlanSourceType.RECOMMENDED_PLAN);
            panoramaDataVo.setSourcePlanId(panoramaDataVo.getPlanRecommendedId());

            Integer recommendedPlanId = panoramaDataVo.getPlanRecommendedId();
            if(recommendedPlanId == null) {
                return dataVo;
            }
            DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(recommendedPlanId);
            if(planRecommended == null || planRecommended.getIsRelease() == null) {
                return dataVo;
            }
        }
        try {
            SceneControlInfoDto infoDto = this.getSceneControlInfo(search.getPlatformType(),search.getSceneType(),search.getPlanSourceType()
                    ,panoramaDataVo.getSourcePlanId(),null,search.getQrCodeType());
            if(infoDto != null){
                dataVo.setViewControlType(infoDto.getViewControlType());
                dataVo.setCopyRightPermission(infoDto.getCopyRightPermission());
                dataVo.setHavePurchased(infoDto.getHavePurchased());
            }
        } catch (BizException e) {
            e.printStackTrace();
        }
        return dataVo;
    }

    @Override
    public RoamSceneDataVo vr720SingleSenceData(SceneDataSearch search) throws BizException {
        RoamSceneDataVo dataVo = null;
        if(!checkSearchParamValidity(search)){
            throw new BizException("参数错误");
        }
        //得到渲染图信息
        ResRenderPic renderPic = resRenderPicService.get(search.getRenderId());//原图
        if(renderPic == null){
            logger.error("找不到该渲染图信息,id为{}" , search.getRenderId());
            throw new BizException("找不到该渲染图信息");
        }
      /*  if(renderPic.getIsDeleted() == null || renderPic.getIsDeleted() != 0){
            logger.error("该渲染图已删除,id为{}" , search.getRenderId());
            return null;
        }*/

        //查询用户与企业信息
        dataVo = this.getUserCorrelationInfo(renderPic);
        //查询场景信息
        List<PanoramaDataVo> panoramaDataVos = new ArrayList<PanoramaDataVo>();
        PanoramaDataVo panoramaDataVo = this.getRoamSenceInfoByRenderPic(renderPic);
        panoramaDataVos.add(panoramaDataVo);
        dataVo.setDetails(panoramaDataVos);
        //自定义描述文案
        String desc = this.getCustomizedDescBySceneInfo(search,dataVo.getUserId());
        dataVo.setDesc(desc);
        dataVo.setShareTitle("3D全景");
        //按产品要求区分方案场景显示不同功能与按钮的标识(ViewControlType)
        //判断渲染图对应的方案类型(SourcePlanType),方案id(SourcePlanId),返回给前端，区分查询费用清单的参数
        dataVo = this.changeSceneDataIdentifying(dataVo,renderPic,search);
        return dataVo;
    }

    @Override
    public RoamSceneDataVo vr720RoamSenceData(SceneDataSearch search) throws BizException {
        if(!checkSearchParamValidity(search)){
            throw new BizException("参数错误");
        }
        RoamSceneDataVo dataVo = new RoamSceneDataVo();
        //根据传过来的截图Id查询是否有720漫游组信息
        DesignRenderRoam designRenderRoam = designRenderRoamService.selectByScreenId(search.getRenderId());
        if(designRenderRoam == null){
            logger.error("designRenderRoam is null,渲染图id为{}" , search.getRenderId());
            throw new BizException("未找到渲染资源信息");
        }
        //渲染图
        ResRenderPic mainRenderPic = resRenderPicService.get(designRenderRoam.getScreenShotId());
        if(mainRenderPic == null){
            logger.error("找不到该渲染截图信息,id为{}" , search.getRenderId());
            throw new BizException("找不到该渲染图信息" );
        }
     /*   if(mainRenderPic.getIsDeleted() == null || mainRenderPic.getIsDeleted() != 0){
            logger.error("该渲染截图已删除,id为{}" , search.getRenderId());
            return null;
        }*/
        //用户信息
        dataVo = this.getUserCorrelationInfo(mainRenderPic);
        //场景信息
        List<PanoramaDataVo> panoramaVoList = new ArrayList<PanoramaDataVo>();
        PanoramaDataVo  panoramaDataVo = this.getRoamSenceInfoByRenderPic(mainRenderPic);
        panoramaVoList.add(panoramaDataVo);
        dataVo.setDetails(panoramaVoList);
        //自定义描述
        String desc = this.getCustomizedDescBySceneInfo(search,dataVo.getUserId());
        dataVo.setDesc(desc);
        dataVo.setShareTitle("3D全景");
        //按产品要求区分方案场景显示不同功能与按钮的标识(ViewControlType)
        //判断渲染图对应的方案类型(SourcePlanType),方案id(SourcePlanId),返回给前端，区分查询费用清单的参数
        dataVo = this.changeSceneDataIdentifying(dataVo,mainRenderPic,search);
        return dataVo;
    }

    @Override
    public RoamSceneDataVo vr720GroupSenceData(SceneDataSearch search) throws BizException {
        if(!checkSearchParamValidity(search)){
            throw new BizException("参数错误");
        }
        RoamSceneDataVo dataVo = new RoamSceneDataVo();
        // 获取组合方案信息,参数是组合Id
        SysUserGroup sysUserGroup = sysUserGroupService.getPlanGroupByPrimaryKey(search.getRenderId());
        if(sysUserGroup == null || Utils.isEmpty(sysUserGroup.getBid())){
            logger.error("SysUserGroup is null,渲染图id为{}" , search.getRenderId());
            throw new BizException("找不到该分享信息");
        }
       /* boolean success  = sysUserGroupService.addShareTimes(sysUserGroup);//分享次数+1*/

        Group group = sysUserGroupService.doShare(sysUserGroup.getBid());
        if( group == null ){
            logger.error("group is null,渲染图id为{},组合bid为{}", search.getRenderId(),sysUserGroup.getBid());
            throw new BizException("找不到渲染资源信息");
        }
        List<GroupDetail> groupDetailList = group.getList();
        ResRenderPic mainRenderPic = null;
        List<PanoramaDataVo> panoramaVoList = new ArrayList<PanoramaDataVo>();
        Integer coverPicId = null;
        for (GroupDetail groupDetail : groupDetailList){
            Integer renderPicId = groupDetail.getPicId();//渲染原图Id
            ResRenderPic renderPic = resRenderPicService.get(renderPicId);
            //场景信息
            PanoramaDataVo panoramaDataVo = this.getRoamSenceInfoByRenderPic(renderPic);
            if(mainRenderPic == null){
                mainRenderPic = renderPic;
                panoramaDataVo.setIsMain(1);
                //得到主方案的封面图
                DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(renderPic.getPreRenderSceneId());
                if(planRenderScene !=  null){
                    coverPicId = planRenderScene.getCoverPicId();
                }
            }
            //判断渲染图对应的方案类型(SourcePlanType),方案id(SourcePlanId),返回给前端，区分查询费用清单的参数
            panoramaDataVo.setSourcePlanType(PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN);
            panoramaDataVo.setSourcePlanId(panoramaDataVo.getDesignDesignSceneId());
            panoramaVoList.add(panoramaDataVo);
        }
        //用户信息
        dataVo = this.getUserCorrelationInfo(mainRenderPic);
        dataVo.setDetails(panoramaVoList);
       /* String title = this.getCustomizedDescBySceneInfo(search,dataVo.getUserId());*/
        dataVo.setPv((sysUserGroup.getShareTimes())+1);
        //自定义描述文案
        String desc = this.getCustomizedDescBySceneInfo(search,dataVo.getUserId());
        dataVo.setDesc(desc);
        dataVo.setShareTitle("3D全景");
        //方案名称
        dataVo.setPlanName(sysUserGroup.getName());
        try {
            SceneControlInfoDto infoDto = this.getSceneControlInfo(search.getPlatformType(),search.getSceneType(),search.getPlanSourceType()
                    ,null, null,search.getQrCodeType());
            if(infoDto != null){
                dataVo.setViewControlType(infoDto.getViewControlType());
                dataVo.setCopyRightPermission(infoDto.getCopyRightPermission());
                dataVo.setHavePurchased(infoDto.getHavePurchased());
            }
        } catch (BizException e) {
            e.printStackTrace();
        }
        if(coverPicId != null){
            ResRenderPic pic = resRenderPicService.get(coverPicId);
            dataVo.setCoverPicPath(pic == null ? null : pic.getPicPath());
        }
        return dataVo;
    }

    @Override
    public UnionStoreSingleDataVo vr720UnionStoreSingleData(SceneDataSearch search) throws BizException {
        if(!checkSearchParamValidity(search)){
            throw new BizException("参数错误");
        }
        UnionStoreSingleDataVo dataVo = new UnionStoreSingleDataVo();
        UnionDesignPlanStoreRelease planStoreRelease = unionDesignPlanStoreReleaseService.get(search.getRenderId());

        if(planStoreRelease == null){
            throw new BizException("找不到该分享信息");
        }
        //TODO:再整理一遍，代码太乱了,不使用UnionStoreVo了，把代码抽出来
        dataVo.setPlanName(planStoreRelease.getReleaseName());
        dataVo.setUnionGroupId(planStoreRelease.getUnionGroupId());
        dataVo.setUnionSpecialOfferId(planStoreRelease.getSpecialOfferId());
        //同店联盟方案相关信息
        UnionStoreVo unionStoreVo = this.getUnionStoreDataByReleaseId(search.getRenderId());
        if(unionStoreVo != null){
            dataVo.setContactName(unionStoreVo.getName());//产品要求显示门店信息的地址
          /*  dataVo.setName(unionStoreVo.getName());
            dataVo.setAddress(unionStoreVo.getAddress());
            dataVo.setIsDisplayed(unionStoreVo.getIsDisplayed());
            dataVo.setCompanyName(unionStoreVo.getCompanyName());*/
            dataVo.setContactPhone(unionStoreVo.getPhone());
            dataVo.setLogo(unionStoreVo.getLogo());
            dataVo.setPv(unionStoreVo.getPopularityValue());
            dataVo.setReleaseName(unionStoreVo.getReleaseName());
            dataVo.setUserId(unionStoreVo.getUserId());
            //自定义描述文案
            String desc = this.getCustomizedDescBySceneInfo(search,unionStoreVo.getUserId());
            dataVo.setDesc(desc);
            dataVo.setShareTitle("3D全景");
            //用户信息
            Integer userId = unionStoreVo.getUserId();
            if(unionStoreVo.getStorefrontId() == null) {
                if(userId != null && userId > 0) {
                    SysUser sysUser = sysUserService.get(userId);

                    /**
                     * 得到企业/用户店铺信息
                     * 如果用户类型是设计师，则查找个人店铺
                     * 不是则查找用户所属企业店铺
                     * 有多个店铺，则取创建时间最早的一个
                     * 如果有选择联系人则不显示店铺
                     */
                    CompanyShop shop = companyShopService.getCompanyShopByUserInfo(sysUser);
                    if (shop != null) {
                        dataVo.setCompanyShopId(shop.getId());
                    }
                }
            }
        }else{
            logger.error("未查询到同城联盟720分享相关信息,参数 renderId是{}",search.getRenderId());
        }
        List<ResRenderPic> renderPicLs = resRenderPicService.getListByUnionDesignPlanStoreReleaseId(search.getRenderId());
        if(renderPicLs == null || renderPicLs.size() <= 0) {
            return null;
        }
        //封面图id
        Integer coverPicId = null;
        //场景信息
        List<PanoramaDataVo> panoramaVoList = new ArrayList<PanoramaDataVo>();
        if(CustomerListUtils.isNotEmpty(renderPicLs)){
            for (ResRenderPic renderPic : renderPicLs){
                PanoramaDataVo  panoramaDataVo = this.getRoamSenceInfoByRenderPic(renderPic);
                panoramaDataVo.setSourcePlanType(PanoramaDataVo.PlanSourceType.DESIGN_PLAN);
                panoramaDataVo.setSourcePlanId(panoramaDataVo.getPlanId());
                panoramaVoList.add(panoramaDataVo);
                if(coverPicId == null){
                   if(panoramaDataVo.getPlanId() != null){
                       DesignPlan designPlan = designPlanService.get(panoramaDataVo.getPlanId());
                       coverPicId = designPlan != null ? designPlan.getCoverPicId() : null;
                   }
                }
            }
        }
        dataVo.setDetails(panoramaVoList);
        try {
            SceneControlInfoDto infoDto = this.getSceneControlInfo(search.getPlatformType(),search.getSceneType(),search.getPlanSourceType()
                    ,null, null,search.getQrCodeType());
            if(infoDto != null){
                dataVo.setViewControlType(infoDto.getViewControlType());
              /*  dataVo.setCopyRightPermission(infoDto.getCopyRightPermission());
                dataVo.setHavePurchased(infoDto.getHavePurchased());*/
            }
        } catch (BizException e) {
            e.printStackTrace();
            throw new BizException("出现异常");
        }
        //封面图
        if(coverPicId != null){
            ResRenderPic pic = resRenderPicService.get(coverPicId);
            dataVo.setCoverPicPath(pic == null ? null : pic.getPicPath());
        }
        return dataVo;
    }

    /**
     * 查询分享的标题文案信息
     * 如果SceneQrCodeInfo 表中没有记录，则生成默认文案
     * @param search
     * @param userId
     * @return
     */
    @Override
    public String getCustomizedDescBySceneInfo(SceneDataSearch search,Integer userId){
        boolean flag = false;//是否返回自定义标题
        if(search.getRenderId() == null || search.getSceneType() == null){
            flag = true;
        }
        //如果分享的文案未被修改，则生成默认文案
        if(search.getHasChanged() == null || search.getHasChanged() == 0){
            flag = true;
        }
        if(!flag){
            //读取用户自定义的文案信息
          /*  SceneQrCodeInfo qrCodeInfo = new SceneQrCodeInfo();
            qrCodeInfo.setRenderId(search.getRenderId().toString());
            qrCodeInfo.setSceneType(search.getSceneType());
            qrCodeInfo.setUserId(userId);
            SceneQrCodeInfo info = sceneQrCodeInfoService.getSceneQrCodeInfo(qrCodeInfo);
            if(info != null && !Utils.isEmpty(info.getCopywritingInformation())){
                return info.getCopywritingInformation();
            }else{

            }*/
          //如果已有自定义的文案，则返回空，在其他地方查询
          return null;
        }
        //根据用户关联企业生成默认文案，并返回
        String defaultDesc = null;
        if(search.getPlanSourceType() != null && search.getPlanSourceType() != 3){
            defaultDesc = WX_SMALL_INFORMATION_D;
        }else{
            //推荐方案的文案不同
            defaultDesc = WX_SMALL_INFORMATION_R;
        }
        try {
            if(userId != null){
               String companyName = sysUserService.getCompanyNameByUserId(userId);          //获取用户企业名称
                if(StringUtils.isNotBlank(companyName)){
                    defaultDesc = defaultDesc.replace("企业名称",companyName);          //替换真正的企业名称
                }else{
                    //企业为空则使用用户名
                    SysUser user = sysUserService.get(userId);
                    if(user != null && StringUtils.isNotBlank(user.getUserName())){
                        defaultDesc = defaultDesc.replace("企业名称",user.getUserName());
                    }else{
                        defaultDesc = defaultDesc.replace("[企业名称]","");
                    }
                }
            }else{
                defaultDesc = defaultDesc.replace("[企业名称]","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //自定义标题
        return defaultDesc;
    }


    /**
     * 组装720页面基本信息
     * 需求要求界面显示有变更
     * XZW-316
     * add by chem on 218/11/17
     * @param scene
     * @return
     */
    @Override
    public PanoramaVo assemblyPanoramaVoForNewScene(Scene scene) {
        Integer planId = scene.getPlanId();
        Integer designPlanRenderSceneId = scene.getDesignPlanRenderSceneId();
        Integer designPlanRecommendedId = scene.getDesignPlanRecommendedId();
        //TODO:部分旧数据 草图的渲染图也有 副本id,如何避免这个影响
        PanoramaVo panoramaVo = new PanoramaVo();

        if(designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
            panoramaVo = this.assemblyPanoramaVoRecommendedIdForNewScene(designPlanRecommendedId);
        }else if(designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            panoramaVo = this.assemblyPanoramaVoSceneIdForNewScene(designPlanRenderSceneId);
        }else if(planId != null && planId > 0) {
            panoramaVo = this.assemblyPanoramaVoPlanIdForNewScene(planId);
        }

        return panoramaVo;
    }

    /**
     * 拼装推荐方案 720 界面显示信息
     * @param designPlanRecommendedId
     * @return
     */
    private PanoramaVo assemblyPanoramaVoRecommendedIdForNewScene(Integer designPlanRecommendedId) {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setDesignPlanRecommendedId(designPlanRecommendedId);
        // 参数验证 ->start
        if(designPlanRecommendedId == null || designPlanRecommendedId < 1) {
            return panoramaVo;
        }
        // 参数验证 ->end

        DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanRecommendedId);
        if(designPlanRecommended != null && designPlanRecommended.getUserId() != null && designPlanRecommended.getUserId() > 0) {
            panoramaVo.setTitle("3D全景漫游");
            // 获取用户昵称
            Integer userId = designPlanRecommended.getUserId();
            SysUser sysUser = sysUserService.get(userId);
            if( sysUser != null ){
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());
                //得到企业/店铺信息
                panoramaVo = this.assemblySceneWithUserShopInfo(panoramaVo);
            }

            // 获取房型类型
            if(designPlanRecommended.getSpaceCommonId() != null){
                SpaceCommon spaceCommon = spaceCommonService.get(designPlanRecommended.getSpaceCommonId());
                if(spaceCommon != null && spaceCommon.getSpaceFunctionId() != null){
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }
        return panoramaVo;
    }


    /**
     * 拼装效果图方案 720场景显示信息
     * @param designPlanRenderSceneId
     * @return
     */
    private PanoramaVo assemblyPanoramaVoSceneIdForNewScene(Integer designPlanRenderSceneId) {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setDesignPlanRenderSceneId(designPlanRenderSceneId);
        // 参数验证 ->start
        if(designPlanRenderSceneId == null || designPlanRenderSceneId < 1) {
            return panoramaVo;
        }
        // 参数验证 ->end

        DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanRenderSceneId);
        if(designPlanRenderScene != null && designPlanRenderScene.getUserId() != null && designPlanRenderScene.getUserId() > 0) {
            // 设计方案风格，如果没有，则默认为混搭
            Integer styleId = designPlanRenderScene.getDesignStyleId();
            panoramaVo.setTitle("3D全景漫游");
            // 获取用户昵称
            Integer userId = designPlanRenderScene.getUserId();
            SysUser sysUser = sysUserService.get(userId);
            if( sysUser != null ){
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());
                //得到企业/店铺信息
                panoramaVo = this.assemblySceneWithUserShopInfo(panoramaVo);
            }

            // 获取房型类型
            if(designPlanRenderScene.getSpaceCommonId() != null){
                SpaceCommon spaceCommon = spaceCommonService.get(designPlanRenderScene.getSpaceCommonId());
                if(spaceCommon != null && spaceCommon.getSpaceFunctionId() != null){
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }
        return panoramaVo;
    }

    /**
     * 拼接草图方案 720场景中页面显示信息
     * add by chenm on 2018/11/17
     * @param planId
     * @return
     */
    private PanoramaVo assemblyPanoramaVoPlanIdForNewScene(Integer planId) {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setPlanId(planId);
        // 参数验证 ->start
        if(planId == null || planId < 1) {
            return panoramaVo;
        }
        // 参数验证 ->end

        DesignPlan designPlan = designPlanService.get(planId);
        if( designPlan != null && designPlan.getUserId() != null && designPlan.getUserId() > 0 ){
            panoramaVo.setPlanId(planId);
            panoramaVo.setTitle("3D全景漫游");
            // 获取用户昵称
            Integer userId = designPlan.getUserId();
            SysUser sysUser = sysUserService.get(userId);
            if( sysUser != null ) {
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());
                //得到企业/店铺信息
                panoramaVo = this.assemblySceneWithUserShopInfo(panoramaVo);
            }
            // 获取房型类型
            if( designPlan.getSpaceCommonId() != null ){
                SpaceCommon spaceCommon = spaceCommonService.get(designPlan.getSpaceCommonId());
                if( spaceCommon != null && spaceCommon.getSpaceFunctionId() != null ){
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }
        return panoramaVo;
    }

    /**
     * 根据用户得到界面显示相关信息 (店铺/企业 名称,logo,联系电话)
     * add by chenm on 2018/11/19
     * @param panoramaVo
     * @return
     */
    private PanoramaVo assemblySceneWithUserShopInfo(PanoramaVo panoramaVo){
        Integer userId = panoramaVo.getUserId();
        SysUser user = sysUserService.get(userId);
        if(user == null){
            return  panoramaVo;
        }
        /**
         * 厂商账号有优先取品牌馆信息，其次取厂商企业信息，取不到则显示账号信息；
         * 非厂商账号，取用户创建的发布到随选网的店铺，如果没有则显示账号信息。
         */
        CompanyShop companyShop = companyShopService.getUserCompanyShopForNewScene(userId);
        ResPic logoPic = null;
        boolean showUserInfo = false;
        if(companyShop != null){
            //显示店铺信息
            panoramaVo.setCompanyName(companyShop.getShopName());
            panoramaVo.setPhone(companyShop.getContactPhone());
            if(companyShop.getLogoPicId() != null){
                logoPic  = resPicService.get(companyShop.getLogoPicId());
            }
        }else{
            if(Objects.equals(UserTypeConstant.USER_TYPE_COMPANY,user.getUserType())){
                //厂商账号查找显示信息
                BaseCompany company = null;
                if(user.getCompanyId() != null){
                    company = baseCompanyService.get(user.getCompanyId());
                }
                if(company != null){
                    //没有店铺信息，则取厂商企业信息
                    panoramaVo.setCompanyName(company.getCompanyName());
                    panoramaVo.setPhone(company.getPhone());
                    if(company.getCompanyLogo() != null){
                        logoPic  = resPicService.get(company.getCompanyLogo());
                    }
                }else{
                    //没有企业信息则显示账号信息
                    showUserInfo = true;
                }
            }else{
                //非厂商账号
                showUserInfo = true;
            }
            if(showUserInfo){
                //显示用户信息
                //为适配界面所以给companyName 赋值,实际显示用户名称
                panoramaVo.setCompanyName(StringUtils.isNotBlank(user.getUserName())? user.getUserName():user.getNickName());
                panoramaVo.setPhone(user.getMobile());
                if(user.getPicId() != null){
                    logoPic  = resPicService.get(user.getPicId());
                }
            }
        }
        //显示logo
        if(logoPic != null ){
            panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(logoPic.getPicPath()));
        }
        return panoramaVo;
    }

    @Override
    public PanoramaVo assemblyGroupPanoramaVoForNewScene(Integer planId) {
        PanoramaVo panoramaVo = new PanoramaVo();
        DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(planId);
        if( designPlanRenderScene != null && designPlanRenderScene.getUserId() != null && designPlanRenderScene.getUserId() > 0 ){
            panoramaVo.setPlanId(planId);
            // 设计方案风格，如果没有，则默认为混搭
            Integer designStyleId = designPlanRenderScene.getDesignStyleId();
            panoramaVo.setTitle("3D全景漫游");
            // 获取用户昵称
            Integer userId = designPlanRenderScene.getUserId();
            SysUser sysUser = sysUserService.get(userId);
            if( sysUser != null ){
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());
                panoramaVo = this.assemblySceneWithUserShopInfo(panoramaVo);
            }

            // 获取房型类型
            if( designPlanRenderScene.getSpaceCommonId() != null ){
                SpaceCommon spaceCommon = spaceCommonService.get(designPlanRenderScene.getSpaceCommonId());
                if( spaceCommon != null && spaceCommon.getSpaceFunctionId() != null ){
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType",spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }
            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }

        return panoramaVo;
    }

    /**
     * 控制场景中功能按钮显示相关值,需求文档见项目中文件 v720-webapp\src\main\webapp\pages\file\【720方案整理】.xlsx
     * 需求：“随选网方案分享后，720中可操作的图标保持不变” 地址: http://192.168.1.201:8080/browse/XZW-328
     * 知识库地址:http://192.168.1.239:8090/pages/viewpage.action?pageId=23560254
     * {@link com.nork.pano.model.constant.ViewControlTypeConstant.java}
     * @param platformType 平台类型
     * @param sceneType 场景类型
     * @param planSourceType 方案类型
     * @param planId 方案id
     * @param userId 查看此场景的用户id
     * @param qrCodeType 二维码类型
     * @return
     * @throws BizException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SceneControlInfoDto getSceneControlInfo(String platformType, Integer sceneType, Integer planSourceType,Integer planId,Integer userId,Integer qrCodeType) throws BizException {
        SceneControlInfoDto infoDto = new SceneControlInfoDto();

        if(Objects.equals(SceneDataSearch.PlatformType.PC2B,platformType)){
            //通用版
            if(sceneType == null){
                throw new BizException("缺少参数sceneType!");
            }
            switch (sceneType){
                case SceneTypeConstant.SINGER_SCENE:
                    //720单点
                case SceneTypeConstant.ROAM_SCENE:
                    //720多点
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
                        infoDto.setCopyRightPermission(0);
                    break;
                case SceneTypeConstant.GROUP_SCENE:
                    //720组合分享
                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
                    infoDto.setCopyRightPermission(0);
                    break;
                case SceneTypeConstant.UNION_STORE_SINGLE_SCENE:
                    //同城联盟
                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FIVE);
                    infoDto.setCopyRightPermission(0);
                    break;
                case SceneTypeConstant.STORE_RELEASE_SCENE:
                    //全屋方案
                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FIVE);
                    infoDto.setCopyRightPermission(0);
                    break;
                default:
                    throw new BizException("参数sceneType错误!");
            }

        }else if(Objects.equals(SceneDataSearch.PlatformType.SELECTDECORATION,platformType)){
            //随选网
            if(sceneType != null ){
               /*
                * 不能这么判断，因为可能有之前别人保存下的分享到随选网的二维码.....
                * && Objects.equals(SceneDataSearch.QRCodeType.QRCODE_TYPE_SELECTDECORATION,qrCodeType)
                */
                //从PC端分享的方案
                switch (sceneType){
                    case SceneTypeConstant.SINGER_SCENE:
                        //720单点
                    case SceneTypeConstant.ROAM_SCENE:
                        //720多点
                        if(Objects.equals(PanoramaDataVo.PlanSourceType.DESIGN_PLAN,planSourceType)){
                            infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
                        }else if(Objects.equals(PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN,planSourceType)){
                            infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                        }else{
                            if(planId == null){
                                throw new BizException("参数为空:" + planId);
                            }
                            DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(planId);
                            if(planRecommended == null){
                                throw new BizException("找不到这个推荐方案,方案id为" + planId);
                            }
                            if(Objects.equals(RecommendedDecorateState.IS_RELEASEING,planRecommended.getIsRelease())){
                                if(Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB,planRecommended.getRecommendedType())){
                                    //一键方案
                                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_ONE);
                                }else if(Objects.equals(Constants.RECOMMENDED_TYPE_SHARE,planRecommended.getRecommendedType())){
                                    //样板方案
                                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                                }
                            }else{
                                infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
                            }
                        }
                        infoDto.setCopyRightPermission(0);
                        break;
                    case SceneTypeConstant.GROUP_SCENE:
                        //720组合分享
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                        infoDto.setCopyRightPermission(0);
                        break;
                    case SceneTypeConstant.UNION_STORE_SINGLE_SCENE:
                        //同城联盟
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FIVE);
                        infoDto.setCopyRightPermission(0);
                        break;
                    case SceneTypeConstant.STORE_RELEASE_SCENE:
                        //全屋方案
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_THREE);
                        infoDto.setCopyRightPermission(0);
                        break;
                    default:
                        throw new BizException("参数sceneType错误!");
                }
            }else{
                //从随选网过来的场景
                //TODO:从移动端/企业小程序分享到随选网的场景没有判断

                if(Objects.equals(PanoramaDataVo.PlanSourceType.FULL_HOUSE_DESIGN_PLAN,planSourceType)){
                    //全屋方案
                    if(planId == null){
                        throw new BizException("缺少planId!");
                    }
                    FullHouseDesignPlan fullHouseDesignPlan = fullHousePlanService.get(planId);
                    if(fullHouseDesignPlan == null){
                        throw new BizException("找不到该全屋方案!方案id为:" + planId);
                    }
                    /**
                     * 以下类型来源全屋方案 (1:内部制作,3:交付,4:分享) 不显示"2D/3D"图标
                     * 因为这些全屋方案里的单空间方案并不是来源于同一个户型
                     */
                    List<Integer> uselessSourceTypeList = Arrays.asList(new Integer[]{1,3,4});
                    if(fullHouseDesignPlan.getSourceType() != null && uselessSourceTypeList.contains(fullHouseDesignPlan.getSourceType())){
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                        infoDto.setCopyRightPermission(0);
                    }else{
                        if(Objects.equals(1,fullHouseDesignPlan.getChargeType())){
                            infoDto.setCopyRightPermission(1);
                            if(userId != null){
                                /**
                                 * 用户是否购买了该付费方案
                                 * 2b端和企业小程序本公司不需要收费
                                 */
                                CompanyDesignPlanIncome incomesearch = new CompanyDesignPlanIncome();
                                incomesearch.setPlanId(fullHouseDesignPlan.getId());
                                incomesearch.setBuyerId(userId.longValue());
                                incomesearch.setPlanType(0);
                                incomesearch.setIsDeleted(0);
                                int count = companyDesignPlanIncomeService.getCount(incomesearch);
                                if(count <= 0){
                                    infoDto.setHavePurchased(0);
                                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_EIGHT);
                                }else{
                                    infoDto.setHavePurchased(1);
                                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_ONE);
                                }
                            }else{
                                //没有用户信息时
                                infoDto.setHavePurchased(0);
                                infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_EIGHT);
                            }

                        }else{
                            infoDto.setCopyRightPermission(0);
                            infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_ONE);
                        }
                    }

                }else if(Objects.equals(PanoramaDataVo.PlanSourceType.RECOMMENDED_PLAN,planSourceType)){
                    //推荐方案
                    DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(planId);
                    if(designPlanRecommended == null){
                        throw new BizException("找不到该推荐方案!方案id为:" + planId);
                    }
                    if (Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, designPlanRecommended.getRecommendedType())) {
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_ONE);
                        //一键装修方案校验是否需收费和用户是否付费
                        if (Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, designPlanRecommended.getRecommendedType())) {
                            if (Objects.equals(1, designPlanRecommended.getChargeType())) {
                                infoDto.setCopyRightPermission(1);
                                if (userId != null) {
                                    CompanyDesignPlanIncome incomesearch = new CompanyDesignPlanIncome();
                                    incomesearch.setPlanId(designPlanRecommended.getId());
                                    incomesearch.setBuyerId(userId.longValue());
                                    incomesearch.setPlanType(1);
                                    incomesearch.setIsDeleted(0);
                                    int count = companyDesignPlanIncomeService.getCount(incomesearch);
                                    if (count <= 0) {
                                        infoDto.setHavePurchased(0);
                                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_EIGHT);
                                    } else {
                                        infoDto.setHavePurchased(1);
                                    }
                                }else{
                                    //没有用户信息
                                    infoDto.setHavePurchased(0);
                                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_EIGHT);
                                }
                            } else {
                                infoDto.setCopyRightPermission(0);
                            }
                        }
                    }else{
                        //样板方案为6
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                        infoDto.setCopyRightPermission(0);
                    }
                }else if(Objects.equals(PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN,planSourceType)){
                    //效果图方案
                    infoDto.setCopyRightPermission(0);
                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                }else{
                }
            }
        }else if(Objects.equals(SceneDataSearch.PlatformType.COMPANY_MINIPROGRAM,platformType)){
            //小程序
            if(sceneType != null && Objects.equals(SceneDataSearch.QRCodeType.QRCODE_TYPE_MINIPROGRAM,qrCodeType)){
                //从通用版分享到小程序的场景
                switch (sceneType){
                    case SceneTypeConstant.SINGER_SCENE:
                        //720单点
                    case SceneTypeConstant.ROAM_SCENE:
                        //720多点
                    case SceneTypeConstant.GROUP_SCENE:
                        //720组合分享
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
                        infoDto.setCopyRightPermission(0);
                        break;
                    case SceneTypeConstant.UNION_STORE_SINGLE_SCENE:
                        //同城联盟
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FIVE);
                        infoDto.setCopyRightPermission(0);
                        break;
                    case SceneTypeConstant.STORE_RELEASE_SCENE:
                        //全屋方案
                        infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FIVE);
                        infoDto.setCopyRightPermission(0);
                        break;
                    default:
                        throw new BizException("参数sceneType错误!");
                }

            }else{
                //从小程序打开的场景
                if(Objects.equals(PanoramaDataVo.PlanSourceType.FULL_HOUSE_DESIGN_PLAN,planSourceType)){
                    //全屋方案
                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_ONE);
                    infoDto.setCopyRightPermission(0);
                }else{
                    infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                    infoDto.setCopyRightPermission(0);
                }
            }
        }else if(Objects.equals(SceneDataSearch.PlatformType.COMPANY_MOBILE2B,platformType)){
            //移动2b端
            if(Objects.equals(PanoramaDataVo.PlanSourceType.FULL_HOUSE_DESIGN_PLAN,planSourceType)){
                //全屋方案
                infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_ONE);
                infoDto.setCopyRightPermission(0);
            }else if(Objects.equals(PanoramaDataVo.PlanSourceType.RECOMMENDED_PLAN,planSourceType)){
                DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(planId);
                if(planRecommended == null){
                    throw new BizException("找不到该推荐方案!方案id为:" + planId);
                }
                infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                infoDto.setCopyRightPermission(0);
                if (Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, planRecommended.getRecommendedType())) {
                    if(userId != null){
                        SysUser sysUser = sysUserService.get(userId);
                        if(sysUser != null && Objects.equals(UserTypeConstant.USER_MEDIATION,sysUser.getUserType())){
                            //中介用户
                            infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TEN);
                        }
                    }
                }
            }else if(Objects.equals(PanoramaDataVo.PlanSourceType.DESIGNSCENE_PLAN,planSourceType)){
                infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_TWO);
                infoDto.setCopyRightPermission(0);
            }
        }else if(Objects.equals(SceneDataSearch.PlatformType.OTHERS,platformType)){
            infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
            infoDto.setCopyRightPermission(0);
        }else{
            logger.warn("请求没有得到有效平台标识，platformType：{}" , platformType);
            infoDto.setViewControlType(ViewControlTypeConstant.VIEW_CONTROL_TYPE_FOUR);
            infoDto.setCopyRightPermission(0);
        }

        return infoDto;
    }


    /**
     * 根据资源地址判断图片切片方式
     * @param filePath 图片地址
     * @return
     */
    public static Integer getIsShearByFilePath(String filePath){
        Integer isShear = DesignPlanStoreReleaseDetailsVo.IsShear.NO;
        File file = new File(Utils.getAbsolutePath(filePath, null));
        // 如果图片资源为目录，则表示为切片资源
        if( file.exists() && file.isDirectory() ) {
            File[] files = file.listFiles();
            boolean haveDirectory = false;
            if(files != null && files.length > 0){
                for (File file1:files){
                    if(file1.isDirectory()){
                        haveDirectory = true;
                        break;
                    }
                }
                //如果图片资源目录里还是目录，则使用的54张切片方式
                if(haveDirectory){
                    isShear = DesignPlanStoreReleaseDetailsVo.IsShear.IS_SHEAR_54;
                }else{
                    isShear = DesignPlanStoreReleaseDetailsVo.IsShear.IS_SHEAR_6;
                }
            }
        }
        return isShear;
    }

    @Override
    public RoamSceneDataVo getBrowseVr720SenceData(SceneDataSearch search) throws BizException {
        RoamSceneDataVo dataVo = new RoamSceneDataVo();
        DesignPlanRecommended planRecommended = designPlanRecommendedServiceV2.get(search.getRenderId());
        if(planRecommended == null){
            throw new BizException("找不到该推荐方案,planRecommendedId:" + search.getRenderId());
        }
        //得到推荐方案单点渲染的图
        ResRenderPic renderPic = resRenderPicService.getResRenderPicByPlanRecommended(search.getRenderId(),4);
        if(renderPic == null){
            throw new BizException("该推荐方案找不到单点渲染资源,planRecommendedId:" + search.getRenderId());
        }
        List<PanoramaDataVo> panoramaDataVos = new ArrayList<PanoramaDataVo>();
        PanoramaDataVo panoramaDataVo = this.getRoamSenceInfoByRenderPic(renderPic);
        panoramaDataVos.add(panoramaDataVo);
        dataVo.setDetails(panoramaDataVos);
        dataVo.setViewControlType(0);

        return dataVo;
    }

}

