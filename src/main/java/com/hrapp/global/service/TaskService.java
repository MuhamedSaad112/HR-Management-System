package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	Task update(Task task);

	/**
	 * Retrieve all task entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link Task} entities.
	 */
	Page<Task> findAll(Pageable pageable);

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
