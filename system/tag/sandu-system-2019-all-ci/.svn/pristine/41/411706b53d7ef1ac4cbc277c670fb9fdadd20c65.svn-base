package com.sandu.service.miniprogram.dao;

import com.sandu.api.miniprogram.input.ProprietorInfoQuery;
import com.sandu.api.miniprogram.model.ProprietorInfo;
import com.sandu.api.miniprogram.output.ProprietorInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yuxc
 * @date 2018/9/4
 */
@Repository
public interface ProprietorInfoMapper {
    /**
     * 删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入记录
     * @param record
     * @return
     */
    int insert(ProprietorInfo record);

    /**
     * 动态插入
     * @param record
     * @return
     */
    int insertSelective(ProprietorInfo record);

    /**
     * 查询记录
     * @param id
     * @return
     */
    ProprietorInfo selectByPrimaryKey(Integer id);

    /**
     * 动态更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ProprietorInfo record);

    /**
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(ProprietorInfo record);

    /**
     * 根据业主手机查询记录
     * @param mobile
     * @param businessType
     * @param type
     * @return
     */
    ProprietorInfo selectByPhone(@Param("mobile") String mobile,
                                 @Param("businessType") Integer businessType,
                                 @Param("type") Integer type);

    /**
     * 动态查询业主信息表
     * @return
     */
    List<ProprietorInfo> selectByInit();

    /**
     * 查询预约信息
     * @param query
     * @return
     */
    List<ProprietorInfoVO> getProprietorInfoList(ProprietorInfoQuery query);

    Integer getProprietorInfoListCount(ProprietorInfoQuery query);

    Integer updateProprietorInfoProcess(List<Integer> idList);
}