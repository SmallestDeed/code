package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:25 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: 用户佣金详情
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/23 0023PM 3:25
 */
@Data
public class MyCommissionInfoVo implements Serializable{

    private static final long serialVersionUID = -5602364964226020708L;



    private Integer commisionTotal; //累计收益

    private Integer myCommisionTotal;//我的资产

    private Integer myBankCardCount;//我绑定的银行卡数量

}
