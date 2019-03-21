package com.sandu.service.goods.dao;

import com.sandu.api.goods.model.ResPic;
import com.sandu.api.goods.output.PicVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResPicMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(ResPic record);

    int insertSelective(ResPic record);

    ResPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResPic record);

    int updateByPrimaryKey(ResPic record);

    Integer getIdBySysCode(String sysCode);

    List<PicVO> getPicsByIds(List<Integer> ids);
}