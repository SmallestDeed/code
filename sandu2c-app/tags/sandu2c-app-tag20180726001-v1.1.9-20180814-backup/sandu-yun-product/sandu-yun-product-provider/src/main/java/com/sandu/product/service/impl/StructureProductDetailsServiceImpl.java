package com.sandu.product.service.impl;

import com.sandu.product.dao.StructureProductDetailsMapper;
import com.sandu.product.model.StructureProductDetails;
import com.sandu.product.model.StructureProductDetailsSearch;
import com.sandu.product.service.StructureProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc 产品模块-结构明细服务
 * @date 20171014
 * @auth pengxuangang
 */
@Service("structureProductDetailsService")
public class StructureProductDetailsServiceImpl implements StructureProductDetailsService {

    @Autowired
    private StructureProductDetailsMapper structureProductDetailsMapper;

    @Override
    public List<StructureProductDetails> findAllByStructureId(Integer structureId) {
        StructureProductDetailsSearch structureProductDetailsSearch = new StructureProductDetailsSearch();
        structureProductDetailsSearch.setStart(new Integer(-1));
        structureProductDetailsSearch.setLimit(new Integer(-1));
        structureProductDetailsSearch.setStructureId(structureId);
        return this.getPaginatedList(structureProductDetailsSearch);
    }

    /**
     * 分页获取数据
     *
     * @return List<StructureProductDetails>
     */
    @Override
    public List<StructureProductDetails> getPaginatedList(
            StructureProductDetailsSearch structureProductDetailsSearch) {
        return structureProductDetailsMapper.selectPaginatedList(structureProductDetailsSearch);
    }
}
