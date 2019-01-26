package com.dabbler.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityMeta {
    private String className;
    private String classComment;
//    private String packageName;
    private List<FieldMeta> fieldMetas;
    private  FieldMeta primaryKeyField;

}
