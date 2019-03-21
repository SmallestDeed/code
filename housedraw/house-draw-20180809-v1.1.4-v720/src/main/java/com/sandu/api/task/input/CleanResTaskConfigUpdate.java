package com.sandu.api.task.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/30
 */

@Setter
@Getter
@ToString
public class CleanResTaskConfigUpdate {
    private Integer limit;
    private Long minFileId;
    private Long maxFileId;
    private Boolean enable;
}
