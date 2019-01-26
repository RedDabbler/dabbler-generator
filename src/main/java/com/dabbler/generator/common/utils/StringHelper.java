package com.dabbler.generator.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;

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

}
