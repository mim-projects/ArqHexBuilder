package ${package};

import ${packageBase}.domain.entities.${className};
import ${packageBase}.domain.repository.${className}Repository;

<#if useJakarta>
import jakarta.ejb.Stateless;
</#if>
<#if !useJakarta>
import javax.ejb.Stateless;
</#if>

@Stateless(name = "<#if module??>${module}_</#if>repository_impl")
public class ${className}RepositoryImpl extends EntityRepositoryImpl<Integer, ${className}> implements ${className}Repository {
    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}