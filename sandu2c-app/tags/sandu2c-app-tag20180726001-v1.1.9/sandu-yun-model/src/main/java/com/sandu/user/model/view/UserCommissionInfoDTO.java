package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:25 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Title: 用户佣金详情
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/23 0023PM 3:25
 */
@Data
public class UserCommissionInfoDTO implements Serializable{

    private static final long serialVersionUID = -5602364964226020708L;
    private Integer userId; //用户ID

    private Integer inviteIdCount; //用户邀请数

    private Integer commisionCount; //用户邀请金额


}
