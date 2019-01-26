package com.dabbler.generator.handler;

import com.dabbler.generator.common.utils.BeanHelper;
import com.dabbler.generator.common.utils.FileHelper;
import com.dabbler.generator.common.utils.FreeMarkerHelper;
import com.dabbler.generator.common.utils.PropertiesUtils;
import com.dabbler.generator.entity.db.Column;
import com.dabbler.generator.entity.EntityMeta;
import com.dabbler.generator.entity.FieldMeta;
import com.dabbler.generator.entity.db.PrimaryKey;
import com.dabbler.generator.entity.db.Table;
import com.dabbler.generator.entity.enums.DataTypeMappingEnum;
import com.dabbler.generator.provider.DbManager;
import com.dabbler.generator.util.GeneratorUtils;
import com.google.common.collect.Lists;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Generator {
    private List<Exception> exceptions = Lists.newArrayList();

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
        Connection connection = DbManager.getConnect();
        DatabaseMetaData databaseMetaData = DbManager.getDatabaseMetaData(connection);
        List<Table> tables = DbManager.getAllTables(databaseMetaData);
        for(Table table:tables){
            generateByTable(table);
            log.info("generate template files by table:{} complete ");
        }
    }

    public static String getBasePackageName(){
        return "com.company.sys";
    }

    public void generateByTable(Table table) throws IOException,TemplateException{
        EntityMeta entityMeta = convert(table);
        DataModel dataModel = getDataModel(table,entityMeta);
        TemplateHandler.process(dataModel);
    }

    private DataModel getDataModel(Table table,EntityMeta entityMeta){
        DataModel dataModel = new DataModel();
        Map map = dataModel.getDataMap();
        map.putAll(BeanHelper.descibe(entityMeta));
        map.putAll(BeanHelper.descibe(table));
        String basePackage = getBasePackageName();
        map.put("basePackage",basePackage);
        return dataModel;
    }




    private EntityMeta convert(Table table){
        EntityMeta entityMeta = new EntityMeta();
        List<Column> columns = table.getColumnList();
        List<FieldMeta> fieldMetas = Lists.newArrayList();
        for (Column column:columns){
            FieldMeta fieldMeta = convert(column);
            fieldMetas.add(fieldMeta);
        }
        entityMeta.setFieldMetas(fieldMetas);
        entityMeta.setPrimaryKeyField(getPrimaryKeyField(fieldMetas));
        entityMeta.setClassComment(table.getTableComment());
        String tableName = table.getTableName();
        entityMeta.setClassName(GeneratorUtils.tableToClass(tableName));

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
        return fieldMeta;
    }

    public class DataModel{
        private Map DataMap = new HashMap();
        private String generatorFilePath;

        public Map getDataMap() {
            return DataMap;
        }

        public void setDataMap(Map dataMap) {
            DataMap = dataMap;
        }

        public String getGeneratorFilePath() {
            return generatorFilePath;
        }

        public void setGeneratorFilePath(String generatorFilePath) {
            this.generatorFilePath = generatorFilePath;
        }
    }
}