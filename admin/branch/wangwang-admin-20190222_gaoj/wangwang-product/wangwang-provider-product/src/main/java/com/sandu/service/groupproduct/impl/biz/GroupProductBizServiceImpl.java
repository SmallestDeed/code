package com.sandu.service.groupproduct.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.groupproducct.input.GroupPutUpdate;
import com.sandu.api.groupproducct.input.GroupQuery;
import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.groupproducct.model.GroupProductDetails;
import com.sandu.api.groupproducct.model.bo.GroupProductInfoBO;
import com.sandu.api.groupproducct.model.bo.GroupProductListBO;
import com.sandu.api.groupproducct.model.po.GroupProductUpdatePO;
import com.sandu.api.groupproducct.service.GroupProductDetailsService;
import com.sandu.api.groupproducct.service.GroupProductService;
import com.sandu.api.groupproducct.service.biz.GroupProductBizService;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.model.PlatformGroupRel;
import com.sandu.api.platform.service.Platform2bGroupRelService;
import com.sandu.api.platform.service.Platform2cGroupRelService;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.PicInfo;
import com.sandu.api.product.model.bo.StyleParseBO;
import com.sandu.api.product.service.BaseProductStyleService;
import com.sandu.api.product.service.ProductService;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.service.UserService;
import com.sandu.constant.Punctuation;
import com.sandu.util.Commoner;
import com.sandu.util.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.platform.model.Platform.*;
import static com.sandu.util.CodeUtil.formatCode;

/**
 * @author Sandu
 */
@Slf4j
@Service("groupProductBizService")
public class GroupProductBizServiceImpl implements GroupProductBizService {


    @Resource
    private ResPicService resPicService;
    private final Platform2bGroupRelService platform2bGroupRelService;
    private final Platform2cGroupRelService platform2cGroupRelService;
    private final PlatformService platformService;
    private final GroupProductService groupProductService;
    private final GroupProductDetailsService groupProductDetailsService;
    private final BrandService brandService;
    private final ResModelService resModelService;
    private final DictionaryService dictionaryService;
    private final ProductService productService;
    private final BaseProductStyleService baseProductStyleService;
    private final UserService userService;

    @Autowired
    public GroupProductBizServiceImpl(Platform2bGroupRelService platform2bGroupRelService, Platform2cGroupRelService platform2cGroupRelService, PlatformService platformService, GroupProductService groupProductService, GroupProductDetailsService groupProductDetailsService, BrandService brandService, ResModelService resModelService, DictionaryService dictionaryService, ProductService productService, BaseProductStyleService baseProductStyleService, UserService userService) {
        this.platform2bGroupRelService = platform2bGroupRelService;
        this.platform2cGroupRelService = platform2cGroupRelService;
        this.platformService = platformService;
        this.groupProductService = groupProductService;
        this.groupProductDetailsService = groupProductDetailsService;
        this.brandService = brandService;
        this.resModelService = resModelService;
        this.dictionaryService = dictionaryService;
        this.productService = productService;
        this.baseProductStyleService = baseProductStyleService;
        this.userService = userService;
    }

    @Override
    public PageInfo<GroupProductListBO> queryGroup(GroupQuery query, String queryType) {
        log.debug("组合查询参数:{}", query, "查询类型:", queryType);
        query.setPlatform2bId(platformService.getOnePlatformIdByBussinessType(Platform.BUSINESS_TYPE_2B));
        query.setPlatform2cId(platformService.getOnePlatformIdByBussinessType(Platform.BUSINESS_TYPE_2C));
        //设置品牌信息
        query.setOrderMethod("desc");
        query.setOrderField("id");
        query.setBrandIds(brandService.getBrandByCompanyId(query.getCompanyId()).stream().map(Brand::getId).collect(Collectors.toList()));
        PageInfo<GroupProductListBO> groupProducts = null;
        if (query.getBrandIds().isEmpty()) {
            return new PageInfo<>(Collections.emptyList());
        }
        if (queryType.equals(PLATFORM_LIBRARY)) {
            //内容库查询
            groupProducts = groupProductService.queryList(query);
        }
        if (queryType.equals(PLATFORM_CHANNEL)) {
            //渠道库查询
            groupProducts = groupProductService.query2bList(query);
        }
        if (queryType.equals(PLATFORM_ONLINE)) {
            //线上库查询
            groupProducts = groupProductService.query2cList(query);
        }
        if (Commoner.isEmpty(groupProducts)) {
            return new PageInfo<>(Collections.emptyList());
        }
        //设置视图数据
        setGroupListViewInfo(queryType, groupProducts);
//        log.info("list:{}",groupProducts.getList());
        groupProducts.getList().forEach(
                item -> log.info("bo----------:{}", item)
        );
        return groupProducts;
    }

    @Override
    public boolean allot(List<Integer> groupIds, String allotTo) {
        StringBuilder to = new StringBuilder();
        if (allotTo.contains(Platform.BUSINESS_TYPE_2B)) {
            to.append(PLATFORM_CHANNEL);
        }
        if (allotTo.contains(Platform.BUSINESS_TYPE_2C)) {
            to.append(PLATFORM_ONLINE);
        }
        doAllot(groupIds, to.toString());
        cancelAllot(groupIds, to.toString());
        return true;
    }

    /**
     * 设置组合列表需要显示的数据
     */
    private void setGroupListViewInfo(String queryType, PageInfo<GroupProductListBO> groupProducts) {
        List<Integer> brandIds = new ArrayList<>();
        List<Integer> picIds = new ArrayList<>();
//        List<Integer> typeIds = new ArrayList<>();
//        List<Integer> modelIds = new ArrayList<>();
        Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();
        Platform platform = new Platform();
        platform.setIsDeleted(0);
        platform.setNuma1(1);
        List<Platform> platforms = platformService.getList(platform);
        //获取此集合中的所有资源信息ID
        groupProducts.getList().forEach(item -> {
            if (!brandIds.contains(item.getBrandId())) {
                brandIds.add(item.getBrandId());
            }
            if (!picIds.contains(item.getPicId())) {
                picIds.add(item.getPicId());
            }
//            if (!typeIds.contains(item.getCompositeType())) {
//                typeIds.add(item.getCompositeType());
//            }
//            if (!modelIds.contains(item.getModelId())) {
//                typeIds.add(item.getModelId());
//            }
            if (item.getSecrecy() == null) {
                item.setSecrecy(0);
            }
            if (item.getStatus2b() == null) {
                item.setStatus2b(0);
            }
            if (item.getStatus2c() == null) {
                item.setStatus2c(0);
            }
        });
        //查询相关信息
        Map<Integer, String> brands = brandService.getBrandNameByIds(brandIds);
        Map<Integer, String> pics = resPicService.idAndPathMap(picIds);
        Map<Integer, String> types = dictionaryService.valueAndName2Map("restaurantAlbuginea");
//        Map<Integer, ResModel> models = resModelService.getModelByIds(modelIds);
        Map<String, String> groupId2PlatformIds
                = groupProductService.listGroupPutStatusByGroupIds(groupProducts.getList().stream().map(GroupProductListBO::getId).collect(Collectors.toList()));

        //列表相关信息设置
        List<Long> needIds = platforms.stream().map(Platform::getId).collect(Collectors.toList());
        groupProducts.getList().forEach(item -> {
            //设置上架状态名称
            if (groupId2PlatformIds.containsKey(item.getId().toString())) {
                List<String> ids = Splitter.on(Punctuation.COMMA).trimResults().splitToList(groupId2PlatformIds.get(item.getId().toString()))
                        .stream().filter(id -> needIds.contains(Long.valueOf(id))).collect(Collectors.toList());

                List<String> names = ids.stream().map(id -> platformMap.get(Integer.valueOf(id))).collect(Collectors.toList());
                item.setPlatformIds(Joiner.on(Punctuation.COMMA).join(ids));
                item.setPutStatusNames(names);
            }
            //线上产品上下架状态设置
//            if (queryType.equals(PLATFORM_ONLINE)) {
//                item.setPutStatus2cNames(new ArrayList<>());
//                Map<Integer, Integer> integerStringMap = mapPlatform2PutStatus(item.getPutAwayStatus2c(), item.getPlatformIds());
//                for (Map.Entry<Integer, Integer> entry : integerStringMap.entrySet()) {
//                    if (Platform.PUT_STATUS_UP.equals(entry.getValue())) {
//                        item.getPutStatus2cNames().add(platformMap.get(entry.getKey()));
//                    }
//                }
//            }
//            if (models.get(item.getModelId()) != null) {
//                item.setModelCode(models.get(item.getModelId()).getModelCode());
//            }
            item.setBrandName(brands.get(item.getBrandId()));
            item.setPicPath(pics.get(item.getPicId()));
            item.setCompositeTypeName(types.get(item.getCompositeType()));
        });
    }

    private Map<Integer, Integer> mapPlatform2PutStatus(String putAwayStatus2c, String platformIds) {
        if (StringUtils.isBlank(putAwayStatus2c) || StringUtils.isBlank(putAwayStatus2c)) {
            return Collections.emptyMap();
        }
        List<String> status = Arrays.stream(putAwayStatus2c.split(Punctuation.COMMA)).collect(Collectors.toList());
        List<String> ids = Arrays.stream(platformIds.split(Punctuation.COMMA)).collect(Collectors.toList());
        Map<Integer, Integer> ret = new HashMap<>(ids.size());
        ids.forEach(item -> ret.put(Integer.parseInt(item), Integer.parseInt(status.get(ids.indexOf(item)))));
        return ret;
    }


    @Override
    public GroupProductInfoBO getGroupInfo(Integer groupId, String type) {
        GroupProductInfoBO.GroupProductInfoBOBuilder builder = GroupProductInfoBO.builder();
        GroupProduct info;
        GroupProduct pGroup = null;
        //组合信息
        info = groupProductService.getInfoById(groupId);
        if (PLATFORM_CHANNEL.equals(type)) {
            pGroup = platform2bGroupRelService.getInfoById(groupId);
        } else if (PLATFORM_ONLINE.equals(type)) {
            pGroup = platform2cGroupRelService.getInfoById(groupId);
        }
        if (null == info) {
            return new GroupProductInfoBO();
        }
        if (pGroup != null) {
            info.setAdvicePrice(pGroup.getAdvicePrice());
            info.setGroupPrice(pGroup.getGroupPrice());
            info.setPicId(pGroup.getPicId());
            info.setDescription(pGroup.getDescription());
            info.setPicIds(pGroup.getPicIds());
        }
        builder.name(info.getGroupName())
                .code(info.getGroupCode())
                .price(info.getGroupPrice())
                .advicePrice(info.getAdvicePrice())
                .desc(info.getDescription())
                .groupNumber(info.getProductType())
                .defaultPicId(info.getPicId());
        //设置风格
        if (StringUtils.isNoneBlank(info.getProductStyleIdInfo())) {
            StyleParseBO styleParseBO = JsonParser.fromJson(info.getProductStyleIdInfo(), StyleParseBO.class);
            String leaf = styleParseBO.getIsLeaf_0();
            if (leaf.contains(Punctuation.COMMA)) {
                leaf = leaf.substring(0, leaf.indexOf(Punctuation.COMMA));
            }
            if (StringUtils.isNoneBlank(leaf)) {
                BaseProductStyle style = baseProductStyleService.getBaseProductStyleById(Integer.parseInt(leaf));
                builder.styleValue(style.getId().intValue()).styleName(style.getName());
            }
        }
        //设置组合字典属性
        Dictionary houseType = dictionaryService.getByTypeAndValue("houseType", info.getType());
        Dictionary areaType = null;
        if (houseType != null) {
            areaType = dictionaryService.getByTypeAndValue(houseType.getValuekey(), info.getSpaceAreaValue());
        }
        Dictionary groupType = dictionaryService.getByTypeAndValue("restaurantAlbuginea", info.getCompositeType());
        if (Commoner.isNotEmpty(houseType)) {
            builder.houseType(houseType.getName()).houseValue(houseType.getValue());
        }
        if (Commoner.isNotEmpty(areaType)) {
            builder.areaType(areaType.getName()).areaValue(areaType.getValue());
        }
        if (Commoner.isNotEmpty(groupType)) {
            builder.groupType(groupType.getName()).groupValue(info.getCompositeType());
        }
        Brand brand = brandService.getBrandById(info.getBrandId());
        if (Commoner.isNotEmpty(brand)) {
            builder.brandName(brand.getBrandName()).brandId(brand.getId().intValue());
        }
        //组合内产品
        List<Product> products = productService.getProductByIds(groupProductDetailsService.getProductIdsByGroupId(groupId));
        Integer mainProduct = groupProductDetailsService.getMainProductIdWithGroupId(groupId);
        Set<Integer> picIds = products.stream().map(Product::getPicId).collect(Collectors.toSet());
        picIds.add(info.getPicId());
        if (StringUtils.isNoneBlank(info.getPicIds())) {
            picIds.addAll(Arrays.stream(info.getPicIds().split(Punctuation.COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
        }
        picIds.remove(0);
        Map<Integer, String> picMap = resPicService.idAndPathMap(new ArrayList<>(picIds));
        //设置组合图片
        List<PicInfo> groupPics = new ArrayList<>();
        groupPics.add(PicInfo.builder().id(info.getPicId()).path(picMap.get(info.getPicId())).build());
        if (StringUtils.isNoneBlank(info.getPicIds())) {
            groupPics.addAll(Arrays.stream(info.getPicIds().split(Punctuation.COMMA)).map(Integer::parseInt)
                    .filter(item -> !item.equals(info.getPicId()) && picMap.get(item) != null)
                    .map(item -> PicInfo.builder().id(item).path(picMap.get(item)).build())
                    .collect(Collectors.toList()));
        }
        builder.picInfos(groupPics.stream().filter(item -> item.getId() != 0).collect(Collectors.toList()));
        //设置组合内产品信息

        GroupProductInfoBO ret = builder.build();
        List<GroupProductInfoBO.ProductInfo> collect = products.stream()
                .map(item -> ret.new ProductInfo(item.getId().intValue(), item.getProductCode(), picMap.get(item.getPicId()), item.getProductName(), mainProduct != null && item.getId().equals(mainProduct.longValue())))
                .collect(Collectors.toList());
        ret.setProducts(collect);
        return ret;
    }

    @Override
    public boolean updateGroup(GroupProductUpdatePO update) {
        boolean flag = true;
        if (PLATFORM_LIBRARY.equals(update.getPlatformType())) {
            GroupProduct groupProduct = new GroupProduct();
            groupProduct.setId(update.getId().longValue());
            groupProduct.setType(update.getHouseValue());
            groupProduct.setCompositeType(update.getGroupValue());
            groupProduct.setGroupName(update.getName());
            groupProduct.setBrandId(update.getBrandId());
            groupProduct.setSpaceAreaValue(update.getAreaValue());
            groupProduct.setGroupPrice(update.getPrice());
            groupProduct.setPicId(update.getPicId());
            if (update.getPicId() != null) {
                groupProduct.setPicIds(StringUtils.join(Arrays.stream(update.getPicIds().split(Punctuation.COMMA)).collect(Collectors.toSet()), Punctuation.COMMA));
            }
            groupProduct.setGmtModified(new Date());
            //组合风格
            if (update.getStyleId() != null) {
                groupProduct.setProductStyleIdInfo(JsonParser.toJson(StyleParseBO.builder().isLeaf_0(update.getStyleId().toString()).isLeaf_1("").build()));
            }
            //组合型号
            groupProduct.setProductType(update.getGroupNumber());
            groupProduct.setSpaceFunctionValue(update.getHouseValue());
            groupProduct.setDescription(update.getDesc());
            groupProduct.setAdvicePrice(update.getAdvicePrice());
            groupProduct.setModifier(userService.getUserById(update.getUserId()).getUserName());
            BeanUtils.copyProperties(update, groupProduct);
            groupProductService.updateGroupProduct(groupProduct);
        } else {
            PlatformGroupRel group = PlatformGroupRel.builder()
                    .productGroupId(update.getId())
                    .gmtModifier(new Date())
                    .salePrice(update.getPrice())
                    .advicePrice(update.getAdvicePrice())
                    .picId(update.getPicId())
                    .picIds(update.getPicIds())
                    .description(update.getDesc())
                    .build();
            List<PlatformGroupRel> rels = Collections.singletonList(group);
            if (PLATFORM_CHANNEL.equals(update.getPlatformType())) {
                flag = platform2bGroupRelService.updateSelectedRelsByGroupId(rels) > 0;
            }
            if (PLATFORM_ONLINE.equals(update.getPlatformType())) {
                flag &= platform2cGroupRelService.updateSelectedRelsByGroupId(rels) > 0;
            }
        }
        return flag;
    }

    @Override
    public Integer addGroup(GroupProduct group, List<Integer> productIds, Integer mainProduct) {
        group.setGroupCode(formatCode("PG", groupProductService.getMaxId()));
        groupProductService.addGroup(group);
        int groupId = group.getId().intValue();
        List<GroupProductDetails> details = productIds.stream().map(item -> {
            GroupProductDetails tmp = GroupProductDetails.builder().groupId(groupId)
                    .productId(item)
                    .isMain(GroupProduct.NOT_MAIN_PRODUCT)
                    .gmtCreate(new Date())
                    .gmtModified(new Date()).build();
            //设置主产品
            if (item.equals(mainProduct)) {
                tmp.setIsMain(GroupProduct.IS_MAIN_PRODUCT);
            }
            return tmp;
        }).collect(Collectors.toList());
        groupProductDetailsService.deleteByGroupIds(Collections.singletonList(groupId));
        groupProductDetailsService.insertList(details);
        return groupId;
    }

    @Override
    public int deleteGroupByIds(List<Integer> ids) {
        return groupProductService.deleteByIds(ids);
    }

    @Override
    public boolean updateGroupSecrecyByIds(List<Integer> ids, Integer secrecy) {
        return groupProductService.updateGroupSecrecyByIds(ids, secrecy);
    }

    @Override
    public Boolean putGroupInLibrary(GroupPutUpdate put) {
        Platform param = new Platform();
        param.setNuma1(1);
        param.setIsDeleted(0);
        List<Platform> platforms = platformService.getList(param);
        List<String> bePutPlatformIds = Splitter.on(Punctuation.COMMA).trimResults().splitToList(put.getPlatformIds());
        //渠道上下架
        List<Long> platformIds2b = platforms.stream().filter(platform -> platform.getPlatformBussinessType().equals(BUSINESS_TYPE_2B))
                .map(Platform::getId).collect(Collectors.toList());
        //上架
        Boolean flag = platformIds2b.stream()
                .filter(item -> bePutPlatformIds.contains(item.toString()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), ids ->
                        ids.isEmpty() || this.putUp(put.getGroupIds(), Joiner.on(Punctuation.COMMA).join(ids), PLATFORM_CHANNEL)
                ));
        //下架
        flag &= platformIds2b.stream()
                .filter(item -> !bePutPlatformIds.contains(item.toString()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), ids ->
                        ids.isEmpty() || this.putDown(put.getGroupIds(), Joiner.on(Punctuation.COMMA).join(ids), PLATFORM_CHANNEL))
                );


        //2c上下架
        List<Long> platformIds2c = platforms.stream().filter(platform -> platform.getPlatformBussinessType().equals(BUSINESS_TYPE_2C))
                .map(Platform::getId).collect(Collectors.toList());
        //上架
        flag &= platformIds2c.stream()
                .filter(item -> bePutPlatformIds.contains(item.toString()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), ids ->
                        ids.isEmpty() || this.putUp(put.getGroupIds(), Joiner.on(Punctuation.COMMA).join(ids), PLATFORM_ONLINE))
                );
        //下架
        flag &= platformIds2c.stream()
                .filter(item -> !bePutPlatformIds.contains(item.toString()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), ids ->
                        ids.isEmpty() || this.putDown(put.getGroupIds(), Joiner.on(Punctuation.COMMA).join(ids), PLATFORM_ONLINE))
                );

        return flag;
    }

    private boolean doAllot(List<Integer> groupIds, String allotTo) {
        log.debug("组合分配--------组合ID:{}", groupIds, "平台类型:{}", allotTo);
        List<Integer> notAllotIds;
        List<String> types = new ArrayList<>();
        int flag = 0;
        if (allotTo.contains(PLATFORM_CHANNEL)) {
            types.add(Platform.BUSINESS_TYPE_2B);
            //获取未分配的组合ID
            notAllotIds = platform2bGroupRelService.getNotAllotGroupIds(groupIds);
            flag += insertPlatformGroupRels(notAllotIds, types);
        }
        if (allotTo.contains(PLATFORM_ONLINE)) {
            types.clear();
            types.add(Platform.BUSINESS_TYPE_2C);
            //获取未分配的组合ID
            notAllotIds = platform2cGroupRelService.getNotAllotGroupIds(groupIds);
            flag += insertPlatformGroupRels(notAllotIds, types);
        }
        return flag > 0;
    }

    private boolean cancelAllot(List<Integer> groupIds, String allotTo) {
        log.debug("组合取消分配--------组合ID:{},平台类型:{}", groupIds, allotTo);
        int count = 0;
        if (!allotTo.contains(PLATFORM_CHANNEL)) {
            count += platform2bGroupRelService.deleteByGroupIds(groupIds);
        }
        if (!allotTo.contains(PLATFORM_ONLINE)) {
            count += platform2cGroupRelService.deleteByGroupIds(groupIds);
        }
        return count > 0;
    }

    private int insertPlatformGroupRels(List<Integer> notAllotIds, List<String> types) {
        if (!notAllotIds.isEmpty()) {
            //获取产品组合信息
            PageInfo<GroupProduct> groups = groupProductService.getInfoByIds(notAllotIds);
            //设置组合分配信息
            List<PlatformGroupRel> list = new ArrayList<>();
            platformService.getPlatformIdsByBussinessTypes(types)
                    .forEach(platformId -> groups.getList().forEach(allotGroup -> {
                        PlatformGroupRel group = PlatformGroupRel.builder()
                                .platformId(platformId)
                                .productGroupId(allotGroup.getId().intValue())
                                .allotState(1)
                                .gmtCreate(new Date())
                                .gmtModifier(new Date())
                                .picId(allotGroup.getPicId())
                                .picIds(allotGroup.getPicIds())
                                .description(allotGroup.getDescription())
                                .advicePrice(allotGroup.getAdvicePrice())
                                .salePrice(allotGroup.getGroupPrice())
                                .build();
                        list.add(group);
                    }));
            //插入组合分配信息
            if (types.contains(Platform.BUSINESS_TYPE_2B)) {
                return platform2bGroupRelService.insertRels(list);
            }
            if (types.contains(Platform.BUSINESS_TYPE_2C)) {
                return platform2cGroupRelService.insertRels(list);
            }
        }
        return 0;
    }

    @Override
    public boolean putUp(List<Integer> groupIds, String upToPlatformIds, String platformType) {
        log.debug("组合上架-----------组合ID:{},平台ids{},管理类型{}", groupIds, upToPlatformIds, platformType);
        return doPut(groupIds, upToPlatformIds, platformType, Platform.PUT_STATUS_UP) > 0;
    }

    @Override
    public boolean putDown(List<Integer> groupIds, String downToPlatformIds, String platformType) {
        log.debug("组合下架-----------组合ID:{},平台ids{},管理类型{}", groupIds, downToPlatformIds, platformType);
        return doPut(groupIds, downToPlatformIds, platformType, Platform.PUT_STATUS_DOWN) > 0;
    }

    private int doPut(List<Integer> groupIds, String platformId, String platformType, Integer putType) {
        List<GroupProduct> groups = groupProductService.getInfoByIds(groupIds).getList();
        if (PLATFORM_CHANNEL.equals(platformType)) {
            //渠道上下架
//            List<PlatformGroupRel> list = groupIds.stream()
//                    .map(groupId -> PlatformGroupRel.builder()
//                            .putawayState(putType)
//                            .productGroupId(groupId)
//                            .gmtModifier(new Date())
//                            .build()).collect(Collectors.toList());
//            return platform2bGroupRelService.updateSelectedRelsByGroupId(list);

            if (StringUtils.isNoneBlank(platformId)) {
                List<PlatformGroupRel> list = setGroupRelList(groups, platformId, putType);

                return platform2bGroupRelService.updateSelectedRelsByGroupId(list);
            }
        }
        if (PLATFORM_ONLINE.equals(platformType)) {
            //线上上下架
            if (StringUtils.isNoneBlank(platformId)) {
                List<PlatformGroupRel> list = setGroupRelList(groups, platformId, putType);

                return platform2cGroupRelService.updateSelectedRelsByGroupId(list);
            }
        }
        return -1;
    }

    private List<PlatformGroupRel> setGroupRelList(List<GroupProduct> groups, String platformId, Integer putType) {
        List<PlatformGroupRel> list = new ArrayList<>();
        Arrays.stream(platformId.split(Punctuation.COMMA)).filter(StringUtils::isNoneBlank)
                .forEach(platformIdTmp ->
                        groups.forEach(group -> list.add(PlatformGroupRel.builder()
                                .putawayState(putType)
                                .gmtModifier(new Date())
                                .productGroupId(group.getId().intValue())
                                .picId(group.getPicId())
                                .picIds(group.getPicIds())
                                .description(group.getDescription())
                                .salePrice(group.getGroupPrice())
                                .advicePrice(group.getAdvicePrice())
                                .platformId(Integer.parseInt(platformIdTmp.trim()))
                                .build())));
        return list;
    }


}
