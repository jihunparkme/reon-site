package com.site.reon.aggregate.catalog.command.domain.dto;

import com.site.reon.aggregate.catalog.command.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public record CategoryResponse(
        Long id,
        String name
) {
    @Builder
    public CategoryResponse {
    }

    public static CategoryResponse of(final Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
