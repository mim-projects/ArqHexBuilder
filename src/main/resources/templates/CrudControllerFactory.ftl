package ${package};

import ${packageBase}.application.usecases.UseCaseCrud;
import ${packageBase}.entrypoint.api.annotations.UseTokenMiddleware;

<#if useJakarta>
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
</#if>
<#if !useJakarta>
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
</#if>

public abstract class CrudControllerFactory<TypeId, Dto> {
    protected abstract UseCaseCrud<TypeId, Dto> getService();

    @GET
    @UseTokenMiddleware
    @Path("/find/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") TypeId id) {
        Dto dto = getService().findById(id);
        if (dto == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(dto).build();
    }

    @GET
    @UseTokenMiddleware
    @Path("/find/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(getService().findAll()).build();
    }

    @POST
    @UseTokenMiddleware
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Dto dto) {
        return Response.ok(getService().save(dto)).build();
    }

    @PUT
    @UseTokenMiddleware
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Dto dto) {
        return Response.ok(getService().update(dto)).build();
    }

    @DELETE
    @UseTokenMiddleware
    @Path("/delete/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") TypeId id) {
        Dto dto = getService().findById(id);
        if (dto == null) return Response.status(Response.Status.NOT_FOUND).build();
        getService().delete(dto);
        return Response.ok().build();
    }
}