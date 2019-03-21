package com.sandu.api.task.refresh.model;

import lombok.Data;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 6:27 2018/7/5 0005
 * @Modified By:
 */
@Data
public class FilePathBO {

    /**文件id**/
    private Integer fileId;
    /**文件路径**/
    private String filePath;
    /**样板房id**/
    private Integer templetId;
    /**户型绘制样板房id**/
    private Integer drawTempletId;
    /**产品唯一标识**/
    private String uniqueId;

}
