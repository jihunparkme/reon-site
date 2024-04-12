package com.site.reon.aggregate.member.command.domain.repository;

import com.site.reon.aggregate.member.command.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}