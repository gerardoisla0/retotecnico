package com.seek.retotecnico.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {
    protected String id;
    protected String name;
    protected String email;
    protected String password;
}