package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.TaskDto;
import com.hrapp.global.entity.Task;
import com.hrapp.global.mapper.TaskMapper;
import com.hrapp.global.repository.TaskRepo;
import com.hrapp.global.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hrapp.global.entity.Task}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class TaskController {

	private final TaskService taskService;
	private final TaskMapper taskMapper;
	private final TaskRepo taskRepo;
	@Value("${properties.clientApp.name}")
	private String applicationName;
	private static final String ENTITY_NAME = "task";

	/**
	 * {@code POST  /tasks} : Create a new task.
	 *
	 * @param dto the task to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new task, or with status {@code 400 (Bad Request)} if the task has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tasks")
	public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto dto) throws URISyntaxException {
		log.debug("REST request to save Task : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Task cannot already have an ID");
		}

		if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Title is required");
		}

		if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Description is required");
		}

		if (dto.getJobs() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Jobs is required");
		}

		Task Task = taskMapper.unMap(dto);
		Task entity = taskService.save(Task);
		TaskDto returnDto = taskMapper.map(entity);

		return ResponseEntity
				.created(new URI("/api/tasks/" + returnDto.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code PUT  /tasks/:id} : Updates an existing task.
	 *
	 * @param id  the id of the task to save.
	 * @param dto the task to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task,
	 * or with status {@code 400 (Bad Request)} if the task is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
	 */
	@PutMapping("/tasks/{id}")
	public ResponseEntity<TaskDto> updateTask(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody TaskDto dto) {
		log.debug("REST request to partial update Task partially : {}", dto);


		if (dto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, dto.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!taskRepo.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Task currentTask = taskService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Task Task = taskMapper.unMap(dto, currentTask);
		Optional<Task> entity = taskService.update(Task);
		TaskDto returnDto = taskMapper.map(entity.get());

		return ResponseEntity
				.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code GET  /tasks} : get all the tasks.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
	 */
	@GetMapping("/tasks")
	public List<Task> getAllTasks() {
		log.debug("REST request to get all Tasks");
		return taskService.findAll();
	}


	/**
	 * {@code GET  /tasks/:id} : get the "id" task.
	 *
	 * @param id the id of the task to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tasks/{id}")
	public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
		log.debug("REST request to get Task : {}", id);

		Task entity = taskService.findById(id);
		if (entity != null) {
			TaskDto dto = taskMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}


	/**
	 * {@code DELETE  /tasks/:id} : delete the "id" task.
	 *
	 * @param id the id of the task to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		log.debug("REST request to delete Task : {}", id);
		if (!taskService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			taskService.delete(id);
			return ResponseEntity
					.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();
		}

	}

}
