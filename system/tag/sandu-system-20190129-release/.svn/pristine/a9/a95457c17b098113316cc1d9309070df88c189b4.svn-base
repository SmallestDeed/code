package com.sandu.api.banner.input;

import com.sandu.api.banner.model.ResBannerPic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 广告图片资源修改入参实体
 * @author WangHaiLin
 * @date 2018/5/17  13:53
 */
@Data
public class ResBannerPicUpdate implements Serializable {

    @ApiModelProperty(value = "图片Id", required = true)
    @NotNull(message = "图片Id不能为空")
    private Integer picId;

    @ApiModelProperty(value = "图片编码")
    @Size(min = 1, max = 80, message = "图片编码长度度限{min}-{max}个字符")
    private String code;

    @ApiModelProperty(value = "图片名称")
    @Size(min = 1, max = 80, message = "图片名称长度度限{min}-{max}个字符")
    private String name;

    @ApiModelProperty(value = "图片文件名称")
    @Size(min = 1, max = 80, message = "图片文件名称长度度限{min}-{max}个字符")
    private String fileName;

    @ApiModelProperty(value = "图片类型")
    @Size(min = 1, max = 80, message = "图片类型长度度限{min}-{max}个字符")
    private String type;

    @ApiModelProperty(value = "图片大小")
    private Integer size;

    @ApiModelProperty(value = "图片长")
    @Size(min = 1, max = 80, message = "图片长长度度限{min}-{max}个字符")
    private String weight;

    @ApiModelProperty(value = "图片高")
    @Size(min = 1, max = 80, message = "图片高长度度限{min}-{max}个字符")
    private String high;

    @ApiModelProperty(value = "图片后缀")
    @Size(min = 1, max = 80, message = "图片后缀长度度限{min}-{max}个字符")
    private String suffix;

    @ApiModelProperty(value = "图片级别")
    @Size(min = 1, max = 80, message = "图片级别长度度限{min}-{max}个字符")
    private String level;

    @ApiModelProperty(value = "图片格式")
    @Size(min = 1, max = 80, message = "图片格式长度度限{min}-{max}个字符")
    private String format;

    @ApiModelProperty(value = "图片存储路径", required = true)
    @NotNull(message = "图片存储路径不能为空")
    @Size(min = 1, max = 150, message = "图片存储路径长度度限{min}-{max}个字符")
    private String path;

    @ApiModelProperty(value = "图片描述")
    @Size(min = 1, max = 80, message = "图片描述长度度限{min}-{max}个字符")
    private String desc;

    @ApiModelProperty(value = "系统编码")
    @Size(min = 1, max = 80, message = "系统编码长度度限{min}-{max}个字符")
    private String sysCode;

    @ApiModelProperty(value = "备注")
    @Size(min = 1, max = 80, message = "备注长度度限{min}-{max}个字符")
    private String remark;

    public ResBannerPic getPicFromPicUpdate(ResBannerPic pic,ResBannerPicUpdate update){
        pic.setId(update.getPicId());
        pic.setPicCode(update.getCode());
        pic.setPicName(update.getName());
        pic.setPicFileName(update.getFileName());
        pic.setPicType(update.getType());
        pic.setPicSize(update.getSize());
        pic.setPicWeight(update.getWeight());
        pic.setPicHigh(update.getHigh());
        pic.setPicSuffix(update.getSuffix());
        pic.setPicLevel(update.getLevel());
        pic.setPicFormat(update.getFormat());
        pic.setPicPath(update.getPath());
        pic.setPicDesc(update.getDesc());
        pic.setSysCode(update.getSysCode());
        pic.setRemark(update.getRemark());
        return  pic;
    }
}
