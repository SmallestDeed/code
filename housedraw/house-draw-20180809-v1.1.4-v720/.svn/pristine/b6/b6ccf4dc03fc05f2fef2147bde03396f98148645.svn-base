package com.sandu.api.house.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BakeFailTask {

    private Long id;
    private Long taskId;
    private Date gmtModified;
    private String message;

    public String toString() {
        StringBuilder buf = new StringBuilder();
        return buf.append(taskId).append("\t\t").append(id).append("\t\t\t\t\t")
                .append(gmtModified).append("\t\t\t\t\t").append(message).append("\n").toString();
    }
}
