package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.DesignPlan2cPlatform;
import com.sandu.api.solution.service.DesignPlan2cPlatformService;
import com.sandu.service.solution.dao.DesignPlan2cPlatformMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 10:15 2018/7/13
 */
@Service("designPlan2cPlatformService")
public class DesignPlan2cPlatformServiceImpl implements DesignPlan2cPlatformService{

    @Resource
    private DesignPlan2cPlatformMapper designPlan2cPlatformMapper;

    @Override
    public Map<Integer, DesignPlan2cPlatform> idAnd2cMap(List<Integer> planIds) {
        planIds = planIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(planIds)) {
            return Collections.emptyMap();
        }
        Map<Integer,DesignPlan2cPlatform> map = new HashMap<>(planIds.size());
        //根据方案id查询2c平台记录
        List<DesignPlan2cPlatform> platformBy2b = designPlan2cPlatformMapper.queryByPlanId(planIds);
        for(Integer id : planIds) {
            DesignPlan2cPlatform newPlatform = new DesignPlan2cPlatform();
            String allot = "";
            for(DesignPlan2cPlatform platform : platformBy2b) {
                boolean existed = id.equals(platform.getPlanId());
                if(existed) {
                    allot = "2c";
                }
            }
            newPlatform.setAtt1(allot);
            map.put(id,newPlatform);
        }
        return map;
    }
}
