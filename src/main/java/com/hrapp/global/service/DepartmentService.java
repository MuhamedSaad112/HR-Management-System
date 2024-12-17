package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Department} entities.
 */
public interface DepartmentService {

	/**
	 * Save a department entity to the database.
	 *
	 * @param department the entity to save.
	 * @return the persisted {@link Department} entity.
	 */
	Department save(Department department);

	/**
	 * Partially update a department entity. This may include only specific fields.
	 *
	 * @param department the entity containing updated fields.
	 * @return the updated {@link Department} entity.
	 */
	Department update(Department department);

	/**
	 * Retrieve all department entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link Department} entities.
	 */
	Page<Department> findAll(Pageable pageable);

	/**
	 * Retrieve a department entity by its unique identifier.
	 *
	 * @param id the ID of the department to retrieve.
	 * @return the {@link Department} entity, or {@code null} if not found.
	 */
	Department findById(Long id);

	/**
	 * Retrieve a department entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the department to retrieve.
	 * @return an {@link Optional} containing the department entity if found, or empty otherwise.
	 */
	Optional<Department> getById(Long id);

	/**
	 * Delete a department entity by its unique identifier.
	 *
	 * @param id the ID of the department to delete.
	 */
	void delete(Long id);
}
