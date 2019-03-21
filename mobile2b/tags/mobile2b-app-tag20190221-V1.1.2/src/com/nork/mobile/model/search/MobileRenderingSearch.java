package com.nork.mobile.model.search;/**
 * Created by chenm on 2019/1/22.
 */

import lombok.Data;

import java.util.List;

/**
 * @ClassName MobileRenderingSearch
 * @Description TODO
 * @Author chenm
 * @Date 2019/1/22 21:36
 * @Version 1.0
 **/
@Data
public class MobileRenderingSearch {

    /** 需查询的用户id **/
    private List<Integer> userIdList;
    /** 需查询的平台id **/
    private List<Integer> platformIdList;
    /** 空间类型 **/
    private Integer spaceFunctionId;
    /** 方案/任务来源 (1:移动端渲染生成;2:PC端渲染生成) **/
    private Integer planSourceType;
    /** 方案名称 **/
    private String planName;
    /** 当前用户id **/
    private Integer userId;

    private Integer start;

    private Integer limit;


}
