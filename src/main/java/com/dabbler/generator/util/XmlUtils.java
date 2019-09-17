package com.dabbler.generator.util;

import com.dabbler.generator.entity.JavaBean;
import com.dabbler.generator.entity.KeyValuePair;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redDabbler.template.tools.utils.FileHelper;
import com.redDabbler.template.tools.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
public class XmlUtils {

    public static List<JavaBean> modelXmlParse(String filePath) throws Exception{
        InputStream inputStream = FileHelper.getInputStream(filePath);
        return modelXmlParse(inputStream);
    }



    public static Document readXmlAsStream(InputStream inputStream)throws DocumentException{
        Preconditions.checkNotNull(inputStream);
        SAXReader saxReader = new SAXReader();
        saxReader.setValidation(false);
        Document document = saxReader.read(inputStream);
        return document;
    }

    public static List getDocument(Document document){
        Element rootElement = document.getRootElement();
        String rootName = rootElement.getName();
        List<Attribute> attributes = rootElement.attributes();
        return attributes;
    }


    public static List<JavaBean> modelXmlParse(InputStream inputStream) throws DocumentException {
        Preconditions.checkNotNull(inputStream);
        Document document = readXmlAsStream(inputStream);
        String xmlEncoding = document.getXMLEncoding();
        Element rootElement = document.getRootElement();
        List<Element> elements = (List<Element>)rootElement.selectNodes("/*/DataType");
        if(CollectionUtils.isEmpty(elements)){
            return Lists.newArrayList();
        }
        List<JavaBean> javaBeans = Lists.newArrayList();
        for(Element element:elements){
            JavaBean javaBean = new JavaBean();
            javaBeans.add(javaBean);
            String elementName = element.attribute("name").getValue();
            javaBean.setDataTypeName(elementName);
            Element propertyElement = element.element("Property");
            String packagePath = propertyElement.getStringValue();
            javaBean.setPackageName(packagePath);
            List<Element> propertyDefElements = element.elements("PropertyDef");
            List<JavaBean.JavaBeanProperty> javaBeanProperties = Lists.newArrayList();
            javaBean.setPropertyList(javaBeanProperties);
            for(Element propertyDefElement:propertyDefElements){
                JavaBean.JavaBeanProperty javaBeanProperty = new JavaBean.JavaBeanProperty();
                String propertyName = propertyDefElement.attribute("name").getValue();
                javaBeanProperty.setPropertyName(propertyName);
                List<Element> commentElements = (List<Element>)propertyDefElement.selectNodes("Property[@name]");
                for (Element commentElement:commentElements){
                    String attributeValue = commentElement.attributeValue("name");
                    if (StringUtils.equalsIgnoreCase(attributeValue,"label")){
                        javaBeanProperty.setComment(commentElement.getStringValue());
                    }else if(StringUtils.equalsIgnoreCase(attributeValue,"dataType")){
                        javaBeanProperty.setDataType(commentElement.getStringValue());
                    }else if(StringUtils.equalsIgnoreCase(attributeValue,"mapping")){
                        javaBeanProperty.setMappingValues(getKeyValuePair(commentElement));
                    }
                }
                javaBeanProperties.add(javaBeanProperty);
            }
        }
        log.info("parse xml success,get javaBean size:{}",javaBeans.size());
        return javaBeans;
    }

    private static List<KeyValuePair> getKeyValuePair(Element element){
        List<KeyValuePair> keyValuePairs = Lists.newArrayList();
        List<Element> mapEntityElement = element.selectNodes("Property/Collection/Entity");
        for(Element entityElement:mapEntityElement){
            List<Element> keyList = entityElement.selectNodes("Property[@name='key']");
            List<Element> valueList = entityElement.selectNodes("Property[@name='value']");
            if(CollectionUtils.isNotEmpty(keyList)&&CollectionUtils.isNotEmpty(valueList)){
                String key  = keyList.get(0).getStringValue();
                String mapValue =valueList.get(0).getStringValue();
                keyValuePairs.add(new KeyValuePair(key,mapValue));
            }
        }
        return keyValuePairs;
    }

    public static Map<String,String> parseGeneratorConfig(String filePath){
        InputStream inputStream;
        try {
            inputStream = FileHelper.getInputStream(PropertiesUtils.getClassLoaderPath()+filePath);
        }catch (IOException e){
            log.info("occur IOException ",e);
            return Maps.newHashMap();
        }

        Map resultMap = Maps.newHashMap();
        Document document ;
        try {
            document = readXmlAsStream(inputStream);
        } catch (DocumentException e) {
            log.info("occur documentException ",e);
   //         e.printStackTrace();
            return resultMap;
        }

        Element root =  document.getRootElement();
        List<Element> dbConnectNodes = root.selectNodes("DbConnect");
        if(CollectionUtils.isNotEmpty(dbConnectNodes)){
            Element element = dbConnectNodes.get(0);
            resultMap.putAll(parseElement(element));
        }
        resultMap.putAll(parseElement(root));
        return resultMap;

    }

    private static Map<String,String> parseElement(Element element){
        Map resultMap = Maps.newHashMap();
        List<Element> elementList = element.elements();
        for(Element e:elementList){
            resultMap.put(e.getName(),e.getText());
        }
        return resultMap;
    }


}
