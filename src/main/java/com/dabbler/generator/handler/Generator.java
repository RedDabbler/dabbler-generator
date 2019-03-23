package com.dabbler.generator.handler;

import com.dabbler.generator.common.utils.BeanHelper;
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
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

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
        try{
            List<Table> tables = DbManager.getAllTables(databaseMetaData);
            for(Table table:tables){
                generateByTable(table);
                log.info("generate template files by table:{} complete ");
            }
        }catch (Exception e){
            exceptions.add(e);
        }finally {
            log.info("generate complete, has {} exceptions",exceptions.size());
        }

    }

    public static String getBasePackageName(){
        return  ContextHolder.getProperties().getProperty("package");
    }

    public void generateByTable(Table table) throws IOException,TemplateException{
        EntityMeta entityMeta = convert(table);
        DataModel dataModel = getDataModel(table,entityMeta);
        TemplateHandler.process(dataModel);
    }

    public static String getAuthor(){
        return ContextHolder.getProperties().getProperty("author");
    }
    public static String getModule(){
        return  ContextHolder.getProperties().getProperty("module");
    }
    private DataModel getDataModel(Table table,EntityMeta entityMeta){
        DataModel dataModel = new DataModel();
        Map map = dataModel.getDataMap();
        map.putAll(BeanHelper.descibe(entityMeta));
        map.putAll(BeanHelper.descibe(table));
        String basePackage = getBasePackageName();
        map.put("basePackage",basePackage);
        map.put("createDate",new Date());
        map.put("author",getAuthor());
        map.put("moduleName",getModule());
        map.put("packagingType","jar");
        map.put("groupId","com.dabbler.template");
        map.put("artifactId","dabbler-template");
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
        entityMeta.setClassName(GeneratorUtils.tableToClass(tableName));
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
        fieldMeta.setFieldName(GeneratorUtils.columnToField(column.getColumnName()));
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
        private Map DataMap = new HashMap();

        public Map getDataMap() {
            return DataMap;
        }

    }
}
