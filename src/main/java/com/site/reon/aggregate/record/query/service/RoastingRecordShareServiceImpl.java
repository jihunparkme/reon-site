package com.site.reon.aggregate.record.query.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.domain.repository.RoastingRecordRepository;
import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordResponse;
import com.site.reon.global.security.exception.NotFoundMemberException;
import com.site.reon.global.security.exception.NotFoundRoastingRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoastingRecordShareServiceImpl implements RoastingRecordShareService {

    private final RoastingRecordRepository recordRepository;
    private final MemberRepository memberRepository;

    @Override
    public ApiRoastingRecordResponse findRoastingRecordByIdAndEmail(final Long recordId, final String email) {
        final Optional<RoastingRecord> roastingRecordOpt = recordRepository.findById(recordId);
        if (roastingRecordOpt.isEmpty()) {
            throw new NotFoundRoastingRecordException();
        }

        final Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isEmpty()) {
            throw new NotFoundMemberException("Not Found Roasting Record Owner Member");
        }

        final RoastingRecord roastingRecord = roastingRecordOpt.get();
        final Member roastingRecordOwner = memberOpt.get();
        if (roastingRecord.getMemberId() != roastingRecordOwner.getId()) {
            throw new IllegalArgumentException("Roasting information is incorrect.");
        }

        return ApiRoastingRecordResponse.of(roastingRecord);
    }
}
