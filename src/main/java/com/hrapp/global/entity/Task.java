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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Table(name = "task")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Task")
@Schema(description = "Task entity.\n@author The HR  team.")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "title")
	private String title;

	@NotNull
	@Column(name = "description")
	private String description;

	// Relationships

	@ManyToMany(mappedBy = "tasks")
	@JsonIgnoreProperties(value = { "tasks", "employee" }, allowSetters = true)
	private Set<Job> jobs = new HashSet<>();

}
