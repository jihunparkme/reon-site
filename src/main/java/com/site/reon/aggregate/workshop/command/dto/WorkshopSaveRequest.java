package com.site.reon.aggregate.workshop.command.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkshopSaveRequest {
    @NotNull(message = "title is required.")
    @Size(min = 1, max = 100, message = "The title length must be between 1 and 100 characters.")
    private String title;

    @NotNull(message = "content is required.")
    @Size(min = 1, max = 20000, message = "The content length must be between 1 and 20000 characters.")
    private String content;

    @Min(value = 1, message = "recordId must be at least greater than 1.")
    private long recordId;
}
