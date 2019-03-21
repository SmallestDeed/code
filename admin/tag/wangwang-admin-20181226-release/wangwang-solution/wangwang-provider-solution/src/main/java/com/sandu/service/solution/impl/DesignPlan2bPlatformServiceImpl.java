package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.DesignPlan2bPlatform;
import com.sandu.api.solution.service.DesignPlan2bPlatformService;
import com.sandu.service.solution.dao.DesignPlan2bPlatformMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 10:16 2018/7/13
 */

@Service("designPlan2bPlatformService")
public class DesignPlan2bPlatformServiceImpl implements DesignPlan2bPlatformService {

    @Resource
    private DesignPlan2bPlatformMapper designPlan2bPlatformMapper;

    @Override
    public Map<Integer, DesignPlan2bPlatform> idAnd2bMap(List<Integer> planIds) {
        planIds = planIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(planIds)) {
            return Collections.emptyMap();
        }
        Map<Integer,DesignPlan2bPlatform> map = new HashMap<>(planIds.size());
        //根据方案id查询2b平台记录
        List<DesignPlan2bPlatform> platformBy2b = designPlan2bPlatformMapper.queryByPlanId(planIds);
        for(Integer id : planIds) {
            DesignPlan2bPlatform newPlatform = new DesignPlan2bPlatform();
            String allot = "";
            for(DesignPlan2bPlatform platform : platformBy2b) {
                boolean existed = id.equals(platform.getPlanId());
                if(existed) {
                    allot = "2b";
                }
            }
            newPlatform.setAtt1(allot);
            map.put(id,newPlatform);
        }
        return map;
    }
}
