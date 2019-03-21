package com.sandu.api.order.input;

import com.sandu.common.BaseQuery;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderManageQuery extends BaseQuery implements Serializable {


    private String queryParam;
}