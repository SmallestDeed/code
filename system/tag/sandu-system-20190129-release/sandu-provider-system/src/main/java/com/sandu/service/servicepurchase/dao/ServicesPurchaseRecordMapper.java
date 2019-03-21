package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.input.query.ServicesPurchaseRecordQuery;
import com.sandu.api.servicepurchase.model.ServicesPurchaseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicesPurchaseRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(ServicesPurchaseRecord record);

    ServicesPurchaseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServicesPurchaseRecord record);

    List<ServicesPurchaseRecord> queryByOption(ServicesPurchaseRecordQuery servicesPurchaseRecordQuery);

    ServicesPurchaseRecord getRecordByOrderNum(@Param("orderNum") String orderNum);

    ServicesPurchaseRecord checkRecordWithUserId(@Param("userId") Integer userId);

	int selectTelephoneExists(@Param("telephone")String telephone);
}
