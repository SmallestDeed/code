package com.sandu.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxCommentPraiseRecord implements Serializable{
    private Long id;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 用户id
     */
    private Long userId;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

}