package com.hrapp.global.mapper;

import java.util.List;

import com.hrapp.global.dto.JobDto;
import com.hrapp.global.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface JobMapper {

	JobDto map(Job entity);

	List<JobDto> map(List<Job> entities);

	Job unMap(JobDto dto);

	Job unMap(JobDto dto, @MappingTarget Job entity);

}
