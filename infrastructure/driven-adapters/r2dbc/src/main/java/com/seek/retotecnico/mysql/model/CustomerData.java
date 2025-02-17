package com.seek.retotecnico.mysql.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerData {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("document_id")
    private String documentId;
    @Column("age")
    private Integer age;
    @Column("birth_day")
    private LocalDate birthDay;
}
