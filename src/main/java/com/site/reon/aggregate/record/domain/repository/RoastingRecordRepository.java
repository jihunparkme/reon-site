package com.site.reon.aggregate.record.domain.repository;

import com.site.reon.aggregate.record.domain.RoastingRecord;
import com.site.reon.aggregate.record.service.dto.RoastingRecordListResponse;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoastingRecordRepository extends JpaRepository<RoastingRecord, Long> {
    @Query("SELECT rr " +
            "FROM RoastingRecord rr " +
            "WHERE rr.roasterSn=:roasterSn")
    Optional<RoastingRecord> findByRoasterSn(@Param(value = "roasterSn") String roasterSn);

    @Query("SELECT rr.id, rr.title, rr.createdDt " +
            "FROM RoastingRecord rr " +
            "LEFT JOIN Member m on rr.roasterSn = m.roasterSn " +
            "WHERE m.email =:email " +
            "AND m.oAuthClient =:oAuth2Client")
    Optional<List<RoastingRecordListResponse>> findRoastingRecordListBy(
            @Param(value = "email") String email, @Param(value = "oAuth2Client") OAuth2Client oAuth2Client);
}
