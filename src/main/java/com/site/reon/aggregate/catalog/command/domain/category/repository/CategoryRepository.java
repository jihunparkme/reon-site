package com.site.reon.aggregate.catalog.command.domain.category.repository;

import com.site.reon.aggregate.catalog.command.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
