package com.sandu.rendermachine.dao.user;

import com.sandu.rendermachine.model.user.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:36 2018/4/20 0020
 * @Modified By:
 */
@Repository
public interface SysUserMapper {

    SysUser selectByPrimaryKey(Integer id);

}
