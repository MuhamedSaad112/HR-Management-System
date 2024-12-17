package com.hrapp.global.dto;

import java.time.Instant;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.hrapp.global.entity.Department;
import com.hrapp.global.entity.Employee;
import com.hrapp.global.entity.Job;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryDto {

	private Long id;

	@NotEmpty
	private Instant startDate;

	@NotEmpty
	private Instant endDate;

	@NotNull
	private Job job;

	@NotNull
	private Department department;

	@NotNull
	private Employee employee;

}
