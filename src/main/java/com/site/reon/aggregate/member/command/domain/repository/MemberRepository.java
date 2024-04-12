package com.site.reon.aggregate.member.command.domain.repository;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<List<Member>> findOneWithAuthoritiesByEmail(String email);

    @Query("SELECT member " +
            "FROM Member member " +
            "WHERE member.email=:email " +
            "AND member.oAuthClient=:oAuthClient")
    Optional<Member> findByEmailAndOAuthClient(@Param("email") String email, @Param("oAuthClient") OAuth2Client oAuthClient);

    @Query("SELECT member " +
            "FROM Member member " +
            "WHERE member.email=:email " +
            "AND member.oAuthClient=:oAuthClient")
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findWithAuthoritiesByEmailAndOAuthClient(@Param("email") String email, @Param("oAuthClient") OAuth2Client oAuthClient);

    @Modifying
    @Query("DELETE FROM Member member " +
            "WHERE member.email=:email " +
            "AND member.oAuthClient=:oAuthClient")
    int deleteByEmailAndOAuthClient(@Param("email") String email, @Param("oAuthClient") OAuth2Client oAuthClient);

    @Modifying
    @Query("UPDATE Member member " +
            "SET member.roasterSn = :serialNo " +
            "WHERE member.id = :memberId")
    int registerMemberSerialNo(@Param("serialNo") String serialNo,
                               @Param("memberId") long memberId);
}
