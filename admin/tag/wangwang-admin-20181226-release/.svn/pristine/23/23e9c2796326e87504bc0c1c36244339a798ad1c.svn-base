package com.sandu.service.mobileArea.impl;

import com.sandu.api.mobileArea.model.MobileArea;
import com.sandu.api.mobileArea.service.MobileAreaService;
import com.sandu.service.mobileArea.dao.MobileAreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mobileArea
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
@Slf4j
@Service("mobileAreaService")
public class MobileAreaServiceImpl implements MobileAreaService {

    @Autowired
    private MobileAreaMapper mobileAreaMapper;

    @Override
    public int insert(MobileArea mobileArea) {
        int result = mobileAreaMapper.insert(mobileArea);
        if (result > 0) {
            return mobileArea.getId();
        }
        return 0;
    }

    @Override
    public int update(MobileArea mobileArea) {
        return mobileAreaMapper.updateByPrimaryKey(mobileArea);
    }

    @Override
    public int delete(Set<Integer> mobileAreaIds) {
        return mobileAreaMapper.deleteByPrimaryKey(mobileAreaIds);
    }

    @Override
    public MobileArea getById(int mobileAreaId) {
        return mobileAreaMapper.selectByPrimaryKey(mobileAreaId);
    }

    @Override
    public List<MobileArea> selectByMobilePrefix(String mobilePrefix) {
        return mobileAreaMapper.selectByMobilePrefix(mobilePrefix);
    }


}
