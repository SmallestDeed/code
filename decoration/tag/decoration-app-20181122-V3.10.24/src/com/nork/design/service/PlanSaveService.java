package com.nork.design.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;



public interface PlanSaveService {
    

    Object getDesignPlan(String houseId, String livingId, Integer designPlanId, String residentialUnitsName,
            Integer newFlag, String msgId,LoginUser loginUser,Boolean isRelease,String mediaType,Long startTime,String modelType);
   
    /**
     * 校验是否可以存储,间隔时间由配置文件决定
     * 
     * */
    boolean isNewPlan(LoginUser userLogin, Map map);

}
