package com.sandu.api.v2.house.service;

import com.sandu.api.fix.model.FixCupboardSubmitBO;
import com.sandu.api.fix.service.BakeTransformCallback;
import com.sandu.api.fix.service.DrawSpaceSubmitFilter;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.v2.house.bo.DrawSpaceBO;
import com.sandu.api.v2.house.dto.DrawBaseHouseCallbackDTO;

import java.util.List;

public interface DrawBaseHouseServiceV2 {

    /**
     * 提交户型
     *
     * @param dtoNew
     * @param loginBO
     */
    void submit(DrawBaseHouseSubmitDTO dtoNew, UserLoginBO loginBO);

    /**
     * 烘焙户型回调
     *
     * @param dtoNew
     */
    String callback(DrawBaseHouseCallbackDTO dtoNew);
    String callback(DrawBaseHouseCallbackDTO dtoNew,
                    CleanDrawHouseHandler handler,
                    BakeTransformCallback... transformCallbacks);

    List<DrawSpaceBO> submitByFixCupboard(UserLoginBO loginBO,
                                          FixCupboardSubmitBO fixCupboard,
                                          DrawSpaceSubmitFilter filter);

    String handlerCallback(DrawBaseHouseCallbackDTO dtoNew,
                           List<String> bakeFiles,
                           CleanDrawHouseHandler cleanHandler,
                           BakeTransformCallback... transformCallbacks);

    /**
     * 烘焙回调验证
     *
     * @param dtoNew
     * @return
     */
    DrawBakeTaskDetail getDrawBakeTaskDetailByValid(DrawBaseHouseCallbackDTO dtoNew);

    void handlerCallbackError(DrawBaseHouseCallbackDTO dtoNew, String data);

    /**
     * 更新挂节点/模型文件等信息
     *
     * @param dtoNew
     */
    void handlerBakeCallbackResource(DrawBaseHouseCallbackDTO dtoNew, DrawBakeTaskDetail subTask);

    void transformBaseHouse(DrawBaseHouse drawHouse, DrawBakeTaskDetail subTask);
}
