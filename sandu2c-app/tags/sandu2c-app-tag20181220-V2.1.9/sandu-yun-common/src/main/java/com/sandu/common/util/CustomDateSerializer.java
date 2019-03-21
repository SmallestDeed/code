package com.sandu.common.util;


import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author panda
 * @description 自定义返回JSON 数据格中日期格式化处理
 * @date 2015-5-21
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SimpleDateFormat sdf = null;
        String str = "";
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(value);
        } catch (Exception e) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            str = sdf.format(value);
        }
        gen.writeString(str);
    }
}  