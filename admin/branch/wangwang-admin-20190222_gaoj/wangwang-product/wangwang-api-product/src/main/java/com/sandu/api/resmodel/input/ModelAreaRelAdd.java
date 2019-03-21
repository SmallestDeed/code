package com.sandu.api.resmodel.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ModelAreaRelAdd implements Serializable {
    public static final String MODEL_TYPE_MODEL = "model";
    public static final String MODEL_TYPE_BALL = "ball";
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 模型图片ID
     */
    private Integer picId;
    /**
     * 图片路径
     */
    private String thumbPicPath;
    /**
     * 长
     */
    private Integer length;
    /**
     * 宽
     */
    private Integer width;
    /**
     * 高
     */
    private Integer height;
    /**
     * 模型型号
     */
    private String modelNum;
    /**
     * 分类信息
     */
    private String categoryIds;
    /**
     * 区域信息
     */
    private List<AreaInfo> areas;
    /**
     * 模型小类
     */
    private String smallType;
    /**
     * 硬装产品主模型标志(1:主模型,0:副模型)
     */
    private Integer mainModelFlag;

    @Data
    public static class AreaInfo implements Serializable {
        /**
         * 区域编码
         */
        private String code;
        /**
         * 区域名称
         */
        private String name;
        /**
         * 区域默认材质图片id
         */
        private String texturePicId;
        /**
         * 区域默认材质图片路径
         */
        private String texturePicPath;
        /**
         * 区域材质文件路径
         */
        private String texturePath;
        /**
         * 区域默认高度
         */
        private Integer height;
        /**
         * 区域默认宽度
         */
        private Integer width;
    }

}
