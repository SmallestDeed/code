package com.sandu.validate.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: hzl
 * Date: 2017/2/9
 * Time: 15:30
 * Description: 校验返回类
 *
 * @author Administrator
 */
public class ValidateResult implements Serializable {

    private String message;
    private boolean valid = true;
    private String fieldName;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
        valid = false;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isValid(){
        return valid;
    }
}
