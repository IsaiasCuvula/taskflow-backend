package com.bersyte.exceptions;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

/**
 * Exception mapper for handling {@link ConstraintViolationException}.
 * Converts constraint violations into a standardized error response.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    /**
     * Maps a {@link ConstraintViolationException} to an HTTP response.
     *
     * @param e the exception to map
     * @return a {@link Response} with a BAD_REQUEST status and error details
     */
    @Override
    public Response toResponse(ConstraintViolationException e) {
        Logger.getLogger(TaskNotFoundExceptionHandler.class.getName())
                .warning("Body is null: " + e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).
                entity(
                        new ErrorMessage(
                                e.getLocalizedMessage (),
                                Response.Status.BAD_REQUEST.name (),
                                Response.Status.BAD_REQUEST.getStatusCode ()
                        )
                ).build();
    }
}
