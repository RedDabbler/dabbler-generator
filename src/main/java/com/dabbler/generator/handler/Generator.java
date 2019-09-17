package com.dabbler.generator.handler;

import com.dabbler.generator.entity.Constants;
import com.dabbler.generator.entity.EntityMeta;
import com.dabbler.generator.entity.FieldMeta;
import com.dabbler.generator.entity.db.Column;
import com.dabbler.generator.entity.db.Table;
import com.dabbler.generator.entity.enums.DataTypeMappingEnum;
import com.dabbler.generator.provider.DbManager;
import com.dabbler.generator.util.ContextHolder;
import com.dabbler.generator.util.GeneratorUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.redDabbler.template.tools.utils.BeanHelper;
import com.redDabbler.template.tools.utils.FileHelper;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.*;

@Slf4j
public class Generator {
    private List<Exception> exceptions = Lists.newArrayList();

    public void generateByAllTable(){
        Connection connection = DbManager.getConnect();
        DatabaseMetaData databaseMetaData = DbManager.getDatabaseMetaData(connection);
        List<Table> tables = Lists.newArrayList();
        try{
            tables = DbManager.getAllTables(databaseMetaData);
        }catch (Exception e) {
            exceptions.add(e);
            log.error("generate by all table occur exception:{}", e.getMessage(), e);
        }
        for(Table table:tables){
            log.info("prepare to generate by table:{}",table.getTableName());
            try{
                generateByTable(table);
            }catch (Exception e) {
                exceptions.add(e);
                log.error("generate by all table occur exception:{}", e.getMessage(), e);
            }
        }

        // TODO 打印所有异常
    }

    public void generateByTable(Table table) throws IOException,TemplateException{
        EntityMeta entityMeta = convert(table);
        DataModel dataModel = getDataModel(table,entityMeta);
        generateMavenPath(dataModel);
        TemplateHandler.process(dataModel);
    }

    private static void generateMavenPath(DataModel dataModel){
        String outputPath = ContextHolder.getOutPutPath();
        String modulePath = ContextHolder.getModule();
        String projectPath = outputPath+ File.separator + modulePath + File.separator ;
        dataModel.put("projectPath",projectPath);
        String mainJavaPath = projectPath + Constants.main_java_package + File.separator ;
        dataModel.put("mainJavaPath",mainJavaPath);
        String testJavaPath = projectPath +  Constants.test_java_package + File.separator ;
        dataModel.put("testJavaPath",testJavaPath);
        String mainResourcesPath = projectPath + Constants.main_resources_package + File.separator ;;
        dataModel.put("mainResourcesPath",mainResourcesPath);
        String testResourcesPath = projectPath +  Constants.test_resources_package + File.separator ;
        dataModel.put("testResourcesPath",testResourcesPath);
        try {
            FileHelper.recursionCreateIfNotExists(mainJavaPath,true);
            FileHelper.recursionCreateIfNotExists(testJavaPath,true);
            FileHelper.recursionCreateIfNotExists(mainResourcesPath,true);
            FileHelper.recursionCreateIfNotExists(testResourcesPath,true);
        }catch (Exception e){
            log.error("generate maven path occur exception",e);
        }


    }

    private DataModel getDataModel(Table table,EntityMeta entityMeta){
        DataModel dataModel = new DataModel();
        Map map = dataModel.getDataMap();
        map.putAll(BeanHelper.descibe(entityMeta));
        map.putAll(BeanHelper.descibe(table));
        map.putAll(ContextHolder.getConfigMap());

//        String basePackage = ContextHolder.getBasePackageName();
//        map.put("basePackage",basePackage);
//        map.put("createDate",new Date());
//        map.put("moduleName",ContextHolder.getModule());
//        map.put("packagingType","jar");
//        map.put("groupId","com.dabbler.template");
//        map.put("artifactId","dabbler-template");
        return dataModel;
    }




    private EntityMeta convert(Table table){
        EntityMeta entityMeta = new EntityMeta();
        List<Column> columns = table.getColumnList();
        List<FieldMeta> fieldMetas = Lists.newArrayList();
        Set<String> fieldTypeFullNames = Sets.newHashSet();
        for (Column column:columns){
            FieldMeta fieldMeta = convert(column);
            String fieldTypeFullName = fieldMeta.getFieldTypeFullName();
            fieldTypeFullNames.add(fieldTypeFullName);
            fieldMetas.add(fieldMeta);
        }
        entityMeta.setFieldMetas(fieldMetas);
        entityMeta.setPrimaryKeyField(getPrimaryKeyField(fieldMetas));
        entityMeta.setClassComment(table.getTableComment());
        String tableName = table.getTableName();
        entityMeta.setClassName(GeneratorUtils.tableToClass(tableName.toLowerCase()));
        entityMeta.setFieldTypes(fieldTypeFullNames);

        return entityMeta;
    }

    /**
     * 主键属性，目前只支持单列主键
     * @param fieldMetas
     * @return
     */
    private FieldMeta getPrimaryKeyField(List<FieldMeta> fieldMetas){
        for (FieldMeta fieldMeta:fieldMetas){
            if(fieldMeta.isPrimary()){
                return fieldMeta;
            }
        }
        return null;
    }

    private FieldMeta convert(Column column){
        FieldMeta fieldMeta = new FieldMeta();
        fieldMeta.setFieldComment(column.getColumnComment());
        fieldMeta.setColumnName(column.getColumnName());
        fieldMeta.setFieldName(GeneratorUtils.columnToField(column.getColumnName().toLowerCase()));
        fieldMeta.setPrimary(column.isPrimary());
        fieldMeta.setNotNull(column.isNotNull());
        fieldMeta.setClassName(GeneratorUtils.tableToClass(column.getTableName()));
        String typeName= column.getTypeName();
        DataTypeMappingEnum dataTypeMappingEnum = DataTypeMappingEnum.getByDbDataType(typeName);
        if (dataTypeMappingEnum==null){
            throw new UnsupportedOperationException("不识别的类型"+typeName);
        }
        fieldMeta.setFieldType(dataTypeMappingEnum.getClassName().getSimpleName());
        fieldMeta.setFieldTypeFullName(dataTypeMappingEnum.getClassName().getName());
        return fieldMeta;
    }

    public class DataModel{
        private Map dataMap = new HashMap();

        public Map getDataMap() {
            return dataMap;
        }

        public void put(String key,String value){
            dataMap.put(key,value);
        }

    }
}
