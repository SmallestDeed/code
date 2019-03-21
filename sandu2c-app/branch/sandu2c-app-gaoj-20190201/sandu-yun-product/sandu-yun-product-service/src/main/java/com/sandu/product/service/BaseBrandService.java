package com.sandu.product.service;


import com.sandu.product.model.BaseBrand;

import java.util.List;
import java.util.Map;


/**
 * @version V1.0
 * @Title: BaseBrandService.java
 * @Package com.sandu.product.service
 * @Description:产品-品牌表Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-16 10:03:47
 */
public interface BaseBrandService {
    /**
     * 新增数据
     *
     * @param baseBrand
     * @return int
     */
    int add(BaseBrand baseBrand);

    /**
     * 更新数据
     *
     * @param baseBrand
     * @return int
     */
    int update(BaseBrand baseBrand);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseBrand
     */
    BaseBrand get(Integer id);

    /**
     * 查询公司所拥有的品牌
     * @param companyId 公司ID
     * @return      公司品牌LIST
     */
    List<Integer> queryBrandListByCompanyId(Integer companyId);

	List<BaseBrand> getAllBrandByCompanyId(Integer id);

    /**
     * 通过公司id获取和它品牌联盟的所有品牌集合
     * @param companyId
     * @return
     */
	List<Integer> getAllBrandIdsByCompanyId(Integer companyId);

    /**
     * 获取所有品牌对应信息
     * @return
     */
    Map<Integer,String> brandIdAndNameMap(List<Integer> brandIds);
}
