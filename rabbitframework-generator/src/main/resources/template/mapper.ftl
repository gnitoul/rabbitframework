<#if packageName??>
package ${packageName};
</#if>
<#if (modelPackage??) && (modelPackage != "")>
import ${modelPackage}.${entity.objectName};
</#if>
import com.rabbitframework.dbase.mapping.BaseMapper;
import com.rabbitframework.dbase.annontations.Mapper;
/**
* database table ${entity.tableName} mapper interface
**/
@Mapper
public interface ${entity.objectName}${fileSuffix} extends BaseMapper<${entity.objectName}>{

}
