package com.sandu.service.solution.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.service.BaseProductStyleService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.solution.constant.AllotState;
import com.sandu.api.solution.constant.CopyType;
import com.sandu.api.solution.constant.PlatformType;
import com.sandu.api.solution.input.CompanyShopDesignPlanAdd;
import com.sandu.api.solution.input.DesignPlanConfig;
import com.sandu.api.solution.input.FullHouseDesignPlanQuery;
import com.sandu.api.solution.input.FullHouseDesignPlanUpdate;
import com.sandu.api.solution.model.*;
import com.sandu.api.solution.model.bo.DecoratePriceInfo;
import com.sandu.api.solution.model.bo.DesignPlanBO;
import com.sandu.api.solution.model.bo.FullHouseDesignPlanBO;
import com.sandu.api.solution.model.po.CopyShareDesignPlanPO;
import com.sandu.api.solution.model.po.DesignPlanDeliveryPO;
import com.sandu.api.solution.service.DesignPlanRecommendedService;
import com.sandu.api.solution.service.FullHouseDesignPlanService;
import com.sandu.api.solution.service.PlanDecoratePriceService;
import com.sandu.api.solution.service.ResRenderPicService;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.api.storage.service.ResFileService;
import com.sandu.constant.Constants;
import com.sandu.constant.Punctuation;
import com.sandu.exception.ServiceException;
import com.sandu.service.solution.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.solution.model.PlanDecoratePrice.PLAN_DECORATE_PRICE_FULLHOUSE;
import static com.sandu.api.solution.model.PlanDecoratePrice.PLAN_DECORATE_PRICE_RECOMMEND;
import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 18:33 2018/8/22
 */

@Slf4j
@Service("fullHouseDesignPlanService")
public class FullHouseDesignPlanServiceImpl implements FullHouseDesignPlanService {

    @Autowired
    private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;

    @Autowired
    private FullHouseDesignPlanDetailMapper fullHouseDesignPlanDetailMapper;

    @Autowired
    private ResRenderPicService resRenderPicService;

    @Autowired
    private DesignPlanCopyLogMapper designPlanCopyLogMapper;

    @Autowired
    private DesignPlan2cPlatformMapper designPlan2cPlatformMapper;

    @Autowired
    private DesignPlan2bPlatformMapper designPlan2bPlatformMapper;

    @Autowired
    private DesignPlanRecommendedMapper designPlanRecommendedMapper;

    @Autowired
    private PlanDecoratePriceMapper planDecoratePriceMapper;

    @Autowired
    private PlanDecoratePriceService planDecoratePriceService;

    @Resource
    private PlatformService platformService;

    @Resource
    private BrandService brandService;

    @Resource
    private CompanyService companyService;

    @Resource
    private BaseProductStyleService baseProductStyleService;

    @Resource
    private ResFileService resFileService;

    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Resource
    private QueueService queueService;

    @Resource
    private CompanyDesignPlanIncomeMapper companyDesignPlanIncomeMapper;

    @Resource
    private DesignPlanStoreReleaseMapper designPlanStoreReleaseMapper;

    @Resource
    private DesignPlanStoreReleaseDetailsMapper designPlanStoreReleaseDetailsMapper;

    @Override
    public PageInfo<FullHouseDesignPlanBO> selectListSelective(FullHouseDesignPlanQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<FullHouseDesignPlanBO> fullHouseBOS = fullHouseDesignPlanMapper.selectListSelective(query);
        //方案图片id
        List<Integer> picIds = fullHouseBOS.stream().map(FullHouseDesignPlanBO::getPlanPicId).collect(toList());
        //方案品牌id集
        List<String> brandIds = fullHouseBOS.stream().map(FullHouseDesignPlanBO::getBrandId).collect(toList());

        //根据图片id查询图片途径
        Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);
        //查询platform信息
        Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();
        //查询品牌id-品牌名称集合
        Map<Long, String> brandList = brandService.idAndNameMap(brandIds);
        StringBuilder brandName;
        for (FullHouseDesignPlanBO fullHouseDesignPlanBO : fullHouseBOS) {
            //设置图片
            fullHouseDesignPlanBO.setPlanPicPath(picPathMap.get(fullHouseDesignPlanBO.getPlanPicId()));
            //拆分方案品牌id
            List<String> brandIdStrs = Splitter.on(COMMA)
                    .omitEmptyStrings()
                    .trimResults()
                    .splitToList(Strings.nullToEmpty(fullHouseDesignPlanBO.getBrandId()));
            //设置对应品牌名称
            if (brandIdStrs.size() > 0 && brandIdStrs != null) {
                brandName = new StringBuilder();
                for (String str : brandIdStrs) {
                    brandName.append(brandList.get(Long.valueOf(str))).append(",");
                }
                fullHouseDesignPlanBO.setBrandName(brandName.toString().substring(0, brandName.toString().lastIndexOf(",")));
            } else {
                fullHouseDesignPlanBO.setBrandName("");
            }

            //设置平台名称
            if (!Strings.isNullOrEmpty(fullHouseDesignPlanBO.getPlatformId())) {
                log.info("==========platformIds{}:", fullHouseDesignPlanBO.getPlatformId());
                String[] platId = fullHouseDesignPlanBO.getPlatformId().split(",");
                StringBuilder sbIds = new StringBuilder();
                StringBuilder sb = new StringBuilder();
                for (String str : platId) {
                    if (!StringUtil.isEmpty(str) && platformMap.containsKey(Integer.valueOf(str))) {
                        sbIds.append(str).append(",");
                        sb.append(platformMap.get(Integer.valueOf(str))).append(",");
                    }
                }
                fullHouseDesignPlanBO.setPlatformName(sb.toString().substring(0, sb.toString().length() - 1));
            }
        }
        return new PageInfo<>(fullHouseBOS);
    }

    @Override
    public Map<String, Object> putFullHouseDesignPlan(String planId, String platformIds) {
        Map<String, Object> resultMapAll = new HashMap<>();
        Map<String, Object> resultMap;
        List<String> upPlatformId = Arrays.asList(platformIds.split(Punctuation.COMMA));
        if (upPlatformId.contains("") && upPlatformId.size() == 1) {
            upPlatformId = Collections.emptyList();
        }
        //全部下架
        if (upPlatformId.isEmpty()) {
            resultMap = upAndDownPlan(planId, Collections.emptyList(), Collections.emptyList(), Platform.PUT_STATUS_DOWN);
            resultMapAll.putAll(resultMap);
        } else {
            //去重
            upPlatformId = upPlatformId.stream().distinct().filter(platformId -> StringUtils.isNoneBlank(platformId)).collect(Collectors.toList());
            //获取所有B端的数据
            List<String> plat2bPlats = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList(PlatformType.TO_B.getCode())).stream().map(key -> String.valueOf(key)).collect(Collectors.toList());
            //获取所有C端的数据
            List<String> plat2cPlats = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList(PlatformType.TO_C.getCode())).stream().map(key -> String.valueOf(key)).collect(Collectors.toList());
            //取需要上架到B端的平台
            List<String> tobPlatformIds;
            //取需要上架到C端的平台
            List<String> tocPlatformIds;
            //过滤本次需要上架的2b和2c平台
            Map<String, Object> platFormMap = getUpDownPlatForms(plat2bPlats, plat2cPlats, upPlatformId, Platform.PUT_STATUS_UP);
            tobPlatformIds = platFormMap.get(PlatformType.TO_B.getCode()) == null ? Collections.emptyList() : (List<String>) platFormMap.get(PlatformType.TO_B.getCode());
            tocPlatformIds = platFormMap.get(PlatformType.TO_C.getCode()) == null ? Collections.emptyList() : (List<String>) platFormMap.get(PlatformType.TO_C.getCode());
            List<Integer> tocPlats = new ArrayList<>(tocPlatformIds.size());
            List<Integer> tobPlats = new ArrayList<>(tobPlatformIds.size());
            for (String toc : tocPlatformIds) {
                tocPlats.add(Integer.valueOf(toc));
            }
            for (String tob : tobPlatformIds) {
                tobPlats.add(Integer.valueOf(tob));
            }
            //处理上架
            if (!tocPlatformIds.isEmpty() || !tobPlatformIds.isEmpty()) {
                resultMap = upAndDownPlan(planId, tocPlats, tobPlats, Platform.PUT_STATUS_UP);
                resultMapAll.putAll(resultMap);
            }
            tobPlatformIds.clear();
            tocPlatformIds.clear();
            platFormMap.clear();
            //过滤本次需要下架的2b和2c平台
            platFormMap = getUpDownPlatForms(plat2bPlats, plat2cPlats, upPlatformId, Platform.PUT_STATUS_DOWN);
            tobPlatformIds = platFormMap.get(PlatformType.TO_B.getCode()) == null ? Collections.emptyList() : (List<String>) platFormMap.get(PlatformType.TO_B.getCode());
            tocPlatformIds = platFormMap.get(PlatformType.TO_C.getCode()) == null ? Collections.emptyList() : (List<String>) platFormMap.get(PlatformType.TO_C.getCode());
            List<Integer> toDowncPlats = new ArrayList<>(tocPlatformIds.size());
            List<Integer> toDownbPlats = new ArrayList<>(tobPlatformIds.size());
            for (String toc : tocPlatformIds) {
                toDowncPlats.add(Integer.valueOf(toc));
            }
            for (String tob : tobPlatformIds) {
                toDownbPlats.add(Integer.valueOf(tob));
            }
            //处理下架
            if (!tocPlatformIds.isEmpty() || !tobPlatformIds.isEmpty()) {
                resultMap = upAndDownPlan(planId, toDowncPlats, toDownbPlats, Platform.PUT_STATUS_DOWN);
                resultMapAll.putAll(resultMap);
            }
        }
        return resultMapAll;
    }

    /**
     * @param tocPlatForms B端平台
     * @param tocPlatForms c端平台
     * @param upPlatformId 页面传入的本次需要上下架的平台
     * @return
     */
    private Map<String, Object> getUpDownPlatForms(List<String> tobPlatForms, List<String> tocPlatForms, List<String> upPlatformId, Integer operateType) {
        HashMap<String, Object> platFormMap = new HashMap<>();
        //需要上下架B端的平台
        List<String> tobPlatformIds = new ArrayList<>();
        //需要上下架C端的平台
        List<String> tocPlatformIds = new ArrayList<>();
        if (Platform.PUT_STATUS_UP.equals(operateType)) {
            //取需要上架到B端的平台,取交集
            tobPlatformIds.addAll(upPlatformId);
            tobPlatformIds.retainAll(tobPlatForms);

            //取需要上架到c端的平台,取交集
            tocPlatformIds.addAll(upPlatformId);
            tocPlatformIds.retainAll(tocPlatForms);
        }

        if (Platform.PUT_STATUS_DOWN.equals(operateType)) {
            tocPlatForms.addAll(tobPlatForms);
            List<String> allPutPlatForms = new ArrayList<>();
            allPutPlatForms.addAll(tocPlatForms);
            //取当前需要下架的平台ID
            allPutPlatForms.removeAll(upPlatformId);
            //从C端移除B端平台
            for (Iterator<String> it = tocPlatForms.iterator(); it.hasNext(); ) {
                String platFormId = it.next();
                if (tobPlatForms.contains(platFormId)) {
                    it.remove();
                }
            }
            // 取消分配并下架
            if (!allPutPlatForms.isEmpty()) {
                tocPlatformIds.clear();
                tocPlatForms.forEach(k -> {
                    if (allPutPlatForms.contains(String.valueOf(k))) {
                        tocPlatformIds.add(String.valueOf(k));
                    }
                });
                tobPlatformIds.clear();
                tobPlatForms.forEach(k -> {
                    if (allPutPlatForms.contains(String.valueOf(k))) {
                        tobPlatformIds.add(String.valueOf(k));
                    }
                });
            }
        }
        platFormMap.put(PlatformType.TO_C.getCode(), tocPlatformIds);
        platFormMap.put(PlatformType.TO_B.getCode(), tobPlatformIds);
        return platFormMap;
    }

    /**
     * 全屋方案上下架
     *
     * @param planId
     * @param tocPlatformIds
     * @param tobPlatformIds
     * @param putStatusUp
     * @return
     */
    private Map<String, Object> upAndDownPlan(String planId, List<Integer> tocPlatformIds, List<Integer> tobPlatformIds, Integer putStatusUp) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        log.info("planId:{},tocPlatformIds:{},tobPlatformIds:{},putStatusUp:{},designPlanType:{}", planId, tocPlatformIds, tobPlatformIds, putStatusUp);
        if (StringUtils.isBlank(planId)) {
            log.error("upAndDownPlan上架失败,失败原因:planId参数为空");
            resultMap.put("1", "planId参数为空!");
            return resultMap;
        }
        FullHouseDesignPlan fullHouseDesignPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(Integer.valueOf(planId));
        if (fullHouseDesignPlan == null) {
            log.error("upAndDownPlan上架失败,失败原因:planId:{}没有找到方案信息!", planId);
            resultMap.put("1", "planId没有找到方案信息");
            return resultMap;
        }


        if (Platform.PUT_STATUS_UP.equals(putStatusUp)) {
            System.out.println("####################上架" + putStatusUp);
            //处理B端上架
            if (!tobPlatformIds.isEmpty()) {
                handleShelf(fullHouseDesignPlan.getId(), tobPlatformIds, "2b");
            }
            //处理C端上架
            if (!tocPlatformIds.isEmpty()) {
                handleShelf(fullHouseDesignPlan.getId(), tocPlatformIds, "2c");
            }
        } else if (Platform.PUT_STATUS_DOWN.equals(putStatusUp)) {
            System.out.println("####################下架" + putStatusUp);
            //处理B端下架
            if (!tobPlatformIds.isEmpty()) {
                if (tobPlatformIds.size() > 0) {
                    offShelf(fullHouseDesignPlan.getId(), tobPlatformIds, "2b");
                }
            }

            //处理C端下架
            if (!tocPlatformIds.isEmpty()) {
                if (tocPlatformIds.size() > 0) {
                    offShelf(fullHouseDesignPlan.getId(), tocPlatformIds, "2c");
                }
            }

            //全部下架
            if (tocPlatformIds.isEmpty() && tobPlatformIds.isEmpty()) {
                handleDownTo2B(Integer.valueOf(planId), Collections.emptyList());
                handleDownTo2C(Integer.valueOf(planId), Collections.emptyList());
            }
            //获取分享后变成的方案
            List<Integer> planIdList = designPlanCopyLogMapper.listDeliveredPlanIds(Integer.valueOf(planId), CopyType.SHARE.getCode(), 2);
            //删除这些方案
            planIdList.forEach(fullHouseDesignPlanMapper::deleteByPrimaryKey);
            //清除记录
            designPlanCopyLogMapper.deleteBySourceId(Integer.valueOf(planId), CopyType.SHARE.getCode(), 2);

        }
        resultMap.put("0", "操作成功!");
        return resultMap;
    }

    /**
     * 全屋方案上架
     *
     * @param planId
     * @param toPlatformIds
     */
    private void handleShelf(Integer planId, List<Integer> toPlatformIds, String type) {
        if ("2b".equals(type)) {
            toPlatformIds.forEach(platformId -> {
                handle2bPutOnShelf(planId, platformId);
            });
        } else {
            toPlatformIds.forEach(platformId -> {
                handle2cPutOnShelf(planId, platformId);
            });
        }
    }

    /**
     * 上架2c端
     *
     * @param planId
     * @param platformId
     */
    private void handle2cPutOnShelf(Integer planId, Integer platformId) {
        log.info("============上架c端{}", platformId);
        if (designPlan2cPlatformMapper.selectByPlanIdAndFormId(planId, platformId, 3).size() == 0) {
            FullHouseDesignPlan designPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
            DesignPlan2cPlatform designPlan2c = new DesignPlan2cPlatform();
            designPlan2c.setPlanId(planId);
            designPlan2c.setPlatformId(platformId);
            if (null != designPlan) {

                designPlan2c.setCreator(designPlan.getCreator());
                designPlan2c.setUserId(designPlan.getUserId());
                designPlan2c.setModifier(designPlan.getModifier());
            }
            designPlan2c.setPutawayState(1);
            designPlan2c.setDesignPlanType(3);
            designPlan2c.setAllotState(1);
            designPlan2c.setIsDeleted(0);
            designPlan2c.setGmtCreate(new Date());
            designPlan2c.setGmtModified(new Date());
            designPlan2cPlatformMapper.insertSelective(designPlan2c);
        } else {
            designPlanRecommendedMapper.putOn2cFullHouse(planId, platformId);
        }
    }

    /**
     * 上架2b端
     *
     * @param planId
     * @param platformId
     */
    private void handle2bPutOnShelf(Integer planId, Integer platformId) {
        log.info("============上架b端{}", platformId);
        if (designPlan2bPlatformMapper.selectByPlanIdAndFormId(planId, platformId, 3).size() == 0) {
            FullHouseDesignPlan designPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
            DesignPlan2bPlatform designPlan2b = new DesignPlan2bPlatform();
            designPlan2b.setPlanId(planId);
            designPlan2b.setPlatformId(platformId);
            if (null != designPlan) {

                designPlan2b.setCreator(designPlan.getCreator());
                designPlan2b.setUserId(designPlan.getUserId());
                designPlan2b.setModifier(designPlan.getModifier());
            }
            designPlan2b.setPutawayState(1);
            designPlan2b.setDesignPlanType(3);
            designPlan2b.setAllotState(1);
            designPlan2b.setIsDeleted(0);
            designPlan2b.setGmtCreate(new Date());
            designPlan2b.setGmtModified(new Date());
            designPlan2bPlatformMapper.insertSelective(designPlan2b);
        } else {
            designPlanRecommendedMapper.putOn2bFullHouse(planId, platformId);
        }
    }

    /**
     * 全屋方案下架
     *
     * @param id
     * @param PlatformIds
     */
    private void offShelf(Integer id, List<Integer> PlatformIds, String type) {
        if ("2c".equals(type)) {
            handleDownTo2C(id, PlatformIds);
        } else {
            handleDownTo2B(id, PlatformIds);
        }
    }

    /**
     * 下架2b端
     *
     * @param planId
     * @param platformIds
     */
    private void handleDownTo2B(Integer planId, List<Integer> platformIds) {
        log.info("============下架b端{}", platformIds);
        if (platformIds != null && platformIds.size() > 0) {
            List<DesignPlan2bPlatform> toList = new ArrayList<>();
            platformIds.forEach(plat -> {
                        if (designPlan2bPlatformMapper.selectByPlanIdAndFormId(planId, plat, 3).size() == 0) {
                            FullHouseDesignPlan designPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
                            DesignPlan2bPlatform designPlan2b = new DesignPlan2bPlatform();
                            designPlan2b.setPlanId(planId.intValue());
                            designPlan2b.setPlatformId(Integer.valueOf(plat));
                            if (null != designPlan) {
                                designPlan2b.setCreator(designPlan.getCreator());
                                designPlan2b.setUserId(designPlan.getUserId());
                                designPlan2b.setModifier(designPlan.getModifier());
                            }
                            designPlan2b.setPutawayState(0);
                            designPlan2b.setDesignPlanType(3);
                            designPlan2b.setAllotState(1);
                            designPlan2b.setIsDeleted(0);
                            designPlan2b.setGmtCreate(new Date());
                            designPlan2b.setGmtModified(new Date());
                            toList.add(designPlan2b);
                        } else {
                            designPlanRecommendedMapper.offShelf2bFullHouse(planId.longValue(), plat);
                        }
                    }
            );
            if (toList != null && toList.size() > 0) {
                designPlan2bPlatformMapper.insertRelListIfNotExist(toList);
            }
        } else {
            List<DesignPlan2bPlatform> designPlan2bPlatforms = designPlan2bPlatformMapper.listByPlanIdAndType(planId.longValue(), 3);
            //未分配
            if (isNotEmpty(designPlan2bPlatforms)) {
                designPlan2bPlatforms.forEach(
                        designPlan2bPlatform -> {
                            designPlan2bPlatform.setPutawayState(AllotState.N.getCode());
                            designPlan2bPlatformMapper.updateByPrimaryKeySelective(designPlan2bPlatform);
                        }
                );
            }
        }
    }

    /**
     * 下架2c端
     *
     * @param planId
     * @param tobPlatformIds
     */
    private void handleDownTo2C(Integer planId, List<Integer> tobPlatformIds) {
        log.info("============下架c端{}", tobPlatformIds);
        if (tobPlatformIds != null && tobPlatformIds.size() > 0) {
            List<DesignPlan2cPlatform> toList = new ArrayList<>();
            tobPlatformIds.forEach(plat -> {
                        if (designPlan2cPlatformMapper.selectByPlanIdAndFormId(planId, plat, 3).size() == 0) {
                            FullHouseDesignPlan designPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
                            DesignPlan2cPlatform designPlan2c = new DesignPlan2cPlatform();
                            designPlan2c.setPlanId(planId);
                            designPlan2c.setPlatformId(Integer.valueOf(plat));
                            if (null != designPlan) {
                                designPlan2c.setCreator(designPlan.getCreator());
                                designPlan2c.setUserId(designPlan.getUserId());
                                designPlan2c.setModifier(designPlan.getModifier());
                            }
                            designPlan2c.setPutawayState(0);
                            designPlan2c.setDesignPlanType(3);
                            designPlan2c.setAllotState(1);
                            designPlan2c.setIsDeleted(0);
                            designPlan2c.setGmtCreate(new Date());
                            designPlan2c.setGmtModified(new Date());
                            toList.add(designPlan2c);
                        } else {
                            designPlanRecommendedMapper.offShelf2cFullHouse(planId.longValue(), plat);
                        }
                    }
            );
            if (toList != null && toList.size() > 0) {
                designPlan2cPlatformMapper.insertRelListIfNotExist(toList);
            }
        } else {
            List<DesignPlan2cPlatform> designPlan2cPlatforms = designPlan2cPlatformMapper.listByPlanIdAndType(planId.longValue(), 3);
            if (isNotEmpty(designPlan2cPlatforms)) {
                designPlan2cPlatforms.forEach(
                        designPlan2cPlatform -> {
                            designPlan2cPlatform.setPutawayState(AllotState.N.getCode());
                            designPlan2cPlatformMapper.updateByPrimaryKeySelective(designPlan2cPlatform);
                        }
                );
            }
        }
    }


    @Override
    public FullHouseDesignPlanBO getBaseInfo(Integer planId, Integer companyId) {
        List<FullHouseDesignPlanBO> results = fullHouseDesignPlanMapper.getBaseInfo(planId);
        //方案图片id
        List<Integer> picIds = results.stream().map(FullHouseDesignPlanBO::getPlanPicId).collect(toList());
        //根据图片id查询图片途径
        Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);
        //设置图片
        results.get(0).setPlanPicPath(picPathMap.get(results.get(0).getPlanPicId()));
        //查询platform信息
        Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();

        //查询方案品牌id对应的名称
        List<String> brandIdStrs = Splitter.on(COMMA)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(Strings.nullToEmpty(results.get(0).getBrandId()));
        //设置对应品牌名称
        if (brandIdStrs.size() > 0 && brandIdStrs != null) {
            results.get(0).setBrandName(brandService.getBrandNamesByIds(brandIdStrs));
        } else {
            results.get(0).setBrandName("");
        }

        //设置平台名称
        if (!Strings.isNullOrEmpty(results.get(0).getPlatformId())) {
            log.info("==========platformIds{}:", results.get(0).getPlatformId());
            String[] platId = results.get(0).getPlatformId().split(",");
            StringBuilder sbIds = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            for (String str : platId) {
                if (!StringUtil.isEmpty(str) && platformMap.containsKey(Integer.valueOf(str))) {
                    sbIds.append(str).append(",");
                    sb.append(platformMap.get(Integer.valueOf(str))).append(",");
                }
            }
            results.get(0).setPlatformName(sb.toString().substring(0, sb.toString().length() - 1));
        }
        if (results != null && results.size() > 0) {
            FullHouseDesignPlanBO result = results.get(0);
            result.setPlanPicPath(resRenderPicService.getPathById(results.get(0).getPlanPicId()));
            //设置方案报价信息
            List<DecoratePriceInfo> prices = designPlanRecommendedService.showPlanDecoratePriceInfo(result.getId().longValue(), PLAN_DECORATE_PRICE_FULLHOUSE);
            result.setDecoratePriceInfoList(prices);
            return results.get(0);
        }
        return null;
    }

    @Override
    public int updateFullHouseDetail(FullHouseDesignPlanUpdate designPlanUpdate) {
        FullHouseDesignPlan plan = new FullHouseDesignPlan();
        plan.setId(designPlanUpdate.getId());
        plan.setCompanyId(designPlanUpdate.getCompanyId());
        plan.setModifier(designPlanUpdate.getModifier().toString());
        plan.setGmtModified(new Date());
        plan.setPlanName(designPlanUpdate.getPlanName());
        plan.setPlanDescribe(designPlanUpdate.getPlanDescribe());
        plan.setPlanStyleId(designPlanUpdate.getPlanStyleId());
        plan.setPlanStyleName(designPlanUpdate.getPlanStyleValue());
        plan.setRemark(designPlanUpdate.getRemark());
        plan.setBrandId(designPlanUpdate.getBrandId());
        plan.setIsChanged(1);

        designPlanRecommendedService.setPlanDecoratePrice(designPlanUpdate.getId(), designPlanUpdate.getDecoratePriceInfoList(), PLAN_DECORATE_PRICE_FULLHOUSE)
                .forEach(planDecoratePriceService::updateByPlanRecommendId);
        return fullHouseDesignPlanMapper.updateByPrimaryKeySelective(plan);
    }

    @Override
    public int deletePlanById(Long planId) {
        return fullHouseDesignPlanMapper.deleteByPrimaryKey(planId.intValue());
    }

    @Override
    public FullHouseDesignPlan getFullHousePlanById(Integer planId) {
        return fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
    }

    @Override
    public List<Integer> deliver(DesignPlanDeliveryPO designPlanDeliveryPO, boolean isBatch) {
        //根据交付公司id和方案id查询 该方案已经交付过的公司id集合
        List<Integer> deliveredCompanyIds =
                designPlanCopyLogMapper.listCopiedCompanys(designPlanDeliveryPO.getDeliveryCompanyId(),
                        designPlanDeliveryPO.getDesignPlanId(), CopyType.DELIVER.getCode(), 2);
        //判断是否批量交付（批量交付不能取消）
        if (!isBatch) {
            //取消交付公司
            if (designPlanDeliveryPO.getReceiveBrandIds().size() == 0) {
                deliveredCompanyIds.forEach(deliveredCId -> {
                    if (!designPlanDeliveryPO.getReceiveCompanyIds().contains(deliveredCId)) {
                        // 1. 获取交付后变成的方案
                        List<Integer> planIds =
                                designPlanCopyLogMapper.listDeliveredPlanId(deliveredCId,
                                        designPlanDeliveryPO.getDesignPlanId(), CopyType.DELIVER.getCode(), 2);
                        // 2. 删除该方案
                        // 3. 删除该方案详情
                        planIds.forEach(id -> {
                            fullHouseDesignPlanMapper.deleteByPrimaryKey(id);
                            fullHouseDesignPlanDetailMapper.deleteByFullHousePlanId(id);
                            planDecoratePriceMapper.deleteByFullHouseId(id);
                        });
                        // 4. 删除记录中间表
                        designPlanCopyLogMapper.deleteByTargetCompanyAndSource(deliveredCId,
                                designPlanDeliveryPO.getDesignPlanId(), CopyType.DELIVER.getCode(), 2);

                        //同步到es
                        sycMessageDoSend(SyncMessage.ACTION_DELETE, planIds);
                    }
                });
            }
        }
        //根据品牌进行二次交付 (该方案已交付给该公司，第二次按该公司的品牌再次交付)
        if (designPlanDeliveryPO.getReceiveBrandIds() != null && designPlanDeliveryPO.getReceiveBrandIds().size() > 0) {

            if (designPlanDeliveryPO.getReceiveBrandIds().size() > 0) {
                deliverAgain(designPlanDeliveryPO);
            }
        } else {
            //根据企业进行二次交付 (该方案已交付给该公司，第二次按该公司再次交付)
            deliverAgain(designPlanDeliveryPO);
        }
        return designPlanDeliveryPO.getReceiveCompanyIds().stream().filter(id -> {
            log.debug("=========companyId{}", deliveredCompanyIds);
            return !deliveredCompanyIds.contains(id);
        }).map(companyId -> {
                    //复制方案
                    FullHouseDesignPlan tempDesignPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(designPlanDeliveryPO.getDesignPlanId());
                    Integer oldPlanId = tempDesignPlan.getId();
                    String oldUUID = tempDesignPlan.getVrResourceUuid();
                    tempDesignPlan.setGmtCreate(new Date());
                    tempDesignPlan.setGmtModified(new Date());
                    //3交付
                    tempDesignPlan.setSourceType(3);
                    tempDesignPlan.setCompanyId(companyId);
                    tempDesignPlan.setOpenState(0);
                    tempDesignPlan.setUuid(UUID.randomUUID().toString());
                    tempDesignPlan.setPlanCode(generateCode(System.currentTimeMillis()));
                    tempDesignPlan.setSourcePlanId(tempDesignPlan.getId());
                    tempDesignPlan.setId(null);
                    StringBuffer sb = new StringBuffer();
                    if (designPlanDeliveryPO.getReceiveBrandIds().size() > 0 && designPlanDeliveryPO.getReceiveBrandIds() != null) {
                        for (Integer id : designPlanDeliveryPO.getReceiveBrandIds()) {
                            sb.append(id).append(",");
                        }
                        tempDesignPlan.setBrandId(sb.toString().substring(0, sb.lastIndexOf(",")));
                    } else {
                        List<Brand> brands = brandService.getBrandByCompanyId(companyId);
                        for (Brand brand : brands) {
                            sb.append(brand.getId()).append(",");
                        }
                        tempDesignPlan.setBrandId(sb.toString().isEmpty() ? null : sb.toString().substring(0, sb.lastIndexOf(",")));
                    }
                    fullHouseDesignPlanMapper.insertSelective(tempDesignPlan);

                    //同步到es
                    List<Integer> planIds = new ArrayList<>();
                    planIds.add(tempDesignPlan.getId());
                    sycMessageDoSend(SyncMessage.ACTION_ADD, planIds);

                    //复制详情
                    copyPlanDetail(oldPlanId, tempDesignPlan.getId());
                    //复制store_release
                    copyStoreRelease(oldUUID, tempDesignPlan.getId());

                    DesignPlanCopyLog designPlanCopyLog = new DesignPlanCopyLog();
                    designPlanCopyLog.setGmtCreate(new Date());
                    designPlanCopyLog.setKind(CopyType.DELIVER.getCode());
                    designPlanCopyLog.setSourceId(designPlanDeliveryPO.getDesignPlanId());
                    designPlanCopyLog.setTargetId(tempDesignPlan.getId().intValue());
                    designPlanCopyLog.setTargetCompanyId(companyId);
                    designPlanCopyLog.setSourceCompanyId(designPlanDeliveryPO.getDeliveryCompanyId());
                    designPlanCopyLog.setTargetKind((byte) 3);
                    designPlanCopyLog.setPlanType(2);
                    designPlanCopyLogMapper.insertSelective(designPlanCopyLog);
                    log.info("##############交付方案{}结束###########################", designPlanDeliveryPO.getDesignPlanId());
                    return tempDesignPlan.getId().intValue();
                }
        ).collect(toList());
    }

    /**
     * 复制store_release
     * @param oldUUID
     * @param newPlanId
     */
    private void copyStoreRelease(String oldUUID, Integer newPlanId) {
        DesignPlanStoreRelease storeRelease = designPlanStoreReleaseMapper.selectByVRUUID(oldUUID);
        //原来店铺发布id
        Long initId = storeRelease.getId();
        storeRelease.setId(null);
        storeRelease.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        //新店铺发布
        designPlanStoreReleaseMapper.insertSelective(storeRelease);
        Integer storeReleaseId = storeRelease.getId().intValue();

        //修改原fullhouse表resourceUUID
        FullHouseDesignPlan fullHouseDesignPlan = new FullHouseDesignPlan();
        fullHouseDesignPlan.setId(newPlanId);
        fullHouseDesignPlan.setVrResourceUuid(storeRelease.getUuid());
        fullHouseDesignPlanMapper.updateByPrimaryKeySelective(fullHouseDesignPlan);

        List<DesignPlanStoreReleaseDetails> designPlanStoreReleaseDetails = designPlanStoreReleaseDetailsMapper.selectByStoreId(initId);
        if(designPlanStoreReleaseDetails != null && designPlanStoreReleaseDetails.size()>0) {
            for(DesignPlanStoreReleaseDetails details : designPlanStoreReleaseDetails) {
                details.setId(null);
                details.setStoreReleaseId(storeReleaseId);
                designPlanStoreReleaseDetailsMapper.insertSelective(details);
            }
        }

    }

    /**
     * 复制详情
     *
     * @param oldPlanId
     */
    private void copyPlanDetail(Integer oldPlanId, Integer newPlanId) {
        //获取原方案详情
        List<FullHouseDesignPlanDetail> designPlanDetails = fullHouseDesignPlanDetailMapper.selectByPlanId(oldPlanId);
        //复制详情
        for (FullHouseDesignPlanDetail designPlanDetail : designPlanDetails) {
            designPlanDetail.setUuid(UUID.randomUUID().toString());
            designPlanDetail.setFullHousePlanId(newPlanId);
            designPlanDetail.setId(null);
            fullHouseDesignPlanDetailMapper.insertSelective(designPlanDetail);
        }
        //获取原报价的信息
        List<PlanDecoratePrice> planDecoratePrices = planDecoratePriceMapper.selectByFullHouseId(oldPlanId);
        //复制报价信息
        for (PlanDecoratePrice planDecoratePrice : planDecoratePrices) {
            planDecoratePrice.setId(null);
            planDecoratePrice.setFullHouseId(newPlanId);
            planDecoratePriceMapper.insertSelective(planDecoratePrice);
        }
    }

    /**
     * 生成方案编码,由前缀FH + 日期 + 时间 + 4位随机数组成
     *
     * @param salt 用来生成随机编码的盐值
     * @return 方案编码
     */
    private String generateCode(long salt) {
        // 日期、时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        LocalDateTime date = LocalDateTime.now();
        // 4位随机数
        Random random = new Random(salt);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int nextInt = random.nextInt(10);
            sb.append(nextInt);
        }
        // 返回生成的编码
        return "FH_" + formatter.format(date) + sb.toString();
    }


    /**
     * 按品牌或者企业二次交付
     *
     * @param fullHousePlanDelivery
     */
    private void deliverAgain(DesignPlanDeliveryPO fullHousePlanDelivery) {
        //遍历被交付的企业
        for (Integer receive : fullHousePlanDelivery.getReceiveCompanyIds()) {
            DesignPlanCopyLog query = new DesignPlanCopyLog();
            query.setSourceId(fullHousePlanDelivery.getDesignPlanId());
            query.setSourceCompanyId(fullHousePlanDelivery.getDeliveryCompanyId());
            query.setTargetCompanyId(receive);
            query.setPlanType(2);
            //查询该企业是否有被交付的记录
            List<DesignPlanCopyLog> logs = designPlanCopyLogMapper.queryLog(query);
            if (logs.size() > 0) {
                //公司对应的品牌id集
                List<Integer> brandIds = new ArrayList<>();
                //按公司交付
                if (fullHousePlanDelivery.getReceiveBrandIds().size() == 0 ||
                        fullHousePlanDelivery.getReceiveBrandIds() == null) {
                    log.info("=============根据公司id查询该公司对应的品牌id=============");
                    List<Brand> brands = brandService.getBrandByCompanyId(receive);
                    for (Brand brand : brands) {
                        brandIds.add(brand.getId().intValue());
                    }
                } else {
                    //按品牌交付
                    for (Integer id : fullHousePlanDelivery.getReceiveBrandIds()) {
                        log.info("=============匹配品牌对应的企业=============");
                        Brand brand = brandService.getBrandById(id);
                        if (receive.equals(brand.getCompanyId())) {
                            brandIds.add(brand.getId().intValue());
                        }
                    }
                }

                //更新全屋方案表的brandIds字段
                String brandId = Joiner.on(",").join(brandIds);
                FullHouseDesignPlan update = new FullHouseDesignPlan();
                update.setId(logs.get(0).getTargetId());
                update.setBrandId(brandId);
                fullHouseDesignPlanMapper.updateByPrimaryKeySelective(update);
            }
        }
    }

    @Override
    public boolean batchChangePlanSecrecy(List<Integer> planIds, Integer secrecy) {
        List<Integer> updateIds = new ArrayList<>();
        planIds.forEach(planId -> {
            FullHouseDesignPlan designPlanRecommended = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
            if (designPlanRecommended != null) {
                updateIds.add(planId);
            }
        });
        if (Optional.ofNullable(updateIds).isPresent()) {
            fullHouseDesignPlanMapper.batchUpdatePlanSecrecy(updateIds, secrecy);
        }
        return true;
    }

    @Override
    public PageInfo<FullHouseDesignPlanBO> shareDesignPlan(FullHouseDesignPlanQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<FullHouseDesignPlanBO> designPlanBOS = fullHouseDesignPlanMapper.listShareDesignPlan(query);
        //方案图片id
        List<Integer> picIds = designPlanBOS.stream().map(FullHouseDesignPlanBO::getPlanPicId).collect(toList());

        //根据图片id查询图片途径
        Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);
        //查询公司名称
        List<Integer> companyIdList = designPlanBOS.stream().map(FullHouseDesignPlanBO::getCompanyId).collect(toList());
        Map<Long, String> companyMap = companyService.idAndNameMap(companyIdList);
        //查询要收费的方案
        Set<Integer> planIds = designPlanBOS.stream().filter(plan -> Objects.equals(1, plan.getSalePriceChargeType())).collect(Collectors.toList()).stream().map(FullHouseDesignPlanBO::getId).collect(toSet());
        Set<Integer> designPlanIds = null;
        if (planIds != null && !planIds.isEmpty()){
          designPlanIds = companyDesignPlanIncomeMapper.isExitsUserBuySaleDesignPlan(planIds,query.getUserId(),0);
        }
        for (FullHouseDesignPlanBO fullHouse : designPlanBOS) {
            //设置图片
            fullHouse.setPlanPicPath(picPathMap.get(fullHouse.getPlanPicId()));
            //设置公司名称
            fullHouse.setCompanyName(companyMap.get(fullHouse.getCompanyId().longValue()));
            if (planIds != null && !planIds.isEmpty()){
                //校验用户是否已经购买了方案售卖
                if (designPlanIds.contains(fullHouse.getId())){
                    fullHouse.setSalePriceChargeType(0);
                }
            }
        }
        return new PageInfo<>(designPlanBOS);
    }

    @Override
    public Integer copyDesignPlanToCompany2(CopyShareDesignPlanPO planPO) {
        Integer newPlanId = null;

        for (Integer planId : planPO.getSourceDesignPlanIds()) {
            DesignPlanCopyLog designPlanCopyLog = new DesignPlanCopyLog();
            FullHouseDesignPlan tempDesignPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
            Integer oldPlanId = tempDesignPlan.getId();
            String oldUUID = tempDesignPlan.getVrResourceUuid();
            designPlanCopyLog.setSourceCompanyId(tempDesignPlan.getCompanyId());
            tempDesignPlan.setGmtCreate(new Date());
            //4分享
            tempDesignPlan.setSourceType(4);
            tempDesignPlan.setGmtModified(new Date());
            tempDesignPlan.setOpenState(0);
            tempDesignPlan.setUuid(UUID.randomUUID().toString());
            tempDesignPlan.setPlanCode(generateCode(System.currentTimeMillis()));
            tempDesignPlan.setSourcePlanId(tempDesignPlan.getId());
            tempDesignPlan.setId(null);
            tempDesignPlan.setCompanyId(planPO.getTargetCompanyId());
            StringBuffer sb = new StringBuffer();
            List<Brand> brands = brandService.getBrandByCompanyId(planPO.getTargetCompanyId());
            for (Brand brand : brands) {
                sb.append(brand.getId()).append(",");
            }
            tempDesignPlan.setBrandId(sb.toString().isEmpty() ? null : sb.toString().substring(0, sb.lastIndexOf(",")));
            // 是否改变过：0 -> 否；1 -> 是
            tempDesignPlan.setIsChanged(0);

            fullHouseDesignPlanMapper.insertSelective(tempDesignPlan);
            //返回给前端
            if (newPlanId == null) newPlanId = tempDesignPlan.getId();

            //同步到es
            List<Integer> planIds = new ArrayList<>();
            planIds.add(tempDesignPlan.getId());
            sycMessageDoSend(SyncMessage.ACTION_ADD, planIds);

            //复制详情
            copyPlanDetail(oldPlanId, tempDesignPlan.getId());
            //复制store_release
            copyStoreRelease(oldUUID, tempDesignPlan.getId());

            designPlanCopyLog.setGmtCreate(new Date());
            designPlanCopyLog.setKind(CopyType.SHARE.getCode());
            designPlanCopyLog.setSourceId(planId);
            designPlanCopyLog.setTargetId(tempDesignPlan.getId().intValue());
            designPlanCopyLog.setTargetCompanyId(planPO.getTargetCompanyId());
            designPlanCopyLog.setTargetKind((byte) 3);
            designPlanCopyLog.setPlanType(2);
            designPlanCopyLogMapper.insertSelective(designPlanCopyLog);
            log.info("##############分享方案{}结束###########################", planId);
        }

        return newPlanId;
    }

    @Override
    public void copyDesignPlanToCompany(CopyShareDesignPlanPO copyShareDesignPlanPO) {
        copyShareDesignPlanPO.getSourceDesignPlanIds().forEach(
                planId -> {
                    DesignPlanCopyLog designPlanCopyLog = new DesignPlanCopyLog();
                    FullHouseDesignPlan tempDesignPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(planId);
                    Integer oldPlanId = tempDesignPlan.getId();
                    designPlanCopyLog.setSourceCompanyId(tempDesignPlan.getCompanyId());
                    tempDesignPlan.setGmtCreate(new Date());
                    //4分享
                    tempDesignPlan.setSourceType(4);
                    tempDesignPlan.setGmtModified(new Date());
                    tempDesignPlan.setOpenState(0);
                    tempDesignPlan.setUuid(UUID.randomUUID().toString());
                    tempDesignPlan.setPlanCode(generateCode(System.currentTimeMillis()));
                    tempDesignPlan.setSourcePlanId(tempDesignPlan.getId());
                    tempDesignPlan.setId(null);
                    tempDesignPlan.setCompanyId(copyShareDesignPlanPO.getTargetCompanyId());
                    StringBuffer sb = new StringBuffer();
                    List<Brand> brands = brandService.getBrandByCompanyId(copyShareDesignPlanPO.getTargetCompanyId());
                    for (Brand brand : brands) {
                        sb.append(brand.getId()).append(",");
                    }
                    tempDesignPlan.setBrandId(sb.toString().isEmpty() ? null : sb.toString().substring(0, sb.lastIndexOf(",")));

                    fullHouseDesignPlanMapper.insertSelective(tempDesignPlan);
                    //同步到es
                    List<Integer> planIds = new ArrayList<>();
                    planIds.add(tempDesignPlan.getId());
                    sycMessageDoSend(SyncMessage.ACTION_ADD, planIds);

                    //复制详情
                    copyPlanDetail(oldPlanId, tempDesignPlan.getId());

                    designPlanCopyLog.setGmtCreate(new Date());
                    designPlanCopyLog.setKind(CopyType.SHARE.getCode());
                    designPlanCopyLog.setSourceId(planId);
                    designPlanCopyLog.setTargetId(tempDesignPlan.getId().intValue());
                    designPlanCopyLog.setTargetCompanyId(copyShareDesignPlanPO.getTargetCompanyId());
                    designPlanCopyLog.setTargetKind((byte) 3);
                    designPlanCopyLog.setPlanType(2);
                    designPlanCopyLogMapper.insertSelective(designPlanCopyLog);
                    log.info("##############分享方案{}结束###########################", planId);
                }
        );
    }

    @Override
    public List<BaseProductStyle> styleList() {
        return baseProductStyleService.listBasePlanStyleIdAndName(13);
    }

    @Override
    public void configDesignPlan(DesignPlanConfig config) {
        FullHouseDesignPlan primaryPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(config.getId());
        if (Objects.isNull(primaryPlan)) {
            throw new RuntimeException("获取方案详情异常");
        }
        Long fileId = resFileService.updateFile(primaryPlan.getDescFileId(), config.getContent());
        if (primaryPlan.getDescFileId() == null || primaryPlan.getDescFileId() == 0) {
            primaryPlan.setDescFileId(fileId.intValue());
            fullHouseDesignPlanMapper.updateByPrimaryKeySelective(primaryPlan);
        }
    }

    @Override
    public String showDesignPlanConfig(Integer id) {
        FullHouseDesignPlan primaryPlan = fullHouseDesignPlanMapper.selectByPrimaryKey(id);
        if (Objects.isNull(primaryPlan)) {
            log.warn("获取方案失败,id:{}", id);
            return "";
        }
        return resFileService.readFile(primaryPlan.getDescFileId());
    }

    @Override
    public List<DesignPlanBO> getDetailDesignPlan(Integer planId, Integer companyId) {
        List<FullHouseDesignPlanDetail> details = fullHouseDesignPlanDetailMapper.selectByPlanId(planId);
        List<DesignPlanBO> designPlanBOList = new ArrayList<>();
        for (FullHouseDesignPlanDetail detail : details) {
            List<DesignPlanBO> designPlanBOs = designPlanRecommendedService.getBaseInfos(detail.getRecommendedPlanGroupPrimaryId().longValue(), companyId);
            designPlanBOList.addAll(designPlanBOs);
        }
        Map<String, Integer> comboMap = new HashMap<>();
        String newName;
        Integer newNum;
        //过滤子方案
        Iterator iterator = designPlanBOList.iterator();
        while (iterator.hasNext()) {
            DesignPlanBO result = (DesignPlanBO) iterator.next();
            if (result.getIsPrimary() == 1) {
                iterator.remove();
                continue;
            }
            if (comboMap.containsKey(result.getSpaceTypeName())) {
                //获取key 例如 （卧室，客餐厅）
                newName = result.getSpaceTypeName();
                //如果有相同空间，则在名字后加+1
                newNum = comboMap.get(result.getSpaceTypeName()) + 1;
                comboMap.put(result.getSpaceTypeName(), newNum);
                result.setSpaceTypeName(newName + newNum);
            } else {
                comboMap.put(result.getSpaceTypeName(), 1);
                newName = result.getSpaceTypeName() + "1";
                result.setSpaceTypeName(newName);
            }
        }
        return designPlanBOList;
    }

    @Override
    public PageInfo<FullHouseDesignPlanBO> selectStoreFullHousePlan(FullHouseDesignPlanQuery query) {
        PageInfo<FullHouseDesignPlanBO> bos = this.selectListSelective(query);
        if (bos.getList() != null && bos.getList().size() > 0) {
            Map<Integer, String> isReleaseMap = this.idAndReleaseMap(query.getShopId());
            for (FullHouseDesignPlanBO bo : bos.getList()) {
                if (isReleaseMap.containsKey(bo.getId())) {
                    bo.setIsPublish(isReleaseMap.get(bo.getId()));
                } else {
                    bo.setIsPublish("未发布");
                }
            }
        }
        return bos;
    }

    /**
     * 获取全屋方案是否交付
     *
     * @return
     */
    private Map<Integer, String> idAndReleaseMap(Integer shopId) {
        List<CompanyShopDesignPlan> companyShopDesignPlans = fullHouseDesignPlanMapper.storeFullHouseByShopId(shopId);
        return companyShopDesignPlans.stream().collect(Collectors.toMap(shop -> shop.getPlanId(), valueShop -> {
            if (isEmpty(valueShop.getIsDeleted()) || valueShop.getIsDeleted() == 1) {
                return "未发布";
            } else {
                return "已发布";
            }
        }));
    }

    @Override
    public Integer cancelPublish(List<CompanyShopDesignPlanAdd> lists) {
        List<Integer> planIds = new ArrayList<>();
        lists.forEach(list ->
                planIds.add(list.getPlanId())
        );
        Integer shopId = lists.get(0).getShopId();
        Integer mainShopId = designPlanRecommendedMapper.getMainShopId(shopId);
        if (mainShopId != null && mainShopId != shopId) {
            fullHouseDesignPlanMapper.cancelPublish(mainShopId, planIds);
        }
        return fullHouseDesignPlanMapper.cancelPublish(lists.get(0).getShopId(), planIds);
    }

    @Override
    public Integer publish(List<CompanyShopDesignPlanAdd> lists) {
        List<CompanyShopDesignPlan> addList = new ArrayList<>();
        Integer count = 0;
        Integer shopId = lists.get(0).getShopId();
        Integer mainShopId = designPlanRecommendedMapper.getMainShopId(shopId);
        for (CompanyShopDesignPlanAdd add : lists) {
            count = fullHouseDesignPlanMapper.publish(add);
            if (count <= 0) {
                this.addToList(add, addList);
            }
            if (mainShopId != null && mainShopId != shopId) {
                add.setShopId(mainShopId);
                count = fullHouseDesignPlanMapper.publish(add);
                if (count <= 0) {
                    this.addToList(add, addList);
                }
            }
        }
        designPlanRecommendedMapper.insertCompanyShopDesignPlan(addList);
        return count;
    }

    private void addToList(CompanyShopDesignPlanAdd add, List<CompanyShopDesignPlan> addList){
        Date date = new Date();
        CompanyShopDesignPlan companyShopDesignPlan = new CompanyShopDesignPlan();
        companyShopDesignPlan.setShopId(add.getShopId());
        companyShopDesignPlan.setPlanId(add.getPlanId());
        companyShopDesignPlan.setGmtModified(date);
        companyShopDesignPlan.setModifier(add.getUserId() + "");
        companyShopDesignPlan.setPlanRecommendedType(add.getPlanRecommendedType());
        companyShopDesignPlan.setCreator(add.getUserId() + "");
        companyShopDesignPlan.setGmtCreate(date);
        companyShopDesignPlan.setIsDeleted(0);
        addList.add(companyShopDesignPlan);
    }

    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            tmp.put("planTableType", SyncMessage.PLAN_TYPE_FULLHOUSE);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("S-" + System.currentTimeMillis());
        message.setModule(SyncMessage.MODULE_SOLUTION_RECOMMEND);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.send(message);
    }

    @Override
    public int editSalePrice(Long id, Double salePrice, Integer salePriceChargeType) {
        return fullHouseDesignPlanMapper.updateSalePrice(id,salePrice,salePriceChargeType);
    }

    @Override
    public int editPlanPrice(Long id, Double planPrice, Integer chargeType) {
        return fullHouseDesignPlanMapper.updatePlanPrice(id,planPrice,chargeType);
    }
}

