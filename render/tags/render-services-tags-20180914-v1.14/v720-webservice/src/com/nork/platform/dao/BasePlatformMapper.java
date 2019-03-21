package com.nork.platform.dao;

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
    
}
