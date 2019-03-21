/**
 * 礼品服务接口
 *
 */
package com.sandu.api.pointmall.service;

import com.sandu.api.pointmall.model.UserPointInout;
import com.sandu.api.pointmall.model.vo.PointVo;

public interface UserPointService {

    /**
     * 获取我的可用积分
     * @return
     */
    PointVo getPointVo(int uid);

    UserPointInout selectLastPoint(int uid);

    int insertSelective(UserPointInout record);

}
