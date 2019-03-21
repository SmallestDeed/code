package com.sandu.api.house.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.*;
import com.sandu.api.house.model.BaseLiving;
import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.house.model.DrawDesignTemplet;
import com.sandu.api.house.output.DrawHouseAndAreaVO;
import com.sandu.api.volume.room.model.VolumeRoomHouseBO;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * 户型绘制接口类
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/19
 */
public interface DrawBaseHouseService {

    /**
     * 查询绘制户型列表
     *
     * @param drawBaseHouseSearch 户型查询对象
     * @return
     */
    Map<String, Object> listDrawHouse(DrawBaseHouseSearch drawBaseHouseSearch);

    Map<Long, String> mapResource(List<DrawBaseHouseBO> drawHouses);

    Map<Long, VolumeRoomHouseBO> mapVolumeRoomHouse(List<DrawBaseHouseBO> drawHouses);

    /**
     * 保存户型绘制
     *
     * @param data
     * @return
     * @throws BusinessException
     */
    Map<String, Object> saveDrawBaseHouse(String data, String userId) throws BusinessException;

    /**
     * 删除户型绘制
     *
     * @param houseId
     * @param userId
     * @throws BusinessException
     */
    void deleteDrawBaseHouse(Long houseId, Long userId) throws BusinessException;

    /**
     * 审核户型绘制
     *
     * @param check
     * @throws BusinessException
     */
    void checkDrawBaseHouse(DrawBaseHouseCheck check) throws BusinessException;

    /**
     * 获取户型数据
     *
     * @param houseId
     * @return
     */
    DrawBaseHouse getBaseHouse(Long houseId);

    /**
     * 加载户型详情数据
     *
     * @param houseId 户型id
     * @param userId  用户id
     * @return
     * @throws BusinessException
     */
    DrawBaseHouseBO loadDrawBaseHouse(Long houseId, Long userId) throws BusinessException;

    /**
     * 户型绘制提交数据
     * 1.新建空间
     * 2.新建产品数据
     * 3.新建样板房
     * 4.新建样板房产品表
     *
     * @param jsonStr
     * @param loginBO 用户信息
     * @return
     * @throws DrawBusinessException
     * @author huangsongbo
     */
    DrawBaseHouseSubmitDTO submit(String jsonStr, UserLoginBO loginBO) throws DrawBusinessException;

    void submitV2(String data) throws DrawBusinessException;

    /**
     * 普通绘制员上传户型
     * 1.新建空间
     * 2.新建产品数据
     * 3.新建样板房
     * 4.新建样板房产品表
     *
     * @param houseId
     * @throws DrawBusinessException
     * @author songjianming@sandusapace.com
     */
    void uploadHouse(Long houseId) throws DrawBusinessException;

    /**
     * 户型绘制获取小区下的户型，包括用户已绘制户型和未绘制户型
     *
     * @param query
     * @return
     */
    Map<String, Object> getHouses(DrawBaseHouseSearch query);

    DrawHouseAndAreaVO facsimileHouse(Long houseId, String type);


    /**
     * 烘培回调
     * 主要任务:
     * 1.更新样板房挂节点等信息
     * 2.draw表数据转换到正式表数据
     *
     * @param jsonStr
     * @throws DrawBusinessException
     * @author huangsongbo
     */
    Map<String, Object> callback(String jsonStr) throws DrawBusinessException;

    @Transactional(propagation = Propagation.REQUIRED)
    void handlerHouseStatus(DrawBaseHouse drawHouse, Long taskDetailId);

    void transformHouseResource(DrawBaseHouse drawHouse, Long taskId);

    DrawDesignTemplet updateResource(DrawDesignTempletDTO drawDesignTempletDTO, Long drawSpaceCommonId);

    void updateProduct(DrawDesignTempletDTO drawDesignTempletDTO, DrawDesignTemplet drawDesignTemplet);

    DrawBaseHouse get(Long drawBaseHouseId);

    /**
     * drawBaseHouse ->baseHouse
     *
     * @param drawBaseHouse
     * @return
     * @author huangsongbo
     */
    Map<Long, Long> transformToBaseHouse(DrawBaseHouse drawBaseHouse);

    void update(DrawBaseHouse drawBaseHouse);

    DrawHouseAndAreaVO perfectHouse(Long houseId);

    void handlerSubmitHouse(Long houseId);

    void handlerCallbackHouse(Long houseId, Long taskId);

    Long handlerBakeTaskFailed(String data, String message) throws IOException;

    void handlerCheck(DrawBaseHouseCheck check, DrawBaseHouse drawHouse);

    void transformHouse(String phone, Long houseId);

    void doSyncToIndex(Integer messageAction, List<Long> ids);

    BaseLiving getBaseLiving(DrawBaseHouseNew data);
}
