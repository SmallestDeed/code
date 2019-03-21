package com.nork.cityunion.dao;

import com.nork.cityunion.model.UnionContact;
import com.nork.cityunion.model.search.UnionContactSearch;
import com.nork.cityunion.model.vo.UnionContactVo;
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
