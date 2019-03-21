package com.sandu.common.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
public class JacksonUtil {
	 private static ObjectMapper mapper = new ObjectMapper();
	    
	    public static String bean2Json(Object obj) throws IOException {
	        StringWriter sw = new StringWriter();
	        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
	        mapper.writeValue(gen, obj);
	        gen.close();
	        return sw.toString();
	    }

	    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
	            throws IOException {
	        return mapper.readValue(jsonStr, objClass);
	    }
}
