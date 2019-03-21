package com.sandu.api.fix.model;

import com.sandu.api.house.input.DrawSpaceCommonDTO;
import com.sandu.api.house.model.DrawBaseHouse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class FixCupboardSubmitBO implements Serializable {
    private static final long serialVersionUID = 2484409656321802400L;

    private int dataType;
    private Long houseId;
    private DrawBaseHouse drawHouse;
    private List<DrawSpaceCommonDTO> spaceCommonDTOList;
}
