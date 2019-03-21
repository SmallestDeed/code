package com.sandu.service.solution.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.company.model.Company;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.service.BaseProductStyleService;
import com.sandu.api.solution.constant.AllotState;
import com.sandu.api.solution.constant.CopyType;
import com.sandu.api.solution.constant.PlatformType;
import com.sandu.api.solution.constant.ShelfStatus;
import com.sandu.api.solution.input.CompanyShopDesignPlanAdd;
import com.sandu.api.solution.input.DesignPlanUpdate;
import com.sandu.api.solution.model.*;
import com.sandu.api.solution.model.bo.*;
import com.sandu.api.solution.model.po.DesignPlanAllotPO;
import com.sandu.api.solution.model.po.DesignPlanProductDisplayPO;
import com.sandu.api.solution.model.po.DesignPlanPushAwayPO;
import com.sandu.api.solution.service.*;
import com.sandu.api.user.service.UserService;
import com.sandu.constant.Punctuation;
import com.sandu.service.solution.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.solution.model.PlanDecoratePrice.PLAN_DECORATE_PRICE_FULLHOUSE;
import static com.sandu.api.solution.model.PlanDecoratePrice.PLAN_DECORATE_PRICE_RECOMMEND;
import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.util.Commoner.isEmpty;
import static com.sandu.util.Commoner.isNotEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * @Author Roc Chen
 * @Description 推荐方案 基础服务
 * @Date:Created Administrator in 16:48 2017/12/21 0021
 * @Modified By:
 */
@Slf4j
@Service(value = "designPlanRecommendedService")
public class DesignPlanRecommendedServiceImpl implements DesignPlanRecommendedService {


    @Autowired
    private DesignPlanRecommendedMapper designPlanRecommendedMapper;

    @Resource
    private PlatformService platformService;

    @Autowired
    private DesignPlan2bPlatformMapper designPlan2bPlatformMapper;

    @Autowired
    private DesignPlan2cPlatformMapper designPlan2cPlatformMapper;

    @Autowired
    private DesignPlanBrandMapper designPlanBrandMapper;

    @Autowired
    private ResRenderPicService resRenderPicService;

    @Autowired
    private DesignPlanRecommendedProductMapper designPlanRecommendedProductMapper;

    @Resource
    private DesignPlanCopyLogMapper designPlanCopyLogMapper;

    @Resource
    private SpaceCommonService spaceCommonService;

    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Resource
    private DesignPlanBrandService designPlanBrandService;

    @Resource
    private DesignPlan2cPlatformService designPlan2cPlatformService;

    @Resource
    private DesignPlan2bPlatformService designPlan2bPlatformService;

    @Resource
    private UserService userService;

    @Resource
    private BaseProductStyleService baseProductStyleService;

    @Resource
    private BrandService brandService;

    @Resource
    private DictionaryService dictionaryService;

    @Autowired
    private PlanDecoratePriceService planDecoratePriceService;

    /**
     * 根据id物理删除数据
     *
     * @param id 主键
     * @return 受影响行数
     */
    @Override
    public int deleteDesignPlanRecommended(long id) {
        if (designPlanRecommendedMapper.getPrimaryPlan((int) id) != null) {
            List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan((int) id);
            plans.forEach(plan ->
                    designPlanRecommendedMapper.deleteLogicByPrimaryKey(plan.getId())
            );
        }
        int result = designPlanRecommendedMapper.deleteLogicByPrimaryKey(id);
        return result;
    }

    /**
     * Selective新增数据
     *
     * @param record 对象
     * @return 新增的数据的id
     */
    @Override
    public long addDesignPlanRecommended(DesignPlanRecommended record) {
        int result = designPlanRecommendedMapper.insertSelective(record);
        if (result > 0) {
            return record.getId();
        }
        return 0L;
    }

    /**
     * 根据id查询数据
     *
     * @param id 主键
     * @return 对象数据
     */
    @Override
    public DesignPlanRecommended getDesignPlanRecommended(long id) {
        return designPlanRecommendedMapper.selectByPrimaryKey(id);
    }

    @Override
    public DesignPlanBO getBaseInfo(Long id, Integer companyId) {
        if (id == null || companyId == null) {
            log.info("##############方案id:{} 或公司companyId:{}为空###########################", id, companyId);
            return null;
        }
        //根据方案id查询
        DesignPlanBO designPlanBO = designPlanRecommendedMapper.getBaseInfo(id, companyId);
        if (isEmpty(designPlanBO)) {
            return null;
        }
        if (isNotEmpty(designPlanBO.getPicId())) {
            //设置图片路径
            designPlanBO.setPicPath(resRenderPicService.getPathById(designPlanBO.getPicId()));
        }

        log.info("###########################根据方案id:{}匹配信息开始###########################", id);
        //方案id
        List<Long> planIds = new ArrayList<>();
        planIds.add(id);
        //根据方案id集查询方案品牌表brand_id信息( map(planId,brandId) )
        Map<Integer, String> brandIdMap = designPlanBrandService.idAndSpaceTypeMap(planIds);
        //设置方案品牌id
        designPlanBO.setBrandId(brandIdMap.get(designPlanBO.getPlanId().intValue()));
        //查询空间类型id
        List<String> spaceCommonIds = new ArrayList<>();
        spaceCommonIds.add(designPlanBO.getSpaceCommonId());
        //根据方案space_common_id查询空间类型表space_common信息( map(spaceCommonId,spaceTypeId) )
        Map<Long, Integer> spaceTypeMap = spaceCommonService.idAndSpaceTypeMap(spaceCommonIds);
        //设置空间类型
        designPlanBO.setSpaceTypeId(spaceTypeMap.get(Long.valueOf(designPlanBO.getSpaceCommonId())));
        Map<Integer, String> houseType = dictionaryService.valueAndName2Map("houseType");
        //设置空间类型名称
        if (designPlanBO.getSpaceTypeId() == null) {
            designPlanBO.setSpaceTypeName("");
        } else {
            designPlanBO.setSpaceTypeName(houseType.get(designPlanBO.getSpaceTypeId()));
        }
        //查询方案品牌id对应的id集
        List<String> brandIdStrs = Splitter.on(COMMA)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(Strings.nullToEmpty(designPlanBO.getBrandId()));
        //设置对应品牌名称
        if (brandIdStrs.size() > 0 && brandIdStrs != null) {
            designPlanBO.setBrandName(brandService.getBrandNamesByIds(brandIdStrs));
        } else {
            designPlanBO.setBrandName("");
        }
        List<Integer> planIdList = new ArrayList<>();
        planIdList.add(id.intValue());
        //根据方案id集查询2b端记录
        //Map<Integer, DesignPlan2bPlatform> platform2bMap = designPlan2bPlatformService.idAnd2bMap(planIdList);
        //根据方案id集查询2c端记录
        //Map<Integer, DesignPlan2cPlatform> platform2cMap = designPlan2cPlatformService.idAnd2cMap(planIdList);
        List<DesignPlan2cPlatform> platFormList = designPlan2cPlatformMapper.queryByPlanId(planIdList);
        List<Integer> platform2c = platFormList.stream().filter(plat -> plat.getPutawayState() == 1).map(plat -> plat.getPlatformId()).collect(Collectors.toList());
        //设置对应分配情况
        //设置平台名称
        Map<Integer, String> platFormMap = platformService.getPlatformIdAndNameByBussinessType(PlatformType.TO_C.getCode());
        StringBuffer sb = new StringBuffer();
        platform2c.forEach(plat -> {
            if (platFormMap.containsKey(plat)) {
                sb.append(platFormMap.get(Integer.valueOf(plat))).append(",");
            }
        });
        List<String> shelStatusList = designPlanBO.getShelfStatus() == null ? Collections.emptyList() : Arrays.asList(designPlanBO.getShelfStatus().split(Punctuation.COMMA));
        if (shelStatusList.contains(ShelfStatus.ONEKEY.getCode())) {
            sb.append("通用版-一键方案").append(",");
        }
        if (shelStatusList.contains(ShelfStatus.OPEN.getCode())) {
            sb.append("通用版-公开方案").append(",");
        }
        if (shelStatusList.contains(ShelfStatus.TEMPLATE.getCode())) {
            sb.append("通用版-样板方案").append(",");
        }
        if (StringUtils.isNoneBlank(sb.toString()) && sb.toString().length() > 0) {
            designPlanBO.setPlatformNames(sb.toString().substring(0, sb.toString().length() - 1));
        }
//        designPlanBO.setDistributionStatus(platform2bMap.get(designPlanBO.getPlanId().intValue()).getAtt1() + "," +
//                platform2cMap.get(designPlanBO.getPlanId().intValue()).getAtt1());

        //设置方案风格
        if (isNotEmpty(designPlanBO.getDesignStyleId()) && designPlanBO.getDesignStyleId() > 0) {
            BaseProductStyle baseProductStyle = baseProductStyleService.getBaseProductStyleById(designPlanBO.getDesignStyleId());
            if (isEmpty(baseProductStyle)) {
                designPlanBO.setDesignStyleName("");
            } else {
                designPlanBO.setDesignStyleName(baseProductStyle.getName());
            }
        }

        designPlanBO.setDesigner(userService.getUserByNickname(designPlanBO.getDesigner()));
        log.info("###########################根据方案id:{}匹配信息结束###########################", id);

        //设置方案报价信息
        List<DecoratePriceInfo> decoratePriceInfos = showPlanDecoratePriceInfo(designPlanBO.getPlanId(), PLAN_DECORATE_PRICE_RECOMMEND);
        designPlanBO.setDecoratePriceInfoList(decoratePriceInfos);
        return designPlanBO;
    }

    @Override
    public List<DecoratePriceInfo> showPlanDecoratePriceInfo(Long planId, Integer planType) {
        List<Dictionary> decorateType = dictionaryService.listByType("decorateType");
        return planDecoratePriceService.listByPlanIdAndType(planId.intValue(), planType)
                .stream()
                .filter(it -> it.getDecoratePrice() > 0)
                .filter(it -> it.getIsDeleted().equals(0))
                .map(it -> {
                            DecoratePriceInfo build = DecoratePriceInfo.builder()
                                    .decoratePrice(it.getDecoratePrice())
                                    .decoratePriceTypeValue(it.getDecoratePriceType())
                                    .build();
                            decorateType.stream().filter(type -> type.getValue().equals(build.getDecoratePriceTypeValue()))
                                    .findFirst().ifPresent(type -> build.setDecoratePriceTypeName(type.getName()));
                            return build;
                        }
                ).collect(toList());
    }

    @Override
    public List<DesignPlanBO> getBaseInfos(Long id, Integer companyId) {
        List<DesignPlanBO> designPlanBOs = new ArrayList<>();
        DesignPlanBO designPlanBO = getBaseInfo(id, companyId);
        if (designPlanBO != null) {
            designPlanBOs.add(designPlanBO);
            if (designPlanRecommendedMapper.getPrimaryPlan(designPlanBO.getPlanId().intValue()) != null) {
                List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan(designPlanBO.getPlanId().intValue());
                plans.forEach(plan -> {
                    DesignPlanBO designPlan = getBaseInfo(plan.getId(), companyId);
                    designPlanBOs.add(designPlan);
                });
            }
        }
        return designPlanBOs;
    }

    @Override
    public DesignPlanBO getBaseInfo(Long id) {
        if (id == null) {
            log.error("###########################传入方案id为:{}###########################", id);
            return null;
        }
        DesignPlanBO designPlanBO = designPlanRecommendedMapper.getBaseInfo(id, null);
        if (isEmpty(designPlanBO)) {
            return null;
        }
        log.info("###########################根据方案id:{}匹配信息开始###########################", id);
        //方案id集
        List<Long> planIds = new ArrayList<>();
        planIds.add(id);
        //根据方案id集查询方案品牌表brand_id信息( map(planId,brandId) )
        Map<Integer, String> brandIdMap = designPlanBrandService.idAndSpaceTypeMap(planIds);
        //设置方案品牌id
        designPlanBO.setBrandId(brandIdMap.get(designPlanBO.getPlanId().intValue()));
        //查询空间类型id
        List<String> spaceCommonIds = new ArrayList<>();
        spaceCommonIds.add(designPlanBO.getSpaceCommonId());
        //根据方案space_common_id查询空间类型表space_common信息( map(spaceCommonId,spaceTypeId) )
        Map<Long, Integer> spaceTypeMap = spaceCommonService.idAndSpaceTypeMap(spaceCommonIds);
        //设置空间类型id
        designPlanBO.setSpaceTypeId(spaceTypeMap.get(Long.valueOf(designPlanBO.getSpaceCommonId())));
        //查询方案品牌id对应的id集
        List<String> brandIdStrs = Splitter.on(COMMA)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(Strings.nullToEmpty(designPlanBO.getBrandId()));
        //设置对应品牌名称
        if (brandIdStrs.size() > 0 && brandIdStrs != null) {
            designPlanBO.setBrandName(brandService.getBrandNamesByIds(brandIdStrs));
        } else {
            designPlanBO.setBrandName("");
        }
        List<Integer> planIdList = new ArrayList<>();
        planIdList.add(id.intValue());
        //根据方案id集查询查询2b端记录
        Map<Integer, DesignPlan2bPlatform> platform2bMap = designPlan2bPlatformService.idAnd2bMap(planIdList);
        //根据方案id集查询2c端记录
        Map<Integer, DesignPlan2cPlatform> platform2cMap = designPlan2cPlatformService.idAnd2cMap(planIdList);
        //设置对应分配情况
        designPlanBO.setDistributionStatus(platform2bMap.get(designPlanBO.getPlanId().intValue()).getAtt1() + "," +
                platform2cMap.get(designPlanBO.getPlanId().intValue()).getAtt1());
        //设置图片路径
        String picPath = resRenderPicService.getPathById(designPlanBO.getPicId());
        designPlanBO.setPicPath(picPath);
        //设置方案风格
        if (isNotEmpty(designPlanBO.getDesignStyleId())) {
            BaseProductStyle baseProductStyle = baseProductStyleService.getBaseProductStyleById(designPlanBO.getDesignStyleId());
            System.out.println("=========================设计方案风格" + designPlanBO.getDesignStyleId());
            designPlanBO.setDesignStyleName(baseProductStyle.getName());
        }
        designPlanBO.setDesigner(userService.getUserByNickname(designPlanBO.getDesigner()));


        return designPlanBO;
    }


    /**
     * 根据id Selective修改数据
     *
     * @param designPlanUpdate 对象
     * @return 受影响的行数
     */
    @Override
    public Long updateDesignPlanRecommended(DesignPlanUpdate designPlanUpdate) {
        DesignPlanRecommended designPlanRecommended = DesignPlanRecommended
                .builder()
                .planName(designPlanUpdate.getPlanName())
                .id(Long.valueOf(designPlanUpdate.getPlanId()))
                .remark(designPlanUpdate.getRemark())
                .planDesc(designPlanUpdate.getPlanDesc())
                .picId(designPlanUpdate.getPicId())
                .designRecommendedStyleId(designPlanUpdate.getStyleId())
                .isChanged(1)
                .build();
        if (isNotEmpty(designPlanUpdate.getBrandIds())) {
            designPlanBrandMapper.removePlanBrand(designPlanUpdate.getPlanId());
            designPlanUpdate.getBrandIds().forEach(brandId -> {
                DesignPlanBrand designPlanBrand =
                        DesignPlanBrand.builder()
                                .planId(designPlanUpdate.getPlanId())
                                .brandId(brandId)
                                .companyId(designPlanUpdate.getCompanyId())
                                .isDeleted(0)
                                .gmtCreate(new Date())
                                .gmtModified(new Date())
                                .build();
                designPlanBrandMapper.insertSelective(designPlanBrand);
            });
        }
        designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);

        //设置方案报价信息
        this.setPlanDecoratePrice(designPlanUpdate.getPlanId(), designPlanUpdate.getDecoratePriceInfoList(), PLAN_DECORATE_PRICE_RECOMMEND)
                .forEach(planDecoratePriceService::updateByPlanRecommendId);
        return designPlanRecommended.getId();
    }

    @Override
    public List<PlanDecoratePrice> setPlanDecoratePrice(Integer planId, List<DecoratePriceInfo> priceInfos,
                                                        Integer planType) {
        if (priceInfos == null || priceInfos.isEmpty()) {
            if (planType.equals(PLAN_DECORATE_PRICE_RECOMMEND)) {
                planDecoratePriceService.deleteByRecommendId(planId);
            }
            if (planType.equals(PLAN_DECORATE_PRICE_FULLHOUSE)) {
                planDecoratePriceService.deleteByFullHouseId(planId);
            }
            return Collections.emptyList();
        }

        //获取之前方案报价
        List<PlanDecoratePrice> priceList = planDecoratePriceService.listByPlanIdAndType(planId, planType);

        List<PlanDecoratePrice> result = new LinkedList<>();
        Date now = new Date();
        PlanDecoratePrice build = PlanDecoratePrice.builder()
                .planType(planType)
                .isDeleted(0)
                .gmtCreate(now)
                .gmtModified(now)
                .build();

        if (planType.equals(PLAN_DECORATE_PRICE_RECOMMEND)) {
            build.setPlanRecommendId(planId);
        }
        if (planType.equals(PLAN_DECORATE_PRICE_FULLHOUSE)) {
            build.setFullHouseId(planId);
        }

        List<Dictionary> decorateType = dictionaryService.listByType("decorateType");

        for (DecoratePriceInfo it : priceInfos) {
            //选出type
            for (Dictionary parent : decorateType) {
                if (it.getDecoratePriceTypeValue().equals(parent.getValue())) {
                    //选出ranges
                    List<Dictionary> ranges = dictionaryService.listByType(parent.getType());

                    ranges.sort(Comparator.comparing(Dictionary::getOrdering));
                    it.setDecoratePriceRangeValue(ranges.get(ranges.size() - 1).getValue());
                    ranges.stream()
                            .filter(range -> this.choosePriceRangeWithPrice(range, it.getDecoratePrice()))
                            .findFirst()
                            .ifPresent(range -> it.setDecoratePriceRangeValue(range.getValue()));

                    PlanDecoratePrice tmp = new PlanDecoratePrice();
                    BeanUtils.copyProperties(build, tmp);
                    tmp.setDecoratePrice(it.getDecoratePrice());
                    tmp.setDecoratePriceType(it.getDecoratePriceTypeValue());
                    tmp.setDecoratePriceRange(it.getDecoratePriceRangeValue());
                    priceList.stream()
                            .filter(preIt -> this.setPriceFilterMethod(preIt, tmp))
                            .findFirst()
                            .ifPresent(preIt -> {
                                tmp.setId(preIt.getId());
                                tmp.setGmtCreate(null);
                                tmp.setIsDeleted(0);
                            });
                    if (tmp.getDecoratePrice() == 0) {
                        tmp.setIsDeleted(1);
                    }
                    result.add(tmp);
                }
            }

        }
        log.debug("result:{}", result);
        return result;
    }

    private boolean setPriceFilterMethod(PlanDecoratePrice preIt, PlanDecoratePrice curIt) {
        if (preIt.getPlanType().equals(PLAN_DECORATE_PRICE_RECOMMEND)) {
            return preIt.getPlanRecommendId().equals(curIt.getPlanRecommendId())
                    && preIt.getDecoratePriceType().equals(curIt.getDecoratePriceType());
        }
        if (preIt.getPlanType().equals(PLAN_DECORATE_PRICE_FULLHOUSE)) {
            return preIt.getFullHouseId().equals(curIt.getFullHouseId())
                    && preIt.getDecoratePriceType().equals(curIt.getDecoratePriceType());
        }

        return false;
    }

    private boolean choosePriceRangeWithPrice(Dictionary range, Integer decoratePrice) {
        List<String> prices = Splitter.on(Punctuation.MIDLINE).trimResults().splitToList(range.getName());
        if (prices.size() > 1
                && Integer.parseInt(prices.get(0)) <= decoratePrice
                && decoratePrice < Integer.parseInt(prices.get(1))) {
            return true;
        }
        return false;
    }


    /**
     * 根据id逻辑删除数据
     *
     * @param id 主键
     * @return 受影响的行数
     */
    @Override
    public int deleteLogicDesignPlanRecommended(long id) {
        return designPlanRecommendedMapper.deleteLogicByPrimaryKey(id);
    }


    @Override
    /**
     * 复杂的代码都是适配不对劲儿的数据库设计
     */
    public void allot(DesignPlanAllotPO designPlanAllot) {
        if (designPlanRecommendedMapper.getPrimaryPlan(designPlanAllot.getPlanId().intValue()) != null) {
            List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan(designPlanAllot.getPlanId().intValue());

            plans.forEach(plan -> {

                DesignPlanAllotPO designPlan = new DesignPlanAllotPO();
                designPlan.setPlanId(plan.getId());
                designPlan.setTargetPlatform(designPlanAllot.getTargetPlatform());
                //designPlanAllot.targetPlatform  传递 ['2b','2c']  或者  ['2b']
                List<String> targetPlatforms = designPlan.getTargetPlatform();
                Long planId = designPlan.getPlanId();
                if (targetPlatforms.contains(PlatformType.TO_B.getCode())) {
                    // 分配到 2b
                    handleAllotTo2B(planId);
                }
                if (targetPlatforms.contains(PlatformType.TO_C.getCode())) {
                    // 分配到 2C
                    handleAllotTo2C(planId);
                }
                if (!targetPlatforms.contains(PlatformType.TO_B.getCode())) {
                    //取消分配到2b
                    handleCancelAllotTo2B(planId);
                }
                if (!targetPlatforms.contains(PlatformType.TO_C.getCode())) {
                    //取消分配到2b
                    handleCancelAllotTo2C(planId);
                }
            });
        }

        //designPlanAllot.targetPlatform  传递 ['2b','2c']  或者  ['2b']
        List<String> targetPlatforms = designPlanAllot.getTargetPlatform();
        Long planId = designPlanAllot.getPlanId();

        if (targetPlatforms.contains(PlatformType.TO_B.getCode())) {
            // 分配到 2b
            handleAllotTo2B(planId);
        }
        if (targetPlatforms.contains(PlatformType.TO_C.getCode())) {
            // 分配到 2C
            handleAllotTo2C(planId);
        }
        if (!targetPlatforms.contains(PlatformType.TO_B.getCode())) {
            //取消分配到2b
            handleCancelAllotTo2B(planId);
        }
        if (!targetPlatforms.contains(PlatformType.TO_C.getCode())) {
            //取消分配到2b
            handleCancelAllotTo2C(planId);
        }
    }

    @Override
    public DesignPlanEffectDiagramMergeBO getEffectDiagram(Long planId) {
        DesignPlanEffectDiagramMergeBO merges = new DesignPlanEffectDiagramMergeBO();
        List<DesignPlanEffectDiagramBO> pics = designPlanRecommendedMapper.listAllDiagram(planId, 1);
        List<DesignPlanEffectDiagramBO> sigles = designPlanRecommendedMapper.listSiglesDiagram(planId, 4);
        List<DesignPlanEffectDiagramBO> multis = designPlanRecommendedMapper.listAllDiagram(planId, 8);
        List<DesignPlanEffectDiagramBO> videos = designPlanRecommendedMapper.listAllDiagram(planId, 6);
//        String basePath = "/data001/resource";
//        if(sigles != null && sigles.size()>0) {
//            for(DesignPlanEffectDiagramBO bo1 : sigles) {
//                String path = basePath + bo1.getPicPath();
//                log.info("########################文件路径{}",path);
//                File file = new File(path);
//                if(file.isDirectory()) {
//                    log.info("########################开始遍历文件夹{}",path);
//                    List<DesignPlanEffectDiagramBO> siglesFile = new ArrayList<>();
//                    String[] filePaths = file.list();
//                    for (int i = 0; i < filePaths.length; i++) {
//                        log.info("########################开始遍历文件夹下文件{}",filePaths[i]);
//                        String newPath = bo1.getPicPath() + File.separator + filePaths[i];
//                        DesignPlanEffectDiagramBO bo = new DesignPlanEffectDiagramBO();
//                        bo.setPicId(bo1.getPicId());
//                        bo.setPicPath(newPath);
//                        bo.setRenderingType(bo1.getRenderingType());
//                        bo.setIsDeleted(bo1.getIsDeleted());
//                        bo.setPlanId(bo1.getPlanId());
//                        siglesFile.add(bo);
//                        log.info("########################结束遍历文件夹下文件{}",filePaths[i]);
//                    }
//                    merges.setSingle720Pic(siglesFile);
//                } else {
//                    log.info("########################开始操作文件{}",path);
//                    merges.setSingle720Pic(sigles);
//                }
//            }
//        }
        merges.setSingle720Pic(sigles);
        merges.setPics(pics);
        merges.setMulti720Pic(multis);
        merges.setVideoPic(videos);
        return merges;
    }


    @Override
    public void onlinePushaway(DesignPlanPushAwayPO designPlanPushAwayPO) {
        //组合方案子方案上下架
        if (designPlanRecommendedMapper.getPrimaryPlan(designPlanPushAwayPO.getPlanId().intValue()) != null) {
            List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan(designPlanPushAwayPO.getPlanId().intValue());

            plans.forEach(plan -> {
                Set<Integer> platforms2cs = platformService.getPlatformIdAndNameByBussinessType("2c").keySet();
                List<Integer> pushPlatfroms = designPlanPushAwayPO.getPlatformIds();
                for (Integer platforms2c : platforms2cs) {
                    if (pushPlatfroms.contains(platforms2c)) {
                        handlePutOnShelf(plan.getId().intValue(), platforms2c);
                    } else {
                        this.handleOffShelf(plan.getId().intValue(), platforms2c);
                    }
                }
            });
        }

        //组合方案主方案或单方案上下架
        //[3,7]
        Set<Integer> platforms2cs = platformService.getPlatformIdAndNameByBussinessType("2c").keySet();
        // [3]
        List<Integer> pushPlatfroms = designPlanPushAwayPO.getPlatformIds();
        for (Integer platforms2c : platforms2cs) {
            if (pushPlatfroms.contains(platforms2c)) {
                //上架
                handlePutOnShelf(designPlanPushAwayPO.getPlanId(), platforms2c);
            } else {
                // 下架
                this.handleOffShelf(designPlanPushAwayPO.getPlanId(), platforms2c);
                // TODO: 2018/5/17 方案下架，删除已经被企业使用过的该方案信息
                //判断该方案是否已在所有平台全部下架
//                DesignPlan2cPlatform designPlan2cPlatform = new DesignPlan2cPlatform();
//                designPlan2cPlatform.setPlanId(designPlanPushAwayPO.getPlanId());
//                if (designPlan2cPlatformMapper.selectListSelective(designPlan2cPlatform) == null) {
//                    DesignPlanRecommended result = designPlanRecommendedMapper.getDesignById(designPlanPushAwayPO.getPlanId());
//                    if(result != null) {
//                        if(result.getShelfStatus() == "" || result.getShelfStatus().equalsIgnoreCase("onekey")) {
                //删除已经被企业使用过的该方案

//                        }
//                    }
//                 }
            }
        }
    }

//    private DesignPlanRecommendedServiceImpl getService() {
//        return AopContext.currentProxy() != null ? (DesignPlanRecommendedServiceImpl) AopContext.currentProxy() : this;
//    }


    private void handlePutOnShelf(Integer planId, Integer platforms2c) {
        if (designPlan2cPlatformMapper.selectByPlanIdAndFormId(planId, platforms2c, 1).size() == 0) {
            DesignPlanRecommended designPlan = designPlanRecommendedMapper.selectByPrimaryKey(planId.longValue());
            DesignPlan2cPlatform designPlan2c = new DesignPlan2cPlatform();
            designPlan2c.setPlanId(planId);
            designPlan2c.setPlatformId(platforms2c);
            if (null != designPlan) {

                designPlan2c.setCreator(designPlan.getCreator());
                designPlan2c.setUserId(designPlan.getUserId());
                designPlan2c.setModifier(designPlan.getModifier());
            }
            designPlan2c.setPutawayState(1);
            designPlan2c.setDesignPlanType(2);
            designPlan2c.setAllotState(1);
            designPlan2c.setGmtCreate(new Date());
            designPlan2c.setGmtModified(new Date());
            designPlan2cPlatformMapper.insertSelective(designPlan2c);
        } else {
            designPlanRecommendedMapper.putOnOnlineShelf(planId, platforms2c);
        }
    }

    @Override
    public void handleOffShelf(Integer planId, Integer platforms2c) {
        designPlanRecommendedMapper.offOnOnlineShelf(planId, platforms2c);
        boolean flag = designPlanRecommendedService.getIsValidSharePlan(planId);
        log.debug("the valid share plan flag is {}", flag);
        if (flag) {
            //获取分享后变成的方案
            List<Integer> planIds = designPlanCopyLogMapper.listDeliveredPlanIds(planId, CopyType.SHARE.getCode(), 1);
            //删除这些方案
            planIds.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
            //清除记录
            designPlanCopyLogMapper.deleteBySourceId(planId, CopyType.SHARE.getCode(), 1);
        }

    }

    @Override
    public Map<Integer, Integer> idAndSecrecyFlagMap(List<Integer> planIds) {
        planIds = planIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(planIds)) {
            return Collections.emptyMap();
        }
        List<SecrecyFlagBO> secrecyFlagBOS = designPlanRecommendedMapper.listSecrecyFlagByPlanIds(planIds);
        return secrecyFlagBOS.stream().collect(toMap(SecrecyFlagBO::getPlanId, SecrecyFlagBO::getSecrecyFlag));
    }

    @Override
    public void channelPushaway(DesignPlanPushAwayPO designPlanPushAwayPO) {
        //[3,7]
        Set<Integer> platforms2cs = platformService.getPlatformIdAndNameByBussinessType("2b").keySet();
        // [3]
        List<Integer> pushPlatfroms = designPlanPushAwayPO.getPlatformIds();
        for (Integer platforms2c : platforms2cs) {
            if (pushPlatfroms.contains(platforms2c)) {
                //上架
                handleChannelPutOnShelf(designPlanPushAwayPO.getPlanId(), platforms2c);
            } else {
                // 下架
                handleChannelOffShelf(designPlanPushAwayPO.getPlanId(), platforms2c);
            }
        }
    }

    private void handleChannelPutOnShelf(Integer planId, Integer platforms2c) {
        designPlanRecommendedMapper.putOnChannelShelf(planId, platforms2c);
    }

    @Override
    public void handleChannelOffShelf(Integer planId, Integer platforms2c) {
        designPlanRecommendedMapper.offOnChannelShelf(planId, platforms2c);
    }


    @Override
    public void toggleDisplay(DesignPlanProductDisplayPO displayBO) {
        designPlanRecommendedProductMapper.toggleDisplay(displayBO);
    }

    private void handleDownTo2B(Long planId, List<String> platformIds) {
        if (platformIds != null && platformIds.size() > 0) {
            List<DesignPlan2bPlatform> toList = new ArrayList<DesignPlan2bPlatform>();
            platformIds.forEach(plat -> {
//		    	if (designPlan2bPlatformMapper.selectByPlanIdAndFormId(planId.intValue(), Integer.parseInt(plat)).size() == 0) {
                        DesignPlanRecommended designPlan = designPlanRecommendedMapper.selectByPrimaryKey(planId.longValue());
                        if (null != designPlan) {
                            //普通方案全下架
                            if (designPlan.getRecommendedType() == 1) {
                                handleDownTo2B(planId, Collections.emptyList());
                            } else {
                                DesignPlan2bPlatform designPlan2c = new DesignPlan2bPlatform();
                                designPlan2c.setPlanId(planId.intValue());
                                designPlan2c.setPlatformId(Integer.parseInt(plat));
                                designPlan2c.setCreator(designPlan.getCreator());
                                designPlan2c.setUserId(designPlan.getUserId());
                                designPlan2c.setModifier(designPlan.getModifier());
                                designPlan2c.setPutawayState(0);
                                designPlan2c.setDesignPlanType(2);
                                designPlan2c.setAllotState(1);
                                designPlan2c.setGmtCreate(new Date());
                                designPlan2c.setGmtModified(new Date());
                                toList.add(designPlan2c);
                            }
                        }
//		        } else {
//		        	//全下架
//	                handleDownTo2B(planId,Collections.emptyList());
//		            //designPlanRecommendedMapper.offOnChannelShelf(planId.intValue(), Integer.parseInt(plat));
//		        }
                    }
            );
            if (toList != null && toList.size() > 0) {
                designPlan2bPlatformMapper.insertRelListIfNotExist(toList);
            }
        } else {
//		DesignPlanRecommended tdpr = designPlanRecommendedMapper.selectByPrimaryKey(planId);
            List<DesignPlan2bPlatform> designPlan2bPlatforms = designPlan2bPlatformMapper.listByPlanIdAndType(planId, null);
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

    private void handleDownTo2C(Long planId, List<String> platformIds) {
        if (platformIds != null && platformIds.size() > 0) {
            List<DesignPlan2cPlatform> toList = new ArrayList<DesignPlan2cPlatform>();
            platformIds.forEach(plat -> {
//    		    	if (designPlan2cPlatformMapper.selectByPlanIdAndFormId(planId.intValue(), Integer.parseInt(plat)).size() == 0) {
                        DesignPlanRecommended designPlan = designPlanRecommendedMapper.selectByPrimaryKey(planId.longValue());
                        DesignPlan2cPlatform designPlan2c = new DesignPlan2cPlatform();
                        designPlan2c.setPlanId(planId.intValue());
                        designPlan2c.setPlatformId(Integer.parseInt(plat));
                        if (null != designPlan) {
                            designPlan2c.setCreator(designPlan.getCreator());
                            designPlan2c.setUserId(designPlan.getUserId());
                            designPlan2c.setModifier(designPlan.getModifier());
                        }
                        designPlan2c.setPutawayState(0);
                        designPlan2c.setDesignPlanType(2);
                        designPlan2c.setAllotState(1);
                        designPlan2c.setIsDeleted(0);
                        designPlan2c.setGmtCreate(new Date());
                        designPlan2c.setGmtModified(new Date());
                        toList.add(designPlan2c);
//    		        } else {
//    		            designPlanRecommendedMapper.offOnOnlineShelf(planId.intValue(), Integer.parseInt(plat));
//    		        }
                    }
            );
            if (toList != null && toList.size() > 0) {
                designPlan2cPlatformMapper.insertRelListIfNotExist(toList);
            }
        } else {
//    		 DesignPlanRecommended tdpr = designPlanRecommendedMapper.selectByPrimaryKey(planId);
            List<DesignPlan2cPlatform> designPlan2bPlatforms = designPlan2cPlatformMapper.listByPlanIdAndType(planId, null);
            if (isNotEmpty(designPlan2bPlatforms)) {
                designPlan2bPlatforms.forEach(
                        designPlan2bPlatform -> {
                            designPlan2bPlatform.setPutawayState(AllotState.N.getCode());
                            designPlan2cPlatformMapper.updateByPrimaryKeySelective(designPlan2bPlatform);
                        }
                );
            }
        }
    }

    private void handleCancelAllotTo2C(Long planId) {
        DesignPlanRecommended tdpr = designPlanRecommendedMapper.selectByPrimaryKey(planId);
        //获取有没有分配到2c
        List<DesignPlan2cPlatform> designPlan2cPlatforms = designPlan2cPlatformMapper.listByPlanIdAndType(planId, tdpr.getRecommendedType());
        //未分配
        if (isNotEmpty(designPlan2cPlatforms)) {
            designPlan2cPlatforms.forEach(
                    designPlan2cPlatform -> {
                        designPlan2cPlatform.setAllotState(AllotState.N.getCode());
                        designPlan2cPlatformMapper.updateByPrimaryKeySelective(designPlan2cPlatform);
                    }
            );
        }
    }

    private void handleCancelAllotTo2B(Long planId) {
        DesignPlanRecommended tdpr = designPlanRecommendedMapper.selectByPrimaryKey(planId);
        List<DesignPlan2bPlatform> designPlan2bPlatforms = designPlan2bPlatformMapper.listByPlanIdAndType(planId, tdpr.getRecommendedType());
        //未分配
        if (isNotEmpty(designPlan2bPlatforms)) {
            designPlan2bPlatforms.forEach(
                    designPlan2bPlatform -> {
                        designPlan2bPlatform.setAllotState(AllotState.N.getCode());
                        designPlan2bPlatformMapper.updateByPrimaryKeySelective(designPlan2bPlatform);
                    }
            );
        }
    }

    private void handleAllotTo2C(Long planId) {
//        DesignPlanRecommended tdpr = designPlanRecommendedMapper.selectByPrimaryKey(planId);
        //获取有没有分配到2c
        List<DesignPlan2cPlatform> designPlan2cPlatforms = designPlan2cPlatformMapper.listByPlanIdAndType(planId, null);
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedMapper.selectByPrimaryKey(planId);
        //没有分配
        if (isEmpty(designPlan2cPlatforms)) {
            List<Integer> platformIds = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList("2c"));
            platformIds.forEach(platformId -> {
                DesignPlan2cPlatform designPlan2cPlatform = DesignPlan2cPlatform.builder()
                        .allotState(AllotState.Y.getCode())
                        .designPlanType(designPlanRecommended.getRecommendedType())
                        .putawayState(0)
                        .planId(planId.intValue())
                        .platformId(platformId)
                        .gmtModified(new Date())
                        .gmtCreate(new Date())
                        .isDeleted(0)
                        .build();
                designPlan2cPlatformMapper.insertSelective(designPlan2cPlatform);
            });
        }
        if (isNotEmpty(designPlan2cPlatforms)) {
            designPlan2cPlatforms.forEach(designPlan2cPlatform -> {
                designPlan2cPlatform.setAllotState(AllotState.Y.getCode());
                designPlan2cPlatform.setDesignPlanType(designPlanRecommended.getRecommendedType());
                designPlan2cPlatform.setGmtModified(new Date());
                designPlan2cPlatform.setGmtModified(new Date());
                designPlan2cPlatformMapper.updateByPrimaryKeySelective(designPlan2cPlatform);
            });
        }
    }

    private void handleAllotTo2B(Long planId) {
//        DesignPlanRecommended tdpr = designPlanRecommendedMapper.selectByPrimaryKey(planId);
        //获取有没有分配到2b
        List<DesignPlan2bPlatform> designPlan2bPlatforms = designPlan2bPlatformMapper.listByPlanIdAndType(planId, null);
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedMapper.selectByPrimaryKey(planId);
        //没有分配
        if (isEmpty(designPlan2bPlatforms)) {
            List<Integer> platformIds = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList("2b"));
            platformIds.forEach(platformId -> {
                DesignPlan2bPlatform designPlan2bPlatform = DesignPlan2bPlatform.builder()
                        .allotState(AllotState.Y.getCode())
                        .designPlanType(designPlanRecommended.getRecommendedType())
                        .putawayState(0)
                        .planId(planId.intValue())
                        .platformId(platformId)
                        .isDeleted(0)
                        .gmtModified(new Date())
                        .gmtCreate(new Date())
                        .build();
                designPlan2bPlatformMapper.insertSelective(designPlan2bPlatform);
            });
        }
        if (isNotEmpty(designPlan2bPlatforms)) {
            designPlan2bPlatforms.forEach(designPlan2bPlatform -> {
                designPlan2bPlatform.setAllotState(AllotState.Y.getCode());
                designPlan2bPlatform.setDesignPlanType(designPlanRecommended.getRecommendedType());
                designPlan2bPlatform.setGmtModified(new Date());
                designPlan2bPlatform.setGmtCreate(new Date());
                designPlan2bPlatformMapper.updateByPrimaryKeySelective(designPlan2bPlatform);
            });
        }
    }

    @Override
    public Company getNotUseSharePlanCompanyQueue() {
        return designPlanRecommendedMapper.getNotUseSharePlanCompanyQueue();
    }

    @Override
    public boolean getIsValidSharePlan(Integer planId) {
        return designPlanRecommendedMapper.getIsValidSharePlan(planId) == 0;
    }

    @Override
    public List<DesignPlanRecommended> listAllCopy2CompanyPlan() {
        return designPlanRecommendedMapper.listAllCopy2CompanyPlan();
    }

    @Override
    public int save(DesignPlanRecommended recommended) {
        log.debug("Update:{}", recommended);
        return designPlanRecommendedMapper.updateByPrimaryKeySelective(recommended);
    }

    @Override
    public void fixedDeliverPlanByPrimary() {
        designPlanRecommendedMapper.updateDeleverPlanByPrimary();
    }

    @Override
    public List<DesignPlanRecommended> selectDeliverPlanByPrimary() {
        return designPlanRecommendedMapper.selectDeliverPlanByPrimary();
    }

    @Override
    public void fixedDeliverPlanBySide(Long id, Integer groupPrimaryId) {
        designPlanRecommendedMapper.fixedDeliverPlanBySide(id, groupPrimaryId);
    }

    /**
     * 方案的上下架处理
     *
     * @throws Exception
     */
    @Override
    public Map<String, Object> upAndDownPlan(String planId, List<String> tocPlatformIds, List<String> tobPlatformIds,
                                             Integer putStatusUp, String designPlanType) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        log.info("planId:{},tocPlatformIds:{},tobPlatformIds:{},putStatusUp:{},designPlanType:{}", planId, tocPlatformIds, tobPlatformIds, putStatusUp, designPlanType);
        if (StringUtils.isBlank(planId)) {
            log.error("upAndDownPlan上架失败,失败原因:planId参数为空");
            resultMap.put("1", "planId参数为空!");
            return resultMap;
        }
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedMapper.selectByPrimaryKey(Long.parseLong(planId));
        if (designPlanRecommended == null) {
            log.error("upAndDownPlan上架失败,失败原因:planId:{}没有找到方案信息!", planId);
            resultMap.put("1", "planId没有找到方案信息");
            return resultMap;
        }
        List<DesignPlanRecommended> plans = new ArrayList<DesignPlanRecommended>();
        String shelfStatus = join(tobPlatformIds, COMMA);
        plans = designPlanRecommendedMapper.queryPrimaryPlan(Integer.parseInt(planId));
        plans.add(designPlanRecommended);
        List<String> planIds = plans.stream().map(DesignPlanRecommended::getId).map(s -> String.valueOf(s)).collect(Collectors.toList());
        if (Platform.PUT_STATUS_UP == putStatusUp) {
            //处理B端上架
            if (!tobPlatformIds.isEmpty()) {
                boolean flag = false;
                for (Iterator<DesignPlanRecommended> it = plans.iterator(); it.hasNext(); ) {
                    DesignPlanRecommended plan = (DesignPlanRecommended) it.next();
                    //分享方案不能上架到一键方案
                    if (("share".equals(plan.getPlanSource()) && shelfStatus.contains("ONEKEY"))) {
                        flag = true;
                    }
//                    boolean existed = (
//                            ("share".equals(plan.getPlanSource()) && shelfStatus.contains("OPEN"))
//                                    || (!"share".equals(plan.getPlanSource()))
//                                    || (shelfStatus.contains(("TEMPLATE"))));
//                    if (!existed) {
//                        it.remove();
//                    }
                }
                if (flag) {
                    resultMap.put("2", "分享方案不能上架到一键方案!");
                    return resultMap;
                }
                if (plans != null && plans.size() > 0) {
                    plans.forEach(plan -> {
                        handleBShelf(plan, tobPlatformIds, shelfStatus);
                    });
                }
            }
            //处理C端上架
            if (!tocPlatformIds.isEmpty()) {
                if (plans != null && plans.size() > 0) {
                    designPlan2cPlatformMapper.deleteByPlanId(planIds, tocPlatformIds);
                    plans.forEach(plan -> {
                        handleCShelf(plan, tocPlatformIds, shelfStatus);
                    });
                }
            }
        } else if (Platform.PUT_STATUS_DOWN == putStatusUp) {
            //处理B端下架
            if (!tobPlatformIds.isEmpty()) {
                if (tobPlatformIds.size() > 0 && !plans.isEmpty()) {
                    offShelfB(planIds, tobPlatformIds, shelfStatus);
                }
            }
            //处理C端下架
            if (!tocPlatformIds.isEmpty()) {
                if (tocPlatformIds.size() > 0 && !plans.isEmpty()) {
                    offShelfC(planIds, tocPlatformIds, shelfStatus);
                }
            }
            //全部下架
            if (tocPlatformIds.isEmpty() && tobPlatformIds.isEmpty()) {
                planIds.forEach(id -> handleDownTo2B(Long.valueOf(id), Collections.emptyList()));
                planIds.forEach(id -> handleDownTo2C(Long.valueOf(id), Collections.emptyList()));
                //designPlan2cPlatformMapper.deleteByPlanId(planIds, null);
                //designPlan2bPlatformMapper.deleteByPlanId(planIds, null);
                designPlanRecommendedMapper.updateDesignPlanById(planIds, null);
                planIds.forEach(id -> {
                    boolean flag = designPlanRecommendedService.getIsValidSharePlan(Integer.parseInt(id));
                    log.debug("the valid share plan flag is {}", flag);
                    if (flag) {
                        //获取分享后变成的方案
                        List<Integer> planIdList = designPlanCopyLogMapper.listDeliveredPlanIds(Integer.parseInt(id), CopyType.SHARE.getCode(), 1);
                        //删除这些方案
                        planIdList.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
                        //清除记录
                        designPlanCopyLogMapper.deleteBySourceId(Long.parseLong(id), CopyType.SHARE.getCode(), 1);
                    }
                });
            }
        }
        resultMap.put("0", "操作成功!");
        return resultMap;
    }

    /**
     * 上架到C端
     *
     * @param designPlanRecommended
     * @param platformIds
     * @param shelfStatus
     */
    private void handleCShelf(DesignPlanRecommended designPlanRecommended, List<String> platformIds, String shelfStatus) {
        platformIds.forEach(platformId -> {
            handlePutOnShelf(designPlanRecommended.getId().intValue(), Integer.parseInt(platformId));
        });
    }

    /**
     * C端下架
     *
     * @param platformIds
     * @param shelfStatus
     */
    private void offShelfC(List<String> planIds, List<String> platformIds, String shelfStatus) {
        planIds.forEach(id -> handleDownTo2C(Long.valueOf(id), platformIds));
        //删除线上
//        designPlan2cPlatformMapper.deleteByPlanId(planIds, platformIds);
        planIds.forEach(id -> {
            boolean flag = designPlanRecommendedService.getIsValidSharePlan(Integer.parseInt(id));
            log.debug("the valid share plan flag is {}", flag);
            if (flag) {
                //获取分享后变成的方案
                List<Integer> planIdList = designPlanCopyLogMapper.listDeliveredPlanIds(Integer.parseInt(id), CopyType.SHARE.getCode(), 1);
                //删除这些方案
                planIdList.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
                //清除记录
                designPlanCopyLogMapper.deleteBySourceId(Long.parseLong(id), CopyType.SHARE.getCode(), 1);
            }
        });
    }

    /**
     * B端上架
     *
     * @param designPlanRecommended
     * @param platformIds
     * @param shelfStatus
     */
    private void handleBShelf(DesignPlanRecommended designPlanRecommended, List<String> platformIds, String shelfStatus) {
        List<Integer> platform2bIds = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList("2b"));
        platform2bIds.forEach(platformId -> {
            designPlan2bPlatformMapper.deleteByPlanId(Arrays.asList(String.valueOf(designPlanRecommended.getId())), Arrays.asList(platformId + ""));
            DesignPlan2bPlatform designPlan2bPlatform = DesignPlan2bPlatform.builder()
                    .allotState(AllotState.Y.getCode())
                    .designPlanType(designPlanRecommended.getRecommendedType())
                    .putawayState(1)
                    .planId(designPlanRecommended.getId().intValue())
                    .platformId(platformId)
                    .isDeleted(0)
                    .gmtModified(new Date())
                    .gmtCreate(new Date())
                    .build();
            designPlan2bPlatformMapper.insertSelective(designPlan2bPlatform);
        });
        //来源类型为分享方案智能商家到公开方案里面
        designPlanRecommended.setShelfStatus(join(platformIds, COMMA));
        if (StringUtils.isNoneBlank(designPlanRecommended.getShelfStatus())) {
            List<String> statusList = new ArrayList(Arrays.asList(shelfStatus.split(Punctuation.COMMA)));
            //去除2b移动端平台
            List<String> newStatusList = statusList.stream().filter(status -> !"1".equals(status)).collect(Collectors.toList());
            designPlanRecommended.setShelfStatus(join(newStatusList, COMMA));
        }
        if ("share".equals(designPlanRecommended.getPlanSource()) && shelfStatus.contains("OPEN")) {
            designPlanRecommended.setShelfStatus("OPEN");
        }
        designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
    }

    /**
     * b端下架
     *
     * @param platformIds
     * @param shelfStatus
     */
    private void offShelfB(List<String> planIds, List<String> platformIds, String shelfStatus) {
//      List<Integer> platform2bIds = platformService.getPlatformIdsByBussinessTypes(Collections.singletonList("2b"));
        //删除渠道
//		designPlan2bPlatformMapper.deleteByPlanId(planIds,platform2bIds.stream().map(s->String.valueOf(s)).collect(Collectors.toList()));
        planIds.forEach(id -> {
            if (shelfStatus.contains("1")) {
                handleDownTo2B(Long.valueOf(id), Arrays.asList("1"));
//                designPlan2bPlatformMapper.deleteByPlanId(Arrays.asList(id), Arrays.asList("1"));
            }
            DesignPlanRecommended designPlanRecommended = designPlanRecommendedMapper.selectByPrimaryKey(Long.parseLong(id));
            if (designPlanRecommended != null && StringUtils.isNoneBlank(designPlanRecommended.getShelfStatus())) {
                List<String> statusStr = Arrays.asList(designPlanRecommended.getShelfStatus().split(","));
                List<String> deleteList = new ArrayList<String>();
                statusStr.forEach(status -> {
                    if (!"1".equals(status) && shelfStatus.indexOf(status) < 0) {
                        deleteList.add(status);
                    }
                });
                designPlanRecommended.setShelfStatus(join(deleteList, COMMA));
                designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
            }
            if (StringUtils.isEmpty(designPlanRecommended.getShelfStatus())) {
                handleDownTo2B(Long.valueOf(id), Arrays.asList("2"));
                //designPlan2bPlatformMapper.deleteByPlanId(Arrays.asList(id), Arrays.asList("2"));
            }
            boolean flag = designPlanRecommendedService.getIsValidSharePlan(Integer.parseInt(id));
            log.debug("the valid share plan flag is {}", flag);
            if (flag) {
                //获取分享后变成的方案
                List<Integer> planIdList = designPlanCopyLogMapper.listDeliveredPlanIds(Integer.parseInt(id), CopyType.SHARE.getCode(), 1);
                //删除这些方案
                planIdList.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
                //清除记录
                designPlanCopyLogMapper.deleteBySourceId(Long.parseLong(id), CopyType.SHARE.getCode(), 1);
            }
        });
    }


    @Override
    public Integer addCompanyShopDesignPlan(List<CompanyShopDesignPlanAdd> companyShopDesignPlanAddList, String userName, Integer shopType) {
        if (null == companyShopDesignPlanAddList || companyShopDesignPlanAddList.size() == 0) {
            return 0;
        }
        log.info("{List<CompanyShopDesignPlanAdd>----companyShopDesignPlanAddList}:" + new Gson().toJson(companyShopDesignPlanAddList));
        if (shopType == null || shopType == 2) {
            Integer shopId = companyShopDesignPlanAddList.get(0).getShopId();
            Integer mainShopId = designPlanRecommendedMapper.getMainShopId(shopId);
            List<CompanyShopDesignPlan> companyShopDesignPlanList = new ArrayList<>(companyShopDesignPlanAddList.size());
            for (CompanyShopDesignPlanAdd companyShopDesignPlanAdd : companyShopDesignPlanAddList) {
                this.updateCompanyShopDesignPlan(companyShopDesignPlanAdd, userName, companyShopDesignPlanList);
                if (mainShopId != null && mainShopId != 0) {
                    companyShopDesignPlanAdd.setShopId(mainShopId);
                    this.updateCompanyShopDesignPlan(companyShopDesignPlanAdd, userName, companyShopDesignPlanList);
                }
            }
            log.info("{List<CompanyShopDesignPlan>----companyShopDesignPlanList}:" + new Gson().toJson(companyShopDesignPlanList));
            designPlanRecommendedMapper.insertCompanyShopDesignPlan(companyShopDesignPlanList);
            return 1;
        } else if (shopType == 1) {
            List<CompanyShopDesignPlan> companyShopDesignPlanList = new ArrayList<>(companyShopDesignPlanAddList.size());
            for (CompanyShopDesignPlanAdd companyShopDesignPlanAdd : companyShopDesignPlanAddList) {
                this.updateCompanyShopDesignPlan(companyShopDesignPlanAdd, userName, companyShopDesignPlanList);
            }
            log.info("{List<CompanyShopDesignPlan>----companyShopDesignPlanList}:" + new Gson().toJson(companyShopDesignPlanList));
            designPlanRecommendedMapper.insertCompanyShopDesignPlan(companyShopDesignPlanList);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateCompanyShopDesignPlan(List<CompanyShopDesignPlanAdd> companyShopDesignPlanAddList, String userName, Integer isDeleted, Integer shopType) {
        if (null == companyShopDesignPlanAddList || companyShopDesignPlanAddList.size() == 0) {
            return 0;
        }
        int res = 0;
        log.info("{List<CompanyShopDesignPlanAdd>----companyShopDesignPlanAddList}:" + new Gson().toJson(companyShopDesignPlanAddList));
        if (shopType == null || shopType == 2) {
            Integer shopId = companyShopDesignPlanAddList.get(0).getShopId();
            Integer mainShopId = designPlanRecommendedMapper.getMainShopId(shopId);
            List<CompanyShopDesignPlan> companyShopDesignPlanList = new ArrayList<>(companyShopDesignPlanAddList.size());
            for (CompanyShopDesignPlanAdd companyShopDesignPlanAdd : companyShopDesignPlanAddList) {
                CompanyShopDesignPlan companyShopDesignPlan = new CompanyShopDesignPlan();
                companyShopDesignPlan.setId(companyShopDesignPlanAdd.getId());
                companyShopDesignPlan.setPlanId(companyShopDesignPlanAdd.getPlanId());
                companyShopDesignPlanList.add(companyShopDesignPlan);
            }
            log.info("{List<CompanyShopDesignPlan>----companyShopDesignPlanList}:" + new Gson().toJson(companyShopDesignPlanList));
            res = designPlanRecommendedMapper.updateCompanyShopDesignPlan(companyShopDesignPlanList, new Date(), userName, isDeleted);
            if (mainShopId != null && mainShopId != 0) {
                res += designPlanRecommendedMapper.updateMainCompanyShopDesignPlan(companyShopDesignPlanList, new Date(), userName, mainShopId, isDeleted);
            }
        } else if (shopType == 1) {
            List<CompanyShopDesignPlan> companyShopDesignPlanList = new ArrayList<>(companyShopDesignPlanAddList.size());
            for (CompanyShopDesignPlanAdd companyShopDesignPlanAdd : companyShopDesignPlanAddList) {
                CompanyShopDesignPlan companyShopDesignPlan = new CompanyShopDesignPlan();
                companyShopDesignPlan.setId(companyShopDesignPlanAdd.getId());
                companyShopDesignPlanList.add(companyShopDesignPlan);
            }
            log.info("{List<CompanyShopDesignPlan>----companyShopDesignPlanList}:" + new Gson().toJson(companyShopDesignPlanList));
            res = designPlanRecommendedMapper.updateCompanyShopDesignPlan(companyShopDesignPlanList, new Date(), userName, isDeleted);
        }
        return res;
    }

    @Override
    public Map<Integer, String> getDeliverTimesByPlanId(List<Integer> planIds, Integer companyId) {
        List<Map<String, Object>> list = designPlanRecommendedMapper.getDeliverTimesByPlanId(planIds, companyId);
        Map<Integer, String> map = new HashMap<>(list.size());
        for (Map<String, Object> tmp : list) {
            map.put(Integer.parseInt(tmp.get("planId").toString()), tmp.get("deliverTimes").toString());
        }
        return map;
    }

    @Override
    public Map<Integer, String> getShelfPlatForms(List<Integer> planIds) {
        List<Map<String, Object>> list = designPlanRecommendedMapper.getShelfPlatForms(planIds);
        Map<Integer, String> map = new HashMap<>(list.size());
        for (Map<String, Object> tmp : list) {
            map.put(Integer.parseInt(tmp.get("planId").toString()), tmp.get("platformIds").toString());
        }
        return map;
    }

    @Override
    public List<Integer> selectMainSubPlanList(List<Integer> planIds) {
        return designPlanRecommendedMapper.selectMainSubPlanList(planIds);
    }

    @Override
    public List<DesignPlanDeliverInfoBO> mapDeliverTimesByPlanId(List<Integer> planIds, Integer companyId, Integer planType) {
        return designPlanRecommendedMapper.mapDeliverTimesByPlanId(planIds, companyId, planType);
    }

    @Override
    public Integer checkDesignPlanIsDeleted(Long planId) {
        return designPlanRecommendedMapper.checkDesignPlanIsDeleted(planId);
    }

    private int updateCompanyShopDesignPlan(CompanyShopDesignPlanAdd companyShopDesignPlanAdd, String userName, List<CompanyShopDesignPlan> companyShopDesignPlanList){
        CompanyShopDesignPlan companyShopDesignPlan = new CompanyShopDesignPlan();
        companyShopDesignPlan.setShopId(companyShopDesignPlanAdd.getShopId());
        companyShopDesignPlan.setPlanId(companyShopDesignPlanAdd.getPlanId());
        companyShopDesignPlan.setIsDeleted(0);
        companyShopDesignPlan.setGmtModified(new Date());
        companyShopDesignPlan.setModifier(userName);
        int res = designPlanRecommendedMapper.updateCompanyShopDesignPlanSelective(companyShopDesignPlan);
        if (res <= 0) {
            Date date = new Date();
            companyShopDesignPlan.setPlanRecommendedType(companyShopDesignPlanAdd.getPlanRecommendedType());
            companyShopDesignPlan.setCreator(userName);
            companyShopDesignPlan.setGmtCreate(date);
            companyShopDesignPlan.setIsDeleted(0);
            companyShopDesignPlanList.add(companyShopDesignPlan);
        }
        return res;
    }
}
