import ${basePackage}.entity.${className};
package ${basePackage}.dao;

@Repository
public class ${className}Dao {


    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){

    }
<#list fieldMetas as field>
   <#if !field.primary>
    public List<${className}> listBy${field.fieldName?cap_first}(${field.fieldType}  ${field.fieldName}){
    }
   </#if>

</#list>
    public void save(${className} ${className?uncap_first}Save){
        if(${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}()==null){
            insert(${className?uncap_first}Save);
            return;
        }
        String id = ${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}();
        ${className} ${className?uncap_first}Po = getById(id);

        <#--是否有创建时间，更新时间字段-->


    }

    private void insert(${className} ${className?uncap_first}){
    }


    private void update(String id,${className} ${className?uncap_first}){

    }






}