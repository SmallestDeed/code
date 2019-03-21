package com.sandu.service.user.impl;

import com.sandu.api.redis.RedisService;
import com.sandu.api.user.model.SysFunc;
import com.sandu.api.user.service.SysFuncService;
import com.sandu.common.constant.UserConstant;
import com.sandu.service.user.dao.SysFuncDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysFuncServiceImpl implements SysFuncService {
    private Logger logger = LoggerFactory.getLogger(SysFuncServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private SysFuncDao sysFuncDao;


    @Override
    public List<SysFunc> getUserFuncs(List<Long> funcIdList) {
        if (funcIdList == null || funcIdList.size() == 0) return null;
        Set<String> funcKeys = redisService.keys(UserConstant.RBAC_FUNC_PREFIX + "*");
        logger.info("现有缓存权限id列表:{}", funcKeys);
        String[] funcIdArray = new String[0];
        if (funcKeys == null) funcKeys = new HashSet<String>();
        if (funcKeys.size() >= 0) {
            funcIdArray = new String[funcIdList.size()];
            for (int i = 0; i < funcIdList.size(); i++) {
                //如果缓存数据不存在,则需要从db获取并缓存
                Long funcId = funcIdList.get(i);
                String funcCacheKey = UserConstant.RBAC_FUNC_PREFIX + funcId.toString();
                if (!funcKeys.contains(funcCacheKey)) {
                    SysFunc func = sysFuncDao.selectById(funcId);
                    logger.info("当前用户所拥有的权限{}不在现有缓存中:{}", funcId, funcKeys);
                    if (func != null) {
                        redisService.setObject(funcCacheKey, func);
                    } else {
                        logger.info("数据库数据异常(根据funcid找不到相应数据):{}", funcId);
                        //防止异常数据导致缓存穿透
                        redisService.set(funcCacheKey, "");
                    }
                }
                funcIdArray[i] = UserConstant.RBAC_FUNC_PREFIX + funcId.toString();
            }
        }

        //根据权限id列表获取所对应权限数据
        List<SysFunc> userFuncList = redisService.getObjects(funcIdArray, SysFunc.class);
        logger.info("一次性从缓存获取所有用户权限数据:{}", userFuncList == null ? 0 : userFuncList.size());
        //需要对列表排序
        Comparator<SysFunc> comparator = new Comparator<SysFunc>() {
            public int compare(SysFunc s1, SysFunc s2) {
                // 先排ParentId
                if (s1.getParentid().intValue() != s2.getParentid().intValue()) {
                    return s1.getParentid().intValue() - s2.getParentid().intValue();
                } else {
                    // ParentId相同则按sequence排序
                    int seq1 = s1.getSequence() == null ? 0 : s1.getSequence().intValue();
                    int seq2 = s2.getSequence() == null ? 0 : s2.getSequence().intValue();
                    return seq1 - seq2;
                }
            }
        };
        logger.info("对用户权限数据进行排序 by ParentId,Sequence");
        Collections.sort(userFuncList, comparator);
        return userFuncList;
    }


}
