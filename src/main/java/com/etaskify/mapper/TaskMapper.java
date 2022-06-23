package com.etaskify.mapper;


import com.etaskify.dto.request.TaskRequestDTO;
import com.etaskify.dto.response.TaskResponseDTO;
import com.etaskify.model.Task;
import com.etaskify.repository.TaskProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskMapper {

    public static TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    public abstract Task mapDto(TaskRequestDTO taskRequestDTO);


    @Mappings({
            @Mapping(target = "endDate", expression = "java(parseLocalDateTime(task.getEndDate()))")
    })
    public abstract TaskResponseDTO mapEntityToDto(Task task);

    public String parseLocalDateTime(LocalDateTime endDate) {
        return endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Mappings({
            @Mapping(target = "endDate", expression = "java(parseLocalDateTime(taskProjection.getEndDate()))"),
            @Mapping(target = "created", expression = "java(parseLocalDateTime(taskProjection.getCreated()))")
    })
    public abstract TaskResponseDTO mapProjectionToDto(TaskProjection taskProjection);


}
