//package com.bersyte.resources;
//
//import com.bersyte.dtos.subtask.CreateSubtaskDto;
//import com.bersyte.dtos.subtask.UpdateSubtaskDto;
//import com.bersyte.services.SubtaskService;
//import io.quarkus.test.common.http.TestHTTPEndpoint;
//import io.quarkus.test.junit.QuarkusTest;
//import io.quarkus.test.junit.mockito.InjectSpy;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import java.time.LocalDateTime;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//
//@QuarkusTest
//@TestHTTPEndpoint(SubtaskResource.class)
//class SubtaskResourceTest {
//
//    @InjectSpy
//    SubtaskService subtaskService;
//
//    @Test
//    void getSubtaskByIdEndpoint() {
//        String subtaskId = "c96bbb88-01a5-4c59-8df4-84114c4660a7";
//
//        given().contentType(ContentType.JSON)
//                .pathParam ("id", subtaskId)
//                .when ().get ("{id}")
//                .then()
//                .body ("subtaskId", equalTo (subtaskId))
//                .statusCode(200);
//
//        Mockito.verify (subtaskService, Mockito.atLeastOnce())
//                .getSubtaskById (subtaskId);
//    }
//
//
//    @Test
//    void createSubtaskEndpoint() {
//        String taskId = "dadd7c69-ec90-41e9-8f24-ebf9334c3599";
//        CreateSubtaskDto dto = new CreateSubtaskDto (
//                "",
//                "Finish Project Report",
//                false,
//                LocalDateTime.of (2025, 1, 9, 1, 3)
//        );
//
//        given().contentType(ContentType.JSON)
//                .body (dto)
//                .pathParam ("id", taskId)
//                .when ().post ("/create/{id}")
//                .then ().statusCode (200)
//                .body ("title", equalTo (dto.title ()));
//
//    }
//
//    @Test
//    void updateSubtaskEndpoint() {
//        String subtaskId = "c96bbb88-01a5-4c59-8df4-84114c4660a7";
//        String taskId = "11d4b723-87e1-4f24-803c-2fc72ad45e06";
//
//        UpdateSubtaskDto updateDto = new UpdateSubtaskDto (
//                true,
//                subtaskId
//        );
//
//        given().contentType(ContentType.JSON)
//                .pathParam ("id", taskId)
//                .body (updateDto)
//                .when ().put ("/update/{id}")
//                .then ().statusCode (200)
//                .body ("isCompleted", equalTo (updateDto.isCompleted ()));
//
//        Mockito.verify (subtaskService, Mockito.atLeastOnce())
//                .updateSubtask (updateDto, taskId);
//    }
//}