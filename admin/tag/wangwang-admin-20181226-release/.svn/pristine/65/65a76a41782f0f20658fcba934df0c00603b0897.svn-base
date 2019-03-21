package com.sandu.service.decoratecustomer.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.company.service.CompanyShopService;
import com.sandu.api.decoratecustomer.input.DecorateCustomerAdd;
import com.sandu.api.decoratecustomer.input.DecorateCustomerQuery;
import com.sandu.api.decoratecustomer.input.DecorateCustomerUpdate;
import com.sandu.api.decoratecustomer.model.DecorateCustomer;
import com.sandu.api.decoratecustomer.model.DecoratePrice;
import com.sandu.api.decoratecustomer.model.DecorateSeckill;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;
import com.sandu.api.decoratecustomer.service.DecorateCustomerService;
import com.sandu.api.decoratecustomer.service.DecoratePriceService;
import com.sandu.api.decoratecustomer.service.DecorateSecKillService;
import com.sandu.api.decoratecustomer.service.biz.DecorateCustomerBizService;
import com.sandu.api.decorateorder.input.DecorateOrderRefundUpdate;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.service.DecorateOrderService;
import com.sandu.api.decorateorder.service.biz.DecorateOrderBizService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.proprietorinfo.service.ProprietorInfoService;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.api.user.model.User;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.UserService;
import com.sandu.util.SocketIOUtil;
import com.sandu.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * <p>demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 14:27
 */
@Slf4j
@Service("decorateCustomerBizService")
public class DecorateCustomerBizServiceImpl implements DecorateCustomerBizService {

  private final DecorateCustomerService decorateCustomerService;

  private final CompanyService companyService;

  private final DictionaryService dictionaryService;

  private final DecoratePriceService decoratePriceService;

  private final BaseAreaService baseAreaService;

  private final ProprietorInfoService proprietorInfoService;

  private final DecorateSecKillService decorateSecKillService;

  private final DecorateOrderService decorateOrderService;

  private final DecorateOrderBizService decorateOrderBizService;

  private final UserService userService;

  public static final Gson gson = new Gson();

  @Value("${decorate.message.system.dispatchPrice}")
  private String dispatchMessage;

  @Value("${decorate.message.system.innerSet}")
  private String innerSetMessage;

  @Autowired private CompanyShopService companyShopService;

  @Autowired private SysUserService sysUserService;

  @Autowired
  public DecorateCustomerBizServiceImpl(
      DecorateCustomerService decorateCustomerService,
      CompanyService companyService,
      DictionaryService dictionaryService,
      DecoratePriceService decoratePriceService,
      BaseAreaService baseAreaService,
      ProprietorInfoService proprietorInfoService,
      DecorateSecKillService decorateSecKillService,
      DecorateOrderService decorateOrderService,
      DecorateOrderBizService decorateOrderBizService,
      UserService userService) {
    this.decorateCustomerService = decorateCustomerService;
    this.companyService = companyService;
    this.dictionaryService = dictionaryService;
    this.decoratePriceService = decoratePriceService;
    this.baseAreaService = baseAreaService;
    this.proprietorInfoService = proprietorInfoService;
    this.decorateSecKillService = decorateSecKillService;
    this.decorateOrderService = decorateOrderService;
    this.decorateOrderBizService = decorateOrderBizService;
    this.userService = userService;
  }

  @Override
  public int create(DecorateCustomerAdd input) {
    DecorateCustomer.DecorateCustomerBuilder builder = DecorateCustomer.builder();
    DecorateCustomer decorateCustomer = builder.build();
    BeanUtils.copyProperties(input, decorateCustomer);

    return decorateCustomerService.insert(decorateCustomer);
  }

  @Override
  public int update(DecorateCustomerUpdate input) {
    DecorateCustomer.DecorateCustomerBuilder builder = DecorateCustomer.builder();
    DecorateCustomer decorateCustomer = builder.build();

    BeanUtils.copyProperties(input, decorateCustomer);
    decorateCustomer.setId(input.getCustomerId());
    int result = decorateCustomerService.update(decorateCustomer);

    if (input.getDecorateType().equals(4)) {
      decoratePriceService
              .listByCustomerIds(Collections.singleton(input.getCustomerId()))
              .stream()
              .filter(it -> it.getStatus().equals(4))
              .limit(3)
              .forEach(
                      price -> {
                        DecorateOrder order = decorateOrderService.findOrderByPriceId(price.getId());
                        if (price.getCompanyId().equals(input.getContractCompany())) {
                          // 签约成功企业
                          order.setOrderStatus(5);
                          order.setContractStatus(0);
                          log.debug("start update order status, order id :{}", order.getId());
                          decorateOrderService.update(order);
                        } else {
                          // 退款企业
                          DecorateOrderRefundUpdate update = new DecorateOrderRefundUpdate();
                          update.setId(order.getId());
                          update.setOperateType(0);
                          update.setRefundRejectReason("系统自动退款");
                          decorateOrderBizService.updateRefund(update, input.getLoginUser().getId(), order);
                          order.setOrderStatus(6);
                          order.setContractFee(0 + "");
                          log.debug("start update order status, order id :{}", order.getId());
                          decorateOrderService.update(order);
                        }
                      });
    }
    return result;
  }

  @Override
  public int delete(String decorateCustomerId) {
    if (Strings.isNullOrEmpty(decorateCustomerId)) {
      return 0;
    }

    Set<Integer> decorateCustomerIds = new HashSet<>();
    List<String> list = Stringer.split(decorateCustomerId, ",");
    list.forEach(id -> decorateCustomerIds.add(Integer.valueOf(id)));

    if (decorateCustomerIds.size() == 0) {
      return 0;
    }
    return decorateCustomerService.delete(decorateCustomerIds);
  }

  @Override
  public DecorateCustomerBO getById(int decorateCustomerId) {
    DecorateCustomerBO customer =
        decorateCustomerService.getByIdWithProprietorInfo(decorateCustomerId);
    if (customer == null) {
      return null;
    }
    if (StringUtils.isNotBlank(customer.getAreaCode())) {
      BaseAreaListVO area = baseAreaService.queryAreaByCode(customer.getAreaCode());
      if (area != null) {
        customer.setAreaName(area.getAreaName());
      }
    }
    if (StringUtils.isNotBlank(customer.getCityCode())) {
      BaseAreaListVO city = baseAreaService.queryAreaByCode(customer.getCityCode());
      if (city != null) {
        customer.setCityName(city.getAreaName());
      }
    }
    // 设置字典数据
    setCustomerDictionaryValues(customer);
    return customer;
  }

  private void setCustomerDictionaryValues(DecorateCustomerBO customer) {
    if (customer.getDecorateBudget() != null) {
      Dictionary dictionary =
          dictionaryService.getByTypeAndValue("decorateBudget", customer.getDecorateBudget());
      if (dictionary != null) {
        customer.setDecorateBudgetStr(dictionary.getName());
      }
    }
    if (customer.getDecorateType() != null) {
      Dictionary dictionary =
          dictionaryService.getByTypeAndValue("decorateType", customer.getDecorateType());
      if (dictionary != null) {
        customer.setDecorateTypeStr(dictionary.getName());
      }
    }
    // 装修类型
    switch (customer.getDecorateHouseType()) {
      case 0:
        customer.setDecorateHouseTypeStr("新房装修");
        break;
      case 1:
        customer.setDecorateHouseTypeStr("旧房改造");
        break;
      default:
        break;
    }
    if (customer.getDecorateStyle() != null) {
      Dictionary dictionary =
          dictionaryService.getByTypeAndValue("goodStyle", customer.getDecorateStyle());
      if (dictionary != null) {
        customer.setDecorateStyleStr(dictionary.getName());
      }
    }
  }

  @Override
  public PageInfo<DecorateCustomerListBO> query(DecorateCustomerQuery query) {
    PageInfo<DecorateCustomerListBO> customers = decorateCustomerService.findAllWithBiz(query);
    Set<Integer> companyIds = new HashSet<>();
    customers
        .getList()
        .forEach(
            it -> {
              companyIds.add(it.getCompanyId());
              companyIds.add(it.getInnerCompanyId());
              companyIds.add(it.getFirstSecKillCompany());
              companyIds.add(it.getSecondSecKillCompany());
            });
    Map<Long, String> companyId2Name = companyService.idAndNameMap(new LinkedList<>(companyIds));

    // 设置客户相关信息
    setCustomerShowInfo(customers, companyId2Name);

    return customers;
  }

  private void setCustomerShowInfo(
      PageInfo<DecorateCustomerListBO> customers, Map<Long, String> companyId2Name) {

    // 装修进度
    Map<Integer, String> typeMap = dictionaryService.valueAndName2Map("customerDecorateType");
    // 获取列表客户报价
    List<DecoratePrice> decoratePrices =
        decoratePriceService.listByCustomerIds(
            customers
                .getList()
                .stream()
                .map(DecorateCustomerListBO::getId)
                .collect(Collectors.toSet()));
    // 获取所有报价公司信息
    Map<Long, String> priceCompanyName =
        companyService.idAndNameMap(
            decoratePrices
                .stream()
                .map(DecoratePrice::getCompanyId)
                .distinct()
                .collect(Collectors.toList()));

    for (DecorateCustomerListBO it : customers.getList()) {

      //设置业主信息
      it.setProprietorInfo((getById(it.getId())));

      if (it.getCompanyId() != null) {
        it.setCompanyName(companyId2Name.get(it.getCompanyId().longValue()));
      }
      if (it.getInnerCompanyId() != null) {
        it.setInnerCompanyName(companyId2Name.get(it.getInnerCompanyId().longValue()));
      }
      if (it.getFirstSecKillCompany() != null) {
        it.setCommitOrderFunc(true);
        it.setFirstSecKillCompanyName(companyId2Name.get(it.getFirstSecKillCompany().longValue()));
      }
      if (it.getSecondSecKillCompany() != null) {
        it.setSecondSecKillCompanyName(
            companyId2Name.get(it.getSecondSecKillCompany().longValue()));
      }
      // 城市
      BaseAreaListVO baseArea = baseAreaService.queryAreaByCode(it.getCityCode());
      if (baseArea != null) {
        it.setCityName(baseArea.getAreaName());
      }
      // 装修进度
      it.setDecorateTypeName(typeMap.get(it.getDecorateType()));

      it.setAllPriceList(new LinkedList<>());
      it.setChoosePriceList(new LinkedList<>());

      // 设置报价企业
      decoratePrices
          .stream()
          .filter(price -> price.getCustomerId().equals(it.getId()))
          .forEach(
              price -> {
                double v = price.getCheckFee() == null ? 0 : price.getCheckFee();
                double v1 = price.getDesignFee() == null ? 0 : price.getDesignFee();
                double v2 = price.getLabourFee() == null ? 0 : price.getLabourFee();
                double v3 = price.getMaterialFee() == null ? 0 : price.getMaterialFee();
                double totalFee = v1 + v2 + v3 + v;
                DecorateCustomerListBO.SimpleDecoratePriceInfo simplePrice =
                    DecorateCustomerListBO.SimpleDecoratePriceInfo.builder()
                        .name(priceCompanyName.get(price.getCompanyId().longValue()))
                        .price(totalFee / 10000)
                        .build();
                it.getAllPriceList().add(simplePrice);
                if (price.getStatus() != null && price.getStatus().equals(4)) {
                  it.getChoosePriceList().add(simplePrice);
                }
              });
    }
  }

  @Override
  public void dispatchDecoratePrice() {

    // 获取未指定
    List<DecorateCustomer> dCustomers = decorateCustomerService.findWithNullCompanyId();

    // 处理派单
    List<Integer> dispatchIds =
        dCustomers
            .stream()
            .filter(
                customer -> customer.getDesignPlanId() != null && customer.getDesignPlanId() > 0)
            .collect(Collectors.collectingAndThen(Collectors.toList(), this::fixWithDispatchPrice));

    log.info("decoratePrice Ids :{}", dispatchIds);

    // 处理抢单
    List<Integer> sedKillIds =
        dCustomers
            .stream()
            .filter(customer -> customer.getDesignPlanId() == null)
            .collect(
                Collectors.collectingAndThen(Collectors.toList(), this::fixWithDecorateSedKill));

    log.info("sedKill Ids :{}", sedKillIds);
  }

  // 预约装企客户生成订单
  @Override
  public List<Integer> initOrderForCompanyCustomer() {
    // 获取指定装企的业主
    List<DecorateCustomer> oCustomers =
        decorateCustomerService.listCustomerWithInnerCompanyAndNoneOrder();
    Map<Integer, Double> priceMap =
        dictionaryService
            .listByType("decorateBudget")
            .stream()
            .collect(
                Collectors.toMap(
                    Dictionary::getValue, it -> it.getNumAtt3() == null ? 100 : it.getNumAtt3()));
    List<Integer> orderIds = new LinkedList<>();
    for (DecorateCustomer customer : oCustomers) {
      // 生成订单
      ProprietorInfo info = proprietorInfoService.getById(customer.getProprietorInfoId());
      CompanyShop shop = companyShopService.getById(info.getShopId().intValue());
      User user = userService.selectById(shop.getUserId().longValue());
      if (user == null) {
        log.error("根据店铺获取用户失败,shopId:{},ProprietorInfo id:{}", info.getShopId(), info.getId());
        continue;
      }
      Date date = new Date();
      DecorateOrder order =
          DecorateOrder.builder()
              .customerId(customer.getId())
              .proprietorInfoId(customer.getProprietorInfoId())
              .companyId(customer.getCompanyId())
              .orderType(DecorateOrder.ORDER_TYPE_COMPANY)
              .orderStatus(2)
              .price(0 + "")
              .userId(user.getId())
              .creator("sanduAdmin")
              .modifier("sanduAdmin")
              .gmtCreate(date)
              .gmtModified(date)
              .isDeleted(0)
              .build();
      int id = decorateOrderService.insert(order);
      if (id > 0) {
        orderIds.add(order.getId());
        // 推送消息
        sendMessage("推荐业主", innerSetMessage, user, id);
      } else {
        log.error("生成订单失败,customerId:{}", customer.getId());
      }
    }
    log.info("auto init order Ids :{}", orderIds);
    return orderIds;
  }

  @Override
  public Boolean submitToSedKill(Integer customerId) {
    // 验证当前用户是否拥有提交抢单资格
    DecorateCustomer customer = decorateCustomerService.getById(customerId);
    if (customer.getFirstSeckillCompany() != null && customer.getSecondSeckillCompany() == null) {
      List<DecorateSeckill> list =
          decorateSecKillService.findSedKillByCustomerIds(Collections.singletonList(customerId));
      list.stream()
          .filter(
              it -> it.getCustomerId().equals(customerId) && it.getFirstSeckillCompany() == null)
          .findFirst()
          .ifPresent(
              it -> {
                Date date = new Date();
                // update it
                it.setFirstSeckillCompany(customer.getFirstSeckillCompany());
                //								it.setGmtModified(date);
                //								decorateSecKillService.update(it);

                // insert second
                DecorateSeckill second = new DecorateSeckill();
                BeanUtils.copyProperties(it, second);
                second.setId(null);
                second.setStatus((byte) 0);
                second.setGmtCreate(date);
                second.setGmtModified(date);
                decorateSecKillService.insertDecorateSedKill(second);
              });
      return true;
    }
    return false;
  }

  private List<Integer> fixWithDispatchPrice(List<DecorateCustomer> list) {


    // 过滤出未派单的客户
    List<Integer> noneDispatchCustomerIds =
        decoratePriceService.findNoneDispatchCustomerIds(
            list.stream().map(DecorateCustomer::getId).collect(Collectors.toList()));

    List<Integer> resultIds = new LinkedList<>();
    list.stream()
        .filter(it -> noneDispatchCustomerIds.contains(it.getId()))
        .forEach(
            customer -> {
              List<Integer> companyIds =
                  decoratePriceService.listCompanyIdForDispatchPrice(
                      customer.getProprietorInfoId(), 10);
				log.info("init decorate price companyIds is ----{}", companyIds);
              List<DecoratePrice> insertList = new LinkedList<>();
              Date date = new Date();
              //							Date endDate = DateUtils.addMinutes(date, 4);
              Date endDate = DateUtils.addDays(date, 1);
              companyIds
                  .stream()
                  .distinct()
                  .forEach(
                      companyId -> {
                        insertList.add(
                            DecoratePrice.builder()
                                .customerId(customer.getId())
                                .companyId(companyId)
                                .proprietorInfoId(customer.getProprietorInfoId())
                                .price(899 + "")
                                .status(0)
                                .startTime(date)
                                .endTime(endDate)
                                .creator("sanduAdmin")
                                .modifier("sanduAdmin")
                                .gmtCreate(date)
                                .gmtModified(date)
                                .build());
                      });
              decoratePriceService.insertList(insertList);
              customer.setBidStatus(0);
              customer.setBidEndTime(endDate);
              decorateCustomerService.update(customer);
              insertList.forEach(insert -> resultIds.add(insert.getId()));
              // 推送消息
              for (DecoratePrice price : insertList) {
                HashSet<Integer> companyIdsSet = new HashSet<>();
                companyIdsSet.add(price.getCompanyId());
                List<User> users = userService.listUserByCompanyIds(companyIdsSet);
                users
                    .stream()
                    .distinct()
                    .forEach(
                        it -> {
                          System.out.println(it);
                          sendMessage("系统派单", dispatchMessage, it, price.getId());
                        });
              }
            });
    return resultIds;
  }

  private List<Integer> fixWithDecorateSedKill(List<DecorateCustomer> list) {

    // 获取未抢单的客户
    List<Integer> noneSedKillCustomerIds =
        decorateSecKillService.findNoneSedKillCustomerIds(
            list.stream().map(DecorateCustomer::getId).collect(Collectors.toList()));
    Map<Integer, Double> priceMap =
        dictionaryService
            .listByType("decorateBudget")
            .stream()
            .collect(
                Collectors.toMap(
                    Dictionary::getValue, it -> it.getNumAtt3() == null ? 100 : it.getNumAtt3()));
    priceMap.put(null, 0D);
    Date date = new Date();
    List<DecorateSeckill> insertList = new LinkedList<>();
    list.stream()
        .filter(it -> noneSedKillCustomerIds.contains(it.getId()))
        .forEach(
            customer -> {
              insertList.add(
                  DecorateSeckill.builder()
                      .customerId(customer.getId())
                      .proprietorInfoId(customer.getProprietorInfoId())
                      .price(priceMap.get(customer.getDecorateBudget()))
                      .status((byte) 0)
                      .gmtCreate(date)
                      .creator("sanduAdmin")
                      .gmtModified(date)
                      .modifier("sanduAdmin")
                      .isDeleted(0)
                      .build());
            });
    decorateSecKillService.insertDecorateSeckillList(insertList);

    return insertList.stream().map(DecorateSeckill::getId).collect(Collectors.toList());
  }

  private void sendMessage(String title, String content, User user, Integer bussnessId) {
    // save message to db
    SysUserMessage message =
        decorateOrderBizService.insertSysUserMessage(title, content, -1, bussnessId, user.getId());

    // push message
    String messageFinal = gson.toJson(message);
    log.info("updateRefund开始推送消息,开始时间{}", DateUtil.formatDate(new Date()));
    log.info("updateRefund消息体{}", messageFinal);
    SocketIOUtil.handlerSendMessage(
        SocketIOUtil.IM_PUSH_MSG_EVENT,
        SocketIOUtil.IM_PUSH_SYSTEM_MSG_CODE,
        user.getUuid(),
        messageFinal);
  }
}
