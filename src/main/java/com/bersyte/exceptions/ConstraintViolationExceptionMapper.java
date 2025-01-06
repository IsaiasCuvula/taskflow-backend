package com.bersyte.exceptions;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        Logger.getLogger(TaskNotFoundExceptionHandler.class.getName())
                .warning("Body is null: " + e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).
                entity(
                        new ErrorMessage(
                                "Body must not be null",
                                Response.Status.BAD_REQUEST.name (),
                                Response.Status.BAD_REQUEST.getStatusCode ()
                        )
                ).build();
    }
}
