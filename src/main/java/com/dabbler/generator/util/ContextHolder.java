package com.dabbler.generator.util;

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


}
