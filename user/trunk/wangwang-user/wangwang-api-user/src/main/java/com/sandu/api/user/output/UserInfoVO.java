package com.sandu.api.user.output;

import com.sandu.api.user.model.SysFunc;
import com.sandu.api.user.model.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class UserInfoVO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    private String name;


    private List<SysRole> roleList;

    private List<SysFunc> menuList;

    private List<SysFunc> funcList;

    private List<SysFunc> fieldList;


}
