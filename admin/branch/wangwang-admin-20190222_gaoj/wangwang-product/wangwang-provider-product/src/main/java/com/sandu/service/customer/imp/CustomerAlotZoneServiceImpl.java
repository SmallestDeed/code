package com.sandu.service.customer.imp;


import com.sandu.api.area.output.BaseAreaBo;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.customer.input.CustomerAlotZoneAdd;
import com.sandu.api.customer.input.query.CustomerAlotZoneQuery;
import com.sandu.api.customer.model.AllCode;
import com.sandu.api.customer.model.CustomerAlotLog;
import com.sandu.api.customer.model.CustomerAlotZone;
import com.sandu.api.customer.model.CustomerBaseInfo;
import com.sandu.api.customer.output.CustomerAlotZoneVO;
import com.sandu.api.customer.service.CustomerAlotLogService;
import com.sandu.api.customer.service.CustomerAlotZoneService;
import com.sandu.api.customer.service.CustomerBaseInfoService;
import com.sandu.api.customer.service.CustomerCommonService;
import com.sandu.api.user.service.UserService;
import com.sandu.service.customer.dao.CustomerAlotZoneMapper;
import com.sandu.service.customer.dao.CustomerBaseInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sandu-lipeiyuan
 */
@Slf4j
@Service("customerAlotZoneService")
public class CustomerAlotZoneServiceImpl implements CustomerAlotZoneService {

    @Resource
    private CustomerAlotZoneMapper customerAlotZoneMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerAlotLogService customerAlotLogService;
    @Autowired
    private CustomerBaseInfoService customerBaseInfoService;
    @Autowired
    private CustomerCommonService customerCommonService;
    @Resource
    private BaseAreaService baseAreaService;
    @Autowired
    private CustomerBaseInfoMapper customerBaseInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerAlotZoneMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(CustomerAlotZone record) {
        return customerAlotZoneMapper.insertSelective(record);
    }

    @Override
    public CustomerAlotZone selectByPrimaryKey(Long id) {
        return customerAlotZoneMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerAlotZone record) {
        return customerAlotZoneMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<CustomerAlotZone> selectByOption(CustomerAlotZoneQuery query) {
        return customerAlotZoneMapper.selectByOption(query);
    }

    @Override
    public int countByOption(CustomerAlotZoneQuery query) {
        Integer count = customerAlotZoneMapper.countByOption(query);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public void autoGeneraRule() {
        //查询有地址的经销商 distributor
        List<Company> distributors = companyService.queryDistributor();

        for (Company distributor : distributors) {
            log.info("############满足条件的经销商:{}##############", distributor);
            CustomerAlotZone customerAlotZone = new CustomerAlotZone();
            //设置区域长编码
            customerAlotZone.setLongCode(distributor.getProvinceCode() + "-" + distributor.getCityCode() + "-" + distributor.getAreaCode());
            //设置为自动生成类型
            customerAlotZone.setSourceType(0);

            //根据经销商id，和来源类型 查询该经销商是否已经自动生成过分配规则
            Company company = customerAlotZoneMapper.getDistributorByType(0, distributor.getId().intValue());
            if (company != null) {
                //如果有，则不再自动生成分配规则
                continue;
            } else {
                //如果没有，根据经销商id,区域code查询分配规则表
                List<Company> companies = customerAlotZoneMapper.queryDistributorByInfo(distributor);

                //如果有， 则代表已手动添加过该区域规则
                if (companies != null && companies.size() > 0) {
                    //获取该手动添加区域
                    customerAlotZone.setId(companies.get(0).getId());
                    //不再自动生成分配规则 直接更新该条数据的longCode，type
                    customerAlotZone.setModifier("1");
                    customerAlotZone.setGmtModified(LocalDateTime.now());
                    customerAlotZoneMapper.updateByPrimaryKeySelective(customerAlotZone);
                } else {
                    //按经销商查找有没有分配规则
                    List<CustomerAlotZone> customerAlotZones = customerAlotZoneMapper.queryDistributorByCompanyId(distributor.getId());
                    //如果有，则代表已手动添加过该经销商的其他分配规则
                    if (customerAlotZones.size() > 0 && customerAlotZones != null) {
                        //获取第一条， 更新该条数据的longCode,Type
                        customerAlotZone.setId(customerAlotZones.get(0).getId());
                        customerAlotZone.setModifier("1");
                        customerAlotZone.setGmtModified(LocalDateTime.now());
                        customerAlotZoneMapper.updateByPrimaryKeySelective(customerAlotZone);
                    } else {
                        //如果该经销商没有分配过任何区域，自动分配一条按经销商地址的分配规则
                        customerAlotZone.setId(null);
                        customerAlotZone.setCompanyId(distributor.getPid());
                        customerAlotZone.setChannelCompanyId(distributor.getId().intValue());
                        customerAlotZone.setProvinceCode(distributor.getProvinceCode());
                        customerAlotZone.setCityCode(distributor.getCityCode());
                        customerAlotZone.setAreaCode(distributor.getAreaCode());
                        customerAlotZone.setCreator("1");
                        customerAlotZone.setGmtCreate(LocalDateTime.now());
                        customerAlotZone.setIsDeleted(0);
                        customerAlotZoneMapper.insertSelective(customerAlotZone);
                    }
                }
            }
            log.info("############结束生成经销商id分配规则:{}##############", distributor.getId());
        }
    }

    @Override
    public void allotAreaRule() {
        //查询满足条件的用户 (未被分配过 并且手机号不为空)
        List<CustomerBaseInfo> customerBaseInfos = customerBaseInfoService.queryNotAllotCus();

        for (CustomerBaseInfo user : customerBaseInfos) {
            log.info("############用户id:{}##############", user.getUserId());
            //调用根据用户id获得地址 areaBo对象(省编码，市编码，区编码)
            BaseAreaBo areaBo = customerCommonService.queryAreaInfoByUserId(user.getUserId());
            log.info("############用户id{},用户地址:{}##############", user.getUserId(), areaBo);
            //存放满足条件的经销商分配规则id
            List<CustomerAlotZone> ids;

            //根据用户地址 从 区 -> 市 -> 省 跟经销商进行匹配
            if (areaBo.getAreaCode() != null) {
                ids = customerAlotZoneMapper.queryCustomerByCode(user.getCompanyId(), areaBo.getProvinceCode(),
                        areaBo.getCityCode(), areaBo.getAreaCode());
                if (ids.size() < 1 || ids == null) {
                    ids = customerAlotZoneMapper.queryCustomerByCode(user.getCompanyId(), areaBo.getProvinceCode(),
                            areaBo.getCityCode(), null);
                }
            } else if (areaBo.getCityCode() != null) {
                ids = customerAlotZoneMapper.queryCustomerByCode(user.getCompanyId(), areaBo.getProvinceCode(),
                        areaBo.getCityCode(), null);
                if (ids.size() < 1 || ids == null) {
                    ids = customerAlotZoneMapper.queryCustomerByCode(user.getCompanyId(), areaBo.getProvinceCode(),
                            areaBo.getCityCode(), "1");
                }
            } else {
                continue;
            }

            //更新客户信息表
            CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
            customerBaseInfo.setUserId(user.getUserId().intValue());
            customerBaseInfo.setProvinceCode(areaBo.getProvinceCode());
            customerBaseInfo.setCityCode(areaBo.getCityCode());
            customerBaseInfo.setAreaCode(areaBo.getAreaCode());
            customerBaseInfo.setStreetCode(areaBo.getStreetCode());

            //客户地址
            StringBuilder stringBuilder = new StringBuilder();
            if (areaBo.getProvinceName() != null) {
                stringBuilder.append(areaBo.getProvinceName());
            }
            if (areaBo.getCityName() != null) {
                stringBuilder.append(areaBo.getCityName());
            }
            if (areaBo.getAreaName() != null) {
                stringBuilder.append(areaBo.getAreaName());
            }
            if (areaBo.getStreetName() != null) {
                stringBuilder.append(areaBo.getStreetName());
            }
            customerBaseInfo.setAddress(stringBuilder.toString());

            customerBaseInfo.setModifier("1");
            customerBaseInfo.setGmtModified(LocalDateTime.now());
            customerBaseInfoService.updateByUserId(customerBaseInfo);

            if (ids.size() > 0 && ids != null) {
                Company company = companyService.getCompanyById(user.getCompanyId());
                if (company != null && company.getIsManage() == 0) {
                    //查询符合匹配的区域的匹配信息(按少到多排序) 取一条分配规则
                    Integer alotZoneId = ids.get(0).getId().intValue();
                    Integer distributorId = ids.get(0).getChannelCompanyId();
                    //插入匹配表
                    CustomerAlotLog customerAlotLog = new CustomerAlotLog();
                    customerAlotLog.setCustomerAlotZoneId(alotZoneId);
                    customerAlotLog.setUserId(user.getUserId().toString());
                    customerAlotLog.setCreator("1");
                    customerAlotLog.setGmtCreate(LocalDateTime.now());
                    customerAlotLog.setIsDeleted(0);
                    customerAlotLogService.insertSelective(customerAlotLog);
                    //更新分配记录表分配次数
                    customerAlotZoneMapper.updateAllotCountById(alotZoneId);
                    //更新客户信息表 alot_status字段
                    customerBaseInfo = new CustomerBaseInfo();
                    customerBaseInfo.setUserId(user.getUserId().intValue());
                    customerBaseInfo.setAlotStatus(1);
                    customerBaseInfo.setAlotType(0);
                    customerBaseInfo.setAlotUserId(1);
                    customerBaseInfo.setChannelCompanyId(distributorId);
                    customerBaseInfo.setAlotTime(LocalDateTime.now());
                    customerBaseInfo.setModifier("1");
                    customerBaseInfo.setGmtModified(LocalDateTime.now());
                    customerBaseInfoService.updateByUserId(customerBaseInfo);
                }
            }
        }
    }

    @Override
    public void allotCustomer() {
        customerBaseInfoMapper.allotCustomer();
    }

    @Override
    public int addGeneraRule(CustomerAlotZoneAdd add) {
        log.info("############开始设置经销商id:{} 分配规则##############", add.getChannelCompanyId());
        int result = 0;
        //查询经销商原有的分配规则
        List<CustomerAlotZone> oldCustomerAlotZones =
                customerAlotZoneMapper.queryDistributorByCompanyId(add.getChannelCompanyId().longValue());

        // 原有分配规则id集
        List<Integer> oldZoneIds = new ArrayList<>();
        for (CustomerAlotZone alotZone : oldCustomerAlotZones) {
            oldZoneIds.add(alotZone.getId().intValue());
        }
        log.info("############经销商原有分配规则id{}##############", oldZoneIds);

        //新分配规则的id集
        List<Integer> newZoneIds = new ArrayList<>();
        for (AllCode code : add.getAllCode()) {
            newZoneIds.add(code.getId());
            if (code.getId() == -1) {
                log.info("############开始为经销商添加规则：{}##############", code);
                //新增
                CustomerAlotZone customerAlotZone = new CustomerAlotZone();
                customerAlotZone.setCompanyId(add.getCompanyId());
                customerAlotZone.setChannelCompanyId(add.getChannelCompanyId());
                customerAlotZone.setProvinceCode(code.getProvinceCode());
                customerAlotZone.setCityCode(code.getCityCode());
                customerAlotZone.setAreaCode(code.getAreaCode());
                customerAlotZone.setSourceType(1);
                customerAlotZone.setCreator(add.getUserName());
                customerAlotZone.setGmtCreate(LocalDateTime.now());
                customerAlotZone.setIsDeleted(0);
                result = customerAlotZoneMapper.insertSelective(customerAlotZone);
            } else {
                log.info("############开始修改原经销商规则：{}##############", code);
                //修改
                CustomerAlotZone customerAlotZone = new CustomerAlotZone();
                customerAlotZone.setId(code.getId().longValue());
                customerAlotZone.setProvinceCode(code.getProvinceCode());
                customerAlotZone.setCityCode(code.getCityCode());
                customerAlotZone.setAreaCode(code.getAreaCode());
                customerAlotZone.setModifier(add.getUserName());
                customerAlotZone.setGmtModified(LocalDateTime.now());
                result = customerAlotZoneMapper.updateByPrimaryKeySelective(customerAlotZone);
            }
        }
        log.info("############经销商新分配规则id{}##############", newZoneIds);
        //遍历原分配规则表中的id
        for (Integer id : oldZoneIds) {
            //如果新分配规则表id不包含原分配表id 则删除该原规则id
            if (!newZoneIds.contains(id)) {
                log.info("############删除已取消的分配规则id{}##############", id);
                customerAlotZoneMapper.deleteByPrimaryKey(id.longValue());
            }
        }
        log.info("############结束经销商新分配规则id：{}##############", add.getChannelCompanyId());
        return result;
    }

    @Override
    public List<CustomerAlotZone> queryAlotZoneByCompany(Long companyId) {
        return customerAlotZoneMapper.queryDistributorByCompanyId(companyId);
    }

    @Override
    public int deleteByCompanyId(Integer companyId) {
        return customerAlotZoneMapper.deleteByCompanyId(companyId);
    }

    @Override
    public List<CustomerAlotZoneVO> queryAlotZoneList(CustomerAlotZoneQuery query) {
        log.info("############查询条件：{}##############", query);
        //根据经销商id分组查询分配规则表
        List<Integer> distributorIds = customerAlotZoneMapper.queryAlotZoneGourpByDistributorId(query);
        //根据经销商id查询对应的分配信息
        List<CustomerAlotZone> customerAlotZones = new ArrayList<>();
        if (distributorIds != null && distributorIds.size() > 0) {
            customerAlotZones = customerAlotZoneMapper.queryAlotZoneByDistributorIds(distributorIds);
        }

        //设置返回的经销商分配列表
        List<CustomerAlotZoneVO> customerAlotZoneVOS = new ArrayList<>();
        //区域code，名称集
        Map<String, String> baseAreaMap = baseAreaService.codeAndNameMap();
        //遍历经销商规则列表 (可能有重复经销商)
        for (CustomerAlotZone customerAlotZone : customerAlotZones) {
//            if(query.getAreaCode()!=null) {
//                if(!customerAlotZone.getAreaCode().equals(query.getAreaCode())) {
//                    continue;
//                }
//            }else if(query.getCityCode()!=null){
//                if(!customerAlotZone.getCityCode().equals(query.getCityCode())) {
//                    continue;
//                }
//            }else if(query.getProvinceCode()!= null){
//                if(!customerAlotZone.getProvinceCode().equals(query.getProvinceCode())) {
//                    continue;
//                }
//            }
            //重复经销商的分配规则 true ,不重复的false
            boolean flag = false;

            //遍历需要返回的经销商分配列表
            for (CustomerAlotZoneVO vos : customerAlotZoneVOS) {
                //如果遍历的经销商和返回的经销商有重复 则将分配规则直接存到该返回的经销商列表
                if (vos.getChannelCompanyId().equals(customerAlotZone.getChannelCompanyId())) {
                    flag = true;
                    List<AllCode> codes = vos.getAllCode();
                    AllCode code = new AllCode();
                    //设置省 市 区编码及名称
                    code.setProvinceCode(customerAlotZone.getProvinceCode());
                    code.setProvinceName(baseAreaMap.get(code.getProvinceCode()));

                    code.setCityCode(customerAlotZone.getCityCode());
                    code.setCityName(baseAreaMap.get(code.getCityCode()));

                    code.setAreaCode(customerAlotZone.getAreaCode());
                    code.setAreaCodeName(baseAreaMap.get(code.getAreaCode()));
                    codes.add(code);
                    vos.setAllCode(codes);
                    break;
                }
            }
            if (!flag) {
                log.info("############经销商id:{}##############", customerAlotZone.getChannelCompanyId());
                CustomerAlotZoneVO vo = new CustomerAlotZoneVO();
                vo.setChannelCompanyId(customerAlotZone.getChannelCompanyId());
                //根据经销商id查询名称
                Company company = companyService.getCompanyNameById(customerAlotZone.getChannelCompanyId());
                vo.setChannelCompanyName(company.getCompanyName());
                //新的区域编码集
                List<AllCode> codes = new ArrayList<>();
                AllCode code = new AllCode();
                //设置省 市 区编码及名称
                code.setProvinceCode(customerAlotZone.getProvinceCode());
                code.setProvinceName(baseAreaMap.get(code.getProvinceCode()));

                code.setCityCode(customerAlotZone.getCityCode());
                code.setCityName(baseAreaMap.get(code.getCityCode()));

                code.setAreaCode(customerAlotZone.getAreaCode());
                code.setAreaCodeName(baseAreaMap.get(code.getAreaCode()));
                codes.add(code);
                vo.setAllCode(codes);
                customerAlotZoneVOS.add(vo);
            }
        }
        return customerAlotZoneVOS;
    }

    @Override
    public List<Company> queryCompanyList(Integer companyId) {
        return companyService.queryDistributorByCompanyId(companyId);
    }

    @Override
    public Map<String, String> queryAreaMap() {
        return baseAreaService.codeAndNameMap();
    }

    @Override
    public List<CustomerAlotZone> queryAlotZoneByDistributorId(Integer distributorId) {
        return customerAlotZoneMapper.queryAlotZoneByDistributorId(distributorId);
    }
}