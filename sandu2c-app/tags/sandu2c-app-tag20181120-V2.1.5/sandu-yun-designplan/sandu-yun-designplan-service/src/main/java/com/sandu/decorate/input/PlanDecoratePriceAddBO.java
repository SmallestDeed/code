package com.sandu.decorate.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanDecoratePriceAddBO implements Serializable{

    //插入数据集合
    private List<PlanDecoratePriceAdd> addList;

    private String mesId;

}
