package com.nork.home.model;

import java.io.Serializable;

public class MyHouseVO implements Serializable
{
    private Integer houseId;

    private Integer spaceFunctionId;

    private String houseName;

    private String houseSpe;

    private String houseArea;

    private String livingName;

    private String pic;

    public Integer getHouseId()
    {
        return houseId;
    }

    public void setHouseId(Integer houseId)
    {
        this.houseId = houseId;
    }

    public Integer getSpaceFunctionId()
    {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(Integer spaceFunctionId)
    {
        this.spaceFunctionId = spaceFunctionId;
    }

    public String getHouseName()
    {
        return houseName;
    }

    public void setHouseName(String houseName)
    {
        this.houseName = houseName;
    }

    public String getHouseSpe()
    {
        return houseSpe;
    }

    public void setHouseSpe(String houseSpe)
    {
        this.houseSpe = houseSpe;
    }

    public String getHouseArea()
    {
        return houseArea;
    }

    public void setHouseArea(String houseArea)
    {
        this.houseArea = houseArea;
    }

    public String getLivingName()
    {
        return livingName;
    }

    public void setLivingName(String livingName)
    {
        this.livingName = livingName;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }
}
