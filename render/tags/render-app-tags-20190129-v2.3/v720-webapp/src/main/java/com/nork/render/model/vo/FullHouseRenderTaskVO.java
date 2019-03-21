package com.nork.render.model.vo;

import java.io.Serializable;

public class FullHouseRenderTaskVO implements Serializable {
    //全屋制作生成的全屋方案
    private Integer fullHousePlanId;
    //全屋制作生成的全屋主任务
    private Integer mainTaskId;
    //当前任务ID
    private Integer curTaskId;
    // 现在redis排队任务数
    private Long redisTaskListSize;

    public FullHouseRenderTaskVO() {
    }

    public FullHouseRenderTaskVO(Integer fullHousePlanId, Integer mainTaskId, Integer curTaskId, Long redisTaskListSize) {
        this.fullHousePlanId = fullHousePlanId;
        this.mainTaskId = mainTaskId;
        this.curTaskId = curTaskId;
        this.redisTaskListSize = redisTaskListSize;
    }

    public Integer getFullHousePlanId() {
        return fullHousePlanId;
    }

    public void setFullHousePlanId(Integer fullHousePlanId) {
        this.fullHousePlanId = fullHousePlanId;
    }

    public Integer getMainTaskId() {
        return mainTaskId;
    }

    public void setMainTaskId(Integer mainTaskId) {
        this.mainTaskId = mainTaskId;
    }

    public Long getRedisTaskListSize() {
        return redisTaskListSize;
    }

    public void setRedisTaskListSize(Long redisTaskListSize) {
        this.redisTaskListSize = redisTaskListSize;
    }


    public Integer getCurTaskId() {
        return curTaskId;
    }

    public void setCurTaskId(Integer curTaskId) {
        this.curTaskId = curTaskId;
    }

    @Override
    public String toString() {
        return "FullHouseRenderTaskVO{" +
                "fullHousePlanId=" + fullHousePlanId +
                ", mainTaskId=" + mainTaskId +
                ", curTaskId=" + curTaskId +
                ", redisTaskListSize=" + redisTaskListSize +
                '}';
    }
}
