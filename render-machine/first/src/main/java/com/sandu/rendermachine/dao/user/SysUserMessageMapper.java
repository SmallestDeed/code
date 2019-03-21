package com.sandu.rendermachine.dao.user;

import com.sandu.rendermachine.model.user.SysUserMessage;
import org.springframework.stereotype.Repository;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:53 2018/4/18 0018
 * @Modified By:
 */
@Repository
public interface SysUserMessageMapper {

    int insertSelective(SysUserMessage sysUserMessage);

}
