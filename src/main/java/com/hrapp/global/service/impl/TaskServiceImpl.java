package com.hrapp.global.service.impl;

import java.util.Optional;

import com.hrapp.global.entity.Task;
import com.hrapp.global.repository.TaskRepo;
import com.hrapp.global.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

	private final TaskRepo taskRepo;

	@Override
	public Task save(Task task) {
		log.debug("Request to save Task : {}", task);
		return taskRepo.save(task);
	}

	@Override
	public Task update(Task task) {
		log.debug("Request to update Task : {}", task);
		return taskRepo.save(task);
	}

	@Override
	public Page<Task> findAll(Pageable pageable) {
		log.debug("Request to get all Task ");
		return taskRepo.findAll(pageable);
	}

	@Override
	public Task findById(Long id) {
		log.debug("Request to get Task : {}", id);
		return taskRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<Task> getById(Long id) {
		log.debug("Request to Get Task from cash: {}", id);
		return Optional.of(taskRepo.getReferenceById(id));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Task : {}", id);
		taskRepo.deleteById(id);

	}

}
