package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.DesignPlanCopyLog;
import com.sandu.api.solution.service.DesignPlanCopyLogService;
import com.sandu.service.solution.dao.DesignPlanCopyLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 17:47 2018/7/12
 */
@Service("designPlanCopyLogService")
public class DesignPlanCopyLogServiceImpl implements DesignPlanCopyLogService{

    @Resource
    private DesignPlanCopyLogMapper designPlanCopyLogMapper;

    @Override
    public Map<Integer, String> idAndDeliveredMap(List<Integer> planIds,Integer companyId) {
        planIds = planIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(planIds)) {
            return Collections.emptyMap();
        }
        Map<Integer,String> map = new HashMap<>(planIds.size());
        List<DesignPlanCopyLog> copyLogs = designPlanCopyLogMapper.getDeliverDetail(planIds,companyId);
        for(Integer id : planIds) {
            map.put(id,"N");
            for(DesignPlanCopyLog copyLog : copyLogs) {
                boolean existed = id.equals(copyLog.getSourceId());
                if(existed) {
                    map.put(id,"Y");
                    break;
                }
            }
        }
        return map;
    }
}
