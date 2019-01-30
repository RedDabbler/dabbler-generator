package com.dabbler.generator.common.utils;

public class PropertiesUtils {

    private PropertiesUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getProjectBootPath(){
        return System.getProperty("user.dir");
    }

    public static String getClassLoaderPath(){
       return  Thread.currentThread().getContextClassLoader().getResource(".").getPath();
    }

    public static String getCurrentClassPath(Class clazz){
        return  clazz.getResource(".").getPath();
    }

    public static void main(String[]args){
        String rootPath = getProjectBootPath();
        System.out.println(rootPath);
        String path =  getClassLoaderPath();
        System.out.println(path);
    }

}
