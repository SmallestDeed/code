package com.sandu.supplydemand.input;/**
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
public class DemandAdd implements Serializable{

    private static final long serialVersionUID = 3743397768496398515L;
    @NotBlank
    @Length(min = 1,max = 30)
    @Name("标题")
    private String title;//标题

    @NotBlank
    @Length(min = 1,max = 2000)
    @Name("信息描述")
    private String description;//信息描述

    private String province; //省份编码

    private String city; //城市编码

    private String coverPicId;//封面图片列表

    private Integer planId; //方案ID

    private Integer planType; //方案来源类型

    private Integer houseId; //户型ID

}
