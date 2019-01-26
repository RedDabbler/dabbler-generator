package com.dabbler.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMeta {
    private String className;
    private String fieldName;
    private String columnName;
    private String fieldType;
    private String fieldComment;
    private boolean primary;
    private boolean notNull;
}
