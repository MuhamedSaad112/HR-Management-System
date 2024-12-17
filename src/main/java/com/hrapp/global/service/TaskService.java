package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskService {

	// Save a Task.

	Task save(Task task);

	// updates a Task.

	Task update(Task task);

	// Get all the Task.

	Page<Task> findAll(Pageable pageable);

	// Get the "id" Task

	Task findById(Long id);

	// Get the "id" Location from cash
	public Optional<Task> getById(Long id);

	// Delete the "id" Task.

	void delete(Long id);
}