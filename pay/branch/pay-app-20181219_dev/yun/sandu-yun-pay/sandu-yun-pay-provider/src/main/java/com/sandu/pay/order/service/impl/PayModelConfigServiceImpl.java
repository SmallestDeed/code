package com.sandu.pay.order.service.impl;

import com.sandu.pay.order.dao.PayModelConfigMapper;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.service.PayModelConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yzw
 * @Date 2018/1/16 15:19
 */
@Service("payModelConfigService")
public class PayModelConfigServiceImpl implements PayModelConfigService {

    private static Logger logger = LogManager.getLogger(PayModelConfigServiceImpl.class);

    @Autowired
    private PayModelConfigMapper payModelConfigMapper;


    /**
     * 添加
     *
     * @param record
     * @return
     */
    public PayModelConfig add(PayModelConfig record) {
        if (this.payModelConfigMapper.insertSelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 删除
     *
     * @param id 付款方式表id
     * @return
     */
    public boolean delete(Integer id) {
        return this.payModelConfigMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    public PayModelConfig update(PayModelConfig record) {
        if (this.payModelConfigMapper.updateByPrimaryKeySelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 查询
     *
     * @param id 付款方式表id
     * @return
     */
    public PayModelConfig get(Integer id) {
        return this.payModelConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取付款方式表信息列表
     *
     * @param platformId 平台id
     * @param bizType    业务类型
     * @param rangeType  范围类型
     * @return
     */
    @Override
    public List<PayModelConfig> getPayModelConfigList(Integer platformId, String bizType, Integer rangeType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("platformId", platformId);
        map.put("bizType", bizType);
        map.put("rangeType", rangeType);
        return this.payModelConfigMapper.getPayModelConfigList(map);
    }

    /**
     * 获取付款方式信息列表
     *
     * @param platformId 平台id
     * @param bizType    业务类型
     * @param rangeType  范围类型
     * @return
     */
    @Override
    public List<Map<String, Object>> getRenderPayConfigList(Integer platformId, String bizType, Integer rangeType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("platformId", platformId);
        map.put("bizType", bizType);
        map.put("rangeType", rangeType);
        return this.payModelConfigMapper.getRenderPayConfigList(map);
    }

    @Override
    public PayModelConfig getPayModelConfigByBizType(String bizType) {
        return payModelConfigMapper.getPayModelConfigByBizType(bizType);
    }

}
