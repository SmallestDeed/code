package com.sandu.api.base.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserCardStatisticsVo
 * @Auther: gaoj
 * @Date: 2019/3/5 11:35
 * @Description:
 * @Version 1.0
 */
@Data
public class UserCardStatisticsVo implements Serializable {

    /**登录名(对应sys_user表就是nick_name)**/
    private String nickName;
    /**昵称(对应sys_user表就是user_name)**/
    private String userName;
    /**手机号(对应sys_user表的mobile)**/
    private String mobile;
    /**品牌名称集合**/
    private String brandNameStr;
    /**公司id**/
    private Integer companyId;
    /**公司名称**/
    private String companyName;
    /**今日访问次数**/
    private Integer todayVisitCount;
    /**今日访问人数**/
    private Integer todayVisitUser;
    /**当月访问次数**/
    private Integer monthVisitCount;
    /**当月访问人数**/
    private Integer monthVisitUser;
    /**总访问次数**/
    private Integer totalVisitCount;
    /**总访问人数**/
    private Integer totalVisitUser;
    /**今日转发次数**/
    private Integer todayTransmitCount;
    /**当月转发次数**/
    private Integer monthTransmitCount;
    /**总转发次数**/
    private Integer totalTransmitCount;

}
