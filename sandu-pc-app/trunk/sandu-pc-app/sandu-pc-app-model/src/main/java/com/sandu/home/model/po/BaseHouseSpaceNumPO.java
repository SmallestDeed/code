package com.sandu.home.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 户型房型类别对应空间数量类
 * 可得到每个户型对应不同房型类别的空间数量
 * Created by chenm on 2018/8/17.
 */
@Data
public class BaseHouseSpaceNumPO implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 户型Id **/
    private Integer houseId;
    /** 空间房型类别 **/
    private Integer spaceFunctionId;
    /** 空间数量 **/
    private Integer spaceNum;
}
