package com.sandu.user.model.input;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 9:49 2018/5/15 0015
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.annotation.Name;
import com.sandu.annotation.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: 用户评论入参
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/15 0015AM 9:49
 */
@Data
public class UserDemandReviewsAdd implements Serializable {
    private static final long serialVersionUID = 8613783434208818777L;

    /**
     * 评论详情
     */
    @NotBlank
    @Name("评论详情")
    private String reviewsMsg;

    @NotBlank
    @Name("供求信息ID")
    private Integer businessId;


    /**
     * 信息发布者ID
     */
    @NotBlank
    @Name("供求信息发布者ID")
    private Integer supplyDemandPublisherId;

    /**
     * 图片ID
     */
    private String coverPicIds;

    /**
     * 方案ID
     */
    private Integer planId;

    /**
     * 户型ID
     */
    private Integer houseId;

    /**
     * 方案类型
     */
    private Integer planType;


}
