package com.hrapp.global.dto;

import java.util.HashSet;
import java.util.Set;



import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.hrapp.global.entity.Employee;
import com.hrapp.global.entity.Location;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

	private Long id;

	@NotEmpty
	private String departmentName;

	private Location location;

	private Set<Employee> employees = new HashSet<>();

}
