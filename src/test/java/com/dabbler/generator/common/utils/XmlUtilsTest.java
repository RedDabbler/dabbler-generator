package com.dabbler.generator.common.utils;

import com.dabbler.generator.util.XmlUtils;
import org.dom4j.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class XmlUtilsTest {

    InputStream inputStream ;
    @Before
    public void getInputStream()throws Exception{
        String classPath = PropertiesUtils.getClassLoaderPath();
        String filePath = classPath +"/config/generatorConfig.xml";
        inputStream = FileHelper.getInputStream(filePath);
    }

    @Test
    public void modelXmlParse() throws Exception{
        String filePath = "config/generatorConfig.xml";
        XmlUtils.modelXmlParse(filePath);
    }

    @Test
    public void parseAttribute()throws Exception{
        Document document = XmlUtils.readXmlAsStream(inputStream);
        XmlUtils.getDocument(document);

    }


}