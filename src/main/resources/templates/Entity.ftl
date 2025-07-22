package ${package};

import jakarta.persistence.*;

<#if importDate>
import java.util.Date;

</#if>
@Entity
@Table(name = "${tableName}")
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
    @Column(name = "${col.nameReferenceColumn}")
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
}