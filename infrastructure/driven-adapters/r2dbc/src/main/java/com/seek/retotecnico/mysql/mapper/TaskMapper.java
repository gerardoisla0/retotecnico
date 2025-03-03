package com.seek.retotecnico.mysql.mapper;

import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.mysql.model.TaskData;
import com.seek.retotecnico.mysql.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);
    @Mapping(target = "title", source = "task.title")
    @Mapping(target = "description", source = "task.description")
    @Mapping(target = "status", source = "task.status")
    @Mapping(target = "id", source = "id")
    TaskData domainToData(Task task, Long id);
    @Mapping(target = "title", source = "task.title")
    @Mapping(target = "description", source = "task.description")
    @Mapping(target = "status", source = "task.status")
    TaskData domainToDataWitouthID(Task task);
    @Mapping(target = "title", source = "taskData.title")
    @Mapping(target = "description", source = "taskData.description")
    @Mapping(target = "status", source = "taskData.status")
    @Mapping(target = "id", source = "taskData.id")
    Task dataToDomain(TaskData taskData);
}
