package com.sandu.service.brand.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.model.bo.BrandQuery;
import com.sandu.api.brand.service.BrandService;
import com.sandu.service.brand.dao.BrandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toMap;

/**
 * @author Sandu
 * @date 2017/12/15
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    /**
     * 新增数据
     *
     * @param brand
     * @return int
     */
    @Override
    public int saveBrand(Brand brand) {
        return brandDao.saveBrand(brand);
    }

    /**
     * 更新数据
     *
     * @param brand
     * @return int
     */
    @Override
    public int updateBrand(Brand brand) {
        return brandDao.updateBrand(brand);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int deleteBrandById(long id) {
        return brandDao.deleteBrandById(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return Brand
     */
    @Override
    public Brand getBrandById(long id) {
        return brandDao.getBrandById(id);
    }

    @Override
    public Brand getBrandByBrandCode(String brandCode) {
        return brandDao.getBrandByBrandCode(brandCode);
    }

    @Override
    public List<Brand> findAllBrandNameAndId() {
        return brandDao.findAllBrandNameAndId();
    }

    /**
     * 无条件查询
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Brand> findAllBrand(int page, int limit) {
        PageHelper.startPage(page, limit);
        return brandDao.findAllBrand();
    }

    @Override
    public List<Brand> queryBrandByBrandInfo(String brandCode, String brandName, String brandRefered, Integer companyId,
                                             String orders, String order, String orderWay, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Brand> brands = brandDao.queryBrandByBrandInfo(brandCode, brandName, brandRefered, companyId, orders, order, orderWay);
        return brands;
    }

    @Override
    public String queryMaxBrandCodeByCompanyId(int companyId) {
        return brandDao.queryMaxBrandCodeByCompanyId(companyId);
    }

    @Override
    public List<Brand> queryBrandByBrandStyleId(int brandStyleId) {
        return brandDao.queryBrandByBrandStyleId(brandStyleId);
    }

    @Override
    public int deleteBrandCompanyId(long companyId) {
        return brandDao.deleteBrandCompanyId(companyId);
    }

    @Override
    public Map<Integer, String> getBrandNameByIds(List<Integer> brandIds) {
        if (brandIds.isEmpty()) {
            return Collections.emptyMap();
        }
        brandIds = brandIds.stream().distinct().collect(Collectors.toList());
        List<Brand> brands = brandDao.getBrandNameByIds(brandIds);
        Map<Integer, String> map = new HashMap<>(brandIds.size());
        for (Brand brand : brands) {
            map.put(brand.getId().intValue(), brand.getBrandName());
        }
        return map;
    }

    @Override
    public List<Brand> getBrandByCompanyId(Integer companyId) {
        return brandDao.getBrandByCompanyId(companyId);
    }

    @Override
    public List<Integer> listPeerBrandIds(Integer companyId) {
        return brandDao.listPeerBrandIds(companyId);
    }

    @Override
    public PageInfo<Brand> pageQueryBrand(BrandQuery brandQuery,Integer pageNo,Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        return new PageInfo<>(brandDao.queryByOption(brandQuery));
    }

    @Override
    public List<Brand> queryBrand(BrandQuery brandQuery) {
        return brandDao.queryByOption(brandQuery);
    }

    @Override
    public String createBrandCode(Brand brand) {
        return brandDao.createBrandCode(brand);
    }

    @Override
    public String getBrandNamesByIds(List<String> brandIds) {
        return brandDao.getBrandNamesByIds(brandIds);
    }

    @Override
    public Map<Long,String> idAndNameMap(List<String> brandIds) {
        List<Integer> newId = new ArrayList<>();
        for(String str : brandIds) {
            List<String> brandIdStrs = Splitter.on(COMMA)
                    .omitEmptyStrings()
                    .trimResults()
                    .splitToList(Strings.nullToEmpty(str));
            for(String strs : brandIdStrs) {
                newId.add(Integer.valueOf(strs));
            }
        }
        newId = newId.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (isEmpty(newId)) {
            return Collections.emptyMap();
        }
        List<Brand> brandList = brandDao.getBrandNameByIds(newId);
        return brandList.stream().collect(toMap(Brand::getId, Brand::getBrandName));
    }
}
