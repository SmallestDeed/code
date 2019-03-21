package com.sandu.panorama.dao;

import com.sandu.cityunion.model.UnionContact;
import com.sandu.panorama.model.input.UnionContactSearch;
import com.sandu.panorama.model.output.UnionContactVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnionContactMapper {

    int insertSelective(UnionContact record);

    int updateByPrimaryKeySelective(UnionContact record);

    int deleteByPrimaryKey(Integer id);

    UnionContact selectByPrimaryKey(Integer id);

    UnionContactVo getContact(Integer id);

    /**
     * 查询联系人数量
     * @param unionContactSearch
     * @return
     */
    int selectCount(UnionContactSearch unionContactSearch);

    /**
     * 查询联系人列表
     * @param unionContactSearch
     * @return
     */
    List<UnionContactVo> selectList(UnionContactSearch unionContactSearch);
}
