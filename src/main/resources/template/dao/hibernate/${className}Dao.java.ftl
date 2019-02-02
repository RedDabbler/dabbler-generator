package ${basePackage}.dao;

import ${basePackage}.dao.HibernateBaseDao;
import ${basePackage}.entity.${className};
<#list fieldTypes as fieldType>
    <#if !fieldType?string?starts_with("java.lang")>
import ${fieldType};
    </#if>
</#list>

/**
  * @author ${author}
  * @create ${createDate?string("yyyy-MM-dd hh:mm:ss")}
  *
  */
@Repository
public class ${className}Dao extends HibernateBaseDao{

    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){
        return super.selectById(${primaryKeyField.fieldName});
    }
<#list fieldMetas as field>

   <#if !field.primary>
    public List<${className}> listBy${field.fieldName?cap_first}(${field.fieldType}  ${field.fieldName}){
        Criteria criterion = getSession().createCriteria(${className}.class).add(Restrictions.eq("${field.fieldName}", ${field.fieldName}));
        return criterion.list();
    }
   </#if>
</#list>

    public void save(${className} ${className?uncap_first}Save){
        if(${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}()==null){
            insert${className}(${className?uncap_first}Save);
            return;
        }
    //  ${primaryKeyField.fieldType} id = ${className?uncap_first}Save.get${primaryKeyField.fieldName?cap_first}();
    //  ${className} ${className?uncap_first}Po = getById(id);

        updateTest(${className?uncap_first}Save);


    }

    private void insert${className}(${className} ${className?uncap_first}){
         super.insert(${className?uncap_first});
    }

     /**
     * suggest update only by pk
     * @param ${className?uncap_first}
     */
    private void update${className}(${className} ${className?uncap_first}){
        super.update("${primaryKeyField.fieldName}",${className?uncap_first});
    }

    public void deleteById(${primaryKeyField.fieldType}  ${primaryKeyField.fieldName}){
        super.deleteById(${primaryKeyField.fieldName});
    }

    public void deleteByName(String name){
        List<Test> testList =  listByName(name);
    super.deleteAll(testList);
    }

    public void delete${className}(Test test){
        super.delete(test);
    }

    public List<Test> listAll(){
        return  super.findAll();
        }

        public List<Test> getByParam(Map<String,Object> parameter){
            Integer id = (Integer)parameter.get("id");
            String name = (String)parameter.get("name");
            Criteria criterion = getSession().createCriteria(Test.class);
            if (StringUtils.isNotBlank(name)){
            criterion.add(Restrictions.eq("name",name));
            }

            if (id!=null){
            criterion.add(Restrictions.eq("name",name));
            }
            return criterion.list();
            }






}