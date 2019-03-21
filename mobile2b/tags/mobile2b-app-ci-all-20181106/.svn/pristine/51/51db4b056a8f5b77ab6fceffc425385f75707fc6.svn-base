package com.nork.mobile.service.impl;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.dao.OptimizePlanMapper;
import com.nork.design.model.ProductsCostType;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.mobile.service.MobileSearchDesignPlanProductService;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.pano.service.PanoramaService;
import com.nork.product.cache.ProCategoryCacher;
import com.nork.product.model.ProCategory;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.ProCategoryService;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.model.BasePlatform;
import com.nork.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.nork.system.controller.PlatformController.BASE_PLATFORM_INFO;
import static com.nork.system.controller.PlatformController.MOBILE2B_PLATFORM_CODE;

@Service("mobileSearchDesignPlanProductService")
public class MobileSearchDesignPlanProductServiceImpl implements MobileSearchDesignPlanProductService {

    private static Logger logger = Logger.getLogger(MobileSearchDesignPlanProductServiceImpl.class);
    private static final Gson GSON = new Gson();
    private static final String TYPE_NOT_NULL = "参数type不能为空";
    private static final String TYPE_IS = "参数type为‘";
    private static final String MUST_BE_INTRODUCTION_CID = "’时，必须传入cid参数";
    private static final String ELSE = "其他";

    @Autowired
    private PanoramaService panoramaService;
    @Autowired
    private DesignPlanMapper designPlanMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;
    @Autowired
    private OptimizePlanMapper optimizePlanMapper;
    @Autowired
    private ProCategoryService proCategoryService;

    /**
     * 获取该方案的产品清单
     */
    //@Override
    public Object getDesignPlanProductList(MobileRenderingModel model) {
        Integer planId = model.getPlanId();
        String msgId = model.getMsgId();
        Integer userId = model.getUserId();
        Integer designPlanRenderSceneId = 0;
        Integer oneKeyDesignPlanId = 0;
        Integer designPlanRecommendedId = 0;
        ResponseEnvelope<ProductsCostType> envelope = new ResponseEnvelope<>();
        // 根据不同的请求 查询不同的方案来源
        if ("design".equals(msgId)) {

        } else if ("recommended".equals(msgId)) {
            designPlanRecommendedId = planId;
        } else if ("renderScene".equals(msgId)) {
        } else if ("oneKey".equals(msgId)) {
        }

        if (userId.intValue() != 0) {
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            Integer userType = sysUser.getUserType();
            //添加平台过滤，平台id
            Integer platformId = null;
            String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
            if (StringUtils.isNotEmpty(basePlatformInfo)) {
                BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
                platformId = basePlatform.getId();
            }
            PanoramaVo panoramaVo = new PanoramaVo();
            panoramaVo.setDesignPlanRenderSceneId(designPlanRenderSceneId);
            panoramaVo.setOneKeyDesignPlanId(oneKeyDesignPlanId);
            panoramaVo.setPlanId(planId);
            panoramaVo.setUserId(userId);
            panoramaVo.setUserType(userType);
            panoramaVo.setPlatformId(platformId);
            panoramaVo.setDesignPlanRecommendedId(designPlanRecommendedId);
            List<ProductsCostType> list = panoramaService.getProductsCost(panoramaVo);
            if (list == null || list.size() <= 0) {
                envelope.setSuccess(false);
                envelope.setMessage("搜索产品清单失败");
                return envelope;
            }
            envelope.setTotalCount(list.size());
            envelope.setDatalist(list);
        } else {
            envelope.setMessage("数据异常!");
        }
        return envelope;
    }

    @Override
    public Object searchProCategory(String type, Integer cid, String msgId) {
        long startTime = System.currentTimeMillis();
        String msg = "";
        List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
        if (StringUtils.isNotBlank(type)) {
            ProCategory category = null;
            int total = 0;
            // 查询所有类目
            if ("A".equals(type)) {
                if (Utils.enableRedisCache()) {
                    category = (ProCategory) ProCategoryCacher.getAllList();
                } else {
                    category = proCategoryService.getAllCategory();
                }
            } else {
                if (cid != null) {
                    // 查询pid等于cid的所有产品类目
                    if ("P".equals(type)) {
                        if (Utils.enableRedisCache()) {
                            category = ProCategoryCacher.get(cid.intValue());
                        } else {
                            category = proCategoryService.get(cid.intValue());
                        }
                        categoryList = recursionCategory(category);
//						MyCompartor compartor = new MyCompartor();
//						Collections.sort(categoryList, compartor);
                        category.setChildrenNodes(categoryList);
                        // 查询ID等cid的类目
                    } else if ("O".equals(type)) {
                        if (Utils.enableRedisCache()) {
                            category = ProCategoryCacher.get(cid.intValue());
                        } else {
                            category = proCategoryService.get(cid.intValue());
                        }
                    }
                } else {
                    msg = TYPE_IS + type + MUST_BE_INTRODUCTION_CID;
                    return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
                }
            }
            if (category != null) {
                return resultList(msgId, startTime, category, total);
            }
        } else {
            msg = TYPE_NOT_NULL;
            return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
        }
        return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
    }

    private Object resultList(String msgId, long startTime, ProCategory category, int total) {
        SearchProCategorySmall categorySmall;
        categorySmall = new SearchProCategorySmall();
        categorySmall.setGg(category.getNameFirstLetter());
        categorySmall.setAa(category.getId());
        categorySmall.setBb(category.getCode());
        categorySmall.setCc(category.getPid());
        categorySmall.setDd(category.getName());
        categorySmall.setFf(category.getChildrenNodes() == null ? new ArrayList<SearchProCategorySmall>() : category.getChildrenNodes());
        List<SearchProCategorySmall> resultList = new ArrayList<SearchProCategorySmall>();
        resultList.add(categorySmall);
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        logger.info("searchProCategory executeTime : " + executeTime + "ms");
        MyCompartor compartor = new MyCompartor();
        Collections.sort(resultList, compartor);
        return new ResponseEnvelope<SearchProCategorySmall>(total, resultList, msgId);
    }

    // 1、排序升序 2、name拼音首字排序
    class MyCompartor implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            ProCategory sdto1 = (ProCategory) o1;
            ProCategory sdto2 = (ProCategory) o2;
            int flag = (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
                    .compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
            if (flag != 0) {
                return (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
                        .compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
            } else {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(sdto1.getName(), sdto2.getName());
            }
        }
    }

    /**
     * 递归查询分类
     *
     * @param category
     * @return
     */
    private List<SearchProCategorySmall> recursionCategory(ProCategory category) {
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        List<ProCategory> list = null;

        if (Utils.enableRedisCache()) {
            ProCategory pc = new ProCategory();
            pc.setPid(category.getId());
            pc.setLongCode(category.getLongCode());
            list = ProCategoryCacher.getAllList(pc);
        } else {
            ProCategorySearch search = new ProCategorySearch();
            search.setPid(category.getId());
            search.setLongCode(category.getLongCode());
            list = proCategoryService.getList(search);
        }

        if (list != null && list.size() > 0) {
            if (childrenNodes == null) {
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
            SearchProCategorySmall newCategory = null;
            SearchProCategorySmall newCategory_ = null;
            for (ProCategory childrenNode : list) {
                if (ELSE.equals(childrenNode.getName())) {
                    newCategory_ = new SearchProCategorySmall();
                    newCategory_.setAa(childrenNode.getId());
                    newCategory_.setCc(childrenNode.getPid());
                    newCategory_.setBb(childrenNode.getCode());
                    newCategory_.setDd(childrenNode.getName());
                    newCategory_.setFf(recursionCategory(childrenNode));
                } else {
                    newCategory = new SearchProCategorySmall();
                    newCategory.setAa(childrenNode.getId());
                    newCategory.setCc(childrenNode.getPid());
                    newCategory.setBb(childrenNode.getCode());
                    newCategory.setDd(childrenNode.getName());
                    newCategory.setFf(recursionCategory(childrenNode));
                    childrenNodes.add(newCategory);
                }
            }
            if (newCategory_ != null) {
                childrenNodes.add(newCategory_);
            }
            category.setChildrenNodes(childrenNodes);
        }
        return childrenNodes;
    }


    /**
     * @Author: zhangchengda
     * @Description: 
     * @Date: 16:40 2018/5/11
     */
    public Object newGetDesignPlanProductList(MobileRenderingModel model) {
        Integer planId = model.getPlanId();
        String msgId = model.getMsgId();
        Integer userId = model.getUserId();
        Integer designPlanRenderSceneId = 0;
        Integer oneKeyDesignPlanId = 0;
        Integer designPlanRecommendedId = 0;
        ResponseEnvelope<ProductsCostType> envelope = new ResponseEnvelope<>();
        // 根据不同的请求 查询不同的方案来源
        if ("design".equals(msgId)) {

        } else if ("recommended".equals(msgId)) {
            designPlanRecommendedId = planId;
        } else if ("renderScene".equals(msgId)) {
            designPlanRenderSceneId = planId;
        } else if ("oneKey".equals(msgId)) {
            oneKeyDesignPlanId = planId;
        }

        if (userId.intValue() != 0) {
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            //添加平台过滤，平台id
            Integer platformId = null;
            String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
            if (StringUtils.isNotEmpty(basePlatformInfo)) {
                BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
                platformId = basePlatform.getId();
            }
            LoginUser loginUser = new LoginUser();
            loginUser.setSysUser(sysUser);
            loginUser.setId(sysUser.getId());
            loginUser.setUserType(sysUser.getUserType());
            PanoramaVo panoramaVo = new PanoramaVo();
            panoramaVo.setDesignPlanRenderSceneId(designPlanRenderSceneId);
            panoramaVo.setOneKeyDesignPlanId(oneKeyDesignPlanId);
            panoramaVo.setPlanId(planId);
            panoramaVo.setPlatformId(platformId);
            panoramaVo.setDesignPlanRecommendedId(designPlanRecommendedId);
            panoramaVo.setLoginUser(loginUser);
            List<ProductsCostType> list = panoramaService.getProductsCost(panoramaVo);
            if (list == null || list.size() <= 0) {
                envelope.setSuccess(false);
                envelope.setMessage("搜索产品清单失败");
                return envelope;
            }
            envelope.setTotalCount(list.size());
            envelope.setDatalist(list);
        } else {
            envelope.setMessage("数据异常!");
        }
        return envelope;
    }
}
