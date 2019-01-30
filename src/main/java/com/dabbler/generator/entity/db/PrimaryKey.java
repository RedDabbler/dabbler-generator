package com.dabbler.generator.entity.db;

import lombok.*;

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
