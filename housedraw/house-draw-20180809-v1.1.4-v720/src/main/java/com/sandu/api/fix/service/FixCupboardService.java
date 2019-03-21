package com.sandu.api.fix.service;

import com.sandu.api.fix.model.FixCupboardCallbackBO;
import com.sandu.api.fix.model.FixCupboardQuery;
import com.sandu.api.fix.model.FixCupboardSaveBO;
import com.sandu.api.fix.model.FixCupboardSubmitBO;
import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.v2.house.bo.DrawSpaceBO;

import java.util.List;
import java.util.Map;

public interface FixCupboardService {

    Map<String, Object> listFixCupboardHouse(FixCupboardQuery query);
    Map<String, Object> listFixCupboardHouseOfRedis(FixCupboardQuery query);

    Map<String, Object> save(FixCupboardSaveBO fixCupboard);
    Map<String, Object> saveV2(FixCupboardSaveBO fixCupboard);

    void handlerFixCupboardErrorBySave(FixCupboardSaveBO fixCupboard);

    void submit(FixCupboardSubmitBO fixCupboard);
    void submitV2(FixCupboardSubmitBO fixCupboard);

    void check(DrawBaseHouse drawHouse, List<DrawSpaceBO> drawSpaceBOS, UserLoginBO loginBO);

    Long getTaskId(String queueName);

    List<DrawBakeTaskBO> getSubTask(String queueName, Long taskId);

    String callback(FixCupboardCallbackBO fixCupboard);

    void handlerCallbackError(FixCupboardCallbackBO fixCupboard, String data);

    /**
     * 添加户型到修复队列中
     * @param houseIds
     * @param fixQuery
     * @param isReset
     */
    void addFixTaskToQueue(List<String> houseIds, boolean isReset, FixCupboardQuery fixQuery);

    /**
     * 返回修复列表中的待修复的户型数量
     * @return
     */
    Long getFixQueueCount();
}
