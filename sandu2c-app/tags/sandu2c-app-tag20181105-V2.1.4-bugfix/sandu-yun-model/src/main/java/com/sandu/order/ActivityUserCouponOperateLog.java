package com.sandu.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户优惠卷操作记录
 * @author WangHaiLin
 * @date 2018/8/1  14:10
 */
@Data
public class ActivityUserCouponOperateLog  implements Serializable{

    /**主键Id**/
    private Long id;

    /**用户Id**/
    private Long userId;

    /**企业Id**/
    private Long companyId;

    /**店铺Id**/
    private Long shopId;

    /**优惠卷Id**/
    private Long couponId;

    /**优惠卷编号**/
    private Long couponNo;

    /**操作类型 1:使用优惠券,2:退还优惠券**/
    private Integer operatorType;

    /**操作时间**/
    private Date operatorTime;

    /**订单Id**/
    private Long orderId;

    /**订单编号**/
    private Long orderNo;

    /**创建者**/
    private String creator;

    /**创建时间**/
    private Date gmtCreate;
    /**是否删除**/
    private Integer isDeleted;

}
