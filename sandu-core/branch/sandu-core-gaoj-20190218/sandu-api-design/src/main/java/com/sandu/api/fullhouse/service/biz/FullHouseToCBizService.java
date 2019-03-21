package com.sandu.api.fullhouse.service.biz;

import com.sandu.api.fullhouse.common.exception.BizException;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanCopy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface FullHouseToCBizService extends FullHouseBizService {
    /**
     * created by zhangchengda
     * 2018/9/20 12:37
     * 单空间装进我家时临时创建的全屋方案，用于在移动端创建全屋方案(先占坑)
     *
     * @param userId                   当前用户ID
     * @param oldFullHouseDesignPlanId 旧的全屋方案ID
     * @param houseId                  户型ID
     * @return
     * @throws BizException
     */
    Integer addTempFullHouseDesignPlan(Integer userId, Integer oldFullHouseDesignPlanId, Integer houseId);

    /**
     * 在供求信息列表处改造全屋方案时复制全屋方案
     * @param
     * @return
     */
    String copyFullHouseDesignPlan(FullHouseDesignPlanCopy fullHouseDesignPlanCopy, Integer userId) throws BizException;

    Map<String, Object> copyMsgFullHouseExist(String historyMsgId, Integer id);
}
