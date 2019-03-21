package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 6:11 2018/9/14 0014
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/9/14 0014PM 6:11
 */
@Data
public class CommisionInfoVo implements Serializable{
    private static final long serialVersionUID = 5473620108244501887L;


    private String payNumber;//交易单号

    private String uitCompanyName; //签约公司

    private Integer status; //佣金状态(是否返现)

    private String statusName; //佣金状态名称(是否返现)

    private Integer commision;//每笔佣金金额

    private Date commisionIntime;//佣金收入时间

    private Date commisionRemitTime;//佣金返现时间

    private String userName; //业主名称


}
