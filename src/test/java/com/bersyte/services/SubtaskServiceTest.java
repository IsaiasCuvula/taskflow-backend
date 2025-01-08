package com.bersyte.services;

import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.dtos.subtask.UpdateSubtaskDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.mappers.SubtaskMapper;
import com.bersyte.repositories.SubtaskRepository;
import com.bersyte.utils.TaskPriority;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SubtaskServiceTest {

    @Mock private SubtaskRepository subtaskRepository;
    @Mock private SubtaskMapper subtaskMapper;
    @Mock private TaskService taskService;

    @InjectMocks
    private SubtaskService subtaskService;

    private Task task;
    private Subtask subtask1, subtask2;
    private PanacheQuery<Subtask> mockPanacheQuery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Mock PanacheQuery
        mockPanacheQuery = Mockito.mock();

        task = new Task();
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
    void createSubtask_thenShouldReturnSubtaskResponse() {
        //Arrange
        String taskId = task.getTaskId ();
        CreateSubtaskDto createSubtaskDto = new CreateSubtaskDto (
                subtask2.getSubtaskId (),
                subtask2.getTitle (),
                subtask2.getCompleted (),
                subtask2.getCreatedAt ()
        );

        SubtaskResponseDto expectedResponseDto = new SubtaskResponseDto(
                subtask2.getSubtaskId (),
                taskId,
                subtask2.getTitle (),
                subtask2.getCompleted (),
                subtask2.getCreatedAt ()
        );

        Mockito.when(subtaskMapper.fromDto(createSubtaskDto)).thenReturn(subtask2);
        Mockito.doNothing ().when(subtaskRepository).persist (any (Subtask.class));
        Mockito.when(subtaskMapper.toResponseDto(subtask2)).thenReturn(expectedResponseDto);

        // Act
        SubtaskResponseDto actualResponseDto = subtaskService.createSubtask (
                createSubtaskDto, taskId
        );

        //Assert
        assertNotNull (actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        verify(subtaskRepository, times (1)).persist (subtask2);
    }

    @Test
    void updateSubtask_thenShouldThrowSubtaskNotFoundException(){
        // Arrange
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        String subtaskId = subtask1.getSubtaskId ();

        UpdateSubtaskDto updateSubtaskDto = new UpdateSubtaskDto(
                true,
                subtaskId
        );

        Subtask updatedSubtask = new Subtask();
        updatedSubtask.setSubtaskId (subtaskId);
        updatedSubtask.setTask (subtask1.getTask ());
        updatedSubtask.setTitle (subtask1.getTitle ());
        updatedSubtask.setCompleted (true);
        updatedSubtask.setCreatedAt (subtask1.getCreatedAt());

        List<Subtask> subtaskList = List.of(updatedSubtask);

        Mockito.when(subtaskRepository.find("subtaskId", subtaskId))
                .thenReturn(mockPanacheQuery);
        Mockito.when(mockPanacheQuery.firstResult()).thenReturn(updatedSubtask);

        Mockito.when(taskService.findTaskById (taskId))
                .thenReturn(task);

        /// Act & Assert
        assertThrows (
                TaskNotFoundException.class,
                ()-> subtaskService.updateSubtask (updateSubtaskDto, "taskId"),
                "Subtask not found"
        );
    }

    @Test
    void updateSubtask_thenShouldReturnUpdatedSubtask() {
        // Arrange
        String taskId = task.getTaskId ();
        String subtaskId = subtask1.getSubtaskId ();

        Subtask existingSubtask = new Subtask();
        existingSubtask.setSubtaskId (subtaskId);
        existingSubtask.setTask (subtask1.getTask ());
        existingSubtask.setTitle (subtask1.getTitle ());
        existingSubtask.setCompleted (subtask1.getCompleted ());
        existingSubtask.setCreatedAt (subtask1.getCreatedAt());

        UpdateSubtaskDto updateSubtaskDto = new UpdateSubtaskDto(
                true,
                subtaskId
        );

        Subtask updatedSubtask = new Subtask();
        updatedSubtask.setSubtaskId (subtaskId);
        updatedSubtask.setTask (subtask1.getTask ());
        updatedSubtask.setTitle (subtask1.getTitle ());
        updatedSubtask.setCompleted (true);
        updatedSubtask.setCreatedAt (subtask1.getCreatedAt());


        SubtaskResponseDto expectedResponseDto = new SubtaskResponseDto(
                updatedSubtask.getSubtaskId (),
                updatedSubtask.getTask ().getTaskId (),
                updatedSubtask.getTitle (),
                updatedSubtask.getCompleted (),
                updatedSubtask.getCreatedAt ()
        );

        List<Subtask> subtaskList = List.of(updatedSubtask);

        Mockito.when(mockPanacheQuery.stream()).thenReturn(subtaskList.stream());
        Mockito.when(subtaskRepository.find("subtaskId", subtaskId))
                .thenReturn(mockPanacheQuery);

        Mockito.when(taskService.findTaskById (taskId))
                .thenReturn(task);

        Mockito.doNothing ().when(subtaskRepository).persist(any(Subtask.class));

        // Lenient stubbing for the taskMapper
        Mockito.lenient().when(subtaskMapper.toResponseDto(any(Subtask.class)))
                .thenReturn(expectedResponseDto);

        // Act
        SubtaskResponseDto actualResponseDto = subtaskService.updateSubtask (
                updateSubtaskDto, taskId
        );

        // Assert
        assertNotNull(actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        Mockito.verify(subtaskRepository, Mockito.times(1))
                .persist(any(Subtask.class));
    }


    @Test
    void deleteSubtaskById_shouldFindTheTaskLinkAndThenDeleteIt(){
        //Arrange
        String subtaskId = subtask1.getSubtaskId ();
        Subtask expectedSubtask = subtask1;
        List<Subtask> subtaskList = List.of(expectedSubtask);
        String expectedMessage = "Subtask deleted successfully";

        Mockito.when(mockPanacheQuery.stream()).thenReturn(subtaskList.stream());
        Mockito.when(subtaskRepository.find("subtaskId", subtaskId))
                .thenReturn(mockPanacheQuery);

        //Act
        String actualResult = subtaskService.deleteSubtaskById (subtaskId);

        //Assert
        assertNotNull (actualResult);
        assertEquals(expectedMessage, actualResult);
        verify(subtaskRepository, times (1)).find ("subtaskId", subtaskId);
        verify(subtaskRepository, times (1)).delete (subtask1);
    }

    @Test
    void getSubtaskById_thenShouldReturnTaskLink() {
        //Arrange
        String subtaskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        List<Subtask> subtaskList = List.of(subtask2);

        SubtaskResponseDto expectedResponseDto = new SubtaskResponseDto(
                subtask2.getSubtaskId (),
                subtask2.getTask ().getTaskId (),
                subtask2.getTitle (),
                subtask2.getCompleted (),
                subtask2.getCreatedAt ()
        );

        Mockito.when(mockPanacheQuery.stream()).thenReturn(subtaskList.stream());
        Mockito.when(subtaskRepository.find("subtaskId", subtaskId))
                .thenReturn(mockPanacheQuery);

        Mockito.when(subtaskMapper.toResponseDto(subtask2)).thenReturn(expectedResponseDto);


        //Act
        SubtaskResponseDto actualSubtask = subtaskService.getSubtaskById (subtaskId);

        //Assert
        assertNotNull (actualSubtask);
        assertEquals(expectedResponseDto, actualSubtask);
        verify(subtaskRepository, times (1))
                .find ("subtaskId", subtaskId);
    }
}