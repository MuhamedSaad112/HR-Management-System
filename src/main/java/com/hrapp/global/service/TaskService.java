package com.hrapp.global.service;

import com.hrapp.global.entity.Task;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Task} entities.
 */
public interface TaskService {

	/**
	 * Save a task entity to the database.
	 *
	 * @param task the entity to save.
	 * @return the persisted {@link Task} entity.
	 */
	Task save(Task task);

	/**
	 * Update an existing task entity.
	 *
	 * @param task the entity containing updated fields.
	 * @return the updated {@link Task} entity.
	 */
	Optional<Task> update(Task task);

	/**
	 * Retrieve all task entities with pagination support.
	 *
	 * @return a paginated list of {@link Task} entities.
	 */
	List<Task> findAll();

	/**
	 * Retrieve a task entity by its unique identifier.
	 *
	 * @param id the ID of the task to retrieve.
	 * @return the {@link Task} entity, or {@code null} if not found.
	 */
	Task findById(Long id);

	/**
	 * Retrieve a task entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the task to retrieve.
	 * @return an {@link Optional} containing the task entity if found, or empty otherwise.
	 */
	Optional<Task> getById(Long id);

	/**
	 * Delete a task entity by its unique identifier.
	 *
	 * @param id the ID of the task to delete.
	 */
	void delete(Long id);
}
