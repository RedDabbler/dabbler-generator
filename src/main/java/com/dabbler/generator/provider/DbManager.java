package com.dabbler.generator.provider;

import com.dabbler.generator.common.utils.JDBCUtils;
import com.dabbler.generator.entity.db.Column;
import com.dabbler.generator.entity.db.PrimaryKey;
import com.dabbler.generator.entity.db.Table;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
public class DbManager {
    private static String driverClass="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
    private static String userName="root";
    private static String password="root";
    private static final String MYSQL_ALL_MATCH_PATTERN ="%";

    // TODO 数据库连接池
    public static Connection getConnect(){
        return JDBCUtils.getConnect(driverClass,url,userName,password);
    }
    public static DatabaseMetaData getDatabaseMetaData(Connection connection){
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            log.error("occur SQLException ",e);
            e.printStackTrace();
        }
        return null;
    }
    public static List<Table> getAllTables(DatabaseMetaData databaseMetaData)throws SQLException{
        List<Table> tables = Lists.newArrayList();
        ResultSet resultSet = databaseMetaData.getTables(null,null,MYSQL_ALL_MATCH_PATTERN,null);
        while(resultSet.next()){
            Table table = new Table();
            String tableName = resultSet.getString("TABLE_NAME");
            String tableComment = resultSet.getString("REMARKS");
            String tableType = resultSet.getString("TABLE_TYPE");
            PrimaryKey primaryKey = getPrimaryKey(databaseMetaData,tableName);
            table.setPrimaryKey(primaryKey);
            List<Column> columns = getAllColumn(databaseMetaData,tableName,primaryKey);
            table.setTableComment(tableComment);
            table.setTableName(tableName);
            table.setTableType(tableType);
            table.setColumnList(columns);
            tables.add(table);
        }
        return tables;
    }



    /**
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static List<Column> getAllColumn(DatabaseMetaData databaseMetaData,String tableName,PrimaryKey primaryKey)throws SQLException{
        List<Column> columns = Lists.newArrayList();
        ResultSet resultSet = databaseMetaData.getColumns(null,null,tableName,MYSQL_ALL_MATCH_PATTERN);
        while (resultSet.next()){
            Column column = new Column();
            column.setTableName(resultSet.getString("TABLE_NAME"));
            column.setColumnName(resultSet.getString("COLUMN_NAME"));
            column.setDataType(resultSet.getInt("DATA_TYPE"));
            column.setTypeName(resultSet.getString("TYPE_NAME"));
            column.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            column.setColumnComment(resultSet.getString("REMARKS"));
            column.setNotNull(resultSet.getString("IS_NULLABLE"));
            column.setPrimary(primaryKey);
            columns.add(column);
        }
        return columns;
    }

    public static PrimaryKey getPrimaryKey(DatabaseMetaData databaseMetaData,String tableName)throws SQLException{
        ResultSet resultSet = databaseMetaData.getPrimaryKeys(null,null,tableName);
        List<PrimaryKey> primaryKeys = Lists.newArrayList();
        while (resultSet.next()){
            PrimaryKey primaryKey = new PrimaryKey();
            primaryKey.setTableName(resultSet.getString("TABLE_NAME"));
            primaryKey.setColumnName(resultSet.getString("COLUMN_NAME"));
            primaryKey.setKeySeq(resultSet.getShort("KEY_SEQ"));
            primaryKey.setPkName(resultSet.getString("PK_NAME"));
            primaryKeys.add(primaryKey);
        }
        if (primaryKeys.size()>1){
            log.warn("the primary key is composed key, it does not support");
            throw new UnsupportedOperationException("composite primary key");
        }
        if (primaryKeys.size()==0){
            log.warn("notice table:{} has no primary key",tableName);
            return null;
        }
        return primaryKeys.get(0);
    }





}
