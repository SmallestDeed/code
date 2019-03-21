package com.sandu.service.user.dao;

import com.sandu.api.user.model.ServicesAccountRef;
import org.springframework.stereotype.Repository;

/**
 *套餐账号关联Dao层接口
 * @author WangHaiLin
 * @date 2018/6/2  16:27
 */
@Repository
public interface ServicesAccountRefDao  {
    /**
     * 新增套餐账号关联
     * @param ref
     * @return
     */
    int add (ServicesAccountRef ref);

}
