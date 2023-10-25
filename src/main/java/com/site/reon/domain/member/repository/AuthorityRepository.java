package com.site.reon.domain.member.repository;

import com.site.reon.domain.member.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}