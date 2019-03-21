package com.sandu.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {

    /** 主键ID **/
    private Integer id;
    /** 系统code **/
    private String sysCode;
    /** 创建人 **/
    private String creator;
    /** 创建时间 **/
    private Date gmtCreate;
    /** 修改人 **/
    private String modifier;
    /** 修改时间 **/
    private Date gmtModified;
    /** 是否删除 **/
    private Integer isDeleted;
    /** 备注信息 **/
    private String remark;

    public static void sysSave(BaseModel model, LoginUser loginUser){
        if( model != null ){
            if( loginUser == null ){
                loginUser = new LoginUser();
                loginUser.setLoginName("nologin");
            }

            if( model.getId() == null ){
                model.setCreator(loginUser.getLoginName());
                model.setGmtCreate(new Date());
                model.setIsDeleted(0);
            }

            model.setModifier(loginUser.getLoginName());
            model.setGmtModified(new Date());
        }
    }

}
