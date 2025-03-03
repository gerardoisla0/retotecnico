package com.seek.retotecnico.mysql.mapper;

import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.mysql.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    UserData domainToData(User user);
    @Mapping(target = "name", source = "userData.name")
    @Mapping(target = "email", source = "userData.email")
    User dataToDomain(UserData userData);
}
