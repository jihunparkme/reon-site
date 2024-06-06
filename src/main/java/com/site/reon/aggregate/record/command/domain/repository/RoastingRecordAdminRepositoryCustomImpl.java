package com.site.reon.aggregate.record.command.domain.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.site.reon.aggregate.member.command.domain.QMember;
import com.site.reon.aggregate.record.command.domain.QRoastingRecord;
import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import com.site.reon.aggregate.record.util.RecordUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoastingRecordAdminRepositoryCustomImpl implements RoastingRecordAdminRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<RoastingRecord> findAllByAdminFilter(final AdminRecordSearchRequestParam param) {
        final var pageable = PageRequest.of(param.getPage(), param.getSize(), Sort.by("id").descending());

        final List<RoastingRecord> content = getContent(param, pageable);
        final long count = getCount(param);

        return new PageImpl<>(content, pageable, count);
    }

    private List<RoastingRecord> getContent(final AdminRecordSearchRequestParam param, final PageRequest pageable) {
        QRoastingRecord record = QRoastingRecord.roastingRecord;
        QMember member = QMember.member;
        final JPAQuery<RoastingRecord> query = jpaQueryFactory.selectFrom(record)
                .join(member).on(record.roastingInfo.memberId.eq(member.id));

        applyWhereClause(param, query, record, member);

        return query
                .orderBy(record.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private long getCount(final AdminRecordSearchRequestParam param) {
        QRoastingRecord record = QRoastingRecord.roastingRecord;
        QMember member = QMember.member;
        final JPAQuery<Long> query = jpaQueryFactory.select(record.count())
                .from(record)
                .join(member).on(record.roastingInfo.memberId.eq(member.id));;

        applyWhereClause(param, query, record, member);

        return query.fetchOne();
    }

    private <T> void applyWhereClause(
            final AdminRecordSearchRequestParam param, final JPAQuery<T> query,
            final QRoastingRecord record, final QMember member) {

        if (StringUtils.isNotEmpty(param.getTitle())) {
            query.where(record.roastingInfo.title.containsIgnoreCase(param.getTitle()));
        }

        if (StringUtils.isNotEmpty(param.getSerialNo())) {
            query.where(record.roastingInfo.roasterSn.eq(param.getSerialNo()));
        }

        if (StringUtils.isNotEmpty(param.getEmail())) {
            query.where(member.email.eq(param.getEmail()));
        }

        final String startDate = param.getStartDate();
        final String endDate = param.getEndDate();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            LocalDate start = LocalDate.parse(startDate, RecordUtils.DATE_FORMATTER);
            LocalDate end = LocalDate.parse(endDate, RecordUtils.DATE_FORMATTER);
            query.where(record.createdDt.between(start.atStartOfDay(), end.atTime(LocalTime.of(23, 59, 59))));
        }
    }
}
