package com.lihp.common;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON工具类
 * @author lihp
 * @date 
 *
 */
public class JSONUtil {
    /**
     * 将Java对象转为JSON字符串
     * 
     * @param obj 要转换的Java对象
     * @param writeNullValue 对象属性值为null时是否还需要输出该属性
     * @return JSON格式的字符串
     */
    public static String toJSONString(Object obj, boolean writeNullValue){
        if(writeNullValue){
            return  JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        } else {
            return  JSON.toJSONString(obj);
        }
    }
    
    /**
     * 将JSON格式的字符串转为Java对象
     * 
     * @param text JSON格式的字符串
     * @param objectClass Java对象的Class
     * @return 转换后的Java对象
     */
    public static <T> T parseObject(String text, Class<T> objectClass){
    	return JSON.parseObject(text, objectClass);
    }
    
    /**
     * 将JSON格式的字符串转为List, List中的对象必须一致.
     * 
     * @param text JSON格式的字符串
     * @param objectClass List中对象的Class
     * @return 转换后的List
     */
    public static <T> List<T> parseArray(String text, Class<T> objectClass){
        List<T> result = JSON.parseArray(text, objectClass);
        return result;
    }
}
