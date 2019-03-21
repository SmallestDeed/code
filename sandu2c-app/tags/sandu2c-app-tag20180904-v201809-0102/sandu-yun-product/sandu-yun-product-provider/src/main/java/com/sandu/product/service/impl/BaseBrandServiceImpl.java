package com.sandu.product.service.impl;

import com.sandu.product.dao.BaseBrandMapper;
import com.sandu.product.model.BaseBrand;
import com.sandu.product.service.BaseBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: BaseBrandServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品-品牌表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-16 10:03:47
 */
@Service("baseBrandService")
public class BaseBrandServiceImpl implements BaseBrandService {

    @Autowired
    private BaseBrandMapper baseBrandMapper;

    /**
     * 新增数据
     *
     * @param baseBrand
     * @return int
     */
    @Override
    public int add(BaseBrand baseBrand) {
        baseBrandMapper.insertSelective(baseBrand);
        return baseBrand.getId();
    }

    /**
     * 更新数据
     *
     * @param baseBrand
     * @return int
     */
    @Override
    public int update(BaseBrand baseBrand) {
        return baseBrandMapper
                .updateByPrimaryKeySelective(baseBrand);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return baseBrandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseBrand
     */
    @Override
    public BaseBrand get(Integer id) {
        return baseBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Integer> queryBrandListByCompanyId(Integer companyId) {
        return baseBrandMapper.queryBrandListByCompanyId(companyId);
    }

	@Override
	public List<BaseBrand> getAllBrandByCompanyId(Integer id) {

		return baseBrandMapper.selectBrandListByCompanyId(id);
	}

    /**
     * 通过公司id获取和它品牌联盟的所有品牌集合
     * @param companyId
     * @return
     */
    @Override
    public List<Integer> getAllBrandIdsByCompanyId(Integer companyId) {
        if (companyId == null) {
            return null;
        }
        return baseBrandMapper.getAllBrandIdsByCompanyId(companyId);
    }

}
