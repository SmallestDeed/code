package com.sandu.api.house.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.bo.DrawSpaceCommonBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.model.DrawSpaceCommon;
import com.sandu.api.house.model.HouseSpaceNew;
import com.sandu.api.house.model.SystemDictionary;
import com.sandu.exception.BusinessException;
import com.sandu.exception.DrawBusinessException;

/**
 * Description:
 * 空间接口类
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
public interface DrawSpaceCommonService {

    /**
     * 查询户型下的所有的空间
     *
     * @param houseId
     * @return
     */
    List<DrawSpaceCommonBO> listSpaceCommon(Long houseId);

    List<DrawSpaceCommonBO> listSpaceCommon(List<DrawSpaceCommon> drawSpaces);

    /**
     * 获取空间功能代码
     *
     * @param spaceFunctionId 功能类型
     * @return
     */
    SystemDictionary getSpaceFunction(Integer spaceFunctionId) throws BusinessException;

    /**
     * 获取空间平均面积
     *
     * @param spaceAreas 空间面积
     * @return
     */
    SystemDictionary getSpaceArea(String spaceAreas, String type) throws BusinessException;

    /**
     * 获取空间编码前缀
     *
     * @param spaceFunctionId 空间功能类型
     * @param spaceAreas      空间面积
     * @return
     * @throws BusinessException
     */
    String getSpaceCommonCode(Integer spaceFunctionId, String spaceAreas) throws BusinessException;

    /**
     * 查询正式空间表中用该空间前缀的有多少
     *
     * @param spaceCodeSuffix
     * @return
     */
    Integer countSpaceCommonBySpaceCode(String spaceCodeSuffix);

    /**
     * 保存 空间信息
     *
     * @param spaceCommon
     */
    Long saveSpaceCommon(DrawSpaceCommon spaceCommon);

    /**
     * 保存户型和空间的关系
     *
     * @param relation
     */
    void saveHouseAndSpaceRelation(HouseSpaceNew relation);

    /**
     * 更新户型与灯光模型的关系
     *
     * @param resModelId 灯光模型文件id
     * @param spaceId    空间id
     */
    void updateSpaceLight(Long resModelId, Long spaceId, String modelType);

    /**
     * 根据id获取空间信息
     *
     * @param id 空间id
     * @return
     */
    DrawSpaceCommon getSpaceCommon(Long id);

    /**
     * 保存绘制空间
     *
     * @param drawSpaceCommon
     * @return
     */
    Long saveDrawSpaceCommon(DrawSpaceCommon drawSpaceCommon);

    /**
     * 保存户型和空间的关系
     *
     * @param relation
     */
    void saveDrawHouseAndSpaceRelation(HouseSpaceNew relation);

    /**
     * 批量删除空间
     *
     * @param spaceIds 空间id集合
     */
    void deleteDrawSpaceCommon(List<Long> spaceIds);

    /**
     * 删除户型和空间的关系
     *
     * @param houseId 户型id
     */
    void deleteDrawHouseSpaceRelation(Long houseId);

    DrawBaseHouseSubmitDTO saveDrawSpaceCommonBySubmit(
            DrawBaseHouseSubmitDTO dto, UserLoginBO userLoginBO);

    void update(DrawSpaceCommon drawSpaceCommon);

    DrawSpaceCommon get(Long spaceId);

    Map<Long, Long> transformToSpaceCommon(DrawSpaceCommon drawSpaceCommon,
                                           Long baseHouseId) throws DrawBusinessException;

    void handlerSpaceCommon(DrawSpaceCommon drawSpaceCommon);

    void handlerSpacePic(DrawSpaceCommon drawSpaceCommon);

    List<Long> getDealDrawSpaceCommon(Long houseId);

    /**
     * 批量删除绘制空间(draw_space_common)
     *
     * @param status
     * @param emptySpaces
     * @return
     */
    Integer deleteDrawSpaceCommon(Integer status, List<Long> emptySpaces);

    /**
     * 批量删除绘制空间(space_common)
     *
     * @param status
     * @param emptySpaces
     * @return
     */
    Integer deleteSpaceCommon(Integer status, List<Long> emptySpaces);

    List<Long> getDealSpaceCommon(Long spaceId);

    Integer deleteHouseSpaceRelation(Integer status, List<Long> emptySpaces);

    List<Long> getEmptyDealSpaceCommon(Long houseId);

    Integer emptySpaceCommon(Integer status, List<Long> emptySpaces);

    Integer emptyHouseSpaceRelation(Integer status, List<Long> emptySpaces);
}
