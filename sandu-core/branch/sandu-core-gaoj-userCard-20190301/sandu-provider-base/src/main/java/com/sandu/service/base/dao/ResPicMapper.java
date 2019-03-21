package com.sandu.service.base.dao;

import com.sandu.api.base.model.ResPic;
import com.sandu.api.base.output.ResPicVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResPicMapper {

    ResPic selectByPrimaryKey(Integer id);

    List<String> getPathListByIdList(@Param("idList") List<Integer> idList);

    List<ResPicVo> getByIdList(@Param("idList") List<Integer> idList);
}
