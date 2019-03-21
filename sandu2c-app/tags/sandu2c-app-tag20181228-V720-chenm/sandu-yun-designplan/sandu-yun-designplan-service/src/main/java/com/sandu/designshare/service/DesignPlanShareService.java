package com.sandu.designshare.service;

import com.sandu.designplan.model.Group;

/**
 * Created by Administrator on 2017-11-13.
 * Rename to DesignPlanShareService From SysUserGroupService by pengxuangang on 20171113.
 * 设计方案分享服务
 *
 * @date 20171113
 * @auth pengxuangang
 */
public interface DesignPlanShareService {

    /**
     * doShare 分享组里面的设计方案渲染图
     * @param groupBusinessId
     * @return
     * @return List    返回类型
     * @Exception 异常对象
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    Group doShare(String groupBusinessId);
}
