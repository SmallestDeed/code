package com.sandu.designplan.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopInfo implements Serializable {
    private Integer shopId;

    private String logo;
}
