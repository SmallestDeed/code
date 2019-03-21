package com.sandu.api.base.output;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserCardAccessRecordVo implements Serializable{
    private static final long serialVersionUID = 3561303447422603323L;

    private String accessUserName;

    private String accessUserHeadPicPath;

    private int sex;

    /**
     * 修改时间，自动更新
     */
    private Date gmtModified;

    /**
     * 操作类型:1:获取微信号 2: 获取手机号 3 : 获取邮箱 4 :获取地址 5 :留下联系方式
     */
    private Integer operationType;

    /**
     * 用户留下的手机号
     */
    private String contactPhone;

}