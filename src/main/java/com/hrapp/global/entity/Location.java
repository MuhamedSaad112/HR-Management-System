package com.hrapp.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
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

import java.io.Serializable;

@Entity
@Table(name = "location")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "not an ignored comment")
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "street_address")
	private String streetAddress;

	@NotNull
	@Column(name = "postal_code")
	private String postalCode;

	@NotNull
	@Column(name = "city")
	private String city;

	@NotNull
	@Column(name = "state_province")
	private String stateProvince;

	// Relationships

	@JsonIgnoreProperties(value = { "region" }, allowSetters = true)
	@OneToOne
	@JoinColumn(unique = true)
	private Country country;

}
