package ${basePackage}.controller;

import com.dabbler.template.sys.helper.Result;
import ${basePackage}.service.${className}Service;
import ${basePackage}.entity.${className};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
/**
  * @author ${author!""}
  * @create ${createDate!.now?string("yyyy-MM-dd HH:mm:ss")}
  *
  */
@RestController
@RequestMapping("${module}/${className?uncap_first}")
public class ${className}RestController{
    private static final Logger LOG = LoggerFactory.getLogger(${className}RestController.class);
    @Resource
    private ${className}Service ${className?uncap_first}Service;

    @RequestMapping(value="/info/{${primaryKeyField.fieldName}}",method = RequestMethod.GET)
    public Result info(@PathVariable("${primaryKeyField.fieldName}") ${primaryKeyField.fieldType} ${primaryKeyField.fieldName}){
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.getById(${primaryKeyField.fieldName});
        return Result.build().ok(${className?uncap_first});
    }

    @RequestMapping(value = "/update/{${primaryKeyField.fieldName}}", method = RequestMethod.PUT)
    public Result update(@PathVariable("${primaryKeyField.fieldName}") ${primaryKeyField.fieldType} ${primaryKeyField.fieldName}, @RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.save(${className?uncap_first});
        LOG.info("request /update/{} to update{} success:",${primaryKeyField.fieldName},${className?uncap_first}.toString());
        return Result.build().ok();
    }



}