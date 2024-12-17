package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DepartmentService {

	// Save a Department.

	Department save(Department department);

	// Partially updates a Department.

	Department update(Department department);

	// Get all the department.

	Page<Department> findAll(Pageable pageable);

	// Get the "id" Department

	Department findById(Long id);

	// Get the "id" Department from cash
	public Optional<Department> getById(Long id);

	// Delete the "id" Department.

	void delete(Long id);
}