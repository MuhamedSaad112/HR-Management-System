package com.hrapp.global.mapper;

import java.util.List;

import com.hrapp.global.dto.LocationDto;
import com.hrapp.global.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper
public interface LocationMapper {

	LocationDto map(Location entity);

	List<LocationDto> map(List<Location> entities);

	Location unMap(LocationDto dto);

	Location unMap(LocationDto dto, @MappingTarget Location entity);

}
