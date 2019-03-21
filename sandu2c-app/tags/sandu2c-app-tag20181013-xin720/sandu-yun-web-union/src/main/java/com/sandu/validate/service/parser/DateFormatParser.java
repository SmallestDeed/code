package com.sandu.validate.service.parser;



import com.sandu.annotation.DateFormat;
import com.sandu.annotation.Name;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 时间格式校验
 *
 */
public class DateFormatParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {
        ValidateResult result = new ValidateResult();
        if (f.isAnnotationPresent(DateFormat.class)) {
            DateFormat dateFormat = f.getAnnotation(DateFormat.class);
            Name name = f.getAnnotation(Name.class);
            String nameValue = name == null ? f.getName() : name.value();
            try {
                if (value != null && !value.equals("")) {
                    SimpleDateFormat format = new SimpleDateFormat(dateFormat.value());
                    format.setLenient(false);
                    format.parse(value.toString());
                }
            } catch (ParseException e) {
                result.setFieldName(f.getName());
                result.setMessage(nameValue + "格式必须为：" + dateFormat.value());
            }
        }
        return result;
    }

}
