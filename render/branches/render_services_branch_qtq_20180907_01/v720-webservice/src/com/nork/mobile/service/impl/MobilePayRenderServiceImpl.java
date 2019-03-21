package com.nork.mobile.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.common.MobileCommonConstants;
import com.nork.mobile.dao.MobileRenderRecordMapper;
import com.nork.mobile.model.MobileProductReplace;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.search.MobileRenderRecord;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.pay.common.IdGenerator;
import com.nork.pay.dao.PayOrderMapper;
import com.nork.pay.metadata.BizType;
import com.nork.pay.metadata.FinanceType;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.platform.model.BasePlatform;
import com.nork.platform.service.BasePlatformService;
import com.nork.product.dao.ProductGroupDao;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.ProductGroupBaseInfoModel;
import com.nork.product.model.SplitTextureInfoDTO;
import com.nork.product.model.result.SearchProductGroupDetail;
import com.nork.product.model.result.SearchProductGroupResult;
import com.nork.product.model.search.GroupProductSearch;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductServiceV2;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("mobilePayRenderService")
public class MobilePayRenderServiceImpl implements MobilePayRenderService {

    private final static Logger logger = LogManager.getLogger(MobilePayRenderServiceImpl.class);
    @Autowired
    private MobileRenderRecordMapper mobileRenderRecordMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;
    @Autowired
    private DesignPlanAutoRenderService designPlanAutoRenderServiceImpl;
    @Autowired
    private ResRenderPicMapper resRenderPicMapper;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private ProductGroupDao productGroupDao;
    @Autowired
    private GroupProductServiceV2 groupProductServiceV2;

    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private DesignPlanProductService designPlanProductService;
    @Autowired
    private BasePlatformService basePlatformService;

    /**
     * 生成订单的方法
     */
    @Override
    public PayOrder getOrder(int totalFee, String payType, Integer productId, String productType, String productDesc,
                             String tradeType, Integer userId, Integer taskId) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setTotalFee(new Integer(totalFee));
        payOrder.setPayType(payType);
        payOrder.setProductId(productId);
        payOrder.setProductType(productType);
        if (productType.equals(ProductType.COMMON_RENDER)) {// 普通照片级
            payOrder.setProductName(ProductType.COMMON_RENDER_NAME);
        } else if (productType.equals(ProductType.HD_RENDER)) {// 高清照片级
            payOrder.setProductName(ProductType.HD_RENDER_NAME);
        } else if (productType.equals(ProductType.UHD_RENDER)) {// 超高清照片级
            payOrder.setProductName(ProductType.UHD_RENDER_NAME);
        } else if (productType.equals(ProductType.AUTHCODE_PURCHASE)) {// 购买序列号
            payOrder.setProductName(ProductType.AUTHCODE_PURCHASE_NAME);
        } else if (productType.equals(ProductType.AUTHCODE_RENEW)) {// 序列号续费
            payOrder.setProductName(ProductType.AUTHCODE_RENEW_NAME);
        } else if (productType.equals(ProductType.AUTHCODE_DREDGE)) {// 开通序列号
            payOrder.setProductName(ProductType.AUTHCODE_DREDGE_NAME);
        } else if (productType.equals(ProductType.PANORAMA_RENDER)) {// 720全景
            payOrder.setProductName(ProductType.PANORAMA_RENDER_NAME);
        } else if (productType.equals(ProductType.ROAM_VIDEO_RENDER)) {// 漫游视频
            payOrder.setProductName(ProductType.ROAM_VIDEO_RENDER_NAME);
        } else if (productType.equals(ProductType.ROAM_PANORAMA_RENDER)) {// 720多点
            payOrder.setProductName(ProductType.ROAM_PANORAMA_RENDER_NAME);
        }
        payOrder.setProductDesc(productDesc);
        payOrder.setTradeType(tradeType);
        payOrder.setBizType(BizType.BUY);
        payOrder.setFinanceType(FinanceType.OUT);
        payOrder.setPayState(PayState.SUCCESS);

        String flag = Utils.getValue("auto.task.test.flag", "false");
        if ("true".equals(flag)) {
            // 渲染测试模拟用户
            if (userId == null || userId == 0) {
                userId = 36;
            }
        }
        // 插入支付订单
        if (userId > 0) {
            payOrder.setUserId(userId);
        }
        SysUser sysUser = sysUserService.get(userId);
        int balance = sysUser.getBalanceAmount().intValue();

        if (payOrder.getId() == null) {
            payOrder.setUserId(sysUser.getId());
            payOrder.setGmtCreate(new Date());
            payOrder.setCreator(sysUser.getUserName());
            payOrder.setIsDeleted(0);
            if (payOrder.getSysCode() == null || "".equals(payOrder.getSysCode())) {
                payOrder.setSysCode(
                        Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            }
        }

        payOrder.setGmtModified(new Date());
        payOrder.setModifier(sysUser.getUserName());

        payOrder.setCurrentIntegral(new Integer(balance - totalFee));
        // FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
        if ("refunds".equals(productType)) {
            payOrder.setProductType("recharge");
            payOrder.setProductName("渲染失败退款");
            payOrder.setProductDesc("渲染失败退款");
            payOrder.setBizType(BizType.REFUND);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setPayState(PayState.SUCCESS);

            payOrder.setCurrentIntegral(new Integer(balance + totalFee));
        }
        payOrder.setTaskId(taskId);

        int id = payOrderMapper.insertSelective(payOrder);

        if (id == 0) {
            return null;
        }

        return payOrder;

    }

    /**
     * 生成消费记录和渲染记录 并修改用户余额
     */
    @Override
    public Object deductionOfPoints(MobileRenderRecord model) {
        Integer userId = model.getUserId();
        Double totalFee = model.getTotalFee();
        String productType = model.getProductType();

        SysUser sysUser = sysUserService.get(userId);
        if (sysUser != null) {
            Double balanceAmount = sysUser.getBalanceAmount() - totalFee;
            Double consumAmount = sysUser.getConsumAmount() + totalFee;
            if (balanceAmount > 0.00) {
                // 添加一条渲染记录到数据库
                MobileRenderRecord record = new MobileRenderRecord();
                record.setUserId(userId);
                record.setPlanId(model.getPlanId());
                record.setTemplateId(model.getTemplateId());
                record.setRecommendedPlanId(model.getRecommendedPlanId());
                record.setRenderingType(model.getRenderingType());
                record.setTotalFee(totalFee);
                record.setCreator(sysUser.getUserName());
                record.setGmtCreate(new Date());
                record.setIsDeleted(0);
                record.setIsDecorate(0);
                record.setSysCode(
                        Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                Integer i = mobileRenderRecordMapper.insertSelective(record);

                if (i <= 0) {
                    return new ResponseEnvelope<>(false, "渲染失败！");
                }

                // 修改该用户的消费总金额和当前账户余额
                sysUser.setBalanceAmount(balanceAmount);
                sysUser.setConsumAmount(consumAmount);
                Integer id = sysUserService.update(sysUser);

                if (id > 0) {
                    return new ResponseEnvelope<>(true, "渲染成功！");
                }
            } else {
                return new ResponseEnvelope<>(false, "余额不足，请充值之后再进行该操作！");
            }
        }
        return new ResponseEnvelope<>(false, "用户不存在，请重新登录！");
    }

    /**
     * 获取移动端替换产品
     *
     * @throws UnknownHostException
     */
    @SuppressWarnings({"rawtypes"})
    @Override
    public Object replaceRecord(MobileProductReplace model) throws UnknownHostException {
        ResponseEnvelope message = new ResponseEnvelope();

        Integer userId = model.getUserId();
        Double totalFee = model.getTotalFee();
        String payType = model.getPayType();
        String productType = model.getProductType();
        SysUser sysUser = sysUserService.get(userId);
        //判断用户余额是不是充足
        if (sysUser.getBalanceAmount().intValue() < totalFee.intValue()) {
            return new ResponseEnvelope<>(false, "您的余额不足，请到PC端充值！");
        }
        Integer productId = 0;
        String productDesc = "移动端替换产品";
        String tradeType = "";
        Integer operationSource = model.getOperationSource();
        String spaceCode = null;
        Integer recommendedPlanId = model.getRecommendedPlanId();
        Integer designPlanSceneId = model.getDesignPlanSceneId();
        Integer spaceCommonId = null;
        // 生成一条订单记录到数据库
        PayOrder payOrder = this.getOrder(totalFee.intValue(), payType, productId, productType, productDesc, tradeType,
                userId, -1);
        if (payOrder == null) {
            message.setSuccess(false);
            message.setMessage("生成订单失败");
            return message;
        }
        int taskId = -1;
        AutoRenderTask autoRenderTask = new AutoRenderTask();
        String designCode = null;
        String designName = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        if (payOrder != null) {
            // 生成替换渲染任务start
            if (operationSource.intValue() == 0) {
                DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanSceneId);
                designCode = designPlanRenderScene.getPlanCode();
                designName = designPlanRenderScene.getPlanName();
                spaceCommonId = designPlanRenderScene.getSpaceCommonId();
                autoRenderTask.setPlanId(designPlanSceneId);
            } else if (operationSource.intValue() == 1) {
                DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(recommendedPlanId);
                designCode = designPlanRecommended.getPlanCode();
                designName = designPlanRecommended.getPlanName();
                spaceCommonId = designPlanRecommended.getSpaceCommonId();
                autoRenderTask.setPlanId(recommendedPlanId);
            }
            SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
            spaceCode = spaceCommon.getSpaceCode();
//			SysUser sysUser = sysUserService.get(userId);
            autoRenderTask.setPriority(99);
            autoRenderTask.setOperationUserId(userId);
            autoRenderTask.setSpaceCode(spaceCode);
            autoRenderTask.setOperationUserName(sysUser.getUserName());
            autoRenderTask.setOrderNumber(payOrder.getOrderNo());
            autoRenderTask.setTemplateId(-1);
            autoRenderTask.setTaskType(1);
            autoRenderTask.setOperationSource(operationSource);
            autoRenderTask.setGmtCreate(new Date());
            autoRenderTask.setGmtModified(new Date());
            autoRenderTask.setCreator(sysUser.getUserName());
            autoRenderTask.setModifier(sysUser.getUserName());
            autoRenderTask.setDesignCode(designCode);
            autoRenderTask.setDesignName(designName);
            autoRenderTask.setTaskSource(1);
            autoRenderTask.setHostIp(hostIp);
            if (productType.equals(ProductType.COMMON_RENDER)) {// 普通照片级
                autoRenderTask.setRenderTypesStr(ProductType.PHOTO);
                autoRenderTask.setRender720(0);
            } else if (productType.equals(ProductType.PANORAMA_RENDER)) {// 720全景
                autoRenderTask.setRenderTypesStr(ProductType.ROAM720);
                autoRenderTask.setRender720(0);
            } else if (productType.equals(ProductType.ROAM_VIDEO_RENDER)) {// 漫游视频
                autoRenderTask.setRenderTypesStr(ProductType.VIDEO);
                autoRenderTask.setRenderVideo(0);
            } else if (productType.equals(ProductType.ROAM_PANORAMA_RENDER)) {// 720多点
                autoRenderTask.setRenderTypesStr(ProductType.ROAMN720);
                autoRenderTask.setRenderN720(0);
            }
            // 生成渲染任务end
        }
        if (autoRenderTask != null) {
            taskId = designPlanAutoRenderServiceImpl.add(autoRenderTask);
            autoRenderTask.setId(taskId);
            designPlanAutoRenderServiceImpl.addRedisReplaceList(autoRenderTask);
            logger.error("create taskId==>" + taskId);
            // 批量插入替换产品
            if (model.getProductReplaceList() != null) {
                List<ProductReplaceTaskDetail> productReplaceList = model.getProductReplaceList();
                if (productReplaceList != null && productReplaceList.size() > 0) {
                    for (ProductReplaceTaskDetail productReplaceTaskDetail : productReplaceList) {
                        productReplaceTaskDetail.setTaskId(Integer.valueOf(taskId));
                        productReplaceTaskDetail.setCreator(model.getLoginUserName());
                        productReplaceTaskDetail.setModifier(model.getLoginUserName());
                        productReplaceTaskDetail.setGmtCreate(new Date());
                        productReplaceTaskDetail.setGmtModified(new Date());
                    }
                    mobileRenderRecordMapper.batchInsertDataList(productReplaceList);
                }
            }

            // 批量插入删除产品
            if (model.getProductDeleteList() != null) {
                List<ProductReplaceTaskDetail> productDeleteList = model.getProductDeleteList();
                if (productDeleteList != null && productDeleteList.size() > 0) {
                    for (ProductReplaceTaskDetail productReplaceTaskDetail : productDeleteList) {
                        productReplaceTaskDetail.setTaskId(Integer.valueOf(taskId));
                        productReplaceTaskDetail.setCreator(model.getLoginUserName());
                        productReplaceTaskDetail.setModifier(model.getLoginUserName());
                        productReplaceTaskDetail.setGmtCreate(new Date());
                        productReplaceTaskDetail.setGmtModified(new Date());
                    }
                    mobileRenderRecordMapper.batchInsertDataList(productDeleteList);
                }
            }

            //批量插入组合替换产品
            if (model.getProductGroupReplaceList() != null) {
                List<ProductGroupReplaceTaskDetail> productGroupReplaceList = model.getProductGroupReplaceList();
                if (productGroupReplaceList != null && productGroupReplaceList.size() > 0) {
                    for (ProductGroupReplaceTaskDetail productGroupReplaceTaskDetail : productGroupReplaceList) {
                        //如果该组合数量>2,才会传方案ID过来,处理组合主产品相同,子产品不同,也同时替换该组合.
                        if (productGroupReplaceTaskDetail.getPlanId().intValue() > 0) {
                            DesignPlanProduct designPlanProduct = new DesignPlanProduct();
                            designPlanProduct.setPlanId(productGroupReplaceTaskDetail.getPlanId());
                            designPlanProduct.setProductId(productGroupReplaceTaskDetail.getSourceProductId());
                            designPlanProduct.setIsMainProduct(1);
                            List<DesignPlanProduct> groupIdList = designPlanProductService.getList(designPlanProduct);
                            for (DesignPlanProduct PlanProduct : groupIdList) {
                                productGroupReplaceTaskDetail.setSourceGroupProductId(PlanProduct.getProductGroupId());
//						        productGroupReplaceTaskDetail.getSearchProductGroupResult();
                                productGroupReplaceTaskDetail.setTaskId(Integer.valueOf(taskId));
                                productGroupReplaceTaskDetail.setCreator(model.getLoginUserName());
                                productGroupReplaceTaskDetail.setModifier(model.getLoginUserName());
                                productGroupReplaceTaskDetail.setGmtCreate(new Date());
                                productGroupReplaceTaskDetail.setGmtModified(new Date());
                                mobileRenderRecordMapper.insertGroupProductReplace(productGroupReplaceTaskDetail);
//								mobileRenderRecordMapper.batchInsertGroupList(productGroupReplaceList);
                            }
                        } else {
                            productGroupReplaceTaskDetail.setTaskId(Integer.valueOf(taskId));
                            productGroupReplaceTaskDetail.setCreator(model.getLoginUserName());
                            productGroupReplaceTaskDetail.setModifier(model.getLoginUserName());
                            productGroupReplaceTaskDetail.setGmtCreate(new Date());
                            productGroupReplaceTaskDetail.setGmtModified(new Date());
                            mobileRenderRecordMapper.insertGroupProductReplace(productGroupReplaceTaskDetail);
                        }
                    }
                }
            }

            //批量插入材质替换产品
            if (model.getTextureReplaceList() != null) {
                String newSplitTextureInfo = null;
                List<ProductReplaceTaskDetail> textureReplaceList = model.getTextureReplaceList();
                if (textureReplaceList != null && textureReplaceList.size() > 0) {
                    for (ProductReplaceTaskDetail textureReplaceTaskDetail : textureReplaceList) {
                        newSplitTextureInfo = getNewSplitTexturesChooseInfo(textureReplaceTaskDetail);
                        textureReplaceTaskDetail.setReplaceSplitTexturesChooseInfo(newSplitTextureInfo);
                        textureReplaceTaskDetail.setTaskId(Integer.valueOf(taskId));
                        textureReplaceTaskDetail.setCreator(model.getLoginUserName());
                        textureReplaceTaskDetail.setModifier(model.getLoginUserName());
                        textureReplaceTaskDetail.setGmtCreate(new Date());
                        textureReplaceTaskDetail.setGmtModified(new Date());
                    }
                    mobileRenderRecordMapper.batchInsertTextureList(textureReplaceList);
                }
            }

            payOrder.setTaskId(taskId);
            payOrderService.update(payOrder);
            logger.error("replaceRecord=>update the pay order==" + payOrder.getOrderNo() + "taskId ==" + taskId);
        }

        // 订单号 就是 payOrder.getOrderNo()
        // 修改用户余额
        message = this.updateBalance(model);
        return message;
    }

    /**
     * 修改用户余额的方法
     */
    @SuppressWarnings("rawtypes")
    private ResponseEnvelope updateBalance(MobileProductReplace model) {
        ResponseEnvelope message = new ResponseEnvelope();

        SysUser sysUser = sysUserService.get(model.getUserId());
        if (sysUser != null) {
            Double balanceAmount = sysUser.getBalanceAmount() - model.getTotalFee();
            Double consumAmount = sysUser.getConsumAmount() + model.getTotalFee();
            if (balanceAmount > 0.00) {
                // 添加一条移动端的渲染记录到数据库
                MobileRenderRecord record = new MobileRenderRecord();
                record.setUserId(model.getUserId());
                record.setPlanId(model.getPlanId());
                record.setTemplateId(model.getTemplateId());
                record.setRecommendedPlanId(model.getRecommendedPlanId());
                record.setRenderingType(model.getRenderingType());
                record.setTotalFee(model.getTotalFee());
                record.setIsDecorate(1);
                record.setCreator(model.getLoginUserName());
                record.setGmtCreate(new Date());
                record.setIsDeleted(0);
                record.setSysCode(
                        Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                int recordId = mobileRenderRecordMapper.insertSelective(record);

                if (recordId > 0) {
                    // 修改该用户的消费总金额和当前账户余额
                    sysUser.setBalanceAmount(balanceAmount);
                    sysUser.setConsumAmount(consumAmount);
                    sysUserService.update(sysUser);
                    message.setSuccess(true);
                    message.setMessage("付款成功.");
                    return message;
                }
            }
            message.setSuccess(false);
            message.setMessage("当前用户余额不足，请充值后在执行此操作！");
            return message;
        }
        message.setSuccess(false);
        message.setMessage("当前用户不存在！");
        return message;
    }

    @Override
    public List<ProductReplaceTaskDetail> selectListByTaskId(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> replaceProductList = mobileRenderRecordMapper.selectListByTaskId(taskId);
        // mobileRenderRecordMapper.deleteReplaceProductById(taskId);
        return replaceProductList;
    }

    @Override
    public List<ProductReplaceTaskDetail> selectDelListByTaskId(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> delProductList = mobileRenderRecordMapper.selectDelListByTaskId(taskId);
//		 mobileRenderRecordMapper.deleteReplaceProductById(taskId);
        return delProductList;
    }

    @Override
    public List<ProductGroupReplaceTaskDetail> selectGroupReplaceListByTaskId(Integer taskId, String msgId) {
        String mediaType = "MOBILE";
        List<ProductGroupBaseInfoModel> groupList = null;

        List<SearchProductGroupResult> resultList = new ArrayList<>();
        //查出该任务下的所有需要替换的组合产品
        List<ProductGroupReplaceTaskDetail> groupReplaceList = mobileRenderRecordMapper.selectGroupListByTaskId(taskId);
        AutoRenderTask autoRenderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);
        //这里获取空间ID,或从副本方案获取，或从推荐方案获取。
        Integer spaceCommonId = null;
        if (autoRenderTask.getOperationSource() == 0) {
            DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(autoRenderTask.getPlanId());
            spaceCommonId = designPlanRenderScene.getSpaceCommonId();
        } else if (autoRenderTask.getOperationSource() == 1) {
            DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(autoRenderTask.getPlanId());
            spaceCommonId = designPlanRecommended.getSpaceCommonId();
        }
        //获取该任务下的所有组合ids,作为查询条件
        List<Integer> groupIds = new ArrayList<Integer>();

        for (ProductGroupReplaceTaskDetail replaceTaskDetail : groupReplaceList) {

            groupIds.add(replaceTaskDetail.getReplaceGroupId());
        }
        GroupProductSearch groupSearch = new GroupProductSearch();

        groupSearch.setGroupIds(groupIds);
        if (groupSearch.getGroupIds() != null && groupSearch.getGroupIds().size() > 0) {
            groupList = productGroupDao.getGroupListByProductV3(groupSearch);
        }

        if (groupList != null && groupList.size() > 0) {

            List<Integer> groupIDSList = new ArrayList<>();

            // 遍历组列表
            for (ProductGroupBaseInfoModel baseInfo : groupList) {

                groupIDSList.add(baseInfo.getGroupID());

                SearchProductGroupResult productGroup = new SearchProductGroupResult();

                // 目前数据库 location字段， 分为 txt 文件路径、json 串。
                String filePath = baseInfo.getFilePath();

                if (filePath != null && !filePath.trim().isEmpty()) {

                    productGroup.setFilePath(filePath);

                } else {
                    // 直接用 location json 串
                    productGroup.setGroupConfig(baseInfo.getLocation());
                }
                productGroup.setGroupPrice(baseInfo.getGroupPrice());
                productGroup.setSalePrice(baseInfo.getGroupPrice());
                productGroup.setGroupId(baseInfo.getGroupID());
                productGroup.setGroupCode(baseInfo.getGroupCode());
                productGroup.setGroupName(baseInfo.getGroupName());
                productGroup.setProductName(baseInfo.getGroupName());
                // 组装产品组封面图片
                Integer picId = baseInfo.getPicID();

                if (picId != null && picId > 0) {

                    productGroup.setPicPath(baseInfo.getPicPath());
                }
                // 放到 resultList 里面
                resultList.add(productGroup);
            }
        }

        if (resultList != null && resultList.size() > 0) {
            // 处理 组装产品组中的产品列表
            Map<Integer, List<SearchProductGroupDetail>> detailListMap = groupProductServiceV2.getGroupProductDetailsByGroupIDReplaceList(resultList, mediaType, spaceCommonId);
            if (detailListMap != null) {
                //获取组合中的产品详情
                for (SearchProductGroupResult replaceResult : resultList) {
                    for (ProductGroupReplaceTaskDetail replaceTaskDetail : groupReplaceList) {
                        if (replaceResult.getGroupId().intValue() == replaceTaskDetail.getReplaceGroupId().intValue()) {
                            Integer groupId = replaceResult.getGroupId();
                            if (detailListMap.get(groupId) != null) {
                                replaceResult.setGroupProductDetails(detailListMap.get(groupId));
                            }
                            replaceTaskDetail.setSearchProductGroupResult(replaceResult);
                        }
                    }
                }
            }
        }
        return groupReplaceList;
    }

    @Override
    public List<ProductReplaceTaskDetail> selectTextureReplaceListByTaskId(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> selectTextureReplaceList = mobileRenderRecordMapper.selectTextureListByTaskId(taskId);
        return selectTextureReplaceList;
    }

    /**
     * 查询该用户的所有替换记录 add by yangzhun
     */
    @Override
    public Object getALLReplaceRecordByUserId(AutoRenderTask model) {
        List<Integer> sceneIds = new ArrayList<Integer>();
        List<Integer> recommendIds = new ArrayList<Integer>();
        List<AutoRenderTask> allTaskList = new ArrayList<AutoRenderTask>();
        // 查询该用户的所有替换记录和方案名称的list 这里只会查出刚刚插入的任务，未开始渲染的，开始了的会被删除
        List<AutoRenderTask> noBeginningTaskList = designPlanAutoRenderMapper.getALLReplaceRecordByUserId(model);
        for (AutoRenderTask noBeginningTask : noBeginningTaskList) {
            noBeginningTask.setIsSuccess(new Integer(0));
            if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(noBeginningTask.getOperationSource())) {
                sceneIds.add(noBeginningTask.getPlanId());
            } else if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(noBeginningTask.getOperationSource())) {
                recommendIds.add(noBeginningTask.getPlanId());
            }
        }

        // 查询该用户的所有替换记录和方案名称的list 这里只会查出正在跑的任务，已经开始渲染的
        List<AutoRenderTask> startedTaskList = designPlanAutoRenderMapper.getAllReplaceRecordByUserId2(model);
        for (AutoRenderTask startedTask : startedTaskList) {
            // 判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
            startedTask = setTaskSuccessState(startedTask);
            if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(startedTask.getOperationSource())) {
                sceneIds.add(startedTask.getPlanId());
            } else if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(startedTask.getOperationSource())) {
                recommendIds.add(startedTask.getPlanId());
            }
        }
        // 合并两个list

        if (noBeginningTaskList.size() == 0 && startedTaskList.size() == 0) {
            return new ResponseEnvelope<>(allTaskList.size(), allTaskList);
        } else {
            allTaskList.addAll(noBeginningTaskList);
            allTaskList.addAll(startedTaskList);
            allTaskList = setTaskSmallPicPath(allTaskList, sceneIds, recommendIds);
            noBeginningTaskList = null;
            startedTaskList = null;
//		JedisUtils.setMap() TODO : Cache the small pic data
            return new ResponseEnvelope<>(allTaskList.size(), allTaskList);

        }

    }

    /**
     * 查询该用户所有渲染记录
     */
    @Override
    public Object getAllTaskByUserId(AutoRenderTask autoRenderTask) {

        String platformCode = autoRenderTask.getPlatformCode();
	  /*if (StringUtils.isNotBlank(platformCode)) {
		  return new ResponseEnvelope<AutoRenderTask>(false,"参数platformCode不能为空！");
	  }*/
        BasePlatform basePlatform = basePlatformService.getByCode(platformCode);
        logger.info("getAllTaskByUserId===============" + basePlatform.toString());
        if (basePlatform == null) {
            return new ResponseEnvelope<AutoRenderTask>(false, "参数platformCode错误！");
        }
        //平台业务类型
        String platformType = basePlatform.getPlatformBussinessType();
        autoRenderTask.setPlatformBussinessType(platformType);
        // 平台Id
        Integer platformId = basePlatform.getId();
        autoRenderTask.setPlatformId(platformId);
        //所有渲染任务
        List<AutoRenderTask> allTaskList = new ArrayList<AutoRenderTask>();
        //副本方案ids
        List<Integer> sceneIds = new ArrayList<Integer>();
        //推荐方案ids
        List<Integer> recommendIds = new ArrayList<Integer>();
        Integer count = designPlanAutoRenderMapper.selectUnionCount(autoRenderTask);
        allTaskList = designPlanAutoRenderMapper.selectUnionList(autoRenderTask);
        for (AutoRenderTask task : allTaskList) {
            // 判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
            task = setTaskSuccessState(task);
//			if(MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(task.getOperationSource())) {
//				sceneIds.add(task.getPlanId());
//			}else if(MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(task.getOperationSource())){
//				recommendIds.add(task.getPlanId());
//			}
            if (task.getBusinessId() != null && task.getBusinessId() > 0) {
                sceneIds.add(task.getBusinessId());
            }
        }
        allTaskList = this.setTaskSmallPicPath(allTaskList, sceneIds, recommendIds);
        /*return new ResponseEnvelope<>(count,allTaskList);*/
        return new ResponseEnvelope<AutoRenderTask>(count, allTaskList);
    }

    /**
     * 我的消息list
     */
    @Override
    public Object getMyMessageList(AutoRenderTask model) {
        // 查询该用户的所有替换记录和方案名称的list 这里只会查出正在跑的任务，已经开始渲染的
        List<AutoRenderTask> allStartedTaskList = designPlanAutoRenderMapper.getAllReplaceRecordByUserId2(model);
        List<AutoRenderTask> resultTaskList = new ArrayList<AutoRenderTask>();
        List<Integer> sceneIds = new ArrayList<Integer>();
        List<Integer> recommendIds = new ArrayList<Integer>();
        if (Lists.isNotEmpty(allStartedTaskList)) {
            for (AutoRenderTask task : allStartedTaskList) {
                // 判断渲染是否成功，移动端我的替换列表需要根据这个值进行判断
                task = setTaskSuccessState(task);
                if (task.getIsSuccess().intValue() > 1) {
                    resultTaskList.add(task);
                    if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(task.getOperationSource())) {
                        sceneIds.add(task.getPlanId());
                    } else if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(task.getOperationSource())) {
                        recommendIds.add(task.getPlanId());
                    }
                }
            }
        }
        allStartedTaskList = null;
        resultTaskList = setTaskSmallPicPath(resultTaskList, sceneIds, recommendIds);
        return new ResponseEnvelope<>(resultTaskList.size(), resultTaskList);
    }

    private List<AutoRenderTask> setTaskSmallPicPath(List<AutoRenderTask> allStartedTaskList, List<Integer> sceneIds, List<Integer> recommendIds) {
        ResRenderPic pic = new ResRenderPic();
        if (Lists.isNotEmpty(allStartedTaskList)) {
//			pic.setRecommendIds(recommendIds);
            pic.setSceneIds(sceneIds);
            pic.setIsDeleted(new Integer(0));
            List<String> fileKeyList = new ArrayList<>();
            fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
            fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
//			fileKeyList.add(ResProperties.DESIGNPLANRECOMMENDED_PIC_SMALL_FILEKEY);
            pic.setFileKeyList(fileKeyList);
            List<ResRenderPic> picList = resRenderPicMapper.getReplaceRenderResult(pic);
            for (AutoRenderTask task : allStartedTaskList) {
                // 获取一张缩略图
                // 如果查询到的缩略图集不为空 ，就把第一张缩略图给到替换记录的list
//				if (Lists.isNotEmpty(picList)) {
//					for (ResRenderPic smallPic : picList) {
//						if(MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(task.getOperationSource())
//								&& task.getPlanId().equals(smallPic.getDesignSceneId())) {
//							task.setSmallPicPath(smallPic.getPicPath());
//
//						} else if(MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(task.getOperationSource())
//								&& task.getPlanId().equals(smallPic.getPlanRecommendedId())) {
//							task.setSmallPicPath(smallPic.getPicPath());
//						}
//					}
//				}
                if (task.getIsSuccess() == 0 || task.getIsSuccess() == 1) {
                    //未渲染或渲染中，显示渲染中的图片
                    task.setSmallPicPath("/default/myReplaceRecord_rendering_thumb.gif");
                } else if (task.getIsSuccess() == 2 && task.getBusinessId() != null) {
                    //渲染成功，显示效果图的缩略图
                    ResRenderPic resRenderPic = (ResRenderPic) CollectionUtils.find(picList, new BeanPropertyValueEqualsPredicate("designSceneId", task.getBusinessId()));
                    if (resRenderPic != null) {
                        task.setSmallPicPath(resRenderPic.getPicPath());
                    } else {
                        sceneIds.clear();
                        recommendIds.clear();
                        fileKeyList.clear();
                        if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(task.getOperationSource())) {
                            sceneIds.add(task.getPlanId());
                            fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
                        } else if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(task.getOperationSource())) {
                            recommendIds.add(task.getPlanId());
                            fileKeyList.add(ResProperties.DESIGNPLANRECOMMENDED_PIC_SMALL_FILEKEY);
                        }
                        pic.setRecommendIds(recommendIds);
                        pic.setSceneIds(sceneIds);
                        pic.setFileKeyList(fileKeyList);
                        List<ResRenderPic> sourcePlanPicList = resRenderPicMapper.getReplaceRenderResult(pic);
                        if (sourcePlanPicList != null && sourcePlanPicList.size() > 0) {
                            task.setSmallPicPath(sourcePlanPicList.get(0).getPicPath());
                        } else {
                            task.setSmallPicPath("/default/myReplaceRecord_fail_thumb.png");
                        }
                    }
                } else {
                    //显示渲染失败的图片
                    task.setSmallPicPath("/default/myReplaceRecord_fail_thumb.png");
                }
            }

        }
        return allStartedTaskList;
    }

    private AutoRenderTask setTaskSuccessState(AutoRenderTask task) {
        if ("1".equals(task.getRenderTypesStr())) {
            task.setIsSuccess(task.getRenderPic());
        } else if ("2".equals(task.getRenderTypesStr())) {
            task.setIsSuccess(task.getRender720());
        } else if ("4".equals(task.getRenderTypesStr())) {
            task.setIsSuccess(task.getRenderVideo());
        } else if ("3".equals(task.getRenderTypesStr())) {
            task.setIsSuccess(task.getRenderN720());
        }
        return task;
    }

    @Override
    public String getNewSplitTexturesChooseInfo(ProductReplaceTaskDetail replaceDetail) {
        // TODO Auto-generated method stub
        //替换方案产品列表中的数据
        Integer productId = replaceDetail.getSourceProductId();
        //当前产品
        BaseProduct currentProduct = baseProductService.get(productId);
        //材质替换参数
        String splitTexturesChooseParam = replaceDetail.getReplaceSplitTexturesParam();
        //当前产品材质信息
        String currentSplitTexturesInfo = currentProduct.getSplitTexturesInfo();
        //json 转list
        Gson gson = new Gson();
        List<SplitTextureInfoDTO> currentSplitTextInfoList = gson.fromJson(currentSplitTexturesInfo, new TypeToken<List<SplitTextureInfoDTO>>() {
        }.getType());
        List<String> newSplitTextInfoList = Utils.getListFromStr2(splitTexturesChooseParam, ";");
        for (String splitTextures : newSplitTextInfoList) {
            //更新方案产品列表相同产品的多材质信息
            currentSplitTextInfoList = this.updateSplitTexture(currentSplitTextInfoList, splitTextures);
        }
        String newSplitTextInfo = gson.toJson(currentSplitTextInfoList);
        // TODO : if currentSplitTextInfoList is null should use the orginal split text info in design plan product
        return newSplitTextInfo;
    }

    private static List<SplitTextureInfoDTO> updateSplitTexture(List<SplitTextureInfoDTO> splitTextureDTOList, String str) {
        if (str.indexOf(":") == -1)
            return splitTextureDTOList;
        String[] strs = str.split(":");
        if (strs.length < 2)
            return splitTextureDTOList;
        String key = strs[0];
        String value = strs[1];
        for (SplitTextureInfoDTO splitTextureDTO : splitTextureDTOList) {
            if (StringUtils.equals(key, splitTextureDTO.getKey())) {
                splitTextureDTO.setDefaultId(Integer.valueOf(value));
                break;
            }
        }
        return splitTextureDTOList;
    }

    /**
     * 移动端删除我的任务和设计
     */
    @Override
    public Object deteleMyTaskAndDesign(AutoRenderTaskState model) {
        int a = 0;
        model.setIsDeleted(new Integer(1));
        //逻辑删除我的任务
        a = designPlanAutoRenderMapper.updateTaskStateByTaskId(model);
        if (a == 0) {
            return new ResponseEnvelope<>(false, "删除我的任务失败");
        }
        Integer sceneId = model.getBusinessId();
        if (sceneId != null) {
            DesignPlanRenderScene scene = designPlanRenderSceneService.get(sceneId);
            if (scene != null) {
                scene.setIsDeleted(new Integer(1));
            } else {
                return new ResponseEnvelope<>(false, "参数businessId 错误");
            }
            try {
                //逻辑删除我的设计
                designPlanRenderSceneService.update(scene);
            } catch (Exception e) {
                return new ResponseEnvelope<>(false, "删除我的设计失败");
            }
        }

        return new ResponseEnvelope<>(true, "删除我的任务和我的设计成功！");
    }

}
