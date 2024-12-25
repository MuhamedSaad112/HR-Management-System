package com.hrapp.global.service;

import com.hrapp.global.entity.Department;

import java.util.List;
import java.util.Optional;

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
	Optional<Department> update(Department department);

	/**
	 * Retrieve all department entities with pagination support.
	 *
	 * @return a paginated list of {@link Department} entities.
	 */
	List<Department> findAll();

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
