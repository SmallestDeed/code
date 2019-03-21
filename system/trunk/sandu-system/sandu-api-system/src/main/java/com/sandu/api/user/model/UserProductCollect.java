package com.sandu.api.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 14:26
 * @since 1.8
 */

@Setter
@Getter
@ToString
public class UserProductCollect implements Serializable {
    Integer userId;
    Long spuId;
}
