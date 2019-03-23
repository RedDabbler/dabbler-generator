package com.dabbler.generator.common.utils;

import com.dabbler.generator.util.XmlUtils;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

@Slf4j
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

    @Test
    public void parseGeneratorConfig() throws Exception{

        String filePath = PropertiesUtils.getClassLoaderPath()+"/config/generator.xml";
        inputStream = FileHelper.getInputStream(filePath);
        Map<String,String> resultMap = XmlUtils.parseGeneratorConfig(filePath);
        log.info(Joiner.on(";").withKeyValueSeparator("=").join(resultMap));
    }


}