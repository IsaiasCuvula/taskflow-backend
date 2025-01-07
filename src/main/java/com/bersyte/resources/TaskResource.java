package com.bersyte.resources;

import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.UpdateTaskDto;
import com.bersyte.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @PUT
    @Path ("/update/{id}")
    public Response updateTask(
            @NotNull @Valid UpdateTaskDto dto,
            @PathParam ("id") String taskId
    ) {
        return Response.ok (taskService.updateTask (dto, taskId)).build ();
    }

    @DELETE
    @Path ("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTaskById(@Valid @PathParam ("id") String taskId) {
        return Response.ok (taskService.deleteTaskById (taskId)).build ();
    }

    @GET
    @Path ("/{id}")
    public Response getTaskById(@Valid @PathParam ("id") String taskId) {
        return Response.ok (taskService.getTaskById (taskId)).build ();
    }

    @POST
    @Path ("/create")
    public Response createTask(@NotNull @Valid CreateTaskDto dto) {
        return Response.ok (taskService.createTask (dto)).build ();
    }

    @GET
    public Response getAllTasks(
            @QueryParam("page") @DefaultValue("0") int page,
            @DefaultValue ("10") @QueryParam ("pageSize") int pageSize
    ) {
        return Response.ok (taskService.getAllTasks (page, pageSize)).build ();
    }
}
