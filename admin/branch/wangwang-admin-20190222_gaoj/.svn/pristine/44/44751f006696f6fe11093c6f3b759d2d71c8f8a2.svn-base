package com.sandu.service.product.impl;

import com.sandu.api.product.model.BaseSeries;
import com.sandu.api.product.service.BaseSeriesService;
import com.sandu.service.product.dao.BaseSeriesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sandu
 */
@Service("baseSeriesService")
public class BaseSeriesServiceImpl implements BaseSeriesService {
    @Autowired
    private BaseSeriesDao baseSeriesDao;
    @Override
    public int delete(long id) {
        return baseSeriesDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(BaseSeries baseSeries) {
        baseSeriesDao.updateByPrimaryKeySelective(baseSeries);
    }

    @Override
    public int save(BaseSeries baseSeries) {
        return baseSeriesDao.insert(baseSeries);
    }

    @Override
    public BaseSeries getById(long id) {
        return baseSeriesDao.selectByPrimaryKey(id);
    }

    @Override
    public List<BaseSeries> listAll() {
        return baseSeriesDao.listAllIdAndName();
    }
}
