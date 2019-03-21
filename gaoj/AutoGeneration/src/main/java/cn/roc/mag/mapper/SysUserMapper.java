package cn.roc.mag.mapper;

import cn.roc.mag.entity.SysUser;

public interface SysUserMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysUser record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysUser record);

    /**
     *
     * @mbggenerated
     */
    SysUser selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysUser record);
}