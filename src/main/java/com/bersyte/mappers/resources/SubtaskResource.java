package com.bersyte.mappers.resources;
import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.UpdateSubtaskDto;
import com.bersyte.services.SubtaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/subtasks")
public class SubtaskResource {

    private final SubtaskService subtaskService;

    public SubtaskResource(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @PUT
    @Path ("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubtask(
            @NotNull @Valid UpdateSubtaskDto dto,
            @PathParam("id") String subtaskId
    ) {
        return Response.ok (subtaskService.updateSubtask (dto, subtaskId)).build ();
    }

    @DELETE
    @Path ("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubtaskById(@Valid @PathParam ("id") String subtaskId) {
        return Response.ok (subtaskService.deleteSubtaskById (subtaskId)).build ();
    }

    @GET
    @Path ("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubtaskById(@Valid @PathParam ("id") String subtaskId) {
        return Response.ok (subtaskService.getSubtaskById (subtaskId)).build ();
    }

    @POST
    @Path ("/create/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubtask(
            @NotNull @Valid CreateSubtaskDto dto,
            @Valid @PathParam ("id") String taskId
    ) {
        return Response.ok (subtaskService.createSubtask (dto,taskId)).build ();
    }

    @GET
    @Path ("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubtasksByTaskId(
            @QueryParam("page") @DefaultValue("0") int page,
            @DefaultValue ("10") @QueryParam ("pageSize") int pageSize,
            @Valid @PathParam ("id") String taskId
    ) {
        return Response.ok (
                subtaskService.getAllSubtasksByTaskId (
                        page, pageSize,taskId
                )
        ).build ();
    }
}
