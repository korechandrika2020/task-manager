package com.chandrika.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandrika.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{

}
