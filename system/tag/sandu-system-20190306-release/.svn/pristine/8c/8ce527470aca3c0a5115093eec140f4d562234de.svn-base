package com.sandu.service.servicepurchase.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sandu.api.dictionary.input.SysDictionaryQuery;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.servicepurchase.input.ServicesBaseInfoAdd;
import com.sandu.api.servicepurchase.input.ServicesBaseInfoUpdate;
import com.sandu.api.servicepurchase.input.ServicesPriceAdd;
import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.model.*;
import com.sandu.api.servicepurchase.model.bo.ServicesFuncBO;
import com.sandu.api.servicepurchase.model.bo.ServicesListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseLogListBO;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import com.sandu.api.servicepurchase.serivce.ServicesBaseInfoService;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.service.servicepurchase.dao.ServiceActionDetailsLogMapper;
import com.sandu.service.servicepurchase.dao.ServicesBaseInfoMapper;
import com.sandu.service.servicepurchase.dao.ServicesPriceMapper;
import com.sandu.service.servicepurchase.dao.ServicesRoleRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("servicesBaseInfoService")
@Slf4j
public class ServicesBaseInfoServiceImpl implements ServicesBaseInfoService {

    @Resource
    private ServicesBaseInfoMapper servicesBaseInfoMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private ServicesPriceMapper servicesPriceMapper;
    @Resource
    private ServicesRoleRelMapper servicesRoleRelMapper;
    @Resource
    private ServiceActionDetailsLogMapper serviceActionDetailsLogMapper;

    private final static String REDIS_KEY_PIX = "SERVICES_BASE_INFO_";

    @Override
    public PageInfo<ServicesListBO> getServicesListByUserScope(List<Integer> userTypes, String saleChannel, Integer page, Integer limit) {
        if (userTypes.isEmpty()) {
            return new PageInfo<>();
        }
        PageHelper.startPage(page, limit);

        PageInfo<ServicesListBO> result = new PageInfo<>(servicesBaseInfoMapper.getServicesListByUserScopeAndSaleChannel(userTypes, saleChannel));
//        List<ServicesListBO> list = result.getList().stream()
//                .filter(bo -> {
//                    AtomicBoolean ret = new AtomicBoolean(false);
//                    userTypes.forEach(type -> {
//                        if (bo.getUserScope().contains(String.valueOf(type))) {
//                            ret.set(true);
//                        }
//                    });
//                    return ret.get();
//                }).collect(Collectors.toList());
        return result;
    }

    @Override
    public ServicesBaseInfo getById(Long id) {
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "ID_" + id;
        ServicesBaseInfo servicesBaseInfo;
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return (ServicesBaseInfo) JSON.parse(json);
//        }
//        log.info("套餐价格信息缓存不存在=================");
        servicesBaseInfo = servicesBaseInfoMapper.selectByPrimaryKey(id);
//        String jsonString = JSON.toJSONString(servicesBaseInfo);
//        ops.set(redisKey, jsonString, 3000, TimeUnit.MILLISECONDS);
        return servicesBaseInfo;
    }

    @Override
    public PageInfo<ServicesPurchaseLogListBO> getPurchasedServicesLogsByCompanyId(Integer companyId, Integer start, Integer limit) {
        PageHelper.startPage(start, limit);
        return new PageInfo<>(servicesBaseInfoMapper.getPurchasedServicesLogsByCompanyId(companyId));
    }

    @Override
    public List<ServicesBaseInfo> getServicesByType(String servicesType, String saleChannel) {
        return servicesBaseInfoMapper.getServicesByType(servicesType, saleChannel);
    }

    @Override
    public List<ServicesBaseInfo> queryByOption(ServicesBaseInfoQuery servicesBaseInfoQuery) {
        List<ServicesBaseInfo> servicesBaseInfoList;
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String redisKey = REDIS_KEY_PIX + "QUERY_OPTION_" + JSON.toJSONString(servicesBaseInfoQuery);
//        if (stringRedisTemplate.hasKey(redisKey)) {
//            log.info("套餐价格信息缓存开启=================");
//            //开启缓存
//            String json = ops.get(redisKey);
//            return JSONArray.parseArray(json, ServicesBaseInfo.class);
//        }
//
//        log.info("套餐价格信息缓存不存在=================");
        servicesBaseInfoList = servicesBaseInfoMapper.queryByOption(servicesBaseInfoQuery);
//        String listJson = JSON.toJSONString(servicesBaseInfoList);
//        ops.set(redisKey, listJson, 3000, TimeUnit.MILLISECONDS);
        return servicesBaseInfoList;
    }

    @Override
    public List<ServicesBaseInfo> queryBuyServices(Long noId, String userScope, String saleChannel, String servicesStatus, Integer servicesType,
                                                   Integer isDeleted, Integer start, Integer limit) {
        ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
        servicesBaseInfoQuery.setUserScope(userScope);
        servicesBaseInfoQuery.setSaleChannel(saleChannel);
        servicesBaseInfoQuery.setServicesStatus(servicesStatus);
        servicesBaseInfoQuery.setServicesType(servicesType);
        servicesBaseInfoQuery.setIsDeleted(isDeleted);
        servicesBaseInfoQuery.setStart(start);
        servicesBaseInfoQuery.setLimit(limit);
        servicesBaseInfoQuery.setNoId(noId);
        return this.queryByOption(servicesBaseInfoQuery);
    }

    @Override
    public ServicesBaseInfo queryReBuyServices(Long id, Set<String> userScopeSet, String saleChannel, String servicesStatus, Integer servicesType, Integer isDeleted) {
        if (CollectionUtils.isEmpty(userScopeSet)) {
            return null;
        }
        ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
        servicesBaseInfoQuery.setId(id);
        servicesBaseInfoQuery.setUserScopeSet(userScopeSet);
        servicesBaseInfoQuery.setSaleChannel(saleChannel);
        servicesBaseInfoQuery.setServicesStatus(servicesStatus);
        servicesBaseInfoQuery.setServicesType(servicesType);
        servicesBaseInfoQuery.setIsDeleted(isDeleted);
        List<ServicesBaseInfo> servicesBaseInfoList = this.queryByOption(servicesBaseInfoQuery);
        if (CollectionUtils.isEmpty(servicesBaseInfoList)) {
            return null;
        }
        return servicesBaseInfoList.get(0);
    }

    @Override
    public int countQueryBuyServices(Long noId, String userScope, String saleChannel, String servicesStatus, Integer servicesType, Integer isDeleted) {
        ServicesBaseInfoQuery servicesBaseInfoQuery = new ServicesBaseInfoQuery();
        servicesBaseInfoQuery.setUserScope(userScope);
        servicesBaseInfoQuery.setSaleChannel(saleChannel);
        servicesBaseInfoQuery.setServicesStatus(servicesStatus);
        servicesBaseInfoQuery.setServicesType(servicesType);
        servicesBaseInfoQuery.setIsDeleted(isDeleted);
        servicesBaseInfoQuery.setNoId(noId);
        return servicesBaseInfoMapper.countQueryByOption(servicesBaseInfoQuery);
    }

    @Override
    public List<ServicesBaseInfo> getByIds(Set<Long> collect) {
        if (collect != null && collect.isEmpty()) {
            return Collections.emptyList();
        }
        return servicesBaseInfoMapper.getByIds(collect);
    }

    @Override
    public List<ServicesBaseInfo> getServicesListByUserScopeAndTye(String servicesType, String saleChannel, String userScope) {
        return servicesBaseInfoMapper.selectServicesListByUserScopeAndTye(servicesType, saleChannel, userScope);
    }

    @Override
    public PageInfo<ServicesBaseInfo> queryAll(ServicesBaseInfoQuery query) {
        log.info("##################{}",query);
        PageHelper.startPage(query.getStart(), query.getLimit());
        List<ServicesBaseInfo> services = servicesBaseInfoMapper.queryServiceBaseInfo(query);
        for (ServicesBaseInfo service : services) {

            SysDictionaryQuery sysDictionary = new SysDictionaryQuery();
            sysDictionary.setType("userType");
            sysDictionary.setValue(Integer.valueOf(service.getUserScope()));
            sysDictionary.setIsDeleted(0);
            List<SysDictionary> userTypeList = dictionaryService.queryByOption(sysDictionary);

            String scopeName = userTypeList.get(0).getName();
            service.setScopeName(scopeName);
            String[] saleId = service.getSaleChannel().split(",");
            StringBuilder sb = new StringBuilder();
            for (String id : saleId) {
                sysDictionary.setType("saleChannel");
                sysDictionary.setValue(Integer.valueOf(id));
                List<SysDictionary> saleChannelList = dictionaryService.queryByOption(sysDictionary);
                sb.append(saleChannelList.get(0).getName()).append("，");
            }

            service.setSaleNames(sb.toString().substring(0, sb.length() - 1));
        }
        return new PageInfo<>(services);
    }

    @Override
    @Transactional
    public ResponseEnvelope saveServices(ServicesBaseInfoAdd input) {
        ServicesBaseInfo servicesBaseInfo = new ServicesBaseInfo();
        BeanUtils.copyProperties(input, servicesBaseInfo);
        servicesBaseInfo.setGmtCreate(new Date());
        Integer result = servicesBaseInfoMapper.insertSelective(servicesBaseInfo);
        if (result > 0) {
            //套餐编码
            ServicesBaseInfo update = new ServicesBaseInfo();
            StringBuilder sb = new StringBuilder("s");
            for (int i = 0; i < 6 - servicesBaseInfo.getId().toString().length(); i++) {
                sb.append("0");
            }
            update.setId(servicesBaseInfo.getId());
            update.setServicesCode(sb.append(servicesBaseInfo.getId()).toString());
            servicesBaseInfoMapper.updateByPrimaryKeySelective(update);

            //获取套餐写入的价格信息
            for (ServicesPriceAdd priceAdd : input.getPrices()) {
                Integer tmp = null;
                if(!Strings.isNullOrEmpty(priceAdd.getPriceUnit())) {
                    tmp = Integer.valueOf(priceAdd.getPriceUnit());
                }
                if (servicesPriceMapper.getPriceInfo(servicesBaseInfo.getId().intValue(), "-1", priceAdd.getDuration(), tmp) != null) {
                    throw new RuntimeException("输入数据有误,已有相同价格信息套餐");
//                    return new ResponseEnvelope<>(false, "输入数据有误,已有相同价格信息套餐");
                }
                //保存价格信息到套餐价格表
                ServicesPrice price = new ServicesPrice();
                price.setServicesId(servicesBaseInfo.getId());
                price.setPrice(BigDecimal.valueOf(priceAdd.getPrice()));
                price.setDuration(priceAdd.getDuration());
                price.setGiveDuration(priceAdd.getGiveDuration() == null ? 0 : priceAdd.getGiveDuration());
                price.setFreeRenderDuration(priceAdd.getFreeRenderDuration());
                price.setSanduCurrency(priceAdd.getSanduCurrency());
                price.setCompanyId(-1);
                price.setPriceUnit(priceAdd.getPriceUnit());
                price.setGmtCreate(new Date());
                price.setCreator(input.getCreator());
                servicesPriceMapper.insertSelective(price);
            }
            ServiceActionDetailsLog serviceLog = new ServiceActionDetailsLog();
            serviceLog.setActionType(1);
            serviceLog.setRemark("新增套餐id => "+servicesBaseInfo.getId());
            serviceLog.setCreator(input.getCreator());
            serviceLog.setGmtCreate(new Date());
            serviceActionDetailsLogMapper.insertSelective(serviceLog);
            return new ResponseEnvelope<>(true, "新增成功!", result);
        }
        return new ResponseEnvelope<>(false, "输入数据有误,新增套餐失败");
    }

    @Override
    @Transactional
    public ResponseEnvelope updateServices(ServicesBaseInfoUpdate input) {
        //修改套餐基本信息
        ServicesBaseInfo servicesBaseInfo = new ServicesBaseInfo();
        servicesBaseInfo.setId(Long.valueOf(input.getId()));
        servicesBaseInfo.setServicesName(input.getServicesName());
        servicesBaseInfo.setServiceDesc(input.getServiceDesc());
        servicesBaseInfo.setUserScope(input.getUserScope());
        servicesBaseInfo.setSaleChannel(input.getSaleChannel());
        servicesBaseInfo.setServicesType(input.getServicesType());
        servicesBaseInfo.setGmtModified(new Date());
        servicesBaseInfo.setRemark(input.getRemark());
        servicesBaseInfo.setModifier(input.getModifier());
        int result = servicesBaseInfoMapper.updateByPrimaryKeySelective(servicesBaseInfo);
        if (result > 0) {
            //查询原套餐价格信息id
            List<ServicesPrice> oldPriceInfo = servicesPriceMapper.queryServicesPriceBySid(servicesBaseInfo.getId().intValue());
            List<Integer> delIds = new ArrayList<>();
            for (ServicesPrice temp : oldPriceInfo) {
                delIds.add(temp.getId().intValue());
            }
            //删除已取消的套餐价格信息
            if(input.getPrices() == null ||input.getPrices().size() == 0) {
                throw new RuntimeException("请输入完整的价格信息");
            }
            for (int i = 0; i < input.getPrices().size(); i++) {
                if (input.getPrices().get(i).getId() != 0) {
                    if (delIds.contains(input.getPrices().get(i).getId())) {
                        delIds.remove(input.getPrices().get(i).getId());
                    }
                }
            }
            delIds.forEach(id -> servicesPriceMapper.deleteByPrimaryKey(id.longValue()));
            //判断价格信息为新加入还是修改（新加入价格信息id为0）
            for (int i = 0; i < input.getPrices().size(); i++) {
                if (input.getPrices().get(i).getId() != 0) {
                    Integer tmp = null;
                    if(!Strings.isNullOrEmpty(input.getPrices().get(i).getPriceUnit())) {
                        tmp = Integer.valueOf(input.getPrices().get(i).getPriceUnit());
                    }
                    ServicesPrice res1= servicesPriceMapper.getPriceInfo(input.getId(), "-1", input.getPrices().get(i).getDuration(), tmp);
                    if (res1 != null) {
                        ServicesPrice res2 = servicesPriceMapper.getPriceInfo(input.getId(), "-1", input.getPrices().get(i).getDuration(), tmp);
                        if (res2.getId().intValue() != input.getPrices().get(i).getId()) {
                            throw new RuntimeException("输入数据有误,已有相同价格信息套餐");
                        }
                    }
                    ServicesPrice price = new ServicesPrice();
                    price.setId(input.getPrices().get(i).getId().longValue());
                    price.setServicesId(servicesBaseInfo.getId());
                    price.setPrice(BigDecimal.valueOf(input.getPrices().get(i).getPrice()));
                    price.setDuration(input.getPrices().get(i).getDuration());
                    price.setGiveDuration(input.getPrices().get(i).getGiveDuration() == null ? 0 : input.getPrices().get(i).getGiveDuration());
                    price.setPriceUnit(input.getPrices().get(i).getPriceUnit().toString());
                    price.setSanduCurrency(input.getPrices().get(i).getSanduCurrency());
                    price.setFreeRenderDuration(input.getPrices().get(i).getFreeRenderDuration());
                    price.setCompanyId(-1);
                    price.setGmtModified(new Date());
                    price.setModifier(input.getModifier());
                    price.setIsDeleted(0);
                    if (servicesPriceMapper.updateByPrimaryKeySelective(price) <= 0) {
                        throw new RuntimeException("输入数据有误,修改套餐失败");
                    }
                } else {
                    Integer tmp = null;
                    if(!Strings.isNullOrEmpty(input.getPrices().get(i).getPriceUnit())) {
                        tmp = Integer.valueOf(input.getPrices().get(i).getPriceUnit());
                    }
                    if (servicesPriceMapper.getPriceInfo(input.getId(), "-1", input.getPrices().get(i).getDuration(), tmp) != null) {
                        throw new RuntimeException("输入数据有误,已有相同价格信息套餐");
                    }
                    ServicesPrice price = new ServicesPrice();
                    price.setServicesId(servicesBaseInfo.getId());
                    price.setPrice(BigDecimal.valueOf(input.getPrices().get(i).getPrice()));
                    price.setDuration(input.getPrices().get(i).getDuration());
                    price.setGiveDuration(input.getPrices().get(i).getGiveDuration() == null ? 0 : input.getPrices().get(i).getGiveDuration());
                    price.setPriceUnit(input.getPrices().get(i).getPriceUnit().toString());
                    price.setSanduCurrency(input.getPrices().get(i).getSanduCurrency());
                    price.setFreeRenderDuration(input.getPrices().get(i).getFreeRenderDuration());
                    price.setCreator(input.getCreator());
                    price.setCompanyId(-1);
                    price.setGmtCreate(new Date());
                    price.setCreator(input.getModifier());
                    if (servicesPriceMapper.insertSelective(price) <= 0) {
                        throw new RuntimeException("输入数据有误,修改套餐失败");
                    }
                }
            }
            ServiceActionDetailsLog serviceLog = new ServiceActionDetailsLog();
            serviceLog.setActionType(1);
            serviceLog.setRemark("修改套餐id => "+servicesBaseInfo.getId());
            serviceLog.setCreator(input.getModifier());
            serviceLog.setGmtCreate(new Date());
            serviceActionDetailsLogMapper.insertSelective(serviceLog);
            return new ResponseEnvelope<>(true, "修改成功!", result);
        }
        return new ResponseEnvelope<>(false, "输入数据有误,修改套餐失败");
    }

    @Override
    public int deleteSetMeal(String id,String creator) {
        try {
            //删除套餐
            Integer result = servicesBaseInfoMapper.deleteByPrimaryKey(Long.valueOf(id));
            //删除套餐关联价格
            servicesPriceMapper.deleteServicesPrice(Integer.valueOf(id));
            //删除套餐关联角色
            servicesRoleRelMapper.deleteByServicesId(id);
            ServiceActionDetailsLog serviceLog = new ServiceActionDetailsLog();
            serviceLog.setActionType(1);
            serviceLog.setRemark("删除套餐id => "+id);
            serviceLog.setCreator(creator);
            serviceLog.setGmtCreate(new Date());
            serviceActionDetailsLogMapper.insertSelective(serviceLog);
            return result;
        } catch (Exception e) {
            log.error("######删除套餐失败#######:{}", e.getMessage());
            return 0;
        }
    }

    @Override
    public int openOrCloseServices(String id, String servicesStatus) {
        Integer status;
        if (Integer.valueOf(servicesStatus) == 0) {
            status = 1;
        } else {
            status = 0;
        }
        return servicesBaseInfoMapper.openOrCloseServices(Integer.valueOf(id), status);
    }

    @Override
    public ServicesBaseInfo getServicesById(int id) {
        ServicesBaseInfo service = servicesBaseInfoMapper.selectByPrimaryKey(Long.valueOf(id));
        List<ServicesPrice> prices = servicesPriceMapper.queryServicesPriceBySid(Math.toIntExact(service.getId()));
        List<ServicesPriceAdd> adds = Lists.newArrayList();
        for (ServicesPrice price : prices) {
            ServicesPriceAdd priceAdd = new ServicesPriceAdd();
            priceAdd.setId(price.getId().intValue());
            priceAdd.setPrice(price.getPrice().floatValue());
            priceAdd.setDuration(price.getDuration());
            priceAdd.setGiveDuration(price.getGiveDuration());
            priceAdd.setPriceUnit(price.getPriceUnit());
            priceAdd.setFreeRenderDuration(price.getFreeRenderDuration());
            priceAdd.setSanduCurrency(price.getSanduCurrency());
            adds.add(priceAdd);
        }
        service.setPrices(adds);
        return service;
    }

    @Override
    @Transactional
    public ResponseEnvelope addCompanyPrice(ServicesBaseInfoUpdate input) {
        String[] companyId = input.getCompanyId().split(",");
        int result = 0;
        for (String cid : companyId) {
            for (int i = 0; i < input.getPrices().size(); i++) {
                Integer tmp = null;
                if(!Strings.isNullOrEmpty(input.getPrices().get(i).getPriceUnit())) {
                    tmp = Integer.valueOf(input.getPrices().get(i).getPriceUnit());
                }
                if (servicesPriceMapper.getPriceInfo(input.getId(), cid, input.getPrices().get(i).getDuration(), tmp) != null) {
                    throw new RuntimeException("输入数据有误,已有相同价格信息套餐");
                }
                ServicesPrice price = new ServicesPrice();
                price.setServicesId(input.getId().longValue());
                price.setPrice(BigDecimal.valueOf(input.getPrices().get(i).getPrice()));
                price.setDuration(input.getPrices().get(i).getDuration());
                price.setGiveDuration(input.getPrices().get(i).getGiveDuration() == null ? 0 : input.getPrices().get(i).getGiveDuration());
                price.setPriceUnit(input.getPrices().get(i).getPriceUnit().toString());
                price.setSanduCurrency(input.getPrices().get(i).getSanduCurrency());
                price.setFreeRenderDuration(input.getPrices().get(i).getFreeRenderDuration());
                price.setCompanyId(Integer.valueOf(cid));
                price.setCreator(input.getCreator());
                price.setGmtCreate(new Date());
                result = servicesPriceMapper.insertSelective(price);
                if (result <= 0) {
                    throw new RuntimeException("输入数据有误,新增套餐失败");
                }
            }
        }
        return new ResponseEnvelope<>(true, "新增成功!", result);
    }

    @Override
    public List<ServicesPriceVO> queryServicesPriceInfo(ServicesBaseInfoQuery query) {
        List<ServicesPriceVO> results = new ArrayList<>();
        List<ServicesPrice> priceLists = servicesPriceMapper.queryPriceForCompany(query);
        List<Integer> companyIds = new ArrayList<>();
        for (ServicesPrice price : priceLists) {
            companyIds.add(price.getCompanyId());
        }

        if(companyIds == null || companyIds.size() == 0) {
            return null;
        }
        List<ServicesPrice> priceList = servicesPriceMapper.queryPriceForCompanyByPage(query.getId().intValue(), companyIds);

        for (int i = 0; i < priceList.size(); i++) {
            if (i != 0 && priceList.get(i).getCompanyId().equals(priceList.get(i - 1).getCompanyId())) {
                for (ServicesPriceVO result : results) {
                    List<ServicesPriceAdd> adds = result.getPrices();
                    if (result.getCompanyId().equals(priceList.get(i).getCompanyId())) {
                        ServicesPriceAdd add = new ServicesPriceAdd();
                        add.setPrice(priceList.get(i).getPrice().floatValue());
                        add.setDuration(priceList.get(i).getDuration());
                        add.setPriceUnit(priceList.get(i).getPriceUnit());
                        add.setGiveDuration(priceList.get(i).getGiveDuration());
                        add.setFreeRenderDuration(priceList.get(i).getFreeRenderDuration());
                        add.setSanduCurrency(priceList.get(i).getSanduCurrency());
                        adds.add(add);
                    }
                    result.setPrices(adds);
                }
            } else {
                ServicesPriceVO vo = new ServicesPriceVO();
                vo.setCompanyId(priceList.get(i).getCompanyId());
                vo.setCompanyName(servicesPriceMapper.getCompanyList(priceList.get(i).getCompanyId()).getCompanyName());
                vo.setCompanyCode(servicesPriceMapper.getCompanyList(priceList.get(i).getCompanyId()).getCompanyCode());
                List<ServicesPriceAdd> adds = Lists.newArrayList();
                ServicesPriceAdd add = new ServicesPriceAdd();
                add.setPrice(priceList.get(i).getPrice().floatValue());
                add.setDuration(priceList.get(i).getDuration());
                add.setPriceUnit(priceList.get(i).getPriceUnit());
                add.setGiveDuration(priceList.get(i).getGiveDuration());
                add.setFreeRenderDuration(priceList.get(i).getFreeRenderDuration());
                add.setSanduCurrency(priceList.get(i).getSanduCurrency());
                adds.add(add);
                vo.setPrices(adds);
                results.add(vo);
            }
        }
        if (results.size() > 0) {
            results.get(0).setServicesId(query.getId());
        }
        return results;
    }

    @Override
    public int deleteCompanyPrice(Integer companyId, Integer servicesId) {
        return servicesPriceMapper.deleteCompanyPrice(companyId, servicesId);
    }

    @Override
    public ServicesBaseInfo getCompanyPriceDetail(Integer companyId, Integer servicesId) {
        List<ServicesPrice> prices = servicesPriceMapper.getPriceByCompanyId(companyId, servicesId);
        ServicesBaseInfo result = new ServicesBaseInfo();

        List<ServicesPriceAdd> adds = Lists.newArrayList();
        for (ServicesPrice price : prices) {
            ServicesPriceAdd priceAdd = new ServicesPriceAdd();
            priceAdd.setId(price.getId().intValue());
            priceAdd.setPrice(price.getPrice().floatValue());
            priceAdd.setDuration(price.getDuration());
            priceAdd.setGiveDuration(price.getGiveDuration());
            priceAdd.setPriceUnit(price.getPriceUnit());
            priceAdd.setFreeRenderDuration(price.getFreeRenderDuration());
            priceAdd.setSanduCurrency(price.getSanduCurrency());
            adds.add(priceAdd);
        }
        result.setPrices(adds);
        result.setCompanyId(companyId);
        result.setCompanyName(servicesPriceMapper.getCompanyList(companyId).getCompanyName());
        result.setId(servicesId.longValue());
        return result;
    }

    @Override
    public ResponseEnvelope updateCompanyPrice(ServicesBaseInfoUpdate input) {
        List<ServicesPrice> oldPriceInfo = servicesPriceMapper.getPriceByCompanyId(Integer.valueOf(input.getCompanyId()), input.getId());
        List<Integer> delIds = new ArrayList<>();
        for (ServicesPrice temp : oldPriceInfo) {
            delIds.add(temp.getId().intValue());
        }
        for (int i = 0; i < input.getPrices().size(); i++) {
            if (input.getPrices().get(i).getId() != 0) {
                if (delIds.contains(input.getPrices().get(i).getId())) {
                    delIds.remove(input.getPrices().get(i).getId());
                }
            }
        }
        delIds.forEach(id -> servicesPriceMapper.deleteByPrimaryKey(Long.valueOf(id)));
        for (int i = 0; i < input.getPrices().size(); i++) {
            if (input.getPrices().get(i).getId() != 0) {
                Integer tmp = null;
                if(!Strings.isNullOrEmpty(input.getPrices().get(i).getPriceUnit())) {
                    tmp = Integer.valueOf(input.getPrices().get(i).getPriceUnit());
                }
                if (servicesPriceMapper.getPriceInfo(input.getId(), input.getCompanyId(), input.getPrices().get(i).getDuration(), tmp) != null) {
                    if (servicesPriceMapper.getPriceInfo(input.getId(), input.getCompanyId(),
                                        input.getPrices().get(i).getDuration(), tmp).getId().intValue() != input.getPrices().get(i).getId()) {
                        throw new RuntimeException("输入数据有误,已有相同价格信息套餐");
                    }
                }
                ServicesPrice price = new ServicesPrice();
                price.setId(input.getPrices().get(i).getId().longValue());
                price.setServicesId(input.getId().longValue());
                price.setPrice(BigDecimal.valueOf(input.getPrices().get(i).getPrice()));
                price.setDuration(input.getPrices().get(i).getDuration());
                price.setGiveDuration(input.getPrices().get(i).getGiveDuration() == null ? 0 : input.getPrices().get(i).getGiveDuration());
                price.setPriceUnit(input.getPrices().get(i).getPriceUnit().toString());
                price.setSanduCurrency(input.getPrices().get(i).getSanduCurrency());
                price.setFreeRenderDuration(input.getPrices().get(i).getFreeRenderDuration());
                price.setCompanyId(Integer.valueOf(input.getCompanyId()));
                price.setGmtModified(new Date());
                price.setModifier(input.getModifier());
                price.setIsDeleted(0);
                if (servicesPriceMapper.updateByPrimaryKeySelective(price) <= 0) {
                    throw new RuntimeException("输入数据有误,修改企业价格设置失败");
                }
            } else {
                Integer tmp = null;
                if(!Strings.isNullOrEmpty(input.getPrices().get(i).getPriceUnit())) {
                    tmp = Integer.valueOf(input.getPrices().get(i).getPriceUnit());
                }
                if (servicesPriceMapper.getPriceInfo(input.getId(), input.getCompanyId(), input.getPrices().get(i).getDuration(), tmp) != null) {
                    throw new RuntimeException("输入数据有误,已有相同价格信息套餐");
                }
                ServicesPrice price = new ServicesPrice();
                price.setServicesId(input.getId().longValue());
                price.setPrice(BigDecimal.valueOf(input.getPrices().get(i).getPrice()));
                price.setDuration(input.getPrices().get(i).getDuration());
                price.setGiveDuration(input.getPrices().get(i).getGiveDuration() == null ? 0 : input.getPrices().get(i).getGiveDuration());
                price.setPriceUnit(input.getPrices().get(i).getPriceUnit().toString());
                price.setSanduCurrency(input.getPrices().get(i).getSanduCurrency());
                price.setFreeRenderDuration(input.getPrices().get(i).getFreeRenderDuration());
                price.setCreator(input.getCreator());
                price.setCompanyId(Integer.valueOf(input.getCompanyId()));
                price.setGmtCreate(new Date());
                price.setCreator(input.getModifier());
                    if (servicesPriceMapper.insertSelective(price) <= 0) {
                    throw new RuntimeException("输入数据有误,修改企业价格设置失败");
                }
            }
        }
        return new ResponseEnvelope<>(true, "修改成功!");
    }

    @Override
    public List<ServicesFuncBO> getFunctionList(Integer servicesId) {
        //查询套餐相关功能
        List<ServicesRole> platformList;
        if(servicesId != null) {
            platformList = servicesBaseInfoMapper.getFunctionList(servicesId);
        }else{
            platformList = servicesBaseInfoMapper.getInitFunctionList();
        }
        if (platformList != null && platformList.size() > 0) {
            List<ServicesFuncBO> results = Lists.newArrayList();
            for (ServicesRole tmp : platformList) {
            //相关角色id组
            List<String> roleIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(tmp.getAtt2()));
            //相关角色名称组
            List<String> roleNames = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(tmp.getAtt3()));
                ServicesFuncBO func = new ServicesFuncBO();
                func.setId(tmp.getPlatformId().intValue());
                func.setLabel(tmp.getPlatformName());
                List<ServicesRole> children = Lists.newArrayList();
                for (int i = 0; i < roleIds.size(); i++) {
                    ServicesRole role = new ServicesRole();
                    role.setId(Integer.valueOf(roleIds.get(i)));
                    role.setLabel(roleNames.get(i));
                    role.setParentId(func.getId());
                    children.add(role);
                }
                func.setChildren(children);
                results.add(func);
            }
            return results;
        }
        return null;
    }

    @Override
    public Integer saveFunction(Integer servicesId, String roleIds) {
        try {
            //新角色
            List<String> newRoleIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(roleIds));
            List<ServicesRole> platformList = servicesBaseInfoMapper.getFunctionList(servicesId);
            //旧角色
            List<String> oldRoleIds = Lists.newArrayList();
            for(ServicesRole tmp : platformList) {
                List<String> roleIdList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(tmp.getAtt2()));
                oldRoleIds.addAll(roleIdList);
            }

            Set<Integer> ids = new HashSet<>();
            newRoleIds.forEach(n -> {
                if(!oldRoleIds.contains(n)) {
                    ids.add(Integer.valueOf(n));
                    //新增角色
                    ServicesRoleRel roleRel = new ServicesRoleRel();
                    roleRel.setServicesId(servicesId.longValue());
                    roleRel.setRoleId(Integer.valueOf(n));
                    roleRel.setGmtModified(new Date());
                    servicesRoleRelMapper.insertSelective(roleRel);
                }
            });
            Set<Integer> platformIds =  selectPlatformIdsByRoleId(ids);

            //为用户插入平台权限
            platformIds.stream().forEach(id -> {
                servicesRoleRelMapper.insertUserPlatformRole(id,servicesId);
            });
        }catch (Exception e) {
            log.error("####添加失败：{}",e.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * 查询平台
     * @param ids
     * @return
     */
    private Set<Integer> selectPlatformIdsByRoleId(Set<Integer> ids) {
        return servicesRoleRelMapper.selectPlatformIdsByRoleId(ids);
    }

    @Override
    public Integer removeFunction(Integer servicesId, String roleIds) {
        try {
            List<String> newRoleIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(roleIds));
            newRoleIds.forEach(n ->
                servicesRoleRelMapper.deleteByServicesIdAndRoleId(servicesId,Integer.valueOf(n))
            );
            Set<Integer> queryRoleIds =  servicesRoleRelMapper.getRoleIdsByServicesId(servicesId);
            if (Objects.nonNull(queryRoleIds) && !queryRoleIds.isEmpty()){
                //获取套餐所有的用户
                Set<Integer> userIds = servicesRoleRelMapper.getPackageUserByServicesId(servicesId);

                //获取角色的平台权限
                Set<Integer> platformIds = selectPlatformIdsByRoleId(queryRoleIds);

                //删除用户的平台权限
                if (Objects.nonNull(userIds) && !userIds.isEmpty()){
                    userIds.stream().forEach(userid ->{
                        servicesRoleRelMapper.delUserJurisdictionByUserIdANDPlatformIds(userid,platformIds);
                    });
                }
                // userJurisdictionMapper.delPackageUserPlatformRole(servicesId,platformIds);
            }else{
                //删除用户的平台权限
                servicesRoleRelMapper.delPackageUserPlatformRole(servicesId,null);
            }

        }catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<ServicesBaseInfo> queryServiceBaseInfo(ServicesBaseInfoQuery servicesBaseInfoQuery) {
        return servicesBaseInfoMapper.selectServicesBaseInfo(servicesBaseInfoQuery);
    }
}
