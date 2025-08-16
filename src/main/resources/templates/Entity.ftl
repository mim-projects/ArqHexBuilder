package ${package};

<#if useJakarta>
import jakarta.persistence.*;
</#if>
<#if !useJakarta>
import javax.persistence.*;
</#if>

<#if importDate>
import java.util.Date;

</#if>
@Entity
@Table(name = "<#if module??>${module}_</#if>${tableName}")
public class ${className} {
<#list columns as col>
    <#if col.primaryKey>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    <#if !col.foreignKey>
    <#if col.type == "Date">
    @Temporal(TemporalType.TIMESTAMP)
    </#if>
    @Column(name = "${col.nameReferenceColumn}"<#if col.type == "Boolean">, columnDefinition = "tinyint(1) default 0"</#if>)
    private ${col.type} ${col.name};

    </#if>
    <#if col.foreignKey>
    @JoinColumn(name = "${col.foreignKeyColumn}", referencedColumnName = "${col.foreignKeyReferenceColumn}")
    @ManyToOne(optional = false)
    private ${col.foreignKeyReferenceTable} ${col.name};

    </#if>
</#list>
    public ${className}() {
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
    public void set${col.nameSetter}(${col.foreignKeyReferenceTable} ${col.name}) {
        this.${col.name} = ${col.name};
    }

    public ${col.foreignKeyReferenceTable} get${col.nameGetter}() {
        return this.${col.name};
    }
    </#if>
</#list>

    @Override
    public String toString() {
        return "${className}Dto{" +
        <#list columns as col>
            "${col.name}=" + ${col.name}<#if col_has_next> + ", " +</#if>
        </#list>
        + '}';
    }
}