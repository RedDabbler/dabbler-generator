package com.dabbler.generator.handler.sql.handler;

import com.dabbler.generator.provider.DbManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbManagerTest {

    @Test
    public void getConnect() throws Exception{
       Connection connection =  DbManager.getConnect();
       DatabaseMetaData me = connection.getMetaData();
       ResultSet rs = me.getTables("","","test",null);

       rs.getMetaData();
    }


}