package ${package};

<#if importDate>
import java.util.Date;

</#if>
public class ${className}Dto {
<#list columns as col>
    <#if !col.foreignKey>
    private ${col.type} ${col.name};
    </#if>
    <#if col.foreignKey>
    private ${col.foreignKeyReferenceTable}Dto ${col.name};
    </#if>
</#list>

    public ${className}Dto() {
    }
<#list columns as col>

    <#if !col.foreignKey>
    public void set${col.nameSetter}(${col.type} ${col.name}) {
        this.${col.name} = ${col.name};
    }

    public ${col.type} get${col.nameGetter}() {
        return this.${col.name};
    }
    </#if>
    <#if col.foreignKey>
    public void set${col.nameSetter}(${col.foreignKeyReferenceTable}Dto ${col.name}) {
        this.${col.name} = ${col.name};
    }

    public ${col.foreignKeyReferenceTable}Dto get${col.nameGetter}() {
        return this.${col.name};
    }
    </#if>
</#list>
}