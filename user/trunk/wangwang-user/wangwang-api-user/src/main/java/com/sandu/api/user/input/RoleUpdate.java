package com.sandu.api.user.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class RoleUpdate implements Serializable {
    Integer userId;
    String roleIds;
}
