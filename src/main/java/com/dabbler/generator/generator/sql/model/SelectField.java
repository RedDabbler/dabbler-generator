package com.dabbler.generator.generator.sql.model;


import lombok.Data;

@Data
public class SelectField {

    private String originName;

    private String aliasName;

    private String tableName;

}
