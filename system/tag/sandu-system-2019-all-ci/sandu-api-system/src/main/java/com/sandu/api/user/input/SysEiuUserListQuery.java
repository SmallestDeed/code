package com.sandu.api.user.input;

import com.sandu.commons.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 用户 内部用户 列表 入参
 * @Date 2018/6/4 0004 17:44
 * @Modified By
 */
@Data
public class SysEiuUserListQuery extends Mapper implements Serializable{

    @ApiModelProperty(value = "企业id",required = true)
    @NotBlank(message = "企业id不能为空")
    private String companyId;

    @ApiModelProperty(value = "账号")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;


}
