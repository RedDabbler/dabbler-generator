package com.dabbler.generator.generator.sql.model;

import com.google.common.base.Joiner;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public abstract class SQL {

    private SQLTypeEnum type;
    private List<SelectField> selectFieldList;

    private FromTable fromTable;

    private Where where;




    protected String generateSqlStr(){
        String selectFieldStr = Joiner.on(SQLConstant.SEPARATOR_COMMA).skipNulls().join(selectFieldList);
        if (StringUtils.isBlank(selectFieldStr)){
            return StringUtils.EMPTY;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(selectFieldStr)
                .append(SQLConstant.SQL_FROM).append(fromTable.getTableName()).append(SQLConstant.WHITE_SPACE).append(fromTable.getAliasName());
        stringBuilder.append(where.toString());

        return stringBuilder.toString();
    }


}
