package com.bersyte.resources;

import com.bersyte.dtos.CreateTaskDto;
import com.bersyte.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/tasks")
public class TaskResource {

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @POST
    @Path ("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@NotNull @Valid CreateTaskDto dto) {
        return Response.ok (taskService.createTask (dto)).build ();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks(
            @QueryParam("page") @DefaultValue("0") int page,
            @DefaultValue ("10") @QueryParam ("pageSize") int pageSize
    ) {
        return Response.ok (taskService.getAllTasks (page, pageSize)).build ();
    }
}
