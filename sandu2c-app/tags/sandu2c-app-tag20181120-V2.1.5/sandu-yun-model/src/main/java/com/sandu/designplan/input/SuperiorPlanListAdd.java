package com.sandu.designplan.input;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 5:14 2018/10/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/10/29 0029PM 5:14
 */
@Data
public class SuperiorPlanListAdd implements Serializable{
    private static final long serialVersionUID = -3972371450406063532L;


    private Integer spaceType; //空间类型

    private Integer bizType;//业务类型

}
