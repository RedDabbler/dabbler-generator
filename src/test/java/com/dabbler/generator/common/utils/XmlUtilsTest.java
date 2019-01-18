package com.dabbler.generator.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class XmlUtilsTest {

    @Test
    public void modelXmlParse() throws Exception{
        String filePath = "C:\\Users\\Administrator\\Desktop\\Oil.model.xml";
        XmlUtils.modelXmlParse(filePath);
    }
}