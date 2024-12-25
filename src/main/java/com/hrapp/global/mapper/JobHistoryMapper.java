package com.hrapp.global.mapper;

import com.hrapp.global.dto.JobHistoryDto;
import com.hrapp.global.entity.JobHistory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper
public interface JobHistoryMapper {

	JobHistoryDto map(JobHistory entity);

	List<JobHistoryDto> map(List<JobHistory> entities);

	JobHistory unMap(JobHistoryDto dto);

	JobHistory unMap(JobHistoryDto dto, @MappingTarget JobHistory entity);

}
