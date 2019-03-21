package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.input.query.ServicesAccountRelQuery;
import com.sandu.api.servicepurchase.model.ServicesAccountRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicesAccountRelMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(ServicesAccountRel record);

    ServicesAccountRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServicesAccountRel record);

	List<ServicesAccountRel> selectAccountListByAccount(@Param("account")String account);

	void batchInsertServiceAccount(@Param("accountList")List<ServicesAccountRel> accountList);

	List<ServicesAccountRel> queryByOption(ServicesAccountRelQuery servicesAccountRelQuery);


    ServicesAccountRel getAccountByUserId(@Param("userId") Integer userId);

    List<ServicesAccountRel> listRemainingAccounts(@Param("topDays") Integer topDays);
}