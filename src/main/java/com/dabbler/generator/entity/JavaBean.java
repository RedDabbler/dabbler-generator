package com.dabbler.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JavaBean {
    private String packageName;
    private String dataTypeName;
    private List<JavaBeanProperty> propertyList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JavaBeanProperty{
        private String  propertyName;
        private String comment;
        private String dataType;
        private List<KeyValuePair> mappingValues;

    }
}
