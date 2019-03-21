package com.sandu.api.springFestivalActivity.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: WxUserCardVo
 * @Auther: gaoj
 * @Date: 2019/1/26 15:11
 * @Description:
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUserCardVo implements Serializable {
    /**
     * 卡片来源类型(0-绑定手机号;1-装修我家;2-产品替换;3-邀请好友)
     **/
    private Byte businessType;
    /**
     * 卡片标识(1-12代表随选网拜大年免费看贺岁片)
     **/
    private Integer cardNumber;
    /**
     * 卡片生成日期
     **/
    private Date cardDate;
    /**
     * 卡片文字
     */
    private String cardWord;
}
