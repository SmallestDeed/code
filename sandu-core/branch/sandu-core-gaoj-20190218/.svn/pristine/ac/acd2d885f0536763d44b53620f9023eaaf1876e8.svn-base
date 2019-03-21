package com.sandu.api.springFestivalActivity.model;

import com.sandu.api.user.model.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendTemplateMsg implements Serializable {
    private SysUser user;

    private Integer msgType;

    private Map<String, Map<String, String>> templateData;

    private Object[] pageParams;
}
