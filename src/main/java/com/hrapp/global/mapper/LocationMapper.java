package com.hrapp.global.mapper;

import com.hrapp.global.dto.LocationDto;
import com.hrapp.global.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper
public interface LocationMapper {

	LocationDto map(Location entity);

	List<LocationDto> map(List<Location> entities);

	Location unMap(LocationDto dto);

	Location unMap(LocationDto dto, @MappingTarget Location entity);

}
