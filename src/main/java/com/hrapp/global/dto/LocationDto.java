package com.hrapp.global.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.hrapp.global.entity.Country;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

	@NotNull
	private Long id;

	@NotNull
	private String streetAddress;

	@NotNull
	private String postalCode;

	@NotNull
	private String city;

	@NotNull
	private String stateProvince;

	// Relationships

	@NotNull
	private Country country;

}
