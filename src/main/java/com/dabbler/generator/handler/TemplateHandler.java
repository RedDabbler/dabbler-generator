package com.dabbler.generator.handler;

import com.dabbler.generator.common.utils.FileHelper;
import com.dabbler.generator.common.utils.FreeMarkerHelper;
import com.dabbler.generator.common.utils.PropertiesUtils;
import com.dabbler.generator.common.utils.StringHelper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemplateHandler {

    public static List<File> getTemplateFiles(String templateDir){
        File file = new File(templateDir);
        List<File> templateFiles = FileHelper.listFiles(file);
        log.info("get templateFiles size:{}", templateFiles.size());
        return templateFiles;
    }

    public static String getOutPutPath(){
        return "f:\\outputs";
    }

    public static String getBasePackageName(){
        return "com.company.sys";
    }

    public static String getTemplateDir(){
        return PropertiesUtils.getClassLoaderPath()+File.separator+"template";
    }

    public static void process(Generator.DataModel dataModel) throws  TemplateException,IOException {
        String templateDir = getTemplateDir();
        File templateDirFile = new File(templateDir);
        List<File> files = getTemplateFiles(templateDir);
        for (File file:files){
            Path relativePath = FileHelper.getRelativePath(file,templateDirFile);
            Template template = FreeMarkerHelper.getTemplate(templateDir,relativePath.toString());
            generateFile(template,dataModel);
        }
    }



    public  static void generateFile(Template template, Generator.DataModel dataModel) throws TemplateException {
        Map dataMap = dataModel.getDataMap();
        String fileOutPutPath = getFilePath(template.getName(),dataMap);

        try {
            FileHelper.createIfNotExists(fileOutPutPath);
        } catch (IOException e) {
            log.error("createIfNotExists file occur IOException",e);
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(fileOutPutPath));
            template.process(dataModel.getDataMap(),fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getFilePath(String templateFileName, Map<String,Object> map){
        String outputPath = getOutPutPath();
      //  int start = templateFileName.lastIndexOf(File.separator)==-1?0:templateFileName.lastIndexOf(File.separator);
        int end = templateFileName.lastIndexOf(".ftl");
        String fileName = StringUtils.substring(templateFileName,0,end);
        fileName = StringHelper.placeHolderMatch(fileName,map);
        String packagePath = getBasePackageName();
        String packageDirPath = packagePath.replaceAll("\\.","\\\\");
        String filePath = outputPath + File.separator + packageDirPath + File.separator + fileName;
        try {
            FileHelper.createIfNotExists(filePath);
        } catch (IOException e) {
            log.error("createIfNotExists occur IOException",e);
        }
        return filePath;
    }
}
