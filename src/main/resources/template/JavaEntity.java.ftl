package ${entityMeta.packageName}
/**
  * ${(entityMeta.classComment)!}
  */
public class ${entityMeta.className}{

    <#list entityMeta.fieldMetas as fieldMeta>
    /**
     * ${fieldMeta.fieldComment}
     */
    private ${fieldMeta.fieldType} ${fieldMeta.fieldName};
    </#list>

    public ${entityMeta.className}(){

    }


    <#list entityMeta.fieldMetas as fieldMeta>

    public ${fieldMeta.fieldType} get${fieldMeta.fieldName?cap_first}(){
        return ${fieldMeta.fieldName};
    }

    public void set${fieldMeta.fieldName?cap_first}(${fieldMeta.fieldType} ${fieldMeta.fieldName}){
        this.${fieldMeta.fieldName} = ${fieldMeta.fieldName};
    }
    </#list>






}