package com.sandu.validate.service.parser;



import com.sandu.annotation.Name;
import com.sandu.annotation.NotBlank;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 字符串非空验证
 *
 */
public class NotBlankParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {
        ValidateResult result = new ValidateResult();
        if(f.isAnnotationPresent(NotBlank.class)){
            if(value == null || value.toString().length() == 0){
                Name name = f.getAnnotation(Name.class);
                String nameValue = name==null?f.getName() : name.value();
                result.setFieldName(f.getName());
                result.setMessage(nameValue + "不能为空");
            }
        }
        return result;
    }

}
