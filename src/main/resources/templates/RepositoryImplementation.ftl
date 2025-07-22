package ${package};

import ${packageBase}.domain.entities.${className};
import ${packageBase}.domain.repository.${className}Repository;
import jakarta.ejb.Stateless;

@Stateless
public class ${className}RepositoryImpl extends EntityRepositoryImpl<Integer, ${className}> implements ${className}Repository {
    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}