package com.sandu.service.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.product.bo.ProductTypeBO;
import com.sandu.api.house.bo.SystemDictionaryBO;
import com.sandu.api.house.input.SystemDictionarySearch;
import com.sandu.api.house.model.SystemDictionary;
import com.sandu.service.product.impl.BaseProductServiceImpl.ProType;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Repository
public interface SystemDictionaryMapper {

	List<SystemDictionary> listSysDictionaryByType(SystemDictionarySearch search);

	List<SystemDictionary> listHardBaimoType(SystemDictionarySearch search);

	List<SystemDictionary> listHardBaimoSamllType(SystemDictionarySearch search);

	List<SystemDictionaryBO> listSoftBiamoType(SystemDictionarySearch search);

	ProductTypeBO getProductType(@Param("productType") Integer productType, @Param("productSmallType") Integer productSmallType);

	List<ProductTypeBO> getProductTypes(@Param("proTypes") List<ProType> proTypes);

	SystemDictionary findOneByValueKey(@Param("valuekey") String valuekey);

	SystemDictionary findOneByTypeAndArea(@Param("type") String type, @Param("area") Double area);
}
