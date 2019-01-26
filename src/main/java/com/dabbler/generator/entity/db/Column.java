package com.dabbler.generator.entity.db;

import com.dabbler.generator.entity.db.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    private String tableName;
    private String columnName;
    private String columnComment;
    private int dataType;
    private String typeName;
    private int columnSize;
    // 只支持单主键，不支持联合主键
    private boolean primary = false;
    private boolean notNull = false;

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setNotNull(String nullAble){
        if(StringUtils.equalsIgnoreCase("NO",nullAble)){
            this.notNull = true;
        }
    }

    public void setPrimary(PrimaryKey primarykey){
        if (primarykey==null){
            return;
        }
        String primaryKeyColumn = primarykey.getColumnName();
        if (StringUtils.equalsIgnoreCase(primaryKeyColumn,columnName)){
            this.primary = true;
        }

    }

}
