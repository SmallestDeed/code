package com.sandu.pay.order.dao;


import com.sandu.pay.order.model.PayModelConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PayModelConfigMapper {

    /**
     * 删除
     *
     * @param id 付款方式表id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加
     *
     * @param record
     * @return
     */
    int insertSelective(PayModelConfig record);

    /**
     * 查询
     *
     * @param id 付款方式表id
     * @return
     */
    PayModelConfig selectByPrimaryKey(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PayModelConfig record);

    /**
     * 获取付款方式表信息列表
     *
     * @param map
     * @return
     */
    List<PayModelConfig> getPayModelConfigList(Map<String, Object> map);

    /**
     * 获取付款方式信息列表
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> getRenderPayConfigList(Map<String, Object> map);

    PayModelConfig getPayModelConfigByBizType(String bizType);
}