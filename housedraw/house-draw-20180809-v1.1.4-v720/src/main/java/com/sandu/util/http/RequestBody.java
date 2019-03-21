package com.sandu.util.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestBody {
    String data;
    String sign;
    Integer clientType;
    Long timestamp;
}
