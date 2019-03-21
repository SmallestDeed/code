package com.nork.system.service;

import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/8/7 0007 11:12
 * @Modified By
 */
@Component
public interface ModelAreaRelService {

    /**
     * 获取模型 多维材质 详情
     * @author chenqiang
     * @param baseProductId         产品id
	 * @param modelId               模型id
	 * @param type                  获取类型
	 * @param modelType             是否使用模型获取材质信息：1:使用
     * @return
     * @date 2018/8/7 0007 11:19
     */
    Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId, Integer modelId,String type,String modelType);
}
