package com.nork.platform.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nork.platform.model.BasePlatform;


/**   
 * @Title: BasePlatformMapper.java 
 * @Package com.nork.platform.dao
 * @Description:基础-平台表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
@Repository
@Transactional
public interface BasePlatformMapper {
        
    BasePlatform selectByCode(String platform);

    /**
     * 根据id查询平台信息
     * @author: chenm
     * @date: 2019/1/18 18:22
     * @param id
     * @return: com.nork.platform.model.BasePlatform
     */
    BasePlatform selectPlatformById(@Param(value = "id") Integer id);
}
