package com.seek.retotecnico.model.customer;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Customer {
    protected String name;
    protected String lastName;
    protected String documentId;
    protected Integer age;
    protected Date birthDay;
}