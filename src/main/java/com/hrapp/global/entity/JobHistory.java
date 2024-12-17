package com.hrapp.global.entity;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_history")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "start_date")
	private Instant startDate;

	@NotNull
	@Column(name = "end_date")
	private Instant endDate;

	// Relationships

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "tasks", "employee" }, allowSetters = true)
	private Job job;

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "location", "employees" }, allowSetters = true)
	private Department department;

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "jobs", "manager", "department" }, allowSetters = true)
	private Employee employee;
}
