package com.sandu.api.goods.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class PicVO implements Serializable
{
    private Integer picId;

    private String picPath;

    public PicVO(){super();}

    public PicVO(Integer picId, String picPath)
    {
        super();
        this.picId = picId;
        this.picPath = picPath;
    }
}
