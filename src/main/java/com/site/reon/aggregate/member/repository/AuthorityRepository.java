package com.site.reon.aggregate.member.repository;

import com.site.reon.aggregate.member.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}