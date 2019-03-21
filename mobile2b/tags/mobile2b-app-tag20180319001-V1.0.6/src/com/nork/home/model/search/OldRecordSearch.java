package com.nork.home.model.search;

import java.io.Serializable;

public class OldRecordSearch implements Serializable
{
    private Integer userId;

    private Integer houseId;

    private Integer platformId;

    private Integer renderType;

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getHouseId()
    {
        return houseId;
    }

    public void setHouseId(Integer houseId)
    {
        this.houseId = houseId;
    }

    public Integer getPlatformId()
    {
        return platformId;
    }

    public void setPlatformId(Integer platformId)
    {
        this.platformId = platformId;
    }

    public Integer getRenderType()
    {
        return renderType;
    }

    public void setRenderType(Integer renderType)
    {
        this.renderType = renderType;
    }
}
