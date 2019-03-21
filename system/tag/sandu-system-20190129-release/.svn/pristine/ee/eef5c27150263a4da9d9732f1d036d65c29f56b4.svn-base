package com.sandu.api.servicepurchase.serivce;

import com.sandu.api.servicepurchase.input.query.ServicesPriceQuery;
import com.sandu.api.servicepurchase.model.ServicesPrice;
import com.sandu.api.servicepurchase.output.ServicesPriceVO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ServicesPriceService{
	
	List<ServicesPriceVO> findServicesPriceById(Long servicesId);
	
	ServicesPrice findPriceById(Long id);
	
	public List<ServicesPrice> findServicesPriceList(Long servicesId);

	List<ServicesPrice> queryByOption(ServicesPriceQuery servicesPriceQuery);

	List<ServicesPrice> queryByServicesIdAndCompanyId(Long servicesId,Integer companyId,Integer isDelete);

}
