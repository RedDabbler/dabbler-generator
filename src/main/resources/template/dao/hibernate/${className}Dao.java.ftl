package ${basePackage}.dao.hibernate;

import org.springframework.stereotype.Repository;
import ${basePackage}.entity.${className};
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import java.util.Map;
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
public class ${className}Dao extends HibernateBaseDao<${className}>{

<#if primaryKeyField??>
    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){
        return super.selectById(${primaryKeyField.fieldName});
    }
</#if>
<#list fieldMetas as field>

   <#if !field.primary>
    public List<${className}> listBy${field.fieldName?cap_first}(${field.fieldType}  ${field.fieldName}){
        Criteria criterion = getSession().createCriteria(${className}.class).add(Restrictions.eq("${field.fieldName}", ${field.fieldName}));
        return criterion.list();
    }
   </#if>
</#list>

    public void save(${className} ${className?uncap_first}Save){
<#if primaryKeyField??>
        if(${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}()==null){
            insert(${className?uncap_first}Save);
            return;
        }
</#if>
        update(${className?uncap_first}Save);
    }
<#if primaryKeyField??>
    public void deleteById(${primaryKeyField.fieldType}  ${primaryKeyField.fieldName}){
        ${className} ${className?uncap_first} = getById(${primaryKeyField.fieldName});
        super.delete(${className?uncap_first});
    }
</#if>
<#list fieldMetas as field>
    <#if !field.primary>
    public void deleteBy${field.fieldName?cap_first}(${field.fieldType}  ${field.fieldName}){
        List<${className}> list =  listBy${field.fieldName?cap_first}(${field.fieldName});
        super.deleteAll(list);
    }
    </#if>
</#list>

    /**
      *  不定参数查询
      */
    public List<${className}> getByParam(Map<String,Object> parameter){
<#list fieldMetas as field>
    <#if !field.primary>
        ${field.fieldType} ${field.fieldName}Param = (${field.fieldType})parameter.get("${field.fieldName}");
    </#if>
</#list>
        Criteria criterion = getSession().createCriteria(${className}.class);

<#list fieldMetas as field>
    <#if !field.primary>
    <#if field.fieldType=="String">
        if (StringUtils.isNotBlank(${field.fieldName}Param)){
            criterion.add(Restrictions.eq("${field.fieldName}",${field.fieldName}Param));
        }
    <#else>
        if (null != ${field.fieldName}Param){
            criterion.add(Restrictions.eq("${field.fieldName}",${field.fieldName}Param));
        }
    </#if>
    </#if>
</#list>
        return criterion.list();
    }






}