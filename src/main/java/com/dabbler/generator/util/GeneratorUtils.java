package com.dabbler.generator.util;

import com.dabbler.generator.common.utils.StringHelper;
import com.dabbler.generator.handler.Generator;
import org.apache.commons.lang3.StringUtils;

public class GeneratorUtils {
    private GeneratorUtils(){
        throw new UnsupportedOperationException();
    }

    public static String tableToClass(String tableName){
        return StringHelper.captial(tableName);
    }

    public static String columnToField(String columnName){
        return StringUtils.uncapitalize(StringHelper.captial(columnName));
    }
}
