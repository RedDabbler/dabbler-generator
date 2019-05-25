package ${basePackage}.dao;

import ${basePackage}.entity.${className};
import java.util.List;
import java.util.Map;
<#list fieldTypes as fieldType>
    <#if !fieldType?string?starts_with("java.lang")>
import ${fieldType};
    </#if>
</#list>

/**
  * @author ${author!""}
  * @create ${createDate!.now?string("yyyy-MM-dd HH:mm:ss")}
  * DAO 如果只有一个实现类的话，没有必要抽象接口，否则，会给未来的扩展带来麻烦，可以将接口删掉，因此没有让实现类去implement
  */

public interface ${className}Dao{

    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName});
<#list fieldMetas as field>

   <#if !field.primary>
    public List<${className}> listBy${field.fieldName?cap_first}(${field.fieldType}  ${field.fieldName});
   </#if>
</#list>

    public void save(${className} ${className?uncap_first}Save);

    public void deleteById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName});

    public void delete(${className} ${className?uncap_first}Save);

    public List<${className}> listAll();

    public List<${className}> getByParam(Map parameter);









}