package com.sandu.validate.service.parser;


import com.sandu.annotation.Chinese;
import com.sandu.annotation.Phone;
import com.sandu.common.util.RegValidator;
import com.sandu.validate.service.IAnnotationParser;
import com.sandu.validate.vo.ValidateResult;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: weis
 * Date: 2018/8/20
 * Time: 15:26
 * Description: 是否是合法的手机号
 *
 */
public class ChineseParser implements IAnnotationParser {

    @Override
    public ValidateResult validate(Field f, Object value) {
        ValidateResult result = new ValidateResult();
        if(f.isAnnotationPresent(Chinese.class)){
            if(value != null && !value.equals("")){
                if(!RegValidator.isChinese(value.toString())){
                    result.setFieldName(f.getName());
                    result.setMessage(value+"包含非中文字符");
                }
            }
        }
        return result;
    }

}
