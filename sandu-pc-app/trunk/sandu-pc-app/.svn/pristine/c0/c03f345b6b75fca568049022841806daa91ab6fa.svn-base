package com.sandu.product.service.impl;

import com.sandu.product.dao.CompanyShopMapper;
import com.sandu.product.model.CompanyShop;
import com.sandu.product.model.input.CompanyShopSearch;
import com.sandu.product.model.output.CompanyShopVo;
import com.sandu.product.service.CompanyShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: CompanyShopServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:企业商铺-企业店铺ServiceImpl
 * @version V1.0
 */
@Service("companyShopService")
public class CompanyShopServiceImpl implements CompanyShopService {

    private static Logger logger = LoggerFactory.getLogger(CompanyShopServiceImpl.class);

    private CompanyShopMapper companyShopMapper;

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
    public CompanyShop getCompanyShopByCompanyId(Integer companyId) {
        if (null == companyId || 0 == companyId) {
            return null;
        }

        CompanyShop companyShop = companyShopMapper.getCompanyShopByCompanyId(companyId);

        return companyShop;
    }

    @Override
    public int getCountBySearch(CompanyShopSearch search) {
        return companyShopMapper.getCountBySearch(search);
    }

    @Override
    public List<CompanyShopVo> getCompanyShopListBySearch(CompanyShopSearch search) {
        return companyShopMapper.selectListBySearch(search);
    }
}
