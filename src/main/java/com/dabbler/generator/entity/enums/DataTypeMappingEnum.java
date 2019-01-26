package com.dabbler.generator.entity.enums;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public enum DataTypeMappingEnum {
    BIT("BIT",Boolean.class),
    SMALLINT("SMALLINT",Short.class),
    BIGINT("BIGINT",Long.class),
    INT("INT",Integer.class),
    VARCHAR("VARCHAR",String.class),
    CHAR("CHAR",String.class),
    LONG("LONG",Long.class),
    FLOAT("float",Double.class),
    REAL("real",Float.class),
    NUMERIC("numeric",BigDecimal.class),
    DECIMAL("decimal",BigDecimal.class),
    DATE("DATE", Date.class),
    DATETIME("DATETIME",Date.class),
    TIME("TIME", Time.class),
    TIMESTAMP("TIMESTAMP", Timestamp.class),
    BLOB("BLOB",String.class),
    CLOB("CLOB",String.class),
    TEXT("TEXT",String.class),
    BINARY("BINARY",byte[].class),
    VARBINARY("VARBINARY",byte[].class);


    private String dbDataType;
    private Class className;

    DataTypeMappingEnum(String dbDataType, Class className) {
        this.dbDataType = dbDataType;
        this.className = className;
    }

    public String getDbDataType() {
        return dbDataType;
    }

    public Class getClassName() {
        return className;
    }

    public static DataTypeMappingEnum getByDbDataType(String dbDataType){
        for(DataTypeMappingEnum dataTypeMappingEnum : DataTypeMappingEnum.values()){
            if(StringUtils.equalsIgnoreCase(dataTypeMappingEnum.getDbDataType(),dbDataType)){
                return dataTypeMappingEnum;
            }
        }
        return null;
    }
}
