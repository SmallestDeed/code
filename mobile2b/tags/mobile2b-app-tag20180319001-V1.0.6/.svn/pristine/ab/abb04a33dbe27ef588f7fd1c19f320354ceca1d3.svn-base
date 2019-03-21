package com.nork.pano.service.impl;

import com.nork.common.model.LoginUser;
import com.nork.common.pano.PanoramaUtil;
import com.nork.common.util.Encodes;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.JAXBUtil;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.Lists;
import com.nork.common.util.Utils;
import com.nork.design.controller.web.WebDesignPlanController;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.dao.DesignRenderRoamMapper;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanProductRenderSceneService;
import com.nork.design.dao.OptimizePlanMapper;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignRenderRoam;
import com.nork.design.model.ProductsCostType;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.OptimizePlanService;
import com.nork.design.service.impl.DesignPlanProductServiceImpl.costListEnum;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.pano.model.roam.Roam;
import com.nork.pano.model.scene.*;
import com.nork.pano.model.scene.hotspot.Hotspot;
import com.nork.pano.service.PanoramaService;
import com.nork.product.dao.AuthorizedConfigMapper;
import com.nork.product.dao.BaseCompanyMapper;
import com.nork.product.dao.BaseProductStyleMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.BaseProductStyle;
import com.nork.render.model.RenderPanoLevel;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResDesignMapper;
import com.nork.system.dao.ResPicMapper;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserGroupService;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service("PanoramaService")
@Transactional
public class PanoramaServiceImpl implements PanoramaService
{
    private static Logger logger = Logger
            .getLogger(PanoramaServiceImpl.class);
    @Autowired
    private DesignPlanMapper designPlanMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private AuthorizedConfigMapper authorizedConfigMapper;
    @Autowired
    private BaseCompanyMapper baseCompanyMapper;
    @Autowired
    private ResPicMapper resPicMapper;
    @Autowired
    private SpaceCommonMapper spaceCommonMapper;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private ResRenderPicMapper resRenderPicMapper;
    @Autowired
    private DesignPlanProductService designPlanProductService;
    @Autowired
    private DesignRenderRoamMapper designRenderRoamMapper;
    @Autowired
    private ResDesignMapper resDesignMapper;
    @Autowired
    private BaseProductStyleMapper baseProductStyleMapper;
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private DesignPlanProductRenderSceneService designPlanProductRenderSceneService;
    @Autowired
    private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;
    @Autowired
    private OptimizePlanMapper optimizePlanMapper;
    @Autowired
    private OptimizePlanService optimizePlanService;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;

    /**
     * 单场景加载（主函数）
     *
     * @param sysCode
     * @return
     */
    @Override
    public SingleSceneVo vr720Single(String sysCode)
    {
        SingleSceneVo singleSceneVo = null;
        if (StringUtils.isNotBlank(sysCode))
        {
            singleSceneVo = new SingleSceneVo();
            // 获取单个场景对象
            Scene scene = this.getSceneBySysCode(sysCode);
            if (scene != null)
            {
                singleSceneVo.setScene(JAXBUtil.beanToXml(scene, Scene.class));

                // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
                PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);
                panoramaVo.setThumbUrl(scene.getThumbUrl());
                panoramaVo.setWindowsPercent(scene.getWindowsPercent());
                singleSceneVo.setPanoramaVo(panoramaVo);

                // 获取设计方案产品费用清单
                //如果有就走以前,没有就走onekey
                /*ResRenderPic resRenderPic = new ResRenderPic();
                resRenderPic.setSysCode(sysCode);
                resRenderPic.setBusinessId(panoramaVo.getPlanId());*/
                /*Integer i = optimizePlanService.countOnekeyDesignPlan(resRenderPic);*/
                /*List<ProductsCostType> list = this.getProductsCost(panoramaVo.getPlanId(), panoramaVo.getUserId(), panoramaVo.getUserType());*/
                List<ProductsCostType> list = this.getProductsCost(panoramaVo);
//             if (i > 0) {
//                	 list = this.getProductsCost(panoramaVo.getPlanId(), panoramaVo.getUserId(), panoramaVo.getUserType());
//				}else {
//					 list = optimizePlanService.getProductsCost(panoramaVo.getPlanId(), panoramaVo.getUserId(), panoramaVo.getUserType());
//				}
                if (list != null && list.size() > 0)
                {
                    BigDecimal totalPrice = new BigDecimal(0.0);
                    for (ProductsCostType cost : list)
                    {
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
     *
     * @param sysCode design_render_roam表的uuid
     * @return
     */
    @Override
    public RoamSceneVo vr720Roam(String sysCode)
    {
        RoamSceneVo roamSceneVo = null;
        if (StringUtils.isNotBlank(sysCode))
        {
            roamSceneVo = new RoamSceneVo();
        }
        // 获取漫游的全景图模型集合
        List<Roam> roamList = getRoamRenderRelation(sysCode);

        // 组装pano场景标签
        if (roamList != null && roamList.size() > 0)
        {
            Integer screenPicId = null;// 截图ID
            List<String> xmlList = new ArrayList<String>();
            StringBuffer sceneXml = new StringBuffer();
            Scene scene = null;
            Image image = null;
            ImageSphere imageSphere = null;
            List<Hotspot> hotspotList = null;
            ResRenderPic resRenderPic = null;
            for (Roam roam : roamList)
            {
                Integer renderPicId = roam.getFieldName();
                logger.error("renderPicId----------------" + renderPicId);
                resRenderPic = resRenderPicMapper.selectByPrimaryKey(renderPicId);
                if (resRenderPic != null)
                {
                    screenPicId = resRenderPic.getSysTaskPicId();
                    logger.error("screenPicId*****************" + screenPicId);
                    scene = new Scene();
                    scene.setPlanId(resRenderPic.getBusinessId());
                    scene.setName("scene_" + renderPicId);
                    scene.setTitle("");
                    scene.setThumbUrl("");
                    scene.setView(View.getDefaultView());// 使用默认view
                    image = new Image();
                    imageSphere = new ImageSphere();
                    /*imageSphere.setUrl(FileUploadUtils.RESOURCES_URL + resRenderPic.getPicPath());*/
                    imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
                    image.setSphere(imageSphere);
                    scene.setImage(image);

                    // 添加热点
                    Hotspot hotspot = null;
                    hotspotList = new ArrayList<Hotspot>();
                    for (Roam roam1 : roamList)
                    {
                        if (roam1.getFieldName() != roam.getFieldName())
                        {
                            hotspot = new Hotspot();
                            hotspot.setName("hotspot_" + roam1.getFieldName());
                            hotspot.setUrl("images/hotspot/arrow.gif");
                            hotspot.setOnClick("loadscene('scene_" + roam1.getFieldName() + "')");// 点击事件，加载其他场景
                            hotspot.setText(String.valueOf(roam1.getIndex()));
                            hotspot.setLinkedScene("scene_" + roam1.getFieldName());
//                            hotspot.setOnLoaded("add_all_the_time_tooltip();");// 文字提示小标签.优先使用text中的文本，如果text为空则使用linkscene对应的场景的title属性
                            hotspot.setAth(PanoramaUtil.getAth(roam, roam1));
                            hotspot.setAtv(PanoramaUtil.getAtv(roam, roam1));
                            hotspot.setDistance(PanoramaUtil.distance(roam.getPosition(), roam1.getPosition()));
                            hotspotList.add(hotspot);
                        }
                    }
                    PanoramaUtil.ComparatorT comparatorT = new PanoramaUtil.ComparatorT();
                    Collections.sort(hotspotList, comparatorT);
                    scene.setHotspotList(hotspotList);
                    String sceneStr = JAXBUtil.beanToXml(scene, Scene.class);
                    sceneXml.append(sceneStr).append(",");
                    xmlList.add(sceneStr);
                }
            }

            // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
            /*PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene.getPlanId(), null, null);*/
            PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);
            // 获取缩略图
            if (screenPicId != null)
            {
                logger.error("screenPicId ===================" + screenPicId);
                ResRenderPic thumbPic = resRenderPicMapper.selectOneByPid(screenPicId);
                if (thumbPic != null)
                {
                    /*panoramaVo.setThumbUrl(FileUploadUtils.RESOURCES_URL + thumbPic.getPicPath());*/
                    panoramaVo.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
                }
            }
            roamSceneVo.setPanoramaVo(panoramaVo);

            // 获取设计方案产品费用清单
            /*List<ProductsCostType> list = this.getProductsCost(panoramaVo.getPlanId(), panoramaVo.getUserId(), panoramaVo.getUserType());*/
            List<ProductsCostType> list = this.getProductsCost(panoramaVo);
            if (list != null && list.size() > 0)
            {
                BigDecimal totalPrice = new BigDecimal(0.0);
                for (ProductsCostType cost : list)
                {
                    totalPrice = totalPrice.add(cost.getTotalPrice());
                }
                roamSceneVo.setTotalPrice(totalPrice.toString());
                roamSceneVo.setProductsCostTypeList(list);
            }

            // 资源访问url
            roamSceneVo.setResourceUrl(FileUploadUtils.RESOURCES_URL);

            try
            {
                roamSceneVo.setScenes(URLEncoder.encode(sceneXml.substring(0, sceneXml.length() - 1), "UTF-8").replaceAll("\\+", "%20"));
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

        }
        return roamSceneVo;
    }

    /**
     * 组装720页面基本信息
     *
     * @param planId
     * @param integer2
     * @param integer
     * @return
     */
    @Override
    public PanoramaVo assemblyPanoramaVo(Scene scene)
    {
    /*public PanoramaVo assemblyPanoramaVo(Integer planId, Integer designPlanRenderSceneId, Integer designPlanRecommendedId) {*/
        Integer planId = scene.getPlanId();
        Integer designPlanRenderSceneId = scene.getDesignPlanRenderSceneId();
        Integer designPlanRecommendedId = scene.getDesignPlanRecommendedId();

        PanoramaVo panoramaVo = new PanoramaVo();

        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0)
        {
            panoramaVo = this.assemblyPanoramaVoRecommendedId(designPlanRecommendedId);
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0)
        {
            panoramaVo = this.assemblyPanoramaVoSceneId(designPlanRenderSceneId);
        } else if (planId != null && planId > 0)
        {
            panoramaVo = this.assemblyPanoramaVoPlanId(planId);
        }

        return panoramaVo;
    }

    private PanoramaVo assemblyPanoramaVoSceneId(Integer designPlanRenderSceneId)
    {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setDesignPlanRenderSceneId(designPlanRenderSceneId);
        // 参数验证 ->start
        if (designPlanRenderSceneId == null || designPlanRenderSceneId < 1)
        {
            return panoramaVo;
        }
        // 参数验证 ->end

		/*DesignPlan designPlan = designPlanMapper.selectByPrimaryKey(planId);*/
        DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.get(designPlanRenderSceneId);
        if (designPlanRenderScene != null && designPlanRenderScene.getUserId() != null && designPlanRenderScene.getUserId() > 0)
        {
		    /*panoramaVo.setPlanId(planId);*/
            // 设计方案风格，如果没有，则默认为混搭
            Integer styleId = designPlanRenderScene.getDesignRecommendedStyleId();
            panoramaVo.setTitle("3D全景漫游|混搭");
            if (styleId != null)
            {
                BaseProductStyle style = baseProductStyleMapper.selectByPrimaryKey(styleId);
                if (style != null)
                {
                    panoramaVo.setTitle("3D全景漫游|" + style.getName());
                }
            }
            // 获取用户昵称
            Integer userId = designPlanRenderScene.getUserId();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            if (sysUser != null)
            {
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());

                // 获取用户公司
                AuthorizedConfig authorizedConfig = new AuthorizedConfig();
                authorizedConfig.setUserId(sysUser.getId());
                authorizedConfig.setState(1);
                authorizedConfig.setIsDeleted(0);
                List<AuthorizedConfig> authorizedConfigs = authorizedConfigMapper.selectList(authorizedConfig);
                if (authorizedConfigs != null && authorizedConfigs.size() > 0)
                {
                    authorizedConfig = authorizedConfigs.get(0);
                    String companyId = authorizedConfig.getCompanyId();
                    BaseCompany baseCompany = baseCompanyMapper.selectByPrimaryKey(Integer.valueOf(companyId));
                    if (baseCompany != null)
                    {
                        panoramaVo.setCompanyName(baseCompany.getCompanyName());

                        // 获取用户公司LOGO
                        if (baseCompany.getCompanyLogo() != null)
                        {
                            ResPic resPic = resPicMapper.selectByPrimaryKey(baseCompany.getCompanyLogo());
                            if (resPic != null)
                            {
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
                                panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
                            }
                        }
                    }
                }
            }

            // 获取房型类型
            if (designPlanRenderScene.getSpaceCommonId() != null)
            {
                SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlanRenderScene.getSpaceCommonId());
                if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null)
                {
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }
        return panoramaVo;
    }

    private PanoramaVo assemblyPanoramaVoRecommendedId(Integer designPlanRecommendedId)
    {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setDesignPlanRecommendedId(designPlanRecommendedId);
        // 参数验证 ->start
        if (designPlanRecommendedId == null || designPlanRecommendedId < 1)
        {
            return panoramaVo;
        }
        // 参数验证 ->end
		
		/*DesignPlan designPlan = designPlanMapper.selectByPrimaryKey(planId);*/
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanRecommendedId);
        if (designPlanRecommended != null && designPlanRecommended.getUserId() != null && designPlanRecommended.getUserId() > 0)
        {
		    /*panoramaVo.setPlanId(planId);*/
            // 设计方案风格，如果没有，则默认为混搭
            Integer styleId = designPlanRecommended.getDesignRecommendedStyleId();
            panoramaVo.setTitle("3D全景漫游|混搭");
            if (styleId != null)
            {
                BaseProductStyle style = baseProductStyleMapper.selectByPrimaryKey(styleId);
                if (style != null)
                {
                    panoramaVo.setTitle("3D全景漫游|" + style.getName());
                }
            }
            // 获取用户昵称
            Integer userId = designPlanRecommended.getUserId();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            if (sysUser != null)
            {
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());

                // 获取用户公司
                AuthorizedConfig authorizedConfig = new AuthorizedConfig();
                authorizedConfig.setUserId(sysUser.getId());
                authorizedConfig.setState(1);
                authorizedConfig.setIsDeleted(0);
                List<AuthorizedConfig> authorizedConfigs = authorizedConfigMapper.selectList(authorizedConfig);
                if (authorizedConfigs != null && authorizedConfigs.size() > 0)
                {
                    authorizedConfig = authorizedConfigs.get(0);
                    String companyId = authorizedConfig.getCompanyId();
                    BaseCompany baseCompany = baseCompanyMapper.selectByPrimaryKey(Integer.valueOf(companyId));
                    if (baseCompany != null)
                    {
                        panoramaVo.setCompanyName(baseCompany.getCompanyName());

                        // 获取用户公司LOGO
                        if (baseCompany.getCompanyLogo() != null)
                        {
                            ResPic resPic = resPicMapper.selectByPrimaryKey(baseCompany.getCompanyLogo());
                            if (resPic != null)
                            {
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
                                panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
                            }
                        }
                    }
                }
            }

            // 获取房型类型
            if (designPlanRecommended.getSpaceCommonId() != null)
            {
                SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlanRecommended.getSpaceCommonId());
                if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null)
                {
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }
        return panoramaVo;
    }

    private PanoramaVo assemblyPanoramaVoPlanId(Integer planId)
    {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setPlanId(planId);
        // 参数验证 ->start
        if (planId == null || planId < 1)
        {
            return panoramaVo;
        }
        // 参数验证 ->end

        DesignPlan designPlan = designPlanMapper.selectByPrimaryKey(planId);
        if (designPlan != null && designPlan.getUserId() != null && designPlan.getUserId() > 0)
        {
            panoramaVo.setPlanId(planId);
            // 设计方案风格，如果没有，则默认为混搭
            Integer designStyleId = designPlan.getDesignRecommendedStyleId();
            panoramaVo.setTitle("3D全景漫游|混搭");
            if (designStyleId != null)
            {
                BaseProductStyle style = baseProductStyleMapper.selectByPrimaryKey(designStyleId);
                if (style != null)
                {
                    panoramaVo.setTitle("3D全景漫游|" + style.getName());
                }
            }
            // 获取用户昵称
            Integer userId = designPlan.getUserId();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            if (sysUser != null)
            {
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());

                // 获取用户公司
                AuthorizedConfig authorizedConfig = new AuthorizedConfig();
                authorizedConfig.setUserId(sysUser.getId());
                authorizedConfig.setState(1);
                authorizedConfig.setIsDeleted(0);
                List<AuthorizedConfig> authorizedConfigs = authorizedConfigMapper.selectList(authorizedConfig);
                if (authorizedConfigs != null && authorizedConfigs.size() > 0)
                {
                    authorizedConfig = authorizedConfigs.get(0);
                    String companyId = authorizedConfig.getCompanyId();
                    BaseCompany baseCompany = baseCompanyMapper.selectByPrimaryKey(Integer.valueOf(companyId));
                    if (baseCompany != null)
                    {
                        panoramaVo.setCompanyName(baseCompany.getCompanyName());

                        // 获取用户公司LOGO
                        if (baseCompany.getCompanyLogo() != null)
                        {
                            ResPic resPic = resPicMapper.selectByPrimaryKey(baseCompany.getCompanyLogo());
                            if (resPic != null)
                            {
		                        /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
                                panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
                            }
                        }
                    }
                }
            }

            // 获取房型类型
            if (designPlan.getSpaceCommonId() != null)
            {
                SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlan.getSpaceCommonId());
                if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null)
                {
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }
        return panoramaVo;
    }

    /**
     * 组装720页面基本信息
     *
     * @param planId
     * @param integer2
     * @param integer
     * @return
     */
    public PanoramaVo assemblyPanoramaVo_old(Integer planId)
    {
        PanoramaVo panoramaVo = new PanoramaVo();
        DesignPlan designPlan = designPlanMapper.selectByPrimaryKey(planId);
        if (designPlan != null && designPlan.getUserId() != null && designPlan.getUserId() > 0)
        {
            panoramaVo.setPlanId(planId);
            // 设计方案风格，如果没有，则默认为混搭
            Integer designStyleId = designPlan.getDesignRecommendedStyleId();
            panoramaVo.setTitle("3D全景漫游|混搭");
            if (designStyleId != null)
            {
                BaseProductStyle style = baseProductStyleMapper.selectByPrimaryKey(designStyleId);
                if (style != null)
                {
                    panoramaVo.setTitle("3D全景漫游|" + style.getName());
                }
            }
            // 获取用户昵称
            Integer userId = designPlan.getUserId();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            if (sysUser != null)
            {
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());

                // 获取用户公司
                AuthorizedConfig authorizedConfig = new AuthorizedConfig();
                authorizedConfig.setUserId(sysUser.getId());
                authorizedConfig.setState(1);
                authorizedConfig.setIsDeleted(0);
                List<AuthorizedConfig> authorizedConfigs = authorizedConfigMapper.selectList(authorizedConfig);
                if (authorizedConfigs != null && authorizedConfigs.size() > 0)
                {
                    authorizedConfig = authorizedConfigs.get(0);
                    String companyId = authorizedConfig.getCompanyId();
                    BaseCompany baseCompany = baseCompanyMapper.selectByPrimaryKey(Integer.valueOf(companyId));
                    if (baseCompany != null)
                    {
                        panoramaVo.setCompanyName(baseCompany.getCompanyName());

                        // 获取用户公司LOGO
                        String companyLogo = "";
                        if (baseCompany.getCompanyLogo() != null)
                        {
                            ResPic resPic = resPicMapper.selectByPrimaryKey(baseCompany.getCompanyLogo());
                            if (resPic != null)
                            {
                                /*panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());*/
                                panoramaVo.setCompanyLogoPath(Utils.getAbsoluteUrlByRelativeUrl(resPic.getPicPath()));
                            }
                        }
                    }
                }
            }

            // 获取房型类型
            if (designPlan.getSpaceCommonId() != null)
            {
                SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlan.getSpaceCommonId());
                if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null)
                {
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
                    panoramaVo.setHouseTypeName("我设计的" + sysDictionary.getName());
                }
            }

            panoramaVo.setLogoPath(FileUploadUtils.UPLOAD_ROOT + "");
        }

        return panoramaVo;
    }

    /**
     * 通过sysCode得到一个场景
     *
     * @param sysCode
     * @return
     */
    @Override
    public Scene getSceneBySysCode(String sysCode)
    {
        Scene scene = null;
        Image image = null;
        ImageSphere imageSphere = null;
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setSysCode(sysCode);
        resRenderPic.setRenderingType(4);
        resRenderPic.setStart(0);
        resRenderPic.setLimit(1);
        List<ResRenderPic> list = resRenderPicMapper.selectList(resRenderPic);
        ResRenderPic thumbPic = null;
        if (list != null && list.size() > 0)
        {
            resRenderPic = list.get(0);
            scene = new Scene();
            scene.setName("scene_" + resRenderPic.getId());
            scene.setTitle("");
            // 获取缩略图
            thumbPic = resRenderPicMapper.selectOneByPid(resRenderPic.getId());
            if (thumbPic != null)
            {
                /*scene.setThumbUrl(FileUploadUtils.RESOURCES_URL + thumbPic.getPicPath());*/
                scene.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
            }
            scene.setPlanId(resRenderPic.getBusinessId());
            scene.setDesignPlanRecommendedId(resRenderPic.getPlanRecommendedId());
            scene.setDesignPlanRenderSceneId(resRenderPic.getDesignSceneId());
            scene.setView(View.getDefaultView());// 使用默认view
            image = new Image();
            imageSphere = new ImageSphere();
            /*imageSphere.setUrl(FileUploadUtils.RESOURCES_URL+resRenderPic.getPicPath());*/
            imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
            image.setSphere(imageSphere);
            scene.setImage(image);
            String windowsPercent = "width:100%;height:100%;";
            if (resRenderPic.getPanoLevel() != null)
            {
                if (resRenderPic.getPanoLevel() == RenderPanoLevel.LEVEL_ONE)
                {
                    windowsPercent = "width:35%;height:35%;";
                } else if (resRenderPic.getPanoLevel() == RenderPanoLevel.LEVEL_TWO)
                {
                    windowsPercent = "width:70%;height:70%;";
                } else if (resRenderPic.getPanoLevel() == RenderPanoLevel.LEVEL_THREE)
                {
                    windowsPercent = "width:90%;height:90%;";
                }
            }
            scene.setWindowsPercent(windowsPercent);
        }
        return scene;
    }

    /**
     * 获取设计方案产品费用清单
     *
     * @param planId
     * @param userId
     * @param userType
     * @return
     */
    public List<ProductsCostType> getProductsCost_old(Integer planId, Integer userId, Integer userType)
    {
        List<ProductsCostType> list = null;
        if (planId == null || userId == null || userType == null)
        {
            return null;
        }
        // 获取产品列表
        LoginUser loginUser = new LoginUser();
        loginUser.setId(userId);
        loginUser.setUserType(userType);
        DesignPlanProduct designPlanProduct = new DesignPlanProduct();
        designPlanProduct.setPlanId(planId);
        designPlanProduct.setUserId(userId);
        list = designPlanProductService.costList(loginUser, designPlanProduct, costListEnum.designPlan);
        return list;
    }

    /*public List<ProductsCostType> getProductsCost(Integer planId, Integer userId, Integer userType){*/
    public List<ProductsCostType> getProductsCost(PanoramaVo panoramaVo)
    {

        Integer planId = panoramaVo.getPlanId();
        Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
        Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();
        Integer oneKeyDesignPlanId = panoramaVo.getOneKeyDesignPlanId();
        Integer userId = panoramaVo.getUserId();
        Integer userType = panoramaVo.getUserType();

        List<ProductsCostType> list = null;
        if ((planId == null && designPlanRecommendedId == null && designPlanRenderSceneId == null && oneKeyDesignPlanId == null)
                || userId == null || userType == null)
        {
            return null;
        }
        // 获取产品列表
        LoginUser loginUser = new LoginUser();
        loginUser.setId(userId);
        loginUser.setUserType(userType);
        DesignPlanProduct designPlanProduct = new DesignPlanProduct();
        designPlanProduct.setUserId(userId);
        designPlanProduct.setPlatformId(panoramaVo.getPlatformId());
        logger.error("designPlanRecommendedId = " + designPlanRecommendedId
                + ";designPlanRenderSceneId = " + designPlanRenderSceneId
                + ";oneKeyDesignPlanId = " + oneKeyDesignPlanId
                + ";planId=" + planId);
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0)
        {
            designPlanProduct.setPlanId(designPlanRecommendedId);
            list = designPlanProductService.costList(loginUser, designPlanProduct, costListEnum.designPlanRecommended);
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0)
        {
            designPlanProduct.setPlanId(designPlanRenderSceneId);
            list = designPlanProductService.costList(loginUser, designPlanProduct, costListEnum.designPlanRenderScene);
        } else if (oneKeyDesignPlanId != null && oneKeyDesignPlanId.intValue() > 0)
        {
            designPlanProduct.setPlanId(oneKeyDesignPlanId);
            list = designPlanProductService.costList(loginUser, designPlanProduct, costListEnum.oneKeyDesignPlan);
        } else if (planId != null && planId > 0)
        {
            designPlanProduct.setPlanId(planId);
            list = designPlanProductService.costList(loginUser, designPlanProduct, costListEnum.designPlan);
        }

        return list;
    }

    /**
     * 获取全景图组关系
     *
     * @return
     */
    public List<Roam> getRoamRenderRelation(String uuid)
    {
        List<Roam> roamList = null;
        if (StringUtils.isBlank(uuid))
        {
            return null;
        }
        // 获取漫游
        logger.error("uuid   =  =  " + uuid);
        DesignRenderRoam designRenderRoam = designRenderRoamMapper.selectByUUID(uuid);
        if (designRenderRoam != null)
        {
            // 获取多720图片之间的关系信息
            String roamConfigContext = "";
            Integer roamConfigId = designRenderRoam.getRoamConfig();
            if (roamConfigId != null && roamConfigId.intValue() > 0)
            {
                ResDesign roamConfig = resDesignMapper.selectByPrimaryKey(roamConfigId);
                if (roamConfig != null)
                {
                    /*roamConfigContext = FileUploadUtils.getFileContext(FileUploadUtils.UPLOAD_ROOT + roamConfig.getFilePath());*/
                    roamConfigContext = FileUploadUtils.getFileContext(Utils.getAbsolutePath(roamConfig.getFilePath(), null));
                }
            }

            if (StringUtils.isNotBlank(roamConfigContext))
            {
                JSONArray jsonArray = JSONArray.fromObject(roamConfigContext);
                roamList = JSONArray.toList(jsonArray, Roam.class);
            }
        }
        return roamList;
    }

    /**
     * 720组合场景加载（主函数）
     *
     * @param code 组合UUID
     * @return
     */
    @Override
    public GroupSceneVo vr720Group(String code)
    {
        GroupSceneVo groupSceneVo = new GroupSceneVo();
        // 获取组合信息
        Group group = sysUserGroupService.doShare(code);
        if (group == null)
        {
            return null;
        }
        List<GroupDetail> groupDetailList = group.getList();
        if (Lists.isNotEmpty(groupDetailList))
        {
            StringBuilder sceneXml = new StringBuilder();
            List<Thumb> thumbList = new ArrayList<>();
            Scene scene = null;
            Scene sceneMain = null;
            Image image = null;
            Thumb thumb = null;
            ImageSphere imageSphere = null;
            GroupDetail groupDetail = null;
//            List<Hotspot> hotspotList = null;
            for (int i = 0; i < groupDetailList.size(); i++)
            {
                groupDetail = groupDetailList.get(i);
                scene = new Scene();
                scene.setPlanId(groupDetail.getPlanId());
                /** 1.组装场景 **/
                scene.setName("scene_" + groupDetail.getPicId());
                scene.setTitle("");
                scene.setThumbUrl(FileUploadUtils.RESOURCES_URL + groupDetail.getThumbPath());
                image = new Image();
                imageSphere = new ImageSphere();
                imageSphere.setUrl(FileUploadUtils.RESOURCES_URL + groupDetail.getPicPath());
                image.setSphere(imageSphere);
                scene.setImage(image);
                scene.setView(View.getDefaultView());// 使用默认的view参数
                // 判断类型
                if (RenderTypeCode.ROAM_720_LEVEL == groupDetail.getType())
                {// 720漫游
                    // 添加热点
                }
                if (RenderTypeCode.COMMON_PICTURE_LEVEL == groupDetail.getType())
                {// 普通高清渲染图
                    //
                }
                if (RenderTypeCode.COMMON_VIDEO == groupDetail.getType())
                {// 视频
                    //
                }

                /** 2.组装缩略图列表 **/
                thumb = new Thumb();
                thumb.setType(groupDetail.getType());
                thumb.setThumbUrl(scene.getThumbUrl());
                thumb.setDesc(groupDetail.getThumbDesc());

                sceneXml.append(JAXBUtil.beanToXml(scene, Scene.class)).append(",");
                thumbList.add(thumb);

                if (i == 0)
                {
                    sceneMain = scene;
                }
            }
            try
            {
                groupSceneVo.setScenes(URLEncoder.encode(sceneXml.substring(0, sceneXml.length() - 1), "UTF-8").replaceAll("\\+", "%20"));
                if (sceneMain != null)
                {
                    // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
                    PanoramaVo panoramaVo = this.assemblyGroupPanoramaVo(sceneMain.getPlanId());
                    // 获取缩略图
                    panoramaVo.setThumbUrl(FileUploadUtils.RESOURCES_URL + group.getThumbPath());
                    groupSceneVo.setPanoramaVo(panoramaVo);
                }
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            groupSceneVo.setThumbList(thumbList);
        }
        return groupSceneVo;
    }

    /***
     * 获取设计方案副本的产品费用清单
     * @return
     */
    @Override
    public List<ProductsCostType> getDesignRenderGroupCost(Integer planId, Integer userId, Integer userType)
    {
        List<ProductsCostType> list = null;
        if (planId == null || userId == null || userType == null)
        {
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
     *
     * @param planId
     * @return
     */
    @Override
    public PanoramaVo assemblyGroupPanoramaVo(Integer planId)
    {
        PanoramaVo panoramaVo = new PanoramaVo();
        DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.get(planId);
        if (designPlanRenderScene != null && designPlanRenderScene.getUserId() != null && designPlanRenderScene.getUserId() > 0)
        {
            panoramaVo.setPlanId(planId);
            // 设计方案风格，如果没有，则默认为混搭
            Integer designStyleId = designPlanRenderScene.getDesignRecommendedStyleId();
            panoramaVo.setTitle("3D全景漫游|混搭");
            if (designStyleId != null)
            {
                BaseProductStyle style = baseProductStyleMapper.selectByPrimaryKey(designStyleId);
                if (style != null)
                {
                    panoramaVo.setTitle("3D全景漫游|" + style.getName());
                }
            }
            // 获取用户昵称
            Integer userId = designPlanRenderScene.getUserId();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            if (sysUser != null)
            {
                panoramaVo.setUserId(sysUser.getId());
                panoramaVo.setUserType(sysUser.getUserType());
                panoramaVo.setUserName(sysUser.getUserName());

                // 获取用户公司
                AuthorizedConfig authorizedConfig = new AuthorizedConfig();
                authorizedConfig.setUserId(sysUser.getId());
                authorizedConfig.setState(1);
                authorizedConfig.setIsDeleted(0);
                List<AuthorizedConfig> authorizedConfigs = authorizedConfigMapper.selectList(authorizedConfig);
                if (authorizedConfigs != null && authorizedConfigs.size() > 0)
                {
                    authorizedConfig = authorizedConfigs.get(0);
                    String companyId = authorizedConfig.getCompanyId();
                    BaseCompany baseCompany = baseCompanyMapper.selectByPrimaryKey(Integer.valueOf(companyId));
                    if (baseCompany != null)
                    {
                        panoramaVo.setCompanyName(baseCompany.getCompanyName());

                        // 获取用户公司LOGO
                        String companyLogo = "";
                        if (baseCompany.getCompanyLogo() != null)
                        {
                            ResPic resPic = resPicMapper.selectByPrimaryKey(baseCompany.getCompanyLogo());
                            if (resPic != null)
                            {
                                panoramaVo.setCompanyLogoPath(FileUploadUtils.RESOURCES_URL + resPic.getPicPath());
                            }
                        }
                    }
                }
            }

            // 获取房型类型
            if (designPlanRenderScene.getSpaceCommonId() != null)
            {
                SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlanRenderScene.getSpaceCommonId());
                if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null)
                {
                    SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
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
    public Scene getMobileSceneBySysCodeForAuto(String sysCode)
    {
        Scene scene = null;
        Image image = null;
        ImageSphere imageSphere = null;
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setSysCode(sysCode);
        resRenderPic.setRenderingType(4);
        resRenderPic.setStart(0);
        resRenderPic.setLimit(1);
        List<ResRenderPic> list = resRenderPicMapper.selectListOfMobile(resRenderPic);
        ResRenderPic thumbPic = null;
        if (list != null && list.size() > 0)
        {
            resRenderPic = list.get(0);
            scene = new Scene();
            scene.setName("scene_" + resRenderPic.getId());
            scene.setTitle("");
            // 获取缩略图
            thumbPic = resRenderPicMapper.selectOneByPidOfMobile(resRenderPic.getId());
            if (thumbPic != null)
            {
                /*scene.setThumbUrl(FileUploadUtils.RESOURCES_URL + thumbPic.getPicPath());*/
                scene.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
            }
            scene.setPlanId(resRenderPic.getBusinessId());
            scene.setView(View.getDefaultView());// 使用默认view
            image = new Image();
            imageSphere = new ImageSphere();
            /*imageSphere.setUrl(FileUploadUtils.RESOURCES_URL+resRenderPic.getPicPath());*/
            imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
            image.setSphere(imageSphere);
            scene.setImage(image);
            String windowsPercent = "width:100%;height:100%;";
            /*if( resRenderPic.getPanoLevel() != null ){
                if( resRenderPic.getPanoLevel() == RenderPanoLevel.LEVEL_ONE ){
                    windowsPercent = "width:35%;height:35%;";
                }else if( resRenderPic.getPanoLevel() == RenderPanoLevel.LEVEL_TWO ){
                    windowsPercent = "width:70%;height:70%;";
                }else if( resRenderPic.getPanoLevel() == RenderPanoLevel.LEVEL_THREE ){
                    windowsPercent = "width:90%;height:90%;";
                }
            }*/
            scene.setWindowsPercent(windowsPercent);
        }
        return scene;
    }


    /**
     * 从自动渲染资源里获取场景信息
     */
    @Override
    public SingleSceneVo vr720SingleMobileForAuto(String sysCode)
    {
        SingleSceneVo singleSceneVo = null;
        if (StringUtils.isNotBlank(sysCode))
        {
            singleSceneVo = new SingleSceneVo();
            // 获取单个场景对象
            Scene scene = this.getMobileSceneBySysCodeForAuto(sysCode);
            if (scene != null)
            {
                singleSceneVo.setScene(JAXBUtil.beanToXml(scene, Scene.class));

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
                if (list != null && list.size() > 0)
                {
                    BigDecimal totalPrice = new BigDecimal(0.0);
                    for (ProductsCostType cost : list)
                    {
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
    public PanoramaVo assemblyPanoramaVoForAuto(Integer planId)
    {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setPlanId(planId);
        panoramaVo.setOneKeyDesignPlanId(planId);
        panoramaVo.setTitle("3D全景漫游|混搭");
        DesignPlan designPlan = optimizePlanService.getPlan(planId);
        // 获取房型类型
        if (designPlan.getSpaceCommonId() != null)
        {
            SpaceCommon spaceCommon = spaceCommonMapper.selectByPrimaryKey(designPlan.getSpaceCommonId());
            if (spaceCommon != null && spaceCommon.getSpaceFunctionId() != null)
            {
                SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue("houseType", spaceCommon.getSpaceFunctionId());
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
     *
     * @param sysCode
     * @return
     */
    @Override
    public SingleSceneVo vr720MobileSingle(String sysCode)
    {
        SingleSceneVo singleSceneVo = null;
        if (StringUtils.isNotBlank(sysCode))
        {
            singleSceneVo = new SingleSceneVo();
            // 获取单个场景对象
            Scene scene = this.getMobileSceneBySysCode(sysCode);
            if (scene != null)
            {
                singleSceneVo.setScene(JAXBUtil.beanToXml(scene, Scene.class));

                // 获取页面需要显示的业务信息（用户昵称、企业LOGO、企业名称、产品费用清单）
                PanoramaVo panoramaVo = this.assemblyPanoramaVo(scene);
                panoramaVo.setThumbUrl(scene.getThumbUrl());
                panoramaVo.setWindowsPercent(scene.getWindowsPercent());
                singleSceneVo.setPanoramaVo(panoramaVo);

                // 获取设计方案产品费用清单
                //如果有就走以前,没有就走onekey
                List<ProductsCostType> list = this.getProductsCost(panoramaVo);
                if (list != null && list.size() > 0)
                {
                    BigDecimal totalPrice = new BigDecimal(0.0);
                    for (ProductsCostType cost : list)
                    {
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

    private Scene getMobileSceneBySysCode(String sysCode)
    {
        Scene scene = null;
        Image image = null;
        ImageSphere imageSphere = null;
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setSysCode(sysCode);
        resRenderPic.setRenderingType(4);
        resRenderPic.setStart(0);
        resRenderPic.setLimit(1);
        List<ResRenderPic> list = resRenderPicMapper.selectList(resRenderPic);
        ResRenderPic thumbPic = null;
        if (list != null && list.size() > 0)
        {
            resRenderPic = list.get(0);
            scene = new Scene();
            scene.setName("scene_" + resRenderPic.getId());
            scene.setTitle("");
            // 获取缩略图
            thumbPic = resRenderPicMapper.selectOneByPid(resRenderPic.getId());
            if (thumbPic != null)
            {
                scene.setThumbUrl(Utils.getAbsoluteUrlByRelativeUrl(thumbPic.getPicPath()));
            }
            scene.setPlanId(resRenderPic.getBusinessId());
            scene.setDesignPlanRecommendedId(resRenderPic.getPlanRecommendedId());
            scene.setDesignPlanRenderSceneId(resRenderPic.getDesignSceneId());
            scene.setView(View.getDefaultView());// 使用默认view
            image = new Image();
            imageSphere = new ImageSphere();
            imageSphere.setUrl(Utils.getAbsoluteUrlByRelativeUrl(resRenderPic.getPicPath()));
            image.setSphere(imageSphere);
            scene.setImage(image);
            String windowsPercent = "width:100%;height:100%;";
            scene.setWindowsPercent(windowsPercent);
        }
        return scene;
    }


    /**
     * @Author: zhangchengda
     * @Description:
     * @Date: 14:49 2018/5/10
     */
    public List<ProductsCostType> newGetProductsCost(PanoramaVo panoramaVo)
    {
        Integer planId = panoramaVo.getPlanId();
        Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
        Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();
        Integer oneKeyDesignPlanId = panoramaVo.getOneKeyDesignPlanId();
        Integer userId = panoramaVo.getLoginUser().getId();
        Integer userType = panoramaVo.getLoginUser().getUserType();

        List<ProductsCostType> list = null;
        if ((planId == null && designPlanRecommendedId == null && designPlanRenderSceneId == null && oneKeyDesignPlanId == null)
                || userId == null || userType == null)
        {
            return null;
        }
        // 获取产品列表
        DesignPlanProduct designPlanProduct = new DesignPlanProduct();
        designPlanProduct.setUserId(userId);
        designPlanProduct.setPlatformId(panoramaVo.getPlatformId());
        logger.error("designPlanRecommendedId = " + designPlanRecommendedId
                + ";designPlanRenderSceneId = " + designPlanRenderSceneId
                + ";oneKeyDesignPlanId = " + oneKeyDesignPlanId
                + ";planId=" + planId);
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0)
        {
            designPlanProduct.setPlanId(designPlanRecommendedId);
            list = designPlanProductService.costList(panoramaVo.getLoginUser(), designPlanProduct, costListEnum.designPlanRecommended);
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0)
        {
            designPlanProduct.setPlanId(designPlanRenderSceneId);
            list = designPlanProductService.costList(panoramaVo.getLoginUser(), designPlanProduct, costListEnum.designPlanRenderScene);
        } else if (oneKeyDesignPlanId != null && oneKeyDesignPlanId.intValue() > 0)
        {
            designPlanProduct.setPlanId(oneKeyDesignPlanId);
            list = designPlanProductService.costList(panoramaVo.getLoginUser(), designPlanProduct, costListEnum.oneKeyDesignPlan);
        } else if (planId != null && planId > 0)
        {
            designPlanProduct.setPlanId(planId);
            list = designPlanProductService.costList(panoramaVo.getLoginUser(), designPlanProduct, costListEnum.designPlan);
        }

        return list;
    }
}
