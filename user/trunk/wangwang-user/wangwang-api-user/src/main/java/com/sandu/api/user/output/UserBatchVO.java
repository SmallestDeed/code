package com.sandu.api.user.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 购买“套餐”批量生成用户输出
 * @author WangHaiLin
 * @date 2018/6/1  16:04
 */
@Data
public class UserBatchVO  implements Serializable {

    /**
     * 套餐Id
     */
    private Long serviceId;
    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * 购买记录Id
     */
    private Long purchaseRecordId;

    /**
     *账户列表
     */
    private Map<Long,String> map;


}
