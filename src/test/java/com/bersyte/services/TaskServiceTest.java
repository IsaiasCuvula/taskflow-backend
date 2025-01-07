package com.bersyte.services;

import com.bersyte.dtos.task.AllTaskResponseDto;
import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.TaskResponseDto;
import com.bersyte.dtos.task.UpdateTaskDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.mappers.TaskMapper;
import com.bersyte.repositories.TaskRepository;
import com.bersyte.utils.TaskPriority;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
@Transactional
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @Mock private TaskMapper taskMapper;
    @InjectMocks private TaskService taskService;

    private Task task;
    private Subtask subtask1, subtask2;
    private PanacheQuery<Task> mockPanacheQuery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Mock PanacheQuery
        mockPanacheQuery = Mockito.mock();

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
    void updateTask_thenShouldReturnUpdatedTask() {
        // Arrange
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";

        Task existingTask = new Task();
        existingTask.setTaskId(taskId);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setDueDate(LocalDate.now());
        existingTask.setDueTime(LocalTime.now());
        existingTask.setPriority(TaskPriority.LOW);

        UpdateTaskDto updateTaskDto = new UpdateTaskDto(
                "New Title",
                "New Description",
                LocalDate.now().plusDays(1),
                LocalTime.now().plusHours(1),
                TaskPriority.HIGH
        );

        Task updatedTask = new Task();
        updatedTask.setTaskId(taskId);
        updatedTask.setTitle(updateTaskDto.title());
        updatedTask.setDescription(updateTaskDto.description());
        updatedTask.setDueDate(updateTaskDto.dueDate());
        updatedTask.setDueTime(updateTaskDto.dueTime());
        updatedTask.setPriority(updateTaskDto.priority());

        TaskResponseDto expectedResponseDto = new TaskResponseDto(
                taskId,
                updateTaskDto.title(),
                false,
                0.0,
                updateTaskDto.description(),
                updateTaskDto.dueDate(),
                updateTaskDto.dueTime(),
                updateTaskDto.priority(),
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        List<Task> taskList = List.of(updatedTask);

        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskList.stream());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        Mockito.doNothing ().when(taskRepository).persist(any(Task.class));

        // Lenient stubbing for the taskMapper
        Mockito.lenient().when(taskMapper.toResponseDto(any(Task.class))).thenReturn(expectedResponseDto);

        // Act
        TaskResponseDto actualResponseDto = taskService.updateTask(updateTaskDto, taskId);

        // Assert
        assertNotNull(actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        Mockito.verify(taskRepository, Mockito.times(1)).persist(any(Task.class));
    }

    @Test
    void updateTaskById_shouldNotFindTheTaskAndThrowException(){
        //Arrange
        UpdateTaskDto updateTaskDto = new UpdateTaskDto(
                "New Title",
                "New Description",
                LocalDate.now().plusDays(1),
                LocalTime.now().plusHours(1),
                TaskPriority.HIGH
        );
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        Mockito.when(mockPanacheQuery.stream()).thenReturn(Stream.empty ());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        /// Act & Assert
        assertThrows (
                TaskNotFoundException.class,
                ()-> taskService.updateTask (updateTaskDto, taskId), "Task not found"
        );
    }



    @Test
    void deleteTaskById_shouldNotFindTheTaskAndThrowException(){
        //Arrange
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        Mockito.when(mockPanacheQuery.stream()).thenReturn(Stream.empty ());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        /// Act & Assert
        assertThrows (
                TaskNotFoundException.class,
                ()-> taskService.deleteTaskById (taskId), "Task not found"
        );
    }


    @Test
    void deleteTaskById_shouldFindTheTaskAndDeleteTheTask(){
        //Arrange
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        Task expectedTask = task;
        List<Task> taskList = List.of(expectedTask);
        String expectedMessage = "Task deleted successfully";

        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskList.stream());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        //Act
        String actualResult = taskService.deleteTaskById (taskId);

        //Assert
        assertNotNull (actualResult);
        assertEquals(expectedMessage, actualResult);
        verify(taskRepository, times (1)).find ("taskId", taskId);
        verify(taskRepository, times (1)).delete (task);
    }



    @Test
    void findTaskById_thenShouldReturnTaskResponseDto() {
        //Arrange
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        Task expectedTask = task;
        List<Task> taskList = List.of(expectedTask);

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

        Mockito.when(taskMapper.toResponseDto(task)).thenReturn(expectedResponseDto);
        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskList.stream());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        // Act
        TaskResponseDto actualResponseDto = taskService.getTaskById(taskId);

        //Assert
        assertNotNull (actualResponseDto);
        assertEquals(expectedResponseDto, actualResponseDto);
        verify(taskRepository, times (1)).find ("taskId", taskId);
    }


    @Test
    void getAllTasks_ShouldReturnEmptyList_WhenNoTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(mockPanacheQuery);
        when(mockPanacheQuery.page(0, 10)).thenReturn(mockPanacheQuery);
        when(mockPanacheQuery.stream()).thenReturn(Stream.empty ());

        // Act
        List<AllTaskResponseDto> result = taskService.getAllTasks(0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(taskRepository).findAll();
        verify(mockPanacheQuery).page(0, 10);
    }



    @Test
    void getAllTasks_ShouldReturnPagedResults() {
        // Arrange
        AllTaskResponseDto expectedResponseDto = new AllTaskResponseDto(
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
        List<Task> taskList = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(mockPanacheQuery);
        when(mockPanacheQuery.page(0, 10)).thenReturn(mockPanacheQuery);
        when(mockPanacheQuery.stream()).thenReturn(taskList.stream());
        when(taskMapper.toAllTaskResponseDto(task)).thenReturn(expectedResponseDto);

        // Act
        List<AllTaskResponseDto> result = taskService.getAllTasks(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskRepository).findAll();
        verify(mockPanacheQuery).page(0, 10);
    }

    @Test
    void findTaskById_shouldHandleNullInput() {
        // Arrange
        String taskId = null;

        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> taskService.findTaskById(taskId),
                "Task ID cannot be null"
        );
    }

    @Test
    void findTaskById_shouldThrowExceptionWhenTaskNotFound() {
        String taskId = "7f98484f-be70-4a5f-9571-652be21aa59c";

        Mockito.when(mockPanacheQuery.stream()).thenReturn(Stream.empty ());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        /// Act & Assert
        assertThrows (
                TaskNotFoundException.class,
                ()-> taskService.findTaskById (taskId), "Task not found"
        );
        verify(taskRepository, times (1)).find ("taskId", taskId);
    }

    @Test
    void findTaskById_thenShouldReturnTask() {
        //Arrange
        String taskId = "a23e798c-05ca-4651-b7f8-0d477ca6b3a2";
        Task expectedTask = task;
        List<Task> taskList = List.of(expectedTask);

        Mockito.when(mockPanacheQuery.stream()).thenReturn(taskList.stream());
        Mockito.when(taskRepository.find("taskId", taskId))
                .thenReturn(mockPanacheQuery);

        //Act
        Task actualTask = taskService.findTaskById (taskId);

        //Assert
        assertNotNull (actualTask);
        assertEquals(expectedTask, actualTask);
        verify(taskRepository, times (1)).find ("taskId", taskId);
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