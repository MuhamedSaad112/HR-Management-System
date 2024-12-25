package com.hrapp.global.service;

import com.hrapp.global.entity.JobHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link JobHistory} entities.
 */
public interface JobHistoryService {

	/**
	 * Save a job history entity to the database.
	 *
	 * @param jobHistory the entity to save.
	 * @return the persisted {@link JobHistory} entity.
	 */
	JobHistory save(JobHistory jobHistory);

	/**
	 * Update an existing job history entity.
	 *
	 * @param jobHistory the entity containing updated fields.
	 * @return the updated {@link JobHistory} entity.
	 */
	Optional<JobHistory> update(JobHistory jobHistory);

	/**
	 * Retrieve all job history entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link JobHistory} entities.
	 */
	Page<JobHistory> findAll(Pageable pageable);

	/**
	 * Retrieve a job history entity by its unique identifier.
	 *
	 * @param id the ID of the job history to retrieve.
	 * @return the {@link JobHistory} entity, or {@code null} if not found.
	 */
	JobHistory findById(Long id);

	/**
	 * Retrieve a job history entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the job history to retrieve.
	 * @return an {@link Optional} containing the job history entity if found, or empty otherwise.
	 */
	Optional<JobHistory> getById(Long id);

	/**
	 * Delete a job history entity by its unique identifier.
	 *
	 * @param id the ID of the job history to delete.
	 */
	void delete(Long id);
}