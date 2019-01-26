package com.dabbler.generator.entity.db;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrimaryKey {
    private String tableName;
    private String columnName;
    private short keySeq;
    private String pkName;

}
