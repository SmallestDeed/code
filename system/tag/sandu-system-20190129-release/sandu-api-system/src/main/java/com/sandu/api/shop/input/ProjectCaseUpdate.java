package com.sandu.api.shop.input;

import com.sandu.api.shop.model.ProjectCase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 新增企业店铺
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Data
public class ProjectCaseUpdate implements Serializable {

    @ApiModelProperty(value = "案例ID", required = true)
    @NotNull(message = "案例Id不能为空")
    private Integer caseId;

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

    @ApiModelProperty(value = "发布状态(1：发布，0：取消发布)")
    private Integer releaseStatus;

    @ApiModelProperty(value = "店铺类型")
    private Integer shopType;

    /**
     * 转换model对象
     *
     * @param caseUpdate
     * @return
     */
    public ProjectCase getProjectCaseFromProjectCaseUpdate(ProjectCaseUpdate caseUpdate) {
        ProjectCase projectCase = new ProjectCase();
        if (caseUpdate != null) {
            projectCase.setId(caseUpdate.getCaseId());
            projectCase.setCaseTitle(caseUpdate.getCaseTitle());
            projectCase.setContent(caseUpdate.getContent());
            projectCase.setShopId(caseUpdate.getShopId());
            projectCase.setPicIds(caseUpdate.getPicIds());
        }
        return projectCase;
    }

}
