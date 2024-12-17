package com.hrapp.global.repository;

import com.hrapp.global.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL repository for the Task entity.
 */

@SuppressWarnings("unused")
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

}
