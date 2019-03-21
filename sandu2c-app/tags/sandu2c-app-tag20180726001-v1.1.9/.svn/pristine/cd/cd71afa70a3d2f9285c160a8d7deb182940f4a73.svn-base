package com.sandu.product.dao;

import com.sandu.product.vo.UnionVO;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:36 2017/12/8 0008
 * @Modified By:
 */
public interface CompanyUnionMapper {
    /**查询企业所在的所有联盟信息*/
    List<UnionVO> selectByCompanyId(Integer companyId);

    /**查询联盟下所有的企业id*/
    List<Integer> selectByUnionId(Integer unionId);

    /**查询所有联盟的企业id*/
    List<Integer> selectAllCompanyId(Integer companyId);

    /**查询所有联盟的企业下的所有品牌id*/
    List<Integer> selectAllBrandId(Integer companyId);
}
