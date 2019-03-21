package com.sandu.api.base.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NodeDetailStatusAdd implements Serializable {
    @NotNull
    private Integer contentId;
    @NotNull
    private Byte nodeType;
    @NotNull
    private Byte detailType;
    @NotNull
    private Byte status;
    @NotNull
    private Integer blockType;
}
