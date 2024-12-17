package com.hrapp.global.service.impl;

import java.util.Optional;

import com.hrapp.global.entity.Employee;
import com.hrapp.global.repository.EmployeeRepo;
import com.hrapp.global.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepo employeeRepo; // Injecting the EmployeeRepo for database operations

	/**
	 * Retrieves an employee by their first name and last name, ordered by ID.
	 *
	 * @param firstName The first name of the employee
	 * @param lastName The last name of the employee
	 * @return The employee with the given first and last name, ordered by ID
	 */
	@Override
	public Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName) {
		log.debug("Request to get Employee by FirstName And LastName : {}:{}", firstName, lastName); // Logging the request
		return employeeRepo.findByFirstNameAndLastNameOrderById(firstName, lastName); // Retrieving employee by first and last name
	}

	/**
	 * Retrieves all employees with pagination and sorting.
	 *
	 * @param pageable Pagination and sorting information (page, size, sort)
	 * @return A paginated list of employees
	 */
	@Override
	public Page<Employee> findAll(Pageable pageable) {
		log.debug("Request to Get all Employees with Sorted"); // Logging the request
		return employeeRepo.findAll(pageable); // Returning the paginated list of employees
	}

	/**
	 * Saves the given employee to the database.
	 *
	 * @param entity The employee entity to save
	 * @return The saved employee entity
	 */
	@Override
	public Employee save(Employee entity) {
		log.debug("Request to save Employee : {}", entity); // Logging the save request
		return employeeRepo.save(entity); // Saving the employee entity
	}

	/**
	 * Updates the given employee in the database.
	 *
	 * @param entity The employee entity to update
	 * @return The updated employee entity
	 */
	@Override
	public Employee update(Employee entity) {
		log.debug("Request to update Employee : {}", entity); // Logging the update request
		return employeeRepo.save(entity); // Saving the updated employee entity
	}

	/**
	 * Retrieves an employee by their ID.
	 *
	 * @param id The ID of the employee
	 * @return The employee with the given ID, or null if not found
	 */
	@Override
	public Employee findById(Long id) {
		return employeeRepo.findById(id).orElse(null); // Returning the employee with the given ID or null if not found
	}

	/**
	 * Retrieves a reference to an employee by their ID using a cache for better performance.
	 *
	 * @param id The ID of the employee
	 * @return An Optional containing the employee reference, or empty if not found
	 */
	@Override
	public Optional<Employee> getById(Long id) {
		log.debug("Request to Get Employee from cache: {}", id); // Logging the cache retrieval request
		return Optional.of(employeeRepo.getReferenceById(id)); // Returning the employee reference
	}

	/**
	 * Deletes an employee by their ID.
	 *
	 * @param id The ID of the employee to delete
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Employee : {}", id); // Logging the delete request
		employeeRepo.deleteById(id); // Deleting the employee by its ID
	}
}
