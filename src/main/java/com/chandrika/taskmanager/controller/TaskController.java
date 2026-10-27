package com.chandrika.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chandrika.taskmanager.entity.Task;
import com.chandrika.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
	public List<Task>getAll(){
		return taskService.getAllTasks();
	}
	
	@GetMapping("/{id}")
	public Task getById(@PathVariable Long id) {
		return taskService.getTaskById(id);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Task create(@Valid @RequestBody Task task) {
		return taskService.createTask(task);
	}
	
	@PutMapping("/{id}")
	public Task update(@PathVariable Long id, @Valid @RequestBody Task task) {
		return taskService.updateTask(id, task);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		taskService.deleteTask(id);
	}
	
}
