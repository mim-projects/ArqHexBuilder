package ${package};

<#if allTablesRender>
import ${packageBase}.entrypoint.api.controllers.base.*;
import ${packageBase}.entrypoint.api.filters.*;

import java.util.Set;
</#if>

public class Configuration {
    public static final String API_KEY_FOR_CLIENTS = "${module}";

    public static final String STORAGE_PATH_FILES = "/opt/wildfly/${module}/";

    <#if allTablesRender>
    public static void AddControllerService(Set<Class<?>> resources) {
        resources.add(TokenFilter.class);
        resources.add(CorsFilter.class);

        <#list allTables as tableName>
        resources.add(${tableName}Controller.class);
        </#list>
    }
    </#if>
}