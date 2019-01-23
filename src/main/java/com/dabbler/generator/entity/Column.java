package com.dabbler.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    private String tableName;
    private String columnName;
    private String columnComment;
    //
    private int dataType;
    private int columnSize;
    private boolean primary;
    private boolean notNull;

}
