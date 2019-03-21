package com.sandu.common.util;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class GenericsUtils {
	
    public static Map getClassGenricType(Class clazz) {
    	Map m = new HashMap();
    	Field[] fs = clazz.getDeclaredFields(); // 得到所有的fields  
    	  
    	for(Field f : fs)   
    	{   
    	    Class fieldClazz = f.getType(); // 得到field的class及类型全路径  
    	  
    	    if(fieldClazz.isPrimitive()) {
                continue;  //【1】 //判断是否为基本类型
            }
    	  
    	    if(fieldClazz.getName().startsWith("java.lang")) {
                continue; //getName()返回field的类型全路径；
            }
    	  
    	    if(fieldClazz.isAssignableFrom(List.class)) //【2】  
    	    {   
    	             Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型  
    	  
    	             if(fc == null) {
                         continue;
                     }
    	  
    	          
    	             if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型   
    	            {   
    	                   ParameterizedType pt = (ParameterizedType) fc;  
    	  
    	                   Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。  
    	  
    	                   m.put(f.getName(), genericClazz);  
    	  
    	                   //Map<String, Class> m1 = prepareMap(genericClazz);  
    	  
    	                  // m.putAll(m1);   
    	             }   
    	      }   
    	}
    	return m;
}

	/**    
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.    
     * 如public BookManager extends GenricManager<Book>    
     *    
     * @param clazz The class to introspect    
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined    
     */    
    public static Class getSuperClassGenricType(Class clazz) {    
        return getSuperClassGenricType(clazz, 0);    
    }    
    
    /**    
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.    
     * 如public BookManager extends GenricManager<Book>    
     *    
     * @param clazz clazz The class to introspect    
     * @param index the Index of the generic ddeclaration,start from 0.    
     */    
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {    
    
        Type genType = clazz.getGenericSuperclass();    
    
        if (!(genType instanceof ParameterizedType)) {    
            return Object.class;    
        }    
    
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();    
    
        if (index >= params.length || index < 0) {    
            return Object.class;    
        }    
        if (!(params[index] instanceof Class)) {    
            return Object.class;    
        }    
        return (Class) params[index];    
    }    
    
    
}
