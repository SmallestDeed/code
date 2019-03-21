package com.sandu.pay.order.service;

import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.ResultMessage;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author yzw
 * @Date 2018/1/16 15:18
 */
public interface PayModelConfigService {

    /**
     * 添加
     *
     * @param record
     * @return
     */
    PayModelConfig add(PayModelConfig record);

    /**
     * 删除
     *
     * @param id 付款方式表id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    PayModelConfig update(PayModelConfig record);

    /**
     * 查询
     *
     * @param id 付款方式表id
     * @return
     */
    PayModelConfig get(Integer id);

    /**
     * 获取付款方式表信息列表
     *
     * @param platformId 平台id
     * @param bizType    业务类型
     * @param rangeType  范围类型
     * @return
     */
    List<PayModelConfig> getPayModelConfigList(Integer platformId, String bizType, Integer rangeType);

    /**
     * 获取付款方式信息列表
     *
     * @param platformId 平台id
     * @param bizType    业务类型
     * @param rangeType  范围类型
     * @return
     */
    List<Map<String, Object>> getRenderPayConfigList(Integer platformId, String bizType, Integer rangeType);

    PayModelConfig getPayModelConfigByBizType(String s);
}
