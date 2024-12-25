package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.JobDto;
import com.hrapp.global.entity.Job;
import com.hrapp.global.mapper.JobMapper;
import com.hrapp.global.repository.JobRepo;
import com.hrapp.global.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
 * REST controller for managing {@link com.hrapp.global.entity.Job}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
@Transactional
public class JobController {

    private final JobService jobService;
    private final JobMapper jobMapper;
    private static final String ENTITY_NAME = "job";
    private final JobRepo jobRepo;
    @Value("${properties.clientApp.name}")
    private String applicationName;

    /**
     * {@code POST  /jobs} : Create a new job.
     *
     * @param dto the job to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new job, or with status {@code 400 (Bad Request)} if the job has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jobs")
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobDto dto) throws URISyntaxException {
        log.debug("REST request to save Job : {}", dto);

        if (dto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Job cannot already have an ID");
        }

        if (dto.getJobTitle() == null || dto.getJobTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Name is required");
        }
        if (dto.getMaxSalary() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max Salary is required");
        }
        if (dto.getMinSalary() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Min Salary is required");
        }
        if (dto.getEmployee() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is required");
        }
        if (dto.getTasks() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tasks is required");
        }

        Job job = jobMapper.unMap(dto);
        Job entity = jobService.save(job);
        JobDto returnDto = jobMapper.map(entity);

        return ResponseEntity
                .created(new URI("/api/jobs/" + returnDto.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
                .body(returnDto);
    }

    /**
     * {@code PUT  /jobs/:id} : Updates an existing job.
     *
     * @param id  the id of the job to save.
     * @param dto the job to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated job,
     * or with status {@code 400 (Bad Request)} if the job is not valid,
     * or with status {@code 500 (Internal Server Error)} if the job couldn't be updated.
     */
    @PutMapping("/jobs/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable(value = "id", required = false) final Long id,@Valid  @RequestBody JobDto dto) {
        log.debug("REST request to partial update Job partially : {}", dto);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobRepo.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }


        Job currentJob = jobService.getById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

        Job job = jobMapper.unMap(dto, currentJob);
        Optional<Job> entity = jobService.update(job);
        JobDto returnDto = jobMapper.map(entity.get());

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, job.getId().toString()))
                .body(returnDto);
    }


    /**
     * {@code GET  /jobs} : get all the jobs.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobs in body.
     */
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs(Pageable pageable,
                                                @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Jobs");
        Page<Job> page;
        if (eagerload) {
            page = jobRepo.findAllWithEagerRelationships(pageable);
        } else {
            page = jobService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jobs/:id} : get the "id" job.
     *
     * @param id the id of the job to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the job, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDto> getJob(@PathVariable Long id) {
        log.debug("REST request to get Job : {}", id);

        Optional<Job> entity = jobRepo.findOneWithEagerRelationships(id);
        if (entity != null) {
            JobDto dto = jobMapper.map(entity.get());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * {@code DELETE  /jobs/:id} : delete the "id" job.
     *
     * @param id the id of the job to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        if (!jobService.getById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        } else {

            jobService.delete(id);
            return ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build();
        }

    }

}
