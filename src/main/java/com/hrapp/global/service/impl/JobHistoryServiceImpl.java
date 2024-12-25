package com.hrapp.global.service.impl;

import com.hrapp.global.entity.JobHistory;
import com.hrapp.global.repository.JobHistoryRepo;
import com.hrapp.global.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobHistory}.
 */

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepo historyRepo; // Injecting the JobHistoryRepo for database operations
    private final JobHistoryRepo jobHistoryRepo;

    /**
     * Saves the given job history to the database.
     *
     * @param jobHistory The job history entity to save
     * @return The saved job history entity
     */
    @Override
    public JobHistory save(JobHistory jobHistory) {
        log.debug("Request to save jobHistory : {}", jobHistory); // Logging the save request
        return historyRepo.save(jobHistory); // Saving the job history entity
    }

    /**
     * Updates the given job history in the database.
     *
     * @param jobHistory The job history entity to update
     * @return The updated job history entity
     */
    @Override
    public Optional<JobHistory> update(JobHistory jobHistory) {
        log.debug("Request to update jobHistory : {}", jobHistory); // Logging the update request
        return historyRepo.findById(jobHistory.getId()).map(existingJobHistory -> {

            if (jobHistory.getStartDate() != null) {
                existingJobHistory.setStartDate(jobHistory.getStartDate());
            }
            if (jobHistory.getEndDate() != null) {
                existingJobHistory.setEndDate(jobHistory.getEndDate());
            }
            return existingJobHistory;

        }).map(jobHistoryRepo::save); // Saving the updated job history entity
    }

    /**
     * Retrieves all job histories with pagination and sorting.
     *
     * @param pageable Pagination and sorting information (page, size, sort)
     * @return A paginated list of job histories
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobHistory> findAll(Pageable pageable) {
        log.debug("Request to Get all jobHistories"); // Logging the request to fetch all job histories
        return historyRepo.findAll(pageable); // Returning the paginated list of job histories
    }

    /**
     * Retrieves a job history by its ID.
     *
     * @param id The ID of the job history
     * @return The job history with the given ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public JobHistory findById(Long id) {
        log.debug("Request to Get jobHistory : {}", id); // Logging the request for a job history with the given ID
        return historyRepo.findById(id).orElse(null); // Returning the job history with the given ID, or null if not found
    }

    /**
     * Retrieves a reference to a job history by its ID using a cache for better performance.
     *
     * @param id The ID of the job history
     * @return An Optional containing the job history reference, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JobHistory> getById(Long id) {
        log.debug("Request to Get JobHistory from cache: {}", id); // Logging the cache retrieval request
        return Optional.of(historyRepo.getReferenceById(id)); // Returning the job history reference
    }

    /**
     * Deletes a job history by its ID.
     *
     * @param id The ID of the job history to delete
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete jobHistory : {}", id); // Logging the delete request
        historyRepo.deleteById(id); // Deleting the job history by its ID
    }
}
