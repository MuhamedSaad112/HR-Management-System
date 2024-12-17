package com.hrapp.global.repository;

import com.hrapp.global.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName);

}
