package com.chandrika.taskmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chandrika.taskmanager.entity.Task;
import com.chandrika.taskmanager.exception.TaskNotFoundException;
import com.chandrika.taskmanager.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskService taskService;

	private Task task;

	@BeforeEach
	void setUp() {
		task = new Task();
		task.setId(1L);
		task.setTitle("Learn Mockito");
		task.setDescription("Write unit tests");
		task.setCompleted(false);
	}

	// ---------- getAllTasks ----------

	@Test
	void getAllTasks_returnsAllTasks() {
		when(taskRepository.findAll()).thenReturn(List.of(task));

		List<Task> result = taskService.getAllTasks();

		assertEquals(1, result.size());
		assertEquals("Learn Mockito", result.get(0).getTitle());
	}

	@Test
	void getAllTasks_whenNoTasks_returnsEmptyList() {
		when(taskRepository.findAll()).thenReturn(List.of());

		List<Task> result = taskService.getAllTasks();

		assertTrue(result.isEmpty());
	}

	// ---------- getTaskById ----------

	@Test
	void getTaskById_whenFound_returnsTask() {
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

		Task result = taskService.getTaskById(1L);

		assertEquals(1L, result.getId());
		assertEquals("Learn Mockito", result.getTitle());
	}

	@Test
	void getTaskById_whenNotFound_throwsException() {
		when(taskRepository.findById(99L)).thenReturn(Optional.empty());

		TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));

		assertEquals("Task not found with id 99", ex.getMessage());
	}

	// ---------- createTask ----------

	@Test
	void createTask_savesAndReturnsTask() {
		when(taskRepository.save(task)).thenReturn(task);

		Task result = taskService.createTask(task);

		assertEquals("Learn Mockito", result.getTitle());
		verify(taskRepository).save(task);
	}

	// ---------- updateTask ----------

	@Test
	void updateTask_whenFound_updatesFields() {
		Task updated = new Task();
		updated.setTitle("Updated title");
		updated.setDescription("Updated description");
		updated.setCompleted(true);

		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

		Task result = taskService.updateTask(1L, updated);

		assertEquals("Updated title", result.getTitle());
		assertEquals("Updated description", result.getDescription());
		assertTrue(result.isCompleted());
	}

	@Test
	void updateTask_savesExistingTaskWithNewValues() {
		Task updated = new Task();
		updated.setTitle("New title");
		updated.setDescription("New description");
		updated.setCompleted(true);

		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

		taskService.updateTask(1L, updated);

		ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
		verify(taskRepository).save(captor.capture());
		Task saved = captor.getValue();

		assertEquals(1L, saved.getId()); // same row, not a new one
		assertEquals("New title", saved.getTitle());
		assertTrue(saved.isCompleted());
	}

	@Test
	void updateTask_whenNotFound_throwsAndDoesNotSave() {
		when(taskRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(99L, new Task()));

		verify(taskRepository, never()).save(any(Task.class));
	}

	// ---------- deleteTask ----------

	@Test
	void deleteTask_whenFound_deletesTask() {
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

		taskService.deleteTask(1L);

		verify(taskRepository).delete(task);
	}

	@Test
	void deleteTask_whenNotFound_throwsAndDoesNotDelete() {
		when(taskRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(99L));

		verify(taskRepository, never()).delete(any(Task.class));
	}

}
