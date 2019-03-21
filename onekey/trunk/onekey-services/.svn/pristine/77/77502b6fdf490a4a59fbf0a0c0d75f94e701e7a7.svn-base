package com.nork.onekeydesign.dao;

import com.nork.onekeydesign.model.BaseProductCountertops;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface BaseProductCountertopsMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除数据字典信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName") String loginName);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(BaseProductCountertops record);

    /**
     *
     * @mbggenerated
     */
    BaseProductCountertops selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BaseProductCountertops record);

    /**
     * 根据产品id 获取 橱柜 信息
     * @author chenqiang
     * @param productId 产品id
     * @return BaseProductCountertops 集合
     * @date 2018/7/5 0005 14:54
     */
    List<BaseProductCountertops> selectListByProductId(@Param("productId") Integer productId);
}