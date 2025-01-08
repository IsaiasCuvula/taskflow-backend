package com.bersyte.resources;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.UpdateTaskLinkDto;
import com.bersyte.services.TaskLinkService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(TaskLinkResource.class)
class TaskLinkResourceTest {

    @InjectSpy
    TaskLinkService taskLinkService;

    @Test
    void getTaskLinkByIdEndpoint() {
        String taskLinkId = "a13e6b8c-f42a-4ad4-805c-1b22c789581c";

        given().contentType(ContentType.JSON)
                .pathParam ("id", taskLinkId)
                .when ().get ("{id}")
                .then()
                .body ("taskLinkId", equalTo (taskLinkId))
                .statusCode(200);

        Mockito.verify (taskLinkService, Mockito.atLeastOnce())
                .getTaskLinkById (taskLinkId);
    }


    @Test
    void createTaskLinkEndpoint() {
        String taskId = "dadd7c69-ec90-41e9-8f24-ebf9334c3599";
        CreateTaskLinkDto dto = new CreateTaskLinkDto (
                "",
                "bersyte.com",
                LocalDateTime.of (2025, 1, 9, 1, 3)
        );

        given().contentType(ContentType.JSON)
                .body (dto)
                .pathParam ("id", taskId)
                .when ().post ("/create/{id}")
                .then ().statusCode (200)
                .body ("link", equalTo (dto.link ()));

    }

    @Test
    void updateTaskLinkEndpoint() {
        String taskLinkId = "a13e6b8c-f42a-4ad4-805c-1b22c789581c";

        UpdateTaskLinkDto updateDto = new UpdateTaskLinkDto (
            "google.com"
        );

        given().contentType(ContentType.JSON)
                .pathParam ("id", taskLinkId)
                .body (updateDto)
                .when ().put ("/update/{id}")
                .then ().statusCode (200)
                .body ("link", equalTo (updateDto.link ()));

        Mockito.verify (taskLinkService, Mockito.atLeastOnce())
                .updateTaskLink (updateDto, taskLinkId);
    }
}