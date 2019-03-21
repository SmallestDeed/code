package com.sandu.validate.service.parser;




import com.sandu.annotation.Min;
import com.sandu.annotation.Name;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 最小值验证
 *
 */
public class MinParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {
        ValidateResult result = new ValidateResult();
        if(f.isAnnotationPresent(Min.class)){
            Min max = f.getAnnotation(Min.class);
            Integer m = max.value();
            if(value != null && !value.equals("")){
                Integer v = (Integer)value;
                if(v == null || v < m){
                    Name name = f.getAnnotation(Name.class);
                    String nameValue = name==null?f.getName() : name.value();
                    result.setFieldName(f.getName());
                    result.setMessage(nameValue + "不能小于"+m);
                }
            }
        }
        return result;
    }

}
