package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.input.query.ServicesPriceQuery;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicesPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServicesPrice record);

    int insertSelective(ServicesPrice record);

    ServicesPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServicesPrice record);

    int updateByPrimaryKey(ServicesPrice record);

	List<ServicesPriceVO> selectByServicesId(Long servicesId);

	List<ServicesPrice> selectServicesPriceList(Long servicesId);

    List<ServicesPrice> queryByOption(ServicesPriceQuery servicesPriceQuery);
}