package com.nork.home.model;

import java.io.Serializable;

public class WxUserTaskState implements Serializable {
    private Byte taskOneStatus;
    private Byte taskTwoStatus;
    private Byte taskThreeStatus;

    public Byte getTaskOneStatus() {
        return taskOneStatus;
    }

    public void setTaskOneStatus(Byte taskOneStatus) {
        this.taskOneStatus = taskOneStatus;
    }

    public Byte getTaskTwoStatus() {
        return taskTwoStatus;
    }

    public void setTaskTwoStatus(Byte taskTwoStatus) {
        this.taskTwoStatus = taskTwoStatus;
    }

    public Byte getTaskThreeStatus() {
        return taskThreeStatus;
    }

    public void setTaskThreeStatus(Byte taskThreeStatus) {
        this.taskThreeStatus = taskThreeStatus;
    }
}
