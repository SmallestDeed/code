package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:21 2018/6/6 0006
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: 邀请详情
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/6/6 0006PM 3:21
 */
@Data
public class UserInviteInfoVo implements Serializable {
    private static final long serialVersionUID = -1798663227886196622L;

    private Integer fid; //被邀请人ID

    private String userName; //被邀请人名称

    private String userIcon; //被邀请人头像

    private Date registerTime; //被邀请人注册时间

    private String userTypeStr; //被邀请人用户类型

}
