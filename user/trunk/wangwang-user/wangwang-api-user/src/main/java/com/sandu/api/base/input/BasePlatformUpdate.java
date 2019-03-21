package com.sandu.api.base.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class BasePlatformUpdate implements Serializable {
    Integer userId;
    List<Long> platforms;
}
