package com.sandu.api.dictionary.service.biz;

import org.springframework.stereotype.Component;

/**
 * @Author chenqiang
 * @Description 数据字典 逻辑 业务层
 * @Date 2018/6/28 0028 11:35
 * @Modified By
 */
@Component
public interface DictionaryBizService {

    /**
     * 通过类型和values获取名称
     * @auth chenqiang
     * @param userId 用户id
     * @param userType 用户类型
     * @return DictionaryTypeListVO 列表
     */
    boolean checkUserType(String userId,Integer userType);

}
