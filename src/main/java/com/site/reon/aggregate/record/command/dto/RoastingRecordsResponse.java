package com.site.reon.aggregate.record.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoastingRecordsResponse {
    private Long id;
    private String title;
    private String roasterSn;
    private LocalDateTime createdDt;
    private String email;
    private String firstName;
    private String lastName;
}
