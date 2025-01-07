package com.bersyte.resources;
import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.UpdateSubtaskDto;
import com.bersyte.services.SubtaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource class for managing subtasks.
 * Provides endpoints for CRUD operations on subtasks.
 */
@Path("/api/v1/subtasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubtaskResource {

    private final SubtaskService subtaskService;

    /**
     * Constructor to initialize SubtaskResource with a SubtaskService instance.
     *
     * @param subtaskService the service managing subtask operations
     */
    public SubtaskResource(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    /**
     * Updates an existing subtask by its ID.
     *
     * @param dto     the subtask update details
     * @param taskId  the ID of the associated task
     * @return a Response containing the updated subtask details
     */
    @PUT
    @Path ("/update/{id}")
    public Response updateSubtask(
            @NotNull @Valid UpdateSubtaskDto dto,
            @PathParam("id") String taskId
    ) {
        return Response.ok (subtaskService.updateSubtask (dto, taskId)).build ();
    }

    /**
     * Deletes a subtask by its ID.
     *
     * @param subtaskId the ID of the subtask to delete
     * @return a Response confirming the subtask deletion
     */
    @DELETE
    @Path ("/delete/{id}")
    public Response deleteSubtaskById(@Valid @PathParam ("id") String subtaskId) {
        return Response.ok (subtaskService.deleteSubtaskById (subtaskId)).build ();
    }

    /**
     * Retrieves a subtask by its ID.
     *
     * @param subtaskId the ID of the subtask to retrieve
     * @return a Response containing the subtask details
     */
    @GET
    @Path ("/{id}")
    public Response getSubtaskById(@Valid @PathParam ("id") String subtaskId) {
        return Response.ok (subtaskService.getSubtaskById (subtaskId)).build ();
    }

    /**
     * Creates a new subtask associated with a specific task.
     *
     * @param dto     the subtask creation details
     * @param taskId  the ID of the associated task
     * @return a Response containing the created subtask details
     */
    @POST
    @Path ("/create/{id}")
    public Response createSubtask(
            @NotNull @Valid CreateSubtaskDto dto,
            @Valid @PathParam ("id") String taskId
    ) {
        return Response.ok (subtaskService.createSubtask (dto,taskId)).build ();
    }
}
