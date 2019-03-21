package com.sandu.user.model;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:02 2018/9/14 0014
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
 * @date 2018/9/14 0014PM 4:02
 */
@Data
public class BankWord implements Serializable{
    private static final long serialVersionUID = 7958954386771531931L;

    private String bankName; //银行名称
    private String brankNameFirstLetter;//银行名称首字母
}
