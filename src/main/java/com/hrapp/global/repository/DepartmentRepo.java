package com.hrapp.global.repository;

import com.hrapp.global.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Department entity.
 */

@SuppressWarnings("unused")
@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

}
