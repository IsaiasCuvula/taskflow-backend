package com.bersyte.resources;

import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.UpdateTaskDto;
import com.bersyte.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource class for managing tasks.
 * Handles CRUD operations for tasks through HTTP endpoints.
 */
@Path("/api/v1/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskService taskService;

    /**
     * Constructor to initialize TaskResource with a TaskService instance.
     *
     * @param taskService the service managing task operations
     */
    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param dto    the task update details
     * @param taskId the ID of the task to update
     * @return a Response containing the updated task details
     */
    @PUT
    @Path ("/update/{id}")
    public Response updateTask(
            @NotNull @Valid UpdateTaskDto dto,
            @PathParam ("id") String taskId
    ) {
        return Response.ok (taskService.updateTask (dto, taskId)).build ();
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete
     * @return a Response confirming the task deletion
     */
    @DELETE
    @Path ("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTaskById(@Valid @PathParam ("id") String taskId) {
        return Response.ok (taskService.deleteTaskById (taskId)).build ();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return a Response containing the task details
     */
    @GET
    @Path ("/{id}")
    public Response getTaskById(@Valid @PathParam ("id") String taskId) {
        return Response.ok (taskService.getTaskById (taskId)).build ();
    }

    /**
     * Creates a new task.
     *
     * @param dto the task creation details
     * @return a Response containing the created task details
     */
    @POST
    @Path ("/create")
    public Response createTask(@NotNull @Valid CreateTaskDto dto) {
        return Response.ok (taskService.createTask (dto)).build ();
    }

    /**
     * Retrieves a paginated list of all tasks.
     *
     * @param page     the page number to retrieve
     * @param pageSize the number of tasks per page
     * @return a Response containing the list of tasks
     */
    @GET
    public Response getAllTasks(
            @QueryParam("page") @DefaultValue("0") int page,
            @DefaultValue ("10") @QueryParam ("pageSize") int pageSize
    ) {
        return Response.ok (taskService.getAllTasks (page, pageSize)).build ();
    }
}
