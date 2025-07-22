package ${package};

import ${packageBase}.domain.entities.${className};
import ${packageBase}.application.dto.${className}Dto;

import java.util.ArrayList;
import java.util.List;

public class ${className}Mapper {
    public static ${className}Dto toDto(${className} entity) {
        if (entity == null) return null;
        ${className}Dto dto = new ${className}Dto();
        <#list columns as col>
        <#if !col.foreignKey>
        dto.set${col.nameSetter}(entity.get${col.nameGetter}());
        </#if>
        <#if col.foreignKey>
        dto.set${col.nameSetter}(${col.foreignKeyReferenceTable}Mapper.toDto(entity.get${col.nameGetter}()));
        </#if>
        </#list>
        return dto;
    }

    public static ${className} toEntity(${className}Dto dto) {
        if (dto == null) return null;
        ${className} entity = new ${className}();
        <#list columns as col>
        <#if !col.foreignKey>
        entity.set${col.nameSetter}(dto.get${col.nameGetter}());
        </#if>
        <#if col.foreignKey>
        entity.set${col.nameSetter}(${col.foreignKeyReferenceTable}Mapper.toEntity(dto.get${col.nameGetter}()));
        </#if>
        </#list>
        return entity;
    }

    public static List<${className}Dto> toDtoList(List<${className}> data) {
        List<${className}Dto> list = new ArrayList<>();
        for (${className} item : data) list.add(toDto(item));
        return list;
    }

    public static List<${className}> toEntityList(List<${className}Dto> data) {
        List<${className}> list = new ArrayList<>();
        for (${className}Dto item : data) list.add(toEntity(item));
        return list;
    }
}