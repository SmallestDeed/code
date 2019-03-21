package com.sandu.api.springFestivalActivity.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserInviteRecordVo implements Serializable {
    /**
     * 用户邀请记录表ID
     */
    private Long id;
    /**
     * 被邀请用户昵称
     */
    private String nickName;
    /**
     * 被邀请用户头像
     */
    private String headPic;
    /**
     * 邀请日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date inviteDate;
    /**
     * 是否获得卡片
     */
    private Boolean cardFlag;
    /**
     * 卡片文字
     */
    private String cardWord;
    /**
     * 备注
     */
    private String remark;
}
