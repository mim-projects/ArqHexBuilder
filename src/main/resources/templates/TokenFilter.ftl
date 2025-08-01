package ${package};

import ${packageBase}.configuration.Configuration;
import ${packageBase}.entrypoint.api.annotations.UseTokenMiddleware;

<#if useJakarta>
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
</#if>
<#if !useJakarta>
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
</#if>

@Provider
@UseTokenMiddleware
@Priority(Priorities.AUTHENTICATION)
public class TokenFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String headerValue = containerRequestContext.getHeaderString("Authorization");

        if (headerValue == null || !headerValue.equals(Configuration.API_KEY_FOR_CLIENTS)) {
            containerRequestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid token")
                    .build());
        }
    }
}