package com.sandu.web.servicepurchase;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServicesExportBean implements Serializable {

    @ExcelMark(cellName  = "服务编码",cellNum = 0)
    private String servicesCode;

    @ExcelMark(cellName  = "服务名称",cellNum = 1)
    private String servicesName;

    @ExcelMark(cellName  = "服务描述",cellNum = 2)
    private String serviceDesc;

    @ExcelMark(cellName  = "时长" ,cellNum = 3)
    private String duration;

    @ExcelMark(cellName  = "使用状态(0-立即生效;1使用时生效)",cellNum = 4)
    private String status;

    @ExcelMark(cellName  = "账户",cellNum = 5)
    private String nickName;

    @ExcelMark(cellName  = "账户",cellNum = 6)
    private String account;

    @ExcelMark(cellName  = "密码",cellNum = 7)
    private String password;

    @ExcelMark(cellName  = "购买时间",cellNum = 9)
    private Date gmtCreate;

    @ExcelMark(cellName  = "用户类型",cellNum = 8)
    private String userType;
}
