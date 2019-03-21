package com.sandu.designshare.dao;

import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designshare.model.SysUserGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017-11-13.
 */
@Repository
public interface DesignPlanShareMapper {

    /**
     * get720ResRenderPicByGid 得到720的res_render_pic资源
     * @param groupBusinessId
     * @return
     * @return List<ResRenderPic>    返回类型
     * @Exception 异常对象
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    List<ResRenderPic> get720ResRenderPicByGid(String groupBusinessId);

    /**
     * describe: 获取组的分享次数
     * creat_user: yanghz
     * creat_date: 2017-07-31
     * creat_time: 下午 07:13
     **/
    int countListShare(String group_businessId);

    /**
     * addShareTimes( 发生分享时候的次数统计)
     * @param group
     * @return boolean    返回类型
     * @Exception 异常对象
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    boolean addShareTimes(SysUserGroup group);
}
