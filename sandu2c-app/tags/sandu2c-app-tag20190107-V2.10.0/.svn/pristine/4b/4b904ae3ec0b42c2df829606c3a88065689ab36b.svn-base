package com.sandu.user.model.input;

import com.sandu.annotation.Name;
import com.sandu.annotation.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MiniProgramShareRecordAdd implements Serializable{
    private static final long serialVersionUID = -2438563368891386359L;


    /**
     * 分享人Id
     */
    @NotBlank()
    @Name("分享人ID")
    private Integer userId;

    /**
     * 分享程序标识
     */
    @NotBlank()
    @Name("分享标识")
    private String shareSign;

    /**
     * 1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博,6:贴吧,7:豆瓣,8:知乎,9:微头条,10:简书
     */
    @NotBlank()
    @Name("分享类型")
    private Integer shareType;


    /**
     * 分享时间
     */
    @NotBlank()
    @Name("分享时间")
    private Date shareTime;



}