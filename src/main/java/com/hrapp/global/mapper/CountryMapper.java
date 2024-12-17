package com.hrapp.global.mapper;

import java.util.List;

import com.hrapp.global.dto.CountryDto;
import com.hrapp.global.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper
public interface CountryMapper {

	CountryDto map(Country exist);

	List<CountryDto> map(List<Country> entities);

	Country unMap(CountryDto dto);

	Country unMap(CountryDto dto, @MappingTarget Country entity);

}
