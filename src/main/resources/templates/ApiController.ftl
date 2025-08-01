package ${package};

import ${packageBase}.application.dto.${className}Dto;
import ${packageBase}.application.service.${className}Service;
import ${packageBase}.application.usecases.UseCaseCrud;
import ${packageBase}.entrypoint.api.factory.CrudControllerFactory;

<#if useJakarta>
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
</#if>
<#if !useJakarta>
import javax.inject.Inject;
import javax.ws.rs.Path;
</#if>

@Path("${apiControllerName}")
public class ${className}Controller extends CrudControllerFactory<Integer, ${className}Dto> {
    @Inject
    private ${className}Service ${tableNameStartLowerCase}Service;

    @Override
    protected UseCaseCrud<Integer, ${className}Dto> getService() {
        return ${tableNameStartLowerCase}Service;
    }
}