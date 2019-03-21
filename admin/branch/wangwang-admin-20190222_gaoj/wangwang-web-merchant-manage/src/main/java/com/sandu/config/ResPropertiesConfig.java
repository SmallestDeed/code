package com.sandu.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandu
 * @ClassName ResPropertiesConfig
 * @date 2018/11/6
 */
@Component
@Configuration
@PropertySource("classpath:res.properties")
public class ResPropertiesConfig {


	private final String propertiesSuffix = ".upload.path";
	@Autowired
	private Environment env;

	@Bean("resPropertiesMap")
	public Map<String, String> setResProperties() throws Exception {
		Field[] fields = ResPropertiesConstance.class.getFields();
		ResPropertiesConstance constance = new ResPropertiesConstance();
		Map<String, String> result = new HashMap<>(fields.length);
		for (Field field : fields) {
			String key = field.get(constance).toString();
			String property = env.getProperty(key + propertiesSuffix);
			if (StringUtils.isBlank(property)) {
				continue;
			}
			String ret = property.replace("[", "").replace("]", "");
			result.put(key, ret);
		}

		result.forEach((k, v) -> System.out.println(k + "-->" + v));
		System.out.println("config res properties end!!!");
		return result;
	}

}
