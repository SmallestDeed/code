package com.sandu.api.base.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InteractiveZoneMsgVo implements Serializable {
    private Long id;

    private Long topicId;

    private Long replyId;

    private Integer operateType;

    private Integer blockType;

    private Integer businessType;

    private Integer isRead;

    private String name;

    private String headPic;

    private String content;

    private String myReplyContent;

    private String myProblemContent;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
}
