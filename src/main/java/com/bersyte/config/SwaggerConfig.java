package com.bersyte.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "task management", description = "Task operations."),
        },
        info = @Info(
                title = "TaskFlow (Task Management) API with Quarkus",
                version = "0.0.1",
                contact = @Contact(
                        name = "Isaias Cuvula",
                        url = "https://bersyte.com/contact",
                        email = "s23651272@onlineedu.tu-varna.bg"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
public class SwaggerConfig extends Application { }
