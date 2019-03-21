package com.sandu.api.banner.input;

import com.sandu.api.banner.model.BaseBannerPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 新增广告位置入参
 * @author WangHaiLin
 * @date 2018/5/16  14:22
 */
@Data
public class BaseBannerPositionAdd implements Serializable {

    @ApiModelProperty(value = "位置编码", required = true)
    @NotNull(message = "位置编码不能为空")
    @Size(min = 1, max = 80, message = "编码长度度限{min}-{max}个字符")
    private String positionCode;

    @ApiModelProperty(value = "位置名称", required = true)
    @NotNull(message = "位置名称不能为空")
    @Size(min = 1, max = 80, message = "位置名称长度度限{min}-{max}个字符")
    private String positionName;

    @ApiModelProperty(value = "备注")
    @Size(min = 1, max = 200, message = "备注长度度限{min}-{max}个字符")
    private String positionRemark;

    @ApiModelProperty(value = "位置类型(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)", required = true)
    @NotNull(message = "位置类型不能为空")
    @Range(min = 1, max = 3, message = "无效状态,限{min}-{max}间整数(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)")
    private Integer positionType;

    @ApiModelProperty(value = "系统编码")
    @Size(min = 1, max = 200, message = "系统编码长度度限{min}-{max}个字符")
    private String sysCode;



    /**
     * 数据转换
     * @param positionAdd  入参
     * @return BaseBannerPosition
     */
    public  BaseBannerPosition getPositionFromPositionAdd(BaseBannerPositionAdd positionAdd){
        BaseBannerPosition position=new BaseBannerPosition();
        position.setCode(positionAdd.positionCode);
        position.setName(positionAdd.positionName);
        position.setType(positionAdd.positionType);
        position.setSysCode(positionAdd.sysCode);
        position.setRemark(positionAdd.positionRemark==null?"":positionAdd.getPositionRemark());
        return position;
    }

}
