package com.hrapp.global.service.impl;

import java.util.Optional;

import com.hrapp.global.entity.Department;
import com.hrapp.global.repository.DepartmentRepo;
import com.hrapp.global.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepo departmentRepo;

	@Override
	public Department save(Department department) {
		log.debug("Request to save department : {}", department);
		return departmentRepo.save(department);
	}

	@Override
	public Department update(Department department) {
		log.debug("Request to update department :{}", department);
		return departmentRepo.save(department);
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		log.debug("Request to Get all departments");
		return departmentRepo.findAll(pageable);
	}

	@Override
	public Department findById(Long id) {
		log.debug("Request to Get department}", id);
		return departmentRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<Department> getById(Long id) {
		log.debug("Request to Get Employee  from cash : {}", id);
		return Optional.of(departmentRepo.getReferenceById(id));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete department}", id);
		departmentRepo.deleteById(id);

	}

}
