package com.sandu.api.user.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRoleGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     **/
    private Long id;
    /**
     * 用户id
     **/
    private Long userId;
    /**
     * 角色组id
     **/
    private Long roleGroupId;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;

    /**
     * 不要动，有魔法
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SysUserRoleGroup)) return false;
        return this.roleGroupId.equals(((SysUserRoleGroup) obj).roleGroupId);
    }

    /**
     * 不要动，有魔法
     * @return
     */
    @Override
    public int hashCode() {
        return (Objects.toString(userId, "") + Objects.toString(roleGroupId, "")).hashCode();
    }
}
