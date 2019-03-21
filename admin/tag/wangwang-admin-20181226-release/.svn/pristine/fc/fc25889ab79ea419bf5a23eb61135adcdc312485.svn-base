package com.sandu.service.platform.impl;

import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.platform.model.PlatformGroupRel;
import com.sandu.api.platform.service.Platform2bGroupRelService;
import com.sandu.service.platform.dao.Platform2bGroupRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Sandu
 */
@Service
public class Platform2bGroupRelServiceImpl implements Platform2bGroupRelService {

    @Autowired
    private Platform2bGroupRelDao platform2bGroupRelDao;

    @Override
    public int insertRels(List<PlatformGroupRel> rels) {
        try {
            rels.forEach(platform2bGroupRelDao::insertRel);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateSelectedRelsByGroupId(List<PlatformGroupRel> rels) {
//        AtomicInteger ret = new AtomicInteger();
//        rels.stream().filter(item -> item.getProductGroupId() != null).forEach(item -> {
//            ret.set(platform2bGroupRelDao.updateRelWithGroupId(item) + ret.get());
//        });
//        return ret.get();
        rels.stream().filter(item -> item.getProductGroupId() != null).forEach(item -> {
            int record = platform2bGroupRelDao.updateRelWithGroupId(item);
            if(record == 0) {
                item.setAllotState(1);
                platform2bGroupRelDao.insertRel(item);
            }
        });
        return 1;
    }

    @Override
    public int deleteByGroupIds(List<Integer> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return -1;
        }
        return platform2bGroupRelDao.deleteByGroupIds(groupIds);
    }

    @Override
    public List<Integer> getNotAllotGroupIds(List<Integer> groupIds) {
        List<Integer> ids = platform2bGroupRelDao.getBeAllotGroupIds(groupIds);
        if (ids.isEmpty()) {
            return groupIds;
        }
        return groupIds.stream()
                .filter(item -> !ids.contains(item))
                .collect(Collectors.toList());
    }

    @Override
    public GroupProduct getInfoById(Integer groupId) {
        return platform2bGroupRelDao.getInfoByGroupId(groupId);
    }
}
