package com.bersyte.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.logging.Logger;

public class TaskNotFoundExceptionHandler implements ExceptionMapper<TaskNotFoundException> {

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