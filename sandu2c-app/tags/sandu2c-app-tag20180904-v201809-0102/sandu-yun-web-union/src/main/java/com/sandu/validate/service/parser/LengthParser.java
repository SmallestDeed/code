package com.sandu.validate.service.parser;


import com.sandu.annotation.Length;
import com.sandu.annotation.Name;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 长度校验
 */
public class LengthParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {

        ValidateResult result = new ValidateResult();
        if(f.isAnnotationPresent(Length.class)) {
            Length length = f.getAnnotation(Length.class);
            int max = length.max();
            int min = length.min();
            if(value != null && !value.equals("")){
                int len = value.toString().length();
                if(len>max || len<min){
                    Name name = f.getAnnotation(Name.class);
                    String nameValue = name==null?f.getName() : name.value();
                    result.setFieldName(f.getName());
                    result.setMessage(nameValue + "长度必须在"+min+"-"+max+"个字符之内");
                }
            }
        }
        return result;
    }

}
