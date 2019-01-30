package com.dabbler.generator.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;
import java.util.Properties;

@Slf4j
public class StringHelper {

    private StringHelper(){
        throw new UnsupportedOperationException();
    }


    public static String captial(String str){
        return WordUtils.capitalize(str,'_');
    }


    /**
     * ${} 占位符
     * @param str
     * @param map
     * @return
     */
    public static String placeHolderMatch(String str, Map<String,Object> map){
        StringBuilder stringBuilder = new StringBuilder(str);
        while(true) {
            int start = stringBuilder.indexOf("$");
            int begin = stringBuilder.indexOf("{");
            int end = stringBuilder.indexOf("}");
            if (begin == -1 || end == -1) {
                break;
            }
            String startStr = stringBuilder.substring(0, start);
            String placeHolder = stringBuilder.substring(begin + 1, end);
            String endStr = stringBuilder.substring(end + 1);
            String value = (String) map.get(placeHolder);

            stringBuilder = new StringBuilder(startStr).append(value).append(endStr);
        }
        return stringBuilder.toString();
    }

    /** ${key}_${} 根据占位符 的名称获取占位符的值
     *
     */
    public static String placeHolder(String string, Properties properties) {
        String OPEN = "${"; //$NON-NLS-1$
        String CLOSE = "}"; //$NON-NLS-1$
        if(string == null){
            return null;
        }
        StringBuilder stringBuilder  = new StringBuilder(string);
        int start = stringBuilder.indexOf(OPEN);
        int end = stringBuilder.indexOf(CLOSE);

        while (start > -1 && end > start) {
            String prepend = stringBuilder.substring(0, start);
            String append = stringBuilder.substring(end + CLOSE.length());
            String propName = stringBuilder.substring(start + OPEN.length(), end);
            String propValue = properties.getProperty(propName);
            if (propValue != null) {
                stringBuilder = stringBuilder.append(prepend).append(propValue).append(append);
            }
            start = string.indexOf(OPEN, end);
            end = string.indexOf(CLOSE, end);
        }

        return stringBuilder.toString();
    }

}
