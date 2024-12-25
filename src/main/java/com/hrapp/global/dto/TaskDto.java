package com.hrapp.global.dto;

import com.hrapp.global.entity.Job;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

	private Long id;

	@NotEmpty
	private String title;

	@NotEmpty
	private String description;

	@NotNull
	private Set<Job> jobs = new HashSet<>();

}
