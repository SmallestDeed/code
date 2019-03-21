package com.sandu.api.applyHouse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseHouseApply  implements Serializable {

    private Integer id;
    /**  申请时间  **/
    private Date applyTime;
    /**  用户id  **/
    private Integer userId;
    /**  图片id  **/
    private Integer picId;
    /**  描述  **/
    private String description;
    /*
     * 处理状态value:
     * 0:未处理
     * 1:已处理
     * 2:无此小区
     * 3:未找到户型资料
     * */
    private Integer status;
    /**  城市信息  **/
    private String cityInfo;
    /**  小区信息  **/
    private String livingInfo;
    /**  系统编码  **/
    private String sysCode;
    /**  创建者  **/
    private String creator;
    /**  创建时间  **/
    private Date gmtCreate;
    /**  修改人  **/
    private String modifier;
    /**  修改时间  **/
    private Date gmtModified;
    /**  是否删除  **/
    private Integer isDeleted;
    /*处理状态Info*/
    private String statusInfo;
    /*用户名*/
    private String username;
    /*图片路径*/
    private String picPath;
    /*小区名称*/
    private String houseName;
    /*小区面积*/
    private String houseArea;

    /**用户手机号*/
    private String mobile;

    /** 联系人电话(在移动端由用户自己填写) **/
    private String contactNumber;

    /**申请6小时候是否需要发送模板消息*/
    private Integer enteringAdviceMsgFlag;

    //小区code
    private String areaCode;
}
