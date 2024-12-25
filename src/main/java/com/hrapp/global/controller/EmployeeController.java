package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.EmployeeDto;
import com.hrapp.global.entity.Employee;
import com.hrapp.global.mapper.EmployeeMapper;
import com.hrapp.global.repository.EmployeeRepo;
import com.hrapp.global.service.EmployeeService;
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
 * REST controller for managing {@link com.hrapp.global.entity.Country}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
@Transactional
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    private static final String ENTITY_NAME = "employee";
    private final EmployeeRepo employeeRepo;
    @Value("${properties.clientApp.name}")
    private String applicationName;


    @GetMapping("/employees/filter")
    public ResponseEntity<EmployeeDto> findFirstNameAndLastName(@RequestParam String firstName,
                                                                @RequestParam String lastName) {

        Employee entity = employeeService.findByFirstNameAndLastNameOrderById(firstName, lastName);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee not found with given first and last name");
        }
        EmployeeDto dto = employeeMapper.map(entity);

        return ResponseEntity.ok(dto);
    }


    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param dto the employee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employee, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto dto) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", dto);

        if (dto.getEmpId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new employee cannot already have an ID");
        }


        Employee emp = employeeMapper.unMap(dto);
        Employee entity = employeeService.save(emp);
        EmployeeDto returnDto = employeeMapper.map(entity);

        return ResponseEntity
                .created(new URI("/api/employees/" + returnDto.getEmpId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getEmpId().toString()))
                .body(returnDto);
    }


    /**
     * {@code PUT  /employees/:id} : Updates an existing employee.
     *
     * @param id  the id of the employee to save.
     * @param dto the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeea(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody EmployeeDto dto) {
        log.debug("REST request to partial update Employee partially : {}", dto);

        if (dto.getEmpId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getEmpId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepo.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }


        Employee currentEmp = employeeService.getById(dto.getEmpId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

        Employee emp = employeeMapper.unMap(dto, currentEmp);
        Optional<Employee> entity = employeeService.update(emp);
        EmployeeDto returnDto = employeeMapper.map(entity.get());

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, returnDto.getEmpId().toString()))
                .body(returnDto);
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(Pageable pageable) {
        log.debug("REST request to get a page of Employees");
        Page<Employee> page = employeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);

        Employee entity = employeeService.findById(id);
        if (entity != null) {
            EmployeeDto dto = employeeMapper.map(entity);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        if (!employeeService.getById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        } else {

            employeeService.delete(id);
            return ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build();
        }

    }

}
