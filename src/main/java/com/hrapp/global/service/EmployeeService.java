package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {

	// Get the "FirstName And LastName" Employee
	Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName);

	// Save a Employee.

	Employee save(Employee Employee);

	// updates a Employee.

	Employee update(Employee Employee);

	// Get all the Employee.

	Page<Employee> findAll(Pageable pageable);

	// Get the "id" Employee

	Employee findById(Long id);

	// Get the "id" Employee from cash
	public Optional<Employee> getById(Long id);

	// Delete the "id" Employee.

	void delete(Long id);

}
