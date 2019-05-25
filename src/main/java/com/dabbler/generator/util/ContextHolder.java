package com.dabbler.generator.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Properties;
public class ContextHolder {
    private static Properties properties;
    private static ContextLoader contextLoader = new ContextLoader() ;

    private ContextHolder(){
        throw new UnsupportedOperationException();
    }

    public static Properties getProperties(){
       return properties;
    }

    private static class ContextLoader{

        private ContextLoader(){
            Map<String,String> configMap = XmlUtils.parseGeneratorConfig("config/generator.xml");
            properties = new Properties();
            properties.putAll(configMap);
        }
    }

    public static String getBasePackageName(){
        return  getProperties().getProperty("basePackage");
    }
    public static String getAuthor(){
        return getProperties().getProperty("author");
    }
    public static String getModule(){
        return  getProperties().getProperty("module");
    }

    public static String getOutPutPath(){ return getProperties().getProperty("outputdir");}

    public static Map<String,String> getConfigMap(){
        Map<String,String> resultMap = Maps.newHashMap();
        for(Object key : getProperties().keySet()){
            String value = (String)properties.get(key);
            resultMap.put((String)key,value);
        }
        return resultMap;
    }



}
