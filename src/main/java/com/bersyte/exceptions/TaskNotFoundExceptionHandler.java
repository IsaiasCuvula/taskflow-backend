package com.bersyte.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.logging.Logger;

/**
 * Exception mapper for handling {@link TaskNotFoundException}.
 * Converts task not found exceptions into a standardized error response.
 */
public class TaskNotFoundExceptionHandler implements ExceptionMapper<TaskNotFoundException> {

    /**
     * Maps a {@link TaskNotFoundException} to an HTTP response.
     *
     * @param e the exception to map
     * @return a {@link Response} with a NOT_FOUND status and error details
     */
    @Override
    public Response toResponse(TaskNotFoundException e) {
        Logger.getLogger(TaskNotFoundExceptionHandler.class.getName())
                .warning("Task not found: " + e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).
                entity(
                        new ErrorMessage(
                                e.getMessage (),
                                Response.Status.NOT_FOUND.name (),
                                Response.Status.NOT_FOUND.getStatusCode ()
                        )
                ).build();
    }
}