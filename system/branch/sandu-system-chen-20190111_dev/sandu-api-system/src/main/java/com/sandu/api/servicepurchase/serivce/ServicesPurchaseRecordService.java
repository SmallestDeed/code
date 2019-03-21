package com.sandu.api.servicepurchase.serivce;

import com.sandu.api.servicepurchase.input.query.ServicesPurchaseRecordQuery;
import com.sandu.api.servicepurchase.model.ServicesPurchaseRecord;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ServicesPurchaseRecordService {
	
	public int addServicesPurchaseRecord(ServicesPurchaseRecord record);

	public ServicesPurchaseRecord selectByPrimaryKey(Long purchaseRecordId);
	
	public int updateByPrimaryKeySelective(ServicesPurchaseRecord record);
	List<ServicesPurchaseRecord> queryByOption(ServicesPurchaseRecordQuery servicesPurchaseRecordQuery);
	
    ServicesPurchaseRecord getRecordByOrderNum(String orderNum);

	ServicesPurchaseRecord checkRecordWithUserId(Integer userId);

	public int checkTelephoneExists(String telephone);

}