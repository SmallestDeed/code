package com.sandu.service.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.system.model.ResPic;


@Repository
@Transactional
public interface ResPicDao {
	
    ResPic selectByPrimaryKey(@Param("id") Long id);
   
    List<ResPic> selectByIds(@Param("ids") List<Long> ids);

    int insertResPic(ResPic resPic);
}
