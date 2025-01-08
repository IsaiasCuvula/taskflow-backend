package com.bersyte.services;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.dtos.taskLink.UpdateTaskLinkDto;
import com.bersyte.entities.Task;
import com.bersyte.entities.TaskLink;
import com.bersyte.mappers.TaskLinkMapper;
import com.bersyte.repositories.TaskLinkRepository;
import com.bersyte.utils.TaskPriority;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TaskLinkServiceTest {

    @Mock private TaskLinkRepository taskLinkRepository;
    @Mock private TaskService taskService;
    @Mock private TaskLinkMapper taskLinkMapper;

    @InjectMocks
    private TaskLinkService taskLinkService;

    private Task task;
    private TaskLink taskLink1, taskLink2;
    private PanacheQuery<TaskLink> mockPanacheQuery;

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

        taskLink1 = new TaskLink ();
        taskLink1.setTaskLinkId ("c96bbb88-01a5-4c59-8df4-84114c4660a7");
        taskLink1.setTask (task);
        taskLink1.setLink ("https://www.google.com");
        taskLink1.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 30)
        );

        taskLink2 = new TaskLink();
        taskLink2.setTaskLinkId ("c96bbb88-01a5-4c59-8df4-84114c4660a7");
        taskLink2.setTask (task);
        taskLink2.setLink ("https://www.bersyte.com");
        taskLink2.setCreatedAt (
                LocalDateTime.of (2025, 1,7, 20, 50)
        );
    }

    @Test
    void updateTaskLink_thenShouldReturnUpdatedTask() {
        // Arrange
        String taskLinkId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";

        TaskLink existingTaskLink = new TaskLink();
        existingTaskLink.setTaskLinkId (taskLinkId);
        existingTaskLink.setTask (task);
        existingTaskLink.setLink ("google.com");
        existingTaskLink.setCreatedAt (taskLink1.getCreatedAt());

        UpdateTaskLinkDto updateTaskLinkDto = new UpdateTaskLinkDto(
               "bersyte.com"
        );

        TaskLink updatedTaskLink = new TaskLink();
        updatedTaskLink.setTaskLinkId (taskLinkId);
        updatedTaskLink.setTask (task);
        updatedTaskLink.setLink (updateTaskLinkDto.link ());
        updatedTaskLink.setCreatedAt (taskLink1.getCreatedAt());


        TaskLinkResponseDto expectedResponseDto = new TaskLinkResponseDto(
                updatedTaskLink.getTaskLinkId (),
                updatedTaskLink.getTask ().getTaskId (),
                updatedTaskLink.getLink (),
                updatedTaskLink.getCreatedAt ()
        );

        List<TaskLink> taskLinkList = List.of(updatedTaskLink);

        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskLinkList.stream());
        Mockito.when(taskLinkRepository.find("taskLinkId", taskLinkId))
                .thenReturn(mockPanacheQuery);

        Mockito.doNothing ().when(taskLinkRepository).persist(any(TaskLink.class));

        // Lenient stubbing for the taskMapper
        Mockito.lenient().when(taskLinkMapper.toResponseDto(any(TaskLink.class)))
                .thenReturn(expectedResponseDto);

        // Act
        TaskLinkResponseDto actualResponseDto = taskLinkService.updateTaskLink (
                updateTaskLinkDto, taskLinkId
        );

        // Assert
        assertNotNull(actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        Mockito.verify(taskLinkRepository, Mockito.times(1))
                .persist(any(TaskLink.class));
    }

    @Test
    void deleteTaskLinkById_shouldFindTheTaskLinkAndThenDeleteIt(){
        //Arrange
        String taskLinkId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        TaskLink expectedTaskLink = taskLink2;
        List<TaskLink> taskLinkList = List.of(expectedTaskLink);
        String expectedMessage = "Link deleted successfully";

        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskLinkList.stream());
        Mockito.when(taskLinkRepository.find("taskLinkId", taskLinkId))
                .thenReturn(mockPanacheQuery);

        //Act
        String actualResult = taskLinkService.deleteTaskLinkById (taskLinkId);

        //Assert
        assertNotNull (actualResult);
        assertEquals(expectedMessage, actualResult);
        verify(taskLinkRepository, times (1)).find ("taskLinkId", taskLinkId);
        verify(taskLinkRepository, times (1)).delete (taskLink2);
    }


    @Test
    void getTaskLinkById_thenShouldReturnTaskLink() {
        //Arrange
        String taskLinkId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        List<TaskLink> taskLinkList = List.of(taskLink1);

        TaskLinkResponseDto expectedTaskLink = new TaskLinkResponseDto (
                taskLink1.getTaskLinkId (),
                task.getTaskId (),
                taskLink1.getLink (),
                taskLink1.getCreatedAt ()
        );

        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskLinkList.stream());
        Mockito.when(taskLinkRepository.find("taskLinkId", taskLinkId))
                .thenReturn(mockPanacheQuery);

        Mockito.when(taskLinkMapper.toResponseDto(taskLink1)).thenReturn(expectedTaskLink);


        //Act
        TaskLinkResponseDto actualTaskLink = taskLinkService.getTaskLinkById (taskLinkId);

        //Assert
        assertNotNull (actualTaskLink);
        assertEquals(expectedTaskLink, actualTaskLink);
        verify(taskLinkRepository, times (1))
                .find ("taskLinkId", taskLinkId);
    }

    @Test
    void createTaskLink_thenShouldReturnTaskLinkResponse() {
        //Arrange
        String taskId = task.getTaskId ();
        CreateTaskLinkDto createTaskLinkDto = new CreateTaskLinkDto (
                taskLink1.getTaskLinkId (),
                taskLink1.getLink (),
                taskLink1.getCreatedAt ()
        );

        TaskLinkResponseDto expectedResponseDto = new TaskLinkResponseDto(
                taskLink1.getTaskLinkId (),
                taskId,
                taskLink1.getLink (),
                taskLink1.getCreatedAt ()
        );

        Mockito.when(taskLinkMapper.fromDto(createTaskLinkDto)).thenReturn(taskLink1);
        Mockito.doNothing ().when(taskLinkRepository).persist (any (TaskLink.class));
        Mockito.when(taskLinkMapper.toResponseDto(taskLink1)).thenReturn(expectedResponseDto);

        // Act
        TaskLinkResponseDto actualResponseDto = taskLinkService.createTaskLink (
                createTaskLinkDto, taskId
        );

        //Assert
        assertNotNull (actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        verify(taskLinkRepository, times (1)).persist (taskLink1);
    }
}