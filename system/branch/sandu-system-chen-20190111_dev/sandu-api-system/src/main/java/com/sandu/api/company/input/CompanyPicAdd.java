package com.sandu.api.company.input;

import com.sandu.api.pic.model.ResPic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/6/4 0004 15:06
 * @Modified By
 */
@Data
public class CompanyPicAdd implements Serializable{

    @ApiModelProperty(value = "图片编码")
    @Size(min = 1, max = 80, message = "图片编码长度度限{min}-{max}个字符")
    private String code;

    @ApiModelProperty(value = "图片名称")
    @Size(min = 1, max = 80, message = "图片名称长度度限{min}-{max}个字符")
    private String name;

    @ApiModelProperty(value = "图片类型")
    @Size(min = 1, max = 80, message = "图片类型长度度限{min}-{max}个字符")
    private String type;

    @ApiModelProperty(value = "图片长")
    @Size(min = 1, max = 80, message = "图片长长度度限{min}-{max}个字符")
    private String weight;

    @ApiModelProperty(value = "图片高")
    @Size(min = 1, max = 80, message = "图片高长度度限{min}-{max}个字符")
    private String high;

    @ApiModelProperty(value = "图片级别")
    @Size(min = 1, max = 80, message = "图片级别长度度限{min}-{max}个字符")
    private String level;

    @ApiModelProperty(value = "图片格式")
    @Size(min = 1, max = 80, message = "图片格式长度度限{min}-{max}个字符")
    private String format;

    @ApiModelProperty(value = "图片描述")
    @Size(min = 1, max = 80, message = "图片描述长度度限{min}-{max}个字符")
    private String desc;

    @ApiModelProperty(value = "备注")
    @Size(min = 1, max = 80, message = "备注长度度限{min}-{max}个字符")
    private String remark;

    public ResPic getResPic(){
        ResPic resPic = new ResPic();



        return resPic;
    }
}
