package com.site.reon.aggregate.workshop.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Long>, WorkshopRepositoryCustom {
}
