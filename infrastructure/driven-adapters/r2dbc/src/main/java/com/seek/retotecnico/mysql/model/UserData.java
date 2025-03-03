package com.seek.retotecnico.mysql.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
}
