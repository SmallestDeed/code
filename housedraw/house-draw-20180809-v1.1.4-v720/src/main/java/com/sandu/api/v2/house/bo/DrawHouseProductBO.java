package com.sandu.api.v2.house.bo;

import com.sandu.api.house.input.DrawBaseProductDTO;
import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.house.model.DrawDesignTempletProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DrawHouseProductBO {
    DrawBaseProduct drawBaseProduct;
    DrawDesignTempletProduct drawDesignTempletProduct;

    DrawBaseProductDTO drawBaseProductDTO;
}
