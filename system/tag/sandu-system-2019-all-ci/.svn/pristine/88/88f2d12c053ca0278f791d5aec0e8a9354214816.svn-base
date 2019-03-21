package com.sandu.service.grouppurchase.service.impl.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.sandu.api.grouppurchase.GroupPurchaseBizException;
import com.sandu.api.grouppurchase.GroupPurchaseOpenEndException;
import com.sandu.api.grouppurchase.bo.*;
import com.sandu.api.grouppurchase.input.*;
import com.sandu.api.grouppurchase.model.*;
import com.sandu.api.grouppurchase.output.*;
import com.sandu.api.grouppurchase.service.GroupPurchaseGoodsService;
import com.sandu.api.grouppurchase.service.GroupPurchaseOpenDetailService;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseBizService;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.model.UserProductCollect;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.tool.UniqueIDGenerater;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.order.OrderProduct;
import com.sandu.service.grouppurchase.dao.*;
import com.sandu.service.user.dao.UserProductCollectMapper;
import com.sandu.system.model.ResPic;
import com.sandu.util.Dates;
import com.sandu.util.NumberUtil;
import com.sandu.util.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.grouppurchase.GroupPurchaseConstance.PAY_STATUS_CANCEL;
import static com.sandu.api.grouppurchase.GroupPurchaseConstance.PAY_STATUS_SUCCESS;

/**
 * @author Sandu
 * @ClassName GroupPurchaseBizServiceImpl
 * @date 2018/11/6
 */
@Slf4j
@Service("groupPurchaseBizService")
public class GroupPurchaseBizServiceImpl implements GroupPurchaseBizService {

    private final ResPicService resPicService;

    private final GroupPurchaseGoodsMapper groupPurchaseGoodsMapper;

    private final SysUserService sysUserService;

    private final GroupPurchaseOpenMapper groupPurchaseOpenMapper;

    private final GroupPurchaseOpenDetailMapper groupPurchaseOpenDetailMapper;

    private final UserProductCollectMapper userProductCollectMapper;

    private final GroupPurchaseOpenDetailService groupPurchaseOpenDetailService;

    private final GroupPurchaseGoodsService groupPurchaseGoodsService;

    private final UniqueIDGenerater uniqueIDGenerater = new UniqueIDGenerater();

    private final MallBaseOrderMapper mallBaseOrderMapper;

    private final GroupPurchaseActivityMapper groupPurchaseActivityMapper;

    private final ActivityCouponMapper activityCouponMapper;

    private final BaseGoodsSkuMapper baseGoodsSkuMapper;

    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");


    @Autowired
    public GroupPurchaseBizServiceImpl(GroupPurchaseGoodsMapper groupPurchaseGoodsMapper, SysUserService sysUserService, GroupPurchaseOpenMapper groupPurchaseOpenMapper, GroupPurchaseOpenDetailMapper groupPurchaseOpenDetailMapper, UserProductCollectMapper userProductCollectMapper, GroupPurchaseOpenDetailService groupPurchaseOpenDetailService, GroupPurchaseGoodsService groupPurchaseGoodsService, MallBaseOrderMapper mallBaseOrderMapper, GroupPurchaseActivityMapper groupPurchaseActivityMapper, ResPicService resPicService, ActivityCouponMapper activityCouponMapper, BaseGoodsSkuMapper baseGoodsSkuMapper) {
        this.groupPurchaseGoodsMapper = groupPurchaseGoodsMapper;
        this.sysUserService = sysUserService;
        this.groupPurchaseOpenMapper = groupPurchaseOpenMapper;
        this.groupPurchaseOpenDetailMapper = groupPurchaseOpenDetailMapper;
        this.userProductCollectMapper = userProductCollectMapper;
        this.groupPurchaseOpenDetailService = groupPurchaseOpenDetailService;
        this.groupPurchaseGoodsService = groupPurchaseGoodsService;
        this.mallBaseOrderMapper = mallBaseOrderMapper;
        this.groupPurchaseActivityMapper = groupPurchaseActivityMapper;
        this.resPicService = resPicService;
        this.activityCouponMapper = activityCouponMapper;
        this.baseGoodsSkuMapper = baseGoodsSkuMapper;
    }

    /**
     * 获取活动商品的详情
     *
     * @param id        base_goods_spu.id
     * @param loginUser
     * @return
     */
    @Override
    public ResponseEnvelope<GroupPurchaseGoodsDetailVO> getGoodsDetail(Long id, Long activityId, Long groupId, LoginUser loginUser) {
        GroupPurchaseGoodsDetailVO goodsDetailVO = new GroupPurchaseGoodsDetailVO();
        GroupPurchaseGoodsDetailBO goodsDetailBO = groupPurchaseGoodsMapper.getGoodsDetail(id, activityId);
        if (goodsDetailBO == null) {
            log.error("通过 id => {} 找不到商品详情.", id);
            return new ResponseEnvelope<>(false, goodsDetailVO);
        }

        // 轮播图
        List<Integer> picIdList = new ArrayList<>(0);
        if (!StringUtils.isEmpty(goodsDetailBO.getPicIds())) {
            picIdList = Splitter.on(",").omitEmptyStrings().splitToList(goodsDetailBO.getPicIds())
                    .stream().map(Integer::valueOf).collect(Collectors.toList());
        }

        // 商品详情的轮播图，按后台保存的顺序展示
        if (picIdList.size() > 0) {
            List<ResPic> pics = resPicService.getResPicByIds(picIdList);
            List<String> picPathList = new ArrayList<>();
            loop:
            for (Integer picId : picIdList) {
                for (ResPic resPic : pics) {
                    if (resPic.getId().equals(picId)) {
                        picPathList.add(resPic.getPicPath());
                        continue loop;
                    }
                }
            }
            goodsDetailVO.setSmallPiclist(picPathList);
        }

        goodsDetailVO.setLimitation(goodsDetailBO.getLimitation());
        goodsDetailVO.setPrice(StringUtils.isEmpty(goodsDetailBO.getPrice()) ? "0"
                : new BigDecimal(goodsDetailBO.getPrice()).setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsDetailVO.setSalePrice(StringUtils.isEmpty(goodsDetailBO.getSalePrice()) ? "0"
                : new BigDecimal(goodsDetailBO.getSalePrice()).setScale(2, BigDecimal.ROUND_DOWN).toString());
        // 活动价
        goodsDetailVO.setActivityPrice(StringUtils.isEmpty(goodsDetailBO.getActivityPrice()) ? "0"
                : new BigDecimal(goodsDetailBO.getActivityPrice()).setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsDetailVO.setProductName(goodsDetailBO.getSpuName());
        goodsDetailVO.setProductDesc(goodsDetailBO.getProductDesc());
        goodsDetailVO.setSendOutDay(goodsDetailBO.getDeliveryDay());
        goodsDetailVO.setSpuId(goodsDetailBO.getId());
        goodsDetailVO.setTransportPay(goodsDetailBO.getTransportPay() == null ? "0.00" :
                goodsDetailBO.getTransportPay().setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsDetailVO.setSendOutPresell(goodsDetailBO.getDeliveryPresell());
        goodsDetailVO.setSendOutDate(goodsDetailBO.getDeliveryDate());


        // 收藏列表结果 0 : 未收藏，1：已收藏
        if (loginUser != null) {
            UserProductCollect collect = new UserProductCollect();
            collect.setUserId(loginUser.getId());
            collect.setSpuId(id);
            List<UserProductCollect> userProductCollect = userProductCollectMapper.listUserProductCollect(collect);
            goodsDetailVO.setIsFavorite(userProductCollect.isEmpty() ? 0 : 1);
        }

        // 活动信息
        GroupPurchaseActivityVO goodsActivityVO = this.getGoodsActivity(activityId, groupId);
        goodsDetailVO.setActivity(goodsActivityVO);

        return new ResponseEnvelope<>(true, goodsDetailVO);
    }

    @Override
    public GroupPurchaseActivityVO getGoodsActivity(Long activityId, Long groupId) {
        // 活动信息
        GroupGoodsActivityBO goodsActivityBO = groupPurchaseGoodsMapper.getGoodsActivity(activityId);
        if (goodsActivityBO == null) {
            log.info("活动ID => {} 未有相关的活动信息", activityId);
            return null;
        }

        GroupPurchaseActivityVO goodsActivityVO = new GroupPurchaseActivityVO();
        goodsActivityVO.setActivityId(goodsActivityBO.getActivityId());
        goodsActivityVO.setActivityName(goodsActivityBO.getActivityName());
        goodsActivityVO.setSpuId(goodsActivityBO.getSpuId());
        goodsActivityVO.setActivityStartTime(goodsActivityBO.getActivityStartTime());
        goodsActivityVO.setActivityEndTime(goodsActivityBO.getActivityEndTime());
        Date now = new Date();
        // 距离活动开始剩余时间
        // long howLongStartTime = Dates.diff(goodsActivityBO.getActivityStartTime(), now);
        // goodsActivityVO.setHowLongStartTime(howLongStartTime <= 0 ? 0 : howLongStartTime);
        goodsActivityVO.setHowLongStartTime(Dates.diff(goodsActivityBO.getActivityStartTime(), now));
        // 距离活动结束剩余时间
        // long howLongEndTime = Dates.diff(goodsActivityBO.getActivityEndTime(), now);
        // goodsActivityVO.setHowLongEndTime(howLongEndTime <= 0 ? 0 : howLongEndTime);
        goodsActivityVO.setHowLongEndTime(Dates.diff(goodsActivityBO.getActivityEndTime(), now));

        goodsActivityVO.setGroupValidDay(goodsActivityBO.getGroupValidDay());
        goodsActivityVO.setGroupValidHour(goodsActivityBO.getGroupValidHour());
        goodsActivityVO.setGroupValidMinute(goodsActivityBO.getGroupValidMinute());
        goodsActivityVO.setTotalNumber(goodsActivityBO.getTotalNumber());
        goodsActivityVO.setPurchaseLimitAmount(goodsActivityBO.getPurchaseLimitAmount());
        goodsActivityVO.setGatherFlag(goodsActivityBO.getGatherFlag());
        goodsActivityVO.setVirtualFlag(goodsActivityBO.getVirtualFlag());
        goodsActivityVO.setCouponFlag(goodsActivityBO.getCouponFlag());
        goodsActivityVO.setActivityStatus(goodsActivityBO.getActivityStatus() != null ? goodsActivityBO.getActivityStatus().intValue() : null);
        // 修复定时器没有及时更新活动状态问题
        if (Objects.equals(goodsActivityBO.getActivityStatus(), (byte) 1) && goodsActivityVO.getHowLongStartTime() <= 0) {
            goodsActivityVO.setActivityStatus(2);
        }
        if (Objects.equals(goodsActivityBO.getActivityStatus(), (byte) 2) && goodsActivityVO.getHowLongEndTime() <= 0) {
            goodsActivityVO.setActivityStatus(3);
        }

        // 如果是通过参加别的团，进入商品详情的，需要查询开团信息
        // 是否满团：true 满团；false:未满团
        goodsActivityVO.setIsGroupOverflow((groupId != null && groupId > 0) && this.isGroupOverflow(groupId));

        return goodsActivityVO;
    }

    /**
     * 是否满团：true 满团；false:未满团
     *
     * @param groupId
     * @return
     */
    private boolean isGroupOverflow(Long groupId) {
        return groupPurchaseGoodsMapper.isGroupOverflow(groupId) >= 1;
    }

    /**
     * 获取商品属性（真的改不动了，尽力了）
     *
     * @param id base_goods_spu.id
     * @return
     */
    @Override
    public ResponseEnvelope<GoodsSkuVO> getGoodsAttr(Long id, Long activityId) {
        // 查询商品下的所有产品的所有属性
        List<SpuAttributeBO> spuAttrList = groupPurchaseGoodsMapper.getSpuAttrList(id, activityId);

        // 属于该商品的产品列表
        List<SkuPriceAndAttrBO> paList = groupPurchaseGoodsMapper.getSkuPriceAndAttr(id, activityId);
        if (paList == null || paList.size() == 0) {
            return new ResponseEnvelope<>(false, "该商品下的产品列表为空");
        }

        // 所有产品的价格集合
        List<BigDecimal> priceList = new ArrayList<>();
        for (SkuPriceAndAttrBO skuPriceAndAttrBO : paList) {
            priceList.add(skuPriceAndAttrBO.getPrice() == null ? new BigDecimal("0") : skuPriceAndAttrBO.getPrice());
        }

        // 找到价格最低的索引
        int minIndex = NumberUtil.findMinIndex(priceList, new BigDecimal("0"), false);
        if (minIndex == -1) {
            minIndex = NumberUtil.findMinIndex(priceList, new BigDecimal("0"), true);
            if (minIndex == -1) {
                minIndex = 0;
            }
        }

        // 价格最低的产品
        SkuPriceAndAttrBO pa = paList.get(minIndex);
        List<Integer> attrValueIds = new ArrayList<>();
        if (!StringUtils.isEmpty(pa.getAttrValueIds())) {
            attrValueIds = Splitter.on(",").omitEmptyStrings().splitToList(pa.getAttrValueIds())
                    .stream().map(Integer::valueOf).collect(Collectors.toList());
        }

        // 商品下没有属性时，返回默认属性
        if (spuAttrList == null || spuAttrList.size() == 0) {
            GoodsSkuVO defaultGoodsAttrVO = getDefaultGoodsAttr(pa);
            // 处理选中的状态，默认最低价为选中（给前端判断显示用的）
            this.handlerSelectedAttr(defaultGoodsAttrVO, attrValueIds, defaultGoodsAttrVO.getAttr());

            return new ResponseEnvelope<>(true, defaultGoodsAttrVO);
        }

        // 有属性是返回属性列表
        GoodsSkuVO goodsSkuVO = this.getGoodsSkuVO(pa, spuAttrList, attrValueIds);

        return new ResponseEnvelope<>(true, goodsSkuVO);
    }

    /**
     * 有属性是返回属性列表
     *
     * @param listAttrBO
     * @param listAttrId
     * @return
     */
    public GoodsSkuVO getGoodsSkuVO(SkuPriceAndAttrBO pa, List<SpuAttributeBO> listAttrBO, List<Integer> listAttrId) {
        List<SpuAttributeVO> spuAttributeVOList = new ArrayList<>();
        loop:
        for (SpuAttributeBO spuAttributeBO : listAttrBO) {
            // 处理子属性
            for (SpuAttributeVO spuAttributeVO : spuAttributeVOList) {
                if (spuAttributeVO.getAttrId().equals(spuAttributeBO.getAttrId())) {
                    SpuAttrValueVO newSpuAttrValueVO = new SpuAttrValueVO();
                    newSpuAttrValueVO.setAttrValueName(spuAttributeBO.getAttrValueName());
                    newSpuAttrValueVO.setAttrValueId(spuAttributeBO.getAttrValueId());
                    newSpuAttrValueVO.setIsSelected(0);
                    spuAttributeVO.getAttrValue().add(newSpuAttrValueVO);

                    continue loop;
                }
            }

            // 处理父属性
            SpuAttributeVO attrNew = new SpuAttributeVO();
            attrNew.setAttrId(spuAttributeBO.getAttrId());
            attrNew.setAttrName(spuAttributeBO.getAttrName());
            attrNew.setAttrValue(new ArrayList<>());

            SpuAttrValueVO attrValueNew = new SpuAttrValueVO();
            attrValueNew.setAttrValueName(spuAttributeBO.getAttrValueName());
            attrValueNew.setAttrValueId(spuAttributeBO.getAttrValueId());
            attrValueNew.setIsSelected(0);
            attrNew.getAttrValue().add(attrValueNew);
            spuAttributeVOList.add(attrNew);
        }

        GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
        // 处理选中的状态，默认最低价为选中（给前端判断显示用的）
        this.handlerSelectedAttr(goodsSkuVO, listAttrId, spuAttributeVOList);

        goodsSkuVO.setInventory(pa.getInventory());
        goodsSkuVO.setSalePrice(pa.getSalePrice() == null ? "0" : pa.getSalePrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                pa.getSalePrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsSkuVO.setPrice(pa.getPrice() == null ? "0" : pa.getPrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                pa.getPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
        // 活动价
        goodsSkuVO.setActivityPrice(pa.getActivityPrice() == null ? "0" : pa.getActivityPrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                pa.getActivityPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsSkuVO.setProductName(pa.getProductName());
        goodsSkuVO.setProductPicPath(pa.getSpePicPath() == null || "".equals(pa.getSpePicPath()) ? pa.getDefaultPicPath() : pa.getSpePicPath());
        goodsSkuVO.setProductId(pa.getProductId());
        goodsSkuVO.setSkuId(pa.getSkuId());
        goodsSkuVO.setAttr(spuAttributeVOList);
        goodsSkuVO.setTransportMoney(pa.getTransportMoney() == null ? "0.00" : "" +
                pa.getTransportMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());

        return goodsSkuVO;
    }

    @Override
    public ResponseEnvelope<GoodsSkuVO> getSkuInfoByAttrs(GoodsSkuQuery query) {
        List<GoodsSkuBO> listSku = groupPurchaseGoodsMapper.getSkusBySpuId(query);
        if (listSku == null || listSku.isEmpty()) {
            log.warn("通过 query => {} 未找到SKU信息.", query);
            return new ResponseEnvelope<>(false, "未找到该商品的SKU信息");
        }

        List<Integer> attrValIds = new ArrayList<>();
        for (GoodsSkuBO sku : listSku) {
            List<Integer> skuAttrIds = new ArrayList<>();
            try {
                skuAttrIds = NumberUtil.convertStrToNumList(sku.getAttributeValueIds(), Integer.class);
                attrValIds = NumberUtil.convertStrToNumList(query.getAttrValueIds(), Integer.class);
            } catch (Exception e) {
                log.error("获取SKU => {}属性异常", sku, e);
            }

            if (skuAttrIds.size() == 0) {
                skuAttrIds.add(0);
            }

            Integer flag = 0;
            for (Integer valId : attrValIds) {
                flag += (int) skuAttrIds.stream().filter(valId::equals).count();
            }

            if (flag == skuAttrIds.size() && flag == attrValIds.size()) {
                GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
                goodsSkuVO.setSkuId(sku.getSkuId());
                goodsSkuVO.setInventory(sku.getInventory() == null ? 0 : sku.getInventory());
                goodsSkuVO.setPrice(sku.getPrice() == null ? "0" : sku.getPrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                        sku.getPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
                goodsSkuVO.setSalePrice(sku.getSalePrice() == null ? "0" : sku.getSalePrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                        sku.getSalePrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
                // 活动价
                goodsSkuVO.setActivityPrice(sku.getActivityPrice() == null ? "0" : sku.getActivityPrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                        sku.getActivityPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
                goodsSkuVO.setProductId(sku.getProductId());
                goodsSkuVO.setProductName(sku.getProductName());
                goodsSkuVO.setProductPicPath(this.getSkuSmallPic(sku));
                goodsSkuVO.setAttribute(sku.getAttributeValueNames() == null ? "默认" : sku.getAttributeValueNames());
                goodsSkuVO.setTransportMoney(sku.getTransportMoney() == null ? "0.00" :
                        sku.getTransportMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
                goodsSkuVO.setProductSpec(sku.getProductSpec());
                goodsSkuVO.setProductModelNumber(sku.getProductModelNumber());
                goodsSkuVO.setBrandName(sku.getBrandName());

                return new ResponseEnvelope<>(true, goodsSkuVO);
            }
        }

        return new ResponseEnvelope<>(true, "未找到该商品的SKU信息");
    }

    @Override
    public ResponseEnvelope<GroupPurchaseGatherVO> listGather(GroupPurchaseGatherQuery query) {
        PageHelper.startPage(query.getOffset(), query.getLimit());
        List<GroupPurchaseGatherBO> listGatherBO = groupPurchaseGoodsMapper.listGather(query);

        PageInfo<GroupPurchaseGatherBO> pageInfo = new PageInfo<>(listGatherBO);
        if (pageInfo.getTotal() <= 0 || listGatherBO == null || listGatherBO.isEmpty()) {
            log.info("凑团列表：通过query => {}未找到凑团列表信息.", query);
            return new ResponseEnvelope<>(true, "暂无凑团");
        }

        // 用户头像
        // List<Long> listPhoto = listGatherBO.stream().filter(bo -> bo.getUserPhotoId() != null)
        //       .map(GroupPurchaseGatherBO::getUserPhotoId).collect(Collectors.toList());
        // Map<Long, String> mapPic = resPicService.mapPic(listPhoto);

        List<GroupPurchaseGatherVO> listGatherVO = new ArrayList<>();
        for (GroupPurchaseGatherBO gatherBO : listGatherBO) {
            GroupPurchaseGatherVO gatherVO = new GroupPurchaseGatherVO();
            try {
                BeanUtils.copyProperties(gatherVO, gatherBO);
            } catch (Exception e) {
                log.error("凑团列表：通过query => {}查询凑团列表数据异常", e);
            }

            // 用户头像
            // gatherVO.setUserPhoto(mapPic.get(gatherBO.getUserPhotoId()));

            // Strings.secrecy(gatherVO.getNickName(), 4, 4, "");
            gatherVO.setTelephone(Strings.secrecy(gatherVO.getTelephone(), 4, 4, Strings.DEFAULT_REPLACE));

            Date now = new Date();
            // long howLongOpenDate = Dates.diff(gatherBO.getOpenDate(), now);
            // gatherVO.setHowLongOpenDate(howLongOpenDate <= 0 ? 0 : howLongOpenDate);
            gatherVO.setHowLongOpenDate(Dates.diff(gatherBO.getOpenDate(), now));
            // long howLongExpireDate = Dates.diff(gatherBO.getExpireDate(), now);
            // gatherVO.setHowLongExpireDate(howLongExpireDate <= 0 ? 0 :howLongExpireDate);
            gatherVO.setHowLongExpireDate(Dates.diff(gatherBO.getExpireDate(), now));

            listGatherVO.add(gatherVO);
        }

        return new ResponseEnvelope<>(true, pageInfo.getTotal(), listGatherVO);
    }

    /**
     * 获取属性的图片
     *
     * @param sku
     * @return
     */
    private String getSkuSmallPic(GoodsSkuBO sku) {
        if (sku.getSpePicId() == null || sku.getSpePicId() <= 0) {
            if (sku.getPicId() != null && sku.getPicId() > 0) {
                ResPic pic = resPicService.get(sku.getPicId());
                if (pic != null) {
                    return pic.getPicPath();
                }
            }
        } else {
            ResPic pic = resPicService.get(sku.getSpePicId());
            if (pic != null) {
                return pic.getPicPath();
            }
        }

        return "";
    }

    /**
     * 获取默认属性
     *
     * @param pa
     * @return
     */
    private GoodsSkuVO getDefaultGoodsAttr(SkuPriceAndAttrBO pa) {
        GoodsSkuVO goodsSkuVO = new GoodsSkuVO();

        SpuAttributeVO spuAttributeVO = new SpuAttributeVO();
        List<SpuAttrValueVO> attrValueList = new ArrayList<>();
        SpuAttrValueVO attrValue = new SpuAttrValueVO();
        attrValue.setAttrValueId(0);
        attrValue.setAttrValueName("默认");
        attrValue.setIsSelected(1);
        attrValueList.add(attrValue);

        spuAttributeVO.setAttrId(0);
        spuAttributeVO.setAttrName("默认");
        spuAttributeVO.setAttrValue(attrValueList);

        goodsSkuVO.setInventory(pa.getInventory());
        goodsSkuVO.setSalePrice(pa.getSalePrice() == null ? "0" : pa.getSalePrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                pa.getSalePrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsSkuVO.setPrice(pa.getPrice() == null ? "0" : pa.getPrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                pa.getPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
        // 活动价
        goodsSkuVO.setActivityPrice(pa.getActivityPrice() == null ? "0" : pa.getActivityPrice().compareTo(new BigDecimal("0")) == 0 ? "0" :
                pa.getActivityPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
        goodsSkuVO.setProductName(pa.getProductName());
        goodsSkuVO.setProductPicPath(pa.getSpePicPath() == null || "".equals(pa.getSpePicPath()) ? pa.getDefaultPicPath() : pa.getSpePicPath());
        goodsSkuVO.setProductId(pa.getProductId());
        goodsSkuVO.setSkuId(pa.getSkuId());
        goodsSkuVO.setAttr(Lists.newArrayList(spuAttributeVO));
        goodsSkuVO.setTransportMoney(pa.getTransportMoney() == null ? "0.00" : "" +
                pa.getTransportMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());

        return goodsSkuVO;
    }


    /**
     * 处理选中的状态，默认最低价为选中（给前端判断显示用的）
     *
     * @param goodsSkuVO
     */
    private void handlerSelectedAttr(GoodsSkuVO goodsSkuVO, List<Integer> listAttr, List<SpuAttributeVO> listAttrVO) {
        // 选中价格最低的产品的属性
        StringBuilder buf = new StringBuilder();
        for (Integer attrValueId : listAttr) {
            for (SpuAttributeVO vo : listAttrVO) {
                for (SpuAttrValueVO attrVo : vo.getAttrValue()) {
                    if (attrVo.getAttrValueId().equals(attrValueId)) {
                        attrVo.setIsSelected(1);
                        buf.append("".equals(buf.toString()) ? attrVo.getAttrValueName() : "," + attrVo.getAttrValueName());
                    }
                }
            }
        }

//        for (SpuAttributeVO spuAttributeVO : goodsSkuVO.getAttr()) {
//            for (SpuAttrValueVO spuAttrValueVO : spuAttributeVO.getAttrValue()) {
//                if (spuAttrValueVO.getIsSelected() == 1) {
//                    buf.append("".equals(buf.toString()) ? spuAttrValueVO.getAttrValueName() : "," + spuAttrValueVO.getAttrValueName());
//                }
//            }
//        }

        goodsSkuVO.setAttribute(buf.toString());
    }

    @Override
    public List<UserPurchaseListBO> listPurchaseByUserId(GroupPurchaseOpenQuery query) {
        query.setJoinStatus(2);
        //获取用户所选购的拼团商品信息
        List<UserPurchaseListBO> list = groupPurchaseOpenDetailService.query(query);

        //获取拼团用户信息
        setInnerPurchaseUserInfo(list);

        fetchUserPicId(list);
        return list;
    }

    private void fetchUserPicId(List<UserPurchaseListBO> bos) {
        if (bos == null) {
            return;
        }
        for (UserPurchaseListBO bo : bos) {
            List<PurchaseInnerUserInfo> collect = bo.getInnerUserInfoList().stream().filter(it -> it.getUserId() == -1)
                    .collect(Collectors.toList());
            for (int i = 0; i < collect.size(); i++) {
                PurchaseInnerUserInfo tmp = collect.get(i);
                String s = bo.getPurchaseOpenId() + "_" + bo.getActivityId() + "_" + i + bo.getSpuId();
                int picId = s.hashCode() % 6;
                tmp.setPicId((long) (picId < 0 ? picId * -1 : picId));
            }
        }
    }

    private void setInnerPurchaseUserInfo(@NotNull List<UserPurchaseListBO> list) {
        if (list.isEmpty()) {
            return;
        }
        List<Long> allOpenIds = list.stream().map(UserPurchaseListBO::getPurchaseOpenId).collect(Collectors.toList());

        List<GroupPurchaseOpenDetail> allDetails = groupPurchaseOpenDetailService.listByPurchaseOpenIds(allOpenIds).stream().filter(it -> !Objects.equals(it.getJoinStatus(), 0)).collect(Collectors.toList());

        List<ResPic> pics = resPicService.getResPicByIds(list.stream().map(UserPurchaseListBO::getPicId).filter(Objects::nonNull).collect(Collectors.toList()));
        pics = pics == null ? Collections.emptyList() : pics;

        Date now = new Date();
        for (UserPurchaseListBO bo : list) {
            bo.setExpireTime(bo.getExpireDate().getTime() - now.getTime());
            bo.setStartTime(bo.getStartDate().getTime() - now.getTime());
            List<PurchaseInnerUserInfo> userInfoList = allDetails.stream()
                    .filter(it -> it.getPurchaseOpenId().equals(bo.getPurchaseOpenId()))
                    .map(it -> {
                        PurchaseInnerUserInfo tmp = new PurchaseInnerUserInfo();
                        tmp.setUserId(it.getUserId());
                        tmp.setJoinTime(it.getGmtCreate());
                        tmp.setIsMaster(it.getIsMaster().longValue());
                        tmp.setJoinStatus(it.getJoinStatus().longValue());
                        return tmp;
                    })
                    .collect(Collectors.toList());

            //设置参团用户信息
            List<Long> userIds = userInfoList.stream().map(PurchaseInnerUserInfo::getUserId).distinct().collect(Collectors.toList());
            List<SysUser> users = groupPurchaseOpenDetailMapper.listGroupUserDetail(userIds);


            for (SysUser user : users) {
                userInfoList.stream().filter(it -> it.getUserId().equals(user.getId())).findFirst()
                        .ifPresent(tmp -> {
                            tmp.setUserId(user.getId());
                            tmp.setUserName(user.getNickName());
//                            tmp.setPicPath(user.getHeadPic());
                        });
            }
            userInfoList.sort(Comparator.comparing(PurchaseInnerUserInfo::getJoinTime));
            bo.setInnerUserInfoList(userInfoList);

            //设置参团商品图片
            pics.stream()
                    .filter(it -> Objects.equals(bo.getPicId(), it.getId()))
                    .findFirst()
                    .ifPresent(it -> bo.setPicPath(it.getPicPath()));
        }
    }

    @Override
    public UserPurchaseListBO getPurchaseDetail(Long purchaseId) {
        GroupPurchaseOpenQuery query = new GroupPurchaseOpenQuery();
        query.setPurchaseOpenId(purchaseId);
        query.setLimit(1);

        List<UserPurchaseListBO> list = groupPurchaseOpenDetailService.query(query);
        if (list.isEmpty()) {
            log.info("获取拼团信息失败, purchaseOpenId:" + purchaseId);
            throw new GroupPurchaseBizException("获取拼团信息失败");
        }

        //设置参团商品默认展示的sku信息()
        UserPurchaseListBO bo = list.get(0);

        List<PurchaseGoodSkuBO> skus = groupPurchaseGoodsService.getSkuInfoBySpuId(bo.getSpuId(), bo.getActivityId().longValue());

        skus.stream().min(Comparator.comparing(PurchaseGoodSkuBO::getGmtCreate))
                .ifPresent(sku -> {
                    bo.setActivityPrice(sku.getActivePrice());
                    bo.setBasePrice(sku.getBasePrice());
                    if (sku.getPic() != null) {
                        bo.setPicId(sku.getPic());
                    }
                });

        // this.setInnerPurchaseUserInfo(Lists.newArrayList(bo));

        // Modified by songjianming@sanduspace.cn on 2018/12/18
        // 修复参团用户过多时，导致查询用户信息超时问题
        this.handlerInnerPurchaseUserInfo(bo);

        fetchUserPicId(Collections.singletonList(bo));
        return bo;
    }

    /**
     * 获取参团用户信息
     *
     * @param bo
     */
    private void handlerInnerPurchaseUserInfo(UserPurchaseListBO bo) {
        Date now = new Date();
        bo.setExpireTime(bo.getExpireDate().getTime() - now.getTime());
        bo.setStartTime(bo.getStartDate().getTime() - now.getTime());

        // 图片
        ResPic resPic = resPicService.get(bo.getPicId());
        Optional.of(resPic).ifPresent(pic -> bo.setPicPath(pic.getPicPath()));

        List<GroupPurchaseOpenDetail> listOpenDetail = groupPurchaseOpenDetailService.listByPurchaseOpenIds(Lists.newArrayList(bo.getPurchaseOpenId()));
        if (listOpenDetail != null && !listOpenDetail.isEmpty()) {
            // 获取用户信息
            List<Long> listUserID = listOpenDetail.stream().filter(u -> u.getUserId() != null)
                    .map(GroupPurchaseOpenDetail::getUserId).collect(Collectors.toList());
            Map<Long, SysUser> mapUser = new HashMap<>();
            if (!listUserID.isEmpty()) {
                List<SysUser> listUser = groupPurchaseOpenDetailMapper.listGroupUserDetail(listUserID);
                mapUser = listUser.stream().collect(Collectors.toMap(SysUser::getId, u -> u, (p, n) -> n));
            }

            List<PurchaseInnerUserInfo> userInfoList = new ArrayList<>();
            for (GroupPurchaseOpenDetail detail : listOpenDetail) {

                if (detail.getJoinStatus() != 2) {
                    continue;
                }
                PurchaseInnerUserInfo tmp = new PurchaseInnerUserInfo();
                tmp.setUserId(detail.getUserId());
                tmp.setJoinTime(detail.getGmtCreate());
                tmp.setIsMaster(detail.getIsMaster().longValue());
                tmp.setJoinStatus(detail.getJoinStatus().longValue());
                // 用户信息
                Optional.ofNullable(mapUser.get(detail.getUserId())).ifPresent(user -> {
                    tmp.setUserId(user.getId());
                    tmp.setUserName(user.getNickName());
//                    tmp.setPicPath(user.getHeadPic());
                });

                userInfoList.add(tmp);
            }

            userInfoList.sort(Comparator.comparing(PurchaseInnerUserInfo::getJoinTime));
            bo.setInnerUserInfoList(userInfoList);
        }
    }

    @Override
    public List<GoodsSkuVO> getActivitySku(GoodsSkuQuery query) {

        List<GoodsSkuVO> listSkuVOS = new ArrayList<>();
        List<GoodsSkuBO> listSku = groupPurchaseGoodsMapper.getActivitySkuBySpuId(query.getSpuId());
        if (listSku == null || listSku.isEmpty()) {
            log.warn("通过 query => {} 未找到SKU信息.", query);
            return null;
        }

        listSku.forEach(result -> {
            GoodsSkuVO goodsSkuVO = new GoodsSkuVO();
            org.springframework.beans.BeanUtils.copyProperties(result, goodsSkuVO);
            goodsSkuVO.setPrice(result.getPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
            listSkuVOS.add(goodsSkuVO);
        });
        return listSkuVOS;
    }

    @Override
    public int createActivitySku(GroupPurchaseGoodsAdd input) {
        int result = 0;
        for (int i = 0; i < input.getActivityPrices().size(); i++) {
            GroupPurchaseGoods goods = new GroupPurchaseGoods();
            goods.setPurchaseActivityId(input.getPurchaseActivityId());
            goods.setSpuId(input.getSpuId());
            //若没有设置拼团价，则不创建
            if (input.getActivityPrices().get(i) == null || input.getActivityPrices().get(i) == 0) {
                continue;
            }
            goods.setActivityPrice(BigDecimal.valueOf(input.getActivityPrices().get(i)));
            goods.setSkuId(input.getSkuIds().get(i).longValue());
            goods.setQty(BigDecimal.valueOf(input.getQtys().get(i)));
            goods.setUseQty(BigDecimal.valueOf(input.getQtys().get(i)));
            SysUser creator = sysUserService.getUserById(Long.valueOf(input.getCreator()));
            goods.setCreator(creator.getUserName());
            result = groupPurchaseGoodsMapper.insertSelective(goods);
        }
        return result;
    }

    @Override
    public int updateActivitySku(GroupPurchaseGoodsUpdate input) {
        Integer result = groupPurchaseGoodsMapper.removeByActivityId(input.getPurchaseActivityId());
        GroupPurchaseGoodsAdd add = new GroupPurchaseGoodsAdd();
        org.springframework.beans.BeanUtils.copyProperties(input, add);
        return createActivitySku(add);
    }

    @Override
    @Transactional
    public MallBaseOrder initGroupPurchaseOrder(GroupPurchaseOrderBO orderBO) {
        log.debug("------{}------", orderBO);
        //校验是否为待付款订单
        MallBaseOrder preOrder = validExistOrder(orderBO);
        if (preOrder != null) {
            return preOrder;
        }


        //校验参团活动状态
        GroupPurchaseActivity activityInfo = groupPurchaseActivityMapper.getById(orderBO.getPurchaseActiveId());
        if (activityInfo == null) {
            log.info("获取拼团信息失败，openId => {}", orderBO.getPurchaseOpenId());
            throw new GroupPurchaseBizException("获取拼团信息失败");
        }
        if (activityInfo.getActivityEndTime().before(new Date()) || !Objects.equals(activityInfo.getActivityStatus(), (byte) 2)) {
            log.info("活动已结束或失效, activityId => {}", activityInfo.getId());
            throw new GroupPurchaseBizException("活动已结束或失效");
        }


        //校验参团状态
        GroupPurchaseOpen groupPurchaseOpen = null;
        if (orderBO.getIsMaster() == 0) {
            groupPurchaseOpen = groupPurchaseOpenMapper.selectByPrimaryKey(orderBO.getPurchaseOpenId());
            if (groupPurchaseOpen == null) {
                log.info("获取开团信息失败,openId => {}", orderBO.getPurchaseOpenId());
                throw new GroupPurchaseBizException("参加的团不存在，请选择其它团");
            }
            if (Objects.equals(groupPurchaseOpen.getJoinNumber(), groupPurchaseOpen.getTotalNumber())) {
                log.info("参加的团已满团,openId : {}", orderBO.getPurchaseOpenId());
                throw new GroupPurchaseOpenEndException("参加的团已满员");
            }
            if (!Objects.equals(groupPurchaseOpen.getOpenStatus(), (byte) 0)) {
                log.info("开团信息状态异常,openId : {}", orderBO.getPurchaseOpenId());
                throw new GroupPurchaseOpenEndException("参加的团已满员");
            }
        }


        // 校验库存状态 并设置openId
        BaseGoodsSku sku = baseGoodsSkuMapper.selectByPrimaryKey(orderBO.getSkuId().intValue());
        if (sku == null) {
            log.info("商品信息不存在,skuId : {}", orderBO.getSkuId());
            throw new GroupPurchaseBizException("商品信息不存在");
        }
        if (sku.getInventory() < orderBO.getSkuNum()) {
            log.info("库存不足,skuId : {}", orderBO.getSkuId());
            throw new GroupPurchaseBizException("库存不足");
        }

        //获取团购商品信息
        GroupPurchaseGoods goodInfo = getGroupPurchaseGoods(orderBO);

        //校验购买数量
        List<GroupPurchaseOpenDetail> details = groupPurchaseOpenDetailMapper.checkOpenDetailsWithUserIdAndOpenId(orderBO.getUserId(), orderBO.getPurchaseActiveId());
        details.stream().filter(it -> it.getPurchaseOpenId().equals(orderBO.getPurchaseOpenId()))
                .findFirst()
                .ifPresent(it -> {
                    log.info("不可重复参加同一个团 ,openId {}", orderBO.getPurchaseOpenId());
                    throw new GroupPurchaseBizException("不可重复参加同一个团");
                });

        long count = details.stream().filter(it -> it.getPurchaseActivityId().equals(orderBO.getPurchaseActiveId())).count();
        if (activityInfo.getPurchaseLimitAmount() < count + orderBO.getSkuNum()) {
            log.info("已超出此团购活动可购买最大数量 :【" + activityInfo.getPurchaseLimitAmount() + "】,已购数量:【" + count + "】,待购数量:【" + orderBO.getSkuNum() + "】");
            throw new GroupPurchaseBizException("此商品每人限购" + activityInfo.getPurchaseLimitAmount() + "件");
        }

        SysUser sysUser = sysUserService.get(orderBO.getUserId().intValue());
        if (sysUser == null) {
            log.info("获取用户信息失败, userId :" + orderBO.getUserId());
            throw new GroupPurchaseBizException("请先登录");
        }


        Date now = new Date();

        BigDecimal totalPrice = goodInfo.getActivityPrice().multiply(new BigDecimal(orderBO.getSkuNum()));
        //生成订单
        MallBaseOrder order = createMallOrder(orderBO, activityInfo, sysUser, now, totalPrice);

        //同步库存
        groupPurchaseGoodsService.updateQty(sku, true, orderBO.getSkuNum().intValue(), order);


        log.debug("--------->sku:{}", sku);
        if (sku.getInventory() == 0) {
            List<BaseGoodsSku> skus = baseGoodsSkuMapper.selectBySpuId(orderBO.getSpuId());
            long totalCount = skus.stream().map(BaseGoodsSku::getInventory).reduce((o1, o2) -> o1 + o2).orElse(0);
            log.debug("--------->totalCount:{}", totalCount);
            if (totalCount == 0) {
                //库存为空时,关闭活动
                activityInfo.setRemark(activityInfo.getRemark() + ";_库存售尽,活动失效");
                activityInfo.setActivityStatus((byte) 4);
                groupPurchaseActivityMapper.updateByPrimaryKey(activityInfo);
            }

        }

        //同步团购数据
        syncPurchaseOpenData(orderBO, activityInfo, groupPurchaseOpen, goodInfo, now, sysUser, order);

        order.setGroupPurchaseOpenId(orderBO.getPurchaseOpenId());
        return order;
    }

    private MallBaseOrder createMallOrder(GroupPurchaseOrderBO orderBO, GroupPurchaseActivity activityInfo, SysUser sysUser, Date now, BigDecimal totalPrice) {
        MallBaseOrder order = MallBaseOrder.builder()
                .userId(orderBO.getUserId().intValue())
                .orderCode(this.uniqueIDGenerater.nextId() + "")
                .appId(sysUser.getAppId())
                .companyId(orderBO.getCompanyId().intValue())
                .orderStatus(6)
                .shippingStatus(0)
                .payStatus(0)
                .orderType(1)
                .consignee(orderBO.getConsignee())
                .province(orderBO.getProvince())
                .city(orderBO.getCity())
                .district(orderBO.getDistrict())
                .address(orderBO.getAddress())
                .mobile(orderBO.getMobile())
                .gmtCreate(now)
                .gmtModified(now)
                .isDeleted(0)
                .openId(sysUser.getOpenId())
                .activityId(orderBO.getPurchaseActiveId())
                .orderSource(0)
                .shopId(orderBO.getShopId().intValue())
                .orderAmount(totalPrice)
                .franchiserId(orderBO.getFranchiserId().intValue())
                .deliverProvinceCode(orderBO.getDeliverProvinceCode())
                .deliverCityCode(orderBO.getDeliverCityCode())
                .deliverAreaCode(orderBO.getDeliverAreaCode())
                .deliverStreetCode(orderBO.getDeliverStreetCode())
                .deliverAddress(orderBO.getDeliverAddress())
                .deliverAreaLongCode(orderBO.getDeliverAreaLongCode())
                .build();

        //处理优惠券
        if (activityInfo.getCouponFlag() == 1 && orderBO.getCouponId() != null) {
            ActivityCoupon activityCoupon = activityCouponMapper.selectByPrimaryKey(orderBO.getCouponId());
            if (activityCoupon == null) {
                log.info("获取优惠券信息失败,coupon Id :" + orderBO.getCouponId());
                throw new GroupPurchaseBizException("使用的优惠券不存在, 请选择其它优惠券");
            }
            if (activityCoupon.getDiscountMode().equals((short) 1)) {
                totalPrice = totalPrice.subtract(activityCoupon.getDiscountAmount());
            }
            if (activityCoupon.getDiscountMode().equals((short) 2)) {
                totalPrice = totalPrice.multiply(activityCoupon.getRebateFactor().multiply(new BigDecimal(0.1)));
            }
            order.setCouponId(activityCoupon.getId());
            order.setOrderAmount(totalPrice);
        }

        mallBaseOrderMapper.insertSelective(order);


        //生成订单详情
        MallOrderProductRef mprf = mallBaseOrderMapper.getMallOrderProductBySkuId(orderBO.getSkuId());
        mprf.setOrderId(order.getId());
        mprf.setProductNumber(orderBO.getSkuNum().intValue());
        mprf.setGmtCreate(now);
        mprf.setGmtModified(now);
        mprf.setIsDeleted(0);
        // added by songjianming@sandusapce.cn on 2018/12/17
        // 添加订单商品的product_color_name@product_style_name字段值，解决商家后台订单详情没有规格数据问题
        if (orderBO.getOrderProductList() != null && !orderBO.getOrderProductList().isEmpty()) {
            OrderProduct orderProduct = orderBO.getOrderProductList().get(0);
            Optional.of(orderProduct).ifPresent(op -> {
                mprf.setProductColorName(Objects.toString(op.getProductColorName(), "默认"));
                mprf.setProductStyleName(Objects.toString(op.getProductStyleName(), "默认"));
                // mprf.setProductPicPath(Objects.toString(op.getProductPicPath(), ""));
                // mprf.setTransportationExpenses(op.getTransportationExpenses() == null ? new BigDecimal("0") : op.getTransportationExpenses());
            });
        }
        mallBaseOrderMapper.insertOrderProductRel(mprf);

        return order;
    }

    private MallBaseOrder validExistOrder(GroupPurchaseOrderBO orderBO) {
        if (StringUtils.isNotBlank(orderBO.getOrderNo())) {
            MallBaseOrder preOrder = mallBaseOrderMapper.getOrderByOrderNo(orderBO.getOrderNo());
            if (preOrder == null) {
                log.info("获取订单失败,订单号: " + orderBO.getOrderNo());
                throw new GroupPurchaseBizException("获取订单失败");
            }
            if (preOrder.getUserId().equals(orderBO.getUserId().intValue())) {
                return preOrder;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void callBackSyncPurchaseData(String payTradeNo, Integer payStatus) {
        Date now = new Date();

        MallBaseOrder order = mallBaseOrderMapper.getOrderByOrderNo(payTradeNo);
        if (order == null) {
            log.info("支付回调获取订单失败,订单号: " + payTradeNo);
            throw new GroupPurchaseBizException("支付订单失败");
        }

        GroupPurchaseOpenDetail detail = groupPurchaseOpenDetailMapper.getByOrderId(payTradeNo);
        if (detail == null) {
            log.info("支付回调获取开团详情失败,订单号: " + payTradeNo);
            throw new GroupPurchaseBizException("支付订单失败");
        }

        GroupPurchaseOpen open = groupPurchaseOpenMapper.selectByPrimaryKey(detail.getPurchaseOpenId());
        if (open == null) {
            log.info("支付回调获取开团信息失败,订单号: " + payTradeNo);
            throw new GroupPurchaseBizException("支付订单失败");
        }

        GroupPurchaseGoods goods = groupPurchaseGoodsMapper.selectByPrimaryKey(detail.getPurchaseGoodsId());
        if (goods == null) {
            log.info("支付回调获取订单失败,订单号: " + payTradeNo);
            throw new GroupPurchaseBizException("支付订单失败");
        }

        BaseGoodsSku sku = baseGoodsSkuMapper.selectByPrimaryKey(goods.getSkuId().intValue());
        if (sku == null) {
            log.info("获取商品信息失败 ,skuID :" + goods.getSkuId());
            throw new GroupPurchaseBizException("支付订单失败");
        }


        //同步订单状态, 此处 (支付成功/支付失败) 不处理订单状态
        if (PAY_STATUS_CANCEL.equals(payStatus)) {
            order.setOrderStatus(2);
        }
        order.setPayStatus(PAY_STATUS_SUCCESS.equals(payStatus) ? PAY_STATUS_SUCCESS : PAY_STATUS_CANCEL);
        order.setGmtModified(now);
        mallBaseOrderMapper.updateByPrimaryKeySelective(order);


        //同步团购记录状态
        if (detail.getIsMaster() == 1) {
            //开团
            open.setOpenStatus(PAY_STATUS_SUCCESS.equals(payStatus) ? (byte) 0 : 3);
            open.setGmtModified(now);
        } else {
            //参团
            int count = PAY_STATUS_SUCCESS.equals(payStatus) ? 0 : -1;
            open.setJoinNumber(open.getJoinNumber() + count);
            open.setUnjoinNumber(open.getUnjoinNumber() - count);
            open.setGmtModified(now);
            if (open.getUnjoinNumber() == 0 && open.getTotalNumber().equals(open.getJoinNumber())) {
                //剩余拼团人数为0,即拼团成功
                open.setOpenStatus((byte) 1);

                //改变参与此团所有用户的订单状态
                groupPurchaseSyncOrder(open, true);
            }
        }
        groupPurchaseOpenMapper.updateByPrimaryKeySelective(open);


        //同步参团详情
        detail.setJoinStatus(PAY_STATUS_SUCCESS.equals(payStatus) ? 2 : 1);
        detail.setGmtModified(now);
        groupPurchaseOpenDetailMapper.updateByPrimaryKeySelective(detail);


        // 还原团购商品库存
        //成功无库存变化
        groupPurchaseGoodsService.updateQty(sku, PAY_STATUS_SUCCESS.equals(payStatus) ? null : false, detail.getBuyQuantity().intValue(), order);


    }

    /**
     * 团购结束,同步所有订单状态
     * 只有失败的时候才处理库存
     *
     * @param open
     * @param purchaseStatus 团购状态 : true  成功, false 失败(还原库存)
     */
    private void groupPurchaseSyncOrder(GroupPurchaseOpen open, boolean purchaseStatus) {
        List<GroupPurchaseOpenDetail> details = groupPurchaseOpenDetailMapper.listByPurchaseOpenIds(Collections.singletonList(open.getId()));
        //拼团结束更新所有用户订单状态
        for (GroupPurchaseOpenDetail detail : details) {
            MallBaseOrder tmpOrder = new MallBaseOrder();
            tmpOrder.setId(detail.getOrderId().intValue());
            tmpOrder.setOrderStatus(purchaseStatus ? 0 : 2);
            tmpOrder.setGmtModified(new Date());
            mallBaseOrderMapper.updateByPrimaryKeySelective(tmpOrder);

            if (purchaseStatus) {
                continue;
            }

            //成团失败, 还原库存
            MallBaseOrder order = MallBaseOrder.builder()
                    .id(detail.getOrderId().intValue())
                    .orderCode(detail.getOrderNo())
                    .build();
            GroupPurchaseGoods goods = groupPurchaseGoodsMapper.selectByPrimaryKey(detail.getPurchaseGoodsId());
            if (goods == null) {
                continue;
            }

            BaseGoodsSku sku = baseGoodsSkuMapper.selectByPrimaryKey(goods.getSkuId().intValue());
            if (sku == null) {
                log.error("获取商品信息失败 ,skuID :{}", goods.getSkuId());
                continue;
            }

            groupPurchaseGoodsService.updateQty(sku, false, detail.getBuyQuantity().intValue(), order);
        }

    }

    @Override
    @Transactional
    public List<GroupPurchaseOpenDetail> syncGroupPurchaseStatus() {
        Date now = new Date();
        //获取到期拼团活动
        List<GroupPurchaseActivity> expires = groupPurchaseActivityMapper.listExpireGroupActivity();
        for (GroupPurchaseActivity expire : expires) {
            if (expire.getActivityStatus().intValue() != 4) {
                expire.setActivityStatus((byte) 3);
            }
            expire.setScheduleFlag(1);
            expire.setGmtModified(now);
            groupPurchaseActivityMapper.updateByPrimaryKeySelective(expire);
        }

        //获取到期且为开团中 的开团数据
        List<GroupPurchaseOpen> opens = groupPurchaseOpenMapper.listExpirePurchaseOpenWithActivityIds(expires.stream().map(GroupPurchaseActivity::getId).collect(Collectors.toList()));
        log.info("openIDs :{}", opens.stream().map(GroupPurchaseOpen::getId).collect(Collectors.toList()));
        Set<Long> failedOpenIds = new HashSet<>();
        for (GroupPurchaseOpen open : opens) {
            GroupPurchaseActivity activity = expires.stream()
                    .filter(it -> it.getId().equals(open.getPurchaseActivityId()))
                    .findFirst()
                    .orElse(groupPurchaseActivityMapper.getById(open.getPurchaseActivityId()));
            if (activity == null) {
                log.error("获取团购活动失败,activityId:" + open.getPurchaseActivityId());
                continue;
            }
            if (activity.getVirtualFlag() == 1) {
                // 虚拟成团
                this.beVirtualGroupPurchase(open);
                open.setOpenStatus((byte) 1);
                open.setGmtModified(now);
                open.setUnjoinNumber(0);
                open.setJoinNumber(open.getTotalNumber());
                // 更新订单状态
                this.groupPurchaseSyncOrder(open, true);
            } else {
                open.setOpenStatus((byte) 2);
                failedOpenIds.add(open.getId());
                //还原库存
                this.groupPurchaseSyncOrder(open, false);
            }
            groupPurchaseOpenMapper.updateByPrimaryKey(open);
        }

        //获取所有需要退款的用户

        return failedOpenIds.isEmpty() ? Collections.emptyList() : groupPurchaseOpenDetailMapper.listByGroupPurchaseOpenIds(failedOpenIds);


    }

    @Override
    public boolean checkOpenStatus(Long purchaseOpenId) {
        GroupPurchaseOpen open = groupPurchaseOpenMapper.selectByPrimaryKey(purchaseOpenId);

        if (open == null) {
            log.info("获取开团信息失败,开团Id: " + purchaseOpenId);
            throw new GroupPurchaseBizException("获取开团信息失败");
        }

        return !open.getTotalNumber().equals(open.getJoinNumber());
    }


    /**
     * 虚拟成团--不计库存
     *
     * @param open
     */
    private void beVirtualGroupPurchase(GroupPurchaseOpen open) {
        Date now = new Date();
        List<GroupPurchaseOpenDetail> details = groupPurchaseOpenDetailMapper.listByPurchaseOpenIds(Collections.singletonList(open.getId()));
        if (details.isEmpty()) {
            return;
        }
        GroupPurchaseOpenDetail detail = details.get(0);
        detail.setId(null);
        detail.setUserId((long) -1);
        detail.setUserType((byte) 0);
        detail.setIsMaster((byte) 0);
        detail.setBuyQuantity((long) 0);
        detail.setTelephone("11100000000");
        detail.setOrderAmount(BigDecimal.ZERO);
        detail.setGmtCreate(now);
        detail.setGmtModified(now);
        detail.setIsDeleted(0);
        for (Integer i = 0; i < open.getUnjoinNumber(); i++) {
            groupPurchaseOpenDetailMapper.insert(detail);
        }

    }


    private void syncPurchaseOpenData(@NotNull GroupPurchaseOrderBO orderBO,
                                      @NotNull GroupPurchaseActivity activityInfo,
                                      @NotNull GroupPurchaseOpen groupPurchaseOpen,
                                      GroupPurchaseGoods goodInfo,
                                      Date now,
                                      SysUser sysUser,
                                      MallBaseOrder order) {

        if (orderBO.getIsMaster() == 1) {
            Date expireDate = DateUtils.addDays(now, activityInfo.getGroupValidDay());
            expireDate = DateUtils.addHours(expireDate, activityInfo.getGroupValidHour());
            expireDate = DateUtils.addMinutes(expireDate, activityInfo.getGroupValidMinute());
            //init groupPurchaseOpen
            GroupPurchaseOpen openInsert = GroupPurchaseOpen.builder()
                    .userId(orderBO.getUserId())
                    .openDate(now)
                    .expireDate(expireDate)
                    .purchaseActivityId(orderBO.getPurchaseActiveId())
                    .spuId(orderBO.getSpuId())
                    .purchaseGoodsId(orderBO.getPurchaseGoodsId())
                    .telephone(orderBO.getMobile())
                    .totalNumber(activityInfo.getTotalNumber())
                    .joinNumber(1)
                    .unjoinNumber(activityInfo.getTotalNumber() - 1)
                    .openStatus((byte) 4)
                    .gmtCreate(now)
                    .gmtModified(now)
                    .isDeleted(0)
                    .build();
            groupPurchaseOpenMapper.insertSelective(openInsert);
            orderBO.setPurchaseOpenId(openInsert.getId());
        } else {
            //参团更新状态
            groupPurchaseOpen.setGmtModified(now);
            groupPurchaseOpen.setJoinNumber(groupPurchaseOpen.getJoinNumber() + 1);
            groupPurchaseOpen.setUnjoinNumber(groupPurchaseOpen.getUnjoinNumber() - 1);
            groupPurchaseOpenMapper.updateByPrimaryKeySelective(groupPurchaseOpen);
        }
        GroupPurchaseOpenDetail openDetail = GroupPurchaseOpenDetail.builder()
                .userId(orderBO.getUserId())
                .purchaseActivityId(orderBO.getPurchaseActiveId())
                .purchaseOpenId(orderBO.getPurchaseOpenId())
                .purchaseGoodsId(orderBO.getPurchaseGoodsId())
                .telephone(orderBO.getMobile())
                .userType((byte) 1)
                .isMaster(orderBO.getIsMaster().byteValue())
                .orderAmount(order.getOrderAmount())
                .buyQuantity(orderBO.getSkuNum())
                .orderId(order.getId().longValue())
                .orderNo(order.getOrderCode())
                .joinStatus(0)
                .gmtCreate(now)
                .gmtModified(now)
                .isDeleted(0)
                .build();
        groupPurchaseOpenDetailMapper.insert(openDetail);
    }

    private GroupPurchaseGoods getGroupPurchaseGoods(GroupPurchaseOrderBO orderBO) {
        GroupPurchaseGoods groupPurchaseGoods = new GroupPurchaseGoods();
        groupPurchaseGoods.setIsDeleted(0);
        groupPurchaseGoods.setSkuId(orderBO.getSkuId());
        groupPurchaseGoods.setPurchaseActivityId(orderBO.getPurchaseActiveId());

        List<GroupPurchaseGoods> goodsInfo = groupPurchaseGoodsMapper.listByOption(groupPurchaseGoods);

        if (goodsInfo.isEmpty() || goodsInfo.get(0) == null) {
            log.info("获取商品信息失败,skuId" + orderBO.getSkuId());
            throw new GroupPurchaseBizException("获取商品信息失败");
        }
        GroupPurchaseGoods good = goodsInfo.get(0);
        orderBO.setPurchaseGoodsId(good.getId());
        return good;

//        if (goodsInfo.isEmpty() || goodsInfo.get(0).getUseQty().compareTo(BigDecimal.ZERO) < 0) {
//            throw new GroupPurchaseBizException("库存异常...");
//        }
//
//        GroupPurchaseGoods good = goodsInfo.get(0);
//        good.setUseQty(good.getUseQty().subtract(new BigDecimal(orderBO.getSkuNum())));
//        good.setOutQty(good.getOutQty().add(new BigDecimal(orderBO.getSkuNum())));
//
//        orderBO.setPurchaseGoodsId(good.getId());
//        return goodsInfo.get(0);
    }

    @Override
    public List<GroupPurchaseGoodsVO> getActivitySkuDetail(Integer purchaseActivityId, Integer spuId) {
        List<GroupPurchaseGoods> goods = groupPurchaseGoodsMapper.getSkusBySpuIdAndActivityId(purchaseActivityId, spuId);
        GroupPurchaseActivity activity = groupPurchaseActivityMapper.selectByPrimaryKey(purchaseActivityId.longValue());

        List<GroupPurchaseGoodsVO> goodsVO = new ArrayList<>();
        List<Integer> oldSkuIds = new ArrayList<>();
        List<Integer> newSkuIds = new ArrayList<>();
        goods.forEach(result -> {
            GroupPurchaseGoodsVO goodsSkuVO = new GroupPurchaseGoodsVO();
            oldSkuIds.add(result.getSkuId().intValue());
            org.springframework.beans.BeanUtils.copyProperties(result, goodsSkuVO);
            goodsVO.add(goodsSkuVO);
        });

        List<GoodsSkuBO> listSku = groupPurchaseGoodsMapper.getActivitySkuBySpuId(spuId);

        if (activity.getActivityStatus().equals(Byte.valueOf("0"))) {
            listSku.forEach(result -> {
                newSkuIds.add(result.getSkuId().intValue());
            });

            if (listSku != null && listSku.size() > 0) {
                for (GroupPurchaseGoodsVO vo : goodsVO) {
                    for (GoodsSkuBO skuBO : listSku) {
                        if (vo.getSpuId() == skuBO.getSpuId().longValue() &&
                                vo.getSkuId() == skuBO.getSkuId().longValue()) {
                            vo.setPrice(skuBO.getPrice());
                            vo.setQty(BigDecimal.valueOf(skuBO.getInventory()));
                            vo.setUseQty(BigDecimal.valueOf(skuBO.getInventory()));
                            vo.setAttributeNames(skuBO.getAttributeNames());
                            vo.setAttributeValueNames(skuBO.getAttributeValueNames());
                        }
                    }
                }

                List<Integer> addSkuIds = new ArrayList<>();
                for (Integer skuId : newSkuIds) {
                    if (!oldSkuIds.contains(skuId)) {
                        addSkuIds.add(skuId);
                    }
                }
                if (addSkuIds.size() > 0 && addSkuIds != null) {
                    listSku.forEach(skuInfo -> {
                        for (Integer addSkuId : addSkuIds) {
                            if (skuInfo.getSkuId().equals(addSkuId)) {
                                GroupPurchaseGoodsVO newGoodsSkuVO = new GroupPurchaseGoodsVO();
                                newGoodsSkuVO.setAttributeValueNames(skuInfo.getAttributeValueNames());
                                newGoodsSkuVO.setAttributeNames(skuInfo.getAttributeNames());
                                newGoodsSkuVO.setActivityPrice(BigDecimal.valueOf(0));
                                newGoodsSkuVO.setUseQty(BigDecimal.valueOf(skuInfo.getInventory()));
                                newGoodsSkuVO.setPrice(skuInfo.getPrice());
                                newGoodsSkuVO.setQty(BigDecimal.valueOf(skuInfo.getInventory()));
                                newGoodsSkuVO.setPurchaseActivityId(goodsVO.get(0).getPurchaseActivityId());
                                newGoodsSkuVO.setSkuId(addSkuId.longValue());
                                newGoodsSkuVO.setSpuId(goodsVO.get(0).getSpuId());
                                goodsVO.add(newGoodsSkuVO);
                            }
                        }
                    });
                }

            }
        } else {
            if (listSku != null && listSku.size() > 0) {
                for (GroupPurchaseGoodsVO vo : goodsVO) {
                    for (GoodsSkuBO skuBO : listSku) {
                        if (vo.getSpuId() == skuBO.getSpuId().longValue() &&
                                vo.getSkuId() == skuBO.getSkuId().longValue()) {
                            vo.setPrice(skuBO.getPrice());
                            vo.setAttributeNames(skuBO.getAttributeNames());
                            vo.setQty(BigDecimal.valueOf(skuBO.getInventory()));
                            vo.setUseQty(BigDecimal.valueOf(skuBO.getInventory()));
                            vo.setAttributeValueNames(skuBO.getAttributeValueNames());
                        }
                    }
                }
            }
        }

        return goodsVO;
    }


    public static void main(String[] args) {
        Short i = 1;
        Byte s = 1;
        System.out.println(Objects.equals(s, 1));
    }
}
