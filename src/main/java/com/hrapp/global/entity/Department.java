package com.hrapp.global.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "department_name", nullable = false)
	private String departmentName;

	// Relationships

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "country" }, allowSetters = true)
	private Location location;

	@Schema(description = "A relationship")
	@OneToMany(mappedBy = "department")
	@JsonIgnoreProperties(value = { "jobs", "manager", "department" }, allowSetters = true)
	private Set<Employee> employees = new HashSet<>();
}
