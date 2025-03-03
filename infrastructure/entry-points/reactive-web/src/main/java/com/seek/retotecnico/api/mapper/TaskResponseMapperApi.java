package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})
public interface TaskResponseMapperApi {

    TaskResponseMapperApi MAPPER = Mappers.getMapper(TaskResponseMapperApi.class);
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi requestToResponse(
            SeekRequestApi createCustomerRequestApi, TechnicalMessage technicalMessage);

    @Mapping(target = "listTaskRS.tasks", source = "tasks")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi listToTaskResponse(List<Task> tasks, TechnicalMessage technicalMessage);

    @Mapping(target = "title", source = "seekRequestApi.saveTaskRQ.title")
    @Mapping(target = "description", source = "seekRequestApi.saveTaskRQ.description")
    @Mapping(target = "status", source = "seekRequestApi.saveTaskRQ.status")
    Task taskUserRequestToClient(SeekRequestApi seekRequestApi);

    @Mapping(target = "title", source = "seekRequestApi.updateTaskRQ.title")
    @Mapping(target = "description", source = "seekRequestApi.updateTaskRQ.description")
    @Mapping(target = "status", source = "seekRequestApi.updateTaskRQ.status")
    Task taskUserRequestToTaskUpdate(SeekRequestApi seekRequestApi);

    @Mapping(target = "saveTaskRS.title", source = "task.title")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi taskToCreateTaskResponse(Task task, TechnicalMessage technicalMessage);

}
