package com.hrapp.global.dto;

import com.hrapp.global.entity.Department;
import com.hrapp.global.entity.Employee;
import com.hrapp.global.entity.Job;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryDto {

	private Long id;

	@NotNull
	private Instant startDate;

	@NotNull
	private Instant endDate;

	@NotNull
	private Job job;

	@NotNull
	private Department department;

	@NotNull
	private Employee employee;

}
