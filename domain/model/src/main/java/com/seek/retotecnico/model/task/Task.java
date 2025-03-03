package com.seek.retotecnico.model.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Task {
    protected Long id;
    protected String title;
    protected String description;
    protected String status;
}
