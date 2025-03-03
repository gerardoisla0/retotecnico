package com.seek.retotecnico.mysql.mapper;

import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import com.seek.retotecnico.mysql.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
    @Mapping(target = "name", source = "customer.name")
    @Mapping(target = "lastName", source = "customer.lastName")
    @Mapping(target = "documentId", source = "customer.documentId")
    @Mapping(target = "age", source = "customer.age")
    @Mapping(target = "birthDay", source = "customer.birthDay")
    UserData domainToData(User user);
    @Mapping(target = "name", source = "customerData.name")
    @Mapping(target = "lastName", source = "customerData.lastName")
    @Mapping(target = "documentId", source = "customerData.documentId")
    @Mapping(target = "age", source = "customerData.age")
    @Mapping(target = "birthDay", source = "customerData.birthDay")
    User dataToDomain(UserData userData);

}
