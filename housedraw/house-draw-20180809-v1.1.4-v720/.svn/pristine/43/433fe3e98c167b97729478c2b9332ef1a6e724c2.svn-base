package com.sandu.service.model.dao;

import java.util.List;
import java.util.Set;

import com.sandu.api.house.model.DrawBaseProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.res.model.ResModel;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/2
 */
@Repository
public interface ResModelMapper {

	Long saveResModel(ResModel resModel);

	ResModel getResModel(Long id);

	void insertSelective(ResModel resModel);

	void updateBusinessId(@Param("resModelIdList") List<Integer> resModelIdList, @Param("businessId") Integer businessId);

	List<ResModel> listResModelById(@Param("args") Set<Long> args);

	ResModel selectByPrimaryKey(Integer id);

    Integer addBatchModelFile(List<ResModel> resModels);

	Integer updateBatchBusinessId(List<DrawBaseProduct> drawProducts);
}
