package com.nork.product.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.nork.common.constant.SysDictionaryConstant;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.service.ProCategoryService;
import com.nork.system.cache.SysDictionaryCacher;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.user.model.constant.UserTypeConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.dao.CompanyShopMapper;
import com.nork.product.model.CompanyShop;
import com.nork.product.service.CompanyShopService;

/**
 * @Title: CompanyShopServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:企业商铺-企业店铺ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2018-05-22 20:08:57
 * @version V1.0
 */
@Service("companyShopService")
public class CompanyShopServiceImpl implements CompanyShopService {

    private static Logger logger = LoggerFactory.getLogger(CompanyShopServiceImpl.class);

    private CompanyShopMapper companyShopMapper;
    @Autowired
    private ProCategoryService proCategoryService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    public void setCompanyShopMapper(
            CompanyShopMapper companyShopMapper) {
        this.companyShopMapper = companyShopMapper;
    }

    /**
     * 新增数据
     *
     * @param companyShop
     * @return  int
     */
    @Override
    public int add(CompanyShop companyShop) {
        companyShopMapper.insertSelective(companyShop);
        return companyShop.getId();
    }

    /**
     *    更新数据
     *
     * @param companyShop
     * @return  int
     */
    @Override
    public int update(CompanyShop companyShop) {
        return companyShopMapper
                .updateByPrimaryKeySelective(companyShop);
    }

    /**
     *    删除数据
     *
     * @param id
     * @return  int
     */
    @Override
    public int delete(Integer id) {
        return companyShopMapper.deleteByPrimaryKey(id);
    }

    /**
     *    获取数据详情
     *
     * @param id
     * @return  CompanyShop
     */
    @Override
    public CompanyShop get(Integer id) {
        return companyShopMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param  companyShop
     * @return   List<CompanyShop>
     */
    @Override
    public List<CompanyShop> getList(CompanyShop companyShop) {
        return companyShopMapper.selectList(companyShop);
    }


    @Override
    public int updateCategoryByCompanyId(CompanyShop companyShop) {
        return companyShopMapper.updateCategoryByCompanyId(companyShop);
    }

    @Override
    public List<Integer> findIdListByCompanyId(Integer companyId) {
        return companyShopMapper.findIdListByCompanyId(companyId);
    }

    /**
     * 通过用户id查询用户所属店铺列表
     * @param userId 用户Id
     * @return list
     */
    @Override
    public List<CompanyShop> getShopListByUserId(Long userId) {
        return companyShopMapper.getShopListByUserId(userId);
    }

    /**
     * 通过用户Id删除属于用户的店铺
     * @param userId 用户Id
     * @return
     */
    @Override
    public Integer deleteShopByUserId(Long userId, String loginUserName) {
        return companyShopMapper.deleteShopByUserId(userId,loginUserName);
    }

    /**
     * 通过企业id查询用户所属店铺列表
     * @param companyId 企业Id
     * @return
     */
    @Override
    public List<CompanyShop> getShopListByCompanyId(Long companyId) {
        return companyShopMapper.getShopListByCompanyId(companyId);
    }

    /**
     * 通过用户Id删除属于用户的店铺
     * @param companyId 企业Id
     * @param loginUserName 操作用户名称
     * @return
     */
    @Override
    public int deleteShopByCompanyId(Long companyId,String loginUserName) {
        return companyShopMapper.deleteShopByCompanyId(companyId,loginUserName);
    }

    @Override
    public CompanyShop getCompanyShopByUserInfo(SysUser user) {
        if(user == null){
            return null;
        }
        //填充查询条件
        CompanyShop shopSearch = new CompanyShop();
        shopSearch.setIsDeleted(0);
        shopSearch.setDisplayStatus(1);
        shopSearch.setSch_releasePlatformValues("2");//必须是发布到随选网的店铺
        shopSearch.setOrder("gmt_create");
        shopSearch.setOrderNum("asc");//取最早创建的数据
        if(user.getUserType() != null && Objects.equals(user.getUserType(), UserTypeConstant.USER_TYPE_DESIGNER)){
            //用户为设计师时查询ta个人的店铺
            shopSearch.setUserId(user.getId());
        }else{
            if(user.getCompanyId() != null){
                shopSearch.setCompanyId(user.getCompanyId());
            }else{
                shopSearch.setUserId(user.getId());
            }
        }
        //查询数据
        List<CompanyShop> list = this.getList(shopSearch);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public CompanyShop getCompanyShopByCompanyId(Integer companyId) {
        if (null == companyId || 0 == companyId) {
            return null;
        }

        CompanyShop companyShop = companyShopMapper.getCompanyShopByCompanyId(companyId);
        return companyShop;
    }

    @Override
    public CompanyShop getUserCompanyShopForNewScene(Integer userId) {
        if(userId == null || userId <1){
            logger.error("查找店铺信息时参数为空,userId:" + userId);
            return null;
        }
        SysUser sysUser = sysUserService.get(userId);
        if(sysUser == null){
            logger.error("查找店铺信息时未找到该用户,参数userId:{}", userId);
            return null;
        }
        if(sysUser.getBusinessAdministrationId() == null){
            logger.error("该用户没有BusinessAdministrationId信息,参数userId:{}" , userId );
            return null;
        }
        //查找店铺信息
        CompanyShop shopSearch = new CompanyShop();
        if(Objects.equals(sysUser.getUserType(),UserTypeConstant.USER_TYPE_COMPANY)){
            //厂商则查找品牌馆的信息
            SysDictionary shopTypeDictionary = sysDictionaryService.findOneByTypeAndValueKeyInCache(SysDictionaryConstant.SYS_DICTINARY_SHOP_TYPE , SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION);
            if(shopTypeDictionary == null){
                logger.error("数据字典中缺失type为'{}',valuekey为'{}'的数据!",SysDictionaryConstant.SYS_DICTINARY_SHOP_TYPE ,SysDictionaryConstant.SHOP_TYPE_BRAND_PAVILION);
            }else{
                shopSearch.setBusinessType(shopTypeDictionary.getValue());
            }
            shopSearch.setCompanyId(sysUser.getBusinessAdministrationId());
            shopSearch.setOrder("id");
            shopSearch.setOrderNum("desc");
        }else{
            shopSearch.setUserId(userId);
            shopSearch.setOrder("gmt_create");
            shopSearch.setOrderNum("asc");//取最早创建的数据
        }

        shopSearch.setIsDeleted(0);
        //必须是发布到随选网的店铺
        shopSearch.setSch_releasePlatformValues("2");
        List<CompanyShop> shopList = this.getList(shopSearch);
        if(CustomerListUtils.isEmpty(shopList)){
            return null;
        }
        CompanyShop companyShop = shopList.get(0);
        return companyShop;
    }
}
