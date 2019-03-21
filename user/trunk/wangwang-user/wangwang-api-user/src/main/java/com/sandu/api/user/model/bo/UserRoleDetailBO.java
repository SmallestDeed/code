package com.sandu.api.user.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserRoleDetailBO implements Serializable {
    Integer platformId;
    String platformName;
    String roleIds;
    String roleNames;
}
