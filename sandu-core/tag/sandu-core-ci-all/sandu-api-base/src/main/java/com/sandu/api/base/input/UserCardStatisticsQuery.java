package com.sandu.api.base.input;

import com.sandu.api.base.common.model.PageModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserCardStatisticsQuery
 * @Auther: gaoj
 * @Date: 2019/3/5 10:41
 * @Description:
 * @Version 1.0
 */
@Data
public class UserCardStatisticsQuery extends PageModel implements Serializable{

    /**登录名(对应sys_user表就是nick_name)**/
    private String nickName;

    /**手机号(对应sys_user表的mobile)**/
    private String mobile;

    /**昵称(对应sys_user表就是user_name)**/
    private String userName;

    /**公司id**/
    private Integer companyId;

    /**排序字段**/
    private String order;

    /**排序方式**/
    private String sort;

    /**公司id集合**/
    private List<Integer> companyIdList;

}
