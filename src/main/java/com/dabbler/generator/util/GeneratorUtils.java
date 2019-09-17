package com.dabbler.generator.util;

import com.redDabbler.template.tools.utils.StringHelper;
import org.apache.commons.lang3.StringUtils;

public class GeneratorUtils {

    private GeneratorUtils(){
        throw new UnsupportedOperationException();
    }

    public static String tableToClass(String tableName){
        String table  = StringHelper.captial(tableName);
        return table.replace("_","");
    }

    public static String columnToField(String columnName){
        String column = StringUtils.uncapitalize(StringHelper.captial(columnName));
        return column.replace("_","");
    }
}
