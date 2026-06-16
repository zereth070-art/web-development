package com.zion.pomodorozion;


import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();

        
    }

    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
        throw new IllegalArgumentException("El título es obligatorio");
    }

    return repository.save(task);
    }

    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void deleteTask(long id){
        repository.deleteById(id);
    }

    public Task updateTask(Long id, Task task) {
        Task newTask = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
        Task existingTask = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        existingTask.setTitle(task.getTitle());
        existingTask.setCompleted(newTask.isCompleted());
        existingTask.setCompletePomodoros(newTask.getCompletePomodoros());
        
        return repository.save(existingTask);
    }
}