package com.hrapp.global.service.impl;

import com.hrapp.global.entity.Task;
import com.hrapp.global.repository.TaskRepo;
import com.hrapp.global.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Task}.
 */


@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo; // Injecting the TaskRepo for database operations

    /**
     * Saves the given task to the database.
     *
     * @param task The task entity to save
     * @return The saved task entity
     */
    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task); // Logging the save request
        return taskRepo.save(task); // Saving the task entity
    }

    /**
     * Updates the given task in the database.
     *
     * @param task The task entity to update
     * @return The updated task entity
     */
    @Override
    public Optional<Task> update(Task task) {
        log.debug("Request to update Task : {}", task); // Logging the update request
        return taskRepo.findById(task.getId()).map(existingTask -> {
            if (existingTask.getTitle() != null) {
                existingTask.setTitle(task.getTitle());
            }
            if (existingTask.getDescription() != null) {
                existingTask.setDescription(task.getDescription());
            }
            return existingTask;
        }).map(taskRepo::save);// Saving the updated task entity
    }

    /**
     * Retrieves all tasks with pagination and sorting.
     *
     * @return A paginated list of tasks
     */
    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        log.debug("Request to get all Task "); // Logging the request to fetch all tasks
        return taskRepo.findAll(); // Returning the paginated list of tasks
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task
     * @return The task with the given ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Task findById(Long id) {
        log.debug("Request to get Task : {}", id); // Logging the request for a task with the given ID
        return taskRepo.findById(id).orElse(null); // Returning the task with the given ID, or null if not found
    }

    /**
     * Retrieves a reference to a task by its ID using a cache for better performance.
     *
     * @param id The ID of the task
     * @return An Optional containing the task reference, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Task> getById(Long id) {
        log.debug("Request to Get Task from cache: {}", id); // Logging the cache retrieval request
        return Optional.of(taskRepo.getReferenceById(id)); // Returning the task reference
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id); // Logging the delete request
        taskRepo.deleteById(id); // Deleting the task by its ID
    }
}
