package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.SpaceCommon;
import com.sandu.api.solution.service.SpaceCommonService;
import com.sandu.service.solution.dao.SpaceCommonMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:12 2018/7/5
 */

@Service("spaceCommonService")
public class SpaceCommonServiceImpl implements SpaceCommonService{

    @Resource
    private SpaceCommonMapper spaceCommonMapper;

    @Override
    public Map<Long, Integer> idAndSpaceTypeMap(List<String> spaceTypeIds) {
        spaceTypeIds = spaceTypeIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(spaceTypeIds)) {
            return Collections.emptyMap();
        }
        List<SpaceCommon> spaceCommons = spaceCommonMapper.listByIds(spaceTypeIds);
        return spaceCommons.stream().collect(toMap(SpaceCommon::getId, SpaceCommon::getSpaceFunctionId));
    }

    @Override
    public List<SpaceCommon> getByFunctionId(List<Integer> functionIds) {
        return spaceCommonMapper.getByFunctionId(functionIds);
    }

    @Override
    public SpaceCommon getById(Integer spaceCommonId) {
        return spaceCommonMapper.selectByPrimaryKey(spaceCommonId.longValue());
    }
}
