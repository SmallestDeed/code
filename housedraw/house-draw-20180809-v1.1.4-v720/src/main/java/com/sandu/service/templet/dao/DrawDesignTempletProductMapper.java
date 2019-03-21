package com.sandu.service.templet.dao;

import java.util.List;

import com.sandu.api.house.bo.JumpPositionDesignTempletProductBO;
import org.apache.ibatis.annotations.Param;

import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.house.search.DrawDesignTempletProductSearch;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawDesignTempletProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrawDesignTempletProduct record);

    int insertSelective(DrawDesignTempletProduct record);

    DrawDesignTempletProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrawDesignTempletProduct record);

    int updateByPrimaryKey(DrawDesignTempletProduct record);

	List<DrawDesignTempletProduct> findAllBySearch(
			DrawDesignTempletProductSearch drawDesignTempletProductSearch);

	void transformToDesignTempletProduct(
			DrawDesignTempletProduct drawDesignTempletProduct);

	int deleteDrawDesignTempletProduct(@Param("status")Integer status, @Param("emptyTemplets")List<Long> emptyTemplets);

	int deleteDesignTempletProduct(@Param("status")Integer status, @Param("designTemplets")List<Long> designTemplets);
	
	int emptyDesignTempletProduct(@Param("status")Integer status, @Param("designTemplets")List<Long> designTemplets);

	List<JumpPositionDesignTempletProductBO> getJumpPositionDesignTempletProduct(@Param("proType") String proType, @Param("houseId") Long houseId);

    Integer batchSaveDesignTempletProduct(List<DrawDesignTempletProduct> drawDesignTempletProducts);

    Integer batchUpdateDesignTempletProduct(@Param("products") List<DrawDesignTempletProduct> drawDesignTempletProducts);

    Integer batchTransformDesignTempletProduct(List<DrawDesignTempletProduct> drawDesignTempletProducts);

    Integer batchUpdateDrawDesignTempletProductByCallback(List<DrawDesignTempletProduct> drawDesignTempletProducts);
}