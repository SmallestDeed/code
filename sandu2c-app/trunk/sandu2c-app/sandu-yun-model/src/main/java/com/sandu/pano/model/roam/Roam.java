package com.sandu.pano.model.roam;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class Roam implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 下标 **/
    private int index;
    /** 位置 **/
    private Position position;
    /** 轴位 **/
    private Rotation rotation;
    /** 720原图ID **/
    private int fieldName;
    /** 720原图地址 **/
    private String fieldNamePath;
    /** 720热点坐标集合 **/
    private List<Coordinate> coordinateList;

    public String getFieldNamePath() {
        return fieldNamePath;
    }

    public void setFieldNamePath(String fieldNamePath) {
        this.fieldNamePath = fieldNamePath;
    }




    public List<Coordinate> getCoordinateList() {
        return coordinateList;
    }

    public void setCoordinateList(List<Coordinate> coordinateList) {
        this.coordinateList = coordinateList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public int getFieldName() {
        return fieldName;
    }

    public void setFieldName(int fieldName) {
        this.fieldName = fieldName;
    }
}
