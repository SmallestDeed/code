package com.sandu.service.platform.impl;

import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.platform.model.PlatformGroupRel;
import com.sandu.api.platform.service.Platform2cGroupRelService;
import com.sandu.service.platform.dao.Platform2cGroupRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sandu
 */
@Service
public class Platform2cGroupRelServiceImpl implements Platform2cGroupRelService {
    @Autowired
    private Platform2cGroupRelDao platform2cGroupRelDao;

    @Override
    public int insertRels(List<PlatformGroupRel> rels) {
        try {
            rels.forEach(platform2cGroupRelDao::insertRel);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Override
    public int updateSelectedRelsByGroupId(List<PlatformGroupRel> rels) {
        rels.stream().filter(item -> item.getProductGroupId() != null).forEach(item -> {
           int record = platform2cGroupRelDao.updateRelWithGroupId(item);
           if(record == 0) {
        	   item.setAllotState(1);
        	   platform2cGroupRelDao.insertRel(item);
           }
        });
        return 1;
    }

    @Override
    public int deleteByGroupIds(List<Integer> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return -1;
        }
        return platform2cGroupRelDao.deleteByGroupIds(groupIds);
    }

    @Override
    public List<Integer> getNotAllotGroupIds(List<Integer> groupIds) {
        List<Integer> ids = platform2cGroupRelDao.getBeAllotGroupIds(groupIds);
        if (ids.isEmpty()) {
            return groupIds;
        }
        return groupIds.stream()
                .filter(item -> !ids.contains(item))
                .collect(Collectors.toList());
    }

    @Override
    public GroupProduct getInfoById(Integer groupId) {
        return platform2cGroupRelDao.getInfoByGroupId(groupId);
    }
}
