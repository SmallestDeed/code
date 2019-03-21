package com.sandu.service.basewaterjet.impl;

import com.sandu.api.basewaterjet.input.BasewaterjetQuery;
import com.sandu.api.basewaterjet.model.Basewaterjet;
import com.sandu.api.basewaterjet.service.BasewaterjetService;
import com.sandu.service.basewaterjet.dao.BasewaterjetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-09 10:03
 */
@Slf4j
@Service("basewaterjetService")
public class BasewaterjetServiceImpl implements BasewaterjetService {

    @Autowired
    private BasewaterjetMapper basewaterjetMapper;

    @Override
    public int insert(Basewaterjet basewaterjet) {
        int result = basewaterjetMapper.insert(basewaterjet);
        if (result > 0) {
            return basewaterjet.getId();
        }
        return 0;
    }

    @Override
    public int update(Basewaterjet basewaterjet) {
        return basewaterjetMapper.updateByPrimaryKey(basewaterjet);
    }

    @Override
    public int delete(Set<Integer> basewaterjetIds,String userName) {
        return basewaterjetMapper.deleteByPrimaryKey(basewaterjetIds,userName);
    }

    @Override
    public Basewaterjet getById(int basewaterjetId) {
        return basewaterjetMapper.selectInfoByPrimaryKey(basewaterjetId);
    }

    @Override
    public List<Basewaterjet> findAll(BasewaterjetQuery query) {
        return basewaterjetMapper.findAll(query);
    }
    @Override
    public int findAllCount(BasewaterjetQuery query) {
        return basewaterjetMapper.findAllCount(query);
    }

    @Override
    public String getMaxId(){
        return basewaterjetMapper.selectMaxId();
    }
}
