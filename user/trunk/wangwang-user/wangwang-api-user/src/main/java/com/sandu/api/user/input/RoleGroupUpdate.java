package com.sandu.api.user.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class RoleGroupUpdate implements Serializable {
    Integer userId;
    List<Long> roleGroups;
}
