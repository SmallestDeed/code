package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.input.query.ServicesBaseInfoQuery;
import com.sandu.api.servicepurchase.model.ServicesBaseInfo;
import com.sandu.api.servicepurchase.model.ServicesRole;
import com.sandu.api.servicepurchase.model.bo.ServicesListBO;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseLogListBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ServicesBaseInfoMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ServicesBaseInfo record);

    int insertSelective(ServicesBaseInfo record);

    ServicesBaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServicesBaseInfo record);

    int updateByPrimaryKey(ServicesBaseInfo record);

    List<ServicesListBO> getServicesListByUserScopeAndSaleChannel(@Param("userTypes") List<Integer> userTypes, @Param("saleChannel") String saleChannel);

    List<ServicesPurchaseLogListBO> getPurchasedServicesLogsByCompanyId(@Param("companyId") Integer companyId);

	List<ServicesBaseInfo> getServicesByType(@Param("servicesType")String servicesType,@Param("saleChannel")String saleChannel);

    List<ServicesBaseInfo> queryByOption(ServicesBaseInfoQuery servicesBaseInfoQuery);

    int countQueryByOption(ServicesBaseInfoQuery servicesBaseInfoQuery);

    List<ServicesBaseInfo> getByIds(@Param("collect") Set<Long> collect);

    List<ServicesBaseInfo> selectServicesListByUserScopeAndTye(@Param("servicesType")String servicesType, @Param("saleChannel")String saleChannel, @Param("userScope")String userScope);

    /**
     * ��ѯ�ײ��б�
     * @param query
     * @return
     */
    List<ServicesBaseInfo> queryServiceBaseInfo(ServicesBaseInfoQuery query);

    /**
     * ���û��ֹ�ײ�
     * @param id
     * @param servicesStatus
     * @return
     */
    int openOrCloseServices(@Param("id") Integer id, @Param("servicesStatus") Integer servicesStatus);

    /**
     * �����ײ�id��ȡ����������Ϣ
     * @param servicesId
     * @return
     */
    List<ServicesRole> getFunctionList(@Param("servicesId") Integer servicesId);

    /**
     * 获取关联功能列表
     * @return
     */
    List<ServicesRole> getInitFunctionList();

    List<ServicesBaseInfo> selectServicesBaseInfo(ServicesBaseInfoQuery servicesBaseInfoQuery);

}