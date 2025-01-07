package com.bersyte.resources;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.UpdateTaskLinkDto;
import com.bersyte.services.TaskLinkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource class for managing task links.
 * Provides endpoints for CRUD operations on task links.
 */
@Path("/api/v1/task-links")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskLinkResource {


    private final TaskLinkService taskLinkService;

    /**
     * Constructor to initialize TaskLinkResource with a TaskLinkService instance.
     *
     * @param taskLinkService the service managing task link operations
     */
    public TaskLinkResource(TaskLinkService taskLinkService) {
        this.taskLinkService = taskLinkService;
    }

    /**
     * Updates an existing task link by its ID.
     *
     * @param dto         the task link update details
     * @param taskLinkId  the ID of the task link to update
     * @return a Response containing the updated task link details
     */
    @PUT
    @Path ("/update/{id}")
    public Response updateTaskLink(
            @NotNull @Valid UpdateTaskLinkDto dto,
            @PathParam("id") String taskLinkId
    ) {
        return Response.ok (taskLinkService.updateTaskLink (dto, taskLinkId)).build ();
    }

    /**
     * Deletes a task link by its ID.
     *
     * @param taskLinkId the ID of the task link to delete
     * @return a Response confirming the task link deletion
     */
    @DELETE
    @Path ("/delete/{id}")
    public Response deleteTaskLinkById(@Valid @PathParam ("id") String taskLinkId) {
        return Response.ok (taskLinkService.deleteTaskLinkById (taskLinkId)).build ();
    }

    /**
     * Retrieves a task link by its ID.
     *
     * @param taskLinkId the ID of the task link to retrieve
     * @return a Response containing the task link details
     */
    @GET
    @Path ("/{id}")
    public Response getTaskLinkById(@Valid @PathParam ("id") String taskLinkId) {
        return Response.ok (taskLinkService.getTaskLinkById (taskLinkId)).build ();
    }

    /**
     * Creates a new task link associated with a specific task.
     *
     * @param dto     the task link creation details
     * @param taskId  the ID of the associated task
     * @return a Response containing the created task link details
     */
    @POST
    @Path ("/create/{id}")
    public Response createTaskLink(
            @NotNull @Valid CreateTaskLinkDto dto,
            @Valid @PathParam ("id") String taskId
    ) {
        return Response.ok (taskLinkService.createTaskLink (dto,taskId)).build ();
    }
}
