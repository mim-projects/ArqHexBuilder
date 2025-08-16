package ${package};

import ${packageBase}.domain.entities.${className};
import ${packageBase}.application.dto.${className}Dto;
import ${packageBase}.application.mapper.${className}Mapper;
import ${packageBase}.application.service.${className}Service;
import ${packageBase}.domain.repository.EntityRepository;
import ${packageBase}.domain.repository.${className}Repository;
import ${packageBase}.infrastructure.adapter.usecases.UseCaseCrudImpl;

<#if useJakarta>
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
</#if>
<#if !useJakarta>
import javax.ejb.Stateless;
import javax.inject.Inject;
</#if>

@Stateless(name = "<#if module??>${module}_</#if>service_impl")
public class ${className}ServiceImpl extends UseCaseCrudImpl<Integer, ${className}, ${className}Dto> implements ${className}Service {
    @Inject
    private ${className}Repository repository;

    @Override
    protected EntityRepository<Integer, ${className}> getRepository() {
        return repository;
    }

    @Override
    protected ${className}Dto toDto(${className} entity) {
        return ${className}Mapper.toDto(entity);
    }

    @Override
    protected ${className} toEntity(${className}Dto dto) {
        return ${className}Mapper.toEntity(dto);
    }
}