package com.dabbler.generator.handler;

import com.dabbler.generator.common.utils.FileHelper;
import com.dabbler.generator.common.utils.FreeMarkerHelper;
import com.dabbler.generator.common.utils.StringHelper;
import com.dabbler.generator.entity.Column;
import com.dabbler.generator.entity.EntityMeta;
import com.dabbler.generator.entity.FieldMeta;
import com.dabbler.generator.entity.Table;
import com.dabbler.generator.provider.DbManager;
import com.dabbler.generator.util.GeneratorUtils;
import com.google.common.collect.Lists;
import freemarker.template.Template;
import org.apache.commons.lang3.text.WordUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import sun.nio.cs.Surrogate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generator {

    public static void generate() throws InterruptedException, SQLException, IOException, XMLParserException, InvalidConfigurationException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(Surrogate.Generator.class.getResourceAsStream("/handler/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public void generateByAllTable()throws Exception{
        List<Table> tables = DbManager.getAllTables();
        for(Table table:tables){
            generateByTable(table);
        }
    }

    public String getOutPutPath(){
        return "f:\\output";
    }

    public String getPackageName(){
        return "com.company.sys";
    }

    public String getTemplateDir(){
        return "F:\\GitHome\\dabbler-generator\\src\\main\\resources\\template";
    }

    public void generateByTable(Table table) throws Exception{
        EntityMeta entityMeta = convert(table);
        entityMeta.setPackageName(getPackageName());
        String className = entityMeta.getClassName();
        String packageName = entityMeta.getPackageName();
        String fileOutPutPath = getFilePath(getOutPutPath(),packageName,className+".java");
        FileHelper.createIfNotExists(fileOutPutPath);
        FileWriter fileWriter = new FileWriter(new File(fileOutPutPath));
        Template template = FreeMarkerHelper.getTemplate(getTemplateDir(),"JavaEntity.java.ftl");
        Map map = new HashMap();
        map.put("entityMeta",entityMeta);
        template.process(map,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    private static String getFilePath(String outputPath,String packagePath,String fileName) throws IOException {
        String packageDirPath = packagePath.replaceAll("\\.","\\\\");
        String filePath = outputPath + File.separator + packageDirPath + File.separator + fileName;
        FileHelper.createIfNotExists(filePath);
        return filePath;
    }

    private EntityMeta convert(Table table){
        EntityMeta entityMeta = new EntityMeta();
        List<Column> columns = table.getColumnList();
        List<FieldMeta> fieldMetas = Lists.newArrayList();
        for (Column column:columns){
            FieldMeta fieldMeta = convert(column);
            fieldMetas.add(fieldMeta);
        }
        entityMeta.setClassComment(table.getTableComment());
        String tableName = table.getTableName();
        entityMeta.setClassName(GeneratorUtils.tableToClass(tableName));
        entityMeta.setFieldMetas(fieldMetas);
        return entityMeta;
    }

    private FieldMeta convert(Column column){
        FieldMeta fieldMeta = new FieldMeta();
        fieldMeta.setFieldComment(column.getColumnComment());
        fieldMeta.setFieldName(GeneratorUtils.columnToField(column.getColumnName()));
        fieldMeta.setClassName(GeneratorUtils.tableToClass(column.getTableName()));
        fieldMeta.setFieldType("String");
        return fieldMeta;
    }
}
