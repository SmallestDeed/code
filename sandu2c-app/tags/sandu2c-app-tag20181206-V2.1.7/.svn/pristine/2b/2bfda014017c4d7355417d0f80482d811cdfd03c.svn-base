package com.sandu.system.dao;

import com.sandu.system.model.ResFile;
import com.sandu.system.model.search.ResFileSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResFileMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统模块-文件资源库Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-07-02 17:36:00
 */
@Repository
public interface ResFileMapper {
    int insertSelective(ResFile record);

    int updateByPrimaryKeySelective(ResFile record);

    int deleteByPrimaryKey(Integer id);

    ResFile selectByPrimaryKey(Integer id);

    int selectCount(ResFileSearch resFileSearch);

    List<ResFile> selectPaginatedList(
            ResFileSearch resFileSearch);

    List<ResFile> selectList(ResFile resFile);

    ResFile findById(@Param("id") Long id);
}
