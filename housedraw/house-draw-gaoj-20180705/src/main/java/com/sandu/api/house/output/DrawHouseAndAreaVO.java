package com.sandu.api.house.output;

import com.sandu.api.house.bo.BaseAreaBO;
import com.sandu.api.house.bo.DrawBaseHouseBO;
import lombok.Data;

@Data
public class DrawHouseAndAreaVO {

    private DrawBaseHouseBO drawBaseHouseBO;

    private BaseAreaBO baseAreaBO;

}
