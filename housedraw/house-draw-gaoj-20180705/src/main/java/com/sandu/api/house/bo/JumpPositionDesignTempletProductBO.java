package com.sandu.api.house.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class JumpPositionDesignTempletProductBO implements Serializable {
    private static final long serialVersionUID = -4748113795571335227L;
    private Long drawDtpId;
    private Long drawTempletId;
    private Long designTempletId;
    private String uniqueId;
    private String proType;
    private String proPosition;
}
