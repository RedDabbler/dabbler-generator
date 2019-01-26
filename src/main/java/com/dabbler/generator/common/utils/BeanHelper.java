package com.dabbler.generator.common.utils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanHelper {
    private BeanHelper(){
        throw new UnsupportedOperationException();
    }

    public static Map<String,Object> descibe(Object object){
        if (object instanceof Map){
            return (Map)object;
        }
        Map map = new HashMap();
        PropertyDescriptor[] descriptors = getPropertyDescriptors(object.getClass());
        for (PropertyDescriptor descriptor:descriptors){
            String name = descriptor.getName();
            Method method = descriptor.getReadMethod();
            if (method!=null){
                try {
                    Object value = method.invoke(object);
                    map.put(name,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException e) {
            return (new PropertyDescriptor[0]);
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null) {
            descriptors = new PropertyDescriptor[0];
        }
        return descriptors;
    }
}
