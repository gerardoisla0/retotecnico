package com.seek.retotecnico.model.customer;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class User {
    protected String name;
    protected String email;
    protected String password;
}