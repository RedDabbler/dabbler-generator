package com.dabbler.generator.generator.sql.handler;

import com.google.common.collect.Lists;
import com.dabbler.generator.generator.sql.model.SQL;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class SqlGenerator {

    public static List<String> generatorSimpleSql(List<SQL> sqlList){
        if (CollectionUtils.isEmpty(sqlList)) {
            return Lists.newArrayList();
        }

        List<String> resultList = Lists.newArrayList();
        for (SQL sql : sqlList) {


        }

        return resultList;
    }
}
