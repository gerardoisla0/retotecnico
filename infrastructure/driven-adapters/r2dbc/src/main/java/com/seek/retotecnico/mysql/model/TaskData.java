package com.seek.retotecnico.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class TaskData {
    @Id
    private Long id;
    @Column("title")
    protected String title;
    @Column("description")
    protected String description;
    @Column("status")
    protected String status;
    @Column("created_at")
    private LocalDateTime createAt;
}
