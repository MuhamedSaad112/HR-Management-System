package com.hrapp.global.mapper;

import java.util.List;

import com.hrapp.global.dto.TaskDto;
import com.hrapp.global.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper
public interface TaskMapper {

	TaskDto map(Task entity);

	List<TaskDto> map(List<Task> entities);

	Task unMap(TaskDto dto);

	Task unMap(TaskDto dto, @MappingTarget Task entity);

}
