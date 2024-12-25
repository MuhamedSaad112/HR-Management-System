package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.DepartmentDto;
import com.hrapp.global.entity.Department;
import com.hrapp.global.mapper.DepartmentMapper;
import com.hrapp.global.repository.DepartmentRepo;
import com.hrapp.global.service.DepartmentService;
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
 * REST controller for managing {@link com.hrapp.global.entity.Department}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class DepartmenController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final DepartmentRepo departmentRepo;
    @Value("${properties.clientApp.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "department";


    /**
     * {@code POST  /departments} : Create a new department.
     *
     * @param dto the department to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new department, or with status {@code 400 (Bad Request)} if the department has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto dto) throws URISyntaxException {
        log.debug("REST request to save Department : {}", dto);

        if (dto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Department cannot already have an ID");
        }

        if (dto.getDepartmentName() == null || dto.getDepartmentName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department Name is required");
        }
        if (dto.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region is Location");
        }
        if (dto.getEmployees() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region is Location");
        }

        Department Department = departmentMapper.unMap(dto);
        Department entity = departmentService.save(Department);
        DepartmentDto returnDto = departmentMapper.map(entity);

        return ResponseEntity
                .created(new URI("/api/departments/" + returnDto.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
                .body(returnDto);
    }

    /**
     * {@code PUT  /departments/:id} : Updates an existing department.
     *
     * @param id  the id of the department to save.
     * @param dto the department to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated department,
     * or with status {@code 400 (Bad Request)} if the department is not valid,
     * or with status {@code 500 (Internal Server Error)} if the department couldn't be updated.
     */
    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DepartmentDto dto) {
        log.debug("REST request to partial update Department partially : {}", dto);


        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departmentRepo.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }


        Department currentDepartment = departmentService.getById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

        Department Department = departmentMapper.unMap(dto, currentDepartment);
        Optional<Department> entity = departmentService.update(Department);
        DepartmentDto returnDto = departmentMapper.map(entity.get());

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
                .body(returnDto);
    }


    /**
     * {@code GET  /departments} : get all the departments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departments in body.
     */
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        log.debug("REST request to get all Departments");
        return departmentService.findAll();
    }


    /**
     * {@code GET  /departments/:id} : get the "id" department.
     *
     * @param id the id of the department to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the department, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDto> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);

        Department entity = departmentService.findById(id);
        if (entity != null) {
            DepartmentDto dto = departmentMapper.map(entity);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * {@code DELETE  /departments/:id} : delete the "id" department.
     *
     * @param id the id of the department to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        if (!departmentService.getById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        } else {

            departmentService.delete(id);
            return ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build();
        }

    }

}
