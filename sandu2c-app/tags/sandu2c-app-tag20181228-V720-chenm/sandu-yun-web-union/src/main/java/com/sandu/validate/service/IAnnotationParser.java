package com.sandu.validate.service;


import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: hzl
 * Date: 2017/2/9
 * Time: 15:28
 * Description: 类描述
 */
public interface IAnnotationParser {

    public ValidateResult validate(Field f, Object value);

}
