package com.sandu.api.house.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.model.DrawSpaceCommon;
import com.sandu.exception.DrawBusinessException;

public interface DrawDesignTempletService {

	/**
	 * 保存样板房数据
	 * 
	 * @author huangsongbo
	 * @param dtoNew
	 * @param userLoginBO 
	 * @return
	 */
	DrawBaseHouseSubmitDTO saveDrawDesignTempletBySubmit(
            DrawBaseHouseSubmitDTO dtoNew, UserLoginBO userLoginBO);

	List<DrawDesignTemplet> findAllBySpaceCommonId(Long drawSpaceCommonId);

	DrawDesignTemplet getDrawDesignTempletBySpaceCommonId(Long drawSpaceCommonId);

	void update(DrawDesignTemplet drawDesignTemplet);

	Map<Long, Long> transformToDesignTemplet(
			List<DrawDesignTemplet> drawDesignTempletList, Long spaceCommonId) throws DrawBusinessException;

    void handlerDesignTemplet(DrawDesignTemplet drawDesignTemplet);

    void handlerDesigmTempletPic(DrawDesignTemplet designTemplet);

    Integer deleteDrawDesignTemplet(Integer status, List<Long> emptySpaces);

	Long save(DrawDesignTemplet drawDesignTemplet);

	Integer deleteDesignTemplet(Integer status, List<Long> emptySpaces);

	List<Long> getDeleteDesignTemplet(List<Long> emptySpaces);

	Integer emptyDesignTemplet(Integer status, List<Long> emptySpaces);

	List<Long> getEmptyDesignTemplet(List<Long> emptySpaces);

	List<Long> getEmptyDrawDesignTemplet(List<Long> emptySpaces);
}
