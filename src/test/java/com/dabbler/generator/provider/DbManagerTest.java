package com.dabbler.generator.provider;

import com.dabbler.generator.entity.db.Column;
import com.dabbler.generator.entity.db.PrimaryKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.util.List;

import static com.dabbler.generator.provider.DbManager.*;

@Slf4j
public class DbManagerTest {
    DatabaseMetaData databaseMetaData ;

    @Before
    public void getConnectT() throws Exception{
        databaseMetaData = getDatabaseMetaData(getConnect());
    }

    @Test
    public void getAllTablesTest()throws Exception{
    }

    @Test
    public void getAllColumnTest() throws Exception{
        PrimaryKey primaryKey = getPrimaryKey(databaseMetaData,"test");
        List<Column> columnList = getAllColumn(databaseMetaData,"test",primaryKey);
    }

    @Test
    public void getPrimaryKeyTest() throws Exception{
        PrimaryKey primaryKey = getPrimaryKey(databaseMetaData,"test");
        log.info(primaryKey.toString());
    }
}