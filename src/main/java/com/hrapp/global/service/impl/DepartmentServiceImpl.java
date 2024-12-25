package com.hrapp.global.service.impl;

import com.hrapp.global.entity.Department;
import com.hrapp.global.repository.DepartmentRepo;
import com.hrapp.global.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Department}.
 */

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo; // Injecting the DepartmentRepo for database operations

    /**
     * Saves the given department to the database.
     *
     * @param department The department entity to save
     * @return The saved department entity
     */
    @Override
    public Department save(Department department) {
        log.debug("Request to save department : {}", department); // Logging the save request
        return departmentRepo.save(department); // Saving the department entity
    }

    /**
     * Updates the given department in the database.
     *
     * @param department The department entity to update
     * @return The updated department entity
     */
    @Override
    public Optional<Department> update(Department department) {
        log.debug("Request to update department : {}", department); // Logging the update request
        return departmentRepo.findById(department.getId()).map(existingDepartment -> {
            if (department.getDepartmentName() != null) {
                existingDepartment.setDepartmentName(department.getDepartmentName());
            }
            return existingDepartment;
        }).map(departmentRepo::save);
    }

    /**
     * Retrieves all departments with pagination support.
     *
     * @return A paginated list of departments
     */
    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        log.debug("Request to get all departments"); // Logging the find all request
        return departmentRepo.findAll(); // Returning the paginated list of departments
    }

    /**
     * Retrieves a department by its ID.
     *
     * @param id The ID of the department
     * @return The department with the given ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Department findById(Long id) {
        log.debug("Request to get department : {}", id); // Logging the find by ID request
        return departmentRepo.findById(id).orElse(null); // Returning the department or null if not found
    }

    /**
     * Retrieves a department by its ID using a reference for better performance.
     *
     * @param id The ID of the department
     * @return An Optional containing the department, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Department> getById(Long id) {
        log.debug("Request to Get department from cache : {}", id); // Logging the cache retrieval request
        return Optional.of(departmentRepo.getReferenceById(id)); // Returning a reference of the department
    }

    /**
     * Deletes a department by its ID.
     *
     * @param id The ID of the department to delete
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete department : {}", id); // Logging the delete request
        departmentRepo.deleteById(id); // Deleting the department by its ID
    }
}
