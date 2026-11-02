package com.chandrika.taskmanager.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chandrika.taskmanager.dto.PageResponse;
import com.chandrika.taskmanager.entity.Task;
import com.chandrika.taskmanager.exception.TaskNotFoundException;
import com.chandrika.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updated) {
        Task existing = getTaskById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        return taskRepository.save(existing);
    }

    public void deleteTask(Long id) {
        Task existing = getTaskById(id);
        taskRepository.delete(existing);
    }
    
    private static final Set<String> SORTABLE_FIELDS = Set.of("id", "title", "completed");

    public PageResponse<Task> searchTasks(String title, int page, int size, String sortBy) {
        String sortField = SORTABLE_FIELDS.contains(sortBy) ? sortBy : "id";
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(sortField));
        Page<Task> result = taskRepository.findByTitleContainingIgnoreCase(title, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages());
    }
}
	


