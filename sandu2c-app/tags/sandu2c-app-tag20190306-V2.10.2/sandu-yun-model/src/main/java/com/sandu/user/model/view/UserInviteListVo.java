package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:30 2018/6/6 0006
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: 用户邀请回调接收参数
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/6/6 0006AM 10:30
 */
@Data
public class UserInviteListVo implements Serializable{
    private static final long serialVersionUID = 8754327018804586605L;
    private String  userPicPath; //用户头像
    private Integer userType; //用户类型
    private String  userName; //用户名称
    private Date registertime; //注册时间
    private Date signTime; //签约时间
    private Integer status; //'签约状态0:进行中;1:已签约;2:无意向',
    private String statusName; //签约状态名称
    private Integer sex; //性别

}
