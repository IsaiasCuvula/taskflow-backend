package com.bersyte.resources;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.UpdateTaskLinkDto;
import com.bersyte.services.TaskLinkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/task-links")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskLinkResource {

    private final TaskLinkService taskLinkService;

    public TaskLinkResource(TaskLinkService taskLinkService) {
        this.taskLinkService = taskLinkService;
    }

    @PUT
    @Path ("/update/{id}")
    public Response updateTaskLink(
            @NotNull @Valid UpdateTaskLinkDto dto,
            @PathParam("id") String taskLinkId
    ) {
        return Response.ok (taskLinkService.updateTaskLink (dto, taskLinkId)).build ();
    }

    @DELETE
    @Path ("/delete/{id}")
    public Response deleteTaskLinkById(@Valid @PathParam ("id") String taskLinkId) {
        return Response.ok (taskLinkService.deleteTaskLinkById (taskLinkId)).build ();
    }

    @GET
    @Path ("/{id}")
    public Response getTaskLinkById(@Valid @PathParam ("id") String taskLinkId) {
        return Response.ok (taskLinkService.getTaskLinkById (taskLinkId)).build ();
    }

    @POST
    @Path ("/create/{id}")
    public Response createTaskLink(
            @NotNull @Valid CreateTaskLinkDto dto,
            @Valid @PathParam ("id") String taskId
    ) {
        return Response.ok (taskLinkService.createTaskLink (dto,taskId)).build ();
    }
}
