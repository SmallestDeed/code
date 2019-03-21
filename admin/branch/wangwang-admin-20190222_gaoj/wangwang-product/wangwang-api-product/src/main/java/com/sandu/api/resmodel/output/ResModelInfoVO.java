package com.sandu.api.resmodel.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.sandu.api.resmodel.model.ModelAreaRel;

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
    
    @ApiModelProperty("模型区域关系")
    private List<ModelAreaRel> reList;


}
