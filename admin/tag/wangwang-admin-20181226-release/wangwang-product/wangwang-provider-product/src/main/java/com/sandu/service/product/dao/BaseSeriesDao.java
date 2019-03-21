package com.sandu.service.product.dao;

import com.sandu.api.product.model.BaseSeries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface BaseSeriesDao {

    int deleteByPrimaryKey(Long id);

    int insert(BaseSeries record);

    int insertSelective(BaseSeries record);

    BaseSeries selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseSeries record);

    int updateByPrimaryKey(BaseSeries record);

    List<BaseSeries> listAllIdAndName();
}