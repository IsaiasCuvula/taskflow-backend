package com.bersyte.mappers;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.entities.Task;
import com.bersyte.entities.TaskLink;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.utils.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskLinkMapperTest {

    private TaskLinkMapper taskLinkMapper;

    @BeforeEach
    void setUp() {
        taskLinkMapper = new TaskLinkMapper ();
    }

    @Test
    void shouldMapCreateTaskLinkDtoToTaskLinkEntity() {
        //Arrange
        CreateTaskLinkDto dto = new CreateTaskLinkDto (
                "a23e798c-05ca-4651-b7f8-0d477ca6b3a2",
                "google.com",
                LocalDateTime.of (2025, 1,7, 15, 30)
        );

        //When
        TaskLink taskLink = taskLinkMapper.fromDto (dto);

        //Assert
        assertNotNull (taskLink);
        assertEquals (dto.link (), taskLink.getLink ());
        assertEquals (dto.taskLinkId (), taskLink.getTaskLinkId ());
        assertEquals (dto.createdAt (), taskLink.getCreatedAt ());
    }

    @Test
    void shouldMapTaskLinkEntityToTaskLinkResponseDto_whenTaskLinkIsNotNull() {
        //Arrange
        Task task = getTask ();

        TaskLink taskLink = new TaskLink();
        taskLink.setTaskLinkId ("c96bbb88-01a5-4c59-8df4-84114c4660a7");
        taskLink.setTask (task);
        taskLink.setLink ("bersyte.com");
        taskLink.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 30)
        );

        //When
        TaskLinkResponseDto responseDto = taskLinkMapper.toResponseDto (taskLink);

        //Assert
        assertNotNull (responseDto);
        assertEquals (responseDto.taskLinkId (), taskLink.getTaskLinkId ());
        assertEquals (responseDto.taskId (), taskLink.getTask ().getTaskId ());
        assertEquals (responseDto.link (), taskLink.getLink ());
        assertEquals (responseDto.createdAt (), taskLink.getCreatedAt ());
    }

    @Test
    void shouldThrowTaskNotFoundException_whenTaskLinkEntityIsNull() {
        //Arrange

        //When
        TaskNotFoundException exception = assertThrows (
                TaskNotFoundException.class,
                () -> taskLinkMapper.toResponseDto (null)
        );

        //Assert
        assertEquals ("Link cannot be null", exception.getMessage ());
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