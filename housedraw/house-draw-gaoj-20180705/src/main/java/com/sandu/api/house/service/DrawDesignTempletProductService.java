package com.sandu.api.house.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.bo.JumpPositionDesignTempletProductBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import com.sandu.api.house.search.DrawDesignTempletProductSearch;
import com.sandu.exception.DrawBusinessException;

public interface DrawDesignTempletProductService {

	DrawBaseHouseSubmitDTO saveDesignTempletProductBySubmit(
            DrawBaseHouseSubmitDTO dtoNew, UserLoginBO userLoginBO) throws DrawBusinessException;

	List<DrawDesignTempletProduct> findAllBySearch(
			DrawDesignTempletProductSearch drawDesignTempletProductSearch);

	void update(DrawDesignTempletProduct drawDesignTempletProduct);

	List<DrawDesignTempletProduct> findAllByDesignTempletId(Long designTempletId);

	Map<Long, Long> transformToDesignTempletProduct(
			List<DrawDesignTempletProduct> drawDesignTempletProductList,
			Long designTempletId, Map<Long, Long> drawBaseProductTransformMap) throws DrawBusinessException;

	Integer deleteDrawDesignTempletProduct(Integer status, List<Long> emptyTemplets);

	Integer deleteDesignTempletProduct(Integer status, List<Long> designTemplets);

	Integer emptyDesignTempletProduct(Integer status, List<Long> designTemplets);

	List<JumpPositionDesignTempletProductBO> getJumpPositionDesignTempletProduct(String proType, Long houseId);

    Integer batchUpdateDesignTempletProduct(List<DrawDesignTempletProduct> drawDesignTempletProducts);
}