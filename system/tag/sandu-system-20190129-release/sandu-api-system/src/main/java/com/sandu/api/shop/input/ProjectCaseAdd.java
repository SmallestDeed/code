package com.sandu.api.shop.input;

import com.sandu.api.shop.model.ProjectCase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 新增企业店铺
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Data
public class ProjectCaseAdd implements Serializable {

    @ApiModelProperty(value = "店铺ID", required = true)
    @NotNull(message = "店铺Id不能为空")
    private Integer shopId;

    @ApiModelProperty(value = "案例标题", required = true)
    @NotBlank(message = "案例标题不能为空")
    @Size(min = 1, max = 30, message = "案例标题长度度限{min}-{max}个字符")
    private String caseTitle;

    @ApiModelProperty(value = "案例内容")
    private String content;

    @ApiModelProperty(value = "图片IDs")
    private String picIds;

    @ApiModelProperty(value = "店铺类型")
    private Integer shopType;



    /**
     * 转换model对象
     *
     * @param caseAdd
     * @return
     */
    public ProjectCase getProjectCaseFromProjectCaseAdd(ProjectCaseAdd caseAdd) {
        ProjectCase projectCase = new ProjectCase();
        if (caseAdd != null) {
            projectCase.setCaseTitle(caseAdd.getCaseTitle());
            projectCase.setContent(caseAdd.getContent());
            projectCase.setShopId(caseAdd.getShopId());
            projectCase.setPicIds(caseAdd.getPicIds());
        }
        return projectCase;
    }

}
