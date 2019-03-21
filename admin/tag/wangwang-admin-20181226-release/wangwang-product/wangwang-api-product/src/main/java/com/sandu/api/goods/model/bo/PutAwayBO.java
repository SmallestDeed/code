package com.sandu.api.goods.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PutAwayBO implements Serializable
{
    private List<Integer> ids;

    private Integer companyId;

    private Integer userId;
}
