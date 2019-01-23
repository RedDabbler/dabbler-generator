package com.dabbler.generator.common.utils;

import org.apache.commons.lang3.text.WordUtils;

public class StringHelper {

    private StringHelper(){
        throw new UnsupportedOperationException();
    }


    public static String captial(String str){
        return WordUtils.capitalize(str,'_');
    }


}
