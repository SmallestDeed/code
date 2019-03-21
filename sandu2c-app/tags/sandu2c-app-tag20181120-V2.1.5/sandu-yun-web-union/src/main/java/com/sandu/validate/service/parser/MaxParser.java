package com.sandu.validate.service.parser;



import com.sandu.annotation.Max;
import com.sandu.annotation.Name;
import com.sandu.common.util.RegValidator;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 最大值验证
 *
 */
public class MaxParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {
        ValidateResult result = new ValidateResult();
        if(f.isAnnotationPresent(Max.class)){
            Max max = f.getAnnotation(Max.class);
            Integer m = max.value();
            if(value != null && !value.equals("")){
                Double v = 0d;
                if(RegValidator.isNumeric(value.toString())){
                    v = (Double)value;
                }

                if(v > m){
                    Name name = f.getAnnotation(Name.class);
                    String nameValue = name==null?f.getName() : name.value();
                    result.setFieldName(f.getName());
                    result.setMessage(nameValue + "不能大于"+m);
                }
            }

        }
        return result;
    }

}
