package com.bersyte.services;

import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.TaskResponseDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.mappers.SubtaskMapper;
import com.bersyte.mappers.TaskLinkMapper;
import com.bersyte.mappers.TaskMapper;
import com.bersyte.repositories.TaskRepository;
import com.bersyte.utils.TaskPriority;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
@Transactional
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
//    @Mock private TaskLinkMapper taskLinkMapper;
//    @Mock private SubtaskMapper subtaskMapper;
    @Mock private TaskMapper taskMapper;
    @InjectMocks private TaskService taskService;

    private Task task;
    private Subtask subtask1, subtask2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";

        task = new Task();
        task.setTaskId (taskId);
        task.setTitle ("Finish Project Report");
        task.setCompleted (false);
        task.setPercentageCompleted (20.0);
        task.setDescription ("Complete the final draft of the project report for submission.");
        task.setDueDate (LocalDate.of (2025, 1,7));
        task.setDueTime (LocalTime.of (19, 30));
        task.setPriority (TaskPriority.HIGH);
        task.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 15, 30)
        );

        subtask1 = new Subtask();
        subtask1.setSubtaskId ("c96bbb88-01a5-4c59-8df4-84114c4660a7");
        subtask1.setTask (task);
        subtask1.setTitle ("Create shared drive");
        subtask1.setCompleted (false);
        subtask1.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 30)
        );

        subtask2 = new Subtask();
        subtask2.setSubtaskId ("93168b64-816e-46fb-8193-aeae5e364237");
        subtask2.setTask (task);
        subtask2.setTitle ("Organize all digital files");
        subtask2.setCompleted (true);
        subtask2.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 50)
        );
    }

    @Test
    @TestTransaction
    void createTask_thenShouldReturnCreatedTask() {
        //Arrange
        CreateTaskDto createTaskDto = new CreateTaskDto (
                task.getTaskId (),
                task.getTitle (),
                task.getCompleted (),
                task.getPercentageCompleted (),
                task.getDescription (),
                task.getDueDate (),
                task.getDueTime (),
                task.getPriority (),
                task.getCreatedAt ()
        );

        TaskResponseDto expectedResponseDto = new TaskResponseDto(
                task.getTaskId (),
                task.getTitle (),
                task.getCompleted (),
                task.getPercentageCompleted (),
                task.getDescription (),
                task.getDueDate (),
                task.getDueTime (),
                task.getPriority (),
                task.getCreatedAt (),
                new ArrayList<>(),
                new ArrayList<> ()
        );

        Mockito.when(taskMapper.fromDto(createTaskDto)).thenReturn(task);
        Mockito.doNothing ().when(taskRepository).persist (any (Task.class));
        Mockito.when(taskMapper.toResponseDto(task)).thenReturn(expectedResponseDto);

        // Act
        TaskResponseDto actualResponseDto = taskService.createTask(createTaskDto);

        //Assert
        assertNotNull (actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        verify(taskRepository, times (1)).persist (task);
    }
}