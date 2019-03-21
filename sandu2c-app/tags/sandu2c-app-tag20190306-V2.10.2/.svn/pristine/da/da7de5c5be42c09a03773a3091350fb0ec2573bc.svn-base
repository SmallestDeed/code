package com.sandu.shop.dao;

import com.sandu.shop.vo.CompanyShopVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/11/17  11:31
 */
@Repository
public interface CompanyShopMapper {
    /**
     * 通过企业Id获取店铺列表
     * @param companyId 企业Id
     * @return List店铺列表
     */
    List<CompanyShopVO> selectByCompanyId(@Param("companyId") Long companyId);
}
