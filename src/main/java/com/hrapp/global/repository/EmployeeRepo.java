package com.hrapp.global.repository;

import com.hrapp.global.entity.Employee;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName);

	Optional<Employee> findByEmail(@Email String email);

}
