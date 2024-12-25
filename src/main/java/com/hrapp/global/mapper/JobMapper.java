package com.hrapp.global.mapper;

import com.hrapp.global.dto.JobDto;
import com.hrapp.global.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface JobMapper {

	JobDto map(Job entity);

	List<JobDto> map(List<Job> entities);

	Job unMap(JobDto dto);

	Job unMap(JobDto dto, @MappingTarget Job entity);

}
