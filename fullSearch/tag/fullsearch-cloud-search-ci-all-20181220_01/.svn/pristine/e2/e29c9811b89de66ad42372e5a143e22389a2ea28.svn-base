package com.sandu.search.service.groupproduct;

import com.sandu.search.entity.elasticsearch.index.GroupProductIndexMappingData;
import com.sandu.search.entity.groupproduct.GroupProductSearchVo;
import com.sandu.search.exception.GroupProductSearchException;

public interface GroupProductSearchService {


    GroupProductIndexMappingData searchGroupProductById(Integer groupId) throws GroupProductSearchException;

    GroupProductIndexMappingData searchGroupProduct(GroupProductSearchVo groupProductSearchVo) throws GroupProductSearchException;
}
