//package com.bersyte.resources;
//
//import com.bersyte.dtos.task.CreateTaskDto;
//import com.bersyte.dtos.task.UpdateTaskDto;
//import com.bersyte.services.TaskService;
//import com.bersyte.utils.TaskPriority;
//import io.quarkus.test.junit.mockito.InjectSpy;
//import io.quarkus.test.common.http.TestHTTPEndpoint;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//import static io.restassured.RestAssured.given;
//import static io.restassured.RestAssured.when;
//import static org.hamcrest.Matchers.equalTo;
//
//@QuarkusTest
//@TestHTTPEndpoint(TaskResource.class)
//class TaskResourceTest {
//
//    @InjectSpy
//    TaskService taskService;
//
//    @Test
//    void getAllTasksEndpoint() {
//        given().contentType(ContentType.JSON);
//        when().get()
//                .then().statusCode(200);
//
//        Mockito.verify (taskService, Mockito.atLeastOnce())
//                .getAllTasks (0,10);
//    }
//
//    @Test
//    void getTaskByIdEndpoint() {
//       String taskId = "dadd7c69-ec90-41e9-8f24-ebf9334c3599";
//
//        given().contentType(ContentType.JSON)
//                .pathParam ("id", taskId)
//                .when ().get ("{id}")
//                .then()
//                .body ("taskId", equalTo (taskId))
//                .statusCode(200);
//
//        Mockito.verify (taskService, Mockito.atLeastOnce())
//                .getTaskById (taskId);
//    }
//
//    @Test
//    void getTaskByIdEndpointReturnNullIfTaskNotFound() {
//        String taskId = "7f98484f-be70-4a5f-9571-652be21aa59c";
//
//        given().contentType(ContentType.JSON)
//                .pathParam ("id", taskId)
//                .when ().get ("{id}")
//                .then()
//                .body ("taskId", equalTo (null))
//                .statusCode(500);
//
//        Mockito.verify (taskService, Mockito.atLeastOnce())
//                .getTaskById (taskId);
//    }
//
//    @Test
//    void createTaskEndpoint() {
//        CreateTaskDto createTaskDto = new CreateTaskDto (
//                "",
//        "Finish Project Report",
//        false,
//        0.0,
//        "Complete the final draft of the project report for submission.",
//            LocalDate.of (2025, 2,7),
//            LocalTime.of (19, 30),
//            TaskPriority.HIGH,
//            LocalDateTime.of (2025, 1, 9, 1, 3)
//        );
//
//        given().contentType(ContentType.JSON)
//                .body (createTaskDto)
//                .when ().post ("/create")
//                .then ().statusCode (200)
//                .body ("title", equalTo (createTaskDto.title ()));
//    }
//
//    @Test
//    void updateTaskEndpoint() {
//        String taskId = "dadd7c69-ec90-41e9-8f24-ebf9334c3599";
//
//        UpdateTaskDto updateTaskDto = new UpdateTaskDto (
//                "Finish Project Report",
//                "Complete the final draft of the project report for submission.",
//                LocalDate.of (2025, 2,7),
//                LocalTime.of (19, 30),
//                TaskPriority.HIGH
//        );
//
//        given().contentType(ContentType.JSON)
//                .pathParam ("id", taskId)
//                .body (updateTaskDto)
//                .when ().put ("/update/{id}")
//                .then ().statusCode (200)
//                .body ("title", equalTo (updateTaskDto.title ()));
//
//        Mockito.verify (taskService, Mockito.atLeastOnce())
//                .updateTask (updateTaskDto, taskId);
//
//        Mockito.verify (taskService, Mockito.atLeastOnce())
//                .findTaskById (taskId);
//
//    }
//
////    @Test
////    void deleteTaskByIdEndpoint() {
////        String taskId = "78c3fca1-5960-4dd3-b607-e225283de6ed";
////
////        given().contentType(ContentType.JSON)
////                .pathParam ("id", taskId)
////                .when ().delete ("/delete/{id}")
////                .then ().statusCode (200)
////                 .body (equalTo ("Task deleted successfully"));
////
////        Mockito.verify (taskService, Mockito.atLeastOnce())
////                .deleteTaskById (taskId);
////
////        Mockito.verify (taskService, Mockito.atLeastOnce())
////                .findTaskById (taskId);
////    }
//}
//
