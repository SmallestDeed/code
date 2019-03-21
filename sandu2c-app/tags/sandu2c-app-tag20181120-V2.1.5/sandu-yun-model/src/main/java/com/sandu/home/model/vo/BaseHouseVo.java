package com.sandu.home.model.vo;

import com.sandu.common.model.PageModel;
import lombok.Data;

import java.io.Serializable;


/**
 * 户型Vo
 */
@Data
public class BaseHouseVo implements Serializable {

    private Integer id;
    /**
     * 户型全称
     **/
    private String houseName;
    /**
     * 所属小区
     **/
    private Integer livingId;
    /**
     * 小区名称
     **/
    private String livingName;
    /**
     * 房型
     **/
    private String houseTypeStr;
    /**
     * 户型缩略图地址
     **/
    private String thumbnailPath;
    /**
     * 户型缩略图大图
     **/
    private String largeThumbnailPath;
	/**
     * 总面积
     **/
    private String totalArea;
    /**
     * 收藏数
     */
    private Integer favoriteNum;
    /**
     * 是否收藏
     */
    private Integer isFavorite;
    /**
     * 点赞数
     */
    private Integer likeNum;
    /**
     * 是否点赞
     */
    private Integer isLike;
}
