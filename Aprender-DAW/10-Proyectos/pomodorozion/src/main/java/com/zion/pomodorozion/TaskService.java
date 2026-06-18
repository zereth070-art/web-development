package com.zion.pomodorozion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    //----------------
    //CREATE
    //----------------

    public TaskDTO createTask(TaskCreateDTO dto) {
        Task task = new Task(
            dto.getTitle(),
        dto.getEstimatedPomodoros()
    );
            
    Task saved = taskRepository.save(task);
    return mapToDTO(saved);
    }

    //----------------
    //GET ALL
    //----------------
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
            .stream()
            .map(this::mapToDTO)
            .toList();
    }

    //----------------
    //GET BY ID
    //----------------
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        return mapToDTO(task);
    }

    //----------------
    //UPDATE (simple)
    //----------------
    public TaskDTO updateTask(Long id, TaskCreateDTO dto) {
        Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setTitle(dto.getTitle());
        task.setEstimatedPomodoros(dto.getEstimatedPomodoros());

        Task updated = taskRepository.save(task);
        return mapToDTO(updated);
    }

    //----------------
    //DELETE
    //----------------
    public void deleteTask(Long id) {
        if( !taskRepository.existsById(id)){
            throw new RuntimeException("Task not found");
        }

        taskRepository.deleteById(id);
    }

    //----------------
    //Mapper
    //----------------
    private TaskDTO mapToDTO(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getStatus(),
             task.getEstimatedPomodoros(), 
            task.getCompletedPomodoros()
        );
    }

}