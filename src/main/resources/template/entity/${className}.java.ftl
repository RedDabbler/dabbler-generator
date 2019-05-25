package ${basePackage}.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
<#list fieldTypes as fieldType>
<#if !fieldType?string?starts_with("java.lang")>
import ${fieldType};
</#if>
</#list>

/**
  * @create ${createDate!.now?string("yyyy-MM-dd HH:mm:ss")}
  * @author ${author!}
  * ${(classComment)!}
  */
@Entity
@Table(name = "${tableName}")
public class ${className} implements Serializable {

    <#list fieldMetas as fieldMeta>
    // ${fieldMeta.fieldComment}
    <#if fieldMeta.primary>
    @Id
    </#if>
    @Column(name="${fieldMeta.columnName}" <#if fieldMeta.fieldType=="String">,length = ${fieldMeta.length} <#if fieldMeta.notNull>, nullable = false </#if></#if>)
    private ${fieldMeta.fieldType} ${fieldMeta.fieldName};
    </#list>

    public ${className}(){

    }

    <#list fieldMetas as fieldMeta>
    public ${fieldMeta.fieldType} get${fieldMeta.fieldName?cap_first}(){
        return ${fieldMeta.fieldName};
    }

    public void set${fieldMeta.fieldName?cap_first}(${fieldMeta.fieldType} ${fieldMeta.fieldName}){
        this.${fieldMeta.fieldName} = ${fieldMeta.fieldName};
    }
    </#list>


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("${className}{");
        sb<#list fieldMetas as fieldMeta>
            .append("${fieldMeta.fieldName}='").append(${fieldMeta.fieldName}).append("',")</#list>.append('}');
        return sb.toString();
    }





}