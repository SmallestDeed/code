package com.sandu.api.base.common.tool;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: WXACodeParams
 * @Auther: gaoj
 * @Date: 2019/1/8 19:50
 * @Description:
 * @Version 1.0
 */
@Data
public class WXACodeParams implements Serializable {

    private String scene;

    private String page;

    private Integer width;

    private boolean auto_color;

    private Object line_color;

    private boolean is_hyaline;
}
