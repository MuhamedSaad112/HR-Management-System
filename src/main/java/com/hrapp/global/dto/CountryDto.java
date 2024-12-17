package com.hrapp.global.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.hrapp.global.entity.Region;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {


	private Long id;


	private String countryName;


	private Region region;
	
}
