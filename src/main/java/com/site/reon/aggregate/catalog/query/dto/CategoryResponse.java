package com.site.reon.aggregate.catalog.query.dto;

import com.site.reon.aggregate.catalog.command.domain.category.Category;
import lombok.Builder;

public record CategoryResponse(
        Long id,
        String title
) {
    @Builder
    public CategoryResponse {
    }

    public static CategoryResponse of(final Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }
}
