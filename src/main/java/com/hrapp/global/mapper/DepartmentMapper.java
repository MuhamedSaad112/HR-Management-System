package com.hrapp.global.mapper;

import com.hrapp.global.dto.DepartmentDto;
import com.hrapp.global.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface DepartmentMapper {

	DepartmentDto map(Department entity);

	List<DepartmentDto> map(List<Department> entities);

	Department unMap(DepartmentDto dto);

	Department unMap(DepartmentDto dto, @MappingTarget Department entity);

}
