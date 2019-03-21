package com.sandu.panorama.model.roam;

import com.sandu.panorama.model.output.CoordinateVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Data
public class Roam implements Serializable{

    /** 下标 **/
    private int index;
    /** 位置 **/
    private Position position;
    /** 轴位 **/
    private Rotation rotation;
    /** 720原图ID **/
    private int fieldName;
    /** 720原图路径 **/
    private String fieldResourcePath;
    /** 可行走的热点坐标信息 **/
    private List<CoordinateVo> walkCoordinateList;
    /** 可穿透的热点坐标信息 **/
    private List<CoordinateVo> penetrationCoordinateList;

    public String getRenderPosition(){
        return "{"+
                position.toString() + "," +
                rotation.toString() +
                "}";
    }

}
