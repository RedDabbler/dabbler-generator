package com.dabbler.generator.provider;

import com.dabbler.generator.entity.Column;
import com.dabbler.generator.entity.Table;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.List;

public class DbManager {
    private static String driverClass="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
    private static String userName="root";
    private static String password="root";

    public static Connection getConnect(){
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String MYSQL_ALLMATCH_PATTERN ="%";
    private static final String TABLE_NAME ="TABLE_NAME";
    public static DatabaseMetaData getDatabaseMetaData() throws SQLException{
        return getConnect().getMetaData();
    }
    public static List<Table> getAllTables()throws SQLException{
        DatabaseMetaData databaseMetaData = getDatabaseMetaData();
        List<Table> tables = Lists.newArrayList();
        ResultSet resultSet = databaseMetaData.getTables(null,null,MYSQL_ALLMATCH_PATTERN,null);
        while(resultSet.next()){
            Table table = new Table();
            String tableName = resultSet.getString(TABLE_NAME);
            String tableComment = resultSet.getString("REMARKS");
            String tableType = resultSet.getString("TABLE_TYPE");
            List<Column> columns = getAllColumn(tableName);
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
    public static List<Column> getAllColumn(String tableName)throws SQLException{
        DatabaseMetaData databaseMetaData = getDatabaseMetaData();
        List<Column> columns = Lists.newArrayList();
        ResultSet resultSet = databaseMetaData.getColumns(null,null,tableName,MYSQL_ALLMATCH_PATTERN);
        while (resultSet.next()){
            Column column = new Column();
            column.setTableName(resultSet.getString("TABLE_NAME"));
            column.setColumnName(resultSet.getString("COLUMN_NAME"));
            column.setDataType(resultSet.getInt("DATA_TYPE"));
            column.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            column.setColumnComment(resultSet.getString("REMARKS"));
            columns.add(column);
        }
        return columns;
    }




}
