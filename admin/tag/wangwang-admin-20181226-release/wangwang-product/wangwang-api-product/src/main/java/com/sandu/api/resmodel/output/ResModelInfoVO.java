package com.sandu.api.resmodel.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author bvvy
 * @date 2018/4/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResModelInfoVO implements Serializable {



    /**
     * 模型路径
     */
    @ApiModelProperty("模型文件地址")
    private String modelPath;

    /**
     * 模型区域代码
     */
    @ApiModelProperty("模型区域代码")
    private String codes;


}
