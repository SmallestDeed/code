package com.sandu.supplydemand.output;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 11:02 2018/11/9 0009
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.annotation.Length;
import com.sandu.annotation.Name;
import com.sandu.annotation.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/11/9 0009AM 11:02
 */
@Data
public class DemandOut implements Serializable{


    private static final long serialVersionUID = -6435706914321675558L;
    private Integer supplyDemandId; //需求信息基本表ID

    private Integer demandInfoRelId ; //需求信息详情ID

}
