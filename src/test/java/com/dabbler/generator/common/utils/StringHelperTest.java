package com.dabbler.generator.common.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StringHelperTest {

    @Test
    public void placeHolderMatch() {
        Map map = new HashMap();
        map.put("className","test");
        map.put("col","id");
        StringHelper.placeHolderMatch("${className}${col}Dao.java",map);
    }
}