package com.dabbler.generator.common.utils;

public class PropertiesUtils {

    private PropertiesUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getProjectBootPath(){
        return System.getProperty("user.dir");
    }

    public static String getClassPath(){
       return  Thread.currentThread().getContextClassLoader().getResource(".").getPath();
    }

    public static void main(String[]args){
        String rootPath = getProjectBootPath();
        System.out.println(rootPath);
        String path =  getClassPath();
        System.out.println(path);
    }

}
