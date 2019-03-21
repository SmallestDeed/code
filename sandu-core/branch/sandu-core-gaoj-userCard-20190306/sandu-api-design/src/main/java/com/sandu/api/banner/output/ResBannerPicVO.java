package com.sandu.api.banner.output;

import com.sandu.api.banner.model.ResBannerPic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告-图片 输出实体
 * @author WangHaiLin
 * @date 2018/5/16  20:19
 */
@Data
public class ResBannerPicVO implements Serializable {

    @ApiModelProperty(value = "主键Id")
    private Integer picId;

    @ApiModelProperty(value = "图片编码")
    private String code;

    @ApiModelProperty(value = "图片名称")
    private String name;

    @ApiModelProperty(value = "图片文件名称")
    private String fileName;

    @ApiModelProperty(value = "图片类型")
    private String type;

    @ApiModelProperty(value = "图片大小")
    private Integer size;

    @ApiModelProperty(value = "图片长")
    private String weight;

    @ApiModelProperty(value = "图片高")
    private String high;

    @ApiModelProperty(value = "图片后缀")
    private String suffix;

    @ApiModelProperty(value = "图片级别")
    private String level;

    @ApiModelProperty(value = "图片格式")
    private String format;

    @ApiModelProperty(value = "图片存储路径")
    private String path;

    @ApiModelProperty(value = "图片描述")
    private String desc;

    @ApiModelProperty(value = "系统编码")
    private String sysCode;

    @ApiModelProperty(value = "创建者")
    private String Creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新者")
    private String modifier;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "备注")
    private String remark;

    public ResBannerPicVO getPicVoFromPic(ResBannerPic pic){
        ResBannerPicVO picVO=new ResBannerPicVO();
        picVO.setPicId(pic.getId());
        picVO.setCode(pic.getPicCode());
        picVO.setName(pic.getPicName());
        picVO.setType(pic.getPicType());
        picVO.setFileName(pic.getPicFileName());
        picVO.setSize(pic.getPicSize());
        picVO.setWeight(pic.getPicWeight());
        picVO.setHigh(pic.getPicHigh());
        picVO.setSuffix(pic.getPicSuffix());
        picVO.setLevel(pic.getPicLevel());
        picVO.setFormat(pic.getPicFormat());
        picVO.setPath(pic.getPicPath());
        picVO.setDesc(pic.getPicDesc());
        picVO.setSysCode(pic.getSysCode());
        picVO.setCreator(pic.getCreator());
        picVO.setGmtCreate(pic.getGmtCreate());
        picVO.setModifier(pic.getModifier());
        picVO.setGmtModified(pic.getGmtModified());
        picVO.setIsDeleted(pic.getIsDeleted());
        picVO.setRemark(pic.getRemark());
        return picVO;
    }
}
