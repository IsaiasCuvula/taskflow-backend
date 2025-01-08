package com.bersyte.mappers;

import com.bersyte.dtos.task.AllTaskResponseDto;
import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.TaskResponseDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.entities.TaskLink;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.utils.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        SubtaskMapper subtaskMapper = new SubtaskMapper ();
        TaskLinkMapper taskLinkMapper = new TaskLinkMapper ();
        taskMapper = new TaskMapper (subtaskMapper, taskLinkMapper);
    }

    @Test
    void shouldMapCreateTaskDtoToTaskEntity() {
        //Arrange
        CreateTaskDto dto = new CreateTaskDto (
                "a23e798c-05ca-4651-b7f8-0d477ca6b3a2",
                "New software tool",
                false,
                0.0,
                "Training session on new software tools",
                LocalDate.of (2025, 1,7),
                LocalTime.of (19, 30),
                TaskPriority.HIGH,
                LocalDateTime.of (2025, 1,7, 15, 30)
        );

        //When
        Task taskResult = taskMapper.fromDto (dto);

        //Assert
        assertNotNull (taskResult);
        assertEquals (dto.taskId (), taskResult.getTaskId ());
        assertEquals (dto.title (), taskResult.getTitle ());
        assertEquals (dto.isCompleted (), taskResult.getCompleted ());
        assertEquals (dto.percentageCompleted (), taskResult.getPercentageCompleted ());
        assertEquals (dto.dueDate (), taskResult.getDueDate ());
        assertEquals (dto.dueTime (), taskResult.getDueTime ());
        assertEquals (dto.createdAt (), taskResult.getCreatedAt ());
    }

    @Test
    void shouldMapTaskEntityToTaskResponseDto_whenTaskIsNotNull() {
        //Arrange
        Task task = getTask ();

        Subtask subtask1 = new Subtask();
        subtask1.setSubtaskId ("c96bbb88-01a5-4c59-8df4-84114c4660a7");
        subtask1.setTask (task);
        subtask1.setTitle ("Create shared drive");
        subtask1.setCompleted (false);
        subtask1.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 30)
        );

        TaskLink taskLink = new TaskLink();
        taskLink.setTaskLinkId ("c96bbb88-01a5-4c59-8df4-84114c4660a7");
        taskLink.setTask (task);
        taskLink.setLink ("bersyte.com");
        taskLink.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 30)
        );

        task.setTaskLinks (List.of (taskLink));
        task.setSubtasks (List.of (subtask1));

        //When
        TaskResponseDto responseDto = taskMapper.toResponseDto (task);

        //Assert
        assertNotNull (responseDto);
        assertEquals (responseDto.taskId (), task.getTaskId ());
        assertEquals (responseDto.title (), task.getTitle ());
        assertEquals (responseDto.isCompleted (), task.getCompleted ());
        assertEquals (responseDto.percentageCompleted (), task.getPercentageCompleted ());
        assertEquals (responseDto.dueDate (), task.getDueDate ());
        assertEquals (responseDto.dueTime (), task.getDueTime ());
        assertEquals (responseDto.subtasks ().size (), task.getSubtasks ().size ());
        assertEquals (responseDto.taskLinks ().size (), task.getTaskLinks ().size ());
        assertEquals (responseDto.createdAt (), task.getCreatedAt ());
    }

    @Test
    void shouldThrowTaskNotFoundException_whenTaskEntityIsNull() {
        //Arrange

        //When
        TaskNotFoundException exception = assertThrows (
                TaskNotFoundException.class,
                () -> taskMapper.toResponseDto (null)
        );

        //Assert
        assertEquals ("Task not found", exception.getMessage ());
    }

    @Test
    void shouldMapTaskEntityToAllTaskResponseDto_whenTaskIsNotNull() {
        //Arrange
        Task task = getTask ();

        //When
        AllTaskResponseDto responseDto = taskMapper.toAllTaskResponseDto (task);

        //Assert
        assertNotNull (responseDto);
        assertEquals (responseDto.taskId (), task.getTaskId ());
        assertEquals (responseDto.title (), task.getTitle ());
        assertEquals (responseDto.isCompleted (), task.getCompleted ());
        assertEquals (responseDto.percentageCompleted (), task.getPercentageCompleted ());
        assertEquals (responseDto.dueDate (), task.getDueDate ());
        assertEquals (responseDto.dueTime (), task.getDueTime ());
        assertEquals (responseDto.createdAt (), task.getCreatedAt ());
    }

    @Test
    void shouldThrowTaskNotFoundException_whenTaskEntityIsNullInAllTaskResponseDto() {
        //Arrange

        //When
        TaskNotFoundException exception = assertThrows (
                TaskNotFoundException.class,
                () -> taskMapper.toAllTaskResponseDto (null)
        );

        //Assert
        assertEquals ("Task not found", exception.getMessage ());
    }

    private static Task getTask() {
        Task task = new Task ();
        task.setTaskId ("a23e798c-05ca-4651-b7f8-0d477ca6b3a2");
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
        return task;
    }
}