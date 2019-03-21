package com.sandu.api.operatorLog.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *用户操作日志，用于记录用户失效时间的改变
 * @author WangHaiLin
 * @date 2018/7/27  14:24
 */
@Data
public class SysUserOperatorLog implements Serializable {
    /**主键Id**/
    private Integer id;
    /**用户Id**/
    private Long userId;
    /**操作类型：100:申请延时,101:转正式 ...**/
    private Integer eventCode;
    /**时间值(xxYxxMxxD)**/
    private String value;
    /**备注**/
    private String remark;
    /**操作人**/
    private String operator;
    /**操作时间**/
    private Date operatorTime;
    /**是否删除**/
    private Integer isDeleted;

}
