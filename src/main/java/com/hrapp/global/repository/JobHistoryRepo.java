package com.hrapp.global.repository;

import com.hrapp.global.entity.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JobHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobHistoryRepo extends JpaRepository<JobHistory, Long> {

}
