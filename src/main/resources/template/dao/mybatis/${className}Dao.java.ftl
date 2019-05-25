package ${basePackage}.dao.mybatis;


import ${basePackage}.entity.${className};
import java.util.List;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
<#list fieldTypes as fieldType>
    <#if !fieldType?string?starts_with("java.lang")>
import ${fieldType};
    </#if>
</#list>

/**
  * @author ${author!""}
  * @create ${createDate!.now?string("yyyy-MM-dd HH:mm:ss")}
  *
  */
@Repository
public class ${className}Dao {

    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){
        return null;
    }
<#list fieldMetas as field>

    <#if !field.primary>
    public List<${className}> listBy${field.fieldName?cap_first}(${field.fieldType}  ${field.fieldName}){
        return Lists.newArrayList();
    }
    </#if>
</#list>

    public void save(${className} ${className?uncap_first}Save){
        if(${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}()==null){
            insert(${className?uncap_first}Save);
            return;
        }
        ${primaryKeyField.fieldType} id = ${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}();
        ${className} ${className?uncap_first}Po = getById(id);

<#--是否有创建时间，更新时间字段-->


    }

    private void insert(${className} ${className?uncap_first}){
    }


    private void update(String id,${className} ${className?uncap_first}){

    }






}