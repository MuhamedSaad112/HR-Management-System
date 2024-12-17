package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Employee} entities.
 */
public interface EmployeeService {

	/**
	 * Retrieve an employee entity by first name and last name, ordered by ID.
	 *
	 * @param firstName the first name of the employee.
	 * @param lastName the last name of the employee.
	 * @return the {@link Employee} entity matching the criteria.
	 */
	Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName);

	/**
	 * Save an employee entity to the database.
	 *
	 * @param employee the entity to save.
	 * @return the persisted {@link Employee} entity.
	 */
	Employee save(Employee employee);

	/**
	 * Update an existing employee entity.
	 *
	 * @param employee the entity containing updated fields.
	 * @return the updated {@link Employee} entity.
	 */
	Employee update(Employee employee);

	/**
	 * Retrieve all employee entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link Employee} entities.
	 */
	Page<Employee> findAll(Pageable pageable);

	/**
	 * Retrieve an employee entity by its unique identifier.
	 *
	 * @param id the ID of the employee to retrieve.
	 * @return the {@link Employee} entity, or {@code null} if not found.
	 */
	Employee findById(Long id);

	/**
	 * Retrieve an employee entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the employee to retrieve.
	 * @return an {@link Optional} containing the employee entity if found, or empty otherwise.
	 */
	Optional<Employee> getById(Long id);

	/**
	 * Delete an employee entity by its unique identifier.
	 *
	 * @param id the ID of the employee to delete.
	 */
	void delete(Long id);
}
