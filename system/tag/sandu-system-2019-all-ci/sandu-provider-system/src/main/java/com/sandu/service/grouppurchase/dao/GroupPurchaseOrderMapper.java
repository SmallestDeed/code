package com.sandu.service.grouppurchase.dao;

import com.sandu.api.grouppurchase.model.GroupPurchaseOrder;
import org.springframework.stereotype.Repository;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/11 10:31
 * @since 1.8
 */

@Repository
public interface GroupPurchaseOrderMapper {
    Integer createOrder(GroupPurchaseOrder orderNew);
}
