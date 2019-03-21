package com.sandu.api.banner.input;

import com.sandu.api.banner.model.BaseBannerPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 位置信息修改入参实体
 * @author WangHaiLin
 * @date 2018/5/17  10:46
 */
@Data
public class BaseBannerPositionUpdate implements Serializable {

    @ApiModelProperty(value = "位置Id", required = true)
    @NotNull(message = "位置Id不能为空")
    private Integer positionId;

    @ApiModelProperty(value = "位置编码")
    @Size(min = 1, max = 80, message = "编码长度限{min}-{max}个字符")
    private String positionCode;

    @ApiModelProperty(value = "位置名称")
    @Size(min = 1, max = 80, message = "位置名称长度限{min}-{max}个字符")
    private String positionName;

    @ApiModelProperty(value = "位置备注")
    @Size(min = 1, max = 200, message = "位置备注长度限{min}-{max}个字符")
    private String positionRemark;

    @ApiModelProperty(value = "位置类型(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)")
    @Range(min = 1, max = 3, message = "无效状态,限{min}-{max}间整数")
    private Integer positionType;

    @ApiModelProperty(value = "系统编码")
    @Size(min = 1, max = 200, message = "系统编码长度度限{min}-{max}个字符")
    private String sysCode;


    /**
     * 数据转换
     * @param update  入参
     * @return BaseBannerPosition
     */
    public BaseBannerPosition getPositionFromPositionUpdate(BaseBannerPosition position,BaseBannerPositionUpdate update){
        position.setId(update.positionId);
        position.setCode(update.positionCode);
        position.setName(update.positionName);
        position.setType(update.positionType);
        position.setSysCode(update.sysCode);
        position.setRemark(update.positionRemark);
        return position;
    }
}
