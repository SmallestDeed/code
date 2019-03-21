package com.sandu.service.groupproduct.dao;

import com.sandu.api.groupproducct.model.ResFile;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sandu
 */
@Repository
public interface ResFileDao {

    int deleteByPrimaryKey(Long id);

    int insert(ResFile record);

    int insertSelective(ResFile record);


    ResFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResFile record);

    int updateByPrimaryKey(ResFile record);

	List<ResFile> listByIds(@Param("ids")List<Integer> contractIds);
}