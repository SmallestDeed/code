package com.sandu.designplan.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 5:00 2018/7/26 0026
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title: 推荐分享方案列表
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/26 0026PM 5:00
 */
@Data
public class SharePlanDTO implements Serializable{
    private static final long serialVersionUID = 5918897145095625019L;

    private String planName; //推荐方案名称

    private Integer planRecommendedId; //推荐方案ID

    private String spaceAreas; //方案面积

    private String planStyleName;//方案风格

    private String planCoverPath; //方案照片级渲图

    private Integer decorationQuotation; //方案装修总价

    private BigDecimal planPrepPrice; //方案预计收入
}
