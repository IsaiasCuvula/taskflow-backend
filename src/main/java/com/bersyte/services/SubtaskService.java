package com.bersyte.services;

import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.dtos.subtask.UpdateSubtaskDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.mappers.SubtaskMapper;
import com.bersyte.repositories.SubtaskRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;
    private  final TaskService taskService;

    public SubtaskService(
            SubtaskRepository subtaskRepository,
            SubtaskMapper subtaskMapper, TaskService taskService
    ) {
        this.subtaskRepository = subtaskRepository;
        this.subtaskMapper = subtaskMapper;
        this.taskService = taskService;
    }

    @Transactional
    public SubtaskResponseDto updateSubtask(UpdateSubtaskDto dto, String subtaskId){
        Subtask subtask = findSubtaskById (subtaskId);
        subtask.setCompleted (dto.isCompleted());
        //
        subtaskRepository.persist (subtask);
        //
        return subtaskMapper.toResponseDto (subtask);
    }

    @Transactional
    public String deleteSubtaskById(String taskId){
        Subtask subtask = findSubtaskById (taskId);
        subtaskRepository.delete (subtask);
        return "Subtask deleted successfully";
    }

    public SubtaskResponseDto getSubtaskById(String subtaskId) {
        return subtaskMapper.toResponseDto (findSubtaskById (subtaskId));
    }


    public List<SubtaskResponseDto> getAllSubtasksByTaskId(int page, int pageSize, String taskId) {
        Task task = this.findTaskById(taskId);

        PanacheQuery<Subtask> tasks = subtaskRepository
                .find ("taskOwnerId", task.id)
                .page (page, pageSize);
        return tasks.stream ().map (subtaskMapper::toResponseDto).toList ();
    }

    @Transactional
    public SubtaskResponseDto createSubtask(CreateSubtaskDto dto, String taskId) {
        Task task = this.findTaskById(taskId);

        Subtask subtask = subtaskMapper.fromDto(dto);
        subtask.setTask (task);
        subtaskRepository.persist(subtask);
        return subtaskMapper.toResponseDto (subtask);
    }

    private Subtask findSubtaskById(String subtaskId) {
        return subtaskRepository.find ("subtaskId", subtaskId)
                .stream()
                .findFirst ()
                .orElseThrow (() -> new TaskNotFoundException ("Subtask not found"));
    }

    private Task findTaskById(String taskId) {
        return taskService.findTaskById (taskId);
    }
}
