package com.sandu.api.banner.output;

import com.sandu.api.banner.model.BaseBannerPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告-位置 输出实体
 * @author WangHaiLin
 * @date 2018/5/16  14:42
 */
@Data
public class BaseBannerPositionVO implements Serializable {

    @ApiModelProperty(value = "主键Id")
    private Integer positionId;

    @ApiModelProperty(value = "位置编码:system:module:page:positon")
    private String positionCode;

    @ApiModelProperty(value = "位置名称")
    private String positionName;

    @ApiModelProperty(value = "备注")
    private String positionRemark;

    @ApiModelProperty(value = "位置类型")
    private Integer positionType;

    @ApiModelProperty(value = "系统编码")
    private String positionSysCode;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;

    //数据转换，BaseBannerPosition转BaseBannerPositionVO
    public BaseBannerPositionVO getPositionVOFromPosition(BaseBannerPosition position){
        BaseBannerPositionVO positionVO=new BaseBannerPositionVO();
        positionVO.setPositionId(position.getId());
        positionVO.setPositionCode(position.getCode());
        positionVO.setPositionName(position.getName());
        positionVO.setPositionRemark(position.getRemark());
        positionVO.setPositionType(position.getType());
        positionVO.setPositionSysCode(position.getSysCode());
        positionVO.setCreator(position.getCreator());
        positionVO.setGmtCreate(position.getGmtCreate());
        positionVO.setModifier(position.getModifier());
        positionVO.setGmtModified(position.getGmtModified());
        positionVO.setIsDeleted(position.getIsDeleted());
        return positionVO;
    }

}
