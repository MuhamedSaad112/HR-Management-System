package com.hrapp.global.service;

import com.hrapp.global.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Job} entities.
 */
public interface JobService {

	/**
	 * Save a job entity to the database.
	 *
	 * @param job the entity to save.
	 * @return the persisted {@link Job} entity.
	 */
	Job save(Job job);

	/**
	 * Update an existing job entity.
	 *
	 * @param job the entity containing updated fields.
	 * @return the updated {@link Job} entity.
	 */
	Optional<Job> update(Job job);

	/**
	 * Retrieve all job entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link Job} entities.
	 */
	Page<Job> findAll(Pageable pageable);

	/**
	 * Retrieve a job entity by its unique identifier.
	 *
	 * @param id the ID of the job to retrieve.
	 * @return the {@link Job} entity, or {@code null} if not found.
	 */
	Job findById(Long id);

	/**
	 * Retrieve a job entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the job to retrieve.
	 * @return an {@link Optional} containing the job entity if found, or empty otherwise.
	 */
	Optional<Job> getById(Long id);

	/**
	 * Delete a job entity by its unique identifier.
	 *
	 * @param id the ID of the job to delete.
	 */
	void delete(Long id);
}
