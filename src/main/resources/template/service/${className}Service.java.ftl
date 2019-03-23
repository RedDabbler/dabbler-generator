package ${basePackage}.service;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import javax.annotation.*;
import ${basePackage}.entity.${className};
import ${basePackage}.dao.${className}Dao;


/**
  * @author ${author}
  * @create ${createDate?string("yyyy-MM-dd HH:mm:ss")}
  *
  */
@Service
public class ${className}Service{

    @Autowired
    private ${className}Dao ${className?uncap_first}Dao;

    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){
        return ${className?uncap_first}Dao.getById(${primaryKeyField.fieldName});
    }

    public void save(${className} ${className?uncap_first}Save){
        ${className?uncap_first}Dao.save(${className?uncap_first}Save);
    }

    <#list fieldMetas as fieldMeta>
    <#if !fieldMeta.primary>
    public List<${className}> get${className}By${fieldMeta.fieldName?cap_first}(${fieldMeta.fieldType} ${fieldMeta.fieldName}){
        return ${className?uncap_first}Dao.get${className}By${fieldMeta.fieldName?cap_first}(${fieldMeta.fieldName});
    }
    </#if>

    </#list>


}