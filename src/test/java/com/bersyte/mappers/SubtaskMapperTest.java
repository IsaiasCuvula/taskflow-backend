package com.bersyte.mappers;

import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.utils.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskMapperTest {

    private SubtaskMapper subtaskMapper;

    @BeforeEach
    void setUp() {
        subtaskMapper = new SubtaskMapper ();
    }

    @Test
    void shouldMapCreateSubtaskDtoToSubtaskEntity() {
        //Arrange
        CreateSubtaskDto dto = new CreateSubtaskDto (
                "a23e798c-05ca-4651-b7f8-0d477ca6b3a2",
                    "Training session on new software tools",
                    false,
                LocalDateTime.of (2025, 1,7, 15, 30)
        );

        //When
        Subtask subtask = subtaskMapper.fromDto (dto);

        //Assert
        assertNotNull (subtask);
        assertEquals (dto.title (), subtask.getTitle ());
        assertEquals (dto.subtaskId (), subtask.getSubtaskId ());
        assertEquals (dto.isCompleted (), subtask.getCompleted ());
        assertEquals (dto.createdAt (), subtask.getCreatedAt ());
    }

    @Test
    void shouldMapSubtaskEntityToSubtaskResponseDto_whenSubtaskIsNotNull() {
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

        //When
        SubtaskResponseDto responseDto = subtaskMapper.toResponseDto (subtask1);

        //Assert
        assertNotNull (responseDto);
        assertNotNull (subtask1);
        assertEquals (responseDto.title (), subtask1.getTitle ());
        assertEquals (responseDto.subtaskId (), subtask1.getSubtaskId ());
        assertEquals (responseDto.isCompleted (), subtask1.getCompleted ());
        assertEquals (responseDto.createdAt (), subtask1.getCreatedAt ());
    }

    @Test
    void shouldThrowTaskNotFoundException_whenSubtaskEntityIsNull() {
        //Arrange

        //When
        TaskNotFoundException exception = assertThrows (
                TaskNotFoundException.class,
                () -> subtaskMapper.toResponseDto (null)
        );

        //Assert
        assertEquals ("Subtask cannot be null", exception.getMessage ());
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