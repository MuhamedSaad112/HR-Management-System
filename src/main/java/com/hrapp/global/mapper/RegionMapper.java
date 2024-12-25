package com.hrapp.global.mapper;

import com.hrapp.global.dto.RegionDto;
import com.hrapp.global.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper
public interface RegionMapper {

	RegionDto map(Region entity);

	List<RegionDto> map(List<Region> entities);

	Region unMap(RegionDto dto);

	Region unMap(RegionDto dto, @MappingTarget Region entity);

}
