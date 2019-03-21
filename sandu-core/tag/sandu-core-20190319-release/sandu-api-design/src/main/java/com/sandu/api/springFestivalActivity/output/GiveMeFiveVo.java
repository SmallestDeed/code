package com.sandu.api.springFestivalActivity.output;

import com.sandu.api.springFestivalActivity.api.SendTemplateMsgAble;
import com.sandu.api.springFestivalActivity.model.SendTemplateMsg;
import com.sandu.api.user.model.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiveMeFiveVo implements Serializable, SendTemplateMsgAble{
    /**
     * 助力是否成功
     */
    private Boolean success;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 是否获得卡片
     */
    private Byte cardFlag;
    /**
     * 模板消息
     */
    private SendTemplateMsg sendTemplateMsg;

    @Override
    public SendTemplateMsg sendMsg() {
        return this.sendTemplateMsg;
    }
}
