package com.nork.home.model.search;

import java.io.Serializable;

public class MyHouseSearch implements Serializable
{
    private Integer userId;

    private Integer start = 0;

    private Integer limit = 20;

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getLimit()
    {
        return limit;
    }

    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }
}
