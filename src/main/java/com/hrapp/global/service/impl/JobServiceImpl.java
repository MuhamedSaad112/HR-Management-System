package com.hrapp.global.service.impl;

import com.hrapp.global.entity.Job;
import com.hrapp.global.repository.JobRepo;
import com.hrapp.global.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Job}.
 */

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepo jobRepo; // Injecting the JobRepo for database operations

    /**
     * Saves the given job to the database.
     *
     * @param job The job entity to save
     * @return The saved job entity
     */
    @Override
    public Job save(Job job) {
        log.debug("Request to save job : {}", job); // Logging the save request
        return jobRepo.save(job); // Saving the job entity
    }

    /**
     * Updates the given job in the database.
     *
     * @param job The job entity to update
     * @return The updated job entity
     */
    @Override
    public Optional<Job> update(Job job) {
        log.debug("Request to update job : {}", job); // Logging the update request
        return jobRepo.findById(job.getId()).map(existingJob -> {
            if (existingJob.getJobTitle() != null) {
                existingJob.setJobTitle(job.getJobTitle());
            }
            if (existingJob.getTasks() != null) {
                existingJob.setTasks(job.getTasks());
            }
            if (existingJob.getEmployee() != null) {
                existingJob.setEmployee(job.getEmployee());
            }
            if (existingJob.getMinSalary() != null) {
                existingJob.setMinSalary(job.getMinSalary());
            }
            if (existingJob.getMaxSalary() != null) {
                existingJob.setMaxSalary(job.getMaxSalary());
            }

            return existingJob;
        }).map(jobRepo::save); // Saving the updated job entity
    }

    /**
     * Retrieves all jobs with pagination and sorting.
     *
     * @param pageable Pagination and sorting information (page, size, sort)
     * @return A paginated list of jobs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Job> findAll(Pageable pageable) {
        log.debug("Request to get list of jobs"); // Logging the request to fetch all jobs
        return jobRepo.findAll(pageable); // Returning the paginated list of jobs
    }

    /**
     * Retrieves a job by its ID.
     *
     * @param id The ID of the job
     * @return The job with the given ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Job findById(Long id) {
        log.debug("Request to get job : {}", id); // Logging the request for a job with the given ID
        return jobRepo.findById(id).orElse(null); // Returning the job with the given ID, or null if not found
    }

    /**
     * Retrieves a reference to a job by its ID using a cache for better performance.
     *
     * @param id The ID of the job
     * @return An Optional containing the job reference, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Job> getById(Long id) {
        log.debug("Request to Get Job from cache : {}", id); // Logging the cache retrieval request
        return Optional.of(jobRepo.getReferenceById(id)); // Returning the job reference
    }

    /**
     * Deletes a job by its ID.
     *
     * @param id The ID of the job to delete
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete job : {}", id); // Logging the delete request
        jobRepo.deleteById(id); // Deleting the job by its ID
    }
}
