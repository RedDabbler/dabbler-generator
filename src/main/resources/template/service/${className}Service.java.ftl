import ${basePackage}.entity.${className};
import ${basePackage}.dao.${className}Dao;
package ${basePackage}.service;

@Service
public class ${className}Service{

    @Autowired
    private ${className}Dao ${className?uncap_first}Dao;

    public ${className} getById(${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){
        return  ${className?uncap_first}Dao.getById(${primaryKeyField.fieldName});
    }

    public void save(${className} ${className?uncap_first}Save){
           ${className?uncap_first}Dao.save(${className?uncap_first}Save);
    }

}