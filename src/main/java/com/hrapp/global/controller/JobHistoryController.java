package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.JobHistoryDto;
import com.hrapp.global.entity.JobHistory;
import com.hrapp.global.mapper.JobHistoryMapper;
import com.hrapp.global.repository.JobHistoryRepo;
import com.hrapp.global.service.JobHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hrapp.global.entity.JobHistory}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class JobHistoryController {

	private final JobHistoryService jobHistoryService;

	@Qualifier("jobHistoryMapper")
	private final JobHistoryMapper historyMapper;
	private static final String ENTITY_NAME = "jobHistory";
	private final JobHistoryRepo jobHistoryRepo;
	@Value("${properties.clientApp.name}")
	private String applicationName;

	/**
	 * {@code POST  /job-histories} : Create a new jobHistory.
	 *
	 * @param dto the jobHistory to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobHistory, or with status {@code 400 (Bad Request)} if the jobHistory has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/job-histories")
	public ResponseEntity<JobHistoryDto> createJobHistory(@Valid @RequestBody JobHistoryDto dto) throws URISyntaxException {
		log.debug("REST request to save JobHistory : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Job cannot already have an ID");
		}

		if (dto.getStartDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start Date is required");
		}
		if (dto.getEndDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End Date is required");
		}
		if (dto.getJob() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job is required");
		}
		if (dto.getDepartment() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required");
		}
		if (dto.getEmployee() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is required");
		}

		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start Date cannot be after End Date");
		}

		JobHistory jobHistory = historyMapper.unMap(dto);
		JobHistory entity = jobHistoryService.save(jobHistory);
		JobHistoryDto returnDto = historyMapper.map(entity);

		return ResponseEntity
				.created(new URI("/api/job-histories/" + returnDto.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code PUT  /job-histories/:id} : Updates an existing jobHistory.
	 *
	 * @param id  the id of the jobHistory to save.
	 * @param dto the jobHistory to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobHistory,
	 * or with status {@code 400 (Bad Request)} if the jobHistory is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the jobHistory couldn't be updated.
	 */
	@PutMapping("/job-histories/{id}")
	public ResponseEntity<JobHistoryDto> updateJobHistory(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody JobHistoryDto dto) {
		log.debug("REST request to partial update JobHistory partially : {}", dto);


		if (dto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, dto.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!jobHistoryRepo.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		JobHistory currentJobHistory = jobHistoryService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start Date cannot be after End Date");
		}

		JobHistory job = historyMapper.unMap(dto, currentJobHistory);
		Optional<JobHistory> entity = jobHistoryService.update(job);
		JobHistoryDto returnDto = historyMapper.map(entity.get());

		return ResponseEntity
				.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code GET  /job-histories} : get all the jobHistories.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobHistories in body.
	 */
	@GetMapping("/job-histories")
	public ResponseEntity<List<JobHistory>> getAllJobHistories(Pageable pageable) {
		log.debug("REST request to get a page of JobHistories");
		Page<JobHistory> page = jobHistoryService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}


	/**
	 * {@code GET  /job-histories/:id} : get the "id" jobHistory.
	 *
	 * @param id the id of the jobHistory to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobHistory, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/job-histories/{id}")
	public ResponseEntity<JobHistoryDto> getJobHistory(@PathVariable Long id) {
		log.debug("REST request to get JobHistory : {}", id);

		JobHistory entity = jobHistoryService.findById(id);
		if (entity != null) {
			JobHistoryDto dto = historyMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	/**
	 * {@code DELETE  /job-histories/:id} : delete the "id" jobHistory.
	 *
	 * @param id the id of the jobHistory to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/job-histories/{id}")
	public ResponseEntity<Void> deleteJobHistory(@PathVariable Long id) {
		log.debug("REST request to delete JobHistory : {}", id);
		if (!jobHistoryService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			jobHistoryService.delete(id);
			return ResponseEntity
					.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();
		}

	}

}
